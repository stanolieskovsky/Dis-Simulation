package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;

//meta! id="14"
public class AgentModelu extends Agent {

    public AgentModelu(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerModelu(Id.managerModelu, mySim(), this);
        addOwnMessage(Mc.prichodAuta);
        addOwnMessage(Mc.koniec);
        addOwnMessage(Mc.init);
    }

    //meta! tag="end"

    public void spustiSimulaciu() {
        MyMessage message = new MyMessage(mySim());
        message.setCode(Mc.init);
        message.setAddressee(this);
        message.setIs2vykladac(((MySimulation)mySim()).isIsDruhy());
        message.setIsOdstavenyA(((MySimulation)mySim()).isIsOdstaveny());
        manager().notice(message);
    }
}
