package com.coderconsole.cextracter.cmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Contact Organization Information with Company and Department
 *
 * Created by Nitesh on 19-04-2017.
 */

public class COrganisation {

    private String displayName;
    private String photoUri;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
