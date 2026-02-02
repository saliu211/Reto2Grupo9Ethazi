package Reto2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Bienvenida {
	static ArrayList<Sesion> sesiones = new ArrayList<>();

	public static void main(String[] args) {

		System.out.println("							-- Bienvenido al Cine Errekamari --");
		System.out.println("Pulsa ENTER para iniciar el programa.");
		Inicio.teclado.nextLine();
		System.out.println("Esta es nuestra cartelera: ");
		leerPeliculas();
	}

	public static void leerPeliculas() {
		ConnexionBDD db = new ConnexionBDD();

		try {
			ConnexionBDD.rs = db.getResultSet("select * from pelicula");
			while (ConnexionBDD.rs.next()) {
				System.out
						.println(ConnexionBDD.rs.getObject("numpelicula") + "-" + ConnexionBDD.rs.getObject("titulo"));
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

			ConnexionBDD.rs = db.getResultSet("select * from pelicula where titulo = '" + peliculaSeleccionada + "'");

			boolean peliculaEncontrada = false;

			while (ConnexionBDD.rs.next()) {
				System.out.println("Seleccionada: " + ConnexionBDD.rs.getObject("titulo"));
				mostrarFechas(peliculaSeleccionada);
				peliculaEncontrada = true;
			}
			if (!peliculaEncontrada) {
				System.err.println("No existe ninguna pelicula con ese titulo");
				leerPeliculas();
			}

			ConnexionBDD.rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void mostrarFechas(String peliculaSeleccionada) {
		ConnexionBDD db = new ConnexionBDD();

		try {
			ConnexionBDD.rs = db.getResultSet("select distinct date(s.fecha) as fecha " + "from sesion s "
					+ "join pelicula p on s.numpelicula = p.numpelicula " + "where p.titulo = '" + peliculaSeleccionada
					+ "'");

			int i = 1;

			System.out.println("Fechas disponibles para " + peliculaSeleccionada + ":");

			while (ConnexionBDD.rs.next()) {
				LocalDate fecha = ConnexionBDD.rs.getDate("fecha").toLocalDate();

				Sesion sesion = new Sesion(fecha);
				sesiones.add(sesion);

				System.out.println(i + ". " + fecha);
				i++;
			}

			ConnexionBDD.rs.close();

			int opcion = -1;

			while (opcion != 0) {
				try {
					System.out.println("Introduce el numero de la sesion. (0 para volver atras): ");
					opcion = Inicio.teclado.nextInt();
					Inicio.teclado.nextLine();

					if (opcion == 0)
						leerPeliculas();

					if (opcion < 1 || opcion > sesiones.size()) {

						System.err.println("No hay ninguna sesion con este numero de sesion. ");
						mostrarFechas(peliculaSeleccionada);
					}
					Sesion sesionSeleccionada = sesiones.get(opcion - 1);
					mostrarHorarios(peliculaSeleccionada, sesionSeleccionada.getFecha());
				} catch (InputMismatchException e) {
					System.err.println("Introduce un numero, no un carácter.");
					Inicio.teclado.nextLine();
					mostrarFechas(peliculaSeleccionada);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void mostrarHorarios(String peliculaSeleccionada, LocalDate fecha) {
		ConnexionBDD db = new ConnexionBDD();

		try {
			ConnexionBDD.rs = db
					.getResultSet("SELECT s.numsesion, s.horainicio, s.horafin, sa.nomsala, s.precio, p.titulo "
							+ "FROM sesion s " + "JOIN pelicula p ON s.numpelicula = p.numpelicula "
							+ "JOIN sala sa ON s.numsala = sa.numsala " + "WHERE p.titulo = '" + peliculaSeleccionada
							+ "' " + "AND DATE(s.fecha) = '" + fecha + "' " + "ORDER BY s.horainicio");

			System.out.println("Horarios " + fecha + " (" + peliculaSeleccionada + ")");

			while (ConnexionBDD.rs.next()) {

				String titulo = ConnexionBDD.rs.getString("titulo");
				int numSesion = ConnexionBDD.rs.getInt("numsesion");
				LocalTime horaInicio = ConnexionBDD.rs.getTime("horainicio").toLocalTime();
				LocalTime horaFin = ConnexionBDD.rs.getTime("horafin").toLocalTime();
				String sala = ConnexionBDD.rs.getString("nomsala");
				double precio = ConnexionBDD.rs.getDouble("precio");

				Sesion sesion = new Sesion(numSesion, 0, 0, horaInicio, horaFin, null, precio);
				sesiones.add(sesion);

				System.out.println(numSesion + "." + " - " + horaInicio + " - " + horaFin + " - " + titulo + " ("
						+ ConnexionBDD.rs.getString("nomsala") + ") - " + precio + " €");
			}
			ConnexionBDD.rs.close();

			int sesionDeseada = -1;

			while (sesionDeseada != 0) {
				try {
					System.out.println("Seleccione la sesion deseada. (0 para volver atras):");
					sesionDeseada = Inicio.teclado.nextInt();
					ConnexionBDD.rs = db.getResultSet("SELECT s.* FROM sesion s "
							+ "INNER JOIN pelicula p ON s.numpelicula = p.numpelicula " + "WHERE s.numsesion = '"
							+ sesionDeseada + "' AND p.titulo = '" + peliculaSeleccionada + "'");

					if (sesionDeseada == 0) {
						mostrarFechas(peliculaSeleccionada);
					}
					if (!ConnexionBDD.rs.next() || sesionDeseada < 1) {
						System.err.println("No existe ninguna sesion asociada a esa pelicula.");
						mostrarHorarios(peliculaSeleccionada, fecha);
					}
				} catch (InputMismatchException e) {
					System.err.println("Introduce un numero, no un caracter");
					Inicio.teclado.nextLine();
					mostrarHorarios(peliculaSeleccionada, fecha);
				}
				resumenSeleccion(peliculaSeleccionada, fecha, null);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public static void resumenSeleccion(String peliculaSeleccionada, LocalDate fecha, Sesion sesionSeleccionada) {
		int numEspectadores = -1;

		while (numEspectadores <= 0) {
			try {
				System.out.println("Cuantas entradas desea: ");
				numEspectadores = Inicio.teclado.nextInt();
				Inicio.teclado.nextLine();

				if (numEspectadores <= 0) {
					System.err.println("El numero de entradas debe ser mayor a 0.");
					resumenSeleccion(peliculaSeleccionada, fecha, sesionSeleccionada);
				}

			} catch (InputMismatchException e) {
				System.err.println("Introduce un numero, no un caracter");
				Inicio.teclado.nextLine();
				numEspectadores = -1;
			}
		}
		double total = sesionSeleccionada.getPrecio() * numEspectadores;

		System.out.println("Resumen de la seleccion realizada:");
		System.out.println("Pelicula: " + peliculaSeleccionada);
		System.out.println("Sesion: " + sesionSeleccionada.getNumSesion());
		System.out.println(" - " + sesionSeleccionada.getHoraInicio());
		System.out.println(" - " + sesionSeleccionada.getHoraFin());
		System.out.println("Espectadores: " + numEspectadores);
		System.out.println("Precio total: " + total + "€");
		leerPeliculas();

	}
}
