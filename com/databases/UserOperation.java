package com.databases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.Range;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.SortColumn;
import com.adventnet.ds.query.Table;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;

public class UserOperation {
    
    public JSONArray getUsersData(){
        JSONArray array = new JSONArray();
        try {
            
            // Persistence p = (Persistence) BeanUtil.lookup("Persistence");
            // SelectQuery query = new SelectQueryImpl(Table.getTable("UserDetails"));
            // Criteria c = new Criteria(new Column("UserDetails", "USER_NAME"), "vicky" , QueryConstants.EQUAL, false);
            // Criteria c2 = new Criteria(new Column("UserDetails", "FIRST_NAME"), "Vigneshwaren", QueryConstants.LIKE);
            // SortColumn sort = new SortColumn(new Column(null, "USER_ID"), false);
            // Criteria cri = c.and(c2);
            // Range range = new Range(3, Range.LAST);
            List<String> tableList = new ArrayList<String>();
            tableList.add("UserDetails");
            tableList.add("Projects");
            DataObject obj = DataAccess.get(tableList, (Criteria) null);
            // Join join = new Join("UserDetails", "Projects", new String[]{"USER_ID"}, new String[]{"CREATED_BY"}, Join.LEFT_JOIN);
            // query.addSelectColumn(new Column("UserDetails", "*"));
            // query.addSelectColumn(new Column("Projects", "*"));
            // query.setCriteria(c);
            // query.addSortColumn(sort);
            // query.setRange(range);
            // query.addJoin(join);
            // obj.addJoin(join);
            System.out.println(obj);
            // DataObject object = p.get(query);
            // System.out.println(object);
            // System.out.println(object);
            // Iterator<Row> i = object.getRows("UserDetails");
            // i.forEachRemaining((Row elem) -> {
            //     JSONObject jsonObject = new JSONObject();
            //     jsonObject.put("userId", elem.get("USER_ID"));
            //     jsonObject.put("userName", elem.get("USER_NAME"));
            //     jsonObject.put("fullName", elem.get("FIRST_NAME"));
            //     jsonObject.put("lastName", elem.get("LAST_NAME"));
            //     jsonObject.put("emailId", elem.get("EMAIL_ID"));
            //     jsonObject.put("userPassword", elem.get("USER_PASSWORD"));
            //     array.add(jsonObject);
            // });

            
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
}
