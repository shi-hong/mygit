package com.example.shihong.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private DatabaseOperation dop = new DatabaseOperation(this,db);
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Intent intent = getIntent();
        number = intent.getStringExtra("number");
        final EditText et_oldPassword = (EditText) findViewById(R.id.et_oldPassword);
        final EditText et_password = (EditText) findViewById(R.id.et_password);
        final EditText et_passwordAgain = (EditText) findViewById(R.id.et_passwordAgain);
        Button btn_changePwd = (Button) findViewById(R.id.btn_changePwd);
        btn_changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = et_oldPassword.getText().toString();
                String password = et_password.getText().toString();
                String passwordAgain = et_passwordAgain.getText().toString();
                if(oldPassword.isEmpty() || password.isEmpty() || passwordAgain.isEmpty())
                    Toast.makeText(PasswordActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                else {
                    dop.create_studentDb();
                    Cursor cursor = dop.query_studentDb(number);
                    cursor.moveToFirst();
                    if (!cursor.getString(cursor.getColumnIndex("password")).equals(oldPassword))
                        Toast.makeText(PasswordActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                    else if (!password.equals(passwordAgain))
                        Toast.makeText(PasswordActivity.this, "密码请保持一致！", Toast.LENGTH_SHORT).show();
                    else {
                        dop.update_studentDb(number,password);
                        Toast.makeText(PasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PasswordActivity.this, InfoActivity.class);
                        intent.putExtra("number",number);
                        startActivity(intent);
                        finish();
                    }
                    dop.close_db();
                }
            }
        });
    }
}
