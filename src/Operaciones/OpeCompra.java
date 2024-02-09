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
public class OpeCompra implements Controladores.ConCompra {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModCompra c = new Modelos.ModCompra();

    @Override
    public boolean agregar(Object obj) {
        c = (Modelos.ModCompra) obj;
        String sql = "INSERT INTO compra\n"
                + "(idcompra, numero_documento, numero_timbrado, fecha, observacion, idproveedor, iddeposito, idusuario)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdcompra());
            ps.setString(2, c.getNumero_documento());
            ps.setInt(3, c.getNumero_timbrado());
            ps.setDate(4, c.getFecha());
            ps.setString(5, c.getObservacion());
            ps.setInt(6, c.getIdproveedor());
            ps.setInt(7, c.getIddeposito());
            ps.setInt(8, c.getIdusuario());
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
        c = (Modelos.ModCompra) obj;
        String sql = "DELETE FROM compra WHERE idcompra=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdcompra());
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
        String sql = "select idcompra + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idcompra\n"
                + "         union all\n"
                + "        select idcompra\n"
                + "          from compra) t1\n"
                + " where not exists (select null\n"
                + "                     from compra t2\n"
                + "                    where t2.idcompra = t1.idcompra + 1)\n"
                + " order by idcompra\n"
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
                + "A.COMPRA_CODIGO, \n"
                + "A.COMPRA_NUMERO_DOCUMENTO, \n"
                + "A.COMPRA_NUMERO_TIMBRADO,\n"
                + "A.PROVEEDOR_CODIGO,\n"
                + "A.PROVEEDOR_DESCRIPCION,\n"
                + "A.COMPRA_FECHA  \n"
                + "FROM compra_v AS A\n"
                + "WHERE CONCAT(A.COMPRA_NUMERO_DOCUMENTO, A.COMPRA_NUMERO_TIMBRADO, \n"
                + "A.COMPRA_FECHA, A.PROVEEDOR_DESCRIPCION, \n"
                + "A.PROVEEDOR_CODIGO, A.COMPRA_CODIGO) LIKE ?;";
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
        c = (Modelos.ModCompra) obj;
        String sql = "SELECT * FROM compra WHERE idcompra = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdcompra());
            rs = ps.executeQuery();
            if (rs.next()) {
                c.setIdcompra(rs.getInt(1));
                c.setNumero_documento(rs.getString(2));
                c.setNumero_timbrado(rs.getInt(3));
                c.setFecha(rs.getDate(4));
                c.setObservacion(rs.getString(5));
                c.setIdproveedor(rs.getInt(6));
                c.setIddeposito(rs.getInt(7));
                c.setIdusuario(rs.getInt(8));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE COMPRA CON LOS DATOS INGRESADOS...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
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
                + "FROM compra AS V\n"
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
