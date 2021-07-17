package gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Loader {

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
