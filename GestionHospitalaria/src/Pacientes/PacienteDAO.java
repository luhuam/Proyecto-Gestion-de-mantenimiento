/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Pacientes;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ricse
 */
public interface PacienteDAO {
    public DefaultTableModel buscar(String dni);
    public String mostrarPacientesSQL();
    public String registrarPacientSQL();
}
