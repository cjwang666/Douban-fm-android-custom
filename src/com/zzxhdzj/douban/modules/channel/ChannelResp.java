package com.zzxhdzj.douban.modules.channel;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.zzxhdzj.douban.modules.Resp;
import com.zzxhdzj.douban.modules.song.Song;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/28/13
 * Time: 12:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelResp extends Resp{
    @SerializedName("data")
    public ChannelData channleData;
    @SerializedName("song")
    public ArrayList<Song> songs;
}
