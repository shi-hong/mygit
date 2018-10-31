package com.example.shihong.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ResourceCursorAdapter;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private DatabaseOperation dop = new DatabaseOperation(this,db);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText et_number = (EditText) findViewById(R.id.et_number);
        final EditText et_password = (EditText) findViewById(R.id.et_password);
        final EditText et_passwordAgain = (EditText) findViewById(R.id.et_passwordAgain);
        Button btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = et_number.getText().toString();
                String password = et_password.getText().toString();
                String passwordAgain = et_passwordAgain.getText().toString();
                if(number.isEmpty())
                    Toast.makeText(RegisterActivity.this, "学号不能为空！", Toast.LENGTH_SHORT).show();
                else if(password.isEmpty())
                    Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                else if(passwordAgain.isEmpty())
                    Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                else {
                    dop.create_studentDb();
                    Cursor cursor = dop.query_studentDb(number);
                    if (cursor.getCount() != 0)
                        Toast.makeText(RegisterActivity.this, "该学号已存在！", Toast.LENGTH_SHORT).show();
                    else if (!password.equals(passwordAgain))
                        Toast.makeText(RegisterActivity.this, "密码请保持一致！", Toast.LENGTH_SHORT).show();
                    else {
                        dop.insert_studentDb(number,password);
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    dop.close_db();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("是否放弃注册？");
            builder.setPositiveButton("取消", null);
            builder.setNegativeButton("确定",  new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); }
            }).show();
        }
        return false;
    }
}
