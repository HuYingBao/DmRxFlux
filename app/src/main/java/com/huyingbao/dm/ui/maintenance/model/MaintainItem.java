package com.huyingbao.dm.ui.maintenance.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.rxflux2.constant.Constants;

/**
 * 保养项
 * Created by liujunfeng on 2017/5/26.
 */
public class MaintainItem implements MultiItemEntity {
    /**
     * content : 检查油泵是否发热或漏油
     * id : 2
     * maintainId : 10000
     * planId : 1
     * result : 0
     * resultCause : 油泵漏油
     */
    private String position;
    private String content;
    private int id;
    private int itemId;
    private int maintainId;
    private int planId;
    private boolean result;//true:完成,false:未完成
    private String resultCause;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return id;
    }

    public void setItemId(int itemId) {
        this.itemId = id;
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

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getResultCause() {
        return resultCause;
    }

    public void setResultCause(String resultCause) {
        this.resultCause = resultCause;
    }

    @Override
    public int getItemType() {
        return Constants.MAINTAIN_ITEM;
    }
}
