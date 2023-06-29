/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Citas;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Algoritmo de Dekker para dos hilos. Donde en cada hilo se iteran 20 veces,
 * compitiendo en cada iteración para entrar a su sección critica y utilizando
 * el algoritmo de Dekker para manejar la exclusión mutua.
 */
public class Dekker {

    //Si no se pone volatile, Java supone que es una constante.
    private volatile int turno;
    //0 es false, 1 es true.
    private final AtomicIntegerArray flag = new AtomicIntegerArray(2);

    /**
     * Inicializa los valores requeridos para el funcionamiento del algoritmo de
     * Dekker.
     */
    public Dekker() {
        this.turno = 0;
        //(indice, valor)
        this.flag.set(0, 0);
        this.flag.set(1, 1);
    }

    /**
     * Comienza la ejecución del algoritmo de Dekker.
     *
     * @param id Identificador del hilo, este solo puede ser 0 o 1.
     * @param seccionCritica Sección critica.
     */
    public void comenzar(int id, Citas.SeccionCritica seccionCritica) throws SQLException {
        //Sección no critica.
        int otro = (id + 1) % 2;
        this.flag.set(id, 1);
        while (this.flag.get(otro) == 1) {
            if (this.turno == otro) {
                this.flag.set(id, 0);
                while (this.turno != id) {
                    //Esperar
                }
                this.flag.set(id, 1);
            }
        }
        //Inicio sección critica.
        System.out.println("Comenzo sección critica del algoritmo de Dekker "
                + "del hilo: " + id);
        seccionCritica.registrar();
        
        System.out.println("Finalizo sección critica del algoritmo de Dekker "
                + "del hilo: " + id);
        //Fin sección critica.        
        this.turno = otro;
        this.flag.set(id, 0);
        //Sección no critica.
    }
}