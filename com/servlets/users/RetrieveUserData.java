package com.servlets.users;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import com.databases.Learning;
import com.databases.UserOperation;

public class RetrieveUserData extends HttpServlet{
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserOperation ud = new UserOperation();
        response.getWriter().println("hi");
        // Learning l = new Learning();
        // l.learn();
        response.getWriter().println(ud.getUsersData());
    }
}
