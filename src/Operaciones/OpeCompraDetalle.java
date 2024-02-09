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
public class OpeCompraDetalle implements Controladores.ConCompraDetalle {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModCompraDetalle cd = new Modelos.ModCompraDetalle();

    @Override
    public boolean agregar(Object obj) {
        cd = (Modelos.ModCompraDetalle) obj;
        String sql = "INSERT INTO compra_detalle\n"
                + "(idcompra, idarticulo, numero_item, cantidad, costo)\n"
                + "VALUES (?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, cd.getIdcompra());
            ps.setInt(2, cd.getIdarticulo());
            ps.setInt(3, cd.getNumero_item());
            ps.setDouble(4, cd.getCantidad());
            ps.setDouble(5, cd.getCosto());
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
        cd = (Modelos.ModCompraDetalle) obj;
        String sql = "DELETE FROM compra_detalle WHERE idcompra=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, cd.getIdcompra());
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
    public ArrayList<Object[]> consultar(String criterio) {
        String sql = "SELECT * FROM compra_detalle_v AS A WHERE A.COMPRA_DETALLE_CODIGO = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + criterio + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getInt(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getInt(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getInt(7);
                fila[7] = rs.getDouble(8);
                fila[8] = rs.getDouble(9);
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
        cd = (Modelos.ModCompraDetalle) obj;
        String sql = "SELECT * FROM compra_detalle WHERE idcompra = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, cd.getIdcompra());
            rs = ps.executeQuery();
            if (rs.next()) {
                cd.setIdcompra(rs.getInt(1));
                cd.setIdarticulo(rs.getInt(2));
                cd.setNumero_item(rs.getInt(3));
                cd.setCantidad(rs.getInt(4));
                cd.setCosto(rs.getInt(5));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE DETALLE DE COMPRA CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
