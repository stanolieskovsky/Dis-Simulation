package agents;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.Stat;
import OSPStat.WStat;
import Objekty.Generator;
import Objekty.Konstanty;
import Objekty.Vozidlo;
import simulation.*;
import managers.*;
import continualAssistants.*;
import java.util.LinkedList;
import java.util.Random;

//meta! id="16"
public class AgentVykladaca extends Agent {

    private SimQueue<MessageForm> listVozidielVykladac;
    private SimQueue<MessageForm> deadlistVozidielVykladac;
    private boolean _isWorking = false;
    private boolean _isWorking2 = false;
    private boolean _isOdoberanie = false;
    private boolean pracovnyCas1 = false;
    private boolean pracovnyCas2 = false;
    private Vozidlo vozVykladaci;
    private Vozidlo vozVykladaci2;
    private double vylozeneMnozstvo;
    private double vylozeneMnozstvoNaKontrolu;
    private double ibaOdobratepreStat;
    private double ibaVylozenepreStat;
    Generator gen = new Generator();
    private Stat SpriemCasCakaniaFrontVykladace;
    private Stat SvyuzitieVykladaca1;
    private Stat SvyuzitieVykladaca2;
    double cas2 = 0;
    double cas1 = 0;
    double uspesOdber = 0;
    double celkomOdber = 0;
    int pomocnaPreGraf=0;
    int pomocnaPreGraf2=0;
    int prvezlyhanie=0;
    double casZlyhania=0;
    public AgentVykladaca(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        listVozidielVykladac = new SimQueue<>(new WStat(_mySim));
        deadlistVozidielVykladac = new SimQueue<>();
        _isWorking = false;
        _isWorking2 = false;
        _isOdoberanie = false;
        pracovnyCas1 = false;
        pracovnyCas2 = false;
        vozVykladaci = new Vozidlo(null);
        vozVykladaci2 = new Vozidlo(null);
        vylozeneMnozstvo = Konstanty.vylozeneMnozstvo;
        vylozeneMnozstvoNaKontrolu=Konstanty.vylozeneMnozstvo;
         ibaVylozenepreStat = Konstanty.vylozeneMnozstvo;
         ibaOdobratepreStat= 0;
        SpriemCasCakaniaFrontVykladace = new Stat();
        SvyuzitieVykladaca1 = new Stat();
        SvyuzitieVykladaca2 = new Stat();
        cas2 = 0;
        cas1 = 0;
        uspesOdber = 0;
        celkomOdber = 0;
         pomocnaPreGraf=pomocnaPreGraf+1;
           prvezlyhanie=0;
     casZlyhania=0;
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerVykladaca(Id.managerVykladaca, mySim(), this);
        new SchedulerVykladania(Id.schedulerVykladania, mySim(), this);
        new SchedulerVykladanie2(Id.schedulerVykladanie2, mySim(), this);
        new SchedulerPracDobaVyklOdber(Id.schedulerPracDobaVyklOdber, mySim(), this);
        new SchedulerOdberu(Id.schedulerOdberu, mySim(), this);
        addOwnMessage(Mc.zaciaTokAKoniecVykladania);
        addOwnMessage(Mc.init);
        addOwnMessage(Mc.spustiDobuVykladaca);
        addOwnMessage(Mc.ukonciDobuVykladaca);
        addOwnMessage(Mc.spustiDobuOdoberania);
        addOwnMessage(Mc.pokracujDobuOdoberania);
        addOwnMessage(Mc.ukonciDobuOdoberania);
        addOwnMessage(Mc.pokracujPoHodine);
    }

    public int getPrvezlyhanie() {
        return prvezlyhanie;
    }

    public void setPrvezlyhanie(int prvezlyhanie) {
        this.prvezlyhanie = prvezlyhanie;
    }

    public double getCasZlyhania() {
        return casZlyhania;
    }

    public void setCasZlyhania(double casZlyhania) {
        this.casZlyhania = casZlyhania;
    }

    public int getPomocnaPreGraf2() {
        return pomocnaPreGraf2;
    }

    public void setPomocnaPreGraf2(int pomocnaPreGraf2) {
        this.pomocnaPreGraf2 = pomocnaPreGraf2;
    }

    public double getVylozeneMnozstvoNaKontrolu() {
        return vylozeneMnozstvoNaKontrolu;
    }

    public void setVylozeneMnozstvoNaKontrolu(double vylozeneMnozstvoNaKontrolu) {
        this.vylozeneMnozstvoNaKontrolu = vylozeneMnozstvoNaKontrolu;
    }

    public int getPomocnaPreGraf() {
        return pomocnaPreGraf;
    }

    public void setPomocnaPreGraf(int pomocnaPreGraf) {
        this.pomocnaPreGraf = pomocnaPreGraf;
    }
    
  public void plusUspesne() {
        this.uspesOdber=uspesOdber+1;
    }
   public void plusCelkove() {
        this.celkomOdber=celkomOdber+1;
    }

    public double getUspesOdber() {
        return uspesOdber;
    }

    public void setUspesOdber(double uspesOdber) {
        this.uspesOdber = uspesOdber;
    }

    public double getCelkomOdber() {
        return celkomOdber;
    }

    //meta! tag="end"
    public void setCelkomOdber(double celkomOdber) {
        this.celkomOdber = celkomOdber;
    }

    public void plusCas1(double cas1) {
        this.cas1 += cas1;
    }

    public void plusCas2(double cas2) {
        this.cas2 += cas2;
    }

    public Stat getSvyuzitieVykladaca1() {
        return SvyuzitieVykladaca1;
    }

    public void setSvyuzitieVykladaca1(Stat SvyuzitieVykladaca1) {
        this.SvyuzitieVykladaca1 = SvyuzitieVykladaca1;
    }

    public Stat getSvyuzitieVykladaca2() {
        return SvyuzitieVykladaca2;
    }

    public void setSvyuzitieVykladaca2(Stat SvyuzitieVykladaca2) {
        this.SvyuzitieVykladaca2 = SvyuzitieVykladaca2;
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

    public Stat getSpriemCasCakaniaFrontVykladace() {
        return SpriemCasCakaniaFrontVykladace;
    }

    public void setSpriemCasCakaniaFrontVykladace(Stat SpriemCasCakaniaFrontVykladace) {
        this.SpriemCasCakaniaFrontVykladace = SpriemCasCakaniaFrontVykladace;
    }

    public SimQueue<MessageForm> getListVozidielVykladac() {
        return listVozidielVykladac;
    }

    public void setListVozidielVykladac(SimQueue<MessageForm> listVozidielVykladac) {
        this.listVozidielVykladac = listVozidielVykladac;
    }

    public boolean isIsOdoberanie() {
        return _isOdoberanie;
    }

    public void setIsOdoberanie(boolean _isOdoberanie) {
        this._isOdoberanie = _isOdoberanie;
    }

    public double getVylozeneMnozstvo() {
        return vylozeneMnozstvo;
    }

    public void setVylozeneMnozstvo(double vylozeneMnozstvo) {
        this.vylozeneMnozstvo = vylozeneMnozstvo;
    }

    public boolean isIsWorking() {
        return _isWorking;
    }

    public void setIsWorking(boolean _isWorking) {
        this._isWorking = _isWorking;
    }

    public String vypisFront(SimQueue<MessageForm> list) {
        String retAut = "";
        for (MessageForm message : list) {
            retAut = retAut + ((MyMessage) message).getVoz().toVypis();
        }
        return retAut;
    }

    public Vozidlo getVozVykladaci2() {
        return vozVykladaci2;
    }

    public void setVozVykladaci2(Vozidlo vozVykladaci2) {
        this.vozVykladaci2 = vozVykladaci2;
    }

    public Vozidlo getVozVykladaci() {
        return vozVykladaci;
    }

    public void setVozVykladaci(Vozidlo vozVykladaci) {
        this.vozVykladaci = vozVykladaci;
    }

    public boolean isIsWorking2() {
        return _isWorking2;
    }

    public void setIsWorking2(boolean _isWorking2) {
        this._isWorking2 = _isWorking2;
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

    public SimQueue<MessageForm> getDeadlistVozidielVykladac() {
        return deadlistVozidielVykladac;
    }

    public void setDeadlistVozidielVykladac(SimQueue<MessageForm> deadlistVozidielVykladac) {
        this.deadlistVozidielVykladac = deadlistVozidielVykladac;
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

    public Generator getGen() {
        return gen;
    }

    public void setGen(Generator gen) {
        this.gen = gen;
    }

    public double getIbaOdobratepreStat() {
        return ibaOdobratepreStat;
    }

    public void setIbaOdobratepreStat(double ibaOdobratepreStat) {
        this.ibaOdobratepreStat = ibaOdobratepreStat;
    }
       public void plusIbaOdobratepreStat(double PibaOdobratepreStat) {
        this.ibaOdobratepreStat =ibaOdobratepreStat+ PibaOdobratepreStat;
    }

    public double getIbaVylozenepreStat() {
        return ibaVylozenepreStat;
    }

    public void setIbaVylozenepreStat(double ibaVylozenepreStat) {
        this.ibaVylozenepreStat = ibaVylozenepreStat;
    }
       public void plusIbaVylozenepreStat(double ibaVylozenepreStat) {
        this.ibaVylozenepreStat =this.ibaVylozenepreStat+ibaVylozenepreStat;
    }
    
// tu pocitat statistiku dlzky vozidla

    public void prehodDoMrtveho() {
        while (getListVozidielVykladac().size() != 0) {
            this.SpriemCasCakaniaFrontVykladace.addSample(mySim().currentTime() - ((MyMessage) getListVozidielVykladac().getFirst()).getZaciatokCakanie());
            getDeadlistVozidielVykladac().add(getListVozidielVykladac().dequeue());
        }

    }

    public void prehodZMrtveho() {
        while (getDeadlistVozidielVykladac().size() != 0) {
            ((MyMessage) getDeadlistVozidielVykladac().getFirst()).setZaciatokCakanie(mySim().currentTime());
            ((MyMessage) getDeadlistVozidielVykladac().getFirst()).getVoz().setCasPrichoduNaStavbu(mySim().currentTime());
            getListVozidielVykladac().add(getDeadlistVozidielVykladac().dequeue());
        }
    }
}
