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

    //获取系统存储的联系人
    public void click1(View v){
    	//通过内容提供者访问联系人数据库
    	ContentResolver cr = getContentResolver();
    	Cursor cursorContactId = cr.query(Uri.parse("content://com.android.contacts/raw_contacts"), new String[]{"contact_id"},
    			null, null, null);
    	while(cursorContactId.moveToNext()){
    		//获取联系人id
    		String contactId = cursorContactId.getString(0);
    		
    		//实际中手机查询到的 contactId 有可能有null
			if(TextUtils.isEmpty(contactId)){
				continue;
			}
    		
			Cursor cursorData = cr.query(Uri.parse("content://com.android.contacts/data"), new String[]{"data1","mimetype"}, 
    				"contact_id = ?", new String[]{contactId}, null);
//    		//获取所有字段的名字
//    		String[] names = cursorData.getColumnNames();
//    		for (String string : names) {
//				System.out.println(string);
//			}
    		Contact contact = new Contact();
    		while(cursorData.moveToNext()){
    			String data1 = cursorData.getString(0);
    			String mimetype = cursorData.getString(1);
    			//通过对mimetype的判断，把data1存入到对应的属性
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
    	
    	//1.先查询raw_contacts表，获取最新联系人主键，然后主键+1，就是下一条要插入的联系人的id
    	Cursor cursorContactId = cr.query(Uri.parse("content://com.android.contacts/raw_contacts"), new String[]{"_id"},
    			null, null, null);
    	//默认联系人的id就是1
    	int contact_id = 1;
    	if(cursorContactId.moveToLast()){
    		//取得最新一条联系人的主键id
    		int _id = cursorContactId.getInt(0);
    		//主键+1
    		contact_id = ++_id;
    	}
    	ContentValues values = new ContentValues();
    	values.put("contact_id", contact_id);
    	//把联系人id插入raw_contacts表中
    	cr.insert(Uri.parse("content://com.android.contacts/raw_contacts"), values);
    	
    	//2.往data表中插入数据
    	//插入姓名
    	values.clear();
    	values.put("data1", "nihao");
    	values.put("mimetype", "vnd.android.cursor.item/name");
    	values.put("raw_contact_id", contact_id);
    	cr.insert(Uri.parse("content://com.android.contacts/data"), values);
    	//插入电话
    	values.clear();
    	values.put("data1", "16663666666");
    	values.put("mimetype", "vnd.android.cursor.item/phone_v2");
    	values.put("raw_contact_id", contact_id);
    	cr.insert(Uri.parse("content://com.android.contacts/data"), values);
    	//插入电子邮箱
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
