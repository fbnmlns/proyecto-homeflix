package controlador;

import dataaccess.DACalificacion;
import dataaccess.DAListaReproduccion;
import dataaccess.DAUsuario;
import dataaccess.DAVideo;
import modelo.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BL {
    DAVideo daVideo = new DAVideo();
    DAUsuario daUsuario = new DAUsuario();
    DAListaReproduccion daListaReproduccion = new DAListaReproduccion();
    DACalificacion daCalificacion = new DACalificacion();
    private static BL instanciaBl;
    private Usuario usuarioActual;
    private Video actualVideo;
    private ListaReproduccion playListActual;
    private ModoReproduccion modoReproduccion;

    private SocketServerController socketServerController = new SocketServerController();


    private BL() {
    }

    public static BL getInstanciaBl() {
        if (instanciaBl == null) {
            instanciaBl = new BL();
        }
        return instanciaBl;
    }

    public int annadirVideo(Video video)  {
        return daVideo.annadirVideo(video);
    }

    public void annadirUsuario(Usuario usuario)  {
        if (validarContrasenna(usuario.getContrasenna()) == true) {
            daUsuario.annadirUsuario(usuario);
        }
    }

    public static boolean validarContrasenna(String contrsenna) {
        String regex = "^(?=.*[0-9])" +
                "(?=.*[a-z])(?=.*[A-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regex);

        if (contrsenna == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(contrsenna);

        return matcher.matches();
    }

    public void annadirListaReproduccion(ListaReproduccion listaVideo)  {
        daListaReproduccion.agregarListaReproduccion(listaVideo);
    }

    public Usuario buscarUsuario(String contrasenna, String nombreUsuario)  {

        Usuario usuario = daUsuario.buscarUsuario(contrasenna, nombreUsuario);
        if (usuario != null) {
            usuarioActual = usuario;
        }
        return usuario;
    }

    public boolean UsuarioExiste(String nombreUsuario)  {
        return daUsuario.buscarNombreUsuario(nombreUsuario);
    }

    public ArrayList<Video> listarVideos(int idUsuario)  {
        ArrayList<Video> videos = daVideo.obtenerVideos(idUsuario);
        return videos;
    }

    public ArrayList<ListaReproduccion> listarReproductionList(int idUsuario)  {
        ArrayList<ListaReproduccion> listasDeReproduccion = daListaReproduccion.listarListasReproduccion(idUsuario);
        return listasDeReproduccion;
    }

    public ArrayList<Video> buscarVideo(String criterio)  {
        ArrayList<Video> videosSolicitados = daVideo.buscarVideos(criterio);
        return videosSolicitados;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public Video getVideoActual() {
        return actualVideo;
    }

    public void setVideoActual(Video videoActual) {
        this.actualVideo = videoActual;
    }

    public ListaReproduccion getPlayListActual() {
        return playListActual;
    }

    public void setPlayListActual(ListaReproduccion playListActual) {
        this.playListActual = playListActual;
    }

    public void addVideoToPlayList(int idVideo, int idPlayList)  {
        daListaReproduccion.agregarVideoAPlaylist(idVideo, idPlayList);
    }

    public ArrayList<Video> videoEnPlayListActual(int idListaReproduccion)  {
        return daListaReproduccion.listaVideos(idListaReproduccion);
    }

    public void guardarCalificacion(Calificacion calificacion)  {
        daCalificacion.guardarCalificacion(calificacion);
    }

    public void borrarCalificacion(int idVideo, int idUsuario)  {
        daCalificacion.borrarCalificacion(idVideo, idUsuario);
    }

    public Calificacion obtenerCalificacionActual(int idVideo, int idUsuario)  {
        return daCalificacion.obtenerCalificacionActual(idVideo, idUsuario);
    }

    public ModoReproduccion getModoReproduccion() {
        return modoReproduccion;
    }

    public void setModoReproduccion(ModoReproduccion modoReproduccion) {
        this.modoReproduccion = modoReproduccion;
    }

    public void borrarVideo(int idVideo)  {
        daCalificacion.borrarVideo(idVideo);
        daListaReproduccion.borrarVideoDeLista(idVideo);
        daVideo.borrarVideo(idVideo);
    }

    public void modificar(Video videoActual)  {
        daVideo.modificarVideo(videoActual);
    }

    public void iniciarHost(Video video)  {
        socketServerController.iniciarHost(video);
    }

    public void cerrarHost()  {
        socketServerController.cerrarHost();
    }

    public void borrarPlayList(int idPlayList){
        daListaReproduccion.borrarPlayListTablaIntermedia(idPlayList);
        daListaReproduccion.borrarListaVideos(idPlayList);
    }

    public void borrarVideoEnPlayList(int idVideo){
        daListaReproduccion.borrarVideoEnPlayList(idVideo);
    }

    public void modificarPlayList(ListaReproduccion playListActual){
        daListaReproduccion.modificarPlayLis(playListActual);
    }

    public void modificarUsuario(Usuario usuarioActual){
        daUsuario.modificarUsuario(usuarioActual);
    }

    public void eliminarCuenta(int idUsuario)  {
        ArrayList<Video> videosUsuario = this.listarVideos(idUsuario);
        for (Video videoUsuario: videosUsuario){
             this.borrarVideo(videoUsuario.getId());
        }
        daListaReproduccion.borrarUsuarioPlayList(idUsuario);
        daCalificacion.borrarUsuario(idUsuario);
        daUsuario.borrarCuenta(idUsuario);
    }
}
