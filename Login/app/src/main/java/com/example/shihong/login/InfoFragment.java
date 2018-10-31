package com.example.shihong.login;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoFragment extends Fragment {
    private SQLiteDatabase db;
    private DatabaseOperation dop;
    private String number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dop = new DatabaseOperation(getActivity(),db);
        Bundle bundle = getArguments();
        number = bundle.getString("number");
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView txt = (TextView) getActivity().findViewById(R.id.txt);
        LinearLayout ll_info = (LinearLayout) getActivity().findViewById(R.id.ll_info);
        TextView txt_number = (TextView) getActivity().findViewById(R.id.txt_number);
        TextView txt_name = (TextView) getActivity().findViewById(R.id.txt_name);
        TextView txt_sex = (TextView) getActivity().findViewById(R.id.txt_sex);
        TextView txt_birth = (TextView) getActivity().findViewById(R.id.txt_birth);
        TextView txt_nation = (TextView) getActivity().findViewById(R.id.txt_nation);
        TextView txt_place = (TextView) getActivity().findViewById(R.id.txt_place);
        TextView txt_academy = (TextView) getActivity().findViewById(R.id.txt_academy);
        TextView txt_major = (TextView) getActivity().findViewById(R.id.txt_major);
        TextView txt_class = (TextView) getActivity().findViewById(R.id.txt_class);
        TextView txt_phone = (TextView) getActivity().findViewById(R.id.txt_phone);
        Button btn_changeInfo = (Button) getActivity().findViewById(R.id.btn_changeInfo);
        dop.create_infoDb();
        Cursor cursor = dop.query_infoDb(number);
        if(cursor.getCount() == 0) {
            ll_info.setVisibility(View.GONE);
            btn_changeInfo.setText("登记信息");
        }
        else{
            txt.setVisibility(View.GONE);
            cursor.moveToFirst();
            txt_number.setText(cursor.getString(cursor.getColumnIndex("number")));
            txt_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            txt_sex.setText(cursor.getString(cursor.getColumnIndex("sex")));
            txt_birth.setText(cursor.getString(cursor.getColumnIndex("birthday")).replace(" ","/"));
            txt_nation.setText(cursor.getString(cursor.getColumnIndex("nation")));
            txt_place.setText(cursor.getString(cursor.getColumnIndex("place")));
            txt_academy.setText(cursor.getString(cursor.getColumnIndex("academy")));
            txt_major.setText(cursor.getString(cursor.getColumnIndex("major")));
            txt_class.setText(cursor.getString(cursor.getColumnIndex("class")));
            txt_phone.setText(cursor.getString(cursor.getColumnIndex("phone")));
        }
        btn_changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeActivity.class);
                intent.putExtra("number",number);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

}
