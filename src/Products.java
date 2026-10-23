import java.io.File;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Products {
	private String name;
	private String category;
	private String prodImgFile;
	private double price;
	private String origin;
	
	Products(){}

	public Products(String name, String category, String prodImgFile, double price, String origin) {
		this.name = name;
		this.category = category;
		this.prodImgFile = prodImgFile;
		this.price = price;
		this.origin = origin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProdImgFile() {
		return prodImgFile;
	}

	public void setProdImgFile(String prodImgFile) {
		this.prodImgFile = prodImgFile;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	



}

