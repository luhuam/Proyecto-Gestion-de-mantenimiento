/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Perfil_Personal;

import Conexion.ConnectionPool;
import com.mysql.jdbc.Blob;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import personal.persona;

/**
 *
 * @author DELL
 */
public class Perfil_Controlador {

    public persona datos(int dni) throws SQLException {
        Connection con1 = ConnectionPool.getInstance().getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        persona uno = new persona();

        if (dni < 0 || dni == 0) {
            JOptionPane.showMessageDialog(null, "Uno o mas campos estan vacios. Favor de llenarlos.");
        } else {
            try {
                pst = (PreparedStatement) con1.prepareStatement("SELECT * FROM personal p INNER JOIN medico m ON p.id_dni = m.id_dni_personal INNER JOIN turno t ON p.turno = t.codigo_turno INNER JOIN especialidad e ON m.codigo_especialidad = e.codigo_especialidad WHERE id_dni=" + dni);
                rs = pst.executeQuery();
                if (rs.next()) {
                    uno.setFoto((Blob) rs.getBlob("foto"));
                    uno.setAm(rs.getString("apellido_materno"));
                    uno.setAp(rs.getString("apellido_paterno"));
                    uno.setNacim(rs.getDate("fecha_nacimiento"));
                    uno.setNombres(rs.getString("nombres"));
                    uno.setDni(dni);
                    uno.setTurno(rs.getString("descripcion"));
                    uno.setEspecialidad(rs.getString("nombre_especialidad"));
                    uno.setTelef(rs.getInt("telefono"));
                    uno.setSexo(rs.getString("genero"));

                } else {
                    JOptionPane.showMessageDialog(null, "NO EXISTE ESTE DNI.");
                }
            } catch (SQLException e) {
                System.err.print(e.toString());
                JOptionPane.showMessageDialog(null, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
            }
        }
        return uno;
    }
}
