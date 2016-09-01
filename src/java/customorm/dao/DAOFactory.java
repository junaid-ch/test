/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.dao;

import customorm.controller.ControllerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author junaid.ahmad
 */
public class DAOFactory {

    //returns the required controller object
    public BaseDAO getDAO(String DAOName) {

        if (DAOName == null) {
            return null;
        }
        try {
            return (BaseDAO) Class
                    .forName("customorm.dao." + DAOName)
                    .newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ControllerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
