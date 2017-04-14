package com.beijingtest.bjt.SimulationServer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.beijingtest.bjt.entity.Custom;
import com.beijingtest.bjt.entity.VistLog;
import com.beijingtest.bjt.entity.User;
import com.beijingtest.bjt.util.FilePathUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dev on 2017/3/30.
 */

public class SQLiteUtils {
    private SQLiteDatabase db;
    public void onCreate() {
        // 创建数据库
        createDatabase();
        createTableUser();
        createTableCustom();
        createTableVistLog();
        insertData();
        // 创建数据表
        //createTable();

        // 增加数据
        // insertData();

        // 删除数据
        // deleteData();

        // 修改数据
        // updateData();

        // 新*增加数据
        //callInsert();

        // 新*删除数据
        // callDelete();

        // 新*修改数据
        // callUpdate();

        // 查询数据
        //callQuery();

    }

    /**
     * 查询客户列表
     * @return
     */
    public List<Custom> queryCustomList() {
        createDatabase();
        String table = "custom";
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor c = db.query(table, columns, selection, selectionArgs, groupBy,
                having, orderBy);

        // 处理结果
        List<Custom> customList = new ArrayList<Custom>();
        c.moveToFirst();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex("id"));
                String CustomName = c.getString(c.getColumnIndex("CustomName"));
                double latitude = c.getDouble(c.getColumnIndex("latitude"));
                double longitude = c.getDouble(c.getColumnIndex("longitude"));
                Custom custom = new Custom();
                custom.setID(id);
                custom.setCustomName(CustomName);
                custom.setLatitude(latitude);
                custom.setLongitude(longitude);
                customList.add(custom);
                //int age = c.getInt(c.getColumnIndex("age"));
                c.moveToNext();
            }
        }
        c.close();
        return customList;
    }

    /**
     * 查询客户列表
     * @return
     */
    public List<VistLog> queryVistLogList() {
        createDatabase();
        String table = "vistlog";
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor c = db.query(table, columns, selection, selectionArgs, groupBy,
                having, orderBy);

        // 处理结果
        List<VistLog> vistLogList = new ArrayList<VistLog>();
        c.moveToFirst();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String company = c.getString(c.getColumnIndex("CustomName"));
                String content = c.getString(c.getColumnIndex("vistContent"));
                String address = c.getString(c.getColumnIndex("address"));
                double latitude = c.getDouble(c.getColumnIndex("latitude"));
                double longitude = c.getDouble(c.getColumnIndex("longitude"));
                VistLog vistLog = new VistLog();
                vistLog.setCustomName(company);
                vistLog.setRemark(content);
                vistLog.setAdress(address);
                //vistLog.setLatitude(latitude);
                //vistLog.setLongitude(longitude);
                vistLogList.add(vistLog);
                //int age = c.getInt(c.getColumnIndex("age"));
                c.moveToNext();
            }
        }
        c.close();
        return vistLogList;
    }

    /**
     * 增加客户
     */
    public long insertCustom(Custom custom) {
        createDatabase();
        String table = "custom"; // 数据表名
        String nullColumnHack = "CustomName";
        ContentValues values = new ContentValues();
        values.put("CustomName", custom.getCustomName());
        values.put("latitude" , custom.getLatitude());
        values.put("longitude", custom.getLongitude());
        values.put("address", custom.getAddress());
        long id = db.insert(table, nullColumnHack, values);
        return id;
    }

    /**
     * 写入日志
     */
    public long insertVistLog(VistLog vistLog) {
        if(null != vistLog) {
            createDatabase();
            String table = "vistlog"; // 数据表名
            String nullColumnHack = "vistContent";
            ContentValues values = new ContentValues();
            //values.put("userId", vistLog.getUserId());
            values.put("CustomName", vistLog.getCustomName());
            //values.put("vistContent", vistLog.getVistContent());
            values.put("latitude", vistLog.getLatitude());
            values.put("longitude", vistLog.getLongitude());
            //values.put("address", vistLog.getAddress());
            long id = db.insert(table, nullColumnHack, values);
            return id;
        }
        return -1;
    }

    /**
     * 注册
     */
    public long regist(User user){
        if(null != user) {
            createDatabase();
            String table = "user"; // 数据表名
            String nullColumnHack = "username";
            ContentValues values = new ContentValues();
            values.put("username", user.getUsername());
            values.put("password", user.getPassword());
            long id = db.insert(table, nullColumnHack, values);
            return id;
        }
        return -1;
    }

    /**
     * 根据用户名查询用户
     */
    public User findUserByUsername(String username) {
        createDatabase();
        String table = "user";
        String[] columns = null;
        String selection = "username=?"; //查询条件
        String[] selectionArgs = {username}; //具体的条件,注意要对应条件字段
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor c = db.query(table, columns, selection, selectionArgs, groupBy,
                having, orderBy);

        // 处理结果
        User user = new User();
        c.moveToFirst();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int id = c.getInt(c.getColumnIndex("id"));
                String resUsername = c.getString(c.getColumnIndex("username"));
                user.setId(id);
                user.setUsername(resUsername);
                c.moveToNext();
            }
        }
        c.close();
        return user;
    }

    /**
     * 根据用户名查询密码
     */
    public String  findPwdByUsername(String username) {
        createDatabase();
        String table = "user";
        String[] columns = {"password"};//你要的数据
        String selection = "username=?";//查询条件
        String[] selectionArgs = {username};//具体的条件,注意要对应条件字段
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor c = db.query(table, columns, selection, selectionArgs, groupBy,
                having, orderBy);

        // 处理结果
        String pwd = "";
        c.moveToFirst();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                pwd = c.getString(c.getColumnIndex("password"));
                //int age = c.getInt(c.getColumnIndex("age"));
                c.moveToNext();
            }
        }
        c.close();
        System.out.println("-----------------------------");
        System.out.println(pwd);
        System.out.println("-----------------------------");

        return pwd;
    }

    public void callQuery() {
        String table = "user";
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor c = db.query(table, columns, selection, selectionArgs, groupBy,
                having, orderBy);

        //Contacts contact = new Contacts();
        // 处理结果
        c.moveToFirst();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndex("name"));
                String phone = c.getString(c.getColumnIndex("phone"));
                String email = c.getString(c.getColumnIndex("email"));
                int age = c.getInt(c.getColumnIndex("age"));
//                contact.setName(name);
//                contact.setAge(age);
//                contact.setPhone(phone);
//                contact.setEmail(email);
//                contacts.add(contact);
//                Log.d("Adolph", "name=" + name + ",phone=" + phone + ",email="
//                        + email + ",age=" + age);
                c.moveToNext();
            }
        }
        c.close();
    }

    // 新*修改数据
    public void callUpdate() {
        String name = "张救济";
        String table = "users";
        ContentValues values = new ContentValues();
        values.put("name", "吴用");
        values.put("age", 26);
        values.put("email", "wuyong@tedu.cn");
        values.put("phone", "13800000000");
        String whereClause = "name=?";
        String[] whereArgs = { name };
        db.update(table, values, whereClause, whereArgs);
    }

    // 新*删除数据
    public void callDelete() {
        String name = "成恒";
        int age = 22;
        String table = "users";
        String whereClause = "age<? OR name=?";
        String[] whereArgs = { age + "", name };
        int affectedRows = db.delete(table, whereClause, whereArgs);
        //Log.d("tedu", "新*删除数据，操作执行完成！受影响的行数=" + affectedRows);
    }

    // 新*增加数据
    public void callInsert() {

        String table = "users3"; // 数据表名
        String nullColumnHack = "name";
        ContentValues values = new ContentValues();
        values.put("name", "关胜");
        values.put("email", "guansheng@tedu.cn");
        values.put("age", 31);
        values.put("phone", "13258745856");
        long id = db.insert(table, nullColumnHack, values);

        values.put("name", "潘金莲");
        values.put("email", "panjinlian@tedu.cn");
        values.put("age", 30);
        values.put("phone", "17854878521");
        id = db.insert(table, nullColumnHack, values);

        values.put("name", "燕青");
        values.put("email", "yanqing@tedu.cn");
        values.put("age", 26);
        values.put("phone", "13258545874");
        id = db.insert(table, nullColumnHack, values);

        values.put("name", "宋清");
        values.put("email", "songqing@tedu.cn");
        values.put("age", 25);
        values.put("phone", "12541289345");
        id = db.insert(table, nullColumnHack, values);

        values.put("name", "扈三娘");
        values.put("email", "husanniang@tedu.cn");
        values.put("age", 21);
        values.put("phone", "13878452658");
        id = db.insert(table, nullColumnHack, values);

        values.put("name", "公孙胜");
        values.put("email", "gongsunsheng@tedu.cn");
        values.put("age", 28);
        values.put("phone", "12841287469");
        id = db.insert(table, nullColumnHack, values);
    }

    // 修改数据
    public void updateData() {
        String sql = "update users set age=36 where name='成恒'";
        db.execSQL(sql);

    }

    // 删除数据
    public void deleteData() {
        String sql = "DELETE FROM users WHERE age=30";
        db.execSQL(sql);
    }

    // 增加数据
    public void insertData() {
        String sql = "INSERT INTO user (username,password) VALUES ('zhangsan','123456')";
        db.execSQL(sql);

        String sql1 = "INSERT INTO user (username,password) VALUES ('lisi','123456')";
        db.execSQL(sql1);

        String sql2 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('广州市环保局',22.968815,113.367345)";
        db.execSQL(sql2);

        String sql3 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('深圳市环保局',22.968515,113.367345)";
        db.execSQL(sql3);

        String sql4 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('北京市环保局',22.969815,113.367345)";
        db.execSQL(sql4);

        String sql5 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('上海市环保局',22.963815,113.367345)";
        db.execSQL(sql5);

        String sql6 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('天津市环保局',22.961815,113.367345)";
        db.execSQL(sql6);

        String sql7 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('重庆市环保局',22.960815,113.367345)";
        db.execSQL(sql7);

        String sql8 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('南京市环保局',22.957815,113.367345)";
        db.execSQL(sql8);

        String sql9 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('西安市环保局',22.979715,113.367345)";
        db.execSQL(sql9);

        String sql10 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('南宁市环保局',22.981815,113.367345)";
        db.execSQL(sql10);

        String sql11 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('南昌市环保局',22.984815,113.367345)";
        db.execSQL(sql11);

        String sql12 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('长沙市环保局',22.986815,113.367345)";
        db.execSQL(sql12);

        String sql13 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('杭州市环保局',22.988815,113.367345)";
        db.execSQL(sql13);

        String sql14 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('济南市环保局',22.991815,113.367345)";
        db.execSQL(sql14);

        String sql15 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('太原市环保局',23.034815,113.367345)";
        db.execSQL(sql15);

        String sql16 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('昆明市环保局',23.047815,113.367345)";
        db.execSQL(sql16);

        String sql17 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('兰州市环保局',23.067815,113.367345)";
        db.execSQL(sql17);

        String sql18 = "INSERT INTO custom (CustomName,latitude,longitude) VALUES ('福州市环保局',23.087815,113.367345)";
        db.execSQL(sql18);

        String sql19 = "INSERT INTO custom (CustomName) VALUES ('成都市环保局')";
        db.execSQL(sql19);

        String sql20 = "INSERT INTO vistlog (userId,CustomName,vistContent,latitude,longitude,address) VALUES (1,'京诚检测','今天拜访京诚检测集团',22.96781,113.367386,'广东省广州市番禺区一横西路6号')";
        db.execSQL(sql20);


    }

    // 创建数据库
    public void createDatabase() {
        File fileDir = new File(FilePathUtils.getDiskFilesDir() + "/database");
        System.out.println(fileDir.getAbsoluteFile());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String name = "bjt.db";
        //int mode = MODE_PRIVATE;
        File file = new File(fileDir, name);
        SQLiteDatabase.CursorFactory factory = null;
        //db = openOrCreateDatabase(name, mode, factory);
        db = SQLiteDatabase.openOrCreateDatabase(file, factory);
    }

    // 创建数据表
    public void createTableUser() {
        String sql = "CREATE TABLE user ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username VARCHAR(10) NOT NULL, "
                + "password VARCHAR(32) NOT NULL" + ")";
        db.execSQL(sql);
    }
    // 创建数据表
    public void createTableCustom() {
        String sql = "CREATE TABLE custom ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "CustomName VARCHAR(30) NOT NULL, "
                + "latitude DOUBLE,"
                + "longitude DOUBLE, "
                + "address VARCHAR(100)"
                + ")";
        db.execSQL(sql);
    }
    // 创建拜访日志记录表
    public void createTableVistLog() {
        String sql = "CREATE TABLE vistlog ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userId INTEGER NOT NULL, "
                + "CustomName VARCHAR(30) NOT NULL, "
                + "vistContent TEXT NOT NULL,"
                + "latitude DOUBLE, "
                + "longitude DOUBLE, "
                + "address VARCHAR(100)"
                + ")";
        db.execSQL(sql);
    }

}
