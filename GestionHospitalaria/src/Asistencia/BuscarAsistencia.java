package Asistencia;

import java.sql.CallableStatement;
//import Conexion.TestDBConnectionPool;
import Conexion.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class BuscarAsistencia {

    ConnectionPool cn = new ConnectionPool();
    Connection cnE;
    DefaultTableModel modeloE;
    Statement ste;
    ResultSet rse;
    int idE;

    public DefaultTableModel buscarAsistencias(String buscar) {
        String[] nombreColumna = {"NOMBRES", "APELLIDO PATERNO", "APELLIDO MATERNO", "DNI", "ESPECIALIDAD", "TURNO", "HORA ENTRADA", "HORA LLEGADA", "TARDANZA"};
        Object[] asistenciasSearch = new Object[9];
        DefaultTableModel modelo = new DefaultTableModel(null, nombreColumna);

        //YA NO SE USA QUERY
        //String sql =  "SELECT personal.nombres, personal.apellido_paterno, personal.apellido_materno, medico.id_dni_personal,\n" +
        //              "especialidad.nombre_especialidad, turno.descripcion, medico.hora_entrada, lector_asistencia.hora, timediff(lector_asistencia.hora, hora_entrada)FROM medico\n" +
        //              "INNER JOIN lector_asistencia ON lector_asistencia.dni_medico = medico.codigo_medico\n" +
        //              "INNER JOIN personal ON personal.id_dni = medico.id_dni_personal\n" +
        //              "INNER JOIN especialidad ON medico.codigo_especialidad = especialidad.codigo_especialidad\n" +
        //             "INNER JOIN turno ON personal.turno = turno.codigo_turno\n" +
        //              "WHERE nombres LIKE '%"+buscar+"%' OR apellido_paterno LIKE '%"+buscar+"%' OR apellido_materno LIKE '%"+buscar+"%' OR id_dni_personal LIKE '%"+buscar+"%' OR nombre_especialidad LIKE '%"+buscar+"%' OR descripcion LIKE '%"+buscar+"%' OR hora_entrada LIKE '%"+buscar+"%' OR hora LIKE '%"+buscar+"%' OR timediff(lector_asistencia.hora, hora_entrada) LIKE '%"+buscar+"%'\n"
        //              ;
        
        try{
            cnE = cn.getConnection();
            ste = cnE.createStatement();
            //rse = ste.executeQuery(sql);
            //SE LLAMA AL SP NECESARIO
            CallableStatement cStmt = cnE.prepareCall("{call SP_ASISTENCIA_BUSCAR (?)}");
            cStmt.setString(1, buscar);
            cStmt.execute();
            final ResultSet rse = cStmt.getResultSet();
            
            while(rse.next()){
                asistenciasSearch[0] = rse.getString("nombres");
                asistenciasSearch[1] = rse.getString("apellido_paterno");
                asistenciasSearch[2] = rse.getString("apellido_materno");
                asistenciasSearch[3] = rse.getInt("id_dni_personal");
                asistenciasSearch[4] = rse.getString("nombre_especialidad");
                asistenciasSearch[5] = rse.getString("descripcion");
                asistenciasSearch[6] = rse.getTime("hora_entrada");
                asistenciasSearch[7] = rse.getTime("hora");
                asistenciasSearch[8] = rse.getTime("timediff(lector_asistencia.hora, hora_entrada)");

                modelo.addRow(asistenciasSearch);
            }

//            System.out.println("asistenciasSearch[0]: "+asistenciasSearch[0]);
//            System.out.println("asistenciasSearch[1]: "+asistenciasSearch[1]);
//            System.out.println("asistenciasSearch[2]: "+asistenciasSearch[2]);
//            System.out.println("asistenciasSearch[3]: "+asistenciasSearch[3]);
//            System.out.println("asistenciasSearch[4]: "+asistenciasSearch[4]);
//            System.out.println("asistenciasSearch[5]: "+asistenciasSearch[5]);
//            System.out.println("asistenciasSearch[6]: "+asistenciasSearch[6]);
//            System.out.println("asistenciasSearch[7]: "+asistenciasSearch[7]);
//            System.out.println("asistenciasSearch[8]: "+asistenciasSearch[8]);
        } catch (SQLException e) {
            System.out.println(e);
        }

        return modelo;
    }
}
