package net.andreu;

public class Pagina {

	private String titol;
	private String url;
	private String categoria;
	
	public Pagina(){
		
	}

	public Pagina(String titol, String url, String categoria) {
		this.titol = titol;
		this.url = url;
		this.categoria = categoria;
	}

	public String getTitol() {
		return titol;
	}

	public void setTitol(String titol) {
		this.titol = titol;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
