package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;

//meta! id="14"
public class ManagerModelu extends Manager {

    public ManagerModelu(int id, Simulation mySim, Agent myAgent) {
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

    //meta! sender="AgentOkolia", id="54", type="Notice"
    public void processPrichodAuta(MessageForm message) {
        message.setCode(Mc.zaradAuto);
        message.setAddressee(mySim().findAgent(Id.agentStavby));
       // System.out.println("prislo auto do modelu> "+((MyMessage)message).getVoz().getTyp());
        notice(message);
    }

    //meta! sender="AgentStavby", id="21", type="Notice"
    public void processKoniec(MessageForm message) {
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.init:
                message.setAddressee(mySim().findAgent(Id.agentOkolia));
                 MessageForm newm=message.createCopy();
                notice(message);
                //MessageForm newm=((MessageForm)message).createCopy();
                newm.setAddressee(mySim().findAgent(Id.agentStavby)   );
                
                notice(newm);
                break;
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.koniec:
                processKoniec(message);
                break;

            case Mc.prichodAuta:
                processPrichodAuta(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentModelu myAgent() {
        return (AgentModelu) super.myAgent();
    }

}
