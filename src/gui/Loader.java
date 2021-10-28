package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
* Loads assets files by a path given.
*
* @author Fabricio Tomás <a href="https://github.com/Fabricio-Tomas">github-profile</a>
*/
public class Loader {

	/**
	* Loads a png file by a path given.
	*
	* @param path The file path.
	* @return The png file: image, if it exists or null if the file does not exist.
	*/
	public static BufferedImage loadPng(String path) {

		if (!path.endsWith(".png"))
			path += ".png";

		try {
			return ImageIO.read(Loader.class.getResource(path));
		} catch (IOException e) {
			System.out.println("Error al cargar imagen: " + path);
		}

		return null;

	}

}
