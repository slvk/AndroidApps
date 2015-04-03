/**
 * Created by VIanoshchuk on 05.03.2015.
 */


package com.mycompany.listviewdemo;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class DatabaseAdapter {
    private static final String TAG = "DBAdapter";

    // Subjects Fields
    public static final String SUBJ_ID = "_id";
    public static final String SUBJ_NAME = "name";
    // Questions Fields
    public static final String QUEST_ID = "_id";
    public static final String QUEST_TEXT = "q_text";
    public static final String QUEST_SUBJ_ID = "subj_id";
    public static final String QUEST_SERNO = "ser_num";
    // Questions Answers
    public static final String ANSW_ID = "_id";
    public static final String ANSW_TEXT = "a_text";
    public static final String ANSW_QUEST_ID = "quest_id";
    public static final String ANSW_IS_CORRECT = "is_correct";

    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)

    public static final String[] SUBJ_COLUMNS = new String[] {SUBJ_ID, SUBJ_NAME};
    //public static final String[] QUEST_COLUMNS = new String[] {QUEST_ID, QUEST_TEXT, QUEST_SUBJ_ID,QUEST_SERNO};
    //public static final String[] ANSW_COLUMNS = new String[] {ANSW_ID, ANSW_TEXT, ANSW_QUEST_ID, ANSW_IS_CORRECT};

    public static final String DATABASE_NAME = "MyDb";
    public static final String TAB_SUBJS = "Subjects";
    public static final String TAB_QUESTIONS = "Questions";
    public static final String TAB_ANSWERS = "Answers";

    public static final int DATABASE_VERSION = 29;

    private static final String CREATE_SUBJS_SQL =
            String.format("create table %s (%s integer  primary key autoincrement, %s text not null);", TAB_SUBJS, SUBJ_ID, SUBJ_NAME);
    private static final String CREATE_QUESTS_SQL =
            String.format("create table %s (%s integer primary key autoincrement, %s text not null, %s int not null, %s int not null, UNIQUE (%s, %s) ON CONFLICT REPLACE, FOREIGN KEY(%s) REFERENCES %s(%s));",
                    TAB_QUESTIONS, QUEST_ID, QUEST_TEXT, QUEST_SUBJ_ID, QUEST_SERNO,QUEST_SUBJ_ID, QUEST_SERNO,QUEST_SUBJ_ID, TAB_SUBJS, SUBJ_ID);
    private static final String CREATE_ANSWERS_SQL =
            String.format("create table %s(%s integer primary key autoincrement, %s int not null,%s text not null, %s int not null, FOREIGN KEY(%s) REFERENCES %s(%s));",
                    TAB_ANSWERS, ANSW_ID, ANSW_TEXT, ANSW_QUEST_ID, ANSW_IS_CORRECT, ANSW_QUEST_ID,TAB_QUESTIONS,QUEST_ID);
    private static final String get_quest_query = String.format("select %s,%s from %s where %s = ? and %s = ?", QUEST_TEXT, QUEST_ID,TAB_QUESTIONS, QUEST_SUBJ_ID, QUEST_SERNO);
    private static final String get_answers_query = String.format("select %s, %s from %s where %s = ?", ANSW_TEXT,ANSW_IS_CORRECT, TAB_ANSWERS, ANSW_QUEST_ID);
    private static final String get_count_quests_query = String.format("select count(*) from %s where %s = ?", TAB_QUESTIONS, QUEST_SUBJ_ID);
    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DatabaseAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DatabaseAdapter open() {
        db = myDBHelper.getWritableDatabase();
        //db = myDBHelper.getReadableDatabase();
        String name = myDBHelper.getDatabaseName();
        Log.v(TAG, "getWritableDatabase() "+name);
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
        Log.v(TAG, "myDBHelper.close()");
    }

    public Cursor getAllRowsSubjects() {
        String where = null;
        Cursor c = 	db.query(true, TAB_SUBJS, SUBJ_COLUMNS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public QuestionWithAnswer getQuestionBySN(int subject_id, int serno){

        Cursor q_cursor;
        String question_text = "";
        String question_id = "";

        q_cursor = db.rawQuery(get_quest_query, new String[] {String.valueOf(subject_id), String.valueOf(serno)});

        try {
            if (q_cursor.getCount() > 0) {
                q_cursor.moveToFirst();
                question_text = q_cursor.getString(q_cursor.getColumnIndex(QUEST_TEXT));
                question_id = q_cursor.getString(q_cursor.getColumnIndex(QUEST_ID));
            }
        }
        finally{
            q_cursor.close();
        }

        q_cursor = db.rawQuery(get_answers_query, new String[] {question_id});
        String[] question_answers = new String[q_cursor.getCount()];
        boolean[] answer_correctness = new boolean[q_cursor.getCount()];

        if (q_cursor.moveToFirst()) {
            int i = 0;
            do {
                question_answers[i] = q_cursor.getString(q_cursor.getColumnIndex(ANSW_TEXT));
                answer_correctness[i] = (q_cursor.getInt(q_cursor.getColumnIndex(ANSW_IS_CORRECT)) == 1);
                i++;
            } while (q_cursor.moveToNext());
        }

        q_cursor.close();

        QuestionWithAnswer quest_with_answer = new QuestionWithAnswer(question_text, question_answers, answer_correctness);
        return quest_with_answer;
    }

    public int getCountQuestionsInSubject(long subject_id){
        Cursor cursor_cnt;
        int QuestCount;
        cursor_cnt = db.rawQuery(get_count_quests_query, new String[] {String.valueOf(subject_id)});
        cursor_cnt.moveToFirst();
        QuestCount = cursor_cnt.getInt(0);
        cursor_cnt.close();
        return QuestCount;
    }


    public long insertSubject(String name){
        return DatabaseHelper.insertSubject(db,name);
    }

    public long insertQuestion(String text, long subject_id, int serno){
        return DatabaseHelper.insertQuestion(db, text, subject_id, serno);
    }

    public void insertAnswer(long question_id, String text, int is_correct){
        DatabaseHelper.insertAnswer(db,question_id, text, is_correct);
    }


    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
   /* private */public static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            Log.v(TAG, "OnCreate helper");
            _db.execSQL(CREATE_SUBJS_SQL);
            Log.v(TAG, "subjs created");
            _db.execSQL(CREATE_QUESTS_SQL);
            Log.v(TAG, "quests created");
            _db.execSQL(CREATE_ANSWERS_SQL);
            Log.v(TAG, "OnCreate helper finished");

            DataLoader.LoadData(_db);
            DataLoader.LoadData1(_db);
            DataLoader.LoadData2(_db);
            DataLoader.LoadData3(_db);
            DataLoader.LoadData4(_db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + TAB_SUBJS);
            _db.execSQL("DROP TABLE IF EXISTS " + TAB_QUESTIONS);
            _db.execSQL("DROP TABLE IF EXISTS " + TAB_ANSWERS);
            // Recreate new database:
            onCreate(_db);
        }

        public static void deleteAllData(SQLiteDatabase _db){
            _db.delete(TAB_ANSWERS, null, null);
            _db.delete(TAB_QUESTIONS, null, null);
            _db.delete(TAB_SUBJS, null, null);
        }
        public static long insertSubject(SQLiteDatabase _db, String name) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(SUBJ_NAME, name);
            // Insert it into the database.
            return _db.insert(TAB_SUBJS, null, initialValues);
        }

        public static long insertQuestion(SQLiteDatabase _db,String text, long subject_id, int serno) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(QUEST_TEXT, text);
            initialValues.put(QUEST_SUBJ_ID, subject_id);
            initialValues.put(QUEST_SERNO, serno);
            return _db.insert(TAB_QUESTIONS, null, initialValues);
        }

        public static void insertAnswer(SQLiteDatabase _db,long question_id, String text, int is_correct){
            if (!text.equals("-") && !text.equals("")) {
                ContentValues initialValues = new ContentValues();
                initialValues.put(ANSW_QUEST_ID, question_id);
                initialValues.put(ANSW_TEXT, text);
                initialValues.put(ANSW_IS_CORRECT, is_correct);
                _db.insert(TAB_ANSWERS, null, initialValues);
            }
        }


    }
}
