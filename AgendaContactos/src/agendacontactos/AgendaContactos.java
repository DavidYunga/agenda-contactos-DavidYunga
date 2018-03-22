/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendacontactos;

import controlador.Controlador;
import modelo.ControladorBaseDatos;
import modelo.Persona;
import vista.Interfaz;

/**
 *
 * @author Usuario
 */
public class AgendaContactos {
    
    public static void main(String[] args) {
        ControladorBaseDatos base = new ControladorBaseDatos();
        Persona persona = new Persona();
        Interfaz vista = new Interfaz();
        Controlador ctr = new Controlador(base, persona, vista);
        ctr.iniciar();
        vista.setVisible(true);
        
    }
    
}
