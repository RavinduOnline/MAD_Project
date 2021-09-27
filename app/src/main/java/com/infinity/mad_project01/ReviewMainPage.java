package com.infinity.mad_project01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ReviewMainPage extends AppCompatActivity {

    public static final String TAG = "TAG";
    private TextView itemName1, ProductDis1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_main_page);

        itemName1= findViewById(R.id.itemName1);
        ProductDis1=findViewById(R.id.ProductDis);

        RetiriewData();

    }


    private void RetiriewData() {

        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        DocumentReference documentReference=fStore.collection("products").document("x3E6rF5cscld2qSAQUo8");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot != null) {
                    itemName1.setText(documentSnapshot.getString("productName"));
                    ProductDis1.setText(documentSnapshot.getString("productDescription"));
                }

            }
        });
    }

    public  void DeleteRateBtn(View view){
        FirebaseFirestore.getInstance().collection("UserRate")
                .document("dfa2iPsOVdmOW8UYwS8A")
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "OnSuccess : Delete Successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReviewMainPage.this, "Something went wrong Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void ToBeRevievedBtn(View view) {
        Intent intent1 = new Intent(ReviewMainPage.this, ReviewProject.class);
        startActivity(intent1);
    }


}