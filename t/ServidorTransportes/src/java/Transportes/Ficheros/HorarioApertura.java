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
    private Temporada temporada; //temporada de un horario
    private Hora horaLlegada;//hora de llegada 
    private Hora horaSalida;//hora de sálida
    private TipoDia tipo;//tipo de día de un horario
    
    /**
     * Creadora de una instancia de horarioApertura
     */
    public HorarioApertura() {
        temporada = new Temporada();
        horaLlegada = new Hora();
        horaSalida = new Hora();
        tipo = new TipoDia();
    }

    /**
     * Obtiene la temporada
     * @return la temporada de una horario
     */
    public Temporada getTemporada() {
        return temporada;
    }

    /**
     * Añadir una temporada
     * @param temporada: Temporada de un horario 
     */
    public void setTemporada(Temporada temporada) {
        this.temporada = temporada;
    }

      /**
     * Obtiene la hora de llegada
     * @return la hora de llegada
     */
    public Hora getHoraLlegada() {
        return horaLlegada;
    }

   /**
    * Añade una hora de llegada
    * @param horaLlegada : hora de la llegada
    */
    
    public void setHoraLlegada(Hora horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

      /**
     * Obtiene la hora de salida
     * @return la hora de salida
     */
    public Hora getHoraSalida() {
        return horaSalida;
    }

    /**
     * Añade una hora de salida
     * @param horaSalida: hora de salida 
     */
    public void setHoraSalida(Hora horaSalida) {
        this.horaSalida = horaSalida;
    }

      /**
     * Obtiene el tipo de día
     * @return tipo de día del horario
     */
    public TipoDia getTipo() {
        return tipo;
    }

    /**
     * Añade el tipo de día de un horario
     * @param tipo: tipo de día 
     */
    public void setTipo(TipoDia tipo) {
        this.tipo = tipo;
    }
    
    
}
