package com.example.mysqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {


    //Get Reference
    private EditText nameE,ageE,professionE;
    private Button addB,showB;
    String name,age,profession,updateName,updateAge,updateProfession;
    int id;
    DataBaseModel dataBaseModel;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    ShowDataActivity showDataActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialise ID
        findingID();
        showDataActivity = new ShowDataActivity();
        //Get The permission for write data
        dataBaseHelper = new DataBaseHelper(this);
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();

        dataBaseModel  = (DataBaseModel) getIntent().getSerializableExtra("UPDATE");
        if (dataBaseModel!=null){
            addB.setText("Update");
            nameE.setText(dataBaseModel.getName());
            ageE.setText(String.valueOf(dataBaseModel.getAge()));
            professionE.setText(dataBaseModel.getProfession());
        }

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Update Data
                if (dataBaseModel!=null){
                    id = dataBaseModel.getId();
                    updateName = nameE.getText().toString();
                    updateAge = ageE.getText().toString();
                    updateProfession =professionE.getText().toString();
                    DataBaseModel updateDataBaseModel = new DataBaseModel(id,updateName,updateAge,updateProfession);
                    boolean updated = dataBaseHelper.updateData(updateDataBaseModel);
                    if (updated==true){
                        MainActivity.this.finish();
                        Intent intent = new Intent(MainActivity.this,ShowDataActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "Fail To Update Data", Toast.LENGTH_SHORT).show();
                    }
                }
                //Add new data
                else {
                    name = nameE.getText().toString();
                    age = ageE.getText().toString();
                    profession = professionE.getText().toString();

                    if (name.isEmpty()){
                        nameE.setError("Enter Name");
                        nameE.requestFocus();
                    }else if (ageE==null){
                        ageE.setError("Enter Age");
                        ageE.requestFocus();
                    }else if (profession.isEmpty()){
                        nameE.setError("Enter Profession");
                        nameE.requestFocus();
                    }else {
                        dataBaseModel = new DataBaseModel(name,age,profession);
                        long rowID = dataBaseHelper.addData(dataBaseModel);
                        if (rowID>0){
                            Toast.makeText(MainActivity.this, "Added : "+name, Toast.LENGTH_SHORT).show();
                            MainActivity.this.finish();
                            Intent intent = new Intent(MainActivity.this,ShowDataActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this,"Not Added", Toast.LENGTH_SHORT).show();
                        }
                    }
                }




            }
        });

/*
        //Intent For Show Data
        showB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowDataActivity.class);
                startActivity(intent);
            }
        });*/

    }

    //Initialise ID
    private void findingID() {
        nameE = findViewById(R.id.nameEditText);
        ageE = findViewById(R.id.ageEditText);
        professionE = findViewById(R.id.professionEditText);
        addB = findViewById(R.id.addButton);
//        showB = findViewById(R.id.showButton);
    }



}