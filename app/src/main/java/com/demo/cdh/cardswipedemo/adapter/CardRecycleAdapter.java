package com.demo.cdh.cardswipedemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.demo.cdh.cardswipedemo.R;
import com.demo.cdh.cardswipedemo.adapter.animat.SwipeItemAnimator;
import com.demo.cdh.cardswipedemo.adapter.base.BaseRecyclerAdapter;
import com.demo.cdh.cardswipedemo.adapter.base.ViewHolder;
import com.demo.cdh.cardswipedemo.bean.CardBean;

import java.util.List;

/**
 * Created by hang on 2017/4/27.
 */

public class CardRecycleAdapter extends BaseRecyclerAdapter<CardBean> {

    public CardRecycleAdapter(Context context, List<CardBean> data) {
        super(context, data);
        putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_card);
    }

    @Override
    public void onBind(final ViewHolder holder, final CardBean item, final int position) {
        holder.setImageResource(R.id.ivCover, item.cover);
        CardView cv=holder.getView(R.id.main);
        if (position%3==0){
            cv.setCardBackgroundColor(Color.WHITE);
        }else  if (position%3==1){
            cv.setCardBackgroundColor(Color.parseColor("#DFCDE5"));//Color.argb(0x29,0xff, 0xff,0xff)
        }else{
            cv.setCardBackgroundColor(Color.parseColor("#B790C8"));//Color.argb(0x53,0xff, 0xff,0xff)
        }
        holder.getView(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "不喜欢", Toast.LENGTH_SHORT).show();
                holder.itemView.setTag(SwipeItemAnimator.SWIPE_REMOVE_LEFT);
                remove(item);
                notifyDataSetChanged();
//                mData.add(item);
            }
        });

        holder.getView(R.id.btnLike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "喜欢", Toast.LENGTH_SHORT).show();
                holder.itemView.setTag(SwipeItemAnimator.SWIPE_REMOVE_RIGHT);
                remove(item);
//                mData.add(item);
                notifyDataSetChanged();
            }
        });
    }
}
