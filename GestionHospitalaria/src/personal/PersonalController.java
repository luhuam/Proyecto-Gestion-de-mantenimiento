/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personal;

import Conexion.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rolan
 */
public class PersonalController {
    
    //METODO EXTRAIDO
    public static DefaultTableModel GetPersonal(DefaultTableModel modelo){
         String sql ="SELECT * FROM personal p INNER JOIN medico m ON p.id_dni = m.id_dni_personal INNER JOIN turno t ON p.turno = t.codigo_turno INNER JOIN especialidad e ON m.codigo_especialidad = e.codigo_especialidad";
        try{
            
            Connection conetE;
            ConnectionPool conE = new ConnectionPool();
            Statement st;
            ResultSet rs;
    
            conetE = conE.getConnection();
            st = conetE.createStatement();
            rs = st.executeQuery(sql);
            Object[] especialistas = new Object[8];
            
            while(rs.next()){
                System.out.println("HOLA");
                especialistas [0] = rs.getString("nombres");
              
                especialistas [1] = rs.getString("apellido_paterno");
                especialistas [2] = rs.getString("apellido_materno");
                especialistas [3] = rs.getString("nombre_especialidad");
                especialistas [4] = rs.getString("descripcion");
                especialistas [5] = rs.getString("genero");
                especialistas [6] = rs.getString("fecha_nacimiento");
                especialistas [7] = rs.getString("telefono");
                
                modelo.addRow(especialistas);
            }
            //TablaPersonal.setModel(modelo);
        }catch(Exception e){
            
        }
        return modelo;
    }
}
