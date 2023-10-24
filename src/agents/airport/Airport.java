package agents.airport;

import java.util.ArrayList;
import java.util.List;

import agents.airport.behaviour.ArrivalSchedule;
import agents.airport.behaviour.DepartureSchedule;
import agents.airport.behaviour.ReceiveMessages;
import jade.core.Agent;

public class Airport extends Agent {

    public int maxCapacity;
    public String controllerAddress;
    public List<String> departureList;
    public List<String> arrivalList;
    public boolean hasPlaneDeparture;
    public boolean hasPlaneArrival;

    public Airport() {
        this.maxCapacity = 0;
        this.controllerAddress = "";
        this.departureList = new ArrayList<>();
        this.arrivalList = new ArrayList<>();
        this.hasPlaneDeparture = false;
        this.hasPlaneArrival = false;
    }

    protected void setup() {
        this.maxCapacity = (int) getArguments()[0];
        this.controllerAddress = (String) getArguments()[1];

        addBehaviour(new ReceiveMessages(this));
        addBehaviour(new DepartureSchedule(this));
        addBehaviour(new ArrivalSchedule(this));

        System.out.println("Airport " + this.getName() + " online.");
    }

}
