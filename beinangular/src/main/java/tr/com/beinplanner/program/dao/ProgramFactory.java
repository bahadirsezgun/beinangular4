package tr.com.beinplanner.program.dao;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=As.PROPERTY, property="type")
@JsonSubTypes({@JsonSubTypes.Type(value = ProgramPersonal.class, name = "pp"),
			   @JsonSubTypes.Type(value = ProgramClass.class, name = "pc"),
			   @JsonSubTypes.Type(value = ProgramMembership.class, name = "pm")})
public abstract class ProgramFactory  {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
