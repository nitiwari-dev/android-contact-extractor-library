package com.bbmyjio.contactextractor.cquery;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nitesh on 22-04-2017.
 */

public class GenericCList {

    String _id;
    String contactId;

    String displayName;
    CPhone cPhone;

    String photoUri;

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

    public static class CPhone {
        private HashSet<String> work = new HashSet<>();
        private HashSet<String> home = new HashSet<>();
        private HashSet<String> mobile = new HashSet<>();

        public HashSet<String> getWork() {
            return work;
        }

        public void setWork(HashSet<String> work) {
            this.work = work;
        }

        public HashSet<String> getHome() {
            return home;
        }

        public void setHome(HashSet<String> home) {
            this.home = home;
        }

        public HashSet<String> getMobile() {
            return mobile;
        }

        public void setMobile(HashSet<String> mobile) {
            this.mobile = mobile;
        }
    }
}
