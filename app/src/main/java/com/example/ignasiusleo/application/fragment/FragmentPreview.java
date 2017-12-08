package com.example.ignasiusleo.application.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPreview extends Fragment {
    protected Cursor cursor1, cursor2;
    protected String args1, args2;
    DataHelper dbHelper;
    TextView id_stock, id_barang, nama_barang, qty, harga, tgl_dtg, tgl_exp;
    public FragmentPreview() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_preview, container, false);

        id_stock = v.findViewById(R.id.id_barcode);
        id_barang = v.findViewById(R.id.id_barang);
        nama_barang = v.findViewById(R.id.nama_barang);
        qty = v.findViewById(R.id.jml_barang);
        harga = v.findViewById(R.id.hrg_barang);
        tgl_dtg = v.findViewById(R.id.tgl_dtg);
        tgl_exp = v.findViewById(R.id.tgl_exp);

        Bundle bundle = this.getArguments();
        dbHelper = new DataHelper(getActivity());
        if (bundle != null) {
            args1 = bundle.getString("id_brg");
            args2 = bundle.getString("id_stok");
        }

        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String queryBrg = "SELECT * FROM barang WHERE id_barang = '" +
                    args1 + "';";
            String queryStock = "SELECT * FROM stock WHERE id_stock = '" +
                    args2 + "';";
            cursor1 = db.rawQuery(queryBrg, null);
            cursor2 = db.rawQuery(queryStock, null);


            cursor1.moveToFirst();
            cursor2.moveToFirst();

            if ((cursor1.getCount() > 0) && (cursor2.getCount() > 0)) {
                cursor1.moveToPosition(0);
                cursor2.moveToPosition(0);

                id_barang.setText(cursor1.getString(0));
                nama_barang.setText(cursor1.getString(1));
                qty.setText(cursor1.getString(2));
                harga.setText(cursor1.getString(3));
                id_stock.setText(cursor2.getString(0));
                tgl_dtg.setText(cursor2.getString(2));
                tgl_exp.setText(cursor2.getString(3));
            } else {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLiteException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return v;
    }

}