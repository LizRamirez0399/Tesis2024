package Vistas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Liz
 */
public final class Configuracion extends javax.swing.JInternalFrame {

    Modelos.ModConfiguracion c = new Modelos.ModConfiguracion();
    Modelos.ModTipoArticulo ta = new Modelos.ModTipoArticulo();
    Modelos.ModTimbrado t = new Modelos.ModTimbrado();

    Operaciones.OpeConfiguracion dao = new Operaciones.OpeConfiguracion();
    Operaciones.OpeTipoArticulo daoTipoArticulo = new Operaciones.OpeTipoArticulo();
    Operaciones.OpeTimbrado daoTimbrado = new Operaciones.OpeTimbrado();

    ArrayList<Object[]> datosTipoArticulo = new ArrayList<>();
    ArrayList<Object[]> datosTimbrado = new ArrayList<>();

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";

    /**
     * Creates new form Configuracion
     */
    public Configuracion() {
        this.setTitle("FrmConfiguracion");
        initComponents();
        obtenerConfiguracion();
    }

    private void obtenerConfiguracion() {
        int idconfiguracion = 1;
        c.setIdconfiguracion(idconfiguracion);
        dao.consultarDatos(c);

        ta.setIdtipo(c.getTipo_articulo_servicio());
        daoTipoArticulo.consultarDatos(ta);
        txtTipoArticuloCodigo.setText("" + ta.getIdtipo());
        txtTipoArticuloDescripcion.setText(ta.getDescripcion());

        txtCantidadMaximaItemFacturacion.setText("" + c.getCantidad_maxima_facturacion());

        t.setIdtimbrado(c.getFacturacion_timbrado());
        daoTimbrado.consultarDatos(t);
        txtTimbradoFacturacion.setText("" + t.getIdtimbrado());
        txtTimbradoFacturacionNumero.setText("" + t.getTimbrado());
        txtTimbradoFacturacionFechaInicial.setText(formato.format(t.getFecha_inicial()));
        txtTimbradoFacturacionFechaFinal.setText(formato.format(t.getFecha_final()));

        t.setIdtimbrado(c.getNota_credito_timbrado());
        daoTimbrado.consultarDatos(t);
        txtTimbradoNotaCredito.setText("" + t.getIdtimbrado());
        txtTimbradoNotaCreditoNumero.setText("" + t.getTimbrado());
        txtTimbradoNotaCreditoFechaInicial.setText(formato.format(t.getFecha_inicial()));
        txtTimbradoNotaCreditoFechaFinal.setText(formato.format(t.getFecha_final()));

        t.setIdtimbrado(c.getNota_debito_timbrado());
        daoTimbrado.consultarDatos(t);
        txtTimbradoNotaDebito.setText("" + t.getIdtimbrado());
        txtTimbradoNotaDebitoNumero.setText("" + t.getTimbrado());
        txtTimbradoNotaDebitoFechaInicial.setText(formato.format(t.getFecha_inicial()));
        txtTimbradoNotaDebitoFechaFinal.setText(formato.format(t.getFecha_final()));
        
        txtTipoArticuloCodigo.grabFocus();
    }

    public void cargarTipoArticulo() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosTipoArticulo.getModel();
        modelo.setRowCount(0);
        datosTipoArticulo = daoTipoArticulo.consultar(txtCriterioTipoArticulo.getText());
        for (Object[] obj : datosTipoArticulo) {
            modelo.addRow(obj);
        }
        this.tablaDatosTipoArticulo.setModel(modelo);
    }

    private void buscarTipoArticulo() {
        cargarTipoArticulo();
        BuscadorTipoArticulo.setModal(true);
        BuscadorTipoArticulo.setSize(540, 285);
        BuscadorTipoArticulo.setLocationRelativeTo(this);
        BuscadorTipoArticulo.setVisible(true);
        int fila = tablaDatosTipoArticulo.getSelectedRow();
        if (fila >= 0) {
            txtTipoArticuloCodigo.setText(tablaDatosTipoArticulo.getValueAt(fila, 0).toString());
            txtTipoArticuloDescripcion.setText(tablaDatosTipoArticulo.getValueAt(fila, 1).toString());
        } else {
            txtTipoArticuloCodigo.setText(null);
            txtTipoArticuloDescripcion.setText(null);
        }
    }

    public void cargarTimbrado() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosTimbrado.getModel();
        modelo.setRowCount(0);
        datosTimbrado = daoTimbrado.consultar(txtCriterioTimbrado.getText());
        for (Object[] obj : datosTimbrado) {
            modelo.addRow(obj);
        }
        this.tablaDatosTimbrado.setModel(modelo);
    }

    private void buscarTimbradoFacturacion() {
        cargarTimbrado();
        BuscadorTimbrado.setModal(true);
        BuscadorTimbrado.setSize(1020, 340);
        BuscadorTimbrado.setLocationRelativeTo(this);
        BuscadorTimbrado.setVisible(true);
        int fila = tablaDatosTimbrado.getSelectedRow();
        if (fila >= 0) {
            txtTimbradoFacturacion.setText(tablaDatosTimbrado.getValueAt(fila, 0).toString());
            txtTimbradoFacturacionNumero.setText(tablaDatosTimbrado.getValueAt(fila, 1).toString());
            txtTimbradoFacturacionFechaInicial.setText(tablaDatosTimbrado.getValueAt(fila, 2).toString());
            txtTimbradoFacturacionFechaFinal.setText(tablaDatosTimbrado.getValueAt(fila, 3).toString());
        } else {
            txtTimbradoFacturacion.setText(null);
            txtTimbradoFacturacionNumero.setText(null);
            txtTimbradoFacturacionFechaInicial.setText(null);
            txtTimbradoFacturacionFechaFinal.setText(null);
        }
    }

    private void buscarTimbradoNotaCredito() {
        cargarTimbrado();
        BuscadorTimbrado.setModal(true);
        BuscadorTimbrado.setSize(1020, 340);
        BuscadorTimbrado.setLocationRelativeTo(this);
        BuscadorTimbrado.setVisible(true);
        int fila = tablaDatosTimbrado.getSelectedRow();
        if (fila >= 0) {
            txtTimbradoNotaCredito.setText(tablaDatosTimbrado.getValueAt(fila, 0).toString());
            txtTimbradoNotaCreditoNumero.setText(tablaDatosTimbrado.getValueAt(fila, 1).toString());
            txtTimbradoNotaCreditoFechaInicial.setText(tablaDatosTimbrado.getValueAt(fila, 2).toString());
            txtTimbradoNotaCreditoFechaFinal.setText(tablaDatosTimbrado.getValueAt(fila, 3).toString());
        } else {
            txtTimbradoNotaCredito.setText(null);
            txtTimbradoNotaCreditoNumero.setText(null);
            txtTimbradoNotaCreditoFechaInicial.setText(null);
            txtTimbradoNotaCreditoFechaFinal.setText(null);
        }
    }

    private void buscarTimbradoNotaDebito() {
        cargarTimbrado();
        BuscadorTimbrado.setModal(true);
        BuscadorTimbrado.setSize(1020, 340);
        BuscadorTimbrado.setLocationRelativeTo(this);
        BuscadorTimbrado.setVisible(true);
        int fila = tablaDatosTimbrado.getSelectedRow();
        if (fila >= 0) {
            txtTimbradoNotaDebito.setText(tablaDatosTimbrado.getValueAt(fila, 0).toString());
            txtTimbradoNotaDebitoNumero.setText(tablaDatosTimbrado.getValueAt(fila, 1).toString());
            txtTimbradoNotaDebitoFechaInicial.setText(tablaDatosTimbrado.getValueAt(fila, 2).toString());
            txtTimbradoNotaDebitoFechaFinal.setText(tablaDatosTimbrado.getValueAt(fila, 3).toString());
        } else {
            txtTimbradoNotaDebito.setText(null);
            txtTimbradoNotaDebitoNumero.setText(null);
            txtTimbradoNotaDebitoFechaInicial.setText(null);
            txtTimbradoNotaDebitoFechaFinal.setText(null);
        }
    }

    private void guardar() {
        String msj = "";
        int idconfiguracion = 1;
        int tipo_articulo_servicio = Integer.parseInt(txtTipoArticuloCodigo.getText());
        int cantidad_maxima_facturacion = Integer.parseInt(txtCantidadMaximaItemFacturacion.getText());
        int facturacion_timbrado = Integer.parseInt(txtTimbradoFacturacion.getText());
        int nota_credito_timbrado = Integer.parseInt(txtTimbradoNotaCredito.getText());
        int nota_debito_timbrado = Integer.parseInt(txtTimbradoNotaDebito.getText());

        if (tipo_articulo_servicio == 0) {
            msj += "NO HA SELECCIONADO UN TIPO DE SERVICIO.\n";
        }
        if (facturacion_timbrado == 0) {
            msj += "NO HA SELECCIONADO UN TIMBRADO PARA FACTURACION.\n";
        }
        if (nota_credito_timbrado == 0) {
            msj += "NO HA SELECCIONADO UN TIMBRADO PARA NOTA DE CREDITO.\n";
        }
        if (nota_debito_timbrado == 0) {
            msj += "NO HA SELECCIONADO UN TIMBRADO PARA NOTA DE DEBITO.\n";
        }
        if (msj.isEmpty()) {
            c.setIdconfiguracion(idconfiguracion);
            c.setTipo_articulo_servicio(tipo_articulo_servicio);
            c.setCantidad_maxima_facturacion(cantidad_maxima_facturacion);
            c.setFacturacion_timbrado(facturacion_timbrado);
            c.setNota_credito_timbrado(nota_credito_timbrado);
            c.setNota_debito_timbrado(nota_debito_timbrado);
            dao.modificar(c);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, msj, "ERRORES", JOptionPane.ERROR_MESSAGE);
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

        BuscadorTipoArticulo = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtCriterioTipoArticulo = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosTipoArticulo = new org.jdesktop.swingx.JXTable();
        BuscadorTimbrado = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtCriterioTimbrado = new org.jdesktop.swingx.JXTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatosTimbrado = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTipoArticuloCodigo = new org.jdesktop.swingx.JXTextField();
        txtTipoArticuloDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCantidadMaximaItemFacturacion = new org.jdesktop.swingx.JXTextField();
        btnGuardar = new org.jdesktop.swingx.JXButton();
        btnCancelar = new org.jdesktop.swingx.JXButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtTimbradoFacturacion = new org.jdesktop.swingx.JXTextField();
        txtTimbradoFacturacionNumero = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTimbradoFacturacionFechaInicial = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTimbradoFacturacionFechaFinal = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTimbradoNotaCredito = new org.jdesktop.swingx.JXTextField();
        txtTimbradoNotaCreditoNumero = new org.jdesktop.swingx.JXTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTimbradoNotaCreditoFechaInicial = new org.jdesktop.swingx.JXTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTimbradoNotaCreditoFechaFinal = new org.jdesktop.swingx.JXTextField();
        jLabel15 = new javax.swing.JLabel();
        txtTimbradoNotaDebito = new org.jdesktop.swingx.JXTextField();
        txtTimbradoNotaDebitoNumero = new org.jdesktop.swingx.JXTextField();
        jLabel16 = new javax.swing.JLabel();
        txtTimbradoNotaDebitoFechaInicial = new org.jdesktop.swingx.JXTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTimbradoNotaDebitoFechaFinal = new org.jdesktop.swingx.JXTextField();

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setBackground(new java.awt.Color(50, 104, 151));
        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("BUSCADOR DE TIPOS DE ARTICULOS");
        jLabel7.setOpaque(true);

        txtCriterioTipoArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioTipoArticulo.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioTipoArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioTipoArticuloActionPerformed(evt);
            }
        });
        txtCriterioTipoArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioTipoArticuloKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioTipoArticuloKeyTyped(evt);
            }
        });

        tablaDatosTipoArticulo.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosTipoArticulo.setInheritsPopupMenu(true);
        tablaDatosTipoArticulo.setShowGrid(true);
        tablaDatosTipoArticulo.getTableHeader().setResizingAllowed(false);
        tablaDatosTipoArticulo.getTableHeader().setReorderingAllowed(false);
        tablaDatosTipoArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosTipoArticuloMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosTipoArticulo);
        if (tablaDatosTipoArticulo.getColumnModel().getColumnCount() > 0) {
            tablaDatosTipoArticulo.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosTipoArticulo.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosTipoArticulo.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioTipoArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioTipoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorTipoArticuloLayout = new javax.swing.GroupLayout(BuscadorTipoArticulo.getContentPane());
        BuscadorTipoArticulo.getContentPane().setLayout(BuscadorTipoArticuloLayout);
        BuscadorTipoArticuloLayout.setHorizontalGroup(
            BuscadorTipoArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorTipoArticuloLayout.setVerticalGroup(
            BuscadorTipoArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(50, 104, 151));
        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("BUSCADOR DE TIMBRADOS");
        jLabel9.setOpaque(true);

        txtCriterioTimbrado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioTimbrado.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioTimbrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioTimbradoActionPerformed(evt);
            }
        });
        txtCriterioTimbrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioTimbradoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioTimbradoKeyTyped(evt);
            }
        });

        tablaDatosTimbrado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "identificador", "Timbrado", "Inicio", "Fin", "Desde", "Hasta", "Establecimiento", "P. Emision", "CodigoTipo", "Tipo"
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
        tablaDatosTimbrado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosTimbradoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDatosTimbrado);
        if (tablaDatosTimbrado.getColumnModel().getColumnCount() > 0) {
            tablaDatosTimbrado.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(0).setMaxWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(2).setMinWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(2).setPreferredWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(2).setMaxWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(3).setMinWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(3).setPreferredWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(3).setMaxWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(4).setMinWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(4).setPreferredWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(4).setMaxWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(5).setMinWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(5).setPreferredWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(5).setMaxWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(6).setMinWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(6).setPreferredWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(6).setMaxWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(7).setMinWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(7).setPreferredWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(7).setMaxWidth(100);
            tablaDatosTimbrado.getColumnModel().getColumn(8).setMinWidth(0);
            tablaDatosTimbrado.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablaDatosTimbrado.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioTimbrado, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorTimbradoLayout = new javax.swing.GroupLayout(BuscadorTimbrado.getContentPane());
        BuscadorTimbrado.getContentPane().setLayout(BuscadorTimbradoLayout);
        BuscadorTimbradoLayout.setHorizontalGroup(
            BuscadorTimbradoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorTimbradoLayout.setVerticalGroup(
            BuscadorTimbradoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));

        jLabel1.setBackground(new java.awt.Color(0, 153, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Formulario de Configuración");
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuracion central..."));

        jLabel5.setText("Tipo Articulo Servicio:");

        txtTipoArticuloCodigo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTipoArticuloCodigo.setPrompt("Identificador...");
        txtTipoArticuloCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoArticuloCodigoActionPerformed(evt);
            }
        });
        txtTipoArticuloCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTipoArticuloCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTipoArticuloCodigoKeyTyped(evt);
            }
        });

        txtTipoArticuloDescripcion.setEditable(false);
        txtTipoArticuloDescripcion.setEnabled(false);
        txtTipoArticuloDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTipoArticuloDescripcion.setPrompt("Descripción o nombre...");

        jLabel6.setText("Facturacion Maxima de Items:");

        txtCantidadMaximaItemFacturacion.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantidadMaximaItemFacturacion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtCantidadMaximaItemFacturacion.setPrompt("...0");
        txtCantidadMaximaItemFacturacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadMaximaItemFacturacionActionPerformed(evt);
            }
        });
        txtCantidadMaximaItemFacturacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadMaximaItemFacturacionKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTipoArticuloCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTipoArticuloDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCantidadMaximaItemFacturacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipoArticuloCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipoArticuloDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidadMaximaItemFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGuardar.setBackground(new java.awt.Color(0, 153, 153));
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_save_34px.png"))); // NOI18N
        btnGuardar.setText("Guardar");
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
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuracion de timbrados..."));

        jLabel8.setText("Facturacion:");

        txtTimbradoFacturacion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoFacturacion.setPrompt("Identificador...");
        txtTimbradoFacturacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimbradoFacturacionActionPerformed(evt);
            }
        });
        txtTimbradoFacturacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimbradoFacturacionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimbradoFacturacionKeyTyped(evt);
            }
        });

        txtTimbradoFacturacionNumero.setEditable(false);
        txtTimbradoFacturacionNumero.setEnabled(false);
        txtTimbradoFacturacionNumero.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoFacturacionNumero.setPrompt("Descripción o nombre...");

        jLabel11.setText("Inicio:");

        txtTimbradoFacturacionFechaInicial.setEditable(false);
        txtTimbradoFacturacionFechaInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimbradoFacturacionFechaInicial.setEnabled(false);
        txtTimbradoFacturacionFechaInicial.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoFacturacionFechaInicial.setPrompt("../../....");

        jLabel12.setText("Fin:");

        txtTimbradoFacturacionFechaFinal.setEditable(false);
        txtTimbradoFacturacionFechaFinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimbradoFacturacionFechaFinal.setEnabled(false);
        txtTimbradoFacturacionFechaFinal.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoFacturacionFechaFinal.setPrompt("../../....");

        jLabel10.setText("Nota de Crédito:");

        txtTimbradoNotaCredito.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoNotaCredito.setPrompt("Identificador...");
        txtTimbradoNotaCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimbradoNotaCreditoActionPerformed(evt);
            }
        });
        txtTimbradoNotaCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimbradoNotaCreditoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimbradoNotaCreditoKeyTyped(evt);
            }
        });

        txtTimbradoNotaCreditoNumero.setEditable(false);
        txtTimbradoNotaCreditoNumero.setEnabled(false);
        txtTimbradoNotaCreditoNumero.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoNotaCreditoNumero.setPrompt("Descripción o nombre...");

        jLabel13.setText("Inicio:");

        txtTimbradoNotaCreditoFechaInicial.setEditable(false);
        txtTimbradoNotaCreditoFechaInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimbradoNotaCreditoFechaInicial.setEnabled(false);
        txtTimbradoNotaCreditoFechaInicial.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoNotaCreditoFechaInicial.setPrompt("../../....");

        jLabel14.setText("Fin:");

        txtTimbradoNotaCreditoFechaFinal.setEditable(false);
        txtTimbradoNotaCreditoFechaFinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimbradoNotaCreditoFechaFinal.setEnabled(false);
        txtTimbradoNotaCreditoFechaFinal.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoNotaCreditoFechaFinal.setPrompt("../../....");

        jLabel15.setText("Nota de Débito:");

        txtTimbradoNotaDebito.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoNotaDebito.setPrompt("Identificador...");
        txtTimbradoNotaDebito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimbradoNotaDebitoActionPerformed(evt);
            }
        });
        txtTimbradoNotaDebito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimbradoNotaDebitoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimbradoNotaDebitoKeyTyped(evt);
            }
        });

        txtTimbradoNotaDebitoNumero.setEditable(false);
        txtTimbradoNotaDebitoNumero.setEnabled(false);
        txtTimbradoNotaDebitoNumero.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoNotaDebitoNumero.setPrompt("Descripción o nombre...");

        jLabel16.setText("Inicio:");

        txtTimbradoNotaDebitoFechaInicial.setEditable(false);
        txtTimbradoNotaDebitoFechaInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimbradoNotaDebitoFechaInicial.setEnabled(false);
        txtTimbradoNotaDebitoFechaInicial.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoNotaDebitoFechaInicial.setPrompt("../../....");

        jLabel17.setText("Fin:");

        txtTimbradoNotaDebitoFechaFinal.setEditable(false);
        txtTimbradoNotaDebitoFechaFinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimbradoNotaDebitoFechaFinal.setEnabled(false);
        txtTimbradoNotaDebitoFechaFinal.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTimbradoNotaDebitoFechaFinal.setPrompt("../../....");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimbradoFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimbradoFacturacionNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimbradoFacturacionFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimbradoFacturacionFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimbradoNotaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimbradoNotaCreditoNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimbradoNotaCreditoFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimbradoNotaCreditoFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimbradoNotaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimbradoNotaDebitoNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimbradoNotaDebitoFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimbradoNotaDebitoFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbradoFacturacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbradoFacturacionNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbradoFacturacionFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbradoFacturacionFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbradoNotaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbradoNotaCreditoNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbradoNotaCreditoFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbradoNotaCreditoFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimbradoNotaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTimbradoNotaDebitoNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTimbradoNotaDebitoFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTimbradoNotaDebitoFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txtTipoArticuloCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoArticuloCodigoActionPerformed
        if (txtTipoArticuloCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIPO DE ARTICULO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipo = Integer.parseInt(txtTipoArticuloCodigo.getText());
            ta.setIdtipo(idtipo);
            boolean resultado = daoTipoArticulo.consultarDatos(ta);
            if (resultado == true) {
                txtTipoArticuloDescripcion.setText(ta.getDescripcion());
                txtCantidadMaximaItemFacturacion.grabFocus();
            } else {
                txtTipoArticuloCodigo.setText(null);
                txtTipoArticuloDescripcion.setText(null);
                txtTipoArticuloCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtTipoArticuloCodigoActionPerformed

    private void txtTipoArticuloCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoArticuloCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTipoArticulo();
        }
    }//GEN-LAST:event_txtTipoArticuloCodigoKeyPressed

    private void txtTipoArticuloCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoArticuloCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtTipoArticuloCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTipoArticuloCodigoKeyTyped

    private void txtCriterioTipoArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioTipoArticuloActionPerformed
        cargarTipoArticulo();
    }//GEN-LAST:event_txtCriterioTipoArticuloActionPerformed

    private void txtCriterioTipoArticuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTipoArticuloKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtTipoArticuloCodigo.setText(null);
            txtTipoArticuloDescripcion.setText(null);
            txtTipoArticuloCodigo.grabFocus();
            BuscadorTipoArticulo.dispose();
        }
    }//GEN-LAST:event_txtCriterioTipoArticuloKeyPressed

    private void txtCriterioTipoArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTipoArticuloKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioTipoArticulo.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioTipoArticuloKeyTyped

    private void tablaDatosTipoArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosTipoArticuloMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosTipoArticulo.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioTipoArticulo.setText(null);
                BuscadorTipoArticulo.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosTipoArticuloMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int res = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE CONFIRMAR LOS CAMBIOS?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (res != 1) {
            guardar();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        obtenerConfiguracion();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtCantidadMaximaItemFacturacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadMaximaItemFacturacionActionPerformed
        if (txtCantidadMaximaItemFacturacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtTimbradoFacturacion.grabFocus();
        }
    }//GEN-LAST:event_txtCantidadMaximaItemFacturacionActionPerformed

    private void txtCantidadMaximaItemFacturacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadMaximaItemFacturacionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCantidadMaximaItemFacturacion.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadMaximaItemFacturacionKeyTyped

    private void txtTimbradoFacturacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimbradoFacturacionActionPerformed
        if (txtTimbradoFacturacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIMBRADO DE FACTURACION VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtimbrado = Integer.parseInt(txtTimbradoFacturacion.getText());
            t.setIdtimbrado(idtimbrado);
            boolean resultado = daoTimbrado.consultarDatos(t);
            if (resultado == true) {
                txtTimbradoFacturacionNumero.setText("" + t.getTimbrado());
                txtTimbradoFacturacionFechaInicial.setText(formato.format(t.getFecha_inicial()));
                txtTimbradoFacturacionFechaFinal.setText(formato.format(t.getFecha_final()));
                txtTimbradoNotaCredito.grabFocus();
            } else {
                txtTimbradoFacturacion.setText(null);
                txtTimbradoFacturacionNumero.setText(null);
                txtTimbradoFacturacionFechaInicial.setText(null);
                txtTimbradoFacturacionFechaFinal.setText(null);
                txtTimbradoFacturacion.grabFocus();
            }
        }
    }//GEN-LAST:event_txtTimbradoFacturacionActionPerformed

    private void txtTimbradoFacturacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimbradoFacturacionKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTimbradoFacturacion();
        }
    }//GEN-LAST:event_txtTimbradoFacturacionKeyPressed

    private void txtTimbradoFacturacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimbradoFacturacionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtTimbradoFacturacion.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTimbradoFacturacionKeyTyped

    private void txtCriterioTimbradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioTimbradoActionPerformed
        cargarTimbrado();
    }//GEN-LAST:event_txtCriterioTimbradoActionPerformed

    private void txtCriterioTimbradoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTimbradoKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            BuscadorTimbrado.dispose();
        }
    }//GEN-LAST:event_txtCriterioTimbradoKeyPressed

    private void txtCriterioTimbradoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTimbradoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioTimbrado.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioTimbradoKeyTyped

    private void tablaDatosTimbradoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosTimbradoMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosTimbrado.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioTimbrado.setText(null);
                BuscadorTimbrado.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosTimbradoMouseClicked

    private void txtTimbradoNotaCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimbradoNotaCreditoActionPerformed
        if (txtTimbradoNotaCredito.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIMBRADO DE NOTA DE CREDITO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtimbrado = Integer.parseInt(txtTimbradoNotaCredito.getText());
            t.setIdtimbrado(idtimbrado);
            boolean resultado = daoTimbrado.consultarDatos(t);
            if (resultado == true) {
                txtTimbradoNotaCreditoNumero.setText("" + t.getTimbrado());
                txtTimbradoNotaCreditoFechaInicial.setText(formato.format(t.getFecha_inicial()));
                txtTimbradoNotaCreditoFechaFinal.setText(formato.format(t.getFecha_final()));
                txtTimbradoNotaDebito.grabFocus();
            } else {
                txtTimbradoNotaCredito.setText(null);
                txtTimbradoNotaCreditoNumero.setText(null);
                txtTimbradoNotaCreditoFechaInicial.setText(null);
                txtTimbradoNotaCreditoFechaFinal.setText(null);
                txtTimbradoNotaCredito.grabFocus();
            }
        }
    }//GEN-LAST:event_txtTimbradoNotaCreditoActionPerformed

    private void txtTimbradoNotaCreditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimbradoNotaCreditoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTimbradoNotaCredito();
        }
    }//GEN-LAST:event_txtTimbradoNotaCreditoKeyPressed

    private void txtTimbradoNotaCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimbradoNotaCreditoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtTimbradoNotaCredito.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTimbradoNotaCreditoKeyTyped

    private void txtTimbradoNotaDebitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimbradoNotaDebitoActionPerformed
        if (txtTimbradoNotaDebito.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIMBRADO DE NOTA DE DEBITO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtimbrado = Integer.parseInt(txtTimbradoNotaDebito.getText());
            t.setIdtimbrado(idtimbrado);
            boolean resultado = daoTimbrado.consultarDatos(t);
            if (resultado == true) {
                txtTimbradoNotaDebitoNumero.setText("" + t.getTimbrado());
                txtTimbradoNotaDebitoFechaInicial.setText(formato.format(t.getFecha_inicial()));
                txtTimbradoNotaDebitoFechaFinal.setText(formato.format(t.getFecha_final()));
                btnGuardar.grabFocus();
            } else {
                txtTimbradoNotaDebito.setText(null);
                txtTimbradoNotaDebito.setText(null);
                txtTimbradoNotaDebitoFechaInicial.setText(null);
                txtTimbradoNotaDebitoFechaFinal.setText(null);
                txtTimbradoNotaDebito.grabFocus();
            }
        }
    }//GEN-LAST:event_txtTimbradoNotaDebitoActionPerformed

    private void txtTimbradoNotaDebitoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimbradoNotaDebitoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTimbradoNotaDebito();
        }
    }//GEN-LAST:event_txtTimbradoNotaDebitoKeyPressed

    private void txtTimbradoNotaDebitoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimbradoNotaDebitoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtTimbradoNotaDebito.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTimbradoNotaDebitoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorTimbrado;
    private javax.swing.JDialog BuscadorTipoArticulo;
    private org.jdesktop.swingx.JXButton btnCancelar;
    private org.jdesktop.swingx.JXButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaDatosTimbrado;
    private org.jdesktop.swingx.JXTable tablaDatosTipoArticulo;
    private org.jdesktop.swingx.JXTextField txtCantidadMaximaItemFacturacion;
    private org.jdesktop.swingx.JXTextField txtCriterioTimbrado;
    private org.jdesktop.swingx.JXTextField txtCriterioTipoArticulo;
    private org.jdesktop.swingx.JXTextField txtTimbradoFacturacion;
    private org.jdesktop.swingx.JXTextField txtTimbradoFacturacionFechaFinal;
    private org.jdesktop.swingx.JXTextField txtTimbradoFacturacionFechaInicial;
    private org.jdesktop.swingx.JXTextField txtTimbradoFacturacionNumero;
    private org.jdesktop.swingx.JXTextField txtTimbradoNotaCredito;
    private org.jdesktop.swingx.JXTextField txtTimbradoNotaCreditoFechaFinal;
    private org.jdesktop.swingx.JXTextField txtTimbradoNotaCreditoFechaInicial;
    private org.jdesktop.swingx.JXTextField txtTimbradoNotaCreditoNumero;
    private org.jdesktop.swingx.JXTextField txtTimbradoNotaDebito;
    private org.jdesktop.swingx.JXTextField txtTimbradoNotaDebitoFechaFinal;
    private org.jdesktop.swingx.JXTextField txtTimbradoNotaDebitoFechaInicial;
    private org.jdesktop.swingx.JXTextField txtTimbradoNotaDebitoNumero;
    private org.jdesktop.swingx.JXTextField txtTipoArticuloCodigo;
    private org.jdesktop.swingx.JXTextField txtTipoArticuloDescripcion;
    // End of variables declaration//GEN-END:variables
}
