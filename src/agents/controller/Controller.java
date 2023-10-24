package agents.controller;

import agents.controller.behaviour.ReceiveMessages;
import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

import java.util.ArrayList;
import java.util.List;

public class Controller extends Agent {

    public List<AirportPlane> airports;

    public Controller() {
        this.airports = new ArrayList<>();
    }

    protected void setup() {
        super.setup();
        this.createAirports();
        this.createAirplanes();

        addBehaviour(new ReceiveMessages(this));

        System.out.println("Controller " + this.getName() + " online.");
    }

    private void createAirports() {
        try {
            AgentContainer container = this.getContainerController();
            this.airports.add(new AirportPlane(container.createNewAgent("Curitiba", "agents.airport.Airport", new Object[]{3, container.getAgent("Controlador").getName()}).getName()));
            this.airports.add(new AirportPlane(container.createNewAgent("Floripa", "agents.airport.Airport", new Object[]{5, container.getAgent("Controlador").getName()}).getName()));

            for (AirportPlane airport : this.airports) {
                container.getAgent(airport.airport, true).start();
            }
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }
    }

    private void createAirplanes() {
        try {
            AgentContainer container = this.getContainerController();
            Object[] args = new Object[]{50, container.getAgent("Curitiba").getName(), container.getAgent("Floripa").getName(), container.getAgent("Controlador").getName()};
            this.airports.get(0).planes.add(container.createNewAgent("Boeing", "agents.airplane.Airplane", args).getName());

            for (AirportPlane airports : this.airports) {
                for (String airplane : airports.planes) {
                    container.getAgent(airplane, true).start();
                }
            }
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }
    }

    public static class AirportPlane {
        public String airport;
        public List<String> planes;

        public AirportPlane(String airport) {
            this.airport = airport;
            this.planes = new ArrayList<>();
        }
    }

}
