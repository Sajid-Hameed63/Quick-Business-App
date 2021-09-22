package com.example.quickbusiness.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickbusiness.Activities.MainActivity;
import com.example.quickbusiness.Activities.ProductBuyActivity;
import com.example.quickbusiness.Activities.ProductMainPageActivity;
import com.example.quickbusiness.Models.Product;
import com.example.quickbusiness.Models.Shop;
import com.example.quickbusiness.R;
import com.example.quickbusiness.Utils.ColorPicker;
import com.example.quickbusiness.Utils.ShopIconPicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

//public class ProductAdapter {
//}

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    Context context;
//    public String product_Name;
    ArrayList<Product> products;


    class ProductViewHolder extends RecyclerView.ViewHolder {
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        TextView textViewTitle = itemView.findViewById(R.id.productTitle);
        ImageView iconView = itemView.findViewById(R.id.productIcon);
        CardView cardContainer = itemView.findViewById(R.id.cardContainerProduct);
        ImageView delProductIcon = itemView.findViewById(R.id.deleteProductIcon);
    }

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        holder.delProductIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete").setMessage("Are you sure, you want to delete Product?").setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // begin deleting item
                        Toast.makeText(context, "deleting Product...", Toast.LENGTH_SHORT).show();
                        Product model = products.get(position);
                        String name = model.getProductName();
                        deleteProduct(model, holder);
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
//        String product_Name = products.get(position).getProductName()
//        Log.i(TAG, "product_Name: " + product_Name);

        holder.textViewTitle.setText(products.get(position).getProductName());
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()));
        holder.iconView.setImageResource(ShopIconPicker.getIcon());
        holder.itemView.setOnClickListener( v -> {
//            String product_Name = products.get(position).getProductName();
//              String product_Name = products.get(position).getProductName();
//            Log.i(TAG, "product_Name: " + product_Name);
//            Toast.makeText(context, products.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ProductBuyActivity.class);
            intent.putExtra("Date", products.get(position).getProductName());
            intent.putExtra("Date2", products.get(position).getTimeStamp());
//            Intent intent = new Intent(context, ProductBuyActivity.class);

            Toast.makeText(context, "Buy the Product!", Toast.LENGTH_SHORT).show();
            context.startActivity(intent);

        });
    }

    private void deleteProduct(Product model, ProductViewHolder holder) {
        // delete shop code here
        String name = model.getProductName();
        String timeStamp = model.getTimeStamp();


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Products");
        myRef.child(name + "_" + timeStamp)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // deleted successfully
                        Toast.makeText(context.getApplicationContext(), "Product deleted.", Toast.LENGTH_SHORT).show();
                        ProductAdapter adapter = new ProductAdapter(context, products);
                        Intent intent = new Intent(context, ProductMainPageActivity.class);
                        context.startActivity(intent);
////                        finish();
//                        adapter.notifyDataSetChanged();
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
        return products.size();
    }
}