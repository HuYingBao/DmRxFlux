package com.huyingbao.dm.ui.device.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.inspection.model.Inspection;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.TimeUtils;

import java.util.List;

/**
 * 巡检列表展示
 * Created by liujunfeng on 2017/3/30.
 */
public class DeviceInspectionAdapter extends BaseQuickAdapter<Inspection, BaseViewHolder> {
    public DeviceInspectionAdapter(List<Inspection> dataList) {
        super(R.layout.item_inspection, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Inspection item) {
        helper.setImageResource(R.id.iv_inspection_result, CommonUtils.getInspectionResultImg(item.getResult()))
                .setText(R.id.tv_inspection_time, TimeUtils.formatTime(item.getPlanDate()))
                .setText(R.id.tv_inspection_user, item.getResult() == Constants.TYPE_RESULT_INSPECTION_NO_START ? "未执行" : item.getInspectorName());
    }
}
