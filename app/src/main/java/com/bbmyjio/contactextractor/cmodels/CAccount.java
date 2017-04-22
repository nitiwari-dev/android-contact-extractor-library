package com.bbmyjio.contactextractor.cmodels;

import com.bbmyjio.contactextractor.cmodels.ContactGenericType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitesh on 19-04-2017.
 */

public class CAccount  {
    private List<ContactGenericType> mGenericType;

    public List<ContactGenericType> getmGenericType() {
        return mGenericType;
    }

    public void setmGenericType(List<ContactGenericType> mGenericType) {
        this.mGenericType = new ArrayList<>(mGenericType);
    }
}
