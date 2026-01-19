package Reto2;
public class Sala {
   private int numSala;
   private String nombre;
 
  
   public Sala(int numSala, String nombre) {
       this.numSala = numSala;
       this.nombre = nombre;
   }
	public int getNumSala() {
		return numSala;
	}
	public void setNumSala(int numSala) {
		this.numSala = numSala;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		return "Sala [numSala=" + numSala + ", nombre=" + nombre + "]";
	}
  
}
