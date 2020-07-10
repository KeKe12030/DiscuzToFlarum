package cn.mcplugin.dz2flarum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TrainUtil {
	public static HashMap<Integer,ArrayList<Integer>> discuzIdToFlarumId = null;
	public static HashMap<String,String> replaceStrs = null;
    static {
    	try {
    		discuzIdToFlarumId = new HashMap<Integer,ArrayList<Integer>>();
			BufferedReader bf = new BufferedReader(new InputStreamReader(
			Thread.currentThread().getContextClassLoader().getResourceAsStream("ids.yml")));
			ArrayList<Integer> flIds = null;
			String str = null;
			String[] strs = null;
			String[] flIdsArr = null;
			while((str=bf.readLine())!=null) {
				if(str.startsWith("#")) {
					continue;
				}
				strs = str.split("=");
				flIdsArr = strs[1].split(",");
	    		flIds = new ArrayList<Integer>();
				for(int i=0;i<flIdsArr.length;i++) {
					flIds.add(Integer.valueOf(flIdsArr[i]));
				}
				discuzIdToFlarumId.put(Integer.valueOf(strs[0]),flIds);
			}
			bf.close();
			
			/*
			 * replace的配置文件 
			 * 
			 * */
			
			replaceStrs = new HashMap<String,String>();
			BufferedReader bf1 = new BufferedReader(new InputStreamReader(
			Thread.currentThread().getContextClassLoader().getResourceAsStream("replace.yml")));
			String str1 = null;
			String[] strs1 = null;
			while((str1=bf1.readLine())!=null) {
				if(str1.startsWith("#")) {
					continue;
				}
				strs1 = str1.split("=");
				replaceStrs.put(strs1[0],strs1[1]);
				str1 = bf1.readLine();
			}
			bf1.close();
			
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    	
    }
    public static String bbcodeToHtml(String text) {
     			
         String temp = text;
         Map<String , String> bbMap = new HashMap<String , String>();
         
         bbMap.put("\\[br\\]", "</br>");
         bbMap.put("\\[b\\](.+?)\\[/b\\]", "<b>$1</b>");
         bbMap.put("\\[s\\](.+?)\\[/s\\]", "<s>$1</s>");
         bbMap.put("\\[i\\](.+?)\\[/i\\]",  "<i>$1</i>");
         bbMap.put("\\[i\\=(.+?)\\](.+?)\\[/i\\]",  "<i>$2</i>");
         bbMap.put("\\[u\\](.+?)\\[/u\\]",  "<u>$1</u>");
         bbMap.put("\\[h1\\](.+?)\\[/h1\\]", "<h1>$1</h1>");
         bbMap.put("\\[h2\\](.+?)\\[/h2\\]", "<h2>$1</h2>");
         bbMap.put("\\[h3\\](.+?)\\[/h3\\]", "<h3>$1</h3>");
         bbMap.put("\\[h4\\](.+?)\\[/h4\\]", "<h4>$1</h4>");
         bbMap.put("\\[h5\\](.+?)\\[/h5\\]", "<h5>$1</h5>");
         bbMap.put("\\[h6\\](.+?)\\[/h6\\]", "<h6>$1</h6>");
         bbMap.put("\\[code\\](.+?)\\[/code\\]", "<pre>$1</pre>");
         bbMap.put("\\[quote\\](.+?)\\[/quote\\]", "<pre>$1</pre>");
         bbMap.put("\\[quote\\=(.+?)\\](.+?)\\[/quote\\]","<pre>$1 write : </br>$2</pre>");
         bbMap.put("\\[ifra\\=(.+?)\\](.+?)\\[/ifra\\]", "<a href=\"$2\">$1</a>");
         bbMap.put("\\[font\\=(.+?)\\](.+?)\\[/font\\]", "<span style=\"font-family:$1;\">$2</span>");
         bbMap.put("\\[background\\=(.+?)\\](.+?)\\[/background\\]", "<span style=\"background:http://py.52qdg.com/$1\">$2</span>");
         bbMap.put("\\[center\\](.+?)\\[/center\\]", "<div align=\"center\">$1</div>");
         bbMap.put("\\[left\\](.+?)\\[/left\\]", "<p style=\"display:block; text-align:left\">$1</p>");
         bbMap.put("\\[right\\](.+?)\\[/right\\]", "<p style=\"display:block; text-align:right\">$1</p>");
         bbMap.put("\\[center\\](.+?)\\[/center\\]", "<p style=\"display:block; text-align:center;\">$1</p>");
         bbMap.put("\\[rtl\\](.+?)\\[/rtl\\]", "<div style=\"display:block; dir:rtl\">$1</div>");
         bbMap.put("\\[ltr\\](.+?)\\[/ltr\\]", "<div style=\"display:block; dir:ltr\">$1</div>");
         bbMap.put("\\[align\\=(.+?)\\](.+?)\\[/align\\]", "<div align=\"$1\">$2</div>");
         bbMap.put("\\[color\\=(.+?)\\](.+?)\\[/color\\]", "<span style=\"color:$1\">$2</span>");
         bbMap.put("\\[size\\=([0-9]+?)\\](.+?)\\[/size\\]", "<span style=\"font-size:16px;\">$2</span>");
         bbMap.put("\\[hr\\]", "<hr />");

         bbMap.put("\\[img\\](.+?)\\[/img\\]", "<img src=\"$1\" />");
         bbMap.put("\\[img\\=(.+?),(.+?)\\](.+?)\\[/img\\]", "<img width=\"$1\" height=\"$2\" src=\"$3\" />");
         bbMap.put("\\[img\\]static(.+?)\\[/img\\]", "<img src=\"http://py.52qdg.com/static$1\" />");
         bbMap.put("\\[email\\](.+?)\\[/email\\]", "<a href=\"mailto:$1\">$1</a>");
         bbMap.put("\\[email\\=(.+?)\\](.+?)\\[/email\\]", "<a href=\"mailto:$1\">$2</a>");
         bbMap.put("\\[url\\](.+?)\\[/url\\]", "<a href=\"$1\">$1</a>");
         bbMap.put("\\[url\\=(.+?)\\](.+?)\\[/url\\]", "<a href=\"$1\">$2</a>");
         bbMap.put("\\[video\\](.+?)\\[/video\\]", "<video src=\"$1\" />");
         bbMap.put("\\[groupid\\=(.+?)\\](.+?)\\[/groupid\\]", "<pre>$2</pre>");
         bbMap.put("\\[postbg\\](.+?)\\[/postbg\\]", "");
         bbMap.put("\\[attach\\](.+?)\\[/attach\\]", "{attach}$1{/attach}");

       
         temp = temp.replaceAll("\\n", "</br>");
         
         for (Map.Entry entry: bbMap.entrySet()) {
             temp = temp.replaceAll((String)entry.getKey(), entry.getValue().toString());
         }
         
         temp = temp.replaceAll("\\[list\\]\\[\\*\\](.+?)\\[/list\\]","<ul><li>$1</li></ul>");
         
         temp = temp.replaceAll("\\[list\\=([0-9]+?)\\]\\[\\*\\](.+?)\\[/list\\]","<ol style=\"list-style-type: decimal\"><li>$2</li></ol>");
         
         temp = temp.replaceAll("\\[list\\=(i)\\]\\[\\*\\](.+?)\\[/list\\]","<ol style=\"list-style-type: lower-roman\"><li>$2</li></ol>");
         temp = temp.replaceAll("\\[list\\=(I)\\]\\[\\*\\](.+?)\\[/list\\]","<ol style=\"list-style-type: upper-roman\"><li>$2</li></ol>");
         temp = temp.replaceAll("\\[list\\=([a-z]+?)\\]\\[\\*\\](.+?)\\[/list\\]","<ol style=\"list-style-type: lower-alpha\"><li>$2</li></ol>");
         temp = temp.replaceAll("\\[list\\=([A-Z]+?)\\]\\[\\*\\](.+?)\\[/list\\]","<ol style=\"list-style-type: upper-alpha\"><li>$2</li></ol>");
 
         
         temp = temp.replaceAll("\\[list\\](.+?)\\[\\*\\](.+?)\\[/list\\]","<ul><li>$2</li></ul>");
         temp = temp.replaceAll("\\[list\\=([0-9]+?)\\](.+?)\\[\\*\\](.+?)\\[/list\\]","<ol style=\"list-style-type: decimal\"><li>$3</li></ol>");
         temp = temp.replaceAll("\\[list\\=(i)\\](.+?)\\[\\*\\](.+?)\\[/list\\]","<ol style=\"list-style-type: lower-roman\"><li>$3</li></ol>");
         temp = temp.replaceAll("\\[list\\=(I)\\](.+?)\\[\\*\\](.+?)\\[/list\\]","<ol style=\"list-style-type: upper-roman\"><li>$3</li></ol>");
         temp = temp.replaceAll("\\[list\\=([a-z]+?)\\](.+?)\\[\\*\\](.+?)\\[/list\\]","<ol style=\"list-style-type: lower-alpha\"><li>$3</li></ol>");
         temp = temp.replaceAll("\\[list\\=([A-Z]+?)\\](.+?)\\[\\*\\](.+?)\\[/list\\]","<ol style=\"list-style-type: upper-alpha\"><li>$3</li></ol>");
         temp = temp.replaceAll("\\[\\*\\](.+?)","</li><li>$1");
         temp = "<t>"+temp+"</t>";
         temp = temp.replaceAll("\\[(.+?)\\]", "");
         temp = temp.replaceAll("color:#ffffff", "color:#000000");
         
         
         if(DiscuzSQLUtil.replaceStr) {//如果替换str
        	 
        	 //对configs.xml进行操作
        	 for(int i=0;i<replaceStrs.size();i++) {
        		 Set<String> olds = replaceStrs.keySet();
        		 Iterator<String> it = olds.iterator();
        		 while(it.hasNext()) {
        			 String old = it.next();
        			 temp = temp.replaceAll(old,replaceStrs.get(old));
        		 }
        	 }
        	 
         }
         
//         temp = temp.replaceAll("https://i.mcplugin.cn","http://image.52qdg.com");
     	return temp;
        
   		}
   	   
    public static ArrayList<Integer> discuzIdToFlarumId(int dzId) {
    	return discuzIdToFlarumId.get(dzId);
    }
    
    public static void main(String[] args) {
		System.out.println(replaceStrs);
	}
}