package com.huyingbao.dm.ui.inspection.adapter;

import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.inspection.model.Inspection;
import com.huyingbao.dm.ui.inspection.model.InspectionTitle;
import com.huyingbao.rxflux2.action.ActionCreator;
import com.huyingbao.rxflux2.constant.Actions;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.rxflux2.util.AppUtils;
import com.huyingbao.rxflux2.util.TimeUtils;
import com.huyingbao.rxflux2.util.imageloader.ImageLoaderUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * 巡检记录
 * Created by liujunfeng on 2017/5/31.
 */
public class InspectionUserAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    @Inject
    ActionCreator mActionCreator;

    public InspectionUserAdapter(List<MultiItemEntity> dataList) {
        super(dataList);
        AppUtils.getApplicationComponent().inject(this);
        addItemType(Constants.BASE_DEVICE_TYPE_INSPECTION_TITLE, R.layout.item_inspection_title);
        addItemType(Constants.BASE_DEVICE_TYPE_INSPECTION, R.layout.item_device);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (item.getItemType()) {
            case Constants.BASE_DEVICE_TYPE_INSPECTION_TITLE:
                final InspectionTitle inspectionTitle = (InspectionTitle) item;
                helper.setText(R.id.tv_maintain_item_title, inspectionTitle.getPlanDate())
                        .setImageResource(R.id.iv_maintain_item_title_drop, inspectionTitle.isExpanded() ? R.drawable.ic_v_to_down : R.drawable.ic_v_to_right);
                helper.itemView.setSelected(inspectionTitle.isExpanded());
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();
                    if (inspectionTitle.isExpanded()) collapse(pos);
                    else expand(pos);
                });
                break;
            case Constants.BASE_DEVICE_TYPE_INSPECTION:
                Inspection inspection = (Inspection) item;
                ((RatingBar) helper.getView(R.id.rb_device_degree)).setRating(inspection.getDegree());
                ImageLoaderUtils.loadImage(mContext, inspection.getMachineHeadImg(), R.mipmap.ic_logo, helper.getView(R.id.iv_device_img));
                helper.setText(R.id.tv_device_code, inspection.getMachineCode())
                        .setText(R.id.tv_device_name, inspection.getMachineName())
                        .setGone(R.id.tv_device_status, false);
                String time = inspection.getPlanDate();
                helper.setText(R.id.tv_device_type, TimeUtils.formatTime(time));
                helper.itemView.setOnClickListener(v -> {
                    mActionCreator.postLocalAction(Actions.TO_INSPECTION_ITEM, ActionsKeys.INSPECTION, inspection);
                });
                break;
        }
    }
}
