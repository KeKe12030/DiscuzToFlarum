package cn.mcplugin.dz2flarum;

import java.util.HashMap;
import java.util.Map;

public class TrainUtil {

    
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
         temp = temp.replaceAll("https://i.mcplugin.cn","http://image.52qdg.com");
     	return temp;
        
   		}
   	    
    public static void main(String[] args) {
		String message = "[postbg]3.jpg[/postbg][size=4][b][color=#000000]\r\n" + 
				"[/color][/b][/size][size=4][b][color=#000000]有哪个男孩不想拥有一个MC爆款服务器呢？[/color]\r\n" + 
				"[/b][/size][size=4][b][color=#000000]这是你的服务器在线人数[/color][color=#a0522d]0/99999[/color][color=#000000]，这是隔壁哈皮咳嗽的在线玩家数[/color][color=#a0522d]391940/829840910[/color][/b][/size]\r\n" + 
				"[font=&quot;][size=4][color=#000000][b]一周前，你新开了一个服务器，一周后你的服务器倒闭了。[/b][/color][/size][/font]\r\n" + 
				"[font=&quot;][size=4][color=#696969][b]（走错片场了，（逃    ）[/b][/color][/size][/font]\r\n" + 
				"[b]\r\n" + 
				"[img]static/image/hrline/line2.png[/img]\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"[size=5]什么是Minecraft服务器？[/size][/b][align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=4]要想打造一款爆款MC服务器，首先要了解什么是Minecraft服务器。\r\n" + 
				"[/size][/font][/color][/align][align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=4]Minecraft服务器可以跨越空间和时间的限制，和小伙伴们一同联机游玩的一个游戏服务器。[/size][/font][/color][/align][align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=4]Minecraft服务器分为PC（Personal Computer）端和BE端（Bedrock Edition）[/size][/font][/color][/align][align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=4]我们首先来介绍PC端服务器[/size][/font][/color][/align][align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=3]\r\n" + 
				"[/size][/font][/color][/align][align=left][align=center][attachimg]3012[/attachimg][/align][/align]\r\n" + 
				"[hr][b][size=3]\r\n" + 
				"[/size][/b][b][size=5]PC服务器有哪些不同种类的核心？[/size][/b]\r\n" + 
				"[align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=4][b]PC版服务器核心[/b][/size][/font][/color][/align][align=left][img]static/image/hrline/line2.png[/img]\r\n" + 
				"[/align]\r\n" + 
				"[align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=3][b]PC端的服务器核心有很多种，不过在国内比较常见的有：[/b]\r\n" + 
				"[/size][/font][/color][/align][list]\r\n" + 
				"[*][align=left][spoiler]\r\n" + 
				"[list]\r\n" + 
				"[*][align=left][size=3][b]SpigotMC[/b][/size][/align]\r\n" + 
				"[/list][quote]Spigot是CraftBukkit服务端之后的延续版本，比CraftBukkit 优化了不少地方，支持 CraftBukkit的插件，性能比 CraftBukkit 好很多，并且自带反作弊功能[/quote]\r\n" + 
				"[list]\r\n" + 
				"[*][align=left][size=3][b]BungeeCord[/b][/size][/align]\r\n" + 
				"[/list][quote]BungeeCord 是一个高性能的反向代理服务端，它可以将多个 Minecraft 服务器变成一个 “群组服务器”[/quote]\r\n" + 
				"[list]\r\n" + 
				"[*][align=left][size=3][b]Mohist[/b][/size][/align]\r\n" + 
				"[/list][quote]\r\n" + 
				"Mohist 是一个全新的 Minecraft Forge 服务端，基于 1.12.2 下，核心采用 Forge + Paper 结构，开发环境使用 ForgeGradle，支持 Forge mod 和 Paper 系列插件。Mohist 目前稳定性良好，仍在不断更新，后续还会支持 1.14。支持插件和mod[/quote]\r\n" + 
				"[list]\r\n" + 
				"[*][align=left][size=3][b]Catserver[/b][/size][/align]\r\n" + 
				"[/list][quote]该核心是一款支持高版本MOD和插件的核心，也是国内第一个引领高版本mod和插件兼容的核心，目前只有在catserver官方群下载，支持插件和mod[/quote]\r\n" + 
				"[list]\r\n" + 
				"[*][align=left][size=3][b]Sponge[/b][/size][/align]\r\n" + 
				"[/list][quote]顾名思义这个就是大家所说的海绵端，高版本mod服可用Sponge开服，只是不支持bukkit插件，只能用sponge的插件（大佬用的）还要懂得如何优化，如果不会优化cpu倒着走。[/quote]\r\n" + 
				"[list]\r\n" + 
				"[*][align=left][size=3][b]Cauldron[/b][/size][/align]\r\n" + 
				"[/list][quote]KCauldron/Cauldron是低版本(只支持1.6-1.7.10)的MOD核心，支持bukkit插件\r\n" + 
				"Cauldron 官方由于 DMCA 版权纠纷已停止更新，建议使用 KCauldron 或 Thermos、Contigo 等后续维护版本，更稳定。[/quote]\r\n" + 
				"[align=left][color=rgb(51, 51, 51)]（转载自 Minecraft技术网  [/color][url=http://www.i5mc.cc][size=4]http://www.i5mc.cc [/size][/url][color=rgb(51, 51, 51)]）[/color][/align][/spoiler][/align][/list][align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=3]\r\n" + 
				"[/size][/font][/color][/align][hr][b][size=3]\r\n" + 
				"[/size][/b]\r\n" + 
				"[b][size=5]在哪里可以下载到这些核心？[/size][/b]\r\n" + 
				"[b][size=5]\r\n" + 
				"[/size][/b]\r\n" + 
				"[color=#0100][font=-apple-system-font][size=3][b]1. [/b]Sakura's Mirror 樱花镜像站，是国内优秀的服务端下载镜像站！[/size][/font][/color]\r\n" + 
				"[align=left][size=4][b][url=https://mirror.tcotp.cn/]https://mirror.tcotp.cn/[/url][/b][/size][/align][align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=3]\r\n" + 
				"[/size][/font][/color][/align][align=center][attachimg]3013[/attachimg][/align][align=center]\r\n" + 
				"[img]static/image/hrline/line2.png[/img]\r\n" + 
				"[/align]\r\n" + 
				"\r\n" + 
				"[align=center][hr][/align]\r\n" + 
				"[b][size=5]下载之后如何开服？[/size][/b]\r\n" + 
				"[b][size=5]\r\n" + 
				"[/size][/b]\r\n" + 
				"[align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=3]文章正在编辑中，很快就会发布出来，关注我，第一时间查看消息！[/size][/font][/color][/align][align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=3]\r\n" + 
				"[/size][/font][/color][/align][align=left][color=rgb(51, 51, 51)][font=-apple-system-font, &quot;][size=3]\r\n" + 
				"[/size][/font][/color][/align]\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"";
	}
    
}