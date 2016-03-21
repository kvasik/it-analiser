package com.example.vlad.analyse;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    String name[] = { "Juniper Networks", "Google", "Twitter", "Facebook", "Apple", "eBay", "Zynga", "VMware", "Oracle", "NVIDIA",
            "Microsoft", "Amazon", "Cisco", "Yahoo", "Intel", "Hewlett-Packard", "QUALCOMM",
            "Yandex", "Citrix" };
    int profit[] = {5, 66, 1, 3, 182, 16, 1, 4, 38, 4,
            62, 74, 11, 1, 11, 111,
            19, 1, 3 };
    String country[] = { "USA", "USA", "USA", "USA", "USA", "USA", "USA", "USA", "USA", "USA",
            "USA", "USA", "USA", "USA", "USA", "USA", "USA", "Russia", "USA" };

    Button btnAll, btnProfit, btnSort, btnGroup, btnHaving, btnClear, btnInf;
    EditText etProfit, etCountryProfit;
    RadioGroup rgSort;
    TextView textView;

    DBHelper dbHelper;
    SQLiteDatabase db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnInf = (Button) findViewById(R.id.btnInf);
        btnInf.setOnClickListener(this);

        btnAll = (Button) findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);

        btnProfit = (Button) findViewById(R.id.btnProfit);
        btnProfit.setOnClickListener(this);

        btnSort = (Button) findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        btnGroup = (Button) findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);

        btnHaving = (Button) findViewById(R.id.btnHaving);
        btnHaving.setOnClickListener(this);

        etProfit = (EditText) findViewById(R.id.etProfit);
        etCountryProfit = (EditText) findViewById(R.id.etCountryProfit);

        rgSort = (RadioGroup) findViewById(R.id.rgSort);

        textView = (TextView) findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        if (c.getCount() == 0) {
            ContentValues cv = new ContentValues();
            for (int i = 0; i < 19; i++) {
                cv.put("name", name[i]);
                cv.put("profit", profit[i]);
                cv.put("country", country[i]);
                db.insert("mytable", null, cv);
            }
        }
        c.close();
        dbHelper.close();
        }

    public void onClick(View v) {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.move);
        db = dbHelper.getWritableDatabase();

        String sProfit;
        String sCountryProfit;
        sProfit = etProfit.getText().toString();
        sCountryProfit = etCountryProfit.getText().toString();

        if(sProfit.isEmpty()) {sProfit = "0";}
        if(sCountryProfit.isEmpty()) {sCountryProfit = "0";}

        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor c = null;

        v.startAnimation(a);
        switch (v.getId()) {

            case R.id.btnInf:
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
            break;

            case R.id.btnClear:
                textView.setText("");
                break;

            case R.id.btnAll:
                textView.setText("");
                c = db.query("mytable", null, null, null, null, null, null);
                break;

            case R.id.btnProfit:
                textView.setText("");
                selection = "profit > ?";
                selectionArgs = new String[] { sProfit };
                c = db.query("mytable", null, selection, selectionArgs, null, null, null);
                break;

            case R.id.btnGroup:
                textView.setText("");
                columns = new String[] { "country", "sum(profit) as profit" };
                groupBy = "country";
                c = db.query("mytable", columns, null, null, groupBy, null, null);
                break;

            case R.id.btnHaving:
                textView.setText("");
                columns = new String[] { "country", "sum(profit) as profit" };
                groupBy = "country";
                having = "sum(profit) > " +sCountryProfit;
                c = db.query("mytable", columns, null, null, groupBy, having, null);
                break;

            case R.id.btnSort:
                textView.setText("");
                switch (rgSort.getCheckedRadioButtonId()) {
                    case R.id.rName:
                        orderBy = "name";
                        break;

                    case R.id.rProfit:
                        orderBy = "profit";
                        break;

                    case R.id.rCountry:
                        orderBy = "country";
                        break;
                }
                c = db.query("mytable", null, null, null, null, null, orderBy);
                break;
        }

        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                textView = (TextView)findViewById(R.id.textView);
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        switch(cn) {
                            case "id":
                                str = str.concat(c.getString(c.getColumnIndex(cn)) + ".  ");
                                break;
                            case "name":
                                str = str.concat(c.getString(c.getColumnIndex(cn)) + ",  ");
                                break;
                            default:
                                str = str.concat(cn + " = " + c.getString(c.getColumnIndex(cn)) + ";  ");
                            }
                        }
                    textView.append(str+"\n");
                    } while (c.moveToNext());
                }
            c.close();
            }  else
            textView.setText("");

        dbHelper.close();
    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table mytable ("
                    + "id integer primary key autoincrement," + "name text,"
                    + "profit integer," + "country text" + ");");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}