/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.logic;

import carsapplication.exception.CarDBException;
import carsapplication.model.dao.DBType;

/**
 * Factory class with convenient method to create a manager
 * 
 * @author Jon Zaballa
 */
public class ManagerFactory {
    
    /**
     * Returns a manager implementation 
     *  
     * @param type  The database type (Mongo, Hibernate or Oracle)
     * @return manager
     * @throws CarDBException 
     */
    public static ManagerInterface newManager(DBType type) throws CarDBException {
        return new Manager(type);
    }
}
