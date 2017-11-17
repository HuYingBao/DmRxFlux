package com.huyingbao.dm.ui.common.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.devicepart.model.DevicePart;

import java.util.List;

/**
 * 备品备件删除
 * Created by liujunfeng on 2017/5/31.
 */
public class DevicePartDeleteAdapter extends BaseQuickAdapter<DevicePart, BaseViewHolder> {
    public DevicePartDeleteAdapter(List<DevicePart> dataList) {
        super(R.layout.item_device_part_delete, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, DevicePart item) {
        helper.itemView.setBackgroundResource(helper.getLayoutPosition() % 2 == 0 ? R.drawable.bg_st_gray_normal : R.drawable.bg_st_gray_so_gray_normal);
        helper.setText(R.id.tv_device_part_name, item.getName())
                .setText(R.id.tv_device_part_specification, item.getSpecification())
                .setText(R.id.tv_device_part_quantity, item.getQuantity() + item.getUnit());
        helper.addOnClickListener(R.id.tv_device_part_delete);
    }
}
