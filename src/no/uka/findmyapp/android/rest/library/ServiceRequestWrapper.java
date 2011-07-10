package no.uka.findmyapp.android.rest.library;

import java.net.URI;

public class ServiceRequestWrapper {
	private URI uri;
	private HttpType httpType;
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public HttpType getHttpType() {
		return httpType;
	}
	public void setHttpType(HttpType httpType) {
		this.httpType = httpType;
	}
}
