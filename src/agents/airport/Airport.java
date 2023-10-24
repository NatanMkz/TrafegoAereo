package agents.airport;

import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Airport extends Agent {

    int maxCapacity;
    String controllerAddress;
    List<String> departureList;
    List<String> arrivalList;
    boolean hasPlaneDeparture;
    boolean hasPlaneArrival;


    public Airport() {
        this.maxCapacity = 0;
        this.controllerAddress = "";
        this.departureList = new ArrayList<>();
        this.arrivalList = new ArrayList<>();
        this.hasPlaneDeparture = false;
        this.hasPlaneArrival = false;
    }

    protected void setup() {
        this.maxCapacity = (int) getArguments()[0];
        this.controllerAddress = (String) getArguments()[1];

        addBehaviour(new ReceiveMessages(this));
        addBehaviour(new DepartureSchedule(this));
        addBehaviour(new ArrivalSchedule(this));

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
                    case "departure-plane":
                        System.out.println(airport.getName() + ": departure-plane. Airplane: " + msg.getContent());
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
                    case "arrival-plane":
                        System.out.println(airport.getName() + ": arrival-plane.");
                        airport.arrivalList.addLast(msg.getContent());
                        ACLMessage msg2 = new ACLMessage(ACLMessage.PROPOSE);
                        msg2.addReceiver(new AID(airport.getName(), AID.ISGUID));
                        msg2.setOntology("refresh");
                        airport.send(msg2);

                        break;
                    case "arrival-finished":
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


    public static class ArrivalSchedule extends CyclicBehaviour {

        public ArrivalSchedule(Agent agent) {
            super(agent);
        }

        @Override
        public void action() {
            Airport airport = (Airport) this.myAgent;

            if (!airport.hasPlaneArrival && !airport.arrivalList.isEmpty()) {
                airport.hasPlaneArrival = true;
                String airplane = airport.arrivalList.removeFirst();

                System.out.println(airport.getName() + ": Arrival-Schedule. Airplane: " + airplane);

                ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                message.addReceiver(new AID(airport.controllerAddress, AID.ISGUID));
                message.setOntology("autorize-arrival");
                message.setContent(airplane);
                airport.send(message);
            } else {
                block();
            }
        }
    }

}
