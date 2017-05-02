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
 * Contact Email information
 *
 * Created by Nitesh on 04-04-2017.
 */

public class CEmail {

    private HashSet<String> work = new HashSet<>();
    private HashSet<String> home = new HashSet<>();
    private HashSet<String> mobile = new HashSet<>();
    private HashSet<String> other = new HashSet<>();
    private String photoUri;

    public HashSet<String> getOther() {
        return other;
    }

    public void setOther(HashSet<String> other) {
        this.other = other;
    }

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

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String phonoUri) {
        this.photoUri = phonoUri;
    }
}
