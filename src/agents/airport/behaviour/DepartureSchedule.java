package agents.airport.behaviour;

import agents.airport.Airport;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class DepartureSchedule extends CyclicBehaviour {

    public DepartureSchedule(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        Airport airport = (Airport) this.myAgent;

        if (!airport.hasPlaneDeparture && !airport.departureList.isEmpty()) {
            airport.hasPlaneDeparture = true;
            String airplane = airport.departureList.removeFirst();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(airport.getName() + ": Departure-Schedule. Airplane: " + airplane);

            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.addReceiver(new AID(airport.controllerAddress, AID.ISGUID));
            message.setOntology("autorize-departure");
            message.setContent(airplane);
            airport.send(message);
        } else {
            block();
        }
    }

}