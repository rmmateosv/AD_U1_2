package Examen;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Ejercicio5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ObjectInputStream fichero = null;
		
		try {
			fichero= new ObjectInputStream(new FileInputStream("cuentas.obj"));
			while(true) {
				//Leo objeto
				Cuenta cta = (Cuenta)fichero.readObject();
				cta.mostrar();
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
	}

}
