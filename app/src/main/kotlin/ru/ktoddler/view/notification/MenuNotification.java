package ru.ktoddler.view.notification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import ru.ktoddler.R;
import ru.ktoddler.di.scope.PerApplication;
import rx.subjects.PublishSubject;

@PerApplication
public class MenuNotification {

    private long EQUAL_MESSAGE_INTERVAL_MILLIS = 2000;

    @NonNull
    private final Context context;

    private PublishSubject<Message> messagesBus = PublishSubject.create();

    @Inject
    public MenuNotification(@NonNull Context context) {
        this.context = context;
//        messagesBus.distinctUntilChanged(new Func2<Message, Message, Boolean>() {
//            @Override
//            public Boolean call(Message message1, Message message2) {
//                return Math.abs(message1.creationDateMillis() - message2.creationDateMillis()) < EQUAL_MESSAGE_INTERVAL_MILLIS
//                        && message1.equals(message2);
//            }
//        })
//                .subscribe(new Action1<Message>() {
//                    @Override
//                    public void call(Message message) {
//                        MenuNotification.this.showToastInternal(message);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable t) {
//                        Timber.e(t);
//                    }
//                });
    }

    public static void showSnackbar(Activity activity, @Nullable View view, Message message) {
        showSnackbar(activity, view, message, null);
    }

    public static void showSnackbar(Activity activity, @Nullable View view, Message message, @Nullable Action action) {
        @SuppressWarnings("ResourceType")
        Snackbar sb = Snackbar.make(
                view != null ? view : activity.findViewById(android.R.id.content),
                message.getText(),
                message.getDuration());

        if (action != null) {
            if (action.getActionResId() != 0) {
                sb.setAction(action.getActionResId(), action.getListener());
            } else {
                sb.setAction(action.getActionText(), action.getListener());
            }
            final int actionColor = ContextCompat.getColor(activity, getActionColor(message.getType()));
            sb.setActionTextColor(actionColor);
        }

        View snackBarView = sb.getView();
        final int mesColor = ContextCompat.getColor(activity, getMessageColor(message.getType()));
        snackBarView.setBackgroundColor(mesColor);
        sb.show();
    }

    public void showToast(Message message) {
        messagesBus.onNext(message);
    }

    private void showToastInternal(Message message) {
        int duration;
        switch (message.getDuration()) {
            case Message.INDEFINITE:
                duration = Toast.LENGTH_LONG;
                break;
            case Message.LONG:
                duration = Toast.LENGTH_LONG;
                break;
            case Message.SHORT:
                duration = Toast.LENGTH_SHORT;
                break;
            default:
                duration = Toast.LENGTH_SHORT;
                break;
        }

        Toast toast = Toast.makeText(context, message.getText(), duration);
        TextView toastView = (TextView) LayoutInflater.from(context)
                .inflate(R.layout.view_toast, null, false);
        toastView.setBackground(ContextCompat.getDrawable(context, getMessageDrawable(message.getType())));
        toastView.setText(message.getText());
        toast.setView(toastView);
        toast.show();
    }

    @SuppressLint("SwitchIntDef")
    @ColorRes
    private static int getMessageColor(@Message.Type int type) {
        switch (type) {
            case Message.ALERT:
                return R.color.message_alert;
            case Message.CONFIRM:
                return R.color.message_confirm;
            default:
                return R.color.message_info;
        }
    }

    @SuppressLint("SwitchIntDef")
    private static int getMessageDrawable(@Message.Type int type) {
        switch (type) {
            case Message.ALERT:
                return R.drawable.rounded_button_alert;
            case Message.CONFIRM:
                return R.drawable.rounded_button_confirm;
            default:
                return R.drawable.rounded_button_info;
        }
    }

    @SuppressLint("SwitchIntDef")
    @ColorRes
    private static int getActionColor(@Message.Type int type) {
        switch (type) {
            case Message.ALERT:
                return R.color.message_action_alert;
            case Message.CONFIRM:
                return R.color.message_action_confirm;
            default:
                return R.color.message_action_info;
        }
    }
}
