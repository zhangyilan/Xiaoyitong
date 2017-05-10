package ui.test.cn.xiaoyitong.controller;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.easemob.EMCallBack;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMValueCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatConfig;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMContactManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ui.test.cn.xiaoyitong.model.DefaultHXSDKModel;
import ui.test.cn.xiaoyitong.model.HXNotifier;
import ui.test.cn.xiaoyitong.model.HXSDKModel;

/**
 * Created by YanChunlin on 2017/4/19.
 */

public abstract class HXSDKHelper {

    static public interface HXSyncListener {
        public void onSyncSucess(boolean success);
    }

    private static final String TAG = "HXSDKHelper";
    protected Context appContext = null;
    protected HXSDKModel hxModel = null;
    protected EMConnectionListener connectionListener = null;
    protected String hxId = null;
    protected String password = null;
    private boolean sdkInited = false;
    private static HXSDKHelper me = null;
    protected HXNotifier notifier = null;
    private List<HXSyncListener> syncGroupsListeners;
    private List<HXSyncListener> syncContactsListeners;
    private List<HXSyncListener> syncBlackListListeners;
    private boolean isSyncingGroupsWithServer = false;
    private boolean isSyncingContactsWithServer = false;
    private boolean isSyncingBlackListWithServer = false;
    private boolean isGroupsSyncedWithServer = false;
    private boolean isContactsSyncedWithServer = false;
    private boolean isBlackListSyncedWithServer = false;
    private boolean alreadyNotified = false;
    protected HXSDKHelper(){
        me = this;
    }
    public synchronized boolean onInit(Context context){
        if(sdkInited){
            return true;
        }

        appContext = context;

        hxModel = createModel();

        if(hxModel == null){
            hxModel = new DefaultHXSDKModel(appContext);
        }

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);

        Log.d(TAG, "process app name : " + processAppName);

        // 如果app启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(hxModel.getAppProcessName())) {
            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return false;
        }

        // 初始化环信SDK,一定要先调用init()
        EMChat.getInstance().init(context);

        // 设置sandbox测试环境
        // 建议开发者开发时设置此模式
        if(hxModel.isSandboxMode()){
            EMChat.getInstance().setEnv(EMChatConfig.EMEnvMode.EMSandboxMode);
        }

        if(hxModel.isDebugMode()){
            EMChat.getInstance().setDebugMode(true);
        }

        Log.d(TAG, "initialize EMChat SDK");

        initHXOptions();
        initListener();

        syncGroupsListeners = new ArrayList<HXSyncListener>();
        syncContactsListeners = new ArrayList<HXSyncListener>();
        syncBlackListListeners = new ArrayList<HXSyncListener>();

        isGroupsSyncedWithServer = hxModel.isGroupsSynced();
        isContactsSyncedWithServer = hxModel.isContactSynced();
        isBlackListSyncedWithServer = hxModel.isBacklistSynced();

        sdkInited = true;
        return true;
    }

    public static HXSDKHelper getInstance(){
        return me;
    }

    public Context getAppContext(){
        return appContext;
    }

    public HXSDKModel getModel(){
        return hxModel;
    }

    public String getHXId(){
        if(hxId == null){
            hxId = hxModel.getHXId();
        }
        return hxId;
    }

    public String getPassword(){
        if(password == null){
            password = hxModel.getPwd();
        }
        return password;
    }

    public void setHXId(String hxId){
        if (hxId != null) {
            if(hxModel.saveHXId(hxId)){
                this.hxId = hxId;
            }
        }
    }

    public void setPassword(String password){
        if(hxModel.savePassword(password)){
            this.password = password;
        }
    }

    abstract protected HXSDKModel createModel();

    protected void initHXOptions(){
        Log.d(TAG, "init HuanXin Options");

        // 获取到EMChatOptions对象
        EMChatOptions options = EMChatManager.getInstance().getChatOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(hxModel.getAcceptInvitationAlways());
        // 默认环信是不维护好友关系列表的，如果app依赖环信的好友关系，把这个属性设置为true
        options.setUseRoster(hxModel.getUseHXRoster());
        // 设置是否需要已读回执
        options.setRequireAck(hxModel.getRequireReadAck());
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(hxModel.getRequireDeliveryAck());
        // 设置从db初始化加载时, 每个conversation需要加载msg的个数
        options.setNumberOfMessagesLoaded(1);

        notifier = createNotifier();
        notifier.init(appContext);

        notifier.setNotificationInfoProvider(getNotificationListener());
    }

    protected HXNotifier createNotifier(){
        return new HXNotifier();
    }

    public HXNotifier getNotifier(){
        return notifier;
    }

    public void logout(final EMCallBack callback){
        setPassword(null);
        reset();
        EMChatManager.getInstance().logout(new EMCallBack(){

            @Override
            public void onSuccess() {
                if(callback != null){
                    callback.onSuccess();
                }
            }

            @Override
            public void onError(int code, String message) {
            }

            @Override
            public void onProgress(int progress, String status) {
                if(callback != null){
                    callback.onProgress(progress, status);
                }
            }

        });
    }

    /**
     * 检查是否已经登录过
     * @return
     */
    public boolean isLogined(){
        return EMChat.getInstance().isLoggedIn();
    }

    protected HXNotifier.HXNotificationInfoProvider getNotificationListener(){
        return null;
    }

    protected void initListener(){
        Log.d(TAG, "init listener");

        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                if (error == EMError.USER_REMOVED) {
                    onCurrentAccountRemoved();
                }else if (error == EMError.CONNECTION_CONFLICT) {
                    onConnectionConflict();
                }else{
                    onConnectionDisconnected(error);
                }
            }

            @Override
            public void onConnected() {
                onConnectionConnected();
            }
        };

        //注册连接监听
        EMChatManager.getInstance().addConnectionListener(connectionListener);
    }

    protected void onConnectionConflict(){}


    protected void onCurrentAccountRemoved(){}


    protected void onConnectionConnected(){}

    protected void onConnectionDisconnected(int error){}


    @SuppressWarnings("rawtypes")
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = appContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                            info.processName +"  Label: "+c.toString());
                    processName = c.toString();
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
            }
        }
        return processName;
    }


    public void addSyncGroupListener(HXSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (!syncGroupsListeners.contains(listener)) {
            syncGroupsListeners.add(listener);
        }
    }

    public void removeSyncGroupListener(HXSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (syncGroupsListeners.contains(listener)) {
            syncGroupsListeners.remove(listener);
        }
    }

    public void addSyncContactListener(HXSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (!syncContactsListeners.contains(listener)) {
            syncContactsListeners.add(listener);
        }
    }

    public void removeSyncContactListener(HXSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (syncContactsListeners.contains(listener)) {
            syncContactsListeners.remove(listener);
        }
    }

    public void addSyncBlackListListener(HXSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (!syncBlackListListeners.contains(listener)) {
            syncBlackListListeners.add(listener);
        }
    }

    public void removeSyncBlackListListener(HXSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (syncBlackListListeners.contains(listener)) {
            syncBlackListListeners.remove(listener);
        }
    }

    /**
     * 同步操作，从服务器获取群组列表
     * 该方法会记录更新状态，可以通过isSyncingGroupsFromServer获取是否正在更新
     * 和HXPreferenceUtils.getInstance().getSettingSyncGroupsFinished()获取是否更新已经完成
     * @throws EaseMobException
     */
    public synchronized void asyncFetchGroupsFromServer(final EMCallBack callback){
        if(isSyncingGroupsWithServer){
            return;
        }

        isSyncingGroupsWithServer = true;

        new Thread(){
            @Override
            public void run(){
                try {
                    EMGroupManager.getInstance().getGroupsFromServer();

                    // in case that logout already before server returns, we should return immediately
                    if(!EMChat.getInstance().isLoggedIn()){
                        return;
                    }

                    hxModel.setGroupsSynced(true);

                    isGroupsSyncedWithServer = true;
                    isSyncingGroupsWithServer = false;
                    if(callback != null){
                        callback.onSuccess();
                    }
                } catch (EaseMobException e) {
                    hxModel.setGroupsSynced(false);
                    isGroupsSyncedWithServer = false;
                    isSyncingGroupsWithServer = false;
                    if(callback != null){
                        callback.onError(e.getErrorCode(), e.toString());
                    }
                }

            }
        }.start();
    }

    public void noitifyGroupSyncListeners(boolean success){
        for (HXSyncListener listener : syncGroupsListeners) {
            listener.onSyncSucess(success);
        }
    }

    public void asyncFetchContactsFromServer(final EMValueCallBack<List<String>> callback){
        if(isSyncingContactsWithServer){
            return;
        }

        isSyncingContactsWithServer = true;

        new Thread(){
            @Override
            public void run(){
                List<String> usernames = null;
                try {
                    usernames = EMContactManager.getInstance().getContactUserNames();

                    // in case that logout already before server returns, we should return immediately
                    if(!EMChat.getInstance().isLoggedIn()){
                        return;
                    }

                    hxModel.setContactSynced(true);

                    isContactsSyncedWithServer = true;
                    isSyncingContactsWithServer = false;
                    if(callback != null){
                        callback.onSuccess(usernames);
                    }
                } catch (EaseMobException e) {
                    hxModel.setContactSynced(false);
                    isContactsSyncedWithServer = false;
                    isSyncingContactsWithServer = false;
                    e.printStackTrace();
                    if(callback != null){
                        callback.onError(e.getErrorCode(), e.toString());
                    }
                }

            }
        }.start();
    }

    public void notifyContactsSyncListener(boolean success){
        for (HXSyncListener listener : syncContactsListeners) {
            listener.onSyncSucess(success);
        }
    }

    public void asyncFetchBlackListFromServer(final EMValueCallBack<List<String>> callback){

        if(isSyncingBlackListWithServer){
            return;
        }

        isSyncingBlackListWithServer = true;

        new Thread(){
            @Override
            public void run(){
                try {
                    List<String> usernames = null;
                    usernames = EMContactManager.getInstance().getBlackListUsernamesFromServer();

                    // in case that logout already before server returns, we should return immediately
                    if(!EMChat.getInstance().isLoggedIn()){
                        return;
                    }

                    hxModel.setBlacklistSynced(true);

                    isBlackListSyncedWithServer = true;
                    isSyncingBlackListWithServer = false;
                    if(callback != null){
                        callback.onSuccess(usernames);
                    }
                } catch (EaseMobException e) {
                    hxModel.setBlacklistSynced(false);

                    isBlackListSyncedWithServer = false;
                    isSyncingBlackListWithServer = true;
                    e.printStackTrace();

                    if(callback != null){
                        callback.onError(e.getErrorCode(), e.toString());
                    }
                }

            }
        }.start();
    }

    public void notifyBlackListSyncListener(boolean success){
        for (HXSyncListener listener : syncBlackListListeners) {
            listener.onSyncSucess(success);
        }
    }

    public boolean isSyncingGroupsWithServer() {
        return isSyncingGroupsWithServer;
    }

    public boolean isSyncingContactsWithServer() {
        return isSyncingContactsWithServer;
    }

    public boolean isSyncingBlackListWithServer() {
        return isSyncingBlackListWithServer;
    }

    public boolean isGroupsSyncedWithServer() {
        return isGroupsSyncedWithServer;
    }

    public boolean isContactsSyncedWithServer() {
        return isContactsSyncedWithServer;
    }

    public boolean isBlackListSyncedWithServer() {
        return isBlackListSyncedWithServer;
    }

    public synchronized void notifyForRecevingEvents(){
        if(alreadyNotified){
            return;
        }
        // 通知sdk，UI 已经初始化完毕，注册了相应的receiver和listener, 可以接受broadcast了
        EMChat.getInstance().setAppInited();
        alreadyNotified = true;
    }

    synchronized void reset(){
        isSyncingGroupsWithServer = false;
        isSyncingContactsWithServer = false;
        isSyncingBlackListWithServer = false;

        hxModel.setGroupsSynced(false);
        hxModel.setContactSynced(false);
        hxModel.setBlacklistSynced(false);

        isGroupsSyncedWithServer = false;
        isContactsSyncedWithServer = false;
        isBlackListSyncedWithServer = false;

        alreadyNotified = false;
    }
}

