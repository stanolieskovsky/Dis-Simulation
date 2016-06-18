package simulation;

import OSPABA.*;
import Objekty.Vozidlo;

public class MyMessage extends MessageForm {

    private Vozidlo voz;
    private double mnozstvoMater;
    private int nakladacId;
    private int vykladacId;
    private boolean is2vykladac;
    private boolean isOdstavenyA;
    private double zaciatokCakanie=0;

    public MyMessage(Simulation sim) {
        super(sim);
    }

    public MyMessage(MyMessage original) {
        super(original);
        // copy() is called in superclass
        voz = original.voz;
        mnozstvoMater = original.mnozstvoMater;
        nakladacId = original.nakladacId;
        vykladacId = original.vykladacId;
        is2vykladac = original.is2vykladac;
        is2vykladac = original.is2vykladac;
        isOdstavenyA = original.isOdstavenyA;
        zaciatokCakanie=original.zaciatokCakanie;
    }

    @Override
    public MessageForm createCopy() {
        return new MyMessage(this);
    }

    @Override
    protected void copy(MessageForm message) {
        super.copy(message);
        MyMessage original = (MyMessage) message;
        // Copy attributes
    }

    public Vozidlo getVoz() {
        return voz;
    }

    public void setVoz(Vozidlo voz) {
        this.voz = voz;
    }

    public double getMnozstvoMater() {
        return mnozstvoMater;
    }

    public void setMnozstvoMater(double mnozstvoMater) {
        this.mnozstvoMater = mnozstvoMater;
    }

    public int getNakladacId() {
        return nakladacId;
    }

    public void setNakladacId(int nakladacId) {
        this.nakladacId = nakladacId;
    }

    public int getVykladacId() {
        return vykladacId;
    }

    public void setVykladacId(int vykladacId) {
        this.vykladacId = vykladacId;
    }

    public boolean isIs2vykladac() {
        return is2vykladac;
    }

    public void setIs2vykladac(boolean is2vykladac) {
        this.is2vykladac = is2vykladac;
    }

    public boolean isIsOdstavenyA() {
        return isOdstavenyA;
    }

    public void setIsOdstavenyA(boolean isOdstavenyA) {
        this.isOdstavenyA = isOdstavenyA;
    }

    public double getZaciatokCakanie() {
        return zaciatokCakanie;
    }

    public void setZaciatokCakanie(double zaciatokCakanie) {
        this.zaciatokCakanie = zaciatokCakanie;
    }

}
