package com.example.android.memo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int URL_LOADER = 1;

    ArrayList<littlememo> memo_list;

    EditText titleEditText;
    EditText contentEditText;

    public ListView memoslist;

    public String memoIdforEdit;
    public String memoTitleforEdit;
    public String memoContentforEdit;
    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        memoslist = (ListView) findViewById(R.id.list_memo);

        titleEditText = (EditText) findViewById(R.id.title_ET);
        contentEditText = (EditText) findViewById(R.id.content_ET);

        showit();

        memoslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                final Intent i = new Intent(getApplicationContext(), EditMemo.class);

                littlememo actualmemo = memo_list.get(position);

                memoIdforEdit = actualmemo.getMemo_TrueId();
                memoTitleforEdit = actualmemo.getMemo_title();
                memoContentforEdit = actualmemo.getMemo_content();

                i.putExtra("id", memoIdforEdit);
                i.putExtra("title", memoTitleforEdit);
                i.putExtra("content", memoContentforEdit);

                startActivity(i);



            }
        });
    }

    public void addMemo(View view) {

        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        ContentValues values = new ContentValues();

        values.put(memopro.title, title);
        values.put(memopro.content, content);

        Uri uri = getContentResolver().insert(memopro.Content_URL, values);

        showit();

        titleEditText.setText(null);
        contentEditText.setText(null);

    }

    public  void showit () {

        memo_list = new ArrayList<>();

        int memo_fake_id = 0;

        memoslist = (ListView) findViewById(R.id.list_memo);

        String[] projection = new String[]{"id", "title", "content"};

        Cursor cursor = getContentResolver().query(memopro.Content_URL, projection, null, null, null);

        if (cursor.moveToFirst()) {

            do {

                memo_fake_id += 1;

                id = cursor.getString(cursor.getColumnIndex("id"));

                String title =  cursor.getString(cursor.getColumnIndex("title"));

                String content = cursor.getString(cursor.getColumnIndex("content"));

                memo_list.add( new littlememo(id, String.valueOf(memo_fake_id), title, content));

            } while (cursor.moveToNext());

            MemoAdapter memoAdapter = new MemoAdapter(this, memo_list);

            memoslist.setAdapter(memoAdapter);


        } else {
            memoslist.setAdapter(null);
        }

    }

    public void deleteAll (View view) {
        long deleted = getContentResolver().delete(memopro.Content_URL, null, null);
        memoslist.setAdapter(null);
        memo_list.clear();
        showit();
    }
}
