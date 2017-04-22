package com.bbmyjio.contactextractor.cmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitesh on 19-04-2017.
 */

public class COrganisation {
    public List<CompanyDepart> companyOrgList;

    public COrganisation(List<CompanyDepart> companyOrgList) {
        this.companyOrgList = new ArrayList<CompanyDepart>(companyOrgList);
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
