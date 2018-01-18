/*
 *   Copyright (C) 2017 Nitesh Tiwari.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

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
