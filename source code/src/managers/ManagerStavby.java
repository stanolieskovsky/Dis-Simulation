package managers;

import OSPABA.*;
import Objekty.Vozidlo;
import simulation.*;
import agents.*;
import continualAssistants.*;
import simulation.MySimulation;
//meta! id="4"

public class ManagerStavby extends Manager {

    public ManagerStavby(int id, Simulation mySim, Agent myAgent) {
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

    //meta! sender="AgentModelu", id="55", type="Notice"
    public void processZaradAuto(MessageForm message) {
        message.setCode(Mc.zaciatokAKoniecNakladania);
        myAgent().plusObjemVoFronte(((MyMessage) message).getVoz().getObjem());
        message.setAddressee(mySim().findAgent(Id.agentNakladaca));
        request(message);
    }


    //meta! sender="AgentVykladaca", id="31", type="Response"
    public void processZaciaTokAKoniecVykladania(MessageForm message) {
        myAgent().plusVylozenyObjem(((MyMessage) message).getVoz().getNaklad());
         ((MyMessage)message).getVoz().setPozicia("cesta BC");
        ((MyMessage) message).getVoz().setNaklad(0);
        ((MyMessage) message).getVoz().setObjemdoGui(0);
//        if (myAgent().getVylozenyObjem() == ((MySimulation) mySim()).getKonstanty().mnozstvo) {
//            ((MySimulation) mySim()).setCasSimulacie(mySim().currentTime());
//            mySim().stopReplication();
//        } else {
        // System.out.println("smerujem auto na BC : "+((MyMessage)message).getVoz().getTyp());
        message.setAddressee(mySim().findAgent(Id.agentVozidiel));
        message.setCode(Mc.pohybBC);

        request(message);
//        }
    }

    //meta! sender="AgentNakladaca", id="25", type="Response"
    public void processZaciatokAKoniecNakladania(MessageForm message) {
        //  System.out.println("smerujem auto na AB : "+((MyMessage)message).getVoz().getTyp());
         ((MyMessage)message).getVoz().setPozicia("cesta AB");
        ((MyMessage) message).getVoz().setObjemdoGui(((MyMessage) message).getVoz().getNaklad());
        message.setCode(Mc.pohybAB);
        message.setAddressee(mySim().findAgent(Id.agentVozidiel));
        request(message);
    }

    //meta! sender="AgentVozidiel", id="33", type="Response"
    public void processPohybBC(MessageForm message) {
        //  System.out.println("smerujem auto na CA : "+((MyMessage)message).getVoz().getTyp());
        ((MyMessage)message).getVoz().setPozicia("cesta CA");
        message.setCode(Mc.pohybCA);
        message.setAddressee(mySim().findAgent(Id.agentVozidiel));
        request(message);
    }

    //meta! sender="AgentVozidiel", id="32", type="Response"
    public void processPohybAB(MessageForm message) {
     //   ((MyMessage)message).getVoz().setPozicia("cesta AB");
        message.setCode(Mc.zaciaTokAKoniecVykladania);
        message.setAddressee(mySim().findAgent(Id.agentVykladaca));
        request(message);
    }

    //meta! sender="AgentVozidiel", id="34", type="Response"
    public void processPohybCA(MessageForm message) {

        Vozidlo pavoz = ((MyMessage) message).getVoz();

//        if (myAgent().getObjemVoFronte() < ((MySimulation) (mySim())).getKonstanty().mnozstvo) {
//            if (myAgent().getObjemVoFronte() + pavoz.getObjem() < ((MySimulation) (mySim())).getKonstanty().mnozstvo) {
                pavoz.setNaklad(pavoz.getObjem());
              
//            } else {
//
//                pavoz.setNaklad((((MySimulation) (mySim())).getKonstanty().mnozstvo) - myAgent().getObjemVoFronte());
//            }
            myAgent().plusObjemVoFronte(pavoz.getNaklad());
            message.setCode(Mc.zaciatokAKoniecNakladania);
            message.setAddressee(mySim().findAgent(Id.agentNakladaca));
            request(message);
        

    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    public void processInit(MessageForm message) {
        message.setAddressee(Id.agentNakladaca);
        notice(message);
   
        MessageForm copy = message.createCopy();
        copy.setAddressee(Id.agentVykladaca);
        notice(copy);
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"

    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.zaradAuto:
                processZaradAuto(message);
                break;
            case Mc.init:
                processInit(message);
                break;

            case Mc.pohybBC:
                processPohybBC(message);
                break;

            case Mc.pohybAB:
                processPohybAB(message);
                break;

            case Mc.zaciaTokAKoniecVykladania:
                processZaciaTokAKoniecVykladania(message);
                break;

            case Mc.zaciatokAKoniecNakladania:
                processZaciatokAKoniecNakladania(message);
                break;

            case Mc.pohybCA:
                processPohybCA(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    
    
    
    @Override
    public AgentStavby myAgent() {
        return (AgentStavby) super.myAgent();
    }

}
