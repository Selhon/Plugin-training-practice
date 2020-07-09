import com.intellij.openapi.util.TextRange;
import java.util.HashMap;
import java.util.Map;
/*Обработка поступающего коментария, выявление имен и т.д.*/

public class HighlightTextAttributeUtils {

    private HighlightTextAttributeUtils() {
    }


    public static Map<TextRange, String> getCommentHighlights(String comment, int startOffset) {
        final int lastCharPosition = comment.length() - 1;
        final boolean isDocComment = comment.startsWith("/**");
        final int commentLen = comment.length();
        int currLineStartIndex = startOffset;
        if(isDocComment){//начало для документа-комментария
            currLineStartIndex = startOffset+3;
        }else {//начало для др. видов комментариев
            currLineStartIndex = isValidPosition(comment,startOffset);
        }

        boolean isCurrLineQuestion = false; //является ли текущий коммент вопросом
        boolean isCurrLineAnswer = false; //является ли текущий коммент ответом
        boolean isSkippedFirstStarCharInDocComment = false; // пропуск 1 символа в документах
        String userName = null;

        Map<TextRange,String> questionsForUsers = new HashMap<>();

        for (int i = 0; i < commentLen; i++) {
            char c = comment.charAt(i);
            // проверка на конец строки
            if (c == '\n' || i == lastCharPosition) {
                questionsForUsers.put(new TextRange(currLineStartIndex, startOffset + i + 1), userName);
                currLineStartIndex = startOffset + i + 1;
                isSkippedFirstStarCharInDocComment = false;
                userName = null;
                continue;
            }

            // если коммент это Doc, то пропустить символ *
            if (!isSkippedFirstStarCharInDocComment && shouldSkipFistStarInDocComment(c, isDocComment)) {
                isSkippedFirstStarCharInDocComment = true;
                continue;
            }
            //получение имени пользователя если комментарий является вопросом
            if (c=='?' && !isCurrLineAnswer){
                isCurrLineQuestion = true;
                int j=i;
                while (comment.charAt(j)!=':' || j<commentLen){
                    j++;
                }
                if(comment.charAt(j)==':'){
                    userName = comment.substring(i,j);
                }
            }
            //получение имени пользователя если комментарий является ответом
            if (c=='!' && !isCurrLineQuestion){
                isCurrLineAnswer = true;
                int j=i;
                while (comment.charAt(j)!=':' || j<commentLen){
                    j++;
                }
                if(comment.charAt(j)==':'){
                    userName = comment.substring(i,j);
                }
            }
        }


        return questionsForUsers;
    }

    private static boolean shouldSkipFistStarInDocComment(char c, boolean isDocComment) { //пропуск первого символа строки у комм-документов
        return isDocComment && c == '*';
    }

    private static int isValidPosition(String comment, int start) { //пропуск первых символов комментариев
        char c = comment.charAt(start);
        int length = comment.length();
        if (c == '/' && length > 2) {
            return start + 2;
        }
        return start;
    }


}