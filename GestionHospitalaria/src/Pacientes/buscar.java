/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pacientes;

import Conexion.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class buscar {

    Connection cnE;
    DefaultTableModel modeloE;
    Statement ste;
    ResultSet rse;
    int idE;
//    public paciente_modelo buscar(int dni){
//        
//        try {
//                ste = (PreparedStatement) cn.prepareStatement("SELECT * FROM paciente WHERE id_dni="+dni);
//                rse = ste.executeQuery();
//                if (rse.next()) {
//      
//                    uno.setApellidom(rse.getString("apellido_materno"));
//                    uno.setApellidop(rse.getString("apellido_paterno"));
//                    uno.setNc(rse.getDate("fecha_nacimiento"));
//                    uno.setNombres(rse.getString("nombres"));
//                    uno.setDni(dni);
//                    uno.setEps(rse.getString("eps"));
//                   // uno.setHc(Integer.parseInt(rse.getString("historia")));
//                   uno.setHc(0);
//                    uno.setTelef(rse.getInt("telefono"));
//                    uno.setSexo(rse.getString("genero"));
//                    
//                    
//           
//                } else {
//                    JOptionPane.showMessageDialog(null, "NO EXISTE ESTE DNI.");
//                }
//            } catch (SQLException e) {
//                System.err.print(e.toString());
//                JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
//            }
//        return uno;
//    }

    public DefaultTableModel buscar(String dni) {
        String[] nombreColumna = {"dni", "apellido_paterno", "apellido_materno", "nombres", "eps", "fecha_nacimiento", "sexo", "telefono"};
        Object[] especialistasSearch = new Object[8];
        DefaultTableModel modelo = new DefaultTableModel(null, nombreColumna);

        String sql = "SELECT * FROM paciente WHERE id_dni=" + dni;

        try {
            cnE = ConnectionPool.getInstance().getConnection();
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
}
