package com.huyingbao.dm.ui.maintenance.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.dm.ui.device.adapter.DeviceMaintainPlanAdapter;

/**
 * 保养计划
 * Created by liujunfeng on 2017/5/26.
 */
public class MaintainPlanYear extends AbstractExpandableItem<MaintainPlan> implements MultiItemEntity {
    /**
     * id : 4
     * machineId : 7
     * planDate : 2017-06-18
     * planYear : 2017
     * rank : 2
     * remark : test remark
     * yearPlanId : 1
     */
    private String planYear;
    private int yearPlanId;

    public MaintainPlanYear(String planYear, int yearPlanId) {
        this.planYear = planYear;
        this.yearPlanId = yearPlanId;
    }

    public String getPlanYear() {
        return planYear;
    }

    public void setPlanYear(String planYear) {
        this.planYear = planYear;
    }

    public int getYearPlanId() {
        return yearPlanId;
    }

    public void setYearPlanId(int yearPlanId) {
        this.yearPlanId = yearPlanId;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return DeviceMaintainPlanAdapter.MAINTAIN_PLAN_YEAR;
    }
}
