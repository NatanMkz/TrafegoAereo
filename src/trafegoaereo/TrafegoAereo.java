/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trafegoaereo;

import trafegoaereo.agents.Aeronave;
import trafegoaereo.agents.Aeroporto;

/**
 *
 * @author Natan
 */
public class TrafegoAereo {

    public static Aeronave aeronave = Aeronave.ConstroiAeronave();
    public static Aeroporto aeroporto = Aeroporto.ConstroiAeroporto();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        aeronave.Setup();
    }
    
}
