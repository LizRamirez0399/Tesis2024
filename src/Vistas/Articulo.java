package Vistas;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Liz
 */
public final class Articulo extends javax.swing.JInternalFrame {

    Modelos.ModArticulo a = new Modelos.ModArticulo();
    Modelos.ModMarca m = new Modelos.ModMarca();
    Modelos.ModLinea l = new Modelos.ModLinea();
    Modelos.ModUnidadMedida um = new Modelos.ModUnidadMedida();
    Modelos.ModTipoArticulo ta = new Modelos.ModTipoArticulo();
    Modelos.ModImpuesto i = new Modelos.ModImpuesto();

    Operaciones.OpeArticulo dao = new Operaciones.OpeArticulo();
    Operaciones.OpeMarca daoMarca = new Operaciones.OpeMarca();
    Operaciones.OpeLinea daoLinea = new Operaciones.OpeLinea();
    Operaciones.OpeUnidadMedida daoUnidadMedida = new Operaciones.OpeUnidadMedida();
    Operaciones.OpeTipoArticulo daoTipoArticulo = new Operaciones.OpeTipoArticulo();
    Operaciones.OpeImpuesto daoImpuesto = new Operaciones.OpeImpuesto();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosMarca = new ArrayList<>();
    ArrayList<Object[]> datosLinea = new ArrayList<>();
    ArrayList<Object[]> datosUnidadMedida = new ArrayList<>();
    ArrayList<Object[]> datosTipoArticulo = new ArrayList<>();
    ArrayList<Object[]> datosImpuesto = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";
    double valorMontoCosto = 0.0;
    double valorMontoPrecio = 0.0;

    /**
     * Creates new form Articulo
     */
    public Articulo() {
        this.setTitle("FrmArticulo");
        initComponents();
        cargarTabla();
    }

    public void cargarTabla() {

        String campo = null;

        if (checkIdentificador.isSelected()) {
            campo = "ARTICULO_CODIGO";
        }
        if (checkDescripcion.isSelected()) {
            campo = "ARTICULO_DESCRIPCION";
        }
        if (checkReferencia.isSelected()) {
            campo = "ARTICULO_REFERENCIA";
        }

        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        datos = dao.consultar(txtCriterio.getText(), campo);
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
                txtReferencia.setEnabled(true);
                checkActivo.setEnabled(true);
                checkInactivo.setEnabled(true);
                txtMarcaCodigo.setEnabled(true);
                txtMarcaDescripcion.setEnabled(false);
                txtLineaCodigo.setEnabled(true);
                txtLineaDescripcion.setEnabled(false);
                txtUnidadMedidaCodigo.setEnabled(true);
                txtUnidadMedidaDescripcion.setEnabled(false);
                txtTipoArticuloCodigo.setEnabled(true);
                txtTipoArticuloDescripcion.setEnabled(false);
                txtImpuestoCodigo.setEnabled(true);
                txtImpuestoDescripcion.setEnabled(false);
                txtCosto.setEnabled(true);
                txtPrecio.setEnabled(true);
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
                txtReferencia.setEnabled(true);
                checkActivo.setEnabled(true);
                checkInactivo.setEnabled(true);
                txtMarcaCodigo.setEnabled(true);
                txtMarcaDescripcion.setEnabled(false);
                txtLineaCodigo.setEnabled(true);
                txtLineaDescripcion.setEnabled(false);
                txtUnidadMedidaCodigo.setEnabled(true);
                txtUnidadMedidaDescripcion.setEnabled(false);
                txtTipoArticuloCodigo.setEnabled(true);
                txtTipoArticuloDescripcion.setEnabled(false);
                txtImpuestoCodigo.setEnabled(true);
                txtImpuestoDescripcion.setEnabled(false);
                txtCosto.setEnabled(true);
                txtPrecio.setEnabled(true);
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
                txtReferencia.setEnabled(false);
                checkActivo.setEnabled(false);
                checkInactivo.setEnabled(false);
                txtMarcaCodigo.setEnabled(false);
                txtMarcaDescripcion.setEnabled(false);
                txtLineaCodigo.setEnabled(false);
                txtLineaDescripcion.setEnabled(false);
                txtUnidadMedidaCodigo.setEnabled(false);
                txtUnidadMedidaDescripcion.setEnabled(false);
                txtTipoArticuloCodigo.setEnabled(false);
                txtTipoArticuloDescripcion.setEnabled(false);
                txtImpuestoCodigo.setEnabled(false);
                txtImpuestoDescripcion.setEnabled(false);
                txtCosto.setEnabled(false);
                txtPrecio.setEnabled(false);
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
                txtReferencia.setEnabled(false);
                checkActivo.setEnabled(false);
                checkInactivo.setEnabled(false);
                txtMarcaCodigo.setEnabled(false);
                txtMarcaDescripcion.setEnabled(false);
                txtLineaCodigo.setEnabled(false);
                txtLineaDescripcion.setEnabled(false);
                txtUnidadMedidaCodigo.setEnabled(false);
                txtUnidadMedidaDescripcion.setEnabled(false);
                txtTipoArticuloCodigo.setEnabled(false);
                txtTipoArticuloDescripcion.setEnabled(false);
                txtImpuestoCodigo.setEnabled(false);
                txtImpuestoDescripcion.setEnabled(false);
                txtCosto.setEnabled(false);
                txtPrecio.setEnabled(false);
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
                txtReferencia.setEnabled(false);
                checkActivo.setEnabled(false);
                checkInactivo.setEnabled(false);
                txtMarcaCodigo.setEnabled(false);
                txtMarcaDescripcion.setEnabled(false);
                txtLineaCodigo.setEnabled(false);
                txtLineaDescripcion.setEnabled(false);
                txtUnidadMedidaCodigo.setEnabled(false);
                txtUnidadMedidaDescripcion.setEnabled(false);
                txtTipoArticuloCodigo.setEnabled(false);
                txtTipoArticuloDescripcion.setEnabled(false);
                txtImpuestoCodigo.setEnabled(false);
                txtImpuestoDescripcion.setEnabled(false);
                txtCosto.setEnabled(false);
                txtPrecio.setEnabled(false);
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
        txtCriterioLinea.setText(null);
        txtCriterioMarca.setText(null);
        txtCriterioTipoArticulo.setText(null);
        txtCriterioUnidadMedida.setText(null);
        //CAMPOS
        txtCodigo.setText(null);
        txtDescripcion.setText(null);
        txtReferencia.setText(null);
        checkActivo.setSelected(true);
        txtMarcaCodigo.setText(null);
        txtMarcaDescripcion.setText(null);
        txtLineaCodigo.setText(null);
        txtLineaDescripcion.setText(null);
        txtUnidadMedidaCodigo.setText(null);
        txtUnidadMedidaDescripcion.setText(null);
        txtTipoArticuloCodigo.setText(null);
        txtTipoArticuloDescripcion.setText(null);
        txtImpuestoCodigo.setText(null);
        txtImpuestoDescripcion.setText(null);
        txtCosto.setText(null);
        txtPrecio.setText(null);
        //OPERACIONES
        operacion = null;
        valorMontoCosto = 0.0;
        valorMontoPrecio = 0.0;
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
        String referencia = txtReferencia.getText();
        String estado;
        if (checkActivo.isSelected()) {
            estado = "A";
        } else {
            estado = "I";
        }
        int idmarca = Integer.parseInt(txtMarcaCodigo.getText());
        int idlinea = Integer.parseInt(txtLineaCodigo.getText());
        int idunidadmedida = Integer.parseInt(txtUnidadMedidaCodigo.getText());
        int idtipoarticulo = Integer.parseInt(txtTipoArticuloCodigo.getText());
        int idimpuesto = Integer.parseInt(txtImpuestoCodigo.getText());
        double costo = valorMontoCosto;
        double precio = valorMontoPrecio;
        switch (accion) {
            case "NUEVO":
                if (descripcion.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE DESCRIPCIÓN VACIO.\n";
                }
                if (idmarca == 0) {
                    error += "NO HA SELECCIONADO UNA MARCA PARA EL ARTÍCULO.\n";
                }
                if (idlinea == 0) {
                    error += "NO HA SELECCIONADO UNA LINEA PARA EL ARTÍCULO.\n";
                }
                if (idunidadmedida == 0) {
                    error += "NO HA SELECCIONADO UNA UNIDAD DE MEDIDA PARA EL ARTÍCULO.\n";
                }
                if (idtipoarticulo == 0) {
                    error += "NO HA SELECCIONADO UN TIPO DE ARTÍCULO PARA EL ARTÍCULO.\n";
                }
                if (idimpuesto == 0) {
                    error += "NO HA SELECCIONADO UN IMPUESTO PARA EL ARTÍCULO.\n";
                }
                if (costo < 0.0) {
                    error += "EL COSTO NO PUEDE SER MENOR A 0.\n";
                }
                if (precio < 0.0) {
                    error += "EL PRECIO NO PUEDE SER MENOR A 0.\n";
                }
                if (error.isEmpty()) {
                    a.setIdarticulo(id);
                    a.setDescripcion(descripcion);
                    a.setReferencia(referencia);
                    a.setEstado(estado);
                    a.setIdmarca(idmarca);
                    a.setIdlinea(idlinea);
                    a.setIdunidad(idunidadmedida);
                    a.setIdtipo(idtipoarticulo);
                    a.setCosto(costo);
                    a.setPrecio(precio);
                    a.setIdimpuesto(idimpuesto);
                    dao.agregar(a);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (descripcion.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE DESCRIPCIÓN VACIO.\n";
                }
                if (idmarca == 0) {
                    error += "NO HA SELECCIONADO UNA MARCA PARA EL ARTÍCULO.\n";
                }
                if (idlinea == 0) {
                    error += "NO HA SELECCIONADO UNA LINEA PARA EL ARTÍCULO.\n";
                }
                if (idunidadmedida == 0) {
                    error += "NO HA SELECCIONADO UNA UNIDAD DE MEDIDA PARA EL ARTÍCULO.\n";
                }
                if (idtipoarticulo == 0) {
                    error += "NO HA SELECCIONADO UN TIPO DE ARTÍCULO PARA EL ARTÍCULO.\n";
                }
                if (idimpuesto == 0) {
                    error += "NO HA SELECCIONADO UN IMPUESTO PARA EL ARTÍCULO.\n";
                }
                if (costo < 0.0) {
                    error += "EL COSTO NO PUEDE SER MENOR A 0.\n";
                }
                if (precio < 0.0) {
                    error += "EL PRECIO NO PUEDE SER MENOR A 0.\n";
                }
                if (error.isEmpty()) {
                    a.setDescripcion(descripcion);
                    a.setReferencia(referencia);
                    a.setEstado(estado);
                    a.setIdmarca(idmarca);
                    a.setIdlinea(idlinea);
                    a.setIdunidad(idunidadmedida);
                    a.setIdtipo(idtipoarticulo);
                    a.setCosto(costo);
                    a.setPrecio(precio);
                    a.setIdimpuesto(idimpuesto);
                    a.setIdarticulo(id);
                    dao.modificar(a);
                    cargarTabla();
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    a.setIdarticulo(id);
                    dao.eliminar(a);
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
            a.setIdarticulo(id);
            dao.consultarDatos(a);
            txtCodigo.setText("" + a.getIdarticulo());
            
            txtDescripcion.setText(a.getDescripcion());
            txtReferencia.setText(a.getReferencia());
            if (a.getEstado().equals("A")) {
                checkActivo.setSelected(true);
            } else {
                checkInactivo.setSelected(true);
            }

            m.setIdmarca(a.getIdmarca());
            daoMarca.consultarDatos(m);
            txtMarcaCodigo.setText("" + m.getIdmarca());
            txtMarcaDescripcion.setText(m.getDescripcion());

            l.setIdlinea(a.getIdlinea());
            daoLinea.consultarDatos(l);
            txtLineaCodigo.setText("" + l.getIdlinea());
            txtLineaDescripcion.setText(l.getDescripcion());

            um.setIdunidad(a.getIdunidad());
            daoUnidadMedida.consultarDatos(um);
            txtUnidadMedidaCodigo.setText("" + um.getIdunidad());
            txtUnidadMedidaDescripcion.setText(um.getDescripcion());

            ta.setIdtipo(a.getIdtipo());
            daoTipoArticulo.consultarDatos(ta);
            txtTipoArticuloCodigo.setText("" + ta.getIdtipo());
            txtTipoArticuloDescripcion.setText(ta.getDescripcion());
            
            valorMontoCosto = a.getCosto();
            txtCosto.setText("" + valorMontoCosto);
            valorMontoPrecio = a.getPrecio();
            txtPrecio.setText("" + valorMontoPrecio);
            
            i.setIdimpuesto(a.getIdimpuesto());
            daoImpuesto.consultarDatos(i);
            txtImpuestoCodigo.setText("" + i.getIdimpuesto());
            txtImpuestoDescripcion.setText(i.getDescripcion());

            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarMarca() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosMarca.getModel();
        modelo.setRowCount(0);
        datosMarca = daoMarca.consultar(txtCriterioMarca.getText());
        for (Object[] obj : datosMarca) {
            modelo.addRow(obj);
        }
        this.tablaDatosMarca.setModel(modelo);
    }

    private void buscarMarca() {
        cargarMarca();
        BuscadorMarca.setModal(true);
        BuscadorMarca.setSize(540, 285);
        BuscadorMarca.setLocationRelativeTo(this);
        BuscadorMarca.setVisible(true);
        int fila = tablaDatosMarca.getSelectedRow();
        if (fila >= 0) {
            txtMarcaCodigo.setText(tablaDatosMarca.getValueAt(fila, 0).toString());
            txtMarcaDescripcion.setText(tablaDatosMarca.getValueAt(fila, 1).toString());
        } else {
            txtMarcaCodigo.setText(null);
            txtMarcaDescripcion.setText(null);
        }
    }

    public void cargarLinea() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosLinea.getModel();
        modelo.setRowCount(0);
        datosLinea = daoLinea.consultar(txtCriterioLinea.getText());
        for (Object[] obj : datosLinea) {
            modelo.addRow(obj);
        }
        this.tablaDatosLinea.setModel(modelo);
    }

    private void buscarLinea() {
        cargarLinea();
        BuscadorLinea.setModal(true);
        BuscadorLinea.setSize(540, 285);
        BuscadorLinea.setLocationRelativeTo(this);
        BuscadorLinea.setVisible(true);
        int fila = tablaDatosLinea.getSelectedRow();
        if (fila >= 0) {
            txtLineaCodigo.setText(tablaDatosLinea.getValueAt(fila, 0).toString());
            txtLineaDescripcion.setText(tablaDatosLinea.getValueAt(fila, 1).toString());
        } else {
            txtLineaCodigo.setText(null);
            txtLineaDescripcion.setText(null);
        }
    }

    public void cargarUnidadMedida() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosUnidadMedida.getModel();
        modelo.setRowCount(0);
        datosUnidadMedida = daoUnidadMedida.consultar(txtCriterioUnidadMedida.getText());
        for (Object[] obj : datosUnidadMedida) {
            modelo.addRow(obj);
        }
        this.tablaDatosUnidadMedida.setModel(modelo);
    }

    private void buscarUnidadMedida() {
        cargarUnidadMedida();
        BuscadorUnidadMedida.setModal(true);
        BuscadorUnidadMedida.setSize(540, 285);
        BuscadorUnidadMedida.setLocationRelativeTo(this);
        BuscadorUnidadMedida.setVisible(true);
        int fila = tablaDatosUnidadMedida.getSelectedRow();
        if (fila >= 0) {
            txtUnidadMedidaCodigo.setText(tablaDatosUnidadMedida.getValueAt(fila, 0).toString());
            txtUnidadMedidaDescripcion.setText(tablaDatosUnidadMedida.getValueAt(fila, 1).toString());
        } else {
            txtUnidadMedidaCodigo.setText(null);
            txtUnidadMedidaDescripcion.setText(null);
        }
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

    public void cargarImpuesto() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosImpuesto.getModel();
        modelo.setRowCount(0);
        datosImpuesto = daoImpuesto.consultar(txtCriterioImpuesto.getText());
        for (Object[] obj : datosImpuesto) {
            modelo.addRow(obj);
        }
        this.tablaDatosImpuesto.setModel(modelo);
    }

    private void buscarImpuesto() {
        cargarImpuesto();
        BuscadorImpuesto.setModal(true);
        BuscadorImpuesto.setSize(540, 285);
        BuscadorImpuesto.setLocationRelativeTo(this);
        BuscadorImpuesto.setVisible(true);
        int fila = tablaDatosImpuesto.getSelectedRow();
        if (fila >= 0) {
            txtImpuestoCodigo.setText(tablaDatosImpuesto.getValueAt(fila, 0).toString());
            txtImpuestoDescripcion.setText(tablaDatosImpuesto.getValueAt(fila, 1).toString());
        } else {
            txtImpuestoCodigo.setText(null);
            txtImpuestoDescripcion.setText(null);
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
        grupoOpciones = new javax.swing.ButtonGroup();
        grupoActivoInactivo = new javax.swing.ButtonGroup();
        BuscadorMarca = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtCriterioMarca = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosMarca = new javax.swing.JTable();
        BuscadorLinea = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtCriterioLinea = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosLinea = new org.jdesktop.swingx.JXTable();
        BuscadorUnidadMedida = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtCriterioUnidadMedida = new org.jdesktop.swingx.JXTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaDatosUnidadMedida = new javax.swing.JTable();
        BuscadorTipoArticulo = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtCriterioTipoArticulo = new org.jdesktop.swingx.JXTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaDatosTipoArticulo = new javax.swing.JTable();
        BuscadorImpuesto = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtCriterioImpuesto = new org.jdesktop.swingx.JXTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatosImpuesto = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pestanha = new javax.swing.JTabbedPane();
        panelLista = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCriterio = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatos = new org.jdesktop.swingx.JXTable();
        checkReferencia = new javax.swing.JCheckBox();
        checkDescripcion = new javax.swing.JCheckBox();
        checkIdentificador = new javax.swing.JCheckBox();
        panelOperaciones = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnCancelar = new org.jdesktop.swingx.JXButton();
        btnGuardar = new org.jdesktop.swingx.JXButton();
        btnNuevo = new org.jdesktop.swingx.JXButton();
        txtCodigo = new org.jdesktop.swingx.JXTextField();
        txtDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtReferencia = new org.jdesktop.swingx.JXTextField();
        checkActivo = new javax.swing.JCheckBox();
        checkInactivo = new javax.swing.JCheckBox();
        txtMarcaCodigo = new org.jdesktop.swingx.JXTextField();
        txtMarcaDescripcion = new org.jdesktop.swingx.JXTextField();
        txtLineaCodigo = new org.jdesktop.swingx.JXTextField();
        txtLineaDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        txtUnidadMedidaCodigo = new org.jdesktop.swingx.JXTextField();
        txtUnidadMedidaDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTipoArticuloCodigo = new org.jdesktop.swingx.JXTextField();
        txtTipoArticuloDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCosto = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPrecio = new org.jdesktop.swingx.JXTextField();
        jLabel13 = new javax.swing.JLabel();
        txtImpuestoCodigo = new org.jdesktop.swingx.JXTextField();
        txtImpuestoDescripcion = new org.jdesktop.swingx.JXTextField();

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

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setBackground(new java.awt.Color(50, 104, 151));
        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("BUSCADOR DE MARCAS");
        jLabel18.setOpaque(true);

        txtCriterioMarca.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioMarca.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioMarcaActionPerformed(evt);
            }
        });
        txtCriterioMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioMarcaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioMarcaKeyTyped(evt);
            }
        });

        tablaDatosMarca.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosMarcaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosMarca);
        if (tablaDatosMarca.getColumnModel().getColumnCount() > 0) {
            tablaDatosMarca.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosMarca.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosMarca.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioMarca, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorMarcaLayout = new javax.swing.GroupLayout(BuscadorMarca.getContentPane());
        BuscadorMarca.getContentPane().setLayout(BuscadorMarcaLayout);
        BuscadorMarcaLayout.setHorizontalGroup(
            BuscadorMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorMarcaLayout.setVerticalGroup(
            BuscadorMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setBackground(new java.awt.Color(50, 104, 151));
        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("BUSCADOR DE LÍNEAS");
        jLabel17.setOpaque(true);

        txtCriterioLinea.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioLinea.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioLineaActionPerformed(evt);
            }
        });
        txtCriterioLinea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioLineaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioLineaKeyTyped(evt);
            }
        });

        tablaDatosLinea.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosLinea.setInheritsPopupMenu(true);
        tablaDatosLinea.setShowGrid(true);
        tablaDatosLinea.getTableHeader().setResizingAllowed(false);
        tablaDatosLinea.getTableHeader().setReorderingAllowed(false);
        tablaDatosLinea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosLineaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaDatosLinea);
        if (tablaDatosLinea.getColumnModel().getColumnCount() > 0) {
            tablaDatosLinea.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosLinea.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosLinea.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioLinea, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioLinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorLineaLayout = new javax.swing.GroupLayout(BuscadorLinea.getContentPane());
        BuscadorLinea.getContentPane().setLayout(BuscadorLineaLayout);
        BuscadorLineaLayout.setHorizontalGroup(
            BuscadorLineaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorLineaLayout.setVerticalGroup(
            BuscadorLineaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setBackground(new java.awt.Color(50, 104, 151));
        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("BUSCADOR DE UNIDADES DE MEDIDA");
        jLabel19.setOpaque(true);

        txtCriterioUnidadMedida.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioUnidadMedida.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioUnidadMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioUnidadMedidaActionPerformed(evt);
            }
        });
        txtCriterioUnidadMedida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioUnidadMedidaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioUnidadMedidaKeyTyped(evt);
            }
        });

        tablaDatosUnidadMedida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Identificador", "Descripcion", "Abreviacion"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
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
        tablaDatosUnidadMedida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosUnidadMedidaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaDatosUnidadMedida);
        if (tablaDatosUnidadMedida.getColumnModel().getColumnCount() > 0) {
            tablaDatosUnidadMedida.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosUnidadMedida.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosUnidadMedida.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioUnidadMedida, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorUnidadMedidaLayout = new javax.swing.GroupLayout(BuscadorUnidadMedida.getContentPane());
        BuscadorUnidadMedida.getContentPane().setLayout(BuscadorUnidadMedidaLayout);
        BuscadorUnidadMedidaLayout.setHorizontalGroup(
            BuscadorUnidadMedidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorUnidadMedidaLayout.setVerticalGroup(
            BuscadorUnidadMedidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setBackground(new java.awt.Color(50, 104, 151));
        jLabel20.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("BUSCADOR DE TIPOS DE ARTICULOS");
        jLabel20.setOpaque(true);

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
        tablaDatosTipoArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosTipoArticuloMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tablaDatosTipoArticulo);
        if (tablaDatosTipoArticulo.getColumnModel().getColumnCount() > 0) {
            tablaDatosTipoArticulo.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosTipoArticulo.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosTipoArticulo.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioTipoArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane6))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioTipoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorTipoArticuloLayout = new javax.swing.GroupLayout(BuscadorTipoArticulo.getContentPane());
        BuscadorTipoArticulo.getContentPane().setLayout(BuscadorTipoArticuloLayout);
        BuscadorTipoArticuloLayout.setHorizontalGroup(
            BuscadorTipoArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorTipoArticuloLayout.setVerticalGroup(
            BuscadorTipoArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setBackground(new java.awt.Color(50, 104, 151));
        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("BUSCADOR DE IMPUESTOS");
        jLabel16.setOpaque(true);

        txtCriterioImpuesto.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioImpuesto.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioImpuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioImpuestoActionPerformed(evt);
            }
        });
        txtCriterioImpuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioImpuestoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioImpuestoKeyTyped(evt);
            }
        });

        tablaDatosImpuesto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Identificador", "Descripcion", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
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
        tablaDatosImpuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosImpuestoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDatosImpuesto);
        if (tablaDatosImpuesto.getColumnModel().getColumnCount() > 0) {
            tablaDatosImpuesto.getColumnModel().getColumn(0).setMinWidth(100);
            tablaDatosImpuesto.getColumnModel().getColumn(0).setPreferredWidth(100);
            tablaDatosImpuesto.getColumnModel().getColumn(0).setMaxWidth(100);
            tablaDatosImpuesto.getColumnModel().getColumn(2).setMinWidth(100);
            tablaDatosImpuesto.getColumnModel().getColumn(2).setPreferredWidth(100);
            tablaDatosImpuesto.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioImpuesto, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorImpuestoLayout = new javax.swing.GroupLayout(BuscadorImpuesto.getContentPane());
        BuscadorImpuesto.getContentPane().setLayout(BuscadorImpuestoLayout);
        BuscadorImpuestoLayout.setHorizontalGroup(
            BuscadorImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorImpuestoLayout.setVerticalGroup(
            BuscadorImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(0, 153, 51));

        jLabel1.setBackground(new java.awt.Color(0, 153, 51));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Formulario de Artículos");
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
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Identificador", "Descripcion", "Referencia", "Estado"
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
            tablaDatos.getColumnModel().getColumn(3).setMinWidth(100);
            tablaDatos.getColumnModel().getColumn(3).setPreferredWidth(100);
            tablaDatos.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        grupoOpciones.add(checkReferencia);
        checkReferencia.setText("Referencia");

        grupoOpciones.add(checkDescripcion);
        checkDescripcion.setSelected(true);
        checkDescripcion.setText("Descripción");

        grupoOpciones.add(checkIdentificador);
        checkIdentificador.setText("Identificador");

        javax.swing.GroupLayout panelListaLayout = new javax.swing.GroupLayout(panelLista);
        panelLista.setLayout(panelListaLayout);
        panelListaLayout.setHorizontalGroup(
            panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListaLayout.createSequentialGroup()
                .addGroup(panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
                    .addGroup(panelListaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelListaLayout.createSequentialGroup()
                                .addComponent(checkIdentificador)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkDescripcion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkReferencia)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelListaLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        panelListaLayout.setVerticalGroup(
            panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkDescripcion)
                    .addComponent(checkReferencia)
                    .addComponent(checkIdentificador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
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

        jLabel5.setText("Referencia:");

        jLabel6.setText("Estado:");

        jLabel7.setText("Marca:");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Linea:");

        txtReferencia.setEnabled(false);
        txtReferencia.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtReferencia.setPrompt("Referencias visibles del articulo...");
        txtReferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReferenciaActionPerformed(evt);
            }
        });
        txtReferencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtReferenciaKeyTyped(evt);
            }
        });

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

        txtMarcaCodigo.setEnabled(false);
        txtMarcaCodigo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtMarcaCodigo.setPrompt("");
        txtMarcaCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarcaCodigoActionPerformed(evt);
            }
        });
        txtMarcaCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMarcaCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarcaCodigoKeyTyped(evt);
            }
        });

        txtMarcaDescripcion.setEnabled(false);
        txtMarcaDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtMarcaDescripcion.setPrompt("Descripcion...");

        txtLineaCodigo.setEnabled(false);
        txtLineaCodigo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtLineaCodigo.setPrompt("");
        txtLineaCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLineaCodigoActionPerformed(evt);
            }
        });
        txtLineaCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLineaCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLineaCodigoKeyTyped(evt);
            }
        });

        txtLineaDescripcion.setEnabled(false);
        txtLineaDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtLineaDescripcion.setPrompt("Descripcion...");

        jLabel9.setText("Unidad de M.:");

        txtUnidadMedidaCodigo.setEnabled(false);
        txtUnidadMedidaCodigo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtUnidadMedidaCodigo.setPrompt("");
        txtUnidadMedidaCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnidadMedidaCodigoActionPerformed(evt);
            }
        });
        txtUnidadMedidaCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUnidadMedidaCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUnidadMedidaCodigoKeyTyped(evt);
            }
        });

        txtUnidadMedidaDescripcion.setEnabled(false);
        txtUnidadMedidaDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtUnidadMedidaDescripcion.setPrompt("Descripcion...");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Tipo Art.:");

        txtTipoArticuloCodigo.setEnabled(false);
        txtTipoArticuloCodigo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTipoArticuloCodigo.setPrompt("");
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

        txtTipoArticuloDescripcion.setEnabled(false);
        txtTipoArticuloDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtTipoArticuloDescripcion.setPrompt("Descripcion...");

        jLabel11.setText("Costo:");

        txtCosto.setEnabled(false);
        txtCosto.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtCosto.setPrompt("");
        txtCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoActionPerformed(evt);
            }
        });
        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoKeyTyped(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Precio:");

        txtPrecio.setEnabled(false);
        txtPrecio.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtPrecio.setPrompt("");
        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });
        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        jLabel13.setText("Impuesto:");

        txtImpuestoCodigo.setEnabled(false);
        txtImpuestoCodigo.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtImpuestoCodigo.setPrompt("");
        txtImpuestoCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImpuestoCodigoActionPerformed(evt);
            }
        });
        txtImpuestoCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtImpuestoCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtImpuestoCodigoKeyTyped(evt);
            }
        });

        txtImpuestoDescripcion.setEnabled(false);
        txtImpuestoDescripcion.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        txtImpuestoDescripcion.setPrompt("Descripcion...");

        javax.swing.GroupLayout panelOperacionesLayout = new javax.swing.GroupLayout(panelOperaciones);
        panelOperaciones.setLayout(panelOperacionesLayout);
        panelOperacionesLayout.setHorizontalGroup(
            panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOperacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOperacionesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtReferencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelOperacionesLayout.createSequentialGroup()
                                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                                        .addComponent(checkActivo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(checkInactivo)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelOperacionesLayout.createSequentialGroup()
                                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtCosto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelOperacionesLayout.createSequentialGroup()
                                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtUnidadMedidaCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                            .addComponent(txtMarcaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtMarcaDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                            .addComponent(txtUnidadMedidaDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtLineaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTipoArticuloCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtLineaDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                            .addComponent(txtTipoArticuloDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(txtPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(panelOperacionesLayout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtImpuestoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtImpuestoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkActivo)
                    .addComponent(checkInactivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarcaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarcaDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLineaDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLineaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUnidadMedidaCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUnidadMedidaDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipoArticuloCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTipoArticuloDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtImpuestoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtImpuestoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(panelOperacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addComponent(pestanha))
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
                boolean existe = dao.verificarDuplicacion(txtDescripcion.getText(),"descripcion");
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
                boolean existe = dao.verificarDuplicacion(txtDescripcion.getText(), "descripcion");
                if (existe == true) {
                    JOptionPane.showMessageDialog(null, "LA DESCRIPCION INGRESADA YA ESTA REGISTRADA EN LA BASE DE DATOS");
                } else {
                    txtReferencia.grabFocus();
                }
            } else {
                txtReferencia.grabFocus();
            }
        }
    }//GEN-LAST:event_txtDescripcionActionPerformed

    private void txtReferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReferenciaActionPerformed
        if (txtReferencia.getText().isEmpty()) {
            if (operacion.equals("NUEVO")) {
                boolean existe = dao.verificarDuplicacion(txtReferencia.getText(), "referencia");
                if (existe == true) {
                    JOptionPane.showMessageDialog(null, "LA REFERENCIA INGRESADA YA ESTA REGISTRADA EN LA BASE DE DATOS");
                } else {
                    checkActivo.grabFocus();
                }
            } else {
                checkActivo.grabFocus();
            }
        } else {
            checkActivo.grabFocus();
        }
    }//GEN-LAST:event_txtReferenciaActionPerformed

    private void txtReferenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReferenciaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReferenciaKeyTyped

    private void checkActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkActivoActionPerformed
        txtMarcaCodigo.grabFocus();
    }//GEN-LAST:event_checkActivoActionPerformed

    private void checkActivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_checkActivoKeyPressed
        txtMarcaCodigo.grabFocus();
    }//GEN-LAST:event_checkActivoKeyPressed

    private void checkInactivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkInactivoActionPerformed
        txtMarcaCodigo.grabFocus();
    }//GEN-LAST:event_checkInactivoActionPerformed

    private void checkInactivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_checkInactivoKeyPressed
        txtMarcaCodigo.grabFocus();
    }//GEN-LAST:event_checkInactivoKeyPressed

    private void txtMarcaCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarcaCodigoActionPerformed
        if (txtMarcaCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idmarca = Integer.parseInt(txtMarcaCodigo.getText());
            m.setIdmarca(idmarca);
            boolean resultado = daoMarca.consultarDatos(m);
            if (resultado == true) {
                txtMarcaDescripcion.setText(m.getDescripcion());
                txtLineaCodigo.grabFocus();
            } else {
                txtMarcaCodigo.setText(null);
                txtMarcaDescripcion.setText(null);
                txtMarcaCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtMarcaCodigoActionPerformed

    private void txtMarcaCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarcaCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarMarca();
        }
    }//GEN-LAST:event_txtMarcaCodigoKeyPressed

    private void txtMarcaCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarcaCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtMarcaCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMarcaCodigoKeyTyped

    private void txtCriterioMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioMarcaActionPerformed
        cargarMarca();
    }//GEN-LAST:event_txtCriterioMarcaActionPerformed

    private void txtCriterioMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioMarcaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtMarcaCodigo.setText(null);
            txtMarcaDescripcion.setText(null);
            txtMarcaCodigo.grabFocus();
            BuscadorMarca.dispose();
        }
    }//GEN-LAST:event_txtCriterioMarcaKeyPressed

    private void txtCriterioMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioMarcaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioMarca.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioMarcaKeyTyped

    private void tablaDatosMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosMarcaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosMarca.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioMarca.setText(null);
                BuscadorMarca.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosMarcaMouseClicked

    private void txtLineaCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLineaCodigoActionPerformed
        if (txtLineaCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idlinea = Integer.parseInt(txtLineaCodigo.getText());
            l.setIdlinea(idlinea);
            boolean resultado = daoLinea.consultarDatos(l);
            if (resultado == true) {
                txtLineaDescripcion.setText(l.getDescripcion());
                txtUnidadMedidaCodigo.grabFocus();
            } else {
                txtLineaCodigo.setText(null);
                txtLineaDescripcion.setText(null);
                txtLineaCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtLineaCodigoActionPerformed

    private void txtLineaCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLineaCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarLinea();
        }
    }//GEN-LAST:event_txtLineaCodigoKeyPressed

    private void txtLineaCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLineaCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtLineaCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtLineaCodigoKeyTyped

    private void txtCriterioLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioLineaActionPerformed
        cargarLinea();
    }//GEN-LAST:event_txtCriterioLineaActionPerformed

    private void txtCriterioLineaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioLineaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtLineaCodigo.setText(null);
            txtLineaDescripcion.setText(null);
            txtLineaCodigo.grabFocus();
            BuscadorLinea.dispose();
        }
    }//GEN-LAST:event_txtCriterioLineaKeyPressed

    private void txtCriterioLineaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioLineaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioLinea.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioLineaKeyTyped

    private void tablaDatosLineaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosLineaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosLinea.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioLinea.setText(null);
                BuscadorLinea.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosLineaMouseClicked

    private void txtUnidadMedidaCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUnidadMedidaCodigoActionPerformed
        if (txtUnidadMedidaCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idunidad = Integer.parseInt(txtUnidadMedidaCodigo.getText());
            um.setIdunidad(idunidad);
            boolean resultado = daoUnidadMedida.consultarDatos(um);
            if (resultado == true) {
                txtUnidadMedidaDescripcion.setText(um.getDescripcion());
                txtTipoArticuloCodigo.grabFocus();
            } else {
                txtUnidadMedidaCodigo.setText(null);
                txtUnidadMedidaDescripcion.setText(null);
                txtUnidadMedidaCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtUnidadMedidaCodigoActionPerformed

    private void txtUnidadMedidaCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnidadMedidaCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarUnidadMedida();
        }
    }//GEN-LAST:event_txtUnidadMedidaCodigoKeyPressed

    private void txtUnidadMedidaCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnidadMedidaCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtUnidadMedidaCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtUnidadMedidaCodigoKeyTyped

    private void txtCriterioUnidadMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioUnidadMedidaActionPerformed
        cargarUnidadMedida();
    }//GEN-LAST:event_txtCriterioUnidadMedidaActionPerformed

    private void txtCriterioUnidadMedidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioUnidadMedidaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtUnidadMedidaCodigo.setText(null);
            txtUnidadMedidaDescripcion.setText(null);
            txtUnidadMedidaCodigo.grabFocus();
            BuscadorUnidadMedida.dispose();
        }
    }//GEN-LAST:event_txtCriterioUnidadMedidaKeyPressed

    private void txtCriterioUnidadMedidaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioUnidadMedidaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioUnidadMedida.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioUnidadMedidaKeyTyped

    private void tablaDatosUnidadMedidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosUnidadMedidaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosUnidadMedida.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioUnidadMedida.setText(null);
                BuscadorUnidadMedida.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosUnidadMedidaMouseClicked

    private void txtTipoArticuloCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoArticuloCodigoActionPerformed
        if (txtTipoArticuloCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipo = Integer.parseInt(txtTipoArticuloCodigo.getText());
            ta.setIdtipo(idtipo);
            boolean resultado = daoTipoArticulo.consultarDatos(ta);
            if (resultado == true) {
                txtTipoArticuloDescripcion.setText(ta.getDescripcion());
                txtImpuestoCodigo.grabFocus();
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

    private void txtCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoActionPerformed
        if (txtCosto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE COSTO VACIO", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        } else {
            String number = txtCosto.getText();
            double monto = Double.parseDouble(number);
            if (monto < 0) {
                JOptionPane.showMessageDialog(null, "EL COSTO NO PUEDE SER MENOR A 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                valorMontoCosto = monto;
                DecimalFormat formatter = new DecimalFormat("#,###");
                txtCosto.setText(formatter.format(monto));
                txtPrecio.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCostoActionPerformed

    private void txtCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (c == ',') {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCosto.getText().length() == 28) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCostoKeyTyped

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        if (txtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE PRECIO VACIO", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        } else {
            String number = txtPrecio.getText();
            double monto = Double.parseDouble(number);
            if (monto < 0) {
                JOptionPane.showMessageDialog(null, "EL PRECIO NO PUEDE SER MENOR A 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                valorMontoPrecio = monto;
                DecimalFormat formatter = new DecimalFormat("#,###");
                txtPrecio.setText(formatter.format(monto));
                btnGuardar.grabFocus();
            }
        }
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (c == ',') {
            getToolkit().beep();
            evt.consume();
        }
        if (txtPrecio.getText().length() == 28) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioKeyTyped

    private void txtImpuestoCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImpuestoCodigoActionPerformed
        if (txtImpuestoCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idimpuesto = Integer.parseInt(txtImpuestoCodigo.getText());
            i.setIdimpuesto(idimpuesto);
            boolean resultado = daoImpuesto.consultarDatos(i);
            if (resultado == true) {
                txtImpuestoDescripcion.setText(i.getDescripcion());
                txtCosto.grabFocus();
            } else {
                txtImpuestoCodigo.setText(null);
                txtImpuestoDescripcion.setText(null);
                txtImpuestoCodigo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtImpuestoCodigoActionPerformed

    private void txtImpuestoCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImpuestoCodigoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarImpuesto();
        }
    }//GEN-LAST:event_txtImpuestoCodigoKeyPressed

    private void txtImpuestoCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtImpuestoCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtImpuestoCodigo.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_txtImpuestoCodigoKeyTyped

    private void txtCriterioImpuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioImpuestoActionPerformed
        cargarImpuesto();
    }//GEN-LAST:event_txtCriterioImpuestoActionPerformed

    private void txtCriterioImpuestoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioImpuestoKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtImpuestoCodigo.setText(null);
            txtImpuestoDescripcion.setText(null);
            txtImpuestoCodigo.grabFocus();
            BuscadorImpuesto.dispose();
        }
    }//GEN-LAST:event_txtCriterioImpuestoKeyPressed

    private void txtCriterioImpuestoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioImpuestoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioImpuesto.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioImpuestoKeyTyped

    private void tablaDatosImpuestoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosImpuestoMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosImpuesto.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioImpuesto.setText(null);
                BuscadorImpuesto.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosImpuestoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorImpuesto;
    private javax.swing.JDialog BuscadorLinea;
    private javax.swing.JDialog BuscadorMarca;
    private javax.swing.JDialog BuscadorTipoArticulo;
    private javax.swing.JDialog BuscadorUnidadMedida;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Modificar;
    private org.jdesktop.swingx.JXButton btnCancelar;
    private org.jdesktop.swingx.JXButton btnGuardar;
    private org.jdesktop.swingx.JXButton btnNuevo;
    private javax.swing.JCheckBox checkActivo;
    private javax.swing.JCheckBox checkDescripcion;
    private javax.swing.JCheckBox checkIdentificador;
    private javax.swing.JCheckBox checkInactivo;
    private javax.swing.JCheckBox checkReferencia;
    private javax.swing.ButtonGroup grupoActivoInactivo;
    private javax.swing.ButtonGroup grupoOpciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPopupMenu menuDesplegable;
    private javax.swing.JPanel panelLista;
    private javax.swing.JPanel panelOperaciones;
    private javax.swing.JTabbedPane pestanha;
    private org.jdesktop.swingx.JXTable tablaDatos;
    private javax.swing.JTable tablaDatosImpuesto;
    private org.jdesktop.swingx.JXTable tablaDatosLinea;
    private javax.swing.JTable tablaDatosMarca;
    private javax.swing.JTable tablaDatosTipoArticulo;
    private javax.swing.JTable tablaDatosUnidadMedida;
    private org.jdesktop.swingx.JXTextField txtCodigo;
    private org.jdesktop.swingx.JXTextField txtCosto;
    private org.jdesktop.swingx.JXTextField txtCriterio;
    private org.jdesktop.swingx.JXTextField txtCriterioImpuesto;
    private org.jdesktop.swingx.JXTextField txtCriterioLinea;
    private org.jdesktop.swingx.JXTextField txtCriterioMarca;
    private org.jdesktop.swingx.JXTextField txtCriterioTipoArticulo;
    private org.jdesktop.swingx.JXTextField txtCriterioUnidadMedida;
    private org.jdesktop.swingx.JXTextField txtDescripcion;
    private org.jdesktop.swingx.JXTextField txtImpuestoCodigo;
    private org.jdesktop.swingx.JXTextField txtImpuestoDescripcion;
    private org.jdesktop.swingx.JXTextField txtLineaCodigo;
    private org.jdesktop.swingx.JXTextField txtLineaDescripcion;
    private org.jdesktop.swingx.JXTextField txtMarcaCodigo;
    private org.jdesktop.swingx.JXTextField txtMarcaDescripcion;
    private org.jdesktop.swingx.JXTextField txtPrecio;
    private org.jdesktop.swingx.JXTextField txtReferencia;
    private org.jdesktop.swingx.JXTextField txtTipoArticuloCodigo;
    private org.jdesktop.swingx.JXTextField txtTipoArticuloDescripcion;
    private org.jdesktop.swingx.JXTextField txtUnidadMedidaCodigo;
    private org.jdesktop.swingx.JXTextField txtUnidadMedidaDescripcion;
    // End of variables declaration//GEN-END:variables
}
