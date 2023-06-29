/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package personal;

import Conexion.ConnectionPool;
import InicioSesion.inicioAdministrativo;
import com.mysql.jdbc.Blob;
import com.mysql.jdbc.PreparedStatement;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.constant.ConstantDescs.NULL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author Lisett y Valeria
 */
public class personal extends javax.swing.JFrame {

    /**
     * Creates new form personal
     */
    ConnectionPool conE = new ConnectionPool();
    String path;
    File image; 
    FileInputStream fis; 
    Connection conetE;
    DefaultTableModel modelo;
    Statement st;
    ResultSet rs;
    int idE;
    TableRowSorter trsfiltro;
    String filtro;
    public personal() {
        initComponents();
         setLocationRelativeTo(null);
        mostrarpersonal();
    }
    // EXTRACT METHOT
    void mostrarpersonal(){
//        String sql ="SELECT * FROM personal p INNER JOIN medico m ON p.id_dni = m.id_dni_personal INNER JOIN turno t ON p.turno = t.codigo_turno INNER JOIN especialidad e ON m.codigo_especialidad = e.codigo_especialidad";
//        try{
//            conetE = conE.getConnection();
//            st = conetE.createStatement();
//            rs = st.executeQuery(sql);
//            Object[] especialistas = new Object[8];
//            modelo = (DefaultTableModel) this.TablaPersonal.getModel();
//            
//            while(rs.next()){
//                System.out.println("HOLA");
//                especialistas [0] = rs.getString("nombres");
//              
//                especialistas [1] = rs.getString("apellido_paterno");
//                especialistas [2] = rs.getString("apellido_materno");
//                especialistas [3] = rs.getString("nombre_especialidad");
//                especialistas [4] = rs.getString("descripcion");
//                especialistas [5] = rs.getString("genero");
//                especialistas [6] = rs.getString("fecha_nacimiento");
//                especialistas [7] = rs.getString("telefono");
//                
//                modelo.addRow(especialistas);
//            }
//            TablaPersonal.setModel(modelo);
//        }catch(Exception e){
//            
//        }

        modelo = (DefaultTableModel) this.TablaPersonal.getModel();
        
        var model = PersonalController.GetPersonal(modelo);
        TablaPersonal.setModel(model);
    }
    public void buscar(int dni) throws SQLException {
        Connection con1 = new ConnectionPool().getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        persona uno = new persona(); 
        
        if (dni<0|| dni==0) {
            JOptionPane.showMessageDialog(this, "Uno o mas campos estan vacios. Favor de llenarlos.");
        } else {
            try {
                pst = (PreparedStatement) con1.prepareStatement("SELECT * FROM personal p INNER JOIN medico m ON p.id_dni = m.id_dni_personal INNER JOIN turno t ON p.turno = t.codigo_turno INNER JOIN especialidad e ON m.codigo_especialidad = e.codigo_especialidad WHERE id_dni="+dni);
                rs = pst.executeQuery();
                if (rs.next()) {
                    uno.setFoto((Blob) rs.getBlob("foto"));
                    uno.setAm(rs.getString("apellido_materno"));
                    uno.setAp(rs.getString("apellido_paterno"));
                    uno.setNacim(rs.getDate("fecha_nacimiento"));
                    uno.setNombres(rs.getString("nombres"));
                    uno.setDni(dni);
                    uno.setTurno(rs.getString("turno"));
                    uno.setEspecialidad(rs.getString("nombre_especialidad"));
                    uno.setTelef(rs.getInt("telefono"));
                    uno.setSexo(rs.getString("genero"));
                    colocar(uno); 
                } else {
                    JOptionPane.showMessageDialog(this, "NO EXISTE ESTE DNI.");
                }
            } catch (SQLException e) {
                System.err.print(e.toString());
                JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
            } catch (IOException ex) {
                Logger.getLogger(personal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    //Funcion eliminar en la base de datos: 
    
    public void eliminar(int dni){
        //Connection con1 = new TestDBConnectionPool().test();
        Connection con1 = null;
        PreparedStatement pst = null;
        
        persona uno = new persona();
         if (dni<0|| dni==0) {
            JOptionPane.showMessageDialog(this, "Uno o mas campos estan vacios. Favor de llenarlos.");
        } else {
             try {
                pst = (PreparedStatement) con1.prepareStatement("DELETE FROM personal p INNER JOIN medico m ON p.id_dni = m.id_dni_personal WHERE id_dni="+dni);
                int rs = pst.executeUpdate();
                if (rs>0) {
                    JOptionPane.showMessageDialog(null, "Persona Eliminada");
                } else {
                    JOptionPane.showMessageDialog(this, "NO EXISTE ESTE DNI.");
                }
            } catch (SQLException e) {
                System.err.print(e.toString());
                JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
            } // catch (IOException ex) {
              //  Logger.getLogger(personal.class.getName()).log(Level.SEVERE, null, ex);
            //}
         }
        
    }
    public void colocar(persona per) throws SQLException, IOException{
        this.pnom.setText(per.getNombres());
        this.am.setText(per.getAm());
        this.ap.setText(per.getAp());
//        this.turno.setSelectedIndex(Integer.parseInt(per.getTurno().trim()));
//        this.foto.setText(per.getFoto());
        this.dni1.setText(per.getDni()+"");
        this.telef.setText(per.getTelef()+"");
        this.nac.setText(per.getNacim()+"");
        this.sex.setSelectedItem(per.getSexo());
        this.especialidad.setSelectedItem(per.getEspecialidad());
        this.especialidad.setSelectedIndex(Integer.parseInt(per.getTurno().trim()));
        InputStream in = per.getFoto().getBinaryStream();  
        BufferedImage imagen = ImageIO.read(in);
        Image image = imagen.getScaledInstance(80, 100, Image.SCALE_DEFAULT);
        this.foto.setIcon((new ImageIcon(image)));
        this.foto.setText("");
        
    }
    public void registrar() throws SQLException{
        Connection con1 = new ConnectionPool().getConnection();
        persona uno = new persona(); 
        int dni2=Integer.parseInt(this.dni1.getText().trim());
        PreparedStatement pst = null;
        ResultSet rs = null;
        //JOptionPane.showMessageDialog(this, "Uno o mas campos estan vacios. Favor de llenarlos.");
            String sql1= "INSERT INTO usuario(usuario, contrasenia) VALUES (?,?)";
            try{
                PreparedStatement psd=(PreparedStatement) con1.prepareStatement(sql1);
 
                psd.setString(1, dni1.getText().trim());
                psd.setString(2, dni1.getText().trim());
               // psd.setString(3, "NULL");
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "registroguardado");
            }
            }catch(SQLException e) {
                System.err.print(e.toString());
                JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
            }
            try {
                pst = (PreparedStatement) con1.prepareStatement("SELECT id_usuario FROM usuario where usuario="+dni2);
                rs = pst.executeQuery();
                if (rs.next()) {
                    uno.setId(Integer.parseInt(rs.getString("id_usuario"))); 
                } else {
                    JOptionPane.showMessageDialog(this, "NO EXISTE ESTE DNI.");
                }
            } catch (SQLException e) {
                System.err.print(e.toString());
                JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
            }
            String sql="INSERT INTO personal (nombres,apellido_paterno,apellido_materno,turno,id_dni,telefono,fecha_nacimiento,genero, foto,id_usuario) VALUES (?,?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement psd=(PreparedStatement) con1.prepareStatement(sql);
                psd.setString(1, pnom.getText());
                psd.setString(2, ap.getText());
                psd.setString(3, am.getText());
                psd.setInt(4, especialidad.getSelectedIndex());
                psd.setString(5, dni1.getText());
                psd.setString(6, telef.getText());
                psd.setString(7, nac.getText());
                psd.setString(8, sex.getSelectedItem().toString());
                psd.setBinaryStream(9, fis, (int) image.length());
                psd.setString(10,uno.getId()+"");
            
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Registro Guardado");
            }
            } catch (SQLException e) {
                System.err.print(e.toString());
                JOptionPane.showMessageDialog(this, "Ocurrio un error inesperado.\nFavor comunicarse con el administrador.");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        background = new javax.swing.JPanel();
        personalTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        dniBuscador = new javax.swing.JLabel();
        am = new javax.swing.JTextField();
        datosPersonalesLabel = new javax.swing.JLabel();
        nombreLabel = new javax.swing.JLabel();
        apePaternoLabel = new javax.swing.JLabel();
        apeMaternoLabel = new javax.swing.JLabel();
        especialidadLabel = new javax.swing.JLabel();
        dniLabel = new javax.swing.JLabel();
        nacimientoLabel = new javax.swing.JLabel();
        sexLabel = new javax.swing.JLabel();
        turnoLabel = new javax.swing.JLabel();
        telefonoLabel = new javax.swing.JLabel();
        fotoLabel = new javax.swing.JLabel();
        listaPersonalLabel = new javax.swing.JLabel();
        dni_buscar = new javax.swing.JTextField();
        ap = new javax.swing.JTextField();
        pnom = new javax.swing.JTextField();
        dni1 = new javax.swing.JTextField();
        nac = new javax.swing.JTextField();
        telef = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaPersonal = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        especialidad = new javax.swing.JComboBox<>();
        sex = new javax.swing.JComboBox<>();
        foto = new javax.swing.JLabel();
        turno1 = new javax.swing.JComboBox<>();
        botonRegresar_personal = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setMinimumSize(new java.awt.Dimension(1280, 431));
        background.setPreferredSize(new java.awt.Dimension(1280, 645));
        background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        personalTitle.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        personalTitle.setText("PERSONAL");
        background.add(personalTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, -1, -1));

        jSeparator1.setBackground(new java.awt.Color(217, 217, 217));
        jSeparator1.setForeground(new java.awt.Color(217, 217, 217));
        jSeparator1.setPreferredSize(new java.awt.Dimension(1280, 0));
        background.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 98, 1280, 30));

        dniBuscador.setText("DNI");
        background.add(dniBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 156, -1, -1));

        am.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        am.setPreferredSize(new java.awt.Dimension(284, 35));
        background.add(am, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 300, 180, -1));

        datosPersonalesLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        datosPersonalesLabel.setText("DATOS PERSONALES");
        background.add(datosPersonalesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 242, -1, -1));

        nombreLabel.setText("NOMBRES");
        background.add(nombreLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, -1, -1));

        apePaternoLabel.setText("APELLIDO PATERNO");
        background.add(apePaternoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, -1, -1));

        apeMaternoLabel.setText("APELLIDO MATERNO");
        background.add(apeMaternoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 280, 120, -1));

        especialidadLabel.setText("ESPECIALIDAD");
        background.add(especialidadLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 280, -1, -1));

        dniLabel.setText("DNI");
        background.add(dniLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 346, -1, -1));

        nacimientoLabel.setText("FECHA DE NACIMIENTO");
        background.add(nacimientoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, 150, -1));

        sexLabel.setText("SEXO");
        background.add(sexLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 350, -1, -1));

        turnoLabel.setText("TURNO");
        background.add(turnoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 350, -1, -1));

        telefonoLabel.setText("TELÉFONO");
        background.add(telefonoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 350, -1, -1));

        fotoLabel.setText("FOTO");
        background.add(fotoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 270, -1, -1));

        listaPersonalLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        listaPersonalLabel.setText("LISTA DE PERSONAL");
        background.add(listaPersonalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, -1, -1));

        dni_buscar.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        dni_buscar.setPreferredSize(new java.awt.Dimension(284, 35));
        dni_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dni_buscarActionPerformed(evt);
            }
        });
        background.add(dni_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 177, -1, -1));

        ap.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        ap.setPreferredSize(new java.awt.Dimension(284, 35));
        background.add(ap, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, 200, -1));

        pnom.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        pnom.setPreferredSize(new java.awt.Dimension(284, 35));
        background.add(pnom, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 270, -1));

        dni1.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        dni1.setPreferredSize(new java.awt.Dimension(284, 35));
        background.add(dni1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 180, -1));

        nac.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        nac.setPreferredSize(new java.awt.Dimension(284, 35));
        background.add(nac, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 370, 190, -1));

        telef.setDisabledTextColor(new java.awt.Color(217, 217, 217));
        telef.setPreferredSize(new java.awt.Dimension(284, 35));
        background.add(telef, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 370, 170, -1));

        TablaPersonal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOMBRES", "APELLIDO PATERNO", "APELLIDO MATERNO", "ESPECIALIDAD", "TURNO", "GENERO", "F. NACIMIENTO", "CELULAR"
            }
        ));
        TablaPersonal.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(TablaPersonal);

        background.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, 1060, 100));

        jPanel2.setBackground(new java.awt.Color(199, 247, 247));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Inter SemiBold", 0, 14)); // NOI18N
        jLabel2.setText("BUSCAR");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(38, 38, 38))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        background.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 180, -1, -1));

        jPanel5.setBackground(new java.awt.Color(199, 247, 247));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Inter SemiBold", 0, 14)); // NOI18N
        jLabel3.setText("REGISTRAR");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(40, 40, 40))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        background.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, -1, -1));

        jPanel3.setBackground(new java.awt.Color(199, 247, 247));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Inter SemiBold", 0, 14)); // NOI18N
        jLabel1.setText("LIMPIAR");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        background.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 430, -1, -1));

        especialidad.setEditable(true);
        especialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR", "GINECO-OBSTETRICIA" }));
        background.add(especialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 300, 160, 40));

        sex.setEditable(true);
        sex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR", "M", "F" }));
        background.add(sex, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 370, 90, 40));

        foto.setText("FOTO");
        foto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fotoMouseClicked(evt);
            }
        });
        background.add(foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 300, -1, -1));

        turno1.setEditable(true);
        turno1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR", "MAÑANA", "TARDE", "NOCHE", "GUARDIA" }));
        background.add(turno1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 370, 160, 40));

        botonRegresar_personal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        botonRegresar_personal.setForeground(new java.awt.Color(41, 98, 255));
        botonRegresar_personal.setText("< REGRESAR");
        botonRegresar_personal.setBorder(null);
        botonRegresar_personal.setBorderPainted(false);
        botonRegresar_personal.setContentAreaFilled(false);
        botonRegresar_personal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonRegresar_personal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonRegresar_personalMouseClicked(evt);
            }
        });
        botonRegresar_personal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegresar_personalActionPerformed(evt);
            }
        });
        background.add(botonRegresar_personal, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 80, 30));

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setForeground(new java.awt.Color(255, 0, 0));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel4.setText("ELIMINAR");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        background.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, 120, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:
        int dni = Integer.parseInt(this.dni_buscar.getText().trim()); 
        try {
            buscar(dni);
        } catch (SQLException ex) {
            Logger.getLogger(personal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jPanel2MouseClicked

    private void dni_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dni_buscarActionPerformed
        // TODO add your handling code here:
        int dni = Integer.parseInt(this.dni_buscar.getText().trim()); 
        try {
            buscar(dni);
        } catch (SQLException ex) {
            Logger.getLogger(personal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_dni_buscarActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
        this.pnom.setText("");
        this.am.setText("");
        this.ap.setText("");
        this.especialidad.setSelectedIndex(0);
        this.foto.setIcon(null);
        this.foto.setText("Subir");
        this.dni1.setText("");
        this.telef.setText("");
        this.nac.setText("");
        this.sex.setSelectedIndex(0);
        this.dni_buscar.setText("");
        this.especialidad.setSelectedIndex(0);
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
//         TODO add your handling code here:
        if(!pnom.getText().isEmpty()&&
                !am.getText().isEmpty() && !ap.getText().isEmpty() && !dni1.getText().isEmpty() && !telef.getText().isEmpty()&& !this.nac.getText().isEmpty() &&!path.isEmpty()&&this.especialidad.getSelectedIndex()!=0&& sex.getSelectedIndex()!=0){         
            try {
                registrar();
            } catch (SQLException ex) {
                Logger.getLogger(personal.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        }
        else {
            JOptionPane.showMessageDialog(null, "DEBE LLENAR TODOS LOS CAMPOS. ");
        }
    }//GEN-LAST:event_jPanel5MouseClicked

    private void fotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fotoMouseClicked
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        
    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    
    FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif"); 
    fileChooser.setFileFilter(imgFilter);

    int result = fileChooser.showOpenDialog(this);

    if (result != JFileChooser.CANCEL_OPTION) {

        File fileName = fileChooser.getSelectedFile();

        if ((fileName == null) || (fileName.getName().equals(""))) {
            System.out.println("");
        } else {
            path = fileName.getAbsolutePath();
        }
    }
    
    image = new File(path);
        try {
             fis = new FileInputStream (image);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(personal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_fotoMouseClicked

    private void botonRegresar_personalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonRegresar_personalMouseClicked
        this.dispose();
        new inicioAdministrativo().setVisible(true);
    }//GEN-LAST:event_botonRegresar_personalMouseClicked

    private void botonRegresar_personalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresar_personalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonRegresar_personalActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        //AGREGAR accion con la clase eliminar 
        int dni=Integer.parseInt(this.dni_buscar.getText().trim());
        eliminar(dni);
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseClicked

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
            java.util.logging.Logger.getLogger(personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(personal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new personal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaPersonal;
    private javax.swing.JTextField am;
    private javax.swing.JTextField ap;
    private javax.swing.JLabel apeMaternoLabel;
    private javax.swing.JLabel apePaternoLabel;
    private javax.swing.JPanel background;
    private javax.swing.JButton botonRegresar_personal;
    private javax.swing.JLabel datosPersonalesLabel;
    private javax.swing.JTextField dni1;
    private javax.swing.JLabel dniBuscador;
    private javax.swing.JLabel dniLabel;
    private javax.swing.JTextField dni_buscar;
    private javax.swing.JComboBox<String> especialidad;
    private javax.swing.JLabel especialidadLabel;
    private javax.swing.JLabel foto;
    private javax.swing.JLabel fotoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel listaPersonalLabel;
    private javax.swing.JTextField nac;
    private javax.swing.JLabel nacimientoLabel;
    private javax.swing.JLabel nombreLabel;
    private javax.swing.JLabel personalTitle;
    private javax.swing.JTextField pnom;
    private javax.swing.JComboBox<String> sex;
    private javax.swing.JLabel sexLabel;
    private javax.swing.JTextField telef;
    private javax.swing.JLabel telefonoLabel;
    private javax.swing.JComboBox<String> turno1;
    private javax.swing.JLabel turnoLabel;
    // End of variables declaration//GEN-END:variables
}
