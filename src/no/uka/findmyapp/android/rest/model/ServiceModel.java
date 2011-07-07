package no.uka.findmyapp.android.rest.model;

import java.io.Serializable;
import java.net.URI;

import no.uka.findmyapp.android.rest.library.HttpType;

public class ServiceModel  implements Serializable{
	private URI uri;
	private HttpType httpType;
	private String returnType;
	
	public ServiceModel(URI uri, HttpType httpType, String returnType) {
		super();
		this.uri = uri;
		this.httpType = httpType;
		this.returnType = returnType;
	}
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
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	@Override
	public String toString() {
		return "ServiceModel [uri=" + uri + ", httpType=" + httpType
				+ ", returnType=" + returnType + "]";
	}
}
