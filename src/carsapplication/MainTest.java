/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication;

import carsapplication.exception.CarDBException;
import carsapplication.model.Car;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.math.BigInteger;
import org.bson.BSONObject;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

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
//        try (MongoClient client = getClient()) {
//            MongoDatabase database = client.getDatabase("cars_db");
//            FindIterable<Document> carsDoc = database.getCollection("cars").find();
//            MongoCursor<Document> cursor = carsDoc.iterator();
//            while (cursor.hasNext()) {
//                Document doc = cursor.next();
//                String hex = doc.get("_id").toString();
//                System.out.println("hex:  " + hex);
//                BigInteger code = new BigInteger(hex, 16);
//                String hex2 = code.toString(16);
//                System.out.println("hex2: " + hex2);
////                System.out.println(code);
////                System.out.println(doc.get("_id"));
////                System.out.println(doc.getString("brand"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try (MongoClient client = getClient()) {
            MongoDatabase database = client.getDatabase("cars_db");
            BasicDBObject query = new BasicDBObject("_id", new ObjectId("5af81f88cc11601033accc0c"));
//            BasicDBObject query = new BasicDBObject();
//            query.putAll((BSONObject) obj);
            //FindIterable<Document> owners = database.getCollection("owners").find(eq("_id", new ObjectId("5af81f88cc11601033accc0c")));
            FindIterable<Document> owners = database.getCollection("owners").find(query);
            MongoCursor<Document> cursor2 = owners.iterator();
            while (cursor2.hasNext()) {
                System.out.println("FOUND");
                cursor2.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
