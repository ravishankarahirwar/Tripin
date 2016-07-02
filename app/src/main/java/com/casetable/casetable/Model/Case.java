package com.casetable.casetable.Model;

import java.util.ArrayList;
import java.util.List;


public class Case {
    public Case() {

    }


    private Data[] data;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }


    public class Data  {
        String caseid;
        String casename;
        String caseno;
        String caseclimeamount;
        String currentstage;
        String nextexpactedstage;

        public Data(String caseid, String casename, String caseno, String caseclimeamount, String currentstage, String nextexpactedstage) {

            this.caseid=caseid;
            this.casename=casename;
            this.caseno=caseno;
            this.caseclimeamount=caseclimeamount;
            this.currentstage=currentstage;
            this.nextexpactedstage=nextexpactedstage;

        }


        public String getCaseid() {
            return caseid;
        }

        public void setCaseid(String caseid) {
            this.caseid = caseid;
        }

        public String getCasename() {
            return casename;
        }

        public void setCasename(String casename) {
            this.casename = casename;
        }

        public String getCaseno() {
            return caseno;
        }

        public void setCaseno(String caseno) {
            this.caseno = caseno;
        }

        public String getCaseclimeamount() {
            return caseclimeamount;
        }

        public void setCaseclimeamount(String caseclimeamount) {
            this.caseclimeamount = caseclimeamount;
        }

        public String getCurrentstage() {
            return currentstage;
        }

        public void setCurrentstage(String currentstage) {
            this.currentstage = currentstage;
        }

        public String getNextexpactedstage() {
            return nextexpactedstage;
        }

        public void setNextexpactedstage(String nextexpactedstage) {
            this.nextexpactedstage = nextexpactedstage;
        }
    }

}