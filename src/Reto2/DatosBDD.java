package Reto2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatosBDD {

    public static void LeerDatosBDD() {
        ResultSet rs = ConnexionBDD.getResultSet("SELECT * FROM usuarios");

        if (rs != null) {
            try {
                while (rs.next()) {
                    System.out.println(
                        "DNI: " + rs.getObject("dni") +
                        ", Nombre: " + rs.getObject("nombre") +
                        ", Apellidos: " + rs.getObject("apellidos") +
                        ", Grupo: " + rs.getObject("grupo")
                    );
                }

                // Cerramos el ResultSet después de usarlo
                rs.close();

                // Cerramos la conexión y statement
                ConnexionBDD.cerrarConexion();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
