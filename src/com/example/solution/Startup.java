/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution;

import com.example.solution.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Nikolay
 */
public class Startup extends Application {

    private ViewController ctrl;
    
    @Override
    public void start(Stage stage) throws Exception {  
        final Model data = new Model();
        ctrl = new ViewController(data);
        ctrl.setStage(stage);
        ctrl.showRootLayout();
        ctrl.init();
    }


    @Override
    public void stop() throws Exception {
        ctrl.close();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
