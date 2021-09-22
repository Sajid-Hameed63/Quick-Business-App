package com.example.quickbusiness.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quickbusiness.Models.Shop;
import com.example.quickbusiness.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ShopCreationActivity extends AppCompatActivity {
    Button btnCreateShop;
    EditText shopName, shopOwnerName, shopLocation;
    ImageView BtnDelShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_creation);
        btnCreateShop = (Button) findViewById(R.id.shopCreate);
        shopName = findViewById(R.id.etShopName);
        shopOwnerName = findViewById(R.id.etOwnerName);
        shopLocation = findViewById(R.id.etShopLocation);
        BtnDelShop = findViewById(R.id.deleteShopIcon);


        btnCreateShop.setOnClickListener(v -> {
            createShop();
        });
//        BtnDelShop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                builder.setTitle("Delete")
//                        .setMessage("Are you sure, you want to delete shop?")
//                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // begin deleting item
//                                Toast.makeText(getApplicationContext(), "deleting shop...", Toast.LENGTH_SHORT).show();
//                                deleteShop(holder, model);
//                            }
//                        })
//                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .show();
//            }
//        });
    }

//    private void deleteShop() {
//        // delete shop code here
//        String id = model.getId();
//        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Shops");
//        myRef.child(id)
//                .removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        // deleted successfully
//                        Toast.makeText(getApplicationContext(), "Shop deleted.", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // failed to delete
//                        Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    private void createShop() {
        // get the inputs to create shop from user
        String strShopName = shopName.getText().toString();
        String strShopOwnerName = shopOwnerName.getText().toString();
        String strShopLocation = shopLocation.getText().toString();


        // checking empty fields
        int response = checkEmptyFields(strShopName, strShopOwnerName, strShopLocation);

        if(response == 0){
            // empty fields
        }else{
            // non-empty fields
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStamp  = dateFormat.format(new Date());

            sendDataToRealTimeDB(strShopName, strShopOwnerName, strShopLocation, timeStamp); // sending data to RealTime Database
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Shop Created.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void sendDataToRealTimeDB(String strShopName_param,
                                      String strShopOwnerName_param,
                                      String strShopLocation_param, String timeStamp) {
//        long timestamp = System.currentTimeMillis();
        Shop shop = new Shop(strShopName_param, strShopOwnerName_param, strShopLocation_param, timeStamp);
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("Shop Name", strShopOwnerName_param);
//        hashMap.put("Shop Owner Name", strShopOwnerName_param);
//        hashMap.put("Shop Location", strShopLocation_param);
//        hashMap.put("timestamp", timestamp);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Shops");
        myRef.child(strShopName_param + "_" + timeStamp).setValue(shop);
    }

    private int checkEmptyFields(String strShopName,
                                  String strShopOwnerName,
                                  String strShopLocation) {
        if(strShopName.isEmpty()){
            shopName.setError("Shop Name is required!");
            shopName.requestFocus();
            return 0; // 0 means empty field
        }
        if(strShopOwnerName.isEmpty()){
            shopOwnerName.setError("Owner Name is required!");
            shopOwnerName.requestFocus();
            return 0; // 0 means empty field
        }
        if(strShopLocation.isEmpty()){
            shopLocation.setError("Shop Location is required!");
            shopLocation.requestFocus();
            return 0; // 0 means empty field
        }
        return 1;
    }
}