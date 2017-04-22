package com.bbmyjio.contactextractor.cmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitesh on 19-04-2017.
 */

public class CGroups {
    List<BaseGroups> mList = new ArrayList<>();

    public CGroups(List<BaseGroups> mList) {
        this.mList = new ArrayList<>(mList);
    }

    public static class BaseGroups{
        public int id;
        public String title;

        public BaseGroups(int id, String title) {
            this.id = id;
            this.title = title;
        }
    }
}
