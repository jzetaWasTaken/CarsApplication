/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.logic;

import carsapplication.model.dao.DBType;
import static carsapplication.model.dao.DBType.*;

/**
 *
 * @author jon
 */
public class ManagerFactory {
    public static ManagerInterface newManager(DBType type) {
        switch (type) {
            case HIBERNATE:
                return new ManagerHibernate();
            default:
                return new Manager();
        }
    }
}
