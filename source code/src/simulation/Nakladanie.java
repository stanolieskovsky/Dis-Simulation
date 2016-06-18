package simulation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liesko3
 */
public class Nakladanie {

    private String datum;
    private String dodavatel;
    private String mnozstvo;

    public Nakladanie(String datum, String dodavatel, String mnozstvo) {
        this.datum = datum;
        this.dodavatel = dodavatel;
        this.mnozstvo = mnozstvo;
    }



    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getDodavatel() {
        return dodavatel;
    }

    public void setDodavatel(String dodavatel) {
        this.dodavatel = dodavatel;
    }

    public String getMnozstvo() {
        return mnozstvo;
    }

    public void setMnozstvo(String mnozstvo) {
        this.mnozstvo = mnozstvo;
    }

}
