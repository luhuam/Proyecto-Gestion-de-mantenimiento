package Lector;

import Conexion.ConnectionPool;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectoppc.Home;

public class Lector extends javax.swing.JFrame implements Runnable {

    Connection conetE, conetE2;
    Connection conetsE;
    Statement st, st2;
    ResultSet rs, rs2;
    int dniIngreso;
    String fechaAhora;
    String horaMinutosAhora;
    boolean exist = false;

    // Dato.java
    public class Datos {

        private boolean disponible;
        private int datos;
        private String valorNombre, valorApePat, valorApeMat, valorTurno, valorhoraMinutosAhora, valoridDni;

        public Datos() {
            datos = 0;
            disponible = false;
        }

        public synchronized void Asignar(String nombre, String apePat, String apeMat, String turno, String horaMinutosAhora, String idDni) {
            if (disponible == true) {
                try {
                    wait();
                } catch (Exception e) {

                }
            }

            valorNombre = nombre;
            valorApePat = apePat;
            valorApeMat = apeMat;
            valorTurno = turno;
            valorhoraMinutosAhora = horaMinutosAhora;
            valoridDni = idDni;
            datos = Integer.parseInt(idDni);

            System.out.println("Escribiendo Nombre: " + valorNombre);
            System.out.println("Escribiendo ApellidoPaterno: " + valorApePat);
            System.out.println("Escribiendo ApellidoMaterno: " + valorApeMat);
            System.out.println("Escribiendo Turno: " + valorTurno);
            System.out.println("Escribiendo HoraMinutosAhora: " + valorhoraMinutosAhora);
            System.out.println("Escribiendo DNI: " + valoridDni);

            disponible = true;
            notify();
        }

        public synchronized int Obtener() {
            if (disponible == false) {
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }

            System.out.println("Se leyo el DNI: " + datos);
            disponible = false;
            notify();
            return datos;
        }
    }

    // HiloLector.java
    public class HiloLector extends Thread {

        private Datos ingreso;

        public HiloLector(Datos ingreso) {
            this.ingreso = ingreso;
        }

        @Override
        public void run() {
            int datos;
            try {
                for (int i = 1; i <= 2; i++) {
                    Thread.sleep((int) (Math.random() * 1000));
                    datos = ingreso.Obtener();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloLector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // HiloEscritor.java
    public class HiloEscritor extends Thread {

        private Datos datos;
        private String HEnombre, HEapePat, HEapeMat, HEturno, HEhoraMinutosAhora, HEidDni;

        public HiloEscritor(Datos datos) {
            this.datos = datos;
        }

        @Override
        public void run() {
            for (int i = 1; i < 2; i++) {
                // String mostrarDatos_sql = "SELECT personal.nombres, personal.apellido_paterno, personal.apellido_materno,"
                //       + "personal.turno, personal.id_dni FROM personal WHERE personal.id_dni = "+dniIngreso+"";

                try {
                    Thread.sleep((int) (Math.random() * 1000));
                    conetsE = ConnectionPool.getInstance().getConnection();
                    st = conetsE.createStatement();
                    //rs = st.executeQuery(mostrarDatos_sql);
                    //SE BUSCA EL SP NECESARIO
                    CallableStatement cStmt = conetsE.prepareCall("{call SP_PERSONAL_BUSCAR_DNI (?)}");
                    cStmt.setInt(1, dniIngreso);
                    cStmt.execute();
                    final ResultSet rs = cStmt.getResultSet();

                    fechaAhora = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String nuevohrs;
                    String nuevomnts;

                    int hrs = LocalDateTime.now().getHour();
                    if (hrs < 10) {
                        nuevohrs = "0" + hrs;
                    } else {
                        nuevohrs = Integer.toString(hrs);
                    }
                    System.out.println("Nuevohrs: " + nuevohrs);

                    int mnts = LocalDateTime.now().getMinute();
                    if (mnts < 10) {
                        nuevomnts = "0" + mnts;
                    } else {
                        nuevomnts = Integer.toString(mnts);
                    }
                    System.out.println("Nuevomnts: " + nuevomnts);

                    horaMinutosAhora = nuevohrs + ":" + nuevomnts;

                    if (rs.next()) {
                        exist = true;

                        mensajeNoHayDNI_Label.setVisible(false);

                        nombresTxtField_Lector.setText(rs.getString("nombres"));
                        HEnombre = rs.getString("nombres");

                        apePaternoTxtField_Lector.setText(rs.getString("apellido_paterno"));
                        HEapePat = rs.getString("apellido_paterno");

                        apeMaternoTxtField_Lector.setText(rs.getString("apellido_materno"));
                        HEapeMat = rs.getString("apellido_materno");

                        turnoTxtField_Lector.setText(rs.getString("turno"));
                        HEturno = rs.getString("turno");

                        horaActualTxtField_Lector.setText(horaMinutosAhora);
                        HEhoraMinutosAhora = horaMinutosAhora;

                        dniTxtField_Lector.setText(rs.getString("id_dni"));
                        HEidDni = rs.getString("id_dni");

                        datos.Asignar(HEnombre, HEapePat, HEapeMat, HEturno, HEhoraMinutosAhora, HEidDni);
                    } else {
                        exist = false;
                        System.out.println("Exist2: " + exist);
                        mensajeNoHayDNI_Label.setVisible(true);
                        nombresTxtField_Lector.setText("");
                        apePaternoTxtField_Lector.setText(rs.getString(""));
                        apeMaternoTxtField_Lector.setText(rs.getString(""));
                        turnoTxtField_Lector.setText(rs.getString(""));
                        horaActualTxtField_Lector.setText(horaMinutosAhora);
                        dniTxtField_Lector.setText(rs.getString(""));
                    }

                } catch (Exception e) {
                    //JOptionPane.showMessageDialog(null,"No se encontró registro"+e.getMessage());
                }
            }
        }
    }

    public Lector() {
        initComponents();
        mensajeNoHayDNI_Label.setVisible(false);
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundLector = new javax.swing.JPanel();
        botonRegresar_Lector = new javax.swing.JButton();
        lectorAsistenciaTitle_Lector = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        dniBuscadorLabel_Lector = new javax.swing.JLabel();
        dniBuscadorTxtField_Lector = new javax.swing.JTextField();
        datosPersonalesLabel_Lector = new javax.swing.JLabel();
        nombresLabel_Lector = new javax.swing.JLabel();
        nombresTxtField_Lector = new javax.swing.JTextField();
        apePaternoLabel_Lector = new javax.swing.JLabel();
        apePaternoTxtField_Lector = new javax.swing.JTextField();
        apeMaternoLabel_Lector = new javax.swing.JLabel();
        apeMaternoTxtField_Lector = new javax.swing.JTextField();
        turnoLabel_Lector = new javax.swing.JLabel();
        turnoTxtField_Lector = new javax.swing.JTextField();
        horaActualLabel_Lector = new javax.swing.JLabel();
        horaActualTxtField_Lector = new javax.swing.JTextField();
        dniLabel_Lector = new javax.swing.JLabel();
        dniTxtField_Lector = new javax.swing.JTextField();
        fotoLabel_Lector = new javax.swing.JLabel();
        fotoTxtField_Lector = new javax.swing.JTextField();
        botonLeerPanel_Lector = new javax.swing.JPanel();
        leerLabel_Lector1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        mensajeNoHayDNI_Label = new javax.swing.JLabel();
        botonLeerPanel_Lector1 = new javax.swing.JPanel();
        leerLabel_Lector = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backgroundLector.setBackground(new java.awt.Color(255, 255, 255));
        backgroundLector.setMinimumSize(new java.awt.Dimension(1280, 431));
        backgroundLector.setPreferredSize(new java.awt.Dimension(1280, 645));
        backgroundLector.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        botonRegresar_Lector.setBackground(new java.awt.Color(255, 255, 255));
        botonRegresar_Lector.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        botonRegresar_Lector.setForeground(new java.awt.Color(41, 98, 255));
        botonRegresar_Lector.setText("< REGRESAR");
        botonRegresar_Lector.setBorder(null);
        botonRegresar_Lector.setBorderPainted(false);
        botonRegresar_Lector.setContentAreaFilled(false);
        botonRegresar_Lector.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonRegresar_Lector.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonRegresar_LectorMouseClicked(evt);
            }
        });
        botonRegresar_Lector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegresar_LectorActionPerformed(evt);
            }
        });
        backgroundLector.add(botonRegresar_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 80, 30));

        lectorAsistenciaTitle_Lector.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lectorAsistenciaTitle_Lector.setText("LECTOR ASISTENCIA");
        backgroundLector.add(lectorAsistenciaTitle_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(517, 47, -1, -1));

        jSeparator8.setBackground(new java.awt.Color(217, 217, 217));
        jSeparator8.setForeground(new java.awt.Color(217, 217, 217));
        jSeparator8.setPreferredSize(new java.awt.Dimension(1280, 0));
        backgroundLector.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 98, 1280, 20));

        dniBuscadorLabel_Lector.setText("DNI");
        backgroundLector.add(dniBuscadorLabel_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 156, -1, -1));

        dniBuscadorTxtField_Lector.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        dniBuscadorTxtField_Lector.setPreferredSize(new java.awt.Dimension(284, 35));
        dniBuscadorTxtField_Lector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dniBuscadorTxtField_LectorActionPerformed(evt);
            }
        });
        backgroundLector.add(dniBuscadorTxtField_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 177, -1, -1));

        datosPersonalesLabel_Lector.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        datosPersonalesLabel_Lector.setText("DATOS PERSONALES");
        backgroundLector.add(datosPersonalesLabel_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 242, -1, -1));

        nombresLabel_Lector.setText("NOMBRES");
        backgroundLector.add(nombresLabel_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, -1, -1));

        nombresTxtField_Lector.setEditable(false);
        nombresTxtField_Lector.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        nombresTxtField_Lector.setPreferredSize(new java.awt.Dimension(284, 35));
        backgroundLector.add(nombresTxtField_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 270, -1));

        apePaternoLabel_Lector.setText("APELLIDO PATERNO");
        backgroundLector.add(apePaternoLabel_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, -1, -1));

        apePaternoTxtField_Lector.setEditable(false);
        apePaternoTxtField_Lector.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        apePaternoTxtField_Lector.setPreferredSize(new java.awt.Dimension(284, 35));
        apePaternoTxtField_Lector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apePaternoTxtField_LectorActionPerformed(evt);
            }
        });
        backgroundLector.add(apePaternoTxtField_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, 200, -1));

        apeMaternoLabel_Lector.setText("APELLIDO MATERNO");
        backgroundLector.add(apeMaternoLabel_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 280, 120, -1));

        apeMaternoTxtField_Lector.setEditable(false);
        apeMaternoTxtField_Lector.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        apeMaternoTxtField_Lector.setPreferredSize(new java.awt.Dimension(284, 35));
        backgroundLector.add(apeMaternoTxtField_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 300, 180, -1));

        turnoLabel_Lector.setText("TURNO");
        backgroundLector.add(turnoLabel_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 346, -1, -1));

        turnoTxtField_Lector.setEditable(false);
        turnoTxtField_Lector.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        turnoTxtField_Lector.setPreferredSize(new java.awt.Dimension(284, 35));
        turnoTxtField_Lector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnoTxtField_LectorActionPerformed(evt);
            }
        });
        backgroundLector.add(turnoTxtField_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 270, -1));

        horaActualLabel_Lector.setText("HORA ACTUAL");
        backgroundLector.add(horaActualLabel_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 350, 110, -1));

        horaActualTxtField_Lector.setEditable(false);
        horaActualTxtField_Lector.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        horaActualTxtField_Lector.setPreferredSize(new java.awt.Dimension(284, 35));
        backgroundLector.add(horaActualTxtField_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 370, 200, -1));

        dniLabel_Lector.setText("DNI");
        backgroundLector.add(dniLabel_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 350, -1, -1));

        dniTxtField_Lector.setEditable(false);
        dniTxtField_Lector.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        dniTxtField_Lector.setPreferredSize(new java.awt.Dimension(284, 35));
        backgroundLector.add(dniTxtField_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 370, 180, -1));

        fotoLabel_Lector.setText("FOTO");
        backgroundLector.add(fotoLabel_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 280, -1, -1));

        fotoTxtField_Lector.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        fotoTxtField_Lector.setPreferredSize(new java.awt.Dimension(284, 35));
        backgroundLector.add(fotoTxtField_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 300, 110, 120));

        botonLeerPanel_Lector.setBackground(new java.awt.Color(199, 247, 247));
        botonLeerPanel_Lector.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        leerLabel_Lector1.setFont(new java.awt.Font("Inter SemiBold", 0, 14)); // NOI18N
        leerLabel_Lector1.setText("      REGISTRAR");
        leerLabel_Lector1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leerLabel_Lector1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout botonLeerPanel_LectorLayout = new javax.swing.GroupLayout(botonLeerPanel_Lector);
        botonLeerPanel_Lector.setLayout(botonLeerPanel_LectorLayout);
        botonLeerPanel_LectorLayout.setHorizontalGroup(
            botonLeerPanel_LectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leerLabel_Lector1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );
        botonLeerPanel_LectorLayout.setVerticalGroup(
            botonLeerPanel_LectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, botonLeerPanel_LectorLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(leerLabel_Lector1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        backgroundLector.add(botonLeerPanel_Lector, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 430, -1, -1));

        jLabel1.setText("jLabel1");
        backgroundLector.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 310, 250, -1));

        mensajeNoHayDNI_Label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mensajeNoHayDNI_Label.setForeground(new java.awt.Color(255, 0, 0));
        mensajeNoHayDNI_Label.setText("¡No existe un personal con dicho DNI!");
        backgroundLector.add(mensajeNoHayDNI_Label, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, -1, -1));

        botonLeerPanel_Lector1.setBackground(new java.awt.Color(199, 247, 247));
        botonLeerPanel_Lector1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        leerLabel_Lector.setFont(new java.awt.Font("Inter SemiBold", 0, 14)); // NOI18N
        leerLabel_Lector.setText("   LEER");
        leerLabel_Lector.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leerLabel_LectorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout botonLeerPanel_Lector1Layout = new javax.swing.GroupLayout(botonLeerPanel_Lector1);
        botonLeerPanel_Lector1.setLayout(botonLeerPanel_Lector1Layout);
        botonLeerPanel_Lector1Layout.setHorizontalGroup(
            botonLeerPanel_Lector1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, botonLeerPanel_Lector1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(leerLabel_Lector, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        botonLeerPanel_Lector1Layout.setVerticalGroup(
            botonLeerPanel_Lector1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, botonLeerPanel_Lector1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(leerLabel_Lector, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        backgroundLector.add(botonLeerPanel_Lector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 180, -1, -1));

        getContentPane().add(backgroundLector, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonRegresar_LectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresar_LectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonRegresar_LectorActionPerformed

    private void dniBuscadorTxtField_LectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dniBuscadorTxtField_LectorActionPerformed

    }//GEN-LAST:event_dniBuscadorTxtField_LectorActionPerformed

    private void leerLabel_LectorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leerLabel_LectorMouseClicked
        dniIngreso = Integer.parseInt(dniBuscadorTxtField_Lector.getText());

        // EjScronizar.java
        Datos dni = new Datos();

        HiloLector hilolector_dni = new HiloLector(dni);
        HiloEscritor hiloescribe_datos = new HiloEscritor(dni);

        hilolector_dni.start();
        hiloescribe_datos.start();
    }//GEN-LAST:event_leerLabel_LectorMouseClicked

    private void apePaternoTxtField_LectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apePaternoTxtField_LectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apePaternoTxtField_LectorActionPerformed

    private void turnoTxtField_LectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnoTxtField_LectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_turnoTxtField_LectorActionPerformed

    private void leerLabel_Lector1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leerLabel_Lector1MouseClicked
        if (exist == true) {
            try {
                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());

                //String personalDNI_sql = "SELECT codigo_medico FROM medico INNER JOIN personal ON personal.id_dni = medico.id_dni_personal WHERE personal.id_dni = "+dniIngreso+"";
                conetE2 = ConnectionPool.getInstance().getConnection();
                st2 = conetE2.createStatement();
                //rs2 = st.executeQuery(personalDNI_sql);
                CallableStatement cStmt = conetsE.prepareCall("{call SP_CODIGO_MEDICO_DNI (?)}");
                cStmt.setInt(1, dniIngreso);
                cStmt.execute();
                final ResultSet rs2 = cStmt.getResultSet();

                int codigoPersonal = 0;

                while (rs2.next()) {
                    codigoPersonal = rs2.getInt("codigo_medico");
                }

                String IngresarAsistencia_sql = "INSERT INTO lector_asistencia(id,dni_medico, fecha, hora) VALUES(default,?,?,?)";

                conetE = ConnectionPool.getInstance().getConnection();
                PreparedStatement ps = conetE.prepareStatement(IngresarAsistencia_sql);
                ps.setInt(1, codigoPersonal);
                ps.setDate(2, sqlDate);
                ps.setTimestamp(3, sqlTime);
                ps.execute();
                ps.close();

                JOptionPane.showMessageDialog(null, "Asistencia registrada.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Asistencia no registrada. Comuníquese con el administrador: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "DNI omiso o sin registro.");
        }
    }//GEN-LAST:event_leerLabel_Lector1MouseClicked

    private void botonRegresar_LectorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonRegresar_LectorMouseClicked
        this.dispose();
        new Home().setVisible(true);
    }//GEN-LAST:event_botonRegresar_LectorMouseClicked

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
            java.util.logging.Logger.getLogger(Lector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lector().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apeMaternoLabel_Lector;
    private javax.swing.JTextField apeMaternoTxtField_Lector;
    private javax.swing.JLabel apePaternoLabel_Lector;
    private javax.swing.JTextField apePaternoTxtField_Lector;
    private javax.swing.JPanel backgroundLector;
    private javax.swing.JPanel botonLeerPanel_Lector;
    private javax.swing.JPanel botonLeerPanel_Lector1;
    private javax.swing.JButton botonRegresar_Lector;
    private javax.swing.JLabel datosPersonalesLabel_Lector;
    private javax.swing.JLabel dniBuscadorLabel_Lector;
    private javax.swing.JTextField dniBuscadorTxtField_Lector;
    private javax.swing.JLabel dniLabel_Lector;
    private javax.swing.JTextField dniTxtField_Lector;
    private javax.swing.JLabel fotoLabel_Lector;
    private javax.swing.JTextField fotoTxtField_Lector;
    private javax.swing.JLabel horaActualLabel_Lector;
    private javax.swing.JTextField horaActualTxtField_Lector;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JLabel lectorAsistenciaTitle_Lector;
    private javax.swing.JLabel leerLabel_Lector;
    private javax.swing.JLabel leerLabel_Lector1;
    private javax.swing.JLabel mensajeNoHayDNI_Label;
    private javax.swing.JLabel nombresLabel_Lector;
    private javax.swing.JTextField nombresTxtField_Lector;
    private javax.swing.JLabel turnoLabel_Lector;
    private javax.swing.JTextField turnoTxtField_Lector;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
