package com.huyingbao.rxflux2.constant;

/**
 * Created by liujunfeng on 2017/5/26.
 */
public interface Constants {
    String SERVICE_NAME = "com.huyingbao.dm.service.DmService";
    String RECEIVER_NAME = "com.huyingbao.rxflux2.receiver.AlarmReceiver";
    String PROVIDER_NAME = "com.huyingbao.dm.pugongying";

    String SUCCESS_CODE = "0";
    String ERROR_SESSION_TIMEOUT = "8000";//session过期
    String ERROR_SERVER = "8001";//服务器异常

    int ROLE_USER_ADMIN = 0;//管理员
    int ROLE_USER_MACHINE_ADMIN = 1;//设备管理员
    int ROLE_USER_MACHINE_REPAIR = 2;//设备维修员
    int ROLE_USER_MACHINE_INSPECTION = 3;//设备巡检员

    int DEGREE_ONE = 1;
    int DEGREE_TWO = 2;
    int DEGREE_THREE = 3;
    int DEGREE_FOUR = 4;

    int STATUS_DEVICE_ALL = -1024;//全部

    int STATUS_DEVICE_CHECK = -1023;//保养完成待验收,维修完成待验收
    int STATUS_DEVICE_CHECK_MAINTENANCE = 31;//保养完成待验收
    int STATUS_DEVICE_CHECK_REPAIR = 22;//维修完成待验收

    int STATUS_DEVICE_ERROR = -1021;//停用报废
    int STATUS_DEVICE_SCRAP = -1;//报废
    int STATUS_DEVICE_DISABLE = 0;//停用

    int STATUS_DEVICE_NORMAL = 1;//正常
    int STATUS_DEVICE_MAINTENANCE_WAITING = 10;//待保养
    int STATUS_DEVICE_INSPECTION = 11;//待巡检


    int STATUS_DEVICE_REPAIR = 2;//故障
    int STATUS_DEVICE_REPAIR_WAITING = 20;//待维修
    int STATUS_DEVICE_REPAIR_KEEPING = 21;//维修中


    int STATUS_DEVICE_MAINTENANCE_KEEPING = 30;//保养中
    int STATUS_DEVICE_MAINTENANCE = 3;//保养

    int TYPE_REPAIR_1 = 1;//设备
    int TYPE_REPAIR_2 = 2;//磨具
    int TYPE_REPAIR_3 = 3;//工装
    int TYPE_REPAIR_4 = 4;//其它

    int TYPE_RESULT_NO_START = -1;//未开始
    int TYPE_RESULT_PAUSE = 0;//暂停
    int TYPE_RESULT_KEEPING = 1;//进行中
    int TYPE_RESULT_CHECK = 2;//待验收
    int TYPE_RESULT_FINISH = 3;//完成

    int TYPE_RESULT_INSPECTION_NO_START = -1;//未开始,
    int TYPE_RESULT_INSPECTION_ERROR = 0;//故障；
    int TYPE_RESULT_INSPECTION_NORMAL = 1;//正常；
    int TYPE_RESULT_INSPECTION_NO_PASS = 2;//不通过；

    int RANK_1 = 1;//保养级别
    int RANK_2 = 2;//保养级别
    int RANK_3 = 3;//保养级别

    int GET_DEVICE_DETAIL_BY_CODE = 0x0000c0de;//扫描,获取设备详情

    int GET_REPAIR_DETAIL_BY_DEVICE = 0x0000c0d1;//扫描,获取维修详情
    int GET_MAINTENANCE_DETAIL_BY_DEVICE = 0x0000c0d2;//扫描,获取保养详情
    int GET_INSPECTION_DETAIL_BY_DEVICE = 0x0000c0d2;//扫描,获取巡检详情

    int BASE_DEVICE_TYPE_INSPECTION_TITLE = 0;
    int BASE_DEVICE_TYPE_INSPECTION = 1;

    int MAINTAIN_ITEM_TITLE = 0;
    int MAINTAIN_ITEM = 1;

    int MESSAGE_TYPE_INFO = 0;
    int MESSAGE_TYPE_REPAIR = 1;
    int MESSAGE_TYPE_MAINTAIN = 2;
    int MESSAGE_TYPE_INSPECTION = 3;
    int MESSAGE_TYPE_CHECK_REPAIR = 4;
    int MESSAGE_TYPE_CHECK_MAINTAIN = 5;
}
