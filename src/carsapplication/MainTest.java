/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication;

import carsapplication.exception.CarDBException;
import carsapplication.model.Car;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.math.BigInteger;
import org.bson.Document;

/**
 *
 * @author jon
 */
public class MainTest {
    
    private static MongoClient getClient() throws CarDBException {
        try {
            return new MongoClient("localhost",27017);
        } catch (Exception e) {
            throw new CarDBException(e.getCause());
        }
    } 
    
    public static void main(String[] args) {
        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            FindIterable<Document> carsDoc = database.getCollection("cars").find();
            MongoCursor<Document> cursor = carsDoc.iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String hex = doc.get("_id").toString();
                System.out.println("hex:  " + hex);
                BigInteger code = new BigInteger(hex, 16);
                String hex2 = code.toString(16);
                System.out.println("hex2: " + hex2);
//                System.out.println(code);
//                System.out.println(doc.get("_id"));
//                System.out.println(doc.getString("brand"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.exit(0);
    }
}
