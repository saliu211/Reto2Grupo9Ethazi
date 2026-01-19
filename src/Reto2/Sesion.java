package Reto2;
import java.time.LocalDateTime;
public class Sesion {
	  private int numSesion;
	    private int numSala;
	    private int numPelicula;
	    private LocalDateTime fecInicio;
	    private LocalDateTime fecFinal;
	    private double precio;
	   
	    public Sesion(int numSesion, int numSala, int numPelicula, LocalDateTime fecInicio, LocalDateTime fecFinal, double precio) {
	        this.numSesion = numSesion;
	        this.numSala = numSala;
	        this.numPelicula = numPelicula;
	        this.fecInicio = fecInicio;
	        this.fecFinal = fecFinal;
	        this.precio = precio;
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
		public LocalDateTime getFecInicio() {
			return fecInicio;
		}
		public void setFecInicio(LocalDateTime fecInicio) {
			this.fecInicio = fecInicio;
		}
		public LocalDateTime getFecFinal() {
			return fecFinal;
		}
		public void setFecFinal(LocalDateTime fecFinal) {
			this.fecFinal = fecFinal;
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
					+ ", fecInicio=" + fecInicio + ", fecFinal=" + fecFinal + ", precio=" + precio + "]";
		}
}
