package com.bbmyjio.contactextractor.adapter;

import android.graphics.Bitmap;

/**
 * Created by Nitesh on 21-04-2017.
 */

public class ItemData {


    private String title;
    private String details;
    private Bitmap imageBitmap;

    public ItemData(String title, String details, Bitmap imageBitmap) {
        this.details = details;
        this.title = title;
        this.imageBitmap = imageBitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
