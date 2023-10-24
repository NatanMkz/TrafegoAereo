package agents.airplane;

import agents.airplane.behaviour.GroundOperation;
import agents.airplane.behaviour.ReceiveMessages;
import jade.core.Agent;

public class Airplane extends Agent {

    /*TODO
     * 0 - Parado
     * 1 - Em abastecimento
     * 2 - Abastecido
     * 3 - Embarcando passageiros
     * 4 - Embarcado
     * 5 - Aguardando autorização decolagem
     * 6 - Decolando
     * 7 - Em percurso
     * 8 - Aguardando autorização pouso
     * 9 - Pousando
     * 10 - Finalizado
     * */
    public int status = 0;
    public int passengers;
    public double fuel;
    public double maintenance;
    public String controllerAddress;
    public int maxPassengersCapacity;
    public String fromAddress;
    public String toAddress;
    public double routeConcluded;

    public Airplane() {
    }

    public void setup() {
        this.passengers = 0;
        this.fuel = 0;
        this.maintenance = 100;
        this.controllerAddress = "";
        this.maxPassengersCapacity = 0;
        this.routeConcluded = 0;
        this.fromAddress = "";
        this.toAddress = "";

        System.out.println("Airplane " + this.getName() + " online.");

        this.maxPassengersCapacity = (int) getArguments()[0];
        this.fromAddress = (String) getArguments()[1];
        this.toAddress = (String) getArguments()[2];
        this.controllerAddress = (String) getArguments()[3];

        addBehaviour(new ReceiveMessages(this));
        addBehaviour(new GroundOperation(this));
    }

}
