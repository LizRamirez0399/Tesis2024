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
public class OpeOrdenTrabajo implements Controladores.ConOrdenTrabajo {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModOrdenTrabajo ot = new Modelos.ModOrdenTrabajo();

    @Override
    public boolean agregar(Object obj) {
        ot = (Modelos.ModOrdenTrabajo) obj;
        String sql = "INSERT INTO orden_trabajo\n"
                + "(idorden, numero_comprobante, fecha, observacion, estado, idempleado, idcliente, idusuario)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ot.getIdorden());
            ps.setString(2, ot.getNumero_comprobante());
            ps.setDate(3, ot.getFecha());
            ps.setString(4, ot.getObservacion());
            ps.setString(5, ot.getEstado());
            ps.setInt(6, ot.getIdempleado());
            ps.setInt(7, ot.getIdcliente());
            ps.setInt(8, ot.getIdusuario());
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
        ot = (Modelos.ModOrdenTrabajo) obj;
        String sql = "DELETE FROM orden_trabajo WHERE idorden=?";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ot.getIdorden());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                //JOptionPane.showMessageDialog(null, "ELIMINACIÓN EXITOSA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
        String sql = "select idorden + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idorden\n"
                + "         union all\n"
                + "        select idorden\n"
                + "          from orden_trabajo) t1\n"
                + " where not exists (select null\n"
                + "                     from orden_trabajo t2\n"
                + "                    where t2.idorden = t1.idorden + 1)\n"
                + " order by idorden\n"
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
                + "A.idorden,\n"
                + "A.numero_comprobante,\n"
                + "A.idcliente,\n"
                + "CONCAT(C.nombre,' ',C.apellido) AS CLIENTE_DESCRIPCION,\n"
                + "DATE_FORMAT(A.fecha,'%d/%m/%Y') AS ORDEN_FECHA\n"
                + "FROM orden_trabajo AS A\n"
                + "INNER JOIN cliente AS C ON C.idcliente = A.idcliente\n"
                + "WHERE A.estado LIKE 'P' \n"
                + "AND CONCAT(A.numero_comprobante, DATE_FORMAT(A.fecha,'%d/%m/%Y'), CONCAT(C.nombre,' ',C.apellido), A.idcliente, A.idorden) LIKE ?;";
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
                Object[] fila = new Object[5];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getInt(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getString(5);
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
        ot = (Modelos.ModOrdenTrabajo) obj;
        String sql = "SELECT * FROM orden_trabajo WHERE idorden = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ot.getIdorden());
            rs = ps.executeQuery();
            if (rs.next()) {
                ot.setIdorden(rs.getInt(1));
                ot.setNumero_comprobante(rs.getString(2));
                ot.setFecha(rs.getDate(3));
                ot.setObservacion(rs.getString(4));
                ot.setEstado(rs.getString(5));
                ot.setIdempleado(rs.getInt(6));
                ot.setIdcliente(rs.getInt(7));
                ot.setIdusuario(rs.getInt(8));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE ORDEN DE TRABAJO CON LOS DATOS INGRESADOS...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean verificarDuplicacion(String numero_documento) {
        String sql = "SELECT\n"
                + "COUNT(*) AS EXISTE \n"
                + "FROM orden_trabajo AS V\n"
                + "WHERE V.numero_comprobante = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        boolean resultado = false;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, numero_documento);
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

    @Override
    public boolean ObtenerDatosPendiente(Object obj) {
        ot = (Modelos.ModOrdenTrabajo) obj;
        String sql = "SELECT * FROM orden_trabajo WHERE idorden = ? AND estado LIKE 'P';";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ot.getIdorden());
            rs = ps.executeQuery();
            if (rs.next()) {
                ot.setIdorden(rs.getInt(1));
                ot.setNumero_comprobante(rs.getString(2));
                ot.setFecha(rs.getDate(3));
                ot.setObservacion(rs.getString(4));
                ot.setEstado(rs.getString(5));
                ot.setIdempleado(rs.getInt(6));
                ot.setIdcliente(rs.getInt(7));
                ot.setIdusuario(rs.getInt(8));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE ORDEN DE TRABAJO CON LOS DATOS INGRESADOS O YA FUE UTILIZADO EN UN SERVICIO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean modificar(Object obj) {
        ot = (Modelos.ModOrdenTrabajo) obj;
        String sql = "UPDATE orden_trabajo\n"
                + "	SET\n"
                + "		estado=?\n"
                + "	WHERE idorden=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, ot.getEstado());
            ps.setInt(2, ot.getIdorden());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                //JOptionPane.showMessageDialog(null, "ACTUALIZACIÓN EXITOSA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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

}
