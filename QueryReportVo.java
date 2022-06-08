package mms.reports.vo;

import javax.sql.rowset.WebRowSet;

public class QueryReportVO {

	/** The str msg string. */
	private String strMsgString = "";

	/** The str msg type. */
	private String strMsgType = "";

	private String qName;

	private String fRows;

	private String nRows;

	private WebRowSet resultWs = null;

	private int totalRows;

	private int totalFetchedRows;
/*<!-- Category Dropdown  Start-->*/
	private WebRowSet patientCategoryWs = null;

	private String strHospitalCode;
	/*<!-- Category Dropdown  End-->*/
	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalFetchedRows() {
		return totalFetchedRows;
	}

	public void setTotalFetchedRows(int totalFetchedRows) {
		this.totalFetchedRows = totalFetchedRows;
	}

	public String getqName() {
		return qName;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

	public String getfRows() {
		return fRows;
	}

	public void setfRows(String fRows) {
		this.fRows = fRows;
	}

	public String getnRows() {
		return nRows;
	}

	public void setnRows(String nRows) {
		this.nRows = nRows;
	}

	public WebRowSet getResultWs() {
		return resultWs;
	}

	public void setResultWs(WebRowSet resultWs) {
		this.resultWs = resultWs;
	}

	public String getStrMsgString() {
		return strMsgString;
	}

	public void setStrMsgString(String strMsgString) {
		this.strMsgString = strMsgString;
	}

	public String getStrMsgType() {
		return strMsgType;
	}

	public void setStrMsgType(String strMsgType) {
		this.strMsgType = strMsgType;
	}

	public WebRowSet getPatientCategoryWs() {
		return patientCategoryWs;
	}

	public void setPatientCategoryWs(WebRowSet patientCategoryWs) {
		this.patientCategoryWs = patientCategoryWs;
	}

	public String getStrHospitalCode() {
		return strHospitalCode;
	}

	public void setStrHospitalCode(String strHospitalCode) {
		this.strHospitalCode = strHospitalCode;
	}

}
