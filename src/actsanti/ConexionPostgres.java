/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actsanti;
import java.sql.*;
import javax.swing.JOptionPane;

//jdbc:postgresql://localhost:5432/postgres
public class ConexionPostgres {
        
        public void ConectarBDPost(){
            
            String BD = "jdbc:postgresql://localhost:5432/datos";
            String usuario = "postgres";
            String contrasenia = "teamopapa1";
            
            try {
                Connection conectar = DriverManager.getConnection(BD, usuario, contrasenia);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al conectar" +e );
            }
            
        }
        
}
