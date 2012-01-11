package alpv.mwp.crawler;
import java.io.IOException;
import java.io.InputStream;

public interface HttpConnection {
	/** 
	 * Status-Code der HTTP-Antwort, siehe <a href="http://de.wikipedia.org/wiki/HTTP-Statuscode">HTTP-Statuscodes</a> 
	 */
	public int getResponseCode();
	
	/** 
	 * Der Name(Key) des Eintrags des angegebenen Header-Feldes, das erste Header-Feld
	 * hat die Nummer 0.
	 * 
	 * <p>Ein HTTP-Header ist unterteilt durch Zeilenumbrueche ("\n").
	 * Jeder Zeilen-Umbruch markiert das Ende eines Header-Feldes. </p>
	 * <p>Ein Header Eintrag ist aufgeteilt in <i>Name(Key)</i> und <i>Inhalt</i> des 
	 * Eintrags und ist immer in der Form "<i>Name</i>: <i>Inhalt</i>" gegeben.
	 * 
	 * <a href="http://en.wikipedia.org/wiki/Http_protocol#Example_session">Beispiel</a></p>
	 * 
	 * <p>
	 * Ein doppelter Zeilenumbruch ("\n\n") markiert das Ende des Headers und damit gleichzeitig
	 * der Anfang des Inhaltes (Content).
	 * </p>
	 * 
	 * @param field Nummer des Header-Feldes, beginned bei 0.
	 */
	public String getHeaderFieldKey(int field);
	
	/** 
	 * Der Inhalt des Eintrags des angegebenen Header-Feldes, das erste Header-Feld
	 * hat die Nummer 0.
	 * 
	 * <p>Ein HTTP-Header ist unterteilt durch Zeilenumbrueche ("\n").
	 * Jeder Zeilen-Umbruch markiert das Ende eines Header-Feldes. </p>
	 * <p>Ein Header Eintrag ist aufgeteilt in <i>Name(Key)</i> und <i>Inhalt</i> des 
	 * Eintrags und ist immer in der Form "<i>Name</i>: <i>Inhalt</i>" gegeben.
	 * 
	 * <a href="http://en.wikipedia.org/wiki/Http_protocol#Example_session">Beispiel</a></p>
	 * 
	 * <p>
	 * Ein doppelter Zeilenumbruch ("\n\n") markiert das Ende des Headers und damit gleichzeitig
	 * der Anfang des Inhaltes (Content).
	 * </p>
	 * 
	 * @param field Nummer des Header-Feldes, beginned bei 0.
	 */
	public String getHeaderField(int field);
	
	/**
	 * Stream des Inhaltes der HTTP-Antwort. Dieser Stream streamt alle Daten
	 * nach dem Header bis zum Ende der HTTP-Antwort.
	 * 
	 * <p>Siehe {@link HttpConnection#getHeaderField(int)} wie das Ende der Headers, und damit gleichzeitig
	 * der Anfang des Inhaltes (Content), markiert ist</p>
	 * @throws IOException 
	 */
	public InputStream getContent() throws IOException;
}
