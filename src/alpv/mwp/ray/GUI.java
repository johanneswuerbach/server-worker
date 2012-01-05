package alpv.mwp.ray;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * a display program for pix files.
 * <p>
 * usage: java org.autochthonous.graphics.rt.tools.Disp file
 * <p>
 * where file is a ".pix" file.
 */
public class GUI {
	
	private static class DisplayFrame extends JFrame {

		private static final long serialVersionUID = 4822111026232940929L;

		DisplayFrame(ImagePanel image) {
			super("Disp: " + image.getImageName());

			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});

			this.getContentPane().add(image);
		}
	}

	private static class ImagePanel extends JPanel {

		private static final long serialVersionUID = -1569529666226454608L;
		MemoryImageSource source;
		Image image;
		String imageName;
		Dimension dimension;

		ImagePanel(String imageName, int width, int height, int[] pix) {
			this.imageName = imageName;

			source = new MemoryImageSource(width, height, pix, 0, width);
			image = createImage(source);

			dimension = new Dimension(width, height);
		}

		public String getImageName() {
			return imageName;
		}

		public void paint(Graphics g) {
			g.drawImage(image, 0, 0, this);
		}

		public Dimension getPreferredSize() {
			return dimension;
		}
	}

	public static void display(String file) throws IOException {
		
		BufferedInputStream in;
		String imageName = "";

		try {
			in = new BufferedInputStream(new FileInputStream(file));
			imageName = file;
		} catch (FileNotFoundException e) {
			System.err.println("can't open file " + file);
			System.exit(1);
			return;
		}

		String type = readLine(in);
		String dimensions = readLine(in);
		int[] pix;
		int width, height;

		if (type.equals("RGB")) {
			StringTokenizer st = new StringTokenizer(dimensions, " ");

			width = Integer.parseInt(st.nextToken());
			height = Integer.parseInt(st.nextToken());

			pix = new int[width * height];

			for (int i = 0; i != pix.length; i++) {
				pix[i] = (255 << 24) | (in.read() << 16) | (in.read() << 8)
						| (in.read());
			}
		} else {
			throw new IOException("Unknown image type");
		}

		ImagePanel image = new ImagePanel(imageName, width, height, pix);
		DisplayFrame display = new DisplayFrame(image);

		display.pack();
		display.setVisible(true);
	}

	static final byte LF = 0x0A;
	static final byte CR = 0x0D;

	static public String readLine(BufferedInputStream in) throws IOException {
		String s = new String();
		int b;

		do {
			b = in.read();
			if (b != LF && b != CR)
				s = s + (char) b;
			if (b == CR) {
				in.mark(1); // this will always succeed for buffered input
							// streams
				b = in.read();
				if (b != LF) {
					// we've found a lone CR, reset buffer to before char of
					// next line
					in.reset();
				}
			}
		} while (b != LF && b != CR);

		return s;
	}
}