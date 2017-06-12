package ui.test.cn.xiaoyitong.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.Mood;
import ui.test.cn.xiaoyitong.utils.ActivityForResultUtil;


/**
 * Created by YanChunlin on 2017/4/27.
 */

public class PublishActivity extends Activity {

    private TextView txt_send;
    private EditText mText;
    private ImageView add_image;

    public static String mUploadPhotoPath = null;
    private View viewPhoto;
    private Dialog photoDialog;
    private Button me_photo_take;
    private Button me_photo_local;
    private Button me_photo_no;
    private ImageLoader imageLoader;
    private Dialog loadingDialog;
    private Bitmap image_show;
    private ProgressDialog dialog;
    private List<Mood> listmood = new ArrayList<Mood>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        txt_send = (TextView) findViewById(R.id.txt_send);
        mText = (EditText) findViewById(R.id.timelineText);
        add_image = (ImageView) findViewById(R.id.add_image);

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPhoto = View.inflate(PublishActivity.this, R.layout.photo_show, null);
                photoDialog = new Dialog(PublishActivity.this, R.style.MyDialogStyleBottom);
                photoDialog.setContentView(viewPhoto);
                photoDialog.getWindow().setGravity(Gravity.FILL);
                photoDialog.show();
                viewPhoto(viewPhoto);
               // me_photo_take.setOnClickListener(new photoTake());
                me_photo_local.setOnClickListener(new photoLocal());
                me_photo_no.setOnClickListener(new photoNo());
            }
        });
        txt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mText.getText().toString();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(PublishActivity.this,"亲，你还未输入任何内容哦！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PublishActivity.this,"发布成功！",Toast.LENGTH_SHORT).show();
                    send(content);
                    finish();
                }
            }
        });
    }
    private void send(String content) {
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在发布...");
        dialog.show();
        Mood mood = new Mood();
        listmood.add(mood);
        Intent intent=new Intent();
        intent.putExtra("username", "我");
        intent.putExtra("time", "刚刚");
        intent.putExtra("img", R.drawable.userimg);
        intent.putExtra("content", mText.getText().toString());
        setResult(2,intent);
    }
    private void viewPhoto(View viewExit) {
        me_photo_take = (Button) viewExit.findViewById(R.id.photo_take);
        me_photo_local = (Button) viewExit.findViewById(R.id.photo_local);
        me_photo_no = (Button) viewExit.findViewById(R.id.photo_no);
    }
    class photoTake implements View.OnClickListener {
        Intent intent = null;
        public void onClick(View v) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File dir = new File("/sdcard/huaer/Camera/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            PublishActivity.this.mUploadPhotoPath = "/sdcard/me/Camera/" + UUID.randomUUID().toString();
            File file = new File(PublishActivity.this.mUploadPhotoPath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                }
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            PublishActivity.this.startActivityForResult(intent, ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CAMERA);
        }
    }

    class photoLocal implements View.OnClickListener {
        Intent intent = null;

        public void onClick(View v) {
            intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            PublishActivity.this.startActivityForResult(intent, ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_LOCATION);
        }
    }

    class photoNo implements View.OnClickListener {
        public void onClick(View v) {
            photoDialog.dismiss();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            /**
             * 通过照相修改头像
             */
            case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    File file = new File(PublishActivity.this.mUploadPhotoPath);
                    startPhotoZoom(Uri.fromFile(file));
                } else {
                    Toast.makeText(this, "取消上传=======", Toast.LENGTH_SHORT).show();
                }
                break;
            /**
             * 通过本地修改头像
             */
            case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_LOCATION:
                Uri uri = null;
                if (data == null) {
                    Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    uri = data.getData();
                    startPhotoZoom(uri);
                } else {
                    Toast.makeText(this, "照片获取失败", Toast.LENGTH_SHORT).show();
                }
                break;
            /**
             * 裁剪修改的头像
             */
            case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CROP:
                if (data == null) {
                    Toast.makeText(this, "取消上传================", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    saveCropPhoto(data);
                }
                break;

        }
    }

    /**
     * 系统裁剪照片
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CROP);
    }

    /**
     * 保存裁剪的照片
     *
     * @param data
     */
    private void saveCropPhoto(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            image_show = extras.getParcelable("data");
            // bitmap = PhotoUtil.toRoundCorner(bitmap, 15);
            if (image_show != null) {
                uploadPhoto(image_show);
            }
        } else {
            Toast.makeText(this, "获取裁剪照片错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 更新头像
     */
    private void uploadPhoto(final Bitmap bitmap) {
        photoDialog.dismiss();
        add_image.setImageBitmap(bitmap);
    }

    public synchronized String drawableToString(Drawable drawable) {

        if (drawable != null) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            int size = bitmap.getWidth() * bitmap.getHeight() * 4;

            // 创建一个字节数组输出流,流的大小为size
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
            // 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            // 将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();

            String icon = Base64.encodeToString(imagedata, Base64.DEFAULT);
            return icon;
        }
        return null;
    }
}
