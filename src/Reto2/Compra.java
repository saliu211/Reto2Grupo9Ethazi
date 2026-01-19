package Reto2;
import java.time.LocalDateTime;
public class Compra {
   private int numCompra;
   private String dniCliente;
   private LocalDateTime fechaHora;
   private double precioTotal;
   private double descTotal;
  
   public Compra(int numCompra, String dniCliente, LocalDateTime fechaHora, double precioTotal, double descTotal) {
       this.numCompra = numCompra;
       this.dniCliente = dniCliente;
       this.fechaHora = fechaHora;
       this.precioTotal = precioTotal;
       this.descTotal = descTotal;
   }
	public int getNumCompra() {
		return numCompra;
	}
	public void setNumCompra(int numCompra) {
		this.numCompra = numCompra;
	}
	public String getDniCliente() {
		return dniCliente;
	}
	public void setDniCliente(String dniCliente) {
		this.dniCliente = dniCliente;
	}
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public double getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
	public double getDescTotal() {
		return descTotal;
	}
	public void setDescTotal(double descTotal) {
		this.descTotal = descTotal;
	}
	@Override
	public String toString() {
		return "Compra [numCompra=" + numCompra + ", dniCliente=" + dniCliente + ", fechaHora=" + fechaHora
				+ ", precioTotal=" + precioTotal + ", descTotal=" + descTotal + "]";
	}
}
