package Reto2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Bienvenida {

	public static void main(String[] args) {

		System.out.println("							-- Bienvenido al Cine Errekamari --");

		System.out.println("Esta es nuestra cartelera: ");
		leerPeliculas();
	}

	public static void leerPeliculas() {
		ConnexionBDD db = new ConnexionBDD();

		try {
			ResultSet rs = db.getResultSet("select * from pelicula");
			while (rs.next()) {
				System.out.println("ID: " + rs.getObject("numpelicula") + " Nombre: " + rs.getObject("titulo")
						+ " Duracion: " + rs.getObject("duracion") + " Genero: " + rs.getObject("genero") + " Precio: "
						+ rs.getObject("precio") + "€"

				);
			}
			elegirPelicula();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void elegirPelicula() {
		ConnexionBDD db = new ConnexionBDD();
		try {
			System.out.println("Qué pelicula desea ver? ");
			String peliculaSeleccionada = Inicio.teclado.next();

			ResultSet existe = db.getResultSet("select * from pelicula where titulo = '" + peliculaSeleccionada + "'");

			if (existe.next()) {
				System.out.println("Has seleccionado la película: " + existe.getObject("titulo"));
			} else {
				System.out.println("No existe ninguna pelicula con ese titulo");
				elegirPelicula();
			}
		
			existe.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void sesionesPelicula() {
		ConnexionBDD db = new ConnexionBDD();

		
		
	}
}

