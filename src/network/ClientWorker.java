package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import model.ImageConverter;

public class ClientWorker implements Runnable {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;

	public ClientWorker(Socket socket) {
		try {
			this.socket = socket;
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
    
	@Override
	public void run() {
        while (!socket.isClosed()) {
            try {
                analizeRequest();
			} catch (IOException e) {
                System.out.println(e.getMessage());
				close();
			}
		}
	}

	public void close() {
		try {
			socket.close();
			System.out.println("Cliente desconectado");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void analizeRequest() throws IOException {
        String request = in.readUTF();
        System.out.println("el request:"+request);
        switch (request){
            case "/add-image":
            	String imageName = in.readUTF();
            	System.out.println("name image:"+imageName);
                int sizeBytesImg = in.readInt();
                byte[] imgBytes = new byte[sizeBytesImg];
                System.out.println(sizeBytesImg);
                int result = in.read(imgBytes);
                ImageConverter.toImage(imgBytes, imageName);
                System.out.println("el result..."+ result);
                break;
            case "/get-images":
            	File[] files = new File("data/").listFiles(); 
            	int totalFiles = files.length;
            	out.writeUTF("/get-images");
            	out.writeInt(totalFiles);
            	for (int i = 0; i < files.length; i++) {
					byte[] bytesImage = ImageConverter.toBinary("data/"+files[0].getName()).get();
					out.writeInt(bytesImage.length);
					out.writeUTF(files[i].getName());
					out.write(bytesImage);
				}
            	System.out.println("files:"+files[1].getName());
            	break;
        }
	}

}