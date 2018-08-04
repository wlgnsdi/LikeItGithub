package com.ryu.dev.likeitgithub.db;

public class DatabaseSchema {

    public static final String CREATE_GITHUB_TABLE =
            "CREATE TABLE IF NOT EXISTS " + GithubTable.TABLE_NAME + "( " +
                    GithubTable.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    GithubTable.COLUMN_LOGIN      + " TEXT, " +
                    GithubTable.COLUMN_AVATAR_URL + " TEXT, " +
                    GithubTable.COLUMN_LIKE   + " INTEGER);";
}
