package for_exam;

import lombok.Getter;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Getter
public class ExtendedTableModel<T> extends AbstractTableModel {
    private Class<T> cls;
    private String[] columnNames;
    private List<T> allRows = new ArrayList<>();
    private List<T> filteredRows;
    private Predicate<T>[] filters = new Predicate[10];
    private Comparator<T> sorter;
    @Override
    public int getRowCount() {
        return filteredRows.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Field field = cls.getDeclaredFields()[i1];
        field.setAccessible(true);
        try {
            return field.get(filteredRows.get(i));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public ExtendedTableModel(Class<T> cls, String[] columnNames) {
        this.cls = cls;
        this.columnNames = columnNames;
    }

    public void updateFilters() {
        filteredRows = new ArrayList<>(allRows);
        for(Predicate<T> predicate: filters){
            if(predicate!=null) filteredRows.removeIf(row -> !predicate.test(row));
        }
        if(sorter!=null) Collections.sort(filteredRows, sorter);
        fireTableDataChanged();
        onUpdateRowEvent();
    }

    public void  onUpdateRowEvent(){}

    public void setAllRows(List<T> allRows) {
        this.allRows = allRows;
        updateFilters();
    }

    public void setSorter(Comparator<T> sorter) {
        this.sorter = sorter;
        updateFilters();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.length<=column? cls.getDeclaredFields()[column].getName():columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cls.getDeclaredFields()[columnIndex].getType();
    }
}
