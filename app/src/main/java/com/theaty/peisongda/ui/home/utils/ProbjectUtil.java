package com.theaty.peisongda.ui.home.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.theaty.peisongda.R;
import com.theaty.peisongda.system.AppContext;
import com.theaty.peisongda.ui.home.service.PrintDataService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import foundation.toast.ToastUtil;
import foundation.util.SDCardUtils;
import foundation.util.StringUtil;

/**
 * Created by track on 2017/1/19.
 */

public class ProbjectUtil {
    public final static int PERMISSION_RESULT_CAMERA = 1;
    /**
     * 默认上传的图片的大小
     */
    public final static int NORMAL_UPLOAD_SIZE = 150;

    /**
     * 将dp转换成px
     *
     * @param context
     * @param dpValue
     */
    public static float dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;

        return (dpValue * density + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @BindingAdapter({"loadImage"})
    public static void loadImage(ImageView view, String url) {
        System.out.println("loadImage:" + url);
        if (url == null || url.equals(""))
            Glide.with(view.getContext()).load(R.drawable.ic_placeholder).into(view);
        else
            Glide.with(view.getContext()).load(url).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder)
                    .into(view);
    }

    public static void loadImage(ImageView view, File url) {
        System.out.println("loadImage:" + url);
        Glide.with(view.getContext()).load(url).error(R.drawable.tht_ic_pic_normal)
                .centerCrop()
                .thumbnail(0.5f).dontAnimate()
                .into(view);
    }

    public static final boolean isOpenGps(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    public static void loadImage(ImageView view, int id) {
        Glide.with(view.getContext()).load(id).placeholder(R.drawable.tht_ic_pic_normal).error(R.drawable.tht_ic_pic_normal).into(view);
    }

    /**
     * @param view
     */
    public static void loadImageWhite(ImageView view) {
        Glide.with(view.getContext()).load(R.color.white).into(view);
    }

//    public static File getCameraTempFile() {
//        File f = null;
//        final String CACHE_DIR = "ptour" + File.separator;
//        final File storageDir = StorageUtil.getExternalStorageDirectory();
//        if (null != storageDir) {
//            final File tempDir = new File(storageDir.getAbsolutePath() + "/imgtemp/");
//            if (!tempDir.exists()) {
//                tempDir.mkdirs();
//            }
//
//            f = new File(tempDir, UUID.randomUUID().toString() + ".jpg");
//            try {
//                f.createNewFile();
//            } catch (IOException e) {
//                ToastUtil.showToast("创建图片失败！");
//            }
//        }
//        return f;
//    }

    public static boolean isEmpty(EditText... editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            if (StringUtil.isEmpty(editTexts[i].getText().toString())) {
                ToastUtil.showToast("请输入" + editTexts[i].getHint());
                return true;
            }
        }
        return false;

    }

    /**
     * 自定义提示  长度不一定相等  注意顺序
     *
     * @param info
     * @param editTexts
     * @return
     */
    public static boolean isEmpty(String[] info, EditText... editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            if (StringUtil.isEmpty(editTexts[i].getText().toString())) {
                if (info.length > i && info[i] != null)
                    ToastUtil.showToast(info[i] + "");
                else
                    ToastUtil.showToast("请输入" + editTexts[i].getHint());
                return true;
            }
        }
        return false;

    }

    public static boolean isExChar(EditText... editTexts) {
        String limitEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#¥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        for (int i = 0; i < editTexts.length; i++) {
            Matcher m = pattern.matcher(editTexts[i].getText().toString());
            if (m.find()) {
                ToastUtil.showToast("不允许输入特殊符号！");
                return true;
            }
        }
        return false;

    }

    /**
     * 如果没有就隐藏的
     *
     * @param view
     * @param url
     */
    @BindingAdapter({"loadimagegone"})
    public static void loadImagegone(ImageView view, String url) {
        System.out.println("loadimage:" + url);
        if (url == null || url.equals(""))
            view.setVisibility(View.GONE);
        else {
            view.setVisibility(View.VISIBLE);
            Glide.with(view.getContext()).load(url).error(R.drawable.tht_ic_pic_normal).placeholder(R.drawable.tht_ic_pic_normal).into(view);
        }
    }

    @BindingAdapter({"loadlocalimage"})
    public static void loadLocalImage(ImageView view, String url) {
        System.out.println("loadlocalimage:" + url);
        if (url == null || url.equals(""))
            Glide.with(view.getContext()).load(R.drawable.tht_ic_pic_normal).into(view);
        else
            Glide.with(view.getContext()).load(new File(url)).error(R.drawable.tht_ic_pic_normal).placeholder(R.drawable.tht_ic_pic_normal).into(view);
    }

    public static void ThisloadLocalImage(ImageView view, String url) {
        System.out.println("loadlocalimage:" + url);
        if (url == null || url.equals(""))
            Glide.with(view.getContext()).load(R.drawable.tht_ic_pic_normal).into(view);
        else
            Glide.with(view.getContext()).load(new File(url)).error(R.drawable.tht_ic_pic_normal).into(view);
    }

    public static void ThisloadLocalImage(ImageView view, int url) {
        System.out.println("loadlocalimage:" + url);
        Glide.with(view.getContext()).load(url).error(R.drawable.tht_ic_pic_normal).into(view);
    }

    public static void ThisloadLocalImage(ImageView view, Uri uri) {
        Glide.with(view.getContext()).load(uri).error(R.drawable.tht_ic_pic_normal).into(view);
    }

    /**
     * @param prefix  不参与的
     * @param content
     * @param keyword
     * @return
     */
    public static SpannableString getKetword(String prefix, String content, String keyword) {
        SpannableString s = new SpannableString(prefix + content);

        Pattern p = Pattern.compile(keyword);

        Matcher m = p.matcher(s);

        while (m.find()) {
            int start = m.start();
            int end = m.end();
            if (start >= prefix.length())
                s.setSpan(new ForegroundColorSpan(0xFFFF6F8D), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    public static SpannableString getKetword(String content, String keyword) {
        SpannableString s = new SpannableString(content);

        Pattern p = Pattern.compile(keyword);

        Matcher m = p.matcher(s);

        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(0xFFFF6F8D), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    public static SpannableString getKetword(String content, int start, int end) {
        SpannableString s = new SpannableString(content);
        s.setSpan(new ForegroundColorSpan(0xFFFF6F8D), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    private static String basePath(Context context) {
        String path;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            path = Environment.getExternalStorageDirectory().toString();
        } else {
            path = context.getFilesDir().getPath();
        }
        return path + File.separator + context.getResources().getString(R.string.app_name) + File.separator;
    }

    // 拍照文件临时存储路径
    public static String InitImageCatchDir(Context context) {
        File imagehome = new File(basePath(context) + ".images" + File.separator + "拍照" + File.separator);
        if (!imagehome.exists())
            imagehome.mkdirs();
        return imagehome.getAbsolutePath();
    }

    public static PopupWindow getPopWindow(Activity context, View view) {
        PopupWindow popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.uncheck));
        return popupWindow;
    }

    public static PopupWindow getPopWindowWRAP(Activity context, int width, View view) {
        PopupWindow popupWindow = new PopupWindow(view,
                width,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.uncheck));
        return popupWindow;
    }

//    public static Dialog getDialog(Activity context, int gravity) {
//        Dialog dialog = new Dialog(context, R.style.my_bulider_style);
//        android.view.WindowManager.LayoutParams lp = dialog.getWindow()
//                .getAttributes();
//        Point point = new Point();
//        context.getWindowManager().getDefaultDisplay().getSize(point);
//        lp.width = point.x;
//        lp.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.gravity = gravity;
//
//        if (gravity == Gravity.BOTTOM) {
//            dialog.getWindow().setWindowAnimations(R.style.dialoganimation);
//        } else {
//            lp.width = point.x - 200;
//        }
//        dialog.getWindow().setAttributes(lp);
//        return dialog;
//    }


//    public static String getIPAddress() {
//        Context context = AppContext.getInstance();
//        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
//        if (info != null && info.isConnected()) {
//            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
//                try {
//                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
//                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
//                        NetworkInterface intf = en.nextElement();
//                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                            InetAddress inetAddress = enumIpAddr.nextElement();
//                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
//                                return inetAddress.getHostAddress();
//                            }
//                        }
//                    }
//                } catch (SocketException e) {
//                    e.printStackTrace();
//                }
//
//            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
//                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
//                return ipAddress;
//            }
//        } else {
//            //当前无网络连接,请在设置中打开网络
//        }
//        return null;
//    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    public static String SHA1(String content) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(content.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * EditText空的动画
     **/
//    public static void startEmptyAnim(View v) {
//        Animation shakAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.shake);
//        v.startAnimation(shakAnim);
//
//    }

    @BindingAdapter({"formattime", "sprefix"})
    public static void formattime(TextView view, String formattime, String sprefix) {
        view.setText(sprefix + longToDateString(formattime) + "");
    }

    @BindingAdapter({"formattime", "sprefix"})
    public static void formattime(TextView view, long formattime, String sprefix) {
        view.setText(sprefix + longToDateString(formattime) + "");
    }

    /**
     * long转换String
     **/
    public static String longToDateString(long millSec) {
        if (millSec <= 0) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(millSec);
            return sdf.format(date);
        }
    }

    /**
     * long转换String
     **/
    public static String longToDateString(String millSec) {
        if (TextUtils.isEmpty(millSec)) {
            return "";
        }
        if (millSec.length() <= 0) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(millSec);
            return sdf.format(date);
        }
    }


    /**
     * date转换long
     **/
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * date转换String
     **/
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static boolean compareDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(time);
            sdf = new SimpleDateFormat("yyyyMMdd");
            long curtime = Long.parseLong(sdf.format(new Date())); //20170425
            long ptime = Long.parseLong(sdf.format(date)); //20170411
            return ptime >= curtime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean compareDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date sss = sdf.parse(startTime);
            Date eee = sdf.parse(endTime);
            sdf = new SimpleDateFormat("yyyyMMdd");
            long start = Long.parseLong(sdf.format(sss)); //20170411
            long end = Long.parseLong(sdf.format(eee)); //20170425
            return start >= end;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @BindingAdapter({"setTextByInt"})
    public static void setTextByInt(TextView tv, int ss) {
        String s = String.valueOf(ss);
        tv.setText(s);
    }

    @BindingAdapter({"setTextByInt"})
    public static void setTextByInt(TextView tv, BigDecimal ss) {
        String s = String.valueOf(ss);
        tv.setText(s);
    }

    public static String getString(String... s) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < s.length; i++) {
            buffer.append(s[i]);
            if (i != s.length - 1) {
                buffer.append("/");
            }
        }
        return buffer.toString();
    }

    public static PopupWindow getPopupWindow(Context context, View view) {
        PopupWindow pop = new PopupWindow(context);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setContentView(view);
        pop.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.uncheck));
        pop.setOutsideTouchable(false);
        pop.setFocusable(true);
        return pop;
    }

    public static void startWeb(Activity context, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.showToast("不完整的url");
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static String getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        File savefile = new File(SDCardUtils.getSDCardPath() + UUID.randomUUID().toString() + ".jpg");
        try {
            FileOutputStream out = new FileOutputStream(savefile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savefile.getAbsolutePath();
    }

    public enum AppType {
        CUSTOMER, SONG
    }

    private static AppType curApptype;

    public static AppType getAppType() {
        if (curApptype == null) {
            if (getMeta().equals("shengxiansong"))
                curApptype = AppType.SONG;
            else
                curApptype = AppType.CUSTOMER;

        }
        return curApptype;
    }

    public static String getMeta() {
        ApplicationInfo info = null;
        try {
            info = AppContext.getInstance().getPackageManager()
                    .getApplicationInfo(AppContext.getInstance().getPackageName(),
                            PackageManager.GET_META_DATA);
            return info.metaData.getString("TARGET_VALUE");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static void Call(Activity activity, String number) {
        Intent intent = new Intent();// 创建Intent对象
        intent.setAction(Intent.ACTION_CALL);// 为Intent设置动作

        intent.setData(Uri.parse("tel:" + number));// 为Intent设置数据

        activity.startActivity(intent);// 将Intent传递给Activity
    }

    static BluetoothAdapter bluetoothAdapter;

    static BluetoothAdapter getBadapter() {
        if (bluetoothAdapter == null) {
            bluetoothAdapter = BluetoothAdapter
                    .getDefaultAdapter();
        }
        return bluetoothAdapter;
    }

    public static boolean openBluetooth(Activity activity) {
        if (getBadapter().isEnabled())
            return true;
        return false;
    }

    public static void goBluetooth(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static PrintDataService getPrintDataService() {
        return printDataService;
    }

    public static void setPrintDataService(PrintDataService printDataService) {
        ProbjectUtil.printDataService = printDataService;
    }

    static PrintDataService printDataService;


    public static boolean Print(Activity activity, String data) {
        if (getPrintDataService() == null)
            return false;
        if (getPrintDataService().send(data)) {
            return true;
        }
        return false;

    }

    public static boolean Print(String data) {
        if (getPrintDataService() == null)
            return false;
        getPrintDataService().send(data);
        return true;

    }

//    public static ListDialog bluetoothDialog;
//
//    public static void showBluetoothDialog(final Context context) {
//        if (bluetoothDialog == null) {
//            bluetoothDialog = new ListDialog.Builder(context).setMessage("打印订单需要开启蓝牙并连接打印机才可以打印").setDialog(
//                    ListDialog.DialogStyle.ALERT_1).setConfirmText("去设置").setConfirmClick(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    BluetoothActivity.into((BaseActivity) context);
//                }
//            }).create();
//        }
//
//        bluetoothDialog.show();
//    }ni

    public static String getPrintData() {
        PrintUtils.selectCommand(PrintUtils.RESET);
        PrintUtils.selectCommand(PrintUtils.LINE_SPACING_DEFAULT);
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.selectCommand(PrintUtils.BOLD);
        String small_ticket_title = "配菜打印";// "小票标题",

        String small_ticket_top = "";//:"小票顶部标题",

        String small_ticket_footnotes = "************* 完 *************";//小票脚注"

        PrintUtils.printText(small_ticket_title + "\n\n");


        PrintUtils.printText("************  ************\n\n");
        PrintUtils.selectCommand(PrintUtils.BOLD_CANCEL);
        PrintUtils.selectCommand(PrintUtils.NORMAL);
        PrintUtils.printText(small_ticket_title + "\n\n");
//        PrintUtils.printText("--------------------------------\n");
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("订单编号:" );
        PrintUtils.selectCommand(PrintUtils.ALIGN_RIGHT);
        PrintUtils.printText( "9000000001000011301" + "\n\n");


        PrintUtils.printText("下单时间:" );
        PrintUtils.selectCommand(PrintUtils.ALIGN_RIGHT);
        PrintUtils.printText("2018-02-04 16:21:46" + "\n\n");
        PrintUtils.printText("订单地址:");
        PrintUtils.selectCommand(PrintUtils.ALIGN_RIGHT);
        PrintUtils.printText("北京市 海淀区 西四环 小区 老王餐馆" + "\n\n");
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("************  ************\n\n");
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("菜名称");
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("数量");
        PrintUtils.selectCommand(PrintUtils.ALIGN_RIGHT);
        PrintUtils.printText("单价"+ "\n\n");
        //
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("大白菜");
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("5斤左右");
        PrintUtils.selectCommand(PrintUtils.ALIGN_RIGHT);
        PrintUtils.printText("1.2元"+ "\n\n");
        //
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("胡萝卜");
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("5斤左右");
        PrintUtils.selectCommand(PrintUtils.ALIGN_RIGHT);
        PrintUtils.printText("2.5元"+ "\n\n");
        //
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("青椒");
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("5斤左右");
        PrintUtils.selectCommand(PrintUtils.ALIGN_RIGHT);
        PrintUtils.printText("4.5元"+ "\n\n");
        //
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("辣椒");
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("5斤左右");
        PrintUtils.selectCommand(PrintUtils.ALIGN_RIGHT);
        PrintUtils.printText("3.6元"+ "\n\n");
        //
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("西红柿");
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("5斤左右");
        PrintUtils.selectCommand(PrintUtils.ALIGN_RIGHT);
        PrintUtils.printText("5.2元"+ "\n\n");
        //
        PrintUtils.selectCommand(PrintUtils.ALIGN_LEFT);
        PrintUtils.printText("土豆");
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText("8斤左右");
        PrintUtils.selectCommand(PrintUtils.ALIGN_RIGHT);
        PrintUtils.printText("1.8元"+ "\n\n");

        PrintUtils.printText("--------------------------------\n\n");
        PrintUtils.printText("*******************************\n\n");
        PrintUtils.selectCommand(PrintUtils.ALIGN_CENTER);
        PrintUtils.printText(small_ticket_footnotes + "\n\n\n\n\n\n\n");
        PrintUtils.selectCommand(PrintUtils.QIEZHI);

        return "";
    }

    public static String getFPrice(double p) {
        return String.format("%.2f", p);
    }

    public static String getDoublePrice(double p) {
        BigDecimal decimal = new BigDecimal(p);
        return decimal.setScale(2, BigDecimal.ROUND_HALF_UP) + "";
    }

}
