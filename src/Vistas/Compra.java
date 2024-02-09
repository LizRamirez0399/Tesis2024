package Vistas;

import App.App;
import App.Login;
import Controladores.Database;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Liz
 */
public final class Compra extends javax.swing.JInternalFrame {

    Modelos.ModCompra c = new Modelos.ModCompra();
    Modelos.ModCompraDetalle cd = new Modelos.ModCompraDetalle();
    Modelos.ModConfiguracion con = new Modelos.ModConfiguracion();
    Modelos.ModTimbrado tim = new Modelos.ModTimbrado();
    Modelos.ModNumeracion num = new Modelos.ModNumeracion();
    Modelos.ModProveedor pro = new Modelos.ModProveedor();
    Modelos.ModDeposito dep = new Modelos.ModDeposito();
    Modelos.ModArticulo art = new Modelos.ModArticulo();
    Modelos.ModImpuesto imp = new Modelos.ModImpuesto();
    Modelos.ModApertura ape = new Modelos.ModApertura();
    Modelos.ModCaja caj = new Modelos.ModCaja();
    Modelos.ModVentaApertura vape = new Modelos.ModVentaApertura();

    Operaciones.OpeCompra dao = new Operaciones.OpeCompra();
    Operaciones.OpeCompraDetalle daoDetalle = new Operaciones.OpeCompraDetalle();
    Operaciones.OpeConfiguracion daoConfiguracion = new Operaciones.OpeConfiguracion();
    Operaciones.OpeTimbrado daoTimbrado = new Operaciones.OpeTimbrado();
    Operaciones.OpeNumeracion daoNumeracion = new Operaciones.OpeNumeracion();
    Operaciones.OpeProveedor daoProveedor = new Operaciones.OpeProveedor();
    Operaciones.OpeDeposito daoDeposito = new Operaciones.OpeDeposito();
    Operaciones.OpeArticulo daoArticulo = new Operaciones.OpeArticulo();
    Operaciones.OpeImpuesto daoImpuesto = new Operaciones.OpeImpuesto();
    Operaciones.OpeApertura daoApertura = new Operaciones.OpeApertura();
    Operaciones.OpeCaja daoCaja = new Operaciones.OpeCaja();
    Operaciones.OpeVentaApertura daoVentaApertura = new Operaciones.OpeVentaApertura();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosProveedor = new ArrayList<>();
    ArrayList<Object[]> datosDeposito = new ArrayList<>();
    ArrayList<Object[]> datosArticulo = new ArrayList<>();
    ArrayList<Object[]> datosApertura = new ArrayList<>();

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    String tres_ceros = String.format("%%0%dd", 3);
    String siete_ceros = String.format("%%0%dd", 7);
    int idarticulo;
    double valorCalculoIva = 0.0;
    double valorMontoCosto = 0.0;
    double valorCantidad = 0.0;

    DecimalFormat formato_montos = new DecimalFormat("#,###");
    DecimalFormat formato_cantidades = new DecimalFormat("#,###.00");

    int idapertura = 0;

    Database db = new Database();

    /**
     * Creates new form Compra
     */
    public Compra() {
        this.setTitle("FrmCompra");
        initComponents();
        txtFecha.setFormats(formato);
    }

    public void cargarProveedor() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosProveedor.getModel();
        modelo.setRowCount(0);
        datosProveedor = daoProveedor.consultar(txtCriterioProveedor.getText());
        for (Object[] obj : datosProveedor) {
            modelo.addRow(obj);
        }
        this.tablaDatosProveedor.setModel(modelo);
    }

    private void buscarProveedor() {
        cargarProveedor();
        BuscadorProveedor.setModal(true);
        BuscadorProveedor.setSize(820, 320);
        BuscadorProveedor.setLocationRelativeTo(this);
        BuscadorProveedor.setVisible(true);
        int fila = tablaDatosProveedor.getSelectedRow();
        if (fila >= 0) {
            txtProveedorCodigo.setText(tablaDatosProveedor.getValueAt(fila, 0).toString());
            txtProveedorDescripcion.setText(tablaDatosProveedor.getValueAt(fila, 1).toString());
        } else {
            txtProveedorCodigo.setText(null);
            txtProveedorDescripcion.setText(null);
        }
    }

    public void cargarDeposito() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosDeposito.getModel();
        modelo.setRowCount(0);
        datosDeposito = daoDeposito.consultar(txtCriterioDeposito.getText());
        for (Object[] obj : datosDeposito) {
            modelo.addRow(obj);
        }
        this.tablaDatosDeposito.setModel(modelo);
    }

    private void buscarDeposito() {
        cargarDeposito();
        BuscadorDeposito.setModal(true);
        BuscadorDeposito.setSize(540, 285);
        BuscadorDeposito.setLocationRelativeTo(this);
        BuscadorDeposito.setVisible(true);
        int fila = tablaDatosDeposito.getSelectedRow();
        if (fila >= 0) {
            txtDepositoCodigo.setText(tablaDatosDeposito.getValueAt(fila, 0).toString());
            txtDepositoDescripcion.setText(tablaDatosDeposito.getValueAt(fila, 1).toString());
        } else {
            txtDepositoCodigo.setText(null);
            txtDepositoDescripcion.setText(null);
        }
    }

    private void obtenerArticulo() {
        if (txtArticuloCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE CODIGO DEL ARTÍCULO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtArticuloCodigo.setText(null);
            txtArticuloDescripcion.setText(null);
            txtCosto.setText(null);
            txtCantidad.setText(null);
        } else {
            String criterio = txtArticuloCodigo.getText();
            boolean resultado = false;
            resultado = daoArticulo.busquedaArticulo(criterio, art);
            if (resultado == true) {
                txtArticuloDescripcion.setText(art.getDescripcion());
                idarticulo = art.getIdarticulo();
                imp.setIdimpuesto(art.getIdimpuesto());
                daoImpuesto.consultarDatos(imp);
                valorCalculoIva = imp.getValor();
                valorMontoCosto = art.getCosto();
                txtCosto.setText(formato_montos.format(valorMontoCosto));
                txtCantidad.grabFocus();
                txtCantidad.selectAll();
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE EL CÓDIGO DEL ARTÍCULO INGRESADO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                txtArticuloCodigo.setText(null);
                txtArticuloDescripcion.setText(null);
                art.setReferencia(null);
                art.setIdarticulo(0);
                txtArticuloCodigo.grabFocus();
            }
        }
    }

    public void cargarArticulo() {
        String filtro = "";
        if (rbDescripcion.isSelected()) {
            filtro = "ARTICULO_DESCRIPCION";
        } else if (rbReferencia.isSelected()) {
            filtro = "ARTICULO_REFERENCIA";
        }
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosArticulo.getModel();
        modelo.setRowCount(0);
        datosArticulo = daoArticulo.consultar(txtCriterioArticulo.getText(), filtro);
        for (Object[] obj : datosArticulo) {
            modelo.addRow(obj);
        }
        this.tablaDatosArticulo.setModel(modelo);
    }

    private void buscarArticulo() {
        cargarArticulo();
        BuscadorArticulo.setModal(true);
        BuscadorArticulo.setSize(710, 360);
        BuscadorArticulo.setLocationRelativeTo(this);
        BuscadorArticulo.setVisible(true);
        int fila = tablaDatosArticulo.getSelectedRow();
        if (fila >= 0) {
            txtArticuloCodigo.setText(tablaDatosArticulo.getValueAt(fila, 0).toString());
            txtArticuloDescripcion.setText(tablaDatosArticulo.getValueAt(fila, 1).toString());
            idarticulo = Integer.parseInt(tablaDatosArticulo.getValueAt(fila, 0).toString());
            art.setIdarticulo(idarticulo);
            daoArticulo.consultarDatos(art);
            imp.setIdimpuesto(art.getIdimpuesto());
            daoImpuesto.consultarDatos(imp);
            valorCalculoIva = imp.getValor();
        } else {
            txtArticuloCodigo.setText(null);
            txtArticuloDescripcion.setText(null);
        }
    }

    private void agregar() {
        String msj = "";
        int numero_item = 0;
        String referencia = txtArticuloCodigo.getText();
        int cod_articulo = idarticulo;
        String articulo = txtArticuloDescripcion.getText();
        Double precio = valorMontoCosto;
        Double cantidad = valorCantidad;

        int i;
        boolean existe = false;
        for (i = 0; i < tablaDatos.getRowCount(); i++) {
            int idarticulo_tabla = Integer.parseInt(tablaDatos.getValueAt(i, 2).toString());
            //SE VERIFICA LA TABLA SI YA EXISTEN LOS DATOS QUE SE ESTAN INTENTANDO INGRESAR, PARA PODER INSERTAR O ACTUALIZAR EL REGISTRO
            if (idarticulo_tabla == cod_articulo) {
                existe = true;
                break;
            }
        }
        if (existe == false) {
            insertarRegistro(numero_item, referencia, cod_articulo, articulo, precio, cantidad);
            calcularTotales();
        } else {
            actualizarRegistro(i, cantidad, precio);
            calcularTotales();
        }
        calcularNumeroItem();
    }

    private void insertarRegistro(int numero_item, String referencia, int idarticulo, String descripcion, double precio, double cantidad) {
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, "NO PUEDE AGREGAR UN ARTICULO CON LA CANTIDAD 0", "ALERTA", JOptionPane.WARNING_MESSAGE);
        } else {
            Object[] fila = new Object[7];
            fila[0] = numero_item;
            fila[1] = referencia;
            fila[2] = idarticulo;
            fila[3] = descripcion;
            fila[4] = precio;
            fila[5] = cantidad;
            fila[6] = Math.round(precio * cantidad);

            DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
            modelo.addRow(fila);
            limpiarCamposArticulos();
            txtArticuloCodigo.grabFocus();
        }
    }

    private void limpiarCamposArticulos() {
        txtArticuloCodigo.setText(null);
        txtArticuloDescripcion.setText(null);
        txtCosto.setText(null);
        txtCantidad.setText(null);
        idarticulo = 0;
        valorMontoCosto = 0.0;
        valorCantidad = 0.0;
        valorCalculoIva = 0.0;
    }

    private void actualizarRegistro(int i, double cantidad, double precio) {
        Double CANTIDAD_ANTERIOR = Double.parseDouble(tablaDatos.getValueAt(i, 5).toString());
        Double CANTIDAD_NUEVA = cantidad + CANTIDAD_ANTERIOR;
        if (CANTIDAD_NUEVA > 0.0) {

            tablaDatos.setValueAt(precio, i, 4);
            double SUBTOTAL = Math.round(CANTIDAD_NUEVA * precio);

            tablaDatos.setValueAt(CANTIDAD_NUEVA, i, 5);
            tablaDatos.setValueAt(SUBTOTAL, i, 6);
            limpiarCamposArticulos();
            txtArticuloCodigo.grabFocus();
        } else {
            JOptionPane.showMessageDialog(null, "LA CANTIDAD EN LA TABLA NO PUEDE SER MENOR A 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void calcularTotales() {
        double TOTAL = 0.0;
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            TOTAL = TOTAL + Double.parseDouble((tablaDatos.getValueAt(i, 6).toString()));
        };
        txtTotal.setText(formato_montos.format(TOTAL));
    }

    private void calcularNumeroItem() {
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            tablaDatos.setValueAt(i + 1, i, 0);
        }
    }

    private void cancelar() {
        txtEstablecimiento.setText(null);
        txtPuntoExpedicion.setText(null);
        txtNumero.setText(null);
        txtComprobante.setText(null);
        txtProveedorCodigo.setText(null);
        txtProveedorDescripcion.setText(null);
        txtProveedorRuc.setText(null);
        txtProveedorDireccion.setText(null);
        txtProveedorTelefono.setText(null);
        txtDepositoCodigo.setText(null);
        txtDepositoDescripcion.setText(null);
        txtObservaciones.setText(null);
        txtTimbrado.setText(null);
        limpiarCamposArticulos();
        txtTotal.setText(null);
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        txtFecha.grabFocus();
    }

    private void retirar() {
        int indice = tablaDatos.getSelectedRow();
        if (indice >= 0) {
            int respuesta;
            respuesta = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE QUITAR LA FILA SELECCIONADA?",
                    "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (respuesta != 1) {
                DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
                modelo.removeRow(indice);
                calcularNumeroItem();
                calcularTotales();
            }
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA PARA RETIRAR\n");
        }
    }

    private void guardar() {
        int id = guardarCabecera();
        if (id != 0) {
            guardarDetalle(id);
            JOptionPane.showMessageDialog(null, "COMPRA REGISTRADA EXITOSAMENTE...", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
            cancelar();
        } else {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL MOMENTO DE GUARDAR LOS DATOS...", "ERROR", JOptionPane.ERROR_MESSAGE);
            cancelar();
        }
    }

    private int guardarCabecera() {
        //VARIABLE PARA NUEVA CLAVE
        int id = dao.nuevoID();
        //VARIABLE PARA ALMACENAR MENSAJES DE ERRORES
        String msj = "";
        //CAMPOS PARA LA BASE DE DATOS
        String numero_comprobante = txtComprobante.getText();
        int numero_timbrado = Integer.parseInt(txtTimbrado.getText());
        java.util.Date fecha = txtFecha.getDate();
        java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
        int idproveedor = Integer.parseInt(txtProveedorCodigo.getText());
        int iddeposito = Integer.parseInt(txtDepositoCodigo.getText());
        int idusuario = Login.IDUSUARIO;
        String observacion = txtObservaciones.getText();
        if (fecha == null) {
            msj += "LA FECHA DE LA OPERACION NO PUEDE SER NULO.\n";
        }
        if (idproveedor == 0) {
            msj += "NO HA SELECCIONADO NINGUN PROVEEDOR.\n";
        }
        if (iddeposito == 0) {
            msj += "NO HA SELECCIONADO NINGUN DEPÓSITO.\n";
        }

        if (idusuario == 0) {
            msj += "NO SE HA RECUPERADO NINGUN USUARIO.\n";
        }
        if (msj.isEmpty()) {
            c.setIdcompra(id);
            c.setNumero_documento(numero_comprobante);
            c.setNumero_timbrado(numero_timbrado);
            c.setFecha(fechaSQL);
            c.setObservacion(observacion);
            c.setIdproveedor(idproveedor);
            c.setIddeposito(iddeposito);
            c.setIdusuario(idusuario);
            dao.agregar(c);
        }
        return id;
    }

    private void guardarDetalle(int id) {
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            int numero_item = Integer.parseInt(tablaDatos.getValueAt(i, 0).toString());
            int idarticulo_tabla = Integer.parseInt(tablaDatos.getValueAt(i, 2).toString());
            double costo = Double.parseDouble(tablaDatos.getValueAt(i, 4).toString());
            double cantidad = Double.parseDouble(tablaDatos.getValueAt(i, 5).toString());

            cd.setIdcompra(id);
            cd.setIdarticulo(idarticulo_tabla);
            cd.setNumero_item(numero_item);
            cd.setCantidad(cantidad);
            cd.setCosto(costo);
            daoDetalle.agregar(cd);
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

        grupoContadoCredito = new javax.swing.ButtonGroup();
        BuscadorProveedor = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtCriterioProveedor = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosProveedor = new org.jdesktop.swingx.JXTable();
        BuscadorDeposito = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtCriterioDeposito = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosDeposito = new javax.swing.JTable();
        BuscadorArticulo = new javax.swing.JDialog();
        jPanel14 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtCriterioArticulo = new org.jdesktop.swingx.JXTextField();
        jScrollPane10 = new javax.swing.JScrollPane();
        tablaDatosArticulo = new javax.swing.JTable();
        rbDescripcion = new javax.swing.JRadioButton();
        rbReferencia = new javax.swing.JRadioButton();
        grupoFiltroArticulo = new javax.swing.ButtonGroup();
        VentanaInforme = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        checkContado = new javax.swing.JCheckBox();
        checkCredito = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new org.jdesktop.swingx.JXDatePicker();
        jLabel5 = new javax.swing.JLabel();
        txtEstablecimiento = new org.jdesktop.swingx.JXTextField();
        txtPuntoExpedicion = new org.jdesktop.swingx.JXTextField();
        txtNumero = new org.jdesktop.swingx.JXTextField();
        txtComprobante = new org.jdesktop.swingx.JXTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTimbrado = new org.jdesktop.swingx.JXTextField();
        jLabel7 = new javax.swing.JLabel();
        txtProveedorCodigo = new org.jdesktop.swingx.JXTextField();
        txtProveedorDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        txtProveedorRuc = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        txtProveedorTelefono = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        txtProveedorDireccion = new org.jdesktop.swingx.JXTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDepositoCodigo = new org.jdesktop.swingx.JXTextField();
        txtDepositoDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel14 = new javax.swing.JLabel();
        txtObservaciones = new org.jdesktop.swingx.JXTextField();
        jPanel4 = new javax.swing.JPanel();
        txtArticuloCodigo = new org.jdesktop.swingx.JXTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtArticuloDescripcion = new org.jdesktop.swingx.JXTextField();
        txtCantidad = new org.jdesktop.swingx.JXTextField();
        txtCosto = new org.jdesktop.swingx.JXTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        txtTotal = new org.jdesktop.swingx.JXTextField();
        jPanel8 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnRetirar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setBackground(new java.awt.Color(50, 104, 151));
        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("BUSCADOR DE PROVEEDORES");
        jLabel12.setOpaque(true);

        txtCriterioProveedor.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioProveedor.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioProveedorActionPerformed(evt);
            }
        });
        txtCriterioProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioProveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioProveedorKeyTyped(evt);
            }
        });

        tablaDatosProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Identificador", "Nombre", "Cedula", "Telefono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosProveedor.setInheritsPopupMenu(true);
        tablaDatosProveedor.setShowGrid(true);
        tablaDatosProveedor.getTableHeader().setResizingAllowed(false);
        tablaDatosProveedor.getTableHeader().setReorderingAllowed(false);
        tablaDatosProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosProveedor);
        if (tablaDatosProveedor.getColumnModel().getColumnCount() > 0) {
            tablaDatosProveedor.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosProveedor.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosProveedor.getColumnModel().getColumn(0).setMaxWidth(100);
            tablaDatosProveedor.getColumnModel().getColumn(2).setMinWidth(100);
            tablaDatosProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            tablaDatosProveedor.getColumnModel().getColumn(2).setMaxWidth(100);
            tablaDatosProveedor.getColumnModel().getColumn(3).setMinWidth(100);
            tablaDatosProveedor.getColumnModel().getColumn(3).setPreferredWidth(100);
            tablaDatosProveedor.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorProveedorLayout = new javax.swing.GroupLayout(BuscadorProveedor.getContentPane());
        BuscadorProveedor.getContentPane().setLayout(BuscadorProveedorLayout);
        BuscadorProveedorLayout.setHorizontalGroup(
            BuscadorProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorProveedorLayout.setVerticalGroup(
            BuscadorProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setBackground(new java.awt.Color(50, 104, 151));
        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("BUSCADOR DE DEPOSITOS");
        jLabel18.setOpaque(true);

        txtCriterioDeposito.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioDeposito.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioDeposito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioDepositoActionPerformed(evt);
            }
        });
        txtCriterioDeposito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioDepositoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioDepositoKeyTyped(evt);
            }
        });

        tablaDatosDeposito.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosDeposito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosDepositoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaDatosDeposito);
        if (tablaDatosDeposito.getColumnModel().getColumnCount() > 0) {
            tablaDatosDeposito.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosDeposito.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosDeposito.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioDeposito, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioDeposito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorDepositoLayout = new javax.swing.GroupLayout(BuscadorDeposito.getContentPane());
        BuscadorDeposito.getContentPane().setLayout(BuscadorDepositoLayout);
        BuscadorDepositoLayout.setHorizontalGroup(
            BuscadorDepositoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorDepositoLayout.setVerticalGroup(
            BuscadorDepositoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setBackground(new java.awt.Color(50, 104, 151));
        jLabel24.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("BUSCADOR DE ARTÍCULOS");
        jLabel24.setOpaque(true);

        txtCriterioArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioArticulo.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioArticuloActionPerformed(evt);
            }
        });
        txtCriterioArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioArticuloKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioArticuloKeyTyped(evt);
            }
        });

        tablaDatosArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosArticulo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Descripción"
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
        tablaDatosArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosArticuloMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tablaDatosArticulo);
        if (tablaDatosArticulo.getColumnModel().getColumnCount() > 0) {
            tablaDatosArticulo.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosArticulo.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosArticulo.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        grupoFiltroArticulo.add(rbDescripcion);
        rbDescripcion.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rbDescripcion.setSelected(true);
        rbDescripcion.setText("Descripción");
        rbDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbDescripcionActionPerformed(evt);
            }
        });
        rbDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbDescripcionKeyPressed(evt);
            }
        });

        grupoFiltroArticulo.add(rbReferencia);
        rbReferencia.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rbReferencia.setText("Referencia");
        rbReferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbReferenciaActionPerformed(evt);
            }
        });
        rbReferencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbReferenciaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(rbDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbReferencia)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addComponent(txtCriterioArticulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbDescripcion)
                    .addComponent(rbReferencia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorArticuloLayout = new javax.swing.GroupLayout(BuscadorArticulo.getContentPane());
        BuscadorArticulo.getContentPane().setLayout(BuscadorArticuloLayout);
        BuscadorArticuloLayout.setHorizontalGroup(
            BuscadorArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorArticuloLayout.setVerticalGroup(
            BuscadorArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout VentanaInformeLayout = new javax.swing.GroupLayout(VentanaInforme.getContentPane());
        VentanaInforme.getContentPane().setLayout(VentanaInformeLayout);
        VentanaInformeLayout.setHorizontalGroup(
            VentanaInformeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        VentanaInformeLayout.setVerticalGroup(
            VentanaInformeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 0));

        jLabel1.setBackground(new java.awt.Color(204, 204, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Formulario de Compras");
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
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de la cabecera", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Operación:");

        grupoContadoCredito.add(checkContado);
        checkContado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        checkContado.setSelected(true);
        checkContado.setText("Contado");
        checkContado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkContadoActionPerformed(evt);
            }
        });
        checkContado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                checkContadoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                checkContadoKeyTyped(evt);
            }
        });

        grupoContadoCredito.add(checkCredito);
        checkCredito.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        checkCredito.setText("Crédito");
        checkCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCreditoActionPerformed(evt);
            }
        });
        checkCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                checkCreditoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                checkCreditoKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setText("Fecha:");

        txtFecha.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });
        txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFechaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel5.setText("Comprobante:");

        txtEstablecimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstablecimiento.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtEstablecimiento.setPrompt("000");
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

        txtPuntoExpedicion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPuntoExpedicion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtPuntoExpedicion.setPrompt("000");
        txtPuntoExpedicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPuntoExpedicionActionPerformed(evt);
            }
        });
        txtPuntoExpedicion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuntoExpedicionKeyTyped(evt);
            }
        });

        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtNumero.setPrompt("0000000");
        txtNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroActionPerformed(evt);
            }
        });
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroKeyTyped(evt);
            }
        });

        txtComprobante.setEditable(false);
        txtComprobante.setForeground(new java.awt.Color(0, 102, 0));
        txtComprobante.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtComprobante.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtComprobante.setPrompt("000-000-0000000");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel6.setText("Timbrado:");

        txtTimbrado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimbrado.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtTimbrado.setPrompt("00000000");
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

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel7.setText("Proveedor:");

        txtProveedorCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProveedorCodigo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtProveedorCodigo.setPrompt("");
        txtProveedorCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProveedorCodigoActionPerformed(evt);
            }
        });
        txtProveedorCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProveedorCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProveedorCodigoKeyTyped(evt);
            }
        });

        txtProveedorDescripcion.setEditable(false);
        txtProveedorDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtProveedorDescripcion.setPrompt("");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel9.setText("R.U.C.:");

        txtProveedorRuc.setEditable(false);
        txtProveedorRuc.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtProveedorRuc.setPrompt("");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel10.setText("Telefono:");

        txtProveedorTelefono.setEditable(false);
        txtProveedorTelefono.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtProveedorTelefono.setPrompt("");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel11.setText("Direccion:");

        txtProveedorDireccion.setEditable(false);
        txtProveedorDireccion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtProveedorDireccion.setPrompt("");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel13.setText("Depósito:");

        txtDepositoCodigo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtDepositoCodigo.setPrompt("");
        txtDepositoCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepositoCodigoActionPerformed(evt);
            }
        });
        txtDepositoCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDepositoCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDepositoCodigoKeyTyped(evt);
            }
        });

        txtDepositoDescripcion.setEditable(false);
        txtDepositoDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtDepositoDescripcion.setPrompt("");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel14.setText("Observacion:");

        txtObservaciones.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtObservaciones.setPrompt("");
        txtObservaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObservacionesActionPerformed(evt);
            }
        });
        txtObservaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacionesKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(checkContado)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(checkCredito))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPuntoExpedicion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtProveedorCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtProveedorDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(16, 16, 16)
                                .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtProveedorRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)))
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProveedorTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtProveedorDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 1163, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtDepositoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDepositoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtObservaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(checkContado, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPuntoExpedicion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProveedorCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProveedorDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProveedorRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProveedorTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProveedorDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDepositoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDepositoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de articulos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12))); // NOI18N

        txtArticuloCodigo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtArticuloCodigo.setPrompt("");
        txtArticuloCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtArticuloCodigoMouseClicked(evt);
            }
        });
        txtArticuloCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtArticuloCodigoActionPerformed(evt);
            }
        });
        txtArticuloCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtArticuloCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtArticuloCodigoKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel15.setText("Código/Referencia");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel16.setText("Descripción");

        txtArticuloDescripcion.setEditable(false);
        txtArticuloDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtArticuloDescripcion.setPrompt("");
        txtArticuloDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtArticuloDescripcionActionPerformed(evt);
            }
        });
        txtArticuloDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtArticuloDescripcionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtArticuloDescripcionKeyTyped(evt);
            }
        });

        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantidad.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtCantidad.setPrompt("");
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        txtCosto.setEditable(false);
        txtCosto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCosto.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtCosto.setPrompt("");
        txtCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoActionPerformed(evt);
            }
        });
        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCostoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Cantidad");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Costo");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtArticuloCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtArticuloDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCosto, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtArticuloCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtArticuloDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles de la compra...", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12))); // NOI18N

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Referencia", "ARTICULO_CODIGO", "Articulo", "Precio", "Cantidad", "SubTotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaDatos);
        if (tablaDatos.getColumnModel().getColumnCount() > 0) {
            tablaDatos.getColumnModel().getColumn(0).setMinWidth(35);
            tablaDatos.getColumnModel().getColumn(0).setPreferredWidth(35);
            tablaDatos.getColumnModel().getColumn(0).setMaxWidth(35);
            tablaDatos.getColumnModel().getColumn(1).setMinWidth(110);
            tablaDatos.getColumnModel().getColumn(1).setPreferredWidth(110);
            tablaDatos.getColumnModel().getColumn(1).setMaxWidth(110);
            tablaDatos.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(2).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(4).setMinWidth(150);
            tablaDatos.getColumnModel().getColumn(4).setPreferredWidth(150);
            tablaDatos.getColumnModel().getColumn(4).setMaxWidth(150);
            tablaDatos.getColumnModel().getColumn(5).setMinWidth(150);
            tablaDatos.getColumnModel().getColumn(5).setPreferredWidth(150);
            tablaDatos.getColumnModel().getColumn(5).setMaxWidth(150);
            tablaDatos.getColumnModel().getColumn(6).setMinWidth(150);
            tablaDatos.getColumnModel().getColumn(6).setPreferredWidth(150);
            tablaDatos.getColumnModel().getColumn(6).setMaxWidth(150);
        }

        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtTotal.setPrompt("");
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTotalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1078, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Operaciones...", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12))); // NOI18N

        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_add_24px.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnRetirar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRetirar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_minus_24px.png"))); // NOI18N
        btnRetirar.setText("Retirar");
        btnRetirar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnRetirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetirarActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_tiktok_verified_account_24px.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_cancel_24px.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/icons8_sign_out_24px.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRetirar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRetirar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkContadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkContadoActionPerformed
        txtFecha.grabFocus();
    }//GEN-LAST:event_checkContadoActionPerformed

    private void checkContadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_checkContadoKeyPressed
        txtFecha.grabFocus();
    }//GEN-LAST:event_checkContadoKeyPressed

    private void checkContadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_checkContadoKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
    }//GEN-LAST:event_checkContadoKeyTyped

    private void checkCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCreditoActionPerformed
        txtFecha.grabFocus();
    }//GEN-LAST:event_checkCreditoActionPerformed

    private void checkCreditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_checkCreditoKeyPressed
        txtFecha.grabFocus();
    }//GEN-LAST:event_checkCreditoKeyPressed

    private void checkCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_checkCreditoKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
    }//GEN-LAST:event_checkCreditoKeyTyped

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        if (txtFecha.getDate() == null) {
            txtFecha.setDate(new java.util.Date());
        } else {
            txtEstablecimiento.grabFocus();
        }

    }//GEN-LAST:event_txtFechaActionPerformed

    private void txtFechaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
    }//GEN-LAST:event_txtFechaKeyPressed

    private void txtFechaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
    }//GEN-LAST:event_txtFechaKeyTyped

    private void txtEstablecimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstablecimientoActionPerformed
        if (txtEstablecimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt(txtEstablecimiento.getText())));
            txtPuntoExpedicion.grabFocus();
        }
    }//GEN-LAST:event_txtEstablecimientoActionPerformed

    private void txtEstablecimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstablecimientoKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtEstablecimiento.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEstablecimientoKeyTyped

    private void txtPuntoExpedicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPuntoExpedicionActionPerformed
        if (txtPuntoExpedicion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtPuntoExpedicion.setText(String.format(tres_ceros, Integer.parseInt(txtPuntoExpedicion.getText())));
            txtNumero.grabFocus();
        }
    }//GEN-LAST:event_txtPuntoExpedicionActionPerformed

    private void txtPuntoExpedicionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPuntoExpedicionKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtPuntoExpedicion.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPuntoExpedicionKeyTyped

    private void txtNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroActionPerformed
        if (txtNumero.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNumero.setText(String.format(siete_ceros, Integer.parseInt(txtNumero.getText())));
            txtComprobante.setText(txtEstablecimiento.getText() + "-" + txtPuntoExpedicion.getText() + "-" + txtNumero.getText());
            txtTimbrado.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroActionPerformed

    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumero.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroKeyTyped

    private void txtTimbradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimbradoActionPerformed
        if (txtTimbrado.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIMBRADO VACIO.", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        } else {
            txtProveedorCodigo.grabFocus();
        }
    }//GEN-LAST:event_txtTimbradoActionPerformed

    private void txtTimbradoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimbradoKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtTimbrado.getText().length() == 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTimbradoKeyTyped

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

    }//GEN-LAST:event_formInternalFrameOpened

    private void txtProveedorCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProveedorCodigoActionPerformed
        if (txtProveedorCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE PROVEEDOR VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idproveedor = Integer.parseInt(txtProveedorCodigo.getText());
            pro.setIdproveedor(idproveedor);
            boolean resultado = daoProveedor.consultarDatos(pro);
            if (resultado == true) {
                txtProveedorDescripcion.setText(pro.getRazon_social());
                txtProveedorRuc.setText(pro.getRuc());
                txtProveedorTelefono.setText(pro.getTelefono());
                txtProveedorDireccion.setText(pro.getDireccion());
                txtDepositoCodigo.grabFocus();
            } else {
                txtProveedorDescripcion.setText(null);
                txtProveedorRuc.setText(null);
                txtProveedorTelefono.setText(null);
                txtProveedorDireccion.setText(null);
                txtProveedorCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtProveedorCodigoActionPerformed

    private void txtProveedorCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProveedorCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarProveedor();
        }
    }//GEN-LAST:event_txtProveedorCodigoKeyPressed

    private void txtProveedorCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProveedorCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtProveedorCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtProveedorCodigoKeyTyped

    private void txtCriterioProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioProveedorActionPerformed
        cargarProveedor();
    }//GEN-LAST:event_txtCriterioProveedorActionPerformed

    private void txtCriterioProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioProveedorKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtProveedorCodigo.setText(null);
            txtProveedorDescripcion.setText(null);
            txtProveedorCodigo.grabFocus();
            BuscadorProveedor.dispose();
        }
    }//GEN-LAST:event_txtCriterioProveedorKeyPressed

    private void txtCriterioProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioProveedorKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioProveedor.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioProveedorKeyTyped

    private void tablaDatosProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosProveedorMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosProveedor.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioProveedor.setText(null);
                BuscadorProveedor.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosProveedorMouseClicked

    private void txtDepositoCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepositoCodigoActionPerformed
        if (txtDepositoCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int iddeposito = Integer.parseInt(txtDepositoCodigo.getText());
            dep.setIddeposito(iddeposito);
            boolean resultado = daoDeposito.consultarDatos(dep);
            if (resultado == true) {
                txtDepositoDescripcion.setText(dep.getDescripcion());
                txtObservaciones.grabFocus();
            } else {
                txtDepositoCodigo.setText(null);
                txtDepositoDescripcion.setText(null);
                txtDepositoCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtDepositoCodigoActionPerformed

    private void txtDepositoCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDepositoCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarDeposito();
        }
    }//GEN-LAST:event_txtDepositoCodigoKeyPressed

    private void txtDepositoCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDepositoCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtDepositoCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDepositoCodigoKeyTyped

    private void txtCriterioDepositoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioDepositoActionPerformed
        cargarDeposito();
    }//GEN-LAST:event_txtCriterioDepositoActionPerformed

    private void txtCriterioDepositoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioDepositoKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtDepositoCodigo.setText(null);
            txtDepositoDescripcion.setText(null);
            txtDepositoCodigo.grabFocus();
            BuscadorDeposito.dispose();
        }
    }//GEN-LAST:event_txtCriterioDepositoKeyPressed

    private void txtCriterioDepositoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioDepositoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioDeposito.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioDepositoKeyTyped

    private void tablaDatosDepositoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosDepositoMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosDeposito.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioDeposito.setText(null);
                BuscadorDeposito.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosDepositoMouseClicked

    private void txtObservacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObservacionesActionPerformed
        txtArticuloCodigo.grabFocus();
    }//GEN-LAST:event_txtObservacionesActionPerformed

    private void txtObservacionesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionesKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtObservaciones.getText().length() == 250) {
            evt.consume();
        }
    }//GEN-LAST:event_txtObservacionesKeyTyped

    private void txtArticuloCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtArticuloCodigoActionPerformed
        obtenerArticulo();
    }//GEN-LAST:event_txtArticuloCodigoActionPerformed

    private void txtArticuloCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArticuloCodigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarArticulo();
        }
        if (evt.VK_F2 == evt.getKeyCode()) {
            btnGuardar.grabFocus();
        }
    }//GEN-LAST:event_txtArticuloCodigoKeyPressed

    private void txtArticuloCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArticuloCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtArticuloDescripcion.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtArticuloCodigoKeyTyped

    private void txtArticuloDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtArticuloDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtArticuloDescripcionActionPerformed

    private void txtArticuloDescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArticuloDescripcionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtArticuloDescripcionKeyPressed

    private void txtArticuloDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArticuloDescripcionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtArticuloDescripcionKeyTyped

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        if (txtCantidad.getText().isEmpty()) {
            txtCantidad.setText("1");
        } else {
            String number = txtCantidad.getText();
            double monto = Double.parseDouble(number);
            if (monto < 0) {
                JOptionPane.showMessageDialog(null, "LA CANTIDAD NO PUEDE SER MENOR A 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                valorCantidad = monto;
                DecimalFormat formatter = new DecimalFormat("#,###.00");
                txtCantidad.setText(formatter.format(monto));
                agregar();
            }
        }
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadKeyPressed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (c == ',') {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCantidad.getText().length() == 28) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoActionPerformed

    private void txtCostoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoKeyPressed

    private void txtCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoKeyTyped

    private void txtCriterioArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioArticuloActionPerformed
        cargarArticulo();
    }//GEN-LAST:event_txtCriterioArticuloActionPerformed

    private void txtCriterioArticuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioArticuloKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtArticuloCodigo.setText(null);
            txtArticuloDescripcion.setText(null);
            BuscadorArticulo.dispose();
        }
    }//GEN-LAST:event_txtCriterioArticuloKeyPressed

    private void txtCriterioArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioArticuloKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioArticulo.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioArticuloKeyTyped

    private void tablaDatosArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosArticuloMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosArticulo.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioArticulo.setText(null);
                BuscadorArticulo.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosArticuloMouseClicked

    private void rbDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDescripcionActionPerformed
        txtCriterioArticulo.grabFocus();
    }//GEN-LAST:event_rbDescripcionActionPerformed

    private void rbDescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbDescripcionKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            txtCriterioArticulo.grabFocus();
        }
    }//GEN-LAST:event_rbDescripcionKeyPressed

    private void rbReferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbReferenciaActionPerformed
        txtCriterioArticulo.grabFocus();
    }//GEN-LAST:event_rbReferenciaActionPerformed

    private void rbReferenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbReferenciaKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            txtCriterioArticulo.grabFocus();
        }
    }//GEN-LAST:event_rbReferenciaKeyPressed

    private void txtArticuloCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtArticuloCodigoMouseClicked
        txtCosto.setText(null);
        txtCantidad.setText(null);
        idarticulo = 0;
    }//GEN-LAST:event_txtArticuloCodigoMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        agregar();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnRetirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetirarActionPerformed
        retirar();
    }//GEN-LAST:event_btnRetirarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int respuesta;
        respuesta = JOptionPane.showConfirmDialog(null, "¿CONFIRMAR COMPRA?",
                "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (respuesta != 1) {
            guardar();
            txtFecha.grabFocus();
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int respuesta;
        respuesta = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE CANCELAR LOS CAMBIOS?",
                "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (respuesta != 1) {
            cancelar();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        int respuesta;
        respuesta = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE SALIR DEL PROGRAMA?",
                "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (respuesta != 1) {
            dispose();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalKeyPressed

    private void txtTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorArticulo;
    private javax.swing.JDialog BuscadorDeposito;
    private javax.swing.JDialog BuscadorProveedor;
    private javax.swing.JDialog VentanaInforme;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRetirar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JCheckBox checkContado;
    private javax.swing.JCheckBox checkCredito;
    private javax.swing.ButtonGroup grupoContadoCredito;
    private javax.swing.ButtonGroup grupoFiltroArticulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JRadioButton rbDescripcion;
    private javax.swing.JRadioButton rbReferencia;
    private javax.swing.JTable tablaDatos;
    private javax.swing.JTable tablaDatosArticulo;
    private javax.swing.JTable tablaDatosDeposito;
    private org.jdesktop.swingx.JXTable tablaDatosProveedor;
    private org.jdesktop.swingx.JXTextField txtArticuloCodigo;
    private org.jdesktop.swingx.JXTextField txtArticuloDescripcion;
    private org.jdesktop.swingx.JXTextField txtCantidad;
    private org.jdesktop.swingx.JXTextField txtComprobante;
    private org.jdesktop.swingx.JXTextField txtCosto;
    private org.jdesktop.swingx.JXTextField txtCriterioArticulo;
    private org.jdesktop.swingx.JXTextField txtCriterioDeposito;
    private org.jdesktop.swingx.JXTextField txtCriterioProveedor;
    private org.jdesktop.swingx.JXTextField txtDepositoCodigo;
    private org.jdesktop.swingx.JXTextField txtDepositoDescripcion;
    private org.jdesktop.swingx.JXTextField txtEstablecimiento;
    private org.jdesktop.swingx.JXDatePicker txtFecha;
    private org.jdesktop.swingx.JXTextField txtNumero;
    private org.jdesktop.swingx.JXTextField txtObservaciones;
    private org.jdesktop.swingx.JXTextField txtProveedorCodigo;
    private org.jdesktop.swingx.JXTextField txtProveedorDescripcion;
    private org.jdesktop.swingx.JXTextField txtProveedorDireccion;
    private org.jdesktop.swingx.JXTextField txtProveedorRuc;
    private org.jdesktop.swingx.JXTextField txtProveedorTelefono;
    private org.jdesktop.swingx.JXTextField txtPuntoExpedicion;
    private org.jdesktop.swingx.JXTextField txtTimbrado;
    private org.jdesktop.swingx.JXTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
