package app.entities;

public class Room {

    private String room_number;
    private String description;

    public Room(String room_number, String description) {
        this.room_number = room_number;
        this.description = description;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
