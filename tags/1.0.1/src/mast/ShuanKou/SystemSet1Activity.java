package mast.ShuanKou;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SystemSet1Activity  extends Activity {
	/**使用SharedPreferences 来储存与读取数据**/
	private SharedPreferences mShared = null;
	/**程序中可以同时存在多个SharedPreferences数据， 根据SharedPreferences的名称就可以拿到对象**/
	private final static String SHARED_MAIN = "main";
	/**SharedPreferences中储存数据的Key名称**/
	private final static String KEY_P = "set_p";
	private final static String KEY_D = "set_d";
	private final static String KEY_S = "set_s";
	private final static String KEY_MAX = "set_max";		
	
	//系统设置信息变量
	private float set_p=0;//平扣基础分
	private float set_d=0;//单扣基础分
	private float set_s=0;//双扣基础分
	
	private int set_max=0;
	
	private EditText txtp = null;
	private EditText txtd = null;
	private EditText txts = null;

	private Button cmdSave = null;
	private Button cmdReturn = null;
	private Button cmdReSet = null;
	
	private Spinner spinner_max;
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.systemset1);
		/**拿到名称是SHARED_MAIN 的SharedPreferences对象**/
		mShared = getSharedPreferences(SHARED_MAIN,  Context.MODE_PRIVATE);
		//读取参数
		set_p = mShared.getFloat(KEY_P, 1);
		set_d = mShared.getFloat(KEY_D, 2);
		set_s = mShared.getFloat(KEY_S, 3);
		
		set_max = mShared.getInt(KEY_MAX, 0);
		
		txtp=(EditText)findViewById(R.id.txtp);
		txtd=(EditText)findViewById(R.id.txtd);
		txts=(EditText)findViewById(R.id.txts);
		
		cmdSave=(Button)findViewById(R.id.cmdSave);
		cmdReturn=(Button)findViewById(R.id.cmdReturn);
		cmdReSet=(Button)findViewById(R.id.cmdReSet);

		spinner_max=(Spinner)findViewById(R.id.spinner_max);		
		
		txtp.setText(set_p + "");
		txtd.setText(set_d + "");
		txts.setText(set_s + "");
		
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.slevel,android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_max.setAdapter(adapter);
		spinner_max.setPrompt("最高翻至");
		spinner_max.setSelection(set_max);
		
		cmdSave.setOnClickListener(new cmdSave_Listener());
		cmdReSet.setOnClickListener(new cmdReSet_Listener());
		cmdReturn.setOnClickListener(new cmdReturn_Listener());
		
		txtp.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s){}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before,int count){
				if(s.length()>0){
					txtd.setText(Float.parseFloat(txtp.getText().toString())* 2 + "");
					txts.setText(Float.parseFloat(txtp.getText().toString())* 3 + "");
	            	}
				else{
					txtd.setText("");
					txts.setText("");
					}
				}
		});
		txtp.setOnFocusChangeListener(new txtFocusChang_Listener());
		txtd.setOnFocusChangeListener(new txtFocusChang_Listener());
		txts.setOnFocusChangeListener(new txtFocusChang_Listener());
	
	}
	//输入框获得焦点
	class txtFocusChang_Listener implements OnFocusChangeListener{
		 public void onFocusChange(View v, boolean hasFocus) {
			 ((TextView) v).setSelectAllOnFocus(true);
		 }
	}
	//保存
	class cmdSave_Listener implements OnClickListener{
		public void onClick(View V){
		    Editor editor = mShared.edit();
		    editor.putFloat(KEY_P, Float.parseFloat(txtp.getText().toString()));
		    editor.putFloat(KEY_D, Float.parseFloat(txtd.getText().toString()));
		    editor.putFloat(KEY_S, Float.parseFloat(txts.getText().toString()));
		    editor.putInt(KEY_MAX, spinner_max.getSelectedItemPosition());		    
		    editor.commit();
			finish();
		}
	}
	//默认
	class cmdReSet_Listener implements OnClickListener{
		public void onClick(View V){
			txtp.setText("1");
			txtd.setText("2");
			txts.setText("3");
			spinner_max.setSelection(0);
		}
	}
	//返回
	class cmdReturn_Listener implements OnClickListener{
		public void onClick(View V){			
			finish();
		}		
	}
}