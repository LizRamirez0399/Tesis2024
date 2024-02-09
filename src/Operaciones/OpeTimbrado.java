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
public class OpeTimbrado implements Controladores.ConTimbrado {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModTimbrado t = new Modelos.ModTimbrado();

    @Override
    public boolean agregar(Object obj) {
        t = (Modelos.ModTimbrado) obj;
        String sql = "INSERT INTO timbrado\n"
                + "(idtimbrado, timbrado, fecha_inicial, \n"
                + "fecha_final, numero_desde, numero_hasta, \n"
                + "establecimiento, punto_emision, idtipo)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getIdtimbrado());
            ps.setInt(2, t.getTimbrado());
            ps.setDate(3, t.getFecha_inicial());
            ps.setDate(4, t.getFecha_final());
            ps.setInt(5, t.getNumero_desde());
            ps.setInt(6, t.getNumero_hasta());
            ps.setInt(7, t.getEstablecimiento());
            ps.setInt(8, t.getPunto_emision());
            ps.setInt(9, t.getIdtipo());
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
        t = (Modelos.ModTimbrado) obj;
        String sql = "UPDATE timbrado\n"
                + "	SET\n"
                + "		timbrado=?,\n"
                + "		fecha_inicial=?,\n"
                + "		fecha_final=?,\n"
                + "		numero_desde=?,\n"
                + "		numero_hasta=?,\n"
                + "		establecimiento=?,\n"
                + "		punto_emision=?,\n"
                + "		idtipo=?\n"
                + "	WHERE idtimbrado=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getTimbrado());
            ps.setDate(2, t.getFecha_inicial());
            ps.setDate(3, t.getFecha_final());
            ps.setInt(4, t.getNumero_desde());
            ps.setInt(5, t.getNumero_hasta());
            ps.setInt(6, t.getEstablecimiento());
            ps.setInt(7, t.getPunto_emision());
            ps.setInt(8, t.getIdtipo());
            ps.setInt(9, t.getIdtimbrado());
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
        t = (Modelos.ModTimbrado) obj;
        String sql = "DELETE FROM timbrado WHERE idtimbrado=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getIdtimbrado());
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
        String sql = "select idtimbrado + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idtimbrado\n"
                + "         union all\n"
                + "        select idtimbrado\n"
                + "          from timbrado) t1\n"
                + " where not exists (select null\n"
                + "                     from timbrado t2\n"
                + "                    where t2.idtimbrado = t1.idtimbrado + 1)\n"
                + " order by idtimbrado\n"
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
        String sql = "SELECT * FROM timbrado_v AS A WHERE CONCAT(A.TIMBRADO_FECHA_INICIAL, A.TIMBRADO_FECHA_FINAL, A.TIMBRADO_NUMERO, A.TIPO_DOCUMENTO_DESCRIPCION) LIKE ?;";
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
                fila[3] = rs.getString(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getString(7);
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
        t = (Modelos.ModTimbrado) obj;
        String sql = "SELECT * FROM timbrado WHERE idtimbrado = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getIdtimbrado());
            rs = ps.executeQuery();
            if (rs.next()) {
                t.setIdtimbrado(rs.getInt(1));
                t.setTimbrado(rs.getInt(2));
                t.setFecha_inicial(rs.getDate(3));
                t.setFecha_final(rs.getDate(4));
                t.setNumero_desde(rs.getInt(5));
                t.setNumero_hasta(rs.getInt(6));
                t.setEstablecimiento(rs.getInt(7));
                t.setPunto_emision(rs.getInt(8));
                t.setIdtipo(rs.getInt(9));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE TIMBRADO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
