package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.Careerpublish;
import ui.test.cn.xiaoyitong.httpHelper.HttpCallBackListener;
import ui.test.cn.xiaoyitong.httpHelper.http1;

/**
 * Created by lenovo on 2017/05/29.
 */

public class Career_Pulish_Second_Activity extends Activity {

    public static final int TAKE_PHOTO = 1;
    public static final int PHOTO_ALBUM = 2;
    EditText them, background, instruct, flow_path, content;
    ImageView career_publish_image;
    Button commit;
    Uri image;
    private View viewPhoto;
    private Dialog photoDialog;
    private Button me_photo_take;
    private Button me_photo_local;
    private Button me_photo_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.career_pulish_them_layout);
        career_publish_image = (ImageView) findViewById(R.id.career_publish_image);
        final Bitmap bitmap = career_publish_image.getDrawingCache();
        them = (EditText) findViewById(R.id.career_publish_them);
        background = (EditText) findViewById(R.id.career_publish_background);
        flow_path = (EditText) findViewById(R.id.career_publish_flow_path);
        instruct = (EditText) findViewById(R.id.career_publish_intrudece);
        content = (EditText) findViewById(R.id.career_publish_content);
        commit = (Button) findViewById(R.id.career_publish_commit);
        Intent intent = getIntent();
        String school = intent.getStringExtra("school");
        final String department = intent.getStringExtra("department");
        final String ministry = intent.getStringExtra("ministry");
        Log.d("ce","系部"+department+","+ministry);
        final String starttime = intent.getStringExtra("starttime");
        final String stoptime = intent.getStringExtra("stoptime");
        final String adress = intent.getStringExtra("adress");
        final String score = intent.getStringExtra("score");
        final String standclass = intent.getStringExtra("standclass");
        final String standproject = intent.getStringExtra("standproject");
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        int res=Integer.parseInt(msg.obj.toString());
                        if (res==200){
                            Toast.makeText(Career_Pulish_Second_Activity.this,"发布成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Career_Pulish_Second_Activity.this,AdminListActivity.class);

                            startActivity(intent);
                        }else {
                            Toast.makeText(Career_Pulish_Second_Activity.this,"发布失败",Toast.LENGTH_SHORT).show();
                        }
                        break;

                }

            }
        };

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                File outputImage = new File(getExternalCacheDir(), "output_img.jpg");
                Log.d("tag", outputImage.length() + "");
                String dateString = formatter.format(new Date());
                String a = "opt=insert_school_activity" +
                        "&publish_branch=" + department + ministry +
                        "&quality_frade=" + score +
                        "&theme=" + them.getText().toString() +
                        "&include=" + content.getText().toString() +
                        "&publish_time=" + dateString +
                        "&start_time=" + starttime +
                        "&end_time=" + stoptime +
                        "&activity_address=" + adress +
                        "&activity_type=" + standclass +","+standproject+
                        "&activity_background=" + background.getText().toString() +
                        "&activity_suggest=" + instruct.getText().toString();
//
//                Map<String, String> postData = new HashMap<String, String>();
//                // postData.put("opt","insert_school_activity");
//                postData.put("publish_branch", department + ministry);
//                postData.put("quality_frade", "0.5");
//                postData.put("theme", them.getText().toString());
//                postData.put("include", content.getText().toString());
//                postData.put("publish_time", dateString);
//                postData.put("start_time", starttime);
//                postData.put("end_time", stoptime);
//                postData.put("activity_address", adress);
//                postData.put("activity_type", standclass);
//                postData.put("activity_background", background.getText().toString());
//                postData.put("activity_suggest", instruct.getText().toString());
//
                Careerpublish careerpublish=new Careerpublish();
                careerpublish.setPublish_branch(department+","+ministry);
                careerpublish.setQuality_frade(score);
                careerpublish.setTheme(them.getText().toString());
                careerpublish.setInclude(content.getText().toString());
                careerpublish.setPublish_time(dateString);
                careerpublish.setStart_time(starttime);
                careerpublish.setEnd_time(stoptime);
                careerpublish.setActivity_address(adress);
                careerpublish.setActivity_type(standclass+","+standproject);
                careerpublish.setActivity_background(background.getText().toString());
                careerpublish.setActivity_suggest(instruct.getText().toString());

                String url = "http://123.206.92.38:80/SimpleSchool/schoolActivityServlet?opt=insert_school_activity";


                Log.d("ce", "url" + url);
                http1.postAsynFile(url, outputImage, careerpublish, new HttpCallBackListener() {
                    @Override
                    public void onFinish(Object respones) throws JSONException {
                        Message message=new Message();
                        message.what=1;
                        message.obj=respones.toString();
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(Career_Pulish_Second_Activity.this,"网络异常,发布失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        career_publish_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPhoto = View.inflate(Career_Pulish_Second_Activity.this, R.layout.photo_show, null);
                photoDialog = new Dialog(Career_Pulish_Second_Activity.this, R.style.MyDialogStyleBottom);
                photoDialog.setContentView(viewPhoto);
                photoDialog.getWindow().setGravity(Gravity.FILL);
                photoDialog.show();
                viewPhoto(viewPhoto);
                me_photo_take.setOnClickListener(new photoTake());
                me_photo_local.setOnClickListener(new photoLocal());
                me_photo_no.setOnClickListener(new photoNo());
            }
        });

    }

    private void viewPhoto(View viewExit) {
        me_photo_take = (Button) viewExit.findViewById(R.id.photo_take);
        me_photo_local = (Button) viewExit.findViewById(R.id.photo_local);
        me_photo_no = (Button) viewExit.findViewById(R.id.photo_no);
    }

    private void Acess_photo() {
        photoDialog.dismiss();
        File outputImage = new File(getExternalCacheDir(), "output_img.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            image = FileProvider.getUriForFile(Career_Pulish_Second_Activity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
        } else {
            image = Uri.fromFile(outputImage);
        }
//        try {
//            compressAndGenImage(image.toString(),outputImage);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Log.d("ce", "拍照路径uri" + image);
        Log.d("ce", "拍照路径ima" + getImagePath(image, null));
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Log.d("ce", "路径lu" + image);
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(image));
                        career_publish_image.setImageBitmap(bitmap);
                        saveBitmap(bitmap);

                        photoDialog.dismiss();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PHOTO_ALBUM:
                Log.d("ce", "路径是");
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        Log.d("ce", "路径是1");
                        handleImageOnKitKat(data);
                    } else {
                        Log.d("ce", "路径是2");
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        Log.d("ce", "相册uri" + uri.toString());

        String imgpath = getImagePath(uri, null);
        Log.d("ce", "路径是" + imgpath);
        display(imgpath);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imgpath = null;
        Uri uri = null;
        try {
            uri = data.getData();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imgpath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);

            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri uri1 = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imgpath = getImagePath(uri1, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imgpath = getImagePath(uri, null);

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imgpath = uri.getPath();
        }
        Log.d("ce", "路径是" + imgpath);
        display(imgpath);

    }

    private void display(String imgpath) {
        Log.d("ce", "相册imgpath" + imgpath);
        if (imgpath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgpath);
            File outputImage = new File(getExternalCacheDir(), "output_img.jpg");

            career_publish_image.setImageBitmap(bitmap);
            saveBitmap(bitmap);
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(externalContentUri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            }
            cursor.close();
        }
        return path;
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        Career_Pulish_Second_Activity.this.startActivityForResult(intent, PHOTO_ALBUM);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Acess_photo();
                } else {
                    Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    /**
     * 保存方法
     */
    public void saveBitmap(Bitmap bm) {

        File f = new File(getExternalCacheDir(), "output_img.jpg");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Log.i("ce", "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    class photoTake implements View.OnClickListener {

        public void onClick(View v) {
            photoDialog.dismiss();
            if (ContextCompat.checkSelfPermission(Career_Pulish_Second_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Career_Pulish_Second_Activity.this, new String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 2);
            } else {
                Acess_photo();
            }

        }
    }

    class photoLocal implements View.OnClickListener {
        Intent intent = null;

        public void onClick(View v) {
            photoDialog.dismiss();
            if (ContextCompat.checkSelfPermission(Career_Pulish_Second_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Career_Pulish_Second_Activity.this, new String[]{
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
            } else {
                openAlbum();
            }

        }
    }

    class photoNo implements View.OnClickListener {
        public void onClick(View v) {
            photoDialog.dismiss();
        }
    }


}
