package mast.ShuanKou;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Result  extends Activity {
	/**使用SharedPreferences 来储存与读取数据**/
	private SharedPreferences mShared = null;
	/**程序中可以同时存在多个SharedPreferences数据， 根据SharedPreferences的名称就可以拿到对象**/
	private final static String SHARED_MAIN = "main";
	/**SharedPreferences中储存数据的Key名称**/
	private final static String KEY_GET1 = "sGet1";
	private final static String KEY_GET2 = "sGet2";
	private final static String KEY_GET3 = "sGet3";
	private final static String KEY_GET4 = "sGet4";	
	
	private String sGet1="";
    private String sGet2="";
    private String sGet3="";
    private String sGet4=""; 

	private EditText txtresultWin1 = null;
	private EditText txtresultWin2 = null;
	private EditText txtresultLose1 = null;
	private EditText txtresultLose2 = null;

	private Button cmdresultReturn=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.result);
		/**拿到名称是SHARED_MAIN 的SharedPreferences对象**/
		mShared = getSharedPreferences(SHARED_MAIN,  Context.MODE_PRIVATE);
		//读取参数
		sGet1 = mShared.getString(KEY_GET1, "");
		sGet2 = mShared.getString(KEY_GET2, "");
		sGet3 = mShared.getString(KEY_GET3, "");
		sGet4 = mShared.getString(KEY_GET4, "");
		
		txtresultWin1=(EditText)findViewById(R.id.txtresultWin1);
		txtresultWin2=(EditText)findViewById(R.id.txtresultWin2);
		txtresultLose1=(EditText)findViewById(R.id.txtresultLose1);
		txtresultLose2=(EditText)findViewById(R.id.txtresultLose2);
		cmdresultReturn=(Button)findViewById(R.id.cmdresultReturn);

		txtresultWin1.setText(sGet1);
		txtresultWin2.setText(sGet2);
		txtresultLose1.setText(sGet3);
		txtresultLose2.setText(sGet4);
		
		cmdresultReturn.setOnClickListener(new cmdReturn_Listener());
	}
	//返回
	class cmdReturn_Listener implements OnClickListener{
		public void onClick(View V){			
			finish();
		}		
	}
}
