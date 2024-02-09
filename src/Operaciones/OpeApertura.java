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
public class OpeApertura implements Controladores.ConApertura {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModApertura a = new Modelos.ModApertura();

    @Override
    public boolean agregar(Object obj) {
        a = (Modelos.ModApertura) obj;
        String sql = "INSERT INTO apertura\n"
                + "(idapertura, idcaja, fecha, monto_inicial, observacion, estado, idusuario)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdapertura());
            ps.setInt(2, a.getIdcaja());
            ps.setTimestamp(3, a.getFecha());
            ps.setDouble(4, a.getMonto_inicial());
            ps.setString(5, a.getObservacion());
            ps.setString(6, a.getEstado());
            ps.setInt(7, a.getIdusuario());
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
        a = (Modelos.ModApertura) obj;
        String sql = "UPDATE apertura\n"
                + "	SET\n"
                + "		idcaja=?,\n"
                + "		fecha=?,\n"
                + "		monto_inicial=?,\n"
                + "		observacion=?,\n"
                + "		estado=?,\n"
                + "		idusuario=?\n"
                + "	WHERE idapertura=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdcaja());
            ps.setTimestamp(2, a.getFecha());
            ps.setDouble(3, a.getMonto_inicial());
            ps.setString(4, a.getObservacion());
            ps.setString(5, a.getEstado());
            ps.setInt(6, a.getIdusuario());
            ps.setInt(7, a.getIdapertura());
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
        a = (Modelos.ModApertura) obj;
        String sql = "DELETE FROM apertura WHERE idapertura=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdapertura());
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
        String sql = "select idapertura + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idapertura\n"
                + "         union all\n"
                + "        select idapertura\n"
                + "          from apertura) t1\n"
                + " where not exists (select null\n"
                + "                     from apertura t2\n"
                + "                    where t2.idapertura = t1.idapertura + 1)\n"
                + " order by idapertura\n"
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
        String sql = "SELECT * FROM apertura_v AS A WHERE CONCAT(A.CAJA_DESCRIPCION, A.APERTURA_FECHA, A.USUARIO_CODIGO, A.APERTURA_CODIGO, A.APERTURA_ESTADO) LIKE ?;";
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
                fila[1] = rs.getInt(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getDouble(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getString(7);
                fila[7] = rs.getInt(8);
                fila[8] = rs.getString(9);
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
        a = (Modelos.ModApertura) obj;
        String sql = "SELECT * FROM apertura WHERE idapertura = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdapertura());
            rs = ps.executeQuery();
            if (rs.next()) {
                a.setIdapertura(rs.getInt(1));
                a.setIdcaja(rs.getInt(2));
                a.setFecha(rs.getTimestamp(3));
                a.setMonto_inicial(rs.getDouble(4));
                a.setObservacion(rs.getString(5));
                a.setEstado(rs.getString(6));
                a.setIdusuario(rs.getInt(7));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE APERTURA CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public ArrayList<Object[]> consultarAperturaAbierta(String criterio) {
        String sql = "SELECT\n"
                + "A.idapertura,\n"
                + "A.idcaja,\n"
                + "C.descripcion AS caja,\n"
                + "DATE_FORMAT(A.fecha,'%d/%m/%Y %r') AS FECHA\n"
                + "FROM apertura AS A\n"
                + "INNER JOIN caja AS C ON C.idcaja = A.idcaja\n"
                + "WHERE A.estado = 'A'\n"
                + "AND CONCAT(DATE_FORMAT(A.fecha,'%d/%m/%Y %r')) LIKE ?\n"
                + "ORDER BY A.fecha DESC;";
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
                Object[] fila = new Object[4];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getInt(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER LA LISTA DE LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

    @Override
    public boolean consultarDatosAperturaAbierta(Object obj) {
        a = (Modelos.ModApertura) obj;
        String sql = "SELECT * FROM apertura WHERE estado LIKE \"A\"\n"
                + "and idapertura = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdapertura());
            rs = ps.executeQuery();
            if (rs.next()) {
                a.setIdapertura(rs.getInt(1));
                a.setIdcaja(rs.getInt(2));
                a.setFecha(rs.getTimestamp(3));
                a.setMonto_inicial(rs.getDouble(4));
                a.setObservacion(rs.getString(5));
                a.setEstado(rs.getString(6));
                a.setIdusuario(rs.getInt(7));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE APERTURA CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean consultarDatosAperturaCerrada(Object obj) {
        a = (Modelos.ModApertura) obj;
        String sql = "SELECT * FROM apertura WHERE estado LIKE \"C\"\n"
                + "and idapertura = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdapertura());
            rs = ps.executeQuery();
            if (rs.next()) {
                a.setIdapertura(rs.getInt(1));
                a.setIdcaja(rs.getInt(2));
                a.setFecha(rs.getTimestamp(3));
                a.setMonto_inicial(rs.getDouble(4));
                a.setObservacion(rs.getString(5));
                a.setEstado(rs.getString(6));
                a.setIdusuario(rs.getInt(7));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE APERTURA CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
