package Reto2;
public class Entrada {
   private int numEntrada;
   private int numSesion;
   private int numClientes;
   private double precio;
   private double descuento;
  
   public Entrada(int numEntrada, int numSesion, int numClientes, double precio, double descuento) {
       this.numEntrada = numEntrada;
       this.numSesion = numSesion;
       this.numClientes = numClientes;
       this.precio = precio;
       this.descuento = descuento;
   }
	public int getNumEntrada() {
		return numEntrada;
	}
	public void setNumEntrada(int numEntrada) {
		this.numEntrada = numEntrada;
	}
	public int getNumSesion() {
		return numSesion;
	}
	public void setNumSesion(int numSesion) {
		this.numSesion = numSesion;
	}
	public int getNumClientes() {
		return numClientes;
	}
	public void setNumClientes(int numClientes) {
		this.numClientes = numClientes;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public double getDescuento() {
		return descuento;
	}
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	@Override
	public String toString() {
		return "Entrada [numEntrada=" + numEntrada + ", numSesion=" + numSesion + ", numClientes=" + numClientes
				+ ", precio=" + precio + ", descuento=" + descuento + "]";
	}
}
