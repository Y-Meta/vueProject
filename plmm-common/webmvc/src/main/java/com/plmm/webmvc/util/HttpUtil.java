package com.plmm.webmvc.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 发送http请求
 */
public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * 封装httpclient的请求
	 * @param httpUrl 请求的url 1、get提交则将 参数以?形式拼接在请求url后面 2、post提交时 此值为请求地址
	 * @param json 1、get提交时此值可以不传 2、post提交时此值为参数值 例如
	 *            {"username":"admin","password":"123456"}
	 * @param method 请求类型， post为POST提交 ， get 为GET提交
	 * @return
	 */
	public static String sendHttpUrlRequestByJson(String httpUrl, String json, String method) throws Exception {

		return jsonHttp(httpUrl, json, method, null);
	}

	/**
	 *
	 * @param httpUrl 请求的url 1、get提交则将 参数以?形式拼接在请求url后面 2、post提交时 此值为请求地址
	 * @param json 1、get提交时此值可以不传 2、post提交时此值为参数值 例如
	 *            {"username":"admin","password":"123456"}
	 * @param method 请求类型， post为POST提交 ， get 为GET提交
	 * @param headKey 请求头的参数名
	 * @param headValue 请求头的参数值
	 * @return
	 * @throws Exception
	 */
	public static String jsonHttp(String httpUrl, String json, String method, String headKey, String headValue)
			throws Exception {

		Map<String, String> header = new HashMap<String, String>();
		header.put(headKey, headValue);
		return jsonHttp(httpUrl, json, method, header);
	}

	/**
	 * 覆盖原有的 jsonHttpBak
	 * @param httpUrl
	 * @param json
	 * @param method
	 * @param header
	 * @return
	 * @throws Exception
	 */
	public static String jsonHttp(String httpUrl, String json, String method, Map<String, String> header)
			throws Exception {

		String result = "";
		HttpRequest hr = HttpRequest.post(httpUrl).trustAllCerts().trustAllHosts();
		if(header != null){
			hr.headers(header);
		}
		hr.header("Content-Type", "application/json; charset=UTF-8");
		if(json != null){
			hr.send(json);
		}
		result = hr.body();
		return result;
	}

	/**
	 * 封装httpclient的请求
	 * @see #jsonHttp(String, String, String, Map)
	 * @param httpUrl 请求的url 1、get提交则将 参数以?形式拼接在请求url后面 2、post提交时 此值为请求地址
	 * @param json 1、get提交时此值可以不传 2、post提交时此值为参数值 例如
	 *            {"username":"admin","password":"123456"}
	 * @param method 请求类型， post为POST提交 ， get 为GET提交
	 * @param header http请求所需的请求头
	 * @return
	 */
	@Deprecated
	public static String jsonHttpBak(String httpUrl, String json, String method, Map<String, String> header)
			throws Exception {

		String result = "";
		CloseableHttpClient httpClient = null;
		// 设置为30分
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1800000).setSocketTimeout(1800000)
				.setConnectionRequestTimeout(7000).setStaleConnectionCheckEnabled(true).build();
		if (httpUrl == null) {
			throw new IllegalArgumentException("Url is null");
		}
		if (httpUrl.toLowerCase().startsWith("https://")) {
			httpClient = getHttpsClient();
		} else {
			httpClient = HttpClients.createDefault();
		}
		HttpRequestBase request = null;
		HttpResponse response = null;
		try {
			if ("get".equalsIgnoreCase(method)) {
				request = new HttpGet(httpUrl);
			} else {
				HttpPost post = new HttpPost(httpUrl);
				if (!StringUtils.isEmpty(json)) {
					StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
					entity.setContentEncoding("UTF-8");
					entity.setContentType("application/json; charset=UTF-8");
					post.setEntity(entity);
				}
				request = post;
			}
			if (header != null && !header.isEmpty()) {
				for (Map.Entry<String, String> entry : header.entrySet()) {
					String k = entry.getKey();
					String v = entry.getValue();
					request.addHeader(k, v);
				}
			}
			request.setConfig(requestConfig);
			response = httpClient.execute(request);
			// 请求结束，返回结果
			result = EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (httpClient != null) {
				httpClient.close();
			}
		}
		return result;
	}

	private static CloseableHttpClient getHttpsClient() {

		RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory> create();
		ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
		registryBuilder.register("http", plainSF);
		//指定信任密钥存储对象和连接套接字工厂
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			//信任任何链接
			TrustStrategy anyTrustStrategy = new TrustStrategy() {

				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

					return true;
				}
			};
			SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy)
					.build();
			LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			registryBuilder.register("https", sslSF);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		Registry<ConnectionSocketFactory> registry = registryBuilder.build();
		//设置连接管理器
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
		//      connManager.setDefaultConnectionConfig(connConfig);
		//      connManager.setDefaultSocketConfig(socketConfig);
		//构建客户端
		return HttpClientBuilder.create().setConnectionManager(connManager).build();
	}

	public static void main(String[] args) {

		String httpUrl = "https://test.coracle.com:15000/mchl/jsse/message/push";
		//          String httpUrl = "http://192.168.8.16:9999/mchl/jsse/message/push";
		String json = "{\n" + "  \"appKey\" : \"c7e5424b-da56-4367-bebb-232a10f3cf21\",\n"
				+ "  \"boardcast\" : \"true\",\n" + "  \"userList\" : [\"230900\"],\n" + "  \"sender\": \"230900\",\n"
				+ "  \"title\" : \"\",\n" + "  \"msgContent\" : {\n" + "    \"type\": \"IM_txt\",\n"
				+ "    \"content\": \"${receivers}-test2016-10-19-${msg}\",\n" + "    \"mk\": \"chat\",\n"
				+ "    \"rids\": \"${rids}\"\n" + "  },\n" + "  \"badge\" : \"\"\n" + "}";
		String method = "POST";
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json");
		header.put("X-xSimple-AuthToken", "accc910dc04cce2bf802026eb3331e92");
		header.put("X-xSimple-LoginName", "230900");
		header.put(
				"User-Agent",
				"Mozilla/5.0 (Linux; U; Android 7.0; zh-cn; HUAWEI NXT-TL00) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
		String result = "";
		try {
			result = jsonHttpBak(httpUrl, json, "post", header);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}