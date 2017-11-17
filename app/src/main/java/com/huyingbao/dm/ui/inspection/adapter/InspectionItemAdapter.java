package com.huyingbao.dm.ui.inspection.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.dm.R;
import com.huyingbao.dm.ui.inspection.model.InspectItem;
import com.huyingbao.rxflux2.constant.Constants;
import com.huyingbao.rxflux2.util.CommonUtils;
import com.huyingbao.rxflux2.widget.switchview.SwitchView;

import java.util.List;

/**
 * 巡检项
 * Created by liujunfeng on 2017/6/2.
 */
public class InspectionItemAdapter extends BaseQuickAdapter<InspectItem, BaseViewHolder> {
    private boolean mChangeAble = false;//false:不可以更改,不显示switch
    private boolean mIsStart = true;//true：已经巡检过，false：未巡检

    public InspectionItemAdapter(List<InspectItem> dataList) {
        super(R.layout.item_inspection_item, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectItem item) {
        helper.setImageResource(R.id.iv_inspection_item_result, CommonUtils.getInspectionResultImg(item.getResult()))
                .setGone(R.id.sw_inspection_item_result, mChangeAble)
                .setGone(R.id.tv_inspection_item_result, mChangeAble)
                .setText(R.id.tv_inspection_item_content, item.getContent());
        if (mChangeAble) {
            helper.setGone(R.id.iv_inspection_item_result, false);
            SwitchView swResult = helper.getView(R.id.sw_inspection_item_result);
            TextView tvResult = helper.getView(R.id.tv_inspection_item_result);
            switch (item.getResult()) {
                default:
                case Constants.TYPE_RESULT_INSPECTION_NORMAL:
                    swResult.displayOn();
                    tvResult.setText("通过");
                    tvResult.setTextColor(mContext.getResources().getColor(R.color.result_normal));
                    break;
                case Constants.TYPE_RESULT_INSPECTION_NO_PASS:
                    swResult.displayMid();
                    tvResult.setText("不通过");
                    tvResult.setTextColor(mContext.getResources().getColor(R.color.result_no_pass));
                    break;
                case Constants.TYPE_RESULT_INSPECTION_ERROR:
                    swResult.displayOff();
                    tvResult.setText("故障");
                    tvResult.setTextColor(mContext.getResources().getColor(R.color.result_error));
                    break;
            }
            swResult.setOnClickListener(view -> {
                switch (item.getResult()) {
                    default:
                    case Constants.TYPE_RESULT_INSPECTION_NORMAL:
                        item.setResult(Constants.TYPE_RESULT_INSPECTION_NO_PASS);
                        swResult.displayMid();
                        tvResult.setText("不通过");
                        tvResult.setTextColor(mContext.getResources().getColor(R.color.result_no_pass));
                        break;
                    case Constants.TYPE_RESULT_INSPECTION_NO_PASS:
                        item.setResult(Constants.TYPE_RESULT_INSPECTION_ERROR);
                        swResult.displayOff();
                        tvResult.setText("故障");
                        tvResult.setTextColor(mContext.getResources().getColor(R.color.result_error));
                        break;
                    case Constants.TYPE_RESULT_INSPECTION_ERROR:
                        item.setResult(Constants.TYPE_RESULT_INSPECTION_NORMAL);
                        swResult.displayOn();
                        tvResult.setText("通过");
                        tvResult.setTextColor(mContext.getResources().getColor(R.color.result_normal));
                        break;
                }
            });
        } else {
            helper.setGone(R.id.iv_inspection_item_result, mIsStart);
        }
    }

    public void setChangeAble(boolean changeAble) {
        mChangeAble = changeAble;
    }

    public void setStart(boolean start) {
        mIsStart = start;
    }
}
