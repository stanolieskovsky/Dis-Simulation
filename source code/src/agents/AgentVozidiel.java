package agents;

import OSPABA.*;
import Objekty.Vozidlo;
import simulation.*;
import managers.*;
import continualAssistants.*;
import java.util.LinkedList;

//meta! id="2"
public class AgentVozidiel extends Agent {

    private double _prichodPoslednehoNakladac = 0;
    private double _prichodPoslednehoVykladac = 0;
    private LinkedList<Vozidlo> _frontCestaAB;
    private LinkedList<Vozidlo> _frontCestaCA;
    private LinkedList<Vozidlo> _frontCestaBC;

    public AgentVozidiel(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        _prichodPoslednehoNakladac = 0;
        _prichodPoslednehoVykladac = 0;
        _frontCestaAB = new LinkedList<>();
        _frontCestaCA = new LinkedList<>();
        _frontCestaBC = new LinkedList<>();
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerVozidiel(Id.managerVozidiel, mySim(), this);
        new SchedulerAB(Id.schedulerAB, mySim(), this);
        new SchedulerAC(Id.schedulerAC, mySim(), this);
        new SchedulerBC(Id.schedulerBC, mySim(), this);
        addOwnMessage(Mc.pohybBC);
        addOwnMessage(Mc.pohybAB);
        addOwnMessage(Mc.pohybCA);
    }
    //meta! tag="end"

    public double getPrichodPoslednehoNakladac() {
        return _prichodPoslednehoNakladac;
    }

    public void setPrichodPoslednehoNakladac(double _prichodPoslednehoNakladac) {
        this._prichodPoslednehoNakladac = _prichodPoslednehoNakladac;
    }

    public double getPrichodPoslednehoVykladac() {
        return _prichodPoslednehoVykladac;
    }

    public void setPrichodPoslednehoVykladac(double _prichodPoslednehoVykladac) {
        this._prichodPoslednehoVykladac = _prichodPoslednehoVykladac;
    }

    public LinkedList<Vozidlo> getFrontCestaAB() {
        return _frontCestaAB;
    }

    public void setFrontCestaAB(LinkedList<Vozidlo> _frontCestaAB) {
        this._frontCestaAB = _frontCestaAB;
    }

    public LinkedList<Vozidlo> getFrontCestaCA() {
        return _frontCestaCA;
    }

    public void setFrontCestaCA(LinkedList<Vozidlo> _frontCestaCA) {
        this._frontCestaCA = _frontCestaCA;
    }

    public LinkedList<Vozidlo> getFrontCestaBC() {
        return _frontCestaBC;
    }

    public void setFrontCestaBC(LinkedList<Vozidlo> _frontCestaBC) {
        this._frontCestaBC = _frontCestaBC;
    }

    public String vypisFront(LinkedList<Vozidlo> list) {
       String retAut="";
        for (Vozidlo prvok : list) {
         retAut=retAut+prvok.toVypis();
        }
        return retAut;
    }
}
