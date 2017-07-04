package ui.test.cn.xiaoyitong.LyoutHandle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.InternetUtils.HttpCallbackListener;
import ui.test.cn.xiaoyitong.InternetUtils.HttpUtilX;
import ui.test.cn.xiaoyitong.R;

/**
 * Created by John on 2017/4/19.
 */

public class CloudReleaseHandle extends SwipeBackActivity implements View.OnClickListener {
    private ImageView back;
    private TextView biaoti;
    private TextView cloudTitle;
    private TextView cloudNickaName;
    private EditText cloudFile;
    private EditText cloudPhoneNumber;
    private EditText cloudPaginalNumber;
    private EditText cloudAddress;
    private TextView cloudType;
    private TextView cloudServiceTime;
    private TextView cloudColor;
    private TextView cloudUnivalent;
    private Button cloudConfirm;
    TimePickerView pvTime;
    private String title;
    private String nickName;
    private String file;
    private String phoneNumber;
    private String paginalNumber;
    private String address;
    private String type;
    private String serviceTime;
    private String color;
    private String univalent;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3;
    private static final int FILE_SELECT_CODE = 0X111;
    private String path;
    private static int fileStatus = 1;
    private static int dataStatus = 1;
    private static final int TIME_OUT = 10 * 10000000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    ArrayList<String> expressage1 = new ArrayList<>();
    ArrayList<String> expressage2 = new ArrayList<>();
    OptionsPickerView pvNoLinkOptions1, pvNoLinkOptions2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.layout_cloud_release);

        init();

        cloudConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
                if (("".equals(title)) || ("".equals(nickName)) || ("".equals(file)) || ("".equals(phoneNumber)) || ("".equals(paginalNumber)) || ("".equals(address)) || ("".equals(type)) || ("".equals(serviceTime)) || ("".equals(color)) || ("".equals(univalent))) {
                    Toast.makeText(CloudReleaseHandle.this, "请确认你的信息正确", Toast.LENGTH_SHORT).show();
                } else {
                    Runnable networkTask = new Runnable() {

                        @Override
                        public void run() {
                            File file = new File(path);
                            file.mkdirs();
                            uploadFile(file);
                        }
                    };
                    new Thread(networkTask).start();
                    String url = assembleData(title, nickName, phoneNumber, paginalNumber, address, type, serviceTime, color, univalent);
                    String method = "POST";
                    try {
                        HttpUtilX.sendHttpRequest(url, method, new HttpCallbackListener() {
                            @Override
                            public void onFinish(String response) {
                                dataStatus = 1;
                            }

                            @Override
                            public void onError(Exception e) {
                                dataStatus = 0;
                            }
                        });
                    } catch (Exception e) {
                        dataStatus = 0;
                    }
                }
                if ((fileStatus == 1) && (dataStatus == 1)) {
                    Toast.makeText(CloudReleaseHandle.this, "信息发布成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CloudReleaseHandle.this, "信息发布失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        linearLayout1 = (LinearLayout) findViewById(R.id.linerlayout1);
        linearLayout3 = (LinearLayout) findViewById(R.id.linerlayout3);
        linearLayout2 = (LinearLayout) findViewById(R.id.linerlayout2);
        cloudTitle = (TextView) findViewById(R.id.layout_cloud_release_title);
        cloudNickaName = (TextView) findViewById(R.id.layout_cloud_release_nickname);
        cloudFile = (EditText) findViewById(R.id.layout_cloud_release_fiel);
        cloudPhoneNumber = (EditText) findViewById(R.id.layout_cloud_release_phonenumber);
        cloudPaginalNumber = (EditText) findViewById(R.id.layout_cloud_release_paginalnumber);
        cloudAddress = (EditText) findViewById(R.id.layout_cloud_release_address);
        cloudType = (TextView) findViewById(R.id.layout_cloud_release_type);
        cloudServiceTime = (TextView) findViewById(R.id.layout_cloud_release_servicetime);
        cloudColor = (TextView) findViewById(R.id.layout_cloud_release_color);
        cloudUnivalent = (TextView) findViewById(R.id.layout_cloud_release_univalent);
        cloudConfirm = (Button) findViewById(R.id.layout_cloud_release_confirm);
        back = (ImageView) findViewById(R.id.back);
        biaoti = (TextView) findViewById(R.id.biaoti);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("发布打印");
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        initNoLinkOptionsPicker1();
        initNoLinkOptionsPicker2();
        initTimePicker();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void initTimePicker() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        SimpleDateFormat formatter1 = new SimpleDateFormat("MM");
        Date curDate1 = new Date(System.currentTimeMillis());//获取当前时间
        String str1 = formatter1.format(curDate1);
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd");
        Date curDate2 = new Date(System.currentTimeMillis());//获取当前时间
        String str2 = formatter2.format(curDate2);
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(Integer.parseInt(str), Integer.parseInt(str1), Integer.parseInt(str2));
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null

                /*btn_Time.setText(getTime(date));*/
                Button btn = (Button) v;
                cloudServiceTime.setText(getTime(date));
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setSubmitColor(Color.parseColor("#00B9DA"))
                .setCancelColor(Color.parseColor("#00B9DA"))
                .setContentSize(20)
                .setTitleText("选择时间")
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .build();
    }

    private void initNoLinkOptionsPicker1() {
        expressage1.add("单面");
        expressage1.add("双面");
        pvNoLinkOptions1 = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                cloudType.setText(expressage1.get(options1));
            }
        }).setSubmitColor(Color.parseColor("#00B9DA"))

                .setCancelColor(Color.parseColor("#00B9DA"))
                .setTitleText("单双页")
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvNoLinkOptions1.setPicker(expressage1);
    }

    private void initNoLinkOptionsPicker2() {
        expressage2.add("黑白");
        expressage2.add("彩色");
        pvNoLinkOptions2 = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                cloudColor.setText(expressage2.get(options1));
            }
        }).setSubmitColor(Color.parseColor("#00B9DA"))
                .setCancelColor(Color.parseColor("#00B9DA"))
                .setTitleText("色彩")
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvNoLinkOptions2.setPicker(expressage2);
    }

    private void initData() {
        title = cloudTitle.getText().toString();
        nickName = cloudNickaName.getText().toString();
        file = cloudFile.getText().toString();
        phoneNumber = cloudPhoneNumber.getText().toString();
        paginalNumber = cloudPaginalNumber.getText().toString();
        address = cloudAddress.getText().toString();
        type = cloudType.getText().toString();
        serviceTime = cloudServiceTime.getText().toString();
        color = cloudColor.getText().toString();
        univalent = cloudUnivalent.getText().toString();
    }

    //打开本地文件管理器选择文件
    public void OpenSystemFile(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择文件!"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
            System.out.println("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 根据返回选择的文件，来进行操作
     **/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            path = uri.getPath();
        }
        super.onActivityResult(requestCode, resultCode, data);
        cloudFile.setText(path);

    }

    public void uploadFile(File file) {
        System.out.println("开始上传");
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String RequestURL = "http://123.206.92.38:80/SimpleSchool/UploadServlet?opt=get_file&f1=" + file;
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
            if (file != null || !file.equals("")) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                OutputStream outputSteam = conn.getOutputStream();

                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */

                sb.append("Content-Disposition: form-data; name=\"word\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024 * 1024 * 10];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                if (res == 200) {
                    System.out.println("成功");
                    fileStatus = 1;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("失败");
            fileStatus = 0;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("失败");
            fileStatus = 0;
        }
    }

    public String assembleData(String title, String nickName, String phoneNumber, String paginalNumber,
                               String address, String type, String serviceTime, String color, String univalent) {
        String url = "http://123.206.92.38:80/SimpleSchool/stampservlet?opt=insert_stamp&stamp_pages="
                + paginalNumber
                + "&stamp_time="
                + serviceTime
                + "&stamp_price="
                + univalent
                + "&stamp_user="
                + nickName
                + "&user_address="
                + address
                + "&user_phone="
                + phoneNumber
                + "&single_and_double="
                + type
                + "&color="
                + color;

        return url;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linerlayout1:
                pvNoLinkOptions1.show();
                break;
            case R.id.linerlayout2:
                pvNoLinkOptions2.show();
                break;
            case R.id.linerlayout3:
                pvTime.show();
                break;
        }
    }
}
