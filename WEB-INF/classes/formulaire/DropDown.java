package formulaire;

import javax.swing.JComboBox;

public class DropDown extends JComboBox<String> {
    String[] data;
    String[] value;

    public void setData(String[] data, String[] value) throws Exception {
        if (data.length != value.length) 
            throw new Exception("Invalid data");
        this.data = data;
        this.value = value;
    }

    public DropDown(String[] data, String[] value) throws Exception {
        super(data);
        setData(data, value);
    }

    public String[] getData() {
        return data;
    }

    public String getSelectedValue() {
        return this.value[this.getSelectedIndex()];
    }
    
    public String[] getValue() {
        return value;
    }
}