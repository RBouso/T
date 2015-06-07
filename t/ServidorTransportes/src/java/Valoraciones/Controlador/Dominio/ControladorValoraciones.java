/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Valoraciones.Controlador.Dominio;

import FactoriaDatos.CtrlFactoriaDatos;
import Valoraciones.Controlador.Datos.Interficie.CtrlEstacionValorada;
import Valoraciones.Ficheros.EstacionValorada;
/**
 *
 * @author raquel
 */
public class ControladorValoraciones {
    
    public Double añadirPuntuacion(int puntuacion, double latitud, double longitud,
            String ciudad, String pais) {
            if (puntuacion < 0 || puntuacion > 5) {
                return -1.0;
            }
            else {
                CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
                CtrlEstacionValorada cev = cfd.getEstacionValorada();
                EstacionValorada val = cev.anadirPuntuacion(puntuacion,latitud,longitud,
                        ciudad, pais, false);

                return val.getMedia();
            }
        }

    
    public Double modificarPuntuacion(int puntuacionNueva, int puntuacionAntigua, 
            double latitud, double longitud,
            String ciudad, String pais) {
         if (puntuacionNueva < 0 || puntuacionNueva > 5 || puntuacionAntigua <0 || puntuacionAntigua > 5) {
                return -1.0;
            }
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionValorada cev = cfd.getEstacionValorada();
        EstacionValorada val = cev.anadirPuntuacion(puntuacionNueva-puntuacionAntigua,
                latitud,longitud,ciudad, pais, true);
        
        return val.getMedia();
    }

    public Double getMedia(Double latitud, double longitud, String ciudad, String pais) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionValorada cev = cfd.getEstacionValorada();
        EstacionValorada val = cev.getEstacionValorada(
                latitud,longitud,ciudad, pais);
        return val.getMedia();
    }
}
