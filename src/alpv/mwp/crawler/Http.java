package alpv.mwp.crawler;

import java.io.IOException;

public class Http {

	public static void main(String[] args) throws IOException {
		
		new HttpConnectionImpl(new HttpURLImpl("http://www.google.de/"));
	}
}
