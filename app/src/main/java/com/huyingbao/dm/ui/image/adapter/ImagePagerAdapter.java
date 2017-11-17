package com.huyingbao.dm.ui.image.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.util.imageloader.ImageLoader;
import com.huyingbao.rxflux2.util.imageloader.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager 适配器
 * Created by liujunfeng on 2017/5/9.
 */
public class ImagePagerAdapter extends PagerAdapter {
    private List<String> mImageList = new ArrayList<>();

    private LayoutInflater mLayoutInflater;

    private Context mContext;

    private ImageView smallImageView = null;

    public ImagePagerAdapter(Context context, List<String> imgList) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mImageList = imgList;
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.item_pager_image, container, false);
        final ImageView imageView = view.findViewById(R.id.pv_image);

        // 预览imageView
        smallImageView = new ImageView(mContext);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        smallImageView.setLayoutParams(layoutParams);
        smallImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ((FrameLayout) view).addView(smallImageView);

        // loading
        final ProgressBar loading = new ProgressBar(mContext);
        FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingLayoutParams.gravity = Gravity.CENTER;
        loading.setLayoutParams(loadingLayoutParams);
        ((FrameLayout) view).addView(loading);

        // 图片根地址
        final String imgPath = mImageList.get(position);

        RequestListener requestListener = new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                loading.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                loading.setVisibility(View.GONE);
                return false;
            }
        };
        ImageLoader.Builder builder = new ImageLoader.Builder();
        builder.requestListener = requestListener;
        builder.resource = imgPath;
        builder.netImage = true;
        builder.placeHolder = R.color.divider;
        builder.errorHolder = R.mipmap.ic_logo;
        builder.imgView = imageView;

        loading.setVisibility(View.VISIBLE);
        ImageLoaderUtils.loadImage(mContext, builder.build());
        container.addView(view, 0);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
