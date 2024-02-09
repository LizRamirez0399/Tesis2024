package Vistas;

import App.Login;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Liz
 */
public final class Apertura extends javax.swing.JInternalFrame {

    Modelos.ModApertura a = new Modelos.ModApertura();
    Modelos.ModCaja c = new Modelos.ModCaja();
    Modelos.ModUsuario u = new Modelos.ModUsuario();

    Operaciones.OpeApertura daoApertura = new Operaciones.OpeApertura();
    Operaciones.OpeCaja daoCaja = new Operaciones.OpeCaja();
    Operaciones.OpeUsuario daoUsuario = new Operaciones.OpeUsuario();

    ArrayList<Object[]> datosApertura = new ArrayList<>();
    ArrayList<Object[]> datosCaja = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";
    Timestamp fechaHoraSQL = null;
    Double valorMonto = 0.0;

    /**
     * Creates new form Apertura
     */
    public Apertura() {
        this.setTitle("FrmApertura");
        initComponents();
    }

    public void cargarCaja() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosCaja.getModel();
        modelo.setRowCount(0);
        datosCaja = daoCaja.consultar(txtCriterioCaja.getText());
        for (Object[] obj : datosCaja) {
            modelo.addRow(obj);
        }
        this.tablaDatosCaja.setModel(modelo);
    }

    private void buscarCaja() {
        cargarCaja();
        BuscadorCaja.setModal(true);
        BuscadorCaja.setSize(540, 285);
        BuscadorCaja.setLocationRelativeTo(this);
        BuscadorCaja.setVisible(true);
        int fila = tablaDatosCaja.getSelectedRow();
        if (fila >= 0) {
            txtCajaCodigo.setText(tablaDatosCaja.getValueAt(fila, 0).toString());
            txtCajaDescripcion.setText(tablaDatosCaja.getValueAt(fila, 1).toString());
        } else {
            txtCajaCodigo.setText(null);
            txtCajaDescripcion.setText(null);
        }
    }

    public void habilitarCampos(String accion) {
        switch (accion) {
            case "NUEVO":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtCajaCodigo.setEnabled(true);
                txtCajaDescripcion.setEnabled(false);
                txtMontoInicial.setEnabled(true);
                txtObservacion.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnGuardar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnBuscar.setEnabled(false);
                //REDIRECIONAMOS
                txtCajaCodigo.grabFocus();
                break;
            case "GUARDAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtCajaCodigo.setEnabled(false);
                txtCajaDescripcion.setEnabled(false);
                txtMontoInicial.setEnabled(false);
                txtObservacion.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(true);
                btnGuardar.setEnabled(false);
                btnCancelar.setEnabled(false);
                btnBuscar.setEnabled(true);
                //REDIRECIONAMOS
                btnNuevo.grabFocus();
                break;
            case "CANCELAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtCajaCodigo.setEnabled(false);
                txtCajaDescripcion.setEnabled(false);
                txtMontoInicial.setEnabled(false);
                txtObservacion.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(true);
                btnGuardar.setEnabled(false);
                btnCancelar.setEnabled(false);
                btnBuscar.setEnabled(true);
                //REDIRECIONAMOS
                btnBuscar.grabFocus();
                break;
            default:
                JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR EN LA HABILITACIÓN DE LOS CAMPOS. AVISE AL ADMINISTRADOR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarFechaHora() {
        Date SYSDATE = new Date();
        SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.sql.Time fechaSQL = new java.sql.Time(SYSDATE.getTime());
        fechaHoraSQL = new Timestamp(fechaSQL.getTime());  
        txtFechaHora.setText(dateParser.format(fechaSQL));
        txtEstado.setText("ABIERTO");

        u.setIdusuario(Login.IDUSUARIO);
        daoUsuario.consultarDatos(u);
        txtUsuario.setText(u.getNombre() + ' ' + u.getApellido());
    }

    public void cargarApertura() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosApertura.getModel();
        modelo.setRowCount(0);
        datosApertura = daoApertura.consultar(txtCriterioApertura.getText());
        for (Object[] obj : datosApertura) {
            modelo.addRow(obj);
        }
        this.tablaDatosApertura.setModel(modelo);
    }

    private void buscarApertura() {
        cargarApertura();
        BuscadorApertura.setModal(true);
        BuscadorApertura.setSize(1020, 370);
        BuscadorApertura.setLocationRelativeTo(this);
        BuscadorApertura.setVisible(true);
        int fila = tablaDatosApertura.getSelectedRow();
        if (fila >= 0) {
            int idapertura = Integer.parseInt(tablaDatosApertura.getValueAt(fila, 0).toString());
            a.setIdapertura(idapertura);
            daoApertura.consultarDatos(a);
            txtCodigo.setText("" + a.getIdapertura());

            c.setIdcaja(a.getIdcaja());
            daoCaja.consultarDatos(c);
            txtCajaCodigo.setText("" + c.getIdcaja());
            txtCajaDescripcion.setText(c.getDescripcion());

            txtFechaHora.setText(tablaDatosApertura.getValueAt(fila, 3).toString());

            DecimalFormat formatter = new DecimalFormat("#,###");

            txtMontoInicial.setText(formatter.format(a.getMonto_inicial()));
            txtObservacion.setText(a.getObservacion());
            String estado = a.getEstado();
            if (estado.equals("A")) {
                txtEstado.setText("ABIERTO");
            }
            if (estado.equals('C')) {
                txtEstado.setText("CERRADO");
            }
            
            u.setIdusuario(a.getIdusuario());
            daoUsuario.consultarDatos(u);
            txtUsuario.setText(u.getNombre() + ' ' + u.getApellido());
        } else {
            txtCodigo.setText(null);
            txtCajaCodigo.setText(null);
            txtCajaDescripcion.setText(null);
            txtFechaHora.setText(null);
            txtMontoInicial.setText(null);
            txtObservacion.setText(null);
            txtEstado.setText(null);
            txtUsuario.setText(null);
        }
    }

    private void limpiarCampos() {
        //FILTROS DE BUSQUEDAS
        txtCriterioCaja.setText(null);
        txtCriterioApertura.setText(null);
        //CAMPOS
        txtCodigo.setText(null);
        txtCajaCodigo.setText(null);
        txtCajaDescripcion.setText(null);
        txtFechaHora.setText(null);
        txtMontoInicial.setText(null);
        txtObservacion.setText(null);
        txtEstado.setText(null);
        txtUsuario.setText(null);
        //OPERACIONES
        operacion = null;
        fechaHoraSQL = null;
        valorMonto = 0.0;
    }

    private void guardar() {
        String error = "";
        int id = daoApertura.nuevoID();
        int idcaja = Integer.parseInt(txtCajaCodigo.getText());
        
        double monto = valorMonto;
        String observacion = txtObservacion.getText();
        String estado = "A";
        int idusuario = Login.IDUSUARIO;
       

        if (idcaja == 0) {
            error += "NO HA SELECCIONADO UNA CAJA PARA LA APERTURA.\n";
        }
        if (monto < 0.0) {
            error += "EL MONTO INICIAL DE LA APERTURA NO PUEDE SER MENOR A 0.\n";
        }

        if (error.isEmpty()) {
            a.setIdapertura(id);
            a.setIdcaja(idcaja);
            a.setFecha(fechaHoraSQL);
            a.setMonto_inicial(monto);
            a.setObservacion(observacion);
            a.setEstado(estado);
            a.setIdusuario(idusuario);
            daoApertura.agregar(a);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
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

        BuscadorCaja = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtCriterioCaja = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosCaja = new org.jdesktop.swingx.JXTable();
        BuscadorApertura = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtCriterioApertura = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosApertura = new org.jdesktop.swingx.JXTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCajaCodigo = new org.jdesktop.swingx.JXTextField();
        txtCajaDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtFechaHora = new org.jdesktop.swingx.JXTextField();
        btnNuevo = new org.jdesktop.swingx.JXButton();
        btnGuardar = new org.jdesktop.swingx.JXButton();
        btnCancelar = new org.jdesktop.swingx.JXButton();
        jLabel14 = new javax.swing.JLabel();
        txtMontoInicial = new org.jdesktop.swingx.JXTextField();
        jLabel15 = new javax.swing.JLabel();
        txtObservacion = new org.jdesktop.swingx.JXTextField();
        jLabel16 = new javax.swing.JLabel();
        txtEstado = new org.jdesktop.swingx.JXTextField();
        jLabel17 = new javax.swing.JLabel();
        txtUsuario = new org.jdesktop.swingx.JXTextField();
        btnBuscar = new javax.swing.JButton();

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setBackground(new java.awt.Color(50, 104, 151));
        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("BUSCADOR DE CAJAS");
        jLabel13.setOpaque(true);

        txtCriterioCaja.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioCaja.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioCajaActionPerformed(evt);
            }
        });
        txtCriterioCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioCajaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioCajaKeyTyped(evt);
            }
        });

        tablaDatosCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Identificador", "Descripcion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosCaja.setInheritsPopupMenu(true);
        tablaDatosCaja.setShowGrid(true);
        tablaDatosCaja.getTableHeader().setResizingAllowed(false);
        tablaDatosCaja.getTableHeader().setReorderingAllowed(false);
        tablaDatosCaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosCajaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosCaja);
        if (tablaDatosCaja.getColumnModel().getColumnCount() > 0) {
            tablaDatosCaja.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosCaja.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosCaja.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioCaja, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorCajaLayout = new javax.swing.GroupLayout(BuscadorCaja.getContentPane());
        BuscadorCaja.getContentPane().setLayout(BuscadorCajaLayout);
        BuscadorCajaLayout.setHorizontalGroup(
            BuscadorCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorCajaLayout.setVerticalGroup(
            BuscadorCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setBackground(new java.awt.Color(50, 104, 151));
        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("BUSCADOR DE APERTURAS DE CAJA");
        jLabel18.setOpaque(true);

        txtCriterioApertura.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioApertura.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioApertura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioAperturaActionPerformed(evt);
            }
        });
        txtCriterioApertura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioAperturaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioAperturaKeyTyped(evt);
            }
        });

        tablaDatosApertura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Identificador", "CodigoCaja", "Caja", "Fecha/Hora", "Monto", "Observaciones", "Estado", "UsuarioCodigo", "Usaurio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosApertura.setInheritsPopupMenu(true);
        tablaDatosApertura.setShowGrid(true);
        tablaDatosApertura.getTableHeader().setResizingAllowed(false);
        tablaDatosApertura.getTableHeader().setReorderingAllowed(false);
        tablaDatosApertura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosAperturaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDatosApertura);
        if (tablaDatosApertura.getColumnModel().getColumnCount() > 0) {
            tablaDatosApertura.getColumnModel().getColumn(1).setMinWidth(0);
            tablaDatosApertura.getColumnModel().getColumn(1).setPreferredWidth(0);
            tablaDatosApertura.getColumnModel().getColumn(1).setMaxWidth(0);
            tablaDatosApertura.getColumnModel().getColumn(7).setMinWidth(0);
            tablaDatosApertura.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablaDatosApertura.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioApertura, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioApertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorAperturaLayout = new javax.swing.GroupLayout(BuscadorApertura.getContentPane());
        BuscadorApertura.getContentPane().setLayout(BuscadorAperturaLayout);
        BuscadorAperturaLayout.setHorizontalGroup(
            BuscadorAperturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorAperturaLayout.setVerticalGroup(
            BuscadorAperturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));

        jLabel1.setBackground(new java.awt.Color(0, 153, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Formulario de Aperturas de Caja");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Código:");

        txtCodigo.setEditable(false);
        txtCodigo.setEnabled(false);
        txtCodigo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtCodigo.setPrompt("Identificador...");

        jLabel11.setText("Caja:");

        txtCajaCodigo.setEnabled(false);
        txtCajaCodigo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtCajaCodigo.setPrompt("Identificador...");
        txtCajaCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCajaCodigoActionPerformed(evt);
            }
        });
        txtCajaCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCajaCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCajaCodigoKeyTyped(evt);
            }
        });

        txtCajaDescripcion.setEditable(false);
        txtCajaDescripcion.setEnabled(false);
        txtCajaDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtCajaDescripcion.setPrompt("Descripción o nombre...");

        jLabel12.setText("Fecha:");

        txtFechaHora.setEditable(false);
        txtFechaHora.setEnabled(false);
        txtFechaHora.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtFechaHora.setPrompt("");

        btnNuevo.setBackground(new java.awt.Color(0, 153, 51));
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_plus_34px.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(0, 153, 153));
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_save_34px.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(204, 0, 51));
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_cancel_34px.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel14.setText("Monto Inicial:");

        txtMontoInicial.setEnabled(false);
        txtMontoInicial.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtMontoInicial.setPrompt("");
        txtMontoInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoInicialActionPerformed(evt);
            }
        });
        txtMontoInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoInicialKeyTyped(evt);
            }
        });

        jLabel15.setText("Observacion:");

        txtObservacion.setEnabled(false);
        txtObservacion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtObservacion.setPrompt("");
        txtObservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObservacionActionPerformed(evt);
            }
        });
        txtObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacionKeyTyped(evt);
            }
        });

        jLabel16.setText("Estado:");

        txtEstado.setEditable(false);
        txtEstado.setEnabled(false);
        txtEstado.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtEstado.setPrompt("");

        jLabel17.setText("Usuario:");

        txtUsuario.setEditable(false);
        txtUsuario.setEnabled(false);
        txtUsuario.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtUsuario.setPrompt("");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_search_24px.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCajaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCajaDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnBuscar))
                                    .addComponent(txtFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMontoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtObservacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCajaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCajaDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMontoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCajaCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCajaCodigoActionPerformed
        if (txtCajaCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIPO DE DOCUMENTO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idcaja = Integer.parseInt(txtCajaCodigo.getText());
            c.setIdcaja(idcaja);
            boolean resultado = daoCaja.consultarDatos(c);
            if (resultado == true) {
                txtCajaDescripcion.setText(c.getDescripcion());
                txtMontoInicial.grabFocus();
            } else {
                txtCajaCodigo.setText(null);
                txtCajaDescripcion.setText(null);
                txtCajaCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCajaCodigoActionPerformed

    private void txtCajaCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCajaCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarCaja();
        }
    }//GEN-LAST:event_txtCajaCodigoKeyPressed

    private void txtCajaCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCajaCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCajaCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCajaCodigoKeyTyped

    private void txtCriterioCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioCajaActionPerformed
        cargarCaja();
    }//GEN-LAST:event_txtCriterioCajaActionPerformed

    private void txtCriterioCajaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioCajaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCajaCodigo.setText(null);
            txtCajaDescripcion.setText(null);
            txtCajaCodigo.grabFocus();
            BuscadorCaja.dispose();
        }
    }//GEN-LAST:event_txtCriterioCajaKeyPressed

    private void txtCriterioCajaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioCajaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioCaja.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioCajaKeyTyped

    private void tablaDatosCajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosCajaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosCaja.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioCaja.setText(null);
                BuscadorCaja.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosCajaMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiarCampos();
        operacion = "NUEVO";
        habilitarCampos(operacion);
        generarFechaHora();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int res = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE CONFIRMAR LOS CAMBIOS?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (res != 1) {
            guardar();
            habilitarCampos("GUARDAR");
            limpiarCampos();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        operacion = "CANCELAR";
        habilitarCampos(operacion);
        limpiarCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarApertura();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtCriterioAperturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioAperturaActionPerformed
        cargarApertura();
    }//GEN-LAST:event_txtCriterioAperturaActionPerformed

    private void txtCriterioAperturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioAperturaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            /*txtCajaCodigo.setText(null);
            txtCajaDescripcion.setText(null);
            txtCajaCodigo.grabFocus();*/
            BuscadorApertura.dispose();
        }
    }//GEN-LAST:event_txtCriterioAperturaKeyPressed

    private void txtCriterioAperturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioAperturaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioApertura.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioAperturaKeyTyped

    private void tablaDatosAperturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosAperturaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosApertura.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioApertura.setText(null);
                BuscadorApertura.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosAperturaMouseClicked

    private void txtMontoInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoInicialActionPerformed
        if (txtMontoInicial.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE MONTO INICIAL VACIO", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        } else {
            String number = txtMontoInicial.getText();
            double monto = Double.parseDouble(number);
            if (monto < 0) {
                JOptionPane.showMessageDialog(null, "EL MONTO INICIAL NO PUEDE SER MENOR A 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                valorMonto = monto;
                DecimalFormat formatter = new DecimalFormat("#,###");
                txtMontoInicial.setText(formatter.format(monto));
                txtObservacion.grabFocus();
            }
        }
    }//GEN-LAST:event_txtMontoInicialActionPerformed

    private void txtMontoInicialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoInicialKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (c == ',') {
            getToolkit().beep();
            evt.consume();
        }
        if (txtMontoInicial.getText().length() == 28) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMontoInicialKeyTyped

    private void txtObservacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtObservacion.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtObservacionKeyTyped

    private void txtObservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObservacionActionPerformed
        btnGuardar.grabFocus();
    }//GEN-LAST:event_txtObservacionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorApertura;
    private javax.swing.JDialog BuscadorCaja;
    private javax.swing.JButton btnBuscar;
    private org.jdesktop.swingx.JXButton btnCancelar;
    private org.jdesktop.swingx.JXButton btnGuardar;
    private org.jdesktop.swingx.JXButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.swingx.JXTable tablaDatosApertura;
    private org.jdesktop.swingx.JXTable tablaDatosCaja;
    private org.jdesktop.swingx.JXTextField txtCajaCodigo;
    private org.jdesktop.swingx.JXTextField txtCajaDescripcion;
    private org.jdesktop.swingx.JXTextField txtCodigo;
    private org.jdesktop.swingx.JXTextField txtCriterioApertura;
    private org.jdesktop.swingx.JXTextField txtCriterioCaja;
    private org.jdesktop.swingx.JXTextField txtEstado;
    private org.jdesktop.swingx.JXTextField txtFechaHora;
    private org.jdesktop.swingx.JXTextField txtMontoInicial;
    private org.jdesktop.swingx.JXTextField txtObservacion;
    private org.jdesktop.swingx.JXTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
