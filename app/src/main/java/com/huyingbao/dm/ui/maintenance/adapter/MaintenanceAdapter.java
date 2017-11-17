package com.huyingbao.dm.ui.maintenance.adapter;

import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.maintenance.model.Maintenance;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.TimeUtils;
import com.huyingbao.rxflux2.util.imageloader.ImageLoaderUtils;

import java.util.List;

/**
 * 待维修的维修item
 * Created by liujunfeng on 2017/3/29.
 */
public class MaintenanceAdapter extends BaseQuickAdapter<Maintenance, BaseViewHolder> {
    public MaintenanceAdapter(List<Maintenance> data) {
        super(R.layout.item_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Maintenance item) {
        ((RatingBar) helper.getView(R.id.rb_device_degree)).setRating(item.getDegree());
        ImageLoaderUtils.loadImage(mContext, item.getMachineHeadImg(), R.mipmap.ic_logo, helper.getView(R.id.iv_device_img));
        helper.setText(R.id.tv_device_code, item.getMachineCode())
                .setText(R.id.tv_device_name, item.getMachineName())
                .setGone(R.id.tv_maintenance_rank, true)
                .setText(R.id.tv_maintenance_rank, item.getRank() + "")
                .setBackgroundRes(R.id.tv_maintenance_rank, CommonUtils.getRankImg(item.getRank()))
                .setText(R.id.tv_device_status, CommonUtils.getResultName(item.getResult()))
                .setBackgroundRes(R.id.tv_device_status, CommonUtils.getResultColor(item.getResult()))
                .setText(R.id.tv_device_type, TimeUtils.formatTime(item.getPlanDate()));
    }
}