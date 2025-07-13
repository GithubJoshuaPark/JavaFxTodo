package com.soro.javafxex01;

import com.soro.javafxex01.model.TodoItem;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

    // 선택된 아이템 저장용 변수
    final TodoItem[] selectedItemForEdit = {null};

    // TableView setup
    TableView<TodoItem> tableView               = new TableView<>(todoList);
    TableColumn<TodoItem, String> titleCol      = new TableColumn<>("Title");
    TableColumn<TodoItem, String> descriptionCol= new TableColumn<>("Description");
    TableColumn<TodoItem, Boolean> completedCol = new TableColumn<>("IsCompleted");
    TableColumn<TodoItem, Void> deleteCol       = new TableColumn<>("Delete");

    TextField inputTitleField = new TextField();
    TextField inputDesField   = new TextField();
    Button addButton          = new Button("Add");
    Button editButton         = new Button("Edit");

    HBox hBoxForAddAndEdit = new HBox(10, addButton, editButton);
    VBox inputBox          = new VBox(10, inputTitleField, inputDesField, hBoxForAddAndEdit);
    VBox root              = new VBox(10, inputBox, tableView);
    Scene scene            = new Scene(root, 800, 600);

    private void initEditButton() {
        editButton.setPrefWidth(60);
        editButton.setDisable(true); // 처음에는 비활성화
        editButton.setStyle("-fx-background-color: #87d068; -fx-text-fill: white; -fx-font-weight: bold;");
        editButton.setOnMouseEntered(event -> editButton.setStyle("-fx-background-color: #6fbf5a; -fx-text-fill: white; -fx-font-weight: bold;"));
        editButton.setOnMouseExited(event -> editButton.setStyle("-fx-background-color: #87d068; -fx-text-fill: white; -fx-font-weight: bold;"));
        editButton.setOnAction(event -> {
            if (selectedItemForEdit[0] != null) {
                selectedItemForEdit[0].setTitle(inputTitleField.getText().trim());
                selectedItemForEdit[0].setDescription(inputDesField.getText().trim());
                tableView.refresh();
                inputTitleField.clear();
                inputDesField.clear();
                editButton.setDisable(true); // 수정 끝나면 다시 비활성화
                selectedItemForEdit[0] = null;
                inputTitleField.requestFocus(); // 포커스 이동

                // 수정 후 원래 스타일로
                inputBox.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5");
                editButton.setText("Edit");
            }
        });
    }

    private void initAddButton() {
        addButton.setPrefWidth(60);
        addButton.setStyle("-fx-background-color: #62a1f7; -fx-text-fill: white; -fx-font-weight: bold;");
        addButton.setAlignment(Pos.CENTER);
        addButton.setOnMouseExited(event -> addButton.setStyle("-fx-background-color: #62a1f7; -fx-text-fill: white; -fx-font-weight: bold;"));
        addButton.setOnMouseEntered(event -> addButton.setStyle("-fx-background-color: #4a8cd9; -fx-text-fill: white; -fx-font-weight: bold;"));
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
    }

    private void initInputTitleField(TextField inputTitleField, String Enter_Title) {
        inputTitleField.setPromptText(Enter_Title);
        inputTitleField.setPrefWidth(120);
    }

    private void initTableView() {

        // TableView 스타일 지정
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setStyle("-fx-font-size: 14px; -fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 5");

        // MARK: - 컬럼 설정 Start
        titleCol.setCellValueFactory(cellData ->cellData.getValue().titleProperty());
        checkIsCompleteAndSetStrikeThroughStyle(titleCol);

        descriptionCol.setCellValueFactory(cellData ->cellData.getValue().descriptionProperty());
        checkIsCompleteAndSetStrikeThroughStyle(descriptionCol);

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
        deleteCol.setCellFactory(new Callback<TableColumn<TodoItem, Void>, TableCell<TodoItem, Void>>() {
            @Override
            public TableCell<TodoItem, Void> call(TableColumn<TodoItem, Void> param) {
                return new TableCell<TodoItem, Void>() {
                    private final Button btn = new Button("Del");
                    {
                        btn.setStyle("-fx-background-color: #f76b6b; -fx-text-fill: white; -fx-background-radius: 10;");
                        btn.setPrefWidth(40);
                    }

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

        titleCol.setPrefWidth(100);
        descriptionCol.setPrefWidth(150);
        completedCol.setPrefWidth(70);
        deleteCol.setPrefWidth(60);
        // MARK: - 컬럼 설정 End

        tableView.setRowFactory(tv -> {
            TableRow<TodoItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    // 더블 클릭 시 해당 아이템을 선택하고 입력 필드에 값 설정
                    TodoItem clickedItem = row.getItem();
                    tableView.getSelectionModel().select(clickedItem); // 이 코드 추가!
                    System.out.println("Double clicked on: " + clickedItem.getTitle());

                    inputTitleField.setText(clickedItem.getTitle());
                    inputDesField.setText(clickedItem.getDescription());
                    selectedItemForEdit[0] = clickedItem;
                    editButton.setDisable(false); // 수정버튼 활성화

                    inputBox.setStyle("-fx-background-color: #fff9c4; -fx-padding: 10; -fx-border-color: #fbc02d; -fx-border-radius: 5");
                    editButton.setText("Save"); // 버튼 텍스트 변경
                }
            });
            return row;
        });

        // 컬럼을 TableView에 추가
        tableView.getColumns().addAll(titleCol, descriptionCol, completedCol, deleteCol);

        for(TodoItem item: todoList) {
            System.out.println("Todo Item: " + item.getTitle() + ", Completed: " + item.isCompleted());
            item.completedProperty().addListener((observable
                                                    , oldValue
                                                    , newValue) -> tableView.refresh());
        }
        todoList.addListener((javafx.collections.ListChangeListener<TodoItem>) c -> {
            while(c.next()) {
                if (c.wasAdded()) {
                    for (TodoItem item : c.getAddedSubList()) {
                        System.out.println("Added Todo Item: " + item.getTitle() + ", Completed: " + item.isCompleted());
                        item.completedProperty().addListener((observable, oldValue, newValue) -> {
                            System.out.println("Todo Item Completed Status Changed: " + item.getTitle() + " to " + newValue);
                            tableView.refresh(); // 상태 변경 시 테이블 새로고침
                        });
                    }
                }
                if (c.wasRemoved()) {
                    for (TodoItem item : c.getRemoved()) {
                        System.out.println("Removed Todo Item: " + item.getTitle() + ", Completed: " + item.isCompleted());
                        item.completedProperty().removeListener((observable, oldValue, newValue) -> {
                            System.out.println("Todo Item Completed Status Changed: " + item.getTitle() + " to " + newValue);
                            tableView.refresh(); // 상태 변경 시 테이블 새로고침
                        });
                    }
                }
            }
        });
    }

    private void checkIsCompleteAndSetStrikeThroughStyle(TableColumn<TodoItem, String> titleCol) {
        titleCol.setCellFactory(column -> new TableCell<TodoItem, String>() {

            private final Text text = new Text();

            // check if the content is completed, if so, apply a strikethrough style
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getIndex() >= todoList.size()) {
                    System.out.println(String.format("clicked on: %s", item));
                    setText(null);
                    setStyle(null);
                } else {
                    text.setText(item);
                    TodoItem todoItem = getTableView().getItems().get(getIndex());

                    System.out.println(String.format("clicked on: %s %b", item, todoItem.isCompleted()));

                    text.setStrikethrough(todoItem.isCompleted()); // 여기서 줄긋기 ON/OFF
                    text.setStyle(todoItem.isCompleted() ? "-fx-fill: #888;" : "-fx-fill: #111;");
                    setGraphic(text); // 텍스트를 그래픽으로 설정, it means that the text will be displayed in the cell
                    setText(null);    // 텍스트는 이미 Text 객체에 설정되어 있으므로 null로 설정
                }
            }
        });
    }

    private void setInitTodoList() {
        todoList.add(new TodoItem("Buy groceries", "Milk, Bread, Eggs" ));
        todoList.add(new TodoItem("Walk the dog", "Evening walk in the park"));
        todoList.add(new TodoItem("Finish homework", "Math and Science assignments"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Todo Application");

        // Add some sample data
        setInitTodoList();

        // Initialize the TableView and its columns
        initTableView();

        // TextField and Button for adding new todo items can be added here
        initInputTitleField(inputTitleField, "Enter Title");
        initInputTitleField(inputDesField, "Enter Description");
        //inputDesField.setOnAction(e -> addButton.fire());

        initAddButton();
        initEditButton();

        inputBox.setAlignment(Pos.CENTER_LEFT);
        inputBox.setPadding(new Insets(10, 0, 10, 0));
        inputBox.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5");

        // VBox layout for the scene
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));

        // Secne which contains VBox root with 400, 300 dimensions
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application which will call the start method
    }
}
