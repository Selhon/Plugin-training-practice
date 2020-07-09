import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
/*получение комментариев из кода*/

public class CommentHighlighterAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof PsiComment) {
            final String comment = element.getText();
            int startOffset = element.getTextRange().getStartOffset();

            Map<TextRange, String> highlightAnnotationData = HighlightTextAttributeUtils.getCommentHighlights(comment, startOffset);


        }
    }

}