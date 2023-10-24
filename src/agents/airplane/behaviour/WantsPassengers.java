package agents.airplane.behaviour;

import agents.airplane.Airplane;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class WantsPassengers extends OneShotBehaviour {

    public WantsPassengers(Agent agent) {
        super(agent);
    }

    public void action() {
        Airplane agent = (Airplane) this.myAgent;
        agent.status = 3;
        System.out.println(agent.getName() + ": Wants passengers from " + agent.fromAddress + ".");
        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
        message.addReceiver(new AID(agent.fromAddress, AID.ISGUID));
        message.setOntology("wants-passengers");
        message.setContent(String.valueOf(agent.maxPassengersCapacity));
        agent.send(message);
    }

}
