package GestorAlumnos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3306/ipasen";
        try {
            // Enlazar con el driver
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, "root", "12345");
            Statement stm = con.createStatement();
            System.out.println("Conexion establecida");

            stm.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Ha fallado la conexión: " + e.getMessage());
        }
    }
}