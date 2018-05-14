/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model.dao;

import carsapplication.exception.CarDBException;
import carsapplication.hibernate.HibernateUtil;
import carsapplication.model.Car;
import carsapplication.model.Owner;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author jon
 */

public class DAOHibernate implements DAOInterface {

    private final SessionFactory factory = HibernateUtil.getSessionFactory();
    private Transaction tx;
    
    @Override
    public List<Car> findCars() throws CarDBException {
        Session session = null;
        List<Car> cars = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            cars = session.getNamedQuery("findCars").list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw new CarDBException(e.getCause());
        } finally {
            if (session != null) session.close();
        }
        return cars;
    }

    @Override
    public List<Car> findCarsByBrand(String brand) throws CarDBException {
        Session session = null;
        List<Car> cars = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            cars = session.getNamedQuery("findCarsByBrand")
                    .setParameter("brand", brand)
                    .list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw new CarDBException(e.getCause());
        } finally {
            if (session != null) session.close();
        }
        return cars;
    }

    @Override
    public List<Car> findCarsByOwnerName(String ownerName) throws CarDBException {
        Session session = null;
        List<Car> cars = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            cars = session.getNamedQuery("findCarsByOwner")
                    .setParameter("owner", ownerName)
                    .list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
            throw new CarDBException(e.getCause());
        } finally {
            if (session != null) session.close();
        }
        return cars;
    }

    @Override
    public List<Car> findCarsByColor(String color) throws CarDBException {
        Session session = null;
        List<Car> cars = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            cars = session.getNamedQuery("findCarsByColor")
                    .setParameter("color", color)
                    .list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw new CarDBException(e.getCause());
        } finally {
            if (session != null) session.close();
        }
        return cars;
    }

    @Override
    public List<Car> findCarsByModel(String model) throws CarDBException {
        Session session = null;
        List<Car> cars = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            cars = session.getNamedQuery("findCarsByModel")
                    .setParameter("model", model)
                    .list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw new CarDBException(e.getCause());
        } finally {
            if (session != null) session.close();
        }
        return cars;
    }

    @Override
    public List<Car> findCarsByPlate(String plateNumber) throws CarDBException {
        Session session = null;
        List<Car> cars = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            cars = session.getNamedQuery("findCarsByPlate")
                    .setParameter("plate", plateNumber)
                    .list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
            throw new CarDBException(e.getCause());
        } finally {
            if (session != null) session.close();
        }
        return cars;
    }

    @Override
    public List<Owner> findOwners() throws CarDBException {
        Session session = null;
        List<Owner> owners = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            owners = session.getNamedQuery("findOwners").list();
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw new CarDBException(e.getCause());
        } finally {
            if (session != null) session.close();
        }
        return owners;
    }

    @Override
    public void createCar(Car car) throws CarDBException {
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.persist(car);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw new CarDBException(e.getCause());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void createOwner(Owner owner) throws CarDBException {
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.persist(owner);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw new CarDBException(e.getCause());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void updateCar(Car car) throws CarDBException {
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.merge(car);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw new CarDBException(e.getCause());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void deleteCar(Car car) throws CarDBException {
        Session session = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(car);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw new CarDBException(e.getCause());
        } finally {
            if (session != null) session.close();
        }
    }
}
