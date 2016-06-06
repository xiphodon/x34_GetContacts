package com.example.x34_getcontacts;

import com.example.x34_getcontacts.bean.Contact;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //��ȡϵͳ�洢����ϵ��
    public void click1(View v){
    	//ͨ�������ṩ�߷�����ϵ�����ݿ�
    	ContentResolver cr = getContentResolver();
    	Cursor cursorContactId = cr.query(Uri.parse("content://com.android.contacts/raw_contacts"), new String[]{"contact_id"},
    			null, null, null);
    	while(cursorContactId.moveToNext()){
    		//��ȡ��ϵ��id
    		String contactId = cursorContactId.getString(0);
    		
    		//ʵ�����ֻ���ѯ���� contactId �п�����null
			if(TextUtils.isEmpty(contactId)){
				continue;
			}
    		
			Cursor cursorData = cr.query(Uri.parse("content://com.android.contacts/data"), new String[]{"data1","mimetype"}, 
    				"contact_id = ?", new String[]{contactId}, null);
//    		//��ȡ�����ֶε�����
//    		String[] names = cursorData.getColumnNames();
//    		for (String string : names) {
//				System.out.println(string);
//			}
    		Contact contact = new Contact();
    		while(cursorData.moveToNext()){
    			String data1 = cursorData.getString(0);
    			String mimetype = cursorData.getString(1);
    			//ͨ����mimetype���жϣ���data1���뵽��Ӧ������
    			if("vnd.android.cursor.item/name".equals(mimetype)){
    				contact.setName(data1);
    			}else if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
    				contact.setPhone(data1);
    			}else if("vnd.android.cursor.item/email_v2".equals(mimetype)){
    				contact.setEmail(data1);
    			}
    		}
    		System.out.println(contact.toString());
    	}
    }
    

    public void click2(View v){
    	
    	ContentResolver cr = getContentResolver();
    	
    	//1.�Ȳ�ѯraw_contacts����ȡ������ϵ��������Ȼ������+1��������һ��Ҫ�������ϵ�˵�id
    	Cursor cursorContactId = cr.query(Uri.parse("content://com.android.contacts/raw_contacts"), new String[]{"_id"},
    			null, null, null);
    	//Ĭ����ϵ�˵�id����1
    	int contact_id = 1;
    	if(cursorContactId.moveToLast()){
    		//ȡ������һ����ϵ�˵�����id
    		int _id = cursorContactId.getInt(0);
    		//����+1
    		contact_id = ++_id;
    	}
    	ContentValues values = new ContentValues();
    	values.put("contact_id", contact_id);
    	//����ϵ��id����raw_contacts����
    	cr.insert(Uri.parse("content://com.android.contacts/raw_contacts"), values);
    	
    	//2.��data���в�������
    	//��������
    	values.clear();
    	values.put("data1", "nihao");
    	values.put("mimetype", "vnd.android.cursor.item/name");
    	values.put("raw_contact_id", contact_id);
    	cr.insert(Uri.parse("content://com.android.contacts/data"), values);
    	//����绰
    	values.clear();
    	values.put("data1", "16663666666");
    	values.put("mimetype", "vnd.android.cursor.item/phone_v2");
    	values.put("raw_contact_id", contact_id);
    	cr.insert(Uri.parse("content://com.android.contacts/data"), values);
    	//�����������
    	values.clear();
    	values.put("data1", "nh@126.com");
    	values.put("mimetype", "vnd.android.cursor.item/email_v2");
    	values.put("raw_contact_id", contact_id);
    	cr.insert(Uri.parse("content://com.android.contacts/data"), values);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
