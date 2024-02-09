/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.sql.Date;

/**
 *
 * @author Liz
 */
public class ModServicio {
    private int idservicio;
    private String numero_documento;
    private int numero_timbrado;
    private Date fecha;
    private String observacion;
    private int idcliente;
    private int idusuario;
    private int iddeposito;

    public int getIdservicio() {
        return idservicio;
    }

    public void setIdservicio(int idservicio) {
        this.idservicio = idservicio;
    }

    public String getNumero_documento() {
        return numero_documento;
    }

    public void setNumero_documento(String numero_documento) {
        this.numero_documento = numero_documento;
    }

    public int getNumero_timbrado() {
        return numero_timbrado;
    }

    public void setNumero_timbrado(int numero_timbrado) {
        this.numero_timbrado = numero_timbrado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIddeposito() {
        return iddeposito;
    }

    public void setIddeposito(int iddeposito) {
        this.iddeposito = iddeposito;
    }
    
}
