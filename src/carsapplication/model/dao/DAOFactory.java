/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model.dao;

import carsapplication.exception.CarDBException;

/**
 *
 * @author jon
 */
public class DAOFactory {
    
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
