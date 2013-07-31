package com.dbdoc.utils;
/**
 * 
 * @author moonights
 *
 * @date 2011-11-23
 */
public class StringUtils {
	
	
	/** 
    * 把输入字符串的首字母改成大写 
    *  
    * @param str 
    * @return 
    */  
    public static String initcap(String str) {  
        char[] ch = str.toCharArray();  
        if (ch[0] >= 'a' && ch[0] <= 'z') {  
            ch[0] = (char) (ch[0]-32);  
        }  
        return new String(ch);  
    }  
    
    public static void main(String args[]){
    	System.out.println(StringUtils.initcap("sqlName"));
    }
}
