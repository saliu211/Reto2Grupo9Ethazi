package Reto2;

	public class Cliente {
		private String dni;
		private String nombre;
		private String apellidos;
		private String email;
		private String contraseña;
		public Cliente(String dni, String nombre, String apellidos, String email, String contraseña) {
			this.dni = dni;
			this.nombre = nombre;
			this.apellidos = apellidos;
			this.email = email;
			this.contraseña = contraseña;
		}
		public String getDni() {
			return dni;
		}
		public void setDni(String dni) {
			this.dni = dni;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellidos() {
			return apellidos;
		}
		public void setApellidos(String apellidos) {
			this.apellidos = apellidos;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getContraseña() {
			return contraseña;
		}
		public void setContraseña(String contraseña) {
			this.contraseña = contraseña;
		}
		@Override
		public String toString() {
			return "Cliente [dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
					+ ", contraseña=" + contraseña + "]";
		}


	}

