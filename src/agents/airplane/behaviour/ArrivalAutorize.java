package agents.airplane.behaviour;

import agents.airplane.Airplane;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ArrivalAutorize extends OneShotBehaviour {

    public ArrivalAutorize(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        Airplane agent = ((Airplane) this.myAgent);
        agent.status = 9;

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(agent.getName() + ": arrival-finished.");

        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
        message.addReceiver(new AID(agent.controllerAddress, AID.ISGUID));
        message.setOntology("arrival-finished");
        message.setContent(agent.toAddress);
        agent.send(message);
        agent.status = 10;

        // TODO alterar o toAddress para fromAddress, selecionar um novo aeroporto e dar reset no Agent
    }
}
