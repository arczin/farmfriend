package com.google.appinventor.components.runtime;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.runtime.util.EclairUtil;
import com.google.appinventor.components.runtime.util.TextViewUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;

@SimpleObject
public abstract class TextBoxBase extends AndroidViewComponent implements View.OnFocusChangeListener, TextWatcher, AccessibleComponent {
    private int backgroundColor;
    private boolean bold;
    private Drawable defaultTextBoxDrawable;
    private String fontTypeface;
    private String hint;
    private int hintColor = 0;
    private int hintColorDefault;
    private boolean isBigText = false;
    private boolean isHighContrast = false;
    private boolean italic;
    private String lastText = "";
    private int textAlignment;
    private int textColor;
    protected final EditText view;

    public TextBoxBase(ComponentContainer container, EditText textview) {
        super(container);
        this.view = textview;
        this.hintColorDefault = this.view.getCurrentHintTextColor();
        if (Build.VERSION.SDK_INT >= 24) {
            EclairUtil.disableSuggestions(textview);
        }
        this.view.setOnFocusChangeListener(this);
        this.defaultTextBoxDrawable = this.view.getBackground();
        container.$add(this);
        container.setChildWidth(this, ComponentConstants.TEXTBOX_PREFERRED_WIDTH);
        TextAlignment(0);
        Enabled(true);
        this.fontTypeface = Component.TYPEFACE_DEFAULT;
        TextViewUtil.setFontTypeface(container.$form(), this.view, this.fontTypeface, this.bold, this.italic);
        FontSize(14.0f);
        Hint("");
        if (this.isHighContrast || container.$form().HighContrast()) {
            this.view.setHintTextColor(-256);
        } else {
            this.view.setHintTextColor(this.hintColorDefault);
        }
        Text("");
        this.view.addTextChangedListener(this);
        TextColor(0);
        BackgroundColor(0);
    }

    public View getView() {
        return this.view;
    }

    @SimpleEvent(description = "Event raised when the %type% is selected for input, such as by the user touching it.")
    public void GotFocus() {
        EventDispatcher.dispatchEvent(this, "GotFocus", new Object[0]);
    }

    @SimpleEvent(description = "Event raised when the %type% is no longer selected for input, such as if the user touches a different text box.")
    public void LostFocus() {
        EventDispatcher.dispatchEvent(this, "LostFocus", new Object[0]);
    }

    @SimpleEvent(description = "Event raised when the text of the %type% is changed, either by the user or the program.")
    public void TextChanged() {
        EventDispatcher.dispatchEvent(this, "TextChanged", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Whether the text should be left justified, centered, or right justified.  By default, text is left justified.", userVisible = false)
    public int TextAlignment() {
        return this.textAlignment;
    }

    @DesignerProperty(defaultValue = "0", editorType = "textalignment")
    @SimpleProperty(userVisible = false)
    public void TextAlignment(int alignment) {
        this.textAlignment = alignment;
        TextViewUtil.setAlignment(this.view, alignment, false);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The background color of the input box.  You can choose a color by name in the Designer or in the Blocks Editor.  The default background color is 'default' (shaded 3-D look).")
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @DesignerProperty(defaultValue = "&H00000000", editorType = "color")
    @SimpleProperty
    public void BackgroundColor(int argb) {
        this.backgroundColor = argb;
        if (argb != 0) {
            TextViewUtil.setBackgroundColor(this.view, argb);
        } else if (this.isHighContrast || this.container.$form().$form().HighContrast()) {
            TextViewUtil.setBackgroundColor(this.view, -16777216);
        } else {
            ViewUtil.setBackgroundDrawable(this.view, this.defaultTextBoxDrawable);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the user can enter text into the %type%.  By default, this is true.")
    public boolean Enabled() {
        return TextViewUtil.isEnabled(this.view);
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty
    public void Enabled(boolean enabled) {
        TextViewUtil.setEnabled(this.view, enabled);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Whether the font for the text should be bold.  By default, it is not.", userVisible = false)
    public boolean FontBold() {
        return this.bold;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(userVisible = false)
    public void FontBold(boolean bold2) {
        this.bold = bold2;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, bold2, this.italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Whether the text should appear in italics.  By default, it does not.", userVisible = false)
    public boolean FontItalic() {
        return this.italic;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(userVisible = false)
    public void FontItalic(boolean italic2) {
        this.italic = italic2;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, this.bold, italic2);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The font size for the text.  By default, it is 14.0 points.")
    public float FontSize() {
        return TextViewUtil.getFontSize(this.view, this.container.$context());
    }

    @DesignerProperty(defaultValue = "14.0", editorType = "non_negative_float")
    @SimpleProperty
    public void FontSize(float size) {
        if (size != 14.0f || !this.container.$form().BigDefaultText()) {
            TextViewUtil.setFontSize(this.view, size);
        } else {
            TextViewUtil.setFontSize(this.view, 24.0f);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The font for the text.  The value can be changed in the Designer.", userVisible = false)
    public String FontTypeface() {
        return this.fontTypeface;
    }

    @DesignerProperty(defaultValue = "0", editorType = "typeface")
    @SimpleProperty(userVisible = false)
    public void FontTypeface(String typeface) {
        this.fontTypeface = typeface;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, this.bold, this.italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Text that should appear faintly in the %type% to provide a hint as to what the user should enter.  This can only be seen if the Text property is empty.")
    public String Hint() {
        return this.hint;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void Hint(String hint2) {
        this.hint = hint2;
        this.view.setHint(hint2);
        this.view.invalidate();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Specifies the color of the hint of the %type%.")
    public int HintColor() {
        return this.hintColor;
    }

    @DesignerProperty(defaultValue = "&HFF888888", editorType = "color")
    @SimpleProperty
    public void HintColor(int hintColor2) {
        this.hintColor = hintColor2;
        if (hintColor2 != 0) {
            this.view.setHintTextColor(hintColor2);
        } else if (this.isHighContrast || this.container.$form().HighContrast()) {
            this.view.setHintTextColor(-256);
        } else {
            this.view.setHintTextColor(this.hintColorDefault);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String Text() {
        return TextViewUtil.getText(this.view);
    }

    @DesignerProperty(defaultValue = "", editorType = "textArea")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The text in the %type%, which can be set by the programmer in the Designer or Blocks Editor, or it can be entered by the user (unless the <code>Enabled</code> property is false).")
    public void Text(String text) {
        TextViewUtil.setText(this.view, text);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The color for the text.  You can choose a color by name in the Designer or in the Blocks Editor.  The default text color is black.")
    public int TextColor() {
        return this.textColor;
    }

    @DesignerProperty(defaultValue = "&H00000000", editorType = "color")
    @SimpleProperty
    public void TextColor(int argb) {
        this.textColor = argb;
        if (argb != 0) {
            TextViewUtil.setTextColor(this.view, argb);
            return;
        }
        int i = -1;
        if (this.isHighContrast || this.container.$form().HighContrast()) {
            TextViewUtil.setTextColor(this.view, -1);
            return;
        }
        EditText editText = this.view;
        if (!this.container.$form().isDarkTheme()) {
            i = -16777216;
        }
        TextViewUtil.setTextColor(editText, i);
    }

    @SimpleFunction(description = "Repositions the cursor of the %type% before the character at the given 1-indexed position. If the given position is larger than the length of the %type%, the cursor will be moved to the end of the text; and if the given position is smaller or equal to 1, the cursor will be moved to the start.")
    public void MoveCursorTo(int position) {
        int len = this.view.getText().toString().length();
        if (position > len) {
            this.view.setSelection(len);
        } else if (position <= 1) {
            this.view.setSelection(0);
        } else {
            this.view.setSelection(position - 1);
        }
    }

    @SimpleFunction(description = "Repositions the cursor to the end of the %type%'s text.")
    public void MoveCursorToEnd() {
        MoveCursorTo(this.view.getText().length() + 1);
    }

    @SimpleFunction(description = "Repositions the cursor to the start of the %type%'s text.")
    public void MoveCursorToStart() {
        MoveCursorTo(1);
    }

    @SimpleFunction(description = "Sets the %type% active.")
    public void RequestFocus() {
        this.view.requestFocus();
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!this.lastText.equals(this.view.getText().toString())) {
            TextChanged();
        }
        this.lastText = s.toString();
    }

    public void afterTextChanged(Editable s) {
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onFocusChange(View previouslyFocused, boolean gainFocus) {
        if (gainFocus) {
            GotFocus();
        } else {
            LostFocus();
        }
    }

    public void setHighContrast(boolean isHighContrast2) {
        int i = -16777216;
        if (this.backgroundColor == 0) {
            if (isHighContrast2) {
                TextViewUtil.setBackgroundColor(this.view, -16777216);
            } else {
                ViewUtil.setBackgroundDrawable(this.view, this.defaultTextBoxDrawable);
            }
        }
        if (this.textColor != 0) {
            return;
        }
        if (isHighContrast2) {
            TextViewUtil.setTextColor(this.view, -1);
            this.view.setHintTextColor(-256);
            return;
        }
        EditText editText = this.view;
        if (this.container.$form().isDarkTheme()) {
            i = -1;
        }
        TextViewUtil.setTextColor(editText, i);
        this.view.setHintTextColor(this.hintColorDefault);
    }

    public boolean getHighContrast() {
        return this.isHighContrast;
    }

    public void setLargeFont(boolean isLargeFont) {
        if (((double) TextViewUtil.getFontSize(this.view, this.container.$context())) != 24.0d && TextViewUtil.getFontSize(this.view, this.container.$context()) != 14.0f) {
            return;
        }
        if (isLargeFont) {
            TextViewUtil.setFontSize(this.view, 24.0f);
        } else {
            TextViewUtil.setFontSize(this.view, 14.0f);
        }
    }

    public boolean getLargeFont() {
        return this.isBigText;
    }
}
