package com.bbmyjio.contactextractor.cmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitesh on 19-04-2017.
 */

public class COrganisation {
    private List<CompanyDepart> companyOrgList = new ArrayList<>();

    public List<CompanyDepart> getCompanyOrgList() {
        return companyOrgList;
    }

    public void setCompanyOrgList(List<CompanyDepart> companyOrgList) {
        this.companyOrgList = companyOrgList;
    }

    public static class CompanyDepart {
        public String company;
        public String org;

        public CompanyDepart(String company, String org) {
            this.company = company;
            this.org = org;
        }
    }
}
