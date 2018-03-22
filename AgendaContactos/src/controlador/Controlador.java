/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ControladorBaseDatos;
import modelo.Persona;
import vista.Interfaz;

/**
 *
 * @author Usuario
 */
public class Controlador implements ActionListener {

    private ControladorBaseDatos base;
    private Persona persona;
    private Interfaz vista;
    private ArrayList<Persona> todos = new ArrayList<Persona>();
    int indiceActual = 0;
    int totalResultados = 0;

    public Controlador(ControladorBaseDatos base, Persona persona, Interfaz vista) {
        this.base = base;
        this.persona = persona;
        this.vista = vista;
        this.vista.btnAgregar.addActionListener(this);

        this.vista.btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        
        this.vista.btnPresentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPresentaractionPerformed(evt);
            }
        });
        this.vista.jBAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarAnterior(evt);
            }
        });
        this.vista.jBSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarSiguiente(evt);
            }
        });
    }

    public void iniciar() {
        vista.setTitle("Agenda Contactos");
        vista.setLocationRelativeTo(null);
    }

    public void btnPresentaractionPerformed(ActionEvent ae) {
        base.ControladorBaseDatos();
        todos = base.presentarContactos();
        Persona persona = todos.get(indiceActual);
        setTextos(persona, indiceActual, todos.size());

    }

    public void actionPerformed(ActionEvent e) {
        base.ControladorBaseDatos();
        persona.setId(vista.jTextID.getText());
        persona.setNombre(vista.jTexNombre.getText());
        persona.setApellido(vista.jTextApellido.getText());
        persona.setEmail(vista.jTextEmail.getText());
        persona.setTelefono(vista.jTextTelefono.getText());
        try {
            base.agregarPersonas(persona);
        } catch (SQLException ez) {
            System.out.println(ez);
        }
    }
    
    public void btnBuscarActionPerformed(ActionEvent e) {
        base.ControladorBaseDatos();
        String apellido = vista.jTexBuscarApellido.getText();
        todos = base.presentarPersonasPorApellido(apellido);
        Persona persona = todos.get(indiceActual);
        setTextos(persona, indiceActual, todos.size());
    }
    
public void btnMostrarAnterior(ActionEvent e) {
        int count = indiceActual - 1;
        if (count >= 0) {
            Persona persona = todos.get(count);
            setTextos(persona, count, totalResultados);
            indiceActual = indiceActual - 1;
        } else {
            System.out.println("fin de la lista");
        }
    }

    public void btnMostrarSiguiente(ActionEvent e) {
        int count = indiceActual + 1;
        totalResultados = todos.size();
        if (count < totalResultados) {
            Persona persona = todos.get(count);
            setTextos(persona, count, totalResultados);
            indiceActual = indiceActual + 1;
        } else {
            System.out.println("fin de la lista");
        }
    }
    public void setTextos(Persona persona, int indiceActual, int totalResultados) {
        vista.jTextID.setText(persona.getId());
        vista.jTexNombre.setText(persona.getNombre());
        vista.jTextApellido.setText(persona.getApellido());
        vista.jTextEmail.setText(persona.getEmail());
        vista.jTextTelefono.setText(persona.getTelefono());
        vista.jBAnterior.setText(String.valueOf(indiceActual + 1));
        vista.jBSiguiente.setText(String.valueOf(totalResultados));
    }

}
