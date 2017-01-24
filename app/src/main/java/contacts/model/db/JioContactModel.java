package contacts.model.db;

/**
 * Created by Nitesh on 20-01-2017.
 */

public class JioContactModel {

    private static final String TAG = JioContactModel.class.getSimpleName();

    public int identity;

    public String favorites;

    public String home_phone;

    public String work_phone;

    public String mobile_phone;

    public String display_name;

    public String family_name;

    public String given_name;

    public String work_email;

    public String home_email;

    public String postal_code;

    public String city;

    public String company;

    public String department;

    public String birth_event;

    public String annv_event;

    public String account_info;

    public String relation;

    public JioContactModel() {
    }


    public void setRelation(String relation) {
        this.relation = relation;
    }


    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
    }

    public void setWork_phone(String work_phone) {
        this.work_phone = work_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public void setWork_email(String work_email) {
        this.work_email = work_email;
    }

    public void setHome_email(String home_email) {
        this.home_email = home_email;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setBirth_event(String birth_event) {
        this.birth_event = birth_event;
    }

    public void setAnnv_event(String annv_event) {
        this.annv_event = annv_event;
    }

    public void setAccount_info(String account_info) {
        this.account_info = account_info;
    }

}

