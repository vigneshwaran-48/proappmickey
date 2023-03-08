package com.servlets.tasks;

import java.io.IOError;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.persistence.QueryConstructor;
import com.databases.TaskOperation;
import com.databases.tableobjects.Project;

@MultipartConfig
public class AddTask extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskOperation td = new TaskOperation();
        JSONParser parser = new JSONParser();

        JSONObject object = null;
        try {
            object = td.addTask((JSONObject) parser.parse(request.getParameter("taskData")));
        } 
        catch (ParseException e) {
            e.printStackTrace();
        }
        if(!object.isEmpty()){
            response.getWriter().println(object);
        }
        else {
            response.getWriter().println("Oops, Something went wrong");
        }
    }
}
