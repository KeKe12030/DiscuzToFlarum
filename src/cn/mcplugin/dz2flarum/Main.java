package cn.mcplugin.dz2flarum;


import java.sql.SQLException;
public class Main {
	
	public static void main(String[] args) throws SQLException {
		
		/*
		 * 食用教程：https://blog.mcplugin.cn/p/549
		 * 
		 * 
		 * */
		
		System.out.println("欢迎使用 Discuz2Flarum（DZ2FL） 论坛数据转换系统");
		System.out.println("本系统由@VioletTec 开发制作，已在GitHub开源！");
		System.out.println("GITHUB开源地址：https://github.com/KeKe12030/DiscuzToFlarum");
		System.out.println("食用教程地址：https://blog.mcplugin.cn/p/549");
		System.out.println("正在尝试链接您的服务器数据库，请保证配置文件配置无误！");
		System.out.println("请保证数据库可以外链，保障通信通畅！");
		System.out.println("————————————————————————————————————");
		System.out.println("正在连接Discuz论坛数据库...............................");
		FlarumSQLUtil.conSql();//discuz数据库建立连接
		System.out.println("Discuz论坛数据库连接成功！");
		System.out.println("————————————————————————————————————");
		System.out.println("正在连接Flarum论坛数据库..............................");
		DiscuzSQLUtil.conSql();//flarum数据库建立连接
		System.out.println("Flarum论坛数据库连接成功！");
		
		
		long start = System.currentTimeMillis();
		System.out.println("======================");
		System.out.println("正在转换用户...............");
		FlarumSQLUtil.transformationUser();//转换用户
		System.out.println("======================");
		System.out.println("正在转换帖子..............");
		FlarumSQLUtil.transformationThread();//转换帖子
		System.out.println("======================");
		System.out.println("正在转回复..............");
		FlarumSQLUtil.transformationReply();//转换回复
		System.out.println("======================");
		System.out.println("正在更新帖子信息..............");
		FlarumSQLUtil.transformationThreadInfo();//更新帖子信息
		
		
		
		
		System.out.println("*****************************");
		System.out.println("√ 恭喜您，论坛转换结束，如果终于遇到什么问题，欢迎到GITHUB提交ISSUES");
		System.out.println("GITHUB开源地址：https://github.com/KeKe12030/DiscuzToFlarum");
		System.out.println("食用教程地址：https://blog.mcplugin.cn/p/549");
		System.out.println("总耗时："+(System.currentTimeMillis()-start)/1000+"s");
		System.out.println("*****************************");
		
		
		
		
		DiscuzSQLUtil.conSql();//discuz数据库关闭连接
		FlarumSQLUtil.conStop();//flarum数据库关闭连接
	}
}
