package com.bbmyjio.contactextractor.cmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Contact Organization
 *
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
        private String company;
        private String org;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getOrg() {
            return org;
        }

        public void setOrg(String org) {
            this.org = org;
        }
    }
}
