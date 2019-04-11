package Examen;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Ejercicio7_JAXB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner t = new Scanner(System.in);
		File fichero = new File("cuentas.xml");
		Movimientos mov;
		if(fichero.exists()) {
			mov=unmarshal();
		}
		else {
			mov= new Movimientos();
			System.out.println("Nombre sucursal:");
			mov.setSucursal(t.nextLine());
		}
		System.out.println("Cuenta:");
		int cuenta = t.nextInt();t.nextLine();
		//Obtenemos los datos de la cuenta
		Cuenta c = buscarCuenta(cuenta);
		if(c==null || c.isCancelada()) {
			System.out.println("La cuenta no existe o está cancelada");
		}
		else {
			System.out.println("Ingreso (I)/Reintegro (R)");
			String tipo = t.nextLine();
			System.out.println("Importe:");
			float importe = t.nextFloat();t.nextLine();
			if(tipo.toUpperCase().equals("I")){
				Movimiento m = new Movimiento();
				m.setTipo("I");
				m.setCuenta(c.getCodigo());
				m.setApellidos(c.getApellidos());
				m.setNombre(c.getNombre());
				m.setImporte(importe);
				mov.getMovimientos().add(m);
			}
			else {
				//Chequeamos si hay saldo suficiente
				if(c.getSaldo()>=importe) {
					Movimiento m = new Movimiento();
					m.setTipo("R");
					m.setCuenta(c.getCodigo());
					m.setApellidos(c.getApellidos());
					m.setNombre(c.getNombre());
					m.setImporte(importe);
					mov.getMovimientos().add(m);
				}
				else {
					System.out.println("No hay saldo suficiente");
				}
			}
		}
		//Hacemos el marshal
		marshal(mov);
	}

	private static void marshal(Movimientos mov) {
		// TODO Auto-generated method stub
		try {
			JAXBContext contexto = 
					JAXBContext.newInstance(Movimientos.class);
			Marshaller m = contexto.createMarshaller();
			m.marshal(mov, new File("cuentas.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Cuenta buscarCuenta(int cuenta) {
		// TODO Auto-generated method stub
		Cuenta resultado = null;
		RandomAccessFile fichero = null;
		try {
			fichero= new RandomAccessFile("cuentas.bin", "r");
			while(true) {
				if(fichero.readInt()==cuenta) {
					resultado = new Cuenta();
					resultado.setCodigo(cuenta);
					String texto = "";
					for(int i=0;i<20;i++) {
						texto+=fichero.readChar();
					}
					resultado.setApellidos(texto.trim());
					texto = "";
					for(int i=0;i<10;i++) {
						texto+=fichero.readChar();
					}
					resultado.setNombre(texto.trim());
					resultado.setSaldo(fichero.readFloat());
					resultado.setCancelada(fichero.readBoolean());
				}
				else {
					fichero.seek(fichero.getFilePointer()+ 65);
				}
			}
		} 
		catch(EOFException e) {
			
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
		return resultado;
	}

	private static Movimientos unmarshal() {
		// TODO Auto-generated method stub
		Movimientos resultado = null;
		try {
			JAXBContext contexto = JAXBContext.newInstance(Movimientos.class);
			Unmarshaller um = contexto.createUnmarshaller();
			resultado = (Movimientos) um.unmarshal(new File("cuentas.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}

}
