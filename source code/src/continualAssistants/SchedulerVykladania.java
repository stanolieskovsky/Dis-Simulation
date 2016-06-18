package continualAssistants;

import OSPABA.*;
import Objekty.Vozidlo;
import simulation.*;
import agents.*;

//meta! id="47"
public class SchedulerVykladania extends Scheduler {

    public SchedulerVykladania(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentVykladaca", id="48", type="Start"
    public void processStart(MessageForm message) {
        Vozidlo pavoz = ((MyMessage) (message)).getVoz();
       //  System.out.println("auto v0 Vykladaci "+((MyMessage)message).getVoz().getTyp());
        double casPrekladu = ((pavoz.getNaklad()) / ((MySimulation) (mySim())).getKonstanty().vykonVykladac) * 3600;
        myAgent().getVozVykladaci().nastav(mySim().currentTime(),casPrekladu+mySim().currentTime());
        myAgent().plusIbaVylozenepreStat(pavoz.getNaklad());
        myAgent().plusCas1(casPrekladu);
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
    public AgentVykladaca myAgent() {
        return (AgentVykladaca) super.myAgent();
    }

}
