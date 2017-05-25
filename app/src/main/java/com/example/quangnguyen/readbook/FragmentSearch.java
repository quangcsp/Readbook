package com.example.quangnguyen.readbook;

import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by quangnguyen on 22/03/2017.
 */

public class FragmentSearch extends Fragment {
    private ListView lvResult;
    private EditText edKeyWord;
    private Button btnSpeak;
    private ArrayAdapter<String> adapter = null;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

    public String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        final String fileName = getArguments().getString("keyWord");

        final FileOperations fop = new FileOperations();
        text = fop.read(fileName);

        lvResult = (ListView) view.findViewById(R.id.lviewresult);
        edKeyWord = (EditText) view.findViewById(R.id.searchText);
        btnSpeak = (Button) view.findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechToText();
            }
        });

        edKeyWord.setFocusableInTouchMode(true);
        edKeyWord.requestFocus();
        edKeyWord.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    final String ed_text = edKeyWord.getText().toString().trim();
                    if (ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null) {
                        // edit text empty, do nothing
                    } else {

                        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1
                                ,removeDuplicateWithOrder(resultReturn(text,ed_text)));
                        lvResult.setAdapter(adapter);

                        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Bundle bundle = new Bundle();
                                String test[] = {fileName,resultReturn(text,ed_text).get(position)};
                                bundle.putStringArray("keyWord",test);
//                                bundle.putString("keyWord", resultReturn(text,ed_text).get(position));

                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                FragmentTransaction ft  = fm.beginTransaction();
                                FragmentRead home = new FragmentRead();
                                home.setArguments(bundle);
//                                home.setArguments(bundle1);
                                ft.replace(R.id.container1,home);
                                ft.addToBackStack(null);
                                ft.commit();
                                fm.executePendingTransactions();

                            }
                        });
                    }

                    return true;
                }
                    return false;
            }
        });


        return view;
    }
//    public void checkVoiceRecognition() {
//        // Check if voice recognition is present
//        PackageManager pm = getActivity().getPackageManager();
//        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
//                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
//        if (activities.size() == 0) {
//            btnSpeak.setEnabled(false);
//            Toast.makeText(getActivity(), "Voice recognizer not present",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void speak(View view) {
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        // Specify the calling package to identify your application
//        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
//                .getPackage().getName());
//
//        // Display an hint to the user about what he should say.
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, edKeyWord.getText()
//                .toString());
//
//        // Given an hint to the recognizer about what the user is going to say
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
//        Toast.makeText(getActivity(), "11111111111111", Toast.LENGTH_SHORT).show();
//
//        // If number of Matches is not selected then return show toast message
//        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Toast.makeText(getActivity(), "222fvdgdfgdfg2", Toast.LENGTH_SHORT).show();
//
//        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)
//            Toast.makeText(getActivity(), "222222222222222222", Toast.LENGTH_SHORT).show();
//
//        //If Voice recognition is successful then it returns RESULT_OK
//        if(resultCode == getActivity().RESULT_OK) {
//
//
//            ArrayList<String> textMatchList = data
//                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            Toast.makeText(getActivity(), "3333333333333333333", Toast.LENGTH_SHORT).show();
//
//            if (!textMatchList.isEmpty()) {
//                // If first Match contains the 'search' word
//                // Then start web search.
//                if (textMatchList.get(0).contains("search")) {
//
//                    String searchQuery = textMatchList.get(0).replace("search",
//                            " ");
//
//                    Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
//                    search.putExtra(SearchManager.QUERY, searchQuery);
//                    startActivity(search);
//                } else {
//                    // populate the Matches
//                    Toast.makeText(getActivity(), "4444444444", Toast.LENGTH_SHORT).show();
//
//                    edKeyWord.setText(textMatchList.get(0)); // hiện thị kết quả thu được khi nói
//                }
//
//            }
//            //Result code for various error.
//        }else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
//            showToastMessage("Audio Error");
//        }else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
//            showToastMessage("Client Error");
//        }else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
//            showToastMessage("Network Error");
//        }else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
//            showToastMessage("No Match");
//        }else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
//            showToastMessage("Server Error");
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//    void showToastMessage(String message){
//        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//    }
//
    public static ArrayList<String> resultReturn(String content, String keyword) //ket qua cua tìm kiếm
    {
        String txcontent = content.toUpperCase();
        String txkeyword = keyword.toUpperCase();

        String[] txwords = txkeyword.split("\\s");
        String[] txwcontent = txcontent.split("\\s");

        String[] words = keyword.split("\\s");
        String[] wcontent = content.split("\\s");
        ArrayList<String> ra = new ArrayList<String>();;
        String output = "";

        System.out.println("11111111");

        for(int i = 0;i < txwcontent.length;i++)
        {
            for(int j = 0; j < txwords.length;j++)
            {
                if(txwcontent[i].equals(txwords[j]))
                {
                    if(i == txwcontent.length - 1){
                        ra.add(output + wcontent[i-1]+" "+ wcontent[i]);
                    }
                    else
                    {
                        ra.add(output + wcontent[i]+" "+ wcontent[i+1]);
                    }
                }
            }
        }
        return ra;
    }
//    public static ArrayList<String> resultReturn(String content, String keyword) //ket qua cua tìm kiếm
//    {
//        String[] words = keyword.split("\\s");
//        String[] wcontent = content.split("\\s");
//        ArrayList<String> ra = new ArrayList<String>();;
//        String output = "";
//
//        System.out.println("11111111");
//
//        for(int i = 0;i < wcontent.length;i++)
//        {
//            for(int j = 0; j < words.length;j++)
//            {
//                if(wcontent[i].equals(words[j]))
//                {
//                    if(i == wcontent.length - 1){
//                        ra.add(output + wcontent[i-1]+" "+ wcontent[i]);
//                    }
//                    else
//                    {
//                        ra.add(output + wcontent[i]+" "+ wcontent[i+1]);
//                    }
//                }
//            }
//        }
//        return ra;
//    }
    //        ============chon khi noi========
    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something...");
            startActivityForResult(intent, 1);
    }
    /**
     * Callback for speech recognition activity
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                if (resultCode == getActivity().RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    edKeyWord.setText(text);
                }
                break;
            }
        }
    }

    public static ArrayList removeDuplicateWithOrder(ArrayList arrList) // xoa các phần tử bj trung
    {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = arrList.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        arrList.clear();
        arrList.addAll(newList);

        return arrList;

    }
}
