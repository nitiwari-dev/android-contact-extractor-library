package com.bbmyjio.contactextractor.cmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitesh on 19-04-2017.
 */

public class CPostBoxCity {

    public List<PostCity> mPostCity;


    public CPostBoxCity(List<PostCity> mPostCity) {
        this.mPostCity = new ArrayList<>(mPostCity);
    }

    public static class PostCity{
        public String post;
        public String city;

        public PostCity(String post, String city) {
            this.post = post;
            this.city = city;
        }
    }
}
