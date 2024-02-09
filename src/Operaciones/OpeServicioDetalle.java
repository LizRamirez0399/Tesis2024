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
public class OpeServicioDetalle implements Controladores.ConServicioDetalle {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModServicioDetalle sd = new Modelos.ModServicioDetalle();

    @Override
    public boolean agregar(Object obj) {
        sd = (Modelos.ModServicioDetalle) obj;
        String sql = "INSERT INTO servicio_detalle\n"
                + "(idservicio, idarticulo, numero_item, cantidad, precio, observacion)\n"
                + "VALUES (?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, sd.getIdservicio());
            ps.setInt(2, sd.getIdarticulo());
            ps.setInt(3, sd.getNumero_item());
            ps.setDouble(4, sd.getCantidad());
            ps.setDouble(5, sd.getPrecio());
            ps.setString(6, sd.getObservacion());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL INSERTAR LOS DATOS DEL DETALLE DEL SERVICIO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean eliminar(Object obj) {
        sd = (Modelos.ModServicioDetalle) obj;
        String sql = "DELETE FROM servicio_detalle WHERE idservicio=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, sd.getIdservicio());
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
        String sql = "SELECT * FROM servicio_detalle_v AS A WHERE A.SERVICIO_DETALLE_CODIGO = ?;";
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
                Object[] fila = new Object[7];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getInt(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getInt(4);
                fila[4] = rs.getDouble(5);
                fila[5] = rs.getDouble(6);
                fila[6] = rs.getString(7);
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
        sd = (Modelos.ModServicioDetalle) obj;
        String sql = "SELECT * FROM servicio_detalle WHERE idventa = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, sd.getIdservicio());
            rs = ps.executeQuery();
            if (rs.next()) {
                sd.setIdservicio(rs.getInt(1));
                sd.setIdarticulo(rs.getInt(2));
                sd.setNumero_item(rs.getInt(3));
                sd.setCantidad(rs.getInt(4));
                sd.setPrecio(rs.getInt(5));
                sd.setObservacion(rs.getString(6));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE DETALLE DEL SERVICIO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
