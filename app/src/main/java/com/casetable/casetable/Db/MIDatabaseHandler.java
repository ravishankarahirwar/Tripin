package com.casetable.casetable.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.casetable.casetable.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

public class MIDatabaseHandler extends SQLiteOpenHelper {

    static MIDatabaseHandler db = null;

    public MIDatabaseHandler(Context context) {
        super(context, context.getString(R.string.app_name), null, 1);
        db = this;
    }

    public static MIDatabaseHandler getDB(Context context) {
        if (db == null) {
            db = new MIDatabaseHandler(context);
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createTableCase(db);

    }

    /**
     * create table ADS_INFO
     *
     * @param db SQLiteDatabase
     */
    private void createTableCase(SQLiteDatabase db) {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put(DbParam.ID, "INTEGER PRIMARY KEY AUTOINCREMENT");
        hashtable.put(DbParam.NAME, "VARCHAR");
        hashtable.put(DbParam.CASENO, "VARCHAR");
        hashtable.put(DbParam.CAMOUNT, "VARCHAR");
        hashtable.put(DbParam.CSTAGE, "VARCHAR DEFAULT 0" );
        hashtable.put(DbParam.CNEXPACTED, "VARCHAR DEFAULT 0");
        createTable(db, DbParam.TABLE_CASE, hashtable);

    }


    public static void deleteData(String TableName, ArrayList<String> arrData, ArrayList<String> arrVals) {

        String arrDel = "";

        for (int i = 0; i < arrData.size(); i++) {

            if (i == 0) {
                arrDel = arrData.get(i) + " = ? ";
            } else {
                arrDel = arrDel + " AND " + arrData.get(i) + " = ? ";
            }

        }

        db.getWritableDatabase().delete(TableName, arrDel,
                arrVals.toArray(new String[arrVals.size()]));

    }

    public void editMultipleData(Context context, String tableName,
                                 ArrayList<String> data, ArrayList<String> whereData) {
        try {

            if (data != null && whereData != null) {
                String query = "UPDATE " + tableName + " SET ";

                for (int i = 0; i < data.size(); i++) {
                    if (i == 0) {
                        query = query + " " + data.get(i);
                    } else {
                        query = query + " , " + data.get(i);
                    }
                }

                for (int i = 0; i < whereData.size(); i++) {
                    if (i == 0) {
                        query = query + " WHERE " + whereData.get(i);
                    } else {
                        query = query + whereData.get(i);
                    }
                }

                getDB(context).getWritableDatabase().execSQL(query);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteMultipleData(String TableName,
                                          ArrayList<String> arrWhere) {

        String query = "DELETE FROM " + TableName, whereCons = "";

        if (arrWhere != null) {

            if (arrWhere.size() > 0) {
                for (int i = 0; i < arrWhere.size(); i++) {
                    whereCons = whereCons + " " + arrWhere.get(i);
                }
            }
        }

        if (!whereCons.equalsIgnoreCase("")) {

            query = query + " where " + whereCons;
            db.getWritableDatabase().execSQL(query);

        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //we add two more field in ads table so drop and create again
        // old version =1 and new versoin ==2
       /* if (oldVersion == 1) {
            db.execSQL("DROP TABLE IF EXISTS '" + DbParam.TABLE_ADS_INFO + "'");
            createTableSubInfo(db);
        }
        if (oldVersion == 2) {
            db.execSQL("ALTER TABLE " + DbParam.TABLE_CATEGORY_INFO + " ADD COLUMN " + DbParam.TEMP + " INTEGER DEFAULT 0 ");
            //com.collection.owner.colllectionapp.db.execSQL("ALTER TABLE foo ADD COLUMN new_column INTEGER DEFAULT 0");
        }*/

        //createTableSubInfo(db);
    }


    public void createTable(SQLiteDatabase database, String tableName,
                            Hashtable<String, String> tmp1) {
        String CREATE_TABLE = "create table if not exists " + tableName + "(";
        for (String key : tmp1.keySet()) {
            CREATE_TABLE = CREATE_TABLE + key + " " + tmp1.get(key) + ",";
        }

        int len = CREATE_TABLE.length();
        CREATE_TABLE = CREATE_TABLE.substring(0, len - 1) + ")";

        database.execSQL(CREATE_TABLE);
    }

    public void insertData(Hashtable<String, String> queryValues,
                           String TableName) {
        try {

            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            for (String key : queryValues.keySet()) {
                values.put(key, queryValues.get(key));
            }
            database.insert(TableName, null, values);
            // System.out.println(TableName + " INSERTED = "
            // + database.insert(TableName, null, values));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertMultipleData(Context context,
                                   ArrayList<Hashtable<String, String>> queryValues, String TableName) {

        try {

            if (queryValues != null && TableName != null) {

                if (queryValues.get(0) != null) {

                    String columns = "";

                    Set<String> keys = queryValues.get(0).keySet();
                    if (keys.size() > 0) {
                        for (String key : keys) {
                            if (columns.length() == 0) {
                                columns = "'" + key + "'";
                            } else {
                                columns = columns + " , " + "'" + key + "'";
                            }

                        }
                    }

                    if (columns.trim().length() > 0) {
                        columns = " ( " + columns + " ) values ";

                        String query = "INSERT INTO " + TableName + columns;
                        String allRowsData = "";

                        for (int i = 0; i < queryValues.size(); i++) {
                            Set<String> key1 = queryValues.get(i).keySet();
                            if (key1.size() > 0) {
                                String rowData = "";
                                for (String key : key1) {
                                    if (rowData.length() == 0) {

                                        rowData = "'"
                                                + queryValues.get(i).get(key)
                                                .replaceAll("'", "''")
                                                + "'";

                                    } else {

                                        rowData = rowData
                                                + " , "
                                                + "'"
                                                + queryValues.get(i).get(key)
                                                .replaceAll("'", "''")
                                                + "'";
                                    }
                                }

                                if (rowData.trim().length() > 0) {

                                    rowData = " ( " + rowData + " )";

                                    if (allRowsData.length() == 0) {
                                        allRowsData = rowData;
                                    } else {
                                        allRowsData = allRowsData + " , "
                                                + rowData;
                                    }

                                }

                            }
                        }

                        query = query + " " + allRowsData;


                        SQLiteDatabase database = getDB(context)
                                .getWritableDatabase();
                        database.execSQL(query);

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteAll(String TableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableName, null, null);

    }

    public void deleteAll(ArrayList<String> TableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < TableName.size(); i++) {
            db.delete(TableName.get(i), null, null);
        }

    }


    public void delete_condiction_wise1(String TABLE_NAME,
                                        Hashtable<String, String> queryValues) {
        int i = 0;

        SQLiteDatabase database = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE_NAME + " WHERE ";

        for (String key : queryValues.keySet()) {

            if (i == queryValues.size() - 1) {
                query = query + " " + key + "=" + queryValues.get(key);
            } else {
                query = query + " " + key + "=" + queryValues.get(key) + " "
                        + " AND ";
            }

            i++;
        }


        database.rawQuery(query, null);

    }

    public void editData(Hashtable<String, String> queryValues, String Key,
                         String value, String TABLE_NAME) {
        try {

            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            for (String key : queryValues.keySet()) {
                values.put(key, queryValues.get(key));
            }

            int ab = database.update(TABLE_NAME, values, Key + "=" + value,
                    null);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Cursor getSearchData(Context context, String TABLE_NAME,
                                String ColumnName, String keyword) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ColumnName
                + " LIKE " + "'%" + keyword + "%'" + ";";

        //System.out.println("query in getsearch" + query);

        SQLiteDatabase database = getDB(context).getWritableDatabase();

        Cursor cursor = database.rawQuery(query, null);

        return cursor;
    }

    public static Cursor getTableDataAsCursor(Context context,
                                              String tableName, HashMap<String, String> getColumns,
                                              ArrayList<String> whereData, String orderByColumn,
                                              String orderByType) {

        String query = "SELECT DISTINCT ", params = " * ", whereCondition = "";
        int i = 0;
        if (getColumns != null) {
            if (getColumns.size() > 0) {
                Set<String> keys = getColumns.keySet();
                if (keys.size() > 0) {
                    for (String key : keys) {
                        if (params.length() == 0) {
                            params = key;
                        } else {
                            params = params + "," + key;
                        }

                    }
                } else {

                }
            }
        }

        query = query + " " + params + " FROM " + tableName;

        if (whereData != null) {
            if (whereData.size() > 0) {
                for (int j = 0; j < whereData.size(); j++) {
                    whereCondition = whereCondition + " " + whereData.get(j);
                }
            }
            if (whereData != null) {
                whereCondition = "  where " + whereCondition;
            }
        }

        query = query + whereCondition;

        if (orderByColumn != null && orderByType != null) {
            query = query + " ORDER BY " + orderByColumn + " " + orderByType;
        }

        query = query.trim();

        //System.out.println("--query" + query);

        SQLiteDatabase database = getDB(context).getWritableDatabase();

        Cursor cursor = database.rawQuery(query, null);

        return cursor;

    }

    public static ArrayList<HashMap<String, String>> getTableDataContactList(
            Context context, String tableName,
            HashMap<String, String> getColumns, ArrayList<String> whereData,
            String orderByColumn, String Column_block, String Column_hide,
            String orderByType) {

        ArrayList<HashMap<String, String>> arrResult = new ArrayList<HashMap<String, String>>();

        String query = "SELECT DISTINCT ", params = " * ", whereCondition = "";
        int i = 0;
        if (getColumns != null) {
            if (getColumns.size() > 0) {
                Set<String> keys = getColumns.keySet();
                if (keys.size() > 0) {
                    for (String key : keys) {
                        if (params.length() == 0) {
                            params = key;
                        } else {
                            params = params + "," + key;
                        }
                    }
                } else {

                }
            }
        }

        query = query + " " + params + " FROM " + tableName;

        if (whereData != null) {
            if (whereData.size() > 0) {
                whereCondition = " where";
                for (int j = 0; j < whereData.size(); j++) {
                    whereCondition = whereCondition + " " + whereData.get(j);
                }
            }
        }

        // else 0 end
        query = query + whereCondition;

        if (orderByColumn != null && orderByType != null) {

            query = query + " ORDER BY CASE WHEN " + Column_block
                    + " is 'yes' then 2 else 0 end, " + "CASE WHEN "
                    + Column_hide + " is 'yes' then 1 else 0 end, "
                    + orderByColumn + " COLLATE NOCASE ASC;";

            //System.out.println("query>>>>>>" + query);
            // query = query + " ORDER BY " +
            // orderByColumn+" BETWEEN 'a' AND 'zzzzz' THEN "+
            // orderByColumn+" ELSE '~' || "+
            // orderByColumn+" END COLLATE NOCASE;";
        }

		/*
         * SELECT * FROM contacts ORDER BY CASE WHEN name COLLATE NOCASE BETWEEN
		 * 'a' AND 'zzzzz' THEN name ELSE '~' || name END COLLATE NOCASE;
		 */

        query = query.trim();

        SQLiteDatabase database = getDB(context).getWritableDatabase();

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    HashMap<String, String> map = new HashMap<String, String>();

                    if (getColumns != null) {
                        if (getColumns.size() > 0) {
                            Set<String> keys = getColumns.keySet();
                            if (keys.size() > 0) {
                                for (String key : keys) {
                                    if (cursor.getColumnIndex(key) != -1) {
                                        map.put(key, cursor.getString(cursor
                                                .getColumnIndex(key)));

                                    }
                                }
                            } else {

                            }
                        }
                    } else {
                        for (int j = 0; j < cursor.getColumnCount(); j++) {
                            map.put(cursor.getColumnName(j), cursor
                                    .getString(cursor.getColumnIndex(cursor
                                            .getColumnName(j))));
                        }
                    }

                    arrResult.add(map);

                } while (cursor.moveToNext());
            }
        }

        return arrResult;

    }

    /**
     * provide max timestamp from category table
     *
     * @return last timestamp
     */
    public static long getTimestapMax(String columnName, String tableName, String whereColumnName, String columnValue) {
        long timestamp = 0;
        String query = null;
        if (whereColumnName == null) {
            query = "select max(" + columnName + ") as added from " + tableName;
        } else {
            query = "select max(" + columnName + ") as added from " + tableName + " where " + whereColumnName + " = " + columnValue;
        }
        //System.out.println("Query==" + query);
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        //System.out.println("count==" + cursor.getCount() + "");
        if (cursor != null && cursor.getCount() != 0) {

            if (cursor.moveToFirst()) {
                //      System.out.println("...........if...........");
                if (cursor.getString(cursor.getColumnIndex("added")) != null)
                    timestamp = Long.parseLong(cursor.getString(cursor.getColumnIndex("added")));
                else
                    timestamp = 0;
            } else {
                //    System.out.println("...........else...........");
                timestamp = 0;
            }
        } else {
            timestamp = 0;
        }

        return timestamp;
    }


    /**
     * provide min timestamp from category table
     *
     * @return last timestamp
     */
    public static long getTimestapMin(String columnName, String tableName, String whereColumnName, String columnValue) {
        long timestamp = 0;
        String query = null;
        if (whereColumnName == null) {
            query = "select min(" + columnName + ") as added from " + tableName;
        } else {
            query = "select min(" + columnName + ") as added from " + tableName + " where " + whereColumnName + " = " + columnValue;
        }
        //System.out.println("Query==" + query);
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        //System.out.println("count==" + cursor.getCount() + "");
        if (cursor != null && cursor.getCount() != 0) {

            if (cursor.moveToFirst()) {
                //System.out.println("...........if...........");
                if (cursor.getString(cursor.getColumnIndex("added")) != null)
                    timestamp = Long.parseLong(cursor.getString(cursor.getColumnIndex("added")));
                else
                    timestamp = 0;
            } else {
                //System.out.println("...........else...........");
                timestamp = 0;
            }
        } else {
            timestamp = 0;
        }

        return timestamp;
    }

    //=
    public static ArrayList<HashMap<String, String>> getTableData(
            Context context, String tableName,
            HashMap<String, String> getColumns, ArrayList<String> whereData,
            String orderByColumn, String orderByType, String groupByColName) {

        ArrayList<HashMap<String, String>> arrResult = new ArrayList<HashMap<String, String>>();

        String query = "SELECT DISTINCT ", params = " * ", whereCondition = "";
        int i = 0;
        if (getColumns != null) {
            if (getColumns.size() > 0) {
                Set<String> keys = getColumns.keySet();
                if (keys.size() > 0) {
                    for (String key : keys) {
                        if (params.equalsIgnoreCase(" * ")) {
                            params = key;
                        } else {
                            params = params + "," + key;
                        }
                    }
                } else {

                }
            }
        }

        query = query + " " + params + " FROM " + tableName;

        if (whereData != null) {
            if (whereData.size() > 0) {
                whereCondition = " where";
                for (int j = 0; j < whereData.size(); j++) {
                    whereCondition = whereCondition + " " + whereData.get(j);
                }
            }
        }

        query = query + whereCondition;

        query = query.trim();

        if (groupByColName != null) {
            if (!groupByColName.trim().equalsIgnoreCase("")) {
                query = query + " GROUP BY " + groupByColName;
            }
        }

        if (orderByColumn != null && orderByType != null) {
            // query = query + " ORDER BY CASE WHEN " + orderByColumn
            // + " is 'Unknown' then 1 else 0 end, " + orderByColumn
            // + " COLLATE NOCASE ASC;";

            query = query + " ORDER BY " + orderByColumn

                    + " COLLATE NOCASE " + orderByType;

        }

        query = query + " ;";

        SQLiteDatabase database = getDB(context).getWritableDatabase();

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    HashMap<String, String> map = new HashMap<String, String>();

                    for (int j = 0; j < cursor.getColumnCount(); j++) {
                        map.put(cursor.getColumnName(j), cursor
                                .getString(cursor.getColumnIndex(cursor
                                        .getColumnName(j))));
                    }

                    arrResult.add(map);

                } while (cursor.moveToNext());
            }
        }

        return arrResult;

    }

    public static int rowCount(Context context, String TableName,
                               HashMap<String, String> map) {

        String DATABASE_COMPARE = "select count(*) from " + TableName;


        if (map != null) {

            String whrData = null;
            Set<String> keys = map.keySet();
            for (String key : keys) {
                if (whrData == null) {
                    whrData = " where " + key + "=" + map.get(key);
                } else {
                    whrData = whrData + " and " + key + "=" + map.get(key);
                }

            }

            if (whrData != null) {
                DATABASE_COMPARE = DATABASE_COMPARE + " " + whrData;
            }
        }


        int sometotal = (int) DatabaseUtils.longForQuery(getDB(context)
                .getWritableDatabase(), DATABASE_COMPARE, null);

        return sometotal;
    }

}
