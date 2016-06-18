/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objekty;

import java.util.Random;
import simulation.MySimulation;

/**
 *
 * @author liesko3
 */
public class Generator {

    Random randde1;
    Random randde2;
    Random randde3;
    Random randde4;
    Random randde5;
    Random randsr;
    private Random[] poleRand=new Random[5];

    public Generator() {
        randde1 = new Random(MySimulation.nasada.nextLong());
        randde2 = new Random(MySimulation.nasada.nextLong());
        randde3 = new Random(MySimulation.nasada.nextLong());
        randde4 = new Random(MySimulation.nasada.nextLong());
        randde5 = new Random(MySimulation.nasada.nextLong());
        randsr = new Random(MySimulation.nasada.nextLong());
        poleRand[0] = randde1;
        poleRand[1] = randde2;
        poleRand[2] = randde3;  
        poleRand[3] = randde4;
        poleRand[4] = randde5;
    
    }

    public int genDiskretne(int min, int max, int paCisloGen) {
        int hodnota = poleRand[paCisloGen].nextInt(max - min) + min;
        return hodnota;
    }

    public double genRovnomerne() {
        double hodnota = randsr.nextDouble();
        return hodnota;
    }
}
