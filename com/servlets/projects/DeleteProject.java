package com.servlets.projects;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.json.simple.JSONObject;
import com.databases.ProjectOperation;

public class DeleteProject extends HttpServlet{
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProjectOperation pd = new ProjectOperation();
        JSONObject result = new JSONObject();
    
        if(pd.deleteProject(Integer.parseInt(request.getParameter("projectId")))){
            result.put("status", "success");
        }
        else {
            result.put("status", "failed");
        }
        response.getWriter().println(result);
    }
}
