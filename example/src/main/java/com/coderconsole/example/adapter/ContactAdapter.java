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

package com.coderconsole.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coderconsole.example.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<ItemData> mListItemData;
    private Context mContext;

    public ContactAdapter(Context context, List<ItemData> mListItemData) {
        this.mListItemData = mListItemData;
        this.mContext = context;
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_row_recylcer, null);


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.txtViewTitle.setText(mListItemData.get(position).getTitle());

        Bitmap bitmap = mListItemData.get(position).getImageBitmap();
        if (bitmap != null)
            viewHolder.imgViewIcon.setImageBitmap(mListItemData.get(position).getImageBitmap());
        else viewHolder.imgViewIcon.setImageResource(R.drawable.ic_account_box_black_24dp);



        String info = String.format(mContext.getString(R.string.contact_detail_info), mListItemData.get(position).getDetails());

        String data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data = Html.fromHtml(info, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            data = Html.fromHtml(info).toString();
        }
        viewHolder.txtViewDetails.setText(data);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewDetails;
        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            txtViewDetails = (TextView) itemLayoutView.findViewById(R.id.item_detail);

            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
        }
    }

    @Override
    public int getItemCount() {
        return mListItemData.size();
    }


}