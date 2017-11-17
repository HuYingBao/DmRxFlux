package com.huyingbao.dm.ui.device.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.devicelist.model.DeviceType;

import java.util.List;

/**
 * 备品备件使用记录
 * Created by liujunfeng on 2017/5/31.
 */
public class DeviceTypeAdapter extends BaseQuickAdapter<DeviceType, BaseViewHolder> {
    public DeviceTypeAdapter(List<DeviceType> dataList) {
        super(R.layout.item_device_type, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceType item) {
        helper.setText(R.id.tv_device_type, item.getName())
                .setBackgroundRes(R.id.tv_device_type, item.isSelected() ? R.drawable.bg_click_btn_primary : R.drawable.bg_c_st_gray_normal)
                .setTextColor(R.id.tv_device_type, item.isSelected() ? mContext.getResources().getColor(R.color.primary_white) : mContext.getResources().getColor(R.color.text_secondary));
    }
}
