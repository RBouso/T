package BaseDatos;


public class Estacion {
	private String ciudad;
	private String pais;
	private Double latitud;
	private Double longitud;
	private boolean esFavorita;
	private int puntuacion;
	
	
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Double getLatitud() {
		return latitud;
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	public boolean isEsFavorita() {
		return esFavorita;
	}
	public void setEsFavorita(boolean esFavorita) {
		this.esFavorita = esFavorita;
	}
	public int getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	
}
