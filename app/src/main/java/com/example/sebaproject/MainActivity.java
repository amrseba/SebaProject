package com.example.sebaproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtEmailAddresslog,edtPasswordLog;
    Button BtnLoginLog,BtnRegisterLog;
    TextView edtLogininfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtEmailAddresslog = findViewById(R.id.edtEmailAddresslog);
        edtPasswordLog = findViewById(R.id.edtPasswordLog);
        BtnLoginLog = findViewById(R.id.BtnLoginLog);
        BtnRegisterLog = findViewById(R.id.BtnRegisterLog);
        edtLogininfo = findViewById(R.id.edtLogininfo);

        BtnRegisterLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i  = new Intent(MainActivity.this,register.class);
                startActivity(i );

            }

        });
        BtnLoginLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String email = edtEmailAddresslog.getText().toString();
                String password = edtPasswordLog.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    edtLogininfo.setText("all field is required");
                    edtLogininfo.setTextColor(Color.RED);
                }else {
                    dbConnect db = new dbConnect(MainActivity.this);
                    if(db.validateUser(email,password)){
                        Intent x  = new Intent(MainActivity.this, Profile.class);
                        x.putExtra("EMAIL", email);
                        startActivity(x );
                        finish();
                    }else{
                        edtLogininfo.setText("invalid email or password");
                        edtLogininfo.setTextColor(Color.RED);
                    }
                }



            }


            });


    }
}