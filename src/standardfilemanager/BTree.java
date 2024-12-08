package standardfilemanager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author brhb2
 */
public class BTree implements Serializable {

    private static final long serialVersionUID = 6139458848725140940L;
    private Nodo raiz;
    private int orden;

    public BTree(Nodo raiz, int orden) {
        this.raiz = raiz;
        this.orden = orden;
    }

    public BTree() {
        this.orden = 6;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public void crearArbol() {
        orden = 6;
        raiz = new Nodo(true, 0, 0);
        raiz.setKeys(new ArrayList<Key>());
        raiz.setHijos(new ArrayList<Nodo>());
    }

    public Nodo Search(Nodo nodo, Key searchKey) {
        ArrayList<Key> KeysNodo = nodo.getKeys();
        int i = 0;

        String searchKeyString = searchKey.getKey();

        while (i < KeysNodo.size()) {
            String currentKey = KeysNodo.get(i).getKey();
            int comparisonResult = compararKeys(searchKeyString, currentKey);

            if (comparisonResult <= 0) { // salir del bucle si la searchKey es menor o igual a 0
                break;
            }
            i++;
        }

        // si la encuentra devuelve el nodo
        if (i < KeysNodo.size() && compararKeys(searchKeyString, KeysNodo.get(i).getKey()) == 0) {
            nodo.setKeyPos(i);
            return nodo;
        }

        if (nodo.isLeaf()) { //no lo encontró
            return null;
        } else { //sigue buscando
            return Search(nodo.getHijos().get(i), searchKey);
        }
    }

    public int SearchByt(Nodo nodo, Key searchKey) {
        ArrayList<Key> KeysNodo = nodo.getKeys();
        int i = 0;

        String searchKeyString = searchKey.getKey();

        while (i < KeysNodo.size()) {
            String currentKey = KeysNodo.get(i).getKey();
            int comparisonResult = compararKeys(searchKeyString, currentKey);
            System.out.println("Comparando con llave: " + currentKey + " Resultado: " + comparisonResult);

            if (comparisonResult <= 0) { // salir del bucle si la searchKey es menor o igual a 0
                break;
            }
            i++;
        }

        // si la encuentra devuelve el nodo
        if (i < KeysNodo.size() && compararKeys(searchKeyString, KeysNodo.get(i).getKey()) == 0) {
            nodo.setKeyPos(i);
            return nodo.getKeys().get(i).getByteoffset();
        }

        if (nodo.isLeaf()) { // no lo encontró
            return -1;
        } else { // sigue buscando
            // Verificar que el índice i no exceda el tamaño de la lista de hijos
            if (i < nodo.getHijos().size()) {
                System.out.println("Buscando en el hijo índice: " + i);
                return SearchByt(nodo.getHijos().get(i), searchKey);
            } else {
                System.out.println("Índice fuera de los límites. Llave no encontrada.");
                return -1; // Manejar el caso donde el índice está fuera de los límites
            }
        }
    }

    private int compararKeys(String key1, String key2) {
        int length = Math.min(key1.length(), key2.length()); //por si tienen longitud diferente, que agarre la longitud minima

        for (int j = 0; j < length; j++) {
            char char1 = key1.charAt(j);
            char char2 = key2.charAt(j);

            if (char1 != char2 && key1.length() == key2.length()) {
                return char1 - char2; // si char 1 es menor, lo devuelve negativo, sino al reves
            }
        }

        return key1.length() - key2.length(); //lo mismo aca, pero si son completamente iguales hasta en tamanio devuelve 0
    }

    public void splitNodo(Nodo padre, int index, Nodo y) {
        int t = 6; // Orden del árbol B
        int mid = (t / 2) - 1; // Índice de la clave central que será promovida

        // Crear un nuevo nodo z para almacenar claves y hijos de la mitad superior de y
        Nodo z = new Nodo(y.isLeaf(), 0, 0);

        // Mover las últimas claves de y a z
        for (int i = mid + 1; i < t - 1; i++) {
            z.getKeys().add(y.getKeys().get(i));
        }
        // Eliminar esas claves de y
        while (y.getKeys().size() > mid + 1) {
            y.getKeys().remove(mid + 1);
        }

        // Si y no es hoja, mover los hijos correspondientes de y a z
        if (!y.isLeaf()) {
            for (int i = mid + 1; i < t; i++) {
                z.getHijos().add(y.getHijos().get(i));
            }
            // Eliminar esos hijos de y
            while (y.getHijos().size() > mid + 1) {
                y.getHijos().remove(mid + 1);
            }
        }

        // Promover la clave central de y a padre
        Key promotedKey = y.getKeys().get(mid);
        y.getKeys().remove(mid);

        // Insertar la clave promovida en el padre en la posición adecuada
        padre.getKeys().add(index, promotedKey);

        // Añadir z como el nuevo hijo derecho de la clave promovida
        padre.getHijos().add(index + 1, z);

        // Ajustar las cantidades de claves en los nodos
        y.setCantKeys(y.getKeys().size());
        z.setCantKeys(z.getKeys().size());
        padre.setCantKeys(padre.getKeys().size());
    }

    public void insertNonFull(Nodo raiz, Key k) {
        int i = raiz.getCantKeys() - 1; // Posición inicial para insertar
        String keyValue = k.getKey();
        System.out.println("Inicio de insertNonFull");

        if (raiz.isLeaf()) {
            // Caso: Nodo es hoja, insertar directamente en el lugar correcto
            System.out.println("Nodo es hoja");
            while (i >= 0 && compararKeys(keyValue, raiz.getKeys().get(i).getKey()) < 0) {
                if (i + 1 < raiz.getKeys().size()) {
                    raiz.getKeys().set(i + 1, raiz.getKeys().get(i));
                } else {
                    raiz.getKeys().add(raiz.getKeys().get(i));
                }
                i--;
            }

            // Insertar la nueva clave en la posición correcta
            if (i + 1 < raiz.getKeys().size()) {
                raiz.getKeys().set(i + 1, k);
            } else {
                raiz.getKeys().add(k);
            }
            raiz.setCantKeys(raiz.getCantKeys() + 1);
        } else {
            // Caso: Nodo no es hoja, encontrar el hijo adecuado
            System.out.println("Nodo no es hoja");
            while (i >= 0 && compararKeys(keyValue, raiz.getKeys().get(i).getKey()) < 0) {
                i--;
            }
            i++; // Mover a la posición del hijo

            // Si el hijo está lleno, realizar un split
            if (i < raiz.getHijos().size() && raiz.getHijos().get(i).getCantKeys() == 5) {
                System.out.println("Hijo lleno, realizando split");
                splitNodo(raiz, i, raiz.getHijos().get(i));

                // Determinar a cuál hijo proceder después del split
                if (i < raiz.getKeys().size() && compararKeys(keyValue, raiz.getKeys().get(i).getKey()) > 0) {
                    i++;
                }
            }

            // Insertar recursivamente en el hijo adecuado
            if (i < raiz.getHijos().size()) {
                System.out.println("Insertando en el hijo");
                insertNonFull(raiz.getHijos().get(i), k);
            }
        }
    }

    public void insert(Nodo nodo, Key k) {
        // Caso: La raíz está llena y necesita dividirse
        if (nodo.getKeys().size() == 5) {
            System.out.println("La raíz está llena, creando nueva raíz y dividiendo");

            // Crear una nueva raíz
            Nodo nuevaRaiz = new Nodo(false, 0, 0);
            nuevaRaiz.getHijos().add(nodo); // La raíz anterior se convierte en el primer hijo
            splitNodo(nuevaRaiz, 0, nodo); // Dividir la raíz anterior y promover una clave

            // Actualizar la referencia de la raíz
            raiz = nuevaRaiz;

            // Insertar la nueva clave en la nueva estructura
            insertNonFull(nuevaRaiz, k);
        } else {
            // Caso: La raíz no está llena, insertar directamente
            System.out.println("Insertando clave en nodo no lleno");
            insertNonFull(nodo, k);
        }
    }
///12211354
///12311504

    public void delete(Nodo nodo, String key) {
        int i = 0;

        // Buscar la posición de la clave o el hijo correspondiente usando tu método compare
        while (i < nodo.getCantKeys() && compararKeys(nodo.getKeys().get(i).getKey(), key) < 0) {
            i++;
        }

        if (i < nodo.getCantKeys() && compararKeys(nodo.getKeys().get(i).getKey(), key) == 0) {
            // Caso 1: La clave está en el nodo y es una hoja
            if (nodo.isLeaf()) {
                nodo.getKeys().remove(i);
                nodo.setCantKeys(nodo.getCantKeys() - 1);
            } else {  // Caso 2: La clave está en un nodo interno
                Nodo hijoIzq = nodo.getHijos().get(i);
                Nodo hijoDer = nodo.getHijos().get(i + 1);

                // Caso 2a: El hijo izquierdo tiene al menos 2 claves
                if (hijoIzq.getCantKeys() >= 2) {
                    String predecesor = getMaxKey(hijoIzq);
                    nodo.getKeys().get(i).setKey(predecesor);
                    delete(hijoIzq, predecesor);
                } // Caso 2b: El hijo derecho tiene al menos 2 claves
                else if (hijoDer.getCantKeys() >= 2) {
                    String sucesor = getMinKey(hijoDer);
                    nodo.getKeys().get(i).setKey(sucesor);
                    delete(hijoDer, sucesor);
                } // Caso 2c: Ambos hijos tienen solo 1 clave
                else {
                    merge(nodo, i);
                    delete(hijoIzq, key);  // Eliminar la clave original que se ha fusionado
                }
            }
        } else {  // Caso 3: La clave no está en este nodo, proceder con los hijos
            if (nodo.isLeaf()) {
                System.out.println("Clave no encontrada: " + key);
                return;  // Clave no encontrada
            }

            Nodo hijo = nodo.getHijos().get(i);
            if (hijo.getCantKeys() == 2) {  // Si el hijo tiene solo 2 claves
                Nodo hermanoIzq = (i > 0) ? nodo.getHijos().get(i - 1) : null;
                Nodo hermanoDer = (i < nodo.getCantKeys()) ? nodo.getHijos().get(i + 1) : null;

                // Caso 3a: El hermano izquierdo tiene más de 2 claves
                if (hermanoIzq != null && hermanoIzq.getCantKeys() > 2) {
                    rotateRight(nodo, i);  // Realizar rotación a la derecha
                    delete(hijo, key);
                } // Caso 3b: El hermano derecho tiene más de 2 claves
                else if (hermanoDer != null && hermanoDer.getCantKeys() > 2) {
                    rotateLeft(nodo, i);  // Realizar rotación a la izquierda
                    delete(hijo, key);
                } // Caso 3c: Ambos hermanos tienen 2 claves
                else {
                    if (hermanoIzq != null) {
                        merge(nodo, i - 1);  // Fusionar con el hermano izquierdo
                        delete(hermanoIzq, key);
                    } else {
                        merge(nodo, i);  // Fusionar con el hermano derecho
                        delete(hijo, key);
                    }
                }
            } else {
                delete(hijo, key);  // Si el hijo tiene más de 2 claves, seguir con la eliminación
            }
        }
    }

    private void rotateRight(Nodo nodo, int i) {
        Nodo hijoIzq = nodo.getHijos().get(i - 1);
        Nodo hijo = nodo.getHijos().get(i);

        hijo.getKeys().add(0, nodo.getKeys().get(i - 1));  // Mover la clave del nodo al hijo derecho
        nodo.getKeys().set(i - 1, hijoIzq.getKeys().get(hijoIzq.getCantKeys() - 1));  // Mover la clave del hermano al nodo
        hijoIzq.getKeys().remove(hijoIzq.getCantKeys() - 1);  // Eliminar la clave movida
        hijoIzq.setCantKeys(hijoIzq.getCantKeys() - 1);  // Actualizar el número de claves del hermano izquierdo
    }

// Función para rotar una clave del hermano derecho al nodo actual (rotación a la izquierda)
    private void rotateLeft(Nodo nodo, int i) {
        Nodo hijoDer = nodo.getHijos().get(i + 1);
        Nodo hijo = nodo.getHijos().get(i);

        hijo.getKeys().add(nodo.getKeys().get(i));  // Mover la clave del nodo al hijo izquierdo
        nodo.getKeys().set(i, hijoDer.getKeys().get(0));  // Mover la clave del hermano al nodo
        hijoDer.getKeys().remove(0);  // Eliminar la clave movida
        hijoDer.setCantKeys(hijoDer.getCantKeys() - 1);  // Actualizar el número de claves del hermano derecho
    }

    private void merge(Nodo nodo, int i) {
        Nodo hijoIzq = nodo.getHijos().get(i);
        Nodo hijoDer = nodo.getHijos().get(i + 1);

        // Mover la clave del nodo al hijo izquierdo
        hijoIzq.getKeys().add(nodo.getKeys().get(i));
        hijoIzq.setCantKeys(hijoIzq.getCantKeys() + 1);

        // Mover las claves del hermano derecho al hijo izquierdo
        hijoIzq.getKeys().addAll(hijoDer.getKeys());
        hijoIzq.setCantKeys(hijoIzq.getCantKeys() + hijoDer.getCantKeys());

        // Mover los hijos del hermano derecho al hijo izquierdo
        if (!hijoIzq.isLeaf()) {
            hijoIzq.getHijos().addAll(hijoDer.getHijos());
        }

        // Eliminar el nodo hermano y la clave del nodo
        nodo.getHijos().remove(i + 1);
        nodo.getKeys().remove(i);
        nodo.setCantKeys(nodo.getCantKeys() - 1);
    }

// Función para obtener el máximo de un subárbol (usado en la sustitución de claves)
    private String getMaxKey(Nodo nodo) {
        while (!nodo.isLeaf()) {
            nodo = nodo.getHijos().get(nodo.getCantKeys());
        }
        return nodo.getKeys().get(nodo.getCantKeys() - 1).getKey();
    }

// Función para obtener el mínimo de un subárbol (usado en la sustitución de claves)
    private String getMinKey(Nodo nodo) {
        while (!nodo.isLeaf()) {
            nodo = nodo.getHijos().get(0);
        }
        return nodo.getKeys().get(0).getKey();
    }

// Rotaciones y merge no cambian, ya que solo mueven las claves.
    public void printTree() {
        printNodo(raiz, 0);
    }

    private void printNodo(Nodo nodo, int level) {
        if (nodo != null) {
            System.out.print("Level " + level + " ");
            nodo.printKeys();
            System.out.println();
            if (!nodo.isLeaf()) {
                for (Nodo hijo : nodo.getHijos()) {
                    printNodo(hijo, level + 1);
                }
            }
        }
    }

}/// int s = (367)+cont-1*351;
