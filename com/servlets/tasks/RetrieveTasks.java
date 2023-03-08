package com.servlets.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.json.simple.JSONArray;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.QueryConstructor;
import com.databases.TaskOperation;
import com.databases.tableobjects.Project;
import com.databases.tableobjects.User;

public class RetrieveTasks extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskOperation pd = new TaskOperation();
        JSONArray array = pd.getTasks();

        // SelectQuery query = QueryConstructor.get(Project.TABLE,
        // new Criteria(new Column(Project.TABLE, Project.CREATEDBY), 1502, QueryConstants.EQUAL), Project.PROJECTID, Project.PROJECTNAME);
        
        List<String> tablesList = new ArrayList<>();
        tablesList.add(Project.TABLE);
        tablesList.add(User.TABLE);
        
        try {
            Criteria c = new Criteria(new Column(User.TABLE, User.USERID), new Column(Project.TABLE, Project.PROJECTID), QueryConstants.EQUAL);
            SelectQuery query = QueryConstructor.get(tablesList, new boolean[]{true, false}, c);
            Persistence pers = (Persistence) BeanUtil.lookup("Persistence");
            System.out.println(pers.get(query));
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        Criteria c=new Criteria(new Column("UserDetails","USER_ID"),"1",    QueryConstants.EQUAL);  
        Persistence per;
        try {
            per = (Persistence)BeanUtil.lookup("Persistence");
            SelectQuery query = QueryConstructor.getForPersonality("UserPers",c);  
            DataObject d= per.get(query);
            System.out.println(d);  
        } 
        catch (Exception e) {
            e.printStackTrace();
        }  
        if(!array.isEmpty()){
            response.getWriter().println(array);
        }
        else {
            response.getWriter().println("No tasks available");
        } 
    }
}
