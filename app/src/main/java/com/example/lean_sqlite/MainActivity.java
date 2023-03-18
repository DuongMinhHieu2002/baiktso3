package com.example.lean_sqlite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private EditText editName,editPhone;
    private RadioGroup g1,g2;

    private RadioButton r1,r2,r3,r4,r5,r6;

    private ListView lvUser;
    private ArrayAdapter<User> adapter;
    private ArrayList<User> userList = new ArrayList<>();

    int idUpdate = -1;

    private Button btnAdd;
    private Button btnreset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        setContentView(R.layout.activity_main);
        innitData();

        //anh xa
        lvUser = findViewById(R.id.lvData);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnAdd = findViewById(R.id.btnAdd);
        btnreset = findViewById(R.id.btnnhaplai);
        g1 = findViewById(R.id.g1);
        g2 = findViewById(R.id.g2);
        r1 = findViewById(R.id.laptop);
        r2 = findViewById(R.id.utb);
        r3 = findViewById(R.id.ws);
        r4 = findViewById(R.id.mot);
        r5 = findViewById(R.id.hai);
        r6 = findViewById(R.id.ba);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idUpdate < 0){
                    insertRow();
                }else {
//                    updateRow();
//                    idUpdate = -1;
                }
                loadData();
            }
        });
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editName.setText(null);
                editPhone.setText(null);
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);
                r5.setChecked(false);
                r6.setChecked(false);

            }
        });


        adapter = new ArrayAdapter<User>(this, 0, userList ){

            public View getView(int position,  View convertView,  ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.data_item,null);

                TextView tvName = convertView.findViewById(R.id.tvName);
                TextView tvPhone = convertView.findViewById(R.id.tvPhone);
                TextView tvlm = convertView.findViewById(R.id.loaimay);
                TextView tvnam = convertView.findViewById(R.id.nambh);

                User u = userList.get(position);
                tvName.setText(u.getName());
                tvPhone.setText(u.getPhone());
                tvlm.setText(u.getLoaimay());
                tvnam.setText(u.getnambh());


                return convertView;
            }
        };
        lvUser.setAdapter(adapter);
        lvUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteUser(position);
                loadData();
                return false;
            }
        });
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showInfor(position);
            }
        });
        loadData();
    }

    private void showInfor(int position) {
        User u = userList.get(position);
        editName.setText(u.getName());
        editPhone.setText(u.getPhone());

        idUpdate = u.getId();
    }


    private void loadData() {
        userList.clear();
        String sql = "SELECT * FROM tbmaytinh";
        Cursor cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String tenmay = cursor.getString(1);
            String mamay = cursor.getString(2);
            String loaimay = cursor.getString(3);
            String nambh = cursor.getString(4);
            String data = id + "-" + tenmay + "-" + mamay + "\n";

            User u = new User();
            u.setId(id);
            u.setName(tenmay);
            u.setPhone(mamay);
            u.setLoaimay(loaimay);
            u.setnambh(nambh);
            Log.e("Data",data);
            userList.add(u);
            cursor.moveToNext();
        }
        adapter.notifyDataSetChanged();
    }

    private void insertRow() {
        String ten = editName.getText().toString();
        String mamay = editPhone.getText().toString();
        String loaimay;
        int check = g1.getCheckedRadioButtonId();
        if (check == R.id.laptop) loaimay = r1.getText().toString();
        else if (check == R.id.utb) loaimay = r2.getText().toString();
        else if (check == R.id.ws) loaimay = r3.getText().toString();
        else  loaimay = "loaimay";
        String nambh;
        int check1=g2.getCheckedRadioButtonId();
        if (check1 == R.id.mot) nambh = r4.getText().toString();
        else if (check1 == R.id.hai) nambh = r5.getText().toString();
        else if (check1 == R.id.ba) nambh = r6.getText().toString();
        else  nambh="nambh";
        String sql = "INSERT INTO tbmaytinh (name, phone, loaimay, nambh) VALUES ('" + ten + "','" + mamay+ "','" + loaimay + "','" + nambh + "')";
        db.execSQL(sql);
    }
//    private void updateRow() {
//        String name = editName.getText().toString();
//        String phone = editPhone.getText().toString();
//        String sql = "UPDATE tbmaytinh SET name =  ' " + name +"',phone = '" + phone + "'WHERE id = " +idUpdate;
//        db.execSQL(sql);
//    }
    private void deleteUser(int position) {
        int id = userList.get(position).getId();
        String sql = "DELETE FROM tbmaytinh WHERE id = " + id;
        db.execSQL(sql);
    }
    private void innitData() {
        db = openOrCreateDatabase("qlmaytinh.db",MODE_PRIVATE,null);

        String sql = "CREATE TABLE IF NOT EXISTS tbmaytinh (id integer primary key autoincrement, name text, phone text,loaimay text,nambh text)";
        db.execSQL(sql);
    }
}