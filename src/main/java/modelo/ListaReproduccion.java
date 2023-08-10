package modelo;

import java.util.ArrayList;

public class ListaReproduccion {
    private ArrayList<Video> listaVideos = new ArrayList<>();
    private String nombre;

    private String archivoImagen;

    private int id;

    private int idUsuario;



    public ListaReproduccion(ArrayList<Video> listaVideos, String nombre, String archivoImagen, int id, int idUsuario) {
        this.listaVideos = listaVideos;
        this.nombre = nombre;
        this.archivoImagen = archivoImagen;
        this.id = id;
        this.idUsuario = idUsuario;
    }

    public ListaReproduccion() {
        this.listaVideos = new ArrayList<>();
    }

    public ArrayList<Video> getListaVideos() {
        return listaVideos;
    }

    public void setListaVideos(ArrayList<Video> listaVideos) {
        this.listaVideos = listaVideos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArchivoImagen() {
        return archivoImagen;
    }

    public void setArchivoImagen(String archivoImagen) {
        this.archivoImagen = archivoImagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }


    @Override
    public String toString() {
        return "ListaVideos{" +
                "listaVideos=" + listaVideos +
                ", nombre='" + nombre + '\'' +
                ", archivoImagen='" + archivoImagen + '\'' +
                '}';
    }
}
