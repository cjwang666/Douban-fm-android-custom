package com.zzxhdzj.douban.db.tables;


/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/5/14
 * To change this template use File | Settings | File Templates.
 */
public class ChannelCategeryTable extends DbTable {
        public static final String TABLE_NAME = "fm_channel_types";
        //public static final Uri CONTENT_URI = Uri
        //        .parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static String getInitializeDataSQL() {
            String initializeData = "insert into "+ TABLE_NAME +"("+
                    Columns.CHANNEL_TYPE_ID+ ","+
                    Columns.ZH_NAME+ ","+
                    Columns.EN_NAME+ ","+
                    Columns.IS_PUBLIC+ ")"+
                    " values "+
                    "(1,\"区域&语言\", \"Region & Language\",1"+"),"+
                    "(2,\"年代\", \"Ages\",1"+"),"+
                    "(3,\"流派\", \"Genre\",1"+"),"+
                    "(4,\"特别推荐\", \"Special\",1"+"),"+
                    "(5,\"品牌\", \"Brand\",1"+"),"+
                    "(6,\"艺术家\", \"Artist\",1"+"),"+
                    "(7,\"上升最快\", \"Trending\",1"+"),"+
                    "(8,\"热门兆赫\", \"Hits\",0"+"),"+
                    "(9,\"试试这些\", \"Try\",0"+");";                    
            return initializeData;
        }


        public static class Columns {            
        	public static final String CHANNEL_TYPE_ID = "_id";
            public static final String ZH_NAME = "zhName";
            public static final String EN_NAME = "enName";
            public static final String IS_PUBLIC = "isPublic";
        }
        public static String getCreateSQL() {
            String createString = TABLE_NAME +
                    "("+Columns.CHANNEL_TYPE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Columns.ZH_NAME	+" TEXT," +
                    Columns.EN_NAME	+" TEXT," +
                    Columns.IS_PUBLIC+" INT)";
            return "CREATE TABLE " + createString;
        }

        public static String getDropSQL() {
            return "DROP TABLE " + TABLE_NAME;
        }

        public static String getCreateIndexSQL() {
            String createIndexSQL = "CREATE INDEX " + TABLE_NAME + "_idx ON "
                    + TABLE_NAME + " ( " + Columns.CHANNEL_TYPE_ID + " );";
            return createIndexSQL;
        }
}
