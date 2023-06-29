/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pacientes;

import Conexion.ConnectionPool;
import com.mysql.jdbc.Blob;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import personal.personal;

/**
 *
 * @author ricse
 */
public class PacienteAct implements PacienteDAO {

    ConnectionPool cn = new ConnectionPool();
    Connection cnE;
    DefaultTableModel modeloE;
    Statement ste;
    ResultSet rse;
    int idE;

    @Override
    public DefaultTableModel buscar(String dni) {
        String[] nombreColumna = {"dni", "apellido_paterno", "apellido_materno", "nombres", "eps", "fecha_nacimiento", "sexo", "telefono"};
        Object[] especialistasSearch = new Object[8];
        DefaultTableModel modelo = new DefaultTableModel(null, nombreColumna);

        String sql = "SELECT * FROM paciente WHERE id_dni=" + dni;

        try {
            cnE = cn.getConnection();
            ste = cnE.createStatement();
            rse = ste.executeQuery(sql);

            while (rse.next()) {
                especialistasSearch[0] = rse.getString("id_dni");
                especialistasSearch[1] = rse.getString("apellido_paterno");
                especialistasSearch[2] = rse.getString("apellido_materno");
                especialistasSearch[3] = rse.getString("nombres");
                especialistasSearch[4] = rse.getString("eps");
                especialistasSearch[5] = rse.getString("fecha_nacimiento");
                especialistasSearch[6] = rse.getString("genero");
                especialistasSearch[7] = rse.getInt("telefono");

                modelo.addRow(especialistasSearch);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return modelo;
    }

    @Override
    public String mostrarPacientesSQL() {
        String sql = "SELECT * from pacientes";
        return sql;
    }

    @Override
    public String registrarPacientSQL() {
        String sql = "INSERT INTO `paciente`(`id_dni`, `nombres`, `apellido_paterno`, `apellido_materno`, `fecha_nacimiento`, `genero`, `eps`, `telefono`) VALUES (?,?,?,?,?,?,?,?)";
        return sql;
    }

}
