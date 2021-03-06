
package com.reptilemobile.MultipleContactsPicker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author Konrad Gadzinowski
 * kgadzinowski@gmail.com
 */
public class ContactData implements Parcelable {

	public static final String CHECK_ALL = "CHECK_ALL";
	public static final String CONTACTS_DATA = "CONTACTS_DATA";
	public static final String PICK_CONTACTS_ACTION = "com.reptilemobile.MultipleContactsPicker.PICK_CONTACTS_ACTION";

	public String id = "";
	public String firstname = "";
	public String lastname = "";
	public String phoneNmb = "";
	public String email = "";
	public boolean checked = false;
	
	public ContactData(String id, String firstname, String lastname, String phoneNmb, String email, boolean checked ) {
		
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phoneNmb = phoneNmb;
		this.email = email;
		this.checked = checked;
		
	}
	
	public ContactData(Parcel in){
        String[] data = new String[6];

        in.readStringArray(data);
        this.id = data[0];
        this.firstname = data[1];
        this.lastname = data[2];
        this.phoneNmb = data[3];
        this.email = data[4];
        this.checked = data[5].equals("1") ? true : false;
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] {
			this.id,
			this.firstname,
			this.lastname,
			this.phoneNmb,
			this.email,
			this.checked ? "1" : "0"
		});
		
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ContactData createFromParcel(Parcel in) {
            return new ContactData(in); 
        }

        public ContactData[] newArray(int size) {
            return new ContactData[size];
        }
    };
	
	
}
