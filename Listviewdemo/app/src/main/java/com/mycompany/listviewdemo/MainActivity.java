package com.mycompany.listviewdemo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    DatabaseAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Log.v("MainActivity", "OnCreate event");
        openDB();
        //Log.v("MainActivity", "Database opened");

        //Log.v("MainActivity", "Table cleared");

        // for the moment data is inserted here
        //but it should be loaded by special loader
        AddData();
        Log.v("MainActivity", "Rows inserted");
        final Cursor cursor = myDb.getAllRowsSubjects();

        CustomCursorAdapter myAdapter = new CustomCursorAdapter(this, cursor);
        ListView lvSubjsList = (ListView)findViewById(R.id.list);
        lvSubjsList.setAdapter(myAdapter);

        lvSubjsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                TextView textViewSubj = (TextView) view.findViewById(R.id.name);
                String SubjSelectedFromList = textViewSubj.getText().toString();
                TextView textViewSubjId = (TextView) view.findViewById(R.id.serNo);
                String SubjIdFromList = textViewSubjId.getText().toString();

                //Toast.makeText(getApplicationContext(), selectedFromList/*"some text"*/, Toast.LENGTH_SHORT).show();
                Intent quest_activity_intent = new Intent(MainActivity.this, QuestionActivity.class);
                quest_activity_intent.putExtra("subj", SubjSelectedFromList);
                quest_activity_intent.putExtra("subj_id", Integer.valueOf(SubjIdFromList));
                closeDB();
                MainActivity.this.startActivity(quest_activity_intent);
                //TextView header = (TextView) findViewById(R.id.edit_message);
            }

        });
    }

    private void openDB() {
        myDb = new DatabaseAdapter(this);
        myDb.open();
        Log.v("MainActivity", "DB opened");
    }

    private void closeDB() {
        myDb.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void AddData(){
        //clear old data
        myDb.deleteAllData();

        long SubjId, QuestId;
        SubjId = myDb.insertSubject("Терапія");
        QuestId = myDb.insertQuestion(
                "При дослiдженi кровi хворого виявлено значне збiльшення активностi МВ-форм КФК (креатинфосфокiнази) та ЛДГ-1. Яку патологiю можна  припустити?",
                SubjId, 1);

        myDb.insertAnswer(QuestId,"Iнфаркт мiокарда",1);
        myDb.insertAnswer(QuestId,"Гепатит",0);
        myDb.insertAnswer(QuestId,"Ревматизм",0);
        myDb.insertAnswer(QuestId,"Панкреатит",0);
        myDb.insertAnswer(QuestId,"Холецистит",0);

        QuestId = myDb.insertQuestion(
              "У спортсмена внаслiдок довiльної затримки дихання на 40 секунд зросли частота серцевих скорочень та системний артерiальний тиск. Реалiзацiя яких механiзмiв регуляцiї зумовлює змiни показникiв?",
              SubjId, 2);

        myDb.insertAnswer(QuestId,"Безумовнi симпатичнi рефлекси",1);
        myDb.insertAnswer(QuestId,"Безумовнi парасимпатичнi рефлекси",0);
        myDb.insertAnswer(QuestId,"Умовнi симпатичнi рефлекси",0);
        myDb.insertAnswer(QuestId,"Умовнi парасимпатичнi рефлекси",0);

        SubjId = myDb.insertSubject("Хірургія");
        QuestId = myDb.insertQuestion(
                "Пiсля загоєння рани на її мiсцi утворився рубець. Яка речовина є основним компонентом цього рiзновиду сполучної тканини?",
                SubjId, 1);

        myDb.insertAnswer(QuestId, "Колаген", 1);
        myDb.insertAnswer(QuestId, "Еластин",0);
        myDb.insertAnswer(QuestId, "Гiалуронова кислота",0);
        myDb.insertAnswer(QuestId, "Хондроiтин-сульфат",0);
        myDb.insertAnswer(QuestId, "Кератансульфат",0);

        SubjId = myDb.insertSubject("Травматологія");
        QuestId = myDb.insertQuestion(
            "В результатi травми пошкоджений спинний мозок (з повним розривом) на рiвнi першого шийного хребця.Що вiдбудеться з диханням?",
            SubjId, 1);
        myDb.insertAnswer(QuestId, "Припиняється", 1);
        myDb.insertAnswer(QuestId, "Не змiнюється", 0);
        myDb.insertAnswer(QuestId, "Зростає частота",0);
        myDb.insertAnswer(QuestId, "Зростає глибина",0);
        myDb.insertAnswer(QuestId, "Зменшується частота",0);


        SubjId = myDb.insertSubject("Інфекційні хвороби");
        QuestId = myDb.insertQuestion(
             "При мiкроскопiї мiкропрепарату з видiлень хворої хронiчним кольповагiнiтом лiкар виявив округлої форми та елiпсоподiбнi клiтини, що брунькуються, розмiром 3-6 мкм.Про збудника якої грибкової хвороби може йти мова в даному випадку?",
             SubjId, 1);
        myDb.insertAnswer(QuestId, "Кандидоз",1);
        myDb.insertAnswer(QuestId, "Кокцидiоз", 0);
        myDb.insertAnswer(QuestId, "Епiдермофiтiя", 0);
        myDb.insertAnswer(QuestId, "Мiкроспорiя", 0);
        myDb.insertAnswer(QuestId, "Криптококоз", 0);

        QuestId = myDb.insertQuestion(
             "До лiкарнi надiйшла дитина з дiагнозом \"стафiлококовий сепсис\". На яке живильне середовище потрiбно посiяти кров хворого з метою видiлення збудника?",
             SubjId, 2);
        myDb.insertAnswer(QuestId,"Цукрово-пептонний бульйон",1);
        myDb.insertAnswer(QuestId,"М’ясо-пептонний агар",0);
        myDb.insertAnswer(QuestId,"Середовище Плоскiрьова",1);
        myDb.insertAnswer(QuestId,"Середовище Бучiна",0);
        myDb.insertAnswer(QuestId,"Жовчно-сольовий агар",0);

        SubjId = myDb.insertSubject("Фармакологія");

        QuestId = myDb.insertQuestion("При обстеженнi чоловiка 40-ка рокiв було встановлено дiагноз: гiпохромна анемiя. Який препарат треба призначити для лiкування?",
            SubjId, 1);
        myDb.insertAnswer(QuestId,"Ферковен", 1);
        myDb.insertAnswer(QuestId,"Цiанокобаламiн", 0);
        myDb.insertAnswer(QuestId,"Пентоксил", 0);
        myDb.insertAnswer(QuestId,"Гепарин", 0);
        myDb.insertAnswer(QuestId,"Вiкасол", 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    lvSubjsList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView parentView, View childView,
        int position, long id)
        {
            setDetail(position);
        }

    public void onNothingSelected(AdapterView parentView) {

    }
});
*/
/*
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this, "You have selected " + SubjectsList.get(position), Toast.LENGTH_LONG).show();
    }
*/


}
