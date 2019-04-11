package Examen;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Ejercicio1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Declaramos el fichero cuentas.txt
		BufferedReader fTexto = null;
		//Declaramos el fichero cuentas.bin
		DataOutputStream fBinario=null;
		
		try {
			//Abrimos el fichero de texto para lectura
			fTexto=new BufferedReader(new FileReader("cuentas.txt"));
			//Abrimos el fichero de binario para escritura
			fBinario = 
				new DataOutputStream(new FileOutputStream("cuentas.bin",false));
			
			//Leemos línea a línea del fichero de texto
			String linea=null;
			while((linea=fTexto.readLine())!=null) {
				//Rompemos la línea por campos
				String[] campos=linea.split(";");
				
				//Obtenemos el código y lo escribimos en fichero binario
				int codigo = Integer.parseInt(campos[0]);
				fBinario.writeInt(codigo);
				
				//Obtenemos los apellidos, fijamos 20 carc y escribimos en f. binario
				StringBuilder apellidos = new StringBuilder(campos[1]);
				apellidos.setLength(20);
				fBinario.writeChars(apellidos.toString());
				
				//Obtenemos el nombre, fijamos 10 carc y escribimos en f. binario
				StringBuilder nombre = new StringBuilder(campos[2]);
				nombre.setLength(10);
				fBinario.writeChars(nombre.toString());
				
				//Obtenemos el saldo, convertimos a float y escribimos en f.binario
				float saldo = Float.parseFloat(campos[3]);
				fBinario.writeFloat(saldo);
				
				//Escribimos false en cancelada
				fBinario.writeBoolean(false);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			//Cerramos ficheros
			if(fTexto!=null) {
				try {
					fTexto.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
