package Horarios;

import Conexion.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class BuscarHorario {

    Connection cnE;
    DefaultTableModel modeloE;
    Statement ste;
    ResultSet rse;

    public DefaultTableModel buscarHorarios(String buscar1, Date buscar2, String buscar3) {
        String[] nombreColumna = {"CÓDIGO CITA", "DNI PACIENTE", "APELLIDO PATERNO", "APELLIDO MATERNO", "EPS", "FECHA", "HORA", "TURNO", "N° ORDEN"};
        Object[] horariosSearch = new Object[9];
        DefaultTableModel modelo = new DefaultTableModel(null, nombreColumna);

        String sql = "SELECT citas.codigo_cita, citas.dni_paciente, citas.dni_medico, paciente.apellido_paterno,\n"
                + "paciente.apellido_materno, paciente.eps, citas.fecha, citas.turno, citas.nro_orden\n"
                + "FROM citas INNER JOIN paciente ON paciente.id_dni = citas.dni_paciente\n"
                + "WHERE citas.dni_medico = '" + buscar1 + "' AND citas.fecha LIKE '%" + buscar2 + "%' AND citas.turno = " + buscar3 + "\n"
                + "ORDER BY codigo_cita";

//        String sql = "SELECT citas.codigo_cita, citas.dni_paciente, paciente.apellido_paterno,\n"
//                + "paciente.apellido_materno, paciente.eps, citas.fecha, citas.turno, citas.nro_orden\n"
//                + "FROM citas INNER JOIN paciente ON paciente.id_dni = citas.dni_paciente\n"
//                + "WHERE paciente.eps = '"+buscar1+"' AND citas.fecha LIKE '%"+buscar2+"%' AND citas.turno = "+buscar3+"\n"
//                + "ORDER BY codigo_cita";
        try {
            cnE = ConnectionPool.getInstance().getConnection();
            ste = cnE.createStatement();
            rse = ste.executeQuery(sql);

            while (rse.next()) {
                horariosSearch[0] = rse.getString("codigo_cita");
                horariosSearch[1] = rse.getString("dni_paciente");
                horariosSearch[2] = rse.getString("apellido_paterno");
                horariosSearch[3] = rse.getString("apellido_materno");
                horariosSearch[4] = rse.getString("eps");
                horariosSearch[5] = rse.getDate("fecha");
                horariosSearch[6] = rse.getTime("fecha");
                horariosSearch[7] = rse.getInt("turno");
                horariosSearch[8] = rse.getInt("nro_orden");

                modelo.addRow(horariosSearch);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return modelo;
    }
}
