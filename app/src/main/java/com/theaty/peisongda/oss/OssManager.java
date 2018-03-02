package com.theaty.peisongda.oss;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;

import static org.droidparts.Injector.getApplicationContext;

/**
 * Created by lixiangyi on 2017/4/19.
 */

public class OssManager {
    //private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    //private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";

//    private static final String callbackAddress = "http://182.92.8.122/tymallbase/mobile/osstest/testdatas";
//    private static String bucket = "zhizhanxiao";
//    private static String bucket = "tymallbase";
    //private static String bucket = "mamiriji";

//    private static String stsServer = "http://101.200.173.3/mobile/Osstest/index";

    //private static String stsServer = "http://182.92.8.122/tymallbase/Mobile/Osstest/";
   // private static String stsServer = " http://47.94.198.177/mobile/osstest/index";
//    private static String stsServer = "  http://182.92.8.122/tymallbase/Mobile/Osstest/";
    private static OssService ossService;
    //初始化一个OssService用来上传下载
    public static void initOSS() {
        //如果希望直接使用accessKey来访问的时候，可以直接使用OSSPlainTextAKSKCredentialProvider来鉴权。
        // OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);

        //使用自己的获取STSToken的类
        OSSCredentialProvider credentialProvider = new STSGetter(Ossutil.stsServer);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        OSS oss = new OSSClient(getApplicationContext(), Ossutil.endpoint, credentialProvider, conf);

        ossService =  new OssService(oss, Ossutil.bucket);

    }

    public static OssService getOssService() {
        return ossService;
    }
}
