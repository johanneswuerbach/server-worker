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

	private InputStream _content;
	List<URL> _urls;
	List<String> _mailTos;

	public URLParser(InputStream content) {
		_content = content;
		_urls = new ArrayList<URL>();
		_mailTos = new ArrayList<String>();
	}

	public static void main(String[] args) throws IOException,
			BadLocationException {
		HttpConnection connection = new HttpConnectionImpl(new HttpURLImpl(
				"http://de.selfhtml.org/html/verweise/anzeige/a_href_mailto.htm"));
		URLParser p = new URLParser(connection.getContent());
		p.parse();
	}

	public void parse() throws IOException, BadLocationException {
		BufferedReader contentReader = new BufferedReader(
				new InputStreamReader(_content));
		parse(contentReader);
	}

	private void parse(BufferedReader reader) throws IOException {
		Pattern p = Pattern.compile("href=(\"|')((http:[^\"']*)|(mailto:[^\"']*))(\"|')");
		String line;
		Matcher m;
		String url;

		while ((line = reader.readLine()) != null) {
			m = p.matcher(line);
			while (m.find()) {
				url = m.group(2);
				if (url.startsWith("http")) {
					System.out.println("http url detected: " + url);
					_urls.add(new URL(url));
				} else if (url.startsWith("mailto")) {
					System.out.println("mailto detected: " + url);
					_mailTos.add(url);
				}else{
					System.out.println("bad url detected: " + url);
				}
			}
		}
	}
}
