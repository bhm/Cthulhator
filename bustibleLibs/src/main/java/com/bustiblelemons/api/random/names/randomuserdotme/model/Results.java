
package com.bustiblelemons.api.random.names.randomuserdotme.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Results{
   	private String seed;
   	private User user;
   	private String version;

 	public String getSeed(){
		return this.seed;
	}
	public void setSeed(String seed){
		this.seed = seed;
	}
 	public User getUser(){
		return this.user;
	}
	public void setUser(User user){
		this.user = user;
	}
 	public String getVersion(){
		return this.version;
	}
	public void setVersion(String version){
		this.version = version;
	}
}
