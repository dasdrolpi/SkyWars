package de.drolpi.skywars.scoreboard.creator;

import de.drolpi.skywars.scoreboard.result.ScoreboardResult;
import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public abstract class AbstractScoreboardConfiguration {

    private Consumer<ScoreboardResult> showHandler;
    private Consumer<ScoreboardResult> hideHandler;

    private Function<ScoreboardResult, String> titleHandler;
    private Function<ScoreboardResult, List<String>> linesHandler;

    public abstract void configure();

    public void configureShowHandler(Consumer<ScoreboardResult> showHandler) {
        this.showHandler = showHandler;
    }

    public void configureHideHandler(Consumer<ScoreboardResult> hideHandler) {
        this.hideHandler = hideHandler;
    }

    public void configureTitleHandler(Function<ScoreboardResult, String> titleHandler) {
        this.titleHandler = titleHandler;
    }

    public void configureLinesHandler(Function<ScoreboardResult, List<String>> linesHandler) {
        this.linesHandler = linesHandler;
    }
}