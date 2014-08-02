
package com.bustiblelemons.api.random.names.randomuserdotme.model;

import org.apache.commons.lang.WordUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Name implements Serializable{
   	private String first;
   	private String last;
   	private String title;

 	public String getFirst(){
		return this.first;
	}
	public void setFirst(String first){
		this.first = WordUtils.capitalizeFully(first);
	}
 	public String getLast(){
		return this.last;
	}
	public void setLast(String last){
		this.last = WordUtils.capitalizeFully(last);
	}
 	public String getTitle(){
		return this.title;
	}
	public void setTitle(String title){
		this.title = WordUtils.capitalizeFully(title);
	}

    public String getFullName() {
        return String.format(Locale.ENGLISH, "%s %s", getFirst(), getLast());
    }
}
