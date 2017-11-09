package vishal.com.taskmanagementapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private List<Date> dateList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter mAdapter;
    String selectedDate;
    private ListAdapter todoListAdapter;
    TextView dateView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        mAdapter = new Adapter(dateList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        PopupMenu popup = new PopupMenu(MainActivity.this, recyclerView);
                        popup.getMenuInflater().inflate(R.menu.menu1, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getTitle().toString().equals("Delete this task")) {
                                    dateList.remove(position);
                                    mAdapter.notifyDataSetChanged();
                                    Toast.makeText(MainActivity.this, "Task deleted.", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            }
                        });
                        popup.show();
                    }
                })
        );
        if (dateList.size() == 0) {

        }

        // prepareData("Birthday","Birthday of sahil","6 November, 2017");

    }

    private void prepareData(String a, String b, String c) {
        Date date;
        date = new Date(a, b, c);
        // date = new Date("Birthday","Birthday of sahil","6 November, 2017");
        dateList.add(date);

        mAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_task:


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater mFactory = LayoutInflater.from(MainActivity.this);
                final View mView = mFactory.inflate(R.layout.task_entry, null);
                dateView = (TextView) mView.findViewById(R.id.h1);
                alertDialogBuilder.setView(mView);

                final AlertDialog alert11 = alertDialogBuilder.create();


                ImageButton bt = (ImageButton) mView.findViewById(R.id.imageButton);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DateFormat df_medium_us = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);

                        DatePickerDialog dp = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int selectedYear,
                                                  int selectedMonth, int selectedDay) {
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(selectedYear, selectedMonth, selectedDay);
                                selectedDate = df_medium_us.format(newDate.getTime());


                                dateView.setText("Date: " + selectedDate);

                                //      prepareData("new","new description",  df_medium_us_str);

                                // editTextFromDate.setText("Date of journey: "+selectedDay + "-" + (selectedMonth + 1) + "-"+ selectedYear);
                            }

                        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                        dp.getDatePicker().setMinDate(new java.util.Date().getTime());

                        dp.getDatePicker().setMinDate(new java.util.Date().getTime());
                        dp.setTitle("");


                        dp.show();


                    }
                });
                Button btAdd = (Button) mView.findViewById(R.id.addButton);
                final EditText title = (EditText) mView.findViewById(R.id.title_);
                final EditText title_des = (EditText) mView.findViewById(R.id.title_desc);
                btAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dateView.getText().toString().equals("select date")) {
                            Toast.makeText(MainActivity.this, "Please select date.", Toast.LENGTH_SHORT).show();

                        } else if (title.getText().toString().equals("")) {
                            title.setError("Please enter title.");
                        } else if (title_des.getText().toString().equals("")) {
                            title_des.setError("Please enter some description.");
                        } else {
                              prepareData(title.getText().toString().trim(), title_des.getText().toString().trim(), selectedDate.trim());
                            alert11.dismiss();


                        }

                    }
                });
                Button btCancel = (Button) mView.findViewById(R.id.cancelButton);
                btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert11.dismiss();
                    }
                });
                alert11.setCancelable(false);
                alert11.show();


            default:
                return super.onOptionsItemSelected(item);
        }
    }





}