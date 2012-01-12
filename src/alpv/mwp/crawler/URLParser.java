package alpv.mwp.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLParser {

	private InputStream _content;
	List<HttpURL> _urls;
	List<String> _mailTos;
	HttpURL _url;

	public URLParser(HttpURL url) {

		_urls = new ArrayList<HttpURL>();
		_mailTos = new ArrayList<String>();

		try {
			_url = url;
			_content = _url.openConnection().getContent();
			if (_content.available() > 0) {
				BufferedReader contentReader = new BufferedReader(
						new InputStreamReader(_content));
				parse(contentReader);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		HttpURL url = new HttpURLImpl(
				"http://www.fu-berlin.de/universitaet/was-uns-auszeichnet/nachwuchs/bsrt.html");
		new URLParser(url);
	}

	private void parse(BufferedReader reader) throws IOException {
		Pattern p = Pattern
				.compile("href=(\"|')((http:[^\"']*)|(mailto:[^\"']*)|([^\"^:']*))(\"|')");
		String line;
		Matcher m;
		String url;

		while ((line = reader.readLine()) != null) {
//			System.out.println(line);
			m = p.matcher(line);
			while (m.find()) {
				url = m.group(2);
				if (url.startsWith("http")) {
//					System.out.println("http url detected: " + url);
					_urls.add(new HttpURLImpl(url));
				} else if (url.startsWith("mailto")) {
//					System.out.println("mailto detected: " + url);
					_mailTos.add(url);
				} else if (url.startsWith("#")) {
					System.out.println("bad url detected: " + url);
				} else if (url.startsWith("/")) {
//					System.out.println("http://" + _url.getHost() + url);
					_urls.add(new HttpURLImpl("http://" + _url.getHost() + url));
				} else {
//					System.out.println("http://" + _url.getHost() + _url.getPath() + url);
					_urls.add(new HttpURLImpl("http://" + _url.getHost()
							+ _url.getPath() + url));
				}
			}
		}
	}

	public List<HttpURL> get_urls() {
		return _urls;
	}

	public List<String> get_mailTos() {
		return _mailTos;
	}

}
