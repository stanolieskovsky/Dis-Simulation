package continualAssistants;

import OSPABA.*;
import Objekty.Konstanty;
import simulation.*;
import agents.*;

//meta! id="114"
public class SchedulerPracDobaNaklad extends Scheduler {

    public SchedulerPracDobaNaklad(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentNakladaca", id="115", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.spustiDobuNakladaca);
        MessageForm copy = message.createCopy();
        copy.setCode(Mc.finish);
        if (((MySimulation) mySim()).isIsGrafN()) {
            if (myAgent().getPomocnaPreGraf() == 1) {
               myAgent().setPomocnaPreGraf2(0);
                MessageForm copyHodina = message.createCopy();
                copyHodina.setCode(Mc.pokracujPoHodine);
                hold(0, copyHodina);
            }
        }
        if (((MyMessage) message).getNakladacId() == 1) {
            myAgent().setCas1(0);
            hold(Konstanty.zaciatokNakladac * 3600, message);
            hold(Konstanty.zaciatokNakladac * 3600, copy);
        } else {
            myAgent().setCas2(0);
            hold(Konstanty.zaciatokNakladac2 * 3600, message);
            hold(Konstanty.zaciatokNakladac2 * 3600, copy);
        }

    }

    public void processSpusti(MessageForm message) {
        message.setCode(Mc.ukonciDobuNakladaca);
        if (((MyMessage) message).getNakladacId() == 1) {

            myAgent().setPracovnyCas1(true);
            myAgent().setCas1(0);
            myAgent().prehodZMrtveho();
            hold((Konstanty.koniecNakladac - Konstanty.zaciatokNakladac) * 3600, message);
        } else {

            myAgent().setPracovnyCas2(true);
            myAgent().setCas2(0);
            hold((Konstanty.koniecNakladac2 - Konstanty.zaciatokNakladac2) * 3600, message);
        }
    }

    public void processUkonci(MessageForm message) {
        message.setCode(Mc.spustiDobuNakladaca);
        MessageForm copy = message.createCopy();
        copy.setCode(Id.finish);
        if (((MyMessage) message).getNakladacId() == 1) {
            myAgent().setPracovnyCas1(false);
            myAgent().getSvyuzitieNakladaca1().addSample(myAgent().getCas1() / ((Konstanty.koniecNakladac - Konstanty.zaciatokNakladac) * 3600));
            hold((24 - Konstanty.koniecNakladac + Konstanty.zaciatokNakladac) * 3600, message);
            hold((24 - Konstanty.koniecNakladac + Konstanty.zaciatokNakladac) * 3600, copy);
        } else {
            myAgent().setPracovnyCas2(false);
            myAgent().getSvyuzitieNakladaca2().addSample(myAgent().getCas2() / ((Konstanty.koniecNakladac2 - Konstanty.zaciatokNakladac2) * 3600));
            myAgent().prehodDoMrtveho();
            hold((24 - Konstanty.koniecNakladac2 + Konstanty.zaciatokNakladac2) * 3600, message);
            hold((24 - Konstanty.koniecNakladac2 + Konstanty.zaciatokNakladac2) * 3600, copy);
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
            case Mc.pokracujPoHodine:
                vlozPoHodineDoGrafu(message);
                break;
            case Mc.spustiDobuNakladaca:
                processSpusti(message);
                break;
            case Mc.ukonciDobuNakladaca:
                processUkonci(message);
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

    private void vlozPoHodineDoGrafu(MessageForm message) {
        if(mySim().currentTime()/3600/Konstanty.pocethodinDoGrafu> Konstanty.pocetHodnotVgrafe *( ((MySimulation) mySim()).getOdsadenie()/100)){
        
       // if (myAgent().getPomocnaPreGraf2() > Konstanty.pocetHodnotVgrafe / ((MySimulation) mySim()).getOdsadenie()) {
            ((MySimulation) mySim()).pridajDoGrafuN(myAgent().getNalozene(), mySim().currentTime());
        }
       //  myAgent().setPomocnaPreGraf2(myAgent().getPomocnaPreGraf2()+1);
        message.setCode(Mc.pokracujPoHodine);
        hold(Konstanty.pocethodinDoGrafu*3600, message);
    }

}
