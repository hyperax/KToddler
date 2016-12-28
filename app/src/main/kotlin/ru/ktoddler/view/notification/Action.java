package ru.ktoddler.view.notification;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import java.lang.ref.WeakReference;

public class Action {

    @StringRes
    private int actionTextId;

    @Nullable
    private String actionText;

    private WeakReference<View.OnClickListener> actionListener = new WeakReference<>(null);

    private Action(int actionTextId, @Nullable String actionText, @Nullable View.OnClickListener actionListener) {
        this.actionTextId = actionTextId;
        this.actionText = actionText;
        this.actionListener = new WeakReference<>(actionListener);
    }

    public static Action get(@StringRes int textId, @Nullable View.OnClickListener actionListener) {
        return new Action(textId, null, actionListener);
    }

    public static Action get(@Nullable String actionText, @Nullable View.OnClickListener actionListener) {
        return new Action(0, actionText, actionListener);
    }

    @StringRes
    public int getActionResId() {
        return actionTextId;
    }

    @Nullable
    public String getActionText() {
        return actionText;
    }

    @Nullable
    public View.OnClickListener getListener() {
        return actionListener.get();
    }
}
