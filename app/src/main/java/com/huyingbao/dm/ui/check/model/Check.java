package com.huyingbao.dm.ui.check.model;

/**
 * 验收记录
 * Created by liujunfeng on 2017/5/26.
 */
public class Check {
    /**
     * checkResult : false
     * checkTime : 2017-07-26 21:31:16
     * checkerId : 2
     * checkerName : 杨前锋
     * id : 1
     * repairId : 10000
     */
    private boolean checkResult;
    private String checkTime;
    private int checkerId;
    private String checkerName;
    private int id;
    private int repairId;
    private int maintainId;
    private int planId;
    private String checkOpinion;

    public boolean isCheckResult() {
        return checkResult;
    }

    public void setCheckResult(boolean checkResult) {
        this.checkResult = checkResult;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public int getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(int checkerId) {
        this.checkerId = checkerId;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRepairId() {
        return repairId;
    }

    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    public int getMaintainId() {
        return maintainId;
    }

    public void setMaintainId(int maintainId) {
        this.maintainId = maintainId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getCheckOpinion() {
        return checkOpinion;
    }

    public void setCheckOpinion(String checkOpinion) {
        this.checkOpinion = checkOpinion;
    }
}
