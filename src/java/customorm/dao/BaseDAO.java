/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.dao;


import customorm.DBDriver;
import customorm.QueryBuilder;
import customorm.model.BaseModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author junaid.ahmad
 */
public abstract class BaseDAO {

    private final DBDriver dBDriver;
    private final String tableName;
    private final String modelName;

    public BaseDAO(String className) {
        modelName = className;
        tableName = className.toLowerCase();
        dBDriver = DBDriver.getInstance();
    }

    public int insert(BaseModel model) {

        QueryBuilder queryBuilder = new QueryBuilder();
        List<String> fieldNames = new ArrayList<>();
        int rowsAffected = 0;

        for (Class<?> c = model.getClass(); c != null; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            for (Field classField : fields) {
                if (classField.getName().equalsIgnoreCase("id") 
                        || fieldNames.contains(classField.getName())) {
                    continue;
                }
                fieldNames.add(classField.getName());
            }
        }
        queryBuilder.insert(tableName, fieldNames.toArray(new String[0]));
        String q = queryBuilder.prepare();
        rowsAffected = dBDriver.executeInsert(q, model);

        return rowsAffected;
    }

    public int delete(int id) {
        QueryBuilder queryBuilder = new QueryBuilder();
        int rowsAffected = 0;

        queryBuilder.delete().from(tableName).where("id = ");
        String query = queryBuilder.prepare();
        rowsAffected = dBDriver.executeDelete(query, id);

        return rowsAffected;
    }

    public int update(BaseModel model) {

        QueryBuilder queryBuilder = new QueryBuilder();
        List<String> fieldNames = new ArrayList<>();
        int rowsAffected = 0;

        for (Class<?> c = model.getClass(); c != null; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            for (Field classField : fields) {
                if (classField.getName().equalsIgnoreCase("id")) {
                    continue;
                }
                fieldNames.add(classField.getName());
            }
        }

        queryBuilder.update(tableName)
                .set(fieldNames.toArray(new String[0]))
                .where("id = ");
        String query = queryBuilder.prepare();
        rowsAffected = dBDriver.executeUpdate(query, model);
        return rowsAffected;
    }

    public BaseModel select(int id) {

        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.select().from(tableName).where("id = ");
        String query = queryBuilder.prepare();
        BaseModel model = dBDriver.executeSelect(query, modelName, id);
        return model;
    }
}
