package com.example.organizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.organizer.Database.EventModel;
import com.example.organizer.Database.DatabaseHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventList extends AppCompatActivity {


    TableLayout table_tb;
    SearchView sv_search;
    private DatabaseHelper dbHelper;
    private ArrayList<EventModel> eventModelArrylist;
    String status;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        dbHelper=new DatabaseHelper(this);
        eventModelArrylist=dbHelper.getAllEvents();

        sv_search=findViewById(R.id.view_painting_search1);
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(EventList.this, EventSearch.class);
                intent.putExtra("painting_title",sv_search.getQuery().toString());
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });




        table_tb=findViewById(R.id.view_table);
        //TableLayout table = (TableLayout) findViewById(R.id.view_table);
        table_tb.setStretchAllColumns(true);
        if(eventModelArrylist!=null) {
            for (int i = 0; i < eventModelArrylist.size(); i++) {


                TableRow row = new TableRow(this);
                row.setBackgroundColor(Color.parseColor("#FFFFFF"));
                final String eventid = Integer.toString(eventModelArrylist.get(i).getEventId());
                String eventName  = eventModelArrylist.get(i).getEventName();
                String eventDate = eventModelArrylist.get(i).getDate();
                String eventTime = eventModelArrylist.get(i).getTime();
                String eventLocation = eventModelArrylist.get(i).getLocation_event();
                String dateTime=eventDate + " "+eventTime+ ":00";

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dateobj = new Date();


                try {
                    Date d1 = df.parse(dateTime);
                    Date d2 = df.parse(df.format(dateobj));
                    if(d1.compareTo(d2) > 0){
                        status="current";
                    }else {
                        status="Past";
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(EventList.this, EventEdit.class);
                        intent.putExtra("event_id", eventid);
                        startActivity(intent);
                    }
                });



                TextView tveventName = new TextView(this);
                tveventName.setText("  " + eventid);
                tveventName.setTextAppearance(getApplicationContext(), R.style.table_row_tView2);

                TextView tveventDate = new TextView(this);
                tveventDate.setText("" + eventName);
                tveventDate.setTextAppearance(getApplicationContext(), R.style.table_row_tView3);

                TextView tveventTime = new TextView(this);
                tveventTime.setText("" + status);
                tveventTime.setTextAppearance(getApplicationContext(), R.style.table_row_tView3);



                row.setBottom(2);


                row.addView(tveventName);
                row.addView(tveventDate);
                row.addView(tveventTime);

                table_tb.addView(row);
            }
        }else{
            TableRow rowMsg = new TableRow(this);
            rowMsg.setBackgroundColor(Color.parseColor("#FFFFFF"));
            TextView tvmsg = new TextView(this);
            tvmsg.setText("No Events");
            tvmsg.setTextAppearance(getApplicationContext(), R.style.table_row_tView1);
            tvmsg.setGravity(Gravity.CENTER);
            rowMsg.addView(tvmsg);
            table_tb.addView(rowMsg);
        }
    }

}
