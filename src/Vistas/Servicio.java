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
import java.text.ParseException;
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
public final class Servicio extends javax.swing.JInternalFrame {

    Modelos.ModServicio s = new Modelos.ModServicio();
    Modelos.ModServicioDetalle sd = new Modelos.ModServicioDetalle();
    Modelos.ModConfiguracion con = new Modelos.ModConfiguracion();
    Modelos.ModTimbrado tim = new Modelos.ModTimbrado();
    Modelos.ModNumeracion num = new Modelos.ModNumeracion();
    Modelos.ModCliente cli = new Modelos.ModCliente();
    Modelos.ModDeposito dep = new Modelos.ModDeposito();
    Modelos.ModArticulo art = new Modelos.ModArticulo();
    Modelos.ModImpuesto imp = new Modelos.ModImpuesto();
    Modelos.ModApertura ape = new Modelos.ModApertura();
    Modelos.ModCaja caj = new Modelos.ModCaja();
    Modelos.ModServicioApertura sape = new Modelos.ModServicioApertura();
    Modelos.ModOrdenTrabajo ot = new Modelos.ModOrdenTrabajo();
    Modelos.ModOrdenTrabajoDetalle otd = new Modelos.ModOrdenTrabajoDetalle();
    Modelos.ModServicioOrdenTrabajo sot = new Modelos.ModServicioOrdenTrabajo();

    Operaciones.OpeServicio dao = new Operaciones.OpeServicio();
    Operaciones.OpeServicioDetalle daoDetalle = new Operaciones.OpeServicioDetalle();
    Operaciones.OpeConfiguracion daoConfiguracion = new Operaciones.OpeConfiguracion();
    Operaciones.OpeTimbrado daoTimbrado = new Operaciones.OpeTimbrado();
    Operaciones.OpeNumeracion daoNumeracion = new Operaciones.OpeNumeracion();
    Operaciones.OpeCliente daoCliente = new Operaciones.OpeCliente();
    Operaciones.OpeDeposito daoDeposito = new Operaciones.OpeDeposito();
    Operaciones.OpeArticulo daoArticulo = new Operaciones.OpeArticulo();
    Operaciones.OpeImpuesto daoImpuesto = new Operaciones.OpeImpuesto();
    Operaciones.OpeApertura daoApertura = new Operaciones.OpeApertura();
    Operaciones.OpeCaja daoCaja = new Operaciones.OpeCaja();
    Operaciones.OpeServicioApertura daoServicioApertura = new Operaciones.OpeServicioApertura();
    Operaciones.OpeOrdenTrabajo daoOrdenTrabajo = new Operaciones.OpeOrdenTrabajo();
    Operaciones.OpeOrdenTrabajoDetalle daoOrdenTrabajoDetalle = new Operaciones.OpeOrdenTrabajoDetalle();
    Operaciones.OpeServicioOrdenTrabajo daoServicioOrdenTrabajo = new Operaciones.OpeServicioOrdenTrabajo();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosCliente = new ArrayList<>();
    ArrayList<Object[]> datosDeposito = new ArrayList<>();
    ArrayList<Object[]> datosArticulo = new ArrayList<>();
    ArrayList<Object[]> datosApertura = new ArrayList<>();
    ArrayList<Object[]> datosOrdenTrabajo = new ArrayList<>();

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
     * Creates new form Servicio
     */
    public Servicio() {
        this.setTitle("FrmServicio");
        initComponents();
        txtFecha.setFormats(formato);
        cargarApertura();
    }

    private void obtenerOrdenTrabajo() {
        int idorden = Integer.parseInt(txtCodigoOrdenTrabajo.getText());
        ot.setIdorden(idorden);
        boolean resultado = daoOrdenTrabajo.ObtenerDatosPendiente(ot);
        if (resultado == true) {
            txtFecha.setDate(ot.getFecha());
            txtNumeroComprobanteOrdenTrabajo.setText(ot.getNumero_comprobante());
            cli.setIdcliente(ot.getIdcliente());
            daoCliente.consultarDatos(cli);
            txtClienteCodigo.setText("" + cli.getIdcliente());
            txtClienteDescripcion.setText(cli.getNombre() + ' ' + cli.getApellido());
            txtClienteCedula.setText(cli.getCedula());
            txtClienteRuc.setText(cli.getRuc());
            txtClienteTelefono.setText(cli.getTelefono());
            txtClienteDireccion.setText(cli.getDireccion());

            obtenerOrdenTrabajoDetalle(idorden);
            txtDepositoCodigo.grabFocus();
        }
    }

    private void obtenerOrdenTrabajoDetalle(int idorden) {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        otd.setIdorden(idorden);
        datos = daoOrdenTrabajoDetalle.consultar(otd);
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        this.tablaDatos.setModel(modelo);
        calcularTotales();
    }

    private void obtenerNumeroFactura() {
        int numero_timbrado;
        String ult_nro_fac;
        int establecimiento;
        int punto_emision;
        int numero;
        //SE OBTIENE LOS DATOS DE LA CONFIGURACION
        int idconfiguracion = 1;
        con.setIdconfiguracion(idconfiguracion);
        daoConfiguracion.consultarDatos(con);
        //SE OBTIENE LOS DATOS DEL TIMRBADO EN BASE A LA CONFIGURACION
        tim.setIdtimbrado(con.getFacturacion_timbrado());
        daoTimbrado.consultarDatos(tim);
        numero_timbrado = tim.getTimbrado();
        //SE OBTIENE EL ULTIMO NUMERO DE FACTURA INGRESADO Y SE LE AGREGA 1 PARA PODER INGRESAR
        int idnumeracion = 1;
        num.setIdnumeracion(idnumeracion);
        daoNumeracion.consultarDatos(num);
        ult_nro_fac = num.getUlt_nro_fac();
        establecimiento = Integer.parseInt((ult_nro_fac.replace("-", "")).substring(0, 3));
        punto_emision = Integer.parseInt((ult_nro_fac.replace("-", "")).substring(3, 6));
        numero = Integer.parseInt((ult_nro_fac.replace("-", "")).substring(6, 13));

        txtEstablecimiento.setText(String.format(tres_ceros, establecimiento));
        txtPuntoExpedicion.setText(String.format(tres_ceros, punto_emision));
        txtNumero.setText(String.format(siete_ceros, (numero + 1)));
        txtComprobante.setText(String.format(tres_ceros, establecimiento) + '-' + String.format(tres_ceros, punto_emision) + '-' + String.format(siete_ceros, (numero + 1)));
        txtTimbrado.setText("" + numero_timbrado);
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

    private void calcularTotales() {
        double TOTAL = 0.0;
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            TOTAL = TOTAL + Double.parseDouble((tablaDatos.getValueAt(i, 6).toString()));
        };
        txtTotal.setText(formato_montos.format(TOTAL));
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
        txtDepositoCodigo.setText(null);
        txtDepositoDescripcion.setText(null);
        txtObservaciones.setText(null);
        txtTimbrado.setText(null);
        txtTotal.setText(null);
        txtCodigoOrdenTrabajo.setText(null);
        txtNumeroComprobanteOrdenTrabajo.setText(null);
        txtFecha.setDate(null);
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        obtenerNumeroFactura();
        txtCodigoOrdenTrabajo.grabFocus();
    }

    public void cargarApertura() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosSucursal.getModel();
        modelo.setRowCount(0);
        datosApertura = daoApertura.consultarAperturaAbierta(txtCriterioAperturaCaja.getText());
        for (Object[] obj : datosApertura) {
            modelo.addRow(obj);
        }
        this.tablaDatosSucursal.setModel(modelo);
    }

    private void buscarApertura() {
        cargarApertura();
        BuscadorApertura.setModal(true);
        BuscadorApertura.setSize(540, 285);
        BuscadorApertura.setLocationRelativeTo(this);
        BuscadorApertura.setVisible(true);
        int fila = tablaDatosSucursal.getSelectedRow();
        if (fila >= 0) {
            txtAperturaCodigo.setText(tablaDatosSucursal.getValueAt(fila, 0).toString());
            txtAperturaDescripcion.setText(tablaDatosSucursal.getValueAt(fila, 0).toString() + "-" + tablaDatosSucursal.getValueAt(fila, 2).toString() + "-" + tablaDatosSucursal.getValueAt(fila, 3).toString());
            btnSeleccionar.grabFocus();
        } else {
            txtAperturaCodigo.setText(null);
            txtAperturaDescripcion.setText(null);
        }
    }

    private void seleccionarApertura() {
        SeleccionarApertura.setModal(true);
        SeleccionarApertura.setSize(720, 175);
        SeleccionarApertura.setLocationRelativeTo(this);
        SeleccionarApertura.setVisible(true);
        txtAperturaCodigo.grabFocus();
    }

    private void guardar() {
        int id = guardarCabecera();
        if (id != 0) {
            guardarDetalle(id);
            JOptionPane.showMessageDialog(null, "SERVICIO REGISTRADO EXITOSAMENTE...", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
            actualizarUltimoNumeroFactura();
            registrarRelacionAperturaServicio(idapertura, id);
            registrarRelacionOrdenTrabajoServicio(Integer.parseInt(txtCodigoOrdenTrabajo.getText()), id);
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
        int numero_timbrado = Integer.parseInt(txtTimbrado.getText());
        java.util.Date fecha = txtFecha.getDate();
        java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
        int idcliente = Integer.parseInt(txtClienteCodigo.getText());
        int iddeposito = Integer.parseInt(txtDepositoCodigo.getText());
        int idusuario = Login.IDUSUARIO;
        String observacion = txtObservaciones.getText();
        if (fecha == null) {
            msj += "LA FECHA DE LA OPERACION NO PUEDE SER NULO.\n";
        }
        if (idcliente == 0) {
            msj += "NO HA SELECCIONADO NINGUN CLIENTE.\n";
        }
        if (iddeposito == 0) {
            msj += "NO HA SELECCIONADO NINGUN DEPÓSITO.\n";
        }

        if (idusuario == 0) {
            msj += "NO SE HA RECUPERADO NINGUN USUARIO.\n";
        }
        if (msj.isEmpty()) {
            s.setIdservicio(id);
            s.setNumero_documento(numero_comprobante);
            s.setNumero_timbrado(numero_timbrado);
            s.setFecha(fechaSQL);
            s.setObservacion(observacion);
            s.setIdcliente(idcliente);
            s.setIddeposito(iddeposito);
            s.setIdusuario(idusuario);
            dao.agregar(s);
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

            sd.setIdservicio(id);
            sd.setIdarticulo(idarticulo_tabla);
            sd.setNumero_item(numero_item);
            sd.setCantidad(cantidad);
            sd.setPrecio(precio);
            sd.setObservacion(observacion);
            daoDetalle.agregar(sd);
        }
    }

    private void actualizarUltimoNumeroFactura() {
        String ultimo_nro_factura = txtComprobante.getText();
        num.setIdnumeracion(1);
        num.setUlt_nro_fac(ultimo_nro_factura);
        daoNumeracion.modificar(num);
    }

    private void registrarRelacionAperturaServicio(int IDAPERTURA, int IDSERVICIO) {
        sape.setIdservicio(IDSERVICIO);
        sape.setIdapertura(IDAPERTURA);
        daoServicioApertura.agregar(sape);
    }

    private void registrarRelacionOrdenTrabajoServicio(int IDORDEN, int IDSERVICIO) {
        /*System.out.println("IDORDEN: " + IDORDEN);
        System.out.println("IDSERVICIO: " + IDSERVICIO);*/
        sot.setIdservicio(IDSERVICIO);
        sot.setIdorden(IDORDEN);
        daoServicioOrdenTrabajo.agregar(sot);
        /*APROVECHAMOS EL PROCEDIMIENTO PARA ACTUALIZAR EL ESTADO DE LA ORDEN DE TRABAJO*/
        ot.setIdorden(IDORDEN);
        ot.setEstado("C");
        daoOrdenTrabajo.modificar(ot);
    }

    private void generarInforme(int ID) {
        String msj = "";
        String ruta = "servicio.jasper";
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

            InputStream reporteInputStream = Servicio.class.getResourceAsStream("/Informes/" + ruta_archivo);
            JasperPrint jp = JasperFillManager.fillReport(reporteInputStream, parametros, con);
            JasperViewer jv = new JasperViewer(jp, false);
            JDialog dialog = new JDialog(VentanaInforme);
            dialog.setContentPane(jv.getContentPane());
            dialog.setSize(jv.getSize());
            dialog.setTitle("FACTURA DE SERVICIOS");
            dialog.setLocationRelativeTo(this);
            dialog.setModal(true);
            dialog.setVisible(true);
            //jv.setVisible(true);

        } catch (JRException | ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "ERROR: " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarOrdenTrabajo() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosOrdenesTrabajo.getModel();
        modelo.setRowCount(0);
        datosOrdenTrabajo = daoOrdenTrabajo.consultar(txtCriterioOrdenTrabajo.getText());
        for (Object[] obj : datosOrdenTrabajo) {
            modelo.addRow(obj);
        }
        this.tablaDatosOrdenesTrabajo.setModel(modelo);
    }

    private void buscarOrdenTrabajo() {
        cargarOrdenTrabajo();
        BuscadorOrdenTrabajo.setModal(true);
        BuscadorOrdenTrabajo.setSize(540, 285);
        BuscadorOrdenTrabajo.setLocationRelativeTo(this);
        BuscadorOrdenTrabajo.setVisible(true);
        int fila = tablaDatosOrdenesTrabajo.getSelectedRow();
        if (fila >= 0) {
            txtCodigoOrdenTrabajo.setText(tablaDatosOrdenesTrabajo.getValueAt(fila, 0).toString());
        } else {
            txtCodigoOrdenTrabajo.setText(null);
            txtCodigoOrdenTrabajo.grabFocus();
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
        BuscadorDeposito = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtCriterioDeposito = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosDeposito = new javax.swing.JTable();
        grupoFiltroArticulo = new javax.swing.ButtonGroup();
        SeleccionarApertura = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtAperturaCodigo = new org.jdesktop.swingx.JXTextField();
        txtAperturaDescripcion = new org.jdesktop.swingx.JXTextField();
        btnSeleccionar = new javax.swing.JButton();
        BuscadorApertura = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtCriterioAperturaCaja = new org.jdesktop.swingx.JXTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaDatosSucursal = new org.jdesktop.swingx.JXTable();
        VentanaInforme = new javax.swing.JDialog();
        BuscadorOrdenTrabajo = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtCriterioOrdenTrabajo = new org.jdesktop.swingx.JXTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaDatosOrdenesTrabajo = new org.jdesktop.swingx.JXTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
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
        txtDepositoCodigo = new org.jdesktop.swingx.JXTextField();
        txtDepositoDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel14 = new javax.swing.JLabel();
        txtObservaciones = new org.jdesktop.swingx.JXTextField();
        txtCodigoOrdenTrabajo = new org.jdesktop.swingx.JXTextField();
        txtNumeroComprobanteOrdenTrabajo = new org.jdesktop.swingx.JXTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        txtTotal = new org.jdesktop.swingx.JXTextField();
        jPanel8 = new javax.swing.JPanel();
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

        SeleccionarApertura.setUndecorated(true);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setBackground(new java.awt.Color(0, 153, 51));
        jLabel20.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Seleccione la Apertura de Caja a Utilizar");
        jLabel20.setToolTipText("");
        jLabel20.setOpaque(true);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setText("Apertura:");

        txtAperturaCodigo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtAperturaCodigo.setPrompt("");
        txtAperturaCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAperturaCodigoActionPerformed(evt);
            }
        });
        txtAperturaCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAperturaCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAperturaCodigoKeyTyped(evt);
            }
        });

        txtAperturaDescripcion.setEnabled(false);
        txtAperturaDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtAperturaDescripcion.setPrompt("Descripcion...");

        btnSeleccionar.setBackground(new java.awt.Color(0, 153, 51));
        btnSeleccionar.setFont(new java.awt.Font("Nirmala UI Semilight", 1, 18)); // NOI18N
        btnSeleccionar.setForeground(new java.awt.Color(255, 255, 255));
        btnSeleccionar.setText("Seleccionar");
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSeleccionar))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAperturaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAperturaDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtAperturaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtAperturaDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SeleccionarAperturaLayout = new javax.swing.GroupLayout(SeleccionarApertura.getContentPane());
        SeleccionarApertura.getContentPane().setLayout(SeleccionarAperturaLayout);
        SeleccionarAperturaLayout.setHorizontalGroup(
            SeleccionarAperturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SeleccionarAperturaLayout.setVerticalGroup(
            SeleccionarAperturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setBackground(new java.awt.Color(50, 104, 151));
        jLabel23.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("BUSCADOR DE APERTURAS DE CAJA EN ESTADO ABIERTO");
        jLabel23.setOpaque(true);

        txtCriterioAperturaCaja.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioAperturaCaja.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioAperturaCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioAperturaCajaActionPerformed(evt);
            }
        });
        txtCriterioAperturaCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioAperturaCajaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioAperturaCajaKeyTyped(evt);
            }
        });

        tablaDatosSucursal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Identificador", "ID_CAJA", "CAJA", "FECHA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
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
        tablaDatosSucursal.setInheritsPopupMenu(true);
        tablaDatosSucursal.setShowGrid(true);
        tablaDatosSucursal.getTableHeader().setResizingAllowed(false);
        tablaDatosSucursal.getTableHeader().setReorderingAllowed(false);
        tablaDatosSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosSucursalMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaDatosSucursal);
        if (tablaDatosSucursal.getColumnModel().getColumnCount() > 0) {
            tablaDatosSucursal.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosSucursal.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosSucursal.getColumnModel().getColumn(0).setMaxWidth(100);
            tablaDatosSucursal.getColumnModel().getColumn(1).setMinWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(1).setPreferredWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioAperturaCaja, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioAperturaCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorAperturaLayout = new javax.swing.GroupLayout(BuscadorApertura.getContentPane());
        BuscadorApertura.getContentPane().setLayout(BuscadorAperturaLayout);
        BuscadorAperturaLayout.setHorizontalGroup(
            BuscadorAperturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorAperturaLayout.setVerticalGroup(
            BuscadorAperturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setBackground(new java.awt.Color(50, 104, 151));
        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("BUSCADOR DE ORDENES DE TRABAJO PENDIENTES");
        jLabel15.setOpaque(true);

        txtCriterioOrdenTrabajo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioOrdenTrabajo.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioOrdenTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioOrdenTrabajoActionPerformed(evt);
            }
        });
        txtCriterioOrdenTrabajo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioOrdenTrabajoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioOrdenTrabajoKeyTyped(evt);
            }
        });

        tablaDatosOrdenesTrabajo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Identificador", "Comprobante", "ClienteCodigo", "Cliente", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
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
        tablaDatosOrdenesTrabajo.setInheritsPopupMenu(true);
        tablaDatosOrdenesTrabajo.setShowGrid(true);
        tablaDatosOrdenesTrabajo.getTableHeader().setResizingAllowed(false);
        tablaDatosOrdenesTrabajo.getTableHeader().setReorderingAllowed(false);
        tablaDatosOrdenesTrabajo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosOrdenesTrabajoMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tablaDatosOrdenesTrabajo);
        if (tablaDatosOrdenesTrabajo.getColumnModel().getColumnCount() > 0) {
            tablaDatosOrdenesTrabajo.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosOrdenesTrabajo.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosOrdenesTrabajo.getColumnModel().getColumn(0).setMaxWidth(100);
            tablaDatosOrdenesTrabajo.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatosOrdenesTrabajo.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatosOrdenesTrabajo.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioOrdenTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane6))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioOrdenTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorOrdenTrabajoLayout = new javax.swing.GroupLayout(BuscadorOrdenTrabajo.getContentPane());
        BuscadorOrdenTrabajo.getContentPane().setLayout(BuscadorOrdenTrabajoLayout);
        BuscadorOrdenTrabajoLayout.setHorizontalGroup(
            BuscadorOrdenTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorOrdenTrabajoLayout.setVerticalGroup(
            BuscadorOrdenTrabajoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setToolTipText("");

        jLabel1.setBackground(new java.awt.Color(0, 204, 204));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Formulario de Servicios");
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
        jLabel3.setText("N° O.T.:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setText("Fecha:");

        txtFecha.setEditable(false);
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

        txtEstablecimiento.setEditable(false);
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

        txtPuntoExpedicion.setEditable(false);
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

        txtNumero.setEditable(false);
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

        txtTimbrado.setEditable(false);
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
        jLabel7.setText("Cliente:");

        txtClienteCodigo.setEditable(false);
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

        txtCodigoOrdenTrabajo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtCodigoOrdenTrabajo.setPrompt("");
        txtCodigoOrdenTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoOrdenTrabajoActionPerformed(evt);
            }
        });
        txtCodigoOrdenTrabajo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoOrdenTrabajoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoOrdenTrabajoKeyTyped(evt);
            }
        });

        txtNumeroComprobanteOrdenTrabajo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txtNumeroComprobanteOrdenTrabajo.setPrompt("");
        txtNumeroComprobanteOrdenTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroComprobanteOrdenTrabajoActionPerformed(evt);
            }
        });
        txtNumeroComprobanteOrdenTrabajo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroComprobanteOrdenTrabajoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroComprobanteOrdenTrabajoKeyTyped(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel22.setText("Comprobante O.T.:");

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
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCodigoOrdenTrabajo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPuntoExpedicion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNumeroComprobanteOrdenTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClienteCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClienteDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClienteCedula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 50, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtClienteRuc, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(txtTimbrado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtClienteTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtClienteDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 1163, Short.MAX_VALUE)
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
                    .addComponent(txtCodigoOrdenTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroComprobanteOrdenTrabajo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
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
                    .addComponent(txtDepositoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDepositoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles de los servicios...", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12))); // NOI18N

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Operaciones...", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12))); // NOI18N

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
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 207, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            txtClienteCodigo.grabFocus();
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
        obtenerNumeroFactura();
        seleccionarApertura();
    }//GEN-LAST:event_formInternalFrameOpened

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
                txtDepositoCodigo.grabFocus();
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
        btnGuardar.grabFocus();
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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int respuesta;
        respuesta = JOptionPane.showConfirmDialog(null, "¿CONFIRMAR SERVICIO?",
                "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (respuesta != 1) {
            guardar();
            txtCodigoOrdenTrabajo.grabFocus();
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

    private void txtAperturaCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAperturaCodigoActionPerformed
        buscarApertura();
    }//GEN-LAST:event_txtAperturaCodigoActionPerformed

    private void txtAperturaCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAperturaCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarApertura();
        }
    }//GEN-LAST:event_txtAperturaCodigoKeyPressed

    private void txtAperturaCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAperturaCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtAperturaCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAperturaCodigoKeyTyped

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
        if (txtAperturaCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO HA SELECCIONADO NADA, SE CERRARA EL FORMULARIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            SeleccionarApertura.dispose();
            dispose();
        } else {
            int respuesta;
            respuesta = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE SELECCIONAR ESTA APERTURA?",
                    "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (respuesta != 1) {
                idapertura = Integer.parseInt(txtAperturaCodigo.getText());
                SeleccionarApertura.dispose();
            }
        }
    }//GEN-LAST:event_btnSeleccionarActionPerformed

    private void txtCriterioAperturaCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioAperturaCajaActionPerformed
        cargarApertura();
    }//GEN-LAST:event_txtCriterioAperturaCajaActionPerformed

    private void txtCriterioAperturaCajaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioAperturaCajaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtAperturaCodigo.setText(null);
            txtAperturaDescripcion.setText(null);
            txtAperturaCodigo.grabFocus();
            BuscadorApertura.dispose();
        }
    }//GEN-LAST:event_txtCriterioAperturaCajaKeyPressed

    private void txtCriterioAperturaCajaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioAperturaCajaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioAperturaCaja.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioAperturaCajaKeyTyped

    private void tablaDatosSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosSucursalMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosSucursal.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioAperturaCaja.setText(null);
                BuscadorApertura.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosSucursalMouseClicked

    private void txtCodigoOrdenTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoOrdenTrabajoActionPerformed
        obtenerOrdenTrabajo();
    }//GEN-LAST:event_txtCodigoOrdenTrabajoActionPerformed

    private void txtCodigoOrdenTrabajoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoOrdenTrabajoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarOrdenTrabajo();
        }
    }//GEN-LAST:event_txtCodigoOrdenTrabajoKeyPressed

    private void txtCodigoOrdenTrabajoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoOrdenTrabajoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoOrdenTrabajo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoOrdenTrabajoKeyTyped

    private void txtNumeroComprobanteOrdenTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroComprobanteOrdenTrabajoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroComprobanteOrdenTrabajoActionPerformed

    private void txtNumeroComprobanteOrdenTrabajoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroComprobanteOrdenTrabajoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroComprobanteOrdenTrabajoKeyPressed

    private void txtNumeroComprobanteOrdenTrabajoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroComprobanteOrdenTrabajoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroComprobanteOrdenTrabajoKeyTyped

    private void txtCriterioOrdenTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioOrdenTrabajoActionPerformed
        cargarOrdenTrabajo();
    }//GEN-LAST:event_txtCriterioOrdenTrabajoActionPerformed

    private void txtCriterioOrdenTrabajoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioOrdenTrabajoKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoOrdenTrabajo.setText(null);
            txtNumeroComprobanteOrdenTrabajo.setText(null);
            txtCodigoOrdenTrabajo.grabFocus();
            BuscadorOrdenTrabajo.dispose();
        }
    }//GEN-LAST:event_txtCriterioOrdenTrabajoKeyPressed

    private void txtCriterioOrdenTrabajoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioOrdenTrabajoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioOrdenTrabajo.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioOrdenTrabajoKeyTyped

    private void tablaDatosOrdenesTrabajoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosOrdenesTrabajoMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosOrdenesTrabajo.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioOrdenTrabajo.setText(null);
                BuscadorOrdenTrabajo.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosOrdenesTrabajoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorApertura;
    private javax.swing.JDialog BuscadorCliente;
    private javax.swing.JDialog BuscadorDeposito;
    private javax.swing.JDialog BuscadorOrdenTrabajo;
    private javax.swing.JDialog SeleccionarApertura;
    private javax.swing.JDialog VentanaInforme;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.ButtonGroup grupoContadoCredito;
    private javax.swing.ButtonGroup grupoFiltroArticulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable tablaDatos;
    private org.jdesktop.swingx.JXTable tablaDatosCliente;
    private javax.swing.JTable tablaDatosDeposito;
    private org.jdesktop.swingx.JXTable tablaDatosOrdenesTrabajo;
    private org.jdesktop.swingx.JXTable tablaDatosSucursal;
    private org.jdesktop.swingx.JXTextField txtAperturaCodigo;
    private org.jdesktop.swingx.JXTextField txtAperturaDescripcion;
    private org.jdesktop.swingx.JXTextField txtClienteCedula;
    private org.jdesktop.swingx.JXTextField txtClienteCodigo;
    private org.jdesktop.swingx.JXTextField txtClienteDescripcion;
    private org.jdesktop.swingx.JXTextField txtClienteDireccion;
    private org.jdesktop.swingx.JXTextField txtClienteRuc;
    private org.jdesktop.swingx.JXTextField txtClienteTelefono;
    private org.jdesktop.swingx.JXTextField txtCodigoOrdenTrabajo;
    private org.jdesktop.swingx.JXTextField txtComprobante;
    private org.jdesktop.swingx.JXTextField txtCriterioAperturaCaja;
    private org.jdesktop.swingx.JXTextField txtCriterioCliente;
    private org.jdesktop.swingx.JXTextField txtCriterioDeposito;
    private org.jdesktop.swingx.JXTextField txtCriterioOrdenTrabajo;
    private org.jdesktop.swingx.JXTextField txtDepositoCodigo;
    private org.jdesktop.swingx.JXTextField txtDepositoDescripcion;
    private org.jdesktop.swingx.JXTextField txtEstablecimiento;
    private org.jdesktop.swingx.JXDatePicker txtFecha;
    private org.jdesktop.swingx.JXTextField txtNumero;
    private org.jdesktop.swingx.JXTextField txtNumeroComprobanteOrdenTrabajo;
    private org.jdesktop.swingx.JXTextField txtObservaciones;
    private org.jdesktop.swingx.JXTextField txtPuntoExpedicion;
    private org.jdesktop.swingx.JXTextField txtTimbrado;
    private org.jdesktop.swingx.JXTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
