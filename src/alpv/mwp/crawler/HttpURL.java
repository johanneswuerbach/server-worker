package alpv.mwp.crawler;
import java.io.IOException;
import java.net.UnknownHostException;


/**
 * Beschreibung einer URL um eine HTTP-Verbindung aufzubauen. Eine URL besteht
 * immer aus Host, Pfad und Port. Beispielsweise fuer
 * 
 * <p>"http://www.test.de/themen/" aus
 * </br>
 * Host ("www.test.de"), Pfad ("themen/"), Port ("80").</p>
 */
public interface HttpURL {
	public HttpConnection openConnection() throws UnknownHostException, IOException;
	/**
	 * Der Host einer HTTP-URL.
	 * 
	 * <p>"http://www.test.de/themen/" -> "www.test.de"</p>
	 */
	public String getHost();
	/**
	 * Der Pfad einer HTTP-URL.
	 * 
	 * <p>"http://www.test.de/themen/" -> "themen/"</p>
	 */
	public String getPath();
	/**
	 * Der Port einer HTTP-URL.
	 * 
	 * <p>"http://www.test.de/themen/" -> "80"</br>
	 * "http://www.test.de:4000/themen/" -> "4000"</p>
	 */
	public int getPort();
}
