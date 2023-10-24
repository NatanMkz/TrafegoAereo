package agents.controller.behaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ArrivalToAirport extends OneShotBehaviour {

    private final String airport;
    private final String airplane;

    public ArrivalToAirport(Agent agent, String airport, String airplane) {
        super(agent);

        this.airport = airport;
        this.airplane = airplane;
    }

    @Override
    public void action() {
        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
        message.addReceiver(new AID(this.airport, AID.ISGUID));
        message.setOntology("arrival-plane");
        message.setContent(String.valueOf(this.airplane));
        this.myAgent.send(message);
    }
}

