package managers;

import OSPABA.*;
import Objekty.Vozidlo;
import simulation.*;
import agents.*;
import continualAssistants.*;

//meta! id="51"
public class ManagerOkolia extends Manager {

    public ManagerOkolia(int id, Simulation mySim, Agent myAgent) {
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

    //meta! sender="AgentModelu", id="52", type="Notice"
    public void processInit(MessageForm message) {
        MyMessage copyMsg = null;

        for (Vozidlo voz : ((MySimulation) mySim()).getListVozidiel()) {
            copyMsg = (MyMessage) message.createCopy();
            copyMsg.setCode(Mc.prichodAuta);
            copyMsg.setVoz(voz);
            copyMsg.setAddressee(mySim().findAgent(Id.agentModelu));
            //  System.out.println("poslane auto"+voz.getTyp());
            notice(copyMsg);
        }
        MessageForm copyMsg2 = copyMsg.createCopy();

        copyMsg2.setAddressee(Id.schedulerCelkovCas);
        startContinualAssistant(copyMsg2);
    }

    public void processFinish(MessageForm message) {
        ((MySimulation) mySim()).setCasSimulacie(mySim().currentTime());
        mySim().stopReplication();
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
            case Mc.init:
                processInit(message);
                break;

            case Mc.finish:
                processFinish(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentOkolia myAgent() {
        return (AgentOkolia) super.myAgent();
    }

}
