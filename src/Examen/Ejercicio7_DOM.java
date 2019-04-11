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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class Ejercicio7_DOM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner t = new Scanner(System.in);
		File fichero = new File("cuentasDOM.xml");	
		try {
			DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
			DocumentBuilder dc = factoria.newDocumentBuilder();
			Document doc = null;
			Element lista = null;
			Element raiz = null;
			
			//Obtenemos los datos de la cuenta
			System.out.println("Introduce el número de cuenta");
			Cuenta c = buscarCuenta(t.nextInt());t.nextLine();
			//Comprobamos que exist la cuenta y no está cancelada
			if(c==null || c.isCancelada()) {
				System.out.println("La cuenta no existe o está canelada");
			}
			else {
				//Obtenemos la lista de movimientos tanto si el fichero existe 
				//como si se crea
				if(fichero.exists()) {
					doc = dc.parse(fichero);
					raiz = doc.getDocumentElement();
					//Buscamos el nodo listamovimiento
					lista=(Element)raiz.getChildNodes().item(1);
				}
				else {
					//Creamos árbol vacío
					doc = dc.newDocument();
					//Establecemos versión
					doc.setXmlVersion("1.0");
					//Creamos nodo raiz
					raiz = doc.createElement("movimientos");
					//Asignar el nodo al árbol
					doc.appendChild(raiz);
					//creamos nodo sucursal
					Element sucursal = doc.createElement("sucursal");
					//Añadimos sucursal a raiz
					raiz.appendChild(sucursal);
					//Creamos el valor de sucursal
					System.out.println("Sucursal:");
					Text texto = doc.createTextNode(t.nextLine());
					sucursal.appendChild(texto);
					//Creamos el nodo lista de movimientos
					lista = doc.createElement("listamovimientos");
					raiz.appendChild(lista);
					
				}
				//Añadimos un nodo movimiento a la lista
				Element mov = doc.createElement("movimiento");
				System.out.println("Movimiento I/R");
				String tipo = t.nextLine();
				System.out.println("Importe");
				float importe = t.nextFloat();t.nextLine();			
				//Comprobamos si es reitengro y hay saldo
				if(tipo.toUpperCase()=="R" && c.getSaldo()<importe) {
					System.out.println("No hay saldo");
				}
				else {
					mov.setAttribute("tipo", tipo);
					Element nodo = doc.createElement("cuenta");
					mov.appendChild(nodo);
					Text texto = doc.createTextNode(Integer.toString(c.getCodigo()));
					nodo.appendChild(texto);
					
					nodo = doc.createElement("nombre");
					mov.appendChild(nodo);
					texto = doc.createTextNode(c.getNombre());
					nodo.appendChild(texto);
					
					nodo = doc.createElement("apellidos");
					mov.appendChild(nodo);
					texto = doc.createTextNode(c.getApellidos());
					nodo.appendChild(texto);
					
					nodo = doc.createElement("importe");
					mov.appendChild(nodo);
					texto = doc.createTextNode(Float.toString(importe));
					nodo.appendChild(texto);
					
					//Añadimos movimiento a la lista
					lista.appendChild(mov);
					
					//Guardar el árbol DOM en fichero
					Source fuente = new DOMSource(doc);
					Result salida = new StreamResult(fichero);
					Transformer transformar = TransformerFactory.newInstance().newTransformer();
					transformar.transform(fuente, salida);
				}
				
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
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

	

}
