package com.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.json.simple.JSONObject;
import com.databases.TaskOperation;

public class DeleteTask extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskOperation pd = new TaskOperation();
        JSONObject result = new JSONObject();
    
        if(pd.deleteTask(Integer.parseInt(request.getParameter("taskId")))){
            result.put("status", "success");
        }
        else {
            result.put("status", "failed");
        }
        response.getWriter().println(result);
    }
}
