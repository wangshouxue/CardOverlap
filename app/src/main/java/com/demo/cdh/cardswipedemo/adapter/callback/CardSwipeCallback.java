package com.demo.cdh.cardswipedemo.adapter.callback;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.cdh.cardswipedemo.adapter.CardRecycleAdapter;
import com.demo.cdh.cardswipedemo.adapter.manager.CardSwipeLayoutManager.CardConfig;
import com.demo.cdh.cardswipedemo.bean.CardBean;

/**
 * Created by hang on 2017/4/27.
 */

public class CardSwipeCallback extends ItemTouchHelper.Callback {//ItemTouchHelper.SimpleCallback

    private Context context;
    private CardRecycleAdapter adapter;

//    public CardSwipeCallback(Context context, CardRecycleAdapter adapter) {
//        super(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT|ItemTouchHelper.UP|ItemTouchHelper.DOWN);
//        this.context = context;
//        this.adapter = adapter;
//    }
    public CardSwipeCallback(Context context, CardRecycleAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (adapter.getItemCount()-1==viewHolder.getLayoutPosition()){
            //最后一个item不可滑动
            return 0;
        }
        return makeMovementFlags(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT|ItemTouchHelper.UP|ItemTouchHelper.DOWN);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //不能拖拽
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(direction==ItemTouchHelper.LEFT) {
            Toast.makeText(context, "不喜欢", Toast.LENGTH_SHORT).show();
        } else  if(direction==ItemTouchHelper.RIGHT) {
            Toast.makeText(context, "喜欢", Toast.LENGTH_SHORT).show();
        }else{
            if (isToLeft){
                Toast.makeText(context, "不喜欢", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "喜欢", Toast.LENGTH_SHORT).show();
            }
        }

        //移除滑出的item添加到尾部
        CardBean item = adapter.getmData().remove(viewHolder.getLayoutPosition());
        //item循环的关键
//        adapter.getmData().add(item);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    boolean isToLeft=false;
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dX!=0.0){//往上往下时最终都会归为0，所以要排除
            if (dX>0){
                isToLeft=false;
            }else{
                isToLeft=true;
            }
        }
        //设置滑动临界值，避免无限偏移缩放
        double maxDistance = recyclerView.getWidth() / 2;
        //当前滑动距离
        double distance = Math.sqrt(dX*dX + dY*dY);
        //计算比例
        double ratio = distance / maxDistance;
        if(ratio > 1) {
            ratio = 1;
        }

        //获取当前recyclerview显示item的个数，遍历计算偏移和缩放
        int count = recyclerView.getChildCount();
        for (int i=0; i<count; i++) {
            View child = recyclerView.getChildAt(i);
            //越前面越底层
            int level = count - i - 1;
            //最底层的view不需要改变
            if(level != count-1) {
                //在原来的位置下加上偏移量
                child.setTranslationY((float) (CardConfig.TRANS_Y_GAP * (level-ratio)));
                child.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + CardConfig.SCALE_GAP*ratio));
                child.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + CardConfig.SCALE_GAP*ratio));
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }
    //拖动的时候，位于其他item上的时候绘制
    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
//            final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
//            viewHolder.itemView.setAlpha(0.5f);
//        }else if(actionState==ItemTouchHelper.ACTION_STATE_IDLE){
//            viewHolder.itemView.setAlpha(0.1f);
//        }else if(actionState==ItemTouchHelper.ACTION_STATE_DRAG){
//            final float alpha = 1 - Math.abs(dX) / (float)viewHolder.itemView.getWidth();
//            viewHolder.itemView.setAlpha(0.5f);
//        }
    }
}
