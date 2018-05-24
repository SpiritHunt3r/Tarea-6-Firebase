package ec.tec.ac.cr.juan_jop96.tarea6_firebase;

import java.util.UUID;

public class Producto {
    private String id;
    private String nombre;
    private String precio;
    private String foto;
    private String description;

    public Producto() {
    }

    public Producto(String nombre, String precio, String foto, String description) {
        id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.precio = precio;
        this.foto = foto;
        this.description = description;
    }

    public Producto(String ID, String nombre, String precio, String foto, String description) {
        this.id = ID;
        this.nombre = nombre;
        this.precio = precio;
        this.foto = foto;
        this.description = description;
    }

    public String getID() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
