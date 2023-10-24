package agents;

import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Airport extends Agent {

    String controllerAddress;
    int maxCapacity;
    List<String> departureList;
    boolean hasPlaneDeparture;

    public Airport() {
        this.departureList = new ArrayList<>();
        this.hasPlaneDeparture = false;
        this.controllerAddress = "";
    }

    protected void setup() {
        Object[] args = getArguments();
        this.maxCapacity = (int) args[0];
        this.controllerAddress = (String) args[1];

        addBehaviour(new ReceiveMessages(this));
        addBehaviour(new DepartureSchedule(this));

        System.out.println("Airport " + this.getName() + " online.");
    }

    public static class ReceiveMessages extends CyclicBehaviour {

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
                        System.out.println(airport.getName() + ": wants-passengers. QTD: " + msg.getContent());

                        ACLMessage reply = msg.createReply();
                        reply.setOntology("receive-passengers");
                        reply.setPerformative(ACLMessage.AGREE);
                        reply.setContent(msg.getContent());
                        this.myAgent.send(reply);

                        break;
                    case "wants-fuel":
                        System.out.println(this.myAgent.getName() + ": wants-fuel. QTD: " + msg.getContent());

                        ACLMessage replyFuel = msg.createReply();
                        replyFuel.setOntology("receive-fuel");
                        replyFuel.setPerformative(ACLMessage.AGREE);
                        replyFuel.setContent(msg.getContent());
                        this.myAgent.send(replyFuel);

                        break;
                    case "queue-plane":
                        System.out.println(airport.getName() + ": queue-plane. Airplane: " + msg.getContent());
                        airport.departureList.addLast(msg.getContent());
                        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                        message.addReceiver(new AID(airport.getName(), AID.ISGUID));
                        message.setOntology("refresh");
                        airport.send(message);

                        break;
                    case "departure-finished":
                        System.out.println(airport.getName() + ": departure-finished.");

                        airport.hasPlaneDeparture = false;
                        ACLMessage refresh = new ACLMessage(ACLMessage.PROPOSE);
                        refresh.addReceiver(new AID(airport.getName(), AID.ISGUID));
                        refresh.setOntology("refresh");
                        airport.send(refresh);

                        break;

                    default:
                        block();
                }

            } else {
                block();
            }
        }
    }

    public static class DepartureSchedule extends CyclicBehaviour {

        public DepartureSchedule(Agent agent) {
            super(agent);
        }

        @Override
        public void action() {
            Airport airport = (Airport) this.myAgent;

            if (!airport.hasPlaneDeparture && !airport.departureList.isEmpty()) {
                airport.hasPlaneDeparture = true;
                String airplane = airport.departureList.removeFirst();

                System.out.println(airport.getName() + ": Departure-Schedule. Airplane: " + airplane);

                ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                message.addReceiver(new AID(airport.controllerAddress, AID.ISGUID));
                message.setOntology("autorize-departure");
                message.setContent(airplane);
                airport.send(message);
            } else {
                block();
            }
        }
    }


}
