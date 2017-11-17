package com.huyingbao.dm.ui.device.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.repair.model.Repair;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.TimeUtils;

import java.util.List;

/**
 * 维修列表展示
 * Created by liujunfeng on 2017/3/30.
 */
public class DeviceRepairAdapter extends BaseQuickAdapter<Repair, BaseViewHolder> {
    public DeviceRepairAdapter(List<Repair> dataList) {
        super(R.layout.item_repair, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Repair item) {
        helper.setText(R.id.tv_repair_informant_time, TimeUtils.formatTime(item.getCreateTime()))
                .setText(R.id.tv_repair_id, item.getId() + "")
                .setText(R.id.tv_repair_duration, "用时:" + TimeUtils.secondConvertDayHourMin(item.getDuration()))
                .setText(R.id.tv_repair_user, item.getRepairManName())
                .setImageResource(R.id.iv_repair_result, CommonUtils.getResultImg(item.getResult()))
                .setText(R.id.tv_repair_type, CommonUtils.getRepairTypeName(item.getRepairType()))
                .setBackgroundRes(R.id.tv_repair_type, CommonUtils.getStatusColor(item.getRepairType()));
    }
}
