package com.mycompany.listviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class StatisticsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Intent intent = getIntent();

        String questions_subj = intent.getStringExtra("subj");
        getSupportActionBar().setTitle(questions_subj);
        int QuestionsCount = intent.getIntExtra("QuestionsCount", 0);
        int CorrectAnswersCount = intent.getIntExtra("CorrectAnswersCount", 0);
        long TimeElapsed =  intent.getLongExtra("TimeElapsed", 0);

        TextView tvQuestionsCount = (TextView)findViewById(R.id.QuestionsCount);
        TextView tvCorrectAnswersCount = (TextView)findViewById(R.id.RightAnswersCount);
        TextView tvRightAnsPercent = (TextView)findViewById(R.id.RightAnswPercent);
        TextView tvTimeElapsed = (TextView)findViewById(R.id.TimeElapsed);

        tvQuestionsCount.setText(Integer.toString(QuestionsCount));
        tvCorrectAnswersCount.setText(Integer.toString(CorrectAnswersCount));
        tvRightAnsPercent.setText(Integer.toString((int)Math.round(CorrectAnswersCount  * 100.0 /QuestionsCount)) + "%");
        tvTimeElapsed.setText(Integer.toString((int)Math.round(TimeElapsed/(1000000000.0 * 60))) + getResources().getString(R.string.minute_label));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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
}
