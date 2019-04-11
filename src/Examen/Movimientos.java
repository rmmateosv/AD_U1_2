package Examen;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder= {"sucursal","movimientos"})
public class Movimientos {
	String sucursal;
	ArrayList<Movimiento> movimientos=new ArrayList<>();
	public Movimientos() {
		super();
	}
	@XmlElement
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	@XmlElementWrapper(name="listamovimientos")
	@XmlElement(name="movimiento")
	public ArrayList<Movimiento> getMovimientos() {
		return movimientos;
	}
	public void setMovimientos(ArrayList<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	
	
}
