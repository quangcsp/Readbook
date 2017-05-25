package com.example.quangnguyen.readbook;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by quangnguyen on 21/03/2017.
 */

public class FragmentRead extends Fragment {
    private TextView content;

    public interface GiaotiepActivity{
        public  void thaydoi(String chuoi);
    }

    FragmentChapter.GiaotiepActivity mCallBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container,false);
//        String textResult[] = getArguments().getStringArray("keyWord");
        ArrayList<String> textResult = getArguments().getStringArrayList("keyWord");

        String data = textResult.get(textResult.size()-1);

        FileOperations fop = new FileOperations();
        String text = fop.read(textResult.get(Integer.parseInt(data.substring(data.indexOf("Chuong ")+7)) - 1));
        mCallBack.thaydoi("Chương "+Integer.parseInt(data.substring(data.indexOf("Chuong ")+7)));
//        Toast.makeText(getActivity(), textResult.get(Integer.parseInt(data.substring(data.indexOf("Chuong ")+7)) - 1),
//                    Toast.LENGTH_SHORT).show();

        content = (TextView) view.findViewById(R.id.test);
//            String textToHighlight = textResult.get(textResult.size()-1).substring(0,data.indexOf('-')-1);
//            String replacedWith = "<font color='red'>" + textToHighlight + "</font>";;
//
//            text = text.replaceAll(textToHighlight,replacedWith);
//            content.setText(Html.fromHtml(text));
            content.setText(text);
        return  view;
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft  = fm.beginTransaction();
            FragmentHome home = new FragmentHome();
            ft.replace(R.id.container1,home);
            ft.addToBackStack("");
            ft.commit();
            fm.executePendingTransactions();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (FragmentChapter.GiaotiepActivity) getActivity();
    }

}
