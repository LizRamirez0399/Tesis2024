/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

/**
 *
 * @author Ariel
 */
public class Database {

    private String url;
    private String driver;
    private String user;
    private String pass;

    public Database() {
        this.url = "jdbc:mysql://localhost:3306/taller_bd?useUnicode=true&characterEncoding=UTF-8";
        this.driver = "com.mysql.jdbc.Driver";
        this.user = "root";
        this.pass = "";
    }

    public String getUrl() {
        return url;
    }

    public String getDriver() {
        return driver;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
    
}
