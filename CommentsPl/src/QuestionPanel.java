import com.intellij.CommonBundle;
import com.intellij.openapi.application.ApplicationBundle;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.JBColor;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.UIBundle;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.ListTableModel;
import com.intellij.xml.util.XmlStringUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
/*внешний вид "чата" в панели настроек*/

public class QuestionPanel extends JPanel implements SearchableConfigurable, Configurable.NoScroll{
    private final JBTable myTokenTable;
    private List<String> myTokens;
    private ListTableModel<String> myModel;

    private static final ColumnInfo<String, String> NAME_COLUMN = new ColumnInfo<String, String>("Questions") {
        @Override
        public String valueOf(String token) {
            return token;
        }
    };

    private JLabel reopenLabel;

    public QuestionPanel() {
        super(new BorderLayout());
        reopenLabel = new JLabel(XmlStringUtil.wrapInHtml(""));
        reopenLabel.setForeground(JBColor.RED);
        MessageTable mt = new MessageTable();
        myTokenTable = new JBTable(mt);
        myTokenTable.getEmptyText().setText("no messages");
        reset();
        add(
                new JLabel(XmlStringUtil.wrapInHtml("Table of all questions sorted by recipients")),
                BorderLayout.NORTH
        );
        add(
                ToolbarDecorator.createDecorator(myTokenTable)
                        .setRemoveAction(button -> {
                            int returnValue = Messages.showOkCancelDialog("Delete selected question?",
                                    UIBundle.message("delete.dialog.title"),
                                    ApplicationBundle.message("button.delete"),
                                    CommonBundle.getCancelButtonText(),
                                    Messages.getQuestionIcon());
                            if (returnValue == Messages.OK) {
                                int selRow = myTokenTable.getSelectedRow();
                                myTokens.remove(selRow);
                                myModel.fireTableDataChanged();
                                if (myTokenTable.getRowCount() > 0) {
                                    if (selRow >= myTokenTable.getRowCount()) {
                                        selRow--;
                                    }
                                    myTokenTable.getSelectionModel().setSelectionInterval(selRow, selRow);
                                }
                            }
                        })
                        .setEditAction(button -> {
                            String token = myModel.getItem(myTokenTable.getSelectedRow());
                            AddDialogWindow dialogWin = new AddDialogWindow();
                            dialogWin.setTitle("Edit message");
                            dialogWin.setToken(token);
                            if (dialogWin.showAndGet()) {
                                final String editedToken = dialogWin.getToken();
                                myTokens.remove(token);
                                myTokens.add(editedToken);
                                myModel.fireTableDataChanged();
                            }
                        })
                        .setButtonComparator("Edit", "Remove")
                        .disableUpDownActions()
                        .createPanel(),
                BorderLayout.CENTER
        );
    }

    @Override
    public void apply() {
        add(reopenLabel, BorderLayout.SOUTH);
        CommentTokenConfiguration.getInstance().setCustomTokens(myTokens);
    }

    @Override
    public boolean isModified() {
        return !myTokens.equals(CommentTokenConfiguration.getInstance().getCustomTokens());
    }

    @Override
    public void reset() {
        myTokens = new ArrayList<>(CommentTokenConfiguration.getInstance().getCustomTokens());
        myModel = new ListTableModel<>(new ColumnInfo[]{NAME_COLUMN,}, myTokens, 0);
        myTokenTable.setModel(myModel);
    }

    @Override
    @Nls
    public String getDisplayName() {
        return "Questions from developers";
    }

    @Override
    @NotNull
    public String getId() {
        return "comment";
    }

    @Override
    public JComponent createComponent() {
        return this;
    }
}
