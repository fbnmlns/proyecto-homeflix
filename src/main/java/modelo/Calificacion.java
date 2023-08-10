package modelo;

public class Calificacion {
    private int idCalificacion;
    private int idVideo;
    private int idUsuario;
    private boolean estado;

    public Calificacion(int idCalificacion, int idVideo, int idUsuario, boolean estado) {
        this.idCalificacion = idCalificacion;
        this.idVideo = idVideo;
        this.idUsuario = idUsuario;
        this.estado = estado;
    }

    public Calificacion() {

    }

    public int getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(int idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public int getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(int idVideo) {
        this.idVideo = idVideo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Calificacion{" +
                "idCalificacion=" + idCalificacion +
                ", idVideo=" + idVideo +
                ", idUsuario=" + idUsuario +
                ", estado=" + estado +
                '}';
    }
}
