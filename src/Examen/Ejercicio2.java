package Examen;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Ejercicio2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Declarar fichero binario
		DataInputStream fBinario = null;
		try {
			//Abrir el fichero para lectura
			fBinario = new DataInputStream(new FileInputStream("cuentas.bin"));
			//Vamos leyendo bytes del fichero
			while(true) {
				//Leemos el código que es un entero
				int codigo = fBinario.readInt();
				//Leemos los apellidos que es un texto de 20 carac, 
				//se lee caracter a caracter
				String apellidos = "";
				for(int i =0;i<20;i++) {
					apellidos+=fBinario.readChar();
				}
				//Leemos el nombre que es un texto de 10 carac, 
				//se lee caracter a caracter
				String nombre = "";
				for(int i =0;i<10;i++) {
					nombre+=fBinario.readChar();
				}
				//Leemos el saldo que es un float
				float saldo=fBinario.readFloat();
				//Leemos cancelada que es un boolean
				boolean cancelada = fBinario.readBoolean();
				
				//Mostramos el registro
				System.out.println("Código:"+codigo+
						"\tApellidos:"+apellidos+
						"\tNombre:"+nombre+
						"\tSaldo:"+saldo+
						"\tCancelada:"+cancelada);
			}
		} 
		catch (EOFException e) {
			// TODO: handle exception
			
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(fBinario!=null) {
				try {
					fBinario.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
