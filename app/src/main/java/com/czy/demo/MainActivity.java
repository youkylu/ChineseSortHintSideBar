package com.czy.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Window;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements SideBar.OnChooseLetterChangedListener {

    private List<User> userList;

    private UserAdapter adapter;

    private LinearLayoutManager manager;

    private SearchView mSearchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setIconifiedByDefault(false);

        HintSideBar hintSideBar = (HintSideBar) findViewById(R.id.hintSideBar);
        RecyclerView rv_userList = (RecyclerView) findViewById(R.id.rv_userList);
        hintSideBar.setOnChooseLetterChangedListener(this);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_userList.setLayoutManager(manager);
        userList = new ArrayList<>();
        adapter = new UserAdapter(this);
        initData();
        adapter.setData(userList);
        rv_userList.setAdapter(adapter);
    }

    @Override
    public void onChooseLetter(String s) {
        int i = adapter.getFirstPositionByChar(s.charAt(0));
        if (i == -1) {
            return;
        }
        manager.scrollToPositionWithOffset(i, 0);
    }

    @Override
    public void onNoChooseLetter() {

    }

    public void initData() {
        ArrayList<String>  users = new ArrayList<String>();

        users.add("Am");
        users.add("gm");
        users.add("赵");
        users.add("蔡");
        users.add("吴");
        users.add("asdb");
        users.add("李巴");
        users.add("刘秀");
        users.add("李小龙");


        String[] arrays = new String[] { "gyu", "sdf", "zf", "大同", "收到",
                "地方", "三等分", "的人", "反对高铁", "泛代数", "上的投入", "和国家" ,"古时候","古代","2","李巴", "刘秀","李小龙" };
        for (int i = 0; i < arrays.length; i++) {
            String str = arrays[i];
            if (str.length() == 0)
                return;
            String alphabet = str.substring(0, 1);
            /*判断首字符是否为中文，如果是中文便将首字符拼音的首字母和&符号加在字符串前面*/
            if (alphabet.matches("[\\u4e00-\\u9fa5]+")) {
                str = getAlphabet(str) + "&" + str;
                arrays[i] = str;
            }else{
                str = alphabet + "&" + str;
                arrays[i] = str;
            }

        }
        /*设置排序语言环境*/
        Comparator<Object> com = Collator.getInstance(Locale.CHINESE);
        Collections.sort(users, com);
        Arrays.sort(arrays, com);
        /*遍历数组，去除标识符&及首字母*/
        for (int i=0;i<arrays.length;i++) {
            String str=arrays[i];
            if(str.contains("&")&&str.indexOf("&")==1){
                arrays[i]=str.split("&")[1];
            }

            User user101 = new User();
            user101.setUserName(arrays[i]);
            user101.setHeadLetter(Utils.getHeadChar(arrays[i]));
            userList.add(user101);
//            System.out.println(arrays[i]);
        }



    }




    public static String getAlphabet(String str) {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部小写
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String pinyin = null;
        try {
            pinyin = (String) PinyinHelper.toHanyuPinyinStringArray(str.charAt(0),
                    defaultFormat)[0];
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return pinyin.substring(0, 1);
    }


}
