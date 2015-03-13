package com.mycompany.listviewdemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class QuestionActivity extends ActionBarActivity {

    final String TAG = "QuestActivity";
    DatabaseAdapter myDb;
    ArrayList<Integer> QuestionList;
    final int END_LIST_INDICATOR = -1;
    Random QuestNumberGenerator = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent intent = getIntent();
        String questions_subj = intent.getStringExtra("subj");

        int questions_subj_id = intent.getIntExtra("subj_id", 0);
        getSupportActionBar().setTitle(questions_subj);
        openDB();

        Log.v(TAG, "Before init");
        InitQuestionsList(questions_subj_id);

        displayNextQuestion(questions_subj_id);
        //header.setText(String.valueOf(CountQuestionsInSubject));
        //myDb.
        closeDB();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
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
    private void openDB() {
        myDb = new DatabaseAdapter(this);
        myDb.open();
        Log.v(TAG, "DB opened");
    }

    private void closeDB() {
        myDb.close();
    }

    private void displayNextQuestion(int subject_id){
        int NextQuestionSN = GetRandomQuestionNumber();
        TextView QuestTV = (TextView) findViewById(R.id.question);
        ListView AnswLV = (ListView) findViewById(R.id.listViewAnswers);
        if (NextQuestionSN == END_LIST_INDICATOR) {
            //todo start new activity that shows answers statistic
            QuestTV.setText("");
        } else {
            //Log.v(TAG, "NextQuestionSN " + NextQuestionSN);
            QuestionWithAnswer quest = myDb.getQuestionBySN(subject_id, NextQuestionSN);
            QuestTV.setText(quest.Question);
            ArrayAdapter<String> AnswersLA =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quest.Answers);
            AnswLV.setAdapter(AnswersLA);

            //Log.v(TAG, quest.Question);
            //for (String str : quest.Answers)
            //    if (str != null) Log.v(TAG, str);
/*            Log.v(TAG, quest.Answers[0]);
            Log.v(TAG, quest.Answers[1]);
            Log.v(TAG, quest.Answers[2]);
            Log.v(TAG, quest.Answers[3]); */
        }
    }

    private void InitQuestionsList(int SubjId){
        Log.v(TAG, "inside method SubjId = "+SubjId);
        int QuestionsCount = myDb.getCountQuestionsInSubject(SubjId);
        Log.v(TAG, "QuestionsCount = "+QuestionsCount);
        QuestionList = new ArrayList<Integer>(QuestionsCount);

        for (int i = 0; i < QuestionsCount; i++){
            QuestionList.add(i, i);
        }

    }

    public int GetRandomQuestionNumber(){
        int QuestSerno;
        if (QuestionList.size() == 0)
            QuestSerno = END_LIST_INDICATOR;
        else{
            int QuestIndex = QuestNumberGenerator.nextInt(QuestionList.size());
            QuestSerno = QuestionList.get(QuestIndex);
            QuestionList.remove(QuestIndex);
            ++QuestSerno; // add 1 because in DB generation starts from 1
        }
        return QuestSerno;
    }

}
