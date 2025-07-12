package com.soro.javafxex01.model;

import javafx.beans.property.*;

/**
 * packageName : com.soro.javafxex01.model
 * fileName    : TodoItem
 * author      : joshuapark
 * date        : 7/11/25
 * description :
 * ================================================
 * DATE        AUTHOR              DATE
 * ================================================
 * 7/11/25     joshuapark             7/11/25
 */
public class TodoItem {
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final BooleanProperty completed  = new SimpleBooleanProperty();

    public TodoItem(String title, String description) {
        this.title.set(title);
        this.description.set(description);
        this.completed.set(false);
    }

    public String getTitle() { return title.get(); }
    public StringProperty titleProperty() { return title; }
    public void setTitle(String title) { this.title.set(title); }

    public String getDescription() { return description.get(); }
    public StringProperty descriptionProperty() { return description; }
    public void setDescription(String description) { this.description.set(description); }

    public boolean isCompleted() { return completed.get(); }
    public void setCompleted(boolean completed) { this.completed.set(completed); }
    public BooleanProperty completedProperty() { return completed; }
}
