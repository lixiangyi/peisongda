package com.theaty.peisongda.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.theaty.peisongda.R;
import com.theaty.peisongda.UmengShareUtils;
import com.theaty.peisongda.model.BaseModel;
import com.theaty.peisongda.model.ResultsModel;
import com.theaty.peisongda.model.peisongda.MemberModel;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;
import foundation.base.activity.BaseActivity;
import foundation.toast.ToastUtil;

public class MyCodeActivity extends BaseActivity {
    @BindView(R.id.code)
    ImageView code;
    @BindView(R.id.weixin)
    ImageView weixin;
    @BindView(R.id.circle)
    ImageView circle;
    @BindView(R.id.local)
    ImageView local;
    private String codeImg = "";
    Bitmap bitmap;
    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_my_code);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的二维码");
        registerBack();
        initView();
    }

    private void initView() {
        new MemberModel().get_member_code(new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {
                     showLoading();
            }

            @Override
            public void successful(Object o) {

                codeImg = o.toString();
                hideLoading();
                Glide.with(MyCodeActivity.this).load(o.toString()).placeholder(R.drawable.empty_img).into(code);

            }

            @Override
            public void failed(ResultsModel resultsModel) {
                    hideLoading(resultsModel.getErrorMsg());
            }
        });


    }

    public static void into(Context context) {
        Intent starter = new Intent(context, MyCodeActivity.class);

        context.startActivity(starter);
    }




    @OnClick({R.id.weixin, R.id.circle, R.id.local})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.weixin:
                new UmengShareUtils(this).share(codeImg, SHARE_MEDIA.WEIXIN);
                break;
            case R.id.circle:
                new UmengShareUtils(this).share(codeImg, SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.local:
                if (codeImg.trim().length()>0) {
                    new Task().execute(codeImg);
                }else {
                    ToastUtil.showToast("二维码没有生成，请联系客服");
                }
                break;
        }
    }

    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0x123){
                SavaImage(bitmap, getSDPath()+"/送菜");
                ToastUtil.showToast("图片保存成功并添加到相册");
            }
        }
    };

    /**
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            url = new URL(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(8000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    /**
     * 异步线程下载图片
     *
     */
    class Task extends AsyncTask<String, Integer, Void> {

        protected Void doInBackground(String... params) {
            bitmap=GetImageInputStream((String)params[0]);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Message message=new Message();
            message.what=0x123;
            handler.sendMessage(message);
        }

    }

    /**
     * 保存位图到本地
     * @param bitmap
     * @param path 本地路径
     * @return void
     */
    public void SavaImage(Bitmap bitmap, String path){
        File file=new File(path);
        String pathimg = path+"/"+"二维码"+".png";
        FileOutputStream fileOutputStream=null;
        //文件夹不存在，则创建它
        if(!file.exists()){
            file.mkdir();
        }
        try {
            fileOutputStream=new FileOutputStream(pathimg);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,fileOutputStream);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + pathimg)));
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }
}
