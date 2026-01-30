package Reto2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatosBDD {


	public static void LeerDatosBDD() {
		ConnexionBDD db = new ConnexionBDD();

		try {
			ConnexionBDD.rs = db.getResultSet("select * from cliente");

			while (ConnexionBDD.rs.next()) {
				System.out.println("DNI: " + ConnexionBDD.rs.getObject("dni") + ", Nombre: " + ConnexionBDD.rs.getObject("nombre")
						+ ", Apellidos: " + ConnexionBDD.rs.getObject("apellidos") + ", Email: " + ConnexionBDD.rs.getObject("email")
						+ ", Contraseña" + ConnexionBDD.rs.getObject("contraseña"));
			}

			ConnexionBDD.rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.cerrarConexion();

	}

	public static void main(String[] args) {
		LeerDatosBDD();
	}
}
