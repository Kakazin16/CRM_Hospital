package br.com.hospitalcrm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionDB {

    private static ConnectionDB instance;

    private final Dotenv dotenv = Dotenv.load();

    private final String url = dotenv.get("DB_URL");
    private final String user = dotenv.get("DB_USER");
    private final String pass = dotenv.get("DB_PASS");

    private ConnectionDB() {}

    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar", e);
        }
    }
}