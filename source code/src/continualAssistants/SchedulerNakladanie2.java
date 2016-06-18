package continualAssistants;

import OSPABA.*;
import Objekty.Vozidlo;
import simulation.*;
import agents.*;

//meta! id="96"
public class SchedulerNakladanie2 extends Scheduler {

    public SchedulerNakladanie2(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentNakladaca", id="97", type="Start"
    public void processStart(MessageForm message) {
        Vozidlo pavoz = ((MyMessage) (message)).getVoz();
        double casPrekladu = ((pavoz.getNaklad()) / ((MySimulation) (mySim())).getKonstanty().vykonNakladac2) * 3600;
        myAgent().getVozVNakladaci2().nastav(mySim().currentTime(),casPrekladu+mySim().currentTime());
        myAgent().plusCas2(casPrekladu);
        message.setCode(Mc.finish);
        hold(casPrekladu, message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.start:
                processStart(message);
                break;
            case Mc.finish:
                assistantFinished(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentNakladaca myAgent() {
        return (AgentNakladaca) super.myAgent();
    }

}
