package com.zzxhdzj.app.home;

import java.util.ArrayList;

import org.joda.time.DateTime;

import android.database.Cursor;

import com.zzxhdzj.app.home.activity.DoubanFm;
import com.zzxhdzj.app.login.fragment.LoginFragment;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.channels.local.ChannelHelper;
import com.zzxhdzj.douban.modules.channel.Channel;
import com.zzxhdzj.http.Callback;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/5/14
 * To change this template use File | Settings | File Templates.
 */
public class DoubanFmDelegate implements LoginFragment.LoginListener {
    private DoubanFm doubanFm;
    private Douban douban;
    private static final String KEY_LAST_CHLS_QUERY_TIME = "KEY_LAST_CHLS_QUERY_TIME";
    private ChannelHelper channelHelper;

    public DoubanFmDelegate(DoubanFm doubanFm) {
        this.doubanFm = doubanFm;
        this.douban = new Douban();
        channelHelper = new ChannelHelper(this.doubanFm);
    }

    public void prepare() {
        if (Douban.isLogged()) {
            doubanFm.showPlayFragment();
            doubanFm.showLoggedItems(Douban.getUserInfo());
            long lastChlsUpdateTime = Douban.getSharedPreferences().getLong(KEY_LAST_CHLS_QUERY_TIME, 0);
            if (isRefreshChannelsOverdue(new DateTime().withMillis(lastChlsUpdateTime))) {
                        updateStaticChannelInfo();
                        updateDynamicChannels();
            }
        } else {
            doubanFm.showLoginFragment();
        }
    }

    public Douban getDouban() {
        return douban;
    }

    public void updateStaticChannelInfo() {
        channelHelper.queryStaticChannels(new ChannelHelper.ChannelHelperListener() {
            @Override
            public void onResult(Cursor cursor) {
                Douban.getSharedPreferences().edit().putLong(KEY_LAST_CHLS_QUERY_TIME, new DateTime().getMillis()).commit();
                //���¹̶�Ƶ��id
                fetchChannelsInfo(cursor);
            }
        });
    }

    public void updateDynamicChannels() {
        //�������ıȽ϶��Ƶ���Ƽ�Ƶ��:mock
        final ArrayList<Integer> channelIds = new ArrayList<Integer>();
        //FIXME:����ݿ���������������
        channelIds.add(1);
        channelIds.add(3);
        channelIds.add(5);

        final ArrayList<Channel> tempChannels = new ArrayList<Channel>();

        //step3
        final Callback recommendCallback = new Callback() {
            @Override
            public void onSuccess() {
                synchronized (tempChannels) {
                    tempChannels.add(douban.recommendChannel);
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                persistDynamicChannels(tempChannels);
            }
        };

        //step2
        //ǰ7��
        final Callback trendingCallback = new Callback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                synchronized (tempChannels) {
                    tempChannels.addAll(douban.channels);
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                douban.recommendChannnels(channelIds, recommendCallback);
            }
        };

        //step1
        Callback hotCallback = new Callback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                synchronized (tempChannels) {
                    tempChannels.addAll(douban.channels);
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                douban.queryTrendingChannles(1, 9, trendingCallback);
            }
        };

        douban.queryHotChannels(1, 9, hotCallback);
    }

    void persistDynamicChannels(final ArrayList<Channel> tempChannels) {
        channelHelper.queryDynamicChannels(new ChannelHelper.ChannelHelperListener() {
            @Override
            public void onResult(final Cursor cursor) {
                channelHelper.createOrUpdateDynamicChannels(cursor, tempChannels);
            }
        });
    }

    private void fetchChannelsInfo(final Cursor cursor) {
        //������ݿ��еĹ̶�Ƶ����Ϣ
        try {
//            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String channelId = cursor.getString(Channel.CHANNEL_ID_INDEX);
                final int _id = cursor.getInt(Channel.ID_INDEX);
                //ȥ���ϲ�ѯȻ����µ���ݿ�
                douban.queryChannelInfo(channelId, new Callback() {
                    @Override
                    public void onSuccess() {
                        super.onSuccess();
                        Channel singleObject = (Channel) douban.singleObject;
                        singleObject._id = _id;
                        channelHelper.update(singleObject);
                    }
                });
            }
        } catch (Exception e) {
        } finally {
            cursor.close();
        }

    }

    public boolean isRefreshChannelsOverdue(DateTime lastRequestTime) {
        return lastRequestTime.plusDays(1).isBeforeNow();
    }

    @Override
    public void onLogin() {
        doubanFm.showPlayFragment();
        doubanFm.showLoggedItems(Douban.getUserInfo());
                updateStaticChannelInfo();
                updateDynamicChannels();
    }
}
