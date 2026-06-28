package ir.ammari.nodelook;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

record Category(@NonNull String title, @NonNull String description, @ColorInt int color,
                @NonNull SiteInfo[] items) {
}
