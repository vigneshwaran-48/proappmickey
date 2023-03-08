package com.servlets.tasks;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.databases.TaskOperation;

@MultipartConfig
public class UpdateTask extends HttpServlet {
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskOperation ops = new TaskOperation();
        JSONParser parser = new JSONParser();
        JSONObject resultObject = new JSONObject();

        try{
            if(ops.updateTask((JSONObject) parser.parse(request.getParameter("taskData")))){
                resultObject.put("result", "success");
            }
            else {
                resultObject.put("result", "failed");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            resultObject.put("result", "failed");
        }
        response.getWriter().println(resultObject);
    }
}
