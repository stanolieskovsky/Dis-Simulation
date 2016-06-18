package Objekty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;

/**
 *
 * @author liesko3
 */
public class Vozidlo implements Comparable<Vozidlo> {

    private String typ;
    private double objem;
    private double naklad;
    private double ObjemnaVykreslenie = 0;
    private double ObjemdoGui = 0;
    private double cena=0;
    private double rychlost;
    private double pravPoruchy;
    private double casOpravy;
    private double koniec;
    private double zaciatok;
    private double casZacNakladania;
    private double casPrichoduNaStavbu;
    private double casPrichoduNaSkladku;
    private double casPrichoduNaPrejazd;
    private double casOdchoduNaStavbu;
    private double casOdchoduNaSkladku;
    private double casOdchoduNaPrejazd;
    private double casZacVykladania;
    private double pomocnaKapacita;
    private String pozicia="";
    private Random rand;

    public Vozidlo(String patyp) {
        this.typ = patyp;
        nastav(patyp);
        casPrichoduNaSkladku = 0;
        casPrichoduNaStavbu = 0;
        rand = new Random(simulation.MySimulation.nasada.nextLong());
    }

    public void nastav(String patyp) {
        if (null != patyp) {
            switch (patyp) {
                case "A1":
                    objem = 10;
                    naklad = 10;
                    rychlost = 60;
                    pravPoruchy =0.12;
                    casOpravy = 80;
                    cena=30000;
                    break;
                case "A2":
                    objem = 20;
                    naklad = 20;
                    rychlost = 50;
                    pravPoruchy =0.04;
                    casOpravy = 50;
                    cena=55000;
                    break;
                case "A3":
                    objem = 25;
                    naklad = 25;
                    rychlost = 45;
                    pravPoruchy =0.04;
                    casOpravy = 100;
                    cena=40000;
                    break;
                case "A4":
                    objem = 5;
                    naklad = 5;
                    rychlost = 70;
                    pravPoruchy =0.11;
                    casOpravy = 44;
                    cena=60000;
                    break;
                case "A5":
                    objem = 40;
                    naklad = 40;
                    rychlost = 30;
                    pravPoruchy =0.06;
                    casOpravy = 170;
                    cena=10000;
                    break;

            }
        }

    }

    public void nuluj() {
        setNaklad(objem);
        setCasPrichoduNaSkladku(-1);
        setCasPrichoduNaStavbu(-1);
        setCasPrichoduNaPrejazd(0);
        setObjemnaVykreslenie(0);
        setPomocnaKapacita(0);
        setZaciatok(0);
        setKoniec(0);
        setPozicia("");
    }

    public double getObjemnaVykreslenie() {
        return (double) Math.round(ObjemnaVykreslenie / 3600 * 1000) / 1000 ;
    }

    public void setObjemnaVykreslenie(double ObjemnaVykreslenie) {
        this.ObjemnaVykreslenie = ObjemnaVykreslenie;
    }

    public double getPomocnaKapacita() {
        return pomocnaKapacita;
    }

    public void setPomocnaKapacita(double pomocnaKapacita) {
        this.pomocnaKapacita = pomocnaKapacita;
    }

    public double getKoniec() {
        return koniec;
    }

    public void setKoniec(double koniec) {
        this.koniec = koniec;
    }

    public double getZaciatok() {
        return zaciatok;
    }

    public void setZaciatok(double zaciatok) {
        this.zaciatok = zaciatok;
    }

    public double getNaklad() {
        return naklad;
    }

    public void setNaklad(double naklad) {
        this.naklad = naklad;
    }

    public String getTyp() {
        return typ;
    }

    public double getObjem() {
        return objem;
    }

    public double getRychlost() {
        return (double) (rychlost);
    }

    public double getPravPoruchy() {
        return pravPoruchy;
    }

    public double getCasOpravy() {
        return casOpravy * 60;
    }

    public double getCasZacNakladania() {
        return casZacNakladania;
    }

    public void setCasZacNakladania(double casZacNakladania) {
        this.casZacNakladania = casZacNakladania;
    }

    public double getCasPrichoduNaStavbu() {
        return casPrichoduNaStavbu;
    }

    public void setCasPrichoduNaStavbu(double casPrichoduNaStavbu) {
        this.casPrichoduNaStavbu = casPrichoduNaStavbu;
    }

    public double getCasPrichoduNaSkladku() {
        return casPrichoduNaSkladku;
    }

    public void setCasPrichoduNaSkladku(double casPrichoduNaSkladku) {
        this.casPrichoduNaSkladku = casPrichoduNaSkladku;
    }

    public double getCasZacVykladania() {
        return casZacVykladania;
    }

    public void setCasZacVykladania(double casZacVykladania) {
        this.casZacVykladania = casZacVykladania;
    }

    public double generujNahodu() {
        return rand.nextDouble();

    }

    public double getCasPrichoduNaPrejazd() {
        return casPrichoduNaPrejazd;
    }

    public void setCasPrichoduNaPrejazd(double casPrichoduNaPrejazd) {
        this.casPrichoduNaPrejazd = casPrichoduNaPrejazd;
    }

    @Override
    public String toString() {
        return getTyp() + " " + getObjem() + "/" + getObjemnaVykreslenie() + "\n";

    }
      public String toVypis() {
        return getTyp() + " " + getObjem() + " [m3],  " +getRychlost()+" [km/h], " +""+getCena()+" Eur"+ "\n";

    }

    public void nastav(double Zaciatok, double Koniec) {
        setZaciatok(Zaciatok);
        setKoniec(Koniec);
        setPomocnaKapacita(ObjemnaVykreslenie);
    }

    public void prepocitaHodnotu(double aktualny, double vykon) {

        double delta = aktualny - zaciatok;
        if (aktualny <= koniec) {
            ObjemnaVykreslenie = pomocnaKapacita + (double) (vykon * delta);
        }
     
    }

    public void prepocitaOdpocetHodnotu(double aktualny, double vykon) {

        double delta = aktualny - zaciatok;
        if (aktualny <= koniec) {
            ObjemnaVykreslenie = pomocnaKapacita - (double) (vykon * delta);
        }
    
    }

    public double getCasOdchoduNaStavbu() {
        return casOdchoduNaStavbu;
    }

    public void setCasOdchoduNaStavbu(double casOdchoduNaStavbu) {
        this.casOdchoduNaStavbu = casOdchoduNaStavbu;
    }

    public double getCasOdchoduNaSkladku() {
        return casOdchoduNaSkladku;
    }

    public void setCasOdchoduNaSkladku(double casOdchoduNaSkladku) {
        this.casOdchoduNaSkladku = casOdchoduNaSkladku;
    }

    public double getCasOdchoduNaPrejazd() {
        return casOdchoduNaPrejazd;
    }

    public void setCasOdchoduNaPrejazd(double casOdchoduNaPrejazd) {
        this.casOdchoduNaPrejazd = casOdchoduNaPrejazd;
    }

    public double getCena() {
        return cena;
    }

    public double getObjemdoGui() {
        return ObjemdoGui;
    }

    public void setObjemdoGui(double ObjemdoGui) {
        this.ObjemdoGui = ObjemdoGui;
    }

    public String getPozicia() {
        return pozicia;
    }

    public void setPozicia(String pozicia) {
        this.pozicia = pozicia;
    }

    
    
    
    @Override
    public int compareTo(Vozidlo t) {
        if (casPrichoduNaPrejazd < t.getCasPrichoduNaPrejazd()) {
            return -1;
        } else if (this.casPrichoduNaPrejazd > t.getCasPrichoduNaPrejazd()) {
            return 1;
        } else {
            return 0;
        }
    }

}
