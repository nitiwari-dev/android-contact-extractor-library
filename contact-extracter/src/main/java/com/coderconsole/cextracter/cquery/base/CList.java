package com.coderconsole.cextracter.cquery.base;


import com.coderconsole.cextracter.cmodels.CAccount;
import com.coderconsole.cextracter.cmodels.CEmail;
import com.coderconsole.cextracter.cmodels.CEvents;
import com.coderconsole.cextracter.cmodels.CGroups;
import com.coderconsole.cextracter.cmodels.CName;
import com.coderconsole.cextracter.cmodels.COrganisation;
import com.coderconsole.cextracter.cmodels.CPhone;
import com.coderconsole.cextracter.cmodels.CPostBoxCity;

/**
 * ContactList which contains all the information of contacts in flat class 'CList'
 *
 * Created by Nitesh on 04-04-2017.
 */

public class CList {
    public String id;
    public String contactId;
    public String photoUri;

    public CName cName;
    public CEmail cEmail;
    public CPhone cPhone;
    public CAccount cAccount;
    public CPostBoxCity cPostCode;
    public COrganisation cOrg;
    public CEvents cEvents;
    public CGroups cGroups;

    public CGroups getcGroups() {
        return cGroups;
    }

    public void setcGroups(CGroups cGroups) {
        this.cGroups = cGroups;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CName getcName() {
        return cName;
    }

    public void setcName(CName cName) {
        this.cName = cName;
    }

    public CEmail getcEmail() {
        return cEmail;
    }

    public void setcEmail(CEmail cEmail) {
        this.cEmail = cEmail;
    }

    public CPhone getcPhone() {
        return cPhone;
    }

    public void setcPhone(CPhone cPhone) {
        this.cPhone = cPhone;
    }

    public CAccount getcAccount() {
        return cAccount;
    }

    public void setcAccount(CAccount cAccount) {
        this.cAccount = cAccount;
    }

    public CPostBoxCity getcPostCode() {
        return cPostCode;
    }

    public void setcPostCode(CPostBoxCity cPostCode) {
        this.cPostCode = cPostCode;
    }

    public COrganisation getcOrg() {
        return cOrg;
    }

    public void setcOrg(COrganisation cOrg) {
        this.cOrg = cOrg;
    }

    public CEvents getcEvents() {
        return cEvents;
    }

    public void setcEvents(CEvents cEvents) {
        this.cEvents = cEvents;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
}
