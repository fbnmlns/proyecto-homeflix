package vista;

import com.example.proyecto.Main;
import controlador.BL;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import modelo.ListaReproduccion;
import modelo.ModoReproduccion;
import modelo.Usuario;
import modelo.Video;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaginaPrincipalVista {

    @FXML
    public Label labelUserName;
    @FXML
    public Circle circlePhoto;
    @FXML
    public TextField txtBuscar;
    @FXML
    public HBox hBoxVideos;
    @FXML
    public ImageView trash;
    @FXML
    public ImageView search;
    @FXML
    public ImageView exit;
    @FXML
    public ImageView add;
    @FXML
    public HBox idHboxLista;
    @FXML
    public Label resultadoBusqueda;
    private BL blConexion = BL.getInstanciaBl();
    private Usuario usuarioActual = blConexion.getUsuarioActual();

    public void initialize() {
        Main.userInformation(labelUserName, circlePhoto);
        hBoxVideos.setSpacing(5);
        loadDataVideos(usuarioActual.getId());
        idHboxLista.setSpacing(5);
        loadDataPlayList(usuarioActual.getId());

        trash.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hBoxVideos.getChildren().clear();
                txtBuscar.setText("");
                loadDataVideos(usuarioActual.getId());
            }
        });

        search.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hBoxVideos.getChildren().clear();
                if (txtBuscar.getText().trim().isEmpty()) {
                    loadDataVideos(usuarioActual.getId());
                } else {
                    searchLoadData();
                }
            }
        });

        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.cambiaPantalla("login");
            }
        });

        add.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.cambiaPantalla("registrarVideo");
            }
        });

        circlePhoto.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.cambiaPantalla("modificarUsuario");
            }
        });
    }


    public void loadDataVideos(int userId) {
        resultadoBusqueda.setText("Mis Videos");
        ArrayList<Video> videos = blConexion.listarVideos(userId);
        for (int i = 0; i < videos.size(); i++) {
            Image img;
            Video video = videos.get(i);

            if (video.getThumbnailVideo() != null && !video.getThumbnailVideo().equals("")) {
                img = new Image("file:" + video.getThumbnailVideo());
            } else {
                URL urlImage2 = Main.class.getResource("img/defaultVideoImage.jpeg");
                img = new Image(urlImage2.toString());
            }

            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(Main.HEIGHT);
            imageView.setFitWidth(Main.WIDTH);
            imageView.setPickOnBounds(true);
            Label label = new Label();
            VBox vBox = new VBox();
            label.setText(video.getNombre());
            vBox.getChildren().addAll(imageView, label);
            playingVideo(imageView, video);
            hBoxVideos.getChildren().add(vBox);
        }
    }

    public void loadDataPlayList(int userId) {
        ArrayList<ListaReproduccion> playList = blConexion.listarReproductionList(userId);
        for (int i = 0; i < playList.size(); i++) {
            Image imgPlayList;
            ListaReproduccion list = playList.get(i);

            if (list.getArchivoImagen() != null && !list.getArchivoImagen().equals("")) {
                imgPlayList = new Image("file:" + list.getArchivoImagen());
            } else {
                URL urlImage = Main.class.getResource("img/defaultReproductionList.jpeg");
                imgPlayList = new Image(urlImage.toString());
            }

            ImageView imageView = new ImageView(imgPlayList);
            imageView.setFitHeight(Main.HEIGHT);
            imageView.setFitWidth(Main.WIDTH);
            imageView.setPickOnBounds(true);
            Label label = new Label();
            VBox vBox = new VBox();
            label.setText(list.getNombre());
            vBox.getChildren().addAll(imageView, label);
            seeVideos(imageView, list);
            idHboxLista.getChildren().add(vBox);
        }

    }

    public void searchLoadData()  {
        ArrayList<Video> videos = blConexion.buscarVideo(txtBuscar.getText());
        hBoxVideos.getChildren().clear();
        String texto = videos.size() == 0 ? "No se encontró ningún resultado" : "Resultados";
        resultadoBusqueda.setText(texto);

        for (int i = 0; i < videos.size(); i++) {
            Image img;
            Video video = videos.get(i);

            if (video.getThumbnailVideo() != null && !videos.get(i).getThumbnailVideo().equals("")) {
                img = new Image("file:" + videos.get(i).getThumbnailVideo());

            } else {
                URL urlImage2 = Main.class.getResource("img/defaultVideoImage.jpeg");
                img = new Image(urlImage2.toString());
            }

            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(Main.HEIGHT);
            imageView.setFitWidth(Main.WIDTH);
            imageView.setPickOnBounds(true);
            Label label = new Label();
            VBox vBox = new VBox();
            label.setText(video.getNombre());
            vBox.getChildren().addAll(imageView, label);
            playingVideo(imageView, video);
            hBoxVideos.getChildren().add(vBox);
        }
    }

    public void playingVideo(ImageView imageView, Video video) {
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                blConexion.setVideoActual(video);
                blConexion.setModoReproduccion(ModoReproduccion.Simple);
                System.out.println(blConexion.getVideoActual().getId());
                playVideo();
            }
        });
    }

    public void playVideo() {
        Main.cambiaPantalla("reproducirVideo");
    }

    public void seeVideos(ImageView imageView, ListaReproduccion actualPlayList) {
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                blConexion.setPlayListActual(actualPlayList);
                Main.cambiaPantalla("playList");
            }
        });
    }
}
