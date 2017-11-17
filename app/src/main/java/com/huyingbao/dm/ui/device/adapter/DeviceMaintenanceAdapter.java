package com.huyingbao.dm.ui.device.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.TimeUtils;

import java.util.List;

/**
 * 保养列表展示
 * Created by liujunfeng on 2017/3/30.
 */
public class DeviceMaintenanceAdapter extends BaseQuickAdapter<Maintenance, BaseViewHolder> {
    public DeviceMaintenanceAdapter(List<Maintenance> dataList) {
        super(R.layout.item_maintenance, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Maintenance item) {
        helper.setText(R.id.tv_maintenance_time, TimeUtils.formatTime(item.getPlanDate()))
                .setText(R.id.tv_maintenance_id, item.getId() + "")
                .setText(R.id.tv_maintenance_duration, "用时:" + TimeUtils.secondConvertDayHourMin(item.getDuration()))
                .setText(R.id.tv_maintenance_user, item.getMaintainerName())
                .setImageResource(R.id.iv_maintenance_result, CommonUtils.getResultImg(item.getResult()))
                .setText(R.id.tv_maintenance_rank, item.getRank() + "")
                .setBackgroundRes(R.id.tv_maintenance_rank, CommonUtils.getRankImg(item.getRank()));
    }
}
