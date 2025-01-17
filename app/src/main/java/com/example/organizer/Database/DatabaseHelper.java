package com.example.organizer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME="MyAssistantDB";
    //private static final int DATABASE_VERSION = 4;

    private static final String TABLE_CUSTOMER = "customer";
    private static final String KEY_CUSTOMERID = "id";
    private static final String KEY_CUSTOMERUSERNAME = "username";
    private static final String KEY_CUSTOMEREMAIL = "email";
    private static final String KEY_CUSTOMERPASSWORD = "password";

    private static final String TABLE_TODO = "todo";
    private static final String KEY_TODOID = "todoId";
    private static final String KEY_TASK = "task";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_STATUS = "status";

    private static final String TABLE_EVENT = "event";
    private static final String KEY_EVENTID = "eventId";
    private static final String KEY_EVENTNAME = "eventName";
    private static final String KEY_EVENTDATE = "date";
    private static final String KEY_EVENTTIME = "time";
    private static final String KEY_LOCATION_EVENT = "location_event";

    private static final String TABLE_FRIEND = "friend";
    private static final String KEY_FRIENDID = "fid";
    private static final String KEY_FRIENDFNAME = "fName";
    private static final String KEY_FRIENDLNAME = "lName";
    private static final String KEY_FRIENDGENDER = "gender";
    private static final String KEY_FRIENDAGE = "age";
    private static final String KEY_FRIENDADDRESS = "address";
    private static final String KEY_FRIENDIMAGE = "image";


    private static final String TABLE_PHOTO= "photo";
    private static final String KEY_PHOTOID = "pid";
    private static final String KEY_PHOTONAME = "name";
    private static final String KEY_PHOTO = "image";



    private static final String TABLE_PAINTING = "painting";
    private static final String KEY_PAINTINGID = "pid";
    private static final String KEY_PAINTINGTITLE = "title";
    private static final String KEY_PAINTINGCATEGORY = "category";
    private static final String KEY_PAINTINGDESCRIPTION = "description";
    private static final String KEY_PAINTINGPRICE = "price";
    private static final String KEY_PAINTINGIMAGE = "painting";


    private static final String TABLE_CART = "cart";
    private static final String KEY_ITEMID = "cid";
    private static final String KEY_ITEMTITLE = "itemTitle";
    private static final String KEY_ITEMCATEGORY = "itemCategory";
    private static final String KEY_ITEMDESCRIPTION = "itemDescription";
    private static final String KEY_ITEMPRICE = "itemPrice";
    private static final String KEY_ITEMQUANTITY = "itemQuantity";

    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE " + TABLE_CUSTOMER + "(" + KEY_CUSTOMERID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CUSTOMERUSERNAME + " TEXT, "+ KEY_CUSTOMEREMAIL + " TEXT, "+ KEY_CUSTOMERPASSWORD + " TEXT);";

    private static final String CREATE_TABLE_FRIEND= "CREATE TABLE " + TABLE_FRIEND + "(" + KEY_FRIENDID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FRIENDFNAME + " TEXT, "+ KEY_FRIENDLNAME + " TEXT, "+ KEY_FRIENDGENDER + " TEXT, "+ KEY_FRIENDAGE+ " INTEGER, "+ KEY_FRIENDADDRESS + " TEXT, "+ KEY_FRIENDIMAGE + " BLOB);";

    private static final String CREATE_TABLE_PHOTO = "CREATE TABLE " + TABLE_PHOTO + "(" + KEY_PHOTOID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PHOTONAME + " TEXT, "+ KEY_PHOTO + " BLOB);";

    private static final String CREATE_TABLE_PAINTING = "CREATE TABLE " + TABLE_PAINTING + "(" + KEY_PAINTINGID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PAINTINGTITLE + " TEXT, "+ KEY_PAINTINGCATEGORY + " TEXT, "+ KEY_PAINTINGDESCRIPTION + " TEXT, "+ KEY_PAINTINGPRICE + " TEXT, "+ KEY_PAINTINGIMAGE + " BLOB);";

    private static final String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART + "(" + KEY_ITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ITEMTITLE + " TEXT, "+ KEY_ITEMCATEGORY + " TEXT, "+ KEY_ITEMDESCRIPTION + " TEXT, "+ KEY_ITEMPRICE + " TEXT, "+ KEY_ITEMQUANTITY + " TEXT);";


    private static final String CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_TODO + "(" + KEY_TODOID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TASK + " TEXT, "+ KEY_LOCATION + " TEXT, "+ KEY_STATUS + " TEXT);";

    private static final String CREATE_TABLE_EVENT = "CREATE TABLE " + TABLE_EVENT + "(" + KEY_EVENTID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EVENTNAME + " TEXT, "+ KEY_EVENTDATE+ " TEXT, "+ KEY_EVENTTIME+ " TEXT, "+ KEY_LOCATION_EVENT + " TEXT);";



    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CUSTOMER);
        db.execSQL(CREATE_TABLE_FRIEND);
        db.execSQL(CREATE_TABLE_PHOTO);
        db.execSQL(CREATE_TABLE_PAINTING);
        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_TODO);
        db.execSQL(CREATE_TABLE_EVENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int i1) {

    }
    //Photo Section Start
    public Boolean addPhoto(String name,String imgPath ) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            FileInputStream fs = new FileInputStream(imgPath);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);

            // Creating content values
            ContentValues values = new ContentValues();
            values.put(KEY_PHOTONAME, name);
            values.put(KEY_PHOTO, imgbyte);

            // insert row in friend table
            long insert = db.insert(TABLE_PHOTO, null, values);

            if(insert>=1){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<PhotoModel> getPhoto(String id){

        ArrayList<PhotoModel> photoArray = null;
        SQLiteDatabase db=getReadableDatabase();
        String[] args={id};
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_PHOTO+" WHERE "+KEY_PHOTOID+" = ?",args);
        if(c.moveToFirst()){
            photoArray=new ArrayList<PhotoModel>();
            do{
                PhotoModel photoMod=new PhotoModel();
                photoMod.setSid(c.getInt(c.getColumnIndex(KEY_PHOTOID)));
                photoMod.setName(c.getString(c.getColumnIndex(KEY_PHOTONAME)));
                photoMod.setImage(c.getBlob(c.getColumnIndex(KEY_PHOTO)));

                photoArray.add(photoMod);

            }while (c.moveToNext());
        }
        return photoArray;

    }
    public ArrayList<PhotoModel> getPhotos(){

        ArrayList<PhotoModel> photoArray = null;
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_PHOTO,null);
        if(c.moveToFirst()){
            photoArray=new ArrayList<PhotoModel>();
            do{
                PhotoModel photoMod=new PhotoModel();
                photoMod.setSid(c.getInt(c.getColumnIndex(KEY_PHOTOID)));
                photoMod.setName(c.getString(c.getColumnIndex(KEY_PHOTONAME)));
                photoMod.setImage(c.getBlob(c.getColumnIndex(KEY_PHOTO)));

                photoArray.add(photoMod);

            }while (c.moveToNext());
        }
        return photoArray;

    }
    public Boolean deletePhoto(String id) {

        // delete row in customer table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        int row=db.delete(TABLE_PHOTO, KEY_PHOTOID+ " = ?", new String[]{String.valueOf(id)});

        if(row>=1){
            return true;
        }else {
            return false;
        }

    }
    //Friend Section Start

    public Boolean addFriend(String fName, String lName, String Gender,Integer age,String address,String imgPath ) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            FileInputStream fs = new FileInputStream(imgPath);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_FRIENDFNAME, fName);
        values.put(KEY_FRIENDLNAME, lName);
        values.put(KEY_FRIENDGENDER, Gender);
        values.put(KEY_FRIENDAGE, age);
        values.put(KEY_FRIENDADDRESS, address);
        values.put(KEY_FRIENDIMAGE, imgbyte);

        // insert row in friend table
        long insert = db.insert(TABLE_FRIEND, null, values);

        if(insert>=1){
            return true;
        }else {
            return false;
        }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<FriendModel> getAllFriends() {
        // ArrayList<CustomerModel> customerModelArrayList = new ArrayList<CustomerModel>();
        ArrayList<FriendModel> friendModelArrayList = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_FRIEND,null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            friendModelArrayList = new ArrayList<FriendModel>();
            do {
                FriendModel friendModel = new FriendModel();
                friendModel.setFid(c.getInt(c.getColumnIndex(KEY_FRIENDID)));
                friendModel.setfName(c.getString(c.getColumnIndex(KEY_FRIENDFNAME)));
                friendModel.setlName(c.getString(c.getColumnIndex(KEY_FRIENDLNAME)));
                friendModel.setGender(c.getString(c.getColumnIndex(KEY_FRIENDGENDER)));

                // adding to customer list
                friendModelArrayList.add(friendModel);
            } while (c.moveToNext());
        }
        return friendModelArrayList;

    }
    public ArrayList<FriendModel> getFriend(String id){

        ArrayList<FriendModel> friendsArray = null;
        SQLiteDatabase db=getReadableDatabase();
        String[] args={id};
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_FRIEND+" WHERE "+KEY_FRIENDID+" = ?",args);
        if(c.moveToFirst()){
            friendsArray=new ArrayList<FriendModel>();
            do{
                FriendModel friendMod=new FriendModel();
                friendMod.setFid(c.getInt(c.getColumnIndex(KEY_FRIENDID)));
                friendMod.setfName(c.getString(c.getColumnIndex(KEY_FRIENDFNAME)));
                friendMod.setlName(c.getString(c.getColumnIndex(KEY_FRIENDLNAME)));
                friendMod.setGender(c.getString(c.getColumnIndex(KEY_FRIENDGENDER)));
                friendMod.setAge(c.getInt(c.getColumnIndex(KEY_FRIENDAGE)));
                friendMod.setAddress(c.getString(c.getColumnIndex(KEY_FRIENDADDRESS)));
                friendMod.setImage(c.getBlob(c.getColumnIndex(KEY_FRIENDIMAGE)));

                friendsArray.add(friendMod);

            }while (c.moveToNext());
        }
        return friendsArray;

    }
    public Boolean updateFriend(int id,String fName, String lName, String Gender,Integer age,String address,byte[] imgPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
//            FileInputStream fs = new FileInputStream(imgPath);
//            byte[] imgbyte = new byte[fs.available()];
//            fs.read(imgbyte);
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_FRIENDFNAME, fName);
        values.put(KEY_FRIENDLNAME, lName);
        values.put(KEY_FRIENDGENDER, Gender);
        values.put(KEY_FRIENDAGE, age);
        values.put(KEY_FRIENDADDRESS, address);
        values.put(KEY_FRIENDIMAGE, imgPath);

        // update row in customer table base on customer.is value
        int row=db.update(TABLE_FRIEND, values,KEY_FRIENDID + " = ?",new String[]{String.valueOf(id)});
        if(row>=1){
            return true;
        }else {
            return false;
        }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public Boolean deleteFriend(int id) {

        // delete row in customer table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        int row=db.delete(TABLE_FRIEND, KEY_FRIENDID + " = ?", new String[]{String.valueOf(id)});

        if(row>=1){
            return true;
        }else {
            return false;
        }

    }

    public ArrayList<FriendModel> searchFriend(String friend) {

        ArrayList<FriendModel> friendsArray = null;

        try {

            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_FRIEND + " WHERE " + KEY_FRIENDFNAME + " LIKE ?", new String[] { "%" + friend + "%" });

            // looping through all rows and adding to list
            if(c.moveToFirst()){
                friendsArray=new ArrayList<FriendModel>();
                do{
                    FriendModel friendMod=new FriendModel();
                    friendMod.setFid(c.getInt(c.getColumnIndex(KEY_FRIENDID)));
                    friendMod.setfName(c.getString(c.getColumnIndex(KEY_FRIENDFNAME)));
                    friendMod.setlName(c.getString(c.getColumnIndex(KEY_FRIENDLNAME)));
                    friendMod.setGender(c.getString(c.getColumnIndex(KEY_FRIENDGENDER)));
                    friendMod.setAge(c.getInt(c.getColumnIndex(KEY_FRIENDAGE)));
                    friendMod.setAddress(c.getString(c.getColumnIndex(KEY_FRIENDADDRESS)));
                    friendMod.setImage(c.getBlob(c.getColumnIndex(KEY_FRIENDIMAGE)));

                    friendsArray.add(friendMod);

                }while (c.moveToNext());
            }
        }catch(Exception e) {
            friendsArray = null;
        }
        return friendsArray;
    }



    //Customer Section Start

    public Boolean addCustomer(String name, String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_CUSTOMERUSERNAME, name);
        values.put(KEY_CUSTOMEREMAIL, email);
        values.put(KEY_CUSTOMERPASSWORD, password);

        // insert row in customer table
        long insert = db.insert(TABLE_CUSTOMER, null, values);

        if(insert>=1){
            return true;
        }else {
            return false;
        }

    }
//ADDTODO Section Start
    public Boolean addTODO(String task, String location, String status) {
        SQLiteDatabase db = getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_TASK,task);
        values.put(KEY_LOCATION, location);
        values.put(KEY_STATUS, status);

        // insert row in table
        long insert = db.insert(TABLE_TODO, null, values);

        if(insert>=1){
            return true;
        }else {
            return false;
        }

    }

    public ArrayList<TODOModel> getTODOtasks() {
        // ArrayList<CustomerModel> customerModelArrayList = new ArrayList<CustomerModel>();
        ArrayList<TODOModel> todoModelArrayList = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_TODO,null);


//        String selectQuery = "SELECT  * FROM " + TABLE_CUSTOMER;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            todoModelArrayList = new ArrayList<TODOModel>();
            do {
                TODOModel todoModel = new TODOModel();
                todoModel.setTodoId(c.getInt(c.getColumnIndex(KEY_TODOID)));
                todoModel.setTask(c.getString(c.getColumnIndex(KEY_TASK)));
                todoModel.setLocation(c.getString(c.getColumnIndex(KEY_LOCATION)));
                todoModel.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));

                // adding to customer list
                todoModelArrayList.add(todoModel);
            } while (c.moveToNext());
        }
        return todoModelArrayList;

    }


    public ArrayList<TODOModel> getTODOtask(String id) {
        // ArrayList<CustomerModel> customerModelArrayList = new ArrayList<CustomerModel>();
        ArrayList<TODOModel> todoModelArrayList = null;

        SQLiteDatabase db = getReadableDatabase();

        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_TODO+" WHERE "+KEY_TODOID+" = ?",new String[]{String.valueOf(id)});

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            todoModelArrayList = new ArrayList<TODOModel>();
            do {
                TODOModel todoModel = new TODOModel();
                todoModel.setTodoId(c.getInt(c.getColumnIndex(KEY_TODOID)));
                todoModel.setTask(c.getString(c.getColumnIndex(KEY_TASK)));
                todoModel.setLocation(c.getString(c.getColumnIndex(KEY_LOCATION)));
                todoModel.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));

                // adding to customer list
                todoModelArrayList.add(todoModel);
            } while (c.moveToNext());
        }
        return todoModelArrayList;

    }


    public Boolean updateTODO(String tid, String task, String location, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_TASK,task);
        values.put(KEY_LOCATION, location);
        values.put(KEY_STATUS, status);

        // update row in customer table base on customer.is value
        //String args=Integer.toString(id);
        int row=db.update(TABLE_TODO, values,KEY_TODOID + " = ?",new String[]{String.valueOf(tid)});
        if(row>=1){
            return true;
        }else {
            return false;
        }


    }

    public Boolean deleteTODO(String tid) {

        // delete row in customer table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        int row=db.delete(TABLE_TODO, KEY_TODOID + " = ?",
                new String[]{String.valueOf(tid)});

        if(row>=1){
            return true;
        }else {
            return false;
        }

    }
    public ArrayList<TODOModel>  searchTodo(String task) {
        // ArrayList<CustomerModel> customerModelArrayList = new ArrayList<CustomerModel>();
        ArrayList<TODOModel> todoModels = null;

        try {

            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TODO + " WHERE " + KEY_TASK + " LIKE ?", new String[] { "%" + task + "%" });

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                todoModels = new ArrayList<TODOModel>();
                do {
                    TODOModel todoModel = new TODOModel();
                    todoModel.setTodoId(c.getInt(c.getColumnIndex(KEY_TODOID)));
                    todoModel.setTask(c.getString(c.getColumnIndex(KEY_TASK)));
                    todoModel.setLocation(c.getString(c.getColumnIndex(KEY_LOCATION)));
                    todoModel.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));
                    // adding to customer list
                    todoModels.add(todoModel);
                } while (c.moveToNext());
            }
        }catch(Exception e) {
            todoModels = null;
        }
        return todoModels;
    }



//ADD Event Section Start
    public Boolean addEvent(String eventName,String date,String time,String location_event) {
        SQLiteDatabase db = getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_EVENTNAME,eventName);
        values.put(KEY_EVENTDATE, date);
        values.put(KEY_EVENTTIME, time);
        values.put(KEY_LOCATION_EVENT, location_event);


        // insert row in table
        long insert = db.insert(TABLE_EVENT, null, values);

        if(insert>=1){
            return true;
        }else {
            return false;
        }

    }

    public ArrayList<EventModel> getAllEvents() {
        // ArrayList<CustomerModel> customerModelArrayList = new ArrayList<CustomerModel>();
        ArrayList<EventModel> eventModelArrayList = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_EVENT,null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            eventModelArrayList = new ArrayList<EventModel>();
            do {
                EventModel eventModel = new EventModel();
                eventModel.setEventId(c.getInt(c.getColumnIndex(KEY_EVENTID)));
                eventModel.setEventName(c.getString(c.getColumnIndex(KEY_EVENTNAME)));
                eventModel.setDate(c.getString(c.getColumnIndex(KEY_EVENTDATE)));
                eventModel.setTime(c.getString(c.getColumnIndex(KEY_EVENTTIME)));
                eventModel.setLocation_event(c.getString(c.getColumnIndex(KEY_LOCATION_EVENT)));

                // adding to customer list
                eventModelArrayList.add(eventModel);
            } while (c.moveToNext());
        }
        return eventModelArrayList;

    }


    public ArrayList<EventModel> getEvent(String id){

        ArrayList<EventModel> eventArray = null;
        SQLiteDatabase db=getReadableDatabase();
        String[] args={id};
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_EVENT+" WHERE "+KEY_EVENTID+" = ?",args);
        if(c.moveToFirst()){
            eventArray=new ArrayList<EventModel>();
            do{
                EventModel eventModel=new EventModel();
                eventModel.setEventId(c.getInt(c.getColumnIndex(KEY_EVENTID)));
                eventModel.setEventName(c.getString(c.getColumnIndex(KEY_EVENTNAME)));
                eventModel.setDate(c.getString(c.getColumnIndex(KEY_EVENTDATE)));
                eventModel.setTime(c.getString(c.getColumnIndex(KEY_EVENTTIME)));
                eventModel.setLocation_event(c.getString(c.getColumnIndex(KEY_LOCATION_EVENT)));

                eventArray.add(eventModel);

            }while (c.moveToNext());
        }
        return eventArray;

    }


    public Boolean updateEvent(int id,String eventName,String date,String time,String location_event) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_EVENTNAME,eventName);
        values.put(KEY_EVENTDATE, date);
        values.put(KEY_EVENTTIME, time);
        values.put(KEY_LOCATION_EVENT, location_event);

        // update row in customer table base on customer.is value
        //String args=Integer.toString(id);
        int row=db.update(TABLE_EVENT, values,KEY_EVENTID + " = ?",new String[]{String.valueOf(id)});
        if(row>=1){
            return true;
        }else {
            return false;
        }
    }

    public Boolean deleteEvent(int id) {

        // delete row in customer table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        int row=db.delete(TABLE_EVENT, KEY_EVENTID + " = ?",
                new String[]{String.valueOf(id)});

        if(row>=1){
            return true;
        }else {
            return false;
        }

    }


    public ArrayList<EventModel>  searchEvent(String eventName) {
        // ArrayList<CustomerModel> customerModelArrayList = new ArrayList<CustomerModel>();
        ArrayList<EventModel> eventmodels = null;

        try {

            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_EVENT + " WHERE " + KEY_EVENTNAME + " LIKE ?", new String[] { "%" + eventName + "%" });

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                eventmodels = new ArrayList<EventModel>();
                do {
                    EventModel eventModel=new EventModel();
                    eventModel.setEventId(c.getInt(c.getColumnIndex(KEY_EVENTID)));
                    eventModel.setEventName(c.getString(c.getColumnIndex(KEY_EVENTNAME)));
                    eventModel.setDate(c.getString(c.getColumnIndex(KEY_EVENTDATE)));
                    eventModel.setTime(c.getString(c.getColumnIndex(KEY_EVENTTIME)));
                    eventModel.setLocation_event(c.getString(c.getColumnIndex(KEY_LOCATION_EVENT)));

                    eventmodels.add(eventModel);
                } while (c.moveToNext());
            }
        }catch(Exception e) {
            eventmodels = null;
        }
        return eventmodels;
    }




    public ArrayList<CustomerModel> searchCustomer(String customer) {
        // ArrayList<CustomerModel> customerModelArrayList = new ArrayList<CustomerModel>();
        ArrayList<CustomerModel> customerModels = null;

        try {

            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + KEY_CUSTOMERUSERNAME + " LIKE ?", new String[] { "%" + customer + "%" });

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                customerModels = new ArrayList<CustomerModel>();
                do {
                    CustomerModel customerModel = new CustomerModel();
                    customerModel.setId(c.getInt(c.getColumnIndex(KEY_CUSTOMERID)));
                    customerModel.setUsername(c.getString(c.getColumnIndex(KEY_CUSTOMERUSERNAME)));
                    customerModel.setEmail(c.getString(c.getColumnIndex(KEY_CUSTOMEREMAIL)));
                    customerModel.setPassword(c.getString(c.getColumnIndex(KEY_CUSTOMERPASSWORD)));
                   // adding to customer list
                    customerModels.add(customerModel);
                } while (c.moveToNext());
            }
        }catch(Exception e) {
            customerModels = null;
        }
        return customerModels;
    }

    public boolean readInfo(String eml, String pswd)
    {
        SQLiteDatabase db = getReadableDatabase();

        // define a projection that specifies which columns from the database
        // you will actually use after this query
        String[] projection = {
                CustomerMaster.Users.COLUMN_NAME_EMAIL,
                CustomerMaster.Users.COLUMN_NAME_PASSWORD
        };

        //Filter results
        String selection = CustomerMaster.Users.COLUMN_NAME_EMAIL + " = ?" + " AND " + CustomerMaster.Users.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {eml, pswd};

        // How you want the results sorted in the resulting cursor
        String sortOrder = CustomerMaster.Users.COLUMN_NAME_EMAIL + " DESC";

        Cursor cursor = db.query(
                CustomerMaster.Users.TABLE_NAME,           // the table to query
                projection,                 // the columns to return
                selection,               // the columns for the WHERE clause
                selectionArgs,            // the values for the WHERE clause
                null,               // don't group the rows
                null,                // don't filter by row groups
                sortOrder                  // the sort order
        );

        List emails = new ArrayList<>();
        List passwords = new ArrayList<>();

        while(cursor.moveToNext()){
            String username = cursor.getString( cursor.getColumnIndexOrThrow(CustomerMaster.Users.COLUMN_NAME_EMAIL));
            String password = cursor.getString( cursor.getColumnIndexOrThrow(CustomerMaster.Users.COLUMN_NAME_PASSWORD));
            emails.add(username);
            passwords.add(password);
        }
        if (cursor.getCount() == 0)
            return false;
        else
            return true;
        // cursor.close();

    }

//Customer Section Over


// Painting Section Start

        public Boolean addPainting(String title, String category, String description, String price, String imgPath) {
        SQLiteDatabase db = getWritableDatabase();

            try{

                FileInputStream fs = new FileInputStream(imgPath);
                byte[] imgbyte = new byte[fs.available()];
                fs.read(imgbyte);

                // Creating content values
                ContentValues values = new ContentValues();
                values.put(KEY_PAINTINGTITLE, title);
                values.put(KEY_PAINTINGCATEGORY, category);
                values.put(KEY_PAINTINGDESCRIPTION, description);
                values.put(KEY_PAINTINGPRICE, price);
                values.put(KEY_PAINTINGIMAGE,imgbyte);

                // insert row in customer table
                long insertPainting = db.insert(TABLE_PAINTING, null, values);

                fs.close();

                if(insertPainting>=1){
                    return true;
                }else {
                    return false;
                }


            }catch (Exception e){
                e.printStackTrace();
                return false;
            }


    }



    public Boolean updatePainting(int pid, String title, String category, String description, String price) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(this.KEY_PAINTINGTITLE, title);
        values.put(KEY_PAINTINGCATEGORY,category);
        values.put(KEY_PAINTINGDESCRIPTION,description);
        values.put(KEY_PAINTINGPRICE,price);

        // update row in customer table base on customer.is value
        //String args=Integer.toString(id);
        int row=db.update(TABLE_PAINTING, values,KEY_PAINTINGID + " = ?",new String[]{String.valueOf(pid)});
        if(row>=1){
            return true;
        }else {
            return false;
        }


    }


    public Boolean deletePainting(int pid) {

        // delete row in customer table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        int row=db.delete(TABLE_PAINTING, KEY_PAINTINGID + " = ?",
                new String[]{String.valueOf(pid)});

        if(row>=1){
            return true;
        }else {
            return false;
        }

    }



    public Cursor getPaintingData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

// Painting Section Over


// Cart Section Start

    public Boolean addCart(String itemTitle, String itemCategory, String itemDescription, String itemPrice, String itemQuantity) {
        SQLiteDatabase db = getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_ITEMTITLE, itemTitle);
        values.put(KEY_ITEMCATEGORY, itemCategory);
        values.put(KEY_ITEMDESCRIPTION, itemDescription);
        values.put(KEY_ITEMPRICE, itemPrice);
        values.put(KEY_ITEMQUANTITY, itemQuantity);

        // insert row in customer table
        long insertItem = db.insert(TABLE_CART, null, values);

        if(insertItem>=1){
            return true;
        }else {
            return false;
        }


    }


   /* public Boolean updateCart(String itemTitle, String itemCategory, String itemDescription, String itemPrice) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(this.KEY_ITEMTITLE, itemTitle);
        values.put(KEY_ITEMCATEGORY,itemCategory);
        values.put(KEY_ITEMDESCRIPTION,itemDescription);
        values.put(KEY_ITEMPRICE,itemPrice);
        values.put(KEY_ITEMQUANTITY,itemQuantity);

        // update row in customer table base on customer.is value
        //String args=Integer.toString(id);
        int row=db.update(TABLE_CART, values,KEY_ITEMID + " = ?",new String[]{String.valueOf(cid)});
        if(row>=1){
            return true;
        }else {
            return false;
        }


    }*/


    public Boolean deleteItem(int cid) {

        // delete row in customer table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        int row=db.delete(TABLE_CART, KEY_ITEMID + " = ?",
                new String[]{String.valueOf(cid)});

        if(row>=1){
            return true;
        }else {
            return false;
        }

    }



// Cart Section Over

}
