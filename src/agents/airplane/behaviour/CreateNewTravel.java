package agents.airplane.behaviour;

import agents.airplane.Airplane;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class CreateNewTravel extends OneShotBehaviour {

    // TODO Implementar o envio do novo aeroporto
    public String newDestiny;

    public CreateNewTravel(Agent agent, String newDestiny) {
        super(agent);

        this.newDestiny = newDestiny;
    }

    @Override
    public void action() {
        Airplane agent = ((Airplane) this.myAgent);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(agent.getName() + ": new-travel.");

        String toAddress = agent.toAddress;
        agent.fromAddress = agent.toAddress;
        agent.toAddress = toAddress;
        agent.status = 0;
        agent.maintenance = 100;
        agent.routeConcluded = 0;

        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
        message.addReceiver(new AID(agent.getName(), AID.ISGUID));
        message.setOntology("refresh");
        agent.send(message);

    }
}
