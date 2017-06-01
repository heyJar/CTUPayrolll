/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author acer
 */
public class dbConnect {
    
    final String  dbURL = "jdbc:mysql://localhost:3306/edtrps?zeroDateTimeBehavior=convertToNull";
    final String dbUser = "root";
    final String dbPass = "";
    
    
    public Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL,dbUser,dbPass);
            return conn;
        } catch(SQLException e) {
            return null;
        }
    }
}
