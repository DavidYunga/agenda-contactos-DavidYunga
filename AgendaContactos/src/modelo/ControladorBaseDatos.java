/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ControladorBaseDatos {

    private String url;
    private String USUARIO;
    private String CLAVE;
    private Connection conexion;
    private PreparedStatement seleccionarPersonas;
    private PreparedStatement insertarNuevaPersona;
    PreparedStatement seleccionarPersonasPorApellido;

    public void ControladorBaseDatos() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.out.println("Driver javaDB cargado");
        } catch (ClassNotFoundException es) {
            System.out.println("Error no encuentra la clase");
        }
        conexion = null;
        try {
            url = "jdbc:derby://localhost:1527//Agenda_Contactos";
            conexion = DriverManager.getConnection(url, "agenda", "davidyunga");
            System.out.println("Conexion establecida a la Base de Datos");
        } catch (SQLException ex) {
            System.out.println("No se pudo establecer conexion a la ase de datos");
        }
    }

    public void agregarPersonas(Persona datos) throws SQLException {
        try {
            insertarNuevaPersona = conexion.prepareStatement("INSERT INTO CONTATOS(id,nombre,apellido,email,telefono) VALUES(?,?,?,?,?)");
            insertarNuevaPersona.setString(1, datos.getId());
            insertarNuevaPersona.setString(2, datos.getNombre());
            insertarNuevaPersona.setString(3, datos.getApellido());
            insertarNuevaPersona.setString(4, datos.getEmail());
            insertarNuevaPersona.setString(5, datos.getTelefono());
            insertarNuevaPersona.executeUpdate();
            System.out.println("datos ingresados ");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public ArrayList presentarContactos(){
    ArrayList<Persona> persona = new ArrayList<Persona>();
        try {
            String query = "Select * from contato";
            seleccionarPersonas = conexion.prepareStatement(query);
            ResultSet resultado = seleccionarPersonas.executeQuery();
            while(resultado.next()){
        Persona personaEncontrada = new Persona();
        String id = resultado.getString("id");
        String nombre = resultado.getString("nombre");
        String apellido = resultado.getString("apellido");
        String email = resultado.getString("email");
        String telef = resultado.getString("telefono");
        personaEncontrada.setId(id);
        personaEncontrada.setNombre(nombre);
        personaEncontrada.setApellido(apellido);
        personaEncontrada.setEmail(email);
        personaEncontrada.setTelefono(telef);
        persona.add(personaEncontrada);
            }
                    } catch (SQLException ex) {
            System.out.println(ex);
        }
        return persona;
    }
public ArrayList presentarPersonasPorApellido(String apellido) {
        ArrayList<Persona> encontrado = new ArrayList<Persona>();
        try {
            String query = "Select * from CONTATOS where LOWER(apellido) LIKE LOWER('" + apellido + "%')";
            seleccionarPersonasPorApellido = conexion.prepareStatement(query);
            ResultSet resultado = seleccionarPersonasPorApellido.executeQuery();
            if (resultado.next() == true) {
                Persona personaEncontrada = new Persona();
                String id = resultado.getString("id");
                String nombre = resultado.getString("nombre");
                String apellid = resultado.getString("apellido");
                String email = resultado.getString("email");
                String tlf = resultado.getString("telefono");
                personaEncontrada.setId(id);
                personaEncontrada.setNombre(nombre);
                personaEncontrada.setApellido(apellid);
                personaEncontrada.setEmail(email);
                personaEncontrada.setTelefono(tlf);
                encontrado.add(personaEncontrada);

            } else {
                System.out.println("No existe");
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta" + ex);
        }
        return encontrado;
    }
}
