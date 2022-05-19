package com.hollysmart.platformsdk.contacts;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;


import com.hollysmart.platformsdk.utils.Mlog;

import java.util.ArrayList;

public class ContactUtils {
    public static ArrayList<SystemContacts> getAllContacts(Context context) {
        ArrayList<SystemContacts> contacts = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            //获取联系人姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Mlog.d("联系人：" + name);


            //获取联系人电话号码
            Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);

            if (phoneCursor.getCount() == 0){
                //新建一个联系人实例
                SystemContacts temp = new SystemContacts();
                temp.name = name;
                contacts.add(temp);
            }else {
                while (phoneCursor.moveToNext()) {
                    SystemContacts temp = new SystemContacts();
                    temp.name = name;
                    String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phone = phone.replace("-", "");
                    phone = phone.replace(" ", "");
                    temp.phone = phone;
                    Mlog.d("联系人电话：" + phone);
                    contacts.add(temp);
                }
            }
            //记得要把cursor给close掉
            phoneCursor.close();
        }
        cursor.close();
        return contacts;
    }
}