package app.entities;

public class Item {

    private String item_name;
    private String description;
    private String category;
    private String status;

    public Item(String item_name, String description, String category, String status) {
        this.item_name = item_name;
        this.description = description;
        this.category = category;
        this.status = status;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
