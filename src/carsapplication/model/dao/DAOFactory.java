/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model.dao;

/**
 *
 * @author jon
 */
public class DAOFactory {
    public static DAOInterface newDAO(DBType type) {
        switch (type) {
            case HIBERNATE:
                return new DAOHibernate();
            case MONGO_DB:
                return new DAOMongo();
            case ORACLE:
                return new DAOOracle();
            default:
                return null;
        }
    }
}
