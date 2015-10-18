package com.zzxhdzj.app.channels;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzxhdzj.app.channels.views.CircleImageView;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.modules.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/19/14
 * To change this template use File | Settings | File Templates.
 */
public class ChannelItemView extends LinearLayout {

    private DisplayImageOptions options;    
    CircleImageView mChannelImage;
    TextView mChannelItemName;    
    TextView mChannelItemIntro;

    public ChannelItemView(Context context) {
        super(context);
    }

    public ChannelItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        init();
    }

    private void init() {
        final View view = View.inflate(getContext(), R.layout.channel_list_item_view, this);        
        mChannelImage = (CircleImageView) view.findViewById(R.id.channel_image);
        mChannelItemName = (TextView) view.findViewById(R.id.channel_item_name);        
        mChannelItemIntro = (TextView) view.findViewById(R.id.channel_item_intro);
    }

    public void bindView(Channel channel) {
        mChannelItemName.setText(channel.name);
        mChannelItemIntro.setText(channel.intro);
        ImageLoader.getInstance().displayImage(channel.cover, mChannelImage, options);
    }
}
