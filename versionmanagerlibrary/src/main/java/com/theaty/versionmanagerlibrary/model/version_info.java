package com.theaty.versionmanagerlibrary.model;

import java.io.Serializable;

/**
 * Created by lixiangyi on 2017/12/6.
 */

public class version_info implements Serializable {

    public int version_id;//版本ID，客户端保存
    public String version_code;//版本编号(安卓返回：Android，IOS返回：IOS)
    public String update_url;//更新地址
    public int version_num;//版本号
    public String reviewed_status;//IOS审核状态
    public String update_reason;//提示




}
