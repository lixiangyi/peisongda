package com.theaty.peisongda.oss;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.blankj.utilcode.utils.EncryptUtils;
import com.blankj.utilcode.utils.ToastUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 支持普通上传
 */
public class OssService {

    private OSS oss;
    private String bucket;
    private Handler handler = new Handler(Looper.getMainLooper());
    private String image = "image/";
    private String video = "video/";
    private String url = "http://mamiriji.oss-cn-beijing.aliyuncs.com/";

    public OssService(OSS oss, String bucket) {
        this.oss = oss;
        this.bucket = bucket;

    }

    /**
     * @param urls
     * @function:多文件（同步）
     */
    public ArrayList<String> ossUploads(final List<String> urls, String firstUrl) {
        if (urls == null || urls.size() <= 0) {
            throw new IllegalArgumentException("urls must have item");
        }
        ArrayList<String> paths = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            String path = ossUploads(urls.get(i), firstUrl);
            if (path != null) {
                paths.add(path);
            } else {
                ToastUtils.showShortToastSafe(urls.get(i) + "多文件（同步）上传失败");
            }
        }
        return paths;
    }


    public interface Callback {
        void onSuccess(ArrayList<String> paths);

        void onFail(String message);
    }


    /**
     * @param urls
     * @function:多文件（异步）
     */
    public void ossAsyncUploads(final List<String> urls, final String firstUrl, final Callback callback) {
        if (urls == null || urls.size() <= 0) {
            throw new IllegalArgumentException("urls must have item");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<String> paths = new ArrayList<>();
                for (int i = 0; i < urls.size(); i++) {
                    String path = ossUploads(urls.get(i), firstUrl);
                    if (path != null) {
                        paths.add(path);
                    } else {
                        ToastUtils.showShortToastSafe(urls.get(i) + "多文件（异步）上传失败");
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (paths.size() == urls.size()) {
                                callback.onSuccess(paths);
                            } else {
                                callback.onFail("多文件（异步）上传出错");
                            }

                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 单文件上传（异步）
     *
     * @param url
     */
    public void ossAsyncUploads(final String url, String firstUrl) {
//        ArrayList<String> urls = new ArrayList<>();
//         urls.add1(url);
//        ArrayList<String> paths = new ArrayList<>();
//        urls.add1(path);
//        return ossUploads(urls,paths);  // 文件后缀
        if (url.isEmpty()) {
            return;
        }
        final File file = new File(url);
        if (null == file || !file.exists()) {
            return;
        }
        String fileSuffix = "";
        if (file.isFile()) {
            // 获取文件后缀名
            fileSuffix = file.getName().substring(file.getName().lastIndexOf("."));
        }
        String fileName = EncryptUtils.encryptMD5ToString(System.currentTimeMillis() + UUID.randomUUID().toString()) + fileSuffix;
        // 文件标识符objectKey
        final String objectKey = firstUrl + fileName;
        String path = Ossutil.firstPath + objectKey;
        // 下面3个参数依次为bucket名，ObjectKey名，上传文件路径
        PutObjectRequest put = new PutObjectRequest(bucket, objectKey, url);

        // 设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                // 进度逻辑

            }
        });

        // 异步上传
        OSSAsyncTask task = oss.asyncPutObject(put,
                new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) { // 上传成功
                        Log.e("*******", objectKey + "上传成功了");
                        ToastUtils.showLongToast(objectKey + "上传成功了");
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientExcepion,
                                          ServiceException serviceException) { // 上传失败
                        Log.e("*******", objectKey + "上传失败了");
                        ToastUtils.showLongToast(objectKey + "上传失败了");
                        // 请求异常
                        if (clientExcepion != null) {
                            // 本地异常如网络异常等
                            clientExcepion.printStackTrace();
                        }
                        if (serviceException != null) {
                            // 服务异常
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                    }
                });
        if (task.isCompleted()) {
            Log.e("*******", path + "上传成功了");
        }
    }


    /**
     * 单文件上传(同步)
     *
     * @param url
     */
    public String ossUploads(final String url, String firstUrl) {
        if (url.isEmpty()) {
            return null;
        }
        final File file = new File(url);
        if (!file.exists()) {
            return null;
        }

        String fileSuffix = "";
        if (file.isFile()) {
            // 获取文件后缀名
            fileSuffix = file.getName().substring(file.getName().lastIndexOf("."));
        }
        // 文件标识符objectKey
        String fileName = EncryptUtils.encryptMD5ToString(System.currentTimeMillis() + UUID.randomUUID().toString()) + fileSuffix;
        final String objectKey = firstUrl + fileName;
        String path = Ossutil.firstPath + objectKey;
        // 下面3个参数依次为bucket名，ObjectKey名，上传文件路径
        PutObjectRequest put = new PutObjectRequest(bucket, objectKey, url);

        // 设置进度回调
        try {
            PutObjectResult result = oss.putObject(put);
            if (result.getStatusCode() == 200) {

                Log.e("*******", objectKey + "上传成功了");
                return fileName;
            } else {
                Log.e("*******", objectKey + " 失败");
                return null;
            }
        } catch (ClientException clientException) {
            System.out.println("******" + clientException.getMessage());
            clientException.printStackTrace();
            return null;
        } catch (ServiceException serviceException) {
            System.out.println("******" + serviceException.getMessage());
            Log.e("ErrorCode", serviceException.getErrorCode());
            Log.e("RequestId", serviceException.getRequestId());
            Log.e("HostId", serviceException.getHostId());
            Log.e("RawMessage", serviceException.getRawMessage());
            return null;
        }
    }

    /**
     * 单文件上传(同步)  不随机生成
     *
     * @param url
     */
    public String ossUploadsOfBook(final String url, String firstUrl) {
        if (url.isEmpty()) {
            return null;
        }
        final File file = new File(url);
        if (!file.exists()) {
            return null;
        }

        String fileSuffix = "";
        if (file.isFile()) {
            // 获取文件后缀名
            fileSuffix = file.getName().substring(file.getName().lastIndexOf("."));
        }
        // 文件标识符objectKey
        String fileName = EncryptUtils.encryptMD5ToString(System.currentTimeMillis() + UUID.randomUUID().toString()) + fileSuffix;
        final String objectKey = firstUrl;
        String path = Ossutil.firstPath + objectKey;
        // 下面3个参数依次为bucket名，ObjectKey名，上传文件路径
        PutObjectRequest put = new PutObjectRequest(bucket, objectKey, url);

        // 设置进度回调
        try {
            PutObjectResult result = oss.putObject(put);
            if (result.getStatusCode() == 200) {

                Log.e("*******", objectKey + "上传成功了");
                return fileName;
            } else {
                Log.e("*******", objectKey + " 失败");
                return null;
            }
        } catch (ClientException clientException) {
            System.out.println("******" + clientException.getMessage());
            clientException.printStackTrace();
            return null;
        } catch (ServiceException serviceException) {
            System.out.println("******" + serviceException.getMessage());
            Log.e("ErrorCode", serviceException.getErrorCode());
            Log.e("RequestId", serviceException.getRequestId());
            Log.e("HostId", serviceException.getHostId());
            Log.e("RawMessage", serviceException.getRawMessage());
            return null;
        }
    }


    /**
     * 单文件上传(同步)
     * 返回全路径
     *
     * @param url
     */
    public String ossUploads2(final String url, String firstUrl) {
        if (url.isEmpty()) {
            return null;
        }
        final File file = new File(url);
        if (!file.exists()) {
            return null;
        }

        String fileSuffix = "";
        if (file.isFile()) {
            // 获取文件后缀名
            fileSuffix = file.getName().substring(file.getName().lastIndexOf("."));
        }
        // 文件标识符objectKey
        String fileName = EncryptUtils.encryptMD5ToString(System.currentTimeMillis() + UUID.randomUUID().toString()) + fileSuffix;
        final String objectKey = firstUrl + fileName;
        String path = Ossutil.firstPath + objectKey;
        // 下面3个参数依次为bucket名，ObjectKey名，上传文件路径
        PutObjectRequest put = new PutObjectRequest(bucket, objectKey, url);

        // 设置进度回调
        try {
            PutObjectResult result = oss.putObject(put);
            if (result.getStatusCode() == 200) {

                Log.e("*******", objectKey + "上传成功了");
                return path;
            } else {
                Log.e("*******", objectKey + " 失败");
                return null;
            }
        } catch (ClientException clientException) {
            System.out.println("******" + clientException.getMessage());
            clientException.printStackTrace();
            return null;
        } catch (ServiceException serviceException) {
            System.out.println("******" + serviceException.getMessage());
            Log.e("ErrorCode", serviceException.getErrorCode());
            Log.e("RequestId", serviceException.getRequestId());
            Log.e("HostId", serviceException.getHostId());
            Log.e("RawMessage", serviceException.getRawMessage());
            return null;
        }
    }


    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
