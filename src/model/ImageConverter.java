package model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.imageio.ImageIO;

public class ImageConverter {
	public static Optional<byte[]> toBinary(String path) {
	    int len = path.split("\\.").length;
	    String ext = path.split("\\.")[len - 1];
	    try {
	        BufferedImage img = ImageIO.read(new File(path));
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(img, ext, baos);
	        return Optional.of(baos.toByteArray());
	    } catch(IOException e) {
	        return Optional.empty();
	    }
	}
	
	public static void toImage(byte[] imageBytes, String imageName ) {

		try (InputStream inputStream = new ByteArrayInputStream(imageBytes)) {
		    BufferedImage imagen = ImageIO.read(inputStream);
		    File archivoImagen = new File("data/"+imageName);
			System.out.println("error posble"+imageName);
		    ImageIO.write(imagen, "jpg", archivoImagen);
		} catch (IOException e) {
		    
		}
	}
}
