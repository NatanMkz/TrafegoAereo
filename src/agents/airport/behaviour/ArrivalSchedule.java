package agents.airport.behaviour;

import agents.airport.Airport;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ArrivalSchedule extends CyclicBehaviour {

    public ArrivalSchedule(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        Airport airport = (Airport) this.myAgent;

        if (!airport.hasPlaneArrival && !airport.arrivalList.isEmpty()) {
            airport.hasPlaneArrival = true;
            String airplane = airport.arrivalList.removeFirst();

            System.out.println(airport.getName() + ": Arrival-Schedule. Airplane: " + airplane);

            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.addReceiver(new AID(airport.controllerAddress, AID.ISGUID));
            message.setOntology("autorize-arrival");
            message.setContent(airplane);
            airport.send(message);
        } else {
            block();
        }
    }
}