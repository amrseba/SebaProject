package com.example.sebaproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class register extends AppCompatActivity {
    EditText edtusername,edtEmail,edtFname,edtLname,edtpassword;
    Button btnRegister;
    TextView txtRegisterinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtusername = findViewById(R.id.edtusername);
        edtEmail = findViewById(R.id.edtEmail);
        edtFname = findViewById(R.id.edtFname);
        edtLname = findViewById(R.id.edtLname);
        edtpassword = findViewById(R.id.edtpassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtRegisterinfo = findViewById(R.id.txtRegisterinfo);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strFname = edtFname.getText().toString();
                String strLname = edtLname.getText().toString();
                String strEmail = edtEmail.getText().toString();
                String strUsername = edtusername.getText().toString();
                String strPassword = edtpassword.getText().toString();
                if (strFname.isEmpty() || strLname.isEmpty() || strUsername.isEmpty() ||
                        strEmail.isEmpty() || strPassword.isEmpty()){
                    txtRegisterinfo.setText("all field is required");
                    txtRegisterinfo.setTextColor(Color.RED);
                }else{
                    dbConnect db = new dbConnect(register.this);
                    Users newUser = new Users(strFname, strLname, strEmail, strUsername, strPassword);
                    db.addUser(newUser);

                    txtRegisterinfo.setText("register successful");
                    txtRegisterinfo.setTextColor(Color.GREEN);
                    Intent z = new Intent(register.this, MainActivity.class);
                    startActivity(z);
                    finish();
                }
            }
        });
    }
}