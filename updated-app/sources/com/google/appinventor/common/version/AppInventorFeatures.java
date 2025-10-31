package com.google.appinventor.common.version;

public final class AppInventorFeatures {
    private AppInventorFeatures() {
    }

    public static boolean hasYailGenerationOption() {
        return true;
    }

    public static boolean sendBugReports() {
        return true;
    }

    public static boolean allowMultiScreenApplications() {
        return true;
    }

    public static boolean showInternalComponentsCategory() {
        return false;
    }

    public static boolean takeScreenShots() {
        return false;
    }

    public static boolean trackClientEvents() {
        return false;
    }

    public static boolean showSplashScreen() {
        return true;
    }

    public static boolean showSurveySplashScreen() {
        return false;
    }

    public static boolean requireOneLogin() {
        return true;
    }

    public static boolean doCompanionSplashScreen() {
        return false;
    }

    public static boolean doPrettifyXml() {
        return false;
    }

    public static boolean enableFutureFeatures() {
        return false;
    }

    public static boolean enableHttpRedirect() {
        return true;
    }

    public static String chatBotHost() {
        return "https://chatbot.appinventor.mit.edu/";
    }

    public static boolean doingSurvey() {
        return true;
    }

    public static boolean allowIosBuilds() {
        return false;
    }

    public static boolean allowAppStoreBuilds() {
        return false;
    }
}
