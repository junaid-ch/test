/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public class BaseModel {

    private int id;
    private String name;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setField(BaseModel object, Field field, Object fieldValue) {
        Class<?> clazz = object.getClass();
        // MZ: Find the correct method
        for (Method method : clazz.getMethods()) {
            if ((method.getName().startsWith("set")) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    // MZ: Method found, run it
                    try {
                        method.invoke(object, fieldValue);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
    }

    public <V> V getField(BaseModel object, Field field) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {

                for (Method method : clazz.getMethods()) {
                    if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
                        if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                            // MZ: Method found, run it
                            try {
                                return (V) method.invoke(object);
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                Logger.getLogger(BaseModel.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                }

            } catch (SecurityException | IllegalArgumentException e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }
}
