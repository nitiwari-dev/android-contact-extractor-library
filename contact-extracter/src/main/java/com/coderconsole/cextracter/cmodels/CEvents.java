package com.coderconsole.cextracter.cmodels;

/**
 * Contacts Events Information
 *
 * Created by Nitesh on 19-04-2017.
 */

public class CEvents {
    private String displayName;
    private String photoUri;



    private String anniversay;
    private String birthDay;

    public String getAnniversay() {
        return anniversay;
    }

    public void setAnniversay(String anniversay) {
        this.anniversay = anniversay;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
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
