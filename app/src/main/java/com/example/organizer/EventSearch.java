package com.example.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.organizer.Database.DatabaseHelper;
import com.example.organizer.Database.EventModel;

import java.util.ArrayList;

public class EventSearch extends AppCompatActivity {

    TableLayout tb_search;
    SearchView sv_search;
    private DatabaseHelper dbHelper;
    private ArrayList<EventModel> eventModelArrylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_painting_search);


        dbHelper=new DatabaseHelper(this);
        String p_title=getIntent().getStringExtra("painting_title");
        eventModelArrylist=dbHelper.searchEvent(p_title);

        sv_search=findViewById(R.id.view_drug_search2);
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(EventSearch.this, EventSearch.class);
                intent.putExtra("painting_title",sv_search.getQuery().toString());
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        tb_search=findViewById(R.id.view_table);
        //TableLayout table = (TableLayout) findViewById(R.id.view_table);
        tb_search.setStretchAllColumns(true);
        if(eventModelArrylist!=null) {
            for (int i = 0; i < eventModelArrylist.size(); i++) {


                TableRow row = new TableRow(this);
                row.setBackgroundColor(Color.parseColor("#FFFFFF"));
                final String eventid = Integer.toString(eventModelArrylist.get(i).getEventId());
                String eventName  = eventModelArrylist.get(i).getEventName();
                String eventDate = eventModelArrylist.get(i).getDate();
                String eventTime = eventModelArrylist.get(i).getTime();
                String eventLocation = eventModelArrylist.get(i).getLocation_event();

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(EventSearch.this, EventEdit.class);
                        intent.putExtra("event_id", eventid);
                        startActivity(intent);
                    }
                });



                TextView tveventName = new TextView(this);
                tveventName.setText("" + eventName);
                tveventName.setTextAppearance(getApplicationContext(), R.style.table_row_tView2);

                TextView tveventDate = new TextView(this);
                tveventDate.setText("" + eventDate);
                tveventDate.setTextAppearance(getApplicationContext(), R.style.table_row_tView3);

                TextView tveventTime = new TextView(this);
                tveventTime.setText("" + eventTime);
                tveventTime.setTextAppearance(getApplicationContext(), R.style.table_row_tView3);



                row.setBottom(2);


                row.addView(tveventName);
                row.addView(tveventDate);
                row.addView(tveventTime);

                tb_search.addView(row);
            }
        }else{
            TableRow rowMsg = new TableRow(this);
            rowMsg.setBackgroundColor(Color.parseColor("#FFFFFF"));
            TextView tvmsg = new TextView(this);
            tvmsg.setText("No Paintings");
            tvmsg.setTextAppearance(getApplicationContext(), R.style.table_row_tView1);
            tvmsg.setGravity(Gravity.CENTER);
            rowMsg.addView(tvmsg);
            tb_search.addView(rowMsg);
        }
    }
}
