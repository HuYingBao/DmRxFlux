package com.huyingbao.dm.ui.common.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.BR;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.devicepart.model.DevicePart;

import java.util.List;

/**
 * 备品备件添加
 * Created by liujunfeng on 2017/5/31.
 */
public class DevicePartAddAdapter extends BaseQuickAdapter<DevicePart, DevicePartAddAdapter.DevicePartViewHolder> {
    public DevicePartAddAdapter(List<DevicePart> dataList) {
        super(R.layout.item_device_part_add, dataList);
    }

    @Override
    protected void convert(DevicePartViewHolder helper, DevicePart item) {
        helper.itemView.setBackgroundResource(helper.getLayoutPosition() % 2 == 0 ? R.drawable.bg_st_gray_normal : R.drawable.bg_st_gray_so_gray_normal);
        helper.setText(R.id.tv_device_part_name, item.getName())
                .setText(R.id.tv_device_part_specification, item.getSpecification())
                .setText(R.id.tv_device_part_unit, item.getUnit());

        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.devicePart, item);
        binding.executePendingBindings();
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) return super.getItemView(layoutResId, parent);
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    protected class DevicePartViewHolder extends BaseViewHolder {

        public DevicePartViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }
}
