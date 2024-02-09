package Vistas;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Liz
 */
public final class Numeracion extends javax.swing.JInternalFrame {

    Modelos.ModNumeracion n = new Modelos.ModNumeracion();

    Operaciones.OpeNumeracion dao = new Operaciones.OpeNumeracion();

    ArrayList<Object[]> datos = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";

    String tres_ceros = String.format("%%0%dd", 3);
    String siete_ceros = String.format("%%0%dd", 7);

    /**
     * Creates new form Numeracion
     */
    public Numeracion() {
        this.setTitle("FrmNumeracion");
        initComponents();
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
                txtFacEstablecimiento.setEnabled(true);
                txtNotCredEstablecimiento.setEnabled(true);
                txtNotDebEstablecimiento.setEnabled(true);
                txtFacEstablecimiento.setEnabled(true);
                txtNotCredEstablecimiento.setEnabled(true);
                txtNotDebEstablecimiento.setEnabled(true);
                txtFacPuntoEmision.setEnabled(true);
                txtNotCredPuntoEmision.setEnabled(true);
                txtNotDebPuntoEmision.setEnabled(true);
                txtFacNumero.setEnabled(true);
                txtNotCredNumero.setEnabled(true);
                txtNotDebNumero.setEnabled(true);
                txtOrdenTrabajoEstablecimiento.setEnabled(true);
                txtOrdenTrabajoPuntoEmision.setEnabled(true);
                txtOrdenTrabajoNumero.setEnabled(true);

                txtUltNroFac.setEnabled(false);
                txtUltNroNotCred.setEnabled(false);
                txtUltNroNotDeb.setEnabled(false);
                txtUltNroOrdTra.setEnabled(false);

                //BOTONES
                btnNuevo.setEnabled(false);
                btnGuardar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtFacEstablecimiento.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtFacEstablecimiento.setEnabled(true);
                txtNotCredEstablecimiento.setEnabled(true);
                txtNotDebEstablecimiento.setEnabled(true);
                txtFacEstablecimiento.setEnabled(true);
                txtNotCredEstablecimiento.setEnabled(true);
                txtNotDebEstablecimiento.setEnabled(true);
                txtFacPuntoEmision.setEnabled(true);
                txtNotCredPuntoEmision.setEnabled(true);
                txtNotDebPuntoEmision.setEnabled(true);
                txtFacNumero.setEnabled(true);
                txtNotCredNumero.setEnabled(true);
                txtNotDebNumero.setEnabled(true);
                txtOrdenTrabajoEstablecimiento.setEnabled(true);
                txtOrdenTrabajoPuntoEmision.setEnabled(true);
                txtOrdenTrabajoNumero.setEnabled(true);

                txtUltNroFac.setEnabled(false);
                txtUltNroNotCred.setEnabled(false);
                txtUltNroNotDeb.setEnabled(false);
                txtUltNroOrdTra.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnGuardar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECCIONAMOS
                pestanha.setSelectedIndex(1);
                txtFacEstablecimiento.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtFacEstablecimiento.setEnabled(false);
                txtNotCredEstablecimiento.setEnabled(false);
                txtNotDebEstablecimiento.setEnabled(false);
                txtFacEstablecimiento.setEnabled(false);
                txtNotCredEstablecimiento.setEnabled(false);
                txtNotDebEstablecimiento.setEnabled(false);
                txtFacPuntoEmision.setEnabled(false);
                txtNotCredPuntoEmision.setEnabled(false);
                txtNotDebPuntoEmision.setEnabled(false);
                txtFacNumero.setEnabled(false);
                txtNotCredNumero.setEnabled(false);
                txtNotDebNumero.setEnabled(false);
                txtOrdenTrabajoEstablecimiento.setEnabled(false);
                txtOrdenTrabajoPuntoEmision.setEnabled(false);
                txtOrdenTrabajoNumero.setEnabled(false);

                txtUltNroFac.setEnabled(false);
                txtUltNroNotCred.setEnabled(false);
                txtUltNroNotDeb.setEnabled(false);
                txtUltNroOrdTra.setEnabled(false);
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
                txtFacEstablecimiento.setEnabled(false);
                txtNotCredEstablecimiento.setEnabled(false);
                txtNotDebEstablecimiento.setEnabled(false);
                txtFacEstablecimiento.setEnabled(false);
                txtNotCredEstablecimiento.setEnabled(false);
                txtNotDebEstablecimiento.setEnabled(false);
                txtFacPuntoEmision.setEnabled(false);
                txtNotCredPuntoEmision.setEnabled(false);
                txtNotDebPuntoEmision.setEnabled(false);
                txtFacNumero.setEnabled(false);
                txtNotCredNumero.setEnabled(false);
                txtNotDebNumero.setEnabled(false);
                txtOrdenTrabajoEstablecimiento.setEnabled(false);
                txtOrdenTrabajoPuntoEmision.setEnabled(false);
                txtOrdenTrabajoNumero.setEnabled(false);

                txtUltNroFac.setEnabled(false);
                txtUltNroNotCred.setEnabled(false);
                txtUltNroNotDeb.setEnabled(false);
                txtUltNroOrdTra.setEnabled(false);
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
                txtFacEstablecimiento.setEnabled(false);
                txtNotCredEstablecimiento.setEnabled(false);
                txtNotDebEstablecimiento.setEnabled(false);
                txtFacEstablecimiento.setEnabled(false);
                txtNotCredEstablecimiento.setEnabled(false);
                txtNotDebEstablecimiento.setEnabled(false);
                txtFacPuntoEmision.setEnabled(false);
                txtNotCredPuntoEmision.setEnabled(false);
                txtNotDebPuntoEmision.setEnabled(false);
                txtFacNumero.setEnabled(false);
                txtNotCredNumero.setEnabled(false);
                txtNotDebNumero.setEnabled(false);
                txtOrdenTrabajoEstablecimiento.setEnabled(false);
                txtOrdenTrabajoPuntoEmision.setEnabled(false);
                txtOrdenTrabajoNumero.setEnabled(false);

                txtUltNroFac.setEnabled(false);
                txtUltNroNotCred.setEnabled(false);
                txtUltNroNotDeb.setEnabled(false);
                txtUltNroOrdTra.setEnabled(false);
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
        txtFacEstablecimiento.setText(null);
        txtFacNumero.setText(null);
        txtFacPuntoEmision.setText(null);
        txtUltNroFac.setText(null);
        txtNotCredEstablecimiento.setText(null);
        txtNotCredNumero.setText(null);
        txtNotCredPuntoEmision.setText(null);
        txtUltNroNotCred.setText(null);
        txtNotDebEstablecimiento.setText(null);
        txtNotDebNumero.setText(null);
        txtNotDebPuntoEmision.setText(null);
        txtOrdenTrabajoEstablecimiento.setText(null);
        txtOrdenTrabajoPuntoEmision.setText(null);
        txtOrdenTrabajoNumero.setText(null);
        txtUltNroOrdTra.setText(null);

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
        String ult_nro_fac = txtUltNroFac.getText();
        String ult_nro_not_cred = txtUltNroNotCred.getText();
        String ult_nro_not_deb = txtUltNroNotDeb.getText();
        String ult_nro_ord_tra = txtUltNroOrdTra.getText();
        switch (accion) {
            case "NUEVO":
                if (ult_nro_fac.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO NUMERO DE FACTURA VACIO.\n";
                }
                if (ult_nro_not_cred.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO NUMERO DE NOTA DE CREDITO VACIO.\n";
                }
                if (ult_nro_not_deb.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO NUMERO DE NOTA DE DEBITO VACIO.\n";
                }
                if (ult_nro_ord_tra.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO NUMERO DE ORDEN DE TRABAJO VACIO.\n";
                }
                if (error.isEmpty()) {
                    n.setIdnumeracion(id);
                    n.setUlt_nro_fac(ult_nro_fac);
                    n.setUlt_nro_not_cred(ult_nro_not_cred);
                    n.setUlt_nro_not_deb(ult_nro_not_deb);
                    n.setUlt_nro_ord_tra(ult_nro_ord_tra);
                    boolean resultado = dao.agregar(n);
                    if (resultado == true) {
                        JOptionPane.showMessageDialog(null, "REGISTRO EXITOSO", "EXITO", JOptionPane.INFORMATION_MESSAGE);
                    }
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (ult_nro_fac.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO NUMERO DE FACTURA VACIO.\n";
                }
                if (ult_nro_not_cred.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO NUMERO DE NOTA DE CREDITO VACIO.\n";
                }
                if (ult_nro_not_deb.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO NUMERO DE NOTA DE DEBITO VACIO.\n";
                }
                if (ult_nro_ord_tra.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO NUMERO DE ORDEN DE TRABAJO VACIO.\n";
                }
                if (error.isEmpty()) {
                    n.setUlt_nro_fac(ult_nro_fac);
                    n.setUlt_nro_not_cred(ult_nro_not_cred);
                    n.setUlt_nro_not_deb(ult_nro_not_deb);
                    n.setUlt_nro_ord_tra(ult_nro_ord_tra);
                    n.setIdnumeracion(id);
                    boolean resultado = dao.modificar(n);
                    if (resultado == true) {
                        JOptionPane.showMessageDialog(null, "REGISTRO EXITOSO", "EXITO", JOptionPane.INFORMATION_MESSAGE);
                    }
                    cargarTabla();
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    n.setIdnumeracion(id);
                    dao.eliminar(n);
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
            n.setIdnumeracion(id);
            dao.consultarDatos(n);
            txtCodigo.setText("" + n.getIdnumeracion());

            txtFacEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt((n.getUlt_nro_fac().replace("-", "")).substring(0, 3))));
            txtFacPuntoEmision.setText(String.format(tres_ceros, Integer.parseInt((n.getUlt_nro_fac().replace("-", "")).substring(3, 6))));
            txtFacNumero.setText(String.format(siete_ceros, Integer.parseInt((n.getUlt_nro_fac().replace("-", "")).substring(6, 13))));
            txtUltNroFac.setText(n.getUlt_nro_fac());

            txtNotCredEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt((n.getUlt_nro_not_cred().replace("-", "")).substring(0, 3))));
            txtNotCredPuntoEmision.setText(String.format(tres_ceros, Integer.parseInt((n.getUlt_nro_not_cred().replace("-", "")).substring(3, 6))));
            txtNotCredNumero.setText(String.format(siete_ceros, Integer.parseInt((n.getUlt_nro_not_cred().replace("-", "")).substring(6, 13))));
            txtUltNroNotCred.setText(n.getUlt_nro_not_cred());

            txtNotDebEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt((n.getUlt_nro_not_deb().replace("-", "")).substring(0, 3))));
            txtNotDebPuntoEmision.setText(String.format(tres_ceros, Integer.parseInt((n.getUlt_nro_not_deb().replace("-", "")).substring(3, 6))));
            txtNotDebNumero.setText(String.format(siete_ceros, Integer.parseInt((n.getUlt_nro_not_deb().replace("-", "")).substring(6, 13))));
            txtUltNroNotDeb.setText(n.getUlt_nro_not_deb());
            
            txtOrdenTrabajoEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt((n.getUlt_nro_ord_tra().replace("-", "")).substring(0, 3))));
            txtOrdenTrabajoPuntoEmision.setText(String.format(tres_ceros, Integer.parseInt((n.getUlt_nro_ord_tra().replace("-", "")).substring(3, 6))));
            txtOrdenTrabajoNumero.setText(String.format(siete_ceros, Integer.parseInt((n.getUlt_nro_ord_tra().replace("-", "")).substring(6, 13))));
            txtUltNroOrdTra.setText(n.getUlt_nro_ord_tra());

            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFacEstablecimiento = new org.jdesktop.swingx.JXTextField();
        txtFacPuntoEmision = new org.jdesktop.swingx.JXTextField();
        txtFacNumero = new org.jdesktop.swingx.JXTextField();
        txtNotCredEstablecimiento = new org.jdesktop.swingx.JXTextField();
        txtNotCredPuntoEmision = new org.jdesktop.swingx.JXTextField();
        txtNotCredNumero = new org.jdesktop.swingx.JXTextField();
        txtNotDebEstablecimiento = new org.jdesktop.swingx.JXTextField();
        txtNotDebPuntoEmision = new org.jdesktop.swingx.JXTextField();
        txtNotDebNumero = new org.jdesktop.swingx.JXTextField();
        txtUltNroFac = new org.jdesktop.swingx.JXTextField();
        txtUltNroNotCred = new org.jdesktop.swingx.JXTextField();
        txtUltNroNotDeb = new org.jdesktop.swingx.JXTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOrdenTrabajoEstablecimiento = new org.jdesktop.swingx.JXTextField();
        txtOrdenTrabajoPuntoEmision = new org.jdesktop.swingx.JXTextField();
        txtOrdenTrabajoNumero = new org.jdesktop.swingx.JXTextField();
        txtUltNroOrdTra = new org.jdesktop.swingx.JXTextField();

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

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));

        jLabel1.setBackground(new java.awt.Color(0, 153, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Formulario de Numeraciones");
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
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Identificador", "Ult. Fac.", "Ult. Not. Cred.", "Ult. Not. Deb.", "Ult. Ord. Tra."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
                        .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)))
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pestanha.addTab("Lista de registros", panelLista);

        panelOperaciones.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Código:");

        jLabel4.setText("Ult. N° Fac.:");

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

        jLabel5.setText("Ult. N° Not. Cred.:");

        jLabel6.setText("Ult. N° Not. Deb.:");

        txtFacEstablecimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFacEstablecimiento.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtFacEstablecimiento.setPrompt("000");
        txtFacEstablecimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFacEstablecimientoActionPerformed(evt);
            }
        });
        txtFacEstablecimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFacEstablecimientoKeyTyped(evt);
            }
        });

        txtFacPuntoEmision.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFacPuntoEmision.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtFacPuntoEmision.setPrompt("000");
        txtFacPuntoEmision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFacPuntoEmisionActionPerformed(evt);
            }
        });
        txtFacPuntoEmision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFacPuntoEmisionKeyTyped(evt);
            }
        });

        txtFacNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFacNumero.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtFacNumero.setPrompt("0000000");
        txtFacNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFacNumeroActionPerformed(evt);
            }
        });
        txtFacNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFacNumeroKeyTyped(evt);
            }
        });

        txtNotCredEstablecimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNotCredEstablecimiento.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtNotCredEstablecimiento.setPrompt("000");
        txtNotCredEstablecimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotCredEstablecimientoActionPerformed(evt);
            }
        });
        txtNotCredEstablecimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNotCredEstablecimientoKeyTyped(evt);
            }
        });

        txtNotCredPuntoEmision.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNotCredPuntoEmision.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtNotCredPuntoEmision.setPrompt("000");
        txtNotCredPuntoEmision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotCredPuntoEmisionActionPerformed(evt);
            }
        });
        txtNotCredPuntoEmision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNotCredPuntoEmisionKeyTyped(evt);
            }
        });

        txtNotCredNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNotCredNumero.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtNotCredNumero.setPrompt("0000000");
        txtNotCredNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotCredNumeroActionPerformed(evt);
            }
        });
        txtNotCredNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNotCredNumeroKeyTyped(evt);
            }
        });

        txtNotDebEstablecimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNotDebEstablecimiento.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtNotDebEstablecimiento.setPrompt("000");
        txtNotDebEstablecimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotDebEstablecimientoActionPerformed(evt);
            }
        });
        txtNotDebEstablecimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNotDebEstablecimientoKeyTyped(evt);
            }
        });

        txtNotDebPuntoEmision.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNotDebPuntoEmision.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtNotDebPuntoEmision.setPrompt("000");
        txtNotDebPuntoEmision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotDebPuntoEmisionActionPerformed(evt);
            }
        });
        txtNotDebPuntoEmision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNotDebPuntoEmisionKeyTyped(evt);
            }
        });

        txtNotDebNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNotDebNumero.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtNotDebNumero.setPrompt("0000000");
        txtNotDebNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotDebNumeroActionPerformed(evt);
            }
        });
        txtNotDebNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNotDebNumeroKeyTyped(evt);
            }
        });

        txtUltNroFac.setEditable(false);
        txtUltNroFac.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltNroFac.setEnabled(false);
        txtUltNroFac.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtUltNroFac.setPrompt("000-000-0000000");
        txtUltNroFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUltNroFacActionPerformed(evt);
            }
        });
        txtUltNroFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUltNroFacKeyTyped(evt);
            }
        });

        txtUltNroNotCred.setEditable(false);
        txtUltNroNotCred.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltNroNotCred.setEnabled(false);
        txtUltNroNotCred.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtUltNroNotCred.setPrompt("000-000-0000000");
        txtUltNroNotCred.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUltNroNotCredActionPerformed(evt);
            }
        });
        txtUltNroNotCred.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUltNroNotCredKeyTyped(evt);
            }
        });

        txtUltNroNotDeb.setEditable(false);
        txtUltNroNotDeb.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltNroNotDeb.setEnabled(false);
        txtUltNroNotDeb.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtUltNroNotDeb.setPrompt("000-000-0000000");
        txtUltNroNotDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUltNroNotDebActionPerformed(evt);
            }
        });
        txtUltNroNotDeb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUltNroNotDebKeyTyped(evt);
            }
        });

        jLabel7.setText("Ult. N° Ord. Tra.:");

        txtOrdenTrabajoEstablecimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOrdenTrabajoEstablecimiento.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtOrdenTrabajoEstablecimiento.setPrompt("000");
        txtOrdenTrabajoEstablecimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrdenTrabajoEstablecimientoActionPerformed(evt);
            }
        });
        txtOrdenTrabajoEstablecimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOrdenTrabajoEstablecimientoKeyTyped(evt);
            }
        });

        txtOrdenTrabajoPuntoEmision.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOrdenTrabajoPuntoEmision.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtOrdenTrabajoPuntoEmision.setPrompt("000");
        txtOrdenTrabajoPuntoEmision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrdenTrabajoPuntoEmisionActionPerformed(evt);
            }
        });
        txtOrdenTrabajoPuntoEmision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOrdenTrabajoPuntoEmisionKeyTyped(evt);
            }
        });

        txtOrdenTrabajoNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOrdenTrabajoNumero.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtOrdenTrabajoNumero.setPrompt("0000000");
        txtOrdenTrabajoNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrdenTrabajoNumeroActionPerformed(evt);
            }
        });
        txtOrdenTrabajoNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOrdenTrabajoNumeroKeyTyped(evt);
            }
        });

        txtUltNroOrdTra.setEditable(false);
        txtUltNroOrdTra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltNroOrdTra.setEnabled(false);
        txtUltNroOrdTra.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtUltNroOrdTra.setPrompt("000-000-0000000");
        txtUltNroOrdTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUltNroOrdTraActionPerformed(evt);
            }
        });
        txtUltNroOrdTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUltNroOrdTraKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelOperacionesLayout = new javax.swing.GroupLayout(panelOperaciones);
        panelOperaciones.setLayout(panelOperacionesLayout);
        panelOperacionesLayout.setHorizontalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOperacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOperacionesLayout.createSequentialGroup()
                        .addGap(0, 148, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOperacionesLayout.createSequentialGroup()
                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelOperacionesLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNotDebEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNotDebPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNotDebNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelOperacionesLayout.createSequentialGroup()
                                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                                        .addComponent(txtFacEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFacPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFacNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                                        .addComponent(txtNotCredEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNotCredPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNotCredNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtUltNroFac, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtUltNroNotCred, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtUltNroNotDeb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOrdenTrabajoEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOrdenTrabajoPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOrdenTrabajoNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUltNroOrdTra, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(txtFacEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFacPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFacNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUltNroFac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNotCredEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNotCredPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNotCredNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUltNroNotCred, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNotDebEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNotDebPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNotDebNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUltNroNotDeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtOrdenTrabajoEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtOrdenTrabajoPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtOrdenTrabajoNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUltNroOrdTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
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
                .addComponent(pestanha, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txtFacEstablecimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFacEstablecimientoActionPerformed
        if (txtFacEstablecimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtFacEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt(txtFacEstablecimiento.getText())));
            txtFacPuntoEmision.grabFocus();
        }
    }//GEN-LAST:event_txtFacEstablecimientoActionPerformed

    private void txtFacEstablecimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFacEstablecimientoKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtFacEstablecimiento.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtFacEstablecimientoKeyTyped

    private void txtFacPuntoEmisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFacPuntoEmisionActionPerformed
        if (txtFacPuntoEmision.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtFacPuntoEmision.setText(String.format(tres_ceros, Integer.parseInt(txtFacPuntoEmision.getText())));
            txtFacNumero.grabFocus();
        }
    }//GEN-LAST:event_txtFacPuntoEmisionActionPerformed

    private void txtFacPuntoEmisionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFacPuntoEmisionKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtFacPuntoEmision.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtFacPuntoEmisionKeyTyped

    private void txtFacNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFacNumeroActionPerformed
        if (txtFacNumero.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtFacNumero.setText(String.format(siete_ceros, Integer.parseInt(txtFacNumero.getText())));
            txtUltNroFac.setText(txtFacEstablecimiento.getText() + "-" + txtFacPuntoEmision.getText() + "-" + txtFacNumero.getText());
            txtNotCredEstablecimiento.grabFocus();
        }
    }//GEN-LAST:event_txtFacNumeroActionPerformed

    private void txtFacNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFacNumeroKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtFacNumero.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtFacNumeroKeyTyped

    private void txtNotCredEstablecimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotCredEstablecimientoActionPerformed
        if (txtNotCredEstablecimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNotCredEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt(txtNotCredEstablecimiento.getText())));
            txtNotCredPuntoEmision.grabFocus();
        }
    }//GEN-LAST:event_txtNotCredEstablecimientoActionPerformed

    private void txtNotCredEstablecimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotCredEstablecimientoKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNotCredEstablecimiento.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNotCredEstablecimientoKeyTyped

    private void txtNotCredPuntoEmisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotCredPuntoEmisionActionPerformed
        if (txtNotCredPuntoEmision.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNotCredPuntoEmision.setText(String.format(tres_ceros, Integer.parseInt(txtNotCredPuntoEmision.getText())));
            txtNotCredNumero.grabFocus();
        }
    }//GEN-LAST:event_txtNotCredPuntoEmisionActionPerformed

    private void txtNotCredPuntoEmisionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotCredPuntoEmisionKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNotDebPuntoEmision.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNotCredPuntoEmisionKeyTyped

    private void txtNotCredNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotCredNumeroActionPerformed
        if (txtNotCredNumero.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNotCredNumero.setText(String.format(siete_ceros, Integer.parseInt(txtNotCredNumero.getText())));
            txtUltNroNotCred.setText(txtNotCredEstablecimiento.getText() + "-" + txtNotCredPuntoEmision.getText() + "-" + txtNotCredNumero.getText());
            txtNotDebEstablecimiento.grabFocus();
        }
    }//GEN-LAST:event_txtNotCredNumeroActionPerformed

    private void txtNotCredNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotCredNumeroKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNotCredNumero.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNotCredNumeroKeyTyped

    private void txtNotDebEstablecimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotDebEstablecimientoActionPerformed
        if (txtNotDebEstablecimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNotDebEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt(txtNotDebEstablecimiento.getText())));
            txtNotDebPuntoEmision.grabFocus();
        }
    }//GEN-LAST:event_txtNotDebEstablecimientoActionPerformed

    private void txtNotDebEstablecimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotDebEstablecimientoKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNotDebEstablecimiento.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNotDebEstablecimientoKeyTyped

    private void txtNotDebPuntoEmisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotDebPuntoEmisionActionPerformed
        if (txtNotDebPuntoEmision.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNotDebPuntoEmision.setText(String.format(tres_ceros, Integer.parseInt(txtNotDebPuntoEmision.getText())));
            txtNotDebNumero.grabFocus();
        }
    }//GEN-LAST:event_txtNotDebPuntoEmisionActionPerformed

    private void txtNotDebPuntoEmisionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotDebPuntoEmisionKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNotDebPuntoEmision.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNotDebPuntoEmisionKeyTyped

    private void txtNotDebNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotDebNumeroActionPerformed
        if (txtNotDebNumero.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNotDebNumero.setText(String.format(siete_ceros, Integer.parseInt(txtNotDebNumero.getText())));
            txtUltNroNotDeb.setText(txtNotDebEstablecimiento.getText() + "-" + txtNotDebPuntoEmision.getText() + "-" + txtNotDebNumero.getText());
            txtOrdenTrabajoEstablecimiento.grabFocus();
        }
    }//GEN-LAST:event_txtNotDebNumeroActionPerformed

    private void txtNotDebNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNotDebNumeroKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNotDebNumero.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNotDebNumeroKeyTyped

    private void txtUltNroFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUltNroFacActionPerformed

    }//GEN-LAST:event_txtUltNroFacActionPerformed

    private void txtUltNroFacKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUltNroFacKeyTyped

    }//GEN-LAST:event_txtUltNroFacKeyTyped

    private void txtUltNroNotCredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUltNroNotCredActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltNroNotCredActionPerformed

    private void txtUltNroNotCredKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUltNroNotCredKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltNroNotCredKeyTyped

    private void txtUltNroNotDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUltNroNotDebActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltNroNotDebActionPerformed

    private void txtUltNroNotDebKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUltNroNotDebKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltNroNotDebKeyTyped

    private void txtOrdenTrabajoEstablecimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrdenTrabajoEstablecimientoActionPerformed
        if (txtOrdenTrabajoEstablecimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtOrdenTrabajoEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt(txtOrdenTrabajoEstablecimiento.getText())));
            txtOrdenTrabajoPuntoEmision.grabFocus();
        }
    }//GEN-LAST:event_txtOrdenTrabajoEstablecimientoActionPerformed

    private void txtOrdenTrabajoEstablecimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOrdenTrabajoEstablecimientoKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtOrdenTrabajoEstablecimiento.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtOrdenTrabajoEstablecimientoKeyTyped

    private void txtOrdenTrabajoPuntoEmisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrdenTrabajoPuntoEmisionActionPerformed
        if (txtOrdenTrabajoPuntoEmision.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtOrdenTrabajoPuntoEmision.setText(String.format(tres_ceros, Integer.parseInt(txtOrdenTrabajoPuntoEmision.getText())));
            txtOrdenTrabajoNumero.grabFocus();
        }
    }//GEN-LAST:event_txtOrdenTrabajoPuntoEmisionActionPerformed

    private void txtOrdenTrabajoPuntoEmisionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOrdenTrabajoPuntoEmisionKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtOrdenTrabajoPuntoEmision.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtOrdenTrabajoPuntoEmisionKeyTyped

    private void txtOrdenTrabajoNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrdenTrabajoNumeroActionPerformed
        if (txtOrdenTrabajoNumero.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtOrdenTrabajoNumero.setText(String.format(siete_ceros, Integer.parseInt(txtOrdenTrabajoNumero.getText())));
            txtUltNroOrdTra.setText(txtOrdenTrabajoEstablecimiento.getText() + "-" + txtOrdenTrabajoPuntoEmision.getText() + "-" + txtOrdenTrabajoNumero.getText());
            btnGuardar.grabFocus();
        }
    }//GEN-LAST:event_txtOrdenTrabajoNumeroActionPerformed

    private void txtOrdenTrabajoNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOrdenTrabajoNumeroKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtOrdenTrabajoNumero.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtOrdenTrabajoNumeroKeyTyped

    private void txtUltNroOrdTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUltNroOrdTraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltNroOrdTraActionPerformed

    private void txtUltNroOrdTraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUltNroOrdTraKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltNroOrdTraKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Modificar;
    private org.jdesktop.swingx.JXButton btnCancelar;
    private org.jdesktop.swingx.JXButton btnGuardar;
    private org.jdesktop.swingx.JXButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu menuDesplegable;
    private javax.swing.JPanel panelLista;
    private javax.swing.JPanel panelOperaciones;
    private javax.swing.JTabbedPane pestanha;
    private org.jdesktop.swingx.JXTable tablaDatos;
    private org.jdesktop.swingx.JXTextField txtCodigo;
    private org.jdesktop.swingx.JXTextField txtCriterio;
    private org.jdesktop.swingx.JXTextField txtFacEstablecimiento;
    private org.jdesktop.swingx.JXTextField txtFacNumero;
    private org.jdesktop.swingx.JXTextField txtFacPuntoEmision;
    private org.jdesktop.swingx.JXTextField txtNotCredEstablecimiento;
    private org.jdesktop.swingx.JXTextField txtNotCredNumero;
    private org.jdesktop.swingx.JXTextField txtNotCredPuntoEmision;
    private org.jdesktop.swingx.JXTextField txtNotDebEstablecimiento;
    private org.jdesktop.swingx.JXTextField txtNotDebNumero;
    private org.jdesktop.swingx.JXTextField txtNotDebPuntoEmision;
    private org.jdesktop.swingx.JXTextField txtOrdenTrabajoEstablecimiento;
    private org.jdesktop.swingx.JXTextField txtOrdenTrabajoNumero;
    private org.jdesktop.swingx.JXTextField txtOrdenTrabajoPuntoEmision;
    private org.jdesktop.swingx.JXTextField txtUltNroFac;
    private org.jdesktop.swingx.JXTextField txtUltNroNotCred;
    private org.jdesktop.swingx.JXTextField txtUltNroNotDeb;
    private org.jdesktop.swingx.JXTextField txtUltNroOrdTra;
    // End of variables declaration//GEN-END:variables
}
