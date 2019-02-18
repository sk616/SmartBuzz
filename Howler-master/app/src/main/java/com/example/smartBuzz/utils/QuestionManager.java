package com.example.smartBuzz.utils;

import android.util.Log;

import com.example.smartBuzz.model.Question;

import io.realm.Realm;

/*
 ** Created by Gautam Krishnan {@link https://github.com/GautiKrish}
 */public class QuestionManager {
    private static Realm realm;
    private static Question question;
    private static String[] questions = {
            "Who is the first Prime Minister of India?",
            "Give the correct differentiation of sin(x)",
            "The biggest planet in our Solar System is",
            "Value of -123%17 is?",
            "What is heavier :  A pound of feathers or a pound of stones?",
            "In one day, how many times do the minute hand and the second hand of a clock make a straight line?",
            "1=5, 2=10, 3=15, 4=20, 5=?",
            "8 - 1 * 0 + 2 / 2 = ??"
    };
    private static String[][] answers = {
            {"Jawaharlal Nehru", "Subhash Chandra Bose", "Mahatma Gandhi", "Dr B.R.Ambedkar"},
            {"cos(x)", "tan(x)", "-cot(x)", "Not Differentiable"},
            {"Jupiter", "Venus", "Earth", "Uranus"},
            {"21", "4", "13", "19"},
            {"a pound of stones", "a pound of feathers", "same weight", "undeterminable"},
            {"32", "44", "50", "24"},
            {"25", "2", "45", "1"},
            {"1", "9", "7", "10"}
    };
    private static String correctAnswers[] = {"Jawaharlal Nehru", "cos(x)", "Jupiter", "13", "same weight", "44", "1", "9"};

    private static int length = questions.length;

    public static void populateQuestionDatabase() {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm -> {
            for (int i = 0; i < length; i++) {
                question = realm.createObject(Question.class, i);
                question.setQuestion(questions[i]);
                question.setAnswer1(answers[i][0]);
                question.setAnswer2(answers[i][1]);
                question.setAnswer3(answers[i][2]);
                question.setAnswer4(answers[i][3]);
                question.setCorrectAnswer(correctAnswers[i]);
            }
        });

    }


    public static Question supplyQuestionObject() {
        int random = (int) (Math.random() * 7);
        Log.i("Random Number", String.valueOf(random));
        realm = Realm.getDefaultInstance();
        Question question = realm.where(Question.class).equalTo("iD", random).findFirst();
        return question;
    }
}
