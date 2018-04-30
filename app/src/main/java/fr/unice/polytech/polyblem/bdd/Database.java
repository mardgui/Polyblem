package fr.unice.polytech.polyblem.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.polyblem.model.Issue;

/**
 * Created by user on 16/04/2018.
 */

public class Database extends SQLiteOpenHelper {

    private static final String ISSUE_ID = "idIssue";
    private static final String ISSUE_TITLE = "titleIssue";
    private static final String ISSUE_CATEGORY = "category";
    private static final String ISSUE_DESCRIPTION = "description";
    private static final String ISSUE_PICTUREURL = "pictureURL";
    private static final String ISSUE_LOCATION = "location";
    private static final String ISSUE_LOCATIONDETAILS = "locationDetails";
    private static final String ISSUE_URGENCY = "urgency";
    private static final String ISSUE_EMAIL = "email";
    private static final String ISSUE_DATE = "dateIssue";

    private static final String ISSUE_TABLE_NAME = "issue";
    private static final String ISSUE_CREATE_TABLE =
            "CREATE TABLE "+ ISSUE_TABLE_NAME +" ( "+ ISSUE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            ISSUE_TITLE+" TEXT NOT NULL,"+
            ISSUE_CATEGORY + " TEXT CHECK (category IN ('Manque','Casse','Dysfonctionnement', 'Propreté', 'Autre')), "+
            ISSUE_DESCRIPTION + " TEXT," +
            ISSUE_PICTUREURL +" TEXT,"+
            ISSUE_LOCATION + " TEXT,"+
            ISSUE_LOCATIONDETAILS +" TEXT,"+
            ISSUE_URGENCY + " TEXT CHECK (urgency IN ('Faible','Moyen','Forte')),"+
            ISSUE_EMAIL+" TEXT,"+
            ISSUE_DATE + " TEXT)";

    private static final String ISSUE_INSERT = "INSERT INTO issue(titleissue, category, description, pictureurl, location, locationdetails, urgency, email, dateissue) " +
            "VALUES ('Issue1', 'Casse', null, null, 'Bat O', '355', 'Faible', 'marion@etu.fr', '16/05/18');";
    private static final String ISSUE_INSERT2 =  " INSERT INTO issue(titleissue, category, description, pictureurl, location,locationdetails, urgency, email, dateissue)" +
            "VALUES ('Issue2', 'Propreté', null, null, 'Bat E', '235', 'Forte', 'florian@etu.fr', '10/05/18');";
    private static final String ISSUE_INSERT3 = " INSERT INTO issue(titleissue, category, description, pictureurl, location,locationdetails, urgency, email, dateissue)" +
            "VALUES ('Issue3', 'Autre', null, null, 'Bat W', '235', 'Moyen', 'quentin@etu.fr', '14/05/18');";


    private static final String ISSUE_DROP_TABLE = "DROP TABLE IF EXISTS" + ISSUE_TABLE_NAME+";";


    private static String DB_NAME = "polyblem_database";
    private final Context myContext;

    public Database(Context context){
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(ISSUE_CREATE_TABLE);
        db.execSQL(ISSUE_INSERT);
        db.execSQL(ISSUE_INSERT2);
        db.execSQL(ISSUE_INSERT3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(ISSUE_DROP_TABLE);
        onCreate(db);
    }

    @Override
    public synchronized void close() {
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null)
            db.close();
        super.close();
    }

    public Issue getIssue(Cursor c){
        int idIssue = c.getInt(0);
        String titleIssue = c.getString(1);
        String category= c.getString(2);
        String description= c.getString(3);
        String pictureUrl= c.getString(4);
        String location= c.getString(5);
        String locationDetails= c.getString(6);
        String urgency= c.getString(7);
        String email= c.getString(8);
        String date= c.getString(9);
        return new Issue(idIssue,titleIssue,category,description,pictureUrl,location,locationDetails,urgency,
                email, date);
    }

    public Issue getIssue(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM issue WHERE " + ISSUE_ID  + " = " + id, null);
        c.moveToFirst();
        return getIssue(c);
    }

    public List<Issue> getAllIssues(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM issue", null);
        c.moveToFirst();
        List<Issue> issues = new ArrayList<>();
        while(!c.isAfterLast()){
            issues.add(getIssue(c));
            c.moveToNext();
        }
        c.close();
        return issues;
    }

    public void addIssue(Issue issue){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ISSUE_TITLE, issue.getTitleIssue());
        values.put(ISSUE_CATEGORY, issue.getCategory());
        values.put(ISSUE_DESCRIPTION, issue.getDescription());
        values.put(ISSUE_PICTUREURL, issue.getPictureUrl());
        values.put(ISSUE_LOCATION, issue.getLocation());
        values.put(ISSUE_LOCATIONDETAILS, issue.getLocationDetails());
        values.put(ISSUE_URGENCY, issue.getUrgency());
        values.put(ISSUE_EMAIL, issue.getEmail());
        values.put(ISSUE_DATE, issue.getDate());

        db.insert(ISSUE_TABLE_NAME, null, values);
        db.close();
    }

    public int addAPicture(Issue issue){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues value = new ContentValues();

        value.put(ISSUE_PICTUREURL, issue.getPictureUrl());

        return db.update(ISSUE_TABLE_NAME,value,
                ISSUE_ID +"=?", new String[]{String.valueOf(issue.getIdIssue())});
    }

    public void deleteAIssue(Issue issue){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(ISSUE_TABLE_NAME, ISSUE_ID +"=?", new String[]{String.valueOf(issue.getIdIssue())});
        db.close();
    }

}
