package com.databases;

import java.util.Iterator;
import org.json.simple.*;
import com.adventnet.ds.query.*;
import com.adventnet.ds.query.InsertQueryImpl.Condition;
import com.adventnet.persistence.*;
import com.databases.tableobjects.*;

public class TaskOperation {
    
    public JSONObject addTask(JSONObject taskDetails){
        JSONObject addedTask = new JSONObject();
        Row row = new Row(Task.TABLE);
        row.set(Task.TASKNAME, String.valueOf(taskDetails.get("taskName")));
        row.set(Task.DESCRIPTION, String.valueOf(taskDetails.get("taskDesc")));
        row.set(Task.CREATEDBY, Integer.parseInt(String.valueOf(taskDetails.get("createdBy"))));
        row.set(Task.FROM, String.valueOf(taskDetails.get("fromDate")));
        row.set(Task.TO, String.valueOf(taskDetails.get("toDate")));
        row.set(Task.STATUS, "Yet To Start");
        row.set(Task.PROJECTID, Integer.parseInt(String.valueOf(taskDetails.get("projectId"))));

        DataObject dataObject = new WritableDataObject();

        try {
            dataObject.addRow(row);
            DataObject result = DataAccess.add(dataObject);
            Row addedRow = result.getRow(Task.TABLE);
            addTaskUsers(addedRow.getInt(Task.TASKID), (JSONArray) taskDetails.get("users"));

            addedTask.put("taskId", addedRow.getInt(Task.TASKID));
            addedTask.put("taskName", addedRow.getString(Task.TASKNAME));
            addedTask.put("taskDesc", addedRow.getString(Task.DESCRIPTION));
            addedTask.put("fromDate", addedRow.getDate(Task.FROM));
            addedTask.put("toDate", addedRow.getDate(Task.TO));
            addedTask.put("status", addedRow.getString(Task.STATUS));
            addedTask.put("createdBy", addedRow.getInt(Task.CREATEDBY));
            addedTask.put("projectId", addedRow.getInt(Task.PROJECTID));
        } 
        catch (DataAccessException e) {
            e.printStackTrace();
        }
        return addedTask;
    }

    private void addTaskUsers(int taskId, JSONArray users){
        InsertQueryImpl insert = new InsertQueryImpl(TaskRelation.TABLE, users.size(), Condition.ON_DUPLICATE_PK_IGNORE);

        users.forEach(elem -> {
            Row row = new Row(TaskRelation.TABLE);
            row.set(TaskRelation.TASKID, taskId);
            row.set(TaskRelation.USERID, elem);
            insert.addRow(row);
        }); 
        try {
            DataAccess.add(insert);
        } 
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteTask(int taskId){
        try{
            DataObject dataObject = DataAccess.get(Task.TABLE, (Criteria) null);
            Row r = new Row(Task.TABLE);
            r.set(Task.TASKID, taskId);
            dataObject.deleteRow(r);
            DataAccess.update(dataObject);

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public JSONArray getTasks(){
        JSONArray array = new JSONArray();

        try {
            SelectQuery query = new SelectQueryImpl(Table.getTable(Task.TABLE));
            query.addSelectColumn(new Column(Task.TABLE, "*"));
        
            DataObject object = DataAccess.get(query);
            Iterator<Row> iterator = object.getRows(Task.TABLE);
            iterator.forEachRemaining(elem -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("taskId", elem.get(Task.TASKID));
                jsonObject.put("taskName", elem.get(Task.TASKNAME));
                jsonObject.put("status", elem.get(Task.STATUS));
                jsonObject.put("fromDate", elem.get(Task.FROM));
                jsonObject.put("toDate", elem.get(Task.TO));
                jsonObject.put("description", elem.get(Task.DESCRIPTION));
                jsonObject.put("createdBy", elem.get(Task.CREATEDBY));
                jsonObject.put("projectId", elem.get(Task.PROJECTID));
                array.add(jsonObject);
            });
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
}
