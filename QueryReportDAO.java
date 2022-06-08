package mms.reports.dao;

import hisglobal.transactionmgnt.HisDAO;
import ipd.reports.AdmittedPatientRptVO;

import javax.sql.rowset.WebRowSet;

import mms.reports.vo.QueryReportVO;

public class QueryReportDAO {

	public static synchronized void getResults(QueryReportVO vo) {

		HisDAO dao = null;
		WebRowSet ws = null;

		try {
			dao = new HisDAO("eAushadi", "QueryReportDAO");

			int index = dao.setQuery(vo.getqName());

		//	//System.out.println(vo.getqName());
			
			ws = dao.executeQry(index);

			if (ws != null) {

				int totalSize = ws.size();
 
				vo.setTotalRows(totalSize );

				if ("1".equals(vo.getfRows())) {

					int fSize = Integer.parseInt(vo.getnRows());
					
					vo.setTotalFetchedRows(totalSize > fSize ? fSize 
							: totalSize );

				} else {

					vo.setTotalFetchedRows(totalSize );

				}

			}

			vo.setResultWs(ws);

		} catch (Exception e) {

			vo.setStrMsgString(e.getMessage().split("ERROR:")[1]);
			vo.setStrMsgType("1");

		} finally {

			if (dao != null) {
				dao.free();
				dao = null;
			}

		}

	}
	/*<!-- Category Dropdown  Start-->*/

	public static void getPatientCategoryCombo(QueryReportVO voObj) {

		HisDAO daoObj = null;
		WebRowSet ws = null;

		String strProcName = "{call pkg_reg_view_railtel.proc_patient_category_cmb(?,?,?,?)}";
		int nProcIndex = 0;
	
		String strErr = "";

		try {

			daoObj = new HisDAO("Query Reports","QueryReportDAO");

			nProcIndex = daoObj.setProcedure(strProcName);
			daoObj.setProcInValue( nProcIndex, "p_modeval","1",1);
			daoObj.setProcInValue( nProcIndex, "p_hosp_code",voObj.getStrHospitalCode(),2);
			daoObj.setProcOutValue(nProcIndex, "err", 1,3);
			daoObj.setProcOutValue(nProcIndex, "resultset", 2,4);

			daoObj.executeProcedureByPosition(nProcIndex);
	
			strErr = daoObj.getString(nProcIndex, "err");

			if (strErr == null)
				strErr = "";

			if (strErr.equals("")) {

				ws = daoObj.getWebRowSet(nProcIndex, "resultset");
		
				voObj.setPatientCategoryWs(ws);
				
				
			} else {
				throw new Exception(strErr);
			}

		} catch (Exception e) {

			voObj
					.setStrMsgString("QueryReportDAO.getPatientCategoryCombo() --> "
							+ e.getMessage());
			voObj.setStrMsgType("1");

		} finally {
			if (daoObj != null) {
				daoObj.free();
				daoObj = null;
			}
		}

	}
	/*<!-- Category Dropdown  End-->*/
}
