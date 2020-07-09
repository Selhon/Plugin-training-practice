import com.intellij.openapi.util.TextRange;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Map;

/*Модель таблицы для окна плагина в настройках*/

public class MessageTable extends AbstractTableModel {


    public int rowCount = 0;
    public int columnCount = 1; //прочитанные и непрочитанные
    public ArrayList <Map<TextRange, String>> data;

    public MessageTable(){
        data = new ArrayList<Map<TextRange, String>>();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return null;
    }

    public void addData(Map<TextRange, String> row){  //добавление одного вопроса и ответа, если таковой имеется
        data.add(row);
    }
}
