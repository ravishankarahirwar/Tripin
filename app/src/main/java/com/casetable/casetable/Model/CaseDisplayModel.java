package com.casetable.casetable.Model;

public class CaseDisplayModel
{
    private String total;

    private String message;

    private String status;

    private Data[] data;

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total = "+total+", message = "+message+", status = "+status+", data = "+data+"]";
    }



    public class Data
    {
        private String LawFirm;

        private String ExpactedStage;

        private String CaseStage;

        private String CaseNo;

        private String CaseId;

        private String CaseAmount;

        private String CaseName;


        public Data(String CaseId , String CaseNo, String CaseName, String CaseAmount, String CaseStage,String ExpactedStage,String LawFirm) {

            this.CaseId=CaseId;
            this.CaseNo=CaseNo;
            this.CaseName=CaseName;
            this.CaseAmount=CaseAmount;
            this.CaseStage=CaseStage;
            this.ExpactedStage=ExpactedStage;
            this.LawFirm=LawFirm;





        }

        public String getLawFirm ()
        {
            return LawFirm;
        }

        public void setLawFirm (String LawFirm)
        {
            this.LawFirm = LawFirm;
        }

        public String getExpactedStage ()
        {
            return ExpactedStage;
        }

        public void setExpactedStage (String ExpactedStage)
        {
            this.ExpactedStage = ExpactedStage;
        }

        public String getCaseStage ()
        {
            return CaseStage;
        }

        public void setCaseStage (String CaseStage)
        {
            this.CaseStage = CaseStage;
        }

        public String getCaseNo ()
        {
            return CaseNo;
        }

        public void setCaseNo (String CaseNo)
        {
            this.CaseNo = CaseNo;
        }

        public String getCaseId ()
        {
            return CaseId;
        }

        public void setCaseId (String CaseId)
        {
            this.CaseId = CaseId;
        }

        public String getCaseAmount ()
        {
            return CaseAmount;
        }

        public void setCaseAmount (String CaseAmount)
        {
            this.CaseAmount = CaseAmount;
        }

        public String getCaseName ()
        {
            return CaseName;
        }

        public void setCaseName (String CaseName)
        {
            this.CaseName = CaseName;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [LawFirm = "+LawFirm+", ExpactedStage = "+ExpactedStage+", CaseStage = "+CaseStage+", CaseNo = "+CaseNo+", CaseId = "+CaseId+", CaseAmount = "+CaseAmount+", CaseName = "+CaseName+"]";
        }
    }

}