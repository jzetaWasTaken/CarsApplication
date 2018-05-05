/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.logic;

import carsapplication.model.dao.DBType;

/**
 *
 * @author jon
 */
public class ManagerFactory {
    public static ManagerInterface newManager(DBType type) {
        return new Manager(type);
    }
}
