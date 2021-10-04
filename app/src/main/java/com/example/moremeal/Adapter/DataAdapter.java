package com.example.moremeal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moremeal.Interface.Callback;
import com.example.moremeal.Model_new.Pizza;
import com.example.moremeal.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    Context context;
    ArrayList<Pizza>arrayList;
    Callback callback;
    private int lastPosition = -1;

    public DataAdapter(Context context, ArrayList<Pizza> arrayList, Callback callback) {
        this.context = context;
        this.arrayList = arrayList;
        this.callback =callback;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v=layoutInflater.inflate(R.layout.item_layout,parent,false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        Pizza model = arrayList.get(position);
        Glide.with(context).load(model.getImageUrl()).into(holder.imageView);
        holder.tvname .setText(model.getName());
        holder.tvPrice.setText(model.getPrice());
        holder.tvDesc.setText(model.getDescription());


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(position);
            }
        });

        setAnimation(holder.itemView,position);



    }
    //for animation
    public void setAnimation(View viewToAnimate,int position){

        if (position > lastPosition){
            ScaleAnimation animation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            animation.setDuration(1500);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }




    public class DataViewHolder extends RecyclerView.ViewHolder{


        ImageView imageView;
        TextView tvname;
        TextView tvPrice;
        TextView tvDesc;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imageView);
            tvname= itemView.findViewById(R.id.tvName);
            tvPrice= itemView.findViewById(R.id.tvPrice);
            tvDesc= itemView.findViewById(R.id.tvDesc);
        }
    }
    public void searchItemName(ArrayList<Pizza> pizzaArrayList) {
        arrayList=pizzaArrayList;
        notifyDataSetChanged();
    }

}
