package alpv.mwp.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.BadLocationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLParser {

	// private HttpURL _url;

	private URL _url;
	List<URL> _urls;
	List<String> _mailTos;

	public URLParser(URL url) {
		_url = url;
		_urls = new ArrayList<URL>();
		_mailTos = new ArrayList<String>();
	}

	public static void main(String[] args) throws IOException,
			BadLocationException {
		URLParser p = new URLParser(new URL(
				"http://archive.ncsa.illinois.edu/mosaic.html"));
		p.parse();
	}

	public void parse() throws IOException, BadLocationException {
		System.out.println("start parsing file " + _url.getHost()
				+ _url.getPath());
		// InputStream contentStream = _url.openConnection().getContent();
		InputStream contentStream = _url.openStream();
		BufferedReader contentReader = new BufferedReader(
				new InputStreamReader(contentStream));
		parse(contentReader);
	}

	private void parse(BufferedReader reader) throws IOException {
		Pattern p = Pattern.compile("href=(\"|')(http[^\"']*)(\"|')");
		String line;
		Matcher m;
		String url;

		while ((line = reader.readLine()) != null) {
			m = p.matcher(line);
			while (m.find()) {
				url = m.group(2);
				System.out.println(url);
				if (url.startsWith("http")) {
					_urls.add(new URL(url));
				} else if (url.startsWith("mailto")) {
					_mailTos.add(url);
				}

			}
		}
	}
}