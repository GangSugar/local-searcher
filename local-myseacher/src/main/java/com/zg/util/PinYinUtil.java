package com.zg.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {
    public static String getPinYin(String name){
        HanyuPinyinOutputFormat format  = new HanyuPinyinOutputFormat();

        format.setVCharType(HanyuPinyinVCharType.WITH_V);//替换符号
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不带声调的
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);//输出小写的


        StringBuilder sb = new StringBuilder();//因为每一个字符都是多音字，用来将拼音拼接成一句话
        for (char ch : name.toLowerCase().toCharArray()){
            try {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch,format);
                if (pinyinArray == null || pinyinArray.length == 0){
                    sb.append(ch);
                    continue;
                }
                sb.append(pinyinArray[0]);
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                //badHanyuPinyinOutputFormatCombination.printStackTrace();
                sb.append(ch);//转拼音不成功，就打印出来它本身
            }
        }
        return sb.toString();
    }

    public static String getPinYinFirst(String name) {
        HanyuPinyinOutputFormat format  = new HanyuPinyinOutputFormat();

        format.setVCharType(HanyuPinyinVCharType.WITH_V);//替换符号
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不带声调的
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);//输出小写的


        StringBuilder sb = new StringBuilder();//因为每一个字符都是多音字，用来将拼音拼接成一句话
        for (char ch : name.toLowerCase().toCharArray()){
            try {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch,format);
                if (pinyinArray == null || pinyinArray.length == 0){
                    sb.append(ch);
                    continue;
                }
                sb.append(pinyinArray[0].charAt(0));//取得到拼音的首字母
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                //badHanyuPinyinOutputFormatCombination.printStackTrace();
                sb.append(ch);//转拼音不成功，就打印出来它本身
            }
        }
        return sb.toString();
    }


}
