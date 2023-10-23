package agents;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

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

        addBehaviour(new WantsPassengers(this.fromAddress, this.maxPassengersCapacity));

        System.out.println("Sou um avião e vou de " + this.fromAddress + " para " + this.toAddress + ".");
    }

    public static class WantsPassengers extends OneShotBehaviour {

        private String airportAddress;
        private int quantity;

        public WantsPassengers(String airportAddress, int quantity) {
            this.airportAddress = airportAddress;
            this.quantity = quantity;
        }


        public void action() {
            // TODO Enviar para o endereço do aeroporto, a quantidade de passageiros desejadas


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
