
package com.bustiblelemons.api.random.names.randomuserdotme.model;

import com.bustiblelemons.model.OnlinePhotoUrl;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements OnlinePhotoUrl, Serializable {
   	private String sSN;
   	private String cell;
   	private String dob;
   	private String email;
   	private String gender;
   	private Location location;
   	private String md5;
   	private Name name;
   	private String password;
   	private String phone;
   	private String picture;
   	private String registered;
   	private String salt;
   	private String sha1;
   	private String sha256;
   	private String username;

 	public String getSSN(){
		return this.sSN;
	}
	public void setSSN(String sSN){
		this.sSN = sSN;
	}
 	public String getCell(){
		return this.cell;
	}
	public void setCell(String cell){
		this.cell = cell;
	}
 	public String getDob(){
		return this.dob;
	}
	public void setDob(String dob){
		this.dob = dob;
	}
 	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
 	public String getGender(){
		return this.gender;
	}
	public void setGender(String gender){
		this.gender = gender;
	}
 	public Location getLocation(){
		return this.location;
	}
	public void setLocation(Location location){
		this.location = location;
	}
 	public String getMd5(){
		return this.md5;
	}
	public void setMd5(String md5){
		this.md5 = md5;
	}
 	public Name getName(){
		return this.name;
	}
	public void setName(Name name){
		this.name = name;
	}
 	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
 	public String getPhone(){
		return this.phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
 	public String getPicture(){
		return this.picture;
	}
	public void setPicture(String picture){
		this.picture = picture;
	}
 	public String getRegistered(){
		return this.registered;
	}
	public void setRegistered(String registered){
		this.registered = registered;
	}
 	public String getSalt(){
		return this.salt;
	}
	public void setSalt(String salt){
		this.salt = salt;
	}
 	public String getSha1(){
		return this.sha1;
	}
	public void setSha1(String sha1){
		this.sha1 = sha1;
	}
 	public String getSha256(){
		return this.sha256;
	}
	public void setSha256(String sha256){
		this.sha256 = sha256;
	}
 	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		this.username = username;
	}

    @Override
    public String getUrl() {
        return getPicture();
    }
}
