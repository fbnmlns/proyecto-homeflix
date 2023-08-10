package controlador;

import com.example.proyecto.Main;
import modelo.Video;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServerController {
    private Logger logger = Logger.getLogger(SocketServerController.class.getName());

    private AsynchronousServerSocketChannel serverSocket;

        public void iniciarHost(Video video) {
            try{
                this.serverSocket  =
                        AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(3400));
                logger.log(Level.INFO, "conexion del servidor iniciada");

                serverSocket.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

                    @Override
                    public void completed(AsynchronousSocketChannel result, Void attachment) {
                        serverSocket.accept(null, this);
                        File videoFile = new File(video.getArchivo());


                        try {
                            FileInputStream fileInputStream = new FileInputStream(videoFile.getAbsolutePath());
                            OutputStream outputStream = Channels.newOutputStream(result);
                            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                            String fileName = videoFile.getName();
                            byte[] fileNameBytes = fileName.getBytes();


                            byte[] fileContentBytes = new byte[(int) videoFile.length()];
                            fileInputStream.read(fileContentBytes);

                            dataOutputStream.writeInt(fileNameBytes.length);
                            dataOutputStream.write(fileNameBytes);

                            dataOutputStream.writeInt(fileContentBytes.length);
                            dataOutputStream.write(fileContentBytes);

                            logger.log(Level.INFO, "video envidado");
                            result.close();
                        } catch (IOException e) {
                            logger.log(Level.SEVERE, "Video no enviado");
                            logger.log(Level.SEVERE, e.toString());
                        }

                    }

                    @Override
                    public void failed(Throwable exc, Void attachment) {

                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void cerrarHost()  {
            try {
                serverSocket.close();
                logger.log(Level.SEVERE, "Conexión terminada");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "no se pudo cerrar la conexión");
                logger.log(Level.SEVERE, e.toString());
            }

        }



}
