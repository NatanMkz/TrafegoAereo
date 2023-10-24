package agents;

import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Airport extends Agent {

    int maxCapacity;
    List<String> airplanes;

    public Airport() {
    }

    protected void setup() {
        Object[] args = getArguments();
        this.maxCapacity = (int) args[0];

        addBehaviour(new ReceiveMessages(this));

        System.out.println("Airport " + this.getName() + " online.");
    }

    public static class ReceiveMessages extends CyclicBehaviour {

        public ReceiveMessages(Agent agent) {
            super(agent);
        }

        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();

            if (msg != null) {
                String ontology = msg.getOntology();

                switch (ontology) {
                    case "wants-passengers":
                        System.out.println(this.myAgent.getName() + ": wants-passengers. QTD: " + msg.getContent());

                        ACLMessage reply = msg.createReply();
                        reply.setOntology("receive-passengers");
                        reply.setPerformative(ACLMessage.AGREE);
                        reply.setContent(msg.getContent());
                        this.myAgent.send(reply);

                        break;
                    default:
                        block();
                }

            } else {
                block();
            }
        }
    }


}
