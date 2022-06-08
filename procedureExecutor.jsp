<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/dateTag.tld" prefix="dateTag"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Query Report</title>

<link href="../../hisglobal/bootstrap4.0_newgui/css/bootstrap.css" rel="stylesheet" />
<link href="../../hisglobal/bootstrap4.0_newgui/font-awesome/css/all.css" rel="stylesheet">
<link href="../../ipd/css/newlayout.css" rel="stylesheet" type="text/css">
<link href="../../hisglobal/bootstrap4.0_newgui/datetimepicker/css/gijgo.min.css"
	rel="stylesheet" type="text/css">   

<script type="text/javascript"
	src="../../hisglobal/bootstrap4.0_newgui/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript"
	src="../../hisglobal/bootstrap4.0_newgui/swAl/swal.js"></script>
<script type="text/javascript"
	src="../../hisglobal/bootstrap4.0_newgui/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="../../hisglobal/bootstrap4.0_newgui/datetimepicker/js/gijgo.min.js"></script>

<link href="../../sickleave/css/sickLeave.css" rel="stylesheet">


<link href="../../hisglobal/css/dwh.css" rel="stylesheet" type="text/css">
<link href="../../hisglobal/css/control_qry.css" rel="stylesheet" type="text/css">
<link href="../../hisglobal/css/calendar-tas.css" rel="stylesheet" type="text/css">
<link href="../../hisglobal/css/newpopup.css" rel="stylesheet" type="text/css">
<link href="../../hisglobal/css/popup.css" rel="stylesheet" type="text/css">
<link href="../../hisglobal/css/tab-process-style.css" rel="stylesheet" type="text/css">

<script language="JavaScript" src="../../hisglobal/js/jquery-1.10.1.min.js"></script>
<script language="JavaScript" src="../../hisglobal/js/jquery-ui.min.js"></script>
<script language="JavaScript" src="../../masterutil/js/"></script>
<script language="JavaScript" src="../../hisglobal/"></script>
<script language="Javascript" src="../../hisglobal/js/validationCommon_qry.js"></script>
<script language="Javascript" src="../../hisglobal/js/commonFunctions_qryReport.js"></script>
<script language="Javascript" src="../../hisglobal/js/util.js"></script>
<script language="Javascript" src="../../hisglobal/js/validation.js"></script> 
<style type="text/css">
body {
	/* font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;*/
	font-size: 14px;
	line-height: 1.42857143;
	color: rgba(75, 75, 75, 0.7);
	font-weight: 501;
}
TD {
   text-align: center;
}
.prescriptionTile {
	margin: 0.5% 0;
	border-bottom: 1px solid #d7d7d7;
	padding-bottom: 10px;
	padding: 1% 2% 0.5% 2%;
	background-color: #fff;
	transition: 0.5s;
	box-shadow: 0 0.5px 10px 2px #b0acac;
	border-radius: .25rem;
	color: rgba(75, 75, 75, 0.7);
}
.subHeaders {
	font-weight: 500 !important;
	/* font-family: "Helvetica Neue", "Helvetica"; */
	font-size: 19px !important;
	padding-bottom: 0px !important;
	color: rgba(75, 75, 75, 0.7);
}
.legend2 {
	position: absolute;
	top: -2.5em;
	right: 44px;
	background:#fff;
	line-height: 1.2em;
}
 
.badge {
    display: inline-block;
    padding: 0.45em 0.4em;
    font-size: 100%;
    }
    
    thead{
    background-color: #17a2b8;
    }
 
#loadingTab {
	position: absolute;
	left: 50%;
	top: 50%;
	transform: translate(-50%, -50%);
}
</style>

<script type="text/javascript">

function goToHome() 
{
	document.forms[0].hmode.value = "CANCELPAGE";
	showMenuFrame();
	document.forms[0].submit();
}
function executeJob(obj)
{
	if(document.forms[0].nRows.value <= 0)
	{		
		alert("No. of Rows should be Greater than Zero");
		return false;		
	}
	document.forms[0].hmode.value="EXECUTE_DATA";
	//document.forms[0].submit();	
	var url ="QueryReportCNT.cnt?hmode=EXECUTE_DATA&nRows="+document.forms[0].nRows.value+"&cattype="+document.forms[0].cattype.value;	
	 //	alert("url :: "+url);	 
	 
	$("#loadingTab").css("display","block");
	ajaxFunction(url,"1");
}

function getAjaxResponse(res,mode)
{ 		
	    if(mode=='1')
	    {	    	
	    	var temp1 = res.split("####");		 		
		 	document.getElementById("normalMsg").innerHTML=temp1[0];
		 	document.getElementById("normalMsg").style.display="";
		 	$("#loadingTab").css("display","none");
		}
}
</script>

</head>
<body class="" onload="hideMenuFrame();" style="background: #eaeef3;">
<style>
 #loadingTab {
	position: relative; 
	z-index: 10;
}
.loder {
	position: absolute;
	background: #0000002e;
	height: 100vh;
	width: 100vw;
	left: 0;
	top: 0;
}
.loder .fa.fa-spinner.fa-spin {
	position: absolute;
	left: 50%;
	top: 50%;
	z-index: 999;
	transform: translate(-50%, -50%);
}
	
.legend2 button:first-child {
	border-radius: 5px 0 0 5px !important;
}
.legend2 button:last-child {
	border-radius: 0 5px 5px 0 !important;
}
.legend2 button.btn {
	border-radius: 0;
	margin-right: -5px;
}
</style>
<div id="loadingTab" style="display: none;">
	<div class="loder">
		<i class="fa fa-spinner fa-spin" style="font-size: 48px; color: #1f6291"></i>
	</div>
</div>
	<html:form name="queryReportBean" action="/reports/QueryReportCNT" type="mms.reports.controller.fb.QueryReportFB">
	 <!-- new Records section Start -->	
		 <div class="head_title_sec">
		 	<div class="container-fluid">
		 		<div class="row">
		 			<div class="col-8">
		 				<div class=" title_sec">
							<i class="fas fa-file-excel iround text-success" title="Query"></i><h5>Bulk CR Generator </h5>
						</div>
		 			</div>
		 			<div class="col-4">
						<div class="btn_group" id='nonPrintableLegend2'>
							<button type="button" class="btn btn-outline-danger" onclick="cancelFunc();"> <i class="fas fa-times" title="Cancel"></i> </button>
							<button type="button" class=" btn btn-outline-secondary " onclick="pageResetMethod();"> <i class="fa fa-redo fa-bolder" aria-hidden="true" title="Clear"></i> </button>
							<button type="button" id="saveid" class="btn btn-outline-success" tabindex='2' onclick='executeJob(this)'> <i class="fas fa-sync" title='Sync'></i> </button>
						</div>
					</div>
		 		</div>
		 	</div>
		 </div>
		<div class="container-fluid ">
			<div class="row">
				<div class="col-12">
					<div id="errMsg" class="errMsg">
						<bean:write name="queryReportBean" property="strErrMsg" />
					</div>
					<div id="warningMsg" class="warningMsg">
						<bean:write name="queryReportBean" property="strWarningMsg" />
					</div>
					 
					<div id="normalMsg"  style="display:none;" class="alert alert-success mt-2 mb-0" ><i class="fa fa-thumbs-up" style="float: left; font-size: 16px;"></i>
						<bean:write name="queryReportBean" property="strNormalMsg" />
					</div>
				</div>
			</div>
		</div> 
		<div class="tab_in_sec">
			<div class="container-fluid">
				<div class="row">
					<div class="col-sm-12">
						<!-- <div class="row">
							<div class="col-8">
								<div class="title_sec">
									<i class="fas fa-file-alt iround text-success" title="Query"></i><h4>Bulk CR Generator </h4>
								</div>
							</div>
							<div class="col-4">
								<div class="btn_group" id='nonPrintableLegend2'>
									<button type="button" class="btn btn-outline-danger" onclick="cancelFunc();"> <i class="fas fa-ban iround" title="Cancel"></i> </button>
									<button type="button" class=" btn btn-outline-secondary " onclick="pageResetMethod();"> <i class="fa fa-redo fa-bolder" aria-hidden="true" title="Clear"></i> </button>
									<button type="button" id="saveid" class="btn btn-outline-success" tabindex='2' onclick='executeJob(this)'> <i class="fas fa-sync" title='Sync'></i> </button>
								</div>
							</div>
						</div> -->
						<div class="row">
							<div class="col-2">
								<label for="new_records">New Records</label>
								<input type="radio" name="records" id="new_records" />
							</div>
							<div class="col-2">
								<label for="update_records">Update Records</label>
								<input type="radio" name="records" id="update_records" />
							</div>
							<div class="col-2">
								<label for="entiled_rec">Entiled</label>
								<input type="radio"  id="entiled_rec"  value='1' checked="checked" name='cattype'/>
							</div>
							<div class="col-2">
								<label for="nonentiled_rec">Non-Entiled</label>
								<input type="radio" value='2' name='cattype' id="nonentiled_rec" />
							</div>
							<div class="col-1">
								 <label for="">Category</label>
							</div>
							<!-- Category Dropdown  Start-->
							<div class="col-2">
								<select name="strPatientCategory" class="browser-default custom-select labelcaps labelbold">
									<bean:write name="queryReportBean" property="strPatientCategoryValues" filter="false"/>
								</select>
							</div> 
							<!-- Category Dropdown  End-->
						</div>
						<div class="row">
							<div class="col-6">
								<div class="row">
									<div class="col-6"><label for="">Number Of Total Records</label></div>
									<div class="col-3"><input type="number" class="form-control" name="limit_rec" onclick="enableFetchValue(this);"/></div>
								</div>
								<div class="row">
									<div class="col-6"><label for="">Number Of Records Allready Sync</label></div>
									<div class="col-3"><input type="number" class="form-control" name="limit_rec" onclick="enableFetchValue(this);"/></div>
								</div>
								<div class="row">
									<div class="col-6"><label for="">Pending Records</label></div>
									<div class="col-3"><input type="number" class="form-control" name="limit_rec"/></div>
								</div>
								<div class="row">
									<div class="col-6"><label for="">Number Of Records Limit</label></div>
									<div class="col-3"><input type="number" class="form-control" name="limit_rec"/></div>
								</div>
							</div>
						</div>
						<hr />
						<div class="" align="right">
							<font color="red">* Mandatory Field</font>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- new Records section End-->
		<%-- <div class="container-fluid ">
			<div class="row">
				<div class="col-12">
					<div id="errMsg" class="errMsg">
						<bean:write name="queryReportBean" property="strErrMsg" />
					</div>
					<div id="warningMsg" class="warningMsg">
						<bean:write name="queryReportBean" property="strWarningMsg" />
					</div>
					 
					<div id="normalMsg"  style="display:none;" class="alert alert-success mt-2" ><i class="fa fa-thumbs-up" style="float: left; font-size: 16px;"></i>
						<bean:write name="queryReportBean" property="strNormalMsg" />
					</div>
				</div>
			</div>
		</div> --%>
		<div class="container-fluid p-4">
			<div class="row">
				<div class="prescriptionTile col-sm-12" align="center">
					<div class="row rowFlex reFlex">
						<div class="col-sm-6" style="text-align: initial;">
							<p class="subHeaders">
								<i class="fas fa-file-alt iround text-success" title="Query"></i>
								&nbsp; Bulk CR Generator
							</p>
							<!-- <button  type="button" class="btn btn-outline-success mt-1  savebtn" onclick="cancelFunc();">
		                                        <i class="fas fa-file-alt iround"  title="Cancel"></i>
	                                      </button> -->
						</div>
						<div class="col-sm-6">
							<div class="legend2" id='nonPrintableLegend2'>
								<button type="button" class="btn btn-outline-danger" onclick="cancelFunc();"> <i class="fas fa-times" title="Cancel"></i> </button>
								<button type="button" class=" btn btn-outline-secondary " onclick="pageResetMethod();"> <i class="fa fa-redo fa-bolder" aria-hidden="true" title="Clear"></i> </button>
								<button type="button" id="saveid" class="btn btn-outline-success" tabindex='2' onclick='executeJob(this)'> <i class="fas fa-exchange-alt" title='sync'></i> </button>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2"></div>
						<div class="col-sm-5">
							<input type="radio" value='1' checked="checked" name='cattype'/> Entitled
						</div>
						<div class="col-sm-5">
							<input type="radio" value='2' name='cattype'/> Non Entitled
						</div>
					</div>				
					<div class="row">
						<div class="col-sm-2">
							<font color="red"></font>
						</div>
						<div class="col-sm-2">
							<input type="radio" name="fRows" value="1" onclick="enableFetchValue(this);" checked="checked" /> No. of Records
						</div>
						<div class="col-sm-2">
							<input type="text"  name="nRows" class="form-control" value="50">
						</div>

						<div class="col-sm-2">
							<input type="radio" name="fRows" value="2" onclick="enableFetchValue(this);" /> All Records
						</div>
					</div> 
					<div id="resultDivAreaId" style="display: none;">
			  			<div class="linenew" id="recDetailsDivId">
							<table cellspacing="1" cellpadding="1" align="center" class="NEWTABLEWIDTH">
								<tbody>
									<tr>
										<td colspan="2">Query Result</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="row">
							<div class="col-sm-11">
								<table class="NEWTABLEWIDTH" align="center" cellpadding="1px"
								cellspacing="1px">
									<tr>
										<td width="25%" class="LABEL">Total Records</td>
										<td width="25%" class="CONTROL"><div id='totalRowsId' style="font-size: large;color: blue;"></div> </td> 
										
										<td width="25%" class="LABEL">Total Fetched Rows </td>
										<td width="25%" class="CONTROL"><div id='totalFetchedRowsId' style="font-size: large;color: blue;"></div> </td>
									</tr>
								</table>
							</div>
							<div class="col-sm-1">	
								<div id="printImg" style="width: 99%;display: none;"><img align="right" onclick="getExcel();" src="../../hisglobal/images/excel1.png" title="Save As Excel" style="cursor: pointer;height: 20px;width: 20px;">  <img align="right" onclick="printData(1);" src="../../hisglobal/images/print.gif" title="Print Report" style="cursor: pointer; width: 20px; height: 20px; margin-bottom:2px; margin-right:5px;">  </div>
							</div>
						</div>
						<div style="height: 250px;overflow: scroll;">
							<div id="qResultsDivId" style="width: 100%;" ></div>
						</div>
					</div>
  					<hr>
					<div class="row">
						<div class="col-sm-6" align="left" >
							<font color='red'></font>
						</div>
						<div class="col-sm-6" align="right" >
							<font color='red'>* Mandatory Field</font>
						</div>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" name="hmode" />
		<input type="hidden" name="sFRows" value='1' />
		<input type="hidden" name="lastQuery" value='' />
	</html:form>
</body>
</html>