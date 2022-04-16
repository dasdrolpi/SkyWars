package de.drolpi.skywars.setup;

import de.drolpi.skywars.setup.answer.QuestionAnswer;
import lombok.Data;

@Data
public class Question<T> {

    private final String key;
    private final String question;
    private final QuestionAnswer<T> answer;

}
