package com.databases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.*;
import com.databases.tableobjects.User;

public class UserOperation {
    
    public JSONArray getUsersData(){
        JSONArray array = new JSONArray();
        try {
            Persistence pers = (Persistence) BeanUtil.lookup("Persistence");
            SelectQuery query = new SelectQueryImpl(Table.getTable(User.TABLE));
            query.addSelectColumn(Column.getColumn(User.TABLE, "*"));
            DataObject object = pers.get(query);
            
            Iterator<Row> i = object.getRows("UserDetails");
            i.forEachRemaining(elem -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", elem.get(User.USERID));
                jsonObject.put("userName", elem.get(User.USERNAME));
                jsonObject.put("firstName", elem.get(User.FIRSTNAME));
                jsonObject.put("lastName", elem.get(User.LASTNAME));
                jsonObject.put("emailId", elem.get(User.LASTNAME));
                jsonObject.put("userPassword", elem.get(User.PASSWORD));
                array.add(jsonObject);
            });

            
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
}
