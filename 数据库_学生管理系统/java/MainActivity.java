package com.example.pc.myapplication5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private Button btn_add;
    private Button btn_clear;
    private ListView lv;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> data = new ArrayList<>();
    private DBAdapter db;
    private int mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add = (Button)findViewById(R.id.btn_add);
        btn_clear = (Button)findViewById(R.id.btn_clear);
        lv = (ListView)findViewById(R.id.lv);

        db = new DBAdapter(MainActivity.this);
        db.open();

        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mode = 0;
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("mode", mode);
                startActivity(intent);
                finish();
            }
        });

        String[] from = {"id", "class", "number", "name"};
        int[] to = {R.id.tv_id, R.id.tv_class, R.id.tv_number, R.id.tv_name};
        simpleAdapter = new SimpleAdapter(this, data, R.layout.item_view, from, to);
        lv.setAdapter(simpleAdapter);
        Student[] students = db.queryAllData();
        if(students != null){
            String clas;
            String num;
            String name;
            int id;
            for(int i = 0; i < students.length; i++){
                clas = students[i].getClas();
                num = students[i].getNumber();
                name = students[i].getName();
                id = students[i].getID();

                Map<String, Object> item = new HashMap<>();
                item.put("id", id);
                item .put("class", clas);
                item.put("number", num);
                item.put("name",name);
                data.add(item);
                simpleAdapter.notifyDataSetChanged();
            }
        }

        btn_clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                db.deleteAllData();
                data.clear();
                simpleAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "清空成功", Toast.LENGTH_SHORT).show();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view,final int pos, long id) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Map<String, Object> student = data.get(pos);
                        switch (item.getItemId()){
                            case R.id.update:
                                mode = 1;
                                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                                intent.putExtra("mode", mode);
                                intent.putExtra("id",(Integer) student.get("id"));
                                startActivity(intent);
                                finish();
                                return true;
                            case R.id.delete:
                                db.deleteOneData((Integer) student.get("id"));
                                data.remove(pos);
                                simpleAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "ID:" + student.get("id") + "删除成功", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                return true;
            }
        });

    }
}