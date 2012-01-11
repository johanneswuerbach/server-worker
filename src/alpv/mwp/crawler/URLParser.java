package alpv.mwp.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.BadLocationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLParser {

	private InputStream _content;
	List<HttpURL> _urls;
	List<String> _mailTos;
	HttpURL _url;

	public URLParser(InputStream content, HttpURL url) {
		_content = content;
		_urls = new ArrayList<HttpURL>();
		_mailTos = new ArrayList<String>();
		_url = url;
	}

	public static void main(String[] args) throws IOException,
			BadLocationException {
		HttpURL url = 
		new HttpURLImpl("http://www.fu-berlin.de/universitaet/was-uns-auszeichnet/nachwuchs/bsrt.html");
		HttpConnection connection = new HttpConnectionImpl(url);
		URLParser p = new URLParser(connection.getContent(), url);
		p.parse();
	}

	public void parse() throws IOException, BadLocationException {
		if (_content.available() > 0) {
			BufferedReader contentReader = new BufferedReader(
					new InputStreamReader(_content));
			parse(contentReader);
		}
	}

	private void parse(BufferedReader reader) throws IOException {
		Pattern p = Pattern.compile("href=(\"|')((http:[^\"']*)|(mailto:[^\"']*)|([^\"^:']*))(\"|')");
		String line;
		Matcher m;
		String url;

		while ((line = reader.readLine()) != null) {
			m = p.matcher(line);
			while (m.find()) {
				url = m.group(2);
				if (url.startsWith("http")) {
					System.out.println("http url detected: " + url);
					_urls.add(new HttpURLImpl(url));
				} else if (url.startsWith("mailto")) {
					System.out.println("mailto detected: " + url);
					_mailTos.add(url);
				}else{
					if(url.startsWith("#")) {
						System.out.println("bad url detected: " + url);
					}
					else {
						System.out.println("http url detected: " + url);
						if (url.startsWith("/")) {
							System.out.println("http://" + _url.getHost() + url);
							_urls.add(new HttpURLImpl("http://" + _url.getHost() + url));
						}
						else {
							System.out.println("http://" + _url.getHost() + _url.getPath() + url);
							_urls.add(new HttpURLImpl("http://" + _url.getHost() + _url.getPath() + url));
						}
						
					}
					
					
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
