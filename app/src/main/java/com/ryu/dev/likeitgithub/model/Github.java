package com.ryu.dev.likeitgithub.model;

import android.content.ContentValues;
import com.google.gson.annotations.SerializedName;
import com.ryu.dev.likeitgithub.db.GithubTable;
import java.util.ArrayList;
import java.util.List;

public class Github {

    @SerializedName("items")
    public List<Items> itemsList = new ArrayList<>();

    public List<Items> getItemsList() {
        return itemsList;
    }

    public static class Items {

        @SerializedName("login")
        String login;
        @SerializedName("avatar_url")
        String avatar_url;
        boolean like;

        int id;

        public int getId() {
            return id;
        }
        public void setId(int serial) {
            id = serial;
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

        public void setLogin(String login) {
            this.login = login;
        }

        public String getAvatarUrl() {
            return avatar_url;
        }

        public void setAvatalUrl(String url) {
            this.avatar_url = url;
        }

        @Override
        public String toString() {
            return login + ", " + Boolean.toString(like) + ", " + avatar_url;
        }
    }

    public static ContentValues getContentValues(Items items) {
        ContentValues values = new ContentValues();
        values.put(GithubTable.COLUMN_LOGIN, items.login);
        values.put(GithubTable.COLUMN_AVATAR_URL, items.avatar_url);
        values.put(GithubTable.COLUMN_LIKE, items.like);
        return values;
    }
}
