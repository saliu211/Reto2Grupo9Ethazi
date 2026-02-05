package Reto2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bienvenida {
    static ArrayList<Sesion> sesiones = new ArrayList<>();
    static ArrayList<Compra> carrito = new ArrayList<>();
    static ArrayList<String> peliculasSeleccionadas = new ArrayList<>();
    static Scanner teclado = new Scanner(System.in);
    static int opcion = -1;

    public static void main(String[] args) {
        System.out.println("							-- Bienvenido al Cine Errekamari --");
        System.out.println("Pulsa ENTER para iniciar el programa.");
        teclado.nextLine();
        System.out.println("Esta es nuestra cartelera: ");
        leerPeliculas();
    }

    public static void leerPeliculas() {
        ConnexionBDD db = new ConnexionBDD();
        try {
            ConnexionBDD.rs = db.getResultSet("SELECT * FROM pelicula");
            while (ConnexionBDD.rs.next()) {
                System.out.println(ConnexionBDD.rs.getObject("numpelicula") + "-" + ConnexionBDD.rs.getObject("titulo"));
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
            String peliculaSeleccionada = teclado.nextLine();

            ConnexionBDD.rs = db.getResultSet("SELECT * FROM pelicula WHERE titulo = '" + peliculaSeleccionada + "'");
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
            ConnexionBDD.rs = db.getResultSet(
                    "SELECT DISTINCT DATE(s.fecha) AS fecha FROM sesion s "
                            + "JOIN pelicula p ON s.numpelicula = p.numpelicula "
                            + "WHERE p.titulo = '" + peliculaSeleccionada + "'");

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

            while (opcion != 0) {
                try {
                    System.out.println("Introduce el numero de la sesion. (0 para volver atras): ");
                    opcion = teclado.nextInt();
                    teclado.nextLine();

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
                    teclado.nextLine();
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
            ConnexionBDD.rs = db.getResultSet(
                    "SELECT s.numsesion, s.horainicio, s.horafin, sa.nomsala, s.precio, p.titulo "
                            + "FROM sesion s "
                            + "JOIN pelicula p ON s.numpelicula = p.numpelicula "
                            + "JOIN sala sa ON s.numsala = sa.numsala "
                            + "WHERE p.titulo = '" + peliculaSeleccionada + "' "
                            + "AND DATE(s.fecha) = '" + fecha + "' "
                            + "ORDER BY s.horainicio");

            System.out.println("Horarios " + fecha + " (" + peliculaSeleccionada + ")");
            while (ConnexionBDD.rs.next()) {
                int numSesion = ConnexionBDD.rs.getInt("numsesion");
                LocalTime horaInicio = ConnexionBDD.rs.getTime("horainicio").toLocalTime();
                LocalTime horaFin = ConnexionBDD.rs.getTime("horafin").toLocalTime();
                double precio = ConnexionBDD.rs.getDouble("precio");

                Sesion sesion = new Sesion(numSesion, 0, 0, horaInicio, horaFin, null, precio);
                sesiones.add(sesion);

                System.out.println(numSesion + "." + " - " + horaInicio + " - " + horaFin + " - " + peliculaSeleccionada
                        + " (" + ConnexionBDD.rs.getString("nomsala") + ") - " + precio + " €");
            }
            ConnexionBDD.rs.close();

            int sesionDeseada = -1;
            while (sesionDeseada != 0) {
                try {
                    System.out.println("Seleccione la sesion deseada. (0 para volver atras):");
                    sesionDeseada = teclado.nextInt();
                    teclado.nextLine();

                    if (sesionDeseada == 0) {
                        mostrarFechas(peliculaSeleccionada);
                    }

                    Sesion sesionSeleccionada = null;
                    for (Sesion s : sesiones) {
                        if (s.getNumSesion() == sesionDeseada) {
                            sesionSeleccionada = s;
                            break;
                        }
                    }

                    if (sesionSeleccionada != null) {
                        resumenSeleccion(peliculaSeleccionada, fecha, sesionSeleccionada);
                    } else {
                        System.err.println("No existe ninguna sesion asociada a esa pelicula.");
                        mostrarHorarios(peliculaSeleccionada, fecha);
                    }

                } catch (InputMismatchException e) {
                    System.err.println("Introduce un numero, no un caracter");
                    teclado.nextLine();
                    mostrarHorarios(peliculaSeleccionada, fecha);
                }
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
                numEspectadores = teclado.nextInt();
                teclado.nextLine();
                if (numEspectadores <= 0) {
                    System.err.println("El numero de entradas debe ser mayor a 0.");
                    resumenSeleccion(peliculaSeleccionada, fecha, sesionSeleccionada);
                }
            } catch (InputMismatchException e) {
                System.err.println("Introduce un numero, no un caracter");
                teclado.nextLine();
                numEspectadores = -1;
            }
        }

        double total = sesionSeleccionada.getPrecio() * numEspectadores;
        System.out.println("Resumen de la seleccion realizada:");
        System.out.println("Pelicula: " + peliculaSeleccionada);
        System.out.println("Sesion: " + sesionSeleccionada.getNumSesion());
        System.out.println(" - " + sesionSeleccionada.getHoraInicio());
        System.out.println(" - " + sesionSeleccionada.getHoraFin());
        System.out.println("Sala: " + sesionSeleccionada.getNumSala());
        System.out.println("Espectadores: " + numEspectadores);
        System.out.println("Precio total: " + total + "€");

        Compra compra = new Compra(0, "pendiente", null, total, 0);
        carrito.add(compra);
        peliculasSeleccionadas.add(peliculaSeleccionada);

        System.out.println("¿Qué desea hacer?");
        System.out.println("1. Seguir seleccionando peliculas");
        System.out.println("2. Finalizar (Realiza la compra)");
        opcion = teclado.nextInt();
        teclado.nextLine();

        if (opcion == 1) {
            elegirPelicula();
        } else if (opcion == 2) {
            Cliente clienteLogueado = login();
            for (Compra c : carrito) {
                c.setDniCliente(clienteLogueado.getDni());
            }
            mostrarFactura(clienteLogueado);
        } else {
            System.out.println("Opción no válida.");
            resumenSeleccion(peliculaSeleccionada, fecha, sesionSeleccionada);
        }
    }

    public static Cliente login() {
        ConnexionBDD db = new ConnexionBDD();
        while (true) {
            System.out.println("Introduce tu DNI:");
            String dni = teclado.nextLine();
            System.out.println("Introduce tu contraseña:");
            String pass = teclado.nextLine();

            try {
                ResultSet rs = db.getResultSet(
                        "SELECT * FROM cliente WHERE dni = '" + dni + "' AND contraseña = '" + pass + "'");
                if (rs.next()) {
                    Cliente cliente = new Cliente(rs.getString("dni"), rs.getString("nombre"),
                            rs.getString("apellidos"), rs.getString("email"), rs.getString("contraseña"));
                    System.out.println("Login correcto. Bienvenido " + cliente.getNombre());
                    rs.close();
                    return cliente;
                } else {
                    System.err.println("DNI o contraseña incorrectos. Intente de nuevo.");
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void mostrarFactura(Cliente cliente) {
        if (carrito.isEmpty())
            return;

        System.out.println("¿Desea confirmar la compra? (si / no)");
        String opcion = teclado.nextLine();

        if (opcion.equalsIgnoreCase("si")) {
            System.out.println("Compra realizada correctamente.");
            System.out.println("Factura");
            System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellidos());
            System.out.println("DNI: " + cliente.getDni());

            System.out.println("Peliculas seleccionadas:");
            for (String titulo : peliculasSeleccionadas) {
                System.out.println("- " + titulo);
            }

            double total = 0;
            for (Compra c : carrito) {
                total += c.getPrecioTotal();
            }

            int numPeliculas = carrito.size();
            double descuento = 0;
            if (numPeliculas == 2)
                descuento = 0.20;
            else if (numPeliculas >= 3)
                descuento = 0.30;

            double totalFinal = total - (total * descuento);

            System.out.println("Precio descuento: " + total + "€");
            System.out.println("Descuento aplicado: " + (descuento * 100) + "%");
            System.out.println("TOTAL A PAGAR: " + totalFinal + "€");

            carrito.clear();
            peliculasSeleccionadas.clear();
            System.exit(0);
        } else if (opcion.equalsIgnoreCase("no")) {
            leerPeliculas();
        } else {
            System.err.println("Opcion no válida.");
            mostrarFactura(cliente);
        }
    }
}