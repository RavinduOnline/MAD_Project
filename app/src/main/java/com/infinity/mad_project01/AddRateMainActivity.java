package com.infinity.mad_project01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddRateMainActivity extends AppCompatActivity {

    TextView RateCount, RateDis;
    ImageView detailedImg;
    TextView itemName, ProductDis;
    RatingBar RatingBar;
    float rateValue;
    String message;
    Button PostBtn;

    FirebaseFirestore fStore;
    FirebaseAuth auth;

    PaymentComplete paymentComplete = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rate_main);

        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof PaymentComplete){
            paymentComplete = (PaymentComplete) object;
        }

        RateCount = findViewById(R.id.RateCount);
        RatingBar = findViewById(R.id.RatingBar);

        detailedImg = findViewById(R.id.detailedImg);

        itemName = findViewById(R.id.itemName);
        ProductDis = findViewById(R.id.ProductDis);

        if(paymentComplete != null){
            itemName.setText(paymentComplete.getProductName());
            ProductDis.setText(paymentComplete.getProductDescription());
        }

        RateDis = findViewById(R.id.RateDis);
        PostBtn = findViewById(R.id.Post);

        PostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                PostedRate();
            }
        });

        RatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                rateValue = RatingBar.getRating();

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

    }

    private void PostedRate() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH : mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> RateMap = new HashMap<>();

        RateMap.put("productName",paymentComplete.getProductName());
        RateMap.put("productDescription",paymentComplete.getProductDescription());
        RateMap.put("currentDate", saveCurrentDate);
        RateMap.put("currentTime", saveCurrentTime);
        RateMap.put("rateStar", rateValue);
        RateMap.put("rDescription", RateDis.getText().toString());


        fStore.collection("Rate").add(RateMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AddRateMainActivity.this, "Rate Successfully added!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ReviewMainPage.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddRateMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void BackBtn(View view) {
        Intent intent1 = new Intent(AddRateMainActivity.this, ReviewProject.class);
        startActivity(intent1);
    }
}