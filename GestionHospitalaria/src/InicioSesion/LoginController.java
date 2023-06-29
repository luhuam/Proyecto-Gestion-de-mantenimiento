/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InicioSesion;

import Conexion.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author rolan
 */
// EXTRAC CLASS
public class LoginController {
    public static int Login(String user, String pass) throws SQLException {
        Connection con1 = ConnectionPool.getInstance().getConnection();
        //Connection con1 = new Conexion().Conectar();
        //Connection con1 = new ConnectionPool().Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        //String User = userTxtf.getText();
        //String Pass = contraTxt.getText();
        if (user.equals("") || pass.equals("")) {
            return 1; //JOptionPane.showMessageDialog(this, "Uno o mas campos estan vacios. Favor de llenarlos.");
        } else {
            try {
                //pst = (PreparedStatement) con1.prepareStatement("select usuario, contrasenia from usuario where usuario='" + User
                //        + "' and contrasenia ='" + Pass + "'");
                pst = (PreparedStatement) con1.prepareStatement("SELECT usuario.usuario, usuario.contrasenia, rol.nombre_rol FROM usuario "
                        + "INNER JOIN usuario_rol ON usuario_rol.id_usuario=usuario.id_usuario "
                        + "INNER JOIN rol ON rol.id_rol=usuario_rol.id_rol where usuario.usuario='" + user
                        + "' and usuario.contrasenia ='" + pass + "'");
                rs = pst.executeQuery();

                if (rs.next()) {

                    String u = rs.getString("usuario.usuario");
                    String p = rs.getString("usuario.contrasenia");
                    String rol = rs.getString("rol.nombre_rol");
                    if (rol.equals("Recepcionista")) {
                        return 4;
                    } else {
                        if (rol.equals("Medico")) {
                            return 5;
                        } else {
                            return 6;
                        }
                    }

                } else {

                    return 3; //JOptionPane.showMessageDialog(this, "Credenciales incorrectas. Vuelve a intentar de nuevo.");
                }
            } catch (SQLException e) {
                System.err.print(e.toString());
                return 2; //JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
            }
        }
    }
}