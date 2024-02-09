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
public class OpeNumeracion implements Controladores.ConNumeracion {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModNumeracion n = new Modelos.ModNumeracion();

    @Override
    public boolean agregar(Object obj) {
        n = (Modelos.ModNumeracion) obj;
        String sql = "INSERT INTO numeracion\n"
                + "(idnumeracion, ult_nro_fac, ult_nro_not_cred, ult_nro_not_deb, ult_nro_ord_tra)\n"
                + "VALUES (?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, n.getIdnumeracion());
            ps.setString(2, n.getUlt_nro_fac());
            ps.setString(3, n.getUlt_nro_not_cred());
            ps.setString(4, n.getUlt_nro_not_deb());
            ps.setString(5, n.getUlt_nro_ord_tra());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                //JOptionPane.showMessageDialog(null, "REGISTRO EXITOSO", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
        n = (Modelos.ModNumeracion) obj;
        String sql = "UPDATE numeracion\n"
                + "	SET\n"
                + "		ult_nro_fac=?,\n"
                + "		ult_nro_not_cred=?,\n"
                + "		ult_nro_not_deb=?, \n"
                + "		ult_nro_ord_tra=?\n"
                + "	WHERE idnumeracion=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, n.getUlt_nro_fac());
            ps.setString(2, n.getUlt_nro_not_cred());
            ps.setString(3, n.getUlt_nro_not_deb());
            ps.setString(4, n.getUlt_nro_ord_tra());
            ps.setInt(5, n.getIdnumeracion());
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

    @Override
    public boolean eliminar(Object obj) {
        n = (Modelos.ModNumeracion) obj;
        String sql = "DELETE FROM numeracion WHERE idnumeracion=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, n.getIdnumeracion());
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
        String sql = "select idnumeracion + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idnumeracion\n"
                + "         union all\n"
                + "        select idnumeracion\n"
                + "          from numeracion) t1\n"
                + " where not exists (select null\n"
                + "                     from numeracion t2\n"
                + "                    where t2.idnumeracion = t1.idnumeracion + 1)\n"
                + " order by idnumeracion\n"
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
        String sql = "SELECT * FROM numeracion WHERE CONCAT(idnumeracion) LIKE ? ORDER BY idnumeracion;";
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
                fila[2] = rs.getString(3);
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
        n = (Modelos.ModNumeracion) obj;
        String sql = "SELECT * FROM numeracion WHERE idnumeracion = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, n.getIdnumeracion());
            rs = ps.executeQuery();
            if (rs.next()) {
                n.setIdnumeracion(rs.getInt(1));
                n.setUlt_nro_fac(rs.getString(2));
                n.setUlt_nro_not_cred(rs.getString(3));
                n.setUlt_nro_not_deb(rs.getString(4));
                n.setUlt_nro_ord_tra(rs.getString(5));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE NUMERACION CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
