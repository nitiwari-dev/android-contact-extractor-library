package com.coderconsole.cextracter.cmodels;

import java.util.HashSet;

/**
 * Contacts Groups Information
 *
 * Created by Nitesh on 19-04-2017.
 */

public class CGroups {
    private HashSet<BaseGroups> mList = new HashSet<>();

    public HashSet<BaseGroups> getmList() {
        return mList;
    }

    public void setmList(HashSet<BaseGroups> mList) {
        this.mList = mList;
    }

    public static class BaseGroups {
        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
