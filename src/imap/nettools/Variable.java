package imap.nettools;

import android.os.Environment;

import com.example.imap.R;

//数据库字段
public final class Variable
{ 
	// 登陆注册
	public static String login[] = {"LoginCheck"};
	public static String signup[] = {"SignUp", "facePic"};
	
	// 上传下载语音
	public static String sendVoice[] = {"UploadVoice", "viewSpotId", "voice", "title", "description", "gender", "language", "style"};
	public static String getVoice[] = {"DownloadVoice", "voiceId"};
	
	// 上传下载景点
	public static String applySpot[] = {"NewSpot", "name", "description", "longitude", "latitude"};
	public static String updateSpot[] = {"GetSpots", "viewSpotId"};
	
	// 获取排行榜
	public static String personRank[] = {"RankingListUser"};
	public static String spotRank[] = {"RankingListSpot"};
	
	// 获取个人信息
	public static String myVoice[] = {"PersonalVoices"};
	public static String myInfo[] = {"PersonalInfo"};
	public static String mySign[] = {"PersonalMarks"};
	
	// 获得语音列表
	public static String getHotVoiceFromSpot[] = {"GetSpotVoicesHot", "viewSpotId"};
	public static String getNewVoiceFromSpot[] = {"GetSpotVoicesNew", "viewSpotId"};
	public static String getFilterVoiceFromSpot[] = {"GetSpotVoicesRecommend", "viewSpotId", "gender", "language", "style"};
	
	// 对语音的三种行为
	public static String delVoice[] = {"DeleteVoice", "voiceId"};
	public static String likeVoice[] = {"LikeVoice", "voiceId"};
	public static String reportVoice[] = {"ReportVoice", "voiceId"};
	
	// 获得默认语音的id
	public static String getOfficialVoice[] = {"GetOfficialVoice", "viewSpotId"};
	public static String getRecommendVoice[] = {"GetRecommendVoice", "viewSpotId", "gender", "language", "style"};
	public static String getRandomVoice[] = {"GetRandomVoice", "viewSpotId"};
	
	// 签到
	public static String markSpot[] = {"MarkSpot", "viewSpotId"};
	
	public static String errorCode[] = {"成功", "身份验证失败", "数据库连接错误", "标号不合法", "用户名已注册", 
		"还未完全更新", "语音不属于该用户", "语音已经不存在", "不能对自己的语音进行点赞", "已经点过赞", 
		"已经举报过", "上传语音格式不正确", "上传失败", "下载失败", "景点不存在",
		"已经签到过", "不能举报自己的语音", "该景点无语音", "该景点无官方语音"};
	
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
	
	public static String[] gender = {"全选", "男生", "女生", "混合", "其他"};
	public static String[] language = {"全选", "中文", "英文", "方言", "其他"};
	public static String[] style = {"全选", "正式", "狂野", "欢乐", "其他"};
	
	public static String[] defaultvoice = {"官方", "根据偏好推荐", "随机"};
	
	public static String voicepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvoice";	
	//public static String temppath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvoice/temp";	
}