package ru.ktoddler.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Consumer;

import java.lang.ref.WeakReference;

import ru.ktoddler.R;
import ru.ktoddler.model.exception.KToddlerException;
import ru.ktoddler.util.NpeUtils;
import ru.ktoddler.view.View;
import timber.log.Timber;

public abstract class BasePresenter<V extends View> implements Presenter<V> {

    private final Context context;

    private WeakReference<V> view = new WeakReference<>(null);

    public BasePresenter(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public void bindView(@NonNull V view) {
        this.view.clear();
        this.view = new WeakReference<>(view);
    }

    @Override
    public void unbindView(@NonNull V view) {
        if (NpeUtils.equals(this.view.get(), view)) {
            this.view.clear();
        }
    }

    protected void showLoadingState(final String info) {
        getView().ifPresent(new Consumer<V>() {
            @Override
            public void accept(V v) {
                v.showLoading(info);
            }
        });
    }

    protected void hideLoadingState() {
        getView().ifPresent(new Consumer<V>() {
            @Override
            public void accept(V v) {
                v.hideLoading();
            }
        });
    }

    protected Optional<V> getView() {
        return Optional.ofNullable(view.get());
    }

    protected void handleError(Throwable throwable) {
        if (throwable != null) {
            String mes;
            if (throwable instanceof KToddlerException) {
                KToddlerException te = (KToddlerException) throwable;
                mes = getString(R.string.pattern_definition, te.getTitle(), te.getDetails());
            } else {
                String details = NpeUtils.isEmpty(throwable.getMessage()) ? throwable.toString() : throwable.getMessage();
                mes = getString(R.string.error_unknown_with_description, details);
            }
            showAlert(mes);

            Timber.e(throwable, mes);
        }
    }

    protected void showAlert(final String error) {
        getView().ifPresent(new Consumer<V>() {
            @Override
            public void accept(V v) {
                v.showError(error);
            }
        });
    }

    protected void showInfo(final String info) {
        getView().ifPresent(new Consumer<V>() {
            @Override
            public void accept(V v) {
                v.showInfo(info);
            }
        });
    }

    protected void showConfirm(final String confirm) {
        getView().ifPresent(new Consumer<V>() {
            @Override
            public void accept(V v) {
                v.showConfirm(confirm);
            }
        });
    }

    @NonNull
    public final String getString(@StringRes int resId) {
        return context.getString(resId);
    }

    @NonNull
    public final String getString(@StringRes int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

    @Override
    public void onFinish() {
    }
}
