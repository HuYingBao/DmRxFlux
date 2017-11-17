package com.huyingbao.rxflux2.constant;

/**
 * 常量以及intent或者action中传递数据用的key
 * Created by liujunfeng on 2017/1/1.
 */
public interface ActionsKeys {
    String USER = "user";
    String NAME = "name";
    String PWD = "pwd";

    String DEVICE = "device";
    String REPAIR = "repair";//维修

    String RESPONSE = "response";

    String ID = "id";//设备id
    String CODE = "code";//设备code
    String MACHINE_CODE = "machineCode";//设备code
    String STATUS = "status";
    String PAGE = "page";//页码，默认0，base on 0
    String ROWS = "rows";//行数，默认20

    String NOTICE = "notice";//消息通知
    String CHANNEL_ID = "baiduChannelId";//百度推送id

    String IS_FIRST = "is_first";//是否第一次启动
    String IS_LOGIN = "is_login";//是否已经登录
    String IS_LOGIN_AUTO = "is_login_auto";//是否自动登录

    String ARRAY_TYPE = "types[]";//设备类型id，可以是多个
    String ARRAY_STATUS = "statuses[]";//设备状态,可以是多个,主状态，-1报废；0停用；1正常；2故障；3维修中；4待保养；5保养中；6待巡检；7保养完成待验收；8维修完成待验收

    String TITLE = "title";//标题
    String INFO = "info";//信息
    String CONTENT = "content";//输入的内容
    String CONTEXT = "context";//上下文
    String POSITION = "position";//位置

    String IMG_PATH_LIST = "img_path_list";
    String IMG_BEAN_LIST = "img_bean_list";

    String MAINTENANCE = "maintenance";
    String INSPECTION = "inspection";
    String MAINTAIN_ITEM_CONTENT = "maintain_item_content";
    String BASE_ACTION = "base_action";
    String SECOND_ACTION = "second_action";

    String REPAIR_ID = "repairId";
    String FAULT_CAUSE = "faultCause";
    String SCHEME = "scheme";
    String REMARK = "remark";
    String PAUSE_EXPLAIN = "pauseExplain";
    String SPAREPARTS = "spareparts";
    String MACHINE_TYPE_ID = "machineTypeId";
    String OPINION = "opinion";
    String MAINTAIN_ID = "maintainId";
    String PRE_EXPLAIN = "preExplain";
    String INSPECT_ID = "inspectId";
    String ITEMS = "items";
    String IS_REPAIR = "isRepair";//true：维修验收申请，false：保养验收申请，不传：全部验收申请
    String IS_SHOW_CANCEL = "is_show_cancel";
    String DEVICE_PART_LSIT = "device_part_list";
    String SERVER_IN_URL = "server_in_url";
    String SERVER_OUT_URL = "server_out_url";
    String SERVER_STATE = "server_state";
}
