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
public class HorarioApertura {
    private Temporada temporada;
    private Hora horaLlegada;
    private Hora horaSalida;
    private TipoDia tipo;
    
    public HorarioApertura() {
        temporada = new Temporada();
        horaLlegada = new Hora();
        horaSalida = new Hora();
        tipo = new TipoDia();
    }

    public Temporada getTemporada() {
        return temporada;
    }

    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

    public Hora getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(Hora horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Hora getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Hora horaSalida) {
        this.horaSalida = horaSalida;
    }

    public TipoDia getTipo() {
        return tipo;
    }

    public void setTipo(TipoDia tipo) {
        this.tipo = tipo;
    }
    
    
}
