package Examen;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Ejercicio6 {

	public static void main(String[] args) {
		Scanner t = new Scanner(System.in);
		// TODO Auto-generated method stub
		ObjectInputStream fichero = null;
		ObjectOutputStream fTmp = null;
		try {
			//Abrimos ficheros
			fichero = new ObjectInputStream(new FileInputStream("cuentas.obj"));
			fTmp = new ObjectOutputStream(new FileOutputStream("cuentas.tmp",false));
			//Pedir al usuario cuenta a cancelar
			System.out.println("Introduce el código de cuenta a cancelar");
			int codigo = t.nextInt();t.nextLine();
			while(true) {
				//Leemos objeto de cuentas.obj
				Cuenta c = (Cuenta)fichero.readObject();
				//Comprobar si la cuenta es la que hay que cancelar
				if(codigo==c.getCodigo()) {
					//Cancelamos
					c.setCancelada(true);
				}
				//Pasamos la cuenta al fichero temporal
				fTmp.writeObject(c);
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(fichero!=null) {
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fTmp!=null) {
				try {
					fTmp.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		File fObj = new File("cuentas.obj");
		File fTmp2 = new File("cuentas.tmp");
		if(fObj.exists() && fTmp2.exists()) {
			//Borrar cuentas.obj
			if(!fObj.delete()) {
				System.out.println("No se puede borrar cuentas.obj");
			}
			//Renombrar cuentas.tmp
			if(!fTmp2.renameTo(fObj)) {
				System.out.println("No se puede renombar cuentas.tmp");
			}
		}
		
	}

}
