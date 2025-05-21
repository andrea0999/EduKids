package cg.edukids.learn.utils.localization;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class ContextWrapper extends android.content.ContextWrapper {
    public ContextWrapper(Context base) {
        super(base);
    }

    public static Context wrap(Context context, Locale newLocale) {
        Configuration config = context.getResources().getConfiguration();
        config.setLocale(newLocale);
        return context.createConfigurationContext(config);
    }
}
