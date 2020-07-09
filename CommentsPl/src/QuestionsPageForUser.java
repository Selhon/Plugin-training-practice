import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
/*панель в настройках IDE*/

public class QuestionsPageForUser implements SearchableConfigurable {

        private QuestionPanel SettingsPanel;

        @NotNull
        @Override
        public String getId() {
            return "QuestionsPageForUser";
        }

        @Nls(capitalization = Nls.Capitalization.Title)
        @Override
        public String getDisplayName() {
            return "QuestionsPageForUser";
        }

        @Nullable
        @Override
        public JComponent createComponent() {
            if (SettingsPanel == null) {
                SettingsPanel = new QuestionPanel();
            }
            return SettingsPanel;
        }

        @Override
        public boolean isModified() {
            return SettingsPanel.isModified();
        }

        @Override
        public void apply() {
            SettingsPanel.apply();
        }
    }
