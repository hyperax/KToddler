package ru.ktoddler.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Consumer;

import javax.inject.Inject;

import ru.ktoddler.KToddlerApp;
import ru.ktoddler.di.AppComponent;
import ru.ktoddler.util.NpeUtils;
import ru.ktoddler.view.behaviour.ToolbarProvider;
import ru.ktoddler.view.fragment.BaseFragment;
import ru.ktoddler.view.notification.KToddlerNotification;
import ru.ktoddler.view.notification.Message;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity implements
        BaseFragment.TitleUpdateListener, ToolbarProvider {

    @NonNull
    private CompositeSubscription subscription = new CompositeSubscription();

    @Inject
    KToddlerNotification notification;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected <T extends ViewDataBinding> T bindView(@LayoutRes int layoutId) {
        return DataBindingUtil.setContentView(this, layoutId);
    }

    protected void addSubscription(Subscription s) {
        subscription.add(s);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        subscription = new CompositeSubscription();
    }

    @Override
    protected void onDestroy() {
        subscription.unsubscribe();
        if (isFinishing()) {
            onFinish();
        }
        super.onDestroy();
    }

    protected void onFinish() {
    }

    protected void replaceFragment(@IdRes int layoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(layoutId, fragment)
                .commit();
    }

    protected void replaceFragment(@IdRes int layoutId, Fragment fragment, String backStackName) {
        getSupportFragmentManager().beginTransaction()
                .replace(layoutId, fragment)
                .addToBackStack(backStackName)
                .commit();
    }

    @Override
    public boolean updateTitleRequest(@NonNull BaseFragment fragment) {
        String title = fragment.getTitle();
        if (title != null && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            return true;
        }
        return false;
    }

    protected void showToast(Message message) {
        notification.showToast(message);
    }

    protected void showSnackbar(Message message) {
        notification.showSnackbar(this, null, message, null);
    }

    public AppComponent getAppComponent() {
        return ((KToddlerApp) getApplication()).getAppComponent();
    }

    protected boolean hasBackStack() {
        return getSupportFragmentManager().getBackStackEntryCount() > 0;
    }

    @NonNull
    protected Bundle getExtras() {
        return Optional.ofNullable(getIntent().getExtras()).orElse(new Bundle());
    }

    protected <T extends DialogFragment> void showDialog(@NonNull T dialog) {
        if (!isFragmentActive(dialog.getClass().getSimpleName())) {
            dialog.show(getSupportFragmentManager(), dialog.getClass().getSimpleName());
        }
    }

    private boolean isFragmentActive(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        return fragment != null && !fragment.isDetached() && !fragment.isRemoving();
    }

    protected void hideDialog(Class<?> dialog) {
        NpeUtils.call(getSupportFragmentManager().findFragmentByTag(dialog.getSimpleName()),
                DialogFragment.class,
                new Consumer<DialogFragment>() {
                    @Override
                    public void accept(DialogFragment dialogFragment) {
                        dialogFragment.dismissAllowingStateLoss();
                    }
                });
    }

    @NonNull
    protected Optional<BaseFragment> findFragment(@IdRes int id) {
        return Optional.ofNullable((BaseFragment) getSupportFragmentManager().findFragmentById(id));
    }

    @Nullable
    @Override
    public Toolbar getToolbar() {
        return null;
    }
}
