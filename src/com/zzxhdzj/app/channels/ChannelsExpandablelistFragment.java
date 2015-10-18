/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zzxhdzj.app.channels;

import com.zzxhdzj.douban.R;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.SimpleCursorTreeAdapter;

import com.zzxhdzj.douban.db.tables.ChannelCategeryTable;
import com.zzxhdzj.douban.db.tables.ChannelTable;
import com.zzxhdzj.douban.providers.DoubanProvider;

/**
 * Demonstrates expandable lists backed by Cursors
 */
public class ChannelsExpandablelistFragment extends Fragment {

    private static final String[] CHANNEL_TYPE_PROJECTION = new String[] {
    	ChannelCategeryTable.Columns.CHANNEL_TYPE_ID,
        ChannelCategeryTable.Columns.ZH_NAME
    };
    private static final int CHANNEL_TYPE_ID_COLUMN_INDEX = 0;

    private static final String[] CHANNEL_PROJECTION = new String[] {
    	ChannelTable.Columns._ID,
        ChannelTable.Columns.NAME
    };

    private static final int TOKEN_GROUP = 0;
    private static final int TOKEN_CHILD = 1;
    View v;
    private static final class QueryHandler extends AsyncQueryHandler {
        private CursorTreeAdapter mAdapter;
        private ExpandableListView expandableListView;

        public QueryHandler(Context context, CursorTreeAdapter adapter, ExpandableListView expandableListView) {
            super(context.getContentResolver());
            this.mAdapter = adapter;
            this.expandableListView = expandableListView;
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            switch (token) {
            case TOKEN_GROUP:
                mAdapter.setGroupCursor(cursor);
                for (int i = 0; i < mAdapter.getGroupCount(); i++) {
                	expandableListView.expandGroup(i);
        		}
                break;

            case TOKEN_CHILD:
                int groupPosition = (Integer) cookie;
                mAdapter.setChildrenCursor(groupPosition, cursor);
                break;
            }
        }
    }

    public class MyExpandableListAdapter extends SimpleCursorTreeAdapter {

        // Note that the constructor does not take a Cursor. This is done to avoid querying the 
        // database on the main thread.
        public MyExpandableListAdapter(Context context, int groupLayout,
                String[] groupFrom, int[] groupTo, int childLayout, String[] childrenFrom,
                int[] childrenTo) {

            super(context, null, groupLayout, groupFrom, groupTo, childLayout, childrenFrom,
                    childrenTo);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // Given the group, we return a cursor for all the children within that group 

            // Return a cursor that points to this contact's phone numbers
            //Uri.Builder builder = DoubanProvider.CONTENT_URI_CHANNEL_TYPE.buildUpon();
            //ContentUris.appendId(builder, groupCursor.getLong(GROUP_ID_COLUMN_INDEX));
            //builder.appendEncodedPath(Contacts.Data.CONTENT_DIRECTORY);
            //Uri phoneNumbersUri = builder.build();
        	
            mQueryHandler.startQuery(TOKEN_CHILD, groupCursor.getPosition(), DoubanProvider.CONTENT_URI_CHANNEL, 
                    CHANNEL_PROJECTION, ChannelTable.Columns.CATEGORY_ID + "=?", 
                    new String[] { Integer.toString(groupCursor.getInt(CHANNEL_TYPE_ID_COLUMN_INDEX)) }, null);

            return null;
        }
    }

    private QueryHandler mQueryHandler;
    private CursorTreeAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onActivityCreated(savedInstanceState);
        // Set up our adapter
        mAdapter = new MyExpandableListAdapter(
        		getActivity(),
                R.drawable.expandablelistview_groups,                
                new String[] { ChannelCategeryTable.Columns.ZH_NAME }, // Name for group layouts
                new int[] { R.id.textGroup },
                R.drawable.expandablelistview_child,
                new String[] { ChannelTable.Columns.NAME }, // Number for child layouts
                new int[] { R.id.textChild });

        //setListAdapter(mAdapter);       
        // Inflate the layout for this fragment     
        ExpandableListView expandableListView = (ExpandableListView)v.findViewById(R.id.channellist);
        expandableListView.setAdapter(mAdapter);
        /*
        expandableListView.setOnGroupClickListener(new OnGroupClickListener() {			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				return true;
			}
		});*/
        mQueryHandler = new QueryHandler(getActivity(), mAdapter,expandableListView);
        // Query for people
        mQueryHandler.startQuery(TOKEN_GROUP, null, DoubanProvider.CONTENT_URI_CHANNEL_TYPE, CHANNEL_TYPE_PROJECTION, 
                null, null, null);         
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);   
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.channels_expandable_list_view, container, false);
        return v;
    }

    @Override
    public void onDestroyView() {
        // Null out the group cursor. This will cause the group cursor and all of the child cursors
        // to be closed.
        mAdapter.changeCursor(null);
        mAdapter = null;
        super.onDestroyView(); 
        Log.d("Fragment 1", "onDestroyView"); 
    }
}
