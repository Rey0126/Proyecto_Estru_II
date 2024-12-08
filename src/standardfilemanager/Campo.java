package standardfilemanager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author walte
 */
public class Campo{
    private String nombre;
    private String tipo;
    private int longitud;
    private boolean llave;

    public Campo() {
    }

    public Campo(String nombre, String tipo, int longitud, boolean llave) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.longitud = longitud;
        this.llave = llave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public boolean isLlave() {
        return llave;
    }

    public void setLlave(boolean llave) {
        this.llave = llave;
    }

    @Override
    public String toString() {
        return "Campo: " + nombre + " Tipo: " + tipo + " Longitud: " + longitud + " Key: " + llave;
    }
    
    
}
