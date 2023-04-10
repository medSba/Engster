package com.example.engster;

public class Images {
    public Images() {

    }

        public byte[] getImageData() {
                return imageData;
        }

        public void setImageData(byte[] imageData) {
                this.imageData = imageData;
        }

        public int getWordExpressionId() {
                return wordExpressionId;
        }

        public void setWordExpressionId(int wordExpressionId) {
                this.wordExpressionId = wordExpressionId;
        }

        private byte[] imageData;
        private int wordExpressionId; // Foreign key referencing the word and expression table

        public Images(byte[] imageData, int wordExpressionId) {
                this.imageData = imageData;
                this.wordExpressionId = wordExpressionId;
        }
}
