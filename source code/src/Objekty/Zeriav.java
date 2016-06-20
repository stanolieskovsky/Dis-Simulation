/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objekty;

import java.util.Objects;

/**
 *
 * @author liesko3
 */
public class Zeriav {
    private int hmotnost;
     private int rychlost;
      private String nazovTyp;

    public Zeriav(String nazovTyp) {
        this.nazovTyp = nazovTyp;
    }

    public int getHmotnost() {
        return hmotnost;
    }

    public void setHmotnost(int hmotnost) {
        this.hmotnost = hmotnost;
    }

    public int getRychlost() {
        return rychlost;
    }

    public void setRychlost(int rychlost) {
        this.rychlost = rychlost;
    }

    public String getNazovTyp() {
        return nazovTyp;
    }

    public void setNazovTyp(String nazovTyp) {
        this.nazovTyp = nazovTyp;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.nazovTyp);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Zeriav other = (Zeriav) obj;
        if (this.hmotnost != other.hmotnost) {
            return false;
        }
        if (!Objects.equals(this.nazovTyp, other.nazovTyp)) {
            return false;
        }
        return true;
    }
      
    
}
