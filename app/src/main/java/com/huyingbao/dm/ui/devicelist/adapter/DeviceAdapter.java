package com.huyingbao.dm.ui.devicelist.adapter;

import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.devicelist.model.Device;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.imageloader.ImageLoaderUtils;

import java.util.List;

/**
 * 设备列表页面适配器
 * Created by liujunfeng on 2017/3/29.
 */
public class DeviceAdapter<T extends Device> extends BaseQuickAdapter<T, BaseViewHolder> {
    public DeviceAdapter(List<T> data) {
        super(R.layout.item_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        ((RatingBar) helper.getView(R.id.rb_device_degree)).setRating(item.getDegree());
        ImageLoaderUtils.loadImage(mContext, item.getHeadImg(), R.mipmap.ic_logo, helper.getView(R.id.iv_device_img));
        helper.setText(R.id.tv_device_code, item.getCode())
                .setText(R.id.tv_device_name, item.getName())
                .setText(R.id.tv_device_status, CommonUtils.getStatusName(item.getStatusMain()))
                .setBackgroundRes(R.id.tv_device_status, CommonUtils.getStatusColor(item.getStatusMain()))
                .setText(R.id.tv_device_type, item.getType());
    }
}
