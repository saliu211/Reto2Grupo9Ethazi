package Reto2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionBDD {

    private static Connection conexion;
    private static Statement st;

    // Constructor estático para iniciar la conexión al cargar la clase
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(
                "jdbc:mysql://localhost:33060/Reto2Grupo9",
                "root",
                "elorrieta"
            );
            System.out.println("Conexión Correcta.");
            st = conexion.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error de Conexión");
        }
    }

    // Devuelve el Statement
    public static Statement getStatement() {
        return st;
    }

    // Ejecuta la consulta y devuelve ResultSet
    public static ResultSet getResultSet(String sql) {
        try {
            return st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Cerrar la conexión y Statement
    public static void cerrarConexion() {
        try {
            if (st != null) st.close();
            if (conexion != null) conexion.close();
            System.out.println("Conexión cerrada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main para probar
    public static void main(String[] args) {
        // Solo para probar
        ResultSet rs = ConnexionBDD.getResultSet("SELECT * FROM cliente");
        try {
            while (rs != null && rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnexionBDD.cerrarConexion();
        }
    }
}