package continualAssistants;

import OSPABA.*;
import Objekty.Konstanty;
import simulation.*;
import agents.*;

//meta! id="85"
public class SchedulerDodA extends Scheduler {

    public SchedulerDodA(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentNakladaca", id="86", type="Start"
    public void processStart(MessageForm message) {
        //System.out.println("Som sa dostal do A initom");
        message.setCode(Mc.pridanieMaterialu);
        double casDodavky = myAgent().getExpoA().sample() * 60;
        if (myAgent().isKontrola()) {
            if (mySim().currentTime()+casDodavky <= Konstanty.odstavenieDodavatelaACas) {
                hold(casDodavky , message);
            }
        } else {
            hold(casDodavky, message);
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
            case Mc.pridanieMaterialu:
                ((MyMessage) message).setMnozstvoMater(vygenerujMnozstvo());
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

    public int vygenerujMnozstvo() {
        double cislo = myAgent().getMaterialA().nextDouble();
        if (cislo < 0.0033) {
            return 5;
        } else if (cislo < 0.0161) {
            return 6;
        } else if (cislo < 0.0546) {
            return 7;
        } else if (cislo < 0.1046) {
            return 8;
        } else if (cislo < 0.1671) {
            return 9;
        } else if (cislo < 0.2415) {
            return 10;
        } else if (cislo < 0.3375) {
            return 11;
        } else if (cislo < 0.4442) {
            return 12;
        } else if (cislo < 0.5749) {
            return 13;
        } else if (cislo < 0.7237) {
            return 14;
        } else if (cislo < 0.8838) {
            return 15;
        } else if (cislo < 0.9996) {
            return 16;
        } else {
            return 19;
        }

    }

}
