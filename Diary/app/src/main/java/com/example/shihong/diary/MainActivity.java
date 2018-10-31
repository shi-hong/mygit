package com.example.shihong.diary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private DatabaseOperation dop = new DatabaseOperation(this,db);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listDiary = (ListView) findViewById(R.id.diaryList);
        showNotesList(null);

        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        listDiary.setTextFilterEnabled(true);

        // 设置搜索文本监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    showNotesList(newText);
                }else{
                    showNotesList(null);
                }
                return false;
            }
        });

        //为记事列表添加监听器
        listDiary.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                TextView txtId = (TextView)view.findViewById(R.id.txtId);
                int item_id = Integer.parseInt(txtId.getText().toString());
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                intent.putExtra("editModel", "update");
                intent.putExtra("diaryId", item_id);
                startActivity(intent);
                finish();
            }
        });

        //为记事列表添加长按事件
        listDiary.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                TextView txtId = (TextView)view.findViewById(R.id.txtId);
                int item_id = Integer.parseInt(txtId.getText().toString());
                simpleList(item_id);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionAdd:
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                intent.putExtra("editModel", "newAdd");
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showNotesList(String text){
        final ListView listDiary = (ListView) findViewById(R.id.diaryList);
        dop.create_db();
        Cursor cursor;
        if(text == null)
            cursor = dop.query_db();
        else
            cursor = dop.query_db(text);
        int n = cursor.getCount();
        String[] id=new String[n];
        String[] title=new String[n];
        String[] time=new String[n];
        cursor.moveToFirst();

        List<Map<String,Object>> items=new ArrayList<Map<String,Object>>();

        for(int i=0;i<n;i++) {
            Map<String,Object> item=new HashMap<String,Object>();
            id[i]=String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
            item.put("id", id[i]);
            title[i]=cursor.getString(cursor.getColumnIndex("title"));
            item.put("title", title[i]);
            time[i]=cursor.getString(cursor.getColumnIndex("time"));
            item.put("time", time[i]);
            items.add(item);
            cursor.moveToNext();
        }
        SimpleAdapter adapter = new SimpleAdapter(this,items, R.layout.item,
                new String[]{"id","title","time"}, new int[]{R.id.txtId,R.id.txtTitle,R.id.txtTime});
        listDiary.setAdapter(adapter);
        dop.close_db();
    }

    //简单列表对话框，用于选择操作
    public void simpleList(final int diaryId){
        final ListView listDiary = (ListView) findViewById(R.id.diaryList);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("选择操作");
        final  CharSequence[] items={"编辑","删除"};
        alertDialogBuilder.setItems(items, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    //编辑
                    case 0 :
                        Intent intent = new Intent(MainActivity.this,AddActivity.class);
                        intent.putExtra("editModel", "update");
                        intent.putExtra("diaryId", diaryId);
                        startActivity(intent);
                        finish();
                        break;
                    //删除
                    case 1 :
                        AlertDialog.Builder builder = new  AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("确定删除吗？");
                        builder.setPositiveButton("取消",null);
                        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dop.create_db();
                                dop.delete_db(diaryId);
                                dop.close_db();
                                //刷新列表显示
                                listDiary.invalidate();
                                showNotesList(null);
                                Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                             }
                        }).show();
                        break;
                }
            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }
}
