package ui.test.cn.xiaoyitong.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.FileMessageBody;
import com.easemob.chat.TextMessageBody;
import com.easemob.util.EMLog;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.Constant;
import ui.test.cn.xiaoyitong.ui.AlertDialog;
import ui.test.cn.xiaoyitong.ui.ChatActivity;
import ui.test.cn.xiaoyitong.ui.ContextMenu;
import ui.test.cn.xiaoyitong.utils.DateUtils;
import ui.test.cn.xiaoyitong.utils.SmileUtils;
import ui.test.cn.xiaoyitong.utils.UserUtils;

/**
 * Created by YanChunlin on 2017/4/19.
 */

public class MessageAdapter extends BaseAdapter {

    private final static String TAG = "msg";

    private static final int MESSAGE_TYPE_RECV_TXT = 0;
    private static final int MESSAGE_TYPE_SENT_TXT = 1;
    private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
    private static final int MESSAGE_TYPE_SENT_LOCATION = 3;
    private static final int MESSAGE_TYPE_RECV_LOCATION = 4;
    private static final int MESSAGE_TYPE_RECV_IMAGE = 5;
    private static final int MESSAGE_TYPE_SENT_VOICE = 6;
    private static final int MESSAGE_TYPE_RECV_VOICE = 7;
    private static final int MESSAGE_TYPE_SENT_VIDEO = 8;
    private static final int MESSAGE_TYPE_RECV_VIDEO = 9;
    private static final int MESSAGE_TYPE_SENT_FILE = 10;
    private static final int MESSAGE_TYPE_RECV_FILE = 11;
    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 12;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 13;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 14;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 15;

    public static final String IMAGE_DIR = "chat/image/";
    public static final String VOICE_DIR = "chat/audio/";
    public static final String VIDEO_DIR = "chat/video";

    private String username;
    private LayoutInflater inflater;
    private Activity activity;

    private static final int HANDLER_MESSAGE_REFRESH_LIST = 0;
    private static final int HANDLER_MESSAGE_SELECT_LAST = 1;
    private static final int HANDLER_MESSAGE_SEEK_TO = 2;

    private EMConversation conversation;
    EMMessage[] messages = null;

    private Context context;

    private Map<String, Timer> timers = new Hashtable<String, Timer>();

    public MessageAdapter(Context context, String username, int chatType) {
        this.username = username;
        this.context = context;
        inflater = LayoutInflater.from(context);
        activity = (Activity) context;
        this.conversation = EMChatManager.getInstance().getConversation(username);
    }

    Handler handler = new Handler() {
        private void refreshList() {
            // UI线程不能直接使用conversation.getAllMessages()
            // 否则在UI刷新过程中，如果收到新的消息，会导致并发问题
            messages = (EMMessage[]) conversation.getAllMessages().toArray(new EMMessage[conversation.getAllMessages().size()]);
            for (int i = 0; i < messages.length; i++) {
                // getMessage will set message as read status
                conversation.getMessage(i);
            }
            notifyDataSetChanged();
        }

        @Override
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case HANDLER_MESSAGE_REFRESH_LIST:
                    refreshList();
                    break;
                case HANDLER_MESSAGE_SELECT_LAST:
                    if (activity instanceof ChatActivity) {
                        ListView listView = ((ChatActivity)activity).getListView();
                        if (messages.length > 0) {
                            listView.setSelection(messages.length - 1);
                        }
                    }
                    break;
                case HANDLER_MESSAGE_SEEK_TO:
                    int position = message.arg1;
                    if (activity instanceof ChatActivity) {
                        ListView listView = ((ChatActivity)activity).getListView();
                        listView.setSelection(position);
                    }
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 获取item数
     */
    public int getCount() {
        return messages == null ? 0 : messages.length;
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        if (handler.hasMessages(HANDLER_MESSAGE_REFRESH_LIST)) {
            return;
        }
        android.os.Message msg = handler.obtainMessage(HANDLER_MESSAGE_REFRESH_LIST);
        handler.sendMessage(msg);
    }

    /**
     * 刷新页面, 选择最后一个
     */
    public void refreshSelectLast() {
        handler.sendMessage(handler.obtainMessage(HANDLER_MESSAGE_REFRESH_LIST));
        handler.sendMessage(handler.obtainMessage(HANDLER_MESSAGE_SELECT_LAST));
    }

    /**
     * 刷新页面, 选择Position
     */
    public void refreshSeekTo(int position) {
        handler.sendMessage(handler.obtainMessage(HANDLER_MESSAGE_REFRESH_LIST));
        android.os.Message msg = handler.obtainMessage(HANDLER_MESSAGE_SEEK_TO);
        msg.arg1 = position;
        handler.sendMessage(msg);
    }

    public EMMessage getItem(int position) {
        if (messages != null && position < messages.length) {
            return messages[position];
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取item类型数
     */
    public int getViewTypeCount() {
        return 16;
    }

    /**
     * 获取item类型
     */
    public int getItemViewType(int position) {
        EMMessage message = getItem(position);
        if (message == null) {
            return -1;
        }
        if (message.getType() == EMMessage.Type.TXT) {
            if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false))
                return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
            else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false))
                return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
            return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TXT : MESSAGE_TYPE_SENT_TXT;
        }
        if (message.getType() == EMMessage.Type.IMAGE) {
            return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_IMAGE : MESSAGE_TYPE_SENT_IMAGE;

        }
        if (message.getType() == EMMessage.Type.LOCATION) {
            return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_LOCATION : MESSAGE_TYPE_SENT_LOCATION;
        }
        if (message.getType() == EMMessage.Type.VOICE) {
            return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE : MESSAGE_TYPE_SENT_VOICE;
        }
        if (message.getType() == EMMessage.Type.VIDEO) {
            return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO : MESSAGE_TYPE_SENT_VIDEO;
        }
        if (message.getType() == EMMessage.Type.FILE) {
            return message.direct == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_FILE : MESSAGE_TYPE_SENT_FILE;
        }

        return -1;
    }


    private View createViewByMessage(EMMessage message, int position) {
        switch (message.getType()) {
            case TXT:
                return message.direct == EMMessage.Direct.RECEIVE ? inflater.inflate(R.layout.row_received_message, null) : inflater.inflate(
                        R.layout.row_sent_message, null);
            case IMAGE:
            case VOICE:
            case VIDEO:
            case FILE:
            default:
                return null;
        }
    }

    @SuppressLint("NewApi")
    public View getView(final int position, View convertView, ViewGroup parent) {
        final EMMessage message = getItem(position);
        EMMessage.ChatType chatType = message.getChatType();
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = createViewByMessage(message, position);
            if (message.getType() == EMMessage.Type.TXT) {

                try {
                    holder.pb = (ProgressBar) convertView.findViewById(R.id.pb_sending);
                    holder.staus_iv = (ImageView) convertView.findViewById(R.id.msg_status);
                    holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_userhead);
                    // 这里是文字内容
                    holder.tv = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                    holder.tv_usernick = (TextView) convertView.findViewById(R.id.tv_userid);
                } catch (Exception e) {
                }

            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 群聊时，显示接收的消息的发送人的名称
        if ((chatType == EMMessage.ChatType.GroupChat || chatType == chatType.ChatRoom) && message.direct == EMMessage.Direct.RECEIVE){
            //demo里使用username代码nick
            holder.tv_usernick.setText(message.getFrom());
        }
        // 如果是发送的消息并且不是群聊消息，显示已读textview
        if (!(chatType == EMMessage.ChatType.GroupChat || chatType == chatType.ChatRoom) && message.direct == EMMessage.Direct.SEND) {
            holder.tv_ack = (TextView) convertView.findViewById(R.id.tv_ack);
            holder.tv_delivered = (TextView) convertView.findViewById(R.id.tv_delivered);
            if (holder.tv_ack != null) {
                if (message.isAcked) {
                    if (holder.tv_delivered != null) {
                        holder.tv_delivered.setVisibility(View.INVISIBLE);
                    }
                    holder.tv_ack.setVisibility(View.VISIBLE);
                } else {
                    holder.tv_ack.setVisibility(View.INVISIBLE);
                    if (holder.tv_delivered != null) {
                        if (message.isDelivered) {
                            holder.tv_delivered.setVisibility(View.VISIBLE);
                        } else {
                            holder.tv_delivered.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        } else {
            // 如果是文本或者地图消息并且不是group messgae,chatroom message，显示的时候给对方发送已读回执
            if ((message.getType() == EMMessage.Type.TXT || message.getType() == EMMessage.Type.LOCATION) && !message.isAcked && chatType != EMMessage.ChatType.GroupChat && chatType != EMMessage.ChatType.ChatRoom) {
                // 不是语音通话记录
                if (!message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    try {
                        EMChatManager.getInstance().ackMessageRead(message.getFrom(), message.getMsgId());
                        // 发送已读回执
                        message.isAcked = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //设置用户头像
        setUserAvatar(message, holder.iv_avatar);

        switch (message.getType()) {
            // 根据消息type显示item
            case IMAGE: // 图片
                break;
            case TXT: // 文本
                handleTextMessage(message, holder, position);
                break;
            case LOCATION: // 位置
                break;
            case VOICE: // 语音
                break;
            case VIDEO: // 视频
                break;
            case FILE: // 一般文件
                break;
            default:
                // not supported
        }

        if (message.direct == EMMessage.Direct.SEND) {
            View statusView = convertView.findViewById(R.id.msg_status);
            // 重发按钮点击事件
            statusView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 显示重发消息的自定义alertdialog
                    Intent intent = new Intent(activity, AlertDialog.class);
                    intent.putExtra("msg", activity.getString(R.string.confirm_resend));
                    intent.putExtra("title", activity.getString(R.string.resend));
                    intent.putExtra("cancel", true);
                    intent.putExtra("position", position);
                    if (message.getType() == EMMessage.Type.TXT)
                        activity.startActivityForResult(intent, ChatActivity.REQUEST_CODE_TEXT);
                    else if (message.getType() == EMMessage.Type.VOICE)
                        activity.startActivityForResult(intent, ChatActivity.REQUEST_CODE_VOICE);
                    else if (message.getType() == EMMessage.Type.IMAGE)
                        activity.startActivityForResult(intent, ChatActivity.REQUEST_CODE_PICTURE);
                    else if (message.getType() == EMMessage.Type.LOCATION)
                        activity.startActivityForResult(intent, ChatActivity.REQUEST_CODE_LOCATION);
                    else if (message.getType() == EMMessage.Type.FILE)
                        activity.startActivityForResult(intent, ChatActivity.REQUEST_CODE_FILE);
                    else if (message.getType() == EMMessage.Type.VIDEO)
                        activity.startActivityForResult(intent, ChatActivity.REQUEST_CODE_VIDEO);

                }
            });

        } else {
            final String st = context.getResources().getString(R.string.Into_the_blacklist);
            if(chatType != EMMessage.ChatType.ChatRoom){
                // 长按头像，移入黑名单
                holder.iv_avatar.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(activity, AlertDialog.class);
                        intent.putExtra("msg", st);
                        intent.putExtra("cancel", true);
                        intent.putExtra("position", position);
                        activity.startActivityForResult(intent, ChatActivity.REQUEST_CODE_ADD_TO_BLACKLIST);
                        return true;
                    }
                });
            }
        }

        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);

        if (position == 0) {
            timestamp.setText(DateUtils.getTimestampString(new Date(message.getMsgTime())));
            timestamp.setVisibility(View.VISIBLE);
        } else {
            // 两条消息时间离得如果稍长，显示时间
            EMMessage prevMessage = getItem(position - 1);
            if (prevMessage != null && DateUtils.isCloseEnough(message.getMsgTime(), prevMessage.getMsgTime())) {
                timestamp.setVisibility(View.GONE);
            } else {
                timestamp.setText(DateUtils.getTimestampString(new Date(message.getMsgTime())));
                timestamp.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }


    /**
     * 显示用户头像
     * @param message
     * @param imageView
     */
    private void setUserAvatar(EMMessage message, ImageView imageView){
        if(message.direct == EMMessage.Direct.SEND){
            //显示自己头像
            UserUtils.setUserAvatar(context, EMChatManager.getInstance().getCurrentUser(), imageView);
        }else{
            UserUtils.setUserAvatar(context, message.getFrom(), imageView);
        }
    }

    /**
     * 文本消息
     *
     * @param message
     * @param holder
     * @param position
     */
    private void handleTextMessage(EMMessage message, ViewHolder holder, final int position) {
        TextMessageBody txtBody = (TextMessageBody) message.getBody();
        Spannable span = SmileUtils.getSmiledText(context, txtBody.getMessage());
        // 设置内容
        holder.tv.setText(span, TextView.BufferType.SPANNABLE);
        // 设置长按事件监听
        holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                activity.startActivityForResult(
                        (new Intent(activity, ContextMenu.class)).putExtra("position", position).putExtra("type",
                                EMMessage.Type.TXT.ordinal()), ChatActivity.REQUEST_CODE_CONTEXT_MENU);
                return true;
            }
        });

        if (message.direct == EMMessage.Direct.SEND) {
            switch (message.status) {
                case SUCCESS: // 发送成功
                    holder.pb.setVisibility(View.GONE);
                    holder.staus_iv.setVisibility(View.GONE);
                    break;
                case FAIL: // 发送失败
                    holder.pb.setVisibility(View.GONE);
                    holder.staus_iv.setVisibility(View.VISIBLE);
                    break;
                case INPROGRESS: // 发送中
                    holder.pb.setVisibility(View.VISIBLE);
                    holder.staus_iv.setVisibility(View.GONE);
                    break;
                default:
                    // 发送消息
                    sendMsgInBackground(message, holder);
            }
        }
    }

    /**
     * 发送消息
     *
     * @param message
     * @param holder
     */
    public void sendMsgInBackground(final EMMessage message, final ViewHolder holder) {
        holder.staus_iv.setVisibility(View.GONE);
        holder.pb.setVisibility(View.VISIBLE);

        final long start = System.currentTimeMillis();
        // 调用sdk发送异步发送方法
        EMChatManager.getInstance().sendMessage(message, new EMCallBack() {

            @Override
            public void onSuccess() {

                updateSendedView(message, holder);
            }

            @Override
            public void onError(int code, String error) {

                updateSendedView(message, holder);
            }

            @Override
            public void onProgress(int progress, String status) {
            }

        });

    }

    private void showDownloadImageProgress(final EMMessage message, final ViewHolder holder) {
        EMLog.d(TAG, "!!! show download image progress");
        final FileMessageBody msgbody = (FileMessageBody) message.getBody();
        if(holder.pb!=null)
            holder.pb.setVisibility(View.VISIBLE);
        if(holder.tv!=null)
            holder.tv.setVisibility(View.VISIBLE);

        msgbody.setDownloadCallback(new EMCallBack() {

            @Override
            public void onSuccess() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (message.getType() == EMMessage.Type.IMAGE) {
                            holder.pb.setVisibility(View.GONE);
                            holder.tv.setVisibility(View.GONE);
                        }
                        notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int code, String message) {

            }

            @Override
            public void onProgress(final int progress, String status) {
                if (message.getType() == EMMessage.Type.IMAGE) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.tv.setText(progress + "%");

                        }
                    });
                }

            }

        });
    }

    private void sendPictureMessage(final EMMessage message, final ViewHolder holder) {
        try {
            String to = message.getTo();
            holder.staus_iv.setVisibility(View.GONE);
            holder.pb.setVisibility(View.VISIBLE);
            holder.tv.setVisibility(View.VISIBLE);
            holder.tv.setText("0%");

            final long start = System.currentTimeMillis();
            EMChatManager.getInstance().sendMessage(message, new EMCallBack() {

                @Override
                public void onSuccess() {
                    Log.d(TAG, "send image message successfully");
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            holder.pb.setVisibility(View.GONE);
                            holder.tv.setVisibility(View.GONE);
                        }
                    });
                }

                @Override
                public void onError(int code, String error) {

                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            holder.pb.setVisibility(View.GONE);
                            holder.tv.setVisibility(View.GONE);
                            holder.staus_iv.setVisibility(View.VISIBLE);
                            Toast.makeText(activity,
                                    activity.getString(R.string.send_fail) + activity.getString(R.string.connect_failuer_toast), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onProgress(final int progress, String status) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            holder.tv.setText(progress + "%");
                        }
                    });
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新ui上消息发送状态
     *
     * @param message
     * @param holder
     */
    private void updateSendedView(final EMMessage message, final ViewHolder holder) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message.getType() == EMMessage.Type.VIDEO) {
                    holder.tv.setVisibility(View.GONE);
                }
                EMLog.d(TAG, "message status : " + message.status);
                if (message.status == EMMessage.Status.SUCCESS) {

                } else if (message.status == EMMessage.Status.FAIL) {

                    if(message.getError() == EMError.MESSAGE_SEND_INVALID_CONTENT){
                        Toast.makeText(activity, activity.getString(R.string.send_fail) + activity.getString(R.string.error_send_invalid_content),  Toast.LENGTH_SHORT)
                                .show();
                    }else if(message.getError() == EMError.MESSAGE_SEND_NOT_IN_THE_GROUP){
                        Toast.makeText(activity, activity.getString(R.string.send_fail) + activity.getString(R.string.error_send_not_in_the_group),  Toast.LENGTH_SHORT)
                                .show();
                    }else{
                        Toast.makeText(activity, activity.getString(R.string.send_fail) + activity.getString(R.string.connect_failuer_toast),  Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                notifyDataSetChanged();
            }
        });
    }


    public static class ViewHolder {
        ImageView iv;
        TextView tv;
        ProgressBar pb;
        ImageView staus_iv;
        ImageView iv_avatar;
        TextView tv_usernick;
        ImageView playBtn;
        TextView timeLength;
        TextView size;
        LinearLayout container_status_btn;
        LinearLayout ll_container;
        ImageView iv_read_status;
        // 显示已读回执状态
        TextView tv_ack;
        // 显示送达回执状态
        TextView tv_delivered;

        TextView tv_file_name;
        TextView tv_file_size;
        TextView tv_file_download_state;
    }

}
