


package ui.test.cn.xiaoyitong.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.VersionEntity;

import static java.lang.Double.valueOf;

/**
 * 弹出跟新提示对话框，弹出下载apk进度条，替换安装程序
 * Created by asus on 2017/3/10.
 */

public class VersionUpdateUtils {
    private static final int MESSAGE_NET_EEOR = 101;
    private static final int MESSAGE_IO_EEOR = 102;
    private static final int MESSAGE_JSON_EEOR = 103;
    private static final int MESSAGE_SHOEW_DIALOG = 104;
    protected static final int MESSAGE_ENTERHOME = 105;
    int aaa = 0;
    int c = 0;
    private ProgressDialog progressDialog;
    Timer timer = new Timer(); // 计时器
    private String mVersion;//本地版本号
    private Context context;
    private ProgressDialog mprogressDialog;//弹框

    private VersionEntity versionEntity;
    Dialog dialog;

    /**
     * 本地版本号
     */
    public VersionUpdateUtils(String Version, Context context) {
        this.mVersion = Version;
        this.context = context;
    }

    /*用于更新ui===handler*/
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_IO_EEOR:
                    Toast.makeText(context, "IO异常", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case MESSAGE_JSON_EEOR:


                    break;
                case MESSAGE_NET_EEOR:
                    openFile(new File("/sdcard/update.apk"));

                    break;

                case MESSAGE_SHOEW_DIALOG:
                    showUpdateDialog(versionEntity);
                    break;


            }
        }
    };

    /**
     * 获取服务器版本号
     */
    public File getCloudVersion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection;
                try {
                    URL url = new URL("http://www.zhangyilan.me/updateinfo.html");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Message message = new Message();
                    message.obj = response.toString();
                    try {
                        versionEntity = new VersionEntity();
                        JSONObject jsonObject = new JSONObject(response.toString());
                        String code = jsonObject.getString("code");
                        Log.d("sdd", code);
                        versionEntity.versioncode = code;
                        String des = jsonObject.getString("des");
                        versionEntity.description = des;
                        String apkurl = jsonObject.getString("apkurl");
                        Log.d("sdd", apkurl);
                        versionEntity.apkurl = apkurl;

                        if (!mVersion.equals(code)) {
                            //版本不一致
                            message.what = MESSAGE_SHOEW_DIALOG;
                            handler.sendMessage(message);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (IllegalArgumentException a){
                    Message message = new Message();
                    message.what = MESSAGE_IO_EEOR;
                    handler.sendMessage(message);
                }
            }
        }).start();
        return null;
    }


    /**
     * 弹出更新提示对话框
     */
    private void showUpdateDialog(final VersionEntity versionEntity) {
        //创建dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("检查到新版本：" + versionEntity.versioncode);
        builder.setMessage(versionEntity.description);
        builder.setCancelable(false);// 设置不能点击手机返回按钮隐藏对话框
        builder.setIcon(R.mipmap.ic_launcher);
        // 设置立即升级按钮点击事件
        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initProgessDia();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            downLoadFile(versionEntity.apkurl);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                ).start();


            }
        });
        builder.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        builder.show();


    }


    private void initProgessDia() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle("联网升级");
        progressDialog.setMessage("正在升级...");
        progressDialog.setProgress(0);
        progressDialog.setCanceledOnTouchOutside(false);//设置点击进度对话框外的区域对话框不消失
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条对话框//样式（水平，旋转）
        progressDialog.show();
        TimerTask task = new TimerTask() {
            @Override
            // 实例化TimerTask对象的时候，需要重写它的run()方法，然后在这个方法体内增加需要执行的具体操作
            public void run() {
                final File file = new File("/sdcard/update.apk");
                if (file.exists()) {
                    try {
                        int downsize = getFileSize(file);
                        float aaaa = (float) downsize / (float) aaa;
                        String aaaaaa = new DecimalFormat("0.00").format(aaaa);
                        c = (Double.valueOf(valueOf(aaaaaa) * 100)).intValue();
                        progressDialog.setProgress(c);
                        if (c == 100) {
                            timer.cancel();
                            timer.purge();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        // 第三步，启动定时器
        timer.schedule(task, 10, 100); // 2秒后


    }

    private void enterHome() {
        handler.sendEmptyMessageDelayed(MESSAGE_ENTERHOME, 2000);
    }

    /**
     * 下载后打开文件
     *
     * @param file
     */
    private void openFile(File file) {
        Log.d("sddfgfh", file + "");
        progressDialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);

    }


    /**
     * 下载apk
     *
     * @param httpUrl
     * @return
     * @throws Exception
     */
    private void downLoadFile(String httpUrl) {
        final String fileName = "update.apk";
        Log.d("sddfgfh", httpUrl);
        File tmpFile = new File("/sdcard/");
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }
        final File file = new File("/sdcard/" + fileName);
        if (file.exists()) {

            file.delete();
        }
        try {
            URL url = new URL(httpUrl);
            try {
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                aaa = conn.getContentLength();
                Log.d("sddfgfh", aaa + "MB");
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[256];
                conn.connect();
                double count = 0;
                if (conn.getResponseCode() >= 400) {
                    Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    while (count <= 100) {
                        if (is != null) {
                            int numRead = is.read(buf);
                            if (numRead <= 0) {
                                break;
                            } else {
                                fos.write(buf, 0, numRead);
                            }

                        } else {
                            break;
                        }

                    }
                }
                progressDialog.dismiss();
                conn.disconnect();
                fos.close();
                is.close();
                Message messag = new Message();
                messag.what = MESSAGE_NET_EEOR;
                handler.sendMessage(messag);
            } catch (IOException e) {

                e.printStackTrace();
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }


    }
    //打开APK程序代码

    private static int getFileSize(File file) throws Exception {
        int size = 0;
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }
}
