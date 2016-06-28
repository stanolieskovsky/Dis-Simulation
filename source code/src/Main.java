
import OSPRNG.ExponentialRNG;
import OSPRNG.GammaRNG;
import java.io.FileNotFoundException;
import java.io.IOException;
import simulation.MySimulation;
import simulation.Nacitanie;
import simulation.ZapisDoSuboru;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liesko3
 */
public class Main {

    /**
     * @param args the command line arguments
     */
	public static void main(String [] args) throws FileNotFoundException, IOException
	{
            ZapisDoSuboru zapis=new ZapisDoSuboru();
            zapis.ulozDoSuborov("bistu");
            zapis.ulozDoSuborov("druhe");
//		          MySimulation sim = new MySimulation();
//		
//		sim.onSimulationWillStart(s ->{
////			System.out.println("Simulating...");
//		});
////
////		sim.simulate(1000, 90000000d);
//                sim.simulate(1);
//            Nacitanie nac=new Nacitanie();
//            nac.nacitajSubor();
//            nac.vypis();
//            nac.ulozDoSuborov();
                
                   ExponentialRNG expoA=new ExponentialRNG(55.9, MySimulation.getNasada(),0.999);
    ExponentialRNG expoB=new ExponentialRNG(33.8, MySimulation.getNasada(),3.0);
   ExponentialRNG expoC=new ExponentialRNG(18.8, MySimulation.getNasada(),0.999);
    GammaRNG expoC=new GammaRNG(26.8, 1.09, 0.999);
            System.out.println(""+expoA.sample());
            System.out.println(""+expoB.sample());
            System.out.println(""+expoC.sample());
	}
}
