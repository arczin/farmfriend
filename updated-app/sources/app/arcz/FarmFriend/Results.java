package appinventor.ai_arnav_chhajed_000.FarmFriend;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.File;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Sharing;
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

/* compiled from: Results.yail */
public class Results extends Form implements Runnable {
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Results").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("BackgroundImage").readResolve());
    static final FString Lit100 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit101 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement8").readResolve());
    static final IntNum Lit103 = IntNum.make(1258291199);
    static final FString Lit104 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit105 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit106 = IntNum.make(20);
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("HTMLFormat").readResolve());
    static final IntNum Lit108 = IntNum.make(-1090);
    static final FString Lit109 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("PrimaryColor").readResolve());
    static final FString Lit110 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement9").readResolve());
    static final FString Lit112 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit113 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final FString Lit114 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final FString Lit115 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit116 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit117 = new FString("com.google.appinventor.components.runtime.File");
    static final FString Lit118 = new FString("com.google.appinventor.components.runtime.File");
    static final FString Lit119 = new FString("com.google.appinventor.components.runtime.Sharing");
    static final IntNum Lit12;
    static final FString Lit120 = new FString("com.google.appinventor.components.runtime.Sharing");
    static final SimpleSymbol Lit121 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit124 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit125 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit126 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit127 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit128 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit129 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("PrimaryColorDark").readResolve());
    static final SimpleSymbol Lit130 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit131 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit132 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit133 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit134 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final IntNum Lit14;
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("ScreenOrientation").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("Scrollable").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("Label1").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol(TinyDB.DEFAULT_NAMESPACE).readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("GetValue").readResolve());
    static final PairWithPosition Lit24 = PairWithPosition.make(Lit9, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("any").readResolve(), LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Results.yail", 98478), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Results.yail", 98472);
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("Sound1").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("Source").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("Results$Initialize").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final FString Lit29 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement2").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit32 = IntNum.make(-2);
    static final FString Lit33 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit34 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("backbutton").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final IntNum Lit37 = IntNum.make(1627389952);
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final IntNum Lit39 = IntNum.make(15);
    static final IntNum Lit4;
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("Shape").readResolve());
    static final IntNum Lit41 = IntNum.make(1);
    static final FString Lit42 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("Play").readResolve());
    static final PairWithPosition Lit44 = PairWithPosition.make(Lit9, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Results.yail", 188553);
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("backbutton$Click").readResolve());
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("backbutton$GotFocus").readResolve());
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("GotFocus").readResolve());
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("backbutton$LostFocus").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("number").readResolve());
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("LostFocus").readResolve());
    static final FString Lit51 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final FString Lit54 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit55 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement3").readResolve());
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final IntNum Lit58 = IntNum.make(3);
    static final FString Lit59 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("ActionBar").readResolve());
    static final FString Lit60 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("Button1").readResolve());
    static final IntNum Lit62;
    static final IntNum Lit63 = IntNum.make(18);
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("FontTypeface").readResolve());
    static final IntNum Lit65 = IntNum.make(-1090);
    static final FString Lit66 = new FString("com.google.appinventor.components.runtime.Button");
    static final FString Lit67 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement6").readResolve());
    static final FString Lit69 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve());
    static final FString Lit70 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve());
    static final FString Lit72 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit73 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("saveButton").readResolve());
    static final IntNum Lit75;
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("FontBold").readResolve());
    static final FString Lit77 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit78 = ((SimpleSymbol) new SimpleSymbol("saveButton$GotFocus").readResolve());
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol("saveButton$LostFocus").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("File1").readResolve());
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("SaveFile").readResolve());
    static final PairWithPosition Lit82 = PairWithPosition.make(Lit9, PairWithPosition.make(Lit9, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Results.yail", 499913), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Results.yail", 499907);
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol("saveButton$Click").readResolve());
    static final FString Lit84 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement5").readResolve());
    static final FString Lit86 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit87 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit88 = ((SimpleSymbol) new SimpleSymbol("Button2").readResolve());
    static final IntNum Lit89;
    static final SimpleSymbol Lit9;
    static final FString Lit90 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol("Button2$GotFocus").readResolve());
    static final SimpleSymbol Lit92 = ((SimpleSymbol) new SimpleSymbol("Button2$LostFocus").readResolve());
    static final PairWithPosition Lit93 = PairWithPosition.make(Lit9, PairWithPosition.make(Lit9, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Results.yail", 630984), "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Results.yail", 630978);
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("Sharing1").readResolve());
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol("ShareFile").readResolve());
    static final PairWithPosition Lit96;
    static final SimpleSymbol Lit97 = ((SimpleSymbol) new SimpleSymbol("Button2$Click").readResolve());
    static final FString Lit98 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement7").readResolve());
    public static Results Results;
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
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public Button Button1;
    public Button Button2;
    public final ModuleMethod Button2$Click;
    public final ModuleMethod Button2$GotFocus;
    public final ModuleMethod Button2$LostFocus;
    public File File1;
    public HorizontalArrangement HorizontalArrangement1;
    public Label Label1;
    public final ModuleMethod Results$Initialize;
    public Sharing Sharing1;
    public Sound Sound1;
    public TinyDB TinyDB1;
    public VerticalArrangement VerticalArrangement1;
    public VerticalArrangement VerticalArrangement2;
    public VerticalArrangement VerticalArrangement3;
    public VerticalArrangement VerticalArrangement5;
    public VerticalArrangement VerticalArrangement6;
    public VerticalArrangement VerticalArrangement7;
    public VerticalArrangement VerticalArrangement8;
    public VerticalArrangement VerticalArrangement9;
    public final ModuleMethod add$Mnto$Mncomponents;
    public final ModuleMethod add$Mnto$Mnevents;
    public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
    public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
    public final ModuleMethod android$Mnlog$Mnform;
    public Button backbutton;
    public final ModuleMethod backbutton$Click;
    public final ModuleMethod backbutton$GotFocus;
    public final ModuleMethod backbutton$LostFocus;
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
    public Button saveButton;
    public final ModuleMethod saveButton$Click;
    public final ModuleMethod saveButton$GotFocus;
    public final ModuleMethod saveButton$LostFocus;
    public final ModuleMethod send$Mnerror;

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit9 = simpleSymbol;
        Lit96 = PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/Results.yail", 631091);
        int[] iArr = new int[2];
        iArr[0] = -1;
        Lit89 = IntNum.make(iArr);
        int[] iArr2 = new int[2];
        iArr2[0] = -1;
        Lit75 = IntNum.make(iArr2);
        int[] iArr3 = new int[2];
        iArr3[0] = -845;
        Lit62 = IntNum.make(iArr3);
        int[] iArr4 = new int[2];
        iArr4[0] = -9592721;
        Lit14 = IntNum.make(iArr4);
        int[] iArr5 = new int[2];
        iArr5[0] = -8271996;
        Lit12 = IntNum.make(iArr5);
        int[] iArr6 = new int[2];
        iArr6[0] = -18654;
        Lit4 = IntNum.make(iArr6);
    }

    public Results() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        frame2.$main = this;
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit121, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit122, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit123, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit124, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit125, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit126, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit127, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit128, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit129, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit130, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit131, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit132, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit133, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit134, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, (Object) null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime15798620743098228844.scm:639");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, (Object) null, 0);
        this.Results$Initialize = new ModuleMethod(frame2, 21, Lit27, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 22, (Object) null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 23, (Object) null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 24, (Object) null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 25, (Object) null, 0);
        this.backbutton$Click = new ModuleMethod(frame2, 26, Lit45, 0);
        this.backbutton$GotFocus = new ModuleMethod(frame2, 27, Lit47, 0);
        this.backbutton$LostFocus = new ModuleMethod(frame2, 28, Lit49, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 29, (Object) null, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 30, (Object) null, 0);
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
        this.saveButton$GotFocus = new ModuleMethod(frame2, 41, Lit78, 0);
        this.saveButton$LostFocus = new ModuleMethod(frame2, 42, Lit79, 0);
        this.saveButton$Click = new ModuleMethod(frame2, 43, Lit83, 0);
        lambda$Fn19 = new ModuleMethod(frame2, 44, (Object) null, 0);
        lambda$Fn20 = new ModuleMethod(frame2, 45, (Object) null, 0);
        lambda$Fn21 = new ModuleMethod(frame2, 46, (Object) null, 0);
        lambda$Fn22 = new ModuleMethod(frame2, 47, (Object) null, 0);
        this.Button2$GotFocus = new ModuleMethod(frame2, 48, Lit91, 0);
        this.Button2$LostFocus = new ModuleMethod(frame2, 49, Lit92, 0);
        this.Button2$Click = new ModuleMethod(frame2, 50, Lit97, 0);
        lambda$Fn23 = new ModuleMethod(frame2, 51, (Object) null, 0);
        lambda$Fn24 = new ModuleMethod(frame2, 52, (Object) null, 0);
        lambda$Fn25 = new ModuleMethod(frame2, 53, (Object) null, 0);
        lambda$Fn26 = new ModuleMethod(frame2, 54, (Object) null, 0);
        lambda$Fn27 = new ModuleMethod(frame2, 55, (Object) null, 0);
        lambda$Fn28 = new ModuleMethod(frame2, 56, (Object) null, 0);
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
        Results = null;
        this.form$Mnname$Mnsymbol = Lit0;
        this.events$Mnto$Mnregister = LList.Empty;
        this.components$Mnto$Mncreate = LList.Empty;
        this.global$Mnvars$Mnto$Mncreate = LList.Empty;
        this.form$Mndo$Mnafter$Mncreation = LList.Empty;
        runtime.$instance.run();
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit0, Lit3, Lit4, Lit5);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit6, Boolean.TRUE, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit8, "gfdhgdghfu", Lit9);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit10, "Do-sharpEdit.png", Lit9);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit11, Lit12, Lit5);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit13, Lit14, Lit5);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "unspecified", Lit9);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Boolean.TRUE, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit17, Boolean.TRUE, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit18, "Responsive", Lit9);
            Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit19, "Results", Lit9), $result);
        } else {
            addToFormDoAfterCreation(new Promise(lambda$Fn2));
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit27, this.Results$Initialize);
        } else {
            addToFormEnvironment(Lit27, this.Results$Initialize);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Results", "Initialize");
        } else {
            addToEvents(Lit0, Lit28);
        }
        this.VerticalArrangement2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit29, Lit30, lambda$Fn3), $result);
        } else {
            addToComponents(Lit0, Lit33, Lit30, lambda$Fn4);
        }
        this.backbutton = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit30, Lit34, Lit35, lambda$Fn5), $result);
        } else {
            addToComponents(Lit30, Lit42, Lit35, lambda$Fn6);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit45, this.backbutton$Click);
        } else {
            addToFormEnvironment(Lit45, this.backbutton$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "Click");
        } else {
            addToEvents(Lit35, Lit46);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit47, this.backbutton$GotFocus);
        } else {
            addToFormEnvironment(Lit47, this.backbutton$GotFocus);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "GotFocus");
        } else {
            addToEvents(Lit35, Lit48);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit49, this.backbutton$LostFocus);
        } else {
            addToFormEnvironment(Lit49, this.backbutton$LostFocus);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "LostFocus");
        } else {
            addToEvents(Lit35, Lit50);
        }
        this.VerticalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit51, Lit52, lambda$Fn7), $result);
        } else {
            addToComponents(Lit0, Lit54, Lit52, lambda$Fn8);
        }
        this.VerticalArrangement3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit55, Lit56, lambda$Fn9), $result);
        } else {
            addToComponents(Lit0, Lit59, Lit56, lambda$Fn10);
        }
        this.Button1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit56, Lit60, Lit61, lambda$Fn11), $result);
        } else {
            addToComponents(Lit56, Lit66, Lit61, lambda$Fn12);
        }
        this.VerticalArrangement6 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit67, Lit68, lambda$Fn13), $result);
        } else {
            addToComponents(Lit0, Lit69, Lit68, lambda$Fn14);
        }
        this.HorizontalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit70, Lit71, lambda$Fn15), $result);
        } else {
            addToComponents(Lit0, Lit72, Lit71, lambda$Fn16);
        }
        this.saveButton = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit71, Lit73, Lit74, lambda$Fn17), $result);
        } else {
            addToComponents(Lit71, Lit77, Lit74, lambda$Fn18);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit78, this.saveButton$GotFocus);
        } else {
            addToFormEnvironment(Lit78, this.saveButton$GotFocus);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "saveButton", "GotFocus");
        } else {
            addToEvents(Lit74, Lit48);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit79, this.saveButton$LostFocus);
        } else {
            addToFormEnvironment(Lit79, this.saveButton$LostFocus);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "saveButton", "LostFocus");
        } else {
            addToEvents(Lit74, Lit50);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit83, this.saveButton$Click);
        } else {
            addToFormEnvironment(Lit83, this.saveButton$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "saveButton", "Click");
        } else {
            addToEvents(Lit74, Lit46);
        }
        this.VerticalArrangement5 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit71, Lit84, Lit85, lambda$Fn19), $result);
        } else {
            addToComponents(Lit71, Lit86, Lit85, lambda$Fn20);
        }
        this.Button2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit71, Lit87, Lit88, lambda$Fn21), $result);
        } else {
            addToComponents(Lit71, Lit90, Lit88, lambda$Fn22);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit91, this.Button2$GotFocus);
        } else {
            addToFormEnvironment(Lit91, this.Button2$GotFocus);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "GotFocus");
        } else {
            addToEvents(Lit88, Lit48);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit92, this.Button2$LostFocus);
        } else {
            addToFormEnvironment(Lit92, this.Button2$LostFocus);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "LostFocus");
        } else {
            addToEvents(Lit88, Lit50);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit97, this.Button2$Click);
        } else {
            addToFormEnvironment(Lit97, this.Button2$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "Click");
        } else {
            addToEvents(Lit88, Lit46);
        }
        this.VerticalArrangement7 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit98, Lit99, lambda$Fn23), $result);
        } else {
            addToComponents(Lit0, Lit100, Lit99, lambda$Fn24);
        }
        this.VerticalArrangement8 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit101, Lit102, lambda$Fn25), $result);
        } else {
            addToComponents(Lit0, Lit104, Lit102, lambda$Fn26);
        }
        this.Label1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit102, Lit105, Lit20, lambda$Fn27), $result);
        } else {
            addToComponents(Lit102, Lit109, Lit20, lambda$Fn28);
        }
        this.VerticalArrangement9 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit110, Lit111, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit112, Lit111, Boolean.FALSE);
        }
        this.TinyDB1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit113, Lit22, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit114, Lit22, Boolean.FALSE);
        }
        this.Sound1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit115, Lit25, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit116, Lit25, Boolean.FALSE);
        }
        this.File1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit117, Lit80, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit118, Lit80, Boolean.FALSE);
        }
        this.Sharing1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit119, Lit94, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit120, Lit94, Boolean.FALSE);
        }
        runtime.initRuntime();
    }

    static Object lambda3() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit3, Lit4, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit6, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit8, "gfdhgdghfu", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit10, "Do-sharpEdit.png", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit11, Lit12, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit13, Lit14, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "unspecified", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit17, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit18, "Responsive", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit19, "Results", Lit9);
    }

    public Object Results$Initialize() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit20, Lit21, runtime.callComponentMethod(Lit22, Lit23, LList.list2("chatResponse", "No response available, please try again."), Lit24), Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit25, Lit26, "ClickSoundEffect(1).mp3", Lit9);
    }

    static Object lambda4() {
        return runtime.setAndCoerceProperty$Ex(Lit30, Lit31, Lit32, Lit5);
    }

    static Object lambda5() {
        return runtime.setAndCoerceProperty$Ex(Lit30, Lit31, Lit32, Lit5);
    }

    static Object lambda6() {
        runtime.setAndCoerceProperty$Ex(Lit35, Lit36, Lit37, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit35, Lit38, Lit39, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit35, Lit40, Lit41, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit35, Lit21, " ⬅️", Lit9);
    }

    static Object lambda7() {
        runtime.setAndCoerceProperty$Ex(Lit35, Lit36, Lit37, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit35, Lit38, Lit39, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit35, Lit40, Lit41, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit35, Lit21, " ⬅️", Lit9);
    }

    public Object backbutton$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit25, Lit43, LList.Empty, LList.Empty);
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Crop"), Lit44, "open another screen");
    }

    public Object backbutton$GotFocus() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit35, Lit38, "13", Lit5);
    }

    public Object backbutton$LostFocus() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit35, Lit38, "15", Lit5);
    }

    static Object lambda8() {
        return runtime.setAndCoerceProperty$Ex(Lit52, Lit53, Lit39, Lit5);
    }

    static Object lambda9() {
        return runtime.setAndCoerceProperty$Ex(Lit52, Lit53, Lit39, Lit5);
    }

    static Object lambda10() {
        runtime.setAndCoerceProperty$Ex(Lit56, Lit57, Lit58, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit56, Lit31, Lit32, Lit5);
    }

    static Object lambda11() {
        runtime.setAndCoerceProperty$Ex(Lit56, Lit57, Lit58, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit56, Lit31, Lit32, Lit5);
    }

    static Object lambda12() {
        runtime.setAndCoerceProperty$Ex(Lit61, Lit36, Lit62, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit38, Lit63, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit64, "Inter_18pt-Bold.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit40, Lit41, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit21, "👉 📊 Here's the Analysis and Suggestion!", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit61, Lit31, Lit65, Lit5);
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit61, Lit36, Lit62, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit38, Lit63, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit64, "Inter_18pt-Bold.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit40, Lit41, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit61, Lit21, "👉 📊 Here's the Analysis and Suggestion!", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit61, Lit31, Lit65, Lit5);
    }

    static Object lambda14() {
        return runtime.setAndCoerceProperty$Ex(Lit68, Lit53, Lit39, Lit5);
    }

    static Object lambda15() {
        return runtime.setAndCoerceProperty$Ex(Lit68, Lit53, Lit39, Lit5);
    }

    static Object lambda16() {
        runtime.setAndCoerceProperty$Ex(Lit71, Lit57, Lit58, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit71, Lit31, Lit32, Lit5);
    }

    static Object lambda17() {
        runtime.setAndCoerceProperty$Ex(Lit71, Lit57, Lit58, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit71, Lit31, Lit32, Lit5);
    }

    static Object lambda18() {
        runtime.setAndCoerceProperty$Ex(Lit74, Lit36, Lit75, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit76, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit38, Lit39, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit64, Component.TYPEFACE_MONOSPACE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit40, Lit41, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit21, "💾 Save", Lit9);
    }

    static Object lambda19() {
        runtime.setAndCoerceProperty$Ex(Lit74, Lit36, Lit75, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit76, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit38, Lit39, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit64, Component.TYPEFACE_MONOSPACE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit74, Lit40, Lit41, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit21, "💾 Save", Lit9);
    }

    public Object saveButton$GotFocus() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit38, "13", Lit5);
    }

    public Object saveButton$LostFocus() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit38, "15", Lit5);
    }

    public Object saveButton$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit25, Lit43, LList.Empty, LList.Empty);
        runtime.callComponentMethod(Lit80, Lit81, LList.list2(runtime.get$Mnproperty.apply2(Lit20, Lit21), "/AgriculturalSuggestionResult.txt"), Lit82);
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit21, "Saved ✅", Lit9);
    }

    static Object lambda20() {
        runtime.setAndCoerceProperty$Ex(Lit85, Lit57, Lit58, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit85, Lit31, Lit39, Lit5);
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit85, Lit57, Lit58, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit85, Lit31, Lit39, Lit5);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit88, Lit36, Lit89, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit76, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit38, Lit39, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit64, Component.TYPEFACE_MONOSPACE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit40, Lit41, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit88, Lit21, "📤 Share", Lit9);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit88, Lit36, Lit89, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit76, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit38, Lit39, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit64, Component.TYPEFACE_MONOSPACE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit88, Lit40, Lit41, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit88, Lit21, "📤 Share", Lit9);
    }

    public Object Button2$GotFocus() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit88, Lit38, "13", Lit5);
    }

    public Object Button2$LostFocus() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit88, Lit38, "15", Lit5);
    }

    public Object Button2$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit25, Lit43, LList.Empty, LList.Empty);
        runtime.callComponentMethod(Lit80, Lit81, LList.list2(runtime.get$Mnproperty.apply2(Lit20, Lit21), "AgriculturalSuggestionResult.txt"), Lit93);
        return runtime.callComponentMethod(Lit94, Lit95, LList.list1("AgriculturalSuggestionResult.txt"), Lit96);
    }

    static Object lambda24() {
        return runtime.setAndCoerceProperty$Ex(Lit99, Lit53, Lit39, Lit5);
    }

    static Object lambda25() {
        return runtime.setAndCoerceProperty$Ex(Lit99, Lit53, Lit39, Lit5);
    }

    static Object lambda26() {
        runtime.setAndCoerceProperty$Ex(Lit102, Lit57, Lit58, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit102, Lit36, Lit103, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit102, Lit31, Lit32, Lit5);
    }

    static Object lambda27() {
        runtime.setAndCoerceProperty$Ex(Lit102, Lit57, Lit58, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit102, Lit36, Lit103, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit102, Lit31, Lit32, Lit5);
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit20, Lit38, Lit106, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit20, Lit64, "Inter_18pt-Regular.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit20, Lit107, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit20, Lit21, "Result", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit20, Lit31, Lit108, Lit5);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit20, Lit38, Lit106, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit20, Lit64, "Inter_18pt-Regular.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit20, Lit107, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit20, Lit21, "Result", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit20, Lit31, Lit108, Lit5);
    }

    /* compiled from: Results.yail */
    public class frame extends ModuleBody {
        Results $main;

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 2:
                    if (!(obj instanceof Results)) {
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
                    if (!(obj instanceof Results)) {
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
                    if (!(obj instanceof Results)) {
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
                    if (!(obj instanceof Results)) {
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
                    Results results = this.$main;
                    try {
                        Component component = (Component) obj;
                        try {
                            String str = (String) obj2;
                            try {
                                if (obj3 == Boolean.FALSE) {
                                    z = false;
                                }
                                try {
                                    results.dispatchGenericEvent(component, str, z, (Object[]) obj4);
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
                    return Results.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return Results.lambda3();
                case 21:
                    return this.$main.Results$Initialize();
                case 22:
                    return Results.lambda4();
                case 23:
                    return Results.lambda5();
                case 24:
                    return Results.lambda6();
                case 25:
                    return Results.lambda7();
                case 26:
                    return this.$main.backbutton$Click();
                case 27:
                    return this.$main.backbutton$GotFocus();
                case 28:
                    return this.$main.backbutton$LostFocus();
                case 29:
                    return Results.lambda8();
                case 30:
                    return Results.lambda9();
                case 31:
                    return Results.lambda10();
                case 32:
                    return Results.lambda11();
                case 33:
                    return Results.lambda12();
                case 34:
                    return Results.lambda13();
                case 35:
                    return Results.lambda14();
                case 36:
                    return Results.lambda15();
                case 37:
                    return Results.lambda16();
                case 38:
                    return Results.lambda17();
                case 39:
                    return Results.lambda18();
                case 40:
                    return Results.lambda19();
                case 41:
                    return this.$main.saveButton$GotFocus();
                case 42:
                    return this.$main.saveButton$LostFocus();
                case 43:
                    return this.$main.saveButton$Click();
                case 44:
                    return Results.lambda20();
                case 45:
                    return Results.lambda21();
                case 46:
                    return Results.lambda22();
                case 47:
                    return Results.lambda23();
                case 48:
                    return this.$main.Button2$GotFocus();
                case 49:
                    return this.$main.Button2$LostFocus();
                case 50:
                    return this.$main.Button2$Click();
                case 51:
                    return Results.lambda24();
                case 52:
                    return Results.lambda25();
                case 53:
                    return Results.lambda26();
                case 54:
                    return Results.lambda27();
                case 55:
                    return Results.lambda28();
                case 56:
                    return Results.lambda29();
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
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
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
        Results = this;
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
