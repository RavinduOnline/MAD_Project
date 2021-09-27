package com.infinity.mad_project01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReviewProject extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<PaymentComplete> RateArrayList;
    RateAdapter rateAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_project);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data..");
        progressDialog.show();

        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db=FirebaseFirestore.getInstance();
        RateArrayList= new ArrayList<PaymentComplete>();
        rateAdapter = new RateAdapter(ReviewProject.this,RateArrayList);

        recyclerView.setAdapter(rateAdapter);
        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("products").orderBy("productName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    DocumentReference documentReference;
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc: value.getDocumentChanges()){
                            if(dc.getType()== DocumentChange.Type.ADDED){

                                RateArrayList.add(dc.getDocument().toObject(PaymentComplete.class));

                            }

                            rateAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }


                    }
                });
    }

    public void BackArrow(View view) {
        Intent intent1 = new Intent(ReviewProject.this, ReviewMainPage.class);
        startActivity(intent1);
        finish();
    }

    public void RateBtn(View view) {
        Intent intent2 = new Intent(ReviewProject.this, AddRateMainActivity.class);
        startActivity(intent2);
    }

    public void CloseBtn(View view) {
        Intent intent3 = new Intent(ReviewProject.this, ReviewMainPage.class);
        startActivity(intent3);
        finish();
    }
}