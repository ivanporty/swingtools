package com.porty.swing;

import java.util.ResourceBundle;

/**
 * Access to various resources in the library. Resources can be substitutes for easier manipulation.
 *
 * @author iportyankin
 */
public class Resources {
    private static ResourceBundle mainBundle;

    public static ResourceBundle getMainResourceBundle() {
        if ( mainBundle == null ) {
            mainBundle = ResourceBundle.getBundle("com.porty.swing.strings");
        }
        return mainBundle;
    }

    public static void setMainResourceBundle(ResourceBundle bundle) {
        Resources.mainBundle = bundle;
    }
}
