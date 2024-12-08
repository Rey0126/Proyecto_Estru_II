package standardfilemanager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author brhb2
 */
public class Nodo implements Serializable {

    private ArrayList<Key> keys = new ArrayList();
    private ArrayList<Nodo> hijos = new ArrayList();
    private int keyPos;
    private int cantKeys;
    private boolean leaf;

    public Nodo(boolean leaf, int keyPos, int cantKeys) {
        this.leaf = leaf;
        this.cantKeys = cantKeys;
        this.keyPos = keyPos;
    }

    public Nodo() {
        this.leaf = true;
        this.cantKeys = 0;
    }

    public ArrayList<Key> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<Key> keys) {
        this.keys = keys;
    }

    public ArrayList<Nodo> getHijos() {
        return hijos;
    }

    public void setHijos(ArrayList<Nodo> hijos) {
        this.hijos = hijos;
    }

    public int getKeyPos() {
        return keyPos;
    }

    public void setKeyPos(int keyPos) {
        this.keyPos = keyPos;
    }

    public int getCantKeys() {
        return cantKeys;
    }

    public void setCantKeys(int cantKeys) {
        this.cantKeys = cantKeys;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public void printKeys() {
        System.out.print("{");
        for (int i = 0; i < keys.size(); i++) {
            if (i < keys.size() - 1) {
                System.out.print(keys.get(i) + " | ");
            } else {
                System.out.print(keys.get(i));
            }
        }
        System.out.print("}");
    }

    @Override
    public String toString() {
        return "Nodo{" + "keys=" + keys + ", hijos=" + hijos + ", keyPos=" + keyPos + ", cantKeys=" + cantKeys + ", leaf=" + leaf + '}';
    }
}
