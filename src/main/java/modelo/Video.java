package modelo;

import java.time.LocalDate;

/**
 * Esta clase contiene los metodos y atributo de Video
 */
public class Video {
    private int id;
    private String nombre;
    private String categoria;
    private LocalDate fecha;
    private String descripcion;
    private int calificacion;
    private String archivo;
    private String thumbnailVideo;
    private int idUsuario;


    public Video(int id, String nombre, String categoria, LocalDate fecha, String descripcion, int calificacion, String archivo, String thumbnailVideo, int idUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.archivo = archivo;
        this.thumbnailVideo = thumbnailVideo;
        this.idUsuario = idUsuario;
    }

    public Video() {
    }

    /**
     * Esta funcion permite obtener el id del video
     *
     * @return id del video
     */
    public int getId() {
        return id;
    }

    /**
     * @param id Esta funcion permite configurar el id del video
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Esta funcion permite obtener el nombre del video
     *
     * @return el nombre del video
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Esta funcion permite la configuracion del nombre del video
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Esta funcion permite obtener el nombre del video
     *
     * @return Categoria del video
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Esta funcion permite la configuracion de la categoria del video
     *
     * @param categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Esta funcion permite obtener la fecha del registro del video
     *
     * @return la fceha del registro del video
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Esta funcion permite la configuracion de la fecha del video
     *
     * @param fecha
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Esta funcion permite obtener la descripcion del video
     *
     * @return la descripcion del video
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Esta funcion permite la configuracion de la descripcion del video
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Esta funcion permite obtener la calificacion del video
     *
     * @return la calificacion del video
     */
    public int getCalificacion() {
        return calificacion;
    }

    /**
     * Esta funcion permite la configuracion de la calificacion del video
     *
     * @param calificacion
     */
    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * Esta funcion permite obtener el archivo del video
     *
     * @return el archivo del video
     */
    public String getArchivo() {
        return archivo;
    }

    /**
     * Esta funcion permite la configuracion del archivo del video
     *
     * @param archivo
     */
    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getThumbnailVideo() {
        return thumbnailVideo;
    }

    public void setThumbnailVideo(String thumbnailVideo) {
        this.thumbnailVideo = thumbnailVideo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }


    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", calificacion=" + calificacion +
                ", archivo='" + archivo + '\'' +
                ", thumbnailVideo='" + thumbnailVideo + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                '}';
    }
}
