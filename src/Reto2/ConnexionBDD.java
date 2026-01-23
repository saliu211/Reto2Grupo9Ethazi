package Reto2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBDD {

    private static final String URL = "jdbc:mysql://localhost:33060/reto2grupo9";
    private static final String USER = "root";
    private static final String PASSWORD = "elorrieta";

    public static void main(String[] args) {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a MySQL");

        } catch (SQLException e) {
            System.out.println("Error en la conexión");
            e.printStackTrace();
        }
    }
}