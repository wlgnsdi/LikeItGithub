package com.ryu.dev.likeitgithub.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Github {

    @SerializedName("items")
    public List<Items> itemsList = new ArrayList<>();

    public List<Items> getItemsList() {
        return itemsList;
    }

    public class Items {
        @SerializedName("login")
        String login;
        @SerializedName("name")
        String name;
        @SerializedName("id")
        int id;
        @SerializedName("avatar_url")
        String avatar_url;
        @SerializedName("html_url")
        String html_url;
        @SerializedName("type")
        String type;
        @SerializedName("score")
        float score;
        boolean like;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public boolean getLike() {
            return like;
        }

        public void setLike(boolean like) {
            this.like = like;
        }

        public String getLogin() {
            return login;
        }

        public int getId() {
            return id;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public String getHtml_url() {
            return html_url;
        }

        public String getType() {
            return type;
        }

        public float getScore() {
            return score;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
