package tr.com.beinplanner.settings.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pt_global")
public class PtGlobal {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="GLB_ID")
	private long glbId;
	
	@JsonIgnore
	@Column(name="FIRM_ID")
	private int firmId;


	@Column(name="PT_TZ")
	private String ptTz;

	@Column(name="PT_DATE_FORMAT")
	private String ptDateFormat;

	@Column(name="PT_CURRENCY")
	private String ptCurrency;

	@Column(name="PT_STATIC_IP")
	private String ptStaticIp="";

	@Column(name="PT_LANG")
	private String ptLang;

	
	@Transient
	private String ptScrDateFormat;
	@Transient
	private String ptDbDateFormat;
	
	public String getPtScrDateFormat() {
		return ptScrDateFormat;
	}

	public void setPtScrDateFormat(String ptScrDateFormat) {
		this.ptScrDateFormat = ptScrDateFormat;
	}

	public String getPtDbDateFormat() {
		return ptDbDateFormat;
	}

	public void setPtDbDateFormat(String ptDbDateFormat) {
		this.ptDbDateFormat = ptDbDateFormat;
	}

	public long getGlbId() {
		return glbId;
	}

	public void setGlbId(long glbId) {
		this.glbId = glbId;
	}

	public int getFirmId() {
		return firmId;
	}

	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}

	public String getPtTz() {
		return ptTz;
	}

	public void setPtTz(String ptTz) {
		this.ptTz = ptTz;
	}

	public String getPtDateFormat() {
		return ptDateFormat;
	}

	public void setPtDateFormat(String ptDateFormat) {
		this.ptDateFormat = ptDateFormat;
	}

	public String getPtCurrency() {
		return ptCurrency;
	}

	public void setPtCurrency(String ptCurrency) {
		this.ptCurrency = ptCurrency;
	}

	public String getPtStaticIp() {
		return ptStaticIp;
	}

	public void setPtStaticIp(String ptStaticIp) {
		this.ptStaticIp = ptStaticIp;
	}

	public String getPtLang() {
		return ptLang;
	}

	public void setPtLang(String ptLang) {
		this.ptLang = ptLang;
	}

		

}
