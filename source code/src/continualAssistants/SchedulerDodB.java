package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;

//meta! id="87"
public class SchedulerDodB extends Scheduler {

    public SchedulerDodB(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentNakladaca", id="88", type="Start"
    public void processStart(MessageForm message) {
        // System.out.println("Som sa dostal do B initom");
        message.setCode(Mc.pridanieMaterialu);
        hold(myAgent().getExpoB().sample() * 60, message);
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
         double cislo = myAgent().getMaterialB().nextDouble();
        if (cislo<0.0004) {return 6;
        } 
         else if (cislo<0.0014){return 7;}
         else if (cislo<0.0042){return 8;}
         else if (cislo<0.0074){return 9;}
         else if (cislo<0.0119){return 10;}
         else if (cislo<0.0193){return 11;}
         else if (cislo<0.0263){return 12;}
         else if (cislo<0.0340){return 13;}
         else if (cislo<0.0456){return 14;}
         else if (cislo<0.0561){return 15;}
         else if (cislo<0.1200){return 16;}
         else if (cislo<0.3028){return 17;}
         else if (cislo<0.4968){return 18;}
         else if (cislo<0.7067){return 19;}
         else if (cislo<0.9291){return 20;}
         
         else{return 21;}
    }
}
