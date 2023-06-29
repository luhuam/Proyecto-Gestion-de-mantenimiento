/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HistorialClinico;

import Conexion.ConnectionPool;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 *
 * @author Julian
 */
public class Buscar extends UnicastRemoteObject implements interfacesBD {

    Connection cnE;
    DefaultTableModel modeloE;
    Statement ste;
    ResultSet rse;
    int idE;

    public Buscar() throws RemoteException {

    }

    public DefaultTableModel Historial(String buscar) {
        String[] nombreColumna = {"id_historial", "cod_medico", "dni_paciente", "fecha_redaccion", "codigo_especialidad", "tipo_sangre", "peso", "talla", "presion", "observaciones"};
        Object[] historialSearch = new Object[10];
        DefaultTableModel modelo = new DefaultTableModel(null, nombreColumna);

        String sql = "SELECT * FROM historial_clinico\n"
                + "WHERE id_historial LIKE '%" + buscar + "%' OR cod_medico LIKE '%" + buscar + "%' OR dni_paciente LIKE '%" + buscar + "%' OR codigo_especialidad LIKE '%" + buscar + "%' OR tipo_sangre LIKE '%" + buscar + "%' OR peso LIKE '%" + buscar + "%' OR presion LIKE '%" + buscar + "%'";

        try {
            cnE = ConnectionPool.getInstance().getConnection();
            ste = cnE.createStatement();
            rse = ste.executeQuery(sql);

            while (rse.next()) {
                historialSearch[0] = rse.getInt("id_historial");
                historialSearch[1] = rse.getInt("cod_medico");
                historialSearch[2] = rse.getInt("dni_paciente");
                historialSearch[3] = rse.getString("fecha_redaccion");
                historialSearch[4] = rse.getInt("codigo_especialidad");
                historialSearch[5] = rse.getString("tipo_sangre");
                historialSearch[6] = rse.getFloat("peso");
                historialSearch[7] = rse.getFloat("talla");
                historialSearch[8] = rse.getFloat("presion");
                historialSearch[9] = rse.getString("observaciones");

                modelo.addRow(historialSearch);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return modelo;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    @Override
    public void metodoListarHistorial() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
