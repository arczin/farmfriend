package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.HoneycombMR1Util;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SimpleObject
@DesignerComponent(category = ComponentCategory.SOCIAL, description = "A button that, when clicked on, displays a list of the contacts to choose among. After the user has made a selection, the following properties will be set to information about the chosen contact: <ul>\n<li> <code>ContactName</code>: the contact's name </li>\n <li> <code>EmailAddress</code>: the contact's primary email address </li>\n <li> <code>ContactUri</code>: the contact's URI on the device </li>\n<li> <code>EmailAddressList</code>: a list of the contact's email addresses </li>\n <li> <code>PhoneNumber</code>: the contact's primary phone number (on Later Android Verisons)</li>\n <li> <code>PhoneNumberList</code>: a list of the contact's phone numbers (on Later Android Versions)</li>\n <li> <code>Picture</code>: the name of the file containing the contact's image, which can be used as a <code>Picture</code> property value for the <code>Image</code> or <code>ImageSprite</code> component.</li></ul>\n</p><p>Other properties affect the appearance of the button (<code>TextAlignment</code>, <code>BackgroundColor</code>, etc.) and whether it can be clicked on (<code>Enabled</code>).\n</p><p>The ContactPicker component might not work on all phones. For example, on Android systems before system 3.0, it cannot pick phone numbers, and the list of email addresses will contain only one email.", iconName = "images/contactPicker.png", version = 6)
@UsesPermissions(permissionNames = "android.permission.READ_CONTACTS")
public class ContactPicker extends Picker implements ActivityResultListener {
    private static String[] CONTACT_PROJECTION = null;
    private static String[] DATA_PROJECTION = null;
    private static final int EMAIL_INDEX = 1;
    private static final int NAME_INDEX = 0;
    private static final int PHONE_INDEX = 2;
    private static final String[] PROJECTION = {"name", "primary_email"};
    protected final Activity activityContext;
    protected String contactName;
    protected String contactPictureUri;
    protected String contactUri;
    protected String emailAddress;
    protected List emailAddressList;
    /* access modifiers changed from: private */
    public boolean havePermission;
    private final Uri intentUri;
    protected String phoneNumber;
    protected List phoneNumberList;

    public ContactPicker(ComponentContainer container) {
        this(container, Contacts.People.CONTENT_URI);
    }

    protected ContactPicker(ComponentContainer container, Uri intentUri2) {
        super(container);
        this.havePermission = false;
        this.activityContext = container.$context();
        if (SdkLevel.getLevel() >= 12 && intentUri2.equals(Contacts.People.CONTENT_URI)) {
            this.intentUri = HoneycombMR1Util.getContentUri();
        } else if (SdkLevel.getLevel() < 12 || !intentUri2.equals(Contacts.Phones.CONTENT_URI)) {
            this.intentUri = intentUri2;
        } else {
            this.intentUri = HoneycombMR1Util.getPhoneContentUri();
        }
    }

    public void click() {
        if (!this.havePermission) {
            this.container.$form().askPermission("android.permission.READ_CONTACTS", new PermissionResultHandler() {
                public void HandlePermissionResponse(String permission, boolean granted) {
                    if (granted) {
                        ContactPicker.this.havePermission = true;
                        ContactPicker.this.click();
                        return;
                    }
                    ContactPicker.this.container.$form().dispatchPermissionDeniedEvent((Component) ContactPicker.this, "Click", "android.permission.READ_CONTACTS");
                }
            });
        } else {
            super.click();
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String Picture() {
        return ensureNotNull(this.contactPictureUri);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ContactName() {
        return ensureNotNull(this.contactName);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String EmailAddress() {
        return ensureNotNull(this.emailAddress);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "URI that specifies the location of the contact on the device.")
    public String ContactUri() {
        return ensureNotNull(this.contactUri);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public List EmailAddressList() {
        return ensureNotNull(this.emailAddressList);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String PhoneNumber() {
        return ensureNotNull(this.phoneNumber);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public List PhoneNumberList() {
        return ensureNotNull(this.phoneNumberList);
    }

    @SimpleFunction(description = "view a contact via its URI")
    public void ViewContact(String uri) {
        if (this.contactUri != null) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
            if (intent.resolveActivity(this.activityContext.getPackageManager()) != null) {
                this.activityContext.startActivity(intent);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Intent getIntent() {
        return new Intent("android.intent.action.PICK", this.intentUri);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00d7, code lost:
        if (r10 != null) goto L_0x00ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ec, code lost:
        if (r10 != null) goto L_0x00ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00ee, code lost:
        r10.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void resultReturned(int r13, int r14, android.content.Intent r15) {
        /*
            r12 = this;
            int r0 = r12.requestCode
            if (r13 != r0) goto L_0x0100
            r0 = -1
            if (r14 != r0) goto L_0x0100
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "received intent is "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r15)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "ContactPicker"
            android.util.Log.i(r1, r0)
            android.net.Uri r0 = r15.getData()
            java.lang.String r2 = ""
            int r3 = com.google.appinventor.components.runtime.util.SdkLevel.getLevel()
            r4 = 12
            if (r3 < r4) goto L_0x0031
            java.lang.String r2 = "//com.android.contacts/contact"
            r8 = r2
            goto L_0x0034
        L_0x0031:
            java.lang.String r2 = "//contacts/people"
            r8 = r2
        L_0x0034:
            boolean r2 = r12.checkContactUri(r0, r8)
            if (r2 == 0) goto L_0x00fd
            r9 = 0
            r10 = 0
            int r2 = com.google.appinventor.components.runtime.util.SdkLevel.getLevel()     // Catch:{ Exception -> 0x00dc }
            if (r2 < r4) goto L_0x0076
            java.lang.String[] r2 = com.google.appinventor.components.runtime.util.HoneycombMR1Util.getContactProjection()     // Catch:{ Exception -> 0x00dc }
            CONTACT_PROJECTION = r2     // Catch:{ Exception -> 0x00dc }
            android.app.Activity r2 = r12.activityContext     // Catch:{ Exception -> 0x00dc }
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch:{ Exception -> 0x00dc }
            java.lang.String[] r4 = CONTACT_PROJECTION     // Catch:{ Exception -> 0x00dc }
            r6 = 0
            r7 = 0
            r5 = 0
            r3 = r0
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00dc }
            r9 = r2
            java.lang.String r2 = r12.postHoneycombGetContactNameAndPicture(r9)     // Catch:{ Exception -> 0x00dc }
            java.lang.String[] r3 = com.google.appinventor.components.runtime.util.HoneycombMR1Util.getDataProjection()     // Catch:{ Exception -> 0x00dc }
            DATA_PROJECTION = r3     // Catch:{ Exception -> 0x00dc }
            android.app.Activity r3 = r12.activityContext     // Catch:{ Exception -> 0x00dc }
            java.lang.String[] r4 = DATA_PROJECTION     // Catch:{ Exception -> 0x00dc }
            android.database.Cursor r3 = com.google.appinventor.components.runtime.util.HoneycombMR1Util.getDataCursor(r2, r3, r4)     // Catch:{ Exception -> 0x00dc }
            r10 = r3
            r12.postHoneycombGetContactEmailAndPhone(r10)     // Catch:{ Exception -> 0x00dc }
            java.lang.String r3 = r0.toString()     // Catch:{ Exception -> 0x00dc }
            r12.contactUri = r3     // Catch:{ Exception -> 0x00dc }
            goto L_0x008a
        L_0x0076:
            android.app.Activity r2 = r12.activityContext     // Catch:{ Exception -> 0x00dc }
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch:{ Exception -> 0x00dc }
            java.lang.String[] r4 = PROJECTION     // Catch:{ Exception -> 0x00dc }
            r6 = 0
            r7 = 0
            r5 = 0
            r3 = r0
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00dc }
            r9 = r2
            r12.preHoneycombGetContactInfo(r9, r0)     // Catch:{ Exception -> 0x00dc }
        L_0x008a:
            java.lang.String r2 = r12.contactName     // Catch:{ Exception -> 0x00dc }
            java.lang.String r3 = r12.emailAddress     // Catch:{ Exception -> 0x00dc }
            java.lang.String r4 = r12.contactUri     // Catch:{ Exception -> 0x00dc }
            java.lang.String r5 = r12.phoneNumber     // Catch:{ Exception -> 0x00dc }
            java.lang.String r6 = r12.contactPictureUri     // Catch:{ Exception -> 0x00dc }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00dc }
            r7.<init>()     // Catch:{ Exception -> 0x00dc }
            java.lang.String r11 = "Contact name = "
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ Exception -> 0x00dc }
            java.lang.StringBuilder r2 = r7.append(r2)     // Catch:{ Exception -> 0x00dc }
            java.lang.String r7 = ", email address = "
            java.lang.StringBuilder r2 = r2.append(r7)     // Catch:{ Exception -> 0x00dc }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00dc }
            java.lang.String r3 = ",contact Uri = "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00dc }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x00dc }
            java.lang.String r3 = ", phone number = "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00dc }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x00dc }
            java.lang.String r3 = ", contactPhotoUri = "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x00dc }
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ Exception -> 0x00dc }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00dc }
            android.util.Log.i(r1, r2)     // Catch:{ Exception -> 0x00dc }
            if (r9 == 0) goto L_0x00d7
            r9.close()
        L_0x00d7:
            if (r10 == 0) goto L_0x00fd
            goto L_0x00ee
        L_0x00da:
            r1 = move-exception
            goto L_0x00f2
        L_0x00dc:
            r2 = move-exception
            java.lang.String r3 = "checkContactUri failed: D"
            android.util.Log.i(r1, r3)     // Catch:{ all -> 0x00da }
            r1 = 1107(0x453, float:1.551E-42)
            r12.puntContactSelection(r1)     // Catch:{ all -> 0x00da }
            if (r9 == 0) goto L_0x00ec
            r9.close()
        L_0x00ec:
            if (r10 == 0) goto L_0x00fd
        L_0x00ee:
            r10.close()
            goto L_0x00fd
        L_0x00f2:
            if (r9 == 0) goto L_0x00f7
            r9.close()
        L_0x00f7:
            if (r10 == 0) goto L_0x00fc
            r10.close()
        L_0x00fc:
            throw r1
        L_0x00fd:
            r12.AfterPicking()
        L_0x0100:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.ContactPicker.resultReturned(int, int, android.content.Intent):void");
    }

    public void preHoneycombGetContactInfo(Cursor contactCursor, Uri theContactUri) {
        List list;
        if (contactCursor.moveToFirst()) {
            this.contactName = guardCursorGetString(contactCursor, 0);
            this.emailAddress = getEmailAddress(guardCursorGetString(contactCursor, 1));
            this.contactUri = theContactUri.toString();
            this.contactPictureUri = theContactUri.toString();
            if (this.emailAddress.equals("")) {
                list = new ArrayList();
            } else {
                list = Arrays.asList(new String[]{this.emailAddress});
            }
            this.emailAddressList = list;
        }
    }

    public String postHoneycombGetContactNameAndPicture(Cursor contactCursor) {
        if (!contactCursor.moveToFirst()) {
            return "";
        }
        int ID_INDEX = HoneycombMR1Util.getIdIndex(contactCursor);
        int NAME_INDEX2 = HoneycombMR1Util.getNameIndex(contactCursor);
        int THUMBNAIL_INDEX = HoneycombMR1Util.getThumbnailIndex(contactCursor);
        int PHOTO_INDEX = HoneycombMR1Util.getPhotoIndex(contactCursor);
        String id = guardCursorGetString(contactCursor, ID_INDEX);
        this.contactName = guardCursorGetString(contactCursor, NAME_INDEX2);
        this.contactPictureUri = guardCursorGetString(contactCursor, THUMBNAIL_INDEX);
        Log.i("ContactPicker", "photo_uri=" + guardCursorGetString(contactCursor, PHOTO_INDEX));
        return id;
    }

    public void postHoneycombGetContactEmailAndPhone(Cursor dataCursor) {
        this.phoneNumber = "";
        this.emailAddress = "";
        List<String> phoneListToStore = new ArrayList<>();
        List<String> emailListToStore = new ArrayList<>();
        if (dataCursor.moveToFirst()) {
            int PHONE_INDEX2 = HoneycombMR1Util.getPhoneIndex(dataCursor);
            int EMAIL_INDEX2 = HoneycombMR1Util.getEmailIndex(dataCursor);
            int MIME_INDEX = HoneycombMR1Util.getMimeIndex(dataCursor);
            String phoneType = HoneycombMR1Util.getPhoneType();
            String emailType = HoneycombMR1Util.getEmailType();
            while (!dataCursor.isAfterLast()) {
                String type = guardCursorGetString(dataCursor, MIME_INDEX);
                if (type.contains(phoneType)) {
                    phoneListToStore.add(guardCursorGetString(dataCursor, PHONE_INDEX2));
                } else if (type.contains(emailType)) {
                    emailListToStore.add(guardCursorGetString(dataCursor, EMAIL_INDEX2));
                } else {
                    Log.i("ContactPicker", "Type mismatch: " + type + " not " + phoneType + " or " + emailType);
                }
                dataCursor.moveToNext();
            }
        }
        if (phoneListToStore.isEmpty() == 0) {
            this.phoneNumber = phoneListToStore.get(0);
        }
        if (!emailListToStore.isEmpty()) {
            this.emailAddress = emailListToStore.get(0);
        }
        this.phoneNumberList = phoneListToStore;
        this.emailAddressList = emailListToStore;
    }

    /* access modifiers changed from: protected */
    public boolean checkContactUri(Uri suspectUri, String requiredPattern) {
        Log.i("ContactPicker", "contactUri is " + suspectUri);
        if (suspectUri == null || !"content".equals(suspectUri.getScheme())) {
            Log.i("ContactPicker", "checkContactUri failed: A");
            puntContactSelection(ErrorMessages.ERROR_PHONE_UNSUPPORTED_CONTACT_PICKER);
            return false;
        } else if (suspectUri.getSchemeSpecificPart().startsWith(requiredPattern)) {
            return true;
        } else {
            Log.i("ContactPicker", "checkContactUri failed: C");
            Log.i("ContactPicker", suspectUri.getPath());
            puntContactSelection(ErrorMessages.ERROR_PHONE_UNSUPPORTED_CONTACT_PICKER);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void puntContactSelection(int errorNumber) {
        this.contactName = "";
        this.emailAddress = "";
        this.contactPictureUri = "";
        this.container.$form().dispatchErrorOccurredEvent(this, "", errorNumber, new Object[0]);
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public String getEmailAddress(String emailId) {
        try {
            String data = "";
            String[] projection = {"data"};
            Cursor cursor = this.activityContext.getContentResolver().query(Contacts.ContactMethods.CONTENT_EMAIL_URI, projection, "contact_methods._id = " + Integer.parseInt(emailId), (String[]) null, (String) null);
            try {
                if (cursor.moveToFirst()) {
                    data = guardCursorGetString(cursor, 0);
                }
                cursor.close();
                return ensureNotNull(data);
            } catch (Throwable th) {
                cursor.close();
                throw th;
            }
        } catch (NumberFormatException e) {
            return "";
        }
    }

    /* access modifiers changed from: protected */
    public String guardCursorGetString(Cursor cursor, int index) {
        String result;
        try {
            result = cursor.getString(index);
        } catch (Exception e) {
            result = "";
        }
        return ensureNotNull(result);
    }

    /* access modifiers changed from: protected */
    public String ensureNotNull(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

    /* access modifiers changed from: protected */
    public List ensureNotNull(List value) {
        if (value == null) {
            return new ArrayList();
        }
        return value;
    }
}
