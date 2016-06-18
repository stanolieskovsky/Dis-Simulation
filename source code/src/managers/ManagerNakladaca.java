package managers;

import OSPABA.*;
import Objekty.Vozidlo;
import simulation.*;
import agents.*;
import continualAssistants.*;

//meta! id="15"
public class ManagerNakladaca extends Manager {

    public ManagerNakladaca(int id, Simulation mySim, Agent myAgent) {
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

    public void processFinishSchedulerPracDobaNaklad(MessageForm message) {
        if (((MyMessage) message).getNakladacId() == 1) {
            if (myAgent().getListVozidielNakladac().size() > 0 && myAgent().isPracovnyCas1()) {
                MyMessage nextMessage1 = (MyMessage) myAgent().getListVozidielNakladac().getFirst();
                if (((MyMessage) nextMessage1).getVoz().getNaklad() <= myAgent().getNalozene()) {
                    MyMessage nextMessage = (MyMessage) myAgent().getListVozidielNakladac().dequeue();
                    myAgent().setVozVNakladaci(((MyMessage) nextMessage).getVoz());
                    startWork(nextMessage);
                }
            }
        } else {
            if (myAgent().getListVozidielNakladac().size() > 0 && myAgent().isPracovnyCas2()) {
                MyMessage nextMessage1 = (MyMessage) myAgent().getListVozidielNakladac().getFirst();
                if (((MyMessage) nextMessage1).getVoz().getNaklad() <= myAgent().getNalozene()) {
                    MyMessage nextMessage = (MyMessage) myAgent().getListVozidielNakladac().dequeue();
                    myAgent().setVozVNakladaci2(((MyMessage) nextMessage).getVoz());
                    startWork2(nextMessage);
                }
            }
        }
    }

    public void processFinishSchedulerNakladania(MessageForm message) {
        myAgent().getVozVNakladaci().setObjemnaVykreslenie(myAgent().getVozVNakladaci().getObjem()*3600);
        myAgent().setNalozene(myAgent().getNalozene() - ((MyMessage) message).getVoz().getNaklad());
        myAgent().setVozVNakladaci(null);
        myAgent().setIsWorking(false);
        if (myAgent().getListVozidielNakladac().size() > 0 && myAgent().isPracovnyCas1()) {
            MyMessage nextMessage1 = (MyMessage) myAgent().getListVozidielNakladac().getFirst();
            if (((MyMessage) nextMessage1).getVoz().getNaklad() <= myAgent().getNalozene()) {
                MyMessage nextMessage = (MyMessage) myAgent().getListVozidielNakladac().dequeue();
                myAgent().setVozVNakladaci(((MyMessage) nextMessage).getVoz());
                startWork(nextMessage);
            }
        }

        message.setCode(Mc.zaciatokAKoniecNakladania);
        response(message);
    }

    public void processFinishSchedulerNakladanie2(MessageForm message) {
       myAgent().getVozVNakladaci2().setObjemnaVykreslenie(myAgent().getVozVNakladaci2().getObjem()*3600);
        myAgent().setNalozene(myAgent().getNalozene() - ((MyMessage) message).getVoz().getNaklad());
        myAgent().setVozVNakladaci2(null);
        myAgent().setIsWorking2(false);
        if (myAgent().getListVozidielNakladac().size() > 0 && myAgent().isPracovnyCas2()) {
            MyMessage nextMessage1 = (MyMessage) myAgent().getListVozidielNakladac().getFirst();
            if (((MyMessage) nextMessage1).getVoz().getNaklad() <= myAgent().getNalozene()) {
                MyMessage nextMessage = (MyMessage) myAgent().getListVozidielNakladac().dequeue();
                myAgent().setVozVNakladaci2(((MyMessage) nextMessage).getVoz());
                startWork2(nextMessage);
            }

        }

        message.setCode(Mc.zaciatokAKoniecNakladania);
        response(message);
    }
//meta! sender="SchedulerDodA", id="86", type="Finish"

    public void processFinishSchedulerDodA(MessageForm message) {
        myAgent().plusNalozene(((MyMessage) message).getMnozstvoMater());
        myAgent().plusIbaNalozene(((MyMessage) message).getMnozstvoMater());
        MyMessage nextMessage = (MyMessage) message.createCopy();
        nextMessage.setAddressee(myAgent().findAssistant(Id.schedulerDodA));
        startContinualAssistant(nextMessage);
    }

    //meta! sender="SchedulerDodB", id="88", type="Finish"
    public void processFinishSchedulerDodB(MessageForm message) {
        myAgent().plusNalozene(((MyMessage) message).getMnozstvoMater());
        myAgent().plusIbaNalozene(((MyMessage) message).getMnozstvoMater());
        MyMessage nextMessage = (MyMessage) message.createCopy();
        nextMessage.setAddressee(myAgent().findAssistant(Id.schedulerDodB));
        startContinualAssistant(nextMessage);
    }

    //meta! sender="SchedulerDodC", id="90", type="Finish"
    public void processFinishSchedulerDodC(MessageForm message) {
        myAgent().plusNalozene(((MyMessage) message).getMnozstvoMater());
        myAgent().plusIbaNalozene(((MyMessage) message).getMnozstvoMater());
        MyMessage nextMessage = (MyMessage) message.createCopy();
        nextMessage.setAddressee(myAgent().findAssistant(Id.schedulerDodC));
        startContinualAssistant(nextMessage);
    }

    //meta! sender="AgentStavby", id="25", type="Request"
    public void processZaciatokAKoniecNakladania(MessageForm message) {
       ((MyMessage)message).setZaciatokCakanie(mySim().currentTime());
       ((MyMessage)message).getVoz().setPozicia("front nakladania");
        if (myAgent().isPracovnyCas1() == false && myAgent().isPracovnyCas2() == false) {
            myAgent().getDeadlistVozidielNakladac().enqueue(message);

        } else if (!myAgent().mozePracovat1() && !myAgent().mozePracovat2()) {
            ((MyMessage) message).getVoz().setCasPrichoduNaSkladku(mySim().currentTime());
            myAgent().getListVozidielNakladac().enqueue(message);

        } else if (myAgent().mozePracovat1() && !myAgent().mozePracovat2()) {
            ((MyMessage) message).getVoz().setCasPrichoduNaSkladku(mySim().currentTime());
            myAgent().getListVozidielNakladac().enqueue(message);
            if (((MyMessage) myAgent().getListVozidielNakladac().getFirst()).getVoz().getObjem() <= myAgent().getNalozene()) {
                myAgent().setVozVNakladaci(((MyMessage) message).getVoz());
                startWork((MyMessage) myAgent().getListVozidielNakladac().dequeue());
            }
        } else if (!myAgent().mozePracovat1() && myAgent().mozePracovat2()) {
            ((MyMessage) message).getVoz().setCasPrichoduNaSkladku(mySim().currentTime());
            myAgent().getListVozidielNakladac().enqueue(message);
            if (((MyMessage) myAgent().getListVozidielNakladac().getFirst()).getVoz().getObjem() <= myAgent().getNalozene()) {
                myAgent().setVozVNakladaci2(((MyMessage) message).getVoz());
                startWork2((MyMessage) myAgent().getListVozidielNakladac().dequeue());
            }
        } else {
            ((MyMessage) message).getVoz().setCasPrichoduNaSkladku(mySim().currentTime());
            myAgent().getListVozidielNakladac().enqueue(message);
            if (((MyMessage) myAgent().getListVozidielNakladac().getFirst()).getVoz().getObjem() <= myAgent().getNalozene()) {
                myAgent().setVozVNakladaci2(((MyMessage) message).getVoz());
                startWork2((MyMessage) myAgent().getListVozidielNakladac().dequeue());
            }
        }

    }

//meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    public void processInit(MessageForm message) {
//           System.out.println("Som sa dostal do nakladaca initom");
        myAgent().setKontrola(((MyMessage) message).isIsOdstavenyA());
        MessageForm novaA = message.createCopy();
        MessageForm novaB = message.createCopy();
        MessageForm novaC = message.createCopy();
        MessageForm novaCas = message.createCopy();
        MessageForm novaCas2 = message.createCopy();
        novaA.setAddressee(myAgent().findAssistant(Id.schedulerDodA));
        startContinualAssistant(novaA);
        novaB.setAddressee(myAgent().findAssistant(Id.schedulerDodB));
        startContinualAssistant(novaB);
        novaC.setAddressee(myAgent().findAssistant(Id.schedulerDodC));
        startContinualAssistant(novaC);
        novaCas.setAddressee(myAgent().findAssistant(Id.schedulerPracDobaNaklad));
        ((MyMessage) novaCas).setNakladacId(1);
        startContinualAssistant(novaCas);
        novaCas2.setAddressee(myAgent().findAssistant(Id.schedulerPracDobaNaklad));
        ((MyMessage) novaCas2).setNakladacId(2);
        startContinualAssistant(novaCas2);

    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {

            case Mc.finish:
                switch (message.sender().id()) {
                    case Id.schedulerDodA:
                        processFinishSchedulerDodA(message);
                        break;

                    case Id.schedulerNakladania:
                        processFinishSchedulerNakladania(message);
                        break;
                    case Id.schedulerNakladanie2:
                        processFinishSchedulerNakladanie2(message);
                        break;
                    case Id.schedulerDodB:
                        processFinishSchedulerDodB(message);
                        break;

                    case Id.schedulerDodC:
                        processFinishSchedulerDodC(message);
                        break;
                    case Id.schedulerPracDobaNaklad:
                        processFinishSchedulerPracDobaNaklad(message);
                        break;
                }
                break;
            case Mc.init:
                processInit(message);
                break;

            case Mc.zaciatokAKoniecNakladania:
                processZaciatokAKoniecNakladania(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    private void startWork(MessageForm message) {
       myAgent().getSpriemCasCakaniaFrontNakladac().addSample(mySim().currentTime() -((MyMessage)message).getZaciatokCakanie());
        myAgent().setIsWorking(true);
        message.setAddressee(myAgent().findAssistant(Id.schedulerNakladania));
       ((MyMessage)message).getVoz().setPozicia("v nakladaci");
        startContinualAssistant(message);
    }

    private void startWork2(MessageForm message) {
        myAgent().getSpriemCasCakaniaFrontNakladac().addSample(mySim().currentTime() -((MyMessage)message).getZaciatokCakanie());
        myAgent().setIsWorking2(true);
        message.setAddressee(myAgent().findAssistant(Id.schedulerNakladanie2));
          ((MyMessage)message).getVoz().setPozicia("v nakladaci2");
        startContinualAssistant(message);
    }

    @Override
    public AgentNakladaca myAgent() {
        return (AgentNakladaca) super.myAgent();
    }

}
