package cn.mcplugin.dz2flarum;


import java.sql.SQLException;
public class Main {
	//flarum�а���id
	public static int[] ids = new int[] {1,3};
	
	
	public static void main(String[] args) throws SQLException {
		//FlarumSQLUtil mgs = new FlarumSQLUtil();
		System.out.println("��ӭʹ�� Discuz2Flarum ��̳����ת��ϵͳ");
		System.out.println("��ϵͳ��@VioletTec ��������������GitHub��Դ��");
		System.out.println("���ڳ����������ķ��������ݿ⣬�뱣֤�����ļ���������");
		System.out.println("�뱣֤���ݿ��������������ͨ��ͨ����");
		System.out.println("������������������������������������������������������������������������");
		System.out.println("��������Discuz��̳���ݿ�...............................");
		FlarumSQLUtil.conSql();//discuz���ݿ⽨������
		System.out.println("Discuz��̳���ݿ����ӳɹ���");
		System.out.println();
		System.out.println();
		System.out.println("������������������������������������������������������������������������");
		System.out.println("��������Flarum��̳���ݿ�..............................");
		DiscuzSQLUtil.conSql();//flarum���ݿ⽨������
		System.out.println("Flarum��̳���ݿ����ӳɹ���");
		//System.out.println(new MotdServer(0).getAddr());//����һ��������MotdServer���Ƿ�����
		//System.out.println( new java.sql.Date(1531208575*1000L));
		int fid = 51;
		System.out.println("======================");
		System.out.println("����ת���û�...............");
		FlarumSQLUtil.transformationUser();//ת���û�
		System.out.println("======================");
		System.out.println("����ת������..............");
		FlarumSQLUtil.transformationThread(fid);//ת������
		System.out.println("======================");
		System.out.println("����ת�ظ�..............");
		FlarumSQLUtil.transformationReply(fid);//ת���ظ�
		System.out.println("======================");
		System.out.println("���ڸ���������Ϣ..............");
		FlarumSQLUtil.transformationThreadInfo(fid);//����������Ϣ
		DiscuzSQLUtil.conSql();//discuz���ݿ�ر�����
		FlarumSQLUtil.conStop();//flarum���ݿ�ر�����
	}
}
