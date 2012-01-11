package alpv.mwp.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Http {

	public static void main(String[] args) throws IOException {
		String host = "www.google.de";
		Socket s = new Socket(host, 80);
		String path = "/";
		String protocol = "HTTP/1.1";
		String request = "GET " + path + " " + protocol + "\r\n" + "Host: "
				+ host;
		System.out.println(request);
		s.getOutputStream().write(request.getBytes());
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		s.close();
	}
}
