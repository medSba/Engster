package com.example.engster;

public class Notes {
        private int id;
        private String wordexample;
        private String expression;
        private String type;

    public Notes(String word, String expression, String type) {
        this.wordexample = word;
        this.expression = expression;
        this.type = type;
    }

    public Notes(int id, String word, String expression, String type) {
        this.id = id;
        this.wordexample = word;
        this.expression = expression;
        this.type = type;
    }

    public Notes() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordexample() {
        return wordexample;
    }

    public void setWordexample(String wordexample) {
        this.wordexample = wordexample;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
