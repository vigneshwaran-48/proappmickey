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
public class UpdateProject extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONParser parser = new JSONParser();
        JSONObject result = new JSONObject();
        try {
            JSONObject object = (JSONObject) parser.parse(request.getParameter("projectData"));
            ProjectOperation ops = new ProjectOperation();

            if(ops.updateProject(object)){
                result.put("result", "success");
            }
            else {
                result.put("result", "failed");
            }
        } 
        catch (ParseException e) {
            e.printStackTrace();
            result.put("result", "failed");
        }
        response.getWriter().println(result);
    }
}
