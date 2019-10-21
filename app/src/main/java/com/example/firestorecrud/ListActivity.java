package com.example.firestorecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

List<Model> modelList=new ArrayList<>();
ProgressDialog pd;
RecyclerView recyclerView;
RecyclerView.LayoutManager  layoutManager;
FirebaseFirestore db;
CustomAdapter customAdapter;
Button AddItemActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        pd=new ProgressDialog(this);
        db=FirebaseFirestore.getInstance();


        recyclerView = findViewById(R.id.rv_list);
        AddItemActivity=findViewById(R.id.AddmoreItem);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        showData();


        AddItemActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListActivity.this,MainActivity.class));
                finish();

            }
        });






    }

    private void showData() {
        pd.setTitle("Fetching Data");
        pd.show();
        db.collection("documnet").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot doc:task.getResult()){
                    Model modelpass= new Model(doc.getString("id"),doc.getString("title"),doc.getString("description"));
                    modelList.add(modelpass);
                }

                customAdapter=new CustomAdapter(ListActivity.this,modelList);
                recyclerView.setAdapter(customAdapter);
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        });








    }
    public void DeleteData(int Index){
        pd.setTitle("Deleting Data");
        pd.show();

        db.collection("documnet").document(modelList.get(Index).getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                showData();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
            }
        });



    }







}
