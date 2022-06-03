package utilities;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Gets the users computer settings
 */
public class UserSettings {

    private static Locale userLocale;
    private static String userLanguage;
    private static ZoneId userTimeZoneId;

    /**
     * Gets computer's setting for locale, language and timezone
     */
    public static void setUserLocation() {
        userLocale = Locale.getDefault();
        userLanguage = userLocale.getDisplayLanguage();
        userTimeZoneId = ZoneId.of(TimeZone.getDefault().getID());
    }

    public static Locale getUserLocale() {
        return userLocale;
    }

    public static String getUserLanguage() {
        return userLanguage;
    }

    public static ZoneId getUserTimeZone() {
        return userTimeZoneId;
    }
}


