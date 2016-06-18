package continualAssistants;

import OSPABA.*;
import Objekty.Vozidlo;
import simulation.*;
import agents.*;

//meta! id="62"
public class SchedulerAC extends Scheduler {

    public SchedulerAC(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentVozidiel", id="63", type="Start"
    public void processStart(MessageForm message) {
        Vozidlo pavoz = ((MyMessage) (message)).getVoz();
        double casPrevozu = ((MySimulation) (mySim())).getKonstanty().cestaCA / (pavoz.getRychlost()) * 3600;
        message.setCode(Mc.finish);
        if (pavoz.getCasOdchoduNaSkladku() + casPrevozu < myAgent().getPrichodPoslednehoNakladac()) {
            hold((myAgent().getPrichodPoslednehoNakladac() - pavoz.getCasOdchoduNaSkladku()), message);
        } else {
           myAgent().setPrichodPoslednehoNakladac(pavoz.getCasOdchoduNaSkladku()+casPrevozu);
            hold((casPrevozu), message);
        }

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
    public AgentVozidiel myAgent() {
        return (AgentVozidiel) super.myAgent();
    }

}
