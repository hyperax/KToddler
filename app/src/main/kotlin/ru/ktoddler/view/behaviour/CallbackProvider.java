package ru.ktoddler.view.behaviour;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface CallbackProvider {
    @Nullable
    <T> T getCallback(@NonNull Class<T> callbackClass);
}
