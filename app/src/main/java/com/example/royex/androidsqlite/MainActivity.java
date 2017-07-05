package com.example.royex.androidsqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText name, surname, marks, id;
    Button submit, retrieve, update, delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        name = (EditText) findViewById(R.id.name);
        id = (EditText) findViewById(R.id.id);
        surname = (EditText) findViewById(R.id.surname);
        marks = (EditText) findViewById(R.id.marks);
        submit = (Button) findViewById(R.id.submit);
        retrieve = (Button) findViewById(R.id.retrieve);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleted = myDb.delete(id.getText().toString());
                if(deleted > 0){
                    Toast.makeText(getApplicationContext(), "Successfully deleted", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Failed to delete", Toast.LENGTH_LONG).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isupdated = myDb.update(id.getText().toString(), name.getText().toString(), surname.getText().toString(),
                        marks.getText().toString());
                if(isupdated == false){
                    Toast.makeText(getApplicationContext(), "Failed to update", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_LONG).show();
                }
            }
        });

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getData();
                if(res.getCount() == 0){
                    showMessage("Error", "No data found");
                }else{
                    StringBuffer stringBuffer = new StringBuffer();
                    while (res.moveToNext()){
                        stringBuffer.append("Id :"+res.getString(0)+"\n");
                        stringBuffer.append("name :"+res.getString(1)+"\n");
                        stringBuffer.append("surname :"+res.getString(2)+"\n");
                        stringBuffer.append("marks :"+res.getString(3)+"\n\n");
                    }

                    showMessage("Data", stringBuffer.toString());
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              boolean returned = myDb.insert(name.getText().toString(), surname.getText().toString(), marks.getText().toString());
                if(returned == false){
                    Toast.makeText(getApplicationContext(), "Failed to insert", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Successfully inserted", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
