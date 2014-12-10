package imap.nettools;

//数据库字段
public final class Variable
{
	public static String login[] = {"LoginCheck"};
	public static String signup[] = {"SignUp", "facePic"};
	public static String sendVoice[] = {"UploadVoice", "viewSpotId", "voice", "tagList"};
	public static String applySpot[] = {"NewSpot", "name", "description", "longitude", "latitude"};
	public static String updateSpot[] = {"GetSpots", "viewSpotId"};
	public static String rank[] = {""};
	public static String myVoice[] = {""};
	public static String mySign[] = {""};
	public static String getVoice[] = {"", "viewSpotId", "tag1", "tag2", "tag3"};
	
	public static String delVoice[] = {"", "voiceId"};
	public static String likeVoice[] = {"", "voiceId"};
	public static String reportVoice[] = {"", "voiceId"};
	
	public static String errorCode[] = {"成功", "身份验证失败", "数据库连接错误", "标号不合法", "用户名已注册", 
		"还未完全更新", "语音不属于该用户", "语音已经不存在", "不能对自己的语音进行点赞", "已经点过赞", 
		"已经举报过"};
	
	public static int faceNum = 8;
}