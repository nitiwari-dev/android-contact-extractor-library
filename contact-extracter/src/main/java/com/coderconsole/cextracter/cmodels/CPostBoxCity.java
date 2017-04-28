package com.coderconsole.cextracter.cmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Contact Post and City Information
 *
 * Created by Nitesh on 19-04-2017.
 */

public class CPostBoxCity {

    private List<PostCity> mPostCity = new ArrayList<>();

    public List<PostCity> getmPostCity() {
        return mPostCity;
    }

    public void setmPostCity(List<PostCity> mPostCity) {
        this.mPostCity = mPostCity;
    }

    public CPostBoxCity() {
        this.mPostCity = new ArrayList<>(mPostCity);
    }

    public static class PostCity {
        private String post;
        private String city;

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
