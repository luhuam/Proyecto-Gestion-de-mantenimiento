/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Citas;

import Conexion.ConnectionPool;
import InicioSesion.inicioRecepcionista;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author DELL
 */
public class Citas extends javax.swing.JFrame {

    /**
     * Creates new form Citas
     */
    Connection cn;
    DefaultTableModel modelo;
    ArrayList<registroCitas> registrocitas = new ArrayList<registroCitas>();
    Statement st, st2;
    ResultSet rs, rs2;
    int idE;
    TableRowSorter trsfiltro;
    String filtro;

    public Citas() {
        initComponents();
        setLocationRelativeTo(null);
        mostrarcitas();
        mostrarEspecialidades(cb_especialidades);
        mostrarTurnos(cb_cita_turno);
    }

    void mostrarcitas() {
        String sql = "SELECT * FROM `citas` c INNER JOIN turno t ON c.turno=t.codigo_turno INNER JOIN especialidad e ON c.codigo_especialidad=e.codigo_especialidad";
        try {
            cn = ConnectionPool.getInstance().getConnection();
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            Object[] citas = new Object[7];

            modelo = (DefaultTableModel) this.Tabla_Citas.getModel();
            while (rs.next()) {
                citas[0] = rs.getString("codigo_cita");
                citas[1] = rs.getString("dni_paciente");
                citas[2] = rs.getString("nombre_especialidad");
                citas[3] = rs.getString("fecha");
                citas[4] = rs.getString("dni_medico");
                citas[5] = rs.getString("descripcion");
                citas[6] = rs.getString("nro_orden");

                modelo.addRow(citas);
            }
            Tabla_Citas.setModel(modelo);
        } catch (SQLException e) {

        }
    }

    public void colocar(registroCitas cit) throws SQLException, IOException {
        this.tf_paciente_dni.setText(cit.getDNIPaciente() + "");
        this.tf_medico_dni.setText(cit.getDNIMedico() + "");
        this.tf_paciente_orden.setText(cit.getNumOrden() + "");
//        this.turno.setSelectedIndex(Integer.parseInt(per.getTurno().trim()));
//        this.foto.setText(per.getFoto());
        this.cb_especialidades.setSelectedItem(cit.getEspecialidad());
        this.tf_cita_fecha.setText(cit.getFecha());
        this.cb_cita_turno.setSelectedIndex(Integer.parseInt(cit.getTurno().trim()));

    }

//    public void registrar(){
//        Connection con1 = new TestDBConnectionPool().test();
//        registroCitas uno = new registroCitas();
//        int atenciones = 0;
//        PreparedStatement pst = null;
//        ResultSet rs = null;
//        
//        //JOptionPane.showMessageDialog(this, "Uno o mas campos estan vacios. Favor de llenarlos.");
//            
//            String sql="INSERT INTO citas(dni_paciente, codigo_especialidad, fecha, dni_medico, turno, nro_orden) VALUES(?, ?, ?, ?, ?, ?)";
//            try {
//                PreparedStatement psd=(PreparedStatement) con1.prepareStatement(sql);
//                psd.setString(1, tf_paciente_dni.getText());
//                psd.setInt(2, cb_especialidades.getSelectedIndex()+1);
//                psd.setString(3, tf_cita_fecha.getText());
//                psd.setString(4, tf_medico_dni.getText());
//                psd.setInt(5, cb_cita_turno.getSelectedIndex()+1);
//                psd.setString(6, tf_paciente_orden.getText());
//            
//            int n=psd.executeUpdate();
//            if(n>0){
//                JOptionPane.showMessageDialog(null, "Registro Guardado");
//                
//                atenciones++;
//            }
//            } catch (SQLException e) {
//                System.err.print(e.toString());
//                JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
//                 
//           }
//    }
    public class SeccionCritica {

        Citas cita = new Citas();
        private int atenciones;

        void registrar() throws SQLException {
            Connection con1 = ConnectionPool.getInstance().getConnection();
            registroCitas uno = new registroCitas();
            int atenciones = 0;
            PreparedStatement pst = null;
            ResultSet rs = null;

            //JOptionPane.showMessageDialog(this, "Uno o mas campos estan vacios. Favor de llenarlos.");
            String sql = "INSERT INTO citas(dni_paciente, codigo_especialidad, fecha, dni_medico, turno, nro_orden) VALUES(?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement psd = (PreparedStatement) con1.prepareStatement(sql);
                psd.setString(1, tf_paciente_dni.getText());
                psd.setInt(2, cb_especialidades.getSelectedIndex() + 1);
                psd.setString(3, tf_cita_fecha.getText());
                psd.setString(4, tf_medico_dni.getText());
                psd.setInt(5, cb_cita_turno.getSelectedIndex() + 1);
                psd.setString(6, tf_paciente_orden.getText());

                int n = psd.executeUpdate();
                if (n > 0) {
                    //JOptionPane.showMessageDialog(null, "Registro Guardado");
                    System.out.println("Registro Guardado");
                    atenciones = atenciones + 1;
                }
            } catch (SQLException e) {
                System.err.print(e.toString());
                //JOptionPane.showMessageDialog(this.cita, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
                System.out.println("Ocurrio un error inesperado.\nFavor comunicarse con el administrador");
            }
        }

        public int getSuma() {
            return atenciones;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tf_buscar_paciente_dni = new javax.swing.JTextField();
        btn_limpiar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        bbuscar2 = new javax.swing.JButton();
        btn_registrar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        tf_eps = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tf_codigo_especialidad = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cb_cita_turno = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        tf_paciente_dni = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tf_medico_dni = new javax.swing.JTextField();
        cb_especialidades = new javax.swing.JComboBox<>();
        tf_paciente_orden = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tf_cita_fecha = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Citas = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        botonRegresar_Citas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 645));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setText("CITAS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 90, 70));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setText("LISTA DE CITAS: ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, 290, 30));

        tf_buscar_paciente_dni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_buscar_paciente_dniActionPerformed(evt);
            }
        });
        jPanel1.add(tf_buscar_paciente_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 200, 30));

        btn_limpiar.setBackground(new java.awt.Color(204, 255, 255));
        btn_limpiar.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        btn_limpiar.setText("LIMPIAR");
        btn_limpiar.setBorder(null);
        btn_limpiar.setPreferredSize(new java.awt.Dimension(136, 31));
        btn_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limpiarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_limpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 380, -1, -1));

        jLabel3.setFont(new java.awt.Font("Inter SemiBold", 0, 14)); // NOI18N
        jLabel3.setText("DNI: ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 40, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("DATOS PACIENTE: ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, 290, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("DATOS CITA: ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 290, 30));

        bbuscar2.setBackground(new java.awt.Color(204, 255, 255));
        bbuscar2.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        bbuscar2.setText("BUSCAR");
        bbuscar2.setBorder(null);
        bbuscar2.setPreferredSize(new java.awt.Dimension(139, 31));
        bbuscar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbuscar2ActionPerformed(evt);
            }
        });
        jPanel1.add(bbuscar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, -1, -1));

        btn_registrar.setBackground(new java.awt.Color(204, 255, 255));
        btn_registrar.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        btn_registrar.setText("REGISTRAR");
        btn_registrar.setBorder(null);
        btn_registrar.setPreferredSize(new java.awt.Dimension(136, 31));
        btn_registrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_registrarMouseClicked(evt);
            }
        });
        btn_registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_registrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 280, -1, -1));

        btn_actualizar.setBackground(new java.awt.Color(204, 255, 255));
        btn_actualizar.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        btn_actualizar.setText("ACTUALIZAR");
        btn_actualizar.setBorder(null);
        btn_actualizar.setPreferredSize(new java.awt.Dimension(136, 31));
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_actualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 330, -1, -1));

        jLabel6.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel6.setText("EPS: ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 260, -1, -1));

        tf_eps.setEnabled(false);
        jPanel1.add(tf_eps, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, 110, 30));

        jLabel7.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel7.setText("TURNO: ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, -1, -1));

        tf_codigo_especialidad.setEnabled(false);
        jPanel1.add(tf_codigo_especialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, 90, 30));

        jLabel8.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel8.setText("DNI: ");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, -1, -1));

        jLabel9.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel9.setText("CODIGO: ");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, -1, -1));

        cb_cita_turno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_cita_turnoActionPerformed(evt);
            }
        });
        jPanel1.add(cb_cita_turno, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 410, 110, 30));

        jLabel10.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel10.setText("DNI MÉDICO: ");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 470, -1, -1));

        tf_paciente_dni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_paciente_dniActionPerformed(evt);
            }
        });
        jPanel1.add(tf_paciente_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, 170, 30));

        jLabel11.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel11.setText("FECHA:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 360, -1, -1));

        tf_medico_dni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_medico_dniActionPerformed(evt);
            }
        });
        jPanel1.add(tf_medico_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 460, 150, 30));

        cb_especialidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_especialidadesActionPerformed(evt);
            }
        });
        jPanel1.add(cb_especialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, 140, 30));
        jPanel1.add(tf_paciente_orden, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 410, 90, 30));

        jLabel12.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel12.setText("NRO ORDEN:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 420, -1, -1));

        jLabel13.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel13.setText("ESPECIALIDAD:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, -1, -1));
        jPanel1.add(tf_cita_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 350, 180, 30));

        Tabla_Citas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DNI PAC", "ESPECIALIDAD", "FECHA", "MEDICO", "TURNO", "NRO DE ORDEN"
            }
        ));
        jScrollPane1.setViewportView(Tabla_Citas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 580, 790, 100));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 520, 780, 10));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 92, 780, 10));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 780, 10));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 560, 10));

        botonRegresar_Citas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        botonRegresar_Citas.setForeground(new java.awt.Color(41, 98, 255));
        botonRegresar_Citas.setText("< REGRESAR");
        botonRegresar_Citas.setBorder(null);
        botonRegresar_Citas.setBorderPainted(false);
        botonRegresar_Citas.setContentAreaFilled(false);
        botonRegresar_Citas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonRegresar_Citas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonRegresar_CitasMouseClicked(evt);
            }
        });
        botonRegresar_Citas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegresar_CitasActionPerformed(evt);
            }
        });
        jPanel1.add(botonRegresar_Citas, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 80, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_buscar_paciente_dniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_buscar_paciente_dniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_buscar_paciente_dniActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed

    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void tf_medico_dniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_medico_dniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_medico_dniActionPerformed

    private void botonRegresar_CitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonRegresar_CitasMouseClicked
        // TODO add your handling code here:
        this.dispose();
        new inicioRecepcionista().setVisible(true);
    }//GEN-LAST:event_botonRegresar_CitasMouseClicked

    private void botonRegresar_CitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresar_CitasActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_botonRegresar_CitasActionPerformed

    private void cb_cita_turnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_cita_turnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_cita_turnoActionPerformed

    private void bbuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bbuscar2ActionPerformed
        int dniPaciente = Integer.parseInt(this.tf_buscar_paciente_dni.getText());
        String sql = "SELECT paciente.id_dni FROM paciente WHERE id_dni = " + dniPaciente;

        try {
            Statement stDNIP = cn.createStatement();
            ResultSet rsPDNI = stDNIP.executeQuery(sql);
            System.out.println(sql);

            if (rsPDNI.next()) {

                paciente p = new paciente(rsPDNI.getInt("id_dni"));
                tf_paciente_dni.setText(p.getDniPaciente() + "");
                tf_buscar_paciente_dni.setText("");
            } else {

                JOptionPane.showMessageDialog(null, "Paciente con DNI " + tf_buscar_paciente_dni.getText() + " no existe");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Citas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bbuscar2ActionPerformed


    private void btn_limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limpiarActionPerformed
        tf_paciente_dni.setText("");
        tf_buscar_paciente_dni.setText("");
        tf_eps.setText("");
        tf_cita_fecha.setText("");
    }//GEN-LAST:event_btn_limpiarActionPerformed

    private void cb_especialidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_especialidadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_especialidadesActionPerformed

    private void btn_registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarActionPerformed

    }//GEN-LAST:event_btn_registrarActionPerformed

    private void tf_paciente_dniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_paciente_dniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_paciente_dniActionPerformed

    private void btn_registrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_registrarMouseClicked
        // TODO add your handling code here:

        if (!tf_paciente_dni.getText().isEmpty() && !tf_cita_fecha.getText().isEmpty()
                && !tf_medico_dni.getText().isEmpty() && !tf_paciente_orden.getText().isEmpty()) {

            Dekker dekker = new Dekker();
            SeccionCritica seccionCriticaDekker = new SeccionCritica();

            //Hilos aplicando exclución mutua con el algoritmo de Dekker.
            Thread hilo1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dekker.comenzar(0, seccionCriticaDekker);
                    } catch (SQLException ex) {
                        Logger.getLogger(Citas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            hilo1.start();
            this.dispose();
            Thread hilo2 = new Thread(new Runnable() {

                @Override
                public void run() {

                    new Citas().setVisible(true);

                    try {
                        dekker.comenzar(1, seccionCriticaDekker);
                    } catch (SQLException ex) {
                        Logger.getLogger(Citas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            hilo2.start();

            try {
                hilo1.join();
                hilo2.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Citas.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println(" Pacientes atendidos "
                    + "iteraciones por cada hilo: " + 2);
        } else {
            JOptionPane.showMessageDialog(null, "DEBE LLENAR TODOS LOS CAMPOS. ");
        }
    }//GEN-LAST:event_btn_registrarMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Citas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Citas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Citas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Citas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Citas().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla_Citas;
    private javax.swing.JButton bbuscar2;
    private javax.swing.JButton botonRegresar_Citas;
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_limpiar;
    private javax.swing.JButton btn_registrar;
    private javax.swing.JComboBox<String> cb_cita_turno;
    public static javax.swing.JComboBox<String> cb_especialidades;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField tf_buscar_paciente_dni;
    private javax.swing.JTextField tf_cita_fecha;
    private javax.swing.JTextField tf_codigo_especialidad;
    private javax.swing.JTextField tf_eps;
    private javax.swing.JTextField tf_medico_dni;
    private javax.swing.JTextField tf_paciente_dni;
    private javax.swing.JTextField tf_paciente_orden;
    // End of variables declaration//GEN-END:variables

    private void mostrarEspecialidades(JComboBox e) {
        DefaultComboBoxModel comboe = new DefaultComboBoxModel();
        e.setModel(comboe);
        cargarEspecialidades cE = new cargarEspecialidades();
        String seleccionEsp;
        try {

            Statement ste = cn.createStatement();
            ResultSet rsE = ste.executeQuery("SELECT especialidad.nombre_especialidad, especialidad.codigo_especialidad, medico.id_dni_personal FROM medico\n"
                    + "INNER JOIN especialidad ON especialidad.codigo_especialidad = medico.codigo_especialidad");

            while (rsE.next()) {
                especialidades esp = new especialidades();
                esp.setEspecialidades(rsE.getString("especialidad.nombre_especialidad"));
                cE.agregarEspecialidades(esp);
                comboe.addElement(esp.getEspecialidades());
                System.out.println("Especialidades cargadas correctamente");

            }
            /*if(Citas.cb_especialidades.getItemCount() > 0){
                seleccionEsp = Citas.cb_especialidades.getSelectedItem().toString();
                String sql = "SELECT especialidad.nombre_especialidad, especialidad.codigo_especialidad, medico.id_dni_personal FROM medico\n" +
                             "INNER JOIN especialidad ON especialidad.codigo_especialidad = medico.codigo_especialidad\n" +
                             "WHERE especialidad.nombre_especialidad = '"+seleccionEsp+"'";
                System.out.println(sql);
            }*/
        } catch (SQLException x) {
            System.out.println("Error al mostrar combobox Turnos" + x);
        }
    }

    private void mostrarTurnos(JComboBox t) {
        DefaultComboBoxModel combot = new DefaultComboBoxModel();
        t.setModel(combot);
        cargarTurno cT = new cargarTurno();

        try {
            Statement stt = cn.createStatement();
            ResultSet rsT = stt.executeQuery("SELECT descripcion FROM turno;");
            while (rsT.next()) {
                turno trn = new turno();
                trn.setTurno(rsT.getString(1));
                cT.agregarTurno(trn);
                combot.addElement(trn.getTurno());
                System.out.println("Turno cargados correctamente");
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar combobox Turnos" + e);
        }
    }

}
