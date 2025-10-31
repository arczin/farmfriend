package appinventor.ai_arnav_chhajed_000.FarmFriend;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import br.ufsc.gqs.teachablemachineimageclassifier.TeachableMachineImageClassifier;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Camera;
import com.google.appinventor.components.runtime.ChatBot;
import com.google.appinventor.components.runtime.CircularProgress;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Sound;
import com.google.appinventor.components.runtime.TinyDB;
import com.google.appinventor.components.runtime.VerticalArrangement;
import com.google.appinventor.components.runtime.WebViewer;
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
import gnu.kawa.functions.NumberCompare;
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
import kawa.standard.throw_name;

/* compiled from: Soil.yail */
public class Soil extends Form implements Runnable {
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Soil").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol("ClassifyVideoData").readResolve());
    static final SimpleSymbol Lit101 = ((SimpleSymbol) new SimpleSymbol("Confirmbutton$Click").readResolve());
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("Confirmbutton$TouchDown").readResolve());
    static final SimpleSymbol Lit103 = ((SimpleSymbol) new SimpleSymbol("Confirmbutton$TouchUp").readResolve());
    static final FString Lit104 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement10").readResolve());
    static final FString Lit106 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit107 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit108 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement11").readResolve());
    static final IntNum Lit109 = IntNum.make(1979711487);
    static final SimpleSymbol Lit11;
    static final FString Lit110 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit111 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("ResultLabel").readResolve());
    static final IntNum Lit113 = IntNum.make(16);
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit115;
    static final IntNum Lit116 = IntNum.make(-1090);
    static final FString Lit117 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit118 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit119 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement12").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("BackgroundImage").readResolve());
    static final IntNum Lit120 = IntNum.make(20);
    static final FString Lit121 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit122 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement15").readResolve());
    static final FString Lit124 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit125 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final SimpleSymbol Lit126 = ((SimpleSymbol) new SimpleSymbol("CircularProgress2").readResolve());
    static final FString Lit127 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final FString Lit128 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit129 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement13").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("OpenScreenAnimation").readResolve());
    static final FString Lit130 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit131 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit132 = ((SimpleSymbol) new SimpleSymbol("Button2").readResolve());
    static final IntNum Lit133;
    static final FString Lit134 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("Label1").readResolve());
    static final SimpleSymbol Lit136 = ((SimpleSymbol) new SimpleSymbol("ChatBot2").readResolve());
    static final SimpleSymbol Lit137 = ((SimpleSymbol) new SimpleSymbol("Converse").readResolve());
    static final PairWithPosition Lit138 = PairWithPosition.make(Lit11, PairWithPosition.make(Lit11, PairWithPosition.make(Lit11, PairWithPosition.make(Lit11, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1090320), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1090315), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1090310), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1090304);
    static final PairWithPosition Lit139 = PairWithPosition.make(Lit11, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1090337);
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("PrimaryColor").readResolve());
    static final SimpleSymbol Lit140 = ((SimpleSymbol) new SimpleSymbol("Button2$Click").readResolve());
    static final SimpleSymbol Lit141 = ((SimpleSymbol) new SimpleSymbol("Button2$TouchDown").readResolve());
    static final SimpleSymbol Lit142 = ((SimpleSymbol) new SimpleSymbol("Button2$TouchUp").readResolve());
    static final FString Lit143 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit144 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement16").readResolve());
    static final IntNum Lit145 = IntNum.make(10);
    static final FString Lit146 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit147 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit148 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement17").readResolve());
    static final IntNum Lit149 = IntNum.make(1979711487);
    static final IntNum Lit15;
    static final FString Lit150 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit151 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit152 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final IntNum Lit153 = IntNum.make(-1090);
    static final FString Lit154 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit155 = new FString("com.google.appinventor.components.runtime.Camera");
    static final SimpleSymbol Lit156 = ((SimpleSymbol) new SimpleSymbol("Camera1").readResolve());
    static final FString Lit157 = new FString("com.google.appinventor.components.runtime.Camera");
    static final FString Lit158 = new FString("com.google.appinventor.components.runtime.ChatBot");
    static final SimpleSymbol Lit159 = ((SimpleSymbol) new SimpleSymbol("ChatBot1").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("PrimaryColorDark").readResolve());
    static final SimpleSymbol Lit160 = ((SimpleSymbol) new SimpleSymbol("Token").readResolve());
    static final FString Lit161 = new FString("com.google.appinventor.components.runtime.ChatBot");
    static final FString Lit162 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final SimpleSymbol Lit163 = ((SimpleSymbol) new SimpleSymbol(TinyDB.DEFAULT_NAMESPACE).readResolve());
    static final FString Lit164 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final FString Lit165 = new FString("br.ufsc.gqs.teachablemachineimageclassifier.TeachableMachineImageClassifier");
    static final SimpleSymbol Lit166 = ((SimpleSymbol) new SimpleSymbol("UrlModel").readResolve());
    static final SimpleSymbol Lit167 = ((SimpleSymbol) new SimpleSymbol("WebViewer").readResolve());
    static final SimpleSymbol Lit168 = ((SimpleSymbol) new SimpleSymbol("component").readResolve());
    static final FString Lit169 = new FString("br.ufsc.gqs.teachablemachineimageclassifier.TeachableMachineImageClassifier");
    static final IntNum Lit17;
    static final SimpleSymbol Lit170 = ((SimpleSymbol) new SimpleSymbol("$item").readResolve());
    static final PairWithPosition Lit171 = PairWithPosition.make(Lit205, PairWithPosition.make(Lit7, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364148), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364142);
    static final PairWithPosition Lit172 = PairWithPosition.make(Lit205, PairWithPosition.make(Lit7, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364277), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364271);
    static final SimpleSymbol Lit173 = ((SimpleSymbol) new SimpleSymbol("$value").readResolve());
    static final PairWithPosition Lit174 = PairWithPosition.make(Lit7, PairWithPosition.make(Lit7, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364414), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364406);
    static final SimpleSymbol Lit175 = ((SimpleSymbol) new SimpleSymbol("$key").readResolve());
    static final SimpleSymbol Lit176 = ((SimpleSymbol) new SimpleSymbol("$result").readResolve());
    static final PairWithPosition Lit177;
    static final PairWithPosition Lit178;
    static final IntNum Lit179 = IntNum.make(-16777216);
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("ScreenOrientation").readResolve());
    static final SimpleSymbol Lit180 = ((SimpleSymbol) new SimpleSymbol("TeachableMachineImageClassifier1$GotClassification").readResolve());
    static final SimpleSymbol Lit181 = ((SimpleSymbol) new SimpleSymbol("GotClassification").readResolve());
    static final FString Lit182 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit183 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit184 = new FString("com.google.appinventor.components.runtime.ChatBot");
    static final SimpleSymbol Lit185 = ((SimpleSymbol) new SimpleSymbol("ApiKey").readResolve());
    static final FString Lit186 = new FString("com.google.appinventor.components.runtime.ChatBot");
    static final SimpleSymbol Lit187 = ((SimpleSymbol) new SimpleSymbol("$responseText").readResolve());
    static final SimpleSymbol Lit188 = ((SimpleSymbol) new SimpleSymbol("ChatBot2$GotResponse").readResolve());
    static final SimpleSymbol Lit189 = ((SimpleSymbol) new SimpleSymbol("GotResponse").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("Scrollable").readResolve());
    static final SimpleSymbol Lit190 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit191 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit192 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit193 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit194 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit195 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit196 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit197 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit198 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit199 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit200 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit201 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit202 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit203 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit204 = ((SimpleSymbol) new SimpleSymbol("proc").readResolve());
    static final SimpleSymbol Lit205 = ((SimpleSymbol) new SimpleSymbol("list").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("Sound1").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("Source").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("WebViewer2").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("GoHome").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("Reload").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("Soil$Initialize").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$maxConfidence").readResolve());
    static final FString Lit30 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement4").readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit33 = IntNum.make(-2);
    static final FString Lit34 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit35 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("backbutton").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final IntNum Lit38 = IntNum.make(1694498816);
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("g$bestClass").readResolve());
    static final IntNum Lit40 = IntNum.make(15);
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("Shape").readResolve());
    static final IntNum Lit42 = IntNum.make(1);
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final FString Lit44 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("Play").readResolve());
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("TeachableMachineImageClassifier1").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("StopWebcam").readResolve());
    static final PairWithPosition Lit48 = PairWithPosition.make(Lit11, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 200938);
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("backbutton$Click").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("backbutton$TouchDown").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("TouchDown").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("backbutton$TouchUp").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("TouchUp").readResolve());
    static final FString Lit55 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement9").readResolve());
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final FString Lit58 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit59 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final IntNum Lit6;
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final IntNum Lit62 = IntNum.make(3);
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("AlignVertical").readResolve());
    static final IntNum Lit64 = IntNum.make(2);
    static final FString Lit65 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit66 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("Button1").readResolve());
    static final IntNum Lit68;
    static final IntNum Lit69 = IntNum.make(17);
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("FontTypeface").readResolve());
    static final IntNum Lit71 = IntNum.make(-1090);
    static final FString Lit72 = new FString("com.google.appinventor.components.runtime.Button");
    static final FString Lit73 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement2").readResolve());
    static final FString Lit75 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit76 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement7").readResolve());
    static final FString Lit78 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit79 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("ActionBar").readResolve());
    static final IntNum Lit80 = IntNum.make(256);
    static final FString Lit81 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final FString Lit82 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement8").readResolve());
    static final FString Lit84 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit85 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement14").readResolve());
    static final FString Lit87 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit88 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final SimpleSymbol Lit89 = ((SimpleSymbol) new SimpleSymbol("CircularProgress1").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve());
    static final SimpleSymbol Lit90 = ((SimpleSymbol) new SimpleSymbol("Visible").readResolve());
    static final FString Lit91 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final FString Lit92 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement6").readResolve());
    static final FString Lit94 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit95 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit96 = ((SimpleSymbol) new SimpleSymbol("Confirmbutton").readResolve());
    static final IntNum Lit97;
    static final SimpleSymbol Lit98 = ((SimpleSymbol) new SimpleSymbol("FontBold").readResolve());
    static final FString Lit99 = new FString("com.google.appinventor.components.runtime.Button");
    public static Soil Soil;
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
    static final ModuleMethod lambda$Fn58 = null;
    static final ModuleMethod lambda$Fn59 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    static final ModuleMethod proc$Fn57 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public Button Button1;
    public Button Button2;
    public final ModuleMethod Button2$Click;
    public final ModuleMethod Button2$TouchDown;
    public final ModuleMethod Button2$TouchUp;
    public Camera Camera1;
    public ChatBot ChatBot1;
    public ChatBot ChatBot2;
    public final ModuleMethod ChatBot2$GotResponse;
    public CircularProgress CircularProgress1;
    public CircularProgress CircularProgress2;
    public Button Confirmbutton;
    public final ModuleMethod Confirmbutton$Click;
    public final ModuleMethod Confirmbutton$TouchDown;
    public final ModuleMethod Confirmbutton$TouchUp;
    public Label Label1;
    public Label ResultLabel;
    public final ModuleMethod Soil$Initialize;
    public Sound Sound1;
    public TeachableMachineImageClassifier TeachableMachineImageClassifier1;
    public final ModuleMethod TeachableMachineImageClassifier1$GotClassification;
    public TinyDB TinyDB1;
    public VerticalArrangement VerticalArrangement1;
    public VerticalArrangement VerticalArrangement10;
    public VerticalArrangement VerticalArrangement11;
    public VerticalArrangement VerticalArrangement12;
    public VerticalArrangement VerticalArrangement13;
    public VerticalArrangement VerticalArrangement14;
    public VerticalArrangement VerticalArrangement15;
    public VerticalArrangement VerticalArrangement16;
    public VerticalArrangement VerticalArrangement17;
    public VerticalArrangement VerticalArrangement2;
    public VerticalArrangement VerticalArrangement4;
    public VerticalArrangement VerticalArrangement6;
    public VerticalArrangement VerticalArrangement7;
    public VerticalArrangement VerticalArrangement8;
    public VerticalArrangement VerticalArrangement9;
    public WebViewer WebViewer2;
    public final ModuleMethod add$Mnto$Mncomponents;
    public final ModuleMethod add$Mnto$Mnevents;
    public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
    public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
    public final ModuleMethod android$Mnlog$Mnform;
    public Button backbutton;
    public final ModuleMethod backbutton$Click;
    public final ModuleMethod backbutton$TouchDown;
    public final ModuleMethod backbutton$TouchUp;
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
        Lit11 = simpleSymbol;
        Lit178 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(Lit11, PairWithPosition.make(Lit11, PairWithPosition.make(Lit11, PairWithPosition.make(Lit11, PairWithPosition.make(Lit11, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364910), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364905), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364900), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364895), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364890), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364884);
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit7 = simpleSymbol2;
        Lit177 = PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Soil.yail", 1364855);
        int[] iArr = new int[2];
        iArr[0] = -1;
        Lit133 = IntNum.make(iArr);
        int[] iArr2 = new int[2];
        iArr2[0] = -1728053248;
        Lit115 = IntNum.make(iArr2);
        int[] iArr3 = new int[2];
        iArr3[0] = -1;
        Lit97 = IntNum.make(iArr3);
        int[] iArr4 = new int[2];
        iArr4[0] = -845;
        Lit68 = IntNum.make(iArr4);
        int[] iArr5 = new int[2];
        iArr5[0] = -9592721;
        Lit17 = IntNum.make(iArr5);
        int[] iArr6 = new int[2];
        iArr6[0] = -8271996;
        Lit15 = IntNum.make(iArr6);
        int[] iArr7 = new int[2];
        iArr7[0] = -18654;
        Lit6 = IntNum.make(iArr7);
    }

    public Soil() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        frame2.$main = this;
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit190, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit191, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit192, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit193, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit194, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit195, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit196, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit197, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit198, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit199, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit200, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit201, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit202, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit203, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, (Object) null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime15798620743098228844.scm:639");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, (Object) null, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 21, (Object) null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 22, (Object) null, 0);
        this.Soil$Initialize = new ModuleMethod(frame2, 23, Lit28, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 24, (Object) null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 25, (Object) null, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 26, (Object) null, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 27, (Object) null, 0);
        this.backbutton$Click = new ModuleMethod(frame2, 28, Lit49, 0);
        this.backbutton$TouchDown = new ModuleMethod(frame2, 29, Lit51, 0);
        this.backbutton$TouchUp = new ModuleMethod(frame2, 30, Lit53, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 31, (Object) null, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 32, (Object) null, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 33, (Object) null, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 34, (Object) null, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 35, (Object) null, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 36, (Object) null, 0);
        lambda$Fn15 = new ModuleMethod(frame2, 37, (Object) null, 0);
        lambda$Fn16 = new ModuleMethod(frame2, 38, (Object) null, 0);
        lambda$Fn17 = new ModuleMethod(frame2, 39, (Object) null, 0);
        lambda$Fn18 = new ModuleMethod(frame2, 40, (Object) null, 0);
        lambda$Fn19 = new ModuleMethod(frame2, 41, (Object) null, 0);
        lambda$Fn20 = new ModuleMethod(frame2, 42, (Object) null, 0);
        lambda$Fn21 = new ModuleMethod(frame2, 43, (Object) null, 0);
        lambda$Fn22 = new ModuleMethod(frame2, 44, (Object) null, 0);
        lambda$Fn23 = new ModuleMethod(frame2, 45, (Object) null, 0);
        lambda$Fn24 = new ModuleMethod(frame2, 46, (Object) null, 0);
        lambda$Fn25 = new ModuleMethod(frame2, 47, (Object) null, 0);
        lambda$Fn26 = new ModuleMethod(frame2, 48, (Object) null, 0);
        lambda$Fn27 = new ModuleMethod(frame2, 49, (Object) null, 0);
        lambda$Fn28 = new ModuleMethod(frame2, 50, (Object) null, 0);
        lambda$Fn29 = new ModuleMethod(frame2, 51, (Object) null, 0);
        lambda$Fn30 = new ModuleMethod(frame2, 52, (Object) null, 0);
        this.Confirmbutton$Click = new ModuleMethod(frame2, 53, Lit101, 0);
        this.Confirmbutton$TouchDown = new ModuleMethod(frame2, 54, Lit102, 0);
        this.Confirmbutton$TouchUp = new ModuleMethod(frame2, 55, Lit103, 0);
        lambda$Fn31 = new ModuleMethod(frame2, 56, (Object) null, 0);
        lambda$Fn32 = new ModuleMethod(frame2, 57, (Object) null, 0);
        lambda$Fn33 = new ModuleMethod(frame2, 58, (Object) null, 0);
        lambda$Fn34 = new ModuleMethod(frame2, 59, (Object) null, 0);
        lambda$Fn35 = new ModuleMethod(frame2, 60, (Object) null, 0);
        lambda$Fn36 = new ModuleMethod(frame2, 61, (Object) null, 0);
        lambda$Fn37 = new ModuleMethod(frame2, 62, (Object) null, 0);
        lambda$Fn38 = new ModuleMethod(frame2, 63, (Object) null, 0);
        lambda$Fn39 = new ModuleMethod(frame2, 64, (Object) null, 0);
        lambda$Fn40 = new ModuleMethod(frame2, 65, (Object) null, 0);
        lambda$Fn41 = new ModuleMethod(frame2, 66, (Object) null, 0);
        lambda$Fn42 = new ModuleMethod(frame2, 67, (Object) null, 0);
        lambda$Fn43 = new ModuleMethod(frame2, 68, (Object) null, 0);
        lambda$Fn44 = new ModuleMethod(frame2, 69, (Object) null, 0);
        lambda$Fn45 = new ModuleMethod(frame2, 70, (Object) null, 0);
        lambda$Fn46 = new ModuleMethod(frame2, 71, (Object) null, 0);
        this.Button2$Click = new ModuleMethod(frame2, 72, Lit140, 0);
        this.Button2$TouchDown = new ModuleMethod(frame2, 73, Lit141, 0);
        this.Button2$TouchUp = new ModuleMethod(frame2, 74, Lit142, 0);
        lambda$Fn47 = new ModuleMethod(frame2, 75, (Object) null, 0);
        lambda$Fn48 = new ModuleMethod(frame2, 76, (Object) null, 0);
        lambda$Fn49 = new ModuleMethod(frame2, 77, (Object) null, 0);
        lambda$Fn50 = new ModuleMethod(frame2, 78, (Object) null, 0);
        lambda$Fn51 = new ModuleMethod(frame2, 79, (Object) null, 0);
        lambda$Fn52 = new ModuleMethod(frame2, 80, (Object) null, 0);
        lambda$Fn53 = new ModuleMethod(frame2, 81, (Object) null, 0);
        lambda$Fn54 = new ModuleMethod(frame2, 82, (Object) null, 0);
        lambda$Fn55 = new ModuleMethod(frame2, 83, (Object) null, 0);
        lambda$Fn56 = new ModuleMethod(frame2, 84, (Object) null, 0);
        proc$Fn57 = new ModuleMethod(frame2, 85, Lit204, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.TeachableMachineImageClassifier1$GotClassification = new ModuleMethod(frame2, 86, Lit180, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn58 = new ModuleMethod(frame2, 87, (Object) null, 0);
        lambda$Fn59 = new ModuleMethod(frame2, 88, (Object) null, 0);
        this.ChatBot2$GotResponse = new ModuleMethod(frame2, 89, Lit188, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
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
        runtime.$instance.run();
        this.$Stdebug$Mnform$St = Boolean.FALSE;
        this.form$Mnenvironment = Environment.make(misc.symbol$To$String(Lit0));
        FString stringAppend = strings.stringAppend(misc.symbol$To$String(Lit0), "-global-vars");
        this.global$Mnvar$Mnenvironment = Environment.make(stringAppend == null ? null : stringAppend.toString());
        Soil = null;
        this.form$Mnname$Mnsymbol = Lit0;
        this.events$Mnto$Mnregister = LList.Empty;
        this.components$Mnto$Mncreate = LList.Empty;
        this.global$Mnvars$Mnto$Mncreate = LList.Empty;
        this.form$Mndo$Mnafter$Mncreation = LList.Empty;
        runtime.$instance.run();
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit3, Component.TYPEFACE_DEFAULT), $result);
        } else {
            addToGlobalVars(Lit3, lambda$Fn2);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit4, ""), $result);
        } else {
            addToGlobalVars(Lit4, lambda$Fn3);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit0, Lit5, Lit6, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit8, Boolean.TRUE, Lit9);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit10, "gfdhgdghfu", Lit11);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit12, "Do-sharpEdit.png", Lit11);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit13, "zoom", Lit11);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit14, Lit15, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Lit17, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit18, "unspecified", Lit11);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit19, Boolean.TRUE, Lit9);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit20, Boolean.TRUE, Lit9);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit21, "Responsive", Lit11);
            Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit22, "Crop Health Checker", Lit11), $result);
        } else {
            addToFormDoAfterCreation(new Promise(lambda$Fn4));
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit28, this.Soil$Initialize);
        } else {
            addToFormEnvironment(Lit28, this.Soil$Initialize);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Soil", "Initialize");
        } else {
            addToEvents(Lit0, Lit29);
        }
        this.VerticalArrangement4 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit30, Lit31, lambda$Fn5), $result);
        } else {
            addToComponents(Lit0, Lit34, Lit31, lambda$Fn6);
        }
        this.backbutton = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit31, Lit35, Lit36, lambda$Fn7), $result);
        } else {
            addToComponents(Lit31, Lit44, Lit36, lambda$Fn8);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit49, this.backbutton$Click);
        } else {
            addToFormEnvironment(Lit49, this.backbutton$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "Click");
        } else {
            addToEvents(Lit36, Lit50);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit51, this.backbutton$TouchDown);
        } else {
            addToFormEnvironment(Lit51, this.backbutton$TouchDown);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "TouchDown");
        } else {
            addToEvents(Lit36, Lit52);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit53, this.backbutton$TouchUp);
        } else {
            addToFormEnvironment(Lit53, this.backbutton$TouchUp);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "TouchUp");
        } else {
            addToEvents(Lit36, Lit54);
        }
        this.VerticalArrangement9 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit55, Lit56, lambda$Fn9), $result);
        } else {
            addToComponents(Lit0, Lit58, Lit56, lambda$Fn10);
        }
        this.VerticalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit59, Lit60, lambda$Fn11), $result);
        } else {
            addToComponents(Lit0, Lit65, Lit60, lambda$Fn12);
        }
        this.Button1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit60, Lit66, Lit67, lambda$Fn13), $result);
        } else {
            addToComponents(Lit60, Lit72, Lit67, lambda$Fn14);
        }
        this.VerticalArrangement2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit73, Lit74, lambda$Fn15), $result);
        } else {
            addToComponents(Lit0, Lit75, Lit74, lambda$Fn16);
        }
        this.VerticalArrangement7 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit76, Lit77, lambda$Fn17), $result);
        } else {
            addToComponents(Lit0, Lit78, Lit77, lambda$Fn18);
        }
        this.WebViewer2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit77, Lit79, Lit25, lambda$Fn19), $result);
        } else {
            addToComponents(Lit77, Lit81, Lit25, lambda$Fn20);
        }
        this.VerticalArrangement8 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit82, Lit83, lambda$Fn21), $result);
        } else {
            addToComponents(Lit0, Lit84, Lit83, lambda$Fn22);
        }
        this.VerticalArrangement14 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit85, Lit86, lambda$Fn23), $result);
        } else {
            addToComponents(Lit0, Lit87, Lit86, lambda$Fn24);
        }
        this.CircularProgress1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit86, Lit88, Lit89, lambda$Fn25), $result);
        } else {
            addToComponents(Lit86, Lit91, Lit89, lambda$Fn26);
        }
        this.VerticalArrangement6 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit92, Lit93, lambda$Fn27), $result);
        } else {
            addToComponents(Lit0, Lit94, Lit93, lambda$Fn28);
        }
        this.Confirmbutton = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit93, Lit95, Lit96, lambda$Fn29), $result);
        } else {
            addToComponents(Lit93, Lit99, Lit96, lambda$Fn30);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit101, this.Confirmbutton$Click);
        } else {
            addToFormEnvironment(Lit101, this.Confirmbutton$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Confirmbutton", "Click");
        } else {
            addToEvents(Lit96, Lit50);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit102, this.Confirmbutton$TouchDown);
        } else {
            addToFormEnvironment(Lit102, this.Confirmbutton$TouchDown);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Confirmbutton", "TouchDown");
        } else {
            addToEvents(Lit96, Lit52);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit103, this.Confirmbutton$TouchUp);
        } else {
            addToFormEnvironment(Lit103, this.Confirmbutton$TouchUp);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Confirmbutton", "TouchUp");
        } else {
            addToEvents(Lit96, Lit54);
        }
        this.VerticalArrangement10 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit104, Lit105, lambda$Fn31), $result);
        } else {
            addToComponents(Lit0, Lit106, Lit105, lambda$Fn32);
        }
        this.VerticalArrangement11 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit107, Lit108, lambda$Fn33), $result);
        } else {
            addToComponents(Lit0, Lit110, Lit108, lambda$Fn34);
        }
        this.ResultLabel = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit108, Lit111, Lit112, lambda$Fn35), $result);
        } else {
            addToComponents(Lit108, Lit117, Lit112, lambda$Fn36);
        }
        this.VerticalArrangement12 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit118, Lit119, lambda$Fn37), $result);
        } else {
            addToComponents(Lit0, Lit121, Lit119, lambda$Fn38);
        }
        this.VerticalArrangement15 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit122, Lit123, lambda$Fn39), $result);
        } else {
            addToComponents(Lit0, Lit124, Lit123, lambda$Fn40);
        }
        this.CircularProgress2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit123, Lit125, Lit126, lambda$Fn41), $result);
        } else {
            addToComponents(Lit123, Lit127, Lit126, lambda$Fn42);
        }
        this.VerticalArrangement13 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit128, Lit129, lambda$Fn43), $result);
        } else {
            addToComponents(Lit0, Lit130, Lit129, lambda$Fn44);
        }
        this.Button2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit129, Lit131, Lit132, lambda$Fn45), $result);
        } else {
            addToComponents(Lit129, Lit134, Lit132, lambda$Fn46);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit140, this.Button2$Click);
        } else {
            addToFormEnvironment(Lit140, this.Button2$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "Click");
        } else {
            addToEvents(Lit132, Lit50);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit141, this.Button2$TouchDown);
        } else {
            addToFormEnvironment(Lit141, this.Button2$TouchDown);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "TouchDown");
        } else {
            addToEvents(Lit132, Lit52);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit142, this.Button2$TouchUp);
        } else {
            addToFormEnvironment(Lit142, this.Button2$TouchUp);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "TouchUp");
        } else {
            addToEvents(Lit132, Lit54);
        }
        this.VerticalArrangement16 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit143, Lit144, lambda$Fn47), $result);
        } else {
            addToComponents(Lit0, Lit146, Lit144, lambda$Fn48);
        }
        this.VerticalArrangement17 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit147, Lit148, lambda$Fn49), $result);
        } else {
            addToComponents(Lit0, Lit150, Lit148, lambda$Fn50);
        }
        this.Label1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit148, Lit151, Lit135, lambda$Fn51), $result);
        } else {
            addToComponents(Lit148, Lit154, Lit135, lambda$Fn52);
        }
        this.Camera1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit155, Lit156, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit157, Lit156, Boolean.FALSE);
        }
        this.ChatBot1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit158, Lit159, lambda$Fn53), $result);
        } else {
            addToComponents(Lit0, Lit161, Lit159, lambda$Fn54);
        }
        this.TinyDB1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit162, Lit163, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit164, Lit163, Boolean.FALSE);
        }
        this.TeachableMachineImageClassifier1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit165, Lit46, lambda$Fn55), $result);
        } else {
            addToComponents(Lit0, Lit169, Lit46, lambda$Fn56);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit180, this.TeachableMachineImageClassifier1$GotClassification);
        } else {
            addToFormEnvironment(Lit180, this.TeachableMachineImageClassifier1$GotClassification);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "TeachableMachineImageClassifier1", "GotClassification");
        } else {
            addToEvents(Lit46, Lit181);
        }
        this.Sound1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit182, Lit23, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit183, Lit23, Boolean.FALSE);
        }
        this.ChatBot2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit184, Lit136, lambda$Fn58), $result);
        } else {
            addToComponents(Lit0, Lit186, Lit136, lambda$Fn59);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit188, this.ChatBot2$GotResponse);
        } else {
            addToFormEnvironment(Lit188, this.ChatBot2$GotResponse);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "ChatBot2", "GotResponse");
        } else {
            addToEvents(Lit136, Lit189);
        }
        runtime.initRuntime();
    }

    static String lambda3() {
        return Component.TYPEFACE_DEFAULT;
    }

    static String lambda4() {
        return "";
    }

    static Object lambda5() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit5, Lit6, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit8, Boolean.TRUE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit10, "gfdhgdghfu", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit12, "Do-sharpEdit.png", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit13, "zoom", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit14, Lit15, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Lit17, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit18, "unspecified", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit19, Boolean.TRUE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit20, Boolean.TRUE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit21, "Responsive", Lit11);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit22, "Crop Health Checker", Lit11);
    }

    public Object Soil$Initialize() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit23, Lit24, "ClickSoundEffect(1).mp3", Lit11);
        runtime.callComponentMethod(Lit25, Lit26, LList.Empty, LList.Empty);
        return runtime.callComponentMethod(Lit25, Lit27, LList.Empty, LList.Empty);
    }

    static Object lambda6() {
        return runtime.setAndCoerceProperty$Ex(Lit31, Lit32, Lit33, Lit7);
    }

    static Object lambda7() {
        return runtime.setAndCoerceProperty$Ex(Lit31, Lit32, Lit33, Lit7);
    }

    static Object lambda8() {
        runtime.setAndCoerceProperty$Ex(Lit36, Lit37, Lit38, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit39, Lit40, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit41, Lit42, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit36, Lit43, "⬅️", Lit11);
    }

    static Object lambda9() {
        runtime.setAndCoerceProperty$Ex(Lit36, Lit37, Lit38, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit39, Lit40, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit41, Lit42, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit36, Lit43, "⬅️", Lit11);
    }

    public Object backbutton$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit23, Lit45, LList.Empty, LList.Empty);
        runtime.callComponentMethod(Lit46, Lit47, LList.Empty, LList.Empty);
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit48, "open another screen");
    }

    public Object backbutton$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit36, Lit39, "13", Lit7);
    }

    public Object backbutton$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit36, Lit39, "15", Lit7);
    }

    static Object lambda10() {
        return runtime.setAndCoerceProperty$Ex(Lit56, Lit57, Lit40, Lit7);
    }

    static Object lambda11() {
        return runtime.setAndCoerceProperty$Ex(Lit56, Lit57, Lit40, Lit7);
    }

    static Object lambda12() {
        runtime.setAndCoerceProperty$Ex(Lit60, Lit61, Lit62, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit60, Lit63, Lit64, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit60, Lit32, Lit33, Lit7);
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit60, Lit61, Lit62, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit60, Lit63, Lit64, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit60, Lit32, Lit33, Lit7);
    }

    static Object lambda14() {
        runtime.setAndCoerceProperty$Ex(Lit67, Lit37, Lit68, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit67, Lit39, Lit69, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit67, Lit70, "Inter_18pt-Bold.ttf", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit67, Lit41, Lit42, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit67, Lit43, "🌱  Hold still and focus on only 1 leaf (close up) to get analysis.", Lit11);
        return runtime.setAndCoerceProperty$Ex(Lit67, Lit32, Lit71, Lit7);
    }

    static Object lambda15() {
        runtime.setAndCoerceProperty$Ex(Lit67, Lit37, Lit68, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit67, Lit39, Lit69, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit67, Lit70, "Inter_18pt-Bold.ttf", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit67, Lit41, Lit42, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit67, Lit43, "🌱  Hold still and focus on only 1 leaf (close up) to get analysis.", Lit11);
        return runtime.setAndCoerceProperty$Ex(Lit67, Lit32, Lit71, Lit7);
    }

    static Object lambda16() {
        runtime.setAndCoerceProperty$Ex(Lit74, Lit61, Lit62, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit63, Lit64, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit57, Lit40, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit32, Lit33, Lit7);
    }

    static Object lambda17() {
        runtime.setAndCoerceProperty$Ex(Lit74, Lit61, Lit62, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit63, Lit64, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit57, Lit40, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit32, Lit33, Lit7);
    }

    static Object lambda18() {
        runtime.setAndCoerceProperty$Ex(Lit77, Lit61, Lit62, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit77, Lit32, Lit33, Lit7);
    }

    static Object lambda19() {
        runtime.setAndCoerceProperty$Ex(Lit77, Lit61, Lit62, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit77, Lit32, Lit33, Lit7);
    }

    static Object lambda20() {
        runtime.setAndCoerceProperty$Ex(Lit25, Lit57, Lit80, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit25, Lit32, Lit80, Lit7);
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit25, Lit57, Lit80, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit25, Lit32, Lit80, Lit7);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit83, Lit57, Lit40, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit83, Lit32, Lit33, Lit7);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit83, Lit57, Lit40, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit83, Lit32, Lit33, Lit7);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit86, Lit61, Lit62, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit86, Lit32, Lit33, Lit7);
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit86, Lit61, Lit62, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit86, Lit32, Lit33, Lit7);
    }

    static Object lambda26() {
        return runtime.setAndCoerceProperty$Ex(Lit89, Lit90, Boolean.FALSE, Lit9);
    }

    static Object lambda27() {
        return runtime.setAndCoerceProperty$Ex(Lit89, Lit90, Boolean.FALSE, Lit9);
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit93, Lit61, Lit62, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit93, Lit63, Lit64, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit93, Lit32, Lit33, Lit7);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit93, Lit61, Lit62, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit93, Lit63, Lit64, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit93, Lit32, Lit33, Lit7);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit96, Lit37, Lit97, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit98, Boolean.TRUE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit39, Lit40, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit70, Component.TYPEFACE_MONOSPACE, Lit11);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit41, Lit42, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit96, Lit43, "✅  Confirm", Lit11);
    }

    static Object lambda31() {
        runtime.setAndCoerceProperty$Ex(Lit96, Lit37, Lit97, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit98, Boolean.TRUE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit39, Lit40, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit70, Component.TYPEFACE_MONOSPACE, Lit11);
        runtime.setAndCoerceProperty$Ex(Lit96, Lit41, Lit42, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit96, Lit43, "✅  Confirm", Lit11);
    }

    public Object Confirmbutton$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit46, Lit100, LList.Empty, LList.Empty);
        runtime.callComponentMethod(Lit23, Lit45, LList.Empty, LList.Empty);
        return runtime.setAndCoerceProperty$Ex(Lit89, Lit90, Boolean.TRUE, Lit9);
    }

    public Object Confirmbutton$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit96, Lit39, "13", Lit7);
    }

    public Object Confirmbutton$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit96, Lit39, "15", Lit7);
    }

    static Object lambda32() {
        return runtime.setAndCoerceProperty$Ex(Lit105, Lit57, Lit40, Lit7);
    }

    static Object lambda33() {
        return runtime.setAndCoerceProperty$Ex(Lit105, Lit57, Lit40, Lit7);
    }

    static Object lambda34() {
        runtime.setAndCoerceProperty$Ex(Lit108, Lit61, Lit62, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit108, Lit37, Lit109, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit108, Lit32, Lit33, Lit7);
    }

    static Object lambda35() {
        runtime.setAndCoerceProperty$Ex(Lit108, Lit61, Lit62, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit108, Lit37, Lit109, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit108, Lit32, Lit33, Lit7);
    }

    static Object lambda36() {
        runtime.setAndCoerceProperty$Ex(Lit112, Lit98, Boolean.TRUE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit112, Lit39, Lit113, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit112, Lit70, "Inter_18pt-Bold.ttf", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit112, Lit43, "Result", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit112, Lit114, Lit115, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit112, Lit32, Lit116, Lit7);
    }

    static Object lambda37() {
        runtime.setAndCoerceProperty$Ex(Lit112, Lit98, Boolean.TRUE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit112, Lit39, Lit113, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit112, Lit70, "Inter_18pt-Bold.ttf", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit112, Lit43, "Result", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit112, Lit114, Lit115, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit112, Lit32, Lit116, Lit7);
    }

    static Object lambda38() {
        return runtime.setAndCoerceProperty$Ex(Lit119, Lit57, Lit120, Lit7);
    }

    static Object lambda39() {
        return runtime.setAndCoerceProperty$Ex(Lit119, Lit57, Lit120, Lit7);
    }

    static Object lambda40() {
        runtime.setAndCoerceProperty$Ex(Lit123, Lit61, Lit62, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit123, Lit32, Lit33, Lit7);
    }

    static Object lambda41() {
        runtime.setAndCoerceProperty$Ex(Lit123, Lit61, Lit62, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit123, Lit32, Lit33, Lit7);
    }

    static Object lambda42() {
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit90, Boolean.FALSE, Lit9);
    }

    static Object lambda43() {
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit90, Boolean.FALSE, Lit9);
    }

    static Object lambda44() {
        runtime.setAndCoerceProperty$Ex(Lit129, Lit61, Lit62, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit129, Lit32, Lit33, Lit7);
    }

    static Object lambda45() {
        runtime.setAndCoerceProperty$Ex(Lit129, Lit61, Lit62, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit129, Lit32, Lit33, Lit7);
    }

    static Object lambda46() {
        runtime.setAndCoerceProperty$Ex(Lit132, Lit37, Lit133, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit132, Lit98, Boolean.TRUE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit132, Lit39, Lit40, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit132, Lit70, Component.TYPEFACE_MONOSPACE, Lit11);
        runtime.setAndCoerceProperty$Ex(Lit132, Lit41, Lit42, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit132, Lit43, "💊 Get Suggestions on Cure", Lit11);
        return runtime.setAndCoerceProperty$Ex(Lit132, Lit90, Boolean.FALSE, Lit9);
    }

    static Object lambda47() {
        runtime.setAndCoerceProperty$Ex(Lit132, Lit37, Lit133, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit132, Lit98, Boolean.TRUE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit132, Lit39, Lit40, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit132, Lit70, Component.TYPEFACE_MONOSPACE, Lit11);
        runtime.setAndCoerceProperty$Ex(Lit132, Lit41, Lit42, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit132, Lit43, "💊 Get Suggestions on Cure", Lit11);
        return runtime.setAndCoerceProperty$Ex(Lit132, Lit90, Boolean.FALSE, Lit9);
    }

    public Object Button2$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit23, Lit45, LList.Empty, LList.Empty);
        runtime.setAndCoerceProperty$Ex(Lit126, Lit90, Boolean.TRUE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit90, Boolean.TRUE, Lit9);
        runtime.callComponentMethod(Lit136, Lit137, LList.list1(runtime.callYailPrimitive(strings.string$Mnappend, LList.list4("You are an agricultural assistant. Based on the following plant disease, generate a short, friendly message in 2–3 sentences that includes: - The name of the disease - A simple explanation - One basic suggestion for what the farmer can do", "Disease: - ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), "Keep the message farmer-friendly and avoid overly technical terms. Also here are the code letters - ls = leaf spot sls = septoria leaf spot bs = bacterial spot br = black rot"), Lit138, "join")), Lit139);
        return runtime.callComponentMethod(Lit46, Lit47, LList.Empty, LList.Empty);
    }

    public Object Button2$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit132, Lit39, "13", Lit7);
    }

    public Object Button2$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit132, Lit39, "15", Lit7);
    }

    static Object lambda48() {
        return runtime.setAndCoerceProperty$Ex(Lit144, Lit57, Lit145, Lit7);
    }

    static Object lambda49() {
        return runtime.setAndCoerceProperty$Ex(Lit144, Lit57, Lit145, Lit7);
    }

    static Object lambda50() {
        runtime.setAndCoerceProperty$Ex(Lit148, Lit61, Lit62, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit148, Lit37, Lit149, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit148, Lit32, Lit33, Lit7);
    }

    static Object lambda51() {
        runtime.setAndCoerceProperty$Ex(Lit148, Lit61, Lit62, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit148, Lit37, Lit149, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit148, Lit32, Lit33, Lit7);
    }

    static Object lambda52() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit39, Lit113, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit70, "Inter_18pt-Bold.ttf", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit43, "Note: Response time depends on your internet speed. 📡", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit152, Lit42, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit90, Boolean.FALSE, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit32, Lit153, Lit7);
    }

    static Object lambda53() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit39, Lit113, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit70, "Inter_18pt-Bold.ttf", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit43, "Note: Response time depends on your internet speed. 📡", Lit11);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit152, Lit42, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit90, Boolean.FALSE, Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit32, Lit153, Lit7);
    }

    static Object lambda54() {
        return runtime.setAndCoerceProperty$Ex(Lit159, Lit160, "2Yf5keLjW1SXPWru55hsZGysg7pn9qUr7arAVALtgNeh7auPxYHrPohTCqobKtx4MrzTwXkuAuZtHtW2QKVSjqaMXa1RbsNAo6neWaHe", Lit11);
    }

    static Object lambda55() {
        return runtime.setAndCoerceProperty$Ex(Lit159, Lit160, "2Yf5keLjW1SXPWru55hsZGysg7pn9qUr7arAVALtgNeh7auPxYHrPohTCqobKtx4MrzTwXkuAuZtHtW2QKVSjqaMXa1RbsNAo6neWaHe", Lit11);
    }

    /* compiled from: Soil.yail */
    public class frame extends ModuleBody {
        Soil $main;

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 2:
                    if (!(obj instanceof Soil)) {
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
                    if (!(obj instanceof Soil)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 85:
                case 86:
                case 89:
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
                    if (!(obj instanceof Soil)) {
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
                    if (!(obj instanceof Soil)) {
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
                case 85:
                    return Soil.lambda58proc(obj);
                case 86:
                    return this.$main.TeachableMachineImageClassifier1$GotClassification(obj);
                case 89:
                    return this.$main.ChatBot2$GotResponse(obj);
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
                    Soil soil = this.$main;
                    try {
                        Component component = (Component) obj;
                        try {
                            String str = (String) obj2;
                            try {
                                if (obj3 == Boolean.FALSE) {
                                    z = false;
                                }
                                try {
                                    soil.dispatchGenericEvent(component, str, z, (Object[]) obj4);
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

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 18:
                    return Soil.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return Soil.lambda3();
                case 21:
                    return Soil.lambda4();
                case 22:
                    return Soil.lambda5();
                case 23:
                    return this.$main.Soil$Initialize();
                case 24:
                    return Soil.lambda6();
                case 25:
                    return Soil.lambda7();
                case 26:
                    return Soil.lambda8();
                case 27:
                    return Soil.lambda9();
                case 28:
                    return this.$main.backbutton$Click();
                case 29:
                    return this.$main.backbutton$TouchDown();
                case 30:
                    return this.$main.backbutton$TouchUp();
                case 31:
                    return Soil.lambda10();
                case 32:
                    return Soil.lambda11();
                case 33:
                    return Soil.lambda12();
                case 34:
                    return Soil.lambda13();
                case 35:
                    return Soil.lambda14();
                case 36:
                    return Soil.lambda15();
                case 37:
                    return Soil.lambda16();
                case 38:
                    return Soil.lambda17();
                case 39:
                    return Soil.lambda18();
                case 40:
                    return Soil.lambda19();
                case 41:
                    return Soil.lambda20();
                case 42:
                    return Soil.lambda21();
                case 43:
                    return Soil.lambda22();
                case 44:
                    return Soil.lambda23();
                case 45:
                    return Soil.lambda24();
                case 46:
                    return Soil.lambda25();
                case 47:
                    return Soil.lambda26();
                case 48:
                    return Soil.lambda27();
                case 49:
                    return Soil.lambda28();
                case 50:
                    return Soil.lambda29();
                case 51:
                    return Soil.lambda30();
                case 52:
                    return Soil.lambda31();
                case 53:
                    return this.$main.Confirmbutton$Click();
                case 54:
                    return this.$main.Confirmbutton$TouchDown();
                case 55:
                    return this.$main.Confirmbutton$TouchUp();
                case 56:
                    return Soil.lambda32();
                case 57:
                    return Soil.lambda33();
                case 58:
                    return Soil.lambda34();
                case 59:
                    return Soil.lambda35();
                case 60:
                    return Soil.lambda36();
                case 61:
                    return Soil.lambda37();
                case 62:
                    return Soil.lambda38();
                case 63:
                    return Soil.lambda39();
                case 64:
                    return Soil.lambda40();
                case 65:
                    return Soil.lambda41();
                case 66:
                    return Soil.lambda42();
                case 67:
                    return Soil.lambda43();
                case 68:
                    return Soil.lambda44();
                case 69:
                    return Soil.lambda45();
                case 70:
                    return Soil.lambda46();
                case 71:
                    return Soil.lambda47();
                case 72:
                    return this.$main.Button2$Click();
                case 73:
                    return this.$main.Button2$TouchDown();
                case 74:
                    return this.$main.Button2$TouchUp();
                case 75:
                    return Soil.lambda48();
                case 76:
                    return Soil.lambda49();
                case 77:
                    return Soil.lambda50();
                case 78:
                    return Soil.lambda51();
                case 79:
                    return Soil.lambda52();
                case 80:
                    return Soil.lambda53();
                case 81:
                    return Soil.lambda54();
                case 82:
                    return Soil.lambda55();
                case 83:
                    return Soil.lambda56();
                case 84:
                    return Soil.lambda57();
                case 87:
                    return Soil.lambda59();
                case 88:
                    return Soil.lambda60();
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
                case 87:
                case 88:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
    }

    static Object lambda56() {
        runtime.setAndCoerceProperty$Ex(Lit46, Lit166, "https://teachablemachine.withgoogle.com/models/NT5rPNQOe/", Lit11);
        return runtime.setAndCoerceProperty$Ex(Lit46, Lit167, runtime.lookupInCurrentFormEnvironment(Lit25), Lit168);
    }

    static Object lambda57() {
        runtime.setAndCoerceProperty$Ex(Lit46, Lit166, "https://teachablemachine.withgoogle.com/models/NT5rPNQOe/", Lit11);
        return runtime.setAndCoerceProperty$Ex(Lit46, Lit167, runtime.lookupInCurrentFormEnvironment(Lit25), Lit168);
    }

    public Object TeachableMachineImageClassifier1$GotClassification(Object $result) {
        Object obj;
        Object $result2 = runtime.sanitizeComponentData($result);
        runtime.setThisForm();
        runtime.addGlobalVarToCurrentFormEnvironment(Lit3, Component.TYPEFACE_DEFAULT);
        runtime.addGlobalVarToCurrentFormEnvironment(Lit4, "");
        Procedure proc = proc$Fn57;
        ModuleMethod moduleMethod = proc$Fn57;
        if ($result2 instanceof Package) {
            obj = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit176), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj = $result2;
        }
        runtime.yailForEach(moduleMethod, obj);
        runtime.setAndCoerceProperty$Ex(Lit89, Lit90, Boolean.FALSE, Lit9);
        SimpleSymbol simpleSymbol = Lit112;
        SimpleSymbol simpleSymbol2 = Lit43;
        ModuleMethod moduleMethod2 = strings.string$Mnappend;
        Pair list1 = LList.list1("Most Probable disease: ");
        LList.chain1(LList.chain4(list1, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), " (", runtime.callYailPrimitive(runtime.yail$Mnround, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit177, "round"), "%"), ")");
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, runtime.callYailPrimitive(moduleMethod2, list1, Lit178, "join"), Lit11);
        runtime.setAndCoerceProperty$Ex(Lit112, Lit114, Lit179, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit132, Lit90, Boolean.TRUE, Lit9);
    }

    public static Object lambda58proc(Object $item) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        ModuleMethod moduleMethod = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($item instanceof Package) {
            obj = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit170), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj = $item;
        }
        Object $key = runtime.callYailPrimitive(moduleMethod, LList.list2(obj, Lit42), Lit171, "select list item");
        ModuleMethod moduleMethod2 = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($item instanceof Package) {
            obj2 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit170), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj2 = $item;
        }
        Object $value = runtime.callYailPrimitive(moduleMethod2, LList.list2(obj2, Lit64), Lit172, "select list item");
        NumberCompare numberCompare = Scheme.numGrt;
        if ($value instanceof Package) {
            obj3 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit173), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj3 = $value;
        }
        if (runtime.callYailPrimitive(numberCompare, LList.list2(obj3, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit174, ">") == Boolean.FALSE) {
            return Values.empty;
        }
        SimpleSymbol simpleSymbol = Lit3;
        if ($value instanceof Package) {
            obj4 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit173), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj4 = $value;
        }
        runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol, obj4);
        SimpleSymbol simpleSymbol2 = Lit4;
        if ($key instanceof Package) {
            obj5 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit175), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj5 = $key;
        }
        return runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol2, obj5);
    }

    static Object lambda59() {
        runtime.setAndCoerceProperty$Ex(Lit136, Lit185, runtime.textDeobfuscate("§«å¼ªÅ©­¡Ëº³¦Õ­Ä¥£Ñ ~GMoG``-7<Lfd[UBmL.km9iRb[B]gljhgldhvCXYQIp{\u0001l\u000fjS_Szb\u0012o\u0006\\~zeaP^V\u001c\u0014\u001d>\u00125\u0011w\u0010\u001a90\u0001 \u00049;e6\u0013x\u000f\fb!&\u0005#\re\u001c-\u0004\t)<\u0016\u001d\r7R\u000f\u000b\u00019\u000e,\r\u001e,M\u0017\u000f(\u00014.\u0001\u0018\u0005$--", "pcjmxpsu"), Lit11);
        return runtime.setAndCoerceProperty$Ex(Lit136, Lit160, "2Yf5keLjW1SXPWru55hsZGysg7pn9qUr7arAVALtgNeh7auPxYHrPohTCqobKtx4MrzTwXkuAuZtHtW2QKVSjqaMXa1RbsNAo6neWaHe", Lit11);
    }

    static Object lambda60() {
        runtime.setAndCoerceProperty$Ex(Lit136, Lit185, runtime.textDeobfuscate("§«å¼ªÅ©­¡Ëº³¦Õ­Ä¥£Ñ ~GMoG``-7<Lfd[UBmL.km9iRb[B]gljhgldhvCXYQIp{\u0001l\u000fjS_Szb\u0012o\u0006\\~zeaP^V\u001c\u0014\u001d>\u00125\u0011w\u0010\u001a90\u0001 \u00049;e6\u0013x\u000f\fb!&\u0005#\re\u001c-\u0004\t)<\u0016\u001d\r7R\u000f\u000b\u00019\u000e,\r\u001e,M\u0017\u000f(\u00014.\u0001\u0018\u0005$--", "pcjmxpsu"), Lit11);
        return runtime.setAndCoerceProperty$Ex(Lit136, Lit160, "2Yf5keLjW1SXPWru55hsZGysg7pn9qUr7arAVALtgNeh7auPxYHrPohTCqobKtx4MrzTwXkuAuZtHtW2QKVSjqaMXa1RbsNAo6neWaHe", Lit11);
    }

    public Object ChatBot2$GotResponse(Object $responseText) {
        Object obj;
        Object $responseText2 = runtime.sanitizeComponentData($responseText);
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit126, Lit90, Boolean.FALSE, Lit9);
        SimpleSymbol simpleSymbol = Lit112;
        SimpleSymbol simpleSymbol2 = Lit43;
        if ($responseText2 instanceof Package) {
            obj = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit187), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj = $responseText2;
        }
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, obj, Lit11);
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
        Soil = this;
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
