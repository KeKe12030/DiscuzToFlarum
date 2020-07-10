package cn.mcplugin.dz2flarum;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;

public class FlarumSQLUtil {
	public static Connection flarumCon = null;
	public static String flarumStartName = null;
	public static boolean defaultDiscuzAvatar = false;
	static {
		flarumStartName = getConfig("flarumStartName");//flarum的数据表前缀
		defaultDiscuzAvatar = Boolean.valueOf(getConfig("defaultDiscuzAvatar"));
	}
	public FlarumSQLUtil(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	private static String getConfig(String paramName) {//参数传入需要读取的内容前缀
		ResourceBundle resource = ResourceBundle.getBundle("config");//config.properties 在src
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
	 * 建立数据库链接
	 * */
	public static void conSql() {
		try {
			if(flarumCon!=null) {
				if(!flarumCon.isClosed()) {
					flarumCon.close();
				}
			}
			flarumCon = DriverManager.getConnection(getConfig("flarumAddress"),getConfig("flarumUserName"),getConfig("flarumUserPass"));

		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	/**
	 * 停止数据库连接
	 * */
	public static void conStop() {
		try {
			flarumCon.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}



	/**
	 * 更新帖子信息
	 * */
	public static void updateThreadInfo(int tid,int lastPostPid,int views,int replyNum,Timestamp lastReplyTime,int lastPostUserId,int participant) {
		PreparedStatement s = null;
		try {
			s = flarumCon.prepareStatement("UPDATE `"+flarumStartName+"discussions` SET `last_posted_user_id`="+lastPostUserId+", `last_post_id`="+lastPostPid+" , `last_post_number`="+replyNum+" , `last_posted_at` = ? , `view_count` = "+views+" ,`post_number_index`="+replyNum+",`participant_count`="+participant+",`comment_count`="+replyNum+" WHERE id="+tid);
			s.setTimestamp(1, lastReplyTime);
			s.executeUpdate();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	/**
	 * 插入DZ帖子转换为FL帖子
	 * */
	public static void insertTranThread(int pid,int tid,ArrayList<Integer> ids,String title,String message,Timestamp postTime,int authorid ) {
		PreparedStatement s = null;
		try {
			s = flarumCon.prepareStatement("INSERT INTO `"+flarumStartName+"discussions` (`id`, `title`, `comment_count`, `participant_count`, `post_number_index`, `created_at`, `user_id`, `first_post_id`, `last_posted_at`, `last_posted_user_id`, `last_post_id`, `last_post_number`, `hidden_at`, `hidden_user_id`, `slug`, `is_private`, `is_approved`, `view_count`, `is_locked`, `is_sticky`) VALUES ('"+tid+"', \""+title+"\", '1', '1', '1', ?, '"+authorid+"', '"+pid+"', NULL, NULL, NULL, NULL, NULL, NULL, '', '0', '1', '0', '0', '0')");
			s.setTimestamp(1, postTime);
			s.executeUpdate();
			s.close();
			s = flarumCon.prepareStatement("INSERT INTO `"+flarumStartName+"posts` (`discussion_id`, `number`, `created_at`, `user_id`, `type`, `content`, `edited_at`, `edited_user_id`, `hidden_at`, `hidden_user_id`, `ip_address`, `is_private`, `is_approved`) VALUES ('"+tid+"', '1', ?, '"+authorid+"', 'comment', ?, NULL, NULL, NULL, NULL, NULL, '0', '1')");
			//System.out.println("INSERT INTO `"+flarumStartName+"posts` (`id`, `discussion_id`, `number`, `created_at`, `user_id`, `type`, `content`, `edited_at`, `edited_user_id`, `hidden_at`, `hidden_user_id`, `ip_address`, `is_private`, `is_approved`) VALUES ('"+pid+"', '"+tid+"', NULL, '"+postTime+"', '1', 'comment', '"+message+"', NULL, NULL, NULL, NULL, NULL, '0', '1')");
			//插入帖子内容
			s.setTimestamp(1, postTime);
			s.setString(2, message);
			s.executeUpdate();
			s.close();

			//插入帖子分类discussion_tag
			s = flarumCon.prepareStatement("INSERT INTO `"+flarumStartName+"discussion_tag` (`discussion_id`, `tag_id`) VALUES ('"+tid+"', ?)");
			for(int q=0;q<ids.size();q++) {
				s.setInt(1, ids.get(q));
				s.addBatch();
			}
			s.executeBatch();
			//System.out.println("INSERT INTO `"+flarumStartName+"discussions` (`id`, `title`, `comment_count`, `participant_count`, `post_number_index`, `created_at`, `user_id`, `first_post_id`, `last_posted_at`, `last_posted_user_id`, `last_post_id`, `last_post_number`, `hidden_at`, `hidden_user_id`, `slug`, `is_private`, `is_approved`, `view_count`, `is_locked`, `is_sticky`) VALUES ('"+tid+"', '"+title+"', '1', '1', '1', '"+postTime+"', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '0', '1', '0', '0', '0')");
			//插入标题
			//第二个里的的id是第一个里的discussion_id
			//System.out.println("INSERT INTO `"+flarumStartName+"discussion_tag` (`discussion_id`, `tag_id`) VALUES ('"+tid+"', '"+fid+"')");
			//插入帖子tag标签
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	/**
	 * 插入DZ回复为FL回复
	 * */
	public static void insertTranReply(int pid,int targetTid,int position,Timestamp postTime,int authorid,String message) {
		PreparedStatement s = null;
		try {
			s = flarumCon.prepareStatement("INSERT INTO `"+flarumStartName+"posts` (`discussion_id`, `number`, `created_at`, `user_id`, `type`, `content`, `edited_at`, `edited_user_id`, `hidden_at`, `hidden_user_id`, `ip_address`, `is_private`, `is_approved`) VALUES ('"+targetTid+"', '"+position+"', ?, '"+authorid+"', 'comment', ?, NULL, NULL, NULL, NULL, NULL, '0', '1')");
			//插入帖子内容
			s.setTimestamp(1, postTime);
			s.setString(2, message);
			s.executeUpdate();
			//插入标题
			//第二个里的的id是第一个里的discussion_id
			//插入帖子tag标签
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * 转换用户（无头像）
	 * */
	public static void insertUser(int uid,String name,String email,Timestamp registerTime) {
		PreparedStatement s = null;

		try {
			s = flarumCon.prepareStatement("INSERT INTO `"+flarumStartName+"users` (`id`, `username`, `email`, `is_email_confirmed`, `password`, `bio`, `avatar_url`, `preferences`, `joined_at`, `last_seen_at`, `marked_all_as_read_at`, `read_notifications_at`, `discussion_count`, `comment_count`, `read_flags_at`, `social_buttons`, `suspended_until`) VALUES ('"+uid+"', ?, ?, '0', 'password', NULL, NULL, NULL, ?, ?, NULL, NULL, '0', '0', NULL, NULL, NULL)");
			s.setString(1, name);
			s.setString(2, email);
			s.setTimestamp(3, registerTime);
			s.setTimestamp(4, registerTime);
			s.executeUpdate();
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * 转换用户（头像设置为网络头像）
	 * */
	public static void insertUser(int uid,String name,String email,String avatar,Timestamp registerTime) {
		PreparedStatement s = null;
		try {
			s = flarumCon.prepareStatement("INSERT INTO `"+flarumStartName+"users` (`id`, `username`, `email`, `is_email_confirmed`, `password`, `bio`, `avatar_url`, `preferences`, `joined_at`, `last_seen_at`, `marked_all_as_read_at`, `read_notifications_at`, `discussion_count`, `comment_count`, `read_flags_at`, `social_buttons`, `suspended_until`) VALUES ('"+uid+"', ?, ?, '0', 'password', NULL, ?, NULL, ?, ?, NULL, NULL, '0', '0', NULL, NULL, NULL)");
			s.setString(1, name);
			s.setString(2, email);
			s.setString(3, avatar);
			s.setTimestamp(4, registerTime);
			s.setTimestamp(5, registerTime);
			s.executeUpdate();
			//获得可以正常显示的TID
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally {
			try {
				s.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}


	/**
	 * 转换帖子
	 * */
	public static void transformationThread() {
		Vector<Integer> tids = DiscuzSQLUtil.getAllThreadTids();
		for(int tid : tids) {
			int pid = DiscuzSQLUtil.getPidByTid(tid);
			int fid = DiscuzSQLUtil.getFidByPid(tid);
			if(fid==-1) {
				System.out.println("[错误][WARNNING] 在tid为"+tid+"的帖子转换失败！！");
				continue;
			}
			ArrayList<Integer> ids = DiscuzSQLUtil.getIdByFid(fid);
			if(ids == null || ids.size()<=0) {
	    		System.out.println("[错误][WARNNING] Discuz板块ID为："+fid+"的板块配置错误！\n请检查您的ids.yml配置文件！");
	    		continue;
	    	}
			String title = DiscuzSQLUtil.getTitleByTid(tid);
			String message = DiscuzSQLUtil.getMessageByTid(tid);
			Timestamp postTime = DiscuzSQLUtil.getPostTimeByTid(tid);
			int authorid = DiscuzSQLUtil.getAuthorIdByTid(tid);
			insertTranThread(pid, tid, ids, title, message, postTime, authorid);
			System.out.println("正在转换帖子 ——> tid:"+tid+" 作者uid："+authorid);
		}
	}
	/**
	 * 转换回复
	 * */
	public static void transformationReply() {
		Vector<Integer> pids = DiscuzSQLUtil.getAllReplyPids();
		for(int pid : pids) {
			int targetTid = DiscuzSQLUtil.getTargetTidByReplyPid(pid);
			int position = DiscuzSQLUtil.getReplyPositionByReplyPid(pid);
			Timestamp postTime = DiscuzSQLUtil.getPostTimeByPid(pid);
			String message = DiscuzSQLUtil.getMessageByPid(pid);
			int authorid = DiscuzSQLUtil.getAuthorIdByPid(pid);
			insertTranReply(pid, targetTid, position, postTime, authorid, message);
			System.out.println("正在转换由UID： "+authorid+"发表的pid为："+pid+"的回帖");
		}
	}

	/**
	 * 通过uid获得到头像URL
	 * */
	public static String getAvatarUrlByUid(int uid) {
		String avatar = "";
		String uidStr = String.valueOf(uid);
		char[] uidArr = uidStr.toCharArray();
		int lenth = uidArr.length;
		switch(lenth) {
		case 1:
			avatar = "000/00/00/0"+uidArr[0];
			break;
		case 2:
			avatar = "000/00/00/"+uidArr[0]+uidArr[1];
			break;
		case 3:
			avatar = "000/00/0"+uidArr[0]+"/"+uidArr[1]+uidArr[2];
			break;
		case 4:
			avatar = "000/00/"+uidArr[0]+uidArr[1]+"/"+uidArr[2]+uidArr[3];
			break;
		case 5:
			avatar = "000/0"+uidArr[0]+"/"+uidArr[1]+uidArr[2]+"/"+uidArr[3]+uidArr[4];
			break;
		case 6:
			avatar = "000/"+uidArr[0]+uidArr[1]+"/"+uidArr[2]+uidArr[3]+"/"+uidArr[4]+uidArr[5];
			break;
		case 7: 
			avatar = "00"+uidArr[0]+"/"+uidArr[1]+uidArr[2]+"/"+uidArr[3]+uidArr[4]+"/"+uidArr[5]+uidArr[6];
			break;
		case 8:
			avatar = "0"+uidArr[0]+uidArr[1]+"/"+uidArr[2]+uidArr[3]+"/"+uidArr[4]+uidArr[5]+"/"+uidArr[6]+uidArr[7];
			break;
		case 9: 
			avatar = uidArr[0]+uidArr[1]+uidArr[2]+"/"+uidArr[3]+uidArr[4]+"/"+uidArr[5]+uidArr[6]+"/"+uidArr[7]+uidArr[8];
			break;
		}
		return "/data/avatar/"+avatar+"_avatar_middle.jpg";
	}
	/**
	 * 转换用户
	 * */
	public static void transformationUser() {
		Vector<Integer> uids = DiscuzSQLUtil.getAllUids();
		System.out.println("uids.size:"+uids.size());
		for(int uid:uids) {
//			if(uid!=1) {
				String name = DiscuzSQLUtil.getNameByUid(uid);
				System.out.println("正在转换用户  ——> UID："+uid+" 昵称："+name);
				String email = DiscuzSQLUtil.getEmailByUid(uid);
				String avatarUrl = "";
				Timestamp registerTime = DiscuzSQLUtil.getRegisterTimeByUid(uid);
				if(DiscuzSQLUtil.hasAvagarByUid(uid)) {//如果有头像
					avatarUrl = DiscuzSQLUtil.ucAddress+getAvatarUrlByUid(uid);
				}else {//如果没头像
					if(defaultDiscuzAvatar) {//是否空头像使用discuz默认头像代替？
						avatarUrl = DiscuzSQLUtil.ucAddress+"/images/noavatar_middle.gif";//默认头像
					}else {
						insertUser(uid,name,email,registerTime);//空头像插入用户
						continue;
					}
				}
				insertUser(uid, name, email,avatarUrl,registerTime);//通过网络URL存入头像的用户
//			}
		}

	}
	/**
	 * 更新每一个帖子的信息，如最后回帖时间，回帖数目等
	 * */
	public static void transformationThreadInfo() {
		Vector<Integer> tids = DiscuzSQLUtil.getAllThreadTids();
		for(int tid : tids) {
			Timestamp lastReplyTime = DiscuzSQLUtil.getLastReplyTimeByTid(tid);
			int views = DiscuzSQLUtil.getViewNumByTid(tid);
			int lastPostUserId = DiscuzSQLUtil.getLastReplyUidByTid(tid);
			int lastPostPid = DiscuzSQLUtil.getLastReplyPidByTid(tid);
			int replyNum = DiscuzSQLUtil.getReplyNumByTid(tid);
			int participant = DiscuzSQLUtil.getParticipantByTid(tid);
			updateThreadInfo(tid,lastPostPid, views,replyNum, lastReplyTime, lastPostUserId,participant);
			System.out.println("正在转换帖子的信息（回复数目 | 阅读数等） ——> tid："+tid);
		}
	}
}
