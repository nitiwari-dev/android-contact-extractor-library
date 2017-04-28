package com.coderconsole.cextracter.cmodels.cquery;


import com.coderconsole.cextracter.cmodels.CPhone;

/**
 * Created by Nitesh on 22-04-2017.
 */

public class GenericCList {

    private String _id;
    private String contactId;
    private String displayName;
    private CPhone cPhone;
    private String photoUri;

    public CPhone getcPhone() {
        return cPhone;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public void setcPhone(CPhone cPhone) {
        this.cPhone = cPhone;
    }

    public String get_id() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
