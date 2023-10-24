package agents.airplane.behaviour;

import agents.airplane.Airplane;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class FlightOperation extends TickerBehaviour {

    public FlightOperation(Agent a) {
        super(a, 500);
    }

    @Override
    protected void onTick() {
        Airplane agent = (Airplane) this.myAgent;

        if (agent.status == 6) {
            agent.status = 7;
        }

        if (agent.routeConcluded < 100) {
            agent.routeConcluded += 1;
        }

        boolean concluded = (agent.routeConcluded == 100);
        agent.fuel -= 0.5;
        agent.maintenance -= 0.2;

        System.out.println(agent.getName() + ": On flight... " + agent.routeConcluded + "%.");

        if ((agent.status == 7) && concluded) {
            agent.addBehaviour(new WantsArrival(agent));
        } else if (agent.status == 10) {
            stop();
        }
    }
}
