package com.example.moremeal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moremeal.Interface.OffersCallback;
import com.example.moremeal.Model_new.Offers;
import com.example.moremeal.R;

import java.util.ArrayList;

public class OffersDataAdapter extends RecyclerView.Adapter<OffersDataAdapter.OffersDataViewHolder> {
    Context context;
    ArrayList<Offers>arrayList;
    OffersCallback callback;

    public OffersDataAdapter(Context context, ArrayList<Offers> arrayList, OffersCallback callback) {
        this.context = context;
        this.arrayList = arrayList;
        this.callback=callback;
    }

    @NonNull
    @Override
    public OffersDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.offers_item_layout,parent,false);

        return new OffersDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersDataViewHolder holder, int position) {
        Offers model =arrayList.get(position);
        Glide.with(context).load(model.getImageUrl()).into(holder.imageView);
        holder.tvname.setText(model.getName());
        holder.tvDesc.setText(model.getName());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }




    public class OffersDataViewHolder extends RecyclerView.ViewHolder{


        ImageView imageView;
        TextView tvname;
        TextView  tvDesc;



        public OffersDataViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            tvname=itemView.findViewById(R.id.tvName);
            tvDesc=itemView.findViewById(R.id.tvDesc);
        }
    }


    public void searchItemName(ArrayList<Offers> offersArrayList) {
        arrayList=offersArrayList;
        notifyDataSetChanged();


    }
}
