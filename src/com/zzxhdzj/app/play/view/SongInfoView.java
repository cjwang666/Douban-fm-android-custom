package com.zzxhdzj.app.play.view;

import com.zzxhdzj.douban.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zzxhdzj.douban.modules.song.Song;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/1/14
 * To change this template use File | Settings | File Templates.
 */
public class SongInfoView extends TableLayout {
    
    TextView mSongNameTv;    
    TextView mSongAlbumTv;    
    TextView mSongArtistTv;    
    TextView mSongYearTv;    
    TextView mSongCompanyTv;    
    ImageView mCdCover;    
    public TextView mSongDurationTv;
    private DisplayImageOptions options;

    public SongInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(getContext(), R.layout.song_info, this);
        mSongNameTv = (android.widget.TextView) view.findViewById(R.id.song_name_tv);
        mSongAlbumTv = (android.widget.TextView) view.findViewById(R.id.song_album_tv);        
        mSongArtistTv = (android.widget.TextView) view.findViewById(R.id.song_artist_tv);        
        mSongYearTv = (android.widget.TextView) view.findViewById(R.id.song_year_tv);        
        mSongCompanyTv = (android.widget.TextView) view.findViewById(R.id.song_company_tv);        
        mCdCover = (android.widget.ImageView) view.findViewById(R.id.cd_cover);        
        mSongDurationTv = (android.widget.TextView) view.findViewById(R.id.song_duration_tv);
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();

    }

    public void bindView(Song song){
        mSongNameTv.setText(song.title);
        mSongNameTv.setSelected(true);
        mSongAlbumTv.setText(song.albumTitle);
        mSongCompanyTv.setSelected(true);
        mSongArtistTv.setText(song.artist);
        mSongArtistTv.setSelected(true);
        mSongYearTv.setText(song.publicTime);
        mSongYearTv.setSelected(true);
        mSongCompanyTv.setText(song.company);
        mSongCompanyTv.setSelected(true);
        ImageLoader.getInstance().displayImage(song.picture, mCdCover, options);
    }


}
