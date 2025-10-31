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
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Sound;
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

/* compiled from: About.yail */
public class About extends Form implements Runnable {
    public static About About;
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("About").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("BackgroundImage").readResolve());
    static final FString Lit100 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit101 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement11").readResolve());
    static final FString Lit102 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit103 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("Button3").readResolve());
    static final IntNum Lit105;
    static final FString Lit106 = new FString("com.google.appinventor.components.runtime.Button");
    static final FString Lit107 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit108 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement12").readResolve());
    static final FString Lit109 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("PrimaryColor").readResolve());
    static final FString Lit110 = new FString("com.google.appinventor.components.runtime.Sound");
    static final FString Lit111 = new FString("com.google.appinventor.components.runtime.Sound");
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit113 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit116 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit117 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit119 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final IntNum Lit12;
    static final SimpleSymbol Lit120 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit121 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit124 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit125 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("PrimaryColorDark").readResolve());
    static final IntNum Lit14;
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("ScreenOrientation").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("Scrollable").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("Sound1").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("Source").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("About$Initialize").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final FString Lit24 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement9").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit27 = IntNum.make(-2);
    static final FString Lit28 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit29 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("backbutton").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final IntNum Lit32 = IntNum.make(1174405120);
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("Shape").readResolve());
    static final IntNum Lit34 = IntNum.make(1);
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final FString Lit36 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("Play").readResolve());
    static final PairWithPosition Lit38;
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("backbutton$Click").readResolve());
    static final IntNum Lit4;
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("backbutton$TouchDown").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("TouchDown").readResolve());
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("backbutton$TouchUp").readResolve());
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("TouchUp").readResolve());
    static final FString Lit46 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve());
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final IntNum Lit49 = IntNum.make(15);
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("number").readResolve());
    static final FString Lit50 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit51 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement3").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final IntNum Lit54 = IntNum.make(3);
    static final FString Lit55 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit56 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("Button1").readResolve());
    static final IntNum Lit58;
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("FontBold").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("ActionBar").readResolve());
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("FontTypeface").readResolve());
    static final IntNum Lit61 = IntNum.make(-1090);
    static final FString Lit62 = new FString("com.google.appinventor.components.runtime.Button");
    static final FString Lit63 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement8").readResolve());
    static final FString Lit65 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit66 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement2").readResolve());
    static final IntNum Lit68 = IntNum.make(1979711487);
    static final FString Lit69 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve());
    static final FString Lit70 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("Label1").readResolve());
    static final IntNum Lit72 = IntNum.make(-1090);
    static final FString Lit73 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit74 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement4").readResolve());
    static final FString Lit76 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit77 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit78 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement5").readResolve());
    static final FString Lit79 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final FString Lit80 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("Button2").readResolve());
    static final IntNum Lit82;
    static final IntNum Lit83 = IntNum.make(17);
    static final IntNum Lit84 = IntNum.make(-1090);
    static final FString Lit85 = new FString("com.google.appinventor.components.runtime.Button");
    static final FString Lit86 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit87 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement7").readResolve());
    static final FString Lit88 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit89 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit9;
    static final SimpleSymbol Lit90 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement6").readResolve());
    static final IntNum Lit91 = IntNum.make(1979711487);
    static final FString Lit92 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit93 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("Label2").readResolve());
    static final IntNum Lit95 = IntNum.make(-1090);
    static final FString Lit96 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit97 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit98 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement10").readResolve());
    static final FString Lit99 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
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
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public final ModuleMethod About$Initialize;
    public Button Button1;
    public Button Button2;
    public Button Button3;
    public Label Label1;
    public Label Label2;
    public Sound Sound1;
    public VerticalArrangement VerticalArrangement1;
    public VerticalArrangement VerticalArrangement10;
    public VerticalArrangement VerticalArrangement11;
    public VerticalArrangement VerticalArrangement12;
    public VerticalArrangement VerticalArrangement2;
    public VerticalArrangement VerticalArrangement3;
    public VerticalArrangement VerticalArrangement4;
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
        iArr[0] = -845;
        Lit105 = IntNum.make(iArr);
        int[] iArr2 = new int[2];
        iArr2[0] = -845;
        Lit82 = IntNum.make(iArr2);
        int[] iArr3 = new int[2];
        iArr3[0] = -845;
        Lit58 = IntNum.make(iArr3);
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit9 = simpleSymbol;
        Lit38 = PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1760513159282_716444792269161472-0/youngandroidproject/../src/appinventor/ai_arnav_chhajed_000/FarmFriend/About.yail", 180364);
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

    public About() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        frame2.$main = this;
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit112, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit113, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit114, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit115, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit116, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit117, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit118, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit119, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit120, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit121, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit122, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit123, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit124, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit125, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, (Object) null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime15798620743098228844.scm:639");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, (Object) null, 0);
        this.About$Initialize = new ModuleMethod(frame2, 21, Lit22, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 22, (Object) null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 23, (Object) null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 24, (Object) null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 25, (Object) null, 0);
        this.backbutton$Click = new ModuleMethod(frame2, 26, Lit39, 0);
        this.backbutton$TouchDown = new ModuleMethod(frame2, 27, Lit42, 0);
        this.backbutton$TouchUp = new ModuleMethod(frame2, 28, Lit44, 0);
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
        lambda$Fn31 = new ModuleMethod(frame2, 53, (Object) null, 0);
        lambda$Fn32 = new ModuleMethod(frame2, 54, (Object) null, 0);
        lambda$Fn33 = new ModuleMethod(frame2, 55, (Object) null, 0);
        lambda$Fn34 = new ModuleMethod(frame2, 56, (Object) null, 0);
        lambda$Fn35 = new ModuleMethod(frame2, 57, (Object) null, 0);
        lambda$Fn36 = new ModuleMethod(frame2, 58, (Object) null, 0);
        lambda$Fn37 = new ModuleMethod(frame2, 59, (Object) null, 0);
        lambda$Fn38 = new ModuleMethod(frame2, 60, (Object) null, 0);
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
        About = null;
        this.form$Mnname$Mnsymbol = Lit0;
        this.events$Mnto$Mnregister = LList.Empty;
        this.components$Mnto$Mncreate = LList.Empty;
        this.global$Mnvars$Mnto$Mncreate = LList.Empty;
        this.form$Mndo$Mnafter$Mncreation = LList.Empty;
        runtime.$instance.run();
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit0, Lit3, Lit4, Lit5);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit6, Boolean.TRUE, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit8, "FarmFriend", Lit9);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit10, "Do-sharpEdit.png", Lit9);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit11, Lit12, Lit5);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit13, Lit14, Lit5);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "unspecified", Lit9);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Boolean.TRUE, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit17, Boolean.TRUE, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit18, "Responsive", Lit9);
            Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit19, "About", Lit9), $result);
        } else {
            addToFormDoAfterCreation(new Promise(lambda$Fn2));
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit22, this.About$Initialize);
        } else {
            addToFormEnvironment(Lit22, this.About$Initialize);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "About", "Initialize");
        } else {
            addToEvents(Lit0, Lit23);
        }
        this.VerticalArrangement9 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit24, Lit25, lambda$Fn3), $result);
        } else {
            addToComponents(Lit0, Lit28, Lit25, lambda$Fn4);
        }
        this.backbutton = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit25, Lit29, Lit30, lambda$Fn5), $result);
        } else {
            addToComponents(Lit25, Lit36, Lit30, lambda$Fn6);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit39, this.backbutton$Click);
        } else {
            addToFormEnvironment(Lit39, this.backbutton$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "Click");
        } else {
            addToEvents(Lit30, Lit40);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit42, this.backbutton$TouchDown);
        } else {
            addToFormEnvironment(Lit42, this.backbutton$TouchDown);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "TouchDown");
        } else {
            addToEvents(Lit30, Lit43);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit44, this.backbutton$TouchUp);
        } else {
            addToFormEnvironment(Lit44, this.backbutton$TouchUp);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "backbutton", "TouchUp");
        } else {
            addToEvents(Lit30, Lit45);
        }
        this.VerticalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit46, Lit47, lambda$Fn7), $result);
        } else {
            addToComponents(Lit0, Lit50, Lit47, lambda$Fn8);
        }
        this.VerticalArrangement3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit51, Lit52, lambda$Fn9), $result);
        } else {
            addToComponents(Lit0, Lit55, Lit52, lambda$Fn10);
        }
        this.Button1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit52, Lit56, Lit57, lambda$Fn11), $result);
        } else {
            addToComponents(Lit52, Lit62, Lit57, lambda$Fn12);
        }
        this.VerticalArrangement8 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit63, Lit64, lambda$Fn13), $result);
        } else {
            addToComponents(Lit0, Lit65, Lit64, lambda$Fn14);
        }
        this.VerticalArrangement2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit66, Lit67, lambda$Fn15), $result);
        } else {
            addToComponents(Lit0, Lit69, Lit67, lambda$Fn16);
        }
        this.Label1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit67, Lit70, Lit71, lambda$Fn17), $result);
        } else {
            addToComponents(Lit67, Lit73, Lit71, lambda$Fn18);
        }
        this.VerticalArrangement4 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit74, Lit75, lambda$Fn19), $result);
        } else {
            addToComponents(Lit0, Lit76, Lit75, lambda$Fn20);
        }
        this.VerticalArrangement5 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit77, Lit78, lambda$Fn21), $result);
        } else {
            addToComponents(Lit0, Lit79, Lit78, lambda$Fn22);
        }
        this.Button2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit78, Lit80, Lit81, lambda$Fn23), $result);
        } else {
            addToComponents(Lit78, Lit85, Lit81, lambda$Fn24);
        }
        this.VerticalArrangement7 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit86, Lit87, lambda$Fn25), $result);
        } else {
            addToComponents(Lit0, Lit88, Lit87, lambda$Fn26);
        }
        this.VerticalArrangement6 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit89, Lit90, lambda$Fn27), $result);
        } else {
            addToComponents(Lit0, Lit92, Lit90, lambda$Fn28);
        }
        this.Label2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit90, Lit93, Lit94, lambda$Fn29), $result);
        } else {
            addToComponents(Lit90, Lit96, Lit94, lambda$Fn30);
        }
        this.VerticalArrangement10 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit97, Lit98, lambda$Fn31), $result);
        } else {
            addToComponents(Lit0, Lit99, Lit98, lambda$Fn32);
        }
        this.VerticalArrangement11 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit100, Lit101, lambda$Fn33), $result);
        } else {
            addToComponents(Lit0, Lit102, Lit101, lambda$Fn34);
        }
        this.Button3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit101, Lit103, Lit104, lambda$Fn35), $result);
        } else {
            addToComponents(Lit101, Lit106, Lit104, lambda$Fn36);
        }
        this.VerticalArrangement12 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit107, Lit108, lambda$Fn37), $result);
        } else {
            addToComponents(Lit0, Lit109, Lit108, lambda$Fn38);
        }
        this.Sound1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit110, Lit20, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit111, Lit20, Boolean.FALSE);
        }
        runtime.initRuntime();
    }

    static Object lambda3() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit3, Lit4, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit6, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit8, "FarmFriend", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit10, "Do-sharpEdit.png", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit11, Lit12, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit13, Lit14, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "unspecified", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit17, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit18, "Responsive", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit19, "About", Lit9);
    }

    public Object About$Initialize() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit20, Lit21, "ClickSoundEffect(1).mp3", Lit9);
    }

    static Object lambda4() {
        return runtime.setAndCoerceProperty$Ex(Lit25, Lit26, Lit27, Lit5);
    }

    static Object lambda5() {
        return runtime.setAndCoerceProperty$Ex(Lit25, Lit26, Lit27, Lit5);
    }

    static Object lambda6() {
        runtime.setAndCoerceProperty$Ex(Lit30, Lit31, Lit32, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit30, Lit33, Lit34, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit30, Lit35, " ⬅️", Lit9);
    }

    static Object lambda7() {
        runtime.setAndCoerceProperty$Ex(Lit30, Lit31, Lit32, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit30, Lit33, Lit34, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit30, Lit35, " ⬅️", Lit9);
    }

    public Object backbutton$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit20, Lit37, LList.Empty, LList.Empty);
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit38, "open another screen");
    }

    public Object backbutton$TouchDown() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit30, Lit41, "13", Lit5);
    }

    public Object backbutton$TouchUp() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit30, Lit41, "15", Lit5);
    }

    static Object lambda8() {
        return runtime.setAndCoerceProperty$Ex(Lit47, Lit48, Lit49, Lit5);
    }

    static Object lambda9() {
        return runtime.setAndCoerceProperty$Ex(Lit47, Lit48, Lit49, Lit5);
    }

    static Object lambda10() {
        runtime.setAndCoerceProperty$Ex(Lit52, Lit53, Lit54, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit52, Lit26, Lit27, Lit5);
    }

    static Object lambda11() {
        runtime.setAndCoerceProperty$Ex(Lit52, Lit53, Lit54, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit52, Lit26, Lit27, Lit5);
    }

    static Object lambda12() {
        runtime.setAndCoerceProperty$Ex(Lit57, Lit31, Lit58, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit59, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit41, Lit49, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit60, "Inter_18pt-Bold.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit33, Lit34, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit35, "🌾 About This App", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit57, Lit26, Lit61, Lit5);
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit57, Lit31, Lit58, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit59, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit41, Lit49, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit60, "Inter_18pt-Bold.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit33, Lit34, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit57, Lit35, "🌾 About This App", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit57, Lit26, Lit61, Lit5);
    }

    static Object lambda14() {
        return runtime.setAndCoerceProperty$Ex(Lit64, Lit48, Lit49, Lit5);
    }

    static Object lambda15() {
        return runtime.setAndCoerceProperty$Ex(Lit64, Lit48, Lit49, Lit5);
    }

    static Object lambda16() {
        runtime.setAndCoerceProperty$Ex(Lit67, Lit53, Lit54, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit67, Lit31, Lit68, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit67, Lit26, Lit27, Lit5);
    }

    static Object lambda17() {
        runtime.setAndCoerceProperty$Ex(Lit67, Lit53, Lit54, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit67, Lit31, Lit68, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit67, Lit26, Lit27, Lit5);
    }

    static Object lambda18() {
        runtime.setAndCoerceProperty$Ex(Lit71, Lit60, "Inter_18pt-Bold.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit71, Lit35, "This is FarmFriend--your smart farming companion--built to help 🌱 farmers and enthusiasts. 🚜 \nMake better decisions, from soil to harvest.\n\nPowered by:\n\n   - 🧠 ML for soil & crop health analysis\n\n   - 🌍 Location & climate insights\n\n   - 🤖 LLMs for personalized advice ✅\n\nWhether you're just starting 🌾 or farming for years 👨‍🌾—this app’s here to support you.", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit71, Lit26, Lit72, Lit5);
    }

    static Object lambda19() {
        runtime.setAndCoerceProperty$Ex(Lit71, Lit60, "Inter_18pt-Bold.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit71, Lit35, "This is FarmFriend--your smart farming companion--built to help 🌱 farmers and enthusiasts. 🚜 \nMake better decisions, from soil to harvest.\n\nPowered by:\n\n   - 🧠 ML for soil & crop health analysis\n\n   - 🌍 Location & climate insights\n\n   - 🤖 LLMs for personalized advice ✅\n\nWhether you're just starting 🌾 or farming for years 👨‍🌾—this app’s here to support you.", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit71, Lit26, Lit72, Lit5);
    }

    static Object lambda20() {
        return runtime.setAndCoerceProperty$Ex(Lit75, Lit48, Lit49, Lit5);
    }

    static Object lambda21() {
        return runtime.setAndCoerceProperty$Ex(Lit75, Lit48, Lit49, Lit5);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit78, Lit53, Lit54, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit78, Lit26, Lit27, Lit5);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit78, Lit53, Lit54, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit78, Lit26, Lit27, Lit5);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit81, Lit31, Lit82, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit59, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit41, Lit83, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit60, "Inter_18pt-Bold.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit33, Lit34, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit35, "❤️ Made with love from my room <3", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit81, Lit26, Lit84, Lit5);
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit81, Lit31, Lit82, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit59, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit41, Lit83, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit60, "Inter_18pt-Bold.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit33, Lit34, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit81, Lit35, "❤️ Made with love from my room <3", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit81, Lit26, Lit84, Lit5);
    }

    static Object lambda26() {
        return runtime.setAndCoerceProperty$Ex(Lit87, Lit48, Lit49, Lit5);
    }

    static Object lambda27() {
        return runtime.setAndCoerceProperty$Ex(Lit87, Lit48, Lit49, Lit5);
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit90, Lit53, Lit54, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit90, Lit31, Lit91, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit90, Lit26, Lit27, Lit5);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit90, Lit53, Lit54, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit90, Lit31, Lit91, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit90, Lit26, Lit27, Lit5);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit94, Lit60, "Inter_18pt-Bold.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit94, Lit35, "This app was handcrafted 🛠️ by one overcaffeinated teenager (that’s me)\n\nFueled by ☕ late nights, 🎧 anime soundtracks, and 1,000 Arc tabs 🌐.\n\nNo team. \nNo budget. \nJust vibes, code, and passion. 💻🔥\n\nIf it helped you—even a little—I’m happy. 😊", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit94, Lit26, Lit95, Lit5);
    }

    static Object lambda31() {
        runtime.setAndCoerceProperty$Ex(Lit94, Lit60, "Inter_18pt-Bold.ttf", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit94, Lit35, "This app was handcrafted 🛠️ by one overcaffeinated teenager (that’s me)\n\nFueled by ☕ late nights, 🎧 anime soundtracks, and 1,000 Arc tabs 🌐.\n\nNo team. \nNo budget. \nJust vibes, code, and passion. 💻🔥\n\nIf it helped you—even a little—I’m happy. 😊", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit94, Lit26, Lit95, Lit5);
    }

    static Object lambda32() {
        return runtime.setAndCoerceProperty$Ex(Lit98, Lit48, Lit49, Lit5);
    }

    static Object lambda33() {
        return runtime.setAndCoerceProperty$Ex(Lit98, Lit48, Lit49, Lit5);
    }

    static Object lambda34() {
        runtime.setAndCoerceProperty$Ex(Lit101, Lit53, Lit54, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit101, Lit26, Lit27, Lit5);
    }

    static Object lambda35() {
        runtime.setAndCoerceProperty$Ex(Lit101, Lit53, Lit54, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit101, Lit26, Lit27, Lit5);
    }

    static Object lambda36() {
        runtime.setAndCoerceProperty$Ex(Lit104, Lit31, Lit105, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit59, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit60, Component.TYPEFACE_MONOSPACE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit33, Lit34, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit104, Lit35, "-Arcz <3", Lit9);
    }

    static Object lambda37() {
        runtime.setAndCoerceProperty$Ex(Lit104, Lit31, Lit105, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit59, Boolean.TRUE, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit60, Component.TYPEFACE_MONOSPACE, Lit9);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit33, Lit34, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit104, Lit35, "-Arcz <3", Lit9);
    }

    static Object lambda38() {
        return runtime.setAndCoerceProperty$Ex(Lit108, Lit48, Lit49, Lit5);
    }

    static Object lambda39() {
        return runtime.setAndCoerceProperty$Ex(Lit108, Lit48, Lit49, Lit5);
    }

    /* compiled from: About.yail */
    public class frame extends ModuleBody {
        About $main;

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 2:
                    if (!(obj instanceof About)) {
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
                    if (!(obj instanceof About)) {
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
                    if (!(obj instanceof About)) {
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
                    if (!(obj instanceof About)) {
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
                    About about = this.$main;
                    try {
                        Component component = (Component) obj;
                        try {
                            String str = (String) obj2;
                            try {
                                if (obj3 == Boolean.FALSE) {
                                    z = false;
                                }
                                try {
                                    about.dispatchGenericEvent(component, str, z, (Object[]) obj4);
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
                    return About.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return About.lambda3();
                case 21:
                    return this.$main.About$Initialize();
                case 22:
                    return About.lambda4();
                case 23:
                    return About.lambda5();
                case 24:
                    return About.lambda6();
                case 25:
                    return About.lambda7();
                case 26:
                    return this.$main.backbutton$Click();
                case 27:
                    return this.$main.backbutton$TouchDown();
                case 28:
                    return this.$main.backbutton$TouchUp();
                case 29:
                    return About.lambda8();
                case 30:
                    return About.lambda9();
                case 31:
                    return About.lambda10();
                case 32:
                    return About.lambda11();
                case 33:
                    return About.lambda12();
                case 34:
                    return About.lambda13();
                case 35:
                    return About.lambda14();
                case 36:
                    return About.lambda15();
                case 37:
                    return About.lambda16();
                case 38:
                    return About.lambda17();
                case 39:
                    return About.lambda18();
                case 40:
                    return About.lambda19();
                case 41:
                    return About.lambda20();
                case 42:
                    return About.lambda21();
                case 43:
                    return About.lambda22();
                case 44:
                    return About.lambda23();
                case 45:
                    return About.lambda24();
                case 46:
                    return About.lambda25();
                case 47:
                    return About.lambda26();
                case 48:
                    return About.lambda27();
                case 49:
                    return About.lambda28();
                case 50:
                    return About.lambda29();
                case 51:
                    return About.lambda30();
                case 52:
                    return About.lambda31();
                case 53:
                    return About.lambda32();
                case 54:
                    return About.lambda33();
                case 55:
                    return About.lambda34();
                case 56:
                    return About.lambda35();
                case 57:
                    return About.lambda36();
                case 58:
                    return About.lambda37();
                case 59:
                    return About.lambda38();
                case 60:
                    return About.lambda39();
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
        About = this;
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
