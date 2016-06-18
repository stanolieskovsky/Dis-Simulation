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
public class ObjektGrafu {
    private String nazov;
    private String spodok;
    private String bok;
   
 private Graf graf;
    public ObjektGrafu(String nazov,String spodok,String bok) {
        this.nazov = nazov;
         graf = new Graf("", nazov, spodok, bok);
         
         
         
        graf.pridajSeries(nazov);
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }
        public void pridajBodDoGrafu(double y, double paP) {
        graf.pridajBodDoSerie(0, paP++, y);
    }
        public Graf getGraf(){
        return graf;
    }

}
