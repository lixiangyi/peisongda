package com.theaty.peisongda.oss;

import android.util.Log;

import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.theaty.peisongda.system.DatasStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2015/12/9 0009.
 * 重载OSSFederationCredentialProvider生成自己的获取STS的功能
 */
public class STSGetter extends OSSFederationCredentialProvider {

    String stsServer;

    public STSGetter(String stsServer) {
        this.stsServer = stsServer;
    }

    public OSSFederationToken getFederationToken() {

        String stsJson;
        OkHttpClient client = new OkHttpClient.Builder()
               // .addInterceptor(new HttpLoggingInterceptor())
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "key=" + DatasStore.getCurMember().key);
        Request request = new Request.Builder()
                .url(stsServer)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                stsJson = response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("GetSTSTokenFail", e.toString());
            return null;
        }

        try {

            JSONObject jsonObjs = new JSONObject(stsJson);
            int status = jsonObjs.getInt("status");
            if (status == 200) {

                String ak = jsonObjs.getString("AccessKeyId");
                String sk = jsonObjs.getString("AccessKeySecret");
                String token = jsonObjs.getString("SecurityToken");
                String expiration = jsonObjs.getString("Expiration");
                return new OSSFederationToken(ak, sk, token, expiration);
            } else {
                Log.d("GetSTSTokenFail", "status not equal 200");
                return null;
            }
        } catch (JSONException e) {
            Log.e("GetSTSTokenFail", e.toString());
            e.printStackTrace();
            return null;
        }
    }

}
