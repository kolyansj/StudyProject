/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.model.provider;

import com.example.solution.model.University;

/**
 *
 * @author Nikolay
 */
public interface DataProvider {
    University loadData();
    void saveData(University sch);
}
