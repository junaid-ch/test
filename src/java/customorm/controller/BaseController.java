/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import customorm.dao.BaseDAO;
import customorm.model.BaseModel;
import customorm.dao.DAOFactory;

/**
 *
 * @author junaid.ahmad
 */
public abstract class BaseController {

    private final BaseDAO dao;
    private final DAOFactory dAOFactory;
    private final String className;

    public BaseController(String name) {
        className = name;
        dAOFactory = new DAOFactory();
        dao = dAOFactory.getDAO(className + "DAO");
    }

    public int add(BaseModel baseModel) {

        return dao.insert(baseModel);
    }

    public int update(BaseModel baseModel) {

        return dao.update(baseModel);
    }

    public int delete(BaseModel baseModel) {

        return dao.delete(baseModel.getId());
    }

    public BaseModel print(BaseModel baseModel) {

        return dao.select(baseModel.getId());
    }
}
