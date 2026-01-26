package Reto2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnexionBDD {

	private static Connection conexion;
	private static Statement st;

	public ConnexionBDD() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:33060/Reto2Grupo9", "root", "elorrieta");
			st = conexion.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error de Conexión");
		}
	}

	public static Statement getStatement() {
		return st;
	}

	public ResultSet getResultSet(String sql) throws SQLException {
		return st.executeQuery(sql);
	}

	public static void cerrarConexion() {
		try {
			if (st != null)
				st.close();
			if (conexion != null)
				conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}