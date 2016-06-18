package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;

//meta! id="89"
public class SchedulerDodC extends Scheduler {

    public SchedulerDodC(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentNakladaca", id="90", type="Start"
    public void processStart(MessageForm message) {
        //   System.out.println("Som sa dostal do C initom");
        message.setCode(Mc.pridanieMaterialu);
        hold(myAgent().getExpoC().sample() * 60, message);
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
         double cislo = myAgent().getMaterialC().nextDouble();
        if (cislo<0.00047) {return 5;
        } 
        else if (cislo<0.00355){return 6;} 
        else if (cislo<0.00734){return 7;}
         else if (cislo<.01326){return 8;}
         else if (cislo<0.02367){return 9;}
         else if (cislo<0.03101){return 10;}
         else if (cislo<0.04735){return 11;}
         else if (cislo<0.06605){return 12;}
         else if (cislo<0.08452){return 13;}
         else if (cislo<0.10298){return 14;}
         else if (cislo<0.12476){return 15;}
         else if (cislo<0.14773){return 16;}
         else if (cislo<0.16643){return 17;}
         else if (cislo<0.19105){return 18;}
         else if (cislo<0.21733){return 19;}
         else if (cislo<0.23840){return 20;}
         else if (cislo<0.38234){return 21;}
         else if (cislo<0.58807){return 22;}
         else if (cislo<0.81297){return 23;}
         
         else{return 24;}
    
    }
}
