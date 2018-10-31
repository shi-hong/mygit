package com.example.shihong.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.MotionEvent;
import android.widget.ListPopupWindow;
import android.view.View.OnTouchListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnTouchListener,
        OnItemClickListener {
    private SQLiteDatabase db;
    private DatabaseOperation dop = new DatabaseOperation(this,db);
    private EditText et_number;
    private ListPopupWindow lpw;
    private List<String> list= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_number = (EditText) findViewById(R.id.et_number);
        final EditText et_password = (EditText) findViewById(R.id.et_password);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = et_number.getText().toString();
                String password = et_password.getText().toString();
                if(number.isEmpty())
                    Toast.makeText(MainActivity.this, "学号不能为空！", Toast.LENGTH_SHORT).show();
                else if(password.isEmpty())
                    Toast.makeText(MainActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                else {
                    dop.create_studentDb();
                    Cursor cursor = dop.query_studentDb(number);
                    cursor.moveToFirst();
                    if (cursor.getCount() == 0)
                        Toast.makeText(MainActivity.this, "该用户不存在！", Toast.LENGTH_SHORT).show();
                    else if (!cursor.getString(cursor.getColumnIndex("password")).equals(password))
                        Toast.makeText(MainActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                        intent.putExtra("number",number);
                        startActivity(intent);
                        finish();
                    }
                    dop.close_db();
                }
            }
        });

        Button btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        et_number.setOnTouchListener(this);
        dop.create_studentDb();
        Cursor cursor = dop.query_studentDb();
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            for(int i=0;i<cursor.getCount();i++){
                list.add(cursor.getString(cursor.getColumnIndex("number")));
                cursor.moveToNext();
            }
            lpw = new ListPopupWindow(this);
            lpw.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, list));
            lpw.setAnchorView(et_number);
            lpw.setModal(true);
            lpw.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String item = list.get(position);
        et_number.setText(item);
        lpw.dismiss();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() >= (v.getWidth() - ((EditText) v)
                    .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                lpw.show();
                return true;
            }
        }
        return false;
    }
}
