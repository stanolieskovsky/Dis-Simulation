/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import Objekty.Vozidlo;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author liesko3
 */
public class TabModel extends AbstractTableModel {

    private LinkedList<Vozidlo> list;

    public TabModel(LinkedList<Vozidlo> palist) {
        this.list = palist;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Typ";
            case 1:
                return "Objem";
            case 2:
                return "Naklad";
            case 3:
                return "cena";
            case 4:
                return "Rychlost";
            case 5:
                return "Pozicia";
            default:
                return "";

        }
    }

    @Override
    public Object getValueAt(int r, int c) {
        if (list == null) {
            return 0;
        }
        switch (c) {
            case 0:
                return list.get(r).getTyp();
            case 1:
                return list.get(r).getObjem();
            case 2:
                return list.get(r).getObjemnaVykreslenie();
            case 3:
                return list.get(r).getCena();
            case 4:
                return list.get(r).getRychlost();
            case 5:
                return list.get(r).getPozicia();
            default:
                return "mistake";
        }
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

}
