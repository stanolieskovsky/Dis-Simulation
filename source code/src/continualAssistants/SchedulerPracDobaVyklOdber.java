package continualAssistants;

import OSPABA.*;
import Objekty.Konstanty;
import simulation.*;
import agents.*;

//meta! id="102"
public class SchedulerPracDobaVyklOdber extends Scheduler {

    public SchedulerPracDobaVyklOdber(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentVykladaca", id="103", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.spustiDobuVykladaca);
        MessageForm copy = message.createCopy();
        MessageForm copy2 = message.createCopy();
        // copy2.setCode(Mc.spustiDobuOdoberania);
        copy.setCode(Id.finish);
        if (((MySimulation) mySim()).isIsGrafV()) {
            if (myAgent().getPomocnaPreGraf() == 1) {
                MessageForm copyHodina = message.createCopy();
                copyHodina.setCode(Mc.pokracujPoHodine);
                hold(0, copyHodina);
            }
        }

        if (((MyMessage) message).getVykladacId() == 1) {
            myAgent().setCas1(0);
            hold(Konstanty.zaciatokVykladac * 3600, message);
            hold(Konstanty.zaciatokVykladac * 3600, copy);
            //  hold(Konstanty.zaciatokOdoberania * 3600, copy2);
        } else {
            myAgent().setCas2(0);
            hold(Konstanty.zaciatokVykladac2 * 3600, message);
            hold(Konstanty.zaciatokVykladac2 * 3600, copy);
        }

    }

    public void processSpusti(MessageForm message) {
        message.setCode(Mc.ukonciDobuVykladaca);
        if (((MyMessage) message).getVykladacId() == 1) {
            myAgent().setCas1(0);
            myAgent().setPracovnyCas1(true);
            myAgent().prehodZMrtveho();
            hold((Konstanty.koniecVykladac - Konstanty.zaciatokVykladac) * 3600, message);
        } else {
            myAgent().setCas2(0);
            myAgent().setPracovnyCas2(true);
            hold((Konstanty.koniecVykladac2 - Konstanty.zaciatokVykladac2) * 3600, message);
        }
    }

    public void processUkonci(MessageForm message) {
        message.setCode(Mc.spustiDobuVykladaca);
        MessageForm copy = message.createCopy();
        copy.setCode(Id.finish);
        if (((MyMessage) message).getVykladacId() == 1) {
            myAgent().setPracovnyCas1(false);
            myAgent().getSvyuzitieVykladaca1().addSample(myAgent().getCas1() / ((Konstanty.koniecVykladac - Konstanty.zaciatokVykladac) * 3600));
            hold((24 - Konstanty.koniecVykladac + Konstanty.zaciatokVykladac) * 3600, message);
            hold((24 - Konstanty.koniecVykladac + Konstanty.zaciatokVykladac) * 3600, copy);
        } else {
            myAgent().setPracovnyCas2(false);
            myAgent().getSvyuzitieVykladaca2().addSample(myAgent().getCas2() / ((Konstanty.koniecVykladac2 - Konstanty.zaciatokVykladac2) * 3600));
            myAgent().prehodDoMrtveho();
            hold((24 - Konstanty.koniecVykladac2 + Konstanty.zaciatokVykladac2) * 3600, message);
            hold((24 - Konstanty.koniecVykladac2 + Konstanty.zaciatokVykladac2) * 3600, copy);
        }
    }

    private void processSpustiDomuOdoberania(MessageForm message) {
        myAgent().setIsOdoberanie(true);
        message.setCode(Mc.pokracujDobuOdoberania);
        hold(0, message);
        MessageForm copy = message.createCopy();
        copy.setCode(Mc.ukonciDobuOdoberania);
        hold((Konstanty.koniecOdoberania - Konstanty.zaciatokOdoberania) * 3600, copy);
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
            case Mc.spustiDobuVykladaca:
                processSpusti(message);
                break;

            case Mc.ukonciDobuVykladaca:
                processUkonci(message);
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

    private void vlozPoHodineDoGrafu(MessageForm message) {
        if(mySim().currentTime()/(3600*Konstanty.pocethodinDoGrafu)> Konstanty.pocetHodnotVgrafe*( ((MySimulation) mySim()).getOdsadenie()/100)){
      //  if (myAgent().getPomocnaPreGraf2() > Konstanty.pocetHodnotVgrafe / ((MySimulation) mySim()).getOdsadenie()) {
            ((MySimulation) mySim()).pridajDoGrafuV(myAgent().getVylozeneMnozstvo(), mySim().currentTime());
        }
         //myAgent().setPomocnaPreGraf2(myAgent().getPomocnaPreGraf2()+1);
        message.setCode(Mc.pokracujPoHodine);
        hold(Konstanty.pocethodinDoGrafu*3600, message);
    }
}
