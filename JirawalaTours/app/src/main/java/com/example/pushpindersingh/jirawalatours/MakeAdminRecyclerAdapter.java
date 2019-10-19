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

public class MakeAdminRecyclerAdapter extends RecyclerView.Adapter<MakeAdminRecyclerAdapter.ViewHolder> {

    private List<MakeAdminRecyclerItem> makeAdminRecyclerItems;
    private Context context;
    private GetRecyclerData getRecyclerData;

    public MakeAdminRecyclerAdapter(List<MakeAdminRecyclerItem> makeAdminRecyclerItems, Context context) {
        this.makeAdminRecyclerItems = makeAdminRecyclerItems;
        this.context = context;
        this.getRecyclerData = (GetRecyclerData) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //using inflate to access layout from other class
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.make_admin_recycler,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final MakeAdminRecyclerItem menuList = makeAdminRecyclerItems.get(i);

        //setting data for each recycler items
        viewHolder.tv_uname.setText(menuList.getUname());

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecyclerData.onGetMakeAdminRecyclerData(viewHolder.tv_uname.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return makeAdminRecyclerItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_uname;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_uname = (TextView) itemView.findViewById(R.id.tv_uname);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}

