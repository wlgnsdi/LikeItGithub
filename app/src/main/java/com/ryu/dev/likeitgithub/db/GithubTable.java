package com.ryu.dev.likeitgithub.db;

public class GithubTable {

    public static final String TABLE_NAME = "github";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_AVATAR_URL = "avatar_url";
    public static final String COLUMN_LIKE = "like";

    public static final String[] getColumns() {
        return new String[]{
                COLUMN_ID,
                COLUMN_LOGIN,
                COLUMN_AVATAR_URL,
                COLUMN_LIKE
        };
    }

        String[] projection = {
            GithubTable.COLUMN_ID,
            GithubTable.COLUMN_LOGIN,
            GithubTable.COLUMN_AVATAR_URL,
            GithubTable.COLUMN_LIKE
    };
}
