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
public class EstructurasPublicas extends Lugar{
    
    private EstacionAlquilerBicicletas eab;//estación de alquiler de bicicletas
    private EstacionAparcamiento ea;//estación de aparcamietos
    


 

    public EstacionAlquilerBicicletas getEab() {
        return eab;
    }

    public void setEab(EstacionAlquilerBicicletas eab) {
        this.eab = eab;
    }

    public EstacionAparcamiento getEa() {
        return ea;
    }

    public void setEa(EstacionAparcamiento ea) {
        this.ea = ea;
    }

    
}
