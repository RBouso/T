/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transportes.Ficheros;

/**
 *
 * @author raquel
 */
public class Lugar extends Cosa{
    private CoordinadasGeo geo;
    private DireccionPostal direccion;
    private String telefono;

    public CoordinadasGeo getGeo() {
        return geo;
    }

    public void setGeo(CoordinadasGeo geo) {
        this.geo = geo;
    }

    public DireccionPostal getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionPostal direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}
