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
public class ModTimbrado {
    private int idtimbrado;
    private int timbrado;
    private Date fecha_inicial;
    private Date fecha_final;
    private int numero_desde;
    private int numero_hasta;
    private int establecimiento;
    private int punto_emision;
    private int idtipo;

    public int getIdtimbrado() {
        return idtimbrado;
    }

    public void setIdtimbrado(int idtimbrado) {
        this.idtimbrado = idtimbrado;
    }

    public int getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(int timbrado) {
        this.timbrado = timbrado;
    }

    public Date getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(Date fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public Date getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }

    public int getNumero_desde() {
        return numero_desde;
    }

    public void setNumero_desde(int numero_desde) {
        this.numero_desde = numero_desde;
    }

    public int getNumero_hasta() {
        return numero_hasta;
    }

    public void setNumero_hasta(int numero_hasta) {
        this.numero_hasta = numero_hasta;
    }

    public int getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(int establecimiento) {
        this.establecimiento = establecimiento;
    }

    public int getPunto_emision() {
        return punto_emision;
    }

    public void setPunto_emision(int punto_emision) {
        this.punto_emision = punto_emision;
    }

    public int getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }
    
    
}
