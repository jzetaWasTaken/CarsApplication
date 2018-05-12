/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.hibernate;

import carsapplication.model.Car;
import carsapplication.model.Owner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author jon
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration config = new Configuration();
            config.addAnnotatedClass(Owner.class);
            config.addAnnotatedClass(Car.class);
            config.configure("carsapplication/hibernate/hibernate.cfg.xml");
            
            ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
            
            return config.buildSessionFactory(registry); 
        } catch (Throwable e) {
            System.err.println("Hibernate Session Factory creation failed");
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
}
}
