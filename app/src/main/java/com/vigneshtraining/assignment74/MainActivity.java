package com.vigneshtraining.assignment74;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.vigneshtraining.assignment74.database.DBhandler;
import com.vigneshtraining.assignment74.model.Person;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TableLayout table;
    private DBhandler handleDB;
    private List<Person> persons=new LinkedList<Person>();
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table= (TableLayout) findViewById(R.id.table);
        handleDB= DBhandler.getInstance(this);

        try {
            retrivePersons();
        }catch (Exception ex){
            Toast.makeText(this,"Error: "+ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void retrivePersons(){

        persons=handleDB.getAllPersons();

        if(persons.isEmpty()){
            addPersons();
        }else{


            for(int i=0;i<persons.size();i++){

                addRow(persons.get(i));
            }




        }
    }

    private void addPersons(){

        for(int i=0;i<5;i++){
            Person person=new Person("First Name "+i,"Last Name "+i);
            long id= handleDB.addPerson(person);
            if(toast!=null){
                toast.cancel();
            }
            toast=Toast.makeText(this,"Adding Person "+id,Toast.LENGTH_LONG);
            toast.show();
        }

        retrivePersons();
    }

    private void addRow(Person person){

        TableRow tr=new TableRow(MainActivity.this);
        tr.setBackgroundColor(getColor(R.color.divider_color));
        TableRow.LayoutParams tableRowParams=new TableRow.LayoutParams();
        tableRowParams.width=TableRow.LayoutParams.MATCH_PARENT;
        tableRowParams.height=TableRow.LayoutParams.MATCH_PARENT;
        tr.setLayoutParams(tableRowParams);

        LinearLayout linearLayout=new LinearLayout(MainActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        View vd1=new View(MainActivity.this);
        ViewGroup.LayoutParams vdParam=new ViewGroup.LayoutParams((int) getResources().getDimension(R.dimen.dip),ViewGroup.LayoutParams.MATCH_PARENT);
        vd1.setLayoutParams(vdParam);
        vd1.setBackgroundColor(getColor(R.color.colorAccent));

        TextView col1=new TextView(MainActivity.this);
        LinearLayout.LayoutParams col1Param= new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.2);
        col1.setText(person.getId().toString());
        col1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        col1.setLayoutParams(col1Param);
        col1.setPadding(2,2,2,2);
        col1.setTextColor(getColor(R.color.white));
        col1.setTextSize(16);

        View vd2=new View(MainActivity.this);
        vd2.setLayoutParams(vdParam);
        vd2.setBackgroundColor(getColor(R.color.colorAccent));

        TextView col2=new TextView(MainActivity.this);
        LinearLayout.LayoutParams col2Param= new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.4);
        col2.setText(person.getFirstName());
        col2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        col2.setLayoutParams(col2Param);
        col2.setPadding(2,2,2,2);
        col2.setTextColor(getColor(R.color.white));
        col2.setTextSize(16);

        View vd3=new View(MainActivity.this);
        vd3.setLayoutParams(vdParam);
        vd3.setBackgroundColor(getColor(R.color.colorAccent));

        TextView col3=new TextView(MainActivity.this);
        LinearLayout.LayoutParams col3Param= new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 0.4);
        col3.setText(person.getLastName());
        col3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        col3.setLayoutParams(col3Param);
        col3.setPadding(2,2,2,2);
        col3.setTextColor(getColor(R.color.white));
        col3.setTextSize(16);

        View vd4=new View(MainActivity.this);
        vd4.setLayoutParams(vdParam);
        vd4.setBackgroundColor(getColor(R.color.colorAccent));







        TableRow lastTr=new TableRow(MainActivity.this);


        lastTr.setLayoutParams(tableRowParams);
        View hd=new View(MainActivity.this);
        ViewGroup.LayoutParams hdParam=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) getResources().getDimension(R.dimen.dip));
        hd.setLayoutParams(hdParam);
        hd.setBackgroundColor(getColor(R.color.colorAccent));

        LinearLayout lastLayout=new LinearLayout(MainActivity.this);
        lastLayout.setOrientation(LinearLayout.HORIZONTAL);

        lastLayout.addView(hd);
        lastTr.addView(lastLayout);


        tr.addView(linearLayout);
        linearLayout.addView(vd1);
        linearLayout.addView(col1);
        linearLayout.addView(vd2);
        linearLayout.addView(col2);
        linearLayout.addView(vd3);
        linearLayout.addView(col3);
        linearLayout.addView(vd4);


        table.addView(tr);
        table.addView(lastTr);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handleDB.close();
    }
}
