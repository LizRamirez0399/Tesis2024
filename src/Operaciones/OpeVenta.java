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
public class OpeVenta implements Controladores.ConVenta {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModVenta v = new Modelos.ModVenta();

    @Override
    public boolean agregar(Object obj) {
        v = (Modelos.ModVenta) obj;
        String sql = "INSERT INTO venta\n"
                + "(idventa, numero_documento, numero_timbrado, fecha, observacion, idcliente, iddeposito, idusuario)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdventa());
            ps.setString(2, v.getNumero_documento());
            ps.setInt(3, v.getNumero_timbrado());
            ps.setDate(4, v.getFecha());
            ps.setString(5, v.getObservacion());
            ps.setInt(6, v.getIdcliente());
            ps.setInt(7, v.getIddeposito());
            ps.setInt(8, v.getIdusuario());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL INSERTAR LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean eliminar(Object obj) {
        v = (Modelos.ModVenta) obj;
        String sql = "DELETE FROM venta WHERE idventa=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdventa());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "ELIMINACIÓN EXITOSA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
    public int nuevoID() {
        String sql = "select idventa + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idventa\n"
                + "         union all\n"
                + "        select idventa\n"
                + "          from venta) t1\n"
                + " where not exists (select null\n"
                + "                     from venta t2\n"
                + "                    where t2.idventa = t1.idventa + 1)\n"
                + " order by idventa\n"
                + " LIMIT 1;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        int id = 0;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER UN NUEVO CÓDIGO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return id;
    }

    @Override
    public ArrayList<Object[]> consultar(String criterio) {
        String sql = "SELECT \n"
                + "A.VENTA_CODIGO, \n"
                + "A.VENTA_NUMERO_DOCUMENTO, \n"
                + "A.VENTA_NUMERO_TIMBRADO,\n"
                + "A.CLIENTE_CODIGO,\n"
                + "A.CLIENTE_DESCRIPCION,\n"
                + "A.VENTA_FECHA  \n"
                + "FROM venta_v AS A\n"
                + "WHERE CONCAT(A.VENTA_NUMERO_DOCUMENTO, A.VENTA_NUMERO_TIMBRADO, \n"
                + "A.VENTA_FECHA, A.CLIENTE_DESCRIPCION, \n"
                + "A.CLIENTE_CODIGO, A.VENTA_CODIGO) LIKE ?;";
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
                Object[] fila = new Object[6];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getInt(5);
                fila[5] = rs.getString(6);
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
        v = (Modelos.ModVenta) obj;
        String sql = "SELECT * FROM venta WHERE idventa = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdventa());
            rs = ps.executeQuery();
            if (rs.next()) {
                v.setIdventa(rs.getInt(1));
                v.setNumero_comprobante(rs.getString(2));
                v.setNumero_timbrado(rs.getInt(3));
                v.setFecha(rs.getDate(4));
                v.setObservacion(rs.getString(5));
                v.setIdcliente(rs.getInt(6));
                v.setIddeposito(rs.getInt(7));
                v.setIdusuario(rs.getInt(8));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE VENTA CON LOS DATOS INGRESADOS...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean verificarDuplicacion(String numero_documento, String numero_timbrado) {
        String sql = "SELECT\n"
                + "COUNT(*) AS EXISTE \n"
                + "FROM venta AS V\n"
                + "WHERE V.numero_documento = ?\n"
                + "AND V.numero_timbrado = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        boolean resultado = false;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, numero_documento);
            ps.setString(2, numero_timbrado);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    return true;
                }
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL CONSULTAR \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return resultado;
    }

}
