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
import java.io.InputStream;
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
 *
 * @author jon
 */
public class DAOOracle implements DAOInterface {

    private String host;
    private String dbName;
    private String dbUser;
    private String dbPassword;
    private String dbPort;
    
    private static final String CAR_SQL = 
            "SELECT CAR_ID, AGE, BRAND, MODEL, PLATE_NUMBER, COLOR,"
            + "DEREF(C.CAR_OWNER).OWNER_CODE OWNER_CODE,"
            + "DEREF(C.CAR_OWNER).NAME OWNER_NAME,"
            + "DEREF(C.CAR_OWNER).SURNAME OWNER_SURNAME,"
            + "DEREF(C.CAR_OWNER).BIRTH_DATE OWNER_BIRTH_DATE "
            + "FROM CARS C";
    private static final String OWNER_SQL =
            "SELECT OWNER_CODE, NAME, SURNAME, BIRTH_DATE "
            + "FROM OWNERS";
    private static final String CAR_INSERT = 
            "INSERT INTO CARS SELECT "
            + "CAR(CAR_ID_SEQ.NEXTVAL,?,?,?,?,?,REF(O)) "
            + "FROM OWNERS O WHERE O.OWNER_CODE = ?";
    private static final String OWNER_INSERT =
            "INSERT INTO OWNERS VALUES(CODE_SEQ.NEXTVAL,?,?,?)";
    private static final String CAR_UPDATE = 
            "UPDATE CARS SET PLATE_NUMBER = ?, COLOR = ?, AGE = ?,"
            + "CAR_OWNER = (SELECT REF(O) FROM OWNERS WHERE O.OWNER_CODE = ?)";
    private static final String CAR_DELETE = "DELETE CARS WHERE CAR_ID = ?";

    public DAOOracle() throws CarDBException {
        try (FileInputStream input = new FileInputStream("db.properties")) {
            Properties config = new Properties();
            config.load(input);
            host = config.getProperty("ip");
            System.out.println("HOST: " + host);
            dbName = config.getProperty("dbname");
            System.out.println("DBNAME: " + dbName);
            dbPort = config.getProperty("port");
            System.out.println("PORT: " + dbPort);
            dbUser = config.getProperty("user");
            System.out.println("USER: " + dbUser);
            dbPassword = config.getProperty("password");
            System.out.println("PASSW: "+dbPassword);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CarDBException(e.getMessage());
        }
    }
    
    private Connection getConnection() throws CarDBException {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = String.format("jdbc:oracle:thin:@%s:%s:%s", host, dbPort, dbName);
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new CarDBException(e.getMessage());
        }
        return connection;
    }

    @Override
    public List<Car> findCars() throws CarDBException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(CAR_SQL);
            ResultSet set = pstmt.executeQuery();
            mapCar(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }

    @Override
    public List<Car> findCarsByBrand(String brand) throws CarDBException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = CAR_SQL + " WHERE C.BRAND LIKE '%' || ? || '%'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, brand.toUpperCase());
            ResultSet set = pstmt.executeQuery();
            mapCar(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }

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
            mapCar(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }

    @Override
    public List<Car> findCarsByColor(String color) throws CarDBException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = CAR_SQL + " WHERE C.COLOR LIKE '%' || ? || '%'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, color.toUpperCase());
            ResultSet set = pstmt.executeQuery();
            mapCar(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }

    @Override
    public List<Car> findCarsByModel(String model) throws CarDBException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = CAR_SQL + " WHERE C.MODEL LIKE '%' || ? || '%'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, model.toUpperCase());
            ResultSet set = pstmt.executeQuery();
            mapCar(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }

    @Override
    public List<Car> findCarsByPlate(String plateNumber) throws CarDBException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String sql = CAR_SQL + " WHERE C.PLATE_NUMBER LIKE '%' || ? || '%'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, plateNumber.toUpperCase());
            ResultSet set = pstmt.executeQuery();
            mapCar(set, cars);
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
        return cars;
    }

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

    @Override
    public void createOwner(Owner owner) throws CarDBException {
        try (Connection connection = getConnection()) {
            String sql =
                    "INSERT INTO OWNERS VALUES(CODE_SEQ.NEXTVAL,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(OWNER_INSERT);
            pstmt.setString(1, owner.getName().toUpperCase());
            pstmt.setString(2, owner.getSurname().toUpperCase());
            pstmt.setDate(3, new Date(owner.getDateOfBirth().getTime()));
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
    }

    @Override
    public void updateCar(Car car) throws CarDBException {
        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(CAR_UPDATE);
            pstmt.setString(1, car.getPlateNumber());
            pstmt.setString(2, car.getColor());
            pstmt.setInt(3, car.getAge());
            pstmt.setObject(4, car.getOwner().getOwnerCode());
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
    }
    
    private void mapCar(ResultSet set, List<Car> cars) throws SQLException {
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

    @Override
    public void deleteCar(Car car) throws CarDBException {
        try (Connection connection = getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(CAR_DELETE);
            pstmt.setObject(1, car.getCarId());
        } catch (SQLException e) {
            throw new CarDBException(e);
        } 
    }
    
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
}
