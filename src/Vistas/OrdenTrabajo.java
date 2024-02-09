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
public final class OrdenTrabajo extends javax.swing.JInternalFrame {

    Modelos.ModOrdenTrabajo ot = new Modelos.ModOrdenTrabajo();
    Modelos.ModOrdenTrabajoDetalle otd = new Modelos.ModOrdenTrabajoDetalle();
    Modelos.ModConfiguracion con = new Modelos.ModConfiguracion();
    Modelos.ModTimbrado tim = new Modelos.ModTimbrado();
    Modelos.ModNumeracion num = new Modelos.ModNumeracion();
    Modelos.ModCliente cli = new Modelos.ModCliente();
    Modelos.ModArticulo art = new Modelos.ModArticulo();
    Modelos.ModImpuesto imp = new Modelos.ModImpuesto();
    Modelos.ModApertura ape = new Modelos.ModApertura();
    Modelos.ModCaja caj = new Modelos.ModCaja();
    Modelos.ModVentaApertura vape = new Modelos.ModVentaApertura();
    Modelos.ModEmpleado emp = new Modelos.ModEmpleado();

    Operaciones.OpeOrdenTrabajo dao = new Operaciones.OpeOrdenTrabajo();
    Operaciones.OpeOrdenTrabajoDetalle daoDetalle = new Operaciones.OpeOrdenTrabajoDetalle();
    Operaciones.OpeConfiguracion daoConfiguracion = new Operaciones.OpeConfiguracion();
    Operaciones.OpeTimbrado daoTimbrado = new Operaciones.OpeTimbrado();
    Operaciones.OpeNumeracion daoNumeracion = new Operaciones.OpeNumeracion();
    Operaciones.OpeCliente daoCliente = new Operaciones.OpeCliente();
    Operaciones.OpeArticulo daoArticulo = new Operaciones.OpeArticulo();
    Operaciones.OpeImpuesto daoImpuesto = new Operaciones.OpeImpuesto();
    Operaciones.OpeApertura daoApertura = new Operaciones.OpeApertura();
    Operaciones.OpeCaja daoCaja = new Operaciones.OpeCaja();
    Operaciones.OpeVentaApertura daoVentaApertura = new Operaciones.OpeVentaApertura();
    Operaciones.OpeEmpleado daoEmpleado = new Operaciones.OpeEmpleado();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosCliente = new ArrayList<>();
    ArrayList<Object[]> datosArticulo = new ArrayList<>();
    ArrayList<Object[]> datosEmpleado = new ArrayList<>();

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    String tres_ceros = String.format("%%0%dd", 3);
    String siete_ceros = String.format("%%0%dd", 7);
    int idarticulo;
    double valorCalculoIva = 0.0;
    double valorMontoPrecio = 0.0;
    double valorCantidad = 0.0;

    DecimalFormat formato_montos = new DecimalFormat("#,###");
    DecimalFormat formato_cantidades = new DecimalFormat("#,###.00");

    int idapertura = 0;

    Database db = new Database();

    /**
     * Creates new form Orden de Trabajo
     */
    public OrdenTrabajo() {
        this.setTitle("FrmOrdenTrabajo");
        initComponents();
        txtFecha.setFormats(formato);
    }

    private void obtenerNumeroOrdenTrabajo() {
        String ult_nro_ord_tra;
        int establecimiento;
        int punto_emision;
        int numero;
        //SE OBTIENE EL ULTIMO NUMERO DE FACTURA INGRESADO Y SE LE AGREGA 1 PARA PODER INGRESAR
        int idnumeracion = 1;
        num.setIdnumeracion(idnumeracion);
        daoNumeracion.consultarDatos(num);
        ult_nro_ord_tra = num.getUlt_nro_ord_tra();
        establecimiento = Integer.parseInt((ult_nro_ord_tra.replace("-", "")).substring(0, 3));
        punto_emision = Integer.parseInt((ult_nro_ord_tra.replace("-", "")).substring(3, 6));
        numero = Integer.parseInt((ult_nro_ord_tra.replace("-", "")).substring(6, 13));

        txtEstablecimiento.setText(String.format(tres_ceros, establecimiento));
        txtPuntoExpedicion.setText(String.format(tres_ceros, punto_emision));
        txtNumero.setText(String.format(siete_ceros, (numero + 1)));
        txtComprobante.setText(String.format(tres_ceros, establecimiento) + '-' + String.format(tres_ceros, punto_emision) + '-' + String.format(siete_ceros, (numero + 1)));

    }

    public void cargarCliente() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosCliente.getModel();
        modelo.setRowCount(0);
        datosCliente = daoCliente.consultar(txtCriterioCliente.getText());
        for (Object[] obj : datosCliente) {
            modelo.addRow(obj);
        }
        this.tablaDatosCliente.setModel(modelo);
    }

    private void buscarCliente() {
        cargarCliente();
        BuscadorCliente.setModal(true);
        BuscadorCliente.setSize(820, 320);
        BuscadorCliente.setLocationRelativeTo(this);
        BuscadorCliente.setVisible(true);
        int fila = tablaDatosCliente.getSelectedRow();
        if (fila >= 0) {
            txtClienteCodigo.setText(tablaDatosCliente.getValueAt(fila, 0).toString());
            txtClienteDescripcion.setText(tablaDatosCliente.getValueAt(fila, 1).toString());
        } else {
            txtClienteCodigo.setText(null);
            txtClienteDescripcion.setText(null);
        }
    }

    public void cargarEmpleado() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosEmpleado.getModel();
        modelo.setRowCount(0);
        datosEmpleado = daoEmpleado.consultar(txtCriterioEmpleado.getText());
        for (Object[] obj : datosEmpleado) {
            modelo.addRow(obj);
        }
        this.tablaDatosEmpleado.setModel(modelo);
    }

    private void buscarEmpleado() {
        cargarEmpleado();
        BuscadorEmpleado.setModal(true);
        BuscadorEmpleado.setSize(540, 285);
        BuscadorEmpleado.setLocationRelativeTo(this);
        BuscadorEmpleado.setVisible(true);
        int fila = tablaDatosEmpleado.getSelectedRow();
        if (fila >= 0) {
            txtEmpleadoCodigo.setText(tablaDatosEmpleado.getValueAt(fila, 0).toString());
            txtEmpleadoDescripcion.setText(tablaDatosEmpleado.getValueAt(fila, 1).toString());
        } else {
            txtEmpleadoCodigo.setText(null);
            txtEmpleadoDescripcion.setText(null);
        }
    }

    private void obtenerArticulo() {
        if (txtArticuloCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE CODIGO DEL ARTÍCULO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtArticuloCodigo.setText(null);
            txtArticuloDescripcion.setText(null);
            txtArticuloObservacion.setText(null);
            txtPrecio.setText(null);
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
                valorMontoPrecio = art.getPrecio();
                txtPrecio.setText(formato_montos.format(valorMontoPrecio));
                txtArticuloObservacion.grabFocus();
                txtArticuloObservacion.selectAll();
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
        String observacion = txtArticuloObservacion.getText();
        Double precio = valorMontoPrecio;
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
            insertarRegistro(numero_item, referencia, cod_articulo, articulo, precio, cantidad, observacion);
            calcularTotales();
        } else {
            actualizarRegistro(i, cantidad, precio);
            calcularTotales();
        }
        calcularNumeroItem();
    }

    private void insertarRegistro(int numero_item, String referencia, int idarticulo, String descripcion, double precio, double cantidad, String observacion) {
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, "NO PUEDE AGREGAR UN ARTICULO CON LA CANTIDAD 0", "ALERTA", JOptionPane.WARNING_MESSAGE);
        } else {
            Object[] fila = new Object[8];
            fila[0] = numero_item;
            fila[1] = referencia;
            fila[2] = idarticulo;
            fila[3] = descripcion;
            fila[4] = precio;
            fila[5] = cantidad;
            fila[6] = Math.round(precio * cantidad);
            fila[7] = observacion;

            DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
            modelo.addRow(fila);
            limpiarCamposArticulos();
            txtArticuloCodigo.grabFocus();
        }
    }

    private void limpiarCamposArticulos() {
        txtArticuloCodigo.setText(null);
        txtArticuloDescripcion.setText(null);
        txtArticuloObservacion.setText(null);
        txtPrecio.setText(null);
        txtCantidad.setText(null);
        idarticulo = 0;
        valorMontoPrecio = 0.0;
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
        txtClienteCodigo.setText(null);
        txtClienteDescripcion.setText(null);
        txtClienteCedula.setText(null);
        txtClienteRuc.setText(null);
        txtClienteDireccion.setText(null);
        txtClienteTelefono.setText(null);
        txtEmpleadoCodigo.setText(null);
        txtEmpleadoDescripcion.setText(null);
        txtObservaciones.setText(null);
        limpiarCamposArticulos();
        txtTotal.setText(null);
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        obtenerNumeroOrdenTrabajo();
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
            JOptionPane.showMessageDialog(null, "ORDEN DE TRABAJO REGISTRADA EXITOSAMENTE...", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
            actualizarUltimoNumeroOrdenTrabajo();
            int res = JOptionPane.YES_OPTION;
            while (res == JOptionPane.YES_OPTION) {
                generarInforme(id);
                res = JOptionPane.showConfirmDialog(null, "DESEA IMPRIMIR EL INFORME", "ACCIONES", JOptionPane.YES_NO_OPTION);
            }
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
        java.util.Date fecha = txtFecha.getDate();
        java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
        int idcliente = Integer.parseInt(txtClienteCodigo.getText());
        int idempleado = Integer.parseInt(txtEmpleadoCodigo.getText());
        int idusuario = Login.IDUSUARIO;
        String observacion = txtObservaciones.getText();
        String estado = "P";
        if (fecha == null) {
            msj += "LA FECHA DE LA OPERACION NO PUEDE SER NULO.\n";
        }
        if (idcliente == 0) {
            msj += "NO HA SELECCIONADO NINGUN CLIENTE.\n";
        }
        if (idempleado == 0) {
            msj += "NO HA SELECCIONADO NINGUN EMPLEADO.\n";
        }

        if (idusuario == 0) {
            msj += "NO SE HA RECUPERADO NINGUN USUARIO.\n";
        }
        if (msj.isEmpty()) {
            ot.setIdorden(id);
            ot.setNumero_comprobante(numero_comprobante);
            ot.setFecha(fechaSQL);
            ot.setObservacion(observacion);
            ot.setEstado(estado);
            ot.setIdcliente(idcliente);
            ot.setIdempleado(idempleado);
            ot.setIdusuario(idusuario);
            dao.agregar(ot);
        }
        return id;
    }

    private void guardarDetalle(int id) {
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            int numero_item = Integer.parseInt(tablaDatos.getValueAt(i, 0).toString());
            int idarticulo_tabla = Integer.parseInt(tablaDatos.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(tablaDatos.getValueAt(i, 4).toString());
            double cantidad = Double.parseDouble(tablaDatos.getValueAt(i, 5).toString());
            String observacion = tablaDatos.getValueAt(i, 7).toString();

            otd.setIdorden(id);
            otd.setIdarticulo(idarticulo_tabla);
            otd.setNumero_item(numero_item);
            otd.setCantidad(cantidad);
            otd.setPrecio(precio);
            otd.setObservacion(observacion);
            daoDetalle.agregar(otd);
        }
    }

    private void actualizarUltimoNumeroOrdenTrabajo() {
        String ultimo_nro_orden_trabajo = txtComprobante.getText();
        num.setIdnumeracion(1);
        num.setUlt_nro_ord_tra(ultimo_nro_orden_trabajo);
        daoNumeracion.modificar(num);
    }

    private void generarInforme(int ID) {
        String msj = "";
        String ruta = "orden_trabajo.jasper";
        Map parametros = new HashMap();
        parametros.put("P_ID", ID);
        if (msj.isEmpty()) {
            generarReporte(ruta, parametros);
        } else {
            JOptionPane.showMessageDialog(null, msj, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generarReporte(String ruta_archivo, Map parametros) {
        try {

            Connection con;
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());

            InputStream reporteInputStream = OrdenTrabajo.class.getResourceAsStream("/Informes/" + ruta_archivo);
            JasperPrint jp = JasperFillManager.fillReport(reporteInputStream, parametros, con);
            JasperViewer jv = new JasperViewer(jp, false);
            JDialog dialog = new JDialog(VentanaInforme);
            dialog.setContentPane(jv.getContentPane());
            dialog.setSize(jv.getSize());
            dialog.setTitle("COMPROBANTE INTERNO DE ORDEN DE TRABAJO");
            dialog.setLocationRelativeTo(this);
            dialog.setModal(true);
            dialog.setVisible(true);
            //jv.setVisible(true);

        } catch (JRException | ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "ERROR: " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
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
        BuscadorCliente = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtCriterioCliente = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosCliente = new org.jdesktop.swingx.JXTable();
        BuscadorEmpleado = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtCriterioEmpleado = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosEmpleado = new javax.swing.JTable();
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
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new org.jdesktop.swingx.JXDatePicker();
        jLabel5 = new javax.swing.JLabel();
        txtEstablecimiento = new org.jdesktop.swingx.JXTextField();
        txtPuntoExpedicion = new org.jdesktop.swingx.JXTextField();
        txtNumero = new org.jdesktop.swingx.JXTextField();
        txtComprobante = new org.jdesktop.swingx.JXTextField();
        jLabel7 = new javax.swing.JLabel();
        txtClienteCodigo = new org.jdesktop.swingx.JXTextField();
        txtClienteDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel8 = new javax.swing.JLabel();
        txtClienteCedula = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        txtClienteRuc = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        txtClienteTelefono = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        txtClienteDireccion = new org.jdesktop.swingx.JXTextField();
        jLabel13 = new javax.swing.JLabel();
        txtEmpleadoCodigo = new org.jdesktop.swingx.JXTextField();
        txtEmpleadoDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel14 = new javax.swing.JLabel();
        txtObservaciones = new org.jdesktop.swingx.JXTextField();
        jPanel4 = new javax.swing.JPanel();
        txtArticuloCodigo = new org.jdesktop.swingx.JXTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtArticuloDescripcion = new org.jdesktop.swingx.JXTextField();
        txtCantidad = new org.jdesktop.swingx.JXTextField();
        txtPrecio = new org.jdesktop.swingx.JXTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtArticuloObservacion = new org.jdesktop.swingx.JXTextField();
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
        jLabel12.setText("BUSCADOR DE CLIENTES");
        jLabel12.setOpaque(true);

        txtCriterioCliente.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioCliente.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioClienteActionPerformed(evt);
            }
        });
        txtCriterioCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioClienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioClienteKeyTyped(evt);
            }
        });

        tablaDatosCliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosCliente.setInheritsPopupMenu(true);
        tablaDatosCliente.setShowGrid(true);
        tablaDatosCliente.getTableHeader().setResizingAllowed(false);
        tablaDatosCliente.getTableHeader().setReorderingAllowed(false);
        tablaDatosCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosClienteMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosCliente);
        if (tablaDatosCliente.getColumnModel().getColumnCount() > 0) {
            tablaDatosCliente.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosCliente.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosCliente.getColumnModel().getColumn(0).setMaxWidth(100);
            tablaDatosCliente.getColumnModel().getColumn(2).setMinWidth(100);
            tablaDatosCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            tablaDatosCliente.getColumnModel().getColumn(2).setMaxWidth(100);
            tablaDatosCliente.getColumnModel().getColumn(3).setMinWidth(100);
            tablaDatosCliente.getColumnModel().getColumn(3).setPreferredWidth(100);
            tablaDatosCliente.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorClienteLayout = new javax.swing.GroupLayout(BuscadorCliente.getContentPane());
        BuscadorCliente.getContentPane().setLayout(BuscadorClienteLayout);
        BuscadorClienteLayout.setHorizontalGroup(
            BuscadorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorClienteLayout.setVerticalGroup(
            BuscadorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setBackground(new java.awt.Color(50, 104, 151));
        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("BUSCADOR DE EMPLEADOS");
        jLabel18.setOpaque(true);

        txtCriterioEmpleado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioEmpleado.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioEmpleadoActionPerformed(evt);
            }
        });
        txtCriterioEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioEmpleadoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioEmpleadoKeyTyped(evt);
            }
        });

        tablaDatosEmpleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Identificador", "Nombre", "Apellido"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosEmpleadoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaDatosEmpleado);
        if (tablaDatosEmpleado.getColumnModel().getColumnCount() > 0) {
            tablaDatosEmpleado.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosEmpleado.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosEmpleado.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorEmpleadoLayout = new javax.swing.GroupLayout(BuscadorEmpleado.getContentPane());
        BuscadorEmpleado.getContentPane().setLayout(BuscadorEmpleadoLayout);
        BuscadorEmpleadoLayout.setHorizontalGroup(
            BuscadorEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorEmpleadoLayout.setVerticalGroup(
            BuscadorEmpleadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setBackground(new java.awt.Color(0, 153, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Formulario de Ordenes de Trabajo");

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

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel7.setText("Cliente:");

        txtClienteCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtClienteCodigo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtClienteCodigo.setPrompt("");
        txtClienteCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteCodigoActionPerformed(evt);
            }
        });
        txtClienteCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClienteCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtClienteCodigoKeyTyped(evt);
            }
        });

        txtClienteDescripcion.setEditable(false);
        txtClienteDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtClienteDescripcion.setPrompt("");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel8.setText("C.I.:");

        txtClienteCedula.setEditable(false);
        txtClienteCedula.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtClienteCedula.setPrompt("");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel9.setText("R.U.C.:");

        txtClienteRuc.setEditable(false);
        txtClienteRuc.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtClienteRuc.setPrompt("");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel10.setText("Telefono:");

        txtClienteTelefono.setEditable(false);
        txtClienteTelefono.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtClienteTelefono.setPrompt("");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel11.setText("Direccion:");

        txtClienteDireccion.setEditable(false);
        txtClienteDireccion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtClienteDireccion.setPrompt("");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel13.setText("Empleado:");

        txtEmpleadoCodigo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtEmpleadoCodigo.setPrompt("");
        txtEmpleadoCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpleadoCodigoActionPerformed(evt);
            }
        });
        txtEmpleadoCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmpleadoCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmpleadoCodigoKeyTyped(evt);
            }
        });

        txtEmpleadoDescripcion.setEditable(false);
        txtEmpleadoDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtEmpleadoDescripcion.setPrompt("");

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
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPuntoExpedicion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtClienteCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClienteDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClienteCedula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 59, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtClienteRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtClienteTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtEmpleadoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEmpleadoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtObservaciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1157, Short.MAX_VALUE)
                            .addComponent(txtClienteDireccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPuntoExpedicion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClienteCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClienteDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClienteCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClienteRuc, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClienteTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClienteDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmpleadoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmpleadoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        txtPrecio.setEditable(false);
        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrecio.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtPrecio.setPrompt("");
        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });
        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrecioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Cantidad");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Precio");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel20.setText("Observacion");

        txtArticuloObservacion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtArticuloObservacion.setPrompt("");
        txtArticuloObservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtArticuloObservacionActionPerformed(evt);
            }
        });
        txtArticuloObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtArticuloObservacionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtArticuloObservacionKeyTyped(evt);
            }
        });

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
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtArticuloDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtArticuloObservacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
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
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtArticuloCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtArticuloDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtArticuloObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles de la Orden...", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12))); // NOI18N

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Referencia", "ARTICULO_CODIGO", "Articulo", "Precio", "Cantidad", "SubTotal", "Observacion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            txtClienteCodigo.grabFocus();
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

    private void txtClienteCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteCodigoActionPerformed
        if (txtClienteCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE CLIENTE VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idcliente = Integer.parseInt(txtClienteCodigo.getText());
            cli.setIdcliente(idcliente);
            boolean resultado = daoCliente.consultarDatos(cli);
            if (resultado == true) {
                txtClienteDescripcion.setText(cli.getNombre() + ' ' + cli.getApellido());
                txtClienteCedula.setText(cli.getCedula());
                txtClienteRuc.setText(cli.getRuc());
                txtClienteTelefono.setText(cli.getTelefono());
                txtClienteDireccion.setText(cli.getDireccion());
                txtEmpleadoCodigo.grabFocus();
            } else {
                txtClienteDescripcion.setText(null);
                txtClienteCedula.setText(null);
                txtClienteRuc.setText(null);
                txtClienteTelefono.setText(null);
                txtClienteDireccion.setText(null);
                txtClienteCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtClienteCodigoActionPerformed

    private void txtClienteCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarCliente();
        }
    }//GEN-LAST:event_txtClienteCodigoKeyPressed

    private void txtClienteCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtClienteCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtClienteCodigoKeyTyped

    private void txtCriterioClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioClienteActionPerformed
        cargarCliente();
    }//GEN-LAST:event_txtCriterioClienteActionPerformed

    private void txtCriterioClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioClienteKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtClienteCodigo.setText(null);
            txtClienteDescripcion.setText(null);
            txtClienteCodigo.grabFocus();
            BuscadorCliente.dispose();
        }
    }//GEN-LAST:event_txtCriterioClienteKeyPressed

    private void txtCriterioClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioClienteKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioCliente.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioClienteKeyTyped

    private void tablaDatosClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosClienteMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosCliente.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioCliente.setText(null);
                BuscadorCliente.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosClienteMouseClicked

    private void txtEmpleadoCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpleadoCodigoActionPerformed
        if (txtEmpleadoCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idempleado = Integer.parseInt(txtEmpleadoCodigo.getText());
            emp.setIdempleado(idempleado);
            boolean resultado = daoEmpleado.consultarDatos(emp);
            if (resultado == true) {
                txtEmpleadoDescripcion.setText(emp.getNombre() + ' ' + emp.getApellido());
                txtObservaciones.grabFocus();
            } else {
                txtEmpleadoCodigo.setText(null);
                txtEmpleadoDescripcion.setText(null);
                txtEmpleadoCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtEmpleadoCodigoActionPerformed

    private void txtEmpleadoCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpleadoCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarEmpleado();
        }
    }//GEN-LAST:event_txtEmpleadoCodigoKeyPressed

    private void txtEmpleadoCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpleadoCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtEmpleadoCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEmpleadoCodigoKeyTyped

    private void txtCriterioEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioEmpleadoActionPerformed
        cargarEmpleado();
    }//GEN-LAST:event_txtCriterioEmpleadoActionPerformed

    private void txtCriterioEmpleadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioEmpleadoKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtEmpleadoCodigo.setText(null);
            txtEmpleadoDescripcion.setText(null);
            txtEmpleadoCodigo.grabFocus();
            BuscadorEmpleado.dispose();
        }
    }//GEN-LAST:event_txtCriterioEmpleadoKeyPressed

    private void txtCriterioEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioEmpleadoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioEmpleado.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioEmpleadoKeyTyped

    private void tablaDatosEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosEmpleadoMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosEmpleado.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioEmpleado.setText(null);
                BuscadorEmpleado.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosEmpleadoMouseClicked

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

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void txtPrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioKeyPressed

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioKeyTyped

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
        txtPrecio.setText(null);
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
        respuesta = JOptionPane.showConfirmDialog(null, "¿CONFIRMAR ORDEN DE TRABAJO?",
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

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        obtenerNumeroOrdenTrabajo();
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtArticuloObservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtArticuloObservacionActionPerformed
        txtCantidad.grabFocus();
    }//GEN-LAST:event_txtArticuloObservacionActionPerformed

    private void txtArticuloObservacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArticuloObservacionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtArticuloObservacionKeyPressed

    private void txtArticuloObservacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArticuloObservacionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtArticuloObservacion.getText().length() == 250) {
            evt.consume();
        }
    }//GEN-LAST:event_txtArticuloObservacionKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorArticulo;
    private javax.swing.JDialog BuscadorCliente;
    private javax.swing.JDialog BuscadorEmpleado;
    private javax.swing.JDialog VentanaInforme;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRetirar;
    private javax.swing.JButton btnSalir;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
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
    private org.jdesktop.swingx.JXTable tablaDatosCliente;
    private javax.swing.JTable tablaDatosEmpleado;
    private org.jdesktop.swingx.JXTextField txtArticuloCodigo;
    private org.jdesktop.swingx.JXTextField txtArticuloDescripcion;
    private org.jdesktop.swingx.JXTextField txtArticuloObservacion;
    private org.jdesktop.swingx.JXTextField txtCantidad;
    private org.jdesktop.swingx.JXTextField txtClienteCedula;
    private org.jdesktop.swingx.JXTextField txtClienteCodigo;
    private org.jdesktop.swingx.JXTextField txtClienteDescripcion;
    private org.jdesktop.swingx.JXTextField txtClienteDireccion;
    private org.jdesktop.swingx.JXTextField txtClienteRuc;
    private org.jdesktop.swingx.JXTextField txtClienteTelefono;
    private org.jdesktop.swingx.JXTextField txtComprobante;
    private org.jdesktop.swingx.JXTextField txtCriterioArticulo;
    private org.jdesktop.swingx.JXTextField txtCriterioCliente;
    private org.jdesktop.swingx.JXTextField txtCriterioEmpleado;
    private org.jdesktop.swingx.JXTextField txtEmpleadoCodigo;
    private org.jdesktop.swingx.JXTextField txtEmpleadoDescripcion;
    private org.jdesktop.swingx.JXTextField txtEstablecimiento;
    private org.jdesktop.swingx.JXDatePicker txtFecha;
    private org.jdesktop.swingx.JXTextField txtNumero;
    private org.jdesktop.swingx.JXTextField txtObservaciones;
    private org.jdesktop.swingx.JXTextField txtPrecio;
    private org.jdesktop.swingx.JXTextField txtPuntoExpedicion;
    private org.jdesktop.swingx.JXTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
