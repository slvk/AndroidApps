package com.mycompany.listviewdemo;

/**
 * Created by VIanoshchuk on 11.03.2015.
 */
public class QuestionWithAnswer {
    public static int AnswersCount = 5;
    public String Question;
    public String[] Answers;

    public int CorrectAnswerNumber;
    QuestionWithAnswer(String Question, String[] Answers, int CorrectAnswerNumber){
        this.Question = Question;
        this.Answers = Answers;
        this.CorrectAnswerNumber = CorrectAnswerNumber;
    }
}
