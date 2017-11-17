package com.huyingbao.rxflux2.util;

import com.huyingbao.dm.ui.devicelist.model.DeviceImg;

import java.util.ArrayList;

/**
 * TODO 假数据工具类
 * Created by liujunfeng on 2017/5/27.
 */
public class MockUtils {
    public static ArrayList<DeviceImg> mockDeviceImgList() {
        ArrayList<DeviceImg> deviceImgArrayList = new ArrayList<>();
        DeviceImg deviceImg = new DeviceImg();
        deviceImg.setFilePath("http://img.ivsky.com/img/tupian/t/201011/04/jixieshebei-118.jpg");
        DeviceImg deviceImg2 = new DeviceImg();
        deviceImg2.setFilePath("http://img.ivsky.com/img/tupian/t/201011/04/jixieshebei-116.jpg");
        DeviceImg deviceImg3 = new DeviceImg();
        deviceImg3.setFilePath("http://img.ivsky.com/img/tupian/t/201011/04/jixieshebei-119.jpg");
        DeviceImg deviceImg4 = new DeviceImg();
        deviceImg4.setFilePath("http://img.ivsky.com/img/tupian/t/201011/04/jixieshebei-120.jpg");
        deviceImgArrayList.add(deviceImg);
        deviceImgArrayList.add(deviceImg2);
        deviceImgArrayList.add(deviceImg3);
        deviceImgArrayList.add(deviceImg4);
        return deviceImgArrayList;
    }
}
