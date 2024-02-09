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
public class OpeCliente implements Controladores.ConCliente {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Modelos.ModCliente c = new Modelos.ModCliente();

    @Override
    public boolean agregar(Object obj) {
        c = (Modelos.ModCliente) obj;
        String sql = "INSERT INTO cliente\n"
                + "(idcliente, nombre, apellido, cedula, ruc, telefono, direccion, estado, idciudad)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdcliente());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellido());
            ps.setString(4, c.getCedula());
            ps.setString(5, c.getRuc());
            ps.setString(6, c.getTelefono());
            ps.setString(7, c.getDireccion());
            ps.setString(8, c.getEstado());
            ps.setInt(9, c.getIdciudad());
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
        c = (Modelos.ModCliente) obj;
        String sql = "UPDATE cliente\n"
                + "	SET\n"
                + "		nombre=?,\n"
                + "		apellido=?,\n"
                + "		cedula=?,\n"
                + "		ruc=?,\n"
                + "		telefono=?,\n"
                + "		direccion=?,\n"
                + "		estado=?,\n"
                + "		idciudad=?\n"
                + "	WHERE idcliente=?;;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellido());
            ps.setString(3, c.getCedula());
            ps.setString(4, c.getRuc());
            ps.setString(5, c.getTelefono());
            ps.setString(6, c.getDireccion());
            ps.setString(7, c.getEstado());
            ps.setInt(8, c.getIdciudad());
            ps.setInt(9, c.getIdcliente());
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
        c = (Modelos.ModCliente) obj;
        String sql = "DELETE FROM cliente WHERE idcliente=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdcliente());
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
        String sql = "select idcliente + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idcliente\n"
                + "         union all\n"
                + "        select idcliente\n"
                + "          from cliente) t1\n"
                + " where not exists (select null\n"
                + "                     from cliente t2\n"
                + "                    where t2.idcliente = t1.idcliente + 1)\n"
                + " order by idcliente\n"
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
        String sql = "SELECT * FROM cliente_v A WHERE CONCAT(A.CLIENTE_NOMBRE, A.CLIENTE_CEDULA, A.CLIENTE_TELEFONO, A.CIUDAD_DESCRIPCION, A.CLIENTE_CODIGO) LIKE ?;";
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
                Object[] fila = new Object[8];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getInt(7);
                fila[7] = rs.getString(8);
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
        c = (Modelos.ModCliente) obj;
        String sql = "SELECT * FROM cliente WHERE idcliente = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdcliente());
            rs = ps.executeQuery();
            if (rs.next()) {
                c.setIdcliente(rs.getInt(1));
                c.setNombre(rs.getString(2));
                c.setApellido(rs.getString(3));
                c.setCedula(rs.getString(4));
                c.setRuc(rs.getString(5));
                c.setTelefono(rs.getString(6));
                c.setDireccion(rs.getString(7));
                c.setEstado(rs.getString(8));
                c.setIdciudad(rs.getInt(9));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE CLIENTE CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean verificarDuplicacion(String criterio) {
        String sql = "SELECT COUNT(*) as EXISTE FROM cliente AS m WHERE m.cedula LIKE ?;";
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
    public ArrayList<Object[]> obtenerExtracto(String criterio) {
        String sql = "SELECT\n"
                + "V.idventa,\n"
                + "V.numero_documento,\n"
                + "V.numero_timbrado,\n"
                + "DATE_FORMAT(V.fecha,'%d/%m/%Y') AS fecha,\n"
                + "/*TOTAL DEL DETALLE*/\n"
                + "SUM(VD.cantidad * VD.precio) AS TOTAL\n"
                + "FROM venta AS V\n"
                + "INNER JOIN venta_detalle AS VD ON VD.idventa = V.idventa\n"
                + "WHERE V.idcliente = ?\n"
                + "GROUP BY\n"
                + "V.idventa,\n"
                + "V.numero_documento,\n"
                + "V.numero_timbrado,\n"
                + "DATE_FORMAT(V.fecha,'%d/%m/%Y');";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, criterio);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getDouble(5);
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL EXTRACTO DEL CLIENTE \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

}
