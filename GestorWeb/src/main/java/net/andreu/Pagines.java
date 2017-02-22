package net.andreu;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Pagines {

	@XmlElement(name="pagina")
	private List<Pagina> pagines;
	
	public Pagines(){
		
	}

	public Pagines(List<Pagina> pagines) {
		this.pagines = pagines;
	}

	public List<Pagina> getPagines() {
		return pagines;
	}

	public void setPagines(List<Pagina> pagines) {
		this.pagines = pagines;
	}
}
