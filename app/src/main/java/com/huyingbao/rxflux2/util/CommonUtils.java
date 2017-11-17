package com.huyingbao.rxflux2.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.dm.ui.device.DeviceDetailActivity;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.dm.ui.devicelist.model.DeviceType;
import com.huyingbao.dm.ui.inspection.InspectionDetailActivity;
import com.huyingbao.dm.ui.maintenance.MaintenanceDetailActivity;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.dm.ui.message.model.Message;
import com.huyingbao.dm.ui.repair.RepairDetailActivity;
import com.huyingbao.dm.ui.repair.model.Repair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujunfeng on 2017/1/1.
 */
public class CommonUtils {
    /**
     * 处理Message跳转
     *
     * @param context
     * @param message
     */
    public static void handleMessage(Context context, @NonNull Message message) {
        switch (message.getType()) {
            case Constants.MESSAGE_TYPE_INFO:
                Device device = new Device();
                device.setId(message.getTargetId());
                context.startActivity(DeviceDetailActivity.newIntent(context, device));
                break;
            case Constants.MESSAGE_TYPE_REPAIR:
                Repair repair = new Repair();
                repair.setId(message.getTargetId());
                context.startActivity(RepairDetailActivity.newIntent(context, repair, Actions.TO_REPAIR_ITEM));
                break;
            case Constants.MESSAGE_TYPE_MAINTAIN:
                Maintenance maintenance = new Maintenance();
                maintenance.setId(message.getTargetId());
                context.startActivity(MaintenanceDetailActivity.newIntent(context, maintenance, Actions.TO_MAINTENANCE_ITEM));
                break;
            case Constants.MESSAGE_TYPE_INSPECTION:
                context.startActivity(InspectionDetailActivity.newIntent(context));
                break;
            case Constants.MESSAGE_TYPE_CHECK_REPAIR:
                Repair checkRepair = new Repair();
                checkRepair.setId(message.getTargetId());
                context.startActivity(RepairDetailActivity.newIntent(context, checkRepair, Actions.TO_REPAIR_CHECK));
                break;
            case Constants.MESSAGE_TYPE_CHECK_MAINTAIN:
                Maintenance checkMaintenance = new Maintenance();
                checkMaintenance.setId(message.getTargetId());
                context.startActivity(MaintenanceDetailActivity.newIntent(context, checkMaintenance, Actions.TO_MAINTENANCE_CHECK));
                break;
        }
    }

    /**
     * 获取设备状态列表
     * 用户1:设备筛选对话框展示
     *
     * @return
     */
    public static List<DeviceType> getDeviceStatusList() {
        List<DeviceType> arrayList = new ArrayList<>();
        arrayList.add(new DeviceType(Constants.STATUS_DEVICE_NORMAL, CommonUtils.getStatusName(Constants.STATUS_DEVICE_NORMAL)));
        arrayList.add(new DeviceType(Constants.STATUS_DEVICE_REPAIR, CommonUtils.getStatusName(Constants.STATUS_DEVICE_REPAIR)));
        arrayList.add(new DeviceType(Constants.STATUS_DEVICE_CHECK, CommonUtils.getStatusName(Constants.STATUS_DEVICE_CHECK)));

        arrayList.add(new DeviceType(Constants.STATUS_DEVICE_MAINTENANCE_WAITING, CommonUtils.getStatusName(Constants.STATUS_DEVICE_MAINTENANCE_WAITING)));
        arrayList.add(new DeviceType(Constants.STATUS_DEVICE_MAINTENANCE_KEEPING, CommonUtils.getStatusName(Constants.STATUS_DEVICE_MAINTENANCE_KEEPING)));
        arrayList.add(new DeviceType(Constants.STATUS_DEVICE_INSPECTION, CommonUtils.getStatusName(Constants.STATUS_DEVICE_INSPECTION)));

        arrayList.add(new DeviceType(Constants.STATUS_DEVICE_REPAIR_KEEPING, CommonUtils.getStatusName(Constants.STATUS_DEVICE_REPAIR_KEEPING)));
        arrayList.add(new DeviceType(Constants.STATUS_DEVICE_ERROR, CommonUtils.getStatusName(Constants.STATUS_DEVICE_ERROR)));
        arrayList.add(new DeviceType(Constants.STATUS_DEVICE_REPAIR_WAITING, CommonUtils.getStatusName(Constants.STATUS_DEVICE_REPAIR_WAITING)));
        return arrayList;
    }

    /**
     * 获取设备状态对应名称
     * 用于1:设备详情页面展示,2:设备列表页面展示,3:设备筛选对话框
     *
     * @return
     */
    public static String getStatusName(int deviceStatus) {
        switch (deviceStatus) {
            default:
            case Constants.STATUS_DEVICE_NORMAL:
                return " 正常 ";
            case Constants.STATUS_DEVICE_REPAIR:
                return " 故障 ";
            case Constants.STATUS_DEVICE_CHECK:
                return "待验收";

            case Constants.STATUS_DEVICE_MAINTENANCE_WAITING:
                return "待保养";
            case Constants.STATUS_DEVICE_MAINTENANCE_KEEPING:
                return "保养中";
            case Constants.STATUS_DEVICE_INSPECTION:
                return "待巡检";
            case Constants.STATUS_DEVICE_REPAIR_KEEPING:
                return "维修中";
            case Constants.STATUS_DEVICE_ERROR:
                return " 报废停用 ";
            case Constants.STATUS_DEVICE_REPAIR_WAITING:
                return " 待维修 ";

            case Constants.STATUS_DEVICE_CHECK_MAINTENANCE:
                return "保养待验收";
            case Constants.STATUS_DEVICE_CHECK_REPAIR:
                return "维修待验收";

            case Constants.STATUS_DEVICE_DISABLE:
                return "停用";
            case Constants.STATUS_DEVICE_SCRAP:
                return "报废";
            case Constants.STATUS_DEVICE_MAINTENANCE:
                return "保养";
        }
    }

    /**
     * 获取设备状态对应色值
     *
     * @return
     */
    @ColorRes
    public static int getStatusColor(int deviceStatus) {
        switch (deviceStatus) {
            default:
            case Constants.STATUS_DEVICE_NORMAL:
                return R.color.status_device_normal;
            case Constants.STATUS_DEVICE_REPAIR:
                return R.color.status_device_repair;
            case Constants.STATUS_DEVICE_CHECK:
                return R.color.status_device_check;

            case Constants.STATUS_DEVICE_MAINTENANCE:
            case Constants.STATUS_DEVICE_MAINTENANCE_KEEPING:
                return R.color.status_device_maintenance_keeping;
            case Constants.STATUS_DEVICE_INSPECTION:
                return R.color.status_device_inspection;
            case Constants.STATUS_DEVICE_MAINTENANCE_WAITING:
                return R.color.status_device_maintenance;

            case Constants.STATUS_DEVICE_REPAIR_KEEPING:
                return R.color.status_device_repair_keeping;
            case Constants.STATUS_DEVICE_ERROR:
                return R.color.status_device_disable;
            case Constants.STATUS_DEVICE_REPAIR_WAITING:
                return R.color.status_device_scrap;
        }
    }

    /**
     * 获取维修类型对应名称
     *
     * @param repairType
     * @return
     */
    public static String getRepairTypeName(int repairType) {
        switch (repairType) {
            default:
            case Constants.TYPE_REPAIR_4:
                return " 其它 ";
            case Constants.TYPE_REPAIR_1:
                return " 设备 ";
            case Constants.TYPE_REPAIR_2:
                return " 磨具 ";
            case Constants.TYPE_REPAIR_3:
                return " 工装 ";
        }
    }

    /**
     * 获取维修结果、保养结果对应图标
     *
     * @return
     */
    @DrawableRes
    public static int getResultImg(int result) {
        switch (result) {
            default:
            case Constants.TYPE_RESULT_NO_START:
                return R.drawable.ic_v_large_result_no_start;
            case Constants.TYPE_RESULT_PAUSE:
                return R.drawable.ic_v_large_result_pause;
            case Constants.TYPE_RESULT_KEEPING:
                return R.drawable.ic_v_large_result_keeping;
            case Constants.TYPE_RESULT_CHECK:
                return R.drawable.ic_v_large_result_check;
            case Constants.TYPE_RESULT_FINISH:
                return R.drawable.ic_v_large_result_finish;
        }
    }

    /**
     * 获取维修结果、保养结果对应图标
     *
     * @return
     */
    @DrawableRes
    public static int getResultSmallImg(int result) {
        switch (result) {
            default:
            case Constants.TYPE_RESULT_NO_START:
                return R.drawable.ic_v_result_no_start;
            case Constants.TYPE_RESULT_PAUSE:
                return R.drawable.ic_v_result_pause;
            case Constants.TYPE_RESULT_KEEPING:
                return R.drawable.ic_v_result_keeping;
            case Constants.TYPE_RESULT_CHECK:
                return R.drawable.ic_v_result_check;
            case Constants.TYPE_RESULT_FINISH:
                return R.drawable.ic_v_result_finish;
        }
    }

    /**
     * 获取维修结果、保养结果对应颜色
     *
     * @return
     */
    @ColorRes
    public static int getResultColor(int result) {
        switch (result) {
            default:
            case Constants.TYPE_RESULT_NO_START:
                return R.color.status_device_check;
            case Constants.TYPE_RESULT_PAUSE:
                return R.color.status_device_disable;
            case Constants.TYPE_RESULT_KEEPING:
                return R.color.status_device_repair;
            case Constants.TYPE_RESULT_CHECK:
                return R.color.status_device_check;
            case Constants.TYPE_RESULT_FINISH:
                return R.color.status_device_normal;
        }
    }

    /**
     * 获取维修结果、保养结果对应名字
     *
     * @return
     */
    public static String getResultName(int result) {
        switch (result) {
            default:
            case Constants.TYPE_RESULT_NO_START:
                return "未开始";
            case Constants.TYPE_RESULT_PAUSE:
                return "暂停";
            case Constants.TYPE_RESULT_KEEPING:
                return "进行中";
            case Constants.TYPE_RESULT_CHECK:
                return "待验收";
            case Constants.TYPE_RESULT_FINISH:
                return "完成";
        }
    }

    /**
     * 只有两种结果
     * 获取保养项结果图标
     *
     * @param result
     * @return
     */
    public static int getSwitchResultImg(boolean result) {
        return result ? R.drawable.ic_v_result_success : R.drawable.ic_v_result_fail;
    }

    /**
     * 获取巡检四种结果对应四种图标
     *
     * @param inspectionResult
     * @return
     */
    public static int getInspectionResultImg(int inspectionResult) {
        switch (inspectionResult) {
            default:
            case Constants.TYPE_RESULT_INSPECTION_NO_START:
                return R.drawable.ic_v_result_no_start;
            case Constants.TYPE_RESULT_INSPECTION_ERROR:
                return R.drawable.ic_v_result_error;
            case Constants.TYPE_RESULT_INSPECTION_NORMAL:
                return R.drawable.ic_v_result_success;
            case Constants.TYPE_RESULT_INSPECTION_NO_PASS:
                return R.drawable.ic_v_result_fail;
        }
    }

    /**
     * 获取巡检四种结果对应四种图标
     *
     * @param inspectionResult
     * @return
     */
    @ColorRes
    public static int getInspectionResultColor(int inspectionResult) {
        switch (inspectionResult) {
            default:
            case Constants.TYPE_RESULT_INSPECTION_NO_START:
                return R.color.status_device_check;
            case Constants.TYPE_RESULT_INSPECTION_ERROR:
                return R.color.status_device_repair;
            case Constants.TYPE_RESULT_INSPECTION_NORMAL:
                return R.color.status_device_normal;
            case Constants.TYPE_RESULT_INSPECTION_NO_PASS:
                return R.color.status_device_repair;
        }
    }

    /**
     * 获取巡检四种结果对应四种名字
     *
     * @param inspectionResult
     * @return
     */
    public static String getInspectionResultName(int inspectionResult) {
        switch (inspectionResult) {
            default:
            case Constants.TYPE_RESULT_INSPECTION_NO_START:
                return "未开始";
            case Constants.TYPE_RESULT_INSPECTION_ERROR:
                return "故障";
            case Constants.TYPE_RESULT_INSPECTION_NORMAL:
                return "正常";
            case Constants.TYPE_RESULT_INSPECTION_NO_PASS:
                return "不通过";
        }
    }

    /**
     * 获取view对应的状态,
     *
     * @return
     */
    public static int getStatusByView(@IdRes int viewId) {
        switch (viewId) {
            default:
            case R.id.rl_status_normal:
                return Constants.STATUS_DEVICE_NORMAL;
            case R.id.rl_status_reapir:
                return Constants.STATUS_DEVICE_REPAIR;
            case R.id.rl_status_check:
                return Constants.STATUS_DEVICE_CHECK;

            case R.id.rl_status_maintenance:
                return Constants.STATUS_DEVICE_MAINTENANCE_WAITING;
            case R.id.rl_status_maintenance_keeping:
                return Constants.STATUS_DEVICE_MAINTENANCE_KEEPING;
            case R.id.rl_status_inspection:
                return Constants.STATUS_DEVICE_INSPECTION;

            case R.id.rl_status_repair_keeping:
                return Constants.STATUS_DEVICE_REPAIR_KEEPING;
            case R.id.rl_status_error:
                return Constants.STATUS_DEVICE_ERROR;
            case R.id.rl_status_repair_waiting:
                return Constants.STATUS_DEVICE_REPAIR_WAITING;
        }
    }

    /**
     * 获取保养级别 对应的图标
     *
     * @param rank
     * @return
     */
    public static int getRankImg(int rank) {
        switch (rank) {
            default:
            case Constants.RANK_1:
                return R.drawable.bg_o_so_red;
            case Constants.RANK_2:
                return R.drawable.bg_o_so_orange;
            case Constants.RANK_3:
                return R.drawable.bg_o_so_blue;
        }
    }

    /**
     * 从 Activity 隐藏键盘
     */
    public static void closeInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && inputMethodManager.isActive()) {
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus == null) currentFocus = new View(activity);
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 从 Fragment 隐藏键盘
     *
     * @param context
     * @param view
     */
    public static void closeInputByDialogFragment(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    /**
     * 判断list是否可用
     *
     * @param list
     * @return true:不为空且size>0
     */
    public static boolean isListAble(List list) {
        return list != null && list.size() > 0;
    }

    /**
     * 获取statusbar 的高度
     *
     * @param resources
     * @return
     */
    public static int getStatusBarHeight(Resources resources) {
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 当前activity是否显示在主界面
     *
     * @param context
     * @return true 当前可见
     */
    public static boolean isTopActivity(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            if (context.getPackageName().equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否可见
     *
     * @param context
     * @return true 当前可见
     */
    public static boolean isVisible(Context context) {
        return !((KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode();
    }

    /**
     * 设置textview值,如果没有值,则不显示
     *
     * @param textView
     * @param value
     */
    public static void setTextViewValue(@NonNull TextView textView, CharSequence value) {
        setTextViewValue(textView, value, "");
    }

    /**
     * 设置textview值,如果没有值,则不显示
     *
     * @param textView
     * @param value
     * @param title
     */
    public static void setTextViewValue(@NonNull TextView textView, CharSequence value, String title) {
        if (TextUtils.isEmpty(value)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(title + value);
        }
    }
}


