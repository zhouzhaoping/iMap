package imap.main;

import imap.nettools.ViewSpotData;

import java.util.List;

import android.R.integer;
import android.app.Application; 

//参照http://oyeal.iteye.com/blog/941183
public class MyAppData extends Application{ 

    private double mylabellat ;   
    private double mylabellong ; 
    private double pre_mylabellat ;   
    private double pre_mylabellong ; 
    private int walk_listen;
    List<ViewSpotData> viewspotlist;
    private int near_listen_view_index;//最近播放的一次默认语音所在的景点的编号
    
    
    
    public double getpre_mylabellat(){ 
        return pre_mylabellat; 
    }    
    public void setpre_mylabellat(double s){ 
        this.pre_mylabellat = s; 
    } 
    public double getpre_mylabellong(){ 
        return pre_mylabellong; 
    }    
    public void setpre_mylabellong(double s){ 
        this.pre_mylabellong = s; 
    } 
    public double getmylabellat(){ 
        return mylabellat; 
    }    
    public void setmylabellat(double s){ 
        this.mylabellat = s; 
    } 
    public double getmylabellong(){ 
        return mylabellong; 
    }    
    public void setmylabellong(double s){ 
        this.mylabellong = s; 
    } 
    
    public int getnear_listen_view_index(){ 
        return near_listen_view_index; 
    }    
    public void setnear_listen_view_index(int s){ 
        this.near_listen_view_index = s; 
    } 
    
    public int getwalk_listen(){ 
        return walk_listen; 
    }    
    public void setwalk_listen(int s){ 
        this.walk_listen = s; 
    } 
    
    public void setviewlist(List<ViewSpotData> s)
    {
    	this.viewspotlist = s;
    }
    public List<ViewSpotData> getviewlist()
    {
    	return viewspotlist;
    }
    @Override 
    public void onCreate() { 
        // TODO Auto-generated method stub 
       
        setmylabellat(-1); //初始化全局变量 
        setmylabellong(-1);
        setwalk_listen(0);
        setpre_mylabellat(-1);
        setpre_mylabellong(-1);
        setnear_listen_view_index(-1);
        
        super.onCreate(); 
    }    
    
    
} 
