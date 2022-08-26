package application;

import java.time.LocalDate;
import java.time.Month;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {
	
	private TableView<Reservation> tableView = new TableView<Reservation>();
	
	private ObservableList<Reservation> data = 
			FXCollections.observableArrayList(
					new Reservation(LocalDate.of(2022, Month.AUGUST,21), "10:00 AM", "John Doe", "3", "1"), 
					new Reservation(LocalDate.of(2022, Month.AUGUST,21), "10:30 AM", "Allie Grater", "1", "2"), 
					new Reservation(LocalDate.of(2022, Month.AUGUST,21), "11:30 AM", "John Mason", "2", "3")
					);
	
	private HBox hbox = new HBox();
	private VBox vbox = new VBox();
	 
	@Override
	public void start(Stage stage) {
		Label label = new Label("Reservations");
		label.setFont(new Font("Arial", 20));

		tableView.setEditable(true);
		Callback<TableColumn, TableCell> cellFactory = 
				new Callback<TableColumn, TableCell>() {
			public TableCell call (TableColumn p) {
				return new CellEditing();
			}
		};
		
		TableColumn numberCol = new TableColumn("#");
        numberCol.setMinWidth(20);
        numberCol.setCellValueFactory(new Callback<CellDataFeatures<Reservation, Reservation>, ObservableValue<Reservation>>() {
            @Override public ObservableValue<Reservation> call(CellDataFeatures<Reservation, Reservation> p) {
                return new ReadOnlyObjectWrapper(p.getValue());
            }
        });

        numberCol.setCellFactory(new Callback<TableColumn<Reservation, Reservation>, TableCell<Reservation, Reservation>>() {
            @Override 
            public TableCell<Reservation, Reservation> call(TableColumn<Reservation, Reservation> param) {
                return new TableCell<Reservation, Reservation>() {
                    @Override 
                    protected void updateItem(Reservation item, boolean empty) {
                        super.updateItem(item, empty);

                        if (this.getTableRow() != null && item != null) {
                            setText(this.getTableRow().getIndex() + 1 + "");
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        numberCol.setSortable(false);
		
		TableColumn<Reservation, LocalDate> dateCol = new TableColumn("Date"); 
		dateCol.setMinWidth(100);
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		dateCol.setCellFactory(col -> new DateCellEditing());
		dateCol.setEditable(true);
		dateCol.setOnEditCommit(event -> event.getRowValue().setDate(event.getNewValue()));
		
		TableColumn timeCol = new TableColumn("Time"); 
		timeCol.setMinWidth(100);
		timeCol.setCellValueFactory( new PropertyValueFactory<Reservation,
				String>("time"));
		timeCol.setCellFactory(cellFactory);
        timeCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Reservation, String>>() {
                @Override
                public void handle(CellEditEvent<Reservation, String> t) {
                    ((Reservation) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setTime(t.getNewValue());
                }
             }
        );

		TableColumn nameCol = new TableColumn("Name"); 
		nameCol.setMinWidth(200);
		nameCol.setCellValueFactory( new PropertyValueFactory<Reservation,
				String>("name"));
		nameCol.setCellFactory(cellFactory);
        nameCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Reservation, String>>() {
                @Override
                public void handle(CellEditEvent<Reservation, String> t) {
                    ((Reservation) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                }
             }
        );

		TableColumn adultCol = new TableColumn("Adult #"); 
		adultCol.setMinWidth(50);
		adultCol.setCellValueFactory( new PropertyValueFactory<Reservation,
				String>("adults"));
		adultCol.setCellFactory(cellFactory);
        adultCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Reservation, String>>() {
                @Override
                public void handle(CellEditEvent<Reservation, String> t) {
                    ((Reservation) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setAdults(t.getNewValue());
                }
             }
        );

		TableColumn childrenCol = new TableColumn("Children #");
		childrenCol.setMinWidth(50); 
		childrenCol.setCellValueFactory( new
				PropertyValueFactory<Reservation, String>("children"));
		childrenCol.setCellFactory(cellFactory);
        childrenCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Reservation, String>>() {
                @Override
                public void handle(CellEditEvent<Reservation, String> t) {
                    ((Reservation) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setChildren(t.getNewValue());
                }
             }
        );
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            Reservation selectedItem = tableView.getSelectionModel().getSelectedItem();
            tableView.getItems().remove(selectedItem);
        });
		
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setItems(data);
		tableView.getColumns().addAll(numberCol, dateCol, timeCol, nameCol, adultCol, childrenCol);
		
		
		final DatePicker addDate = new DatePicker();
		addDate.setPromptText("Date");
		addDate.setMinWidth(100);
		addDate.setMaxWidth(dateCol.getPrefWidth());
		final TextField addTime = new TextField();
		addTime.setPromptText("Time");
		addTime.setMaxWidth(timeCol.getPrefWidth());
		final TextField addName = new TextField();
		addName.setPromptText("Name");
		addName.setMinWidth(150);
		addName.setMaxWidth(nameCol.getPrefWidth());
		final TextField addAdult = new TextField();
		addAdult.setPromptText("No. of Adult");
		addAdult.setMaxWidth(adultCol.getPrefWidth());
		final TextField addChildren = new TextField();
		addChildren.setPromptText("No. of Children");
		addChildren.setMaxWidth(childrenCol.getPrefWidth());
		
		final Button addButton = new Button("Add");
		
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.add(new Reservation(
						addDate.getValue(),
						addTime.getText(),
						addName.getText(),
						addAdult.getText(),
						addChildren.getText()));
				addDate.getEditor().clear();
				addTime.clear();
				addName.clear();
				addAdult.clear();
				addChildren.clear();
			}
		});
		
		hbox.setSpacing(10);
		hbox.getChildren().addAll(addDate, addTime, addName, addAdult, addChildren, addButton, deleteButton);
		
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, tableView, hbox);
		
		Scene scene = new Scene(new Group());
		((Group) scene.getRoot()).getChildren().addAll(vbox);
		
		stage.setWidth(700);
		stage.setHeight(550);
		stage.setTitle("Restaurant Dining Reservation System");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
