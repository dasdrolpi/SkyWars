package de.drolpi.skywars.runnable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CatchingRunnable implements Runnable {

    private final Runnable delegate;

    public CatchingRunnable(@NotNull Runnable delegate) {
        Objects.requireNonNull(delegate, "delegate can't be null!");
        this.delegate = delegate;
    }

    @Override
    public void run() {
        try {
            delegate.run();
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
}
