package com.example.firestorecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    EditText title,description;
    Button   submit,listactivity;
    FirebaseFirestore  db;
    ProgressDialog pd;
    String titletext,descriptiontext,id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        title = (EditText) findViewById(R.id.title);
        description=(EditText) findViewById(R.id.Description);
        submit= (Button) findViewById(R.id.submit);
        listactivity=(Button) findViewById(R.id.gotonextActivity);
        pd=new ProgressDialog(this);
        db=FirebaseFirestore.getInstance();

        Bundle bundle =getIntent().getExtras();
        if(bundle!= null){
            submit.setText("Update");
            titletext=bundle.getString("title");
            descriptiontext=bundle.getString("description");
            id=bundle.getString("id");
            title.setText(titletext);
            description.setText(descriptiontext);
        }else {
            submit.setText("SAVE");

        }





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle1=getIntent().getExtras();
                if(bundle1!=null){

                    titletext = title.getText().toString().trim();
                    descriptiontext = description.getText().toString().trim();
                    updateData(id,titletext,descriptiontext);


                }else{
                    titletext = title.getText().toString().trim();
                    descriptiontext = description.getText().toString().trim();
                    uploadData(titletext,descriptiontext);
                }








            }
        });

        listactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ListActivity.class));
                finish();


            }
        });




    }

    private void updateData(String id, String titletext, String descriptiontext) {

        pd.setTitle("updating Data");
        pd.show();

        db.collection("documnet").document(id).update("title",titletext,"description",descriptiontext)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"value is Updated",Toast.LENGTH_SHORT).show();
                        title.setText("");
                        description.setText("");


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();

                Toast.makeText(getApplicationContext(),"Something Wrong",Toast.LENGTH_SHORT).show();
            }
        });



    }

    protected void uploadData(String titletext, String descriptiontext) {
     pd.setTitle("ADDING VALUE IN DB");
     pd.show();
     id= UUID.randomUUID().toString();

        Map<String,Object> doc=new HashMap<>();
        doc.put("id",id);
        doc.put("title",titletext);
        doc.put("description",descriptiontext);
        db.collection("documnet").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"value added",Toast.LENGTH_SHORT).show();
                title.setText("");
                description.setText("");


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Something Wrong",Toast.LENGTH_SHORT).show();

            }
        });








    }
}
