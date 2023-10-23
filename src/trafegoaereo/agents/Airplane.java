package trafegoaereo.agents;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class Airplane extends Agent {

    int maxPassengersCapacity;
    int passengers;
    double fuel;
    double distance;
    String fromAddress;
    String toAddress;

    public void setup() {
        addBehaviour(new WantsPassengers(this.fromAddress, this.maxPassengersCapacity));

        /*
         * TODO (comportamentos)
         *   Comportamento que simula a porcentagem já voada do aeroporto A para o B
         *   Comportamento para solicitar pouso da aeronave caso essa porcentagem seja superior a 90%
         *
         * */
    }

    public Airplane() {
        this.maxPassengersCapacity = 0;
        this.passengers = 0;
        this.distance = 0;
        this.fuel = 0;
        this.fromAddress = "";
        this.toAddress = "";
    }

    public Airplane(
            int maxPassengersCapacity,
            int passengers,
            double fuel,
            String fromAddress,
            String toAddress
    ) {
        this.maxPassengersCapacity = maxPassengersCapacity;
        this.passengers = passengers;
        this.distance = distance;
        this.fuel = fuel;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }

    public static class WantsPassengers extends OneShotBehaviour {

        private String airportAddress;
        private int quantity;

        public WantsPassengers(String airportAddress, int quantity) {
            this.airportAddress = airportAddress;
            this.quantity = quantity;
        }

        @Override
        public void action() {
            
            // Enviar para o endereço do aeroporto, a quantidade de passageiros desejadas

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
