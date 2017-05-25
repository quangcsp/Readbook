package com.example.quangnguyen.readbook;

/**
 * Created by quangnguyen on 18/04/2017.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FragmentCheckContent extends Fragment {
    private ListView lvResultCheck;

    private ArrayAdapter<String> adapter1 = null;

    public final static String characterFirst[] = {"b","c","ch","d","đ","g","gh","h","k","kh","l","m","n"
            ,"ng","ngh","nh","p","ph","q","r","s","t","th","tr","v","x","y","o","a"};
    public final static String characterEnd[]   = {"c","ch","m","n","ng","nh","p","t"};

    public final static String generator[]   = {"a","á","à","ạ","ã","ả","ă","ắ","ằ","ặ","ẵ","ẳ",
            "â","ấ","ầ","ẫ","ậ","ẩ","o","ó","ò","õ","ọ","ỏ","ô","ố","ồ","ộ","ỗ","ổ",
            "ơ","ớ","ờ","ợ","ỡ","ở","i","í","ì","ị","ĩ","ỉ","e","é","è","ẹ","ẽ","ẻ","ê","ế",
            "ề","ệ","ễ","ể","u","ú","ù","ụ","ũ","ủ","ư","ứ","ừ","ự","ữ","ử","y","ý","ỳ","ỵ","ỹ"
            ,"ỷ"};

    public final static String charaterError[]   = {"z","f","w","j"};

    public final static char note[] = {'(',')','"',':',';','!','\t',',','.','?','“','”','\n','-','[',']','1','2','3','4'};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_check_content, container, false);

        ArrayList<String> textResult = getArguments().getStringArrayList("keyword");
        lvResultCheck = (ListView)view.findViewById(R.id.lvCheckContetn);

        String data = textResult.get(textResult.size() - 1);
        FileOperations fop = new FileOperations();
        String text =fop.read(textResult.get(Integer.parseInt(data.substring(data.indexOf("Chương ") +7)) - 1));
//        Toast.makeText(getActivity(), textResult.get(Integer.parseInt(data.substring(data.indexOf("Chương ")+7)) - 1),
//                Toast.LENGTH_SHORT).show();
        adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,
                removeDuplicateWithOrder(checkWord(text)));
        ArrayList<String> test = checkWord(text);
        lvResultCheck.setAdapter(adapter1);

        return view;
    }
    public static ArrayList<String> checkWord(String content1)
    {
        String content = checkContentInput(content1);
        content.trim();
        content = content.replaceAll("\\s+", " ");
        String[] wcontent = content.split("\\s");
        ArrayList<String> ra = new ArrayList<String>();

        for(int i = 0;i< wcontent.length;i++)
        {
            if(wcontent[i].length() > 7 || wcontent[i].length() < 2 ||
                    checkGeneratorCenter(wcontent[i]) ==0)
            {
                ra.add(wcontent[i]+"-"+i);
                if(wcontent[i].equals("ở") || wcontent[i].equals("á") || wcontent[i].equals("ý")
                        || wcontent[i].equals("à") || wcontent[i].equals("À") || wcontent[i].equals("Ở")
                        || wcontent[i].equals("Á") || wcontent[i].equals("Ý") || wcontent[i].equals("y")
                        || wcontent[i].equals("Y") || wcontent[i].equals("Ỷ") || wcontent[i].equals("ỷ")
                        || wcontent[i].equals("ư") || wcontent[i].equals("ó") || wcontent[i].equals("e")
                        || wcontent[i].equals("ồ") )
                    ra.remove(wcontent[i]+"-"+i);
            }
            else {
                for(int j = 0; j < wcontent[i].length();j++)
                {
                    if(wcontent[i].charAt(j) == '@' ||wcontent[i].charAt(j) == '#' || wcontent[i].charAt(j) == '$' ||
                            wcontent[i].charAt(j) == '*' || wcontent[i].charAt(j) == '^'|| wcontent[i].charAt(j) == '%')
                            ra.add(wcontent[i]+"-"+i);
                }
                if(checkCharaterErrorOn(wcontent[i]) == 1) ra.add(wcontent[i]+"-"+i );
                if(wcontent[i].charAt(0) == wcontent[i].charAt(1)) ra.add(wcontent[i]+"-"+i);
                String fristPut = "";
                String fristPut1 = "";
                String fristPut2 = "";
                String endPut = "";
                String endPut1 = "";
                if(wcontent[i].length() >= 3)
                {
                    fristPut2 = fristPut2 + wcontent[i].charAt(0) + wcontent[i].charAt(1) +
                            wcontent[i].charAt(2);
                    fristPut1 = fristPut1 + wcontent[i].charAt(0)+ wcontent[i].charAt(1);
                    fristPut = fristPut + wcontent[i].charAt(0);
                    endPut = endPut + wcontent[i].charAt(wcontent[i].length()-1);
                    endPut1 = endPut1 + wcontent[i].charAt(wcontent[i].length()-2)
                            + wcontent[i].charAt(wcontent[i].length()-1);
                    if(checkFrist(fristPut) == 0 && checkFrist(fristPut1) ==0
                            && checkFrist(fristPut2) == 0 &&
                            checkEnd(endPut) == 0 && checkEnd(endPut1) == 0)
                    {
                        ra.add(wcontent[i]+"-"+i);
                    }
                    if(wcontent[i].equals("LongNữ")) ra.add(wcontent[i]+"-"+i);
                }else
                {
                    if(checkGenerator(wcontent[i].charAt(0)+"")== 0 &&
                            checkGenerator(wcontent[i].charAt(1)+"") ==0)
                    {
                        ra.add(wcontent[i]+"-"+i);
                    }
                }
                if(wcontent[i].length() == 3 &&checkGenerator(wcontent[i].charAt(1)+"") ==0
                        && checkGenerator(wcontent[i].charAt(2)+"")== 0
                        &&checkGenerator(wcontent[i].charAt(0)+"") ==0)
                {
                    ra.add(wcontent[i]+"-"+i);
                }
            }
        }
        return ra;
    }

    public static int checkFrist(String text)
    {
        for(int i = 0;i < characterFirst.length;i++)
        {
            if(text.toUpperCase().equals(characterFirst[i].toUpperCase()))
                return 1;
        }
        return 0;
    }
    public static int checkEnd(String text)
    {
        for(int i = 0;i < characterEnd.length;i++)
        {
            if(text.toUpperCase().equals(characterEnd[i].toUpperCase()))
                return 1;
        }
        return 0;
    }
    public static int checkGenerator(String text)
    {
        for(int i = 0;i < generator.length;i++)
        {
            if(text.toUpperCase().equals(generator[i].toUpperCase()))
                return 1;
        }
        return 0;
    }
    public static int checkGeneratorCenter(String text)
    {
        for(int i = 0;i<text.length();i++)
        {
            if(checkGenerator(text.charAt(i)+"") == 1)
                return 1;
        }
        return 0;
    }
    public static int checkCharaterError(String text)
    {
        for(int i = 0;i<charaterError.length;i++)
        {
            if(text.toUpperCase().equals(charaterError[i].toUpperCase()))
                return 1;
        }
        return 0;
    }
    public static int checkCharaterErrorOn(String text)
    {
        for(int i = 0;i<text.length();i++)
        {
            if(checkCharaterError(text.charAt(i)+"") == 1)
                return 1;
        }
        return 0;
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
    public static String checkContentInput(String content)
    {
        for(int j = 0; j < content.length();j++)
        {
            for(int i = 0;i < note.length;i++)
            {
                if(content.charAt(j) == '\n') content = content.replace('\n',' ');
                else if(content.charAt(j) == '?') content = content.replace('?',' ');
                else if(content.charAt(j) == note[i])
                {
                    content = removeCharAt(content,j);
                    j--;
                    break;
                }
            }
        }
        return content;
    }
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

}
