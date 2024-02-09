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
public class OpeConfiguracion implements Controladores.ConConfiguracion {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModConfiguracion c = new Modelos.ModConfiguracion();

    @Override
    public boolean agregar(Object obj) {
        c = (Modelos.ModConfiguracion) obj;
        String sql = "INSERT INTO configuracion\n"
                + "(idconfiguracion, tipo_articulo_servicio, \n"
                + "cantidad_maxima_facturacion, facturacion_timbrado, \n"
                + "nota_credito_timbrado, nota_debito_timbrado)\n"
                + "VALUES (?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdconfiguracion());
            ps.setInt(2, c.getTipo_articulo_servicio());
            ps.setInt(3, c.getCantidad_maxima_facturacion());
            ps.setInt(4, c.getFacturacion_timbrado());
            ps.setInt(5, c.getNota_credito_timbrado());
            ps.setInt(6, c.getNota_debito_timbrado());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "REGISTRO EXITOSO", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
    public boolean modificar(Object obj) {
        c = (Modelos.ModConfiguracion) obj;
        String sql = "UPDATE configuracion\n"
                + "	SET\n"
                + "		tipo_articulo_servicio=?,\n"
                + "		cantidad_maxima_facturacion=?,\n"
                + "		facturacion_timbrado=?,\n"
                + "		nota_credito_timbrado=?,\n"
                + "		nota_debito_timbrado=?\n"
                + "	WHERE idconfiguracion=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getTipo_articulo_servicio());
            ps.setInt(2, c.getCantidad_maxima_facturacion());
            ps.setInt(3, c.getFacturacion_timbrado());
            ps.setInt(4, c.getNota_credito_timbrado());
            ps.setInt(5, c.getNota_debito_timbrado());
            ps.setInt(6, c.getIdconfiguracion());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "ACTUALIZACIÓN EXITOSA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL MODIFICAR LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean eliminar(Object obj) {
        c = (Modelos.ModConfiguracion) obj;
        String sql = "DELETE FROM configuracion WHERE idconfiguracion=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdconfiguracion());
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
        String sql = "select idconfiguracion + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idconfiguracion\n"
                + "         union all\n"
                + "        select idconfiguracion\n"
                + "          from configuracion) t1\n"
                + " where not exists (select null\n"
                + "                     from configuracion t2\n"
                + "                    where t2.idconfiguracion = t1.idconfiguracion + 1)\n"
                + " order by idconfiguracion\n"
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
        String sql = "SELECT * FROM configuracion_v AS A WHERE CONCAT(A.CONFIGURACION_CODIGO) LIKE ?;";
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
                Object[] fila = new Object[10];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getInt(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getInt(4);
                fila[4] = rs.getInt(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getInt(7);
                fila[7] = rs.getString(8);
                fila[8] = rs.getInt(9);
                fila[9] = rs.getString(10);
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
        c = (Modelos.ModConfiguracion) obj;
        String sql = "SELECT * FROM configuracion WHERE idconfiguracion = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdconfiguracion());
            rs = ps.executeQuery();
            if (rs.next()) {
                c.setIdconfiguracion(rs.getInt(1));
                c.setTipo_articulo_servicio(rs.getInt(2));
                c.setCantidad_maxima_facturacion(rs.getInt(3));
                c.setFacturacion_timbrado(rs.getInt(4));
                c.setNota_credito_timbrado(rs.getInt(5));
                c.setNota_debito_timbrado(rs.getInt(6));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE CONFIGURACION CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
