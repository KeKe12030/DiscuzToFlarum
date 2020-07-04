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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	/**
	 * 建立数据库链接
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public static void conStop() {
		try {
			discuzCon.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	private static String getConfig(String paramName) {//参数传入需要读取的内容前缀
		ResourceBundle resource = ResourceBundle.getBundle("sql");//config.properties 在src
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
	 * 获取所有主题的TID
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return pids;
	}
	/**
	 * 获取所有用户UID
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return uids;
	}
	/**
	 * 通过TID获取最后一次的回复的用户UID
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return lastPostUserId;
	}
	/**
	 * 通过TID获取最后一次回复的PID
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return maxPid;
	}
	/**
	 * 通过UID获取注册时间戳
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return new Timestamp(registerTime*10000);//转换为毫秒
	}
	/**
	 * 通过TID获取最后一次的回复的时间
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return new Timestamp(lastReplyTime*1000);
	}
	/**
	 * 获取所有回复的PID
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return pids;
	}
	/**
	 * 通过回复的PID获取发表回复的用户UID
	 * */
	public static int getAuthorIdByPid(int pid) {
		int uid = 0;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `authorid` FROM `"+discuzStartName+"forum_post` WHERE pid="+pid+";");
			while(rs.next()) {
				uid=rs.getInt(1);//获取到回复的PID所对应的目标TID
			}
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return uid;
	}
	/**
	 * 通过回复的PID获取回复的位置position
	 * */
	public static int getReplyPositionByReplyPid(int pid) {
		int position=0;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `position` FROM `"+discuzStartName+"forum_post` WHERE `position`>1 AND pid="+pid+";");
			while(rs.next()) {
				position=rs.getInt(1);//获取到回复的PID所对应的目标TID
			}
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return position;
	}
	/**
	 * 通过PID获取内容
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return TrainUtil.bbcodeToHtml(message);
	}
	/**
	 * 通过回复的pid获取回复对应的tid
	 * */
	public static int getTargetTidByReplyPid(int pid) {
		Statement s = null;
		ResultSet rs = null;
		int targetTid = 0;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT distinct `tid` FROM `"+discuzStartName+"forum_post` WHERE `position`>1 AND pid="+pid+";");
			while(rs.next()) {
				targetTid=rs.getInt(1);//获取到回复的PID所对应的目标TID
			}
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return targetTid;
	}
	/**
	 * 通过PID查找帖子TID
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return tid;
	}
	/**
	 * 通过TID查找帖子的标题(subject)
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return title;
	}
	/**
	 * 通过PID获取帖子内容
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return TrainUtil.bbcodeToHtml(message);
	}
	/**
	 * 通过PID获取发表时间，并且转换成Flarum适合的时间格式
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return new Date(timestmap*10000L);
	}
	/**
	 * 通过TID获取发帖时间，并且转换成Flarum适合的时间格式
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return postTime;
	}
	/**
	 * 通过TID获取作者ID  获取帖子的作者
	 * */
	public static int getAuthorIdByTid(int tid) {
		int uid = 0;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT `authorid` FROM `"+discuzStartName+"forum_post` WHERE `position`=1 AND tid="+tid+";");
			while(rs.next()) {
				uid=rs.getInt(1);//获取到回复的PID所对应的目标TID
			}
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return uid;
	}
	/**
	 * 通过attachID获取附件下载链接
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return download;
	}
	/**
	 * 通过TID获取帖子的FID(所在版块ID)并且转换为FL的TAGID
	 * */
	public static int[] getIdByFid(int fid) {
		return Main.ids;
	}
	/**
	 * 通过TID获取帖子一共有几个回复
	 * */
	public static int getReplyNumByTid(int tid) {
		int replyNum = 0;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT COUNT(*) FROM `"+discuzStartName+"forum_post` WHERE tid="+tid+";");
			while(rs.next()) {
				replyNum=rs.getInt(1);//获取到回复的PID所对应的目标TID
			}
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return replyNum;
	}
	

	/**
	 * 通过UID获取EMAIL
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return email;
	}
	/**
	 * 通过UID判断用户是否有头像
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return has;
	}
	/**
	 * 通过TID获取帖子的阅览量
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return num;
	}
	/**
	 * 通过UID获取昵称
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
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return name;
	}
	/**
	 * 通过TID获得帖子参与者人数
	 * */
	public static int getParticipantByTid(int tid) {
		int participant = 0;
		Statement s = null;
		ResultSet rs = null;
		try {
			s = discuzCon.createStatement();
			rs = s.executeQuery("SELECT DISTINCT `authorid` FROM `"+discuzStartName+"forum_post` WHERE tid="+tid+";");
			while(rs.next()) {
				participant=rs.getRow();//获取有多少行，也就是有多少个用户参与了本帖
			}
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return participant;
	}
}
