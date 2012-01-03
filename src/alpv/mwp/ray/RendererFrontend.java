package alpv.mwp.ray;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class RendererFrontend {
	private static String[] SCENES = new String[] { "crank.rml", "waves.rml",
			"textures.rml" };
	private static final int DEFAULT_SCENE = 0; // 0 - crank.rml; 1 - waves.rml
	private static final String LIB_PATH = "xjrt.jar";

	// for reflection magic
	private Class<?> myRendererClass = null;
	private Object myRenderer;
	private Object myRendererRGBVal;

	private final String mySceneRef;
	private final OutputStream myOutputStream;

	/**
	 * Creates a renderer frontend with the standard settings.
	 * 
	 * @param outs
	 *            the output stream for the resulting data
	 */
	public RendererFrontend(OutputStream outs) {
		this(outs, DEFAULT_SCENE);
	}

	/**
	 * Creates a renderer frontend for given scene.
	 * 
	 * @param outs
	 *            the output stream for the resulting data
	 * @param idx
	 *            the index of the scene (between 0 and 1).
	 */
	public RendererFrontend(OutputStream outs, int idx) {
		this(outs, SCENES[idx]);
	}

	/**
	 * Creates a renderer frontend for given scene.
	 * 
	 * @param outs
	 *            the output stream for the resulting data
	 * @param scene
	 *            a path to an rml-file
	 */
	public RendererFrontend(OutputStream outs, String scene) {
		// load the stream
		mySceneRef = scene;
		Reader sceneR = null;
		try {
			URL sceneURL = RendererFrontend.class.getResource(mySceneRef);
			if (sceneURL == null) {
				throw new RuntimeException("Cannot open " + mySceneRef);
			}
			sceneR = new InputStreamReader(sceneURL.openStream());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Cannot access " + mySceneRef);
		}

		// create new class loader, loads classes, construct renderer
		doReflectionMagic(sceneR);

		// set the output stream
		myOutputStream = outs;
	}

	/**
	 * Sets the strip of the image which is to be generated. (end - start)
	 * should be at least 25 pixel, better 40 or more
	 * 
	 * @param start
	 *            the start line
	 * @param end
	 *            the end line
	 */
	public void setWindowStrip(int start, int end) {
		try {
			Method meth = myRendererClass.getMethod("setWindow", new Class[] {
					int.class, int.class, int.class, int.class });
			meth.invoke(myRenderer, new Object[] { new Integer(0),
					new Integer(start), new Integer(getImageWidth()),
					new Integer(end - start) });
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.toString());
		}
	}

	/**
	 * Renders the image.
	 */
	public void render() {
		try {
			Method meth = myRendererClass.getMethod("doRender", new Class[] {
					int.class, OutputStream.class });
			meth.invoke(myRenderer, new Object[] { myRendererRGBVal,
					myOutputStream });
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.toString());
		}
	}

	/**
	 * Returns the width of the image.
	 */
	public int getImageWidth() {
		int res = -1;
		try {
			Method meth = myRendererClass.getMethod("getImageWidth",
					new Class[] {});
			Integer resI = (Integer) meth.invoke(myRenderer, new Object[] {});
			res = resI.intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.toString());
		}
		return res;
	}

	/**
	 * Returns the height of the image.
	 */
	public int getImageHeight() {
		int res = -1;
		try {
			Method meth = myRendererClass.getMethod("getImageHeight",
					new Class[] {});
			Integer resI = (Integer) meth.invoke(myRenderer, new Object[] {});
			res = resI.intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.toString());
		}
		return res;
	}

	/**
	 * Creates a renderer for the given scene from a new classloader.
	 */
	private void doReflectionMagic(Reader sceneR) {
		// create a new classloader for the renderer
		// this is necessary, because the library is not thread-safe
		Object sceneRR = null;
		try {
			// create a new Class loader
			URL libURL = RendererFrontend.class.getResource(LIB_PATH);
			URLClassLoader ucl = new URLClassLoader(new URL[] { libURL });
			// construct a scenario reader
			Class<?> screaderClass = ucl
					.loadClass("org.bouncycastle.graphics.rt.tools.io.RMLReader");
			Constructor<?> screaderCon = screaderClass
					.getConstructor(new Class[] { Reader.class });
			sceneRR = screaderCon.newInstance(new Object[] { sceneR });

			// read the renderer
			Method getRendererM = screaderClass.getMethod("readObject",
					new Class[] {});
			myRenderer = getRendererM.invoke(sceneRR, new Object[] {});

			// get the renderer class
			myRendererClass = myRenderer.getClass();

			// get the RGB value
			Field rgbF = myRendererClass.getField("RGB");
			myRendererRGBVal = rgbF.get(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Cannot load the renderer class.");
		}
	}
}
