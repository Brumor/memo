package com.example.android.memo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by pbric on 09/01/2017.
 */
public class memocur extends CursorAdapter {
    public memocur(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.littlememo, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView memoTitle = (TextView) view.findViewById(R.id.name);
        TextView memoContent = (TextView) view.findViewById(R.id.summary);

        int nameColumnIndex = cursor.getColumnIndex(memopro.title) ;
        int breedColumnIndex= cursor.getColumnIndex(memopro.content);

        String title = cursor.getString(nameColumnIndex);
        String content = cursor.getString(breedColumnIndex);

        memoTitle.setText(title);
        memoContent.setText(content);

    }
}
