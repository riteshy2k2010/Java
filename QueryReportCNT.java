package mms.reports.controller.action;

import hisglobal.exceptions.HisException;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mms.reports.controller.data.QueryReportDATA;
import mms.reports.controller.fb.QueryReportFB;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class QueryReportCNT extends DispatchAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws HisException, IOException, ServletException {
		String target = "reportPage";

		return mapping.findForward(target);
	}

	public ActionForward GO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		QueryReportFB fb = (QueryReportFB) form;
		QueryReportDATA.getResults(fb, request, response);
		return null;
	}

	public ActionForward CANCELPAGE(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward acFwd = new ActionForward();
		acFwd.setPath("/hisglobal/initPage.jsp");
		acFwd.setContextRelative(true);
		return acFwd;
	}

	public ActionForward GETEXCEL(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws HisException, SQLException {
		 
		try {
			QueryReportFB fb = (QueryReportFB) form;
			QueryReportDATA.saveInExcel(fb, request, response);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/*<!-- Category Dropdown  Start-->*/
	public ActionForward PROC_EXECUTOR(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws HisException, IOException, ServletException 
	{
		String target = "procExec";
		QueryReportFB fb = (QueryReportFB) form;
		QueryReportDATA.getEssentials(fb, request, response);
		return mapping.findForward(target);
	}
	/*<!-- Category Dropdown  End-->*/
	public void EXECUTE_DATA(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws HisException, IOException, ServletException 
	{
		String target = "procExec";
		QueryReportFB fb = (QueryReportFB) form;
		QueryReportDATA.save(fb, request, response);
		
		//return mapping.findForward(target);
	}

}
