import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class CommentTokenConfiguration implements PersistentStateComponent<CommentTokenConfiguration> {

    private List<String> myCustomTokens = new ArrayList<>();

    public static CommentTokenConfiguration getInstance() {
        return ServiceManager.getService(CommentTokenConfiguration.class);
    }

    public List<String> getCustomTokens() {
        return myCustomTokens;
    }

    public void setCustomTokens(final List<String> tokens) {
        myCustomTokens = new ArrayList<>(tokens);
    }

    @Override
    public void loadState(@NotNull CommentTokenConfiguration state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    @Override
    public CommentTokenConfiguration getState() {
        return this;
    }
}