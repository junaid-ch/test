/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public class ControllerFactory {

    //returns the required controller object
    public BaseController getController(String controllerName) {

        if (controllerName == null) {
            return null;
        }

        try {
            return (BaseController) Class
                    .forName("customorm.controller." + controllerName)
                    .newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ControllerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
