package com.example.ldy.weiyuweather.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ldy.weiyuweather.R;

/**
 * Created by LDY on 2016/10/4.
 */
public class TablePagerAdapter extends RecyclerView.Adapter<TablePagerAdapter.ViewHolder> {
    private String[] mData;
    public TablePagerAdapter(String[] data) {
        mData = data;
    }

    @Override
    public TablePagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TablePagerAdapter.ViewHolder holder, int position) {
        holder.txt.setText(mData[position].toString());
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt;

        public ViewHolder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.table_txt);
        }
    }
}
