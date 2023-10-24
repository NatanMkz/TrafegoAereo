package agents.airplane.behaviour;

import agents.airplane.Airplane;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class WantsDeparture extends OneShotBehaviour {

    public WantsDeparture(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        Airplane agent = (Airplane) this.myAgent;
        agent.status = 5;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(agent.getName() + ": Wants departure from " + agent.fromAddress + ".");
        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
        message.addReceiver(new AID(agent.controllerAddress, AID.ISGUID));
        message.setOntology("wants-departure");
        agent.send(message);
    }
}

