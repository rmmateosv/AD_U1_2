package Examen;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Ejercicio3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Declaramos el fichero
		RandomAccessFile fichero = null;
		Scanner t = new Scanner(System.in);
		try {
			//Abrimos el fichero para lectura y escritura
			fichero = new RandomAccessFile("cuentas.bin", "rw");
			
			//Pedimos la cuenta
			System.out.println("Introduce el número de cuenta");
			int cta = t.nextInt();t.nextLine();
	
			//Vamos recorriendo registros hasta llegar al buscado
			while(true) {
				//Recuperamos el número de cta del fichero
				int ctaF = fichero.readInt();
				//Comprobamos si la cuenta leída del fichero es la 
				//cuenta que ha introducido el usuario
				if(cta==ctaF) {
					//Comprobamos si no está cancelada
					//Para ello saltamos 64B desde la posición actual y
					//leemos 1 byte de cancelada
					fichero.seek(fichero.getFilePointer()+64);
					boolean cancelada = fichero.readBoolean();
					if(cancelada) {
						System.out.println("Cuenta cancelada, no se puede operar");
					}
					else {
						//Pedimos el tipo de movimiento
						System.out.println("Tipo I: Ingreso - R:Reintegro");
						String tipo = t.nextLine();
						tipo=tipo.toUpperCase();
						//Pedimos el importe
						System.out.println("Introduce importe");
						float importe = t.nextFloat();t.nextLine();
						if(tipo.equals("I")) {
							//Retrocedemos el puntero 5 bytes para leer el 
							//saldo del fichero
							fichero.seek(fichero.getFilePointer()-5);
							float saldoF = fichero.readFloat();
							//Retrocedeos el puntero 4 bytes para sobreescribir
							//el saldo
							fichero.seek(fichero.getFilePointer()-4);
							fichero.writeFloat(saldoF+importe);
							//Paramos de leer cuentas en el fichero
						}
						if(tipo.equals("R")) {
							//Retrocedemos el puntero 5 bytes para leer el 
							//saldo del fichero
							fichero.seek(fichero.getFilePointer()-5);
							float saldoF = fichero.readFloat();
							//Comprobamos si hay saldo suficiente
							if(saldoF>=importe) {
								//Retrocedeos el puntero 4 bytes para sobreescribir
								//el saldo
								fichero.seek(fichero.getFilePointer()-4);
								fichero.writeFloat(saldoF-importe);
							}
							else {
								System.out.println("No hay saldo para realizar la operación");
							}
						}
					}
					//Paramos de leer cuentas en el fichero
					break;
				}
				else {
					//Saltamos 65B para posicionarlo en la siguiente cuenta
					//Para ellos sumamos 65 a la posición actual del 
					//apuntador del fichero
					fichero.seek(fichero.getFilePointer()+65);
				}
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
			if(fichero!=null) {
				try {
					fichero.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
