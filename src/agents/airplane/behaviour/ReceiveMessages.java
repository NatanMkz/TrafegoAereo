package agents.airplane.behaviour;

import agents.airplane.Airplane;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveMessages extends CyclicBehaviour {

    public ReceiveMessages(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        Airplane agent = ((Airplane) this.myAgent);
        ACLMessage msg = agent.receive();

        if (msg != null) {
            String ontology = msg.getOntology();

            switch (ontology) {
                case "receive-fuel":
                    agent.fuel = Integer.parseInt(msg.getContent());
                    agent.status = 2;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(agent.getName() + " (message): receive-fuel. QTD: " + agent.fuel);
                    break;
                case "receive-passengers":
                    agent.status = 4;
                    agent.passengers = Integer.parseInt(msg.getContent());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(agent.getName() + " (message): receive-passengers. QTD: " + agent.passengers);
                    break;
                case "autorize-departure":
                    agent.addBehaviour(new DepartureAutorize(agent));
                    break;
                case "autorize-arrival":
                    agent.addBehaviour(new ArrivalAutorize(agent));
                    break;
                case "new-travel":
                    agent.addBehaviour(new CreateNewTravel(agent, msg.getContent()));
                    break;
            }

        } else {
            block();
        }

    }
}
