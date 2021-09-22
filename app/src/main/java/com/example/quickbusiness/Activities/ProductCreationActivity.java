package com.example.quickbusiness.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickbusiness.Models.Product;
import com.example.quickbusiness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class ProductCreationActivity extends AppCompatActivity {
    Button addProductBTN;
    EditText productName, productPrice, productDescription, productExpiryDate;
    Button choosePhotoBTN;
    ImageView productImageSet;
    String url;
    private static  final int IMAG_PICK_CODE = 1000;
    private static  final int PERMISSION_CODE = 1001;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private final int PICK_IMAGE_CAMERA = 1;

    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_creation);
        addProductBTN = findViewById(R.id.btnAddProduct);
        productName = findViewById(R.id.etProductName);
        productPrice = findViewById(R.id.etProductPrice);
        productDescription = findViewById(R.id.etProductDescription);
        productExpiryDate = findViewById(R.id.etDate);
        productImageSet = findViewById(R.id.ProductPhotoSet);
        choosePhotoBTN = findViewById(R.id.btnGallery);

        choosePhotoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        addProductBTN.setOnClickListener(v -> {

            uploadImageToStorage();

        });
    }

    private void selectImage() {
        final CharSequence[] options = { "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo"))
//                {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                    startActivityForResult(intent, 1);
//
////                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                    startActivityForResult(takePicture, CAMERA_REQUEST);//zero can be replaced with any action code (called requestCode)
////
////                    dialog.dismiss();
////                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                    startActivityForResult(intent, PICK_IMAGE_CAMERA);
//                }
//                else
                if (options[item].equals("Choose from Gallery"))
                {
                    pickImageFromGallery();
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void pickImageFromGallery() {
        // pick Image from gallery
        // intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAG_PICK_CODE);
    }

    // handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    selectImage();
                } else {
                    // permission denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAG_PICK_CODE) {
            // set Image to view
            assert data != null;
            imageUri = data.getData(); // this line prints --> ImageURi: content://media/external/images/media/370703
            Log.i(TAG, "ImageURi: " + imageUri);
            productImageSet.setImageURI(imageUri);
//            Picasso.with(this).load(data.getData()).into(productImageSet);
        }
    }

    private void uploadImageToStorage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Adding Product...");
        pd.show();

        if(imageUri != null){
            StorageReference fileRef = FirebaseStorage.getInstance()
                    .getReference().child("Uploads/Product_Images")
                    .child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference myRef = database.getReference("Products");

            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();
                            Log.i(TAG, "getDownloadUrl: " + url);
                            addProduct();
                            pd.dismiss();
                            Toast.makeText(ProductCreationActivity.this, "Image upload successful!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void addProduct() {
        // get the inputs to create product from user
        String strProductName = productName.getText().toString();
        String strProductPrice = productPrice.getText().toString();
        String strProductDescription = productDescription.getText().toString();
        String strProductExpiryDate = productExpiryDate.getText().toString();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStamp  = dateFormat.format(new Date());

        // checking empty fields
        int response = checkEmptyFields(strProductName, strProductPrice, strProductDescription, strProductExpiryDate);
        if(response == 0){

        }else{
            sendDataToRealTimeDB(strProductName, strProductPrice, strProductDescription, strProductExpiryDate, timeStamp);

            Intent intent = new Intent(this, ProductMainPageActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Product Created.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void sendDataToRealTimeDB(String strProductName_param,
                                      String strProductPrice_param,
                                      String strProductDescription_param,
                                      String strProductExpiryDate_param, String timeStamp) {
        String sample_url = url;
        Product product = new Product(  strProductName_param,
                                        strProductPrice_param,
                                        strProductDescription_param,
                                        strProductExpiryDate_param,
                                        sample_url, timeStamp);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Products");
        myRef.child(strProductName_param + "_" + timeStamp ).setValue(product);
    }

    private int checkEmptyFields(String strProductName,
                                  String strProductPrice,
                                  String strProductDescription,
                                  String strProductExpiryDate) {
        if(strProductName.isEmpty()){
            productName.setError("Product Name is required!");
            productName.requestFocus();
            return 0; // 0 means empty field
        }
        if(strProductPrice.isEmpty()){
            productPrice.setError("Product Price is required!");
            productPrice.requestFocus();
            return 0; // 0 means empty field
        }
        if(strProductDescription.isEmpty()){
            productDescription.setError("Product Description is required!");
            productDescription.requestFocus();
            return 0; // 0 means empty field
        }
        if(strProductExpiryDate.isEmpty()){
            productExpiryDate.setError("Product Expiry Date is required!");
            productExpiryDate.requestFocus();
            return 0; // 0 means empty field
        }
        return 1; // 1 means non-empty field
    }
}