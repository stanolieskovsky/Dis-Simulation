package continualAssistants;

import OSPABA.*;
import Objekty.Konstanty;
import simulation.*;
import agents.*;

//meta! id="119"
public class SchedulerOdberu extends Scheduler {

    public SchedulerOdberu(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentVykladaca", id="120", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.spustiDobuOdoberania);
        hold(Konstanty.zaciatokOdoberania * 3600, message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
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

    private void processUkonciDobuOdoberania(MessageForm message) {
        myAgent().setIsOdoberanie(false);
        odober();
        MessageForm copy = message.createCopy();
        copy.setCode(Mc.finish);
        hold(0, copy);
        message.setCode(Mc.spustiDobuOdoberania);
        hold((24 - Konstanty.koniecOdoberania + Konstanty.zaciatokOdoberania) * 3600, message);
    }

    private void processPokracujDobuOdoberania(MessageForm message) {
        if (myAgent().isIsOdoberanie() == true) {
            odober();
            MessageForm copy = message.createCopy();
            copy.setCode(Mc.finish);
            hold(0, copy);
            message.setCode(Mc.pokracujDobuOdoberania);
            hold(30 * 60, message);
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.finish:
                assistantFinished(message);
                break;
            case Mc.start:
                processStart(message);
                break;
            case Mc.spustiDobuOdoberania:
                processSpustiDomuOdoberania(message);
                break;
            case Mc.pokracujDobuOdoberania:
                processPokracujDobuOdoberania(message);
                break;
            case Mc.ukonciDobuOdoberania:
                processUkonciDobuOdoberania(message);
                break;
            default:
                processDefault(message);
                break;
        }
    }

    //meta! tag="end"
    private void odober() {
        double cislo = myAgent().getGen().genRovnomerne();
        int odobrat = 0;
        if (cislo < 0.02) {
            odobrat = myAgent().getGen().genDiskretne(10, 20, 0);
        } else if (cislo < 0.22) {
            odobrat = myAgent().getGen().genDiskretne(21, 48, 1);
        } else if (cislo < 0.55) {
            odobrat = myAgent().getGen().genDiskretne(49, 65, 2);
        } else if (cislo < 0.82) {
            odobrat = myAgent().getGen().genDiskretne(66, 79, 3);
        } else {
            odobrat = myAgent().getGen().genDiskretne(80, 99, 4);
        }
        if (myAgent().getVylozeneMnozstvo() >= odobrat) {
            myAgent().plusUspesne();
            myAgent().plusCelkove();
            myAgent().plusIbaOdobratepreStat(odobrat);
            myAgent().setVylozeneMnozstvo(myAgent().getVylozeneMnozstvo() - odobrat);
            myAgent().setVylozeneMnozstvoNaKontrolu(myAgent().getVylozeneMnozstvoNaKontrolu() - odobrat);
        } else {
            if (((MySimulation) mySim()).isIsOdstaveny()) {
                if (mySim().currentTime() > Konstanty.odstavenieDodavatelaACas) {
                    if (myAgent().getPrvezlyhanie() == 0) {
                        myAgent().setCasZlyhania(mySim().currentTime());
                        myAgent().setPrvezlyhanie(5);
                    }
                }
            }
            myAgent().plusIbaOdobratepreStat(myAgent().getVylozeneMnozstvo());
            myAgent().plusCelkove();
            myAgent().setVylozeneMnozstvo(0);
            myAgent().setVylozeneMnozstvoNaKontrolu(0);
        }
    }

    @Override
    public AgentVykladaca myAgent() {
        return (AgentVykladaca) super.myAgent();
    }

}
