package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/gmh_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Nimesh%$#10";

    private static void initializeConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                connection.setAutoCommit(true);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to initialize database connection: " + e.getMessage());
            throw new RuntimeException("Database connection error", e);
        }
    }

    public static ResultSet executeQuery(String query, Object... params) throws SQLException {
        initializeConnection();
        PreparedStatement stmt = prepareStatement(query, params);
        return stmt.executeQuery();
    }

    public static int executeUpdate(String query, Object... params) throws SQLException {
        initializeConnection();
        try (PreparedStatement stmt = prepareStatement(query, params)) {
            return stmt.executeUpdate();
        }
    }

    private static PreparedStatement prepareStatement(String query, Object... params) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        return stmt;
    }

    public static int executeUpdateWithKeys(String query, Object... params) throws SQLException {
        initializeConnection();
        try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("No generated keys returned");
            }
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close database connection: " + e.getMessage());
            }
        }
    }
}
