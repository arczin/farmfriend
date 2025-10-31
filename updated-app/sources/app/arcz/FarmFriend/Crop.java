package appinventor.ai_arnav_chhajed_000.FarmFriend;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import br.ufsc.gqs.teachablemachineimageclassifier.TeachableMachineImageClassifier;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.common.Permission;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.ChatBot;
import com.google.appinventor.components.runtime.CircularProgress;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.ListPicker;
import com.google.appinventor.components.runtime.LocationSensor;
import com.google.appinventor.components.runtime.Notifier;
import com.google.appinventor.components.runtime.Sound;
import com.google.appinventor.components.runtime.TextBox;
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

/* compiled from: Crop.yail */
public class Crop extends Form implements Runnable {
    public static Crop Crop;
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Crop").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("g$bestClass").readResolve());
    static final IntNum Lit100;
    static final IntNum Lit101 = IntNum.make(17);
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("FontTypeface").readResolve());
    static final IntNum Lit103 = IntNum.make(-1090);
    static final FString Lit104 = new FString("com.google.appinventor.components.runtime.Button");
    static final FString Lit105;
    static final SimpleSymbol Lit106 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve());
    static final FString Lit107;
    static final FString Lit108;
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement3").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("p$checkReady").readResolve());
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("AlignVertical").readResolve());
    static final IntNum Lit111 = IntNum.make(2);
    static final FString Lit112;
    static final FString Lit113 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final IntNum Lit114 = IntNum.make(256);
    static final FString Lit115 = new FString("com.google.appinventor.components.runtime.WebViewer");
    static final FString Lit116;
    static final SimpleSymbol Lit117 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement4").readResolve());
    static final FString Lit118;
    static final FString Lit119;
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("TextBoxCropPreference").readResolve());
    static final SimpleSymbol Lit120 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement31").readResolve());
    static final FString Lit121;
    static final FString Lit122 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("CircularProgress3").readResolve());
    static final FString Lit124 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final FString Lit125;
    static final SimpleSymbol Lit126 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement5").readResolve());
    static final FString Lit127;
    static final FString Lit128 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit129 = ((SimpleSymbol) new SimpleSymbol("Button1").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final IntNum Lit130;
    static final SimpleSymbol Lit131 = ((SimpleSymbol) new SimpleSymbol("FontBold").readResolve());
    static final FString Lit132 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit133 = ((SimpleSymbol) new SimpleSymbol("ClassifyVideoData").readResolve());
    static final SimpleSymbol Lit134 = ((SimpleSymbol) new SimpleSymbol("Button1$Click").readResolve());
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("Button1$TouchDown").readResolve());
    static final SimpleSymbol Lit136 = ((SimpleSymbol) new SimpleSymbol("Button1$TouchUp").readResolve());
    static final FString Lit137;
    static final SimpleSymbol Lit138 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement15").readResolve());
    static final FString Lit139;
    static final PairWithPosition Lit14 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65732);
    static final FString Lit140;
    static final SimpleSymbol Lit141 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement29").readResolve());
    static final FString Lit142;
    static final FString Lit143 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final SimpleSymbol Lit144 = ((SimpleSymbol) new SimpleSymbol("CircularProgress1").readResolve());
    static final FString Lit145 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final FString Lit146;
    static final SimpleSymbol Lit147 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement22").readResolve());
    static final IntNum Lit148 = IntNum.make(16777215);
    static final FString Lit149;
    static final PairWithPosition Lit15 = PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65757), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65752);
    static final FString Lit150;
    static final SimpleSymbol Lit151 = ((SimpleSymbol) new SimpleSymbol("ResultLabel").readResolve());
    static final SimpleSymbol Lit152 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit153 = IntNum.make(2130706432);
    static final FString Lit154;
    static final FString Lit155;
    static final SimpleSymbol Lit156 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement7").readResolve());
    static final FString Lit157;
    static final FString Lit158;
    static final SimpleSymbol Lit159 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement27").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("TextBoxPlotarea").readResolve());
    static final FString Lit160;
    static final FString Lit161 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit162 = ((SimpleSymbol) new SimpleSymbol("Button6").readResolve());
    static final IntNum Lit163;
    static final IntNum Lit164 = IntNum.make(18);
    static final IntNum Lit165 = IntNum.make(-1090);
    static final FString Lit166 = new FString("com.google.appinventor.components.runtime.Button");
    static final FString Lit167;
    static final SimpleSymbol Lit168 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement8").readResolve());
    static final FString Lit169;
    static final PairWithPosition Lit17 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65917);
    static final FString Lit170;
    static final SimpleSymbol Lit171 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement26").readResolve());
    static final FString Lit172;
    static final FString Lit173 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit174 = ((SimpleSymbol) new SimpleSymbol("Button3").readResolve());
    static final IntNum Lit175;
    static final SimpleSymbol Lit176 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final IntNum Lit177 = IntNum.make(0);
    static final IntNum Lit178 = IntNum.make(-1090);
    static final FString Lit179 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit18 = PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65942), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65937);
    static final FString Lit180 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit181;
    static final SimpleSymbol Lit182 = ((SimpleSymbol) new SimpleSymbol("Hint").readResolve());
    static final SimpleSymbol Lit183 = ((SimpleSymbol) new SimpleSymbol("HintColor").readResolve());
    static final IntNum Lit184;
    static final IntNum Lit185;
    static final IntNum Lit186 = IntNum.make(-1090);
    static final FString Lit187 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final SimpleSymbol Lit188 = ((SimpleSymbol) new SimpleSymbol("TextBoxLocation$LostFocus").readResolve());
    static final SimpleSymbol Lit189 = ((SimpleSymbol) new SimpleSymbol("LostFocus").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("TextBoxLocation").readResolve());
    static final FString Lit190;
    static final SimpleSymbol Lit191 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement9").readResolve());
    static final FString Lit192;
    static final FString Lit193;
    static final SimpleSymbol Lit194 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement10").readResolve());
    static final FString Lit195;
    static final FString Lit196;
    static final SimpleSymbol Lit197 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement6").readResolve());
    static final FString Lit198;
    static final FString Lit199 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final PairWithPosition Lit20 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 66102);
    static final SimpleSymbol Lit200 = ((SimpleSymbol) new SimpleSymbol("GetLocation").readResolve());
    static final IntNum Lit201;
    static final IntNum Lit202 = IntNum.make(-1090);
    static final FString Lit203 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit204 = ((SimpleSymbol) new SimpleSymbol("LocationSensor1").readResolve());
    static final SimpleSymbol Lit205 = ((SimpleSymbol) new SimpleSymbol("Enabled").readResolve());
    static final SimpleSymbol Lit206 = ((SimpleSymbol) new SimpleSymbol("GetLocation$Click").readResolve());
    static final SimpleSymbol Lit207 = ((SimpleSymbol) new SimpleSymbol("GetLocation$TouchDown").readResolve());
    static final SimpleSymbol Lit208 = ((SimpleSymbol) new SimpleSymbol("GetLocation$TouchUp").readResolve());
    static final FString Lit209;
    static final PairWithPosition Lit21 = PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 66127), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 66122);
    static final SimpleSymbol Lit210 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement11").readResolve());
    static final IntNum Lit211 = IntNum.make(30);
    static final FString Lit212;
    static final FString Lit213;
    static final SimpleSymbol Lit214 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement20").readResolve());
    static final IntNum Lit215 = IntNum.make(16777215);
    static final FString Lit216;
    static final FString Lit217 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit218 = ((SimpleSymbol) new SimpleSymbol("Button4").readResolve());
    static final IntNum Lit219;
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("Label5").readResolve());
    static final IntNum Lit220 = IntNum.make(-1090);
    static final FString Lit221 = new FString("com.google.appinventor.components.runtime.Button");
    static final FString Lit222 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit223;
    static final IntNum Lit224;
    static final IntNum Lit225;
    static final IntNum Lit226 = IntNum.make(-1090);
    static final FString Lit227 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final SimpleSymbol Lit228 = ((SimpleSymbol) new SimpleSymbol("TextBoxPlotarea$LostFocus").readResolve());
    static final FString Lit229;
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("Visible").readResolve());
    static final SimpleSymbol Lit230 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement12").readResolve());
    static final FString Lit231;
    static final FString Lit232;
    static final SimpleSymbol Lit233 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement19").readResolve());
    static final FString Lit234;
    static final FString Lit235 = new FString("com.google.appinventor.components.runtime.ListPicker");
    static final IntNum Lit236;
    static final IntNum Lit237 = IntNum.make(-1090);
    static final FString Lit238 = new FString("com.google.appinventor.components.runtime.ListPicker");
    static final SimpleSymbol Lit239 = ((SimpleSymbol) new SimpleSymbol("Selection").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve());
    static final PairWithPosition Lit240 = PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 1900753), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 1900748), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 1900742);
    static final SimpleSymbol Lit241 = ((SimpleSymbol) new SimpleSymbol("ListPicker1$AfterPicking").readResolve());
    static final SimpleSymbol Lit242 = ((SimpleSymbol) new SimpleSymbol("AfterPicking").readResolve());
    static final SimpleSymbol Lit243 = ((SimpleSymbol) new SimpleSymbol("ListPicker1$TouchDown").readResolve());
    static final FString Lit244;
    static final SimpleSymbol Lit245 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement13").readResolve());
    static final FString Lit246;
    static final FString Lit247;
    static final SimpleSymbol Lit248 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement14").readResolve());
    static final IntNum Lit249 = IntNum.make(16777215);
    static final PairWithPosition Lit25 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65732);
    static final FString Lit250;
    static final FString Lit251 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit252 = ((SimpleSymbol) new SimpleSymbol("Button5").readResolve());
    static final IntNum Lit253;
    static final IntNum Lit254 = IntNum.make(-1090);
    static final FString Lit255 = new FString("com.google.appinventor.components.runtime.Button");
    static final FString Lit256 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final IntNum Lit257;
    static final IntNum Lit258;
    static final IntNum Lit259;
    static final PairWithPosition Lit26 = PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65757), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65752);
    static final IntNum Lit260 = IntNum.make(-1090);
    static final FString Lit261 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final SimpleSymbol Lit262 = ((SimpleSymbol) new SimpleSymbol("TextBoxCropPreference$LostFocus").readResolve());
    static final FString Lit263;
    static final SimpleSymbol Lit264 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement16").readResolve());
    static final FString Lit265;
    static final FString Lit266;
    static final SimpleSymbol Lit267 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement23").readResolve());
    static final IntNum Lit268 = IntNum.make(1979711487);
    static final FString Lit269;
    static final PairWithPosition Lit27 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65917);
    static final FString Lit270;
    static final FString Lit271;
    static final FString Lit272;
    static final SimpleSymbol Lit273 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement18").readResolve());
    static final FString Lit274;
    static final FString Lit275;
    static final SimpleSymbol Lit276 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement30").readResolve());
    static final FString Lit277;
    static final FString Lit278 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final SimpleSymbol Lit279 = ((SimpleSymbol) new SimpleSymbol("CircularProgress2").readResolve());
    static final PairWithPosition Lit28 = PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65942), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 65937);
    static final FString Lit280 = new FString("com.google.appinventor.components.runtime.CircularProgress");
    static final FString Lit281;
    static final SimpleSymbol Lit282 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement17").readResolve());
    static final FString Lit283;
    static final FString Lit284 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit285 = ((SimpleSymbol) new SimpleSymbol("Button2").readResolve());
    static final IntNum Lit286;
    static final FString Lit287 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit288 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2466098);
    static final PairWithPosition Lit289 = PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2466123), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2466118);
    static final PairWithPosition Lit29 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 66102);
    static final PairWithPosition Lit290 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2466283);
    static final PairWithPosition Lit291 = PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2466308), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2466303);
    static final PairWithPosition Lit292 = PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2466464), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2466459);
    static final PairWithPosition Lit293 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2466624);
    static final PairWithPosition Lit294 = PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2466649), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2466644);
    static final SimpleSymbol Lit295 = ((SimpleSymbol) new SimpleSymbol("ChatBot1").readResolve());
    static final SimpleSymbol Lit296 = ((SimpleSymbol) new SimpleSymbol("Converse").readResolve());
    static final PairWithPosition Lit297;
    static final PairWithPosition Lit298 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467957);
    static final SimpleSymbol Lit299 = ((SimpleSymbol) new SimpleSymbol("Label6").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$Longitude").readResolve());
    static final PairWithPosition Lit30 = PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 66127), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 66122);
    static final SimpleSymbol Lit300 = ((SimpleSymbol) new SimpleSymbol("Notifier1").readResolve());
    static final SimpleSymbol Lit301 = ((SimpleSymbol) new SimpleSymbol("ShowMessageDialog").readResolve());
    static final PairWithPosition Lit302 = PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2468302), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2468297), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2468291);
    static final SimpleSymbol Lit303 = ((SimpleSymbol) new SimpleSymbol("Button2$Click").readResolve());
    static final SimpleSymbol Lit304 = ((SimpleSymbol) new SimpleSymbol("Button2$TouchDown").readResolve());
    static final SimpleSymbol Lit305 = ((SimpleSymbol) new SimpleSymbol("Button2$TouchUp").readResolve());
    static final FString Lit306 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit307 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement28").readResolve());
    static final FString Lit308 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit309 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final SimpleSymbol Lit310 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement32").readResolve());
    static final IntNum Lit311 = IntNum.make(1979711487);
    static final FString Lit312 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit313 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit314 = IntNum.make(16);
    static final IntNum Lit315 = IntNum.make(-1090);
    static final FString Lit316 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit317 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit318 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement33").readResolve());
    static final FString Lit319 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final IntNum Lit32;
    static final FString Lit320 = new FString("br.ufsc.gqs.teachablemachineimageclassifier.TeachableMachineImageClassifier");
    static final SimpleSymbol Lit321 = ((SimpleSymbol) new SimpleSymbol("UrlModel").readResolve());
    static final SimpleSymbol Lit322 = ((SimpleSymbol) new SimpleSymbol("WebViewer").readResolve());
    static final SimpleSymbol Lit323 = ((SimpleSymbol) new SimpleSymbol("component").readResolve());
    static final FString Lit324 = new FString("br.ufsc.gqs.teachablemachineimageclassifier.TeachableMachineImageClassifier");
    static final SimpleSymbol Lit325 = ((SimpleSymbol) new SimpleSymbol("$item").readResolve());
    static final PairWithPosition Lit326 = PairWithPosition.make(Lit52, PairWithPosition.make(Lit33, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691252), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691246);
    static final PairWithPosition Lit327;
    static final SimpleSymbol Lit328 = ((SimpleSymbol) new SimpleSymbol("$value").readResolve());
    static final PairWithPosition Lit329 = PairWithPosition.make(Lit33, PairWithPosition.make(Lit33, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691518), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691510);
    static final SimpleSymbol Lit33;
    static final SimpleSymbol Lit330 = ((SimpleSymbol) new SimpleSymbol("$key").readResolve());
    static final SimpleSymbol Lit331 = ((SimpleSymbol) new SimpleSymbol("$result").readResolve());
    static final PairWithPosition Lit332;
    static final PairWithPosition Lit333 = PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691950), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691945), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691940), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691935), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691930), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691924);
    static final IntNum Lit334 = IntNum.make(-16777216);
    static final SimpleSymbol Lit335 = ((SimpleSymbol) new SimpleSymbol("TeachableMachineImageClassifier1$GotClassification").readResolve());
    static final SimpleSymbol Lit336 = ((SimpleSymbol) new SimpleSymbol("GotClassification").readResolve());
    static final FString Lit337 = new FString("com.google.appinventor.components.runtime.ChatBot");
    static final SimpleSymbol Lit338 = ((SimpleSymbol) new SimpleSymbol("ApiKey").readResolve());
    static final SimpleSymbol Lit339 = ((SimpleSymbol) new SimpleSymbol("Token").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("ActionBar").readResolve());
    static final FString Lit340 = new FString("com.google.appinventor.components.runtime.ChatBot");
    static final SimpleSymbol Lit341 = ((SimpleSymbol) new SimpleSymbol(TinyDB.DEFAULT_NAMESPACE).readResolve());
    static final SimpleSymbol Lit342 = ((SimpleSymbol) new SimpleSymbol("StoreValue").readResolve());
    static final SimpleSymbol Lit343 = ((SimpleSymbol) new SimpleSymbol("$responseText").readResolve());
    static final PairWithPosition Lit344 = PairWithPosition.make(Lit36, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2736317), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2736311);
    static final PairWithPosition Lit345;
    static final SimpleSymbol Lit346 = ((SimpleSymbol) new SimpleSymbol("ChatBot1$GotResponse").readResolve());
    static final SimpleSymbol Lit347 = ((SimpleSymbol) new SimpleSymbol("GotResponse").readResolve());
    static final FString Lit348 = new FString("com.google.appinventor.components.runtime.LocationSensor");
    static final FString Lit349 = new FString("com.google.appinventor.components.runtime.LocationSensor");
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final SimpleSymbol Lit350 = ((SimpleSymbol) new SimpleSymbol("$latitude").readResolve());
    static final SimpleSymbol Lit351 = ((SimpleSymbol) new SimpleSymbol("$longitude").readResolve());
    static final SimpleSymbol Lit352 = ((SimpleSymbol) new SimpleSymbol("LocationSensor1$LocationChanged").readResolve());
    static final SimpleSymbol Lit353 = ((SimpleSymbol) new SimpleSymbol("LocationChanged").readResolve());
    static final FString Lit354 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final FString Lit355 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final FString Lit356 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit357 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit358 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final IntNum Lit359;
    static final SimpleSymbol Lit36;
    static final SimpleSymbol Lit360 = ((SimpleSymbol) new SimpleSymbol("NotifierLength").readResolve());
    static final FString Lit361 = new FString("com.google.appinventor.components.runtime.Notifier");
    static final SimpleSymbol Lit362 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit363 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit364 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit365 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit366 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit367 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit368 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit369 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("BackgroundImage").readResolve());
    static final SimpleSymbol Lit370 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit371 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit372 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit373 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit374 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit375 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit376 = ((SimpleSymbol) new SimpleSymbol("proc").readResolve());
    static final SimpleSymbol Lit377 = ((SimpleSymbol) new SimpleSymbol("any").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("PrimaryColor").readResolve());
    static final IntNum Lit39;
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("g$Latitude").readResolve());
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("PrimaryColorDark").readResolve());
    static final IntNum Lit41;
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("ScreenOrientation").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("Scrollable").readResolve());
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("AskForPermission").readResolve());
    static final PairWithPosition Lit48 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 135324);
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("ListPicker1").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("g$location").readResolve());
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("Elements").readResolve());
    static final PairWithPosition Lit51 = PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, PairWithPosition.make(Lit377, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 135486), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 135482), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 135478), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 135473);
    static final SimpleSymbol Lit52;
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("Sound1").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("Source").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("WebViewer1").readResolve());
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("GoHome").readResolve());
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("Reload").readResolve());
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("Crop$Initialize").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("g$cropPreference").readResolve());
    static final FString Lit60 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve());
    static final SimpleSymbol Lit62 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit63 = IntNum.make(-2);
    static final FString Lit64 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit65 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit66 = ((SimpleSymbol) new SimpleSymbol("backbutton").readResolve());
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final IntNum Lit68 = IntNum.make(1174405120);
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("g$plotArea").readResolve());
    static final IntNum Lit70 = IntNum.make(15);
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("Shape").readResolve());
    static final IntNum Lit72 = IntNum.make(1);
    static final FString Lit73 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("Play").readResolve());
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("TeachableMachineImageClassifier1").readResolve());
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("StopWebcam").readResolve());
    static final PairWithPosition Lit77 = PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 225514);
    static final SimpleSymbol Lit78 = ((SimpleSymbol) new SimpleSymbol("backbutton$Click").readResolve());
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("g$budget").readResolve());
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("backbutton$TouchDown").readResolve());
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("TouchDown").readResolve());
    static final SimpleSymbol Lit82 = ((SimpleSymbol) new SimpleSymbol("backbutton$TouchUp").readResolve());
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol("TouchUp").readResolve());
    static final FString Lit84;
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement2").readResolve());
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final IntNum Lit87 = IntNum.make(10);
    static final FString Lit88;
    static final FString Lit89;
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("g$maxConfidence").readResolve());
    static final SimpleSymbol Lit90 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement25").readResolve());
    static final FString Lit91;
    static final FString Lit92;
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement24").readResolve());
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final IntNum Lit95 = IntNum.make(3);
    static final IntNum Lit96 = IntNum.make(16777215);
    static final FString Lit97;
    static final FString Lit98 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("Button7").readResolve());
    static final ModuleMethod lambda$Fn1 = null;
    static final ModuleMethod lambda$Fn10 = null;
    static final ModuleMethod lambda$Fn100 = null;
    static final ModuleMethod lambda$Fn101 = null;
    static final ModuleMethod lambda$Fn102 = null;
    static final ModuleMethod lambda$Fn103 = null;
    static final ModuleMethod lambda$Fn104 = null;
    static final ModuleMethod lambda$Fn105 = null;
    static final ModuleMethod lambda$Fn106 = null;
    static final ModuleMethod lambda$Fn107 = null;
    static final ModuleMethod lambda$Fn108 = null;
    static final ModuleMethod lambda$Fn109 = null;
    static final ModuleMethod lambda$Fn11 = null;
    static final ModuleMethod lambda$Fn110 = null;
    static final ModuleMethod lambda$Fn111 = null;
    static final ModuleMethod lambda$Fn112 = null;
    static final ModuleMethod lambda$Fn113 = null;
    static final ModuleMethod lambda$Fn114 = null;
    static final ModuleMethod lambda$Fn115 = null;
    static final ModuleMethod lambda$Fn116 = null;
    static final ModuleMethod lambda$Fn117 = null;
    static final ModuleMethod lambda$Fn118 = null;
    static final ModuleMethod lambda$Fn119 = null;
    static final ModuleMethod lambda$Fn12 = null;
    static final ModuleMethod lambda$Fn120 = null;
    static final ModuleMethod lambda$Fn121 = null;
    static final ModuleMethod lambda$Fn122 = null;
    static final ModuleMethod lambda$Fn123 = null;
    static final ModuleMethod lambda$Fn124 = null;
    static final ModuleMethod lambda$Fn125 = null;
    static final ModuleMethod lambda$Fn126 = null;
    static final ModuleMethod lambda$Fn127 = null;
    static final ModuleMethod lambda$Fn128 = null;
    static final ModuleMethod lambda$Fn13 = null;
    static final ModuleMethod lambda$Fn130 = null;
    static final ModuleMethod lambda$Fn131 = null;
    static final ModuleMethod lambda$Fn132 = null;
    static final ModuleMethod lambda$Fn133 = null;
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
    static final ModuleMethod lambda$Fn64 = null;
    static final ModuleMethod lambda$Fn65 = null;
    static final ModuleMethod lambda$Fn66 = null;
    static final ModuleMethod lambda$Fn67 = null;
    static final ModuleMethod lambda$Fn68 = null;
    static final ModuleMethod lambda$Fn69 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn70 = null;
    static final ModuleMethod lambda$Fn71 = null;
    static final ModuleMethod lambda$Fn72 = null;
    static final ModuleMethod lambda$Fn73 = null;
    static final ModuleMethod lambda$Fn74 = null;
    static final ModuleMethod lambda$Fn75 = null;
    static final ModuleMethod lambda$Fn76 = null;
    static final ModuleMethod lambda$Fn77 = null;
    static final ModuleMethod lambda$Fn78 = null;
    static final ModuleMethod lambda$Fn79 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn80 = null;
    static final ModuleMethod lambda$Fn81 = null;
    static final ModuleMethod lambda$Fn82 = null;
    static final ModuleMethod lambda$Fn83 = null;
    static final ModuleMethod lambda$Fn84 = null;
    static final ModuleMethod lambda$Fn85 = null;
    static final ModuleMethod lambda$Fn86 = null;
    static final ModuleMethod lambda$Fn87 = null;
    static final ModuleMethod lambda$Fn88 = null;
    static final ModuleMethod lambda$Fn89 = null;
    static final ModuleMethod lambda$Fn9 = null;
    static final ModuleMethod lambda$Fn90 = null;
    static final ModuleMethod lambda$Fn91 = null;
    static final ModuleMethod lambda$Fn92 = null;
    static final ModuleMethod lambda$Fn93 = null;
    static final ModuleMethod lambda$Fn94 = null;
    static final ModuleMethod lambda$Fn95 = null;
    static final ModuleMethod lambda$Fn96 = null;
    static final ModuleMethod lambda$Fn97 = null;
    static final ModuleMethod lambda$Fn98 = null;
    static final ModuleMethod lambda$Fn99 = null;
    static final ModuleMethod proc$Fn129 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public Button Button1;
    public final ModuleMethod Button1$Click;
    public final ModuleMethod Button1$TouchDown;
    public final ModuleMethod Button1$TouchUp;
    public Button Button2;
    public final ModuleMethod Button2$Click;
    public final ModuleMethod Button2$TouchDown;
    public final ModuleMethod Button2$TouchUp;
    public Button Button3;
    public Button Button4;
    public Button Button5;
    public Button Button6;
    public Button Button7;
    public ChatBot ChatBot1;
    public final ModuleMethod ChatBot1$GotResponse;
    public CircularProgress CircularProgress1;
    public CircularProgress CircularProgress2;
    public CircularProgress CircularProgress3;
    public final ModuleMethod Crop$Initialize;
    public Button GetLocation;
    public final ModuleMethod GetLocation$Click;
    public final ModuleMethod GetLocation$TouchDown;
    public final ModuleMethod GetLocation$TouchUp;
    public HorizontalArrangement HorizontalArrangement1;
    public Label Label5;
    public Label Label6;
    public ListPicker ListPicker1;
    public final ModuleMethod ListPicker1$AfterPicking;
    public final ModuleMethod ListPicker1$TouchDown;
    public LocationSensor LocationSensor1;
    public final ModuleMethod LocationSensor1$LocationChanged;
    public Notifier Notifier1;
    public Label ResultLabel;
    public Sound Sound1;
    public TeachableMachineImageClassifier TeachableMachineImageClassifier1;
    public final ModuleMethod TeachableMachineImageClassifier1$GotClassification;
    public TextBox TextBoxCropPreference;
    public final ModuleMethod TextBoxCropPreference$LostFocus;
    public TextBox TextBoxLocation;
    public final ModuleMethod TextBoxLocation$LostFocus;
    public TextBox TextBoxPlotarea;
    public final ModuleMethod TextBoxPlotarea$LostFocus;
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
    public VerticalArrangement VerticalArrangement18;
    public VerticalArrangement VerticalArrangement19;
    public VerticalArrangement VerticalArrangement2;
    public VerticalArrangement VerticalArrangement20;
    public VerticalArrangement VerticalArrangement22;
    public VerticalArrangement VerticalArrangement23;
    public VerticalArrangement VerticalArrangement24;
    public VerticalArrangement VerticalArrangement25;
    public VerticalArrangement VerticalArrangement26;
    public VerticalArrangement VerticalArrangement27;
    public VerticalArrangement VerticalArrangement28;
    public VerticalArrangement VerticalArrangement29;
    public VerticalArrangement VerticalArrangement3;
    public VerticalArrangement VerticalArrangement30;
    public VerticalArrangement VerticalArrangement31;
    public VerticalArrangement VerticalArrangement32;
    public VerticalArrangement VerticalArrangement33;
    public VerticalArrangement VerticalArrangement4;
    public VerticalArrangement VerticalArrangement5;
    public VerticalArrangement VerticalArrangement6;
    public VerticalArrangement VerticalArrangement7;
    public VerticalArrangement VerticalArrangement8;
    public VerticalArrangement VerticalArrangement9;
    public WebViewer WebViewer1;
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
        int[] iArr = new int[2];
        iArr[0] = -16777216;
        Lit359 = IntNum.make(iArr);
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit36 = simpleSymbol;
        Lit345 = PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2736395);
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit33 = simpleSymbol2;
        Lit332 = PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691895);
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("list").readResolve();
        Lit52 = simpleSymbol3;
        Lit327 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(Lit33, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691381), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2691375);
        SimpleSymbol simpleSymbol4 = Lit36;
        SimpleSymbol simpleSymbol5 = Lit36;
        Lit297 = PairWithPosition.make(simpleSymbol4, PairWithPosition.make(simpleSymbol5, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, PairWithPosition.make(Lit36, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467940), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467935), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467930), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467925), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467920), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467915), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467910), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467905), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467900), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467895), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467890), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467885), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467880), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467875), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467870), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Crop.yail", 2467864);
        int[] iArr2 = new int[2];
        iArr2[0] = -1;
        Lit286 = IntNum.make(iArr2);
        String str = "com.google.appinventor.components.runtime.VerticalArrangement";
        Lit283 = new FString(str);
        Lit281 = new FString(str);
        Lit277 = new FString(str);
        Lit275 = new FString(str);
        Lit274 = new FString(str);
        Lit272 = new FString(str);
        String str2 = "com.google.appinventor.components.runtime.Label";
        Lit271 = new FString(str2);
        Lit270 = new FString(str2);
        Lit269 = new FString(str);
        Lit266 = new FString(str);
        Lit265 = new FString(str);
        Lit263 = new FString(str);
        int[] iArr3 = new int[2];
        iArr3[0] = -16777216;
        Lit259 = IntNum.make(iArr3);
        int[] iArr4 = new int[2];
        iArr4[0] = -9803158;
        Lit258 = IntNum.make(iArr4);
        int[] iArr5 = new int[2];
        iArr5[0] = -1;
        Lit257 = IntNum.make(iArr5);
        int[] iArr6 = new int[2];
        iArr6[0] = -845;
        Lit253 = IntNum.make(iArr6);
        Lit250 = new FString(str);
        Lit247 = new FString(str);
        Lit246 = new FString(str);
        Lit244 = new FString(str);
        int[] iArr7 = new int[2];
        iArr7[0] = -1;
        Lit236 = IntNum.make(iArr7);
        Lit234 = new FString(str);
        Lit232 = new FString(str);
        Lit231 = new FString(str);
        Lit229 = new FString(str);
        int[] iArr8 = new int[2];
        iArr8[0] = -16777216;
        Lit225 = IntNum.make(iArr8);
        int[] iArr9 = new int[2];
        iArr9[0] = -9803158;
        Lit224 = IntNum.make(iArr9);
        int[] iArr10 = new int[2];
        iArr10[0] = -1;
        Lit223 = IntNum.make(iArr10);
        int[] iArr11 = new int[2];
        iArr11[0] = -845;
        Lit219 = IntNum.make(iArr11);
        Lit216 = new FString(str);
        Lit213 = new FString(str);
        Lit212 = new FString(str);
        Lit209 = new FString(str);
        int[] iArr12 = new int[2];
        iArr12[0] = -1;
        Lit201 = IntNum.make(iArr12);
        Lit198 = new FString(str);
        Lit196 = new FString(str);
        Lit195 = new FString(str);
        Lit193 = new FString(str);
        Lit192 = new FString(str);
        Lit190 = new FString(str);
        int[] iArr13 = new int[2];
        iArr13[0] = -16777216;
        Lit185 = IntNum.make(iArr13);
        int[] iArr14 = new int[2];
        iArr14[0] = -9803158;
        Lit184 = IntNum.make(iArr14);
        int[] iArr15 = new int[2];
        iArr15[0] = -1;
        Lit181 = IntNum.make(iArr15);
        int[] iArr16 = new int[2];
        iArr16[0] = -845;
        Lit175 = IntNum.make(iArr16);
        Lit172 = new FString(str);
        Lit170 = new FString(str);
        Lit169 = new FString(str);
        Lit167 = new FString(str);
        int[] iArr17 = new int[2];
        iArr17[0] = -845;
        Lit163 = IntNum.make(iArr17);
        Lit160 = new FString(str);
        Lit158 = new FString(str);
        Lit157 = new FString(str);
        Lit155 = new FString(str);
        Lit154 = new FString(str2);
        Lit150 = new FString(str2);
        Lit149 = new FString(str);
        Lit146 = new FString(str);
        Lit142 = new FString(str);
        Lit140 = new FString(str);
        Lit139 = new FString(str);
        Lit137 = new FString(str);
        int[] iArr18 = new int[2];
        iArr18[0] = -1;
        Lit130 = IntNum.make(iArr18);
        Lit127 = new FString(str);
        Lit125 = new FString(str);
        Lit121 = new FString(str);
        Lit119 = new FString(str);
        Lit118 = new FString(str);
        Lit116 = new FString(str);
        Lit112 = new FString(str);
        Lit108 = new FString(str);
        Lit107 = new FString(str);
        Lit105 = new FString(str);
        int[] iArr19 = new int[2];
        iArr19[0] = -845;
        Lit100 = IntNum.make(iArr19);
        Lit97 = new FString(str);
        Lit92 = new FString(str);
        Lit91 = new FString(str);
        Lit89 = new FString(str);
        Lit88 = new FString(str);
        Lit84 = new FString(str);
        int[] iArr20 = new int[2];
        iArr20[0] = -9592721;
        Lit41 = IntNum.make(iArr20);
        int[] iArr21 = new int[2];
        iArr21[0] = -8271996;
        Lit39 = IntNum.make(iArr21);
        int[] iArr22 = new int[2];
        iArr22[0] = -18654;
        Lit32 = IntNum.make(iArr22);
    }

    public Crop() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        frame2.$main = this;
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit362, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit363, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit364, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit365, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit366, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit367, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit368, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit369, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit370, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit371, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit372, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit373, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit374, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit375, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, (Object) null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime15798620743098228844.scm:639");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, (Object) null, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 21, (Object) null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 22, (Object) null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 23, (Object) null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 24, (Object) null, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 25, (Object) null, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 26, (Object) null, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 27, (Object) null, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 28, (Object) null, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 29, (Object) null, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 30, (Object) null, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 31, (Object) null, 0);
        lambda$Fn16 = new ModuleMethod(frame2, 32, (Object) null, 0);
        lambda$Fn17 = new ModuleMethod(frame2, 33, (Object) null, 0);
        lambda$Fn18 = new ModuleMethod(frame2, 34, (Object) null, 0);
        lambda$Fn15 = new ModuleMethod(frame2, 35, (Object) null, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 36, (Object) null, 0);
        lambda$Fn19 = new ModuleMethod(frame2, 37, (Object) null, 0);
        this.Crop$Initialize = new ModuleMethod(frame2, 38, Lit58, 0);
        lambda$Fn20 = new ModuleMethod(frame2, 39, (Object) null, 0);
        lambda$Fn21 = new ModuleMethod(frame2, 40, (Object) null, 0);
        lambda$Fn22 = new ModuleMethod(frame2, 41, (Object) null, 0);
        lambda$Fn23 = new ModuleMethod(frame2, 42, (Object) null, 0);
        this.backbutton$Click = new ModuleMethod(frame2, 43, Lit78, 0);
        this.backbutton$TouchDown = new ModuleMethod(frame2, 44, Lit80, 0);
        this.backbutton$TouchUp = new ModuleMethod(frame2, 45, Lit82, 0);
        lambda$Fn24 = new ModuleMethod(frame2, 46, (Object) null, 0);
        lambda$Fn25 = new ModuleMethod(frame2, 47, (Object) null, 0);
        lambda$Fn26 = new ModuleMethod(frame2, 48, (Object) null, 0);
        lambda$Fn27 = new ModuleMethod(frame2, 49, (Object) null, 0);
        lambda$Fn28 = new ModuleMethod(frame2, 50, (Object) null, 0);
        lambda$Fn29 = new ModuleMethod(frame2, 51, (Object) null, 0);
        lambda$Fn30 = new ModuleMethod(frame2, 52, (Object) null, 0);
        lambda$Fn31 = new ModuleMethod(frame2, 53, (Object) null, 0);
        lambda$Fn32 = new ModuleMethod(frame2, 54, (Object) null, 0);
        lambda$Fn33 = new ModuleMethod(frame2, 55, (Object) null, 0);
        lambda$Fn34 = new ModuleMethod(frame2, 56, (Object) null, 0);
        lambda$Fn35 = new ModuleMethod(frame2, 57, (Object) null, 0);
        lambda$Fn36 = new ModuleMethod(frame2, 58, (Object) null, 0);
        lambda$Fn37 = new ModuleMethod(frame2, 59, (Object) null, 0);
        lambda$Fn38 = new ModuleMethod(frame2, 60, (Object) null, 0);
        lambda$Fn39 = new ModuleMethod(frame2, 61, (Object) null, 0);
        lambda$Fn40 = new ModuleMethod(frame2, 62, (Object) null, 0);
        lambda$Fn41 = new ModuleMethod(frame2, 63, (Object) null, 0);
        lambda$Fn42 = new ModuleMethod(frame2, 64, (Object) null, 0);
        lambda$Fn43 = new ModuleMethod(frame2, 65, (Object) null, 0);
        lambda$Fn44 = new ModuleMethod(frame2, 66, (Object) null, 0);
        lambda$Fn45 = new ModuleMethod(frame2, 67, (Object) null, 0);
        lambda$Fn46 = new ModuleMethod(frame2, 68, (Object) null, 0);
        lambda$Fn47 = new ModuleMethod(frame2, 69, (Object) null, 0);
        this.Button1$Click = new ModuleMethod(frame2, 70, Lit134, 0);
        this.Button1$TouchDown = new ModuleMethod(frame2, 71, Lit135, 0);
        this.Button1$TouchUp = new ModuleMethod(frame2, 72, Lit136, 0);
        lambda$Fn48 = new ModuleMethod(frame2, 73, (Object) null, 0);
        lambda$Fn49 = new ModuleMethod(frame2, 74, (Object) null, 0);
        lambda$Fn50 = new ModuleMethod(frame2, 75, (Object) null, 0);
        lambda$Fn51 = new ModuleMethod(frame2, 76, (Object) null, 0);
        lambda$Fn52 = new ModuleMethod(frame2, 77, (Object) null, 0);
        lambda$Fn53 = new ModuleMethod(frame2, 78, (Object) null, 0);
        lambda$Fn54 = new ModuleMethod(frame2, 79, (Object) null, 0);
        lambda$Fn55 = new ModuleMethod(frame2, 80, (Object) null, 0);
        lambda$Fn56 = new ModuleMethod(frame2, 81, (Object) null, 0);
        lambda$Fn57 = new ModuleMethod(frame2, 82, (Object) null, 0);
        lambda$Fn58 = new ModuleMethod(frame2, 83, (Object) null, 0);
        lambda$Fn59 = new ModuleMethod(frame2, 84, (Object) null, 0);
        lambda$Fn60 = new ModuleMethod(frame2, 85, (Object) null, 0);
        lambda$Fn61 = new ModuleMethod(frame2, 86, (Object) null, 0);
        lambda$Fn62 = new ModuleMethod(frame2, 87, (Object) null, 0);
        lambda$Fn63 = new ModuleMethod(frame2, 88, (Object) null, 0);
        lambda$Fn64 = new ModuleMethod(frame2, 89, (Object) null, 0);
        lambda$Fn65 = new ModuleMethod(frame2, 90, (Object) null, 0);
        lambda$Fn66 = new ModuleMethod(frame2, 91, (Object) null, 0);
        lambda$Fn67 = new ModuleMethod(frame2, 92, (Object) null, 0);
        lambda$Fn68 = new ModuleMethod(frame2, 93, (Object) null, 0);
        lambda$Fn69 = new ModuleMethod(frame2, 94, (Object) null, 0);
        this.TextBoxLocation$LostFocus = new ModuleMethod(frame2, 95, Lit188, 0);
        lambda$Fn70 = new ModuleMethod(frame2, 96, (Object) null, 0);
        lambda$Fn71 = new ModuleMethod(frame2, 97, (Object) null, 0);
        lambda$Fn72 = new ModuleMethod(frame2, 98, (Object) null, 0);
        lambda$Fn73 = new ModuleMethod(frame2, 99, (Object) null, 0);
        lambda$Fn74 = new ModuleMethod(frame2, 100, (Object) null, 0);
        lambda$Fn75 = new ModuleMethod(frame2, 101, (Object) null, 0);
        lambda$Fn76 = new ModuleMethod(frame2, 102, (Object) null, 0);
        lambda$Fn77 = new ModuleMethod(frame2, 103, (Object) null, 0);
        this.GetLocation$Click = new ModuleMethod(frame2, 104, Lit206, 0);
        this.GetLocation$TouchDown = new ModuleMethod(frame2, 105, Lit207, 0);
        this.GetLocation$TouchUp = new ModuleMethod(frame2, 106, Lit208, 0);
        lambda$Fn78 = new ModuleMethod(frame2, 107, (Object) null, 0);
        lambda$Fn79 = new ModuleMethod(frame2, 108, (Object) null, 0);
        lambda$Fn80 = new ModuleMethod(frame2, 109, (Object) null, 0);
        lambda$Fn81 = new ModuleMethod(frame2, 110, (Object) null, 0);
        lambda$Fn82 = new ModuleMethod(frame2, 111, (Object) null, 0);
        lambda$Fn83 = new ModuleMethod(frame2, 112, (Object) null, 0);
        lambda$Fn84 = new ModuleMethod(frame2, 113, (Object) null, 0);
        lambda$Fn85 = new ModuleMethod(frame2, 114, (Object) null, 0);
        this.TextBoxPlotarea$LostFocus = new ModuleMethod(frame2, 115, Lit228, 0);
        lambda$Fn86 = new ModuleMethod(frame2, 116, (Object) null, 0);
        lambda$Fn87 = new ModuleMethod(frame2, 117, (Object) null, 0);
        lambda$Fn88 = new ModuleMethod(frame2, 118, (Object) null, 0);
        lambda$Fn89 = new ModuleMethod(frame2, 119, (Object) null, 0);
        lambda$Fn90 = new ModuleMethod(frame2, 120, (Object) null, 0);
        lambda$Fn91 = new ModuleMethod(frame2, 121, (Object) null, 0);
        this.ListPicker1$AfterPicking = new ModuleMethod(frame2, 122, Lit241, 0);
        this.ListPicker1$TouchDown = new ModuleMethod(frame2, 123, Lit243, 0);
        lambda$Fn92 = new ModuleMethod(frame2, 124, (Object) null, 0);
        lambda$Fn93 = new ModuleMethod(frame2, 125, (Object) null, 0);
        lambda$Fn94 = new ModuleMethod(frame2, 126, (Object) null, 0);
        lambda$Fn95 = new ModuleMethod(frame2, 127, (Object) null, 0);
        lambda$Fn96 = new ModuleMethod(frame2, 128, (Object) null, 0);
        lambda$Fn97 = new ModuleMethod(frame2, 129, (Object) null, 0);
        lambda$Fn98 = new ModuleMethod(frame2, 130, (Object) null, 0);
        lambda$Fn99 = new ModuleMethod(frame2, 131, (Object) null, 0);
        this.TextBoxCropPreference$LostFocus = new ModuleMethod(frame2, 132, Lit262, 0);
        lambda$Fn100 = new ModuleMethod(frame2, 133, (Object) null, 0);
        lambda$Fn101 = new ModuleMethod(frame2, 134, (Object) null, 0);
        lambda$Fn102 = new ModuleMethod(frame2, 135, (Object) null, 0);
        lambda$Fn103 = new ModuleMethod(frame2, 136, (Object) null, 0);
        lambda$Fn104 = new ModuleMethod(frame2, 137, (Object) null, 0);
        lambda$Fn105 = new ModuleMethod(frame2, 138, (Object) null, 0);
        lambda$Fn106 = new ModuleMethod(frame2, 139, (Object) null, 0);
        lambda$Fn107 = new ModuleMethod(frame2, 140, (Object) null, 0);
        lambda$Fn108 = new ModuleMethod(frame2, 141, (Object) null, 0);
        lambda$Fn109 = new ModuleMethod(frame2, 142, (Object) null, 0);
        lambda$Fn110 = new ModuleMethod(frame2, 143, (Object) null, 0);
        lambda$Fn111 = new ModuleMethod(frame2, 144, (Object) null, 0);
        lambda$Fn112 = new ModuleMethod(frame2, 145, (Object) null, 0);
        lambda$Fn113 = new ModuleMethod(frame2, 146, (Object) null, 0);
        lambda$Fn114 = new ModuleMethod(frame2, 147, (Object) null, 0);
        lambda$Fn115 = new ModuleMethod(frame2, 148, (Object) null, 0);
        lambda$Fn116 = new ModuleMethod(frame2, 149, (Object) null, 0);
        lambda$Fn117 = new ModuleMethod(frame2, 150, (Object) null, 0);
        lambda$Fn119 = new ModuleMethod(frame2, 151, (Object) null, 0);
        lambda$Fn120 = new ModuleMethod(frame2, 152, (Object) null, 0);
        lambda$Fn118 = new ModuleMethod(frame2, 153, (Object) null, 0);
        this.Button2$Click = new ModuleMethod(frame2, 154, Lit303, 0);
        this.Button2$TouchDown = new ModuleMethod(frame2, 155, Lit304, 0);
        this.Button2$TouchUp = new ModuleMethod(frame2, 156, Lit305, 0);
        lambda$Fn121 = new ModuleMethod(frame2, 157, (Object) null, 0);
        lambda$Fn122 = new ModuleMethod(frame2, 158, (Object) null, 0);
        lambda$Fn123 = new ModuleMethod(frame2, 159, (Object) null, 0);
        lambda$Fn124 = new ModuleMethod(frame2, ComponentConstants.TEXTBOX_PREFERRED_WIDTH, (Object) null, 0);
        lambda$Fn125 = new ModuleMethod(frame2, 161, (Object) null, 0);
        lambda$Fn126 = new ModuleMethod(frame2, 162, (Object) null, 0);
        lambda$Fn127 = new ModuleMethod(frame2, 163, (Object) null, 0);
        lambda$Fn128 = new ModuleMethod(frame2, 164, (Object) null, 0);
        proc$Fn129 = new ModuleMethod(frame2, 165, Lit376, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.TeachableMachineImageClassifier1$GotClassification = new ModuleMethod(frame2, 166, Lit335, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn130 = new ModuleMethod(frame2, 167, (Object) null, 0);
        lambda$Fn131 = new ModuleMethod(frame2, 168, (Object) null, 0);
        this.ChatBot1$GotResponse = new ModuleMethod(frame2, 169, Lit346, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.LocationSensor1$LocationChanged = new ModuleMethod(frame2, 170, Lit352, 16388);
        lambda$Fn132 = new ModuleMethod(frame2, 171, (Object) null, 0);
        lambda$Fn133 = new ModuleMethod(frame2, 172, (Object) null, 0);
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
        Crop = null;
        this.form$Mnname$Mnsymbol = Lit0;
        this.events$Mnto$Mnregister = LList.Empty;
        this.components$Mnto$Mncreate = LList.Empty;
        this.global$Mnvars$Mnto$Mncreate = LList.Empty;
        this.form$Mndo$Mnafter$Mncreation = LList.Empty;
        runtime.$instance.run();
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit3, ""), $result);
        } else {
            addToGlobalVars(Lit3, lambda$Fn2);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit4, ""), $result);
        } else {
            addToGlobalVars(Lit4, lambda$Fn3);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit5, ""), $result);
        } else {
            addToGlobalVars(Lit5, lambda$Fn4);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit6, ""), $result);
        } else {
            addToGlobalVars(Lit6, lambda$Fn5);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit7, ""), $result);
        } else {
            addToGlobalVars(Lit7, lambda$Fn6);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit8, ""), $result);
        } else {
            addToGlobalVars(Lit8, lambda$Fn7);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit9, Component.TYPEFACE_DEFAULT), $result);
        } else {
            addToGlobalVars(Lit9, lambda$Fn8);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit10, ""), $result);
        } else {
            addToGlobalVars(Lit10, lambda$Fn9);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit11, lambda$Fn10), $result);
        } else {
            addToGlobalVars(Lit11, lambda$Fn14);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit0, Lit31, Lit32, Lit33);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit34, Boolean.TRUE, Lit24);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit35, "gfdhgdghfu", Lit36);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit37, "Do-sharpEdit.png", Lit36);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit38, Lit39, Lit33);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit40, Lit41, Lit33);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit42, "unspecified", Lit36);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit43, Boolean.TRUE, Lit24);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit44, Boolean.TRUE, Lit24);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit45, "Responsive", Lit36);
            Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit46, "Farming Suggestions", Lit36), $result);
        } else {
            addToFormDoAfterCreation(new Promise(lambda$Fn19));
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit58, this.Crop$Initialize);
        } else {
            addToFormEnvironment(Lit58, this.Crop$Initialize);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Crop", "Initialize");
        } else {
            addToEvents(Lit0, Lit59);
        }
        this.HorizontalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit60, Lit61, lambda$Fn20), $result);
        } else {
            addToComponents(Lit0, Lit64, Lit61, lambda$Fn21);
        }
        this.backbutton = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit61, Lit65, Lit66, lambda$Fn22), $result);
        } else {
            addToComponents(Lit61, Lit73, Lit66, lambda$Fn23);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit78, this.backbutton$Click);
        } else {
            addToFormEnvironment(Lit78, this.backbutton$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "Click");
        } else {
            addToEvents(Lit66, Lit79);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit80, this.backbutton$TouchDown);
        } else {
            addToFormEnvironment(Lit80, this.backbutton$TouchDown);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "TouchDown");
        } else {
            addToEvents(Lit66, Lit81);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit82, this.backbutton$TouchUp);
        } else {
            addToFormEnvironment(Lit82, this.backbutton$TouchUp);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "TouchUp");
        } else {
            addToEvents(Lit66, Lit83);
        }
        this.VerticalArrangement2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit61, Lit84, Lit85, lambda$Fn24), $result);
        } else {
            addToComponents(Lit61, Lit88, Lit85, lambda$Fn25);
        }
        this.VerticalArrangement25 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit89, Lit90, lambda$Fn26), $result);
        } else {
            addToComponents(Lit0, Lit91, Lit90, lambda$Fn27);
        }
        this.VerticalArrangement24 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit92, Lit93, lambda$Fn28), $result);
        } else {
            addToComponents(Lit0, Lit97, Lit93, lambda$Fn29);
        }
        this.Button7 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit93, Lit98, Lit99, lambda$Fn30), $result);
        } else {
            addToComponents(Lit93, Lit104, Lit99, lambda$Fn31);
        }
        this.VerticalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit105, Lit106, lambda$Fn32), $result);
        } else {
            addToComponents(Lit0, Lit107, Lit106, lambda$Fn33);
        }
        this.VerticalArrangement3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit108, Lit109, lambda$Fn34), $result);
        } else {
            addToComponents(Lit0, Lit112, Lit109, lambda$Fn35);
        }
        this.WebViewer1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit109, Lit113, Lit55, lambda$Fn36), $result);
        } else {
            addToComponents(Lit109, Lit115, Lit55, lambda$Fn37);
        }
        this.VerticalArrangement4 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit116, Lit117, lambda$Fn38), $result);
        } else {
            addToComponents(Lit0, Lit118, Lit117, lambda$Fn39);
        }
        this.VerticalArrangement31 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit119, Lit120, lambda$Fn40), $result);
        } else {
            addToComponents(Lit0, Lit121, Lit120, lambda$Fn41);
        }
        this.CircularProgress3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit120, Lit122, Lit123, lambda$Fn42), $result);
        } else {
            addToComponents(Lit120, Lit124, Lit123, lambda$Fn43);
        }
        this.VerticalArrangement5 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit125, Lit126, lambda$Fn44), $result);
        } else {
            addToComponents(Lit0, Lit127, Lit126, lambda$Fn45);
        }
        this.Button1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit126, Lit128, Lit129, lambda$Fn46), $result);
        } else {
            addToComponents(Lit126, Lit132, Lit129, lambda$Fn47);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit134, this.Button1$Click);
        } else {
            addToFormEnvironment(Lit134, this.Button1$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button1", "Click");
        } else {
            addToEvents(Lit129, Lit79);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit135, this.Button1$TouchDown);
        } else {
            addToFormEnvironment(Lit135, this.Button1$TouchDown);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button1", "TouchDown");
        } else {
            addToEvents(Lit129, Lit81);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit136, this.Button1$TouchUp);
        } else {
            addToFormEnvironment(Lit136, this.Button1$TouchUp);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button1", "TouchUp");
        } else {
            addToEvents(Lit129, Lit83);
        }
        this.VerticalArrangement15 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit137, Lit138, lambda$Fn48), $result);
        } else {
            addToComponents(Lit0, Lit139, Lit138, lambda$Fn49);
        }
        this.VerticalArrangement29 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit140, Lit141, lambda$Fn50), $result);
        } else {
            addToComponents(Lit0, Lit142, Lit141, lambda$Fn51);
        }
        this.CircularProgress1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit141, Lit143, Lit144, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit141, Lit145, Lit144, Boolean.FALSE);
        }
        this.VerticalArrangement22 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit146, Lit147, lambda$Fn52), $result);
        } else {
            addToComponents(Lit0, Lit149, Lit147, lambda$Fn53);
        }
        this.ResultLabel = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit147, Lit150, Lit151, lambda$Fn54), $result);
        } else {
            addToComponents(Lit147, Lit154, Lit151, lambda$Fn55);
        }
        this.VerticalArrangement7 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit155, Lit156, lambda$Fn56), $result);
        } else {
            addToComponents(Lit0, Lit157, Lit156, lambda$Fn57);
        }
        this.VerticalArrangement27 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit158, Lit159, lambda$Fn58), $result);
        } else {
            addToComponents(Lit0, Lit160, Lit159, lambda$Fn59);
        }
        this.Button6 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit159, Lit161, Lit162, lambda$Fn60), $result);
        } else {
            addToComponents(Lit159, Lit166, Lit162, lambda$Fn61);
        }
        this.VerticalArrangement8 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit167, Lit168, lambda$Fn62), $result);
        } else {
            addToComponents(Lit0, Lit169, Lit168, lambda$Fn63);
        }
        this.VerticalArrangement26 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit170, Lit171, lambda$Fn64), $result);
        } else {
            addToComponents(Lit0, Lit172, Lit171, lambda$Fn65);
        }
        this.Button3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit171, Lit173, Lit174, lambda$Fn66), $result);
        } else {
            addToComponents(Lit171, Lit179, Lit174, lambda$Fn67);
        }
        this.TextBoxLocation = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit171, Lit180, Lit19, lambda$Fn68), $result);
        } else {
            addToComponents(Lit171, Lit187, Lit19, lambda$Fn69);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit188, this.TextBoxLocation$LostFocus);
        } else {
            addToFormEnvironment(Lit188, this.TextBoxLocation$LostFocus);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "TextBoxLocation", "LostFocus");
        } else {
            addToEvents(Lit19, Lit189);
        }
        this.VerticalArrangement9 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit190, Lit191, lambda$Fn70), $result);
        } else {
            addToComponents(Lit0, Lit192, Lit191, lambda$Fn71);
        }
        this.VerticalArrangement10 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit193, Lit194, lambda$Fn72), $result);
        } else {
            addToComponents(Lit0, Lit195, Lit194, lambda$Fn73);
        }
        this.VerticalArrangement6 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit196, Lit197, lambda$Fn74), $result);
        } else {
            addToComponents(Lit0, Lit198, Lit197, lambda$Fn75);
        }
        this.GetLocation = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit197, Lit199, Lit200, lambda$Fn76), $result);
        } else {
            addToComponents(Lit197, Lit203, Lit200, lambda$Fn77);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit206, this.GetLocation$Click);
        } else {
            addToFormEnvironment(Lit206, this.GetLocation$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "GetLocation", "Click");
        } else {
            addToEvents(Lit200, Lit79);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit207, this.GetLocation$TouchDown);
        } else {
            addToFormEnvironment(Lit207, this.GetLocation$TouchDown);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "GetLocation", "TouchDown");
        } else {
            addToEvents(Lit200, Lit81);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit208, this.GetLocation$TouchUp);
        } else {
            addToFormEnvironment(Lit208, this.GetLocation$TouchUp);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "GetLocation", "TouchUp");
        } else {
            addToEvents(Lit200, Lit83);
        }
        this.VerticalArrangement11 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit209, Lit210, lambda$Fn78), $result);
        } else {
            addToComponents(Lit0, Lit212, Lit210, lambda$Fn79);
        }
        this.VerticalArrangement20 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit213, Lit214, lambda$Fn80), $result);
        } else {
            addToComponents(Lit0, Lit216, Lit214, lambda$Fn81);
        }
        this.Button4 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit214, Lit217, Lit218, lambda$Fn82), $result);
        } else {
            addToComponents(Lit214, Lit221, Lit218, lambda$Fn83);
        }
        this.TextBoxPlotarea = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit214, Lit222, Lit16, lambda$Fn84), $result);
        } else {
            addToComponents(Lit214, Lit227, Lit16, lambda$Fn85);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit228, this.TextBoxPlotarea$LostFocus);
        } else {
            addToFormEnvironment(Lit228, this.TextBoxPlotarea$LostFocus);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "TextBoxPlotarea", "LostFocus");
        } else {
            addToEvents(Lit16, Lit189);
        }
        this.VerticalArrangement12 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit229, Lit230, lambda$Fn86), $result);
        } else {
            addToComponents(Lit0, Lit231, Lit230, lambda$Fn87);
        }
        this.VerticalArrangement19 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit232, Lit233, lambda$Fn88), $result);
        } else {
            addToComponents(Lit0, Lit234, Lit233, lambda$Fn89);
        }
        this.ListPicker1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit233, Lit235, Lit49, lambda$Fn90), $result);
        } else {
            addToComponents(Lit233, Lit238, Lit49, lambda$Fn91);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit241, this.ListPicker1$AfterPicking);
        } else {
            addToFormEnvironment(Lit241, this.ListPicker1$AfterPicking);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "ListPicker1", "AfterPicking");
        } else {
            addToEvents(Lit49, Lit242);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit243, this.ListPicker1$TouchDown);
        } else {
            addToFormEnvironment(Lit243, this.ListPicker1$TouchDown);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "ListPicker1", "TouchDown");
        } else {
            addToEvents(Lit49, Lit81);
        }
        this.VerticalArrangement13 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit244, Lit245, lambda$Fn92), $result);
        } else {
            addToComponents(Lit0, Lit246, Lit245, lambda$Fn93);
        }
        this.VerticalArrangement14 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit247, Lit248, lambda$Fn94), $result);
        } else {
            addToComponents(Lit0, Lit250, Lit248, lambda$Fn95);
        }
        this.Button5 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit248, Lit251, Lit252, lambda$Fn96), $result);
        } else {
            addToComponents(Lit248, Lit255, Lit252, lambda$Fn97);
        }
        this.TextBoxCropPreference = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit248, Lit256, Lit12, lambda$Fn98), $result);
        } else {
            addToComponents(Lit248, Lit261, Lit12, lambda$Fn99);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit262, this.TextBoxCropPreference$LostFocus);
        } else {
            addToFormEnvironment(Lit262, this.TextBoxCropPreference$LostFocus);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "TextBoxCropPreference", "LostFocus");
        } else {
            addToEvents(Lit12, Lit189);
        }
        this.VerticalArrangement16 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit263, Lit264, lambda$Fn100), $result);
        } else {
            addToComponents(Lit0, Lit265, Lit264, lambda$Fn101);
        }
        this.VerticalArrangement23 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit266, Lit267, lambda$Fn102), $result);
        } else {
            addToComponents(Lit0, Lit269, Lit267, lambda$Fn103);
        }
        this.Label5 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit267, Lit270, Lit22, lambda$Fn104), $result);
        } else {
            addToComponents(Lit267, Lit271, Lit22, lambda$Fn105);
        }
        this.VerticalArrangement18 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit272, Lit273, lambda$Fn106), $result);
        } else {
            addToComponents(Lit0, Lit274, Lit273, lambda$Fn107);
        }
        this.VerticalArrangement30 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit275, Lit276, lambda$Fn108), $result);
        } else {
            addToComponents(Lit0, Lit277, Lit276, lambda$Fn109);
        }
        this.CircularProgress2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit276, Lit278, Lit279, lambda$Fn110), $result);
        } else {
            addToComponents(Lit276, Lit280, Lit279, lambda$Fn111);
        }
        this.VerticalArrangement17 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit281, Lit282, lambda$Fn112), $result);
        } else {
            addToComponents(Lit0, Lit283, Lit282, lambda$Fn113);
        }
        this.Button2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit282, Lit284, Lit285, lambda$Fn114), $result);
        } else {
            addToComponents(Lit282, Lit287, Lit285, lambda$Fn115);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit303, this.Button2$Click);
        } else {
            addToFormEnvironment(Lit303, this.Button2$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "Click");
        } else {
            addToEvents(Lit285, Lit79);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit304, this.Button2$TouchDown);
        } else {
            addToFormEnvironment(Lit304, this.Button2$TouchDown);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "TouchDown");
        } else {
            addToEvents(Lit285, Lit81);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit305, this.Button2$TouchUp);
        } else {
            addToFormEnvironment(Lit305, this.Button2$TouchUp);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "TouchUp");
        } else {
            addToEvents(Lit285, Lit83);
        }
        this.VerticalArrangement28 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit306, Lit307, lambda$Fn121), $result);
        } else {
            addToComponents(Lit0, Lit308, Lit307, lambda$Fn122);
        }
        this.VerticalArrangement32 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit309, Lit310, lambda$Fn123), $result);
        } else {
            addToComponents(Lit0, Lit312, Lit310, lambda$Fn124);
        }
        this.Label6 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit310, Lit313, Lit299, lambda$Fn125), $result);
        } else {
            addToComponents(Lit310, Lit316, Lit299, lambda$Fn126);
        }
        this.VerticalArrangement33 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit317, Lit318, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit319, Lit318, Boolean.FALSE);
        }
        this.TeachableMachineImageClassifier1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit320, Lit75, lambda$Fn127), $result);
        } else {
            addToComponents(Lit0, Lit324, Lit75, lambda$Fn128);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit335, this.TeachableMachineImageClassifier1$GotClassification);
        } else {
            addToFormEnvironment(Lit335, this.TeachableMachineImageClassifier1$GotClassification);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "TeachableMachineImageClassifier1", "GotClassification");
        } else {
            addToEvents(Lit75, Lit336);
        }
        this.ChatBot1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit337, Lit295, lambda$Fn130), $result);
        } else {
            addToComponents(Lit0, Lit340, Lit295, lambda$Fn131);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit346, this.ChatBot1$GotResponse);
        } else {
            addToFormEnvironment(Lit346, this.ChatBot1$GotResponse);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "ChatBot1", "GotResponse");
        } else {
            addToEvents(Lit295, Lit347);
        }
        this.LocationSensor1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit348, Lit204, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit349, Lit204, Boolean.FALSE);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit352, this.LocationSensor1$LocationChanged);
        } else {
            addToFormEnvironment(Lit352, this.LocationSensor1$LocationChanged);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "LocationSensor1", "LocationChanged");
        } else {
            addToEvents(Lit204, Lit353);
        }
        this.TinyDB1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit354, Lit341, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit355, Lit341, Boolean.FALSE);
        }
        this.Sound1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit356, Lit53, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit357, Lit53, Boolean.FALSE);
        }
        this.Notifier1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit358, Lit300, lambda$Fn132), $result);
        } else {
            addToComponents(Lit0, Lit361, Lit300, lambda$Fn133);
        }
        runtime.initRuntime();
    }

    static String lambda3() {
        return "";
    }

    static String lambda4() {
        return "";
    }

    static String lambda5() {
        return "";
    }

    static String lambda6() {
        return "";
    }

    static String lambda7() {
        return "";
    }

    static String lambda8() {
        return "";
    }

    static String lambda9() {
        return Component.TYPEFACE_DEFAULT;
    }

    /* compiled from: Crop.yail */
    public class frame extends ModuleBody {
        Crop $main;

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 18:
                    return Crop.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return Crop.lambda3();
                case 21:
                    return Crop.lambda4();
                case 22:
                    return Crop.lambda5();
                case 23:
                    return Crop.lambda6();
                case 24:
                    return Crop.lambda7();
                case 25:
                    return Crop.lambda8();
                case 26:
                    return Crop.lambda9();
                case 27:
                    return Crop.lambda10();
                case 28:
                    return Crop.lambda12();
                case 29:
                    return Crop.lambda13();
                case 30:
                    return Crop.lambda14();
                case 31:
                    return Crop.lambda11();
                case 32:
                    return Crop.lambda17();
                case 33:
                    return Crop.lambda18();
                case 34:
                    return Crop.lambda19();
                case 35:
                    return Crop.lambda16();
                case 36:
                    return Crop.lambda15();
                case 37:
                    return Crop.lambda20();
                case 38:
                    return this.$main.Crop$Initialize();
                case 39:
                    return Crop.lambda21();
                case 40:
                    return Crop.lambda22();
                case 41:
                    return Crop.lambda23();
                case 42:
                    return Crop.lambda24();
                case 43:
                    return this.$main.backbutton$Click();
                case 44:
                    return this.$main.backbutton$TouchDown();
                case 45:
                    return this.$main.backbutton$TouchUp();
                case 46:
                    return Crop.lambda25();
                case 47:
                    return Crop.lambda26();
                case 48:
                    return Crop.lambda27();
                case 49:
                    return Crop.lambda28();
                case 50:
                    return Crop.lambda29();
                case 51:
                    return Crop.lambda30();
                case 52:
                    return Crop.lambda31();
                case 53:
                    return Crop.lambda32();
                case 54:
                    return Crop.lambda33();
                case 55:
                    return Crop.lambda34();
                case 56:
                    return Crop.lambda35();
                case 57:
                    return Crop.lambda36();
                case 58:
                    return Crop.lambda37();
                case 59:
                    return Crop.lambda38();
                case 60:
                    return Crop.lambda39();
                case 61:
                    return Crop.lambda40();
                case 62:
                    return Crop.lambda41();
                case 63:
                    return Crop.lambda42();
                case 64:
                    return Crop.lambda43();
                case 65:
                    return Crop.lambda44();
                case 66:
                    return Crop.lambda45();
                case 67:
                    return Crop.lambda46();
                case 68:
                    return Crop.lambda47();
                case 69:
                    return Crop.lambda48();
                case 70:
                    return this.$main.Button1$Click();
                case 71:
                    return this.$main.Button1$TouchDown();
                case 72:
                    return this.$main.Button1$TouchUp();
                case 73:
                    return Crop.lambda49();
                case 74:
                    return Crop.lambda50();
                case 75:
                    return Crop.lambda51();
                case 76:
                    return Crop.lambda52();
                case 77:
                    return Crop.lambda53();
                case 78:
                    return Crop.lambda54();
                case 79:
                    return Crop.lambda55();
                case 80:
                    return Crop.lambda56();
                case 81:
                    return Crop.lambda57();
                case 82:
                    return Crop.lambda58();
                case 83:
                    return Crop.lambda59();
                case 84:
                    return Crop.lambda60();
                case 85:
                    return Crop.lambda61();
                case 86:
                    return Crop.lambda62();
                case 87:
                    return Crop.lambda63();
                case 88:
                    return Crop.lambda64();
                case 89:
                    return Crop.lambda65();
                case 90:
                    return Crop.lambda66();
                case 91:
                    return Crop.lambda67();
                case 92:
                    return Crop.lambda68();
                case 93:
                    return Crop.lambda69();
                case 94:
                    return Crop.lambda70();
                case 95:
                    return this.$main.TextBoxLocation$LostFocus();
                case 96:
                    return Crop.lambda71();
                case 97:
                    return Crop.lambda72();
                case 98:
                    return Crop.lambda73();
                case 99:
                    return Crop.lambda74();
                case 100:
                    return Crop.lambda75();
                case 101:
                    return Crop.lambda76();
                case 102:
                    return Crop.lambda77();
                case 103:
                    return Crop.lambda78();
                case 104:
                    return this.$main.GetLocation$Click();
                case 105:
                    return this.$main.GetLocation$TouchDown();
                case 106:
                    return this.$main.GetLocation$TouchUp();
                case 107:
                    return Crop.lambda79();
                case 108:
                    return Crop.lambda80();
                case 109:
                    return Crop.lambda81();
                case 110:
                    return Crop.lambda82();
                case 111:
                    return Crop.lambda83();
                case 112:
                    return Crop.lambda84();
                case 113:
                    return Crop.lambda85();
                case 114:
                    return Crop.lambda86();
                case 115:
                    return this.$main.TextBoxPlotarea$LostFocus();
                case 116:
                    return Crop.lambda87();
                case 117:
                    return Crop.lambda88();
                case 118:
                    return Crop.lambda89();
                case 119:
                    return Crop.lambda90();
                case 120:
                    return Crop.lambda91();
                case 121:
                    return Crop.lambda92();
                case 122:
                    return this.$main.ListPicker1$AfterPicking();
                case 123:
                    return this.$main.ListPicker1$TouchDown();
                case 124:
                    return Crop.lambda93();
                case 125:
                    return Crop.lambda94();
                case 126:
                    return Crop.lambda95();
                case 127:
                    return Crop.lambda96();
                case 128:
                    return Crop.lambda97();
                case 129:
                    return Crop.lambda98();
                case 130:
                    return Crop.lambda99();
                case 131:
                    return Crop.lambda100();
                case 132:
                    return this.$main.TextBoxCropPreference$LostFocus();
                case 133:
                    return Crop.lambda101();
                case 134:
                    return Crop.lambda102();
                case 135:
                    return Crop.lambda103();
                case 136:
                    return Crop.lambda104();
                case 137:
                    return Crop.lambda105();
                case 138:
                    return Crop.lambda106();
                case 139:
                    return Crop.lambda107();
                case 140:
                    return Crop.lambda108();
                case 141:
                    return Crop.lambda109();
                case 142:
                    return Crop.lambda110();
                case 143:
                    return Crop.lambda111();
                case 144:
                    return Crop.lambda112();
                case 145:
                    return Crop.lambda113();
                case 146:
                    return Crop.lambda114();
                case 147:
                    return Crop.lambda115();
                case 148:
                    return Crop.lambda116();
                case 149:
                    return Crop.lambda117();
                case 150:
                    return Crop.lambda118();
                case 151:
                    return Crop.lambda120();
                case 152:
                    return Crop.lambda121();
                case 153:
                    return Crop.lambda119();
                case 154:
                    return this.$main.Button2$Click();
                case 155:
                    return this.$main.Button2$TouchDown();
                case 156:
                    return this.$main.Button2$TouchUp();
                case 157:
                    return Crop.lambda122();
                case 158:
                    return Crop.lambda123();
                case 159:
                    return Crop.lambda124();
                case ComponentConstants.TEXTBOX_PREFERRED_WIDTH:
                    return Crop.lambda125();
                case 161:
                    return Crop.lambda126();
                case 162:
                    return Crop.lambda127();
                case 163:
                    return Crop.lambda128();
                case 164:
                    return Crop.lambda129();
                case 167:
                    return Crop.lambda131();
                case 168:
                    return Crop.lambda132();
                case 171:
                    return Crop.lambda133();
                case 172:
                    return Crop.lambda134();
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
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
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
                case 98:
                case 99:
                case 100:
                case 101:
                case 102:
                case 103:
                case 104:
                case 105:
                case 106:
                case 107:
                case 108:
                case 109:
                case 110:
                case 111:
                case 112:
                case 113:
                case 114:
                case 115:
                case 116:
                case 117:
                case 118:
                case 119:
                case 120:
                case 121:
                case 122:
                case 123:
                case 124:
                case 125:
                case 126:
                case 127:
                case 128:
                case 129:
                case 130:
                case 131:
                case 132:
                case 133:
                case 134:
                case 135:
                case 136:
                case 137:
                case 138:
                case 139:
                case 140:
                case 141:
                case 142:
                case 143:
                case 144:
                case 145:
                case 146:
                case 147:
                case 148:
                case 149:
                case 150:
                case 151:
                case 152:
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 159:
                case ComponentConstants.TEXTBOX_PREFERRED_WIDTH:
                case 161:
                case 162:
                case 163:
                case 164:
                case 167:
                case 168:
                case 171:
                case 172:
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
                    if (!(obj instanceof Crop)) {
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
                    if (!(obj instanceof Crop)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 165:
                case 166:
                case 169:
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
                    if (!(obj instanceof Crop)) {
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
                    if (!(obj instanceof Crop)) {
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
                case 170:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
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
                case 165:
                    return Crop.lambda130proc(obj);
                case 166:
                    return this.$main.TeachableMachineImageClassifier1$GotClassification(obj);
                case 169:
                    return this.$main.ChatBot1$GotResponse(obj);
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
                    Crop crop = this.$main;
                    try {
                        Component component = (Component) obj;
                        try {
                            String str = (String) obj2;
                            try {
                                if (obj3 == Boolean.FALSE) {
                                    z = false;
                                }
                                try {
                                    crop.dispatchGenericEvent(component, str, z, (Object[]) obj4);
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
                case 170:
                    return this.$main.LocationSensor1$LocationChanged(obj, obj2, obj3, obj4);
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

    static String lambda10() {
        return "";
    }

    static Object lambda11() {
        return runtime.processAndDelayed$V(new Object[]{lambda$Fn11, lambda$Fn12, lambda$Fn13}) != Boolean.FALSE ? runtime.setAndCoerceProperty$Ex(Lit22, Lit23, Boolean.FALSE, Lit24) : Values.empty;
    }

    static Object lambda12() {
        return runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.callYailPrimitive(runtime.string$Mntrim, LList.list1(runtime.get$Mnproperty.apply2(Lit12, Lit13)), Lit14, "trim"), ""), Lit15, "=");
    }

    static Object lambda13() {
        return runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.callYailPrimitive(runtime.string$Mntrim, LList.list1(runtime.get$Mnproperty.apply2(Lit16, Lit13)), Lit17, "trim"), ""), Lit18, "=");
    }

    static Object lambda14() {
        return runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.callYailPrimitive(runtime.string$Mntrim, LList.list1(runtime.get$Mnproperty.apply2(Lit19, Lit13)), Lit20, "trim"), ""), Lit21, "=");
    }

    static Procedure lambda15() {
        return lambda$Fn15;
    }

    static Object lambda16() {
        return runtime.processAndDelayed$V(new Object[]{lambda$Fn16, lambda$Fn17, lambda$Fn18}) != Boolean.FALSE ? runtime.setAndCoerceProperty$Ex(Lit22, Lit23, Boolean.FALSE, Lit24) : Values.empty;
    }

    static Object lambda17() {
        return runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.callYailPrimitive(runtime.string$Mntrim, LList.list1(runtime.get$Mnproperty.apply2(Lit12, Lit13)), Lit25, "trim"), ""), Lit26, "=");
    }

    static Object lambda18() {
        return runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.callYailPrimitive(runtime.string$Mntrim, LList.list1(runtime.get$Mnproperty.apply2(Lit16, Lit13)), Lit27, "trim"), ""), Lit28, "=");
    }

    static Object lambda19() {
        return runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.callYailPrimitive(runtime.string$Mntrim, LList.list1(runtime.get$Mnproperty.apply2(Lit19, Lit13)), Lit29, "trim"), ""), Lit30, "=");
    }

    static Object lambda20() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit31, Lit32, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit34, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit35, "gfdhgdghfu", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit37, "Do-sharpEdit.png", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit38, Lit39, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit40, Lit41, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit42, "unspecified", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit43, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit44, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit45, "Responsive", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit46, "Farming Suggestions", Lit36);
    }

    public Object Crop$Initialize() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit0, Lit47, LList.list1(Permission.FineLocation), Lit48);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit50, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.list4("Low", "Medium", "High", "Very High"), Lit51, "make a list"), Lit52);
        runtime.setAndCoerceProperty$Ex(Lit53, Lit54, "ClickSoundEffect(1).mp3", Lit36);
        runtime.callComponentMethod(Lit55, Lit56, LList.Empty, LList.Empty);
        return runtime.callComponentMethod(Lit55, Lit57, LList.Empty, LList.Empty);
    }

    static Object lambda21() {
        return runtime.setAndCoerceProperty$Ex(Lit61, Lit62, Lit63, Lit33);
    }

    static Object lambda22() {
        return runtime.setAndCoerceProperty$Ex(Lit61, Lit62, Lit63, Lit33);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit66, Lit67, Lit68, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit66, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit66, Lit71, Lit72, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit66, Lit13, " ⬅️", Lit36);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit66, Lit67, Lit68, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit66, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit66, Lit71, Lit72, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit66, Lit13, " ⬅️", Lit36);
    }

    public Object backbutton$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit53, Lit74, LList.Empty, LList.Empty);
        runtime.callComponentMethod(Lit75, Lit76, LList.Empty, LList.Empty);
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit77, "open another screen");
    }

    public Object backbutton$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit66, Lit69, "13", Lit33);
    }

    public Object backbutton$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit66, Lit69, "15", Lit33);
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit85, Lit86, Lit87, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit85, Lit62, Lit87, Lit33);
    }

    static Object lambda26() {
        runtime.setAndCoerceProperty$Ex(Lit85, Lit86, Lit87, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit85, Lit62, Lit87, Lit33);
    }

    static Object lambda27() {
        return runtime.setAndCoerceProperty$Ex(Lit90, Lit86, Lit70, Lit33);
    }

    static Object lambda28() {
        return runtime.setAndCoerceProperty$Ex(Lit90, Lit86, Lit70, Lit33);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit93, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit93, Lit67, Lit96, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit93, Lit62, Lit63, Lit33);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit93, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit93, Lit67, Lit96, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit93, Lit62, Lit63, Lit33);
    }

    static Object lambda31() {
        runtime.setAndCoerceProperty$Ex(Lit99, Lit67, Lit100, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit69, Lit101, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit13, "📸  Hold still and focus on soil to get analysis", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit99, Lit62, Lit103, Lit33);
    }

    static Object lambda32() {
        runtime.setAndCoerceProperty$Ex(Lit99, Lit67, Lit100, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit69, Lit101, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit13, "📸  Hold still and focus on soil to get analysis", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit99, Lit62, Lit103, Lit33);
    }

    static Object lambda33() {
        return runtime.setAndCoerceProperty$Ex(Lit106, Lit86, Lit70, Lit33);
    }

    static Object lambda34() {
        return runtime.setAndCoerceProperty$Ex(Lit106, Lit86, Lit70, Lit33);
    }

    static Object lambda35() {
        runtime.setAndCoerceProperty$Ex(Lit109, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit109, Lit110, Lit111, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit109, Lit62, Lit63, Lit33);
    }

    static Object lambda36() {
        runtime.setAndCoerceProperty$Ex(Lit109, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit109, Lit110, Lit111, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit109, Lit62, Lit63, Lit33);
    }

    static Object lambda37() {
        runtime.setAndCoerceProperty$Ex(Lit55, Lit86, Lit114, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit55, Lit62, Lit114, Lit33);
    }

    static Object lambda38() {
        runtime.setAndCoerceProperty$Ex(Lit55, Lit86, Lit114, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit55, Lit62, Lit114, Lit33);
    }

    static Object lambda39() {
        return runtime.setAndCoerceProperty$Ex(Lit117, Lit86, Lit70, Lit33);
    }

    static Object lambda40() {
        return runtime.setAndCoerceProperty$Ex(Lit117, Lit86, Lit70, Lit33);
    }

    static Object lambda41() {
        runtime.setAndCoerceProperty$Ex(Lit120, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit120, Lit62, Lit63, Lit33);
    }

    static Object lambda42() {
        runtime.setAndCoerceProperty$Ex(Lit120, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit120, Lit62, Lit63, Lit33);
    }

    static Object lambda43() {
        return runtime.setAndCoerceProperty$Ex(Lit123, Lit23, Boolean.FALSE, Lit24);
    }

    static Object lambda44() {
        return runtime.setAndCoerceProperty$Ex(Lit123, Lit23, Boolean.FALSE, Lit24);
    }

    static Object lambda45() {
        runtime.setAndCoerceProperty$Ex(Lit126, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit62, Lit63, Lit33);
    }

    static Object lambda46() {
        runtime.setAndCoerceProperty$Ex(Lit126, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit62, Lit63, Lit33);
    }

    static Object lambda47() {
        runtime.setAndCoerceProperty$Ex(Lit129, Lit67, Lit130, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit129, Lit131, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit129, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit129, Lit102, Component.TYPEFACE_MONOSPACE, Lit36);
        runtime.setAndCoerceProperty$Ex(Lit129, Lit71, Lit72, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit129, Lit13, "✅ Confirm", Lit36);
    }

    static Object lambda48() {
        runtime.setAndCoerceProperty$Ex(Lit129, Lit67, Lit130, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit129, Lit131, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit129, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit129, Lit102, Component.TYPEFACE_MONOSPACE, Lit36);
        runtime.setAndCoerceProperty$Ex(Lit129, Lit71, Lit72, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit129, Lit13, "✅ Confirm", Lit36);
    }

    public Object Button1$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit75, Lit133, LList.Empty, LList.Empty);
        runtime.callComponentMethod(Lit53, Lit74, LList.Empty, LList.Empty);
        return runtime.setAndCoerceProperty$Ex(Lit123, Lit23, Boolean.TRUE, Lit24);
    }

    public Object Button1$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit129, Lit69, "13", Lit33);
    }

    public Object Button1$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit129, Lit69, "15", Lit33);
    }

    static Object lambda49() {
        return runtime.setAndCoerceProperty$Ex(Lit138, Lit86, Lit70, Lit33);
    }

    static Object lambda50() {
        return runtime.setAndCoerceProperty$Ex(Lit138, Lit86, Lit70, Lit33);
    }

    static Object lambda51() {
        runtime.setAndCoerceProperty$Ex(Lit141, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit141, Lit23, Boolean.FALSE, Lit24);
        return runtime.setAndCoerceProperty$Ex(Lit141, Lit62, Lit63, Lit33);
    }

    static Object lambda52() {
        runtime.setAndCoerceProperty$Ex(Lit141, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit141, Lit23, Boolean.FALSE, Lit24);
        return runtime.setAndCoerceProperty$Ex(Lit141, Lit62, Lit63, Lit33);
    }

    static Object lambda53() {
        runtime.setAndCoerceProperty$Ex(Lit147, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit147, Lit67, Lit148, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit147, Lit62, Lit63, Lit33);
    }

    static Object lambda54() {
        runtime.setAndCoerceProperty$Ex(Lit147, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit147, Lit67, Lit148, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit147, Lit62, Lit63, Lit33);
    }

    static Object lambda55() {
        runtime.setAndCoerceProperty$Ex(Lit151, Lit131, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit151, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit151, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit151, Lit13, "Soil Type Result", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit151, Lit152, Lit153, Lit33);
    }

    static Object lambda56() {
        runtime.setAndCoerceProperty$Ex(Lit151, Lit131, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit151, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit151, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit151, Lit13, "Soil Type Result", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit151, Lit152, Lit153, Lit33);
    }

    static Object lambda57() {
        runtime.setAndCoerceProperty$Ex(Lit156, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit156, Lit86, Lit70, Lit33);
    }

    static Object lambda58() {
        runtime.setAndCoerceProperty$Ex(Lit156, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit156, Lit86, Lit70, Lit33);
    }

    static Object lambda59() {
        runtime.setAndCoerceProperty$Ex(Lit159, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit159, Lit62, Lit63, Lit33);
    }

    static Object lambda60() {
        runtime.setAndCoerceProperty$Ex(Lit159, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit159, Lit62, Lit63, Lit33);
    }

    static Object lambda61() {
        runtime.setAndCoerceProperty$Ex(Lit162, Lit67, Lit163, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit69, Lit164, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit13, "📝 Add additional details to get personalized suggestions!", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit162, Lit62, Lit165, Lit33);
    }

    static Object lambda62() {
        runtime.setAndCoerceProperty$Ex(Lit162, Lit67, Lit163, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit69, Lit164, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit13, "📝 Add additional details to get personalized suggestions!", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit162, Lit62, Lit165, Lit33);
    }

    static Object lambda63() {
        return runtime.setAndCoerceProperty$Ex(Lit168, Lit86, Lit70, Lit33);
    }

    static Object lambda64() {
        return runtime.setAndCoerceProperty$Ex(Lit168, Lit86, Lit70, Lit33);
    }

    static Object lambda65() {
        runtime.setAndCoerceProperty$Ex(Lit171, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit171, Lit62, Lit63, Lit33);
    }

    static Object lambda66() {
        runtime.setAndCoerceProperty$Ex(Lit171, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit171, Lit62, Lit63, Lit33);
    }

    static Object lambda67() {
        runtime.setAndCoerceProperty$Ex(Lit174, Lit67, Lit175, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit174, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit174, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit174, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit174, Lit13, "🔹 Add Location Manually-", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit174, Lit176, Lit177, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit174, Lit62, Lit178, Lit33);
    }

    static Object lambda68() {
        runtime.setAndCoerceProperty$Ex(Lit174, Lit67, Lit175, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit174, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit174, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit174, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit174, Lit13, "🔹 Add Location Manually-", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit174, Lit176, Lit177, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit174, Lit62, Lit178, Lit33);
    }

    static Object lambda69() {
        runtime.setAndCoerceProperty$Ex(Lit19, Lit67, Lit181, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit19, Lit102, "Inter_18pt-Regular.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit19, Lit182, "   👉 City and State", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit19, Lit183, Lit184, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit19, Lit152, Lit185, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit19, Lit62, Lit186, Lit33);
    }

    static Object lambda70() {
        runtime.setAndCoerceProperty$Ex(Lit19, Lit67, Lit181, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit19, Lit102, "Inter_18pt-Regular.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit19, Lit182, "   👉 City and State", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit19, Lit183, Lit184, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit19, Lit152, Lit185, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit19, Lit62, Lit186, Lit33);
    }

    public Object TextBoxLocation$LostFocus() {
        runtime.setThisForm();
        return runtime.addGlobalVarToCurrentFormEnvironment(Lit5, runtime.get$Mnproperty.apply2(Lit19, Lit13));
    }

    static Object lambda71() {
        return runtime.setAndCoerceProperty$Ex(Lit191, Lit86, Lit70, Lit33);
    }

    static Object lambda72() {
        return runtime.setAndCoerceProperty$Ex(Lit191, Lit86, Lit70, Lit33);
    }

    static Object lambda73() {
        return runtime.setAndCoerceProperty$Ex(Lit194, Lit86, Lit70, Lit33);
    }

    static Object lambda74() {
        return runtime.setAndCoerceProperty$Ex(Lit194, Lit86, Lit70, Lit33);
    }

    static Object lambda75() {
        runtime.setAndCoerceProperty$Ex(Lit197, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit197, Lit110, Lit111, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit197, Lit62, Lit63, Lit33);
    }

    static Object lambda76() {
        runtime.setAndCoerceProperty$Ex(Lit197, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit197, Lit110, Lit111, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit197, Lit62, Lit63, Lit33);
    }

    static Object lambda77() {
        runtime.setAndCoerceProperty$Ex(Lit200, Lit67, Lit201, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit200, Lit131, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit200, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit200, Lit102, Component.TYPEFACE_MONOSPACE, Lit36);
        runtime.setAndCoerceProperty$Ex(Lit200, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit200, Lit13, "📍 Or Get Location", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit200, Lit62, Lit202, Lit33);
    }

    static Object lambda78() {
        runtime.setAndCoerceProperty$Ex(Lit200, Lit67, Lit201, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit200, Lit131, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit200, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit200, Lit102, Component.TYPEFACE_MONOSPACE, Lit36);
        runtime.setAndCoerceProperty$Ex(Lit200, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit200, Lit13, "📍 Or Get Location", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit200, Lit62, Lit202, Lit33);
    }

    public Object GetLocation$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit204, Lit205, Boolean.TRUE, Lit24);
        runtime.callComponentMethod(Lit53, Lit74, LList.Empty, LList.Empty);
        Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit11, runtime.$Stthe$Mnnull$Mnvalue$St));
        return runtime.setAndCoerceProperty$Ex(Lit200, Lit13, "📍 Or Get Location - ✅", Lit36);
    }

    public Object GetLocation$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit200, Lit69, "13", Lit33);
    }

    public Object GetLocation$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit200, Lit69, "15", Lit33);
    }

    static Object lambda79() {
        return runtime.setAndCoerceProperty$Ex(Lit210, Lit86, Lit211, Lit33);
    }

    static Object lambda80() {
        return runtime.setAndCoerceProperty$Ex(Lit210, Lit86, Lit211, Lit33);
    }

    static Object lambda81() {
        runtime.setAndCoerceProperty$Ex(Lit214, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit214, Lit67, Lit215, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit214, Lit62, Lit63, Lit33);
    }

    static Object lambda82() {
        runtime.setAndCoerceProperty$Ex(Lit214, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit214, Lit67, Lit215, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit214, Lit62, Lit63, Lit33);
    }

    static Object lambda83() {
        runtime.setAndCoerceProperty$Ex(Lit218, Lit67, Lit219, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit218, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit218, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit218, Lit13, "🔹 Plot Area-", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit218, Lit176, Lit177, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit218, Lit62, Lit220, Lit33);
    }

    static Object lambda84() {
        runtime.setAndCoerceProperty$Ex(Lit218, Lit67, Lit219, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit218, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit218, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit218, Lit13, "🔹 Plot Area-", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit218, Lit176, Lit177, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit218, Lit62, Lit220, Lit33);
    }

    static Object lambda85() {
        runtime.setAndCoerceProperty$Ex(Lit16, Lit67, Lit223, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit102, "Inter_18pt-Regular.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit182, "   👉 Specify Unit", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit183, Lit224, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit152, Lit225, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit16, Lit62, Lit226, Lit33);
    }

    static Object lambda86() {
        runtime.setAndCoerceProperty$Ex(Lit16, Lit67, Lit223, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit102, "Inter_18pt-Regular.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit182, "   👉 Specify Unit", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit183, Lit224, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit16, Lit152, Lit225, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit16, Lit62, Lit226, Lit33);
    }

    public Object TextBoxPlotarea$LostFocus() {
        runtime.setThisForm();
        return runtime.addGlobalVarToCurrentFormEnvironment(Lit7, runtime.get$Mnproperty.apply2(Lit16, Lit13));
    }

    static Object lambda87() {
        return runtime.setAndCoerceProperty$Ex(Lit230, Lit86, Lit211, Lit33);
    }

    static Object lambda88() {
        return runtime.setAndCoerceProperty$Ex(Lit230, Lit86, Lit211, Lit33);
    }

    static Object lambda89() {
        runtime.setAndCoerceProperty$Ex(Lit233, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit233, Lit62, Lit63, Lit33);
    }

    static Object lambda90() {
        runtime.setAndCoerceProperty$Ex(Lit233, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit233, Lit62, Lit63, Lit33);
    }

    static Object lambda91() {
        runtime.setAndCoerceProperty$Ex(Lit49, Lit67, Lit236, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit131, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit102, Component.TYPEFACE_MONOSPACE, Lit36);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit13, "💵 Select your Relative Budget", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit49, Lit62, Lit237, Lit33);
    }

    static Object lambda92() {
        runtime.setAndCoerceProperty$Ex(Lit49, Lit67, Lit236, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit131, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit102, Component.TYPEFACE_MONOSPACE, Lit36);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit13, "💵 Select your Relative Budget", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit49, Lit62, Lit237, Lit33);
    }

    public Object ListPicker1$AfterPicking() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit49, Lit13, runtime.callYailPrimitive(strings.string$Mnappend, LList.list3("💵 Select Relative Budget- ", runtime.get$Mnproperty.apply2(Lit49, Lit239), "- ✅"), Lit240, "join"), Lit36);
        runtime.addGlobalVarToCurrentFormEnvironment(Lit8, runtime.get$Mnproperty.apply2(Lit49, Lit239));
        return runtime.callComponentMethod(Lit53, Lit74, LList.Empty, LList.Empty);
    }

    public Object ListPicker1$TouchDown() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit53, Lit74, LList.Empty, LList.Empty);
    }

    static Object lambda93() {
        return runtime.setAndCoerceProperty$Ex(Lit245, Lit86, Lit211, Lit33);
    }

    static Object lambda94() {
        return runtime.setAndCoerceProperty$Ex(Lit245, Lit86, Lit211, Lit33);
    }

    static Object lambda95() {
        runtime.setAndCoerceProperty$Ex(Lit248, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit248, Lit67, Lit249, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit248, Lit62, Lit63, Lit33);
    }

    static Object lambda96() {
        runtime.setAndCoerceProperty$Ex(Lit248, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit248, Lit67, Lit249, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit248, Lit62, Lit63, Lit33);
    }

    static Object lambda97() {
        runtime.setAndCoerceProperty$Ex(Lit252, Lit67, Lit253, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit252, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit252, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit252, Lit13, "🔹 Any Crop Type Preference? (Optional) -", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit252, Lit176, Lit177, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit252, Lit62, Lit254, Lit33);
    }

    static Object lambda98() {
        runtime.setAndCoerceProperty$Ex(Lit252, Lit67, Lit253, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit252, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit252, Lit71, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit252, Lit13, "🔹 Any Crop Type Preference? (Optional) -", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit252, Lit176, Lit177, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit252, Lit62, Lit254, Lit33);
    }

    static Object lambda100() {
        runtime.setAndCoerceProperty$Ex(Lit12, Lit67, Lit257, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit12, Lit102, "Inter_18pt-Regular.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit12, Lit182, "   👉 Ex:- Millets , Grains...", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit12, Lit183, Lit258, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit12, Lit152, Lit259, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit12, Lit62, Lit260, Lit33);
    }

    static Object lambda99() {
        runtime.setAndCoerceProperty$Ex(Lit12, Lit67, Lit257, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit12, Lit102, "Inter_18pt-Regular.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit12, Lit182, "   👉 Ex:- Millets , Grains...", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit12, Lit183, Lit258, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit12, Lit152, Lit259, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit12, Lit62, Lit260, Lit33);
    }

    public Object TextBoxCropPreference$LostFocus() {
        runtime.setThisForm();
        return runtime.addGlobalVarToCurrentFormEnvironment(Lit6, runtime.get$Mnproperty.apply2(Lit12, Lit13));
    }

    static Object lambda101() {
        return runtime.setAndCoerceProperty$Ex(Lit264, Lit86, Lit70, Lit33);
    }

    static Object lambda102() {
        return runtime.setAndCoerceProperty$Ex(Lit264, Lit86, Lit70, Lit33);
    }

    static Object lambda103() {
        runtime.setAndCoerceProperty$Ex(Lit267, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit267, Lit67, Lit268, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit267, Lit62, Lit63, Lit33);
    }

    static Object lambda104() {
        runtime.setAndCoerceProperty$Ex(Lit267, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit267, Lit67, Lit268, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit267, Lit62, Lit63, Lit33);
    }

    static Object lambda105() {
        runtime.setAndCoerceProperty$Ex(Lit22, Lit69, Lit101, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit22, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit22, Lit13, "Fill all fields to submit", Lit36);
    }

    static Object lambda106() {
        runtime.setAndCoerceProperty$Ex(Lit22, Lit69, Lit101, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit22, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit22, Lit13, "Fill all fields to submit", Lit36);
    }

    static Object lambda107() {
        runtime.setAndCoerceProperty$Ex(Lit273, Lit86, Lit70, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit273, Lit62, Lit70, Lit33);
    }

    static Object lambda108() {
        runtime.setAndCoerceProperty$Ex(Lit273, Lit86, Lit70, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit273, Lit62, Lit70, Lit33);
    }

    static Object lambda109() {
        runtime.setAndCoerceProperty$Ex(Lit276, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit276, Lit62, Lit63, Lit33);
    }

    static Object lambda110() {
        runtime.setAndCoerceProperty$Ex(Lit276, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit276, Lit62, Lit63, Lit33);
    }

    static Object lambda111() {
        return runtime.setAndCoerceProperty$Ex(Lit279, Lit23, Boolean.FALSE, Lit24);
    }

    static Object lambda112() {
        return runtime.setAndCoerceProperty$Ex(Lit279, Lit23, Boolean.FALSE, Lit24);
    }

    static Object lambda113() {
        runtime.setAndCoerceProperty$Ex(Lit282, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit282, Lit62, Lit63, Lit33);
    }

    static Object lambda114() {
        runtime.setAndCoerceProperty$Ex(Lit282, Lit94, Lit95, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit282, Lit62, Lit63, Lit33);
    }

    static Object lambda115() {
        runtime.setAndCoerceProperty$Ex(Lit285, Lit67, Lit286, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit285, Lit131, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit285, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit285, Lit102, Component.TYPEFACE_MONOSPACE, Lit36);
        runtime.setAndCoerceProperty$Ex(Lit285, Lit71, Lit72, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit285, Lit13, "📤 Submit", Lit36);
    }

    static Object lambda116() {
        runtime.setAndCoerceProperty$Ex(Lit285, Lit67, Lit286, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit285, Lit131, Boolean.TRUE, Lit24);
        runtime.setAndCoerceProperty$Ex(Lit285, Lit69, Lit70, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit285, Lit102, Component.TYPEFACE_MONOSPACE, Lit36);
        runtime.setAndCoerceProperty$Ex(Lit285, Lit71, Lit72, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit285, Lit13, "📤 Submit", Lit36);
    }

    public Object Button2$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit53, Lit74, LList.Empty, LList.Empty);
        runtime.setAndCoerceProperty$Ex(Lit279, Lit23, Boolean.TRUE, Lit24);
        if (runtime.processAndDelayed$V(new Object[]{lambda$Fn116, lambda$Fn117, lambda$Fn118}) != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit22, Lit23, Boolean.FALSE, Lit24);
            SimpleSymbol simpleSymbol = Lit295;
            SimpleSymbol simpleSymbol2 = Lit296;
            ModuleMethod moduleMethod = strings.string$Mnappend;
            Pair list1 = LList.list1("You are an expert agricultural consultant. A farmer is looking for suggestions based on the following:");
            LList.chain1(LList.chain1(LList.chain1(LList.chain4(LList.chain4(LList.chain4(list1, " Soil Type: ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, runtime.$Stthe$Mnnull$Mnvalue$St), " Location:", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, runtime.$Stthe$Mnnull$Mnvalue$St)), " Latitude-", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), " Longitude- ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St)), " PlotArea- ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St), " Budget- ", runtime.lookupGlobalVarInCurrentFormEnvironment(Lit8, runtime.$Stthe$Mnnull$Mnvalue$St)), " Crop Preference- "), runtime.lookupGlobalVarInCurrentFormEnvironment(Lit6, runtime.$Stthe$Mnnull$Mnvalue$St)), " Please suggest: 1. Suitable crops or crop varieties for this soil and region. 2. Seed and fertilizer recommendations. 3. Crop rotation or intercropping suggestions (if any). 4. Water and irrigation suggestions. 5. Any additional advice relevant to this combination. Keep the response concise, friendly, and easy to follow for a beginner farmer. add emojis throughout and plese use html format for bold like <b> text </b> also dont use italics and line break and not ** and not ** ** use <b> text </b> also use <br> to break line properly and break line 2 times when shifting topic. Send short concise and properly formatted text with break lines and all");
            runtime.callComponentMethod(simpleSymbol, simpleSymbol2, LList.list1(runtime.callYailPrimitive(moduleMethod, list1, Lit297, "join")), Lit298);
            runtime.setAndCoerceProperty$Ex(Lit299, Lit23, Boolean.TRUE, Lit24);
            return runtime.callComponentMethod(Lit75, Lit76, LList.Empty, LList.Empty);
        }
        runtime.callComponentMethod(Lit300, Lit301, LList.list3("🚫 Please fill out all the fields before submitting.", "Incomplete Form", "Okay"), Lit302);
        return runtime.setAndCoerceProperty$Ex(Lit279, Lit23, Boolean.FALSE, Lit24);
    }

    static Object lambda117() {
        return runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.callYailPrimitive(runtime.string$Mntrim, LList.list1(runtime.get$Mnproperty.apply2(Lit12, Lit13)), Lit288, "trim"), ""), Lit289, "=");
    }

    static Object lambda118() {
        return runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.callYailPrimitive(runtime.string$Mntrim, LList.list1(runtime.get$Mnproperty.apply2(Lit16, Lit13)), Lit290, "trim"), ""), Lit291, "=");
    }

    static Object lambda119() {
        return runtime.processOrDelayed$V(new Object[]{lambda$Fn119, lambda$Fn120});
    }

    static Object lambda120() {
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.get$Mnproperty.apply2(Lit200, Lit13), "📍 Or Get Location - ✅"), Lit292, "=");
    }

    static Object lambda121() {
        return runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.callYailPrimitive(runtime.string$Mntrim, LList.list1(runtime.get$Mnproperty.apply2(Lit19, Lit13)), Lit293, "trim"), ""), Lit294, "=");
    }

    public Object Button2$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit285, Lit69, "13", Lit33);
    }

    public Object Button2$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit285, Lit69, "15", Lit33);
    }

    static Object lambda122() {
        return runtime.setAndCoerceProperty$Ex(Lit307, Lit86, Lit87, Lit33);
    }

    static Object lambda123() {
        return runtime.setAndCoerceProperty$Ex(Lit307, Lit86, Lit87, Lit33);
    }

    static Object lambda124() {
        runtime.setAndCoerceProperty$Ex(Lit310, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit310, Lit67, Lit311, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit310, Lit62, Lit63, Lit33);
    }

    static Object lambda125() {
        runtime.setAndCoerceProperty$Ex(Lit310, Lit94, Lit95, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit310, Lit67, Lit311, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit310, Lit62, Lit63, Lit33);
    }

    static Object lambda126() {
        runtime.setAndCoerceProperty$Ex(Lit299, Lit69, Lit314, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit299, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit299, Lit13, "Note: Response time depends on your internet speed. 📡", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit299, Lit176, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit299, Lit23, Boolean.FALSE, Lit24);
        return runtime.setAndCoerceProperty$Ex(Lit299, Lit62, Lit315, Lit33);
    }

    static Object lambda127() {
        runtime.setAndCoerceProperty$Ex(Lit299, Lit69, Lit314, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit299, Lit102, "Inter_18pt-Bold.ttf", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit299, Lit13, "Note: Response time depends on your internet speed. 📡", Lit36);
        runtime.setAndCoerceProperty$Ex(Lit299, Lit176, Lit72, Lit33);
        runtime.setAndCoerceProperty$Ex(Lit299, Lit23, Boolean.FALSE, Lit24);
        return runtime.setAndCoerceProperty$Ex(Lit299, Lit62, Lit315, Lit33);
    }

    static Object lambda128() {
        runtime.setAndCoerceProperty$Ex(Lit75, Lit321, "https://teachablemachine.withgoogle.com/models/gPSivgcPY/", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit75, Lit322, runtime.lookupInCurrentFormEnvironment(Lit55), Lit323);
    }

    static Object lambda129() {
        runtime.setAndCoerceProperty$Ex(Lit75, Lit321, "https://teachablemachine.withgoogle.com/models/gPSivgcPY/", Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit75, Lit322, runtime.lookupInCurrentFormEnvironment(Lit55), Lit323);
    }

    public Object TeachableMachineImageClassifier1$GotClassification(Object $result) {
        Object obj;
        Object $result2 = runtime.sanitizeComponentData($result);
        runtime.setThisForm();
        runtime.addGlobalVarToCurrentFormEnvironment(Lit9, Component.TYPEFACE_DEFAULT);
        runtime.addGlobalVarToCurrentFormEnvironment(Lit10, "");
        Procedure proc = proc$Fn129;
        ModuleMethod moduleMethod = proc$Fn129;
        if ($result2 instanceof Package) {
            obj = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit331), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj = $result2;
        }
        runtime.yailForEach(moduleMethod, obj);
        SimpleSymbol simpleSymbol = Lit151;
        SimpleSymbol simpleSymbol2 = Lit13;
        ModuleMethod moduleMethod2 = strings.string$Mnappend;
        Pair list1 = LList.list1("Most Probable Soil Type: ");
        LList.chain1(LList.chain4(list1, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit10, runtime.$Stthe$Mnnull$Mnvalue$St), " (", runtime.callYailPrimitive(runtime.yail$Mnround, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit332, "round"), "%"), ")");
        runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, runtime.callYailPrimitive(moduleMethod2, list1, Lit333, "join"), Lit36);
        runtime.setAndCoerceProperty$Ex(Lit151, Lit152, Lit334, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit123, Lit23, Boolean.FALSE, Lit24);
    }

    public static Object lambda130proc(Object $item) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        ModuleMethod moduleMethod = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($item instanceof Package) {
            obj = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit325), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj = $item;
        }
        Object $key = runtime.callYailPrimitive(moduleMethod, LList.list2(obj, Lit72), Lit326, "select list item");
        ModuleMethod moduleMethod2 = runtime.yail$Mnlist$Mnget$Mnitem;
        if ($item instanceof Package) {
            obj2 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit325), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj2 = $item;
        }
        Object $value = runtime.callYailPrimitive(moduleMethod2, LList.list2(obj2, Lit111), Lit327, "select list item");
        NumberCompare numberCompare = Scheme.numGrt;
        if ($value instanceof Package) {
            obj3 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit328), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj3 = $value;
        }
        if (runtime.callYailPrimitive(numberCompare, LList.list2(obj3, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit329, ">") == Boolean.FALSE) {
            return Values.empty;
        }
        SimpleSymbol simpleSymbol = Lit9;
        if ($value instanceof Package) {
            obj4 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit328), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj4 = $value;
        }
        runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol, obj4);
        SimpleSymbol simpleSymbol2 = Lit10;
        if ($key instanceof Package) {
            obj5 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit330), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj5 = $key;
        }
        return runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol2, obj5);
    }

    static Object lambda131() {
        runtime.setAndCoerceProperty$Ex(Lit295, Lit338, runtime.textDeobfuscate("µ©ê¦¿Ò¸¼µÌª´¨Ï¼Ò¸¡Ì¯kCU~Shl00\"XaeP]Mp^,yo6sGs_ULaqjq`}p}bDTVC[`|\u000fg\u0015pBIQg`\u000f`\u001fIzbtuXRK\u001b\n\t9\u0013>\u0019x\r\b;\"\u0003/\u001e,*a!\u0002~\u0012\f{&7\u00116\u0019b\u0010\"\u0016\u001b9;\u0018\u0016\u0017-C\u0019\t\u001c;\u0013#\u0014\u000b(U\u0006\u001b \r))\u001f\f\u0002%&%", "baewmaw"), Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit295, Lit339, "2Yf5keLjW1SXPWru55hsZGysg7pn9qUr7arAVALtgNeh7auPxYHrPohTCqobKtx4MrzTwXkuAuZtHtW2QKVSjqaMXa1RbsNAo6neWaHe", Lit36);
    }

    static Object lambda132() {
        runtime.setAndCoerceProperty$Ex(Lit295, Lit338, runtime.textDeobfuscate("µ©ê¦¿Ò¸¼µÌª´¨Ï¼Ò¸¡Ì¯kCU~Shl00\"XaeP]Mp^,yo6sGs_ULaqjq`}p}bDTVC[`|\u000fg\u0015pBIQg`\u000f`\u001fIzbtuXRK\u001b\n\t9\u0013>\u0019x\r\b;\"\u0003/\u001e,*a!\u0002~\u0012\f{&7\u00116\u0019b\u0010\"\u0016\u001b9;\u0018\u0016\u0017-C\u0019\t\u001c;\u0013#\u0014\u000b(U\u0006\u001b \r))\u001f\f\u0002%&%", "baewmaw"), Lit36);
        return runtime.setAndCoerceProperty$Ex(Lit295, Lit339, "2Yf5keLjW1SXPWru55hsZGysg7pn9qUr7arAVALtgNeh7auPxYHrPohTCqobKtx4MrzTwXkuAuZtHtW2QKVSjqaMXa1RbsNAo6neWaHe", Lit36);
    }

    public Object ChatBot1$GotResponse(Object $responseText) {
        Object obj;
        Object $responseText2 = runtime.sanitizeComponentData($responseText);
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit279, Lit23, Boolean.FALSE, Lit24);
        SimpleSymbol simpleSymbol = Lit341;
        SimpleSymbol simpleSymbol2 = Lit342;
        if ($responseText2 instanceof Package) {
            obj = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit343), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj = $responseText2;
        }
        runtime.callComponentMethod(simpleSymbol, simpleSymbol2, LList.list2("chatResponse", obj), Lit344);
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Results"), Lit345, "open another screen");
    }

    public Object LocationSensor1$LocationChanged(Object $latitude, Object $longitude, Object $altitude, Object $speed) {
        Object obj;
        Object obj2;
        Object $latitude2 = runtime.sanitizeComponentData($latitude);
        Object $longitude2 = runtime.sanitizeComponentData($longitude);
        runtime.sanitizeComponentData($altitude);
        runtime.sanitizeComponentData($speed);
        runtime.setThisForm();
        SimpleSymbol simpleSymbol = Lit4;
        if ($latitude2 instanceof Package) {
            obj = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit350), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj = $latitude2;
        }
        runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol, obj);
        SimpleSymbol simpleSymbol2 = Lit3;
        if ($longitude2 instanceof Package) {
            obj2 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit351), " is not bound in the current context"), "Unbound Variable");
        } else {
            obj2 = $longitude2;
        }
        return runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol2, obj2);
    }

    static Object lambda133() {
        runtime.setAndCoerceProperty$Ex(Lit300, Lit67, Lit359, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit300, Lit360, Lit177, Lit33);
    }

    static Object lambda134() {
        runtime.setAndCoerceProperty$Ex(Lit300, Lit67, Lit359, Lit33);
        return runtime.setAndCoerceProperty$Ex(Lit300, Lit360, Lit177, Lit33);
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
        Crop = this;
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
