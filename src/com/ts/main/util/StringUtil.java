package com.ts.main.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @(#)StringHelper.java         12:13:58
 *
 * Copyright (c) 2004 Chenwa, Inc.
 * All rights reserved.
 */
/**
 * 字符串的帮助类
 *
 * @author tgp
 * @version 1.0.0beta
 */
public class StringUtil {
    
    private static Pattern p = Pattern.compile("^\\d+[.]?\\d+$");
    public final static String BEGIN = "${";
    public final static String END = "}";
    public static boolean isDoulbe(String str){
    	if(!StringUtil.isNoValue(str)){
    		boolean isNum=str.matches("^(\\-?)(\\d+)(.{0,1})(\\d+)(E{1}(\\-?|\\+?)\\d+)$");
    		if(isNum==false)
    		return str.matches("^(\\-?)(\\d+)(E{1}\\-?\\d+)$");   
    		else
    			return isNum;  	
    	}else
    		return false;
    }
    public static boolean isNoValue(Integer value) {
        return (value == null) || (value.intValue()<= 0);
    }
    /**
     * 字符串是否为空或是空格组成
     *
     * @param str 要检验的字符串
     *
     * @return 是否为空或空格组成<br> true-----为空或空格组成
     */
    public static boolean isNull(String str) {
        if ((str == null) || (str.trim().length() == 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param str DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean isNumber(CharSequence str) {
        if (str == null) {
            return false;
        }

        Matcher m = p.matcher(str);

        return m.find();
    }
    
    /**
     * 字符串是否由数字组成
     * 
     * @param str 要检验的字符串
     * 
     * @return 如果是由数字组成则返回true,否则返回false
     */
    public static boolean isNumberic(CharSequence str) {
    	if(str==null)
    		return false;
    	else{
    		Pattern pattern=Pattern.compile("[0-9]*"); 
    		return pattern.matcher(str).matches();
    	}
    }
    public static boolean existArray(Object[] sourceValues,Object destinationValue){
	      for (int i=0;i<sourceValues.length;i++){
	         if (sourceValues[i].equals(destinationValue))
	            return true;
	      }
	      return false;
	   }
    /**
     * 把一个字符串转化成整型数字
     *
     * @param str 要转化的字符串
     *
     * @return 转化后的数字
     */
    public static int toInt(String str) {
        if (str == null) {
            return -1;
        }

        try {
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return 0;
        }
    }

    /**
     * convert the string to boolean.<br>
     * null, space, 0, false(doesn't different upper or lower)
     *
     * @param str the source string
     *
     * @return true.if not null,space,0,false.
     */
    public static boolean toBoolean(String str) {
        if ((str == null) || (str.trim().length() == 0)) {
            return false;
        }

        String s = str.trim().toUpperCase();

        if ("0".equals(s) || "FALSE".equals(s)) {
            return false;
        }

        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param str DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static char toChar(String str) {
        return toChar(str, ' ');
    }

    /**
     * DOCUMENT ME!
     *
     * @param str DOCUMENT ME!
     * @param def DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static char toChar(String str, char def) {
        try {
            if ((str == null) || (str.trim().length() == 0)) {
                return def;
            }

            return str.trim().toCharArray()[0];
        } catch (Exception e) {
            return def;
        }
    }

    /**
     * <p>
     * 把形如this is ${0} is now!分析成this is , ${0}, is now!类型的字符串.
     * </p>
     * 
     * <p>
     * 分解出代码式中的${开头和}号接尾的字符串.
     * </p>
     * 
     * <p>
     * 如果key为true,则只取${和}号之间的数据.全部KEY均转换为大写
     * </p>
     *
     * @param pattern
     *
     * @return
     *
     * @throws Exception
     */
    public static List parse(String pattern) throws Exception {
    	if ( pattern == null ) return null;
        List lt = new ArrayList();

        if (pattern != null) {
            int begin = pattern.indexOf(BEGIN);

            if (begin != -1) {
                int end = pattern.indexOf(END, begin);

                if (end != -1) { // 是完整的数据
                    lt.add(pattern.substring(0, begin));
                    lt.add(pattern.substring(begin, end + 1)); // 作为KEY

                    String more = pattern.substring(end + 1);

                    if (more.trim().length() > 0) {
                        lt.addAll( parse(more) );
                    }
                } else {
                    throw new Exception("格式错误");
                }
            } else {
                lt.add(pattern);
            }
        }

        return lt;
    }

    /**
     * DOCUMENT ME!
     *
     * @param pattern DOCUMENT ME!
     * @param text DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Map parse(List pattern, String text) {
    	if ( pattern == null || text == null ) return null;
        Map map = null;
        int index = 0;

        for (int i = 0; i < pattern.size() && text.length() > 0; i++) {
            String p = (String) pattern.get(i);
            if (p.startsWith("${")) { // 取出有用的数据            	
                if (i == (pattern.size() - 1)) { // 最后一个数据
                	if ( map == null && index < 0 ) { // 没有一个符合的
                		break;
                	} else if ( map == null ){
                		map = new HashMap();
                	}
                    map.put(p, text);
                } else {
                    String p2 = (String) pattern.get(i + 1);
                    index = text.indexOf(p2); // 下一个数据的开头

                    if (index > 0) { // 如果下一个数据的开头数据存在,采集这一段的数据.并放到相应KEY的散列表里
                        String value = text.substring(0, text.indexOf(p2));
                        if ( map == null ) map = new HashMap();
                        map.put(p, value);
                        text = text.substring(index); // 把已经分析的数据去掉.
                    }
                }
            } else { // 除掉没有用的
                if (p.length() < text.length()) { // 把分析过的数据去掉
                	if ( (index = text.indexOf(p)) != -1 ) {
                		text = text.substring(index + p.length());
                	}
                }
            }
        }

        return map;
    }
    
    /**
     * 截取text中的一部分,字符串begin和end结尾.
     * @param text 原始字符串
     * @param begin 开始的字符串
     * @param end 结束的字符串
     * @return
     */
    public static String substring(String text, String sBegin, String sEnd) {
    	if ( text  == null ) return null;
    	if ( sBegin != null && sBegin.trim().length() != 0 ) {
    		int index = text.indexOf(sBegin);
    		if ( index != -1 ) {
    			text = text.substring(index + sBegin.length() );
    		} else {
    			return null;
    		}
    	}
    	if ( sEnd != null && sEnd.trim().length() != 0 ) {
    		int index = text.indexOf(sEnd);
    		if ( index != -1 ) {
    			text = text.substring(0, index);
    		}  else {
    			return null;
    		}
    	}
    	return text;    	
    }
    
    
    /**
     * <p>把一个字符串按指定的开始和结束分成多个符合条件的字符串.</p>
     * <p>例text="aaTTTbbsdfsdfdsfaaBBBbbokokok".
     * 	输入的sBegin="aa";sEnd="bb".
     * 那么些得到的结果将是"TTT","BBB"这两个字符串的数组
     * </p>
     * @param text 需要分析的截取的字符串
     * @param sBegin 开始部分的字符串
     * @param sEnd 结束部分的字符串
     * @return 分析结束的字符串
     */
    public static String[] split(String text, String sBegin, String sEnd) {
    	if ( text == null || sBegin == null || sEnd == null ) return null;
    	List lt = new ArrayList();
    	int index;
    	while ((index = text.indexOf(sBegin)) != -1 ) {   // 如果还有未分析部分,则继续执行
    		text = text.substring(index + sBegin.length());
    		index = text.indexOf(sEnd);
    		if ( index == -1 ) break; // 如果没有结尾,则排除
    		lt.add(text.substring(0, index)); // 把分解结果放到数组中
    		text = text.substring(index + sEnd.length()); 
    	}
    	if ( lt.size() == 0 ) return null;
    	String[] sr = new String[lt.size()];
    	sr = (String[])lt.toArray(sr);
    	return sr;
    }
	
	public static void main(String[] args) {
		String text = "th${A}this${B}${C}HEHE";
		String sBegin = "${";
		String sEnd = "}";
		String[] splits =  split(text, sBegin, sEnd);
		for ( int i = 0; i < splits.length; i++ ) {
			System.out.println(">> " +splits[i]);
		}
	}
    /**
     * <p>按照指定的模式转换源文档.</p>
     * <p>例pattern={"aaa", "${a}", "bbb","${b}};</p>
     * <p>source = {"${a}"="AAAA"};</p>
     * <p>def="DEF";</p>
     * <p>返回的结果将是:"aaaAAAAbbbDEF"
     * @param pattern 模板
     * @param source 数据源
     * @param def 默认值,源为空时的默认值
     *
     * @return 转换后的结果
     */
    public static String trans(List pattern, Map source, String def) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < pattern.size(); i++) {
            String p = (String) pattern.get(i);

            if (p.startsWith("${")) {
                String value = (source == null) ? def : (String) source.get(p);
                value = (value == null) ? def : value.trim();
                sb.append(value);
            } else {
                sb.append(p);
            }
        }

        return sb.toString();
    }
    
    /**
     * <p>把指定的内容指一格式转换成新的格式.</p>
     * <p><pre>例如:toPattern="aaa${key}bbb${key2}ccc";
     * fromPatern="111${key}000";
     * source="111SSSSSSSS000";
     * def = "DDDDDD";
     * 返回的结果将是"aaaSSSSSSSSbbbDDDDDDccc"
     * @param toPattern 转换后的模板
     * @param fromPattern 源模板
     * @param source 源文档
     * @param def 默认值
     * @return 转换后的结果
     * @throws Exception
     */
    public static String trans(String toPattern, String fromPattern, String source, String def) throws Exception {
    	if ( toPattern == null || fromPattern == null || source == null ) return null;
    	List lt = parse(fromPattern);
    	Map sor = parse(lt, source);
    	List to = parse(toPattern);
    	return trans(to, sor, def);
    }

    /**
     * convert the null to ""
     *
     * @param str the source string
     *
     * @return the result string
     */
    public static String toString(String str) {
        if (str == null) {
            return "";
        }

        return str;
    }

    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password Password or other credentials to use in authenticating
     *        this username
     * @param algorithm Algorithm used to do the digest
     *
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encodedPassword.length; i++) {
            if (((int) encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
        }

        return buf.toString();
    }

    /**
     * Encode a string using Base64 encoding. Used when storing passwords as
     * cookies. This is weak encoding in that anyone can use the decodeString
     * routine to reverse the encoding.
     *
     * @param str
     *
     * @return String
     *
     * @throws IOException
     */
    public static String encodeString(String str) throws IOException {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        String encodedStr = encoder.encodeBuffer(str.getBytes());

        return (encodedStr.trim());
    }

    /**
     * Decode a string using Base64 encoding.
     *
     * @param str
     *
     * @return String
     *
     * @throws IOException
     */
    public static String decodeString(String str) throws IOException {
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        String value = new String(dec.decodeBuffer(str));

        return (value);
    }

    /**
     * <p>
     * 判定一个字符串对象是否是null或全部由空格组成的字符串
     * </p>
     * 
     * <p>
     * .
     * </p>
     * 
     * <p>
     * 如果是null或由空格组成的字符串,则返回true
     * </p>
     * 
     * <p></p>
     * 
     * <p>
     * 否则返回false
     * </p>
     *
     * @param value 要判定的值
     *
     * @return 判定结果
     */
    public static boolean isNoValue(String value) {
        return (value == null) || (value.trim().length() == 0);
    }
    public static boolean isNoValue(Long value) {
        return (value == null) || (value.intValue() <= 0);
    }
    /**
     * DOCUMENT ME!
     *
     * @param value
     * @param defaultInt
     *
     * @return
     */
    public static int toInt(String value, int defaultInt) {
        return (int) toDouble(value, (double) defaultInt);
    }

    /**
     * DOCUMENT ME!
     *
     * @param value
     * @param defaultDouble
     *
     * @return
     */
    private static double toDouble(String value, double defaultDouble) {
        if (isNoValue(value)) {
            return defaultDouble;
        }

        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return defaultDouble;
        }
    }
    
	public static boolean contains(String s, String text, String delimiter) {
	    if ( (s == null) || (text == null) || (delimiter == null)) {
	      return false;
	    }
	
	    if (!s.endsWith(delimiter)) {
	      s += delimiter;
	    }
	
	    int pos = s.indexOf(delimiter + text + delimiter);
	
	    if (pos == -1) {
	      if (s.startsWith(text + delimiter)) {
	        return true;
	      }
	
	      return false;
	    }
	
	    return true;
	  }

  public static int count(String s, String text) {
    if ( (s == null) || (text == null)) {
      return 0;
    }

    int count = 0;

    int pos = s.indexOf(text);

    while (pos != -1) {
      pos = s.indexOf(text, pos + text.length());
      count++;
    }

    return count;
  }

  public static String merge(String array[], String delimiter) {
    if (array == null) {
      return null;
    }

    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < array.length; i++) {
      sb.append(array[i].trim());

      if ( (i + 1) != array.length) {
        sb.append(delimiter);
      }
    }

    return sb.toString();
  }

  public static String read(ClassLoader classLoader, String name) throws
      IOException {

    return read(classLoader.getResourceAsStream(name));
  }

  public static String read(InputStream is) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    StringBuffer sb = new StringBuffer();
    String line = null;

    while ( (line = br.readLine()) != null) {
      sb.append(line).append('\n');
    }

    br.close();

    return sb.toString().trim();
  }


  public static String replace(String s, String oldSub, String newSub) {
    if ( (s == null) || (oldSub == null) || (newSub == null)) {
      return null;
    }

    int y = s.indexOf(oldSub);

    if (y >= 0) {
      StringBuffer sb = new StringBuffer();
      int length = oldSub.length();
      int x = 0;

      while (x <= y) {
        sb.append(s.substring(x, y));
        sb.append(newSub);
        x = y + length;
        y = s.indexOf(oldSub, x);
      }

      sb.append(s.substring(x));

      return sb.toString();
    } else {
      return s;
    }
  }

  public static String replace(String s, String[] oldSubs, String[] newSubs) {
    if ( (s == null) || (oldSubs == null) || (newSubs == null)) {
      return null;
    }

    if (oldSubs.length != newSubs.length) {
      return s;
    }

    for (int i = 0; i < oldSubs.length; i++) {
      s = replace(s, oldSubs[i], newSubs[i]);
    }

    return s;
  }

  public static String reverse(String s) {
    if (s == null) {
      return null;
    }

    char[] c = s.toCharArray();
    char[] reverse = new char[c.length];

    for (int i = 0; i < c.length; i++) {
      reverse[i] = c[c.length - i - 1];
    }

    return new String(reverse);
  }

  public static String shorten(String s) {
    return shorten(s, 20);
  }

  public static String shorten(String s, int length) {
    return shorten(s, length, "..");
  }

  public static String shorten(String s, String suffix) {
    return shorten(s, 20, suffix);
  }

  public static String shorten(String s, int length, String suffix) {
    if (s == null || suffix == null) {
      return null;
    }

    if (s.length() > length) {
      s = s.substring(0, length) + suffix;
    }

    return s;
  }

  public static String[] split(String s, String delimiter) {
    if (s == null || delimiter == null) {
      return new String[0];
    }

    if (!s.endsWith(delimiter)) {
      s += delimiter;
    }

    s = s.trim();

    if (s.equals(delimiter)) {
      return new String[0];
    }

    List nodeValues = new ArrayList();

    if (delimiter.equals("\n") || delimiter.equals("\r")) {
      try {
        BufferedReader br = new BufferedReader(new StringReader(s));

        String line = null;

        while ( (line = br.readLine()) != null) {
          nodeValues.add(line);
        }

        br.close();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    } else {
      int offset = 0;
      int pos = s.indexOf(delimiter, offset);

      while (pos != -1) {
        nodeValues.add(s.substring(offset, pos));

        offset = pos + delimiter.length();
        pos = s.indexOf(delimiter, offset);
      }
    }

    return (String[]) nodeValues.toArray(new String[0]);
  }

  public static boolean[] split(String s, String delimiter, boolean x) {
    String[] array = split(s, delimiter);
    boolean[] newArray = new boolean[array.length];

    for (int i = 0; i < array.length; i++) {
      boolean value = x;

      try {
        value = Boolean.valueOf(array[i]).booleanValue();
      } catch (Exception e) {
      }

      newArray[i] = value;
    }

    return newArray;
  }

  public static double[] split(String s, String delimiter, double x) {
    String[] array = split(s, delimiter);
    double[] newArray = new double[array.length];

    for (int i = 0; i < array.length; i++) {
      double value = x;

      try {
        value = Double.parseDouble(array[i]);
      } catch (Exception e) {
      }

      newArray[i] = value;
    }

    return newArray;
  }

  public static float[] split(String s, String delimiter, float x) {
    String[] array = split(s, delimiter);
    float[] newArray = new float[array.length];

    for (int i = 0; i < array.length; i++) {
      float value = x;

      try {
        value = Float.parseFloat(array[i]);
      } catch (Exception e) {
      }

      newArray[i] = value;
    }

    return newArray;
  }

  public static int[] split(String s, String delimiter, int x) {
    String[] array = split(s, delimiter);
    int[] newArray = new int[array.length];

    for (int i = 0; i < array.length; i++) {
      int value = x;

      try {
        value = Integer.parseInt(array[i]);
      } catch (Exception e) {
      }

      newArray[i] = value;
    }

    return newArray;
  }

  public static long[] split(String s, String delimiter, long x) {
    String[] array = split(s, delimiter);
    long[] newArray = new long[array.length];

    for (int i = 0; i < array.length; i++) {
      long value = x;

      try {
        value = Long.parseLong(array[i]);
      } catch (Exception e) {
      }

      newArray[i] = value;
    }

    return newArray;
  }

  public static short[] split(String s, String delimiter, short x) {
    String[] array = split(s, delimiter);
    short[] newArray = new short[array.length];

    for (int i = 0; i < array.length; i++) {
      short value = x;

      try {
        value = Short.parseShort(array[i]);
      } catch (Exception e) {
      }

      newArray[i] = value;
    }

    return newArray;
  }

  public static final String stackTrace(Throwable t) {
    String s = null;

    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      t.printStackTrace(new PrintWriter(baos, true));
      s = baos.toString();
    } catch (Exception e) {
    }

    return s;
  }

  public static boolean startsWith(String s, char begin) {
    return startsWith(s, (new Character(begin)).toString());
  }

  public static boolean startsWith(String s, String begin) {
    if ( (s == null) || (begin == null)) {
      return false;
    }

    if (begin.length() > s.length()) {
      return false;
    }

    String temp = s.substring(0, begin.length());

    if (temp.equalsIgnoreCase(begin)) {
      return true;
    } else {
      return false;
    }
  }

  public static String wrap(String text) {
    return wrap(text, 80);
  }

  public static String wrap(String text, int width) {
    if (text == null) {
      return null;
    }

    StringBuffer sb = new StringBuffer();

    try {
      BufferedReader br = new BufferedReader(new StringReader(text));

      String s = "";

      while ( (s = br.readLine()) != null) {
        if (s.length() == 0) {
          sb.append("\n");
        } else {
          while (true) {
            int pos = s.lastIndexOf(' ', width);

            if ( (pos == -1) && (s.length() > width)) {
              sb.append(s.substring(0, width));
              sb.append("\n");

              s = s.substring(width, s.length()).trim();
            } else if ( (pos != -1) && (s.length() > width)) {
              sb.append(s.substring(0, pos));
              sb.append("\n");

              s = s.substring(pos, s.length()).trim();
            } else {
              sb.append(s);
              sb.append("\n");

              break;
            }
          }
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

    return sb.toString();
  }
      
  public static String getPassword(int length, String key) {

    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < length; i++) {
      sb.append(key.charAt( (int) (Math.random() * key.length())));
    }

    return sb.toString();
  }

  public static String getPassword(int length) {
    String key =
        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    return getPassword(length, key);
  }

  /**
   * 判断单词是否为用引号（'或")引起来的字符串
   * @function:
   * @param lexer
   * @return
   */
  public static boolean isQuoteLexer(String lexer){
     if (lexer==null || lexer.trim().equals("")) return false;
     if (lexer.charAt(0)=='\'' && lexer.charAt(lexer.length()-1)=='\'')
        return true;
     if (lexer.charAt(0)=='\"' && lexer.charAt(lexer.length()-1)=='\"')
        return true;
     return false;
  }	 
  public static String upperCaseFirst(String name)  {  
      return name.substring(0, 1).toUpperCase() + name.substring(1);  
  }
  
  public static String listMapToStrByKey(List<Map> list, String key) {
	  String result = "";
	  List<String> tempList = new ArrayList<String>();
	  for (Map tempMap : list) {
		  if (tempMap.containsKey(key)) {
			  tempList.add(tempMap.get(key).toString());
		  } 
	  }
	  result = org.apache.commons.lang3.StringUtils.join(tempList.toArray(),",");
	  if (result != null && "".equals(result)) {
		  result = "0";
	  }
	  return result;
  }

	public static List getListFromListMapByKey(List<Map> list, String key) {
		List tempList = new ArrayList();
		for (Map tempMap : list) {
			if (tempMap.containsKey(key)) {
				tempList.add(tempMap.get(key).toString());
			}
		}
		return tempList;
	}

	public static List removeDuplicate(List list) {
		List tempList = new ArrayList();
		HashSet h = new HashSet(list);
		tempList.addAll(h);
		return tempList;
	}
	
	/**
	 * map转化为bean
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;  
        Object obj = beanClass.newInstance();  
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
        return obj;  
    }    
	
	/**
	 * bean转化为map
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Map<?, ?> objectToMap(Object obj) {  
        if(obj == null)  
            return null;   
        return new org.apache.commons.beanutils.BeanMap(obj);  
    }    
	
	
    	   
}

