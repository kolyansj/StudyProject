/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.model.observer;

/**
 *
 * @author Nikolay
 */
public interface Observer {
    void update(Observable o, Object args);
}
