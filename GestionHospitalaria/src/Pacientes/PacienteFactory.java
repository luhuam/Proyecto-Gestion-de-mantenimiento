/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pacientes;

/**
 *
 * @author ricse
 */
public class PacienteFactory {
       public static PacienteDAO methodsDAO(){
        return new PacienteAct();
    }
}
