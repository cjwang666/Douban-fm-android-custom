package com.zzxhdzj.app.channels;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzxhdzj.app.channels.ChannelListFragment.ChannelListFragmentListener;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.db.tables.ChannelTypes;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/12/14
 * To change this template use File | Settings | File Templates.
 */
public class ChannelCategoryFragment extends Fragment implements ChannelListFragmentListener {
    TextView mBtnChannelItemRegion;
    TextView mBtnChannelItemAges;        
    TextView mBtnChannelItemGenre;    
    TextView mBtnChannelItemSpecial;
    TextView mBtnQuickGenre;
    TextView mBtnChannelItemArtist;    
    TextView mBtnChannelItemTrending;    
    TextView mBtnChannelItemHits;    
    TextView mBtnChannelItemTry;    
    RelativeLayout mChannelsGrid;
//    @InjectView(R.id.channels_grid_close_btn)
//    View mChannelsGridCloseBtn;    
    TextView mBtnChannelItemBrand;
    private ChannelFragmentListener listener;
    private View.OnClickListener view_listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channels_category, container, false);
        mBtnChannelItemRegion = (android.widget.TextView) view.findViewById(R.id.btn_channel_item_region);        
        mBtnChannelItemAges = (android.widget.TextView) view.findViewById(R.id.btn_channel_item_ages);
        mBtnChannelItemGenre = (android.widget.TextView) view.findViewById(R.id.btn_channel_item_genre);
        mBtnChannelItemSpecial = (android.widget.TextView) view.findViewById(R.id.btn_channel_item_special);        
        mBtnQuickGenre = (android.widget.TextView) view.findViewById(R.id.btn_channel_item_genre);        
        mBtnChannelItemArtist = (android.widget.TextView) view.findViewById(R.id.btn_channel_item_artist);        
        mBtnChannelItemTrending = (android.widget.TextView) view.findViewById(R.id.btn_channel_item_trending);        
        mBtnChannelItemHits = (android.widget.TextView) view.findViewById(R.id.btn_channel_item_hits);        
        mBtnChannelItemTry = (android.widget.TextView) view.findViewById(R.id.btn_channel_item_try);        
        mChannelsGrid = (android.widget.RelativeLayout) view.findViewById(R.id.channels_grid);        
        mBtnChannelItemBrand = (android.widget.TextView) view.findViewById(R.id.btn_channel_item_brand);
        view_listener = new Button.OnClickListener(){
    		public void onClick(View v) {    
    			switch (v.getId()) {
                case R.id.btn_channel_item_region:
                    mChannelsGrid.setVisibility(View.INVISIBLE);
                    showChannelsInThisCategory(ChannelTypes.Region);
                    break;
                case R.id.btn_channel_item_ages:
                    mChannelsGrid.setVisibility(View.INVISIBLE);
                    showChannelsInThisCategory(ChannelTypes.Ages);
                    break;
                case R.id.btn_channel_item_genre:
                    mChannelsGrid.setVisibility(View.INVISIBLE);
                    showChannelsInThisCategory(ChannelTypes.Genre);
                    break;
                case R.id.btn_channel_item_special:
                    mChannelsGrid.setVisibility(View.INVISIBLE);
                    showChannelsInThisCategory(ChannelTypes.Special);
                    break;
                case R.id.btn_channel_item_brand:
                    mChannelsGrid.setVisibility(View.INVISIBLE);
                    showChannelsInThisCategory(ChannelTypes.Brand);
                    break;
                case R.id.btn_channel_item_artist:
                    mChannelsGrid.setVisibility(View.INVISIBLE);
                    showChannelsInThisCategory(ChannelTypes.Artist);
                    break;
                case R.id.btn_channel_item_trending:
                    mChannelsGrid.setVisibility(View.INVISIBLE);
                    showChannelsInThisCategory(ChannelTypes.Trending);
                    break;
                case R.id.btn_channel_item_hits:
                    mChannelsGrid.setVisibility(View.INVISIBLE);
                    showChannelsInThisCategory(ChannelTypes.Hits);
                    break;
                case R.id.btn_channel_item_try:
                    mChannelsGrid.setVisibility(View.INVISIBLE);
                    showChannelsInThisCategory(ChannelTypes.Try);
                    break;
//                case R.id.channels_grid_close_btn:
//                    getActivity().getSupportFragmentManager().popBackStack();
//                    break;
                default:
                    break;
            }  
    		}
        };
    
        mBtnChannelItemRegion.setOnClickListener(view_listener);
        mBtnChannelItemAges.setOnClickListener(view_listener);
        mBtnChannelItemGenre.setOnClickListener(view_listener);
        mBtnChannelItemSpecial.setOnClickListener(view_listener);
        mBtnQuickGenre.setOnClickListener(view_listener);
        mBtnChannelItemArtist.setOnClickListener(view_listener);
        mBtnChannelItemTrending.setOnClickListener(view_listener);
        mBtnChannelItemHits.setOnClickListener(view_listener);
        mBtnChannelItemTry.setOnClickListener(view_listener);
        mChannelsGrid.setOnClickListener(view_listener);
        mBtnChannelItemBrand.setOnClickListener(view_listener);
        return view;
    }

    
    	
    	
        
    


    private void showChannelsInThisCategory(ChannelTypes channelType) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ChannelListFragment fragment = new ChannelListFragment();
        fragment.setChannelListFragmentListener(this);
        Bundle bundle = new Bundle();
        bundle.putString(ChannelListFragment.KEY_CHANNEL_CATEGORY_ID, channelType.getIndex() + "");
        bundle.putString(ChannelListFragment.KEY_CHANNEL_CATEGORY_NAME, channelType.getZhName() + "");
        fragment.setArguments(bundle);
        ft.replace(R.id.channels_grid_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listener!=null)listener.onChannelFragmentDestroy();
    }

    public void setListener(ChannelFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onChannelListFragmentDestroy() {
        mChannelsGrid.setVisibility(View.VISIBLE);
    }

    public interface ChannelFragmentListener {
        void onChannelFragmentDestroy();
    }
}
