/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import Configuration.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Paula
 */
public class ConnectionPool {

    private final String URL = Configuration.LoadConfig("URL");
    private final String USER = Configuration.LoadConfig("USER");
    private final String PASS = Configuration.LoadConfig("PASS"); 

    private static ConnectionPool instance = null;
    private static Connection connection = null;    

    public ConnectionPool() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
            return instance;
        } else {
            return instance;
        }
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }

    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

}
