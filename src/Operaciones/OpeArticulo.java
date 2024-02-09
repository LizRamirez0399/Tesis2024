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
public class OpeArticulo implements Controladores.ConArticulo {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModArticulo a = new Modelos.ModArticulo();

    @Override
    public boolean agregar(Object obj) {
        a = (Modelos.ModArticulo) obj;
        String sql = "INSERT INTO articulo\n"
                + "(idarticulo, descripcion, referencia, estado, idmarca, idlinea, idunidad, idtipo, costo, precio, idimpuesto)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdarticulo());
            ps.setString(2, a.getDescripcion());
            ps.setString(3, a.getReferencia());
            ps.setString(4, a.getEstado());
            ps.setInt(5, a.getIdmarca());
            ps.setInt(6, a.getIdlinea());
            ps.setInt(7, a.getIdunidad());
            ps.setInt(8, a.getIdtipo());
            ps.setDouble(9, a.getCosto());
            ps.setDouble(10, a.getPrecio());
            ps.setInt(11, a.getIdimpuesto());
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
        a = (Modelos.ModArticulo) obj;
        String sql = "UPDATE articulo\n"
                + "	SET\n"
                + "		descripcion=?,\n"
                + "		referencia=?,\n"
                + "		estado=?,\n"
                + "		idmarca=?,\n"
                + "		idlinea=?,\n"
                + "		idunidad=?,\n"
                + "		idtipo=?,\n"
                + "		costo=?,\n"
                + "		precio=?,\n"
                + "		idimpuesto=?\n"
                + "	WHERE idarticulo=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, a.getDescripcion());
            ps.setString(2, a.getReferencia());
            ps.setString(3, a.getEstado());
            ps.setInt(4, a.getIdmarca());
            ps.setInt(5, a.getIdlinea());
            ps.setInt(6, a.getIdunidad());
            ps.setInt(7, a.getIdtipo());
            ps.setDouble(8, a.getCosto());
            ps.setDouble(9, a.getPrecio());
            ps.setInt(10, a.getIdimpuesto());
            ps.setInt(11, a.getIdarticulo());
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
        a = (Modelos.ModArticulo) obj;
        String sql = "DELETE FROM articulo WHERE idarticulo=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdarticulo());
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
        String sql = "select idarticulo + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idarticulo\n"
                + "         union all\n"
                + "        select idarticulo\n"
                + "          from articulo) t1\n"
                + " where not exists (select null\n"
                + "                     from articulo t2\n"
                + "                    where t2.idarticulo = t1.idarticulo + 1)\n"
                + " order by idarticulo\n"
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
    public ArrayList<Object[]> consultar(String criterio, String campo) {
        String sql = "SELECT\n"
                + "A.ARTICULO_CODIGO,\n"
                + "A.ARTICULO_DESCRIPCION,\n"
                + "A.ARTICULO_REFERENCIA,\n"
                + "A.ARTICULO_ESTADO\n"
                + "FROM articulo_v AS A \n"
                + "WHERE " + campo + " LIKE ?;";
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
                fila[1] = rs.getString(2);
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
    public boolean consultarDatos(Object obj) {
        a = (Modelos.ModArticulo) obj;
        String sql = "SELECT * FROM articulo WHERE idarticulo = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdarticulo());
            rs = ps.executeQuery();
            if (rs.next()) {
                a.setIdarticulo(rs.getInt(1));
                a.setDescripcion(rs.getString(2));
                a.setReferencia(rs.getString(3));
                a.setEstado(rs.getString(4));
                a.setIdmarca(rs.getInt(5));
                a.setIdlinea(rs.getInt(6));
                a.setIdunidad(rs.getInt(7));
                a.setIdtipo(rs.getInt(8));
                a.setCosto(rs.getDouble(9));
                a.setPrecio(rs.getDouble(10));
                a.setIdimpuesto(rs.getInt(11));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE ARTICULO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean verificarDuplicacion(String criterio, String campo) {
        String sql = "SELECT COUNT(*) as EXISTE FROM articulo AS m WHERE " + campo + " LIKE ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        boolean resultado = false;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, criterio);
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
    public boolean busquedaArticulo(String criterio, Object obj) {
        a = (Modelos.ModArticulo) obj;
        String sql = "";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        boolean resultado = false;
        try {
            sql = "SELECT * FROM articulo WHERE referencia LIKE ?;";
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, criterio);
            rs = ps.executeQuery();
            if (rs.next()) {
                a.setIdarticulo(rs.getInt(1));
                a.setDescripcion(rs.getString(2));
                a.setReferencia(rs.getString(3));
                a.setEstado(rs.getString(4));
                a.setIdmarca(rs.getInt(5));
                a.setIdlinea(rs.getInt(6));
                a.setIdunidad(rs.getInt(7));
                a.setIdtipo(rs.getInt(8));
                a.setCosto(rs.getDouble(9));
                a.setPrecio(rs.getDouble(10));
                a.setIdimpuesto(rs.getInt(11));
                con.close();
                resultado = true;
            } else {
                con.close();
                resultado = false;
            }
            if (resultado == false) {
                sql = "SELECT * FROM articulo WHERE idarticulo = ?;";
                Class.forName(db.getDriver());
                con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
                ps = con.prepareStatement(sql);
                ps.setString(1, criterio);
                rs = ps.executeQuery();
                if (rs.next()) {
                    a.setIdarticulo(rs.getInt(1));
                    a.setDescripcion(rs.getString(2));
                    a.setReferencia(rs.getString(3));
                    a.setEstado(rs.getString(4));
                    a.setIdmarca(rs.getInt(5));
                    a.setIdlinea(rs.getInt(6));
                    a.setIdunidad(rs.getInt(7));
                    a.setIdtipo(rs.getInt(8));
                    a.setCosto(rs.getDouble(9));
                    a.setPrecio(rs.getDouble(10));
                    a.setIdimpuesto(rs.getInt(11));
                    con.close();
                    resultado = true;
                } else {
                    con.close();
                    resultado = false;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return resultado;
    }

}
