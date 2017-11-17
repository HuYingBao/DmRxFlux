package com.huyingbao.dm.ui.maintenance.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.dm.ui.device.adapter.DeviceMaintainPlanAdapter;

/**
 * 保养计划
 * Created by liujunfeng on 2017/5/26.
 */
public class MaintainPlan implements MultiItemEntity {
    /**
     * id : 4
     * machineId : 7
     * planDate : 2017-06-18
     * planYear : 2017
     * rank : 2
     * remark : test remark
     * yearPlanId : 1
     */
    private int id;
    private int machineId;
    private String planDate;
    private String planYear;
    private int rank;//保养级别：1、2、3
    private String remark;
    private int yearPlanId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getPlanYear() {
        return planYear;
    }

    public void setPlanYear(String planYear) {
        this.planYear = planYear;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getYearPlanId() {
        return yearPlanId;
    }

    public void setYearPlanId(int yearPlanId) {
        this.yearPlanId = yearPlanId;
    }

    @Override
    public int getItemType() {
        return DeviceMaintainPlanAdapter.MAINTAIN_PLAN;
    }
}
