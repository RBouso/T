/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Valoraciones.Ficheros;

/**
 *
 * @author raquel
 */
public class URL {
    private String url;
    private String nombreReferencia;
    
    public URL(){}

    public URL(String url, String nombreReferencia) {
        this.url = url;
        this.nombreReferencia = nombreReferencia;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombreReferencia() {
        return nombreReferencia;
    }

    public void setNombreReferencia(String nombreReferencia) {
        this.nombreReferencia = nombreReferencia;
    }
}
