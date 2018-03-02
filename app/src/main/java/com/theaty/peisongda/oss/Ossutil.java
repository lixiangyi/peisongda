package com.theaty.peisongda.oss;


import com.theaty.peisongda.model.peisongda.MemberModel;
import com.theaty.peisongda.system.DatasStore;

import java.util.ArrayList;

/**
 * Created by lixiangyi on 2017/7/13.
 */

public class Ossutil {
    public static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    public static final String bucket = "tonghangty";
    public static final String stsServer = " http://123.56.248.81/mobile/osstest/index";
    public static final String firstPath = "http://tonghangty.oss-cn-beijing.aliyuncs.com/";
    public static final String firstUrl = "Upload/shop/";
    public static final String image = "image/";
    public static final String video = "video/";
    public static final String avatar = "avatar";
    public static final String baby = "baby/";
    public static final String trace = "trace/";
    public static final String diary = "diary/";
    public static final String book = "book/";
    public static final String index = "index";
    public static int isShow;

    //    public static final String diarycover = "diarycover/";
    //返回文件名
    public String getOssUrl(String path, String typeUrl) {
        String imgUrl = firstUrl + typeUrl + DatasStore.getCurMember().member_id + "/";
        MemberModel memberModel = DatasStore.getCurMember();
        String imgs = OssManager.getOssService().ossUploads(path, imgUrl);
        return imgs;
    }

    //返回文件名
    public String getOssUrlBook(String path, String typeUrl) {
        String imgUrl = firstUrl + typeUrl;
        MemberModel memberModel = DatasStore.getCurMember();
        String imgs = OssManager.getOssService().ossUploadsOfBook(path, imgUrl);
        return imgs;
    }

    //返回文件名
    public String getOssUrlAvatar(String path, String typeUrl) {
        String imgUrl = firstUrl + typeUrl + "/";
        String imgs = OssManager.getOssService().ossUploads(path, imgUrl);
        return imgs;
    }

    //返回文件全路径
    public String getOssUrl2(String path, String typeUrl) {
        String imgUrl = firstUrl + typeUrl + DatasStore.getCurMember().member_id + "/";
        String imgs = OssManager.getOssService().ossUploads2(path, imgUrl);
        return imgs;
    }

    public ArrayList<String> getOssUrl(ArrayList<String> path, String typeUrl) {
        String imgUrl = firstUrl + typeUrl + DatasStore.getCurMember().member_id + "/";
        final ArrayList<String> imgs = OssManager.getOssService().ossUploads(path, imgUrl);
        return imgs;
    }

    public String getAsyncOssUrl(String path, String typeUrl) {
        String imgUrl = firstUrl + typeUrl + DatasStore.getCurMember().member_id + "/";
        String imgs = "";
        OssManager.getOssService().ossAsyncUploads(path, imgUrl);
        return imgs;
    }

}
