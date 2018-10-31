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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ChangeActivity extends AppCompatActivity implements DatePicker.OnDateChangedListener{
    private SQLiteDatabase db;
    private DatabaseOperation dop = new DatabaseOperation(this,db);
    private String oldNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        Intent intent = getIntent();
        oldNumber = intent.getStringExtra("number");
        final EditText et_number = (EditText) findViewById(R.id.et_number);
        final EditText et_name = (EditText) findViewById(R.id.et_name);
        final EditText et_nation = (EditText) findViewById(R.id.et_nation);
        final EditText et_place = (EditText) findViewById(R.id.et_place);
        final EditText et_academy = (EditText) findViewById(R.id.et_academy);
        final EditText et_major = (EditText) findViewById(R.id.et_major);
        final EditText et_class = (EditText) findViewById(R.id.et_class);
        final EditText et_phone = (EditText) findViewById(R.id.et_phone);
        final RadioGroup rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        RadioButton btn_man = (RadioButton) findViewById(R.id.btn_man);
        RadioButton btn_woman = (RadioButton) findViewById(R.id.btn_woman);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        dop.create_infoDb();
        Cursor cursor = dop.query_infoDb(oldNumber);
        if(cursor.getCount() == 0)
            et_number.setText(oldNumber);
        else{
            cursor.moveToFirst();
            et_number.setText(cursor.getString(cursor.getColumnIndex("number")));
            et_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            et_nation.setText(cursor.getString(cursor.getColumnIndex("nation")));
            et_place.setText(cursor.getString(cursor.getColumnIndex("place")));
            et_academy.setText(cursor.getString(cursor.getColumnIndex("academy")));
            et_major.setText(cursor.getString(cursor.getColumnIndex("major")));
            et_class.setText(cursor.getString(cursor.getColumnIndex("class")));
            et_phone.setText(cursor.getString(cursor.getColumnIndex("phone")));
            if(cursor.getString(cursor.getColumnIndex("sex")).equals("男"))
                btn_man.setChecked(true);
            else
                btn_woman.setChecked(true);
            String birthday[] = cursor.getString(cursor.getColumnIndex("birthday")).split(" ");
            int year = Integer.valueOf(birthday[0]);
            int month = Integer.valueOf(birthday[1]);
            int day = Integer.valueOf(birthday[2]);
            datePicker.init(year,month-1,day,this);
        }
        Button btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = et_number.getText().toString();
                String name = et_name.getText().toString();
                String sex = "";
                String nation = et_nation.getText().toString();
                String place = et_place.getText().toString();
                String academy = et_academy.getText().toString();
                String major = et_major.getText().toString();
                String clas = et_class.getText().toString();
                String phone = et_phone.getText().toString();
                for (int i = 0; i < rg_sex.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) rg_sex.getChildAt(i);
                    if (rd.isChecked()) {
                        sex = rd.getText().toString();
                        break;
                    }
                }
                if(number.isEmpty() || name.isEmpty() || sex.isEmpty() || nation.isEmpty() || place.isEmpty() || academy.isEmpty() || major.isEmpty() || clas.isEmpty() || phone.isEmpty()){
                    Toast.makeText(ChangeActivity.this, "该完善所有信息！", Toast.LENGTH_SHORT).show();
                    return;
                }
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                String birthday = String.valueOf(year) + " " + String.valueOf(month) + " " + String.valueOf(day);
                dop.create_infoDb();
                Cursor cursor = dop.query_infoDb(oldNumber);
                if(!oldNumber.equals(number) && dop.query_studentDb(number).getCount() != 0) {
                    dop.close_db();
                    Toast.makeText(ChangeActivity.this, "该学号已存在！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cursor.getCount() == 0)
                    dop.insert_infoDb(number, name, sex, birthday, nation, place, academy, major, clas, phone);
                else
                    dop.update_infoDb(oldNumber, number, name, sex, birthday, nation, place, academy, major, clas, phone);
                dop.close_db();
                Toast.makeText(ChangeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangeActivity.this, InfoActivity.class);
                intent.putExtra("number", number);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onDateChanged(DatePicker view, int year,int month,int day){
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(ChangeActivity.this);
            builder.setMessage("你还未保存，是否退出？");
            builder.setPositiveButton("取消", null);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(ChangeActivity.this, InfoActivity.class);
                    intent.putExtra("number",oldNumber);
                    startActivity(intent);
                    finish(); }
            }).show();
        }
        return false;
    }
}
