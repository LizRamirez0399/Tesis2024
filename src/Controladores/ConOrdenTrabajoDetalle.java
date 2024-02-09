/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import java.util.ArrayList;

/**
 *
 * @author Liz
 */
public interface ConOrdenTrabajoDetalle {
    public boolean agregar(Object obj);
    public boolean eliminar(Object obj);
    public ArrayList<Object[]> consultar(Object obj);
    public boolean consultarDatos(Object obj);
}
