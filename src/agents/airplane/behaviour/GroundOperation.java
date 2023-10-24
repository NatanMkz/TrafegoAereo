package agents.airplane.behaviour;

import agents.airplane.Airplane;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class GroundOperation extends CyclicBehaviour {

    public GroundOperation(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        Airplane agent = (Airplane) this.myAgent;

        switch (agent.status) {
            case 0:
                agent.addBehaviour(new WantsFuel(agent));
                break;
            case 2:
                agent.addBehaviour(new WantsPassengers(agent));
                break;
            case 4:
                agent.addBehaviour(new WantsDeparture(agent));
                break;
            default:
                block();

                break;
        }
    }
}
