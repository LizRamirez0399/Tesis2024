/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author Liz
 */
public class ModConfiguracion {
    private int idconfiguracion;
    private int tipo_articulo_servicio;
    private int cantidad_maxima_facturacion;
    private int facturacion_timbrado;
    private int nota_credito_timbrado;
    private int nota_debito_timbrado;

    public int getIdconfiguracion() {
        return idconfiguracion;
    }

    public void setIdconfiguracion(int idconfiguracion) {
        this.idconfiguracion = idconfiguracion;
    }

    public int getTipo_articulo_servicio() {
        return tipo_articulo_servicio;
    }

    public void setTipo_articulo_servicio(int tipo_articulo_servicio) {
        this.tipo_articulo_servicio = tipo_articulo_servicio;
    }

    public int getCantidad_maxima_facturacion() {
        return cantidad_maxima_facturacion;
    }

    public void setCantidad_maxima_facturacion(int cantidad_maxima_facturacion) {
        this.cantidad_maxima_facturacion = cantidad_maxima_facturacion;
    }

    public int getFacturacion_timbrado() {
        return facturacion_timbrado;
    }

    public void setFacturacion_timbrado(int facturacion_timbrado) {
        this.facturacion_timbrado = facturacion_timbrado;
    }

    public int getNota_credito_timbrado() {
        return nota_credito_timbrado;
    }

    public void setNota_credito_timbrado(int nota_credito_timbrado) {
        this.nota_credito_timbrado = nota_credito_timbrado;
    }

    public int getNota_debito_timbrado() {
        return nota_debito_timbrado;
    }

    public void setNota_debito_timbrado(int nota_debito_timbrado) {
        this.nota_debito_timbrado = nota_debito_timbrado;
    }
    
}
