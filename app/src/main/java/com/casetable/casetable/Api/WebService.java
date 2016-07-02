package com.casetable.casetable.Api;

public class WebService {



    //LIVE URLs
    public static String BASE_URL="http://tanvirdalal.site11.com";




    public static class ViewCaseList {
        // URL
        public static final String Sub_URL_ViewCaseList = "/ChinaConstruction/CaseList.php";

    }


    public static class Login {
        // URL
        public static final String Sub_URL_Login = "/ChinaConstruction/Login.php";
        // Parameters
        public static final String EMAIL = "UserName";
        public static final String PASSWORD = "Password";

    }

    public static class AddCase {
        // URL
        public static final String Sub_URL_AddCase = "/ChinaConstruction/CaseInsert.php";
        // Parameters
        public static final String CASENO = "txtCaseNo";
        public static final String CASELAWFIRM= "txtLawFirm";

        public static final String CASENAME = "txtCaseName";
        public static final String CASEAMOUNT = "txtCaseAmount";
        public static final String CASEStAGE = "txtCasestage";
        public static final String CASENEXTSTAGE = "txtExpStage";

    }

    public static class EditCase {
        // URL
        public static final String Sub_URL_EditCase = "/ChinaConstruction/CaseEdit.php";
        // Parameters
        public static final String CASEID = "txtid";
        public static final String CASENO = "txtCaseNo";
        public static final String CASELAWFIRM = "txtLawFirm";
        public static final String CASENAME = "txtCaseName";
        public static final String CASEAMOUNT = "txtCaseAmount";
        public static final String CASEStAGE = "txtCasestage";
        public static final String CASENEXTSTAGE = "txtExpStage";

    }


    public static class DeleteCase {
        // URL
        public static final String Sub_URL_DeleteCase = "/ChinaConstruction/CaseDelete.php";
        // Parameters
        public static final String CASEID = "txtid";

    }


}
