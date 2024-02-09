/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Operaciones;

import Controladores.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Liz
 */
public class OpeOrdenTrabajoDetalle implements Controladores.ConOrdenTrabajoDetalle {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModOrdenTrabajoDetalle otd = new Modelos.ModOrdenTrabajoDetalle();

    @Override
    public boolean agregar(Object obj) {
        otd = (Modelos.ModOrdenTrabajoDetalle) obj;
        String sql = "INSERT INTO orden_trabajo_detalle\n"
                + "(idorden, idarticulo, numero_item, cantidad, precio, observacion)\n"
                + "VALUES (?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, otd.getIdorden());
            ps.setInt(2, otd.getIdarticulo());
            ps.setInt(3, otd.getNumero_item());
            ps.setDouble(4, otd.getCantidad());
            ps.setDouble(5, otd.getPrecio());
            ps.setString(6, otd.getObservacion());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL INSERTAR LOS DATOS DEL DETALLE DE VENTAS\n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean eliminar(Object obj) {
        otd = (Modelos.ModOrdenTrabajoDetalle) obj;
        String sql = "DELETE FROM orden_trabajo_detalle WHERE idorden=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, otd.getIdorden());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL ELIMINAR LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public ArrayList<Object[]> consultar(Object obj) {
        otd = (Modelos.ModOrdenTrabajoDetalle) obj;
        String sql = "SELECT\n"
                + "OTD.idarticulo,\n"
                + "A.descripcion,\n"
                + "OTD.numero_item,\n"
                + "OTD.cantidad,\n"
                + "OTD.precio,\n"
                + "OTD.observacion\n"
                + "FROM orden_trabajo_detalle AS OTD\n"
                + "INNER JOIN articulo AS A ON A.idarticulo = OTD.idarticulo\n"
                + "WHERE OTD.idorden = ?\n"
                + "ORDER BY OTD.numero_item;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, otd.getIdorden());
            rs = ps.executeQuery();
            while (rs.next()) {
                double precio = rs.getDouble("OTD.precio");
                double cantidad = rs.getDouble("OTD.cantidad");
                Object[] fila = new Object[8];
                fila[0] = rs.getInt("OTD.numero_item");
                fila[1] = rs.getString("OTD.idarticulo");
                fila[2] = rs.getInt("OTD.idarticulo");
                fila[3] = rs.getString("A.descripcion");
                fila[4] = precio;
                fila[5] = cantidad;
                fila[6] = (precio * cantidad);
                fila[7] = rs.getString("OTD.observacion");
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER LA LISTA DE LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

    @Override
    public boolean consultarDatos(Object obj) {
        otd = (Modelos.ModOrdenTrabajoDetalle) obj;
        String sql = "SELECT * FROM orden_trabajo_detalle WHERE idorden = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, otd.getIdorden());
            rs = ps.executeQuery();
            if (rs.next()) {
                otd.setIdorden(rs.getInt(1));
                otd.setIdarticulo(rs.getInt(2));
                otd.setNumero_item(rs.getInt(3));
                otd.setCantidad(rs.getInt(4));
                otd.setPrecio(rs.getInt(5));
                otd.setObservacion(rs.getString(6));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE DETALLE DE ORDEN DE TRABAJO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
