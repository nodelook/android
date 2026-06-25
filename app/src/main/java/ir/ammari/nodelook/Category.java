package ir.ammari.nodelook;

import androidx.annotation.NonNull;

record Category(@NonNull String title, @NonNull String description, int color, @NonNull SiteInfo[] items) {
}
