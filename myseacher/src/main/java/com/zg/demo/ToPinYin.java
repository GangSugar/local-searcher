package com.zg.demo;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;

public class ToPinYin {
    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
        HanyuPinyinOutputFormat format;
        format = new HanyuPinyinOutputFormat();//默认情况下面是带音调的
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不带音调的
        //format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);

        String[] s1 = PinyinHelper.toHanyuPinyinStringArray('长',format);
        System.out.println(Arrays.toString(s1));

        String s = "我和是一名中国学生,我喜欢学习,学习very good";
        for (char ch : s.toCharArray()){
            String[] r = PinyinHelper.toHanyuPinyinStringArray(ch,format);
            System.out.println(Arrays.toString(r));
        }
    }
}
