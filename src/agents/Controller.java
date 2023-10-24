package agents;

import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

import java.util.ArrayList;
import java.util.List;

public class Controller extends Agent {

    List<String> airports;
    List<String> airplanes;

    public Controller() {
        this.airports = new ArrayList<>();
        this.airplanes = new ArrayList<>();
    }

    protected void setup() {
        super.setup();
        this.createAirports();
        this.createAirplanes();

        System.out.println("Controller " + this.getName() + " online.");
    }

    private void createAirports() {
        try {
            AgentContainer container = this.getContainerController();
            this.airports.add(
                    container.createNewAgent(
                            "Curitiba",
                            "agents.Airport",
                            new Object[]{3}
                    ).getName()
            );
            this.airports.add(
                    container.createNewAgent(
                            "Floripa",
                            "agents.Airport",
                            new Object[]{5}
                    ).getName()
            );


            for (String airport : this.airports) {
                container.getAgent(airport, true).start();
            }
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }
    }

    private void createAirplanes() {
        try {
            AgentContainer container = this.getContainerController();
            Object[] args = new Object[]{
                    50,
                    container.getAgent("Curitiba").getName(),
                    container.getAgent("Floripa").getName()
            };
            this.airplanes.add(
                    container.createNewAgent(
                            "Boeing",
                            "agents.Airplane",
                            args
                    ).getName()
            );

            for (String airplane : this.airplanes) {
                container.getAgent(airplane, true).start();
            }
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }
    }

}
