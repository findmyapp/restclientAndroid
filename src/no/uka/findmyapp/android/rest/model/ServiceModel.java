package no.uka.findmyapp.android.rest.model;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.URI;

import android.content.ContentResolver;

import no.uka.findmyapp.android.rest.library.HttpType;

public class ServiceModel implements Serializable{
	private URI uri;
	private HttpType httpType;
	private Type returnType;
	
	public ServiceModel(URI uri, HttpType httpType, Type typeToken) {
		super();
		this.uri = uri;
		this.httpType = httpType;
		this.returnType = typeToken;
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
	public Type getReturnType() {
		return returnType;
	}
	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}
	@Override
	public String toString() {
		return "ServiceModel [uri=" + uri + ", httpType=" + httpType
				+ ", returnType=" + returnType + "]";
	}
	
}
