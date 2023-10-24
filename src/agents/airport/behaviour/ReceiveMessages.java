package agents.airport.behaviour;

import agents.airport.Airport;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveMessages extends CyclicBehaviour {

    public ReceiveMessages(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        Airport airport = (Airport) this.myAgent;
        ACLMessage msg = airport.receive();

        if (msg != null) {
            String ontology = msg.getOntology();

            switch (ontology) {
                case "wants-passengers":
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(airport.getName() + ": wants-passengers. QTD: " + msg.getContent());
                    ACLMessage reply = msg.createReply();
                    reply.setOntology("receive-passengers");
                    reply.setPerformative(ACLMessage.AGREE);
                    reply.setContent(msg.getContent());
                    this.myAgent.send(reply);

                    break;
                case "wants-fuel":
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(this.myAgent.getName() + ": wants-fuel. QTD: " + msg.getContent());
                    ACLMessage replyFuel = msg.createReply();
                    replyFuel.setOntology("receive-fuel");
                    replyFuel.setPerformative(ACLMessage.AGREE);
                    replyFuel.setContent(msg.getContent());
                    this.myAgent.send(replyFuel);

                    break;
                case "departure-plane":
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(airport.getName() + ": departure-plane. Airplane: " + msg.getContent());
                    airport.departureList.addLast(msg.getContent());
                    ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                    message.addReceiver(new AID(airport.getName(), AID.ISGUID));
                    message.setOntology("refresh");
                    airport.send(message);

                    break;
                case "departure-finished":
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(airport.getName() + ": departure-finished.");
                    airport.hasPlaneDeparture = false;
                    ACLMessage refresh = new ACLMessage(ACLMessage.PROPOSE);
                    refresh.addReceiver(new AID(airport.getName(), AID.ISGUID));
                    refresh.setOntology("refresh");
                    airport.send(refresh);

                    break;
                case "arrival-plane":
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(airport.getName() + ": arrival-plane.");
                    airport.arrivalList.addLast(msg.getContent());
                    ACLMessage msg2 = new ACLMessage(ACLMessage.PROPOSE);
                    msg2.addReceiver(new AID(airport.getName(), AID.ISGUID));
                    msg2.setOntology("refresh");
                    airport.send(msg2);

                    break;
                case "arrival-finished":
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(airport.getName() + ": arrival-finished.");
                    airport.hasPlaneArrival = false;
                    ACLMessage refresh3 = new ACLMessage(ACLMessage.PROPOSE);
                    refresh3.addReceiver(new AID(airport.getName(), AID.ISGUID));
                    refresh3.setOntology("refresh");
                    airport.send(refresh3);

                    break;
                default:
                    block();
            }

        } else {
            block();
        }
    }
}

