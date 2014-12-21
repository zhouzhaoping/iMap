package imap.me;

import imap.main.LoginActivity;
import imap.main.MainActivity;
import imap.main.SignupActivity;
import imap.nettools.NetThread;
import imap.nettools.Variable;
import imap.setting.SettingActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.example.imap.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UnUploadItemAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<UnUploadItem> list = new ArrayList<UnUploadItem>();

	public UnUploadItemAdapter(Context context, ArrayList<UnUploadItem> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		UnUploadItem hh = list.get(position);
		H h = null;
		if (view == null) {
			h = new H();
			view = LayoutInflater.from(context).inflate(
					R.layout.unuploadlist_item, parent, false);
			h.title = (TextView) view.findViewById(R.id.title);
			h.spotname = (TextView) view.findViewById(R.id.spotname);
			h.time = (TextView) view.findViewById(R.id.time);
			h.description = (TextView) view.findViewById(R.id.description);
			h.play = (ImageButton) view.findViewById(R.id.button_play);
			h.upload = (ImageButton) view.findViewById(R.id.button_upload);
			h.delete = (ImageButton) view.findViewById(R.id.button_delete);
			h.thisview = view;
			view.setTag(h);
		} else {
			h = (H) view.getTag();
		}
		
		h.file1 = hh.getPath();
		h.file2 = hh.getPath2();
		h.spotid = hh.getSpotId() + "";
		
		h.title.setText(hh.getTitle());
		h.spotname.setText(MainActivity.viewspotlist.get(hh.getSpotId()).getName());
		h.time.setText(hh.getTime());
		h.description.setText(hh.getDescription());
		h.play.setTag(h);
		h.upload.setTag(h);
		h.delete.setTag(h);

		h.play.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				final H h = (H) v.getTag();
				MediaPlayer player = new MediaPlayer();
				try {
					player.setDataSource(h.file1);
					player.prepare();
					player.start();
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		h.upload.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				final H h = (H) v.getTag();
				
				new AlertDialog.Builder(context)
				.setTitle("确认上传")
				.setMessage("上传\"" + h.title.getText() + "\"?")
				.setPositiveButton("是",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								
								try {
									// 读取文件
									FileInputStream fileInputStream = new FileInputStream(h.file1);
									ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
									int i;
									while ((i = fileInputStream.read()) != -1)
										byteArrayOutputStream.write(i);
									fileInputStream.close();
									// 把文件存在一个字节数组中
									byte[] filea = byteArrayOutputStream.toByteArray();
									byteArrayOutputStream.close();

									String fileaString = new String(filea, "ISO-8859-1");
									// System.out.println(fileaString);
									// 写入文件
									
									// FileOutputStream fileOutputStream = new FileOutputStream(h.file2 + "fortext.amr");
									 //fileOutputStream.write(fileaString.getBytes("ISO-8859-1")); fileOutputStream.flush(); fileOutputStream.close();

									SharedPreferences sp = context.getSharedPreferences("imap", 0);
									String username = sp.getString("username", "");
									String password = sp.getString("password", "");
									
									NetThread netthread = new NetThread(username, password);
									netthread.makeParam(Variable.sendVoice, h.spotid, fileaString, h.title.getText().toString(), h.description.getText().toString(), "0", "0", "0");
									int returnCode = netthread.beginDeal();
									
									if (returnCode == 0)
									  {
										h.thisview.setVisibility(View.GONE);
										File file = new File(h.file1);
										if (file.exists())
											file.delete();
										file = new File(h.file2);
										if (file.exists())
											file.delete();  
										
										//MeActivity.uploadVoicesFresh = true;
										//MeActivity.uploadFragment = null;
										Toast.makeText(context, "语音上传成功！请静待处理~", 
									                 Toast.LENGTH_SHORT).show(); 
									  }
									  else if (returnCode == -1)
									  {
										  Toast.makeText(context, "网络错误！", 
									                 Toast.LENGTH_SHORT).show();
									  }
									  else
									  {
										  Toast.makeText(context, Variable.errorCode[returnCode] + "！", 
									                 Toast.LENGTH_SHORT).show();
									  }
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}).setNegativeButton("否", null).show();
			}
		});
		h.delete.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				final H h = (H) v.getTag();

				new AlertDialog.Builder(context)
						.setTitle("确认删除")
						.setMessage("删除\"" + h.title.getText() + "\"?")
						.setPositiveButton("是",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										
										h.thisview.setVisibility(View.GONE);
										File file = new File(h.file1);
										if (file.exists())
											file.delete();
										file = new File(h.file2);
										if (file.exists())
											file.delete();
										
										Toast.makeText(context, "删除成功！",
												Toast.LENGTH_SHORT).show();
									}
								}).setNegativeButton("否", null).show();
			}
		});
		return view;
	}

	class H {
		View thisview;
		String file1;
		String file2;
		String spotid;

		TextView title;
		TextView spotname;
		TextView time;
		TextView description;
		ImageButton play;
		ImageButton upload;
		ImageButton delete;
	}
}
