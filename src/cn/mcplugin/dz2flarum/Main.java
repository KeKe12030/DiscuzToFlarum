package cn.mcplugin.dz2flarum;


import java.sql.SQLException;
public class Main {
	
	public static void main(String[] args) throws SQLException {
		
		/*
		 * ʳ�ý̳̣�https://blog.mcplugin.cn/p/549
		 * 
		 * 
		 * */
		
		System.out.println("��ӭʹ�� Discuz2Flarum��DZ2FL�� ��̳����ת��ϵͳ");
		System.out.println("��ϵͳ��@VioletTec ��������������GitHub��Դ��");
		System.out.println("GITHUB��Դ��ַ��https://github.com/KeKe12030/DiscuzToFlarum");
		System.out.println("ʳ�ý̵̳�ַ��https://blog.mcplugin.cn/p/549");
		System.out.println("���ڳ����������ķ��������ݿ⣬�뱣֤�����ļ���������");
		System.out.println("�뱣֤���ݿ��������������ͨ��ͨ����");
		System.out.println("������������������������������������������������������������������������");
		System.out.println("��������Discuz��̳���ݿ�...............................");
		FlarumSQLUtil.conSql();//discuz���ݿ⽨������
		System.out.println("Discuz��̳���ݿ����ӳɹ���");
		System.out.println("������������������������������������������������������������������������");
		System.out.println("��������Flarum��̳���ݿ�..............................");
		DiscuzSQLUtil.conSql();//flarum���ݿ⽨������
		System.out.println("Flarum��̳���ݿ����ӳɹ���");
		
		
		long start = System.currentTimeMillis();
		System.out.println("======================");
		System.out.println("����ת���û�...............");
		FlarumSQLUtil.transformationUser();//ת���û�
		System.out.println("======================");
		System.out.println("����ת������..............");
		FlarumSQLUtil.transformationThread();//ת������
		System.out.println("======================");
		System.out.println("����ת�ظ�..............");
		FlarumSQLUtil.transformationReply();//ת���ظ�
		System.out.println("======================");
		System.out.println("���ڸ���������Ϣ..............");
		FlarumSQLUtil.transformationThreadInfo();//����������Ϣ
		
		
		
		
		System.out.println("*****************************");
		System.out.println("�� ��ϲ������̳ת�������������������ʲô���⣬��ӭ��GITHUB�ύISSUES");
		System.out.println("GITHUB��Դ��ַ��https://github.com/KeKe12030/DiscuzToFlarum");
		System.out.println("ʳ�ý̵̳�ַ��https://blog.mcplugin.cn/p/549");
		System.out.println("�ܺ�ʱ��"+(System.currentTimeMillis()-start)/1000+"s");
		System.out.println("*****************************");
		
		
		
		
		DiscuzSQLUtil.conSql();//discuz���ݿ�ر�����
		FlarumSQLUtil.conStop();//flarum���ݿ�ر�����
	}
}
