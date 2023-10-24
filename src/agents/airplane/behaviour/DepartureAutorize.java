package agents.airplane.behaviour;

import agents.airplane.Airplane;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class DepartureAutorize extends OneShotBehaviour {

    public DepartureAutorize(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        Airplane agent = ((Airplane) this.myAgent);
        agent.status = 6;

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
        message.addReceiver(new AID(agent.controllerAddress, AID.ISGUID));
        message.setOntology("departure-finished");
        agent.send(message);
        agent.addBehaviour(new FlightOperation(agent));
    }
}
