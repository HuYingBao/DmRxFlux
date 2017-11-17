package com.huyingbao.dm.ui.device.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.devicepart.model.DevicePart;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.TimeUtils;

import java.util.List;

/**
 * 备品备件使用记录
 * Created by liujunfeng on 2017/5/31.
 */
public class DevicePartAdapter extends BaseQuickAdapter<DevicePart, BaseViewHolder> {
    public DevicePartAdapter(List<DevicePart> dataList) {
        super(R.layout.item_part, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, DevicePart item) {
        helper.setText(R.id.tv_part_time, TimeUtils.formatTime(item.getCreateTime()))
                .setText(R.id.tv_part_name, item.getName())
                .setText(R.id.tv_part_specification, item.getSpecification())
                .setText(R.id.tv_part_user, item.getUserName())
                .setText(R.id.tv_part_quantity, item.getQuantity() + item.getUnit())
                .setText(R.id.tv_part_type, item.isRepair() ? "维修" : "保养")
                .setBackgroundRes(R.id.tv_part_type, CommonUtils.getStatusColor(item.isRepair() ? Constants.STATUS_DEVICE_MAINTENANCE_KEEPING : Constants.STATUS_DEVICE_NORMAL));
    }
}
