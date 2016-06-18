package managers;

import OSPABA.*;
import Objekty.Konstanty;
import Objekty.Vozidlo;
import simulation.*;
import agents.*;
import continualAssistants.*;

//meta! id="16"
public class ManagerVykladaca extends Manager {

    public ManagerVykladaca(int id, Simulation mySim, Agent myAgent) {
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

    public void processFinishSchedulerVykladania(MessageForm message) {
        myAgent().getVozVykladaci().setObjemnaVykreslenie(0);
        myAgent().setVylozeneMnozstvo(myAgent().getVylozeneMnozstvo() + ((MyMessage) message).getVoz().getNaklad());
        myAgent().setVozVykladaci(null);
        myAgent().setIsWorking(false);
        if (myAgent().getListVozidielVykladac().size() > 0) {
            MyMessage nextMessage1 = (MyMessage) myAgent().getListVozidielVykladac().getFirst();
            if (((MyMessage) nextMessage1).getVoz().getNaklad() <= (Konstanty.MaxVylozeneMnozstvo - myAgent().getVylozeneMnozstvo())) {

                MyMessage nextMessage = (MyMessage) myAgent().getListVozidielVykladac().dequeue();
                myAgent().setVozVykladaci(((MyMessage) nextMessage).getVoz());
                startWork(nextMessage);
            }
        }
        message.setCode(Mc.zaciaTokAKoniecVykladania);
        response(message);
    }

    public void processFinishSchedulerVykladania2(MessageForm message) {
      myAgent().getVozVykladaci2().setObjemnaVykreslenie(0);
        myAgent().setVylozeneMnozstvo(myAgent().getVylozeneMnozstvo() + ((MyMessage) message).getVoz().getNaklad());
        myAgent().setVozVykladaci2(null);
        myAgent().setIsWorking2(false);
        if (myAgent().getListVozidielVykladac().size() > 0) {
            MyMessage nextMessage1 = (MyMessage) myAgent().getListVozidielVykladac().getFirst();
            if (((MyMessage) nextMessage1).getVoz().getNaklad() <= (Konstanty.MaxVylozeneMnozstvo - myAgent().getVylozeneMnozstvo())) {

                MyMessage nextMessage = (MyMessage) myAgent().getListVozidielVykladac().dequeue();
                myAgent().setVozVykladaci2(((MyMessage) nextMessage).getVoz());
                startWork2(nextMessage);
            }
        }
        message.setCode(Mc.zaciaTokAKoniecVykladania);
        response(message);
    }

    //meta! sender="SchedulerPracDobaVyklOdber", id="103", type="Finish"
    public void processFinishSchedulerPracDobaVyklOdber(MessageForm message) {
        {
            if (((MyMessage) message).getVykladacId() == 1) {
                if (myAgent().getListVozidielVykladac().size() > 0 && myAgent().isPracovnyCas1()) {
                    MyMessage nextMessage1 = (MyMessage) myAgent().getListVozidielVykladac().getFirst();
                    if (((MyMessage) nextMessage1).getVoz().getNaklad() <= (Konstanty.MaxVylozeneMnozstvo - myAgent().getVylozeneMnozstvo())) {
                        MyMessage nextMessage = (MyMessage) myAgent().getListVozidielVykladac().dequeue();
                        myAgent().setVozVykladaci(((MyMessage) nextMessage).getVoz());
                        startWork(nextMessage);
                    }
                }
            } else {
                if (myAgent().getListVozidielVykladac().size() > 0 && myAgent().isPracovnyCas2()) {
                    MyMessage nextMessage1 = (MyMessage) myAgent().getListVozidielVykladac().getFirst();
                    if (((MyMessage) nextMessage1).getVoz().getNaklad() <= (Konstanty.MaxVylozeneMnozstvo - myAgent().getVylozeneMnozstvo())) {
                        MyMessage nextMessage = (MyMessage) myAgent().getListVozidielVykladac().dequeue();
                        myAgent().setVozVykladaci2(((MyMessage) nextMessage).getVoz());
                        startWork2(nextMessage);
                    }
                }
            }
        }
    }
    //meta! sender="AgentStavby", id="31", type="Request"

    public void processZaciaTokAKoniecVykladania(MessageForm message) {
        ((MyMessage)message).setZaciatokCakanie(mySim().currentTime());
        ((MyMessage)message).getVoz().setPozicia("front vykladky");
        if (myAgent().isPracovnyCas1() == false && myAgent().isPracovnyCas2() == false) {
            myAgent().getDeadlistVozidielVykladac().enqueue(message);
        } else if (!myAgent().mozePracovat1() && !myAgent().mozePracovat2()) {
            ((MyMessage) message).getVoz().setCasPrichoduNaStavbu(mySim().currentTime());
            myAgent().getListVozidielVykladac().enqueue(message);
        } else if (myAgent().mozePracovat1() && !myAgent().mozePracovat2()) {
            ((MyMessage) message).getVoz().setCasPrichoduNaStavbu(mySim().currentTime());
            myAgent().getListVozidielVykladac().enqueue(message);
            if (((MyMessage) myAgent().getListVozidielVykladac().getFirst()).getVoz().getNaklad() <= (Konstanty.MaxVylozeneMnozstvo - myAgent().getVylozeneMnozstvoNaKontrolu())) {
                myAgent().setVozVykladaci(((MyMessage) message).getVoz());
                startWork((MyMessage) myAgent().getListVozidielVykladac().dequeue());
            }
        } else if (!myAgent().mozePracovat1() && myAgent().mozePracovat2()) {
            ((MyMessage) message).getVoz().setCasPrichoduNaStavbu(mySim().currentTime());
            myAgent().getListVozidielVykladac().enqueue(message);
            if (((MyMessage) myAgent().getListVozidielVykladac().getFirst()).getVoz().getNaklad() <= (Konstanty.MaxVylozeneMnozstvo - myAgent().getVylozeneMnozstvoNaKontrolu())) {
                myAgent().setVozVykladaci2(((MyMessage) message).getVoz());
                startWork2((MyMessage) myAgent().getListVozidielVykladac().dequeue());
            }
        } else {
            ((MyMessage) message).getVoz().setCasPrichoduNaStavbu(mySim().currentTime());
            myAgent().getListVozidielVykladac().enqueue(message);
            if (((MyMessage) myAgent().getListVozidielVykladac().getFirst()).getVoz().getNaklad() <= (Konstanty.MaxVylozeneMnozstvo - myAgent().getVylozeneMnozstvoNaKontrolu())) {
                myAgent().setVozVykladaci(((MyMessage) message).getVoz());
                startWork((MyMessage) myAgent().getListVozidielVykladac().dequeue());
            }
        }
    }

    private void processFinishSchedulerOdberu(MessageForm message) {
        if (myAgent().mozePracovat1() && myAgent().getListVozidielVykladac().size() > 0) {
            MyMessage nextMessage1 = (MyMessage) myAgent().getListVozidielVykladac().getFirst();
            if (((MyMessage) nextMessage1).getVoz().getNaklad() <= (Konstanty.MaxVylozeneMnozstvo - myAgent().getVylozeneMnozstvo())) {
                MyMessage nextMessage = (MyMessage) myAgent().getListVozidielVykladac().dequeue();
                myAgent().setVozVykladaci(((MyMessage) nextMessage).getVoz());
                startWork(nextMessage);
            }
        }
        if (myAgent().mozePracovat2() && myAgent().getListVozidielVykladac().size() > 0) {
            MyMessage nextMessage2 = (MyMessage) myAgent().getListVozidielVykladac().getFirst();
            if (((MyMessage) nextMessage2).getVoz().getNaklad() <= (Konstanty.MaxVylozeneMnozstvo - myAgent().getVylozeneMnozstvo())) {
                MyMessage nextMessage3 = (MyMessage) myAgent().getListVozidielVykladac().dequeue();
                myAgent().setVozVykladaci2(((MyMessage) nextMessage3).getVoz());
                startWork2(nextMessage3);
            }
        }

    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    public void processInit(MessageForm message) {
        MessageForm novaA = message.createCopy();
        MessageForm novaB = message.createCopy();
        MessageForm novaC = message.createCopy();

        novaA.setAddressee(myAgent().findAssistant(Id.schedulerPracDobaVyklOdber));
        ((MyMessage) novaA).setVykladacId(1);
        //myAgent().setVozVykladaci(new Vozidlo(null));
        startContinualAssistant(novaA);

        if (((MyMessage) message).isIs2vykladac() == true) {
            novaB.setAddressee(myAgent().findAssistant(Id.schedulerPracDobaVyklOdber));
            ((MyMessage) novaB).setVykladacId(2);
         //   myAgent().setVozVykladaci2(new Vozidlo(null));
            startContinualAssistant(novaB);
        }
        novaC.setAddressee(myAgent().findAssistant(Id.schedulerOdberu));
        startContinualAssistant(novaC);
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.finish:
                switch (message.sender().id()) {
                    case Id.schedulerVykladania:
                        processFinishSchedulerVykladania(message);
                        break;

                    case Id.schedulerPracDobaVyklOdber:
                        processFinishSchedulerPracDobaVyklOdber(message);
                        break;

                    case Id.schedulerVykladanie2:
                        processFinishSchedulerVykladania2(message);
                        break;
                    case Id.schedulerOdberu:
                        processFinishSchedulerOdberu(message);
                        break;
                }
                break;

            case Mc.init:
                processInit(message);
                break;

            case Mc.zaciaTokAKoniecVykladania:
                processZaciaTokAKoniecVykladania(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    private void startWork(MessageForm message) {
        myAgent().getSpriemCasCakaniaFrontVykladace().addSample(mySim().currentTime() -((MyMessage)message).getZaciatokCakanie());
        myAgent().setIsWorking(true);
        message.setAddressee(myAgent().findAssistant(Id.schedulerVykladania));
       myAgent().setVylozeneMnozstvoNaKontrolu(myAgent().getVylozeneMnozstvoNaKontrolu()+((MyMessage)message).getVoz().getNaklad());
       ((MyMessage)message).getVoz().setPozicia("vo vykladaci"); 
       startContinualAssistant(message);
    }

    private void startWork2(MessageForm message) {
        myAgent().getSpriemCasCakaniaFrontVykladace().addSample(mySim().currentTime() -((MyMessage)message).getZaciatokCakanie());
        myAgent().setIsWorking2(true);
        myAgent().setVylozeneMnozstvoNaKontrolu(myAgent().getVylozeneMnozstvoNaKontrolu()+((MyMessage)message).getVoz().getNaklad());
        message.setAddressee(myAgent().findAssistant(Id.schedulerVykladanie2));
        ((MyMessage)message).getVoz().setPozicia("vo vykladaci2");
        startContinualAssistant(message);
    }


    @Override
    public AgentVykladaca myAgent() {
        return (AgentVykladaca) super.myAgent();
    }

}
