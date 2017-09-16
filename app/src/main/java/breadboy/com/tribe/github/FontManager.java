package breadboy.com.tribe.github;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by FRANKCHUKY on 9/14/2017.
 */

public class FontManager {

    public static final String ROOT = "font/",
            FONTAWESOME = ROOT + "fontawesome-webfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

}