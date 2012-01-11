package alpv.mwp.crawler;

import java.io.IOException;
import java.net.UnknownHostException;

public class HttpURLImpl implements HttpURL {
	
	private static final String VALID_URL = "^(?:(http):\\/\\/)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))(?::\\d{2,5})?(?:\\/[^\\s]*)?$";
	
	private final String _host;
	private final int _port;
	private final String _path;
	
	public HttpURLImpl(String url) {
		if(!url.matches(VALID_URL)) {
			throw new IllegalArgumentException();
		}
		
		// Remove http://
		url = url.substring(7);
		// Split host:port/path
		String[] parts = url.split("/", 2);
		String hostAndPort = parts[0];
		
		// Path
		if(parts.length == 2) {
			_path = "/" + parts[1];
		}
		else {
			_path = "/";
		}
		
		// Host and port
		if(!hostAndPort.contains(":")) {
			_host = hostAndPort;
			_port = 80;
		}
		else {
			// Last value is port
			parts = hostAndPort.split(":");
			_host = parts[0];
			_port = Integer.parseInt(parts[1]);
		}
	}

	@Override
	public HttpConnection openConnection() throws UnknownHostException,
			IOException {
		// TODO Auto-generated method stub
		return new HttpConnectionImpl(this);
	}

	public String getHost() {
		return _host;
	}

	public String getPath() {
		return _path;
	}

	public int getPort() {
		return _port;
	}

}
