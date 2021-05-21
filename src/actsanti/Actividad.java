/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actsanti;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Actividad {

 DefaultTableModel modelo=new DefaultTableModel();
 JTextField nombre = new JTextField(20);
 JLabel etiqueta1 = new JLabel(" ");
 JTable TABLA = new JTable();

   public void componentes(JPanel panel){
        panel.setLayout(null);
        JLabel etiqueta = new JLabel("Datos: ");
        etiqueta.setBounds(10, 20, 60, 25);
        panel.add(etiqueta);
        nombre.setBounds(80, 20, 250, 25);
        panel.add(nombre);
        JButton enviarPost = new JButton("Enviar a BD PostgreSQL");
        enviarPost.setBounds(130, 50, 150, 25);
        panel.add(enviarPost);
        JButton enviarMsql = new JButton("Enviar a BD Mysql");
        enviarMsql.setBounds(130, 80, 150, 25);
        panel.add(enviarMsql);
        JButton limpiar = new JButton("Limpiar");
        limpiar.setBounds(new Rectangle(130, 110, 150, 25));
        panel.add(limpiar);
        etiqueta1.setBounds(50, 150, 300, 50);
        etiqueta1.setBorder (BorderFactory.createLineBorder(Color.red, 1));
        panel.add(etiqueta1);
        JScrollPane scroll = new JScrollPane(TABLA);
        TABLA.setBounds(50, 230, 300, 100);
        scroll.setBounds(50, 230, 300, 100);
        panel.add(scroll);


        enviarMsql.addActionListener( e-> {
            String n1=nombre.getText();
            if (n1.isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un dato");
            }else{    
                etiqueta1.setText(n1);
                ConexionBD Con = new ConexionBD();
                Con.ConectarBD();
                try {
                    String SQL = "INSERT INTO datos"
                    + "(dato)"
                    + "values"
                    + "('"+ n1 +"');";
                    Con.sentencia.execute(SQL);
                    JOptionPane.showMessageDialog(null, "¡Los datos han sido guardados con éxito a la BD!");
                    modelotable();
                    ConsultaMsql();
                }catch(SQLException ex){
                Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  

        });
        
        enviarPost.addActionListener(e->{
           String n1=nombre.getText();
           
           if(n1.isEmpty()){
                JOptionPane.showMessageDialog(null, "Por favor ingrese un dato");

           }else{
                etiqueta1.setText(n1);
                guardarPostSQL();
                modelotable();
                consultarPost();
           }
               
            
        });
 
        
        limpiar.addActionListener( e->{
            String n1=nombre.getText();
            if(n1.isEmpty()){
                JOptionPane.showMessageDialog(null, "No hay nada que limpiar");
            }else{
                etiqueta1.setText("");
                nombre.setText("");
            }
        });

   }
   public void guardarPostSQL(){        
        Connection con = null;
        String urlDatabase = "jdbc:postgresql://localhost:5432/datos";           
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(urlDatabase, "postgres", "teamopapa1");
            java.sql.Statement st = con.createStatement();
            String sql = "insert into datos(dato)"+"values('"+ nombre.getText() +"')";                   
            ResultSet result = st.executeQuery(sql);
            con.close();
            st.close();
            JOptionPane.showMessageDialog(null, "¡Los datos han sido guardados con éxito a la BD");
            modelotable();
            
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
   }
   public void consultarenpost(){
       
   }
   public void consultarPost(){
       String datos[] = new String[2];
       Connection base = null;
       Statement at = null;
       String url = "jdbc:postgresql://localhost:5432/datos";
       try {
            base = DriverManager.getConnection(url, "postgres", "teamopapa1");
            at = base.createStatement();
            ResultSet rs = at.executeQuery("select * from datos");
            while(rs.next()){
                datos[0]=rs.getString("iddatos");
                datos[1]=rs.getString("dato");
                modelo.addRow(datos);
            }
            JOptionPane.showMessageDialog(null, "Aceptar para continuar");
            rs.close();
            at.close();
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
   }
   public void modelotable(){
    try{
    modelo = (new DefaultTableModel(
    null, new String[]{
    "ID", "Dato ingresado"}){
    Class[] types = new Class[]{
    java.lang.String.class, java.lang.String.class
    };
    boolean[] canEdit = new boolean[]{
    false, false
    };
    public Class getColumnClass(int columnIndex){
    return types[columnIndex];
    }
    public boolean isCellEditable(int rowIndex, int colIndex){
    return canEdit[colIndex];
    }
    });
    TABLA.setModel(modelo);
    }catch(Exception e){
    JOptionPane.showMessageDialog(null, e.toString() + "Error");
    }
}

   public void ConsultaMsql(){
       try {
        int i = 0;
        Object Ob[] = null;
        ConexionBD Con = new ConexionBD();
        Con.ConectarBD();
        String SQL = "SELECT * FROM datos";
        Con.resultado = Con.sentencia.executeQuery(SQL);
        while(Con.resultado.next()){
        modelo.addRow(Ob);
        modelo.setValueAt(Con.resultado.getString("id"), i, 0);
        modelo.setValueAt(Con.resultado.getString("dato"), i, 1);
        i++;
        }
        Con.DesconectarBD();
        }catch (SQLException ex){
        Logger.getLogger(Actividad.class.getName()).log(Level.SEVERE, null, ex);
        }
   }


   
    public static void main(String[] args) {
        JFrame ventana = new JFrame("My Ventana No1");
        ventana.setSize(400, 400);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contenedor = new JPanel();
        ventana.add(contenedor);
        Actividad objeto = new Actividad();
        objeto.componentes(contenedor);
        ventana.setVisible(true);
        
    }
    
}
