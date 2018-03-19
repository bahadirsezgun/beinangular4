package tr.com.beinplanner.schedule.dao;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonTypeName;

import tr.com.beinplanner.packetpayment.dao.PacketPaymentFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentPersonal;
import tr.com.beinplanner.packetsale.dao.PacketSaleFactory;
import tr.com.beinplanner.packetsale.dao.PacketSalePersonal;
import tr.com.beinplanner.program.dao.ProgramFactory;
import tr.com.beinplanner.user.dao.User;

@Entity
@Table(name="schedule_users_personal_plan")
@JsonTypeName("supp")
public class ScheduleUsersPersonalPlan extends ScheduleFactory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SUPP_ID")
	private long suppId;	
	
	@Column(name="SCHT_ID")
	private long schtId;

	@Column(name="USER_ID")
	private long userId;

	
	@Column(name="SALE_ID")
	private long saleId;

	@Transient
	private PacketSaleFactory packetSaleFactory;

	@Transient
	private PacketPaymentFactory packetPaymentFactory;

	
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="USER_ID",foreignKey=@ForeignKey(foreignKeyDefinition="SUPP_TO_USER_FK"),insertable=false,updatable=false)
	private User user;
	
	@Transient
	private String type="supp";
	
	
	@Transient
	private double unitPrice;
	
	@Transient
	private int saleCount;
	
	
	
	
	public PacketPaymentFactory getPacketPaymentFactory() {
		return packetPaymentFactory;
	}

	public void setPacketPaymentFactory(PacketPaymentFactory packetPaymentFactory) {
		this.packetPaymentFactory = packetPaymentFactory;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	
	public long getSchtId() {
		return schtId;
	}

	public void setSchtId(long schtId) {
		this.schtId = schtId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getSaleId() {
		return saleId;
	}

	public void setSaleId(long saleId) {
		this.saleId = saleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		super.setUser(user);
		this.user = user;
	}

	
	
	public PacketSaleFactory getPacketSaleFactory() {
		return packetSaleFactory;
	}

	public void setPacketSaleFactory(PacketSaleFactory packetSaleFactory) {
		this.packetSaleFactory = packetSaleFactory;
	}

	public long getSuppId() {
		return suppId;
	}

	public void setSuppId(long suppId) {
		this.suppId = suppId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	
	
	
}
