package trafegoaereo.agents;

import java.util.ArrayList;
import java.util.List;

import jade.core.Agent;

public class Airport extends Agent {
    List<Airplane> airplanes;
    //int quantidadePessoas = 0;
    //String enderecoAeroporto = "";

    boolean possuiAviaoNaPista = false;

    protected void setup() {






    }

    public Airport() {
    }

    public Airport(List<Airplane> airplanes, int quantidadePessoas, String enderecoAeroporto, boolean possuiAviaoNaPista) {
        this.airplanes = airplanes;
        this.quantidadePessoas = quantidadePessoas;
        this.enderecoAeroporto = enderecoAeroporto;
        this.possuiAviaoNaPista = possuiAviaoNaPista;
    }

    public static Airport ConstroiAeroporto() {
        Airplane aero1 = Airplane.ConstroiAeronave();
        Airplane aero2 = Airplane.ConstroiAeronave();
        Airplane aero3 = Airplane.ConstroiAeronave();
        List<Airplane> aeronavesses = new ArrayList<Airplane>() {{
            add(aero1);
            add(aero2);
            add(aero3);
        }};


        return new Airport(aeronavesses, 1000, "Navegantes SC-Brasil", false);
    }

}
