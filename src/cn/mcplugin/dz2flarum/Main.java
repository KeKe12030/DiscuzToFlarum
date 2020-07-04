package cn.mcplugin.dz2flarum;


import java.sql.SQLException;
public class Main {
	//flarum中板块的id
	public static int[] ids = new int[] {1,3};
	
	
	public static void main(String[] args) throws SQLException {
		//FlarumSQLUtil mgs = new FlarumSQLUtil();
		System.out.println("欢迎使用 Discuz2Flarum 论坛数据转换系统");
		System.out.println("本系统由@VioletTec 开发制作，已在GitHub开源！");
		System.out.println("正在尝试链接您的服务器数据库，请保证配置文件配置无误！");
		System.out.println("请保证数据库可以外链，保障通信通畅！");
		System.out.println("————————————————————————————————————");
		System.out.println("正在连接Discuz论坛数据库...............................");
		FlarumSQLUtil.conSql();//discuz数据库建立连接
		System.out.println("Discuz论坛数据库连接成功！");
		System.out.println();
		System.out.println();
		System.out.println("————————————————————————————————————");
		System.out.println("正在连接Flarum论坛数据库..............................");
		DiscuzSQLUtil.conSql();//flarum数据库建立连接
		System.out.println("Flarum论坛数据库连接成功！");
		//System.out.println(new MotdServer(0).getAddr());//测试一下新增的MotdServer类是否正常
		//System.out.println( new java.sql.Date(1531208575*1000L));
		int fid = 51;
		System.out.println("======================");
		System.out.println("正在转换用户...............");
		FlarumSQLUtil.transformationUser();//转换用户
		System.out.println("======================");
		System.out.println("正在转换帖子..............");
		FlarumSQLUtil.transformationThread(fid);//转换帖子
		System.out.println("======================");
		System.out.println("正在转回复..............");
		FlarumSQLUtil.transformationReply(fid);//转换回复
		System.out.println("======================");
		System.out.println("正在更新帖子信息..............");
		FlarumSQLUtil.transformationThreadInfo(fid);//更新帖子信息
		DiscuzSQLUtil.conSql();//discuz数据库关闭连接
		FlarumSQLUtil.conStop();//flarum数据库关闭连接
	}
}
