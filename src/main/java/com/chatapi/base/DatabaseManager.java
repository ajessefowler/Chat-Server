package com.chatapi.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    /*public static Connection getConnection() throws SQLException {
        Connection conn = null;

        try (FileInputStream f = new FileInputStream("com/chatapi/base/db.properties")) {
            Properties props = new Properties();
            props.load(f);

            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");

            conn = DriverManager.getConnection(url, user, password);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return conn;
    }*/
}
