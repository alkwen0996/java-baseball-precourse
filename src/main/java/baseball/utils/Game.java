package baseball.utils;

import java.util.HashMap;
import java.util.Map;

import baseball.domain.DeterminationPitching;

public class Game {

    private static final String RESTART_SIGNAL = "1";
    private static final String SPACE = " ";
    private static final int FOUL = 0;
    private static final String RESTART_NUMBER_REGEX = "^[1-2]{1}$";

    public static boolean checkCorrect(final String answer, final String guessAnswer) {
        return answer.equals(guessAnswer);
    }

    public static String writeHintMessage(final int strikeCount, final int ballCount) {

        if ((strikeCount == 0) && (ballCount > 0)) {
            return appendBallHintMessage(ballCount);
        } else if ((strikeCount > 0) && (ballCount == 0)) {
            return appendStrikeHintMessage(strikeCount);
        } else if ((strikeCount == 0) && (ballCount == 0)) {
            return appendNothingHintMessage();
        }

        return appendBallHintMessage(ballCount) + SPACE + appendStrikeHintMessage(strikeCount);
    }

    private static String appendNothingHintMessage() {
        return String.valueOf(DeterminationPitching.NOTHING.determinePitching());
    }

    private static String appendStrikeHintMessage(final int strike) {
        return strike + String.valueOf(DeterminationPitching.STRIKE.determinePitching());
    }

    private static String appendBallHintMessage(final int ball) {
        return ball + String.valueOf(DeterminationPitching.BALL.determinePitching());
    }

    public static boolean decideRestartGame(final String restartOrNotNumber) {
        return restartOrNotNumber.equals(RESTART_SIGNAL);
    }

    public static void validateRestartNumber(final String restartOrNotNumber) {

        if (!restartOrNotNumber.matches(RESTART_NUMBER_REGEX)) {
            throw new IllegalArgumentException();
        }

    }

    public static Map<String, Integer> countStrikeBall(final String answer, final String guessNumber) {
        final Map<String, Integer> strikeBallCount = new HashMap<>();

        int strike = 0;
        int ball = 0;

        for (int i = 0; i < answer.length(); i++) {
            int strikeBall = determineStrikeBall(answer, i, guessNumber);

            if (strikeBall == 1) {
                strike = addStrikeBall(strike, ball, strikeBall);
            } else if (strikeBall == 2) {
                ball = addStrikeBall(strike, ball, strikeBall);
            }

        }

        strikeBallCount.put(String.valueOf(DeterminationPitching.STRIKE), strike);
        strikeBallCount.put(String.valueOf(DeterminationPitching.BALL), ball);

        return strikeBallCount;
    }

    private static int addStrikeBall(int strike, int ball, final int strikeBall) {
        if (strikeBall == 1) {
            strike++;

            return strike;
        } else if (strikeBall == 2) {
            ball++;

            return ball;
        }

        return FOUL;
    }

    private static int determineStrikeBall(final String answer, final int i, final String guessNumber) {
        int decide = 0;

        char answerCharacter = answer.charAt(i);
        char guessNumberCharacter = guessNumber.charAt(i);

        if (answerCharacter == guessNumberCharacter) {
            decide = 1;
        }

        if ((answerCharacter != guessNumberCharacter) && (answer.contains(String.valueOf(guessNumberCharacter)))) {
            decide = 2;
        }

        return decide;
    }

}
