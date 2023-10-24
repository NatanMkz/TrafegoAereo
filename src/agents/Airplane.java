package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Airplane extends Agent {

    /*
    todo
     * 1 - Solicitando combustivel
     * 2 - Abastecido
     * 3 - Solicitando passageiros
     * 4 - Embarcado
     * 5 - Aguardando autorização
     * 6 - Decolando
     * 7 - Em percurso
     * 0 - Finalizado
     * */
    int status = 0;
    int passengers;
    double fuel;
    boolean waitingDeparture;

    String controllerAddress;
    int maxPassengersCapacity;
    String fromAddress;
    String toAddress;
    double routeConcluded;

    public Airplane() {
        this.passengers = 0;
        this.fuel = 0;
        this.waitingDeparture = false;

        this.controllerAddress = "";
        this.maxPassengersCapacity = 0;
        this.routeConcluded = 0;
        this.fromAddress = "";
        this.toAddress = "";
    }

    public void setup() {

        Object[] args = getArguments();
        this.maxPassengersCapacity = (int) args[0];
        this.fromAddress = (String) args[1];
        this.toAddress = (String) args[2];
        this.controllerAddress = (String) args[3];

        addBehaviour(new ReceiveMessages(this));
        addBehaviour(new GroundOperation(this));

        System.out.println("Airplane " + this.getName() + " online.");
    }

    public static class ReceiveMessages extends CyclicBehaviour {

        public ReceiveMessages(Agent agent) {
            super(agent);
        }

        @Override
        public void action() {
            ACLMessage msg = this.myAgent.receive();
            Airplane airplane = ((Airplane) this.myAgent);

            if (msg != null) {
                String ontology = msg.getOntology();

                switch (ontology) {
                    case "receive-fuel":
                        airplane.fuel = Integer.parseInt(msg.getContent());
                        System.out.println(this.myAgent.getName() + ": receive-fuel. QTD: " + airplane.fuel);
                        airplane.status = 2;

                        break;
                    case "receive-passengers":
                        airplane.passengers = Integer.parseInt(msg.getContent());
                        System.out.println(this.myAgent.getName() + ": receive-passengers. QTD: " + airplane.passengers);
                        airplane.status = 4;

                        break;

                    case "autorize-departure":
                        airplane.status = 6;
                        airplane.addBehaviour(new OneShotBehaviour() {
                            @Override
                            public void action() {
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }

                                Airplane airplane = ((Airplane) this.myAgent);
                                ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                                message.addReceiver(new AID(airplane.controllerAddress, AID.ISGUID));
                                message.setOntology("departure-finished");
                                airplane.send(message);

                                // TODO Iniciar comportamento de voo
                            }
                        });

                        break;
                }

            } else {
                block();
            }

        }
    }

    public static class GroundOperation extends CyclicBehaviour {

        public GroundOperation(Agent agent) {
            super(agent);
        }

        @Override
        public void action() {

            Airplane airplane = (Airplane) this.myAgent;

            switch (airplane.status) {
                case 0:
                    airplane.status = 1;
                    airplane.addBehaviour(new WantsFuel(airplane));

                    break;
                case 2:
                    airplane.status = 3;
                    airplane.addBehaviour(new WantsPassengers(airplane));

                    break;
                case 4:
                    airplane.status = 5;
                    airplane.addBehaviour(new WantsDeparture(airplane));
                default:
                    block();

                    break;
            }
        }
    }

    public static class WantsPassengers extends OneShotBehaviour {

        public WantsPassengers(Agent agent) {
            super(agent);
        }

        public void action() {
            Airplane airplane = (Airplane) this.myAgent;
            String print = "Airplane " + airplane.getName() + " solicitando " + airplane.maxPassengersCapacity +
                    " passageiros ao " + airplane.fromAddress + ".";
            System.out.println(print);

            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.addReceiver(new AID(airplane.fromAddress, AID.ISGUID));
            message.setOntology("wants-passengers");
            message.setContent(String.valueOf(airplane.maxPassengersCapacity));
            airplane.send(message);
        }

    }

    public static class WantsFuel extends OneShotBehaviour {

        public WantsFuel(Agent agent) {
            super(agent);
        }

        public void action() {
            Airplane airplane = (Airplane) this.myAgent;
            String print = "Airplane " + airplane.getName() + " solicitando gasolina ao " + airplane.fromAddress + ".";
            System.out.println(print);

            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.addReceiver(new AID(airplane.fromAddress, AID.ISGUID));
            message.setOntology("wants-fuel");
            message.setContent(String.valueOf(100));
            airplane.send(message);
        }
    }

    public static class WantsDeparture extends OneShotBehaviour {

        public WantsDeparture(Agent agent) {
            super(agent);
        }

        public void action() {
            Airplane airplane = (Airplane) this.myAgent;

            if ((airplane.fuel < 100) || (airplane.passengers == 0)) {
                return;
            }

            String print = "Airplane " + airplane.getName() + " solicitando decolagem " + airplane.controllerAddress + ".";
            System.out.println(print);

            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.addReceiver(new AID(airplane.controllerAddress, AID.ISGUID));
            message.setOntology("wants-departure");
            airplane.send(message);
        }
    }


}
