package imap.nettools;

import android.os.Environment;

import com.example.imap.R;

//数据库字段
public final class Variable
{
	public static String login[] = {"LoginCheck"};
	public static String signup[] = {"SignUp", "facePic"};
	public static String sendVoice[] = {"UploadVoice", "viewSpotId", "voice", "title", "description", "gender", "language", "style"};
	public static String getVoice[] = {"DownloadVoice", "voiceId"};
	public static String applySpot[] = {"NewSpot", "name", "description", "longitude", "latitude"};
	public static String updateSpot[] = {"GetSpots", "viewSpotId"};
	public static String personRank[] = {"RankingListUser"};
	public static String spotRank[] = {"RankingListSpot"};
	public static String myVoice[] = {"PersonalVoices"};
	public static String myInfo[] = {"PersonalInfo"};
	public static String mySign[] = {"PersonalMarks"};
	public static String getHotVoiceFromSpot[] = {"GetSpotVoicesHot", "viewSpotId", "gender", "language", "style"};
	public static String getNewVoiceFromSpot[] = {"GetSpotVoicesNew", "viewSpotId", "gender", "language", "style"};
	
	public static String delVoice[] = {"DeleteVoice", "voiceId"};
	public static String likeVoice[] = {"LikeVoice", "voiceId"};
	public static String reportVoice[] = {"ReportVoice", "voiceId"};
	
	public static String errorCode[] = {"成功", "身份验证失败", "数据库连接错误", "标号不合法", "用户名已注册", 
		"还未完全更新", "语音不属于该用户", "语音已经不存在", "不能对自己的语音进行点赞", "已经点过赞", 
		"已经举报过", "上传语音格式不正确", "上传失败", "下载失败", "景点不存在",
		"表示已经签到过"};
	
	public static int faceNum = 8;
	public static int picId[] = {R.drawable.face0, R.drawable.face1, R.drawable.face2, R.drawable.face3, R.drawable.face4, R.drawable.face5, R.drawable.face6, R.drawable.face7};
	public static int int2pic(int picn)
	{
		return (picId[picn % faceNum]);
	}
	
	public static int numNum = 10;
	public static int numId[] = {R.drawable.num1, R.drawable.num2, R.drawable.num3, R.drawable.num4, R.drawable.num5, R.drawable.num6, R.drawable.num7, R.drawable.num8, R.drawable.num9, R.drawable.num10};
	public static int int2num(int numn)
	{
		return (numId[numn % numNum]);
	}
	
	public static double[] ranges = {20, 40, 60, 80, 100};
	public static String[] areas = {"20米", "40米", "60米", "80米", "100米"};
	
	public static String voicepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvoice";	
	public static String temppath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvoice/temp";	
}