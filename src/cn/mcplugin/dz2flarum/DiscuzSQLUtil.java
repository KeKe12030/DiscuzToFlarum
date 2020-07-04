package cn.mcplugin.dz2flarum;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.Vector;

public class DiscuzSQLUtil {
	public static Connection discuzCon = null;
	public static String discuzStartName = null;
	public static String ucAddress = null;
	static {
		discuzStartName = getConfig("discuzStartName");
		ucAddress  = getConfig("ucserverAddress");
	}
	public DiscuzSQLUtil(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	/**
	 * �������ݿ�����
	 * */
	public static void conSql() {
		try {
			if(discuzCon!=null) {
				if(!discuzCon.isClosed()) {
					discuzCon.close();
				}
			}
			discuzCon = DriverManager.getConnection(getConfig("discuzAddress"),getConfig("discuzUserName"),getConfig("discuzUserPass"));
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	public static void conStop() {
		try {
			discuzCon.close();
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	private static String getConfig(String paramName) {//����������Ҫ��ȡ������ǰ׺
		ResourceBundle resource = ResourceBundle.getBundle("sql");//config.properties ��src
		String value = "";
		try {
			value = resource.getString(paramName);  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
	/**
	 * ��ȡ���������TID
	 * */
	public static Vector<Integer> getAllThreadTids(int fid) {
		Vector<Integer> pids = new Vector<Integer>();
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT distinct `tid` FROM `"+discuzStartName+"forum_thread` WHERE fid="+fid+" AND `displayorder`>-1;");
			while(rs.next()) {
				pids.add(rs.getInt(1));
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return pids;
	}
	/**
	 * ��ȡ�����û�UID
	 * */
	public static Vector<Integer> getAllUids(){
		Vector<Integer> uids = new Vector<Integer>();
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `uid` FROM `"+discuzStartName+"ucenter_members`");
			while(rs.next()) {
				uids.add(rs.getInt(1));
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return uids;
	}
	/**
	 * ͨ��TID��ȡ���һ�εĻظ����û�UID
	 * */
	public static int getLastReplyUidByTid(int tid) {
		Statement s = null;
		ResultSet rs = null;
		int maxPid = getLastReplyPidByTid(tid);
		int lastPostUserId = 0;
		try {
			s=discuzCon.createStatement();
			rs = s.executeQuery("SELECT `authorid` FROM `"+discuzStartName+"forum_post` WHERE pid="+maxPid);
			while(rs.next()) {
				lastPostUserId=rs.getInt(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return lastPostUserId;
	}
	/**
	 * ͨ��TID��ȡ���һ�λظ���PID
	 * */
	public static int getLastReplyPidByTid(int tid) {
		Statement s = null;
		ResultSet rs = null;
		int maxPid = 0;
		try {
			s=discuzCon.createStatement();
			rs = s.executeQuery("SELECT MAX(`pid`) FROM `"+discuzStartName+"forum_post` WHERE tid="+tid);
			while(rs.next()) {
				maxPid=rs.getInt(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return maxPid;
	}
	/**
	 * ͨ��UID��ȡע��ʱ���
	 * */
	public static Timestamp getRegisterTimeByUid(int uid) {
		Long registerTime = 0L;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `regdate` FROM `"+discuzStartName+"ucenter_members` WHERE uid="+uid);
			while(rs.next()) {
				registerTime = rs.getLong(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return new Timestamp(registerTime*10000);//ת��Ϊ����
	}
	/**
	 * ͨ��TID��ȡ���һ�εĻظ���ʱ��
	 * */
	public static Timestamp getLastReplyTimeByTid(int tid) {
		Statement s = null;
		ResultSet rs = null;
		int maxPid = getLastReplyPidByTid(tid);
		Long lastReplyTime = 0L;
		try {
			s=discuzCon.createStatement();
			rs = s.executeQuery("SELECT `dateline` FROM `"+discuzStartName+"forum_post` WHERE pid="+maxPid);
			while(rs.next()) {
				lastReplyTime=rs.getLong(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return new Timestamp(lastReplyTime*1000);
	}
	/**
	 * ��ȡ���лظ���PID
	 * */
	public static Vector<Integer> getAllReplyPids(int fid) {
		Vector<Integer> pids = new Vector<Integer>();
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `pid` FROM `"+discuzStartName+"forum_post` WHERE fid="+fid+" AND `position`>1;");
			while(rs.next()) {
				pids.add(rs.getInt(1));
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return pids;
	}
	/**
	 * ͨ���ظ���PID��ȡ����ظ����û�UID
	 * */
	public static int getAuthorIdByPid(int pid) {
		int uid = 0;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `authorid` FROM `"+discuzStartName+"forum_post` WHERE pid="+pid+";");
			while(rs.next()) {
				uid=rs.getInt(1);//��ȡ���ظ���PID����Ӧ��Ŀ��TID
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return uid;
	}
	/**
	 * ͨ���ظ���PID��ȡ�ظ���λ��position
	 * */
	public static int getReplyPositionByReplyPid(int pid) {
		int position=0;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `position` FROM `"+discuzStartName+"forum_post` WHERE `position`>1 AND pid="+pid+";");
			while(rs.next()) {
				position=rs.getInt(1);//��ȡ���ظ���PID����Ӧ��Ŀ��TID
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return position;
	}
	/**
	 * ͨ��PID��ȡ����
	 * */
	public static String getMessageByPid(int pid) {
		Statement s = null;
		ResultSet rs = null;
		String message = "";
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `message` FROM `"+discuzStartName+"forum_post` WHERE pid="+pid);
			while(rs.next()) {
				message = rs.getString(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return TrainUtil.bbcodeToHtml(message);
	}
	/**
	 * ͨ���ظ���pid��ȡ�ظ���Ӧ��tid
	 * */
	public static int getTargetTidByReplyPid(int pid) {
		Statement s = null;
		ResultSet rs = null;
		int targetTid = 0;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT distinct `tid` FROM `"+discuzStartName+"forum_post` WHERE `position`>1 AND pid="+pid+";");
			while(rs.next()) {
				targetTid=rs.getInt(1);//��ȡ���ظ���PID����Ӧ��Ŀ��TID
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return targetTid;
	}
	/**
	 * ͨ��PID��������TID
	 * */
	public static int getPidByTid(int pid) {
		Statement s = null;
		ResultSet rs = null;
		int tid = 0;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `pid` FROM `"+discuzStartName+"forum_post` WHERE tid="+pid);
			while(rs.next()) {
				tid = rs.getInt(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return tid;
	}
	/**
	 * ͨ��TID�������ӵı���(subject)
	 * */
	public static String getTitleByTid(int tid) {
		Statement s = null;
		ResultSet rs = null;
		String title = "";
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `subject` FROM `"+discuzStartName+"forum_thread` WHERE tid="+tid);
			while(rs.next()) {
				title = rs.getString(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return title;
	}
	/**
	 * ͨ��PID��ȡ��������
	 * */
	public static String getMessageByTid(int tid) {
		Statement s = null;
		ResultSet rs = null;
		String message = "";
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `message` FROM `"+discuzStartName+"forum_post` WHERE tid="+tid+" AND position=1");
			while(rs.next()) {
				message = rs.getString(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return TrainUtil.bbcodeToHtml(message);
	}
	/**
	 * ͨ��PID��ȡ����ʱ�䣬����ת����Flarum�ʺϵ�ʱ���ʽ
	 * */
	public static Date getPostTimeByPid(int pid) {
		Statement s = null;
		ResultSet rs = null;
		Long timestmap = 0L;

		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `dateline` FROM `"+discuzStartName+"forum_post` WHERE pid="+pid+";");
			while(rs.next()) {
				timestmap = rs.getLong(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return new Date(timestmap*10000L);
	}
	/**
	 * ͨ��TID��ȡ����ʱ�䣬����ת����Flarum�ʺϵ�ʱ���ʽ
	 * */
	public static Timestamp getPostTimeByTid(int tid) {
		Statement s = null;
		ResultSet rs = null;
		Long timestmap = 0L;
		Timestamp postTime = null;

		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `dateline` FROM `"+discuzStartName+"forum_thread` WHERE tid="+tid+";");
			while(rs.next()) {
				timestmap = (Long)rs.getObject(1);
				postTime = new Timestamp(timestmap*1000);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return postTime;
	}
	/**
	 * ͨ��TID��ȡ����ID  ��ȡ���ӵ�����
	 * */
	public static int getAuthorIdByTid(int tid) {
		int uid = 0;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `authorid` FROM `"+discuzStartName+"forum_post` WHERE `position`=1 AND tid="+tid+";");
			while(rs.next()) {
				uid=rs.getInt(1);//��ȡ���ظ���PID����Ӧ��Ŀ��TID
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return uid;
	}
	/**
	 * ͨ��attachID��ȡ������������
	 * */	
	public static String getDownloadByAttachId(int aid) {
		Statement s = null;
		ResultSet rs = null;
		String download = "";
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `attachment` FROM `"+discuzStartName+"forum_attachment_0`,`"+discuzStartName+"forum_attachment_1`,`"+discuzStartName+"forum_attachment_2`,`"+discuzStartName+"forum_attachment_3`,`"+discuzStartName+"forum_attachment_4`,`"+discuzStartName+"forum_attachment_5`,`"+discuzStartName+"forum_attachment_6`,`"+discuzStartName+"forum_attachment_7` WHERE aid="+aid+";");
			while(rs.next()) {
				download = rs.getString(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return download;
	}
	/**
	 * ͨ��TID��ȡ���ӵ�FID(���ڰ��ID)����ת��ΪFL��TAGID
	 * */
	public static int[] getIdByFid(int fid) {
		return Main.ids;
	}
	/**
	 * ͨ��TID��ȡ����һ���м����ظ�
	 * */
	public static int getReplyNumByTid(int tid) {
		int replyNum = 0;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT COUNT(*) FROM `"+discuzStartName+"forum_post` WHERE tid="+tid+";");
			while(rs.next()) {
				replyNum=rs.getInt(1);//��ȡ���ظ���PID����Ӧ��Ŀ��TID
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return replyNum;
	}
	

	/**
	 * ͨ��UID��ȡEMAIL
	 * */
	public static String getEmailByUid(int uid) {
		String email ="";
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `email` FROM `"+discuzStartName+"ucenter_members` WHERE uid="+uid);
			while(rs.next()) {
				email = rs.getString(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return email;
	}
	/**
	 * ͨ��UID�ж��û��Ƿ���ͷ��
	 * */
	public static boolean hasAvagarByUid(int uid) {
		Statement s = null;
		ResultSet rs = null;
		boolean has = false;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `avatarstatus` FROM `"+discuzStartName+"common_member` WHERE uid="+uid);
			while(rs.next()) {
				if(rs.getInt(1)!=0) {
					has = true;
				}
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return has;
	}
	/**
	 * ͨ��TID��ȡ���ӵ�������
	 * */
	public static int getViewNumByTid(int tid) {
		Statement s = null;
		ResultSet rs = null;
		int num = 0;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `views` FROM `"+discuzStartName+"forum_thread` WHERE tid="+tid);
			while(rs.next()) {
				num = rs.getInt(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return num;
	}
	/**
	 * ͨ��UID��ȡ�ǳ�
	 * */
	public static String getNameByUid(int uid) {
		String name ="";
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `username` FROM `"+discuzStartName+"ucenter_members` WHERE uid="+uid);
			while(rs.next()) {
				name = rs.getString(1);
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return name;
	}
	/**
	 * ͨ��TID������Ӳ���������
	 * */
	public static int getParticipantByTid(int tid) {
		int participant = 0;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT DISTINCT `authorid` FROM `"+discuzStartName+"forum_post` WHERE tid="+tid+";");
			while(rs.next()) {
				participant=rs.getRow();//��ȡ�ж����У�Ҳ�����ж��ٸ��û������˱���
			}
			//��ÿ���������ʾ��TID
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		return participant;
	}
}
