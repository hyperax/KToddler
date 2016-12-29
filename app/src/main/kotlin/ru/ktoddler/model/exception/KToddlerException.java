package ru.ktoddler.model.exception;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.ktoddler.util.NpeUtils;

public class KToddlerException extends RuntimeException {

    @Nullable
    private final String title;

    @Nullable
    private final String details;

    public KToddlerException(@Nullable String title, @Nullable String details) {
        super(title + ": " + details);
        this.title = title;
        this.details = details;
    }

    @NonNull
    public String getTitle() {
        return NpeUtils.getNonNull(title);
    }

    @NonNull
    public String getDetails() {
        return NpeUtils.getNonNull(details);
    }
}
