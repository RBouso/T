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
    
    /**
     * Añadir una nueva puntuación de una estación de transporte
     * @param puntuacion: número de puntos con los que se valora la estación
     * @param latitud: latitud de la ubicación de la estación
     * @param longitud: longitud de la ubicación de la estación
     * @param ciudad: nombre de la ciudad donde se encuentra la estación
     * @param pais: nombre del país
     * @return la media de todas las valoraciones que han hecho los usuarios
     */
    public Double añadirPuntuacion(int puntuacion, double latitud, double longitud,
            String ciudad, String pais) {
        //la puntuación debe ser entre 0 y 5
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
    
    /**
     * Indica si existe valoración para una determinada estación
     * @param latitud: latitud de la ubicación de la estación
     * @param longitud: longitud de la ubicación de la estación
     * @param ciudad: nombre de la ciudad donde se encuentra la estación
     * @param pais: nombre del país
     * @return boolean que indica si la estación ha sido valorada
     */
    public boolean existeValoracion(double latitud, double longitud,
            String ciudad, String pais) {
            
                CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
                CtrlEstacionValorada cev = cfd.getEstacionValorada();
                

                return cev.existeValoracion(latitud, longitud, ciudad, pais);
        }

     /**
     * Modificar una puntuación hecha anteriormente, de una estación de transporte
     * @param puntuacionAntigua : número de puntos con los que se había valorado
     * anteriormente la estación
     * @param puntuacionNueva: número de puntos con los que se quiere valorar la 
     * estación
     * @param latitud: latitud de la ubicación de la estación
     * @param longitud: longitud de la ubicación de la estación
     * @param ciudad: nombre de la ciudad donde se encuentra la estación
     * @param pais: nombre del país
     * @return la media de todas las valoraciones que han hecho los usuarios
     */
    public Double modificarPuntuacion(int puntuacionNueva, int puntuacionAntigua, 
            double latitud, double longitud,
            String ciudad, String pais) {
         if (puntuacionNueva < 0 || puntuacionNueva > 5 || puntuacionAntigua <0 || puntuacionAntigua > 5) {
                return -1.0;
            }
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionValorada cev = cfd.getEstacionValorada();
        //se añade como nueva puntuación la resta entre la Nueva - la Antigua
        EstacionValorada val = cev.anadirPuntuacion(puntuacionNueva-puntuacionAntigua,
                latitud,longitud,ciudad, pais, true);
        
        return val.getMedia();
    }

    /**
     * Obtener la media de todas la valoraciones, que tiene una deteminada estación
     * @param latitud: latitud de la ubicación de la estación
     * @param longitud: longitud de la ubicación de la estación
     * @param ciudad: nombre de la ciudad donde se encuentra la estación
     * @param pais: nombre del país
     * @return la media de todas las valoraciones que han realizado los usuarios,
     * de esa estación
     */
    public Double getMedia(Double latitud, double longitud, String ciudad, String pais) {
        CtrlFactoriaDatos cfd = new CtrlFactoriaDatos();
        CtrlEstacionValorada cev = cfd.getEstacionValorada();
        EstacionValorada val = cev.getEstacionValorada(
                latitud,longitud,ciudad, pais);
        return val.getMedia();
    }
}
