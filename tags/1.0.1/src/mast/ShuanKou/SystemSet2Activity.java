package mast.ShuanKou;

import mast.ShuanKou.SystemSet1Activity.txtFocusChang_Listener;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SystemSet2Activity  extends Activity {
	/**ʹ��SharedPreferences ���������ȡ����**/
	private SharedPreferences mShared = null;
	/**�����п���ͬʱ���ڶ��SharedPreferences���ݣ� ����SharedPreferences�����ƾͿ����õ�����**/
	private final static String SHARED_MAIN = "main";
	/**SharedPreferences�д������ݵ�Key����**/
	private final static String KEY_6 = "set_6";
	private final static String KEY_7 = "set_7";
	private final static String KEY_8 = "set_8";
	private final static String KEY_9 = "set_9";
	private final static String KEY_10 = "set_10";
	private final static String KEY_11 = "set_11";
	private final static String KEY_12 = "set_12";
	/**SharedPreferences�д������ݵ�·��**/
	public final static String DATA_URL = "/data/data/";
	public final static String SHARED_MAIN_XML = "main.xml";
	
	//ϵͳ������Ϣ����	
	private float set_6=0;//6�ȹ��׷�
	private float set_7=0;//7�ȹ��׷�
	private float set_8=0;//8�ȹ��׷�
	private float set_9=0;//9�ȹ��׷�
	private float set_10=0;//10�ȹ��׷�
	private float set_11=0;//11�ȹ��׷�
	private float set_12=0;//12�ȹ��׷�
	
	private EditText txt6 = null;
	private EditText txt7 = null;
	private EditText txt8 = null;
	private EditText txt9 = null;
	private EditText txt10 = null;
	private EditText txt11 = null;
	private EditText txt12 = null;

	private Button cmdSave = null;
	private Button cmdReturn = null;
	private Button cmdReSet = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.systemset2);
		/**�õ�������SHARED_MAIN ��SharedPreferences����**/
		mShared = getSharedPreferences(SHARED_MAIN,  Context.MODE_PRIVATE);
		//��ȡ����
		set_6 = mShared.getFloat(KEY_6, 1);
		set_7 = mShared.getFloat(KEY_7, 2);
		set_8 = mShared.getFloat(KEY_8, 4);
		set_9 = mShared.getFloat(KEY_9, 8);
		set_10 = mShared.getFloat(KEY_10, 16);
		set_11 = mShared.getFloat(KEY_11, 32);
		set_12 = mShared.getFloat(KEY_12, 64);
				
		txt6=(EditText)findViewById(R.id.txts6);
		txt7=(EditText)findViewById(R.id.txts7);
		txt8=(EditText)findViewById(R.id.txts8);
		txt9=(EditText)findViewById(R.id.txts9);
		txt10=(EditText)findViewById(R.id.txts10);
		txt11=(EditText)findViewById(R.id.txts11);
		txt12=(EditText)findViewById(R.id.txts12);
		
		cmdSave=(Button)findViewById(R.id.cmdSave);
		cmdReturn=(Button)findViewById(R.id.cmdReturn);
		cmdReSet=(Button)findViewById(R.id.cmdReSet);
		
		txt6.setText(set_6 + "");
		txt7.setText(set_7 + "");
		txt8.setText(set_8 + "");
		txt9.setText(set_9 + "");
		txt10.setText(set_10 + "");
		txt11.setText(set_11 + "");
		txt12.setText(set_12 + "");		

		cmdSave.setOnClickListener(new cmdSave_Listener());
		cmdReSet.setOnClickListener(new cmdReSet_Listener());
		cmdReturn.setOnClickListener(new cmdReturn_Listener());
		
		txt6.addTextChangedListener(new TextWatcher(){
			public void afterTextChanged(Editable s){}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before,int count){				
				if(s.length()>0){
					txt7.setText(Float.parseFloat(txt6.getText().toString())* 2 + "");
					txt8.setText(Float.parseFloat(txt6.getText().toString())* 4 + "");
					txt9.setText(Float.parseFloat(txt6.getText().toString())* 8 + "");
					txt10.setText(Float.parseFloat(txt6.getText().toString())* 16 + "");
					txt11.setText(Float.parseFloat(txt6.getText().toString())* 32 + "");
					txt12.setText(Float.parseFloat(txt6.getText().toString())* 64 + "");
	                }
				else{
					txt7.setText("");
					txt8.setText("");
					txt9.setText("");
					txt10.setText("");
					txt11.setText("");
					txt12.setText("");
					}
				}
		});	
		txt6.setOnFocusChangeListener(new txtFocusChang_Listener());
		txt7.setOnFocusChangeListener(new txtFocusChang_Listener());
		txt8.setOnFocusChangeListener(new txtFocusChang_Listener());
		txt9.setOnFocusChangeListener(new txtFocusChang_Listener());
		txt10.setOnFocusChangeListener(new txtFocusChang_Listener());
		txt11.setOnFocusChangeListener(new txtFocusChang_Listener());
		txt12.setOnFocusChangeListener(new txtFocusChang_Listener());
	}
	//������ý���
	class txtFocusChang_Listener implements OnFocusChangeListener{
		 public void onFocusChange(View v, boolean hasFocus) {
			 ((TextView) v).setSelectAllOnFocus(true);
		 }
	}
	//����
	class cmdSave_Listener implements OnClickListener{
		public void onClick(View V){
		    Editor editor = mShared.edit();
		    editor.putFloat(KEY_6, Float.parseFloat(txt6.getText().toString()));
		    editor.putFloat(KEY_7, Float.parseFloat(txt7.getText().toString()));
		    editor.putFloat(KEY_8, Float.parseFloat(txt8.getText().toString()));   
		    editor.putFloat(KEY_9, Float.parseFloat(txt9.getText().toString()));   
		    editor.putFloat(KEY_10, Float.parseFloat(txt10.getText().toString()));   
		    editor.putFloat(KEY_11, Float.parseFloat(txt11.getText().toString()));  
		    editor.putFloat(KEY_12, Float.parseFloat(txt12.getText().toString()));   
		    editor.commit();
			finish();
		}
	}
	//Ĭ��
	class cmdReSet_Listener implements OnClickListener{
		public void onClick(View V){				
			txt6.setText("1");
			txt7.setText("2");
			txt8.setText("4");
			txt9.setText("8");
			txt10.setText("16");
			txt11.setText("32");
			txt12.setText("64");
		}		
	}
	//����
	class cmdReturn_Listener implements OnClickListener{
		public void onClick(View V){			
			finish();
		}		
	}
}