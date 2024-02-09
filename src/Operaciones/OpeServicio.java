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
public class OpeServicio implements Controladores.ConServicio {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModServicio s = new Modelos.ModServicio();

    @Override
    public boolean agregar(Object obj) {
        s = (Modelos.ModServicio) obj;
        String sql = "INSERT INTO servicio\n"
                + "(idservicio, numero_documento, numero_timbrado, fecha, observacion, idcliente, idusuario, iddeposito)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, s.getIdservicio());
            ps.setString(2, s.getNumero_documento());
            ps.setInt(3, s.getNumero_timbrado());
            ps.setDate(4, s.getFecha());
            ps.setString(5, s.getObservacion());
            ps.setInt(6, s.getIdcliente());
            ps.setInt(7, s.getIdusuario());
            ps.setInt(8, s.getIddeposito());
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
        s = (Modelos.ModServicio) obj;
        String sql = "DELETE FROM servicio WHERE idservicio=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, s.getIdservicio());
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
        String sql = "select idservicio + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idservicio\n"
                + "         union all\n"
                + "        select idservicio\n"
                + "          from servicio) t1\n"
                + " where not exists (select null\n"
                + "                     from servicio t2\n"
                + "                    where t2.idservicio = t1.idservicio + 1)\n"
                + " order by idservicio\n"
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
        String sql = "SELECT\n"
                + "A.SERVICIO_CODIGO,\n"
                + "A.SERVICIO_COMPROBANTE,\n"
                + "A.SERVICIO_TIMBRADO,\n"
                + "A.CLIENTE_CODIGO,\n"
                + "A.CLIENTE_DESCRIPCION,\n"
                + "A.SERVICIO_FECHA\n"
                + "FROM servicio_v AS A\n"
                + "WHERE CONCAT(A.SERVICIO_COMPROBANTE, A.SERVICIO_TIMBRADO, A.SERVICIO_FECHA, A.CLIENTE_DESCRIPCION, A.CLIENTE_CODIGO, A.SERVICIO_CODIGO) LIKE ?;";
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
                fila[3] = rs.getInt(4);
                fila[4] = rs.getString(5);
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
        s = (Modelos.ModServicio) obj;
        String sql = "SELECT * FROM servicio WHERE idservicio = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, s.getIdservicio());
            rs = ps.executeQuery();
            if (rs.next()) {
                s.setIdservicio(rs.getInt(1));
                s.setNumero_documento(rs.getString(2));
                s.setNumero_timbrado(rs.getInt(3));
                s.setFecha(rs.getDate(4));
                s.setObservacion(rs.getString(5));
                s.setIdcliente(rs.getInt(6));
                s.setIdusuario(rs.getInt(7));
                s.setIddeposito(rs.getInt(8));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE SERVICIO CON LOS DATOS INGRESADOS...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
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
                + "FROM servicio AS V\n"
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
