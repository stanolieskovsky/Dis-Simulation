/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author liesko3
 */
public class Nacitanie {

    Scanner scan;
    File file;
    File fileA;
    File fileB;
    File fileC;
    FileReader reader;
    private PrintWriter writer;

    private LinkedList dodvatelA = new LinkedList();
    private LinkedList dodvatelB = new LinkedList();
    private LinkedList dodvatelC = new LinkedList();

    public void nacitajSubor() {
        try {
            String[] line;
            file = new File("vstupy.txt");
            reader = new FileReader(file);
            scan = new Scanner(reader);
            while (scan.hasNextLine()) {
                line = scan.nextLine().split(";");
                Nakladanie naklad = new Nakladanie(line[0], line[1], line[2]);
                // Zastavka zas = new Zastavka(Integer.parseInt(line[0]), Integer.parseInt(line[1]), (line[2]));
                if (naklad.getDodavatel().equals("A")) {
                    dodvatelA.add(naklad);
                } else if (naklad.getDodavatel().equals("B")) {
                    dodvatelB.add(naklad);
                } else {
                    dodvatelC.add(naklad);
                }
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Nacitanie.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ulozDoSuborov() throws FileNotFoundException {

        fileA = new File("dodavatelA.txt");
        fileB = new File("dodavatelB.txt");
        fileC = new File("dodavatelC.txt");
        writer = new PrintWriter(fileA);
        for (int i = 0; i < dodvatelA.size(); i++) {
            Nakladanie naklad = (Nakladanie) dodvatelA.get(i);
            writer.println(naklad.getDatum() + ";" + naklad.getDodavatel() + ";" + naklad.getMnozstvo());
        }
        writer.close();
        writer = new PrintWriter(fileB);
        for (int i = 0; i < dodvatelB.size(); i++) {
            Nakladanie naklad = (Nakladanie) dodvatelB.get(i);
            writer.println(naklad.getDatum() + ";" + naklad.getDodavatel() + ";" + naklad.getMnozstvo());
        }
        writer.close();
        writer = new PrintWriter(fileC);
        for (int i = 0; i < dodvatelC.size(); i++) {
            Nakladanie naklad = (Nakladanie) dodvatelC.get(i);
            writer.println(naklad.getDatum() + ";" + naklad.getDodavatel() + ";" + naklad.getMnozstvo());
        }
        writer.close();
    }

    public void vypis() {
        System.out.println("dodavatel A> " + dodvatelA.size());
        System.out.println("dodavatel B> " + dodvatelB.size());
        System.out.println("dodavatel C> " + dodvatelC.size());
        System.out.println("suma> " + (dodvatelC.size() + dodvatelA.size() + dodvatelB.size()));
    }

}
