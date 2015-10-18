package com.zzxhdzj.douban.modules.song;

import java.util.LinkedList;

import com.google.gson.annotations.SerializedName;
import com.zzxhdzj.douban.modules.Resp;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 1:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class SongResp extends Resp{
    @SerializedName("song")
    public LinkedList<Song> songs;
}
