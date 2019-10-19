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

public class HotelRecyclerAdapter extends RecyclerView.Adapter<HotelRecyclerAdapter.ViewHolder> {

    private List<HotelRecyclerItem> hotelRecyclerItems;
    private Context context;
    private GetRecyclerData getRecyclerData;

    public HotelRecyclerAdapter(List<HotelRecyclerItem> hotelRecyclerItems, Context context) {
        this.hotelRecyclerItems = hotelRecyclerItems;
        this.context = context;
        this.getRecyclerData = (GetRecyclerData) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //using inflate to access layout from other class
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_recycler,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final HotelRecyclerItem menuList = hotelRecyclerItems.get(i);

        //setting data for each recycler items
        viewHolder.p_id.setText(String.valueOf(menuList.getId()));
        viewHolder.name.setText(menuList.getName());
        viewHolder.rent.setText(String.valueOf(menuList.getRent()));
        viewHolder.av.setText(String.valueOf(menuList.getAv()));

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecyclerData.onGetRecyclerData(Integer.parseInt(viewHolder.p_id.getText().toString()),
                        viewHolder.name.getText().toString(),Integer.parseInt(viewHolder.rent.getText().toString()),
                        Integer.parseInt(viewHolder.av.getText().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelRecyclerItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView p_id;
        public TextView name;
        public TextView rent;
        public TextView av;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            p_id = (TextView) itemView.findViewById(R.id.pId);
            name = (TextView) itemView.findViewById(R.id.name);
            rent = (TextView) itemView.findViewById(R.id.rent);
            av = (TextView) itemView.findViewById(R.id.av);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}

