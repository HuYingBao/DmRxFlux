package com.huyingbao.dm.ui.inspection.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.rxflux2.constant.Constants;

/**
 * 保养项
 * Created by liujunfeng on 2017/5/26.
 */
public class InspectionTitle extends AbstractExpandableItem<Inspection> implements MultiItemEntity {
    private String planDate;

    public InspectionTitle(String planDate) {
        this.planDate = planDate;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return Constants.BASE_DEVICE_TYPE_INSPECTION_TITLE;
    }
}
