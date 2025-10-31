package appinventor.ai_arnav_chhajed_000.FarmFriend;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.Player;
import com.google.appinventor.components.runtime.Sound;
import com.google.appinventor.components.runtime.TinyDB;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.errors.PermissionException;
import com.google.appinventor.components.runtime.errors.StopBlocksExecution;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.RetValManager;
import com.google.appinventor.components.runtime.util.RuntimeErrorAlert;
import com.google.youngandroid.runtime;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lang.Promise;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.require;
import kawa.standard.throw_name;

/* compiled from: Screen1.yail */
public class Screen1 extends Form implements Runnable {
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Screen1").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final FString Lit100 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit101 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement7").readResolve());
    static final FString Lit102 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit103 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement6").readResolve());
    static final FString Lit105 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit106 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("Label3").readResolve());
    static final IntNum Lit108 = IntNum.make(-1080);
    static final FString Lit109 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit11;
    static final FString Lit110 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement8").readResolve());
    static final IntNum Lit112 = IntNum.make(28);
    static final FString Lit113 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit114 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement12").readResolve());
    static final FString Lit116 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit117 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve());
    static final IntNum Lit119 = IntNum.make(-1090);
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("number").readResolve());
    static final FString Lit120 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit121 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("Label1").readResolve());
    static final FString Lit123 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit124 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit125 = ((SimpleSymbol) new SimpleSymbol("Crop").readResolve());
    static final IntNum Lit126;
    static final SimpleSymbol Lit127 = ((SimpleSymbol) new SimpleSymbol("FontBold").readResolve());
    static final IntNum Lit128 = IntNum.make(-1065);
    static final FString Lit129 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("ActionBar").readResolve());
    static final PairWithPosition Lit130 = PairWithPosition.make(Lit16, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Screen1.yail", 954463);
    static final SimpleSymbol Lit131 = ((SimpleSymbol) new SimpleSymbol("Crop$Click").readResolve());
    static final SimpleSymbol Lit132 = ((SimpleSymbol) new SimpleSymbol("Crop$TouchDown").readResolve());
    static final SimpleSymbol Lit133 = ((SimpleSymbol) new SimpleSymbol("Crop$TouchUp").readResolve());
    static final FString Lit134 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("Button1").readResolve());
    static final IntNum Lit136 = IntNum.make(1107296256);
    static final IntNum Lit137 = IntNum.make(66);
    static final SimpleSymbol Lit138 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit139;
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve());
    static final FString Lit140 = new FString("com.google.appinventor.components.runtime.Button");
    static final FString Lit141 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit142 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement2").readResolve());
    static final IntNum Lit143 = IntNum.make(30);
    static final FString Lit144 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit145 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit146 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement13").readResolve());
    static final FString Lit147 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit148 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit149 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve());
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final IntNum Lit150 = IntNum.make(-1090);
    static final FString Lit151 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit152 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit153 = ((SimpleSymbol) new SimpleSymbol("Label2").readResolve());
    static final FString Lit154 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit155 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit156 = ((SimpleSymbol) new SimpleSymbol("Soil").readResolve());
    static final IntNum Lit157;
    static final IntNum Lit158;
    static final IntNum Lit159 = IntNum.make(-1083);
    static final SimpleSymbol Lit16;
    static final FString Lit160 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit161 = PairWithPosition.make(Lit16, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Screen1.yail", 1323103);
    static final SimpleSymbol Lit162 = ((SimpleSymbol) new SimpleSymbol("Soil$Click").readResolve());
    static final SimpleSymbol Lit163 = ((SimpleSymbol) new SimpleSymbol("Soil$TouchDown").readResolve());
    static final SimpleSymbol Lit164 = ((SimpleSymbol) new SimpleSymbol("Soil$TouchUp").readResolve());
    static final FString Lit165 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit166 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement9").readResolve());
    static final IntNum Lit167 = IntNum.make(35);
    static final FString Lit168 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit169 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final SimpleSymbol Lit170 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement14").readResolve());
    static final FString Lit171 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit172 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit173 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement3").readResolve());
    static final IntNum Lit174 = IntNum.make(-1090);
    static final FString Lit175 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit176 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit177 = ((SimpleSymbol) new SimpleSymbol("Label6").readResolve());
    static final FString Lit178 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit179 = new FString("com.google.appinventor.components.runtime.Button");
    static final IntNum Lit18;
    static final SimpleSymbol Lit180 = ((SimpleSymbol) new SimpleSymbol("aboutbutotn").readResolve());
    static final IntNum Lit181;
    static final IntNum Lit182 = IntNum.make(13);
    static final IntNum Lit183 = IntNum.make(-1083);
    static final FString Lit184 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit185;
    static final SimpleSymbol Lit186 = ((SimpleSymbol) new SimpleSymbol("aboutbutotn$Click").readResolve());
    static final SimpleSymbol Lit187 = ((SimpleSymbol) new SimpleSymbol("aboutbutotn$TouchDown").readResolve());
    static final SimpleSymbol Lit188 = ((SimpleSymbol) new SimpleSymbol("aboutbutotn$TouchUp").readResolve());
    static final FString Lit189 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("BackgroundImage").readResolve());
    static final SimpleSymbol Lit190 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement11").readResolve());
    static final IntNum Lit191 = IntNum.make(15);
    static final FString Lit192 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit193 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit194 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit195 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final SimpleSymbol Lit196 = ((SimpleSymbol) new SimpleSymbol("Notifier1").readResolve());
    static final FString Lit197 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final FString Lit198 = new FString("com.google.appinventor.components.runtime.Player");
    static final FString Lit199 = new FString("com.google.appinventor.components.runtime.Player");
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("Icon").readResolve());
    static final FString Lit200 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final SimpleSymbol Lit201 = ((SimpleSymbol) new SimpleSymbol(TinyDB.DEFAULT_NAMESPACE).readResolve());
    static final FString Lit202 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final SimpleSymbol Lit203 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit204 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit205 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit206 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit207 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit208 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit209 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("OpenScreenAnimation").readResolve());
    static final SimpleSymbol Lit210 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit211 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit212 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit213 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit214 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit215 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit216 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit217 = ((SimpleSymbol) new SimpleSymbol("any").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("PrimaryColor").readResolve());
    static final IntNum Lit23;
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("PrimaryColorDark").readResolve());
    static final IntNum Lit25;
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("ScreenOrientation").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("Scrollable").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("p$click").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("Source").readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("Player1").readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("Loop").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("Screen1$Initialize").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final FString Lit36 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement15").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final IntNum Lit39 = IntNum.make(10);
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("g$clickSound").readResolve());
    static final FString Lit40 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit41 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement4").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit44 = IntNum.make(-2);
    static final FString Lit45 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit46 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement17").readResolve());
    static final IntNum Lit48 = IntNum.make(20);
    static final FString Lit49 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final PairWithPosition Lit5 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit217, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Screen1.yail", 32876), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Screen1.yail", 32871);
    static final FString Lit50 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("Button2").readResolve());
    static final IntNum Lit52 = IntNum.make(1174405120);
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final IntNum Lit54 = IntNum.make(14);
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("Shape").readResolve());
    static final IntNum Lit56 = IntNum.make(1);
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final FString Lit58 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit59 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit217, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Screen1.yail", 278623), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Screen1.yail", 278618);
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("Sound1").readResolve());
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("Button2$Click").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final SimpleSymbol Lit62 = ((SimpleSymbol) new SimpleSymbol("Button2$TouchDown").readResolve());
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("TouchDown").readResolve());
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("Button2$TouchUp").readResolve());
    static final SimpleSymbol Lit65 = ((SimpleSymbol) new SimpleSymbol("TouchUp").readResolve());
    static final FString Lit66 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement16").readResolve());
    static final FString Lit68 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit69 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("Play").readResolve());
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("Button3").readResolve());
    static final IntNum Lit71 = IntNum.make(1174405120);
    static final FString Lit72 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit73 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit217, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Screen1.yail", 393304), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Screen1.yail", 393299);
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("Start").readResolve());
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("Stop").readResolve());
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("Button3$Click").readResolve());
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("Button3$TouchDown").readResolve());
    static final SimpleSymbol Lit78 = ((SimpleSymbol) new SimpleSymbol("Button3$TouchUp").readResolve());
    static final FString Lit79 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final PairWithPosition Lit8 = PairWithPosition.make(Lit217, PairWithPosition.make(Lit217, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Screen1.yail", 32876), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Screen1.yail", 32871);
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve());
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final IntNum Lit82 = IntNum.make(3);
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol("AlignVertical").readResolve());
    static final IntNum Lit84 = IntNum.make(2);
    static final IntNum Lit85 = IntNum.make(175);
    static final FString Lit86 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit87 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit88 = ((SimpleSymbol) new SimpleSymbol("Label4").readResolve());
    static final IntNum Lit89;
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("g$bgm").readResolve());
    static final IntNum Lit90 = IntNum.make(18);
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol("FontTypeface").readResolve());
    static final SimpleSymbol Lit92 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final IntNum Lit93 = IntNum.make(-1090);
    static final FString Lit94 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit95 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit96 = ((SimpleSymbol) new SimpleSymbol("Label5").readResolve());
    static final IntNum Lit97;
    static final IntNum Lit98 = IntNum.make(-1090);
    static final FString Lit99 = new FString("com.google.appinventor.components.runtime.Label");
    public static Screen1 Screen1;
    static final ModuleMethod lambda$Fn1 = null;
    static final ModuleMethod lambda$Fn10 = null;
    static final ModuleMethod lambda$Fn11 = null;
    static final ModuleMethod lambda$Fn12 = null;
    static final ModuleMethod lambda$Fn13 = null;
    static final ModuleMethod lambda$Fn14 = null;
    static final ModuleMethod lambda$Fn15 = null;
    static final ModuleMethod lambda$Fn16 = null;
    static final ModuleMethod lambda$Fn17 = null;
    static final ModuleMethod lambda$Fn18 = null;
    static final ModuleMethod lambda$Fn19 = null;
    static final ModuleMethod lambda$Fn2 = null;
    static final ModuleMethod lambda$Fn20 = null;
    static final ModuleMethod lambda$Fn21 = null;
    static final ModuleMethod lambda$Fn22 = null;
    static final ModuleMethod lambda$Fn23 = null;
    static final ModuleMethod lambda$Fn24 = null;
    static final ModuleMethod lambda$Fn25 = null;
    static final ModuleMethod lambda$Fn26 = null;
    static final ModuleMethod lambda$Fn27 = null;
    static final ModuleMethod lambda$Fn28 = null;
    static final ModuleMethod lambda$Fn29 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn30 = null;
    static final ModuleMethod lambda$Fn31 = null;
    static final ModuleMethod lambda$Fn32 = null;
    static final ModuleMethod lambda$Fn33 = null;
    static final ModuleMethod lambda$Fn34 = null;
    static final ModuleMethod lambda$Fn35 = null;
    static final ModuleMethod lambda$Fn36 = null;
    static final ModuleMethod lambda$Fn37 = null;
    static final ModuleMethod lambda$Fn38 = null;
    static final ModuleMethod lambda$Fn39 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn40 = null;
    static final ModuleMethod lambda$Fn41 = null;
    static final ModuleMethod lambda$Fn42 = null;
    static final ModuleMethod lambda$Fn43 = null;
    static final ModuleMethod lambda$Fn44 = null;
    static final ModuleMethod lambda$Fn45 = null;
    static final ModuleMethod lambda$Fn46 = null;
    static final ModuleMethod lambda$Fn47 = null;
    static final ModuleMethod lambda$Fn48 = null;
    static final ModuleMethod lambda$Fn49 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn50 = null;
    static final ModuleMethod lambda$Fn51 = null;
    static final ModuleMethod lambda$Fn52 = null;
    static final ModuleMethod lambda$Fn53 = null;
    static final ModuleMethod lambda$Fn54 = null;
    static final ModuleMethod lambda$Fn55 = null;
    static final ModuleMethod lambda$Fn56 = null;
    static final ModuleMethod lambda$Fn57 = null;
    static final ModuleMethod lambda$Fn58 = null;
    static final ModuleMethod lambda$Fn59 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn60 = null;
    static final ModuleMethod lambda$Fn61 = null;
    static final ModuleMethod lambda$Fn62 = null;
    static final ModuleMethod lambda$Fn63 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public Button Button1;
    public Button Button2;
    public final ModuleMethod Button2$Click;
    public final ModuleMethod Button2$TouchDown;
    public final ModuleMethod Button2$TouchUp;
    public Button Button3;
    public final ModuleMethod Button3$Click;
    public final ModuleMethod Button3$TouchDown;
    public final ModuleMethod Button3$TouchUp;
    public Button Crop;
    public final ModuleMethod Crop$Click;
    public final ModuleMethod Crop$TouchDown;
    public final ModuleMethod Crop$TouchUp;
    public HorizontalArrangement HorizontalArrangement1;
    public HorizontalArrangement HorizontalArrangement2;
    public HorizontalArrangement HorizontalArrangement3;
    public HorizontalArrangement HorizontalArrangement4;
    public Label Label1;
    public Label Label2;
    public Label Label3;
    public Label Label4;
    public Label Label5;
    public Label Label6;
    public Notifier Notifier1;
    public Player Player1;
    public final ModuleMethod Screen1$Initialize;
    public Button Soil;
    public final ModuleMethod Soil$Click;
    public final ModuleMethod Soil$TouchDown;
    public final ModuleMethod Soil$TouchUp;
    public Sound Sound1;
    public TinyDB TinyDB1;
    public VerticalArrangement VerticalArrangement1;
    public VerticalArrangement VerticalArrangement11;
    public VerticalArrangement VerticalArrangement12;
    public VerticalArrangement VerticalArrangement13;
    public VerticalArrangement VerticalArrangement14;
    public VerticalArrangement VerticalArrangement15;
    public VerticalArrangement VerticalArrangement16;
    public VerticalArrangement VerticalArrangement17;
    public VerticalArrangement VerticalArrangement2;
    public VerticalArrangement VerticalArrangement6;
    public VerticalArrangement VerticalArrangement7;
    public VerticalArrangement VerticalArrangement8;
    public VerticalArrangement VerticalArrangement9;
    public Button aboutbutotn;
    public final ModuleMethod aboutbutotn$Click;
    public final ModuleMethod aboutbutotn$TouchDown;
    public final ModuleMethod aboutbutotn$TouchUp;
    public final ModuleMethod add$Mnto$Mncomponents;
    public final ModuleMethod add$Mnto$Mnevents;
    public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
    public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
    public final ModuleMethod android$Mnlog$Mnform;
    public LList components$Mnto$Mncreate;
    public final ModuleMethod dispatchEvent;
    public final ModuleMethod dispatchGenericEvent;
    public LList events$Mnto$Mnregister;
    public LList form$Mndo$Mnafter$Mncreation;
    public Environment form$Mnenvironment;
    public Symbol form$Mnname$Mnsymbol;
    public final ModuleMethod get$Mnsimple$Mnname;
    public Environment global$Mnvar$Mnenvironment;
    public LList global$Mnvars$Mnto$Mncreate;
    public final ModuleMethod is$Mnbound$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod lookup$Mnhandler;
    public final ModuleMethod lookup$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod onCreate;
    public final ModuleMethod process$Mnexception;
    public final ModuleMethod send$Mnerror;

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit16 = simpleSymbol;
        Lit185 = PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Screen1.yail", 1572960);
        int[] iArr = new int[2];
        iArr[0] = -2080374785;
        Lit181 = IntNum.make(iArr);
        int[] iArr2 = new int[2];
        iArr2[0] = -16777216;
        Lit158 = IntNum.make(iArr2);
        int[] iArr3 = new int[2];
        iArr3[0] = -1;
        Lit157 = IntNum.make(iArr3);
        int[] iArr4 = new int[2];
        iArr4[0] = -1;
        Lit139 = IntNum.make(iArr4);
        int[] iArr5 = new int[2];
        iArr5[0] = -1;
        Lit126 = IntNum.make(iArr5);
        int[] iArr6 = new int[2];
        iArr6[0] = -845;
        Lit97 = IntNum.make(iArr6);
        int[] iArr7 = new int[2];
        iArr7[0] = -845;
        Lit89 = IntNum.make(iArr7);
        int[] iArr8 = new int[2];
        iArr8[0] = -9592721;
        Lit25 = IntNum.make(iArr8);
        int[] iArr9 = new int[2];
        iArr9[0] = -8271996;
        Lit23 = IntNum.make(iArr9);
        int[] iArr10 = new int[2];
        iArr10[0] = -16777216;
        Lit18 = IntNum.make(iArr10);
        int[] iArr11 = new int[2];
        iArr11[0] = -18654;
        Lit11 = IntNum.make(iArr11);
    }

    public Screen1() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        frame2.$main = this;
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit203, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit204, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit205, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit206, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit207, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit208, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit209, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit210, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit211, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit212, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit213, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit214, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit215, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit216, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, (Object) null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime15798620743098228844.scm:639");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, (Object) null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 21, (Object) null, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 22, (Object) null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 23, (Object) null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 24, (Object) null, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 25, (Object) null, 0);
        this.Screen1$Initialize = new ModuleMethod(frame2, 26, Lit34, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 27, (Object) null, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 28, (Object) null, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 29, (Object) null, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 30, (Object) null, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 31, (Object) null, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 32, (Object) null, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 33, (Object) null, 0);
        lambda$Fn15 = new ModuleMethod(frame2, 34, (Object) null, 0);
        this.Button2$Click = new ModuleMethod(frame2, 35, Lit60, 0);
        this.Button2$TouchDown = new ModuleMethod(frame2, 36, Lit62, 0);
        this.Button2$TouchUp = new ModuleMethod(frame2, 37, Lit64, 0);
        lambda$Fn16 = new ModuleMethod(frame2, 38, (Object) null, 0);
        lambda$Fn17 = new ModuleMethod(frame2, 39, (Object) null, 0);
        lambda$Fn18 = new ModuleMethod(frame2, 40, (Object) null, 0);
        lambda$Fn19 = new ModuleMethod(frame2, 41, (Object) null, 0);
        this.Button3$Click = new ModuleMethod(frame2, 42, Lit76, 0);
        this.Button3$TouchDown = new ModuleMethod(frame2, 43, Lit77, 0);
        this.Button3$TouchUp = new ModuleMethod(frame2, 44, Lit78, 0);
        lambda$Fn20 = new ModuleMethod(frame2, 45, (Object) null, 0);
        lambda$Fn21 = new ModuleMethod(frame2, 46, (Object) null, 0);
        lambda$Fn22 = new ModuleMethod(frame2, 47, (Object) null, 0);
        lambda$Fn23 = new ModuleMethod(frame2, 48, (Object) null, 0);
        lambda$Fn24 = new ModuleMethod(frame2, 49, (Object) null, 0);
        lambda$Fn25 = new ModuleMethod(frame2, 50, (Object) null, 0);
        lambda$Fn26 = new ModuleMethod(frame2, 51, (Object) null, 0);
        lambda$Fn27 = new ModuleMethod(frame2, 52, (Object) null, 0);
        lambda$Fn28 = new ModuleMethod(frame2, 53, (Object) null, 0);
        lambda$Fn29 = new ModuleMethod(frame2, 54, (Object) null, 0);
        lambda$Fn30 = new ModuleMethod(frame2, 55, (Object) null, 0);
        lambda$Fn31 = new ModuleMethod(frame2, 56, (Object) null, 0);
        lambda$Fn32 = new ModuleMethod(frame2, 57, (Object) null, 0);
        lambda$Fn33 = new ModuleMethod(frame2, 58, (Object) null, 0);
        lambda$Fn34 = new ModuleMethod(frame2, 59, (Object) null, 0);
        lambda$Fn35 = new ModuleMethod(frame2, 60, (Object) null, 0);
        lambda$Fn36 = new ModuleMethod(frame2, 61, (Object) null, 0);
        lambda$Fn37 = new ModuleMethod(frame2, 62, (Object) null, 0);
        lambda$Fn38 = new ModuleMethod(frame2, 63, (Object) null, 0);
        lambda$Fn39 = new ModuleMethod(frame2, 64, (Object) null, 0);
        this.Crop$Click = new ModuleMethod(frame2, 65, Lit131, 0);
        this.Crop$TouchDown = new ModuleMethod(frame2, 66, Lit132, 0);
        this.Crop$TouchUp = new ModuleMethod(frame2, 67, Lit133, 0);
        lambda$Fn40 = new ModuleMethod(frame2, 68, (Object) null, 0);
        lambda$Fn41 = new ModuleMethod(frame2, 69, (Object) null, 0);
        lambda$Fn42 = new ModuleMethod(frame2, 70, (Object) null, 0);
        lambda$Fn43 = new ModuleMethod(frame2, 71, (Object) null, 0);
        lambda$Fn44 = new ModuleMethod(frame2, 72, (Object) null, 0);
        lambda$Fn45 = new ModuleMethod(frame2, 73, (Object) null, 0);
        lambda$Fn46 = new ModuleMethod(frame2, 74, (Object) null, 0);
        lambda$Fn47 = new ModuleMethod(frame2, 75, (Object) null, 0);
        lambda$Fn48 = new ModuleMethod(frame2, 76, (Object) null, 0);
        lambda$Fn49 = new ModuleMethod(frame2, 77, (Object) null, 0);
        lambda$Fn50 = new ModuleMethod(frame2, 78, (Object) null, 0);
        lambda$Fn51 = new ModuleMethod(frame2, 79, (Object) null, 0);
        this.Soil$Click = new ModuleMethod(frame2, 80, Lit162, 0);
        this.Soil$TouchDown = new ModuleMethod(frame2, 81, Lit163, 0);
        this.Soil$TouchUp = new ModuleMethod(frame2, 82, Lit164, 0);
        lambda$Fn52 = new ModuleMethod(frame2, 83, (Object) null, 0);
        lambda$Fn53 = new ModuleMethod(frame2, 84, (Object) null, 0);
        lambda$Fn54 = new ModuleMethod(frame2, 85, (Object) null, 0);
        lambda$Fn55 = new ModuleMethod(frame2, 86, (Object) null, 0);
        lambda$Fn56 = new ModuleMethod(frame2, 87, (Object) null, 0);
        lambda$Fn57 = new ModuleMethod(frame2, 88, (Object) null, 0);
        lambda$Fn58 = new ModuleMethod(frame2, 89, (Object) null, 0);
        lambda$Fn59 = new ModuleMethod(frame2, 90, (Object) null, 0);
        lambda$Fn60 = new ModuleMethod(frame2, 91, (Object) null, 0);
        lambda$Fn61 = new ModuleMethod(frame2, 92, (Object) null, 0);
        this.aboutbutotn$Click = new ModuleMethod(frame2, 93, Lit186, 0);
        this.aboutbutotn$TouchDown = new ModuleMethod(frame2, 94, Lit187, 0);
        this.aboutbutotn$TouchUp = new ModuleMethod(frame2, 95, Lit188, 0);
        lambda$Fn62 = new ModuleMethod(frame2, 96, (Object) null, 0);
        lambda$Fn63 = new ModuleMethod(frame2, 97, (Object) null, 0);
    }

    static Boolean lambda6() {
        return Boolean.TRUE;
    }

    static Boolean lambda7() {
        return Boolean.FALSE;
    }

    public Object lookupInFormEnvironment(Symbol symbol) {
        return lookupInFormEnvironment(symbol, Boolean.FALSE);
    }

    public void run() {
        CallContext instance = CallContext.getInstance();
        Consumer consumer = instance.consumer;
        instance.consumer = VoidConsumer.instance;
        try {
            run(instance);
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        ModuleBody.runCleanup(instance, th, consumer);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        Object find = require.find("com.google.youngandroid.runtime");
        try {
            ((Runnable) find).run();
            this.$Stdebug$Mnform$St = Boolean.FALSE;
            this.form$Mnenvironment = Environment.make(misc.symbol$To$String(Lit0));
            FString stringAppend = strings.stringAppend(misc.symbol$To$String(Lit0), "-global-vars");
            this.global$Mnvar$Mnenvironment = Environment.make(stringAppend == null ? null : stringAppend.toString());
            Screen1 = null;
            this.form$Mnname$Mnsymbol = Lit0;
            this.events$Mnto$Mnregister = LList.Empty;
            this.components$Mnto$Mncreate = LList.Empty;
            this.global$Mnvars$Mnto$Mncreate = LList.Empty;
            this.form$Mndo$Mnafter$Mncreation = LList.Empty;
            Object find2 = require.find("com.google.youngandroid.runtime");
            try {
                ((Runnable) find2).run();
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit3, lambda$Fn2), $result);
                } else {
                    addToGlobalVars(Lit3, lambda$Fn3);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit4, Boolean.TRUE), $result);
                } else {
                    addToGlobalVars(Lit4, lambda$Fn5);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit9, Boolean.FALSE), $result);
                } else {
                    addToGlobalVars(Lit9, lambda$Fn6);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit10, Lit11, Lit12);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit13, Boolean.TRUE, Lit14);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "FarmFriend", Lit16);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit17, Lit18, Lit12);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit19, "Do-sharpEdit.png", Lit16);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit20, "ChatGPTImageApr92025.png", Lit16);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit21, "zoom", Lit16);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit22, Lit23, Lit12);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit24, Lit25, Lit12);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit26, "unspecified", Lit16);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit27, Boolean.TRUE, Lit14);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit28, Boolean.TRUE, Lit14);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit29, "Responsive", Lit16);
                    Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit30, "FarmFriend", Lit16), $result);
                } else {
                    addToFormDoAfterCreation(new Promise(lambda$Fn7));
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit34, this.Screen1$Initialize);
                } else {
                    addToFormEnvironment(Lit34, this.Screen1$Initialize);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Screen1", "Initialize");
                } else {
                    addToEvents(Lit0, Lit35);
                }
                this.VerticalArrangement15 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit36, Lit37, lambda$Fn8), $result);
                } else {
                    addToComponents(Lit0, Lit40, Lit37, lambda$Fn9);
                }
                this.HorizontalArrangement4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit41, Lit42, lambda$Fn10), $result);
                } else {
                    addToComponents(Lit0, Lit45, Lit42, lambda$Fn11);
                }
                this.VerticalArrangement17 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit42, Lit46, Lit47, lambda$Fn12), $result);
                } else {
                    addToComponents(Lit42, Lit49, Lit47, lambda$Fn13);
                }
                this.Button2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit42, Lit50, Lit51, lambda$Fn14), $result);
                } else {
                    addToComponents(Lit42, Lit58, Lit51, lambda$Fn15);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit60, this.Button2$Click);
                } else {
                    addToFormEnvironment(Lit60, this.Button2$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "Click");
                } else {
                    addToEvents(Lit51, Lit61);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit62, this.Button2$TouchDown);
                } else {
                    addToFormEnvironment(Lit62, this.Button2$TouchDown);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "TouchDown");
                } else {
                    addToEvents(Lit51, Lit63);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit64, this.Button2$TouchUp);
                } else {
                    addToFormEnvironment(Lit64, this.Button2$TouchUp);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "TouchUp");
                } else {
                    addToEvents(Lit51, Lit65);
                }
                this.VerticalArrangement16 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit42, Lit66, Lit67, lambda$Fn16), $result);
                } else {
                    addToComponents(Lit42, Lit68, Lit67, lambda$Fn17);
                }
                this.Button3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit42, Lit69, Lit70, lambda$Fn18), $result);
                } else {
                    addToComponents(Lit42, Lit72, Lit70, lambda$Fn19);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit76, this.Button3$Click);
                } else {
                    addToFormEnvironment(Lit76, this.Button3$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button3", "Click");
                } else {
                    addToEvents(Lit70, Lit61);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit77, this.Button3$TouchDown);
                } else {
                    addToFormEnvironment(Lit77, this.Button3$TouchDown);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button3", "TouchDown");
                } else {
                    addToEvents(Lit70, Lit63);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit78, this.Button3$TouchUp);
                } else {
                    addToFormEnvironment(Lit78, this.Button3$TouchUp);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button3", "TouchUp");
                } else {
                    addToEvents(Lit70, Lit65);
                }
                this.VerticalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit79, Lit80, lambda$Fn20), $result);
                } else {
                    addToComponents(Lit0, Lit86, Lit80, lambda$Fn21);
                }
                this.Label4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit80, Lit87, Lit88, lambda$Fn22), $result);
                } else {
                    addToComponents(Lit80, Lit94, Lit88, lambda$Fn23);
                }
                this.Label5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit80, Lit95, Lit96, lambda$Fn24), $result);
                } else {
                    addToComponents(Lit80, Lit99, Lit96, lambda$Fn25);
                }
                this.VerticalArrangement7 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit100, Lit101, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit102, Lit101, Boolean.FALSE);
                }
                this.VerticalArrangement6 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit103, Lit104, lambda$Fn26), $result);
                } else {
                    addToComponents(Lit0, Lit105, Lit104, lambda$Fn27);
                }
                this.Label3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit104, Lit106, Lit107, lambda$Fn28), $result);
                } else {
                    addToComponents(Lit104, Lit109, Lit107, lambda$Fn29);
                }
                this.VerticalArrangement8 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit110, Lit111, lambda$Fn30), $result);
                } else {
                    addToComponents(Lit0, Lit113, Lit111, lambda$Fn31);
                }
                this.VerticalArrangement12 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit114, Lit115, lambda$Fn32), $result);
                } else {
                    addToComponents(Lit0, Lit116, Lit115, lambda$Fn33);
                }
                this.HorizontalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit115, Lit117, Lit118, lambda$Fn34), $result);
                } else {
                    addToComponents(Lit115, Lit120, Lit118, lambda$Fn35);
                }
                this.Label1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit118, Lit121, Lit122, lambda$Fn36), $result);
                } else {
                    addToComponents(Lit118, Lit123, Lit122, lambda$Fn37);
                }
                this.Crop = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit118, Lit124, Lit125, lambda$Fn38), $result);
                } else {
                    addToComponents(Lit118, Lit129, Lit125, lambda$Fn39);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit131, this.Crop$Click);
                } else {
                    addToFormEnvironment(Lit131, this.Crop$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Crop", "Click");
                } else {
                    addToEvents(Lit125, Lit61);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit132, this.Crop$TouchDown);
                } else {
                    addToFormEnvironment(Lit132, this.Crop$TouchDown);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Crop", "TouchDown");
                } else {
                    addToEvents(Lit125, Lit63);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit133, this.Crop$TouchUp);
                } else {
                    addToFormEnvironment(Lit133, this.Crop$TouchUp);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Crop", "TouchUp");
                } else {
                    addToEvents(Lit125, Lit65);
                }
                this.Button1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit118, Lit134, Lit135, lambda$Fn40), $result);
                } else {
                    addToComponents(Lit118, Lit140, Lit135, lambda$Fn41);
                }
                this.VerticalArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit141, Lit142, lambda$Fn42), $result);
                } else {
                    addToComponents(Lit0, Lit144, Lit142, lambda$Fn43);
                }
                this.VerticalArrangement13 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit145, Lit146, lambda$Fn44), $result);
                } else {
                    addToComponents(Lit0, Lit147, Lit146, lambda$Fn45);
                }
                this.HorizontalArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit146, Lit148, Lit149, lambda$Fn46), $result);
                } else {
                    addToComponents(Lit146, Lit151, Lit149, lambda$Fn47);
                }
                this.Label2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit149, Lit152, Lit153, lambda$Fn48), $result);
                } else {
                    addToComponents(Lit149, Lit154, Lit153, lambda$Fn49);
                }
                this.Soil = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit149, Lit155, Lit156, lambda$Fn50), $result);
                } else {
                    addToComponents(Lit149, Lit160, Lit156, lambda$Fn51);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit162, this.Soil$Click);
                } else {
                    addToFormEnvironment(Lit162, this.Soil$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Soil", "Click");
                } else {
                    addToEvents(Lit156, Lit61);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit163, this.Soil$TouchDown);
                } else {
                    addToFormEnvironment(Lit163, this.Soil$TouchDown);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Soil", "TouchDown");
                } else {
                    addToEvents(Lit156, Lit63);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit164, this.Soil$TouchUp);
                } else {
                    addToFormEnvironment(Lit164, this.Soil$TouchUp);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Soil", "TouchUp");
                } else {
                    addToEvents(Lit156, Lit65);
                }
                this.VerticalArrangement9 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit165, Lit166, lambda$Fn52), $result);
                } else {
                    addToComponents(Lit0, Lit168, Lit166, lambda$Fn53);
                }
                this.VerticalArrangement14 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit169, Lit170, lambda$Fn54), $result);
                } else {
                    addToComponents(Lit0, Lit171, Lit170, lambda$Fn55);
                }
                this.HorizontalArrangement3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit170, Lit172, Lit173, lambda$Fn56), $result);
                } else {
                    addToComponents(Lit170, Lit175, Lit173, lambda$Fn57);
                }
                this.Label6 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit173, Lit176, Lit177, lambda$Fn58), $result);
                } else {
                    addToComponents(Lit173, Lit178, Lit177, lambda$Fn59);
                }
                this.aboutbutotn = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit173, Lit179, Lit180, lambda$Fn60), $result);
                } else {
                    addToComponents(Lit173, Lit184, Lit180, lambda$Fn61);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit186, this.aboutbutotn$Click);
                } else {
                    addToFormEnvironment(Lit186, this.aboutbutotn$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "aboutbutotn", "Click");
                } else {
                    addToEvents(Lit180, Lit61);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit187, this.aboutbutotn$TouchDown);
                } else {
                    addToFormEnvironment(Lit187, this.aboutbutotn$TouchDown);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "aboutbutotn", "TouchDown");
                } else {
                    addToEvents(Lit180, Lit63);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit188, this.aboutbutotn$TouchUp);
                } else {
                    addToFormEnvironment(Lit188, this.aboutbutotn$TouchUp);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "aboutbutotn", "TouchUp");
                } else {
                    addToEvents(Lit180, Lit65);
                }
                this.VerticalArrangement11 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit189, Lit190, lambda$Fn62), $result);
                } else {
                    addToComponents(Lit0, Lit192, Lit190, lambda$Fn63);
                }
                this.Sound1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit193, Lit6, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit194, Lit6, Boolean.FALSE);
                }
                this.Notifier1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit195, Lit196, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit197, Lit196, Boolean.FALSE);
                }
                this.Player1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit198, Lit32, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit199, Lit32, Boolean.FALSE);
                }
                this.TinyDB1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit200, Lit201, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit202, Lit201, Boolean.FALSE);
                }
                runtime.initRuntime();
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Runnable.run()", 1, find2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "java.lang.Runnable.run()", 1, find);
        }
    }

    /* compiled from: Screen1.yail */
    public class frame extends ModuleBody {
        Screen1 $main;

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 18:
                    return Screen1.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return Screen1.lambda3();
                case 21:
                    return Screen1.lambda5();
                case 22:
                    return Screen1.lambda4();
                case 23:
                    return Screen1.lambda6();
                case 24:
                    return Screen1.lambda7();
                case 25:
                    return Screen1.lambda8();
                case 26:
                    return this.$main.Screen1$Initialize();
                case 27:
                    return Screen1.lambda9();
                case 28:
                    return Screen1.lambda10();
                case 29:
                    return Screen1.lambda11();
                case 30:
                    return Screen1.lambda12();
                case 31:
                    return Screen1.lambda13();
                case 32:
                    return Screen1.lambda14();
                case 33:
                    return Screen1.lambda15();
                case 34:
                    return Screen1.lambda16();
                case 35:
                    return this.$main.Button2$Click();
                case 36:
                    return this.$main.Button2$TouchDown();
                case 37:
                    return this.$main.Button2$TouchUp();
                case 38:
                    return Screen1.lambda17();
                case 39:
                    return Screen1.lambda18();
                case 40:
                    return Screen1.lambda19();
                case 41:
                    return Screen1.lambda20();
                case 42:
                    return this.$main.Button3$Click();
                case 43:
                    return this.$main.Button3$TouchDown();
                case 44:
                    return this.$main.Button3$TouchUp();
                case 45:
                    return Screen1.lambda21();
                case 46:
                    return Screen1.lambda22();
                case 47:
                    return Screen1.lambda23();
                case 48:
                    return Screen1.lambda24();
                case 49:
                    return Screen1.lambda25();
                case 50:
                    return Screen1.lambda26();
                case 51:
                    return Screen1.lambda27();
                case 52:
                    return Screen1.lambda28();
                case 53:
                    return Screen1.lambda29();
                case 54:
                    return Screen1.lambda30();
                case 55:
                    return Screen1.lambda31();
                case 56:
                    return Screen1.lambda32();
                case 57:
                    return Screen1.lambda33();
                case 58:
                    return Screen1.lambda34();
                case 59:
                    return Screen1.lambda35();
                case 60:
                    return Screen1.lambda36();
                case 61:
                    return Screen1.lambda37();
                case 62:
                    return Screen1.lambda38();
                case 63:
                    return Screen1.lambda39();
                case 64:
                    return Screen1.lambda40();
                case 65:
                    return this.$main.Crop$Click();
                case 66:
                    return this.$main.Crop$TouchDown();
                case 67:
                    return this.$main.Crop$TouchUp();
                case 68:
                    return Screen1.lambda41();
                case 69:
                    return Screen1.lambda42();
                case 70:
                    return Screen1.lambda43();
                case 71:
                    return Screen1.lambda44();
                case 72:
                    return Screen1.lambda45();
                case 73:
                    return Screen1.lambda46();
                case 74:
                    return Screen1.lambda47();
                case 75:
                    return Screen1.lambda48();
                case 76:
                    return Screen1.lambda49();
                case 77:
                    return Screen1.lambda50();
                case 78:
                    return Screen1.lambda51();
                case 79:
                    return Screen1.lambda52();
                case 80:
                    return this.$main.Soil$Click();
                case 81:
                    return this.$main.Soil$TouchDown();
                case 82:
                    return this.$main.Soil$TouchUp();
                case 83:
                    return Screen1.lambda53();
                case 84:
                    return Screen1.lambda54();
                case 85:
                    return Screen1.lambda55();
                case 86:
                    return Screen1.lambda56();
                case 87:
                    return Screen1.lambda57();
                case 88:
                    return Screen1.lambda58();
                case 89:
                    return Screen1.lambda59();
                case 90:
                    return Screen1.lambda60();
                case 91:
                    return Screen1.lambda61();
                case 92:
                    return Screen1.lambda62();
                case 93:
                    return this.$main.aboutbutotn$Click();
                case 94:
                    return this.$main.aboutbutotn$TouchDown();
                case 95:
                    return this.$main.aboutbutotn$TouchUp();
                case 96:
                    return Screen1.lambda63();
                case 97:
                    return Screen1.lambda64();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 18:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 19:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 20:
                case 21:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 74:
                case 75:
                case 76:
                case 77:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                case 87:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 97:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 2:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 3:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 5:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 7:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 12:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 13:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 14:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 4:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 5:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 8:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 9:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 11:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 17:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }

        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 10:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                case 15:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    if (!(obj2 instanceof Component)) {
                        return -786430;
                    }
                    callContext.value2 = obj2;
                    if (!(obj3 instanceof String)) {
                        return -786429;
                    }
                    callContext.value3 = obj3;
                    if (!(obj4 instanceof String)) {
                        return -786428;
                    }
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                case 16:
                    if (!(obj instanceof Screen1)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    if (!(obj2 instanceof Component)) {
                        return -786430;
                    }
                    callContext.value2 = obj2;
                    if (!(obj3 instanceof String)) {
                        return -786429;
                    }
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                default:
                    return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 1:
                    return this.$main.getSimpleName(obj);
                case 2:
                    try {
                        this.$main.onCreate((Bundle) obj);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "onCreate", 1, obj);
                    }
                case 3:
                    this.$main.androidLogForm(obj);
                    return Values.empty;
                case 5:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case 7:
                    try {
                        return this.$main.isBoundInFormEnvironment((Symbol) obj) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "is-bound-in-form-environment", 1, obj);
                    }
                case 12:
                    this.$main.addToFormDoAfterCreation(obj);
                    return Values.empty;
                case 13:
                    this.$main.sendError(obj);
                    return Values.empty;
                case 14:
                    return this.$main.processException(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            boolean z = true;
            switch (moduleMethod.selector) {
                case 10:
                    this.$main.addToComponents(obj, obj2, obj3, obj4);
                    return Values.empty;
                case 15:
                    try {
                        try {
                            try {
                                try {
                                    return this.$main.dispatchEvent((Component) obj, (String) obj2, (String) obj3, (Object[]) obj4) ? Boolean.TRUE : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "dispatchEvent", 4, obj4);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "dispatchEvent", 3, obj3);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "dispatchEvent", 2, obj2);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "dispatchEvent", 1, obj);
                    }
                case 16:
                    Screen1 screen1 = this.$main;
                    try {
                        Component component = (Component) obj;
                        try {
                            String str = (String) obj2;
                            try {
                                if (obj3 == Boolean.FALSE) {
                                    z = false;
                                }
                                try {
                                    screen1.dispatchGenericEvent(component, str, z, (Object[]) obj4);
                                    return Values.empty;
                                } catch (ClassCastException e5) {
                                    throw new WrongType(e5, "dispatchGenericEvent", 4, obj4);
                                }
                            } catch (ClassCastException e6) {
                                throw new WrongType(e6, "dispatchGenericEvent", 3, obj3);
                            }
                        } catch (ClassCastException e7) {
                            throw new WrongType(e7, "dispatchGenericEvent", 2, obj2);
                        }
                    } catch (ClassCastException e8) {
                        throw new WrongType(e8, "dispatchGenericEvent", 1, obj);
                    }
                default:
                    return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
            }
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 4:
                    try {
                        this.$main.addToFormEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "add-to-form-environment", 1, obj);
                    }
                case 5:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj, obj2);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case 8:
                    try {
                        this.$main.addToGlobalVarEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "add-to-global-var-environment", 1, obj);
                    }
                case 9:
                    this.$main.addToEvents(obj, obj2);
                    return Values.empty;
                case 11:
                    this.$main.addToGlobalVars(obj, obj2);
                    return Values.empty;
                case 17:
                    return this.$main.lookupHandler(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }
    }

    static Object lambda3() {
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), Boolean.TRUE), Lit5, "=") != Boolean.FALSE ? runtime.callComponentMethod(Lit6, Lit7, LList.Empty, LList.Empty) : Values.empty;
    }

    static Procedure lambda4() {
        return lambda$Fn4;
    }

    static Object lambda5() {
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), Boolean.TRUE), Lit8, "=") != Boolean.FALSE ? runtime.callComponentMethod(Lit6, Lit7, LList.Empty, LList.Empty) : Values.empty;
    }

    static Object lambda8() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit10, Lit11, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit13, Boolean.TRUE, Lit14);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "FarmFriend", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit17, Lit18, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit19, "Do-sharpEdit.png", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit20, "ChatGPTImageApr92025.png", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit21, "zoom", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit22, Lit23, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit24, Lit25, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit26, "unspecified", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit27, Boolean.TRUE, Lit14);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit28, Boolean.TRUE, Lit14);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit29, "Responsive", Lit16);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit30, "FarmFriend", Lit16);
    }

    public Object Screen1$Initialize() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit6, Lit31, "ClickSoundEffect(1).mp3", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit32, Lit31, "WayHomeChillMusic(2).mp3", Lit16);
        return runtime.setAndCoerceProperty$Ex(Lit32, Lit33, Boolean.TRUE, Lit14);
    }

    static Object lambda10() {
        return runtime.setAndCoerceProperty$Ex(Lit37, Lit38, Lit39, Lit12);
    }

    static Object lambda9() {
        return runtime.setAndCoerceProperty$Ex(Lit37, Lit38, Lit39, Lit12);
    }

    static Object lambda11() {
        return runtime.setAndCoerceProperty$Ex(Lit42, Lit43, Lit44, Lit12);
    }

    static Object lambda12() {
        return runtime.setAndCoerceProperty$Ex(Lit42, Lit43, Lit44, Lit12);
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit47, Lit38, Lit39, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit47, Lit43, Lit48, Lit12);
    }

    static Object lambda14() {
        runtime.setAndCoerceProperty$Ex(Lit47, Lit38, Lit39, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit47, Lit43, Lit48, Lit12);
    }

    static Object lambda15() {
        runtime.setAndCoerceProperty$Ex(Lit51, Lit17, Lit52, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit53, Lit54, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit55, Lit56, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit51, Lit57, "🔊", Lit16);
    }

    static Object lambda16() {
        runtime.setAndCoerceProperty$Ex(Lit51, Lit17, Lit52, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit53, Lit54, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit55, Lit56, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit51, Lit57, "🔊", Lit16);
    }

    public Object Button2$Click() {
        Boolean bool;
        SimpleSymbol simpleSymbol;
        runtime.setThisForm();
        if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), Boolean.TRUE), Lit59, "=") != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit51, Lit57, "🔇", Lit16);
            simpleSymbol = Lit4;
            bool = Boolean.FALSE;
        } else {
            runtime.setAndCoerceProperty$Ex(Lit51, Lit57, "🔊", Lit16);
            simpleSymbol = Lit4;
            bool = Boolean.TRUE;
        }
        return runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol, bool);
    }

    public Object Button2$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit51, Lit53, "10", Lit12);
    }

    public Object Button2$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit51, Lit53, "14", Lit12);
    }

    static Object lambda17() {
        runtime.setAndCoerceProperty$Ex(Lit67, Lit38, Lit39, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit67, Lit43, Lit39, Lit12);
    }

    static Object lambda18() {
        runtime.setAndCoerceProperty$Ex(Lit67, Lit38, Lit39, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit67, Lit43, Lit39, Lit12);
    }

    static Object lambda19() {
        runtime.setAndCoerceProperty$Ex(Lit70, Lit17, Lit71, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit70, Lit53, Lit54, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit70, Lit55, Lit56, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit70, Lit57, "🚫🎵", Lit16);
    }

    static Object lambda20() {
        runtime.setAndCoerceProperty$Ex(Lit70, Lit17, Lit71, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit70, Lit53, Lit54, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit70, Lit55, Lit56, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit70, Lit57, "🚫🎵", Lit16);
    }

    public Object Button3$Click() {
        Boolean bool;
        SimpleSymbol simpleSymbol;
        runtime.setThisForm();
        if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St), Boolean.FALSE), Lit73, "=") != Boolean.FALSE) {
            runtime.callComponentMethod(Lit32, Lit74, LList.Empty, LList.Empty);
            runtime.setAndCoerceProperty$Ex(Lit70, Lit57, "🎵", Lit16);
            simpleSymbol = Lit9;
            bool = Boolean.TRUE;
        } else {
            runtime.callComponentMethod(Lit32, Lit75, LList.Empty, LList.Empty);
            runtime.setAndCoerceProperty$Ex(Lit70, Lit57, "🚫🎵", Lit16);
            simpleSymbol = Lit9;
            bool = Boolean.FALSE;
        }
        return runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol, bool);
    }

    public Object Button3$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit70, Lit53, "10", Lit12);
    }

    public Object Button3$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit70, Lit53, "14", Lit12);
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit80, Lit81, Lit82, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit83, Lit84, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit38, Lit85, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit80, Lit43, Lit44, Lit12);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit80, Lit81, Lit82, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit83, Lit84, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit38, Lit85, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit80, Lit43, Lit44, Lit12);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit88, Lit17, Lit89, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit53, Lit90, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit57, "🧑‍🌾 Welcome to Farm Friend! 🥳", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit92, Lit56, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit88, Lit43, Lit93, Lit12);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit88, Lit17, Lit89, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit53, Lit90, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit57, "🧑‍🌾 Welcome to Farm Friend! 🥳", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit92, Lit56, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit88, Lit43, Lit93, Lit12);
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit96, Lit17, Lit97, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit53, Lit90, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit57, "🚜 Get Suggestions! Grow Smarter, Not Harder", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit92, Lit56, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit96, Lit43, Lit98, Lit12);
    }

    static Object lambda26() {
        runtime.setAndCoerceProperty$Ex(Lit96, Lit17, Lit97, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit53, Lit90, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit57, "🚜 Get Suggestions! Grow Smarter, Not Harder", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit92, Lit56, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit96, Lit43, Lit98, Lit12);
    }

    static Object lambda27() {
        runtime.setAndCoerceProperty$Ex(Lit104, Lit81, Lit82, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit104, Lit43, Lit44, Lit12);
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit104, Lit81, Lit82, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit104, Lit43, Lit44, Lit12);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit107, Lit53, Lit90, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit107, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit107, Lit57, "🚀 Boost your farm's potential — start here! ⬇️", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit107, Lit92, Lit56, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit107, Lit43, Lit108, Lit12);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit107, Lit53, Lit90, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit107, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit107, Lit57, "🚀 Boost your farm's potential — start here! ⬇️", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit107, Lit92, Lit56, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit107, Lit43, Lit108, Lit12);
    }

    static Object lambda31() {
        return runtime.setAndCoerceProperty$Ex(Lit111, Lit38, Lit112, Lit12);
    }

    static Object lambda32() {
        return runtime.setAndCoerceProperty$Ex(Lit111, Lit38, Lit112, Lit12);
    }

    static Object lambda33() {
        runtime.setAndCoerceProperty$Ex(Lit115, Lit81, Lit82, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit115, Lit43, Lit44, Lit12);
    }

    static Object lambda34() {
        runtime.setAndCoerceProperty$Ex(Lit115, Lit81, Lit82, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit115, Lit43, Lit44, Lit12);
    }

    static Object lambda35() {
        runtime.setAndCoerceProperty$Ex(Lit118, Lit81, Lit82, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit118, Lit83, Lit84, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit118, Lit43, Lit119, Lit12);
    }

    static Object lambda36() {
        runtime.setAndCoerceProperty$Ex(Lit118, Lit81, Lit82, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit118, Lit83, Lit84, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit118, Lit43, Lit119, Lit12);
    }

    static Object lambda37() {
        runtime.setAndCoerceProperty$Ex(Lit122, Lit53, Lit48, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit122, Lit57, "👉", Lit16);
    }

    static Object lambda38() {
        runtime.setAndCoerceProperty$Ex(Lit122, Lit53, Lit48, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit122, Lit57, "👉", Lit16);
    }

    static Object lambda39() {
        runtime.setAndCoerceProperty$Ex(Lit125, Lit17, Lit126, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit125, Lit127, Boolean.TRUE, Lit14);
        runtime.setAndCoerceProperty$Ex(Lit125, Lit53, Lit90, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit125, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit125, Lit55, Lit56, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit125, Lit57, "🌾 Get Farming Suggestions", Lit16);
        return runtime.setAndCoerceProperty$Ex(Lit125, Lit43, Lit128, Lit12);
    }

    static Object lambda40() {
        runtime.setAndCoerceProperty$Ex(Lit125, Lit17, Lit126, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit125, Lit127, Boolean.TRUE, Lit14);
        runtime.setAndCoerceProperty$Ex(Lit125, Lit53, Lit90, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit125, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit125, Lit55, Lit56, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit125, Lit57, "🌾 Get Farming Suggestions", Lit16);
        return runtime.setAndCoerceProperty$Ex(Lit125, Lit43, Lit128, Lit12);
    }

    public Object Crop$Click() {
        runtime.setThisForm();
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St));
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Crop"), Lit130, "open another screen");
    }

    public Object Crop$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit125, Lit53, "17", Lit12);
    }

    public Object Crop$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit125, Lit53, "20", Lit12);
    }

    static Object lambda41() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit17, Lit136, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit127, Boolean.TRUE, Lit14);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit53, Lit39, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit38, Lit137, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit55, Lit56, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit57, "Must Try", Lit16);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit138, Lit139, Lit12);
    }

    static Object lambda42() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit17, Lit136, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit127, Boolean.TRUE, Lit14);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit53, Lit39, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit38, Lit137, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit55, Lit56, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit57, "Must Try", Lit16);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit138, Lit139, Lit12);
    }

    static Object lambda43() {
        runtime.setAndCoerceProperty$Ex(Lit142, Lit81, Lit82, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit142, Lit83, Lit84, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit142, Lit38, Lit143, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit142, Lit43, Lit44, Lit12);
    }

    static Object lambda44() {
        runtime.setAndCoerceProperty$Ex(Lit142, Lit81, Lit82, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit142, Lit83, Lit84, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit142, Lit38, Lit143, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit142, Lit43, Lit44, Lit12);
    }

    static Object lambda45() {
        runtime.setAndCoerceProperty$Ex(Lit146, Lit81, Lit82, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit146, Lit43, Lit44, Lit12);
    }

    static Object lambda46() {
        runtime.setAndCoerceProperty$Ex(Lit146, Lit81, Lit82, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit146, Lit43, Lit44, Lit12);
    }

    static Object lambda47() {
        runtime.setAndCoerceProperty$Ex(Lit149, Lit81, Lit82, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit149, Lit83, Lit84, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit149, Lit43, Lit150, Lit12);
    }

    static Object lambda48() {
        runtime.setAndCoerceProperty$Ex(Lit149, Lit81, Lit82, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit149, Lit83, Lit84, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit149, Lit43, Lit150, Lit12);
    }

    static Object lambda49() {
        runtime.setAndCoerceProperty$Ex(Lit153, Lit53, Lit48, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit153, Lit57, "👉", Lit16);
    }

    static Object lambda50() {
        runtime.setAndCoerceProperty$Ex(Lit153, Lit53, Lit48, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit153, Lit57, "👉", Lit16);
    }

    static Object lambda51() {
        runtime.setAndCoerceProperty$Ex(Lit156, Lit17, Lit157, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit127, Boolean.TRUE, Lit14);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit53, Lit48, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit55, Lit56, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit57, "🌿 Crop Health Checker", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit138, Lit158, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit156, Lit43, Lit159, Lit12);
    }

    static Object lambda52() {
        runtime.setAndCoerceProperty$Ex(Lit156, Lit17, Lit157, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit127, Boolean.TRUE, Lit14);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit53, Lit48, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit91, "Inter_18pt-Bold.ttf", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit55, Lit56, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit57, "🌿 Crop Health Checker", Lit16);
        runtime.setAndCoerceProperty$Ex(Lit156, Lit138, Lit158, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit156, Lit43, Lit159, Lit12);
    }

    public Object Soil$Click() {
        runtime.setThisForm();
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St));
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Soil"), Lit161, "open another screen");
    }

    public Object Soil$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit156, Lit53, "17", Lit12);
    }

    public Object Soil$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit156, Lit53, "20", Lit12);
    }

    static Object lambda53() {
        return runtime.setAndCoerceProperty$Ex(Lit166, Lit38, Lit167, Lit12);
    }

    static Object lambda54() {
        return runtime.setAndCoerceProperty$Ex(Lit166, Lit38, Lit167, Lit12);
    }

    static Object lambda55() {
        runtime.setAndCoerceProperty$Ex(Lit170, Lit81, Lit82, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit170, Lit43, Lit44, Lit12);
    }

    static Object lambda56() {
        runtime.setAndCoerceProperty$Ex(Lit170, Lit81, Lit82, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit170, Lit43, Lit44, Lit12);
    }

    static Object lambda57() {
        runtime.setAndCoerceProperty$Ex(Lit173, Lit81, Lit82, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit173, Lit83, Lit84, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit173, Lit43, Lit174, Lit12);
    }

    static Object lambda58() {
        runtime.setAndCoerceProperty$Ex(Lit173, Lit81, Lit82, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit173, Lit83, Lit84, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit173, Lit43, Lit174, Lit12);
    }

    static Object lambda59() {
        runtime.setAndCoerceProperty$Ex(Lit177, Lit53, Lit48, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit177, Lit57, "👉", Lit16);
    }

    static Object lambda60() {
        runtime.setAndCoerceProperty$Ex(Lit177, Lit53, Lit48, Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit177, Lit57, "👉", Lit16);
    }

    static Object lambda61() {
        runtime.setAndCoerceProperty$Ex(Lit180, Lit17, Lit181, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit180, Lit127, Boolean.TRUE, Lit14);
        runtime.setAndCoerceProperty$Ex(Lit180, Lit53, Lit182, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit180, Lit91, Component.TYPEFACE_MONOSPACE, Lit16);
        runtime.setAndCoerceProperty$Ex(Lit180, Lit55, Lit56, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit180, Lit57, "About ❤️", Lit16);
        return runtime.setAndCoerceProperty$Ex(Lit180, Lit43, Lit183, Lit12);
    }

    static Object lambda62() {
        runtime.setAndCoerceProperty$Ex(Lit180, Lit17, Lit181, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit180, Lit127, Boolean.TRUE, Lit14);
        runtime.setAndCoerceProperty$Ex(Lit180, Lit53, Lit182, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit180, Lit91, Component.TYPEFACE_MONOSPACE, Lit16);
        runtime.setAndCoerceProperty$Ex(Lit180, Lit55, Lit56, Lit12);
        runtime.setAndCoerceProperty$Ex(Lit180, Lit57, "About ❤️", Lit16);
        return runtime.setAndCoerceProperty$Ex(Lit180, Lit43, Lit183, Lit12);
    }

    public Object aboutbutotn$Click() {
        runtime.setThisForm();
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St));
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("About"), Lit185, "open another screen");
    }

    public Object aboutbutotn$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit180, Lit53, "11", Lit12);
    }

    public Object aboutbutotn$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit180, Lit53, "13", Lit12);
    }

    static Object lambda63() {
        return runtime.setAndCoerceProperty$Ex(Lit190, Lit38, Lit191, Lit12);
    }

    static Object lambda64() {
        return runtime.setAndCoerceProperty$Ex(Lit190, Lit38, Lit191, Lit12);
    }

    public String getSimpleName(Object object) {
        return object.getClass().getSimpleName();
    }

    public void onCreate(Bundle icicle) {
        AppInventorCompatActivity.setClassicModeFromYail(false);
        super.onCreate(icicle);
    }

    public void androidLogForm(Object message) {
    }

    public void addToFormEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.form$Mnenvironment, object));
        this.form$Mnenvironment.put(name, object);
    }

    public Object lookupInFormEnvironment(Symbol name, Object default$Mnvalue) {
        boolean x = ((this.form$Mnenvironment == null ? 1 : 0) + 1) & true;
        if (!x ? !x : !this.form$Mnenvironment.isBound(name)) {
            return default$Mnvalue;
        }
        return this.form$Mnenvironment.get(name);
    }

    public boolean isBoundInFormEnvironment(Symbol name) {
        return this.form$Mnenvironment.isBound(name);
    }

    public void addToGlobalVarEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.global$Mnvar$Mnenvironment, object));
        this.global$Mnvar$Mnenvironment.put(name, object);
    }

    public void addToEvents(Object component$Mnname, Object event$Mnname) {
        this.events$Mnto$Mnregister = lists.cons(lists.cons(component$Mnname, event$Mnname), this.events$Mnto$Mnregister);
    }

    public void addToComponents(Object container$Mnname, Object component$Mntype, Object component$Mnname, Object init$Mnthunk) {
        this.components$Mnto$Mncreate = lists.cons(LList.list4(container$Mnname, component$Mntype, component$Mnname, init$Mnthunk), this.components$Mnto$Mncreate);
    }

    public void addToGlobalVars(Object var, Object val$Mnthunk) {
        this.global$Mnvars$Mnto$Mncreate = lists.cons(LList.list2(var, val$Mnthunk), this.global$Mnvars$Mnto$Mncreate);
    }

    public void addToFormDoAfterCreation(Object thunk) {
        this.form$Mndo$Mnafter$Mncreation = lists.cons(thunk, this.form$Mndo$Mnafter$Mncreation);
    }

    public void sendError(Object error) {
        RetValManager.sendError(error == null ? null : error.toString());
    }

    public Object processException(Object ex) {
        String str;
        ApplyToArgs applyToArgs = Scheme.applyToArgs;
        if (ex instanceof Error) {
            str = ex.toString();
        } else {
            Object apply1 = Scheme.applyToArgs.apply1(GetNamedPart.getNamedPart.apply2(ex, Lit1));
            str = apply1 == null ? null : apply1.toString();
        }
        RuntimeErrorAlert.alert(this, false, str, ex instanceof YailRuntimeError ? ((YailRuntimeError) ex).getErrorType() : "Runtime Error", "End Application");
        return applyToArgs.apply1(Values.empty);
    }

    public boolean dispatchEvent(Component componentObject, String registeredComponentName, String eventName, Object[] args) {
        SimpleSymbol registeredObject = misc.string$To$Symbol(registeredComponentName);
        if (!isBoundInFormEnvironment(registeredObject)) {
            EventDispatcher.unregisterEventForDelegation(this, registeredComponentName, eventName);
            return false;
        } else if (lookupInFormEnvironment(registeredObject) != componentObject) {
            return false;
        } else {
            boolean x = true;
            try {
                Scheme.apply.apply2(lookupHandler(registeredComponentName, eventName), LList.makeList(args, 0));
                return true;
            } catch (StopBlocksExecution e) {
                if (throw_name.throwName.apply1(e) != Boolean.FALSE) {
                    return true;
                }
                return false;
            } catch (PermissionException e2) {
                PermissionException exception = e2;
                exception.printStackTrace();
                if (this != componentObject) {
                    x = false;
                }
                if (!x ? !x : !IsEqual.apply(eventName, "PermissionNeeded")) {
                    PermissionDenied(componentObject, eventName, exception.getPermissionNeeded());
                    return false;
                }
                processException(exception);
                return false;
            } catch (Throwable th) {
                Throwable exception2 = th;
                androidLogForm(exception2.getMessage());
                exception2.printStackTrace();
                processException(exception2);
                return false;
            }
        }
    }

    public void dispatchGenericEvent(Component componentObject, String eventName, boolean notAlreadyHandled, Object[] args) {
        boolean x = false;
        Object handler = lookupInFormEnvironment(misc.string$To$Symbol(strings.stringAppend("any$", getSimpleName(componentObject), "$", eventName)));
        if (handler != Boolean.FALSE) {
            try {
                Scheme.apply.apply2(handler, lists.cons(componentObject, lists.cons(notAlreadyHandled ? Boolean.TRUE : Boolean.FALSE, LList.makeList(args, 0))));
            } catch (StopBlocksExecution e) {
                StopBlocksExecution exception = e;
            } catch (PermissionException e2) {
                PermissionException exception2 = e2;
                exception2.printStackTrace();
                if (this == componentObject) {
                    x = true;
                }
                if (!x ? !x : !IsEqual.apply(eventName, "PermissionNeeded")) {
                    PermissionDenied(componentObject, eventName, exception2.getPermissionNeeded());
                } else {
                    processException(exception2);
                }
            } catch (Throwable th) {
                Throwable exception3 = th;
                androidLogForm(exception3.getMessage());
                exception3.printStackTrace();
                processException(exception3);
            }
        }
    }

    public Object lookupHandler(Object componentName, Object eventName) {
        String str = null;
        String obj = componentName == null ? null : componentName.toString();
        if (eventName != null) {
            str = eventName.toString();
        }
        return lookupInFormEnvironment(misc.string$To$Symbol(EventDispatcher.makeFullEventName(obj, str)));
    }

    public void $define() {
        int i;
        Object arg0;
        Object arg02;
        Object arg03;
        WrongType wrongType;
        Object var;
        Object apply1;
        Object component$Mnname;
        Language.setDefaults(Scheme.getInstance());
        try {
            run();
        } catch (Exception exception) {
            Exception exception2 = exception;
            androidLogForm(exception2.getMessage());
            processException(exception2);
        }
        Screen1 = this;
        addToFormEnvironment(Lit0, this);
        Object obj = this.events$Mnto$Mnregister;
        Object components = obj;
        Object arg04 = obj;
        while (true) {
            i = -2;
            if (arg04 == LList.Empty) {
                try {
                    break;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "arg0", -2, arg0);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "arg0", -2, arg02);
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "lookup-in-form-environment", 0, apply1);
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "add-to-form-environment", 0, component$Mnname);
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "arg0", i, arg03);
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "add-to-global-var-environment", 0, var);
                } catch (YailRuntimeError exception3) {
                    Object obj2 = components;
                    processException(exception3);
                    return;
                }
            } else {
                try {
                    Pair arg05 = (Pair) arg04;
                    Object event$Mninfo = arg05.getCar();
                    Object apply12 = lists.car.apply1(event$Mninfo);
                    String str = null;
                    String obj3 = apply12 == null ? null : apply12.toString();
                    Object apply13 = lists.cdr.apply1(event$Mninfo);
                    if (apply13 != null) {
                        str = apply13.toString();
                    }
                    EventDispatcher.registerEventForDelegation(this, obj3, str);
                    arg04 = arg05.getCdr();
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "arg0", -2, arg04);
                }
            }
        }
        components = lists.reverse(this.components$Mnto$Mncreate);
        addToGlobalVars(Lit2, lambda$Fn1);
        arg0 = lists.reverse(this.form$Mndo$Mnafter$Mncreation);
        while (arg0 != LList.Empty) {
            Pair arg06 = (Pair) arg0;
            misc.force(arg06.getCar());
            arg0 = arg06.getCdr();
            i = -2;
        }
        Object var$Mnval = null;
        arg02 = components;
        while (arg02 != LList.Empty) {
            Pair arg07 = (Pair) arg02;
            Object component$Mninfo = arg07.getCar();
            Object apply14 = lists.caddr.apply1(component$Mninfo);
            lists.cadddr.apply1(component$Mninfo);
            Object component$Mntype = lists.cadr.apply1(component$Mninfo);
            apply1 = lists.car.apply1(component$Mninfo);
            component$Mnname = apply14;
            Object obj4 = apply1;
            Object component$Mnobject = Invoke.make.apply2(component$Mntype, lookupInFormEnvironment((Symbol) apply1));
            Invoke.invoke.apply3(component$Mnobject, "setComponentName", component$Mnname);
            SlotSet.set$Mnfield$Ex.apply3(this, component$Mnname, component$Mnobject);
            addToFormEnvironment((Symbol) component$Mnname, component$Mnobject);
            arg02 = arg07.getCdr();
            Pair pair = arg07;
            var$Mnval = component$Mninfo;
            i = -2;
        }
        arg03 = lists.reverse(this.global$Mnvars$Mnto$Mncreate);
        while (arg03 != LList.Empty) {
            Pair arg08 = (Pair) arg03;
            var$Mnval = arg08.getCar();
            var = lists.car.apply1(var$Mnval);
            addToGlobalVarEnvironment((Symbol) var, Scheme.applyToArgs.apply1(lists.cadr.apply1(var$Mnval)));
            arg03 = arg08.getCdr();
            Pair pair2 = arg08;
        }
        Object component$Mndescriptors = components;
        Object arg09 = component$Mndescriptors;
        while (arg09 != LList.Empty) {
            try {
                Pair arg010 = (Pair) arg09;
                Object component$Mninfo2 = arg010.getCar();
                lists.caddr.apply1(component$Mninfo2);
                Object obj5 = var$Mnval;
                var$Mnval = lists.cadddr.apply1(component$Mninfo2);
                if (var$Mnval != Boolean.FALSE) {
                    Scheme.applyToArgs.apply1(var$Mnval);
                }
                arg09 = arg010.getCdr();
            } catch (ClassCastException e8) {
                wrongType = new WrongType(e8, "arg0", i, arg09);
                throw wrongType;
            }
        }
        Object arg011 = component$Mndescriptors;
        while (arg011 != LList.Empty) {
            try {
                Pair arg012 = (Pair) arg011;
                Object component$Mninfo3 = arg012.getCar();
                Object apply15 = lists.caddr.apply1(component$Mninfo3);
                lists.cadddr.apply1(component$Mninfo3);
                Object obj6 = var$Mnval;
                var$Mnval = apply15;
                callInitialize(SlotGet.field.apply2(this, var$Mnval));
                arg011 = arg012.getCdr();
            } catch (ClassCastException e9) {
                wrongType = new WrongType(e9, "arg0", i, arg011);
                throw wrongType;
            }
        }
        Object obj7 = components;
    }

    public static SimpleSymbol lambda1symbolAppend$V(Object[] argsArray) {
        Object symbols = LList.makeList(argsArray, 0);
        Apply apply = Scheme.apply;
        ModuleMethod moduleMethod = strings.string$Mnappend;
        Object result = LList.Empty;
        Object arg0 = symbols;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object cdr = arg02.getCdr();
                Object car = arg02.getCar();
                try {
                    result = Pair.make(misc.symbol$To$String((Symbol) car), result);
                    arg0 = cdr;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "symbol->string", 1, car);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, arg0);
            }
        }
        Object apply2 = apply.apply2(moduleMethod, LList.reverseInPlace(result));
        try {
            return misc.string$To$Symbol((CharSequence) apply2);
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "string->symbol", 1, apply2);
        }
    }

    static Object lambda2() {
        return null;
    }
}
