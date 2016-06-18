package agents;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPRNG.ExponentialRNG;
import OSPRNG.GammaRNG;
import OSPStat.Stat;
import OSPStat.WStat;
import Objekty.Konstanty;
import Objekty.Vozidlo;
import simulation.*;
import managers.*;
import continualAssistants.*;
import java.util.LinkedList;
import java.util.Random;

//meta! id="15"
public class AgentNakladaca extends Agent {

    private SimQueue<MessageForm> listVozidielNakladac = new SimQueue<>();
    private SimQueue<MessageForm> deadlistVozidielNakladac = new SimQueue<>();
    private boolean pracovnyCas1 = false;
    private boolean pracovnyCas2 = false;
    private boolean _isWorking = false;
    private boolean _isWorking2 = false;
    private boolean kontrola = false;
    ExponentialRNG expoA = new ExponentialRNG(45.9, MySimulation.getNasada(), 0.999);
    ExponentialRNG expoB = new ExponentialRNG(36.8, MySimulation.getNasada(), 3.0);
    ExponentialRNG expoC = new ExponentialRNG(25.8, MySimulation.getNasada(), 0.999);
    Random materialA = new Random(MySimulation.getNasada().nextLong());
    Random materialB = new Random(MySimulation.getNasada().nextLong());
    Random materialC = new Random(MySimulation.getNasada().nextLong());
    private Vozidlo vozVNakladaci;
    private Vozidlo vozVNakladaci2;
    double nalozene;//to je to co mi nalozili dodavatelia, nie auta kolko si vzali,
 
    private Stat SpriemCasCakaniaFrontNakladac;
    private Stat SvyuzitieNakladaca1;
    private Stat SvyuzitieNakladaca2;
   double cas1=0;
    double cas2=0;
    double ibaNalozene;
    int pomocnaPreGraf=0;
    int pomocnaPreGraf2=0;

   

    public AgentNakladaca(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        listVozidielNakladac = new SimQueue<>(new WStat(_mySim));
        deadlistVozidielNakladac = new SimQueue<>();
        _isWorking = false;
        nalozene = Konstanty.mnozstvonaSkladke;
        vozVNakladaci = new Vozidlo(null);
        vozVNakladaci2 = new Vozidlo(null);
        pracovnyCas1 = false;
        pracovnyCas2 = false;
        kontrola = false;
        SpriemCasCakaniaFrontNakladac=new Stat();
        SvyuzitieNakladaca1=new Stat();
        SvyuzitieNakladaca2=new Stat();
        cas1=0;
        cas2=0;
        pomocnaPreGraf=pomocnaPreGraf+1;
        ibaNalozene=0;
        
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerNakladaca(Id.managerNakladaca, mySim(), this);
        new SchedulerDodA(Id.schedulerDodA, mySim(), this);
        new SchedulerDodC(Id.schedulerDodC, mySim(), this);
        new SchedulerNakladania(Id.schedulerNakladania, mySim(), this);
        new SchedulerNakladanie2(Id.schedulerNakladanie2, mySim(), this);
        new SchedulerDodB(Id.schedulerDodB, mySim(), this);
        new SchedulerPracDobaNaklad(Id.schedulerPracDobaNaklad, mySim(), this);
        addOwnMessage(Mc.zaciatokAKoniecNakladania);
        addOwnMessage(Mc.init);
        addOwnMessage(Mc.pridanieMaterialu);
        addOwnMessage(Mc.spustiDobuNakladaca);
        addOwnMessage(Mc.ukonciDobuNakladaca);
         addOwnMessage(Mc.pokracujPoHodine);
    }
    //meta! tag="end"

    public int getPomocnaPreGraf2() {
        return pomocnaPreGraf2;
    }

    public void setPomocnaPreGraf2(int pomocnaPreGraf2) {
        this.pomocnaPreGraf2 = pomocnaPreGraf2;
    }

    public int getPomocnaPreGraf() {
        return pomocnaPreGraf;
    }

    public void setPomocnaPreGraf(int pomocnaPreGraf) {
        this.pomocnaPreGraf = pomocnaPreGraf;
    }

    
    public double getIbaNalozene() {
        return ibaNalozene;
    }

    public void plusIbaNalozene(double ibaNalozene) {
        this.ibaNalozene = this.ibaNalozene+ibaNalozene;
    }

    
    public Stat getSvyuzitieNakladaca1() {
        return SvyuzitieNakladaca1;
    }

    public void setSvyuzitieNakladaca1(Stat SvyuzitieNakladaca1) {
        this.SvyuzitieNakladaca1 = SvyuzitieNakladaca1;
    }

    public Stat getSvyuzitieNakladaca2() {
        return SvyuzitieNakladaca2;
    }

    public void setSvyuzitieNakladaca2(Stat SvyuzitieNakladaca2) {
        this.SvyuzitieNakladaca2 = SvyuzitieNakladaca2;
    }

    public double getCas2() {
        return cas2;
    }

    public void setCas2(double cas2) {
        this.cas2 = cas2;
    }

    public double getCas1() {
        return cas1;
    }

    public void setCas1(double cas1) {
        this.cas1 = cas1;
    }
  public void plusCas1(double cas2) {
        this.cas1 += cas2;
    }
    public void plusCas2(double cas2) {
        this.cas2 += cas2;
    }


    public Stat getSpriemCasCakaniaFrontNakladac() {
        return SpriemCasCakaniaFrontNakladac;
    }

    public void setSpriemCasCakaniaFrontNakladac(Stat SpriemCasCakaniaFrontNakladac) {
        this.SpriemCasCakaniaFrontNakladac = SpriemCasCakaniaFrontNakladac;
    }


    public SimQueue<MessageForm> getListVozidielNakladac() {
        return listVozidielNakladac;
    }

    public void setListVozidielNakladac(SimQueue<MessageForm> listVozidielNakladac) {
        this.listVozidielNakladac = listVozidielNakladac;
    }

    public boolean isIsWorking() {
        return _isWorking;
    }

    public void setIsWorking(boolean _isWorking) {
        this._isWorking = _isWorking;
    }

    public ExponentialRNG getExpoA() {
        return expoA;
    }

    public void setExpoA(ExponentialRNG expoA) {
        this.expoA = expoA;
    }

    public ExponentialRNG getExpoB() {
        return expoB;
    }

    public void setExpoB(ExponentialRNG expoB) {
        this.expoB = expoB;
    }

    public ExponentialRNG getExpoC() {
        return expoC;
    }

    public void setExpoC(ExponentialRNG expoC) {
        this.expoC = expoC;
    }

    public double getNalozene() {
        return nalozene;
    }

    public void setNalozene(double nalozene) {
        this.nalozene = nalozene;
    }

    public void plusNalozene(double nalozene) {
        this.nalozene += nalozene;
       // nalozeneVykreslenie=this.nalozene;
    }

    public Random getMaterialA() {
        return materialA;
    }

    public void setMaterialA(Random materialA) {
        this.materialA = materialA;
    }

    public Random getMaterialB() {
        return materialB;
    }

    public void setMaterialB(Random materialB) {
        this.materialB = materialB;
    }

    public Random getMaterialC() {
        return materialC;
    }

    public void setMaterialC(Random materialC) {
        this.materialC = materialC;
    }

    public String vypisFront(SimQueue<MessageForm> list) {
        String retAut = "";
        for (MessageForm message : list) {
            retAut = retAut + ((MyMessage) message).getVoz().toVypis();
        }
        return retAut;
    }

    public Vozidlo getVozVNakladaci() {
        return vozVNakladaci;
    }

    public void setVozVNakladaci(Vozidlo vozVNakladaci) {
        this.vozVNakladaci = vozVNakladaci;
    }

    public boolean isIsWorking2() {
        return _isWorking2;
    }

    public void setIsWorking2(boolean _isWorking2) {
        this._isWorking2 = _isWorking2;
    }

    public Vozidlo getVozVNakladaci2() {
        return vozVNakladaci2;
    }

    public void setVozVNakladaci2(Vozidlo vozVNakladaci2) {
        this.vozVNakladaci2 = vozVNakladaci2;
    }

    public boolean isPracovnyCas1() {
        return pracovnyCas1;
    }

    public void setPracovnyCas1(boolean pracovnyCas1) {
        this.pracovnyCas1 = pracovnyCas1;
    }

    public boolean isPracovnyCas2() {
        return pracovnyCas2;
    }

    public void setPracovnyCas2(boolean pracovnyCas2) {
        this.pracovnyCas2 = pracovnyCas2;
    }

    public SimQueue<MessageForm> getDeadlistVozidielNakladac() {
        return deadlistVozidielNakladac;
    }

    public void setDeadlistVozidielNakladac(SimQueue<MessageForm> deadlistVozidielNakladac) {
        this.deadlistVozidielNakladac = deadlistVozidielNakladac;
    }

    public boolean isKontrola() {
        return kontrola;
    }

    public void setKontrola(boolean kontrola) {
        this.kontrola = kontrola;
    }

    public boolean mozePracovat1() {
        if (!isIsWorking() && isPracovnyCas1()) {
            return true;
        }

        return false;
    }

    public boolean mozePracovat2() {
        if (!isIsWorking2() && isPracovnyCas2()) {
            return true;
        }

        return false;
    }
    /// tuto musim pocitat statistiku ako dlho bolo auto v rade

    public void prehodDoMrtveho() {
        while (getListVozidielNakladac().size() != 0) {
            this.SpriemCasCakaniaFrontNakladac.addSample(mySim().currentTime()-((MyMessage)getListVozidielNakladac().getFirst()).getZaciatokCakanie());
            getDeadlistVozidielNakladac().add(getListVozidielNakladac().dequeue());
        }

    }
//tu musim nastavit kazdemu autu novy casprichodu

    public void prehodZMrtveho() {
        while (getDeadlistVozidielNakladac().size() != 0) {
            ((MyMessage) getDeadlistVozidielNakladac().getFirst()).setZaciatokCakanie(mySim().currentTime());
            ((MyMessage) getDeadlistVozidielNakladac().getFirst()).getVoz().setCasPrichoduNaSkladku(mySim().currentTime());
            getListVozidielNakladac().add(getDeadlistVozidielNakladac().dequeue());
        }
    }
}
