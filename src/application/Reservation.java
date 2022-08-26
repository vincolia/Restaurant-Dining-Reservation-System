package application;

import java.time.LocalDate;
import java.time.MonthDay;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Reservation {
	
	private int id;
	private SimpleObjectProperty date;
	private SimpleStringProperty time;
	private SimpleStringProperty name;
	private SimpleStringProperty adults;
	private SimpleStringProperty children;
	
	public Reservation(LocalDate date, String time, String name, String adults, String children) {
		this.id = 0;
		this.date = new SimpleObjectProperty(date);
		this.time = new SimpleStringProperty(time);
		this.name = new SimpleStringProperty(name);
		this.adults = new SimpleStringProperty(adults);
		this.children = new SimpleStringProperty(children);
	}

	public void setId() {
		id++;
	}
	
	public int getId() {
		return this.id;
	}
	
	public ObjectProperty<LocalDate> dateProperty() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date.set(date);
	}
		
	public final LocalDate getDate() {
        return dateProperty().get();
    }
	
	public void setTime(String time) {
		this.time.set(time);
	}
	public String getTime() {
		return time.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}

	public String getName() {
		return name.get();
	}

	public void setAdults(String number) {
		adults.set(number);
	}
	
	public String getAdults() {
		return adults.get();
	}
	
	public void setChildren(String number) {
		children.set(number);
	}

	public String getChildren() {
		return children.get();
	}
	

}
