package com.huyingbao.dm.ui.device.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.maintenance.model.MaintainPlan;
import com.huyingbao.dm.ui.maintenance.model.MaintainPlanYear;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.util.TimeUtils;

import java.util.List;

/**
 * 保养计划列表展示
 * Created by liujunfeng on 2017/3/30.
 */
public class DeviceMaintainPlanAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int MAINTAIN_PLAN_YEAR = 0;
    public static final int MAINTAIN_PLAN = 1;

    public DeviceMaintainPlanAdapter(List<MultiItemEntity> dataList) {
        super(dataList);
        addItemType(MAINTAIN_PLAN_YEAR, R.layout.item_maintain_item_title);
        addItemType(MAINTAIN_PLAN, R.layout.item_maintain_plan);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (item.getItemType()) {
            case MAINTAIN_PLAN_YEAR:
                final MaintainPlanYear maintainItemTitle = (MaintainPlanYear) item;
                helper.setText(R.id.tv_maintain_item_title, maintainItemTitle.getPlanYear())
                        .setImageResource(R.id.iv_maintain_item_title_drop, maintainItemTitle.isExpanded() ? R.drawable.ic_v_to_down : R.drawable.ic_v_to_right)
                        .setGone(R.id.iv_maintain_item_title_result, false);
                helper.itemView.setSelected(maintainItemTitle.isExpanded());
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    if (maintainItemTitle.isExpanded()) collapse(pos);
                    else expand(pos);
                });
                break;
            case MAINTAIN_PLAN:
                final MaintainPlan maintainPlan = (MaintainPlan) item;
                long intervalDays = TimeUtils.getIntervalDays(TimeUtils.formatTimeForDay(System.currentTimeMillis()), maintainPlan.getPlanDate());
                String interval = intervalDays < 0
                        ? (intervalDays == -1 ? "昨天" : intervalDays == -2 ? "前天" : -intervalDays + "天前")
                        : (intervalDays == 0 ? "今天" : intervalDays == 1 ? "明天" : intervalDays == 2 ? "后台" : intervalDays + "天后");
                helper.setBackgroundRes(R.id.tv_maintain_plan_rank, CommonUtils.getRankImg(maintainPlan.getRank()))
                        .setText(R.id.tv_maintain_plan_rank, maintainPlan.getRank() + "")
                        .setText(R.id.tv_maintain_plan_date, maintainPlan.getPlanDate())
                        .setText(R.id.tv_maintain_plan_interval, interval);
                break;
        }
    }
}
