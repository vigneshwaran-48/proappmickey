package com.databases;

import java.util.Iterator;
import org.json.simple.*;
import com.adventnet.ds.query.*;
import com.adventnet.ds.query.InsertQueryImpl.Condition;
import com.adventnet.persistence.*;
import com.databases.tableobjects.Project;
import com.databases.tableobjects.ProjectRelation;

public class ProjectOperation {
    
    public JSONArray getProjectsData(){
        JSONArray array = new JSONArray();

        try {
            SelectQuery query = new SelectQueryImpl(Table.getTable("Projects"));
            query.addSelectColumn(new Column(Project.TABLE, "*"));
        
            DataObject object = DataAccess.get(query);
            Iterator<Row> iterator = object.getRows(Project.TABLE);
            iterator.forEachRemaining(elem -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("projectId", elem.get(Project.PROJECTID));
                jsonObject.put("projectName", elem.get(Project.PROJECTNAME));
                jsonObject.put("status", elem.get(Project.STATUS));
                jsonObject.put("fromDate", elem.get(Project.FROM));
                jsonObject.put("toDate", elem.get(Project.TO));
                jsonObject.put("description", elem.get(Project.DESCRIPTION));
                jsonObject.put("createdBy", elem.get(Project.CREATEDBY));
                array.add(jsonObject);
            });
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    public JSONObject addProject(JSONObject projectDetails){

        JSONObject object = new JSONObject();
        try{
            Row row = new Row(Project.TABLE);
            row.set(Project.PROJECTNAME, String.valueOf(projectDetails.get("projectName")));
            row.set(Project.DESCRIPTION, String.valueOf(projectDetails.get("projectDesc")));
            row.set(Project.FROM, String.valueOf(projectDetails.get("fromDate")));
            row.set(Project.TO, String.valueOf(projectDetails.get("toDate")));
            row.set(Project.CREATEDBY, Integer.parseInt(String.valueOf(projectDetails.get("createdBy"))));
            row.set(Project.STATUS, "Yet To Start");

            DataObject dataObject = new WritableDataObject();
            dataObject.addRow(row);
            DataObject result = DataAccess.add(dataObject);
            System.out.println(result);
            Row r = result.getRow(Project.TABLE);
            addProjectRelation(r.getInt(Project.PROJECTID), (JSONArray) projectDetails.get("users"));

            object.put("projectId", r.getInt(Project.PROJECTID));
            object.put("projectName", r.getString(Project.PROJECTNAME));
            object.put("projectDesc", r.getString(Project.DESCRIPTION));
            object.put("fromDate", r.getDate(Project.FROM));
            object.put("toDate", r.getDate(Project.TO));
            object.put("status", r.getString(Project.STATUS));
            object.put("createdBy", r.getInt(Project.CREATEDBY));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return object;
    }

    private void addProjectRelation(int pid, JSONArray userIds){

        InsertQueryImpl insert = new InsertQueryImpl(ProjectRelation.TABLE, userIds.size(), Condition.ON_DUPLICATE_PK_IGNORE);

        userIds.forEach(elem -> {
            Row row = new Row(ProjectRelation.TABLE);
            row.set(ProjectRelation.PROJECTID, pid);
            row.set(ProjectRelation.USERID, elem);
            insert.addRow(row);
        }); 
        System.out.println(insert);
        try {
            DataAccess.add(insert);
        } 
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteProject(int pid){
        try{
            DataObject dataObject = DataAccess.get(Project.TABLE, (Criteria) null);
            Row r = new Row(Project.TABLE);
            r.set(Project.PROJECTID, pid);
            dataObject.deleteRow(r);
            DataAccess.update(dataObject);

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
