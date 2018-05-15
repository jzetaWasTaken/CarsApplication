/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model.dao;

import carsapplication.exception.CarDBException;

/**
 * Factory class with a convenient method to create the data access object
 * instance depending on the selected database
 * 
 * @author Jon Zaballa
 */
public class DAOFactory {
    
    /**
     * Gets the data access object implementation instance depending on the
     * database type input
     * 
     * @param type
     * @return data access object
     * @throws CarDBException 
     */
    public static DAOInterface newDAO(DBType type) throws CarDBException {
        switch (type) {
            case MONGO_DB:
                return new DAOMongo();
            case ORACLE:
                return new DAOOracle();
            case HIBERNATE:
                return new DAOHibernate();
            default:
                return null;
        }
    }
}
