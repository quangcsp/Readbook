package com.example.quangnguyen.readbook;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by quangnguyen on 03/04/2017.
 */

public class FragmentChapter extends Fragment {

    private Button btnChap1;
    private Button btnChap2;
    private Button btnChap3;
    private Button btnChap4;

    private TextView txChap1;
    private TextView txChap2;
    private TextView txChap3;
    private TextView txChap4;

    public static final String FileInput = "asdf";
    public static final String HOME = "Home";
    public static final String READ = "Read";

    public interface GiaotiepActivity{
        public  void thaydoi(String chuoi);
    }

    GiaotiepActivity mCallBack;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter, container,false);

        btnChap1 = (Button) view.findViewById(R.id.btnChap1);
        btnChap2 = (Button) view.findViewById(R.id.btnChap2);
        btnChap3 = (Button) view.findViewById(R.id.btnChap3);
        btnChap4 = (Button) view.findViewById(R.id.btnChap4);

        txChap1 = (TextView) view.findViewById(R.id.txChap1);
        txChap2 = (TextView) view.findViewById(R.id.txChap2);
        txChap3 = (TextView) view.findViewById(R.id.txChap3);
        txChap4 = (TextView) view.findViewById(R.id.txChap4);

        FileOperations fop = new FileOperations();

        String text1 = fop.read(FileInput);
        txChap1.setText(text1.substring(0,56)+ "...");
        btnChap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("keyWord", "asdf");

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft  = fm.beginTransaction();
                FragmentHome home = new FragmentHome();
                home.setArguments(bundle);
                ft.replace(R.id.container1,home);
                ft.addToBackStack("");
                ft.commit();
                fm.executePendingTransactions();

                mCallBack.thaydoi("Chương 1");

//                Toast.makeText(getActivity(), "11111111111111", Toast.LENGTH_SHORT).show();
            }
        });

        String text2 = fop.read("testfile");
        txChap2.setText(text2.substring(0,35)+ "...");
        btnChap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("keyWord", "testfile");

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft  = fm.beginTransaction();
                FragmentHome home = new FragmentHome();
                home.setArguments(bundle);
                ft.replace(R.id.container1,home);
                ft.addToBackStack("");
                ft.commit();
                fm.executePendingTransactions();

                mCallBack.thaydoi("Chương 2");
            }
        });

        String text3 = fop.read("chuong3");
        txChap3.setText(text3.substring(0,35)+ "...");
        btnChap3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("keyWord", "chuong3");

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft  = fm.beginTransaction();
                FragmentHome home = new FragmentHome();
                home.setArguments(bundle);
                ft.replace(R.id.container1,home);
                ft.addToBackStack("");
                ft.commit();
                fm.executePendingTransactions();

                mCallBack.thaydoi("Chương 3");
            }
        });


        String text4 = fop.read("chuong4");
        txChap4.setText(text4.substring(0,35)+ "...");
        btnChap4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("keyWord", "chuong4");

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft  = fm.beginTransaction();
                FragmentHome home = new FragmentHome();
                home.setArguments(bundle);
                ft.replace(R.id.container1,home);
                ft.addToBackStack("");
                ft.commit();
                fm.executePendingTransactions();

                mCallBack.thaydoi("Chương 4");
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (GiaotiepActivity) getActivity();
    }
}
