package agents;

import java.util.List;

import jade.core.Agent;

public class Airport extends Agent {

    int maxCapacity;
    List<String> airplanes;

    public Airport() {}

    protected void setup() {
        Object[] args = getArguments();
        this.maxCapacity = (int) args[0];

        System.out.println("Sou um aeroporto e possuo " + this.maxCapacity + " de slots.");
    }

}
