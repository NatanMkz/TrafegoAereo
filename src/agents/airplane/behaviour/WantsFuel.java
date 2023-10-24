package agents.airplane.behaviour;

import agents.airplane.Airplane;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class WantsFuel extends OneShotBehaviour {

    public WantsFuel(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        Airplane agent = (Airplane) this.myAgent;
        agent.status = 1;
        System.out.println(agent.getName() + ": Wants fuel from " + agent.fromAddress + ".");
        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
        message.addReceiver(new AID(agent.fromAddress, AID.ISGUID));
        message.setOntology("wants-fuel");
        message.setContent(String.valueOf(100));
        agent.send(message);
    }
}

