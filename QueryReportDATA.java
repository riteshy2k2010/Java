package mms.reports.controller.data;

import hisglobal.exceptions.HisException;
import hisglobal.transactionmgnt.HisDAO;
import hisglobal.utility.HisUtil;

import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.WebRowSet;

import mms.reports.bo.QueryReportBO;
import mms.reports.controller.fb.QueryReportFB;
import mms.reports.controller.hlp.QueryReportHLP;
import mms.reports.vo.QueryReportVO;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class QueryReportDATA {

	public static void getResults(QueryReportFB fb, HttpServletRequest request,
			HttpServletResponse response) {

		String strResult = "";
		QueryReportVO vo = null;
		QueryReportBO bo = null;

		try {
			bo = new QueryReportBO();
			vo = new QueryReportVO();

			strResult = validateFields(fb.getqName(), fb.getfRows(),
					fb.getnRows());

			if (strResult.length() <= 0) {

				vo.setfRows(fb.getfRows());
				vo.setnRows(fb.getnRows());
				vo.setqName(fb.getqName().replace("@@$@@", "%").replace("^^@^^", "+"));

				bo.getQueryResult(vo);

				if (vo.getStrMsgType().equals("1")) {

					strResult = "ERROR####" + vo.getStrMsgString();

				} else {

					strResult = "RESULT####"
							+ vo.getTotalFetchedRows()
							+ "^"
							+ vo.getTotalRows()
							+ "^"
							+ QueryReportHLP.getResults(vo.getResultWs(),
									vo.getTotalFetchedRows());

				}

			}

			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().print(strResult);

		} catch (Exception e) {
			try {
				strResult = "GERROR####" + e.getMessage();
				response.setHeader("Cache-Control", "no-cache");

				response.getWriter().print(strResult);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	private static String validateFields(String query, String fRows,
			String nRows) {

		if (query.trim().length() < 0)
			return "GERROR####Query is a Mandatory Field";

		if (query.indexOf("INSERT") >= 0 || query.indexOf("insert") >= 0
				|| query.indexOf("UPDATE") >= 0 || query.indexOf("update") >= 0
				|| query.indexOf("DELETE") >= 0 || query.indexOf("delete") >= 0
				|| query.indexOf("TRUNCATE") >= 0
				|| query.indexOf("truncate") >= 0
				|| query.indexOf("SLEEP") >= 0 || query.indexOf("sleep") >= 0)
			return "GERROR####Only SELECT Query is allowed";

		if (query.indexOf("SELECT") < 0 && query.indexOf("select") < 0)
			return "GERROR####Only SELECT Query is allowed";

		if ("1".equals(fRows))
			if (nRows.trim().length() == 0
					|| (nRows.trim().length() != 0 && Integer.parseInt(nRows) <= 0))
				return "GERROR####No. of Rows is a Mandatory Field and should be Greater than Zero";

		return "";
	}

	public static void saveInExcel(QueryReportFB fb,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String strResult = "";
		QueryReportVO vo = null;
		QueryReportBO bo = null;

		HSSFWorkbook workbook = null;
		HSSFSheet sh = null;

		WebRowSet ws = null;

		try {
			bo = new QueryReportBO();
			vo = new QueryReportVO();

			strResult = validateFields(fb.getLastQuery(), fb.getfRows(),
					fb.getnRows());

			if (strResult.length() <= 0) {

				vo.setfRows(fb.getfRows());
				vo.setnRows(fb.getnRows());
				
				
				vo.setqName(fb.getLastQuery().replace("@@$@@", "%").replace("^^@^^", "+"));

				bo.getQueryResult(vo);

				if (vo.getStrMsgType().equals("1")) {

					fb.setStrErrMsg(vo.getStrMsgString());

				} else {

					response.setContentType("application/xls");
					response.setHeader("Content-Disposition",
							"inline; filename=Query_Report.xls");

					workbook = new HSSFWorkbook();
					sh = workbook.createSheet("Query_Report.xls");

					ws = vo.getResultWs();

					ResultSetMetaData rsmd = ws.getMetaData();
					int colCount = rsmd.getColumnCount();

					int startRow = 0;

					for (int i = 0; i < colCount; i++) {

						String strColumnName = rsmd.getColumnName((i+1));

						if (sh.getRow(startRow) == null)
							sh.createRow(startRow);

						if (sh.getRow(startRow).getCell((short)i) == null) {
							sh.getRow(startRow).createCell((short)i);
						}

						sh.getRow(startRow).getCell((short)i)
								.setCellValue(strColumnName);

					}

					startRow++;

					if (ws.size() > 0) {

						int x = 0;

						while (ws.next() && x < vo.getTotalFetchedRows()) {

							if (sh.getRow(startRow) == null)
								sh.createRow(startRow);

							for (int i = 0; i < colCount; i++) {

								if (sh.getRow(startRow).getCell((short)i) == null) {
									sh.getRow(startRow).createCell((short)i);
								}

								sh.getRow(startRow).getCell((short)i)
										.setCellValue(ws.getString((i+1)));

							}

							x++;
							startRow++;
						}

					}

					workbook.write(response.getOutputStream());

				}

			}

		} catch (Exception e) {
 
			
			HisException eObj = new HisException("eAushadhi",
					"AnnualDemandFreezeDATA->saveInExcel()",
					e.getMessage());

			fb.setStrErrMsg("Application Error [ERROR ID : "
					+ eObj.getErrorID() + "],Contact System Administrator! ");

		} finally {

			if (ws != null) {

				try {
					ws.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ws = null;

			}

		}

	}
	
	public static void save(QueryReportFB fb,HttpServletRequest request, HttpServletResponse response)
	{
		HisDAO daoObj = null;
		
		String strProcName = "{call pkg_reg_interface.populate_emp_others_from_interface(?,?,?,?,?)}";
		int nProcIndex = 0;
		String strErr = "";
		WebRowSet ws = null;
		String strResult="";
		
		String hospCode=request.getSession().getAttribute("HOSPITAL_CODE").toString();
		String seatId=request.getSession().getAttribute("SEATID").toString();
		String nrows=request.getParameter("nRows");
		String cattype=request.getParameter("cattype");
		
		if(cattype.equals("1"))//Entitled
		{
			strProcName = "{call pkg_reg_interface.populate_emp_from_interface(?,?,?,?,?)}";
		}
		else
		{
			strProcName = "{call pkg_reg_interface.populate_emp_others_from_interface(?,?,?,?,?)}";
		}
		
		try 
		{
			daoObj = new HisDAO("HisGlobal","HisUtil");
			nProcIndex = daoObj.setProcedure(strProcName);
			
			daoObj.setProcInValue(nProcIndex, "modeval", "1",1);
			daoObj.setProcInValue(nProcIndex, "hosp_code", hospCode,2);
			daoObj.setProcInValue(nProcIndex, "noofrecords", nrows,3);
			daoObj.setProcOutValue(nProcIndex, "err", 1,4);
			daoObj.setProcOutValue(nProcIndex, "resultset", 2,5);
			
			daoObj.executeProcedureByPosition(nProcIndex);

			strErr = daoObj.getString(nProcIndex, "err");

			if (strErr == null)
				strErr = "";	
			
			if (strErr.equals("")) 
			{
				ws = daoObj.getWebRowSet(nProcIndex, "resultset");
				System.out.println("size"+ws.size());
				if(ws.next())
				{
					strResult="No Of Records Sync Successfully ::"+ ws.getString(1);					
				}
				
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().print(strResult);
			} 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			if (daoObj != null) 
			{
				daoObj.free();
				daoObj = null;
			}
		}
		
		
	}
/*<!-- Category Dropdown  Start-->*/
	public static void getEssentials(QueryReportFB fb, HttpServletRequest request,
			HttpServletResponse response) {

		QueryReportVO vo = null;
		QueryReportBO bo = null;
		String strmsgText = null;
		HisUtil util = null;
		String strPatientCategoryCmb="";
		try {
			bo = new QueryReportBO();
			vo = new QueryReportVO();
			String hospCode=request.getSession().getAttribute("HOSPITAL_CODE").toString();
			vo.setStrHospitalCode(hospCode);
			bo.getPatientCategoryCombo(vo);
			
			if (vo.getStrMsgType().equals("1"))
			{

				throw new Exception(vo.getStrMsgString());

			}
			
			util = new HisUtil("IPD Reports", "QueryReportDATA");
			strPatientCategoryCmb= util.getOptionValue(vo.getPatientCategoryWs(),"","0^Select Value", false);
			
			fb.setStrPatientCategoryValues(strPatientCategoryCmb);

		} catch (Exception e) {
			strmsgText = "mms.reports.QueryReportDATA.QueryReportDATA --> "
					+ e.getMessage();
			HisException eObj = new HisException("mms",
					"QueryReport->QueryReportDATA()", strmsgText);
			fb.setStrErrMsg("Application Error [ERROR ID : "
					+ eObj.getErrorID() + "],Contact System Administrator! ");

			eObj = null;
		}
		finally
		{

			if (bo != null)
				bo = null;
			if (vo != null)
				vo = null;
			if (util != null)
				util = null;
		}

	}
	/*<!-- Category Dropdown  End-->*/
}
