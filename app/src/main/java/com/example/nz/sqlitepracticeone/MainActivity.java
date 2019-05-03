package com.example.nz.sqlitepracticeone;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MySQLite mySQLite;
    EditText userName, userAge, userGendear,userId;
    Button saveButton, showButton,clearButton,updatButton,deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create SQLite Table ....
        mySQLite = new MySQLite(this);
        SQLiteDatabase sqLiteDatabase = mySQLite.getWritableDatabase();

//Edit Text ....
        userName= (EditText) findViewById(R.id.nameEditText);
        userAge = (EditText) findViewById(R.id.ageEditText);
        userGendear = (EditText) findViewById(R.id.genderEditText);
        userId = (EditText) findViewById(R.id.idEditText);
        // button ......
        saveButton = (Button) findViewById(R.id.saveButton);
        showButton = (Button) findViewById(R.id.showButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        updatButton = (Button) findViewById(R.id.updateButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        // close .....

        // set Listenar ......
        saveButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        updatButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String name = userName.getText().toString();
        String age = userAge.getText().toString();
        String gendear = userGendear.getText().toString();
        String id = userId.getText().toString();

// clear button .....
        if(v.getId() == R.id.clearButton){
            userName.setText("");
            userAge.setText("");
            userGendear.setText("");
        }
 // save Button .....
        else  if (v.getId() == R.id.saveButton){
           long rowId =  mySQLite.insertData(name,age,gendear);
           if(rowId == -1 ){
               Toast.makeText(this,"Data save Failed",Toast.LENGTH_LONG).show();
           }else {

               Toast.makeText(this,"Data successfully saved",Toast.LENGTH_LONG).show();
           }
        }
 // show button ......

        else  if (v.getId() == R.id.showButton){
           Cursor cursor = mySQLite.showData();
           if(cursor.getCount() ==0){
              prepairAllData("Sorry!!! ", "Their no Data ");
               // thar is no value ....
           }

           StringBuffer stringBuffer = new StringBuffer();
           while (cursor.moveToNext()){
               stringBuffer.append("\n"+"Id : "+cursor.getString(0)+"\n");
               stringBuffer.append("Name : "+cursor.getString(1)+"\n");
               stringBuffer.append("Age : "+cursor.getString(2)+"\n");
               stringBuffer.append("Gender : "+cursor.getString(3)+"\n\n________\n");
                    // call method to show data .....
               prepairAllData("SQLite Data ",stringBuffer.toString());
           }
        }

  // update Button ...
         else  if (v.getId() == R.id.updateButton){
          boolean isValue = mySQLite.updateData(id,name,age,gendear);
          if(isValue == true){
              Toast.makeText(this,"Data is updated \n Successfully",Toast.LENGTH_LONG).show();
          }else {
              Toast.makeText(this,"Data update failed ",Toast.LENGTH_LONG).show();
          }
        }

        // delete button....

        else if(v.getId() == R.id.deleteButton){
          int value =   mySQLite.delteData(id);
          if(value > 0){
              Toast.makeText(this,"Data is deleted ",Toast.LENGTH_LONG).show();
          }else {
              Toast.makeText(this,"Sorry Their is no Data",Toast.LENGTH_LONG).show();
          }
        }



    }
// this method use for showButton .......
    public void  prepairAllData(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();

    }
}
