package trafegoaereo.agents;
import java.util.List;

public class ControladorTrafego {
    List<Airplane> airplanes;
    boolean permitePouso = false;
    boolean permiteDecolagem = false;
    
    public ControladorTrafego(){}

    public ControladorTrafego(List<Airplane> airplanes, boolean permitePouso, boolean permiteDecolagem)
    {
        //this.passageiros = passageiros;
        this.airplanes = airplanes;
        this.permitePouso = permitePouso;
        this.permiteDecolagem = permiteDecolagem;
    }
    
    
}
