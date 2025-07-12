package com.soro.javafxex01;

import com.soro.javafxex01.model.TodoItem;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Callback;

/**
 * packageName : com.soro.javafxex01
 * fileName    : TodoApp
 * author      : joshuapark
 * date        : 7/11/25
 * description :
 * ================================================
 * DATE        AUTHOR              DATE
 * ================================================
 * 7/11/25     joshuapark             7/11/25
 */
public class TodoApp extends Application {

    private final ObservableList<TodoItem> todoList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Todo Application");

        // TableView setup
        TableView<TodoItem> tableView = new TableView<>(todoList);
        tableView.setEditable(true);

        TableColumn<TodoItem, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(cellData ->cellData.getValue().titleProperty());

        TableColumn<TodoItem, String> descriptionCol= new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(cellData ->cellData.getValue().descriptionProperty());

        // 완료 컬럼
        TableColumn<TodoItem, Boolean> completedCol = new TableColumn<>("완료");
        completedCol.setCellValueFactory(cellData -> cellData.getValue().completedProperty().asObject());

        // 이 부분이 핵심!
        completedCol.setCellFactory(CheckBoxTableCell.forTableColumn(index -> {
            if (index >= 0 && index < tableView.getItems().size()) {
                return tableView.getItems().get(index).completedProperty();
            } else {
                return new SimpleBooleanProperty(false);
            }
        }));
        completedCol.setEditable(true);

        TableColumn<TodoItem, Void> deleteCol = new TableColumn<>("삭제");
        deleteCol.setCellFactory(new Callback<TableColumn<TodoItem, Void>, TableCell<TodoItem, Void>>() {
            @Override
            public TableCell<TodoItem, Void> call(TableColumn<TodoItem, Void> param) {
                return new TableCell<TodoItem, Void>() {
                    private final Button btn = new Button("Del");

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getIndex() >= todoList.size()) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction(event -> {
                                TodoItem selectedItem = getTableView().getItems().get(getIndex());
                                todoList.remove(selectedItem);
                            });
                            setGraphic(btn);
                        }
                    }
                };
            }
        });

        tableView.getColumns().addAll(titleCol, descriptionCol, completedCol, deleteCol);

        // Add some sample data
        todoList.add(new TodoItem("Buy groceries", "Milk, Bread, Eggs" ));
        todoList.add(new TodoItem("Walk the dog", "Evening walk in the park"));
        todoList.add(new TodoItem("Finish homework", "Math and Science assignments"));

        // TextField and Button for adding new todo items can be added here
        TextField inputTitleField = new TextField();
        inputTitleField.setPromptText("Enter Title");

        TextField inputDesField = new TextField();
        inputDesField.setPromptText("Enter Description");

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            String title = inputTitleField.getText().trim();
            String description = inputDesField.getText().trim();
            if (!title.isEmpty() && !description.isEmpty()) {
                todoList.add(new TodoItem(title, description));
                inputTitleField.clear();
                inputDesField.clear();
            }  else {
                System.out.println("Title and Description cannot be empty.");
            }
        });

        // VBox layout for the scene
        VBox root = new VBox(10,
                            inputTitleField,
                            inputDesField,
                            addButton,
                            tableView);

        // Secne which contains VBox root with 400, 300 dimensions
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
