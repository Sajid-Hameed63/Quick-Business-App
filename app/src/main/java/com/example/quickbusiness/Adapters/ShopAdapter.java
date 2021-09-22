package com.example.quickbusiness.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickbusiness.Activities.ProductMainPageActivity;
import com.example.quickbusiness.Models.Shop;
import com.example.quickbusiness.R;
import com.example.quickbusiness.Utils.ColorPicker;
import com.example.quickbusiness.Utils.ShopIconPicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    Context context;
    ArrayList<Shop> shops;

    class ShopViewHolder extends RecyclerView.ViewHolder {
        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView textViewTitle = itemView.findViewById(R.id.shopTitle);
        ImageView iconView = itemView.findViewById(R.id.shopIcon);
        CardView cardContainer = itemView.findViewById(R.id.cardContainerShop);
        ImageView delShopIcon = itemView.findViewById(R.id.deleteShopIcon);
    }

    public ShopAdapter(Context context, ArrayList<Shop> shops) {
        this.context = context;
        this.shops = shops;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_item, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ShopViewHolder holder, int position) {
        holder.delShopIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete").setMessage("Are you sure, you want to delete shop?").setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // begin deleting item
                                Toast.makeText(context, "deleting shop...", Toast.LENGTH_SHORT).show();
                                Shop model = shops.get(position);
                                String name = model.getName();
                                deleteShop(model, holder);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        holder.textViewTitle.setText(shops.get(position).getName());
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()));
        holder.iconView.setImageResource(ShopIconPicker.getIcon());
        holder.itemView.setOnClickListener( v -> {
            Toast.makeText(context, shops.get(position).getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ProductMainPageActivity.class);
            intent.putExtra("Date", shops.get(position).getName());
            context.startActivity(intent);
        });

//        Shop model = shops.get(position);
//        String name = model.getName();

    }

    private void deleteShop(Shop model, ShopViewHolder holder) {
        // delete shop code here
        String name = model.getName();
        String timeStamp = model.getTimeStamp();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Shops");
        myRef.child(name + "_" + timeStamp)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // deleted successfully
                        Toast.makeText(context.getApplicationContext(), "Shop deleted.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, ProductMainPageActivity.class);
                        context.startActivity(intent);
//                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // failed to delete
                        Toast.makeText(context.getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }
}
