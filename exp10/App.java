package com.anshumaan;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    private DCHandler dcHandler;
    private TextArea outputArea;
    private VBox formContainer;

    @Override
    public void start(Stage stage) {
        DBConn dbConn = new DBConn();
        dcHandler = new DCHandler(dbConn);
        dcHandler.initializeDatabase();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        MenuBar menuBar = buildMenuBar();
        formContainer = new VBox();
        formContainer.setPadding(new Insets(10));
        formContainer.getChildren().add(new Label("Select an operation from the menu."));

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(14);

        root.setTop(menuBar);
        root.setCenter(formContainer);
        root.setBottom(outputArea);

        Scene scene = new Scene(root, 900, 620);
        stage.setTitle("JDBC CRUD Menu App - Restaurant/MenuItem");
        stage.setScene(scene);
        stage.show();
    }

    private MenuBar buildMenuBar() {
        Menu restaurantMenu = new Menu("Restaurant");
        MenuItem rInsert = new MenuItem("Insert");
        MenuItem rSelect = new MenuItem("Select All");
        MenuItem rUpdate = new MenuItem("Update Address");
        MenuItem rDelete = new MenuItem("Delete");

        rInsert.setOnAction(e -> showRestaurantInsertForm());
        rSelect.setOnAction(e -> outputArea.setText(dcHandler.selectAllRestaurants()));
        rUpdate.setOnAction(e -> showRestaurantUpdateForm());
        rDelete.setOnAction(e -> showRestaurantDeleteForm());
        restaurantMenu.getItems().addAll(rInsert, rSelect, rUpdate, rDelete);

        Menu menuItemMenu = new Menu("MenuItem");
        MenuItem mInsert = new MenuItem("Insert");
        MenuItem mSelectAll = new MenuItem("Select All");
        MenuItem mSelectByPrice = new MenuItem("Select By Price <=");
        MenuItem mSelectByRestaurant = new MenuItem("Select By Restaurant Name");
        MenuItem mUpdate = new MenuItem("Update Price");
        MenuItem mDelete = new MenuItem("Delete");

        mInsert.setOnAction(e -> showMenuItemInsertForm());
        mSelectAll.setOnAction(e -> outputArea.setText(dcHandler.selectAllMenuItems()));
        mSelectByPrice.setOnAction(e -> showMenuItemSelectByPriceForm());
        mSelectByRestaurant.setOnAction(e -> showMenuItemSelectByRestaurantForm());
        mUpdate.setOnAction(e -> showMenuItemUpdateForm());
        mDelete.setOnAction(e -> showMenuItemDeleteForm());
        menuItemMenu.getItems().addAll(mInsert, mSelectAll, new SeparatorMenuItem(), mSelectByPrice,
                mSelectByRestaurant, new SeparatorMenuItem(), mUpdate, mDelete);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(restaurantMenu, menuItemMenu);
        return menuBar;
    }

    private void showRestaurantInsertForm() {
        GridPane grid = baseGrid();

        TextField id = new TextField();
        TextField name = new TextField();
        TextField address = new TextField();
        Button submit = new Button("Insert Restaurant");

        grid.add(new Label("Id:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(name, 1, 1);
        grid.add(new Label("Address:"), 0, 2);
        grid.add(address, 1, 2);
        grid.add(submit, 1, 3);

        submit.setOnAction(e -> {
            try {
                String result = dcHandler.insertRestaurant(
                        Integer.parseInt(id.getText().trim()),
                        name.getText().trim(),
                        address.getText().trim());
                outputArea.setText(result + "\n\n" + dcHandler.selectAllRestaurants());
            } catch (NumberFormatException ex) {
                outputArea.setText("Id must be a number.");
            }
        });

        setForm(grid);
    }

    private void showRestaurantUpdateForm() {
        GridPane grid = baseGrid();

        TextField id = new TextField();
        TextField address = new TextField();
        Button submit = new Button("Update Address");

        grid.add(new Label("Restaurant Id:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("New Address:"), 0, 1);
        grid.add(address, 1, 1);
        grid.add(submit, 1, 2);

        submit.setOnAction(e -> {
            try {
                String result = dcHandler.updateRestaurantAddress(
                        Integer.parseInt(id.getText().trim()),
                        address.getText().trim());
                outputArea.setText(result + "\n\n" + dcHandler.selectAllRestaurants());
            } catch (NumberFormatException ex) {
                outputArea.setText("Restaurant Id must be a number.");
            }
        });

        setForm(grid);
    }

    private void showRestaurantDeleteForm() {
        GridPane grid = baseGrid();

        TextField id = new TextField();
        Button submit = new Button("Delete Restaurant");

        grid.add(new Label("Restaurant Id:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(submit, 1, 1);

        submit.setOnAction(e -> {
            try {
                String result = dcHandler.deleteRestaurant(Integer.parseInt(id.getText().trim()));
                outputArea.setText(result + "\n\n" + dcHandler.selectAllRestaurants());
            } catch (NumberFormatException ex) {
                outputArea.setText("Restaurant Id must be a number.");
            }
        });

        setForm(grid);
    }

    private void showMenuItemInsertForm() {
        GridPane grid = baseGrid();

        TextField id = new TextField();
        TextField name = new TextField();
        TextField price = new TextField();
        TextField resId = new TextField();
        Button submit = new Button("Insert MenuItem");

        grid.add(new Label("Id:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(name, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(price, 1, 2);
        grid.add(new Label("Restaurant Id:"), 0, 3);
        grid.add(resId, 1, 3);
        grid.add(submit, 1, 4);

        submit.setOnAction(e -> {
            try {
                String result = dcHandler.insertMenuItem(
                        Integer.parseInt(id.getText().trim()),
                        name.getText().trim(),
                        Double.parseDouble(price.getText().trim()),
                        Integer.parseInt(resId.getText().trim()));
                outputArea.setText(result + "\n\n" + dcHandler.selectAllMenuItems());
            } catch (NumberFormatException ex) {
                outputArea.setText("Id, Price and Restaurant Id must be numbers.");
            }
        });

        setForm(grid);
    }

    private void showMenuItemSelectByPriceForm() {
        GridPane grid = baseGrid();

        TextField maxPrice = new TextField();
        Button submit = new Button("Select MenuItems");

        grid.add(new Label("Max Price:"), 0, 0);
        grid.add(maxPrice, 1, 0);
        grid.add(submit, 1, 1);

        submit.setOnAction(e -> {
            try {
                outputArea.setText(dcHandler.selectMenuItemsByPrice(
                        Double.parseDouble(maxPrice.getText().trim())));
            } catch (NumberFormatException ex) {
                outputArea.setText("Max Price must be a number.");
            }
        });

        setForm(grid);
    }

    private void showMenuItemSelectByRestaurantForm() {
        GridPane grid = baseGrid();

        TextField restaurantName = new TextField();
        Button submit = new Button("Select MenuItems");

        grid.add(new Label("Restaurant Name:"), 0, 0);
        grid.add(restaurantName, 1, 0);
        grid.add(submit, 1, 1);

        submit.setOnAction(e -> outputArea.setText(
                dcHandler.selectMenuItemsByRestaurantName(restaurantName.getText().trim())));

        setForm(grid);
    }

    private void showMenuItemUpdateForm() {
        GridPane grid = baseGrid();

        TextField id = new TextField();
        TextField price = new TextField();
        Button submit = new Button("Update Price");

        grid.add(new Label("MenuItem Id:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("New Price:"), 0, 1);
        grid.add(price, 1, 1);
        grid.add(submit, 1, 2);

        submit.setOnAction(e -> {
            try {
                String result = dcHandler.updateMenuItemPrice(
                        Integer.parseInt(id.getText().trim()),
                        Double.parseDouble(price.getText().trim()));
                outputArea.setText(result + "\n\n" + dcHandler.selectAllMenuItems());
            } catch (NumberFormatException ex) {
                outputArea.setText("MenuItem Id and New Price must be numbers.");
            }
        });

        setForm(grid);
    }

    private void showMenuItemDeleteForm() {
        GridPane grid = baseGrid();

        TextField id = new TextField();
        Button submit = new Button("Delete MenuItem");

        grid.add(new Label("MenuItem Id:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(submit, 1, 1);

        submit.setOnAction(e -> {
            try {
                String result = dcHandler.deleteMenuItem(Integer.parseInt(id.getText().trim()));
                outputArea.setText(result + "\n\n" + dcHandler.selectAllMenuItems());
            } catch (NumberFormatException ex) {
                outputArea.setText("MenuItem Id must be a number.");
            }
        });

        setForm(grid);
    }

    private GridPane baseGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        return grid;
    }

    private void setForm(GridPane grid) {
        formContainer.getChildren().setAll(grid);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
