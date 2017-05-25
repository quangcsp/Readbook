package com.example.quangnguyen.readbook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by quangnguyen on 21/03/2017.
 */

public class FragmentHome extends Fragment {
    private TextView txtv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);

        String fileName = getArguments().getString("keyWord");

        FileOperations fop = new FileOperations();
        String text = fop.read(fileName);
        txtv = (TextView) view.findViewById(R.id.filecon);
        txtv.setText(text);
//            String textToHighlight = getArguments().getString("keyWord");
//            String replacedWith = "<font color='red'>" + textToHighlight + "</font>";;
//
//            String originalString = txtv.getText().toString();
//            String modifiedString = originalString.replaceAll(textToHighlight,replacedWith);
//            txtv.setText(Html.fromHtml(modifiedString));

        return view;
    }

}