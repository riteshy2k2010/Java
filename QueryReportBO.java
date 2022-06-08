package mms.reports.bo;

import mms.reports.dao.QueryReportDAO;
import mms.reports.vo.QueryReportVO;

public class QueryReportBO {

	public void getQueryResult(QueryReportVO vo){
		 
		QueryReportDAO.getResults(vo);

	}
	public void getPatientCategoryCombo(QueryReportVO vo){
		 
		QueryReportDAO.getPatientCategoryCombo(vo);

	}
	
}