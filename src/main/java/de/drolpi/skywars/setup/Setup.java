package de.drolpi.skywars.setup;

import de.drolpi.skywars.setup.answer.QuestionAnswer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class Setup {

    private final SetupManager setupManager;
    private final List<Question<?>> questions;
    private final Map<String, Object> results;
    private final Map<String, Set<Consumer<Object>>> entryCompletionListener;
    protected final Player player;

    private Question<?> currentQuestion;

    public Setup(Player player, SetupManager setupManager) {
        this.setupManager = setupManager;
        this.questions = new ArrayList<>();
        this.player = player;
        this.results = new HashMap<>();
        this.entryCompletionListener = new HashMap<>();
        this.currentQuestion = null;
    }

    public void addQuestion(Question<?> question) {
        questions.add(question);
    }

    public void start() {
        nextQuestion();
    }

    public void nextQuestion() {
        if(currentQuestion == null) {
            currentQuestion = questions.get(0);
        }else {
            int index = questions.indexOf(currentQuestion);

            if((index + 1) >= questions.size()) {
                setupManager.getSkyWars().getPhasenManager().next();
                finish();
                setupManager.getCurrentRunningSetups().remove(player);
                return;
            }else {
                currentQuestion = questions.get(index + 1);
            }
        }

        sendQuestion();
    }

    private void sendQuestion() {
        player.sendMessage(currentQuestion.getQuestion());
    }

    public abstract void configure();

    public abstract void finish();

    public abstract void cancel();

    public void apply(Player player, String input) {
        QuestionAnswer<?> answer = currentQuestion.getAnswer();
        if (answer.isValidInput(input)) {
            Object result = answer.parse(player, input);
            if(entryCompletionListener.containsKey(currentQuestion.getKey())) {
                for (Consumer<Object> listener : entryCompletionListener.get(currentQuestion.getKey())) {
                    listener.accept(result);
                }
            }
            this.results.put(currentQuestion.getKey(), result);
            nextQuestion();
        }else {
            player.sendMessage(answer.getInvalidInputMessage(input));
            sendQuestion();
        }
    }

    public boolean hasResult(@NotNull String key) {
        return this.results.containsKey(key);
    }

    public <T> T getResult(@NotNull String key, Class<T> tClass) {
        return (T) results.get(key);
    }

    public void addCompletionListener(String key, @NotNull Consumer<Object> listener) {
        if(!entryCompletionListener.containsKey(key)) {
            entryCompletionListener.put(key, new HashSet<>());
        }

        Set<Consumer<Object>> consumerSet = entryCompletionListener.get(key);
        consumerSet.add(listener);
        this.entryCompletionListener.put(key, consumerSet);
    }


    public List<Question<?>> getQuestions() {
        return questions;
    }
}
