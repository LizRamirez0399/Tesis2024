package Vistas;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Liz
 */
public final class Empleado extends javax.swing.JInternalFrame {

    Modelos.ModEmpleado p = new Modelos.ModEmpleado();
    Modelos.ModCiudad ci = new Modelos.ModCiudad();

    Operaciones.OpeEmpleado dao = new Operaciones.OpeEmpleado();
    Operaciones.OpeCiudad daoCiudad = new Operaciones.OpeCiudad();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosCiudad = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";

    /**
     * Creates new form empleado
     */
    public Empleado() {
        this.setTitle("FrmEmpleado");
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
                txtNombre.setEnabled(true);
                txtCedula.setEnabled(true);
                txtApellido.setEnabled(true);
                txtTelefono.setEnabled(true);
                txtDireccion.setEnabled(true);
                checkActivo.setEnabled(true);
                checkInactivo.setEnabled(true);
                txtCiudadCodigo.setEnabled(true);
                txtCiudadDescripcion.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnGuardar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtNombre.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtNombre.setEnabled(true);
                txtCedula.setEnabled(true);
                txtApellido.setEnabled(true);
                txtTelefono.setEnabled(true);
                txtDireccion.setEnabled(true);
                checkActivo.setEnabled(true);
                checkInactivo.setEnabled(true);
                txtCiudadCodigo.setEnabled(true);
                txtCiudadDescripcion.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnGuardar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECCIONAMOS
                pestanha.setSelectedIndex(1);
                txtNombre.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtNombre.setEnabled(false);
                txtCedula.setEnabled(false);
                txtApellido.setEnabled(false);
                txtTelefono.setEnabled(false);
                txtDireccion.setEnabled(false);
                checkActivo.setEnabled(false);
                checkInactivo.setEnabled(false);
                txtCiudadCodigo.setEnabled(false);
                txtCiudadDescripcion.setEnabled(false);
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
                txtNombre.setEnabled(false);
                txtCedula.setEnabled(false);
                txtApellido.setEnabled(false);
                txtTelefono.setEnabled(false);
                txtDireccion.setEnabled(false);
                checkActivo.setEnabled(false);
                checkInactivo.setEnabled(false);
                txtCiudadCodigo.setEnabled(false);
                txtCiudadDescripcion.setEnabled(false);
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
                txtNombre.setEnabled(false);
                txtCedula.setEnabled(false);
                txtApellido.setEnabled(false);
                txtTelefono.setEnabled(false);
                txtDireccion.setEnabled(false);
                checkActivo.setEnabled(false);
                checkInactivo.setEnabled(false);
                txtCiudadCodigo.setEnabled(false);
                txtCiudadDescripcion.setEnabled(false);
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
        txtNombre.setText(null);
        txtCedula.setText(null);
        txtApellido.setText(null);
        txtTelefono.setText(null);
        txtDireccion.setText(null);
        checkActivo.setSelected(true);
        txtCiudadCodigo.setText(null);
        txtCiudadDescripcion.setText(null);
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
        String nombre = txtNombre.getText();
        String cedula = txtCedula.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();
        String estado;
        if (checkActivo.isSelected()) {
            estado = "A";
        } else {
            estado = "I";
        }
        int idciudad = Integer.parseInt(txtCiudadCodigo.getText());
        switch (accion) {
            case "NUEVO":
                if (nombre.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE NOMBRE VACIO.\n";
                }
                if (cedula.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE CEDULA VACIO.\n";
                }
                if (idciudad == 0) {
                    error += "NO HA SELECCIONADO UNA CIUDAD.\n";
                }
                if (error.isEmpty()) {
                    p.setIdempleado(id);
                    p.setNombre(nombre);
                    p.setCedula(cedula);
                    p.setApellido(apellido);
                    p.setTelefono(telefono);
                    p.setDireccion(direccion);
                    p.setEstado(estado);
                    p.setIdciudad(idciudad);
                    dao.agregar(p);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (nombre.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE NOMBRE VACIO.\n";
                }
                if (cedula.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE CEDULA VACIO.\n";
                }
                if (idciudad == 0) {
                    error += "NO HA SELECCIONADO UNA CIUDAD.\n";
                }
                if (error.isEmpty()) {
                    p.setNombre(nombre);
                    p.setCedula(cedula);
                    p.setApellido(apellido);
                    p.setTelefono(telefono);
                    p.setDireccion(direccion);
                    p.setEstado(estado);
                    p.setIdciudad(idciudad);
                    p.setIdempleado(id);
                    dao.modificar(p);
                    cargarTabla();
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    p.setIdempleado(id);
                    dao.eliminar(p);
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
            p.setIdempleado(id);
            dao.consultarDatos(p);
            
            txtCodigo.setText("" + id);
            txtNombre.setText(p.getNombre());
            txtCedula.setText(p.getCedula());
            txtApellido.setText(p.getApellido());
            txtTelefono.setText(p.getTelefono());
            txtDireccion.setText(p.getDireccion());
            if (p.getEstado().equals("A")) {
                checkActivo.setSelected(true);
            } else {
                checkInactivo.setSelected(true);
            }
            
            ci.setIdciudad(p.getIdciudad());
            daoCiudad.consultarDatos(ci);

            txtCiudadCodigo.setText("" + p.getIdciudad());
            txtCiudadDescripcion.setText(ci.getDescripcion());
            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarCiudad() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosCiudad.getModel();
        modelo.setRowCount(0);
        datosCiudad = daoCiudad.consultar(txtCriterioCiudad.getText());
        for (Object[] obj : datosCiudad) {
            modelo.addRow(obj);
        }
        this.tablaDatosCiudad.setModel(modelo);
    }

    private void buscarCiudad() {
        cargarCiudad();
        BuscadorCiudad.setModal(true);
        BuscadorCiudad.setSize(540, 285);
        BuscadorCiudad.setLocationRelativeTo(this);
        BuscadorCiudad.setVisible(true);
        int fila = tablaDatosCiudad.getSelectedRow();
        if (fila >= 0) {
            txtCiudadCodigo.setText(tablaDatosCiudad.getValueAt(fila, 0).toString());
            txtCiudadDescripcion.setText(tablaDatosCiudad.getValueAt(fila, 1).toString());
        } else {
            txtCiudadCodigo.setText(null);
            txtCiudadDescripcion.setText(null);
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
        BuscadorCiudad = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtCriterioCiudad = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosCiudad = new org.jdesktop.swingx.JXTable();
        grupoActivoInactivo = new javax.swing.ButtonGroup();
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
        txtNombre = new org.jdesktop.swingx.JXTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCedula = new org.jdesktop.swingx.JXTextField();
        jLabel6 = new javax.swing.JLabel();
        txtApellido = new org.jdesktop.swingx.JXTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTelefono = new org.jdesktop.swingx.JXTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDireccion = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        checkActivo = new javax.swing.JCheckBox();
        checkInactivo = new javax.swing.JCheckBox();
        txtCiudadCodigo = new org.jdesktop.swingx.JXTextField();
        txtCiudadDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();

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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setBackground(new java.awt.Color(50, 104, 151));
        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("BUSCADOR DE CIUDADES");
        jLabel12.setOpaque(true);

        txtCriterioCiudad.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioCiudad.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioCiudadActionPerformed(evt);
            }
        });
        txtCriterioCiudad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioCiudadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioCiudadKeyTyped(evt);
            }
        });

        tablaDatosCiudad.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosCiudad.setInheritsPopupMenu(true);
        tablaDatosCiudad.setShowGrid(true);
        tablaDatosCiudad.getTableHeader().setResizingAllowed(false);
        tablaDatosCiudad.getTableHeader().setReorderingAllowed(false);
        tablaDatosCiudad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosCiudadMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosCiudad);
        if (tablaDatosCiudad.getColumnModel().getColumnCount() > 0) {
            tablaDatosCiudad.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosCiudad.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosCiudad.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioCiudad, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorCiudadLayout = new javax.swing.GroupLayout(BuscadorCiudad.getContentPane());
        BuscadorCiudad.getContentPane().setLayout(BuscadorCiudadLayout);
        BuscadorCiudadLayout.setHorizontalGroup(
            BuscadorCiudadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorCiudadLayout.setVerticalGroup(
            BuscadorCiudadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));

        jLabel1.setBackground(new java.awt.Color(0, 153, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Formulario de Empleados");
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
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Identificador", "Descripcion", "Ruc", "Representante", "Telefono", "Direccion", "Estado", "CiudadCodigo", "Ciudad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
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
            tablaDatos.getColumnModel().getColumn(2).setMinWidth(100);
            tablaDatos.getColumnModel().getColumn(2).setPreferredWidth(100);
            tablaDatos.getColumnModel().getColumn(2).setMaxWidth(100);
            tablaDatos.getColumnModel().getColumn(3).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(3).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(4).setMinWidth(100);
            tablaDatos.getColumnModel().getColumn(4).setPreferredWidth(100);
            tablaDatos.getColumnModel().getColumn(4).setMaxWidth(100);
            tablaDatos.getColumnModel().getColumn(5).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(5).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(6).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(6).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(6).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(7).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(7).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(8).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        javax.swing.GroupLayout panelListaLayout = new javax.swing.GroupLayout(panelLista);
        panelLista.setLayout(panelListaLayout);
        panelListaLayout.setHorizontalGroup(
            panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(panelListaLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelListaLayout.setVerticalGroup(
            panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pestanha.addTab("Lista de registros", panelLista);

        panelOperaciones.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Código:");

        jLabel4.setText("Nombre:");

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

        txtNombre.setToolTipText("Nombre...");
        txtNombre.setEnabled(false);
        txtNombre.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtNombre.setPrompt("Nombre...");
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        jLabel5.setText("C.I.:");

        txtCedula.setEnabled(false);
        txtCedula.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtCedula.setPrompt("Cedula de Identidad...");
        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });

        jLabel6.setText("Apellido:");

        txtApellido.setEnabled(false);
        txtApellido.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtApellido.setPrompt("Apellido...");
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        jLabel7.setText("Telefono:");

        txtTelefono.setEnabled(false);
        txtTelefono.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTelefono.setPrompt("Telefono o celular de contacto...");
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        jLabel8.setText("Direccion:");

        txtDireccion.setEnabled(false);
        txtDireccion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtDireccion.setPrompt("Direccion de domicilio...");
        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        jLabel10.setText("Estado:");

        grupoActivoInactivo.add(checkActivo);
        checkActivo.setText("ACTIVO");
        checkActivo.setEnabled(false);
        checkActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkActivoActionPerformed(evt);
            }
        });
        checkActivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                checkActivoKeyPressed(evt);
            }
        });

        grupoActivoInactivo.add(checkInactivo);
        checkInactivo.setSelected(true);
        checkInactivo.setText("INACTIVO");
        checkInactivo.setEnabled(false);
        checkInactivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkInactivoActionPerformed(evt);
            }
        });
        checkInactivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                checkInactivoKeyPressed(evt);
            }
        });

        txtCiudadCodigo.setEnabled(false);
        txtCiudadCodigo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtCiudadCodigo.setPrompt("Identificador...");
        txtCiudadCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiudadCodigoActionPerformed(evt);
            }
        });
        txtCiudadCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCiudadCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCiudadCodigoKeyTyped(evt);
            }
        });

        txtCiudadDescripcion.setEditable(false);
        txtCiudadDescripcion.setEnabled(false);
        txtCiudadDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtCiudadDescripcion.setPrompt("Descripción o nombre...");

        jLabel11.setText("Ciudad:");

        javax.swing.GroupLayout panelOperacionesLayout = new javax.swing.GroupLayout(panelOperaciones);
        panelOperaciones.setLayout(panelOperacionesLayout);
        panelOperacionesLayout.setHorizontalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOperacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelOperacionesLayout.createSequentialGroup()
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCedula, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
                            .addGroup(panelOperacionesLayout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelOperacionesLayout.createSequentialGroup()
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOperacionesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkActivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkInactivo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCiudadCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCiudadDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkActivo)
                    .addComponent(checkInactivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCiudadCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCiudadDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pestanha)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
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

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtNombre.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreKeyTyped

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
                boolean existe = dao.verificarDuplicacion(txtNombre.getText());
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

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtCedula.grabFocus();
        }
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        if (txtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            if (operacion.equals("NUEVO")) {
                boolean existe = dao.verificarDuplicacion(txtCedula.getText());
                if (existe == true) {
                    JOptionPane.showMessageDialog(null, "EL RUC INGRESADO YA ESTA REGISTRADA EN LA BASE DE DATOS");
                } else {
                    txtApellido.grabFocus();
                }
            } else {
                txtApellido.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCedula.getText().length() == 25) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        if (txtApellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtTelefono.grabFocus();
        }
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCedula.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        txtDireccion.grabFocus();
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtTelefono.getText().length() == 15) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        checkActivo.grabFocus();
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtDireccion.getText().length() == 250) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void checkActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkActivoActionPerformed
        txtCiudadCodigo.grabFocus();
    }//GEN-LAST:event_checkActivoActionPerformed

    private void checkActivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_checkActivoKeyPressed
        txtCiudadCodigo.grabFocus();
    }//GEN-LAST:event_checkActivoKeyPressed

    private void checkInactivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkInactivoActionPerformed
        txtCiudadCodigo.grabFocus();
    }//GEN-LAST:event_checkInactivoActionPerformed

    private void checkInactivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_checkInactivoKeyPressed
        txtCiudadCodigo.grabFocus();
    }//GEN-LAST:event_checkInactivoKeyPressed

    private void txtCiudadCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiudadCodigoActionPerformed
        if (txtCiudadCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE CIUDAD VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idciudad = Integer.parseInt(txtCiudadCodigo.getText());
            ci.setIdciudad(idciudad);
            boolean resultado = daoCiudad.consultarDatos(ci);
            if (resultado == true) {
                txtCiudadDescripcion.setText(ci.getDescripcion());
                btnGuardar.grabFocus();
            } else {
                txtCiudadCodigo.setText(null);
                txtCiudadDescripcion.setText(null);
                txtCiudadCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCiudadCodigoActionPerformed

    private void txtCiudadCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiudadCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarCiudad();
        }
    }//GEN-LAST:event_txtCiudadCodigoKeyPressed

    private void txtCiudadCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiudadCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCiudadCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCiudadCodigoKeyTyped

    private void txtCriterioCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioCiudadActionPerformed
        cargarCiudad();
    }//GEN-LAST:event_txtCriterioCiudadActionPerformed

    private void txtCriterioCiudadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioCiudadKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCiudadCodigo.setText(null);
            txtCiudadDescripcion.setText(null);
            txtCiudadCodigo.grabFocus();
            BuscadorCiudad.dispose();
        }
    }//GEN-LAST:event_txtCriterioCiudadKeyPressed

    private void txtCriterioCiudadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioCiudadKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterio.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioCiudadKeyTyped

    private void tablaDatosCiudadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosCiudadMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosCiudad.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioCiudad.setText(null);
                BuscadorCiudad.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosCiudadMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorCiudad;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Modificar;
    private org.jdesktop.swingx.JXButton btnCancelar;
    private org.jdesktop.swingx.JXButton btnGuardar;
    private org.jdesktop.swingx.JXButton btnNuevo;
    private javax.swing.JCheckBox checkActivo;
    private javax.swing.JCheckBox checkInactivo;
    private javax.swing.ButtonGroup grupoActivoInactivo;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu menuDesplegable;
    private javax.swing.JPanel panelLista;
    private javax.swing.JPanel panelOperaciones;
    private javax.swing.JTabbedPane pestanha;
    private org.jdesktop.swingx.JXTable tablaDatos;
    private org.jdesktop.swingx.JXTable tablaDatosCiudad;
    private org.jdesktop.swingx.JXTextField txtApellido;
    private org.jdesktop.swingx.JXTextField txtCedula;
    private org.jdesktop.swingx.JXTextField txtCiudadCodigo;
    private org.jdesktop.swingx.JXTextField txtCiudadDescripcion;
    private org.jdesktop.swingx.JXTextField txtCodigo;
    private org.jdesktop.swingx.JXTextField txtCriterio;
    private org.jdesktop.swingx.JXTextField txtCriterioCiudad;
    private org.jdesktop.swingx.JXTextField txtDireccion;
    private org.jdesktop.swingx.JXTextField txtNombre;
    private org.jdesktop.swingx.JXTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
