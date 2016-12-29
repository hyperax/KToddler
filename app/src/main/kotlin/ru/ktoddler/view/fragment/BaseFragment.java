package ru.ktoddler.view.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.annimon.stream.function.Consumer;

import javax.inject.Inject;

import ru.ktoddler.di.AppComponent;
import ru.ktoddler.util.NpeUtils;
import ru.ktoddler.view.activity.BaseActivity;
import ru.ktoddler.view.behaviour.CallbackProvider;
import ru.ktoddler.view.notification.Action;
import ru.ktoddler.view.notification.KToddlerNotification;
import ru.ktoddler.view.notification.Message;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment {

    public interface TitleUpdateListener {
        boolean updateTitleRequest(@NonNull BaseFragment fragment);
    }

    @NonNull
    private CompositeSubscription subscription = new CompositeSubscription();

    @Inject
    KToddlerNotification notification;

    protected Object getHostParent() {
        Object hostParent = null;
        if (getParentFragment() != null) {
            hostParent = getParentFragment();
        } else if (getActivity() != null) {
            hostParent = getActivity();
        }
        return hostParent;
    }

    @Nullable
    protected <T> T getCallback(@NonNull Class<T> callbackClass) {
        Object hostParent = getHostParent();
        if (hostParent instanceof CallbackProvider) {
            return ((CallbackProvider) hostParent).getCallback(callbackClass);
        } else if (callbackClass.isInstance(hostParent)) {
            return callbackClass.cast(hostParent);
        }
        return null;
    }

    protected boolean updateTitleRequest() {
        boolean titleUpdated = false;
        TitleUpdateListener listener = getCallback(TitleUpdateListener.class);
        if (listener != null) {
            titleUpdated = listener.updateTitleRequest(this);
        }

        return titleUpdated;
    }

    public final String getTitle() {
        return getInstanceTitle();
    }

    @Nullable
    protected String getInstanceTitle() {
        return null;
    }

    protected void addSubscription(Subscription s) {
        subscription.add(s);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTitleRequest();
    }

    @Override
    public void onStop() {
        super.onStop();
        // TODO repair this
//        ViewUtils.hideKeyboard(getActivity());
    }

    @Override
    public void onDestroy() {
        if (isRemoving() || getActivity().isFinishing()) {
            onFinish();
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        subscription.unsubscribe();
        super.onDestroyView();
    }

    protected void onFinish() {
    }

    public boolean onBackPressed() {
        return false;
    }

    public boolean hasBackStack() {
        return false;
    }

    protected boolean verifyGrantedPermission(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    protected void showToast(Message message) {
        notification.showToast(message);
    }

    protected void showSnackbar(Message message, Action action) {
        notification.showSnackbar(getActivity(), null, message, action);
    }

    protected AppComponent getAppComponent() {
        return ((BaseActivity) getActivity()).getAppComponent();
    }

    protected <T extends DialogFragment> void showDialog(@NonNull T dialog) {
        if (!isFragmentActive(dialog.getClass().getSimpleName())) {
            dialog.show(getChildFragmentManager(), dialog.getClass().getSimpleName());
        }
    }

    private boolean isFragmentActive(String tag) {
        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        return fragment != null && !fragment.isDetached() && !fragment.isRemoving();
    }

    protected void hideDialog(Class<?> dialog) {
        NpeUtils.call(getChildFragmentManager().findFragmentByTag(dialog.getSimpleName()),
                DialogFragment.class,
                new Consumer<DialogFragment>() {
                    @Override
                    public void accept(DialogFragment dialogFragment) {
                        dialogFragment.dismissAllowingStateLoss();
                    }
                });
    }
}
