package com.huyingbao.dm.ui.check.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.check.model.Check;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.TimeUtils;

import java.util.List;

/**
 * 验收列表展示
 * Created by liujunfeng on 2017/3/30.
 */
public class CheckAdapter extends BaseQuickAdapter<Check, BaseViewHolder> {
    public CheckAdapter(List<Check> dataList) {
        super(R.layout.item_check, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Check item) {
        helper.setImageResource(R.id.iv_check_result, CommonUtils.getSwitchResultImg(item.isCheckResult()))
                .setText(R.id.tv_check_time, TimeUtils.formatTime(item.getCheckTime()))
                .setText(R.id.tv_check_user, item.getCheckerName());
    }
}
