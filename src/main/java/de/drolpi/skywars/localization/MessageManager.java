package de.drolpi.skywars.localization;

import de.drolpi.skywars.localization.resource.MissingResourceBundleException;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class MessageManager implements MessageProvider {

    private final Map<Locale, ResourceBundle> bundles;

    public MessageManager(Map<Locale, ResourceBundle> bundles) {
        this.bundles = bundles;
    }

    @Override
    public String getString(Locale locale, String key, Object... params) {
        ResourceBundle bundle = bundles.get(locale);

        if (bundle == null) {
            throw new MissingResourceBundleException(locale);
        }

        String bundleString = bundle.getString(key);
        MessageFormat messageFormat = new MessageFormat(bundleString, locale);

        return messageFormat.format(params);
    }

    @Override
    public String[] getStringArray(Locale locale, String key, Object... params) {
        ResourceBundle bundle = bundles.get(locale);

        if (bundle == null) {
            throw new MissingResourceBundleException(locale);
        }

        String[] bundleArray = bundle.getStringArray(key);
        String[] localizedArray = new String[bundleArray.length];

        for (int i = 0; i < bundleArray.length; i++) {
            String line = bundleArray[i];
            MessageFormat messageFormat = new MessageFormat(line, locale);
            localizedArray[i] = messageFormat.format(params);
        }

        return localizedArray;
    }

    @Override
    public List<String> getStringList(Locale locale, String key, Object... params) {
        return Arrays.asList(getStringArray(locale, key, params));
    }
}