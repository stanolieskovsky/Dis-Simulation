/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author liesko3
 */
public class ZapisDoSuboru {

    File file = new File("simulacieKombinacie.txt");
    File fileIntervaly = new File("simulacieKombinacieIntervaly.txt");
   


    public ZapisDoSuboru() {
    }

    public void ulozDoSuborov(String s) throws FileNotFoundException, IOException {
        try (FileWriter fw = new FileWriter("simulacieKombinacie.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(s);
        } catch (IOException e) {
        }
    }

    public void ulozDoSuborovIntervaly(String s) throws FileNotFoundException {

        // fileIntervaly = new File("simulacieKombinacieIntervaly.txt");
        try (FileWriter fw = new FileWriter("simulacieKombinacieIntervaly.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(s);
        } catch (IOException e) {
        }
        
    }
}
