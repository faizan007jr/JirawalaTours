package com.example.pushpindersingh.jirawalatours;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {

    private List<HistoryRecyclerItem> historyRecyclerItems;
    private Context context;

    public HistoryRecyclerAdapter(List<HistoryRecyclerItem> historyRecyclerItems, Context context) {
        this.historyRecyclerItems = historyRecyclerItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //using inflate to access layout from other class
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_recycler,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final HistoryRecyclerItem menuList = historyRecyclerItems.get(i);

        //setting data for each recycler items
        viewHolder.tv_uname.setText("By: " + menuList.getUname());
        viewHolder.tv_loc.setText(menuList.getLocation());
        viewHolder.tv_hotel.setText(menuList.getHotel().replace("_", " "));
        viewHolder.tv_room.setText(menuList.getRoom());
        viewHolder.tv_client.setText(menuList.getClient());
        viewHolder.tv_date.setText(menuList.getDate());
    }

    @Override
    public int getItemCount() {
        return historyRecyclerItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_uname,tv_loc,tv_hotel,tv_room,tv_client,tv_date;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_uname = (TextView) itemView.findViewById(R.id.tv_uname);
            tv_loc = (TextView) itemView.findViewById(R.id.tv_loc);
            tv_hotel = (TextView) itemView.findViewById(R.id.tv_hotel);
            tv_room = (TextView) itemView.findViewById(R.id.tv_room);
            tv_client = (TextView) itemView.findViewById(R.id.tv_client);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}

