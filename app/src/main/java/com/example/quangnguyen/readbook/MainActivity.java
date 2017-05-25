package com.example.quangnguyen.readbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements FragmentChapter.GiaotiepActivity{
//    private Button btnChap1;
//    private Button btnChap2;
//    private Button btnChap3;
//    private Button btnChap4;
//
//    private TextView txChap1;
//    private TextView txChap2;
//    private TextView txChap3;
//    private TextView txChap4;

    private TextView txTheme;
    private ImageView imgSearch;
    private ImageView imageHome;
    private ImageView imgCheckWord;

//    public static final String FileInput = "asdf";
    public static final String HOME = "Home";
    public static final String SEARCH = "Search";
    public static final String CHECK = "Check";
    public final static String ACTION_UPDATE = "actionUpdate";
    public static String file =null;

    public ArrayList<String> nameFile = new ArrayList<String>();

    public char testContent[];

    private BroadcastReceiver updateReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameFile.add("asdf");
        nameFile.add("testfile");
        nameFile.add("chuong3");
        nameFile.add("chuong4");

        imgSearch = (ImageView)findViewById(R.id.imgSearch);
        imageHome = (ImageView)findViewById(R.id.imagehome);
        imgCheckWord = (ImageView)findViewById(R.id.imgcheckWord);
        txTheme = (TextView)findViewById(R.id.txtheme);

        imageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        FragmentChapter read = new FragmentChapter();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft  = fm.beginTransaction();
        ft.add(R.id.container1 , read , HOME).commit();
        fm.executePendingTransactions();
        Log.d("BACKSTACK:" , "" +getSupportFragmentManager().getBackStackEntryCount());


        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("keyword",nameFile);

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft  = fm.beginTransaction();
                FragmentSearchAllChapter fgsearch = (FragmentSearchAllChapter) fm.findFragmentByTag(SEARCH);

                if(fgsearch == null || !fgsearch.isVisible()) {
                    while (fm.getBackStackEntryCount() > 1){
                        fm.popBackStackImmediate();
                    }
                    fgsearch = new FragmentSearchAllChapter();
                    fgsearch.setArguments(bundle);
                    ft.replace(R.id.container1, fgsearch, SEARCH);
                    if(fm.getBackStackEntryCount() == 0) {
                        ft.addToBackStack(HOME);
                    }
                    ft.commit();
                    fm.executePendingTransactions();
                }
                Log.d("BACKSTACK", "" + fm.getBackStackEntryCount());
            }
        });

        imgCheckWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> check = new ArrayList<String>();

                if(file != null)
                {
                    check.addAll(nameFile);
                    check.add(file);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("keyword",check);

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft  = fm.beginTransaction();
                    FragmentCheckContent fgCheck = (FragmentCheckContent) fm.findFragmentByTag(CHECK);

                    if(fgCheck == null || !fgCheck.isVisible()) {
                        while (fm.getBackStackEntryCount() > 1){
                            fm.popBackStackImmediate();
                        }
                        fgCheck = new FragmentCheckContent();
                        fgCheck.setArguments(bundle);
                        ft.replace(R.id.container1, fgCheck, CHECK);
                        if(fm.getBackStackEntryCount() == 0) {
                            ft.addToBackStack("");
                        }
                        ft.commit();
                        fm.executePendingTransactions();
                    }
                    Log.d("BACKSTACK", "" + fm.getBackStackEntryCount());

                }else Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();

            }
        });

//        fnameread = (EditText)findViewById(R.id.fnameread);
//        read = (Button)findViewById(R.id.btnread);

//        Bundle truyen = new Bundle();
//        truyen.putString("data", "fdfgfdgd");
//
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft  = fm.beginTransaction();
//        FragmentRead home = new FragmentRead();
//        ft.add(R.id.container, home, HomeTag).commit();
//
//        fm.executePendingTransactions();
//        Log.d("BACKSTACK:" , "" +getSupportFragmentManager().getBackStackEntryCount());

//        read.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                String readfilename = fnameread.getText().toString();
//                FileOperations fop = new FileOperations();
//                String text = fop.read(readfilename);
//                content = text;
//                if(text == null){
//                    Toast.makeText(getApplicationContext(), "File not Found", Toast.LENGTH_SHORT).show();
//                }
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                FragmentRead showContent = new FragmentRead();
//
//                    Bundle truyen1 = new Bundle();
//                    truyen1.putString("data", "sfgsfsdf");
//                    Toast.makeText(getApplicationContext(), "File not Found", Toast.LENGTH_SHORT).show();
//                    transaction.replace(R.id.container,showContent,Show);
//                    showContent.setArguments(truyen1);
//                    transaction.commit();
//                    manager.executePendingTransactions();
//                Log.d("BACKSTRACK", "" +manager.getBackStackEntryCount());
//
//            }
//        });

//        read.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String readfilename = fnameread.getText().toString();
//                FileOperations fop = new FileOperations();
//                String text = fop.read(readfilename);
//
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                FragmentRead showContent = (FragmentRead) manager.findFragmentByTag(Show);
//
//                if (showContent == null || !showContent.isVisible())
//                {
//                    while (manager.getBackStackEntryCount() > 1)
//                    {
//                        manager.popBackStackImmediate();
//                    }
//                    showContent = new FragmentRead();
//                    Bundle truyen = new Bundle();
//                    truyen.putString("data", "dfgdfgdfgdfg");
//                    showContent.setArguments(truyen);
//                    transaction.replace(R.id.container,showContent,Show);
//                    if(manager.getBackStackEntryCount() == 0)
//                    {
//                        transaction.addToBackStack(HomeTag);
//                    }
//                    transaction.commit();
//                    manager.executePendingTransactions();
//                }
//                Log.d("BACKSTRACK", "" +manager.getBackStackEntryCount());
//            }
//
//        });
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 0) {
            finish();
        }
        if (fm.getBackStackEntryCount() == 1) {
            imageHome.setImageResource(R.drawable.home_select);
        }
        fm.beginTransaction().remove(fm.findFragmentById(R.id.container1)).commit();
        fm.popBackStackImmediate();
    }

    @Override
    public  void thaydoi(String chuoi){
        file = chuoi;
        txTheme.setText(chuoi);
//        Toast.makeText(MainActivity.this, file, Toast.LENGTH_SHORT).show();
    }
}
