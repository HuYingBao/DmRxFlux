package com.huyingbao.dm.ui.checkdevice.adapter;

import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.checkdevice.model.CheckDevice;
import com.huyingbao.rxflux2.util.TimeUtils;
import com.huyingbao.rxflux2.util.imageloader.ImageLoaderUtils;

import java.util.List;

/**
 * 验收模块适配器
 * Created by liujunfeng on 2017/3/29.
 */
public class CheckDeviceAdapter extends BaseQuickAdapter<CheckDevice, BaseViewHolder> {
    public CheckDeviceAdapter(List<CheckDevice> data) {
        super(R.layout.item_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckDevice item) {
        ((RatingBar) helper.getView(R.id.rb_device_degree)).setRating(item.getDegree());
        ImageLoaderUtils.loadImage(mContext, item.getHeadImg(), R.mipmap.ic_logo, helper.getView(R.id.iv_device_img));
        helper.setText(R.id.tv_device_code, item.getCode())
                .setText(R.id.tv_device_name, item.getName() + "(" + item.getType() + ")")
                .setText(R.id.tv_device_status, item.isIsRepair() ? "维修待验收" : "保养待验收")
                .setBackgroundRes(R.id.tv_device_status, item.isIsRepair() ? R.color.status_device_maintenance_keeping : R.color.status_device_repair_keeping)
                .setText(R.id.tv_device_type, TimeUtils.formatTime(item.getFinishTime()));
    }
}
