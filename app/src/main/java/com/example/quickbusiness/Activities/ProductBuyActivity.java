package com.example.quickbusiness.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quickbusiness.Adapters.ProductAdapter;
import com.example.quickbusiness.Models.Product;
import com.example.quickbusiness.Models.Shop;
import com.example.quickbusiness.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ProductBuyActivity extends AppCompatActivity {
    Button payBillBTN, buyProductBTN;
    EditText editTextProductQuantity;
    ImageView productImage;
    TextView totalBIllLabel, product_NAME_to_display, productOriginalPriceLabel;
    String P_name, P_url, P_price;
    ArrayList<Product> dataOfProduct;
    String product_Name;
    String timeStamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_buy);
        payBillBTN = findViewById(R.id.btnPayBill);
        editTextProductQuantity = findViewById(R.id.etProductQuantity);
        buyProductBTN = findViewById(R.id.btnBuyProduct);
        product_NAME_to_display = findViewById(R.id.labelProductName);
        productImage = findViewById(R.id.productImage);
        productOriginalPriceLabel = findViewById(R.id.productPriceText);

        totalBIllLabel = findViewById(R.id.totalPrice);
        Intent intent = getIntent();
        product_Name = intent.getStringExtra("Date");
        timeStamp = intent.getStringExtra("Date2");
        setDataToScreen();
        buyProductBTN.setOnClickListener(v -> {
            buyProduct(); // but product and show the label of total bill.
        });
//        deleteProductBTN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ProductMainPageActivity.class);
//                startActivity(intent);
//                Toast.makeText(getApplicationContext(), "Product deleted.", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
        payBillBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payBill(); // function call for payment of bill
                Intent intent = new Intent(getApplicationContext(), ProductMainPageActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Product bought.", Toast.LENGTH_SHORT).show();
                finish();
            }

            private void payBill() {
                // function for payment of bill
            }
        });
    }

    private void setDataToScreen() {
        // setting data to views in buy screen.
//        ProductAdapter adapter = new ProductAdapter(this, )
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products").child(product_Name + "_" + timeStamp);
        reference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()
                ) {
                    int index = 0;
//                    Product st = s.getValue(Product.class);
//                    dataOfProduct.add(st);
                    Log.i(TAG, "All Products Retrieved data:" + s.getValue());
//                    dataOfProduct.set(index, s.getValue());


//                    Product p = s.getValue(Product.class);
//
//                    assert p != null;
//                    P_name = p.getProductName();
//                    P_url = p.getUrl();
//                    P_price = p.getProductPrice();



                    // have to retrieve product name, product url, product original price

//                    assert st != null;
//                    String P_name = (String) s.getValue();
//                    String P_description = (String) s.getValue();
//                    String P_expiryDate = (String) s.getValue();
//                    String P_url = (String) s.getValue();

//                    Log.i(TAG, "Product name" + P_name);
//                    Log.i(TAG, "Product description" + P_description);
//                    Log.i(TAG, "Product expiryDate" + P_expiryDate);
//                    Log.i(TAG, "Product url" + P_url);
//                    dataOfProduct.add(index, s.getValue());
//                    dataOfProduct.add(index,(String) s.getValue());
//                    productList.add(st);
//                    setUpRecyclerViewProduct();
                }
//                setDataToViews(P_name, P_url, P_price); // parameters to be passed P_name, P_description, P_expiryDate, P_url

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProductBuyActivity.this, "Failed to read value.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setDataToViews(String P_name, String P_url, String P_price) {
        product_NAME_to_display.setText(P_name);
        Glide.with(this).load(P_url).into(productImage);
        productOriginalPriceLabel.setText("Price of Product is " + P_price + " Rs.");
    }

    @SuppressLint("SetTextI18n")
    private void buyProduct() {
        String quantity = editTextProductQuantity.getText().toString();
        int totalBill = 0;
        String totalBillLabelLine = "Your total bill is ";
        // check empty fields
        int response = checkEmptyFields(quantity);
        if (response == 0) {
        }
        else{
            totalBIllLabel.setText(totalBillLabelLine + totalBill + " Rs.");
        }
    }

    private int checkEmptyFields(String quantity) {
        if(quantity.isEmpty()){
            editTextProductQuantity.setError("Enter Quantity!");
            editTextProductQuantity.requestFocus();
            return 0; // 0 means empty
        }
        return 1; // 1 means fields are not empty
    }
}