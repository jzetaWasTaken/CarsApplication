/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model.dao;

import carsapplication.exception.CarDBException;
import carsapplication.model.Car;
import carsapplication.model.Owner;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Data access object to manage Oracle Object Relational database
 * 
 * @author Jon Zaballa
 */
public class DAOOracle implements DAOInterface {

    /**
     * Host URL
     */
    private String host;
    
    /**
     * Database name
     */
    private String dbName;
    
    /**
     * Database user
     */
    private String dbUser;
    
    /**
     * Database user password
     */
    private String dbPassword;
    
    /**
     * Database listening port
     */
    private String dbPort;
    
    /**
     * SQL statement for car queries
     */
    private static final String CAR_SQL = 
            "SELECT CAR_ID, AGE, BRAND, MODEL, PLATE_NUMBER, COLOR,"
            + "DEREF(C.CAR_OWNER).OWNER_CODE OWNER_CODE,"
            + "DEREF(C.CAR_OWNER).NAME OWNER_NAME,"
            + "DEREF(C.CAR_OWNER).SURNAME OWNER_SURNAME,"
            + "DEREF(C.CAR_OWNER).BIRTH_DATE OWNER_BIRTH_DATE "
            + "FROM CARS C";
    
    /**
     * SQL statement for owner queries
     */
    private static final String OWNER_SQL =
            "SELECT OWNER_CODE, NAME, SURNAME, BIRTH_DATE "
            + "FROM OWNERS";
    
    /**
     * SQL statement for car creation
     */
    private static final String CAR_INSERT = 
            "INSERT INTO CARS SELECT "
            + "CAR(CAR_ID_SEQ.NEXTVAL,?,?,?,?,?,REF(O)) "
            + "FROM OWNERS O WHERE O.OWNER_CODE = ?";
    
    /**
     * SQL statement for owner creation
     */
    private static final String OWNER_INSERT =
            "INSERT INTO OWNERS VALUES(CODE_SEQ.NEXTVAL,?,?,?)";
    
    /**
     * SQL statement for car update
     */
    private static final String CAR_UPDATE = 
            "UPDATE CARS SET PLATE_NUMBER = ?, COLOR = ?, AGE = ?,"
            + "CAR_OWNER = (SELECT REF(O) FROM OWNERS O WHERE O.OWNER_CODE = ?) "
            + "WHERE CAR_ID = ?";
    
    /**
     * SQL statement for car deletion
     */
    private static final String CAR_DELETE = "DELETE CARS WHERE CAR_ID = ?";

    /**
     * Constructs a Oracle data access object. It initializes the host, the
     * port, the database name, the user and the user password
     * 
     * @throws CarDBException 
     */
    public DAOOracle() throws CarDBException {
        try (FileInputStream input = new FileInputStream("db.properties")) {
            Properties config = new Properties();
            config.load(input);
            host = config.getProperty("ip");
            dbName = config.getProperty("dbname");
            dbPort = config.getProperty("port");
            dbUser = config.getProperty("user");
            dbPassword = config.getProperty("password");
        } catch (IOException e) {
            throw new CarDBException(e.getMessage());
        }
    }
    
    /**
     * Gets a database connection
     * 
     * @return connection
     * @throws CarDBException 
     */
    private Connection getConnection() throws CarDBException {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = String.format("jdbc:oracle:thin:@%s:%s:%s", host, dbPort, dbName);
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            throw new CarDBException(e.getMessage());
        }
        return connection;
    }

    /**
     * Gets all cars
     * 
     * @return cars
     * @throws CarDBException 
     */
    @Override
    public List<Car> findCars() throws CarDBException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(CAR_SQL);
            ResultSet set = pstmt.executeQuery();
            mapCars(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }

    /**
     * Gets cars from a given brand
     * 
     * @param brand
     * @return cars
     * @throws CarDBException 
     */
    @Override
    public List<Car> findCarsByBrand(String brand) throws CarDBException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = CAR_SQL + " WHERE C.BRAND LIKE '%' || ? || '%'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, brand.toUpperCase());
            ResultSet set = pstmt.executeQuery();
            mapCars(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }
    
    /**
     * Gets cars from a given owner
     * 
     * @param ownerName
     * @return cars
     * @throws CarDBException 
     */
    @Override
    public List<Car> findCarsByOwnerName(String ownerName) throws CarDBException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = CAR_SQL + " WHERE C.CAR_OWNER.NAME LIKE '%' || ? || '%' "
                    + "OR C.CAR_OWNER.SURNAME LIKE '%' || ? || '%'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, ownerName.toUpperCase());
            pstmt.setString(2, ownerName.toUpperCase());
            ResultSet set = pstmt.executeQuery();
            mapCars(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }

    /**
     * Gets cars from a given color
     * 
     * @param color
     * @return cars
     * @throws CarDBException 
     */
    @Override
    public List<Car> findCarsByColor(String color) throws CarDBException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = CAR_SQL + " WHERE C.COLOR LIKE '%' || ? || '%'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, color.toUpperCase());
            ResultSet set = pstmt.executeQuery();
            mapCars(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }

    /**
     * Gets cars from a given model
     * 
     * @param model
     * @return cars
     * @throws CarDBException 
     */
    @Override
    public List<Car> findCarsByModel(String model) throws CarDBException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = CAR_SQL + " WHERE C.MODEL LIKE '%' || ? || '%'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, model.toUpperCase());
            ResultSet set = pstmt.executeQuery();
            mapCars(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }

    /**
     * Gets cars given a plate number
     * 
     * @param plateNumber
     * @return cars
     * @throws CarDBException 
     */
    @Override
    public List<Car> findCarsByPlate(String plateNumber) throws CarDBException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = CAR_SQL + " WHERE C.PLATE_NUMBER LIKE '%' || ? || '%'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, plateNumber.toUpperCase());
            ResultSet set = pstmt.executeQuery();
            mapCars(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }

    /**
     * Gets all owners
     * 
     * @return cars
     * @throws CarDBException 
     */
    @Override
    public List<Owner> findOwners() throws CarDBException {
        List<Owner> owners = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(OWNER_SQL);
            ResultSet set = pstmt.executeQuery();
            mapOwner(set, owners);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return owners;
    }

    /**
     * Creates a new car
     * 
     * @param car
     * @throws CarDBException 
     */
    @Override
    public void createCar(Car car) throws CarDBException {
        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(CAR_INSERT);
            pstmt.setString(1, car.getPlateNumber().toUpperCase());
            pstmt.setString(2, car.getBrand().toUpperCase());
            pstmt.setString(3, car.getModel().toUpperCase());
            pstmt.setString(4, car.getColor().toUpperCase());
            pstmt.setInt(5, car.getAge());
            pstmt.setObject(6, car.getOwner().getOwnerCode());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
    }

    /**
     * Creates a new owner
     * 
     * @param owner
     * @throws CarDBException 
     */
    @Override
    public void createOwner(Owner owner) throws CarDBException {
        try (Connection connection = getConnection()) {
            String sql =
                    "INSERT INTO OWNERS VALUES(CODE_SEQ.NEXTVAL,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(OWNER_INSERT);
            pstmt.setString(1, owner.getName().toUpperCase());
            pstmt.setString(2, owner.getSurname().toUpperCase());
            pstmt.setDate(3, new Date(owner.getDateOfBirth().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
    }

    /**
     * Modifies a car
     * 
     * @param car
     * @throws CarDBException 
     */
    @Override
    public void updateCar(Car car) throws CarDBException {
        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(CAR_UPDATE);
            pstmt.setString(1, car.getPlateNumber());
            pstmt.setString(2, car.getColor());
            pstmt.setInt(3, car.getAge());
            pstmt.setObject(4, car.getOwner().getOwnerCode());
            pstmt.setInt(5, car.getCarId().intValue());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
    }
    
    /**
     * Deletes a car
     * 
     * @param car
     * @throws CarDBException 
     */
    @Override
    public void deleteCar(Car car) throws CarDBException {
        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(CAR_DELETE);
            pstmt.setObject(1, car.getCarId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
    }
    
    private void mapCars(ResultSet set, List<Car> cars) throws SQLException {
        while (set.next()) {
            Owner owner = new Owner();
            owner.setOwnerCode(((BigDecimal)set.getObject("owner_code")).toBigInteger());
            owner.setName(set.getString("owner_name"));
            owner.setSurname(set.getString("owner_surname"));
            owner.setDateOfBirth(set.getDate("owner_birth_date"));
            Car car = new Car();
            car.setCarId(new BigInteger(String.valueOf(set.getInt("car_id"))));
            car.setAge(set.getInt("age"));
            car.setBrand(set.getString("brand"));
            car.setModel(set.getString("model"));
            car.setPlateNumber(set.getString("plate_number"));
            car.setColor(set.getString("color"));
            car.setOwner(owner);
            cars.add(car);
        }
    }
    
    private void mapOwner(ResultSet set, List<Owner> owners) throws SQLException {
        while (set.next()) {
            Owner owner = new Owner();
            owner.setOwnerCode(((BigDecimal)set.getObject("owner_code")).toBigInteger());
            owner.setName(set.getString("name"));
            owner.setSurname(set.getString("surname"));
            owner.setDateOfBirth(set.getDate("birth_date"));
            owners.add(owner);
        }
    }
    
    @Deprecated
    private Car mapCar(ResultSet set)  throws SQLException {
        Car car = null;
        if (set.next()) {
            Owner owner = new Owner();
            owner.setOwnerCode((BigInteger) set.getObject("owner_code"));
            owner.setName(set.getString("owner_name"));
            owner.setSurname(set.getString("owner_surname"));
            owner.setDateOfBirth(set.getDate("owner_birth_date"));
            car = new Car();
            car.setCarId(new BigInteger(String.valueOf(set.getInt("car_id"))));
            car.setAge(set.getInt("age"));
            car.setBrand(set.getString("brand"));
            car.setModel(set.getString("model"));
            car.setPlateNumber(set.getString("plate_number"));
            car.setOwner(owner);
        }
        return car;
    }
}
