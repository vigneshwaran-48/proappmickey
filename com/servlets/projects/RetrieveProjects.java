package com.servlets.projects;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.GroupByClause;
import com.adventnet.ds.query.GroupByColumn;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstructionException;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.databases.ProjectOperation;
import com.databases.tableobjects.Project;
import com.databases.tableobjects.User;

public class RetrieveProjects extends HttpServlet{
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SelectQuery query = new SelectQueryImpl(Table.getTable(User.TABLE));
        query.addSelectColumn(new Column(User.TABLE, User.USERID));
        query.addSelectColumn(new Column(Project.TABLE, "*").count());

        GroupByColumn grp = new GroupByColumn(Column.getColumn(User.TABLE, User.USERID), false);
        List<GroupByColumn> list = new ArrayList<>();
        list.add(grp);
        query.setGroupByClause(new GroupByClause(list));

        Join join = new Join(User.TABLE, Project.TABLE, new String[]{User.USERID}, new String[]{Project.CREATEDBY}, Join.INNER_JOIN);
        query.addJoin(join);

        RelationalAPI rels = RelationalAPI.getInstance();
        try (Connection con = rels.getConnection();DataSet ds = rels.executeQuery(query, con)){
            System.out.println(ds);
            while(ds.next()){
                System.out.println(ds.getValue(1) + " | " + ds.getValue(2));
            }
            
        } 
        catch(Exception e){
            e.printStackTrace();
        }
        try {
            System.out.println("Query ---> " + rels.getSelectSQL(query));
        } catch (QueryConstructionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ProjectOperation pd = new ProjectOperation();
        response.getWriter().println(pd.getProjectsData());
    }
}
