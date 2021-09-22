package com.example.quickbusiness.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.quickbusiness.Adapters.ProductAdapter;
import com.example.quickbusiness.Adapters.ShopAdapter;
import com.example.quickbusiness.Models.Product;
import com.example.quickbusiness.Models.Shop;
import com.example.quickbusiness.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class ProductMainPageActivity extends AppCompatActivity {
    ProductAdapter adapter;
    ArrayList<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_main_page);
//        popDummyData();
        setUpViews();
    }

//    private void popDummyData() {
//        ProductList.add(new Product("1","SHK Product" ));
//        ProductList.add(new Product("1","SHK Product" ));
//        ProductList.add(new Product("1","SHK Product" ));
//        ProductList.add(new Product("1","SHK Product" ));
//
//    }

    private void setUpViews() {
//        setUpRecyclerView();
        addDataOfProduct();
//        setUpRecyclerViewProduct();
        setUpAddProductBtn();
    }

    private void addDataOfProduct() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Products");
        myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()
                ) {
                    Product st = s.getValue(Product.class);
                    Log.i(TAG, "All Products Data:" + st);
                    productList.add(st);
                    setUpRecyclerViewProduct();
                }
            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to read value
//                      Log.w(TAG, "Failed to read value.", error.toException());
                        Toast.makeText(ProductMainPageActivity.this, "Failed to read value.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setUpAddProductBtn() {
        FloatingActionButton addProduct = findViewById(R.id.btnAddProducts);
        addProduct.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProductCreationActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Create the new Products!", Toast.LENGTH_SHORT).show();
        });
    }



    private void setUpRecyclerViewProduct() {
        adapter = new ProductAdapter(this, productList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ProductsRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}