/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.hibernate;

import carsapplication.model.Car;
import carsapplication.model.Owner;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author jon
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration config = new Configuration();
            config.addAnnotatedClass(Owner.class);
            config.addAnnotatedClass(Car.class);
            
            return config
                    .configure("carsapplication/hibernate/hibernate.cfg.xml")
                    .buildSessionFactory(new StandardServiceRegistryBuilder()
                            .build());
        } catch (Throwable e) {
            System.err.println("Hibernate Session Factory creation failed");
            throw new ExceptionInInitializerError(e);
        }
    }
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
