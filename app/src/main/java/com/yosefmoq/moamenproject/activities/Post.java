package com.yosefmoq.moamenproject.activities;

public class Post {
        private int id;
        private String image;
        private String text;
        private int like_count;
        private int user_id;

        public int getUser_id() {
                return user_id;
        }

        public void setUser_id(int user_id) {
                this.user_id = user_id;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getImage() {
                return image;
        }

        public void setImage(String image) {
                this.image = image;
        }

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public int getLike_count() {
                return like_count;
        }

        public void setLike_count(int like_count) {
                this.like_count = like_count;
        }
}