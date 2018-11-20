package com.example.pc.myapplication5;

import android.content.Intent;
import android.content.pm.InstrumentationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private DBAdapter db;
    private Student student;
    private int mode = -1;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final Button btn_commit = (Button)findViewById(R.id.btn_commit);
        final Button btn_cancel = (Button)findViewById(R.id.btn_cancel);
        final EditText et_class = (EditText)findViewById(R.id.et_class);
        final EditText et_number = (EditText)findViewById(R.id.et_number);
        final EditText et_name = (EditText)findViewById(R.id.et_name);
        intent = getIntent();
        mode = intent.getIntExtra("mode",mode);
        db = new DBAdapter(this);
        db.open();
        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_commit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String clas = et_class.getText().toString();
                String num = et_number.getText().toString();
                String name = et_name.getText().toString();
                student = new Student(clas, num, name);
                if(mode == 0)
                    db.insert(student);
                else if(mode == 1)
                {
                    int id = 0;
                    id = intent.getIntExtra("id",id);
                    db.updateOneData(id, student);
                }
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                db.close();
                finish();
            }
        });

    }
}
