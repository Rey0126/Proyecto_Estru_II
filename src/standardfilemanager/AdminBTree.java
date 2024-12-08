package standardfilemanager;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Brandon
 */
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminBTree {

    private static final Logger LOGGER = Logger.getLogger(AdminBTree.class.getName());
    private BTree arbol = new BTree();
    private File file = null;

    public AdminBTree(String nombre) {
        file = new File(nombre + ".Tree");
        arbol.crearArbol();
    }

    public BTree getArbolCargado() {
        return arbol;
    }

    public void setArbolCargado(BTree arbolCargado) {
        this.arbol = arbolCargado;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void cargarArchivo() {
        try {
            if (file.exists()) {
                try (FileInputStream entrada = new FileInputStream(file);
                     ObjectInputStream objeto = new ObjectInputStream(entrada)) {
                    BTree arbolTemp = (BTree) objeto.readObject();
                    if (arbolTemp != null) {
                        arbol = arbolTemp;
                    } else {
                        LOGGER.info("No se ha creado el arbol");
                        arbol = new BTree();
                    }
                } catch (EOFException e) {
                    LOGGER.log(Level.INFO, "Final del archivo", e);
                }
            } else {
                LOGGER.info("Archivo no existe!!");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error loading file", ex);
        }
    }

    public void escribirArchivo() {
        try (FileOutputStream fw = new FileOutputStream(file);
             ObjectOutputStream bw = new ObjectOutputStream(fw)) {
            bw.writeObject(arbol);
            bw.flush();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error writing file", ex);
        }
    }
    
    
}

