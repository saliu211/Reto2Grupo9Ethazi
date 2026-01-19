package Reto2;
public class Pelicula {
	  private int numPelicula;
	    private String titulo;
	    private int duracion;
	    private String genero;
	    private double precio;
	   
	    public Pelicula(int numPelicula, String titulo, int duracion, String genero, double precio) {
	        this.numPelicula = numPelicula;
	        this.titulo = titulo;
	        this.duracion = duracion;
	        this.genero = genero;
	        this.precio = precio;
	    }
		public int getNumPelicula() {
			return numPelicula;
		}
		public void setNumPelicula(int numPelicula) {
			this.numPelicula = numPelicula;
		}
		public String getTitulo() {
			return titulo;
		}
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		public int getDuracion() {
			return duracion;
		}
		public void setDuracion(int duracion) {
			this.duracion = duracion;
		}
		public String getGenero() {
			return genero;
		}
		public void setGenero(String genero) {
			this.genero = genero;
		}
		public double getPrecio() {
			return precio;
		}
		public void setPrecio(double precio) {
			this.precio = precio;
		}
		@Override
		public String toString() {
			return "Pelicula [numPelicula=" + numPelicula + ", titulo=" + titulo + ", duracion=" + duracion
					+ ", genero=" + genero + ", precio=" + precio + "]";
		}
	   
}
