package continualAssistants;

import OSPABA.*;
import Objekty.Vozidlo;
import simulation.*;
import agents.*;

//meta! id="60"
public class SchedulerBC extends Scheduler {

    public SchedulerBC(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentVozidiel", id="61", type="Start"
    public void processStart(MessageForm message) {
        Vozidlo pavoz = ((MyMessage) message).getVoz();
        myAgent().getFrontCestaBC().add(pavoz);
       // System.out.println("auta jazdi po BC ceste "+((MyMessage)message).getVoz().getTyp());
        message.setCode(Mc.finish);
       double casPrevozu=0;
        if (pavoz.generujNahodu() < pavoz.getPravPoruchy()) {
           casPrevozu = ((((MySimulation) mySim()).getKonstanty().cestaBC / pavoz.getRychlost()) * 3600) + pavoz.getCasOpravy();
        } else {
          casPrevozu = ((((MySimulation) mySim()).getKonstanty().cestaBC / pavoz.getRychlost()) * 3600) ;
        } 
        pavoz.setCasOdchoduNaPrejazd(mySim().currentTime()+casPrevozu);
        hold(casPrevozu, message);
        
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
