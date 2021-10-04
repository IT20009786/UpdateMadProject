package com.example.moremeal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public static  final String EXTRA_MESSAGE= "com.example.myfirstapp.MESSAGE";
    public static  final String EXTRA_MESSAGE1= "com.example.myfirstapp.MESSAGE1";
    public static  final String EXTRA_MESSAGE2= "com.example.myfirstapp.MESSAGE2";
    public static  final String EXTRA_MESSAGE3= "com.example.myfirstapp.MESSAGE3";
    public static  final String EXTRA_MESSAGE4= "com.example.myfirstapp.MESSAGE4";
    public static  final String EXTRA_MESSAGE5= "com.example.myfirstapp.MESSAGE5";
    public static  final String EXTRA_MESSAGE6= "com.example.myfirstapp.MESSAGE6";
    public static  final String EXTRA_MESSAGE7= "com.example.myfirstapp.MESSAGE7";
    private Context mContext ;
    private ArrayList<Model> list;
    private ArrayList<String> myKeys;
    View view;
    Model temp;
    Model temp2;
    Model temp3;
    DatabaseReference ref;
    TextView myTextView;
    Integer Number;
    String Number2;
    Double Number3;

    public MyAdapter( ArrayList<Model> list){


        this.list = list;



    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        String key2 = list.get(position).getMyd();
        Double price = list.get(position).getNowPrice();
        String mySize = list.get(position).getNowSize();
        String txt = list.get(position).getName() +" "+ price;

        holder.name.setText(txt);
        // holder.name.setText(key2);
        holder.tSize.setText(mySize);
        holder.imageView.setImageResource(list.get(position).getImage());

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = new Model(list.get(position).getName(), list.get(position).getImage(), list.get(position).getQuantity(),
                        list.get(position).getNowPrice(), list.get(position).getNowSize(), list.get(position).getMediumPrice(), list.get(position).getLargePrice(), list.get(position).getPremiumPrice()
                        , list.get(position).getMyd());

                //RelativeLayout layout = R.layout.activity_cart_view;
                // CartViewActivity mob = new CartViewActivity();

                //i think this is the key for delete item
                String delKey = list.get(position).getMyd();
                deleteItem(position);
                deleteItemOnDb(delKey);
                String nme = temp.getName();

                //TextView removeChanged;

                //calling a method to delete this item in firebase
                //  deleteItemOnDb(nme);

            }
        });

        holder.purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                temp2 = new Model(list.get(position).getName(), list.get(position).getImage(), list.get(position).getQuantity(),
                        list.get(position).getNowPrice(), list.get(position).getNowSize(), list.get(position).getMediumPrice(), list.get(position).getLargePrice(), list.get(position).getPremiumPrice()
                        , list.get(position).getMyd());
                String nme = temp2.getName();
                Double price = temp2.getNowPrice();
                Integer quantity = temp2.getQuantity();
                String pr = ""+price;

                Intent intent;
                Context cn = CartViewActivity.getMyContext();
                intent = new Intent(cn, AfterCartActivity.class);
                intent.putExtra(EXTRA_MESSAGE, pr);
                cn.startActivity(intent);

            }
        });


        holder.custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                temp3 = new Model(list.get(position).getName(), list.get(position).getImage(), list.get(position).getQuantity(),
                        list.get(position).getNowPrice(), list.get(position).getNowSize(), list.get(position).getMediumPrice(), list.get(position).getLargePrice(), list.get(position).getPremiumPrice()
                        , list.get(position).getMyd());

                String updateKey = list.get(position).getMyd();
                String uItemName = list.get(position).getName();
                Integer uQuantity = list.get(position).getQuantity();
                String uSize = list.get(position).getNowSize();
                Double uNowPrice = list.get(position).getNowPrice();
                Double uMedium = list.get(position).getMediumPrice();
                Double uLarge = list.get(position).getLargePrice();
                Double uPremium = list.get(position).getPremiumPrice();


                String passQuantity = ""+uQuantity;
                String passNowPrice = ""+uNowPrice;
                String passMedium = ""+uMedium;
                String passLarge = ""+uLarge;
                String passPremium = ""+uPremium;

                Intent intent;
                Context cn = CartViewActivity.getMyContext();
                intent = new Intent(cn, UpdateItemActivity.class);
                intent.putExtra(EXTRA_MESSAGE, updateKey);
                intent.putExtra(EXTRA_MESSAGE1, passQuantity);
                intent.putExtra(EXTRA_MESSAGE2, uItemName);
                intent.putExtra(EXTRA_MESSAGE3, uSize);
                intent.putExtra(EXTRA_MESSAGE4, passMedium);
                intent.putExtra(EXTRA_MESSAGE5, passLarge);
                intent.putExtra(EXTRA_MESSAGE6, passPremium);
                intent.putExtra(EXTRA_MESSAGE7, passNowPrice);

                cn.startActivity(intent);


            }
        });
        /*
        holder.menuPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.edit:
                               // Toast.makeText(mContext, "Edit Clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.delete:
                               // Toast.makeText(mContext, "Delete Clicked", Toast.LENGTH_SHORT).show();
                                temp = new Model(list.get(position).getName(), list.get(position).getImage(), list.get(position).getQuantity(),
                                         list.get(position).getNowPrice());
                                deleteItem(position);
                                break;

                        }

                        return true;
                    }
                });
            }
        });
*/
    }

    private void deleteItem(int position){

        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());



    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder  extends  RecyclerView.ViewHolder{
        ImageView imageView, close;
        TextView name;
        Button purchase;
        TextView tSize;
        Button custom;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            close = itemView.findViewById(R.id.menuMore);
            name = itemView.findViewById(R.id.textView);
            purchase = itemView.findViewById(R.id.purchaseitem);
            tSize = itemView.findViewById(R.id.sizetextview);
            custom = itemView.findViewById(R.id.customizeitem);
        }
    }




    public void deleteItemOnDb(String key){
        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Model");


        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild(key)){


                    ref = FirebaseDatabase.getInstance().getReference().child("Model").child(key);
                    ref.removeValue();
                    //Toast.makeText(mContext,);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }





}
