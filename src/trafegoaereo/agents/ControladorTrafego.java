/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafegoaereo.agents;
import java.util.List;

/**
 *
 * @author 7425651
 */
public class ControladorTrafego {
    List<Aeronave> aeronaves;      
    boolean permitePouso = false;
    boolean permiteDecolagem = false;
    
    public ControladorTrafego(){}

    public ControladorTrafego(List<Aeronave> aeronaves, boolean permitePouso, boolean permiteDecolagem)
    {
        //this.passageiros = passageiros;
        this.aeronaves = aeronaves;
        this.permitePouso = permitePouso;
        this.permiteDecolagem = permiteDecolagem;
    }
    
    
}
