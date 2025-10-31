package com.google.appinventor.components.runtime;

import android.content.ContentUris;
import android.database.Cursor;
import android.provider.Contacts;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.HoneycombMR1Util;
import java.util.ArrayList;
import java.util.List;

@SimpleObject
@DesignerComponent(category = ComponentCategory.SOCIAL, description = "A button that, when clicked on, displays a list of the contacts' phone numbers to choose among. After the user has made a selection, the following properties will be set to information about the chosen contact: <ul>\n<li> <code>ContactName</code>: the contact's name </li>\n <li> <code>PhoneNumber</code>: the contact's phone number </li>\n <li> <code>EmailAddress</code>: the contact's email address </li> <li> <code>Picture</code>: the name of the file containing the contact's image, which can be used as a <code>Picture</code> property value for the <code>Image</code> or <code>ImageSprite</code> component.</li></ul>\n</p><p>Other properties affect the appearance of the button (<code>TextAlignment</code>, <code>BackgroundColor</code>, etc.) and whether it can be clicked on (<code>Enabled</code>).</p>\n<p>The PhoneNumberPicker component may not work on all Android devices. For example, on Android systems before system 3.0, the returned lists of phone numbers and email addresses will be empty.\n", iconName = "images/phoneNumberPicker.png", version = 4)
@UsesPermissions(permissionNames = "android.permission.READ_CONTACTS")
public class PhoneNumberPicker extends ContactPicker {
    private static String[] DATA_PROJECTION = null;
    private static final int EMAIL_INDEX = 3;
    private static final String LOG_TAG = "PhoneNumberPicker";
    private static final int NAME_INDEX = 0;
    private static String[] NAME_PROJECTION = null;
    private static final int NUMBER_INDEX = 1;
    private static final int PERSON_INDEX = 2;
    private static final String[] PROJECTION = {"name", "number", "person", "primary_email"};

    public PhoneNumberPicker(ComponentContainer container) {
        super(container, Contacts.Phones.CONTENT_URI);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String PhoneNumber() {
        return ensureNotNull(this.phoneNumber);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00c5, code lost:
        if (r10 != null) goto L_0x00dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00da, code lost:
        if (r10 != null) goto L_0x00dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00dc, code lost:
        r10.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void resultReturned(int r12, int r13, android.content.Intent r14) {
        /*
            r11 = this;
            int r0 = r11.requestCode
            if (r12 != r0) goto L_0x00ee
            r0 = -1
            if (r13 != r0) goto L_0x00ee
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "received intent is "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r14)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "PhoneNumberPicker"
            android.util.Log.i(r1, r0)
            android.net.Uri r0 = r14.getData()
            java.lang.String r2 = ""
            int r3 = com.google.appinventor.components.runtime.util.SdkLevel.getLevel()
            r4 = 12
            if (r3 < r4) goto L_0x0031
            java.lang.String r2 = "//com.android.contacts/data"
            r8 = r2
            goto L_0x0034
        L_0x0031:
            java.lang.String r2 = "//contacts/phones"
            r8 = r2
        L_0x0034:
            boolean r2 = r11.checkContactUri(r0, r8)
            if (r2 == 0) goto L_0x00eb
            r9 = 0
            r10 = 0
            int r2 = com.google.appinventor.components.runtime.util.SdkLevel.getLevel()     // Catch:{ Exception -> 0x00ca }
            if (r2 < r4) goto L_0x0070
            java.lang.String[] r2 = com.google.appinventor.components.runtime.util.HoneycombMR1Util.getNameProjection()     // Catch:{ Exception -> 0x00ca }
            NAME_PROJECTION = r2     // Catch:{ Exception -> 0x00ca }
            android.app.Activity r2 = r11.activityContext     // Catch:{ Exception -> 0x00ca }
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch:{ Exception -> 0x00ca }
            java.lang.String[] r4 = NAME_PROJECTION     // Catch:{ Exception -> 0x00ca }
            r6 = 0
            r7 = 0
            r5 = 0
            r3 = r0
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00ca }
            r9 = r2
            java.lang.String r2 = r11.postHoneycombGetContactNameAndPicture(r9)     // Catch:{ Exception -> 0x00ca }
            java.lang.String[] r3 = com.google.appinventor.components.runtime.util.HoneycombMR1Util.getDataProjection()     // Catch:{ Exception -> 0x00ca }
            DATA_PROJECTION = r3     // Catch:{ Exception -> 0x00ca }
            android.app.Activity r3 = r11.activityContext     // Catch:{ Exception -> 0x00ca }
            java.lang.String[] r4 = DATA_PROJECTION     // Catch:{ Exception -> 0x00ca }
            android.database.Cursor r3 = com.google.appinventor.components.runtime.util.HoneycombMR1Util.getDataCursor(r2, r3, r4)     // Catch:{ Exception -> 0x00ca }
            r10 = r3
            r11.postHoneycombGetContactEmailsAndPhones(r10)     // Catch:{ Exception -> 0x00ca }
            goto L_0x0084
        L_0x0070:
            android.app.Activity r2 = r11.activityContext     // Catch:{ Exception -> 0x00ca }
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch:{ Exception -> 0x00ca }
            java.lang.String[] r4 = PROJECTION     // Catch:{ Exception -> 0x00ca }
            r6 = 0
            r7 = 0
            r5 = 0
            r3 = r0
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00ca }
            r9 = r2
            r11.preHoneycombGetContactInfo(r9)     // Catch:{ Exception -> 0x00ca }
        L_0x0084:
            java.lang.String r2 = r11.contactName     // Catch:{ Exception -> 0x00ca }
            java.lang.String r3 = r11.phoneNumber     // Catch:{ Exception -> 0x00ca }
            java.lang.String r4 = r11.emailAddress     // Catch:{ Exception -> 0x00ca }
            java.lang.String r5 = r11.contactPictureUri     // Catch:{ Exception -> 0x00ca }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ca }
            r6.<init>()     // Catch:{ Exception -> 0x00ca }
            java.lang.String r7 = "Contact name = "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x00ca }
            java.lang.StringBuilder r2 = r6.append(r2)     // Catch:{ Exception -> 0x00ca }
            java.lang.String r6 = ", phone number = "
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ Exception -> 0x00ca }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00ca }
            java.lang.String r3 = ", emailAddress = "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00ca }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x00ca }
            java.lang.String r3 = ", contactPhotoUri = "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00ca }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x00ca }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00ca }
            android.util.Log.i(r1, r2)     // Catch:{ Exception -> 0x00ca }
            if (r9 == 0) goto L_0x00c5
            r9.close()
        L_0x00c5:
            if (r10 == 0) goto L_0x00eb
            goto L_0x00dc
        L_0x00c8:
            r1 = move-exception
            goto L_0x00e0
        L_0x00ca:
            r2 = move-exception
            java.lang.String r3 = "Exception in resultReturned"
            android.util.Log.e(r1, r3, r2)     // Catch:{ all -> 0x00c8 }
            r1 = 1107(0x453, float:1.551E-42)
            r11.puntContactSelection(r1)     // Catch:{ all -> 0x00c8 }
            if (r9 == 0) goto L_0x00da
            r9.close()
        L_0x00da:
            if (r10 == 0) goto L_0x00eb
        L_0x00dc:
            r10.close()
            goto L_0x00eb
        L_0x00e0:
            if (r9 == 0) goto L_0x00e5
            r9.close()
        L_0x00e5:
            if (r10 == 0) goto L_0x00ea
            r10.close()
        L_0x00ea:
            throw r1
        L_0x00eb:
            r11.AfterPicking()
        L_0x00ee:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.PhoneNumberPicker.resultReturned(int, int, android.content.Intent):void");
    }

    public void preHoneycombGetContactInfo(Cursor cursor) {
        if (cursor.moveToFirst()) {
            this.contactName = guardCursorGetString(cursor, 0);
            this.phoneNumber = guardCursorGetString(cursor, 1);
            this.contactPictureUri = ContentUris.withAppendedId(Contacts.People.CONTENT_URI, (long) cursor.getInt(2)).toString();
            this.emailAddress = getEmailAddress(guardCursorGetString(cursor, 3));
        }
    }

    public String postHoneycombGetContactNameAndPicture(Cursor contactCursor) {
        if (!contactCursor.moveToFirst()) {
            return "";
        }
        int CONTACT_ID_INDEX = HoneycombMR1Util.getContactIdIndex(contactCursor);
        int NAME_INDEX2 = HoneycombMR1Util.getNameIndex(contactCursor);
        int PHOTO_INDEX = HoneycombMR1Util.getThumbnailIndex(contactCursor);
        this.phoneNumber = guardCursorGetString(contactCursor, HoneycombMR1Util.getPhoneIndex(contactCursor));
        String id = guardCursorGetString(contactCursor, CONTACT_ID_INDEX);
        this.contactName = guardCursorGetString(contactCursor, NAME_INDEX2);
        this.contactPictureUri = guardCursorGetString(contactCursor, PHOTO_INDEX);
        return id;
    }

    public void postHoneycombGetContactEmailsAndPhones(Cursor dataCursor) {
        List<String> phoneListToStore = new ArrayList<>();
        List<String> emailListToStore = new ArrayList<>();
        if (dataCursor.moveToFirst()) {
            int PHONE_INDEX = HoneycombMR1Util.getPhoneIndex(dataCursor);
            int EMAIL_INDEX2 = HoneycombMR1Util.getEmailIndex(dataCursor);
            int MIME_INDEX = HoneycombMR1Util.getMimeIndex(dataCursor);
            String phoneType = HoneycombMR1Util.getPhoneType();
            String emailType = HoneycombMR1Util.getEmailType();
            while (!dataCursor.isAfterLast()) {
                String type = guardCursorGetString(dataCursor, MIME_INDEX);
                if (type.contains(phoneType)) {
                    phoneListToStore.add(guardCursorGetString(dataCursor, PHONE_INDEX));
                } else if (type.contains(emailType)) {
                    emailListToStore.add(guardCursorGetString(dataCursor, EMAIL_INDEX2));
                } else {
                    Log.i("ContactPicker", "Type mismatch: " + type + " not " + phoneType + " or " + emailType);
                }
                dataCursor.moveToNext();
            }
            this.phoneNumberList = phoneListToStore;
            this.emailAddressList = emailListToStore;
            if (!this.emailAddressList.isEmpty()) {
                this.emailAddress = (String) this.emailAddressList.get(0);
            } else {
                this.emailAddress = "";
            }
        }
    }
}
