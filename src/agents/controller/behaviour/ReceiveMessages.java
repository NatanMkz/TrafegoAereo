package agents.controller.behaviour;

import agents.controller.Controller;
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
        ACLMessage msg = myAgent.receive();

        if (msg == null) {
            block();

            return;
        }

        Controller controller = (Controller) this.myAgent;
        String ontology = msg.getOntology();
        String airportAddress = "";

        switch (ontology) {
            case "wants-departure":
                System.out.println(controller.getName() + ": wants-departure. Airplane: " + msg.getSender().getName());

                for (Controller.AirportPlane airport : controller.airports) {
                    if (airport.planes.contains(msg.getSender().getName())) {
                        airportAddress = airport.airport;
                    }
                }

                this.myAgent.addBehaviour(new DepartureToAirport(controller, airportAddress, msg.getSender().getName()));

                break;
            case "autorize-departure":
                System.out.println(controller.getName() + ": autorize-departure. Airplane: " + msg.getContent());

                ACLMessage messageA = new ACLMessage(ACLMessage.PROPOSE);
                messageA.addReceiver(new AID(msg.getContent(), AID.ISGUID));
                messageA.setOntology("autorize-departure");
                controller.send(messageA);

                break;
            case "departure-finished":
                System.out.println(controller.getName() + ": departure-finished.");

                for (Controller.AirportPlane airport : controller.airports) {
                    if (airport.planes.contains(msg.getSender().getName())) {
                        airportAddress = airport.airport;
                    }
                }

                ACLMessage refresh = new ACLMessage(ACLMessage.PROPOSE);
                refresh.addReceiver(new AID(airportAddress, AID.ISGUID));
                refresh.setOntology("departure-finished");
                controller.send(refresh);

                break;
            case "wants-arrival":
                System.out.println(controller.getName() + ": wants-arrival. Airplane: " + msg.getSender().getName());
                this.myAgent.addBehaviour(new ArrivalToAirport(controller, msg.getContent(), msg.getSender().getName()));

                break;
            case "autorize-arrival":
                System.out.println(controller.getName() + ": autorize-arrival. Airplane: " + msg.getContent());
                ACLMessage messageB = new ACLMessage(ACLMessage.PROPOSE);
                messageB.addReceiver(new AID(msg.getContent(), AID.ISGUID));
                messageB.setOntology("autorize-arrival");
                controller.send(messageB);

                break;
            case "arrival-finished":
                System.out.println(controller.getName() + ": arrival-finished.");
                ACLMessage refresh4 = new ACLMessage(ACLMessage.PROPOSE);
                refresh4.addReceiver(new AID(msg.getContent(), AID.ISGUID));
                refresh4.setOntology("arrival-finished");
                controller.send(refresh4);

                ACLMessage reply = msg.createReply();
                reply.setOntology("new-travel");
                reply.setPerformative(ACLMessage.AGREE);
                reply.setContent(msg.getContent());
                controller.send(reply);

                break;
        }
    }

}
