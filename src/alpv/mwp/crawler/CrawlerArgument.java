package alpv.mwp.crawler;

import java.io.IOException;
import java.io.Serializable;

import alpv.mwp.Poison;

public final class CrawlerArgument implements Poison, Serializable {

	private static final long serialVersionUID = 2991145631611186860L;
	private final int _deep;
	private final HttpURL _httpUrl;
	private final boolean _isPoison;

	public CrawlerArgument(String url, int deep, boolean isPoison)
			throws IOException {
		this(new HttpURLImpl(url), deep, isPoison);
	}

	public CrawlerArgument(HttpURL url, int deep, boolean isPoison) {
		_httpUrl = url;
		_deep = deep;
		_isPoison = isPoison;
	}

	public HttpURL getHttpUrl() {
		return _httpUrl;
	}

	public int getDeep() {
		return _deep;
	}

	@Override
	public boolean isPoison() {
		return _isPoison;
	}

}
