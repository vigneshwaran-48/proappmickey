package com.servlets.projects;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.databases.ProjectOperation;

@MultipartConfig
public class AddProject extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectOperation pd = new ProjectOperation();
        JSONParser parser = new JSONParser();

        JSONObject object = null;
        try {
            object = pd.addProject((JSONObject) parser.parse(request.getParameter("projectData")));
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
