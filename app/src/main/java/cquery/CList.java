package cquery;

import com.bbmyjio.contactextractor.cmodels.CEmail;

import contacts.model.api.CName;
import contacts.model.api.CPhone;

/**
 * ContactList which contains all the information of contacts in flat class 'CList'
 *
 * Created by Nitesh on 04-04-2017.
 */

public class CList {
    public String id;
    public CName cName;
    public CEmail email;
    public CPhone phone;

    public CName getcName() {
        return cName;
    }

    public void setcName(CName cName) {
        this.cName = cName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CEmail getEmail() {
        return email;
    }

    public void setEmail(CEmail email) {
        this.email = email;
    }

    public CPhone getPhone() {
        return phone;
    }

    public void setPhone(CPhone phone) {
        this.phone = phone;
    }
}
