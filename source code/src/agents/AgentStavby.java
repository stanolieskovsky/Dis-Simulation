package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;

//meta! id="4"
public class AgentStavby extends Agent {

    private double objemVoFronte = 0;
    private double vylozenyObjem = 0;

    public AgentStavby(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        objemVoFronte = 0;
        vylozenyObjem = 0;
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerStavby(Id.managerStavby, mySim(), this);
        addOwnMessage(Mc.zaradAuto);
        addOwnMessage(Mc.init);
        addOwnMessage(Mc.zaciaTokAKoniecVykladania);
        addOwnMessage(Mc.zaciatokAKoniecNakladania);
        addOwnMessage(Mc.pohybBC);
        addOwnMessage(Mc.pohybAB);
        addOwnMessage(Mc.pohybCA);
        addOwnMessage(Mc.koniec);
    }
	//meta! tag="end"

    public double getObjemVoFronte() {
        return objemVoFronte;
    }

    public void setObjemVoFronte(double objemVoFronte) {
        this.objemVoFronte = objemVoFronte;
    }

    public double getVylozenyObjem() {
        return vylozenyObjem;
    }

    public void setVylozenyObjem(double vylozenyObjem) {
        this.vylozenyObjem = vylozenyObjem;
    }

    public void plusObjemVoFronte(double objem) {
        this.objemVoFronte = objemVoFronte + objem;
    }

    public void plusVylozenyObjem(double objem) {
        this.vylozenyObjem = vylozenyObjem + objem;
    }
}
