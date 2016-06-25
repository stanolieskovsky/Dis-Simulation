/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statist;

/**
 *
 * @author liesko3
 */
public class Statist {

    private double pocet;
    private double suma;

    public Statist() {
        this.pocet = 0;
        this.suma = 0;
    }
    public double getPocet() {
        return pocet;
    }
 public double setPocet(double poc) {
        return pocet=poc;
    }
    public void incrementPocet() {
        this.pocet++;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double sumaCasu) {
        this.suma = sumaCasu;
    }

    public double priemer() {
        return suma / pocet;

    }
    public void pridajCislo(double cislo,double vaha)
    {
    pocet=pocet+vaha;
    suma=suma+(cislo*vaha);
    
    }
   public void pridajCislo(double cislo)
    {
    pocet++;
    suma=suma+cislo;
    }
    public static void main(String[] args) {
        // TODO code application logic here
    Statist s=new Statist();
    s.pridajCislo(1, 3);
    s.pridajCislo(2, 4);
    s.pridajCislo(1, 2);
        System.out.println(""+ s.priemer());
    
    
    }
}
