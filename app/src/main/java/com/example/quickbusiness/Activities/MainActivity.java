package com.example.quickbusiness.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickbusiness.Adapters.ProductAdapter;
import com.example.quickbusiness.Adapters.ShopAdapter;
import com.example.quickbusiness.Models.Product;
import com.example.quickbusiness.Models.Shop;
import com.example.quickbusiness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    DrawerLayout mainDrawer;
    FirebaseAuth mAuth;
    ShopAdapter shopAdapter;
    ArrayList<Shop> shopList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigationView);
        mainDrawer = (DrawerLayout) findViewById(R.id.mainDrawer);

//        popDummyData();
        setUpViews();
    }

//    private void popDummyData() {
//        shopList.add(new Shop("1","SHK SHop" ));
//        shopList.add(new Shop("1","SHK SHop" ));
//        shopList.add(new Shop("1","SHK SHop" ));
//        shopList.add(new Shop("1","SHK SHop" ));
//        shopList.add(new Shop("1","SHK SHop" ));
//    }

    private void setUpViews() {
        setUpDrawerLayout();
        addDataOfShop();
//        setUpRecyclerViewShop();
        setUpAddShopBtn();
    }

    private void setUpAddShopBtn() {
        FloatingActionButton addShop = findViewById(R.id.btnAddShop);
        addShop.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShopCreationActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Create the new Shop!", Toast.LENGTH_SHORT).show();
        });
    }

    private void setUpRecyclerViewShop() {
//        addDataOfShop(); // this extra call for function
        shopAdapter = new ShopAdapter(this, shopList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shopRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setAdapter(shopAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void addDataOfShop() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Shops");
        myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                    for (DataSnapshot s : dataSnapshot.getChildren()
                    ) {
                        Shop st = s.getValue(Shop.class);
                        Log.i(TAG, "All Shops Data: " + st);
                        shopList.add(st);
                        if(shopList.size() == 0){
                            Toast.makeText(MainActivity.this,"No shop Created Yet.", Toast.LENGTH_LONG).show();
                        }
                        setUpRecyclerViewShop();
                    }
            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to read value
//                      Log.w(TAG, "Failed to read value.", error.toException());
                        Toast.makeText(MainActivity.this, "Please make sure, you have internet connection!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setUpDrawerLayout() {
        setSupportActionBar(findViewById(R.id.appBar));
        actionBarDrawerToggle = new ActionBarDrawerToggle( this, findViewById(R.id.mainDrawer), R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
//        ImageView logo = (ImageView) findViewById(R.id.drawerLogo);
//        ImageView IMAge = (ImageView) findViewById(R.id.drawerLogo);
////        Context context;
//        String imageURL = "https://github.com/Sajid-Hameed63/Project_Picture_for_Glide/blob/main/Quick%20Business%20Logo.png";
//        String imageURL = "https://github.com/Sajid-Hameed63/Project_Picture_for_Glide/blob/main/Pictures/ic_quick_business_logo.xml";

//        Glide.with(this)
//                .load(imageURL)
//                .circleCrop()
//                .into((ImageView) findViewById(R.id.drawerLogo));

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.btnProfile){
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                mainDrawer.closeDrawers();
                return true;
            }
            else if (item.getItemId() == R.id.btnFollowUs){
                Intent intent = new Intent(this, FollowUsActivity.class);
                startActivity(intent);
                mainDrawer.closeDrawers();
                return true;
            }
            else if (item.getItemId() == R.id.btnRateUs){
                Intent intent = new Intent(this, RateUsActivity.class);
                startActivity(intent);
                mainDrawer.closeDrawers();
                return true;
            }
            else if (item.getItemId() == R.id.btnAboutUs){
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                mainDrawer.closeDrawers();
                return true;
            }
            else if (item.getItemId() == R.id.btnLogOut){
                logOut();
                return true;
            }
            return false;
        });
    }

    private void logOut() {
        mAuth.signOut();
        mainDrawer.closeDrawers();
        Intent intent = new Intent(this, LogIn_Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}