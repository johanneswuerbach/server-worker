package alpv.mwp.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class HttpConnectionImpl implements HttpConnection {

	private final int _responseCode;
	private final String[] _headerKeys;
	private final String[] _headerValues;
	private final InputStream _content;

	public HttpConnectionImpl(HttpURL httpURL) throws UnknownHostException, IOException {
		Socket socket = new Socket(httpURL.getHost(), httpURL.getPort());
		
		System.out.println("Connect to: " + httpURL.getHost() + ":" + httpURL.getPort());
		
		// Send request
		String protocol = "HTTP/1.1";
		String request = "GET " + httpURL.getPath() + " " + protocol + "\r\n" + "Host: "
				+ httpURL.getHost()  + "\r\n" + "\r\n";
		
		OutputStream outputStream = socket.getOutputStream();
		_content = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				_content));
		
		outputStream.write(request.getBytes());
		socket.shutdownOutput();
		
		// Parse response
		String line;
		
		// Parse status code
		line = reader.readLine();
		if (line != null) {
			String[] parts = line.split(" ");
			if(line.length() >= 2) {
				_responseCode = Integer.parseInt(parts[1]);
			}
			else {
				throw new IOException("Invalid response");
			}
		}
		else {
			throw new IOException("Invalid response");
		}
		
		// Parse header
		boolean finished = false;
		ArrayList<String> headerKeys = new ArrayList<String>();
		ArrayList<String> headerValues = new ArrayList<String>();
		while ((reader.readLine()) != null && !finished) {
			if(line == "") {
				finished = true;
			}
			else {
				String[] parts = line.split(": ", 2);
				if(parts.length == 2) {
					headerKeys.add(parts[0]);
					headerValues.add(parts[1]);
				}
				else {
					throw new IOException("Invalid response");
				}
			}
			System.out.println(line);
		}

		_headerKeys = (String[]) headerKeys.toArray();
		_headerValues = (String[]) headerValues.toArray();
	}

	@Override
	public int getResponseCode() {
		return _responseCode;
	}

	@Override
	public String getHeaderFieldKey(int field) {
		return _headerKeys[field];
	}

	@Override
	public String getHeaderField(int field) {
		return _headerValues[field];
	}

	@Override
	public InputStream getContent() throws IOException {
		return _content;
	}

}
