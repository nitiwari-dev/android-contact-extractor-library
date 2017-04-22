package com.bbmyjio.contactextractor.cmodels;

import android.graphics.Bitmap;

/**
 * Created by Nitesh on 21-04-2017.
 */

public class ItemData {


    private String title;
    private Bitmap imageBitmap;

    public ItemData(String title,Bitmap imageBitmap){

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
}
