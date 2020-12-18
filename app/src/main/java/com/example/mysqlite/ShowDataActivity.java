package com.example.mysqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShowDataActivity extends AppCompatActivity {

    //Get Reference
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    DataBaseModel dataBaseModel;
    DataBaseHelper dataBaseHelper;
    ArrayList<DataBaseModel> arrayList;
    SQLiteDatabase sqLiteDatabase;
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);


        //Get The permission for read data
        dataBaseHelper = new DataBaseHelper(ShowDataActivity.this);
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();

        //finding ID
        listView = findViewById(R.id.listID);
        floatingActionButton = findViewById(R.id.floatingActionButtonID);

        try {
            //Create ArrayList for get data from database and set in ListView
            arrayList = new ArrayList<DataBaseModel>(dataBaseHelper.showAllData());
            adapter = new ListAdapter(this,arrayList);
            listView.setAdapter(adapter);
        }catch (Exception e){
            Log.e("error2","Error = "+e);
            e.printStackTrace();
        }

        //Set OnItemClick Listener For Update Data
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataBaseModel =arrayList.get(position);
                Intent intent = new Intent(ShowDataActivity.this,MainActivity.class);
                intent.putExtra("UPDATE",dataBaseModel);
                startActivity(intent);
                ShowDataActivity.this.finish();
            }
        });


        //Delete Data By LongClick
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Get The Clicked Item Position
                boolean deleted = dataBaseHelper.deleteData(arrayList.get(position));
                if (deleted==true){
                    Toast.makeText(ShowDataActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    ShowDataActivity.this.finish();
                    Intent intent = new Intent(ShowDataActivity.this,ShowDataActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(ShowDataActivity.this, "Fail To Delete", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        //Floating Button For Jump MainActivity and Add Data
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDataActivity.this,MainActivity.class);
                startActivity(intent);
                ShowDataActivity.this.finish();
            }
        });

    }

}