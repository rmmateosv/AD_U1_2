package Examen;
import java.io.Serializable;

public class Cuenta implements Serializable{
	private int codigo;
	private String apellidos;
	private String nombre;
	private float saldo=0;
	private boolean cancelada=false;
	
	public Cuenta() {
		
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public boolean isCancelada() {
		return cancelada;
	}

	public void setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
	}
	
	public void mostrar() {
		System.out.println("Código:"+codigo+
				"\tApellidos:"+apellidos+
				"\tNombre:"+nombre+
				"\tSaldo:"+saldo+
				"\tCancelada:"+cancelada);
	}
}
