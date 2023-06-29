/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Citas;

import java.util.ArrayList;

/**
 *
 * @author Julian
 */
public class cargarEspecialidades {
    ArrayList<especialidades> lista;
    
    public cargarEspecialidades(){
        lista = new ArrayList();
    }
    
    public void agregarEspecialidades(especialidades e){
        lista.add(e);
    }
}
