package com.example.shihong.diary;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AddActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private DatabaseOperation dop = new DatabaseOperation(this,db);
    private String editModel = "newAdd";
    private Integer diaryId;
    private static final String SD_PATH = "/sdcard/Diary/image/";
    private static final String IN_PATH = "/Diary/image/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText txt = (EditText) findViewById(R.id.txt);
        ImageView image = (ImageView) findViewById(R.id.image);
        Intent intent = getIntent();
        editModel = intent.getStringExtra("editModel");
        //如果是新增记事模式，则将editText清空
        if(editModel.equals("newAdd")){
            txt.setText("");
            image.setImageBitmap(null);
        }
        //如果编辑的是已存在的记事，则将数据库的保存的数据取出，并显示在EditText中
        else if(editModel.equals("update")){
            diaryId = intent.getIntExtra("diaryId", 0);
            dop.create_db();
            Cursor cursor = dop.query_db(diaryId);
            cursor.moveToFirst();
            //取出数据库中相应的字段内容
            String context = cursor.getString(cursor.getColumnIndex("context"));
            txt.append(context);

            String path=cursor.getString(cursor.getColumnIndex("imagepath"));
            Bitmap bitmap =BitmapFactory.decodeFile(path);
            image.setImageBitmap(bitmap);
            dop.close_db();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final EditText txt = (EditText) findViewById(R.id.txt);
        final ImageView image = (ImageView) findViewById(R.id.image);
        switch (item.getItemId()){
            case R.id.actionClearText:
                txt.setText("");
                return true;
            case R.id.actionClearImage:
                image.setImageBitmap(null);
                return true;
            case R.id.actionPhoto:
                //添加图片的主要代码
                Intent intent = new Intent();
                //设定类型为image
                intent.setType("image/*");
                //设置action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //选中相片后返回本Activity
                startActivityForResult(intent, 1);
                return true;
            case R.id.actionSave:
                save();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
            builder.setMessage("你还未保存，是否退出");
            builder.setPositiveButton("取消", null);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); }
            }).show();
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final EditText txt = (EditText) findViewById(R.id.txt);
        final ImageView image = (ImageView) findViewById(R.id.image);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            //取得数据
            Uri uri = data.getData();
            ContentResolver cr = AddActivity.this.getContentResolver();
            Bitmap bitmap = null;
            //如果是选择照片
            if(requestCode == 1){

                try {
                    //将对象存入Bitmap中
                    bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            image.setImageBitmap(bitmap);
        }
    }

    public void save(){
        final TextView txt = (TextView) findViewById(R.id.txt);
        ImageView image = (ImageView) findViewById(R.id.image);
        String context = txt.getText().toString();
        String path;
        image.setDrawingCacheEnabled(true);
        Bitmap bitmap=Bitmap.createBitmap(image.getDrawingCache());
        image.setDrawingCacheEnabled(false);
        path = saveBitmap(this,bitmap);
        if(context.isEmpty()){
            Toast.makeText(AddActivity.this, "内容为空!", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = getIntent();
            if(intent.getStringExtra("editModel").length() > 0)
                editModel = intent.getStringExtra("editModel");
            SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd EE HH:mm");
            Date curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
            String   time   =   formatter.format(curDate);
            String title;
            //截取EditText中的前一部分作为标题，用于显示在主页列表中
            if(context.length()>10)
                title = context.substring(0,10);
            else
                title = context.toString();

            //打开数据库
            dop.create_db();
            //判断是更新还是新增记事
            if(editModel.equals("newAdd")){
                //将记事插入到数据库中
                dop.insert_db(title,context,time,path);
            }
            else if(editModel.equals("update")){
                intent = getIntent();
                Integer diaryId = intent.getIntExtra("diaryId",0);
                dop.update_db(title,context,time,path,diaryId);
            }
            dop.close_db();
            Toast.makeText(AddActivity.this, "保存成功", Toast.LENGTH_LONG).show();
            intent = new Intent(AddActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //随机生产文件名
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

     //保存bitmap到本地
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".png");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return filePic.getAbsolutePath();
    }

}

