/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm.controller;

import customorm.Configurations;
import customorm.model.BaseModel;
import customorm.model.ModelFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author junaid.ahmad
 */
@Controller
public class IndexController {

    private final ControllerFactory controllerFactory;
    private BaseController baseController;
    private String controllerName;
    public IndexController() {
        controllerFactory = new ControllerFactory();
    }

    @RequestMapping(value = "/index")
    public ModelAndView index() {

        Configurations.configure();
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/{entity}", method = RequestMethod.GET)
    public ModelAndView returnView(@PathVariable("entity") String entity) {

        controllerName = entity + "Controller";
        return new ModelAndView(entity.toLowerCase(), "baseModel", new ModelFactory().getModel(entity));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(BaseModel model) {

        baseController = controllerFactory
                .getController(controllerName);
        int rowsAffected = baseController.add(model);
        return new ModelAndView("result", "model", rowsAffected);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(BaseModel model) {

        baseController = controllerFactory
                .getController(controllerName);
        int rowsAffected = baseController.update(model);
        return new ModelAndView("result", "model", rowsAffected);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(BaseModel model) {

        baseController = controllerFactory
                .getController(controllerName);
        int rowsAffected = baseController.delete(model);
        return new ModelAndView("result", "model", rowsAffected);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ModelAndView view(BaseModel model) {

        baseController = controllerFactory
                .getController(controllerName);
        BaseModel baseModel = baseController.print(model);
        return new ModelAndView("result", "model", baseModel);
    }

}
