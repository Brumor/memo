package com.example.android.memo;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditMemo extends AppCompatActivity {

    String memoId;
    String memoTitle;
    String memoContent;

    EditText memoTitleEditText;
    EditText memoContentEditText;

    TextView debug;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);

        Intent i = getIntent();

        memoId = i.getStringExtra("id");
        memoTitle = i.getStringExtra("title");
        memoContent = i.getStringExtra("content");

        debug = (TextView) findViewById(R.id.debug);

        memoTitleEditText = (EditText) findViewById(R.id.title_Edit);
        memoContentEditText = (EditText) findViewById(R.id.content_Edit);

        debug.setText("current Id : " + memoId);

        memoTitleEditText.setText(memoTitle);
        memoContentEditText.setText(memoContent);
    }

    public void updateMemo(View view) {

        ContentValues cs = new ContentValues();
        cs.put("title", memoTitleEditText.getEditableText().toString());
        cs.put("content", memoContentEditText.getEditableText().toString());

        getContentResolver().update(memopro.Content_URL, cs, "id = " + memoId, null);

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void deleteMemo(View view) {

        getContentResolver().delete(memopro.Content_URL, "id = "+ memoId, null);

        Toast.makeText(this, "deleted : " + memoId, Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
