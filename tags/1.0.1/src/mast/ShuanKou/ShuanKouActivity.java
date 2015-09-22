package mast.ShuanKou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class ShuanKouActivity extends Activity {
	/**使用SharedPreferences 来储存与读取数据**/
	SharedPreferences mShared = null;
	/**程序中可以同时存在多个SharedPreferences数据， 根据SharedPreferences的名称就可以拿到对象**/
	private final static String SHARED_MAIN = "main";
	/**SharedPreferences中储存数据的Key名称**/
	private final static String KEY_P = "set_p";
	private final static String KEY_D = "set_d";
	private final static String KEY_S = "set_s";
	private final static String KEY_6 = "set_6";
	private final static String KEY_7 = "set_7";
	private final static String KEY_8 = "set_8";
	private final static String KEY_9 = "set_9";
	private final static String KEY_10 = "set_10";
	private final static String KEY_11 = "set_11";
	private final static String KEY_12 = "set_12";
	private final static String KEY_MAX = "set_max";
	private final static String KEY_GET1 = "sGet1";
	private final static String KEY_GET2 = "sGet2";
	private final static String KEY_GET3 = "sGet3";
	private final static String KEY_GET4 = "sGet4";
		
	//系统设置信息变量
	private float set_p=0;//平扣基础分
	private float set_d=0;//单扣基础分
	private float set_s=0;//双扣基础分
	
	private float set_6=0;//6扇贡献分
	private float set_7=0;//7扇贡献分
	private float set_8=0;//8扇贡献分
	private float set_9=0;//9扇贡献分
	private float set_10=0;//10扇贡献分
	private float set_11=0;//11扇贡献分
	private float set_12=0;//12扇贡献分
	private int set_max=0;//最高翻至	
	
	private RadioButton radio_p= null;
	private RadioButton radio_d= null;
	private RadioButton radio_s= null;
    
    private CheckBox chk1_5 = null; 
    private CheckBox chk1_6 = null; 
    private CheckBox chk1_7 = null; 
    private CheckBox chk1_8 = null; 
    private CheckBox chk1_9 = null; 
    private CheckBox chk1_10 = null; 
	private CheckBox chk1_11 = null; 
	private CheckBox chk1_12 = null;

	private CheckBox chk2_5 = null; 
	private CheckBox chk2_6 = null; 
	private CheckBox chk2_7 = null; 
	private CheckBox chk2_8 = null; 
	private CheckBox chk2_9 = null; 
	private CheckBox chk2_10 = null; 
	private CheckBox chk2_11 = null; 
	private CheckBox chk2_12 = null;

	private CheckBox chk3_5 = null; 
	private CheckBox chk3_6 = null; 
	private CheckBox chk3_7 = null; 
	private CheckBox chk3_8 = null; 
	private CheckBox chk3_9 = null; 
	private CheckBox chk3_10 = null; 
	private CheckBox chk3_11 = null; 
	private CheckBox chk3_12 = null;

	private CheckBox chk4_5 = null; 
	private CheckBox chk4_6 = null; 
	private CheckBox chk4_7 = null; 
	private CheckBox chk4_8 = null; 
	private CheckBox chk4_9 = null; 
	private CheckBox chk4_10 = null; 
	private CheckBox chk4_11 = null; 
	private CheckBox chk4_12 = null;
	
	private Button cmdOK =  null; 
	private Button cmdClear =  null; 
	private Button cmdExit =  null; 
	
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.main);	
		/**拿到名称是SHARED_MAIN 的SharedPreferences对象**/
		mShared = getSharedPreferences(SHARED_MAIN,  Context.MODE_PRIVATE);
		
	    radio_p=(RadioButton)findViewById(R.id.radio_p);
	    radio_d=(RadioButton)findViewById(R.id.radio_d);
	    radio_s=(RadioButton)findViewById(R.id.radio_s);
	    
		chk1_5 = (CheckBox)findViewById(R.id.chkWin1_5); 
		chk1_6 = (CheckBox)findViewById(R.id.chkWin1_6); 
		chk1_7 = (CheckBox)findViewById(R.id.chkWin1_7); 
		chk1_8 = (CheckBox)findViewById(R.id.chkWin1_8); 
		chk1_9 = (CheckBox)findViewById(R.id.chkWin1_9); 
		chk1_10 = (CheckBox)findViewById(R.id.chkWin1_10); 
		chk1_11 = (CheckBox)findViewById(R.id.chkWin1_11); 
		chk1_12 = (CheckBox)findViewById(R.id.chkWin1_12);
	
		chk2_5 = (CheckBox)findViewById(R.id.chkWin2_5); 
		chk2_6 = (CheckBox)findViewById(R.id.chkWin2_6); 
		chk2_7 = (CheckBox)findViewById(R.id.chkWin2_7); 
		chk2_8 = (CheckBox)findViewById(R.id.chkWin2_8); 
		chk2_9 = (CheckBox)findViewById(R.id.chkWin2_9); 
		chk2_10 = (CheckBox)findViewById(R.id.chkWin2_10); 
		chk2_11 = (CheckBox)findViewById(R.id.chkWin2_11); 
		chk2_12 = (CheckBox)findViewById(R.id.chkWin2_12);

		chk3_5 = (CheckBox)findViewById(R.id.chkLose1_5);
		chk3_6 = (CheckBox)findViewById(R.id.chkLose1_6); 
		chk3_7 = (CheckBox)findViewById(R.id.chkLose1_7); 
		chk3_8 = (CheckBox)findViewById(R.id.chkLose1_8); 
		chk3_9 = (CheckBox)findViewById(R.id.chkLose1_9); 
		chk3_10 = (CheckBox)findViewById(R.id.chkLose1_10); 
		chk3_11 = (CheckBox)findViewById(R.id.chkLose1_11); 
		chk3_12 = (CheckBox)findViewById(R.id.chkLose1_12);

		chk4_5 = (CheckBox)findViewById(R.id.chkLose2_5); 
		chk4_6 = (CheckBox)findViewById(R.id.chkLose2_6); 
		chk4_7 = (CheckBox)findViewById(R.id.chkLose2_7); 
		chk4_8 = (CheckBox)findViewById(R.id.chkLose2_8); 
		chk4_9 = (CheckBox)findViewById(R.id.chkLose2_9); 
		chk4_10 = (CheckBox)findViewById(R.id.chkLose2_10); 
		chk4_11 = (CheckBox)findViewById(R.id.chkLose2_11); 
		chk4_12 = (CheckBox)findViewById(R.id.chkLose2_12);
		
		cmdOK = (Button)findViewById(R.id.cmdOK); 
		cmdClear = (Button)findViewById(R.id.cmdClear); 
		cmdExit = (Button)findViewById(R.id.cmdExit); 
			
	    cmdExit.setOnClickListener(new cmdExit_Listener());
	    cmdClear.setOnClickListener(new cmdClear_Listener());
	    cmdOK.setOnClickListener(new cmdOK_Listener());
	    radio_p.setChecked(true);
	}	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==1){
			Intent intent=new Intent();
			intent.setClass(ShuanKouActivity.this,SystemSet1Activity.class);
			ShuanKouActivity.this.startActivity(intent);
		}
		if(item.getItemId()==2){
			Intent intent=new Intent();			
			intent.setClass(ShuanKouActivity.this,SystemSet2Activity.class);
			ShuanKouActivity.this.startActivity(intent);
		}	
		if(item.getItemId()==3){
			Intent intent=new Intent();			
			intent.setClass(ShuanKouActivity.this,AboutActivity.class);
			ShuanKouActivity.this.startActivity(intent);
		}		
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "基础分");
		menu.add(0, 2, 2, "贡献分");
		menu.add(0, 3, 3, "关于");
		return super.onCreateOptionsMenu(menu);
	}
	//退出
	class cmdExit_Listener implements OnClickListener{
		public void onClick(View V){
			finish();
		}		
	}
	//清空
	class cmdClear_Listener implements OnClickListener{
		public void onClick(View V){
			radio_p.setChecked(true);
			
			chk1_5.setChecked(false);
			chk1_6.setChecked(false);
			chk1_7.setChecked(false);
			chk1_8.setChecked(false);
			chk1_9.setChecked(false);
			chk1_10.setChecked(false);
			chk1_11.setChecked(false);
			chk1_12.setChecked(false);
			
			chk2_5.setChecked(false);
			chk2_6.setChecked(false);
			chk2_7.setChecked(false);
			chk2_8.setChecked(false);
			chk2_9.setChecked(false);
			chk2_10.setChecked(false);
			chk2_11.setChecked(false);
			chk2_12.setChecked(false);
			
			chk3_5.setChecked(false);
			chk3_6.setChecked(false);
			chk3_7.setChecked(false);
			chk3_8.setChecked(false);
			chk3_9.setChecked(false);
			chk3_10.setChecked(false);
			chk3_11.setChecked(false);
			chk3_12.setChecked(false);
			
			chk4_5.setChecked(false);
			chk4_6.setChecked(false);
			chk4_7.setChecked(false);
			chk4_8.setChecked(false);
			chk4_9.setChecked(false);
			chk4_10.setChecked(false);
			chk4_11.setChecked(false);
			chk4_12.setChecked(false);
		}		
	}
	//计算
	class cmdOK_Listener implements OnClickListener{
		public void onClick(View V){
			//读取参数
			set_p = mShared.getFloat(KEY_P, 1);
			set_d = mShared.getFloat(KEY_D, 2);
			set_s = mShared.getFloat(KEY_S, 3);

			set_6 = mShared.getFloat(KEY_6, 1);
			set_7 = mShared.getFloat(KEY_7, 2);
			set_8 = mShared.getFloat(KEY_8, 4);
			set_9 = mShared.getFloat(KEY_9, 8);
			set_10 = mShared.getFloat(KEY_10, 16);
			set_11 = mShared.getFloat(KEY_11, 32);
			set_12 = mShared.getFloat(KEY_12, 64);
			
			set_max = mShared.getInt(KEY_MAX, 0);
			//界面数据变量
		    int nS=0;//倍数    
		    float nAlt=0;//牌面得分
		    String sAlt="";
		    
		    float nRlt1=0;//玩家一进供
		    String sRlt1="";
		    float nRlt2=0;//玩家二进供
		    String sRlt2="";
		    float nRlt3=0;//玩家三进供
		    String sRlt3="";
		    float nRlt4=0;//玩家四进供
		    String sRlt4="";
		    
		    float nGet1=0;//玩家一得分
		    String sGet1="";
		    float nGet2=0;//玩家二得分
		    String sGet2="";
		    float nGet3=0;//玩家三得分
		    String sGet3="";
		    float nGet4=0;//玩家四得分
		    String sGet4="";     
		    
		    if(chk1_5.isChecked()){nS=1;}
		    if(chk1_6.isChecked()){nS=2;}
		    if(chk1_7.isChecked()){nS=3;}
		    if(chk1_8.isChecked()){nS=4;}
		    if(chk1_9.isChecked()){nS=5;}
		    if(chk1_10.isChecked()){nS=6;}
		    if(chk1_11.isChecked()){nS=7;}
		    if(chk1_12.isChecked()){nS=8;}    
		
		    if(chk2_5.isChecked() && nS<1){nS=1;}
		    if(chk2_6.isChecked() && nS<2){nS=2;}
		    if(chk2_7.isChecked() && nS<3){nS=3;}
		    if(chk2_8.isChecked() && nS<4){nS=4;}
		    if(chk2_9.isChecked() && nS<5){nS=5;}
		    if(chk2_10.isChecked() && nS<6){nS=6;}
		    if(chk2_11.isChecked() && nS<7){nS=7;}
		    if(chk2_12.isChecked() && nS<8){nS=8;}
		    
		    if(set_max != 0 && nS>set_max){nS=set_max;}
		    
		    //算牌面分
		    if (radio_p.isChecked()){
		    	nAlt=(float)(set_p*Math.pow(2,nS));
		    	sAlt=FormatNumber(set_p)+"x"+FormatNumber((float)Math.pow(2,nS));
		    }
		    else if(radio_d.isChecked()){
		    	nAlt=(float)(set_d*Math.pow(2,nS));
		    	sAlt=FormatNumber(set_d)+"x"+FormatNumber((float)Math.pow(2,nS));
		    }
		    else if(radio_s.isChecked()){
		    	nAlt=(float)(set_s*Math.pow(2,nS));
		    	sAlt=FormatNumber(set_s)+"x"+FormatNumber((float)Math.pow(2,nS));
		    }		    
		    //算玩家进供
		    //赢家一
		    if (chk1_6.isChecked()){nRlt1 = nRlt1 + set_6;}
		    if (chk1_7.isChecked()){nRlt1 = nRlt1 + set_7;}
		    if (chk1_8.isChecked()){nRlt1 = nRlt1 + set_8;}
		    if (chk1_9.isChecked()){nRlt1 = nRlt1 + set_9;}
		    if (chk1_10.isChecked()){nRlt1 = nRlt1 + set_10;}
		    if (chk1_11.isChecked()){nRlt1 = nRlt1 + set_11;}
		    if (chk1_12.isChecked()){nRlt1 = nRlt1 + set_12;}
		    //赢家二
		    if (chk2_6.isChecked()){nRlt2 = nRlt2 + set_6;}
		    if (chk2_7.isChecked()){nRlt2 = nRlt2 + set_7;}
		    if (chk2_8.isChecked()){nRlt2 = nRlt2 + set_8;}
		    if (chk2_9.isChecked()){nRlt2 = nRlt2 + set_9;}
		    if (chk2_10.isChecked()){nRlt2 = nRlt2 + set_10;}
		    if (chk2_11.isChecked()){nRlt2 = nRlt2 + set_11;}
		    if (chk2_12.isChecked()){nRlt2 = nRlt2 + set_12;}
		    //输家一
		    if (chk3_6.isChecked()){nRlt3 = nRlt3 + set_6;}
		    if (chk3_7.isChecked()){nRlt3 = nRlt3 + set_7;}
		    if (chk3_8.isChecked()){nRlt3 = nRlt3 + set_8;}
		    if (chk3_9.isChecked()){nRlt3 = nRlt3 + set_9;}
		    if (chk3_10.isChecked()){nRlt3 = nRlt3 + set_10;}
		    if (chk3_11.isChecked()){nRlt3 = nRlt3 + set_11;}
		    if (chk3_12.isChecked()){nRlt3 = nRlt3 + set_12;}
		    //输家二
		    if (chk4_6.isChecked()){nRlt4 = nRlt4 + set_6;}
		    if (chk4_7.isChecked()){nRlt4 = nRlt4 + set_7;}
		    if (chk4_8.isChecked()){nRlt4 = nRlt4 + set_8;}
		    if (chk4_9.isChecked()){nRlt4 = nRlt4 + set_9;}
		    if (chk4_10.isChecked()){nRlt4 = nRlt4 + set_10;}
		    if (chk4_11.isChecked()){nRlt4 = nRlt4 + set_11;}
		    if (chk4_12.isChecked()){nRlt4 = nRlt4 + set_12;}
		    
		    //算得分
		    sRlt1=FormatNumber(nRlt1);
		    sRlt2=FormatNumber(nRlt2);
		    sRlt3=FormatNumber(nRlt3);
		    sRlt4=FormatNumber(nRlt4);
		    
		    nGet1 = nAlt + nRlt1 * 3 - nRlt2 - nRlt3 - nRlt4;
		    sGet1 = FormatNumber(nGet1)+" = "+sAlt + ((nRlt1 == 0) ? "" : "+(" + sRlt1 + ")x3") + ((nRlt2 == 0) ? "" : "-" + sRlt2) + ((nRlt3 == 0) ? "" : "-" + sRlt3) + ((nRlt4 == 0) ? "" : "-" + sRlt4) ;
		    
		    nGet2 = nAlt + nRlt2 * 3 - nRlt1 - nRlt3 - nRlt4;
		    sGet2 = FormatNumber(nGet2)+" = "+sAlt + ((nRlt2 == 0) ? "" : "+(" + sRlt2 + ")x3") + ((nRlt1 == 0) ? "" : "-" + sRlt1) + ((nRlt3 == 0) ? "" : "-" + sRlt3) + ((nRlt4 == 0) ? "" : "-" + sRlt4);
		    
		    nGet3 = nRlt3 * 3 - nAlt - nRlt1 - nRlt2 - nRlt4;
		    sGet3 = FormatNumber(nGet3)+" = "+((nRlt3 == 0) ? "" : "(" + sRlt3 + ")x3") + "-" + sAlt + ((nRlt1 == 0) ? "" : "-" + sRlt1) + ((nRlt2 == 0) ? "" : "-" + sRlt2) + ((nRlt4 == 0) ? "" : "-" + sRlt4);
		    
		    nGet4 = nRlt4 * 3 - nAlt - nRlt1 - nRlt2 - nRlt3;
		    sGet4 = FormatNumber(nGet4)+" = "+((nRlt4 == 0) ? "" : "(" + sRlt4 + ")x3") + "-" + sAlt + ((nRlt1 == 0) ? "" : "-" + sRlt1) + ((nRlt2 == 0) ? "" : "-" + sRlt2) + ((nRlt3 == 0) ? "" : "-" + sRlt3) ;
		    
		    Editor editor = mShared.edit();
		    editor.putString(KEY_GET1, sGet1);
		    editor.putString(KEY_GET2, sGet2);
		    editor.putString(KEY_GET3, sGet3);
		    editor.putString(KEY_GET4, sGet4);
		    editor.commit();
		    
		    Intent intent=new Intent();
			intent.setClass(ShuanKouActivity.this,Result.class);
			ShuanKouActivity.this.startActivity(intent);
		}		
	}
	//去小数点后的0
    private String FormatNumber(float num) {
    	int iNum=(int) num;
    	if(iNum==num){return iNum+"";}
    	else{return num+"";}        
    }
}