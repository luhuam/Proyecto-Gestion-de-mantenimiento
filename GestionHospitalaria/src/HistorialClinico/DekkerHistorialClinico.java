/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HistorialClinico;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Algoritmo de Dekker para dos hilos. Donde en cada hilo se iteran 20 veces,
 * compitiendo en cada iteración para entrar a su sección critica y utilizando
 * el algoritmo de Dekker para manejar la exclusión mutua.
 */

public class DekkerHistorialClinico {
    //Si no se pone volatile, Java supone que es una constante.
    private volatile int idHC;
    //0 es false, 1 es true.
    private final AtomicIntegerArray flag = new AtomicIntegerArray(2);
    
    public DekkerHistorialClinico(){
        this.idHC = 0;
        this.flag.set(0, 0);
        this.flag.set(1, 1);
    }
    
    /**
     * Comienza la ejecución del algoritmo de Dekker.
     *
     * @param id Identificador del hilo, este solo puede ser 0 o 1.
     * @param seccionCritica Sección critica.
     */
    
    public void comenzar(int id, Historial.SeccionCritica seccionCritica) throws SQLException{
        //Seccion no critica
        int otro = (id + 1) % 2;
        this.flag.set(id, 1);
        while (this.flag.get(otro) == 1) {
            if (this.idHC == otro) {
                this.flag.set(id, 0);
                while (this.idHC != id) {
                    //Esperar
                }
                this.flag.set(id, 1);
            }
        }
        
        //Inicio seccion critica
        System.out.println("Comenzo sección critica del algoritmo de Dekker "
                + "del hilo: " + id);
        seccionCritica.registrar();
        
        System.out.println("Finalizo sección critica del algoritmo de Dekker "
                + "del hilo: " + id);
        //Fin sección critica.        
        this.idHC = otro;
        this.flag.set(id, 0);
        //Sección no critica.
    }
}
