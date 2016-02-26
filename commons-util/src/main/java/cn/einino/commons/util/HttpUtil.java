package cn.einino.commons.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.einino.commons.util.exception.HttpException;
import cn.einino.commons.util.module.http.NameValueParam;
import cn.einino.commons.util.module.http.RouteInfo;

public class HttpUtil {

	private final boolean pooling;
	private PoolingHttpClientConnectionManager cm;
	private int maxTotal = 20;
	private int perRoute = 2;
	private int connetionTimeout = 5000;
	private int soTimeout = 5000;
	private boolean https = false;
	private List<RouteInfo> routeInfos;
	private String userAgent = "Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b Safari/531.21.10 MicroMessenger";
	private CloseableHttpClient httpClient;
	private RequestConfig config;

	public HttpUtil() {
		this(false);
	}

	public HttpUtil(boolean pooling) {
		this.pooling = pooling;
	}

	public final void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public final void setPerRoute(int perRoute) {
		this.perRoute = perRoute;
	}

	public final void setConnetionTimeout(int connetionTimeout) {
		this.connetionTimeout = connetionTimeout;
	}

	public final void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public final void setRouteInfos(List<RouteInfo> routeInfos) {
		this.routeInfos = routeInfos;
	}

	public final void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public final void setHttps(boolean https) {
		this.https = https;
	}

	public void initHttpClient() {
		HttpClientBuilder builder = HttpClients.custom();
		if (pooling) {
			cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(maxTotal);
			cm.setDefaultMaxPerRoute(perRoute);
			if (routeInfos != null && routeInfos.size() > 0) {
				HttpHost host;
				HttpRoute route;
				for (RouteInfo info : routeInfos) {
					host = new HttpHost(info.getHost(), info.getPort());
					route = new HttpRoute(host);
					cm.setMaxPerRoute(route, info.getTotal());
				}
			}
			builder.setConnectionManager(cm);
		}
		if (https) {
			SSLConnectionSocketFactory sslsf = SSLConnectionSocketFactory
					.getSocketFactory();
			builder.setSSLSocketFactory(sslsf);
		}
		httpClient = builder.build();
		config = RequestConfig.custom().setConnectTimeout(connetionTimeout)
				.setConnectionRequestTimeout(soTimeout).build();
	}

	public void destroyHttpClient() {
		if (httpClient != null) {
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}
	}

	public String getUTF8String(final String uri, Map<String, Object> params)
			throws HttpException {
		return getString(uri, params, "UTF-8");
	}

	public byte[] getBinary(final String uri, Map<String, Object> params)
			throws HttpException {
		byte[] datas;
		StringBuilder builder = new StringBuilder(uri).append("?");
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				builder.append(entry.getKey()).append("=")
						.append(entry.getValue()).append("&");
			}
		}
		String url = builder.substring(0, builder.length() - 1);
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent", userAgent);
		httpGet.setConfig(config);
		try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				String emsg = new StringBuilder("Http get request uri:[")
						.append(uri).append("] response error").toString();
				throw new HttpException(emsg);
			} else {
				datas = EntityUtils.toByteArray(response.getEntity());
			}
		} catch (IOException e) {
			String emsg = new StringBuilder("Http get request uri:[")
					.append(uri).append("] request error").toString();
			throw new HttpException(emsg, e);
		}
		return datas;
	}

	public String getString(final String uri, Map<String, Object> params,
			final String charset) throws HttpException {
		String resp = null;
		StringBuilder builder = new StringBuilder(uri).append("?");
		if (params != null && params.size() > 0) {
			for (Entry<String, Object> entry : params.entrySet()) {
				builder.append(entry.getKey()).append("=")
						.append(entry.getValue()).append("&");
			}
		}
		String url = builder.substring(0, builder.length() - 1);
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent", userAgent);
		httpGet.setConfig(config);
		try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				String emsg = new StringBuilder("Http get request uri:[")
						.append(uri).append("] response error").toString();
				throw new HttpException(emsg);
			} else {
				resp = EntityUtils.toString(response.getEntity(), charset);
			}
		} catch (IOException e) {
			String emsg = new StringBuilder("Http get request uri:[")
					.append(uri).append("] request error").toString();
			throw new HttpException(emsg, e);
		}
		return resp;
	}

	public byte[] postUTF8Binary(final String uri,
			final Map<String, Object> params) throws HttpException {
		return postBinary(uri, params, "UTF-8");
	}

	public byte[] postBinary(final String uri,
			final Map<String, Object> params, final String charset)
			throws HttpException {
		byte[] datas;
		HttpPost httpPost = new HttpPost(uri);
		httpPost.addHeader("User-Agent", userAgent);
		httpPost.setConfig(config);
		if (params != null && params.size() > 0) {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(
					params.size());
			BasicNameValuePair pair;
			for (Entry<String, Object> entry : params.entrySet()) {
				pair = new BasicNameValuePair(entry.getKey(), entry.getValue()
						.toString());
				list.add(pair);
			}
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(list, charset));
			} catch (UnsupportedEncodingException ex) {
				String emsg = new StringBuilder("Encod form entity charset:[")
						.append(charset).append("] error").toString();
				throw new HttpException(emsg, ex);
			}
		}
		try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				String emsg = new StringBuilder("Http post request uri:[")
						.append(uri).append("] response error").toString();
				throw new HttpException(emsg);
			} else {
				datas = EntityUtils.toByteArray(response.getEntity());
			}
		} catch (IOException ex) {
			String emsg = new StringBuilder("Http post request uri:[")
					.append(uri).append("] request error").toString();
			throw new HttpException(emsg, ex);
		}
		return datas;
	}

	public String postUTF8String(final String uri, Map<String, Object> params)
			throws HttpException {
		return postString(uri, params, "UTF-8");
	}

	public String postString(final String uri,
			final Map<String, Object> params, final String charset)
			throws HttpException {
		String resp;
		HttpPost httpPost = new HttpPost(uri);
		httpPost.addHeader("User-Agent", userAgent);
		httpPost.setConfig(config);
		if (params != null && params.size() > 0) {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(
					params.size());
			BasicNameValuePair pair;
			for (Entry<String, Object> entry : params.entrySet()) {
				pair = new BasicNameValuePair(entry.getKey(), entry.getValue()
						.toString());
				list.add(pair);
			}
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(list, charset));
			} catch (UnsupportedEncodingException ex) {
				String emsg = new StringBuilder("Encod form entity charset:[")
						.append(charset).append("] error").toString();
				throw new HttpException(emsg, ex);
			}
		}
		try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				String emsg = new StringBuilder("Http post request uri:[")
						.append(uri).append("] response error").toString();
				throw new HttpException(emsg);
			} else {
				resp = EntityUtils.toString(response.getEntity(), charset);
			}
		} catch (IOException ex) {
			String emsg = new StringBuilder("Http post request uri:[")
					.append(uri).append("] request error").toString();
			throw new HttpException(emsg, ex);
		}
		return resp;
	}

	public String postUTF8String(final String uri,
			final List<NameValueParam> params) throws HttpException {
		return postString(uri, params, "UTF-8");
	}

	public String postString(final String uri,
			final List<NameValueParam> params, final String charset)
			throws HttpException {
		String resp;
		HttpPost httpPost = new HttpPost(uri);
		httpPost.addHeader("User-Agent", userAgent);
		httpPost.setConfig(config);
		if (params != null && params.size() > 0) {
			List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(
					params.size());
			BasicNameValuePair pair;
			for (NameValueParam param : params) {
				pair = new BasicNameValuePair(param.getName(), param.getValue());
				list.add(pair);
				try {
					httpPost.setEntity(new UrlEncodedFormEntity(list, charset));
				} catch (UnsupportedEncodingException ex) {
					String emsg = new StringBuilder(
							"Encod form entity charset:[").append(charset)
							.append("] error").toString();
					throw new HttpException(emsg, ex);
				}
			}
		}
		try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				String emsg = new StringBuilder("Http post request uri:[")
						.append(uri).append("] response error").toString();
				throw new HttpException(emsg);
			} else {
				resp = EntityUtils.toString(response.getEntity(), charset);
			}
		} catch (IOException ex) {
			String emsg = new StringBuilder("Http post request uri:[")
					.append(uri).append("] request error").toString();
			throw new HttpException(emsg, ex);
		}
		return resp;
	}

	public String postMultiPartUTF8String(final String uri,
			final Map<String, Object> params) throws HttpException {
		return postMultiPartString(uri, params, "UTF-8");
	}

	public String postMultiPartString(final String uri,
			final Map<String, Object> params, final String charset)
			throws HttpException {
		String resp;
		HttpPost httpPost = new HttpPost(uri);
		httpPost.addHeader("User-Agent", userAgent);
		httpPost.setConfig(config);
		if (params != null && params.size() > 0) {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			Object value;
			StringBody stringBody;
			for (Entry<String, Object> entry : params.entrySet()) {
				value = entry.getKey();
				if (value instanceof String) {
					stringBody = new StringBody((String) value,
							ContentType.create("text/plain", charset));
					builder.addPart(entry.getKey(), stringBody);
				} else if (value instanceof InputStream) {
					builder.addBinaryBody(entry.getKey(), (InputStream) value);
				} else if (value instanceof File) {
					builder.addBinaryBody(entry.getKey(), (File) value);
				}
			}
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
		}
		try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				resp = EntityUtils.toString(response.getEntity(), charset);
			} else {
				String emsg = new StringBuilder(
						"Http post multipart request uri:[").append(uri)
						.append("] response error").toString();
				throw new HttpException(emsg);
			}
		} catch (IOException ex) {
			String emsg = new StringBuilder("Http post multipart request uri:[")
					.append(uri).append("] request error").toString();
			throw new HttpException(emsg, ex);
		}
		return resp;
	}

	public byte[] postStrEntityBinary(final String uri,
			final Map<String, String> uriParams, final String strEntity,
			final String charset) throws HttpException {
		byte[] datas = null;
		String realUri = uri;
		if (uriParams != null && uriParams.size() > 0) {
			StringBuilder builder = new StringBuilder(uri).append("?");
			for (Entry<String, String> entry : uriParams.entrySet()) {
				builder.append(entry.getKey()).append("=")
						.append(entry.getValue());
			}
			realUri = builder.substring(0, builder.length() - 1);
		}
		HttpPost httpPost = new HttpPost(realUri);
		httpPost.addHeader("User-Agent", userAgent);
		httpPost.setConfig(config);
		StringEntity entity = new StringEntity(strEntity, charset);
		httpPost.setEntity(entity);
		try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				datas = EntityUtils.toByteArray(response.getEntity());
			} else {
				String emsg = new StringBuilder(
						"Http post string entity request uri:[").append(uri)
						.append("] response error").toString();
				throw new HttpException(emsg);
			}
		} catch (IOException ex) {
			String emsg = new StringBuilder(
					"Http post string entity request uri:[").append(uri)
					.append("] request error").toString();
			throw new HttpException(emsg, ex);
		}
		return datas;
	}

	public String postStrEntityString(final String uri,
			final Map<String, String> uriParams, final String strEntity,
			final String entityCharset, final String charset)
			throws HttpException {
		String resp = null;
		byte[] datas = postStrEntityBinary(uri, uriParams, strEntity,
				entityCharset);
		if (datas != null) {
			try {
				resp = new String(datas, charset);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return resp;
	}

	public String postStrEntityUTF8String(final String uri,
			final Map<String, String> uriParams, final String strEntity)
			throws HttpException {
		String resp = postStrEntityString(uri, uriParams, strEntity, "UTF-8",
				"UTF-8");
		return resp;
	}
}
