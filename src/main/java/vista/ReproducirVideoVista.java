package vista;

import com.example.proyecto.Main;
import controlador.BL;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Calificacion;
import modelo.ListaReproduccion;
import modelo.ModoReproduccion;
import modelo.Video;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReproducirVideoVista {

    @FXML
    public MediaView mediaVideo;
    private MediaPlayer mediaPlayer;
    @FXML
    public Button btnReproducir;

    @FXML
    public VBox vBoxVideos;

    @FXML
    public Button btnPausar;
    @FXML
    public Button btnParar;
    @FXML
    public Button btnLento;
    @FXML
    public Button btnRapido;
    @FXML
    public Button btnBorrar;
    @FXML
    public Button btnModificar;
    @FXML
    public Slider progresBar;
    @FXML
    public Button btnVolver;
    @FXML
    public Slider volumSlider;
    @FXML
    public ImageView fullScreen;
    @FXML
    public ImageView addReproductionList;
    @FXML
    public ToggleButton btnLike;
    @FXML
    public Label liked;
    @FXML
    public Label labelTime;
    private BL blConexion = BL.getInstanciaBl();
    private int posicionActual = 0;

    @FXML
    public ScrollPane scrollPanePlayList;

    @FXML
    public HBox hboxPrincipal;

    @FXML
    public VBox vboxPrincipal;

    private boolean isHosting = false;

    private static final int defaultVideoHeight = 650;

    private static final int defaultVideoWidth = 900;



    @FXML
    public Button btnHost;
    private ArrayList<Video> videos = new ArrayList<>();

    public void initialize() {

       scrollPanePlayList.setVisible(false);

        loadVideosPlayList(blConexion.getModoReproduccion());
        reproducirVideo();
        indicadorLike();

        volumSlider.setValue(mediaPlayer.getVolume() * 100);
        volumSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumSlider.getValue() / 100);
            }
        });

        fullScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) Main.getEscenaPrincipal().getWindow();



                if (stage.isFullScreen()) {
                    //saliendo de full screen
                    stage.setFullScreen(false);
                    mediaVideo.setFitHeight(defaultVideoHeight);



                    if(blConexion.getModoReproduccion().equals(ModoReproduccion.Multiple)){
                        hboxPrincipal.getChildren().add(scrollPanePlayList);
                        vboxPrincipal.setPrefWidth(930);
                        mediaVideo.setFitWidth(930);
                    }else {
                        vboxPrincipal.setPrefWidth(1100);
                        mediaVideo.setFitWidth(1100);
                    }


                } else {
                    stage.setFullScreen(true);//entrada a full screen
                    double height = stage.getHeight() - 75;
                    System.out.println(stage.getWidth());
                    mediaVideo.setFitHeight(height);
                    mediaVideo.setFitWidth(stage.getWidth());
                    vboxPrincipal.setPrefWidth(stage.getWidth());

                    hboxPrincipal.getChildren().remove(scrollPanePlayList);

                }
            }
        });

        addReproductionList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mediaPlayer.stop();
                Main.cambiaPantalla("listasDeReproduccion");
            }
        });
    }

    public void reproducirVideo()  {
        Video videoActual = videos.get(posicionActual);
        final String nombreArchivo = videoActual.getArchivo();
        boolean tienePermiso = usuarioPermiso(blConexion.getUsuarioActual().getId(), videoActual);
        btnBorrar.setVisible(tienePermiso);
        btnModificar.setVisible(tienePermiso);

        File archivo = new File(nombreArchivo);
        Media video = new Media(archivo.toURI().toString());

        mediaPlayer = new MediaPlayer(video);
        mediaPlayer.setAutoPlay(true);
        mediaVideo.setMediaPlayer(mediaPlayer);
        mediaVideo.setPreserveRatio(false);
        mediaPlayer.setOnEndOfMedia(() ->
        {

            if (posicionActual == videos.size() - 1) {
                mediaPlayer.stop();

            } else {
                posicionActual++;
                reproducirVideo();
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                double valorTiempo =  newValue.toSeconds();
                if(valorTiempo > 60){
                    valorTiempo = valorTiempo/ 60;
                }
                progresBar.setValue(newValue.toSeconds());
                labelTime.setText(String.valueOf(valorTiempo));
            }
        });

        progresBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(Duration.seconds(progresBar.getValue()));
            }
        });

        progresBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(Duration.seconds(progresBar.getValue()));
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                Duration total = video.getDuration();
                progresBar.setMax(total.toSeconds());
            }
        });

        System.out.println("finalizando reproduccion del video");
    }

    public void handleButtonVolver(ActionEvent event) {
        mediaPlayer.stop();
        Main.cambiaPantalla("paginaPrincipal");
    }

    public void handleButtonReproducir(ActionEvent event) {
        mediaPlayer.play();
    }

    public void handleButtonPausar(ActionEvent event) {
        mediaPlayer.pause();
    }

    public void handleButtonParar(ActionEvent event) {
        mediaPlayer.stop();
    }

    public void handleButtonLento(ActionEvent event) {
        mediaPlayer.setRate(0.5);
    }

    public void handleButtonRapido(ActionEvent event) {
        mediaPlayer.setRate(2);
    }

    public void handleButtonModificar(ActionEvent event) {
        Video video = videos.get(posicionActual);
        blConexion.setVideoActual(video);
        mediaPlayer.stop();
        Main.cambiaPantalla("modificarVideo");
    }

    public void handleButtonBorrar(ActionEvent event) {
        Video video = videos.get(posicionActual);
        blConexion.borrarVideo(video.getId());
        mediaPlayer.stop();
        Main.cambiaPantalla("paginaPrincipal");
    }

    public void loadVideosPlayList(ModoReproduccion modoReproduccion) {
        videos.clear();
        if (modoReproduccion.equals(ModoReproduccion.Simple)) {
            videos.add(blConexion.getVideoActual());
            System.out.println(hboxPrincipal.getChildren().size());
            hboxPrincipal.getChildren().remove(scrollPanePlayList);
            mediaVideo.setFitWidth(1000);
            System.out.println(hboxPrincipal.getChildren().size());
            Stage stage = (Stage) Main.getEscenaPrincipal().getWindow();
            System.out.println(stage.getWidth());
            vboxPrincipal.setPrefWidth(1100);
        } else {
            videos.addAll(blConexion.getPlayListActual().getListaVideos());
            loadVideosPlayList();
            mediaVideo.setFitWidth(930);
            vboxPrincipal.setPrefWidth(930);
        }
        mediaVideo.setFitHeight(defaultVideoHeight);

    }

    @FXML
    public void handleDarleLike() {
        Calificacion calificacion = new Calificacion();
        int idVideoActual = blConexion.getVideoActual().getId();
        int idUsuarioActual = blConexion.getUsuarioActual().getId();

        if (btnLike.isSelected()) {
            calificacion.setIdVideo(idVideoActual);
            calificacion.setIdUsuario(idUsuarioActual);
            calificacion.setEstado(true);

            blConexion.guardarCalificacion(calificacion);

        } else {
            blConexion.borrarCalificacion(idVideoActual, idUsuarioActual);
            liked.setText("");
        }
        indicadorLike();
    }

    public void indicadorLike()  {
        int idVideoActual = videos.get(posicionActual).getId();
        int idUsuarioActual = blConexion.getUsuarioActual().getId();

        Calificacion calificacionActual = blConexion.obtenerCalificacionActual(idVideoActual, idUsuarioActual);

        if (calificacionActual != null) {
            if (calificacionActual.isEstado()) {
                liked.setText("liked");
                btnLike.setSelected(true);

            } else {
                liked.setText("");
            }
        }
    }

    public boolean usuarioPermiso(int idUsuarioActual, Video videoActual) {
        return videoActual.getIdUsuario() == idUsuarioActual;
    }

    public void handleButtonAbrirConexion()
    {
        if(isHosting){
            btnHost.setText("Host");
            blConexion.cerrarHost();
            isHosting = false;

        }else {
            btnHost.setText("Terminar");
            blConexion.iniciarHost(videos.get(posicionActual));
            isHosting = true;
        }

    }

    public void loadVideosPlayList() {
        ListaReproduccion actualPlayList = blConexion.getPlayListActual();
        ArrayList<Video>  videos = actualPlayList.getListaVideos();

        vBoxVideos.getChildren().clear();
        vBoxVideos.setSpacing(3);
        scrollPanePlayList.setVisible(true);


        for(int i = 0; i < videos.size(); i++){
            Image img;
            Video video = videos.get(i);


            if(video.getThumbnailVideo() != null && !video.getThumbnailVideo().equals("")){
                img = new Image("file:" + video.getThumbnailVideo());
            }else {
                URL urlImage = Main.class.getResource("img/defaultVideoImage.jpeg");
                img = new Image(urlImage.toString());
            }

            ImageView imageview = new ImageView(img);
            imageview.setFitHeight(Main.HEIGHT);
            imageview.setFitWidth(Main.WIDTH);
            vBoxVideos.getChildren().add(imageview);
            int finalI = i;
            imageview.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.stop();
                    posicionActual = finalI;
                    reproducirVideo();
                }
            });
        }
    }
}
