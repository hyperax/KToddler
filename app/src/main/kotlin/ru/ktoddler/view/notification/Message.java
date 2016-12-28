package ru.ktoddler.view.notification;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ru.ktoddler.util.DateUtils;

public class Message {

    @IntDef({INFO, CONFIRM, ALERT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {}
    public static final int INFO = 1;
    public static final int CONFIRM = 2;
    public static final int ALERT = 4;

    @IntDef({SHORT, LONG, INDEFINITE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {}
    public static final int SHORT = Snackbar.LENGTH_SHORT;
    public static final int LONG = Snackbar.LENGTH_LONG;
    public static final int INDEFINITE = Snackbar.LENGTH_INDEFINITE;

    private long createonDateMillis;

    @Type
    private int type;

    @Duration
    private int duration;

    private String text;

    private static Context ctx;

    public static void init(@NonNull Context context) {
        ctx = context.getApplicationContext();
    }

    private Message() {
        createonDateMillis = DateUtils.getCurrentMillis();
        setType(INFO);
        setDuration(LONG);
    }

    public static Message get() {
        return new Message();
    }

    public static Message get(String text) {
        return get().setText(text);
    }

    public static Message get(@StringRes int resId) {
        return get().setText(resId);
    }

    public static Message info() {
        return get().setType(INFO);
    }

    public static Message info(String text) {
        return info().setText(text);
    }

    public static Message info(@StringRes int resId) {
        return info().setText(resId);
    }

    public static Message alert() {
        return get().setType(ALERT);
    }

    public static Message alert(String text) {
        return alert().setText(text);
    }

    public static Message alert(@StringRes int resId) {
        return alert().setText(resId);
    }

    public static Message confirm() {
        return get().setType(CONFIRM);
    }

    public static Message confirm(String text) {
        return confirm().setText(text);
    }

    public static Message confirm(@StringRes int resId) {
        return confirm().setText(resId);
    }

    public long creationDateMillis() {
        return createonDateMillis;
    }

    @Type
    public int getType() {
        return type;
    }

    public Message setType(@Type int type) {
        this.type = type;
        return this;
    }

    @Duration
    public int getDuration() {
        return duration;
    }

    public Message setDuration(@Duration int duration) {
        this.duration = duration;
        return this;
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public Message setText(@StringRes int resId) {
        this.text = getString(resId);
        return this;
    }

    private String getString(@StringRes int resId) {
        return ctx.getString(resId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (type != message.type) return false;
        if (duration != message.duration) return false;
        return text != null ? text.equals(message.text) : message.text == null;
    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + duration;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
