package Reto2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatosBDD {

	public static void LeerDatosBDD() {
		ConnexionBDD db = new ConnexionBDD();

		try {
			ResultSet rs = db.getResultSet("SELECT * FROM cliente");

			while (rs.next()) {
				System.out.println("DNI: " + rs.getObject("dni") + ", Nombre: " + rs.getObject("nombre")
						+ ", Apellidos: " + rs.getObject("apellidos") + ", Email: " + rs.getObject("email")
						+ ", Contraseña" + rs.getObject("contraseña"));
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.cerrarConexion();

	}

	public static void main(String[] args) {
		LeerDatosBDD();
	}
}
