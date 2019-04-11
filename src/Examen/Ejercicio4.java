package Examen;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

public class Ejercicio4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Declaramos fichero cuentas.bin
		RandomAccessFile fBinario = null;
		//Declaramos el fichero cuentas.obj para escritura
		ObjectOutputStream fObjetos = null;
		
		try {
			//Abrimos ficheros
			fBinario = new RandomAccessFile("cuentas.bin", "r");
			fObjetos = new ObjectOutputStream(
					new FileOutputStream("cuentas.obj",false));
			
			//Leemos cada registro del cuentas.bin
			while(true) {
				//Transformamos cada regristo de cuentas.bin en 
				//un objeto cuenta
				Cuenta cta = new Cuenta();
				cta.setCodigo(fBinario.readInt());
				String texto = "";
				for(int i=0; i<20;i++) {
					texto+=fBinario.readChar();
				}
				cta.setApellidos(texto);
				texto = "";
				for(int i=0; i<10;i++) {
					texto+=fBinario.readChar();
				}
				cta.setNombre(texto);
				cta.setSaldo(fBinario.readFloat());
				cta.setCancelada(fBinario.readBoolean());
				
				//Escribir en el fichero de objetos el obj cuenta
				fObjetos.writeObject(cta);
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
			if(fObjetos!=null) {
				try {
					fObjetos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
