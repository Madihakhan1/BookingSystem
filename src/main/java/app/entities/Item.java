package app.entities;

public class Item {

    private String item_name;
    private String description;

    public Item(String item_name, String description) {
        this.item_name = item_name;
        this.description = description;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
