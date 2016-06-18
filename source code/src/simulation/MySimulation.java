package simulation;

import OSPABA.*;
import OSPStat.Stat;
import Objekty.Konstanty;
import Objekty.Vozidlo;
import agents.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class MySimulation extends Simulation {

    public static Random nasada = new Random();
    private LinkedList<Vozidlo> listVozidiel = new LinkedList<Vozidlo>();

    private Konstanty konstanty = new Konstanty();
    // private Stat statPriemerCacSim = new Stat();
    private Stat statPriemerfRONTnAKLADAC = new Stat();
    private Stat statPriemerfRONTvykladac = new Stat();
    private Stat statPriemerCaKanieNaklad = new Stat();
    private Stat statPriemerCaKanieVyklad = new Stat();
    private Stat statPriemerVyuzNakl1 = new Stat();
    private Stat statPriemerVyuzNakl2 = new Stat();
    private Stat statPriemerVyuzVyklad1 = new Stat();
    private Stat statPriemerVyuzVyklad2 = new Stat();
    private Stat statPriemerUspech = new Stat();
    private Stat statPriemerNeuspechBezA = new Stat();

    private Stat statIbaNalozeneNaSkladke = new Stat();
    private Stat statIbavylozeneVykladac = new Stat();
    private Stat statIbaOdobrateSkladkaVykladac = new Stat();
    ObjektGrafu obGrafuN = new ObjektGrafu("Priebeh meniaceho sa mnozstva materialu na skladke", "mesiace", "mnozstvo materialu");
    ObjektGrafu obGrafuV = new ObjektGrafu("Priebeh meniaceho sa mnozstva materialu na stavbe", "mesiace", "mnozstvo materialu");
    private boolean isGrafN = false;
    private boolean isGrafV = false;

    private double casSimulacie = 0;
    private boolean isDruhy;
    private boolean isOdstaveny;
    private int ir = 0;
    private int  cisloReplikacie=0;
    private double odsadenie;
    Thread vlakno;
    JTextArea finishArea = new JTextArea();
    ZapisDoSuboru zap = new ZapisDoSuboru();

    public MySimulation() {
        init();
    }

    @Override
    public void prepareSimulation() {
        super.prepareSimulation();

        // Create global statistcis
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Reset entities, queues, local statistics, etc...
        this.casSimulacie = 0;
        // listVozidiel = new LinkedList<Vozidlo>();
        agentModelu().spustiSimulaciu();
        cisloReplikacie++;
    }

    @Override
    public void replicationFinished() {
        // Collect local statistics into global, update UI, etc...
        super.replicationFinished();
        // statPriemerCacSim.addSample(casSimulacie);
        statPriemerfRONTnAKLADAC.addSample(agentNakladaca().getListVozidielNakladac().lengthStatistic().mean());
        statPriemerfRONTvykladac.addSample(agentVykladaca().getListVozidielVykladac().lengthStatistic().mean());
        statPriemerCaKanieNaklad.addSample(agentNakladaca().getSpriemCasCakaniaFrontNakladac().mean());
        statPriemerCaKanieVyklad.addSample(agentVykladaca().getSpriemCasCakaniaFrontVykladace().mean());
        statPriemerVyuzNakl1.addSample(agentNakladaca().getSvyuzitieNakladaca1().mean());
        statPriemerVyuzNakl2.addSample(agentNakladaca().getSvyuzitieNakladaca2().mean());
        statPriemerVyuzVyklad1.addSample(agentVykladaca().getSvyuzitieVykladaca1().mean());
        statPriemerVyuzVyklad2.addSample(agentVykladaca().getSvyuzitieVykladaca2().mean());

        statPriemerUspech.addSample(agentVykladaca().getUspesOdber() / agentVykladaca().getCelkomOdber());
        // ani toto nemam v gui, pre jednu iba predel hodnoty
        // System.out.println("" + this.i);

        statIbaNalozeneNaSkladke.addSample(agentNakladaca().getIbaNalozene());
        statIbavylozeneVykladac.addSample(agentVykladaca().getIbaVylozenepreStat());
        statIbaOdobrateSkladkaVykladac.addSample(agentVykladaca().getIbaOdobratepreStat());
        statPriemerNeuspechBezA.addSample(agentVykladaca().getCasZlyhania());
        ir++;
    }

    @Override
    public void simulationFinished() {
        // Dysplay simulation results

        super.simulationFinished();

        //  System.out.println("koniec simulacia statistika " + statPriemerCacSim.mean() / 3600);
//        System.out.println("koniec simulacia priemer vo fronte nakladac " + statPriemerfRONTnAKLADAC.mean());
//        System.out.println("koniec simulacia priemer vo fronte vykladac  " + statPriemerfRONTvykladac.mean());
        System.out.println("koniec simulacia priemer  ");
        if (ir == 1) {
            System.out.println(" " + vypisVsetko());
            finishArea.setText(vypisVsetko());
        } else {
            System.out.println(" " + vypisVsetko());
            System.out.println(" " + vypisIntervaly());
            finishArea.setText(vypisVsetko()+vypisIntervaly());
        }

        
        try {
               if (ir != 1) {
            zap.ulozDoSuborov(vypisStringStatistik());
            zap.ulozDoSuborovIntervaly(vypisStringIntervaly());
               }
            //    System.out.println("koniec simulacia priemer nalozeneho  " + statPriemerMaterialu.mean());
        } catch (IOException ex) {
            Logger.getLogger(MySimulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        setAgentModelu(new AgentModelu(Id.agentModelu, this, null));
        setAgentOkolia(new AgentOkolia(Id.agentOkolia, this, agentModelu()));
        setAgentStavby(new AgentStavby(Id.agentStavby, this, agentModelu()));
        setAgentNakladaca(new AgentNakladaca(Id.agentNakladaca, this, agentStavby()));
        setAgentVykladaca(new AgentVykladaca(Id.agentVykladaca, this, agentStavby()));
        setAgentVozidiel(new AgentVozidiel(Id.agentVozidiel, this, agentStavby()));
    }

    private AgentModelu _agentModelu;

    public AgentModelu agentModelu() {
        return _agentModelu;
    }

    public void setAgentModelu(AgentModelu agentModelu) {
        _agentModelu = agentModelu;
    }

    private AgentOkolia _agentOkolia;

    public AgentOkolia agentOkolia() {
        return _agentOkolia;
    }

    public void setAgentOkolia(AgentOkolia agentOkolia) {
        _agentOkolia = agentOkolia;
    }

    private AgentStavby _agentStavby;

    public AgentStavby agentStavby() {
        return _agentStavby;
    }

    public void setAgentStavby(AgentStavby agentStavby) {
        _agentStavby = agentStavby;
    }

    private AgentNakladaca _agentNakladaca;

    public AgentNakladaca agentNakladaca() {
        return _agentNakladaca;
    }

    public void setAgentNakladaca(AgentNakladaca agentNakladaca) {
        _agentNakladaca = agentNakladaca;
    }

    private AgentVykladaca _agentVykladaca;

    public AgentVykladaca agentVykladaca() {
        return _agentVykladaca;
    }

    public void setAgentVykladaca(AgentVykladaca agentVykladaca) {
        _agentVykladaca = agentVykladaca;
    }

    private AgentVozidiel _agentVozidiel;

    public AgentVozidiel agentVozidiel() {
        return _agentVozidiel;
    }

    public void setAgentVozidiel(AgentVozidiel agentVozidiel) {
        _agentVozidiel = agentVozidiel;
    }
    //meta! tag="end"

    public LinkedList<Vozidlo> getListVozidiel() {
        return listVozidiel;
    }

    public void setListVozidiel(LinkedList<Vozidlo> listVozidiel) {
        this.listVozidiel = listVozidiel;
    }

    public Konstanty getKonstanty() {
        return konstanty;
    }

    public double getCasSimulacie() {
        return casSimulacie;
    }

    public void setCasSimulacie(double casSimulacie) {
        this.casSimulacie = casSimulacie;
    }

    public static Random getNasada() {
        return nasada;
    }

    public static void setNasada(Random nasada) {
        MySimulation.nasada = nasada;
    }

    public void addCar(int i, int j) {
        if (i == 0) {
            for (int k = 0; k < j; k++) {
                listVozidiel.add(new Vozidlo("A1"));
            }

        }
        if (i == 1) {
            for (int k = 0; k < j; k++) {
                listVozidiel.add(new Vozidlo("A2"));
            }
        }
        if (i == 2) {
            for (int k = 0; k < j; k++) {
                listVozidiel.add(new Vozidlo("A3"));
            }
        }
        if (i == 3) {
            for (int k = 0; k < j; k++) {
                listVozidiel.add(new Vozidlo("A4"));
            }
        }
        if (i == 4) {
            for (int k = 0; k < j; k++) {
                listVozidiel.add(new Vozidlo("A5"));
            }
        }

    }

    public JTextArea getFinishArea() {
        return finishArea;
    }

    public void setFinishArea(JTextArea finishArea) {
        this.finishArea = finishArea;
    }

    public String vypisFront(LinkedList<Vozidlo> list) {
        String retAut = "";
        for (Vozidlo voz : list) {
            retAut = retAut + voz.toVypis(); //((Vozidlo) message).getVoz().toVypis();
        }
        retAut = retAut + "celkova suma: " + vratCenu() + " Eur";
        return retAut;
    }

    public void nulujAuta() {
        for (Vozidlo voz : listVozidiel) {
            voz.nuluj();
        }
    }

    public boolean isIsDruhy() {
        return isDruhy;
    }

    public void setIsDruhy(boolean isDruhy) {
        this.isDruhy = isDruhy;
    }

    public boolean isIsOdstaveny() {
        return isOdstaveny;
    }

    public void setIsOdstaveny(boolean isOdstaveny) {
        this.isOdstaveny = isOdstaveny;
    }

    public boolean isIsGrafN() {
        return isGrafN;
    }

    public void setIsGrafN(boolean isGrafN) {
        this.isGrafN = isGrafN;
    }

    public boolean isIsGrafV() {
        return isGrafV;
    }

    public void setIsGrafV(boolean isGrafV) {
        this.isGrafV = isGrafV;
    }

    public void pridajDoGrafuV(double mnozstvo, double cas) {
        obGrafuV.pridajBodDoGrafu(mnozstvo, cas);
    }

    public void pridajDoGrafuN(double mnozstvo, double cas) {
        obGrafuN.pridajBodDoGrafu(mnozstvo, cas);
    }

    public String vypisVsetko() {
        String s = "";
        s = s
                 + "cislo replikacie: " + (cisloReplikacie) + "\n"
                + "priemer dlzky front nakladace: " + (double) Math.round((statPriemerfRONTnAKLADAC.mean()) * 10000) / 10000 + "\n"
                + "priemer dlzky front vykladac: " + (double) Math.round((statPriemerfRONTvykladac.mean()) * 10000) / 10000 + "\n"
                + "priemer cakanie  nakladace:" + (double) Math.round((statPriemerCaKanieNaklad.mean() / 60) * 10000) / 10000 + "min \n"
                + "priemer cakanie  vykladace: " + (double) Math.round((statPriemerCaKanieVyklad.mean() / 60) * 10000) / 10000 + "min \n"
                + "vyuzitie nakladaca1: " + (double) Math.round((statPriemerVyuzNakl1.mean() * 100) * 10000) / 10000 + " %\n"
                + "vyuzitie nakladaca2:" + (double) Math.round((statPriemerVyuzNakl2.mean() * 100) * 10000) / 10000 + " %\n"
                + "vyuzitie vykladac1 :" + (double) Math.round((statPriemerVyuzVyklad1.mean() * 100) * 10000) / 10000 + " %\n"
                + "vyuzitie vykladac2: " + (double) Math.round((statPriemerVyuzVyklad2.mean() * 100) * 10000) / 10000 + "%\n"
                + "priemerny uspech: " + (double) Math.round((statPriemerUspech.mean() * 100) * 10000) / 10000 + " %\n"
                + "priemer materialu dovezeneho na skladku " + (double) Math.round((statIbaNalozeneNaSkladke.mean() / Konstanty.pocetDniSimulacie) * 10000) / 10000 + "[m3] \n"
                + "priemer materialu vylozeneho na stavbu " + (double) Math.round((statIbavylozeneVykladac.mean()) * 10000) / 10000 + " [m3]\n"
                + "priemer materialu odobrateho zo stavby " + (double) Math.round((statIbaOdobrateSkladkaVykladac.mean()) * 10000) / 10000 + " [m3]\n"
                + "priemer cas zlyhania bez A " + (double) Math.round((statPriemerNeuspechBezA.mean() / (3600 * 24)) * 10000) / 10000 + " dni\n";

        return s;
    }

    public String vypisStringStatistik() {
        String s = "";
        s = s
                + "" + (double) Math.round((statPriemerfRONTnAKLADAC.mean()) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerfRONTvykladac.mean()) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerCaKanieNaklad.mean() / 60) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerCaKanieVyklad.mean() / 60) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerVyuzNakl1.mean() * 100) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerVyuzNakl2.mean() * 100) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerVyuzVyklad1.mean() * 100) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerVyuzVyklad2.mean() * 100) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerUspech.mean() * 100) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statIbaNalozeneNaSkladke.mean() / Konstanty.pocetDniSimulacie) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statIbavylozeneVykladac.mean()) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statIbaOdobrateSkladkaVykladac.mean()) * 10000) / 10000 + ";";

        return s;
    }

    public String vypisStringIntervaly() {
        String s = "";
        s = s + (double) Math.round((statPriemerfRONTnAKLADAC.confidenceInterval_90()[0]) * 10000) / 10000 + "," + (double) Math.round((statPriemerfRONTnAKLADAC.confidenceInterval_90()[1]) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerfRONTvykladac.confidenceInterval_90()[0]) * 10000) / 10000 + "," + (double) Math.round((statPriemerfRONTvykladac.confidenceInterval_90()[1]) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerCaKanieNaklad.confidenceInterval_90()[0] / 60) * 10000) / 10000 + "," + (double) Math.round((statPriemerCaKanieNaklad.confidenceInterval_90()[1] / 60) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerCaKanieVyklad.confidenceInterval_90()[0] / 60) * 10000) / 10000 + "," + (double) Math.round((statPriemerCaKanieVyklad.confidenceInterval_90()[1] / 60) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerVyuzNakl1.confidenceInterval_90()[0] * 100) * 10000) / 10000 + "," + (double) Math.round((statPriemerVyuzNakl1.confidenceInterval_90()[1] * 100) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerVyuzNakl2.confidenceInterval_90()[0] * 100) * 10000) / 10000 + "," + (double) Math.round((statPriemerVyuzNakl2.confidenceInterval_90()[1] * 100) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerVyuzVyklad1.confidenceInterval_90()[0] * 100) * 10000) / 10000 + "," + (double) Math.round((statPriemerVyuzVyklad1.confidenceInterval_90()[1] * 100) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerVyuzVyklad2.confidenceInterval_90()[0] * 100) * 10000) / 10000 + "," + (double) Math.round((statPriemerVyuzVyklad2.confidenceInterval_90()[1] * 100) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statPriemerUspech.confidenceInterval_90()[0] * 100) * 10000) / 10000 + "," + (double) Math.round((statPriemerUspech.confidenceInterval_90()[1] * 100) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statIbaNalozeneNaSkladke.confidenceInterval_90()[0]) * 10000) / 10000 + "," + (double) Math.round((statIbaNalozeneNaSkladke.confidenceInterval_90()[1]) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statIbavylozeneVykladac.confidenceInterval_90()[0]) * 10000) / 10000 + "," + (double) Math.round((statIbavylozeneVykladac.confidenceInterval_90()[1]) * 10000) / 10000 + ";"
                + "" + (double) Math.round((statIbaOdobrateSkladkaVykladac.confidenceInterval_90()[0]) * 10000) / 10000 + "," + (double) Math.round((statIbaOdobrateSkladkaVykladac.confidenceInterval_90()[1]) * 10000) / 10000 + ";";
        return s;
    }

    public String vypisIntervaly() {
        String s = "";
        s = s + "\nInterval 90 dlzky front nakladace: <" + (double) Math.round((statPriemerfRONTnAKLADAC.confidenceInterval_90()[0]) * 10000) / 10000 + "," + (double) Math.round((statPriemerfRONTnAKLADAC.confidenceInterval_90()[1]) * 10000) / 10000 + ">\n"
                + "Interval 90 dlzky front vykladac: <" + (double) Math.round((statPriemerfRONTvykladac.confidenceInterval_90()[0]) * 10000) / 10000 + "," + (double) Math.round((statPriemerfRONTvykladac.confidenceInterval_90()[1]) * 10000) / 10000 + ">\n"
                + "Interval 90 cakanie  nakladace: <" + (double) Math.round((statPriemerCaKanieNaklad.confidenceInterval_90()[0] / 60) * 10000) / 10000 + "," + (double) Math.round((statPriemerCaKanieNaklad.confidenceInterval_90()[1] / 60) * 10000) / 10000 + "> min\n"
                + "Interval 90:  cakanie  nakladace: <" + (double) Math.round((statPriemerCaKanieVyklad.confidenceInterval_90()[0] / 60) * 10000) / 10000 + "," + (double) Math.round((statPriemerCaKanieVyklad.confidenceInterval_90()[1] / 60) * 10000) / 10000 + "> min\n"
                + "Interval 90 vyuzitie nakladaca1: <" + (double) Math.round((statPriemerVyuzNakl1.confidenceInterval_90()[0] * 100) * 10000) / 10000 + "," + (double) Math.round((statPriemerVyuzNakl1.confidenceInterval_90()[1] * 100) * 10000) / 10000 + "> %\n"
                + "Interval 90 vyuzitie nakladaca2: <" + (double) Math.round((statPriemerVyuzNakl2.confidenceInterval_90()[0] * 100) * 10000) / 10000 + "," + (double) Math.round((statPriemerVyuzNakl2.confidenceInterval_90()[1] * 100) * 10000) / 10000 + "> %\n"
                + "Interval 90 vyuzitie vykladac1: <" + (double) Math.round((statPriemerVyuzVyklad1.confidenceInterval_90()[0] * 100) * 10000) / 10000 + "," + (double) Math.round((statPriemerVyuzVyklad1.confidenceInterval_90()[1] * 100) * 10000) / 10000 + "> %\n"
                + "Interval 90 vyuzitie vykladac2: <" + (double) Math.round((statPriemerVyuzVyklad2.confidenceInterval_90()[0] * 100) * 10000) / 10000 + "," + (double) Math.round((statPriemerVyuzVyklad2.confidenceInterval_90()[1] * 100) * 10000) / 10000 + "> %\n"
                + "Interval 90 uspech: <" + (double) Math.round((statPriemerUspech.confidenceInterval_90()[0] * 100) * 10000) / 10000 + "," + (double) Math.round((statPriemerUspech.confidenceInterval_90()[1] * 100) * 10000) / 10000 + "> %\n"
                + "Interval 90 materialu dovezeneho na skladku: <" + (double) Math.round((statIbaNalozeneNaSkladke.confidenceInterval_90()[0]) * 10000) / 10000 + "," + (double) Math.round((statIbaNalozeneNaSkladke.confidenceInterval_90()[1]) * 10000) / 10000 + "> [m3]\n"
                + "Interval 90 materialu vylozeneho na stavbu: <" + (double) Math.round((statIbavylozeneVykladac.confidenceInterval_90()[0]) * 10000) / 10000 + "," + (double) Math.round((statIbavylozeneVykladac.confidenceInterval_90()[1]) * 10000) / 10000 + "> [m3]\n"
                + "Interval 90 materialu odobrateho zo stavby: <" + (double) Math.round((statIbaOdobrateSkladkaVykladac.confidenceInterval_90()[0]) * 10000) / 10000 + "," + (double) Math.round((statIbaOdobrateSkladkaVykladac.confidenceInterval_90()[1]) * 10000) / 10000 + "> [m3] \n"
                + "Interval 90 priemeru zlyhania bez A: <" + (double) Math.round((statPriemerNeuspechBezA.confidenceInterval_90()[0] / (3600 * 24)) * 10000) / 10000 + "," + (double) Math.round((statPriemerNeuspechBezA.confidenceInterval_90()[1] / (3600 * 24)) * 10000) / 10000 + "dni >\n";

        return s;
    }

    public String vypisSkladku() {
        String s = "";
        s = s
                + " dlzka front nakladace :" + (double) Math.round((agentNakladaca().getListVozidielNakladac().lengthStatistic().mean()) * 10000) / 10000 + "\n"
                + " cakanie front nakladac:" + (double) Math.round((agentNakladaca().getSpriemCasCakaniaFrontNakladac().mean() / 60) * 10000) / 10000 + " min\n"
                + " vyuzitie nakladaca 1:" + (double) Math.round((agentNakladaca().getSvyuzitieNakladaca1().mean()) * 10000) / 10000 + " %\n"
                + "vyuzitie nakladaca 2:" + (double) Math.round((agentNakladaca().getSvyuzitieNakladaca2().mean()) * 10000) / 10000 + "%\n";
        return s;
    }

    public String vypisStavbu() {
        String s = "";
        s = s
                + "dlzka front Vykladac :" + (double) Math.round((agentVykladaca().getListVozidielVykladac().lengthStatistic().mean()) * 10000) / 10000 + "\n"
                + "cakanie fronte vykladac:" + (double) Math.round((agentVykladaca().getSpriemCasCakaniaFrontVykladace().mean() / 60) * 10000) / 10000 + " min\n"
                + " vyuzitie vykladac 1:" + (double) Math.round((agentVykladaca().getSvyuzitieVykladaca1().mean()) * 10000) / 10000 + " %\n"
                + "vyuzitie vykladac 2:" + (double) Math.round((agentVykladaca().getSvyuzitieVykladaca2().mean()) * 10000) / 10000 + " %\n";
        return s;
    }

    public double vratCenu() {
        double cena = 0;
        for (Vozidlo voz : listVozidiel) {
            cena += voz.getCena();
        }
        if (isDruhy) {
            cena = cena + 130000;
        }
        return cena;
    }

    public Graf vratGraphN() {
        return obGrafuN.getGraf();
    }

    public Graf vratGraphV() {
        return obGrafuV.getGraf();
    }

    public double getOdsadenie() {
        return odsadenie;
    }

    public void setOdsadenie(double odsadenie) {
        this.odsadenie = odsadenie;
    }

    public void simulate(int pocet) {
        vlakno = new Thread(() -> {
            super.simulate(pocet);
        });
        vlakno.start();
    }

}
