package tr.com.beinplanner.packetsale.dao;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import tr.com.beinplanner.schedule.dao.ScheduleFactory;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=As.PROPERTY, property="progType")
@JsonSubTypes({@JsonSubTypes.Type(value = PacketSalePersonal.class, name = "psp"),
			   @JsonSubTypes.Type(value = PacketSaleClass.class, name = "psc"),
			   @JsonSubTypes.Type(value = PacketSaleMembership.class, name = "psm")})
public abstract class PacketSaleFactory {

	private Date 	salesDate;

	private List<ScheduleFactory> scheduleFactory;

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public List<ScheduleFactory> getScheduleFactory() {
		return scheduleFactory;
	}

	public void setScheduleFactory(List<ScheduleFactory> scheduleFactory) {
		this.scheduleFactory = scheduleFactory;
	}
	
	
	
	
	
	
	
}
