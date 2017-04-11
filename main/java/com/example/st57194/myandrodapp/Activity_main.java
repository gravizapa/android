package com.example.st57194.myandrodapp;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class Activity_main extends Activity {
    final ArrayList<String> list = new ArrayList<>();
    DatabaseHandler db = new DatabaseHandler(this);
    ArrayList<String> your_list_arrayArrayList;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                final String name = data.getStringExtra("name");
                final String surname = data.getStringExtra("surname");
                your_list_arrayArrayList = new ArrayList<String>();

                if(name != null || surname != null ){
                    list.addAll(Collections.singletonList(name + " " + surname));
                    your_list_arrayArrayList.addAll(Collections.singletonList(name + " " + surname));

                    db.addListItem(your_list_arrayArrayList);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, list);
                    final ListView listView = (ListView) findViewById(R.id.ListViewItems);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                            final int pos = position;
                            LayoutInflater layoutInflater = LayoutInflater.from(Activity_main.this);
                            View promptView = layoutInflater.inflate(R.layout.input_dialog,parent, false);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Activity_main.this);
                            alertDialogBuilder.setView(promptView);

                            Button details = (Button) promptView.findViewById(R.id.Button_details);
                            Button delete = (Button) promptView.findViewById(R.id.Button_delete);

                            delete.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
                                    list.remove(pos);
                                    adapter.notifyDataSetChanged();

                                }
                            });

                            details.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
                                    Intent createIntent = new Intent(Activity_main.this, Activity_details.class);
                                    setResult(Activity.RESULT_OK, createIntent);
                                    createIntent.putExtra("PersonInfo", listView.getItemAtPosition(position).toString());
                                    startActivity(createIntent);
                                }
                            });

                            alertDialogBuilder.setCancelable(false)
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();
                        }
                    });
                }
            }
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = db.getListItem();
        if(cursor.getCount()!=0) {
            Log.e("count", " " + cursor.getCount());
            if (cursor != null) {
                cursor.moveToNext();

                do {
                    list.addAll(Collections.singletonList(cursor.getString(1)));
                    Log.e("value==", "" + cursor.getString(1));

                } while (cursor.moveToNext());
            }

            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, list);
            final ListView listView = (ListView) findViewById(R.id.ListViewItems);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                    final int pos = position;
                    LayoutInflater layoutInflater = LayoutInflater.from(Activity_main.this);
                    View promptView = layoutInflater.inflate(R.layout.input_dialog, parent, false);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Activity_main.this);
                    alertDialogBuilder.setView(promptView);

                    Button details = (Button) promptView.findViewById(R.id.Button_details);
                    Button delete = (Button) promptView.findViewById(R.id.Button_delete);

                    delete.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            list.remove(pos);
                            adapter.notifyDataSetChanged();
                        }
                    });

                    details.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            Intent createIntent = new Intent(Activity_main.this, Activity_details.class);
                            setResult(Activity.RESULT_OK, createIntent);
                            createIntent.putExtra("PersonInfo", listView.getItemAtPosition(position).toString());
                            startActivity(createIntent);
                        }
                    });

                    alertDialogBuilder.setCancelable(false)
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    AlertDialog alert = alertDialogBuilder.create();
                    alert.show();
                }
            });
        }
        Button next = (Button) findViewById(R.id.Button01);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent createContactIntent = new Intent(Activity_main.this, Activity_add.class);
                startActivityForResult(createContactIntent, 1);
            }
        });
        Button settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent createContactIntent = new Intent(getBaseContext(), Preferences.class);
                startActivity(createContactIntent);
            }
        });

    }
}
