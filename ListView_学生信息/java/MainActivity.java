package com.example.jinec.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout);

        final Button addBtn = (Button)findViewById(R.id.addButton);
        final ListView listView = (ListView)findViewById(R.id.listView);
        final EditText classText = (EditText)findViewById(R.id.classText);
        final EditText idText = (EditText)findViewById(R.id.idText);
        final EditText nameText = (EditText)findViewById(R.id.nameText);

        List<String> list = new ArrayList<>();
        list.add("班级" + "                        " + "学号" + "                        " + "姓名");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, list);
        listView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                ArrayAdapter temp_adapter = (ArrayAdapter)listView.getAdapter();
                temp_adapter.add(classText.getText().toString() + "        " + idText.getText().toString() + "        " + nameText.getText().toString());
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view ,final int position, long id)
            {
                if(id > 0)
                {
                    PopupMenu popup = new PopupMenu(MainActivity.this, view);
                    popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                    {
                        @Override
                        public boolean onMenuItemClick(MenuItem item)
                        {
                            switch (item.getItemId())
                            {
                                case R.id.menu:
                                    ArrayAdapter temp_adapter = (ArrayAdapter) listView.getAdapter();
                                    temp_adapter.remove(temp_adapter.getItem(position));
                                    return true;
                                default:
                                    return false;
                            }

                        }
                    });
                }
                return true;
            }
        });
    }
}
