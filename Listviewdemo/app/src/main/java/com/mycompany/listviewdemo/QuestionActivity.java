package com.mycompany.listviewdemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class QuestionActivity extends ActionBarActivity {

    final String TAG = "QuestActivity";
    DatabaseAdapter myDb;
    ArrayList<Integer> QuestionList;
    final int END_LIST_INDICATOR = -1;
    Random QuestNumberGenerator = new Random();
    int questions_subj_id;
    private QuestionWithAnswer quest;
    private String questions_subj;
    private int QuestionsCount;
    private int CorrectAnswersCount = 0;
    private long StartTime;
    private ProgressBar pbAnswerProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent intent = getIntent();

        questions_subj = intent.getStringExtra("subj");

        questions_subj_id = intent.getIntExtra("subj_id", 0);
        StartTime = System.nanoTime();

        getSupportActionBar().setTitle(questions_subj);
        openDB();

        //Log.v(TAG, "Before init");
        InitQuestionsList(questions_subj_id);

        displayNextQuestion(questions_subj_id);

        final Button button = (Button) findViewById(R.id.nextbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayNextQuestion(questions_subj_id);
            }
        });

        ListView AnswLV = (ListView) findViewById(R.id.listViewAnswers);



        AnswLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Button NextQuestButton = (Button) findViewById(R.id.nextbutton);
                if (!NextQuestButton.isEnabled()) {
                    NextQuestButton.setEnabled(true);

                    TextView textViewAnsw = (TextView) view.findViewById(android.R.id.text1);
                    int highlight_color;
                    if (quest.Correctness[position]) {
                        highlight_color = android.R.color.holo_green_light; // correct answer
                        CorrectAnswersCount++;
                    }
                    else {
                        highlight_color = android.R.color.holo_red_light; // incorrect answer
                        int CorrectAnswerPosition = quest.getCorrectAnswerPosition();

                        TextView textViewCorrectAnsw = (TextView) parent.getChildAt(CorrectAnswerPosition).findViewById(android.R.id.text1);
                        textViewCorrectAnsw.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    }
                    textViewAnsw.setBackgroundColor(getResources().getColor(highlight_color));
                    //todo
                    pbAnswerProgress.setProgress(QuestionsCount - QuestionList.size());
                }
            }

        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent
        // activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            View messageView = getLayoutInflater().inflate(R.layout.about_window, null, false);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name);
            builder.setView(messageView);
            builder.create();
            builder.show();
            return true;
        }
        else if (id ==R.id.action_exit){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
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
        Button NextQuestButton = (Button) findViewById(R.id.nextbutton);
        NextQuestButton.setEnabled(false);
        if(QuestionList.size() == 1)
            NextQuestButton.setText(R.string.statistics_label);
        int NextQuestionSN = GetRandomQuestionNumber();
        TextView QuestTV = (TextView) findViewById(R.id.question);
        ListView AnswLV = (ListView) findViewById(R.id.listViewAnswers);
        if (NextQuestionSN == END_LIST_INDICATOR) {
            Intent quest_activity_intent = new Intent(QuestionActivity.this, StatisticsActivity.class);
            quest_activity_intent.putExtra("subj", questions_subj);
            quest_activity_intent.putExtra("QuestionsCount", QuestionsCount);
            quest_activity_intent.putExtra("CorrectAnswersCount", CorrectAnswersCount);
            quest_activity_intent.putExtra("TimeElapsed", System.nanoTime()- StartTime);
            closeDB();
            this.startActivity(quest_activity_intent);

        } else {
            Log.v(TAG, "subject_id = " + subject_id);
            Log.v(TAG, "NextQuestionSN = " + NextQuestionSN);

            quest = myDb.getQuestionBySN(subject_id, NextQuestionSN);
            QuestTV.setText(quest.Question);
            Log.v(TAG, "quest.Answers.length = " + quest.Answers.length);

            ArrayAdapter<String> AnswersLA =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quest.Answers);
            AnswLV.setAdapter(AnswersLA);
            //Log.v(TAG, "Adapter set correctly");
        }
    }

    private void InitQuestionsList(int SubjId){
        Log.v(TAG, "inside method SubjId = "+SubjId);
        QuestionsCount = myDb.getCountQuestionsInSubject(SubjId);
        //todo
        pbAnswerProgress = (ProgressBar) findViewById(R.id.AnswerProgress);
        pbAnswerProgress.setMax(QuestionsCount);
        Log.v(TAG, "QuestionsCount = "+QuestionsCount);
        QuestionList = new ArrayList<Integer>(QuestionsCount);

        for (int i = 0; i < QuestionsCount; i++){
            QuestionList.add(i, i);
        }

    }

    public int GetRandomQuestionNumber(){
        int QuestSerno;

        Log.v(TAG, "QuestionList.size = " + QuestionList.size());
        if (QuestionList.size() == 0)
            QuestSerno = END_LIST_INDICATOR;
        else{
            int QuestIndex = QuestNumberGenerator.nextInt(QuestionList.size());
            Log.v(TAG, "index generated = " + QuestIndex);
            QuestSerno = QuestionList.get(QuestIndex);
            Log.v(TAG, "QuestSerno = " + QuestSerno);
            QuestionList.remove(QuestIndex);
            Log.v(TAG, "Serno removed from list");
            ++QuestSerno; // add 1 because in DB generation starts from 1
        }
        return QuestSerno;
    }

}
