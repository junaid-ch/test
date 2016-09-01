/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.model;

import customorm.controller.ControllerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public class ModelFactory {

    //returns the required model object
    public BaseModel getModel(String modelName) {

        if (modelName == null) {
            return null;
        }
        try {
            return (BaseModel) Class
                    .forName("customorm.model." + modelName)
                    .newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ControllerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
