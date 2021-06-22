package com.sap.cloud.security.xsuaa.mtls;

import com.sap.cloud.security.client.HttpClientFactory;
import com.sap.cloud.security.config.ClientIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nullable;

/**
 * SpringHttpClient provides factory methods to initialize RestTemplate for
 * certificate(HTTPS) based and client secret(HTTP) based communications.
 */
public class SpringHttpClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringHttpClient.class);

	private static SpringHttpClient instance;

	private SpringHttpClient() {
	}

	public static SpringHttpClient getInstance() {
		if (instance == null) {
			instance = new SpringHttpClient();
		}
		return instance;
	}

	/**
	 * Creates a HTTPS RestTemplate. Used to setup HTTPS client for X.509
	 * certificate based communication. Derives certificate and private key values
	 * from ClientIdentity.
	 *
	 * @param clientIdentity
	 *            ClientIdentity of Xsuaa Service
	 * @return RestTemplate instance
	 */
	public RestTemplate create(@Nullable ClientIdentity clientIdentity) {
		LOGGER.warn("In productive environment provide a well configured RestTemplate");
		if (clientIdentity != null && clientIdentity.isCertificateBased()) {
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(HttpClientFactory.create(clientIdentity));
			return new RestTemplate(requestFactory);
		} else {
			return new RestTemplate();
		}
	}
}