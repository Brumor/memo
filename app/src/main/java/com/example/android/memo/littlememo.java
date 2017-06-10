package com.example.android.memo;

/**
 * Created by pbric on 09/01/2017.
 */
public class littlememo {

    private String memo_Title;

    private String memo_Content;

    private String memo_FakeId;

    private String memo_TrueId;

    private String memo_FullTitle;

    public littlememo(String TrueId, String FakeId, String Title, String Content) {

        memo_TrueId = TrueId;

        memo_FakeId = FakeId;

        memo_Title = Title;

        memo_Content= Content;

        memo_FullTitle = FakeId +" - " + memo_Title;

    }

    public String getMemo_title() {
        return  memo_Title;
    }


    public String getMemo_content() {
        return memo_Content;
    }

    public String getMemo_Fakeid() {return memo_FakeId;}

    public String getMemo_TrueId() {return memo_TrueId;}

    public String getMemo_fulltitle() {return memo_FullTitle;}
}
