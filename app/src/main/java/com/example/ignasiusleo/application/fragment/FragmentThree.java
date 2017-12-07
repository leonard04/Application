package com.example.ignasiusleo.application.fragment;


import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment {
    public static FragmentThree fragmentThree;
    protected Cursor cursor, cursor2;
    String[] daftarNama, daftarId, daftarNama2, daftarId2;
    ListView ListView01, ListView02;
    DataHelper dbCenter = new DataHelper(getActivity());
    private TextView id;

    public FragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_three, container, false);
        ListView01 = v.findViewById(R.id.listView1);
        //ListView02 = v.findViewById(R.id.listView11);

        Button addStock = v.findViewById(R.id.addNewStock);
        addStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentAddStock = new FragmentAddStock();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragmentAddStock);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        Button addItem = v.findViewById(R.id.addNewBarang);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentAddBarang = new FragmentAddItem();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragmentAddBarang);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        dbCenter = new DataHelper(getActivity());
        fragmentThree = this;
        RefreshList();
        return v;
    }

    public void RefreshList() {

        SQLiteDatabase db = dbCenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM barang", null);
        //cursor2 = db.rawQuery("SELECT * FROM stock", null);

        daftarId = new String[cursor.getCount()];
        daftarNama = new String[cursor.getCount()];
        //daftarId2 = new String[cursor2.getCount()];
        //daftarNama2 = new String[cursor2.getCount()];

        cursor.moveToFirst();
        //cursor2.moveToFirst();

        //getNamadanId barang
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            daftarId[i] = cursor.getString(0);
            daftarNama[i] = cursor.getString(1);
        }

        /*for (int j = 0; j < cursor2.getCount(); j++) {
            cursor2.moveToPosition(j);
            daftarId2[j] = cursor2.getString(0);
            daftarNama2[j] = cursor2.getString(1);
        }*/

        ListView01.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarNama));
        ListView01.setSelected(true);
        ListView01.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftarId[arg2];
                final CharSequence[] dialogItem = {"Edit", "Delete"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Options");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        Bundle bundle = new Bundle();
                        switch (item) {
                            /*case 0:
                                Fragment detail = new FragmentDetailStock();
                                bundle.putString("id", selection);
                                detail.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.main_content, detail);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                break;*/

                            case 0:
                                Fragment edit = new FragmentEditStock();
                                bundle.putString("id", selection);
                                edit.setArguments(bundle);

                                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                fragmentTransaction2.replace(R.id.main_content, edit);
                                fragmentTransaction2.addToBackStack(null);
                                fragmentTransaction2.commit();
                                break;

                            case 1:
                                SQLiteDatabase database = dbCenter.getWritableDatabase();
                                database.execSQL("DELETE FROM barang where id_barang ='" + selection + "';");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
                return true;
            }
        });
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                Bundle bundle = new Bundle();
                Fragment preview = new FragmentPreview();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameContent, preview);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        /*ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, final int arg2, long arg3) {
                final String selection = daftarId[arg2];
                final CharSequence[] dialogItem = {"Detail", "Edit", "Delete"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Options");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        Bundle bundle = new Bundle();
                        switch (item) {
                            case 0:
                                Fragment detail = new FragmentDetailStock();
                                bundle.putString("id", selection);
                                detail.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.main_content, detail);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                break;

                            case 1:
                                Fragment edit = new FragmentEditStock();
                                bundle.putString("id", selection);
                                edit.setArguments(bundle);

                                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                fragmentTransaction2.replace(R.id.main_content, edit);
                                fragmentTransaction2.addToBackStack(null);
                                fragmentTransaction2.commit();
                                break;

                            case 2:
                                SQLiteDatabase database = dbCenter.getWritableDatabase();
                                database.execSQL("DELETE FROM barang where id_barang ='" + selection + "';");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });*/

        //ListView02.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarId2));

        ((ArrayAdapter) ListView01.getAdapter()).notifyDataSetInvalidated();
        //((ArrayAdapter) ListView02.getAdapter()).notifyDataSetInvalidated();
    }

}
