package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

import java.util.ArrayList;
import java.util.List;

public class Controller extends Agent {

    List<AirportPlane> airports;

    public Controller() {
        this.airports = new ArrayList<>();
    }

    protected void setup() {
        super.setup();
        this.createAirports();
        this.createAirplanes();

        addBehaviour(new ReceiveMessages(this));

        System.out.println("Controller " + this.getName() + " online.");
    }

    private void createAirports() {
        try {
            AgentContainer container = this.getContainerController();
            this.airports.add(
                    new AirportPlane(
                            container.createNewAgent(
                                    "Curitiba",
                                    "agents.Airport",
                                    new Object[]{
                                            3,
                                            container.getAgent("Controlador").getName()
                                    }
                            ).getName()
                    )
            );
            this.airports.add(
                    new AirportPlane(
                            container.createNewAgent(
                                    "Floripa",
                                    "agents.Airport",
                                    new Object[]{
                                            5,
                                            container.getAgent("Controlador").getName()
                                    }
                            ).getName()
                    )
            );

            for (AirportPlane airport : this.airports) {
                container.getAgent(airport.airport, true).start();
            }
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }
    }

    private void createAirplanes() {
        try {
            AgentContainer container = this.getContainerController();
            Object[] args = new Object[]{
                    50,
                    container.getAgent("Curitiba").getName(),
                    container.getAgent("Floripa").getName(),
                    container.getAgent("Controlador").getName()
            };
            this.airports.get(0).planes.add(
                    container.createNewAgent(
                            "Boeing",
                            "agents.Airplane",
                            args
                    ).getName()
            );

            for (AirportPlane airports : this.airports) {
                for (String airplane : airports.planes) {
                    container.getAgent(airplane, true).start();
                }
            }
        } catch (ControllerException e) {
            throw new RuntimeException(e);
        }
    }

    public static class ReceiveMessages extends CyclicBehaviour {

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

            switch (ontology) {
                case "wants-departure":
                    System.out.println(controller.getName() + ": wants-departure. Airplane: " + msg.getSender().getName());
                    String airportAddress = "";

                    for (AirportPlane airport : controller.airports) {
                        if (airport.planes.contains(msg.getSender().getName())) {
                            airportAddress = airport.airport;
                        }
                    }

                    this.myAgent.addBehaviour(new QueueToAirport(controller, airportAddress, msg.getSender().getName()));

                    break;
            }
        }

    }

    public static class QueueToAirport extends OneShotBehaviour {

        private final String airport;
        private final String airplane;

        public QueueToAirport(Agent agent, String airport, String airplane) {
            super(agent);

            this.airport = airport;
            this.airplane = airplane;
        }

        @Override
        public void action() {
            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.addReceiver(new AID(this.airport, AID.ISGUID));
            message.setOntology("queue-plane");
            message.setContent(String.valueOf(this.airplane));
            this.myAgent.send(message);
        }
    }

    private static class AirportPlane {
        String airport;
        List<String> planes;

        public AirportPlane(String airport) {
            this.airport = airport;
            this.planes = new ArrayList<>();
        }
    }

}
