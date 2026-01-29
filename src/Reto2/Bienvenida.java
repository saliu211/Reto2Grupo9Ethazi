package Reto2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;

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
				System.out.println(rs.getObject("numpelicula") + "-" + rs.getObject("titulo"));
			}
			elegirPelicula();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void elegirPelicula() {
		ConnexionBDD db = new ConnexionBDD();
		try {
			System.out.println("Qué pelicula desea ver? Introduce el nombre de esta: ");
			String peliculaSeleccionada = Inicio.teclado.nextLine();

			ResultSet existe = db.getResultSet("select * from pelicula where titulo = '" + peliculaSeleccionada + "'");

			if (existe.next()) {
				System.out.println("Seleccionada: " + existe.getObject("titulo"));
				mostrarFechas(peliculaSeleccionada);
			} else {
				System.out.println("No existe ninguna pelicula con ese titulo");
				elegirPelicula();
			}

			existe.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void mostrarFechas(String peliculaSeleccionada) {
		ConnexionBDD db = new ConnexionBDD();

		try {
			ResultSet rsFechas = db.getResultSet("select distinct date(s.fecha) as fecha " + "from sesion s "
					+ "join pelicula p on s.numpelicula = p.numpelicula " + "where p.titulo = '" + peliculaSeleccionada
					+ "'");

			ArrayList<LocalDate> fechas = new ArrayList<>();
			int i = 1;

			System.out.println("Fechas disponibles para " + peliculaSeleccionada + ":");

			while (rsFechas.next()) {
				LocalDate fecha = rsFechas.getDate("fecha").toLocalDate();
				fechas.add(fecha);
				System.out.println(i + ". " + fecha);
				i++;
			}

			rsFechas.close();

			int opcion = -1;
			try {
				System.out.println("Introduce el numero de la sesion. (0 para volver atras): ");
				opcion = Inicio.teclado.nextInt();
				Inicio.teclado.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Introduce un numero, no un carácter.");
				Inicio.teclado.nextLine();
				mostrarFechas(peliculaSeleccionada);
			}
			if (opcion == 0) {
				leerPeliculas();
			} else if (opcion < 0 || opcion > fechas.size()) {
				System.out.println("No hay ninguna sesion con este numero de sesion. ");
				mostrarFechas(peliculaSeleccionada);
			} else {
				mostrarHorarios(peliculaSeleccionada, fechas.get(opcion - 1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void mostrarHorarios(String peliculaSeleccionada, LocalDate fecha) {
		ConnexionBDD db = new ConnexionBDD();

		try {
			ResultSet rs = db.getResultSet(

					"select numsesion, s.horainicio, sa.nomsala, s.precio " + "from sesion s "
							+ "join pelicula p on s.numpelicula = p.numpelicula "
							+ "join sala sa on s.numsala = sa.numsala " + "where p.titulo = '" + peliculaSeleccionada
							+ "' " + "and date(s.fecha) = '" + fecha + "' " + "order by s.horainicio");

			System.out.println("Horarios " + fecha + " (" + peliculaSeleccionada + ")");

			while (rs.next()) {
				LocalTime hora = rs.getTimestamp("horainicio").toLocalDateTime().toLocalTime();
				String sala = rs.getString("nomsala");
				double precio = rs.getDouble("precio");

				System.out.println(rs.getObject("numsesion") + " - " + hora + " - " + peliculaSeleccionada + " (" + sala
						+ ") - " + precio + " €");
			}

			rs.close();
			
			System.out.println("Seleccione la sesion deseada. (0 para volver atras):");
			int sesionDeseada = Inicio.teclado.nextInt();
			ResultSet sesionExiste = db.getResultSet("SELECT s.* FROM sesion s "
					+ "INNER JOIN pelicula p ON s.numpelicula = p.numpelicula " + "WHERE s.numsesion = '"
					+ sesionDeseada + "' AND p.titulo = '" + peliculaSeleccionada + "'");
			if (sesionExiste.next()) {
				resumenSeleccion();
			} else {
				System.out.println("No existe ninguna sesion asociada a esa pelicula.");
				mostrarHorarios(peliculaSeleccionada, fecha);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void resumenSeleccion() {
		ConnexionBDD db = new ConnexionBDD();
		System.out.println("ds");

	}

}
