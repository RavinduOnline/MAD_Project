package com.infinity.mad_project01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class AddRate extends AppCompatActivity {

    TextView RateCount;
    EditText editText;
    Button PostBtn;
    RatingBar RatingStar;
    float rateValue;
    String message;
    private TextView itemName1, ProductDis1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rate);

        RateCount = findViewById(R.id.RateCount);
        RatingStar = findViewById(R.id.RatingStar);
        editText = findViewById(R.id.editText);
        PostBtn = findViewById(R.id.Post);

        itemName1= findViewById(R.id.itemName1);
        ProductDis1=findViewById(R.id.ProductDis);

        RetiriewData();

        RatingStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
           @Override
           public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

               rateValue = RatingStar.getRating();

               if (rateValue < 1 && rateValue > 0)
                    RateCount.setText("Very Sorry for your bad experience  " + rateValue + "/5");
               else if (rateValue < 2 && rateValue > 1)
                    RateCount.setText("Sorry for your experience  " + rateValue + "/5");
               else if (rateValue < 3 && rateValue > 2)
                    RateCount.setText("sorry  " + rateValue + "/5");
               else if (rateValue < 4 && rateValue > 3)
                    RateCount.setText("Happy about your experience  " + rateValue + "/5");
               else
                    RateCount.setText("Share with your friend, your experience  " + rateValue + "/5");
           }

        });


        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        PostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            message = editText.getText().toString();

            //validation
            if (rateValue==0){
               Toast.makeText(AddRate.this, "Pleas add the Rate", Toast.LENGTH_SHORT).show();
            }
            else if (message.isEmpty()) {
               Toast.makeText(AddRate.this, "Pleas enter the comment", Toast.LENGTH_SHORT).show();
            }else{

               DocumentReference docref = fStore.collection("UserRate").document("InfinityRating");

               Map<String, Object> Rate = new HashMap<>();
               Rate.put("rateStar", rateValue);
               Rate.put("rDescription", editText.getText().toString());
               Rate.put("PID",("J50kO4uw0lNDHSmULsjO"));
               Rate.put("UID",("NdWegkj6ICWF7JVeFOo3TE7CUgB3"));

               fStore.collection("UserRate").add(Rate).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
               @Override
               public void onSuccess(DocumentReference documentReference) {
                   Toast.makeText(AddRate.this, "Rate Successfully added!", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(getApplicationContext(), ReviewMainPage.class));
                   finish();
               }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                      Toast.makeText(AddRate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
               });
            }}
        });
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

    public void BackBtn(View view) {
        Intent intent1 = new Intent(AddRate.this, ReviewProject.class);
        startActivity(intent1);
    }
}

