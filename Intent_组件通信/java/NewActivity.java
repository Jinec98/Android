package com.example.pc.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        final EditText editText = (EditText)findViewById(R.id.editText);
        final Button button2 = (Button)findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uriString = editText.getText().toString();
                Uri data = Uri.parse(uriString);
                Intent result = new Intent(null,data);
                setResult(RESULT_OK,result);
                finish();
            }
        });
    }
}
