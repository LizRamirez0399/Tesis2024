package Vistas;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Liz
 */
public final class TipoArticulo extends javax.swing.JInternalFrame {

    Modelos.ModTipoArticulo C = new Modelos.ModTipoArticulo();
    Operaciones.OpeTipoArticulo dao = new Operaciones.OpeTipoArticulo();
    ArrayList<Object[]> datos = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";

    /**
     * Creates new form Linea
     */
    public TipoArticulo() {
        this.setTitle("FrmTipoArticulo");
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
                txtDescripcion.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnGuardar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtDescripcion.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtDescripcion.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnGuardar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECCIONAMOS
                pestanha.setSelectedIndex(1);
                txtDescripcion.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtDescripcion.setEnabled(false);
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
                txtDescripcion.setEnabled(false);
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
                txtDescripcion.setEnabled(false);
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
        txtDescripcion.setText(null);
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
        String descripcion = txtDescripcion.getText();
        switch (accion) {
            case "NUEVO":
                if (descripcion.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE DESCRIPCIÓN VACIO.\n";
                }
                if (error.isEmpty()) {
                    C.setIdtipo(id);
                    C.setDescripcion(descripcion);
                    dao.agregar(C);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (descripcion.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE DESCRIPCIÓN VACIO.\n";
                }
                if (error.isEmpty()) {
                    C.setDescripcion(descripcion);
                    C.setIdtipo(id);
                    dao.modificar(C);
                    cargarTabla();
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    C.setIdtipo(id);
                    dao.eliminar(C);
                    cargarTabla();
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR EN LA OPERACION PARA LA BASE DE DATOS. AVISE AL ADMINISTRADOR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void recuperarDatos() {
        int fila = tablaDatos.getSelectedRow();
        String descripcion = null;
        if (fila >= 0) {
            int id = Integer.parseInt(tablaDatos.getValueAt(fila, 0).toString());
            C.setIdtipo(id);
            dao.consultarDatos(C);
            descripcion = C.getDescripcion();
            txtCodigo.setText("" + id);
            txtDescripcion.setText(descripcion);
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
        txtDescripcion = new org.jdesktop.swingx.JXTextField();

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
        jLabel1.setText("Formulario de Tipo Articulo");
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
                        .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)))
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

        jLabel4.setText("Descripción:");

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

        txtDescripcion.setEnabled(false);
        txtDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtDescripcion.setPrompt("Nombre o descripción...");
        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelOperacionesLayout = new javax.swing.GroupLayout(panelOperaciones);
        panelOperaciones.setLayout(panelOperacionesLayout);
        panelOperacionesLayout.setHorizontalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOperacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOperacionesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pestanha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtDescripcion.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDescripcionKeyTyped

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

            if (operacion.equals("NUEVO")) {
                boolean existe = dao.verificarDuplicacion(txtDescripcion.getText());
                if (existe == true) {
                    JOptionPane.showMessageDialog(null, "LA DESCRIPCION INGRESADA YA ESTA REGISTRADA EN LA BASE DE DATOS");
                } else {
                    guardar(operacion);
                    habilitarCampos("GUARDAR");
                    limpiarCampos();
                }
            } else {
                guardar(operacion);
                habilitarCampos("GUARDAR");
                limpiarCampos();
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        if (txtDescripcion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            if (operacion.equals("NUEVO")) {
                boolean existe = dao.verificarDuplicacion(txtDescripcion.getText());
                if (existe == true) {
                    JOptionPane.showMessageDialog(null, "LA DESCRIPCION INGRESADA YA ESTA REGISTRADA EN LA BASE DE DATOS");
                } else {
                    btnGuardar.grabFocus();
                }
            } else {
                btnGuardar.grabFocus();
            }
        }
    }//GEN-LAST:event_txtDescripcionActionPerformed


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
    private org.jdesktop.swingx.JXTextField txtDescripcion;
    // End of variables declaration//GEN-END:variables
}
