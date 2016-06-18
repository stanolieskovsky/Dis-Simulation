package continualAssistants;

import OSPABA.*;
import Objekty.Vozidlo;
import simulation.*;
import agents.*;

//meta! id="39"
public class SchedulerAB extends Scheduler {

    public SchedulerAB(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentVozidiel", id="40", type="Start"
    public void processStart(MessageForm message) {
       //  System.out.println("auto jazdi po ceste AB "+((MyMessage)message).getVoz().getTyp());
        Vozidlo pavoz = ((MyMessage) (message)).getVoz();
        double casPrevozu = ((MySimulation) (mySim())).getKonstanty().cestaAB / (pavoz.getRychlost()) * 3600;
        message.setCode(Mc.finish);
        if ((pavoz.getCasOdchoduNaStavbu() + casPrevozu) < myAgent().getPrichodPoslednehoVykladac()) {
            hold((myAgent().getPrichodPoslednehoVykladac() - pavoz.getCasOdchoduNaStavbu()), message);
        } else {
             myAgent().setPrichodPoslednehoVykladac(pavoz.getCasOdchoduNaStavbu()+casPrevozu);
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
