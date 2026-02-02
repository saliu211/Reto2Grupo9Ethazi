package Reto2;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
public class Sesion {
	  private int numSesion;
	    private int numSala;
	    private int numPelicula;
	    private LocalTime horaInicio;
	    private LocalTime horaFin;
	    private LocalDate fecha;
	    private double precio;
	   
	    public Sesion(int numSesion, int numSala, int numPelicula, LocalTime horaInicio, LocalTime horaFin, LocalDate fecha, double precio) {
	        this.numSesion = numSesion;
	        this.numSala = numSala;
	        this.numPelicula = numPelicula;
	        this.horaInicio = horaInicio;
	        this.horaFin = horaFin;
	        this.fecha = fecha;
	        this.precio = precio;
	    }
		public Sesion(LocalDate fecha) {
			this.fecha = fecha;
		}
		public int getNumSesion() {
			return numSesion;
		}
		public void setNumSesion(int numSesion) {
			this.numSesion = numSesion;
		}
		public int getNumSala() {
			return numSala;
		}
		public void setNumSala(int numSala) {
			this.numSala = numSala;
		}
		public int getNumPelicula() {
			return numPelicula;
		}
		public void setNumPelicula(int numPelicula) {
			this.numPelicula = numPelicula;
		}	
		public LocalTime getHoraInicio() {
			return horaInicio;
		}
		public void setHoraInicio(LocalTime horaInicio) {
			this.horaInicio = horaInicio;
		}
		public LocalTime getHoraFin() {
			return horaFin;
		}
		public void setHoraFin(LocalTime horaFin) {
			this.horaFin = horaFin;
		}
		public LocalDate getFecha() {
			return fecha;
		}
		public void setFecha(LocalDate fecha) {
			this.fecha = fecha;
		}
		public double getPrecio() {
			return precio;
		}
		public void setPrecio(double precio) {
			this.precio = precio;
		}
		@Override
		public String toString() {
			return "Sesion [numSesion=" + numSesion + ", numSala=" + numSala + ", numPelicula=" + numPelicula
					+ ", horaInicio=" + horaInicio + ", horaFinal=" + horaFin + ", fecha=" + fecha + ", precio="
					+ precio + "]";
		}
}		