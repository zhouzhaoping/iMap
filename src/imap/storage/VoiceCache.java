package imap.storage;

import imap.nettools.NetThread;
import imap.nettools.Variable;

import java.io.File;
import java.io.FileOutputStream;

import org.json.JSONException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.widget.Toast;

public class VoiceCache
{
	private static String voicepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/imapcache/voice";	
	private static final int maxVoice = 100;
	private static final long deltaTime = 36000000;// 十小时
	
	private static void checkParent()
	{
		File dir = new File(voicepath);                 //新建文件实例
		if (dir.exists() && dir.isFile())
			dir.delete();
		if (!dir.exists())
			dir.mkdirs();
	}
	
	public static String getVoiceById(Context context, String id)
	{
		checkParent();
		
		String filename = voicepath + "/" + id;
		File file = new File(filename); 
		
		if (file.exists())
		{
			file.setLastModified(System.currentTimeMillis());
			return filename;// 存在缓存，返回
		}
		else
		{
			SharedPreferences sp = context.getSharedPreferences("imap", 0);
			String username = sp.getString("username", "");
			String password = sp.getString("password", "");
			
			NetThread netthread = new NetThread(username, password);
			netthread.makeParam(Variable.getVoice, id);
			int returnCode = netthread.beginDeal();

			if (returnCode == 0) {
				try {
					String voicestr = netthread.getReturn().getString("voice");
					FileOutputStream fileOutputStream = new FileOutputStream(filename);
					fileOutputStream.write(voicestr.getBytes("ISO-8859-1"));
					fileOutputStream.flush();
					fileOutputStream.close();

					// 超过阈值，删除旧缓存
					if (new File(voicepath).list().length > maxVoice)
						refresh();
					
					return filename;// 添加缓存返回
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else if (returnCode == -1) {
				Toast.makeText(context, "网络错误！", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context,
						Variable.errorCode[returnCode] + "！",
						Toast.LENGTH_SHORT).show();
			}
		}
		return null;
	}
	
	public static String getDefaultVoiceBySpotId(Context context, String spotid)
	{
		SharedPreferences sp = context.getSharedPreferences("imap", 0);
		String username = sp.getString("username", "");
		String password = sp.getString("password", "");
		int defaultn = sp.getInt("default", 0);
		
		NetThread netthread = new NetThread(username, password);
		
		if (defaultn == 0)
			netthread.makeParam(Variable.getOfficialVoice, spotid);
		else if (defaultn == 1)
		{
			String gender = sp.getInt("gender", 0) + "";
			String language = sp.getInt("language", 0) + "";
			String style = sp.getInt("style", 0) + "";
			netthread.makeParam(Variable.getRecommendVoice, spotid, gender, language, style);
		}	
		else if (defaultn == 2)
			netthread.makeParam(Variable.getRandomVoice, spotid);
		
		int returnCode = netthread.beginDeal();
		if (returnCode == 0) {
			try {
				String voiceId = netthread.getReturn().getString("voiceId");
				return getVoiceById(context, voiceId);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else if (returnCode == -1) {
			Toast.makeText(context, "网络错误！", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context,
					Variable.errorCode[returnCode] + "！",
					Toast.LENGTH_SHORT).show();
		}
		return null;
	}
	
	private static void refresh()
	{
		File dir = new File(voicepath);
		long time = System.currentTimeMillis();
		if (dir.exists() && dir.isDirectory())
		{
			if (dir.list().length >= maxVoice)
			{
				File[] list = dir.listFiles();
				for(File f:list)
				{
					if (time - f.lastModified() > deltaTime)
						f.delete();
				}
			}
		}
	}
}