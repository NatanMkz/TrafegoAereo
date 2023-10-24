package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class Airplane extends Agent {

    int maxPassengersCapacity;
    String fromAddress;
    String toAddress;
    double routeConcluded;
    int passengers;
    double fuel;

    public Airplane() {
        this.maxPassengersCapacity = 0;
        this.passengers = 0;
        this.routeConcluded = 0;
        this.fuel = 0;
        this.fromAddress = "";
        this.toAddress = "";
    }

    public void setup() {
        /*
         * TODO (comportamentos)
         *   Comportamento que simula a porcentagem já voada do aeroporto A para o B
         *   Comportamento para solicitar pouso da aeronave caso essa porcentagem seja superior a 90%
         *
         * */

        Object[] args = getArguments();
        this.maxPassengersCapacity = (int) args[0];
        this.fromAddress = (String) args[1];
        this.toAddress = (String) args[2];

        addBehaviour(new ReceiveMessages(this));
        addBehaviour(new WantsPassengers(this, this.fromAddress, this.maxPassengersCapacity));

        System.out.println("Airplane " + this.getName() + " online.");
    }

    public static class WantsPassengers extends OneShotBehaviour {

        private String airportAddress;
        private int quantity;

        public WantsPassengers(Agent agent, String airportAddress, int quantity) {
            super(agent);
            this.airportAddress = airportAddress;
            this.quantity = quantity;
        }

        public void action() {
            System.out.println("Airplane " + this.myAgent.getName() + " solicitando " + quantity + " passageiros ao " + airportAddress + ".");
            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.addReceiver(new AID(airportAddress, AID.ISGUID));
            message.setOntology("wants-passengers");
            message.setContent(String.valueOf(quantity));
            this.myAgent.send(message);
        }
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
                    case "receive-passengers":
                        airplane.passengers = Integer.parseInt(msg.getContent());
                        System.out.println(this.myAgent.getName() + ": recebe-passageiro. QTD: " + airplane.passengers);

                        break;
                    default:
                        block();
                }

            } else {
                block();
            }

        }
    }

    // TODO Refazer essas funções
//    public double ChecaQuantidadeCombustivel(double quantidadeGasta) {
//        double combustivel = this.fuel;
//        try {
//            if (this.solicitaPouso == false && this.destino != "") {
//                this.fuel = combustivel - quantidadeGasta;
//            }
//        } catch (Exception ex) {
//            throw ex;
//        }
//
//        return this.fuel;
//    }
    //    public boolean LiberaPouso(String rotaAtual) {
//        boolean pouso = false;
//        try {
//            this.rotaAtual = rotaAtual;
//            //usei pra atualizar a propriedade da rota e declarar que o pouso foi solicitado
//            if (this.rotaAtual.equals(this.destino)) {
//                this.solicitaPouso = true;
//                //da pra colocar uns comandos pra ele interagir com o controle aereo e o aeroporto
//                //por enquanto eu vou deixar o pouso como true para ter um retorno mas isso pode ser uma request para os agentes da torre e do aeroporto
//                pouso = true;
//            }
//        } catch (Exception ex) {
//            throw ex;
//        }
//
//        return pouso;
//    }

}
