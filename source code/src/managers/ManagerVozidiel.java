package managers;

import OSPABA.*;
import Objekty.Vozidlo;
import simulation.*;
import agents.*;
import continualAssistants.*;

//meta! id="2"
public class ManagerVozidiel extends Manager {

    public ManagerVozidiel(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        if (petriNet() != null) {
            petriNet().clear();
        }
    }

    //meta! sender="SchedulerBC", id="61", type="Finish"
    public void processFinishSchedulerBC(MessageForm message) {
        // System.out.println("koniec auta na BC : "+((MyMessage)message).getVoz().getTyp());
        for (int i = 0; i < myAgent().getFrontCestaBC().size(); i++) {
            if (myAgent().getFrontCestaBC().get(i).getCasOdchoduNaPrejazd() == mySim().currentTime()) {
                ((MyMessage) message).setVoz(myAgent().getFrontCestaBC().remove(i));
                break;
            }

        }

        message.setCode(Mc.pohybBC);
        message.setAddressee(mySim().findAgent(Id.agentStavby));
        response(message);
    }

    //meta! sender="SchedulerAB", id="40", type="Finish"
    public void processFinishSchedulerAB(MessageForm message) {
        //    System.out.println("koniec auta na AB ceste "+((MyMessage)message).getVoz().getTyp());
        ((MyMessage) message).setVoz(myAgent().getFrontCestaAB().removeFirst());
        message.setCode(Mc.pohybAB);
        message.setAddressee(mySim().findAgent(Id.agentStavby));
        response(message);
    }

    //meta! sender="SchedulerAC", id="63", type="Finish"
    public void processFinishSchedulerAC(MessageForm message) {
        //   System.out.println("koniec auta na AC : "+((MyMessage)message).getVoz().getTyp());
        ((MyMessage) message).setVoz(myAgent().getFrontCestaCA().removeFirst());
        message.setCode(Mc.pohybCA);
        message.setAddressee(mySim().findAgent(Id.agentStavby));
        response(message);
    }

    //meta! sender="AgentStavby", id="33", type="Request"
    public void processPohybBC(MessageForm message) {
        //    System.out.println("prisiel na BC cestu "+((MyMessage)message).getVoz().getTyp());
        startWorkBC(message);
    }

    //meta! sender="AgentStavby", id="32", type="Request"
    public void processPohybAB(MessageForm message) {
        myAgent().getFrontCestaAB().add(((MyMessage) message).getVoz());
        //   System.out.println("prisiel na AB cestu "+((MyMessage)message).getVoz().getTyp());
        startWorkAB(message);

    }

    //meta! sender="AgentStavby", id="34", type="Request"
    public void processPohybCA(MessageForm message) {
        myAgent().getFrontCestaCA().add(((MyMessage) message).getVoz());
        //    System.out.println("prisiel na CA cestu "+((MyMessage)message).getVoz().getTyp());
        startWorkCA(message);

    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.finish:
                switch (message.sender().id()) {
                    case Id.schedulerBC:
                        processFinishSchedulerBC(message);
                        break;

                    case Id.schedulerAC:
                        processFinishSchedulerAC(message);
                        break;

                    case Id.schedulerAB:
                        processFinishSchedulerAB(message);
                        break;
                }
                break;

            case Mc.pohybBC:
                processPohybBC(message);
                break;

            case Mc.pohybAB:
                processPohybAB(message);
                break;

            case Mc.pohybCA:
                processPohybCA(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }

    //meta! tag="end"
    private void startWorkAB(MessageForm message) {
        //   System.out.println("zaciatok auta na AB ceste "+((MyMessage)message).getVoz().getTyp());
        Vozidlo pavoz = ((MyMessage) message).getVoz();
        pavoz.setCasOdchoduNaStavbu(((MySimulation) mySim()).currentTime());
        message.setAddressee(myAgent().findAssistant(Id.schedulerAB));
        startContinualAssistant(message);
    }

    private void startWorkBC(MessageForm message) {
        //      System.out.println("zaciatok auta na BC ceste "+((MyMessage)message).getVoz().getTyp());
        Vozidlo pavoz = ((MyMessage) message).getVoz();
        pavoz.setCasOdchoduNaPrejazd(((MySimulation) mySim()).currentTime());
        message.setAddressee(myAgent().findAssistant(Id.schedulerBC));
        startContinualAssistant(message);
    }

    private void startWorkCA(MessageForm message) {
        //      System.out.println("zaciatok auta na CA ceste "+((MyMessage)message).getVoz().getTyp());
        Vozidlo pavoz = ((MyMessage) message).getVoz();
        pavoz.setCasOdchoduNaSkladku(((MySimulation) mySim()).currentTime());
        message.setAddressee(myAgent().findAssistant(Id.schedulerAC));
        startContinualAssistant(message);
    }

    @Override
    public AgentVozidiel myAgent() {
        return (AgentVozidiel) super.myAgent();
    }

}
