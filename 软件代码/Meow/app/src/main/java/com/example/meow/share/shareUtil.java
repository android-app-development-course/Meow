package com.example.meow.share;

import android.content.Context;
import android.content.Intent;

/**
 * Created by win on 2017/12/26.
 */

public class shareUtil {
    /**
     * 分享链接
     */
    public static void shareLink(String url, String title, Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "Meow \uD83D\uDC08" + "\n"
                + "你想知道的关于猫咪的一切~"  + "\n" + url);
        context.startActivity(Intent.createChooser(intent, title));
    }
}
