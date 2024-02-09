package Vistas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Liz
 */
public final class Timbrado extends javax.swing.JInternalFrame {

    Modelos.ModTimbrado t = new Modelos.ModTimbrado();
    Modelos.ModTipoDocumento td = new Modelos.ModTipoDocumento();

    Operaciones.OpeTimbrado dao = new Operaciones.OpeTimbrado();
    Operaciones.OpeTipoDocumento daoTipoDocumento = new Operaciones.OpeTipoDocumento();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosTipoDocumento = new ArrayList<>();

    String siete_ceros = String.format("%%0%dd", 7);
    String tres_ceros = String.format("%%0%dd", 3);
    SimpleDateFormat formato_fecha = new SimpleDateFormat("dd/MM/yyyy");

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";

    /**
     * Creates new form Linea
     */
    public Timbrado() {
        this.setTitle("FrmLinea");
        initComponents();
        txtFechaInicial.setFormats(formato_fecha);
        txtFechaFinal.setFormats(formato_fecha);
        cargarTabla();
    }

    public void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        datos = dao.consultar(txtCriterio.getText());
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        this.tablaDatos.setModel(modelo);
    }

    public void habilitarCampos(String accion) {
        switch (accion) {
            case "NUEVO":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtTimbrado.setEnabled(true);
                txtFechaInicial.setEnabled(true);
                txtFechaFinal.setEnabled(true);
                txtNumeroDesde.setEnabled(true);
                txtNumeroHasta.setEnabled(true);
                txtEstablecimiento.setEnabled(true);
                txtPuntoEmision.setEnabled(true);
                txtTipoDocumentoCodigo.setEnabled(true);
                txtTipoDocumentoDescripcion.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnGuardar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtTimbrado.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtTimbrado.setEnabled(true);
                txtFechaInicial.setEnabled(true);
                txtFechaFinal.setEnabled(true);
                txtNumeroDesde.setEnabled(true);
                txtNumeroHasta.setEnabled(true);
                txtEstablecimiento.setEnabled(true);
                txtPuntoEmision.setEnabled(true);
                txtTipoDocumentoCodigo.setEnabled(true);
                txtTipoDocumentoDescripcion.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnGuardar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECCIONAMOS
                pestanha.setSelectedIndex(1);
                txtTimbrado.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtTimbrado.setEnabled(false);
                txtFechaInicial.setEnabled(false);
                txtFechaFinal.setEnabled(false);
                txtNumeroDesde.setEnabled(false);
                txtNumeroHasta.setEnabled(false);
                txtEstablecimiento.setEnabled(false);
                txtPuntoEmision.setEnabled(false);
                txtTipoDocumentoCodigo.setEnabled(false);
                txtTipoDocumentoDescripcion.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnGuardar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECCIONAMOS
                pestanha.setSelectedIndex(1);
                btnGuardar.grabFocus();
                break;
            case "GUARDAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtTimbrado.setEnabled(false);
                txtFechaInicial.setEnabled(false);
                txtFechaFinal.setEnabled(false);
                txtNumeroDesde.setEnabled(false);
                txtNumeroHasta.setEnabled(false);
                txtEstablecimiento.setEnabled(false);
                txtPuntoEmision.setEnabled(false);
                txtTipoDocumentoCodigo.setEnabled(false);
                txtTipoDocumentoDescripcion.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(true);
                btnGuardar.setEnabled(false);
                btnCancelar.setEnabled(false);
                //REDIRECIONAMOS
                btnNuevo.grabFocus();
                break;
            case "CANCELAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtTimbrado.setEnabled(false);
                txtFechaInicial.setEnabled(false);
                txtFechaFinal.setEnabled(false);
                txtNumeroDesde.setEnabled(false);
                txtNumeroHasta.setEnabled(false);
                txtEstablecimiento.setEnabled(false);
                txtPuntoEmision.setEnabled(false);
                txtTipoDocumentoCodigo.setEnabled(false);
                txtTipoDocumentoDescripcion.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(true);
                btnGuardar.setEnabled(false);
                btnCancelar.setEnabled(false);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(0);
                txtCriterio.grabFocus();
                break;
            default:
                JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR EN LA HABILITACIÓN DE LOS CAMPOS. AVISE AL ADMINISTRADOR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCampos() {
        //FILTROS DE BUSQUEDAS
        txtCriterio.setText(null);
        //CAMPOS
        txtCodigo.setText(null);
        txtTimbrado.setText(null);
        txtFechaInicial.setDate(null);
        txtFechaFinal.setDate(null);
        txtNumeroDesde.setText(null);
        txtNumeroHasta.setText(null);
        txtEstablecimiento.setText(null);
        txtPuntoEmision.setText(null);
        txtTipoDocumentoCodigo.setText(null);
        txtTipoDocumentoDescripcion.setText(null);
        //OPERACIONES
        operacion = null;
    }

    public void guardar(String accion) {
        String error = "";
        int id = 0;
        if (accion.equals("NUEVO")) {
            id = dao.nuevoID();
        } else {
            id = Integer.parseInt(txtCodigo.getText());
        }
        int timbrado = Integer.parseInt(txtTimbrado.getText());
        Date fechaInicial = txtFechaInicial.getDate();
        java.sql.Date fecha_inicial_SQL = new java.sql.Date(fechaInicial.getTime());
        Date fechaFinal = txtFechaFinal.getDate();
        java.sql.Date fecha_final_SQL = new java.sql.Date(fechaFinal.getTime());

        int numero_desde = Integer.parseInt(txtNumeroDesde.getText());
        int numero_hasta = Integer.parseInt(txtNumeroHasta.getText());
        int establecimiento = Integer.parseInt(txtEstablecimiento.getText());
        int punto_emision = Integer.parseInt(txtPuntoEmision.getText());
        int idtipo = Integer.parseInt(txtTipoDocumentoCodigo.getText());
        switch (accion) {
            case "NUEVO":
                if (timbrado == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIMBRADO VACIO.\n";
                }
                if (fechaInicial == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA INICIAL VACIO.\n";
                }
                if (fechaFinal == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA FINAL VACIO.\n";
                }
                if (numero_desde == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE N° DESDE VACIO.\n";
                }
                if (numero_hasta == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE N° HASTA VACIO.\n";
                }
                if (establecimiento == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ESTABLECIMIENTO VACIO.\n";
                }
                if (punto_emision == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE PUNTO DE EMISION VACIO.\n";
                }
                if (idtipo == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIPO DE DOCUMENTO VACIO.\n";
                }
                if (error.isEmpty()) {
                    t.setIdtimbrado(id);
                    t.setTimbrado(timbrado);
                    t.setFecha_inicial(fecha_inicial_SQL);
                    t.setFecha_final(fecha_final_SQL);
                    t.setNumero_desde(numero_desde);
                    t.setNumero_hasta(numero_hasta);
                    t.setEstablecimiento(establecimiento);
                    t.setPunto_emision(punto_emision);
                    t.setIdtipo(idtipo);
                    dao.agregar(t);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (timbrado == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIMBRADO VACIO.\n";
                }
                if (fechaInicial == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA INICIAL VACIO.\n";
                }
                if (fechaFinal == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA FINAL VACIO.\n";
                }
                if (numero_desde == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE N° DESDE VACIO.\n";
                }
                if (numero_hasta == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE N° HASTA VACIO.\n";
                }
                if (establecimiento == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ESTABLECIMIENTO VACIO.\n";
                }
                if (punto_emision == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE PUNTO DE EMISION VACIO.\n";
                }
                if (idtipo == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIPO DE DOCUMENTO VACIO.\n";
                }
                if (error.isEmpty()) {
                    t.setTimbrado(timbrado);
                    t.setFecha_inicial(fecha_inicial_SQL);
                    t.setFecha_final(fecha_final_SQL);
                    t.setNumero_desde(numero_desde);
                    t.setNumero_hasta(numero_hasta);
                    t.setEstablecimiento(establecimiento);
                    t.setPunto_emision(punto_emision);
                    t.setIdtipo(idtipo);
                    t.setIdtimbrado(id);
                    dao.modificar(t);
                    cargarTabla();
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    t.setIdtimbrado(id);
                    dao.eliminar(t);
                    cargarTabla();
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR EN LA OPERACION PARA LA BASE DE DATOS. AVISE AL ADMINISTRADOR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void recuperarDatos() {
        int fila = tablaDatos.getSelectedRow();

        if (fila >= 0) {
            int id = Integer.parseInt(tablaDatos.getValueAt(fila, 0).toString());
            t.setIdtimbrado(id);
            dao.consultarDatos(t);

            txtCodigo.setText("" + t.getIdtimbrado());
            txtTimbrado.setText("" + t.getTimbrado());
            txtFechaInicial.setDate(t.getFecha_inicial());
            txtFechaFinal.setDate(t.getFecha_final());
            txtNumeroDesde.setText(String.format(siete_ceros, t.getNumero_desde()));
            txtNumeroHasta.setText(String.format(siete_ceros, t.getNumero_hasta()));
            txtEstablecimiento.setText(String.format(tres_ceros, t.getEstablecimiento()));
            txtPuntoEmision.setText(String.format(tres_ceros, t.getPunto_emision()));

            td.setIdtipo(t.getIdtipo());
            daoTipoDocumento.consultarDatos(td);
            txtTipoDocumentoCodigo.setText("" + td.getIdtipo());
            txtTipoDocumentoDescripcion.setText(td.getDescripcion());

            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarTipoDocumento() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosTipoDocumento.getModel();
        modelo.setRowCount(0);
        datosTipoDocumento = daoTipoDocumento.consultar(txtCriterioTipoDocumento.getText());
        for (Object[] obj : datosTipoDocumento) {
            modelo.addRow(obj);
        }
        this.tablaDatosTipoDocumento.setModel(modelo);
    }

    private void buscarTipoDocumento() {
        cargarTipoDocumento();
        BuscadorTipoDocumento.setModal(true);
        BuscadorTipoDocumento.setSize(540, 285);
        BuscadorTipoDocumento.setLocationRelativeTo(this);
        BuscadorTipoDocumento.setVisible(true);
        int fila = tablaDatosTipoDocumento.getSelectedRow();
        if (fila >= 0) {
            txtTipoDocumentoCodigo.setText(tablaDatosTipoDocumento.getValueAt(fila, 0).toString());
            txtTipoDocumentoDescripcion.setText(tablaDatosTipoDocumento.getValueAt(fila, 1).toString());
        } else {
            txtTipoDocumentoCodigo.setText(null);
            txtTipoDocumentoDescripcion.setText(null);
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

        menuDesplegable = new javax.swing.JPopupMenu();
        Modificar = new javax.swing.JMenuItem();
        Eliminar = new javax.swing.JMenuItem();
        BuscadorTipoDocumento = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtCriterioTipoDocumento = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosTipoDocumento = new org.jdesktop.swingx.JXTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pestanha = new javax.swing.JTabbedPane();
        panelLista = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCriterio = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatos = new org.jdesktop.swingx.JXTable();
        panelOperaciones = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnCancelar = new org.jdesktop.swingx.JXButton();
        btnGuardar = new org.jdesktop.swingx.JXButton();
        btnNuevo = new org.jdesktop.swingx.JXButton();
        txtCodigo = new org.jdesktop.swingx.JXTextField();
        txtTimbrado = new org.jdesktop.swingx.JXTextField();
        jLabel5 = new javax.swing.JLabel();
        txtFechaInicial = new org.jdesktop.swingx.JXDatePicker();
        jLabel6 = new javax.swing.JLabel();
        txtFechaFinal = new org.jdesktop.swingx.JXDatePicker();
        jLabel7 = new javax.swing.JLabel();
        txtNumeroDesde = new org.jdesktop.swingx.JXTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNumeroHasta = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        txtEstablecimiento = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPuntoEmision = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTipoDocumentoCodigo = new org.jdesktop.swingx.JXTextField();
        txtTipoDocumentoDescripcion = new org.jdesktop.swingx.JXTextField();

        Modificar.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_edit_file_20px.png"))); // NOI18N
        Modificar.setText("Modificar");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });
        menuDesplegable.add(Modificar);

        Eliminar.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_delete_file_20px_1.png"))); // NOI18N
        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });
        menuDesplegable.add(Eliminar);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setBackground(new java.awt.Color(50, 104, 151));
        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("BUSCADOR DE TIPOS DE DOCUMENTOS");
        jLabel12.setOpaque(true);

        txtCriterioTipoDocumento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioTipoDocumento.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioTipoDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioTipoDocumentoActionPerformed(evt);
            }
        });
        txtCriterioTipoDocumento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioTipoDocumentoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioTipoDocumentoKeyTyped(evt);
            }
        });

        tablaDatosTipoDocumento.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosTipoDocumento.setInheritsPopupMenu(true);
        tablaDatosTipoDocumento.setShowGrid(true);
        tablaDatosTipoDocumento.getTableHeader().setResizingAllowed(false);
        tablaDatosTipoDocumento.getTableHeader().setReorderingAllowed(false);
        tablaDatosTipoDocumento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosTipoDocumentoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosTipoDocumento);
        if (tablaDatosTipoDocumento.getColumnModel().getColumnCount() > 0) {
            tablaDatosTipoDocumento.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosTipoDocumento.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosTipoDocumento.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioTipoDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorTipoDocumentoLayout = new javax.swing.GroupLayout(BuscadorTipoDocumento.getContentPane());
        BuscadorTipoDocumento.getContentPane().setLayout(BuscadorTipoDocumentoLayout);
        BuscadorTipoDocumentoLayout.setHorizontalGroup(
            BuscadorTipoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorTipoDocumentoLayout.setVerticalGroup(
            BuscadorTipoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));

        jLabel1.setBackground(new java.awt.Color(0, 153, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Formulario de Timbrados");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        pestanha.setBackground(new java.awt.Color(255, 255, 255));
        pestanha.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        panelLista.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Buscar:");

        txtCriterio.setPrompt("Ingrese los filtros de la busqueda...");
        txtCriterio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioActionPerformed(evt);
            }
        });
        txtCriterio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioKeyTyped(evt);
            }
        });

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Identificador", "Timbrado", "F. Inicial", "F. Final", "N° Desde", "N° Hasta", "Establecimiento", "P. Emision", "CodigoTipoDocumento", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatos.setComponentPopupMenu(menuDesplegable);
        tablaDatos.setInheritsPopupMenu(true);
        tablaDatos.setShowGrid(true);
        tablaDatos.getTableHeader().setResizingAllowed(false);
        tablaDatos.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tablaDatos);
        if (tablaDatos.getColumnModel().getColumnCount() > 0) {
            tablaDatos.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatos.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatos.getColumnModel().getColumn(0).setMaxWidth(100);
            tablaDatos.getColumnModel().getColumn(8).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        javax.swing.GroupLayout panelListaLayout = new javax.swing.GroupLayout(panelLista);
        panelLista.setLayout(panelListaLayout);
        panelListaLayout.setHorizontalGroup(
            panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListaLayout.createSequentialGroup()
                .addGroup(panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(panelListaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelListaLayout.setVerticalGroup(
            panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pestanha.addTab("Lista de registros", panelLista);

        panelOperaciones.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Código:");

        jLabel4.setText("N° Timbrado:");

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

        txtCodigo.setEditable(false);
        txtCodigo.setEnabled(false);
        txtCodigo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtCodigo.setPrompt("Identificador...");

        txtTimbrado.setEnabled(false);
        txtTimbrado.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbrado.setPrompt("Nombre o descripción...");
        txtTimbrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimbradoActionPerformed(evt);
            }
        });
        txtTimbrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimbradoKeyTyped(evt);
            }
        });

        jLabel5.setText("Fecha Inicial:");

        txtFechaInicial.setEnabled(false);
        txtFechaInicial.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtFechaInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaInicialActionPerformed(evt);
            }
        });
        txtFechaInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFechaInicialKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaInicialKeyTyped(evt);
            }
        });

        jLabel6.setText("Fecha Final:");

        txtFechaFinal.setEnabled(false);
        txtFechaFinal.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtFechaFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaFinalActionPerformed(evt);
            }
        });
        txtFechaFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFechaFinalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaFinalKeyTyped(evt);
            }
        });

        jLabel7.setText("N° Desde:");

        txtNumeroDesde.setEnabled(false);
        txtNumeroDesde.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtNumeroDesde.setPrompt("");
        txtNumeroDesde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroDesdeActionPerformed(evt);
            }
        });
        txtNumeroDesde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroDesdeKeyTyped(evt);
            }
        });

        jLabel8.setText("N° Hasta:");

        txtNumeroHasta.setEnabled(false);
        txtNumeroHasta.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtNumeroHasta.setPrompt("");
        txtNumeroHasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroHastaActionPerformed(evt);
            }
        });
        txtNumeroHasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroHastaKeyTyped(evt);
            }
        });

        jLabel9.setText("Establecimiento:");

        txtEstablecimiento.setEnabled(false);
        txtEstablecimiento.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtEstablecimiento.setPrompt("");
        txtEstablecimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstablecimientoActionPerformed(evt);
            }
        });
        txtEstablecimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstablecimientoKeyTyped(evt);
            }
        });

        jLabel10.setText("Punto de Emision:");

        txtPuntoEmision.setEnabled(false);
        txtPuntoEmision.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtPuntoEmision.setPrompt("");
        txtPuntoEmision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPuntoEmisionActionPerformed(evt);
            }
        });
        txtPuntoEmision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuntoEmisionKeyTyped(evt);
            }
        });

        jLabel11.setText("Tipo Documento:");

        txtTipoDocumentoCodigo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTipoDocumentoCodigo.setPrompt("Identificador...");
        txtTipoDocumentoCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoDocumentoCodigoActionPerformed(evt);
            }
        });
        txtTipoDocumentoCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTipoDocumentoCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTipoDocumentoCodigoKeyTyped(evt);
            }
        });

        txtTipoDocumentoDescripcion.setEditable(false);
        txtTipoDocumentoDescripcion.setEnabled(false);
        txtTipoDocumentoDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTipoDocumentoDescripcion.setPrompt("Descripción o nombre...");

        javax.swing.GroupLayout panelOperacionesLayout = new javax.swing.GroupLayout(panelOperaciones);
        panelOperaciones.setLayout(panelOperacionesLayout);
        panelOperacionesLayout.setHorizontalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOperacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOperacionesLayout.createSequentialGroup()
                        .addGap(0, 497, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOperacionesLayout.createSequentialGroup()
                                .addComponent(txtTimbrado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelOperacionesLayout.createSequentialGroup()
                                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelOperacionesLayout.createSequentialGroup()
                                                .addComponent(txtNumeroDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(panelOperacionesLayout.createSequentialGroup()
                                                .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel10)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNumeroHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelOperacionesLayout.createSequentialGroup()
                                .addComponent(txtTipoDocumentoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTipoDocumentoDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        panelOperacionesLayout.setVerticalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOperacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipoDocumentoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipoDocumentoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pestanha.addTab("Operaciones", panelOperaciones);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pestanha)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pestanha)
                .addContainerGap())
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
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCriterioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioActionPerformed
        cargarTabla();
    }//GEN-LAST:event_txtCriterioActionPerformed

    private void txtCriterioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterio.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioKeyTyped

    private void txtTimbradoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimbradoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtTimbrado.getText().length() >= 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTimbradoKeyTyped

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiarCampos();
        operacion = "NUEVO";
        habilitarCampos(operacion);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        operacion = "MODIFICAR";
        recuperarDatos();
    }//GEN-LAST:event_ModificarActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        operacion = "ELIMINAR";
        recuperarDatos();
    }//GEN-LAST:event_EliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        operacion = "CANCELAR";
        habilitarCampos(operacion);
        limpiarCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int res = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE CONFIRMAR LOS CAMBIOS?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (res != 1) {
            guardar(operacion);
            habilitarCampos("GUARDAR");
            limpiarCampos();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtTimbradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimbradoActionPerformed
        if (txtTimbrado.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtFechaInicial.grabFocus();
        }
    }//GEN-LAST:event_txtTimbradoActionPerformed

    private void txtFechaInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaInicialActionPerformed
        if (txtFechaInicial.getDate() == null) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE FECHA INICIAL VACIA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtFechaFinal.grabFocus();
        }
    }//GEN-LAST:event_txtFechaInicialActionPerformed

    private void txtFechaInicialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaInicialKeyPressed

    }//GEN-LAST:event_txtFechaInicialKeyPressed

    private void txtFechaInicialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaInicialKeyTyped

    }//GEN-LAST:event_txtFechaInicialKeyTyped

    private void txtFechaFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaFinalActionPerformed
        if (txtFechaFinal.getDate() == null) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE FECHA FINAL VACIA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNumeroDesde.grabFocus();
        }
    }//GEN-LAST:event_txtFechaFinalActionPerformed

    private void txtFechaFinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaFinalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaFinalKeyPressed

    private void txtFechaFinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaFinalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaFinalKeyTyped

    private void txtNumeroDesdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroDesdeActionPerformed
        if (txtNumeroDesde.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNumeroDesde.setText(String.format(siete_ceros, Integer.parseInt(txtNumeroDesde.getText())));
            txtNumeroHasta.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroDesdeActionPerformed

    private void txtNumeroDesdeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroDesdeKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumeroDesde.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroDesdeKeyTyped

    private void txtNumeroHastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroHastaActionPerformed
        if (txtNumeroHasta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNumeroHasta.setText(String.format(siete_ceros, Integer.parseInt(txtNumeroHasta.getText())));
            txtEstablecimiento.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroHastaActionPerformed

    private void txtNumeroHastaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroHastaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumeroHasta.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroHastaKeyTyped

    private void txtEstablecimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstablecimientoActionPerformed
        if (txtEstablecimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt(txtEstablecimiento.getText())));
            txtPuntoEmision.grabFocus();
        }
    }//GEN-LAST:event_txtEstablecimientoActionPerformed

    private void txtEstablecimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstablecimientoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtEstablecimiento.getText().length() == 4) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEstablecimientoKeyTyped

    private void txtPuntoEmisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPuntoEmisionActionPerformed
        if (txtPuntoEmision.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtPuntoEmision.setText(String.format(tres_ceros, Integer.parseInt(txtPuntoEmision.getText())));
            txtTipoDocumentoCodigo.grabFocus();
        }
    }//GEN-LAST:event_txtPuntoEmisionActionPerformed

    private void txtPuntoEmisionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPuntoEmisionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtPuntoEmision.getText().length() == 4) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPuntoEmisionKeyTyped

    private void txtTipoDocumentoCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoDocumentoCodigoActionPerformed
        if (txtTipoDocumentoCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIPO DE DOCUMENTO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipo = Integer.parseInt(txtTipoDocumentoCodigo.getText());
            td.setIdtipo(idtipo);
            boolean resultado = daoTipoDocumento.consultarDatos(td);
            if (resultado == true) {
                txtTipoDocumentoDescripcion.setText(td.getDescripcion());
                btnGuardar.grabFocus();
            } else {
                txtTipoDocumentoCodigo.setText(null);
                txtTipoDocumentoDescripcion.setText(null);
                txtTipoDocumentoCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtTipoDocumentoCodigoActionPerformed

    private void txtTipoDocumentoCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoDocumentoCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTipoDocumento();
        }
    }//GEN-LAST:event_txtTipoDocumentoCodigoKeyPressed

    private void txtTipoDocumentoCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoDocumentoCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtTipoDocumentoCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTipoDocumentoCodigoKeyTyped

    private void txtCriterioTipoDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioTipoDocumentoActionPerformed
        cargarTipoDocumento();
    }//GEN-LAST:event_txtCriterioTipoDocumentoActionPerformed

    private void txtCriterioTipoDocumentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTipoDocumentoKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtTipoDocumentoCodigo.setText(null);
            txtTipoDocumentoDescripcion.setText(null);
            txtTipoDocumentoCodigo.grabFocus();
            BuscadorTipoDocumento.dispose();
        }
    }//GEN-LAST:event_txtCriterioTipoDocumentoKeyPressed

    private void txtCriterioTipoDocumentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTipoDocumentoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioTipoDocumento.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioTipoDocumentoKeyTyped

    private void tablaDatosTipoDocumentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosTipoDocumentoMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosTipoDocumento.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioTipoDocumento.setText(null);
                BuscadorTipoDocumento.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosTipoDocumentoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorTipoDocumento;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Modificar;
    private org.jdesktop.swingx.JXButton btnCancelar;
    private org.jdesktop.swingx.JXButton btnGuardar;
    private org.jdesktop.swingx.JXButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu menuDesplegable;
    private javax.swing.JPanel panelLista;
    private javax.swing.JPanel panelOperaciones;
    private javax.swing.JTabbedPane pestanha;
    private org.jdesktop.swingx.JXTable tablaDatos;
    private org.jdesktop.swingx.JXTable tablaDatosTipoDocumento;
    private org.jdesktop.swingx.JXTextField txtCodigo;
    private org.jdesktop.swingx.JXTextField txtCriterio;
    private org.jdesktop.swingx.JXTextField txtCriterioTipoDocumento;
    private org.jdesktop.swingx.JXTextField txtEstablecimiento;
    private org.jdesktop.swingx.JXDatePicker txtFechaFinal;
    private org.jdesktop.swingx.JXDatePicker txtFechaInicial;
    private org.jdesktop.swingx.JXTextField txtNumeroDesde;
    private org.jdesktop.swingx.JXTextField txtNumeroHasta;
    private org.jdesktop.swingx.JXTextField txtPuntoEmision;
    private org.jdesktop.swingx.JXTextField txtTimbrado;
    private org.jdesktop.swingx.JXTextField txtTipoDocumentoCodigo;
    private org.jdesktop.swingx.JXTextField txtTipoDocumentoDescripcion;
    // End of variables declaration//GEN-END:variables
}
