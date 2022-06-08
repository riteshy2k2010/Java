PROCEDURE proc_patient_category_cmb(IN p_modeval character varying DEFAULT '1'::character varying, IN p_hosp_code character varying DEFAULT NULL::character varying, OUT err character varying, INOUT resultset refcursor);

PROCEDURE proc_patient_category_cmb(IN p_modeval character varying DEFAULT '1'::character varying, IN p_hosp_code character varying DEFAULT NULL::character varying, OUT err character varying, INOUT resultset refcursor) IS
QUERY   VARCHAR2 (4000);
BEGIN
	if(p_modeval=1) then
	QUERY:='SELECT distinct GNUM_PATIENT_CAT_CODE,INITCAP(A.GSTR_PATIENT_CAT_NAME) as GSTR_PATIENT_CAT_NAME
			FROM GBLT_PATIENT_CAT_MST A
			where GNUM_PATIENT_CAT_TYPE = 0
			and gnum_isvalid=1
			and gnum_hospital_code ='||p_hosp_code||'
			order by INITCAP(A.GSTR_PATIENT_CAT_NAME)
			';
	end if;
	OPEN resultset FOR QUERY;
	EXCEPTION
      WHEN OTHERS
      THEN
         IF resultset%ISOPEN
         THEN
            CLOSE resultset;
         END IF;

         err := SQLERRM;
	RAISE;
END;