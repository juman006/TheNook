
import java.awt.Panel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.NonWritableChannelException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.jws.Oneway;
import javax.swing.JOptionPane;

import org.omg.CORBA.VersionSpecHelper;
import org.omg.IOP.TaggedComponentHelper;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Main extends Application {
	Stage primaryStage;
	Scene scene;
	private static List<User> users = new ArrayList<>();
	private static List<Products> allProducts = new ArrayList<>();
    private static List<Category> allCategories = new ArrayList<>();
    private Cart cart = new Cart();


	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();// Creates a GridPane layout: A GridPane is a JavaFX container that arranges its
										// children in a grid of rows and columns. This lets you neatly position UI
										// elements.
		pane.setAlignment(Pos.CENTER);// Centers the GridPane within its parent container.
		// Pos.CENTER makes sure everything inside the GridPane is aligned to the
		// middle.
		pane.setHgap(1);// Sets the horizontal gap between columns to 5 pixels.
		// This adds spacing between items in different columns to prevent them from
		// being too close.
		pane.setVgap(1);// Sets the vertical gap between rows to 5 pixels.
		// This helps keep elements visually separated when stacked vertically.
		pane.setPadding(new Insets(5, 5, 5, 5));// Adds padding around the GridPane. The Insets define space on all four
												// sides:
		// 5 pixels on the top, right, bottom, and left.
		// This prevents the layout from touching the edges of the scene.
		pane.setStyle("-fx-background-color: saddlebrown;");

		VBox themeBox = new VBox();// VBox stacks elements vertically, one on top of the other.
		// HBox places elements side by side, in a horizontal row.
		themeBox.setAlignment(Pos.CENTER);// Aligns all child elements inside themeBox to the center.
		// Ensures that everything inside stays horizontally centered.
		themeBox.setSpacing(1);// Adds 1 pixels of space between each child element in the VBox.
		// Helps prevent UI elements from being too close together.
		// Adds themeBox to the GridPane (pane) at column 0, row 0.
		// Since themeBox is inside pane, it will follow GridPane's alignment rules.

		Image logo = new Image(
				new File("C:\\Users\\Mc\\eclipse-workspace\\TheNook\\Images\\logo.png").toURI().toString());
		ImageView l = new ImageView(logo);
		l.setFitHeight(340);
		l.setFitWidth(396);

		// Create the rectangle with a brown outline and light cream fill
		Rectangle backgroundRect = new Rectangle(400, 60);
		backgroundRect.setFill(Color.BEIGE); // Light cream color
		backgroundRect.setStroke(Color.SADDLEBROWN); // Brown outline
		backgroundRect.setStrokeWidth(7); // Thickness of the outline
		backgroundRect.setArcWidth(15); // Rounded corners
		backgroundRect.setArcHeight(15); // Rounded corners

		// Title label inside the rectangle
		Label title = new Label("The Nook");
		title.setTextFill(Color.SADDLEBROWN); // Matching text color with border
		title.setFont(Font.font("Freestyle Script", FontWeight.BOLD, 40));

		// Stack rectangle and title together
		StackPane titleContainer = new StackPane();
		titleContainer.getChildren().addAll(backgroundRect, title);
		titleContainer.setAlignment(Pos.CENTER);

		// Add both image and title to VBox
		themeBox.getChildren().addAll(l, titleContainer);
		pane.add(themeBox, 0, 0);// Adds themeBox to the GridPane (pane) at column 0, row 0.
		// Since themeBox is inside pane, it will follow GridPane's alignment rules.

		Label username = new Label("Username: ");
		username.setFont(Font.font("Freestyle Script", FontWeight.BOLD, 40));
		username.setTextFill(Color.BEIGE);
		pane.add(username, 0, 1);
		TextField userName = new TextField();
		pane.add(userName, 0, 2);
		Label password = new Label("Password: ");
		password.setFont(Font.font("Freestyle Script", FontWeight.BOLD, 40));
		password.setTextFill(Color.BEIGE);
		pane.add(password, 0, 3);
		PasswordField pswrd = new PasswordField();
		pane.add(pswrd, 0, 4);

		Button login = new Button("Login");
		login.setTextFill(Color.SADDLEBROWN);
		login.setFont(Font.font("Freestyle Script", FontWeight.BOLD, 30));

		GridPane.setHalignment(login, HPos.RIGHT);
		pane.add(login, 0, 19);

		UserReader(new File("C:\\Users\\Mc\\eclipse-workspace\\TheNook\\Users\\USERS.txt"));
		login.setOnAction(e -> {
			String user_name = userName.getText();
			String pass_word = pswrd.getText();

			boolean loginSuccess = false;

			for (User user : users) {
				if (user.getUsername().equals(user_name) && user.getPassword().equals(pass_word)) {
					loginSuccess = true;
					break;
				}
			}
			if (loginSuccess) {
				mainMenu(primaryStage);
			} else {
				JOptionPane.showMessageDialog(null, "Invalid password or username, please try again");
			}
		});

		// Scene scene = new Scene(pane, 700, 300);
		scene = new Scene(pane);
		scene.setRoot(pane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("The Nook");
		primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);

	}

	public static void UserReader(File file) {
		try (Scanner input = new Scanner(file)) {
			while (input.hasNextLine()) {
				String line = input.nextLine();
				String[] parts = line.split(" ");
				if (parts.length == 2) {
					String username = parts[0];
					String password = parts[1];
					users.add(new User(username, password));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
	
	
	
	
	
	
	
	

	public void mainMenu(Stage primaryStage) {
	    BorderPane root = new BorderPane();
	    root.setStyle("-fx-background-color: saddlebrown;");

	    Label welcome = new Label("Welcome to The Nook!");
	    welcome.setFont(Font.font("Freestyle Script", FontWeight.BOLD, 80));
	    welcome.setTextFill(Color.BEIGE);

	    VBox topBox = new VBox();
	    topBox.setAlignment(Pos.CENTER);
	    topBox.setPadding(new Insets(30, 10, 10, 10));
	    topBox.getChildren().add(welcome);

	    root.setTop(topBox);

	    GridPane catPane = new GridPane();
	    catPane.setHgap(30);
	    catPane.setVgap(30);
	    catPane.setPadding(new Insets(20));
	    catPane.setAlignment(Pos.TOP_CENTER);
	    
	    catPane.getChildren().clear();

	    root.setCenter(catPane);

	    File categoryFile = new File("C:\\Users\\Mc\\eclipse-workspace\\TheNook\\txtFiles\\Categories.txt");
	    CategoryReader(categoryFile);

	    File productFile = new File("C:\\Users\\Mc\\eclipse-workspace\\TheNook\\txtFiles\\Products.txt");
	    
	    int[][] positions = {
	    	    {0, 0}, // First image (Column 1, Row 1)
	    	    {1, 1}, // Second image (Column 2, Row 2)
	    	    {2, 0}, // Third image (Column 3, Row 1)
	    	    {3, 1}, // Fourth image (Column 4, Row 2)
	    	    {4, 0}  // Fifth image (Column 5, Row 1)
	    };
	    
	    
	    int p =0;
	    for (Category category : allCategories) {
	        File imgFile = new File("C:\\Users\\Mc\\eclipse-workspace\\TheNook\\Images\\" + category.getCategoryImageName());
	        if (imgFile.exists()) {
	            Image img = new Image(imgFile.toURI().toString());
	            ImageView imgView = new ImageView(img);
	            imgView.setFitHeight(200);
	            imgView.setFitWidth(200);

	            Button catButton = new Button(category.getCategoryName(), imgView);
	            catButton.setContentDisplay(ContentDisplay.TOP);
	            catButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-text-fill: saddlebrown;");
	            catButton.setOnAction(e -> {
	            	ProductReader(productFile);
	                showProducts(primaryStage, category.getCategoryName());
	            });

	            if (p < positions.length) {
	                catPane.add(catButton, positions[p][0], positions[p][1]);
	                p++;
	            }
	        } else {
	            System.out.println("Image not found: " + imgFile.getAbsolutePath());
	        }
	        
	         
	        
	    }
	    
	    Button me = new Button("About Me");
	    me.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
		me.setTextFill(Color.SADDLEBROWN);
	    catPane.add(me, 4, 2);
	    me.setOnAction(e->{
	    	aboutMe(primaryStage);
	    });
		
	    scene = new Scene(root);
	    primaryStage.setScene(scene);
	    primaryStage.setFullScreen(true);
	    primaryStage.show();
	}
	

	
	
	
	
	
	
	
	
	public static void CategoryReader(File file) {
		allCategories.clear();
	    try (Scanner input = new Scanner(file)) {
	        while (input.hasNextLine()) {
	            String line = input.nextLine();
	            String[] parts = line.split("/");
	            if (parts.length == 2) {
	                String name = parts[0].trim();
	                String imagePath = parts[1].trim();
	                allCategories.add(new Category(name, imagePath));
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	public static void ProductReader(File file) {
		allProducts.clear();
	    try (Scanner input = new Scanner(file)) {
	        while (input.hasNextLine()) {
	            String line = input.nextLine();
	            String[] parts = line.split("/");
	            if (parts.length == 5) {
	                String name = parts[0].trim();
	                String category = parts[1].trim();
	                String imagePath = parts[2].trim();
	                double price = Double.parseDouble(parts[3].trim());
	                String origin = parts[4].trim();
	                
	                
	                
	                allProducts.add(new Products(name, category, imagePath, price, origin));
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void showProducts(Stage primaryStage, String categoryName) {
	    GridPane productGrid = new GridPane();
	    productGrid.setPadding(new Insets(20));
	    productGrid.setHgap(20);
	    productGrid.setVgap(20);
	    productGrid.setAlignment(Pos.CENTER);
	    productGrid.setStyle("-fx-background-color: saddlebrown;");
	    
	    productGrid.getChildren().clear();

	    int col = 0;
	    int row = 0;

	    for (Products product : allProducts) {
	        if (product.getCategory().equalsIgnoreCase(categoryName)) {
	            File imageFile = new File("C:\\Users\\Mc\\eclipse-workspace\\TheNook\\Images\\" + product.getProdImgFile());
	            ImageView productImage = new ImageView();
	            if (imageFile.exists()) {
	                productImage.setImage(new Image(imageFile.toURI().toString()));
	            }
	            productImage.setFitHeight(220);
	            productImage.setFitWidth(220);

	            Label name = new Label(product.getName());
	            name.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
				name.setTextFill(Color.BEIGE);
	            
				Label origin = new Label("Made in: " + product.getOrigin());
				origin.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
				origin.setTextFill(Color.BEIGE);
	           
				Label price = new Label("Price: $" + product.getPrice());
				price.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
				price.setTextFill(Color.BEIGE);
				
				Button add = new Button("Add To Cart");
				add.setFont(Font.font("Calibri", FontWeight.BOLD, 23));
				add.setTextFill(Color.SADDLEBROWN);
				add.setOnAction(e->{
					cart.addProduct(product);
					System.out.println(product.getName() + " Added To Cart!");
				});
				
				VBox vbox = new VBox();
				vbox.getChildren().addAll(name,origin,price,add);
				
				productGrid.getChildren().addAll(productImage,vbox);
				
				HBox box = new HBox(20); 
	            box.setAlignment(Pos.CENTER_LEFT);
	            box.getChildren().addAll(productImage, vbox);

	            // Add elements to the grid in (col, row) positions
	            productGrid.add(box, col, row); 

	            col++;
	            if (col == 3) { // Move to next row after 3 items
	                col = 0;
	                row += 1; // Shift down to next row
	            }
	        }
	    }
	    
	    Button backButton = new Button("←");
		backButton.setFont(Font.font("Calibri", FontWeight.BOLD, 25));
		backButton.setTextFill(Color.SADDLEBROWN);
		backButton.setOnAction(e -> 
			mainMenu(primaryStage)
		);
		
		Button cartButton = new Button("🛒");
		cartButton.setFont(Font.font("Calibri", FontWeight.BOLD, 25));
		cartButton.setTextFill(Color.SADDLEBROWN);
		cartButton.setOnAction(e -> showCart(primaryStage));
		
		
		HBox top = new HBox(20);
	    top.setAlignment(Pos.CENTER_LEFT);
	    top.getChildren().addAll(backButton, cartButton);
	    
	    BorderPane productPane = new BorderPane();
	    productPane.setStyle("-fx-background-color: saddlebrown;");
	    productPane.setTop(top);
	    productPane.setCenter(productGrid);

	    

	    Scene scene = new Scene(productPane, 800, 600);
	    primaryStage.setScene(scene);
	    primaryStage.setFullScreen(true);
	    primaryStage.show();
	}
	
	private void showCart(Stage primaryStage) {
	    BorderPane cartPane = new BorderPane();
	    cartPane.setPadding(new Insets(20));
	    cartPane.setStyle("-fx-background-color: saddlebrown;");

	    
	    Label title = new Label("Your Shopping Cart");
	    title.setFont(Font.font("Calibri", FontWeight.BOLD, 40));
	    title.setTextFill(Color.BEIGE);

	    HBox topContainer = new HBox(20);
	    topContainer.setAlignment(Pos.TOP_LEFT);
	    Button backButton = new Button("←");
	    backButton.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
	    backButton.setTextFill(Color.SADDLEBROWN);
	    backButton.setOnAction(e -> mainMenu(primaryStage));
	    topContainer.getChildren().add(backButton);
	    topContainer.setAlignment(Pos.TOP_LEFT);
	    
	    

	    HBox titleBox = new HBox();
	    titleBox.setAlignment(Pos.CENTER);
	    titleBox.getChildren().add(title);

	    VBox topLayout = new VBox(10); 
	    topLayout.getChildren().addAll(topContainer, titleBox);
	    cartPane.setTop(topLayout); 

	    
	    VBox cartItemsBox = new VBox(20);
	    cartItemsBox.setPadding(new Insets(20));
	    cartItemsBox.setAlignment(Pos.TOP_LEFT);
	    cartItemsBox.setStyle("-fx-background-color: transparent;");
	    
	    
	    ScrollPane scrollPane = new ScrollPane(cartItemsBox);
	    scrollPane.setFitToWidth(true); 
	    cartItemsBox.setPrefHeight(700);
	    scrollPane.setStyle("-fx-background: saddlebrown; -fx-border-color: saddlebrown;");
 
	    cartPane.setCenter(scrollPane);

	    ArrayList<Products> displayedProducts = new ArrayList<>();

	    for (Products item : cart.getCartItems()) {
	        int count = 0;
	        double totalPrice = 0;

	        // Count how many times this item appears in the cart
	        for (Products product : cart.getCartItems()) {
	            if (product.getName().equals(item.getName())) {
	                count++;
	                totalPrice += product.getPrice();
	            }
	        }

	        // Prevent duplicates in display
	        boolean alreadyDisplayed = false;
	        for (Products displayedItem : displayedProducts) {
	            if (displayedItem.getName().equals(item.getName())) {
	                alreadyDisplayed = true;
	                break;
	            }
	        }
	        if (!alreadyDisplayed) {
	            displayedProducts.add(item);
	            Label productLabel = new Label(item.getName() + " x" + count + "\nPrice: $" + String.format("%.2f", totalPrice));
	            productLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 25));
	            productLabel.setTextFill(Color.BEIGE);
	            cartItemsBox.getChildren().add(productLabel);
	        }
	    }

	    // 🖨 Print Receipt Button (Bottom Right)
	    Button printReceiptButton = new Button("Print Receipt");
	    printReceiptButton.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
	    printReceiptButton.setTextFill(Color.SADDLEBROWN);
	    printReceiptButton.setOnAction(e -> printReceipt());

	    HBox bottomSection = new HBox();
	    bottomSection.setAlignment(Pos.BOTTOM_RIGHT);
	    bottomSection.getChildren().add(printReceiptButton);
	    cartPane.setBottom(bottomSection);

	    

	    // 🎬 Set Scene
	    Scene cartScene = new Scene(cartPane, 800, 600);
	    primaryStage.setScene(cartScene);
	    primaryStage.setFullScreen(true);
	    primaryStage.show();
	}


	private void printReceipt() {
	    LocalDate today = LocalDate.now();
	    String formattedDate = today.toString();

	    double totalCost = 0;

	    String receiptContent = "\n ~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n" +
	                            " Welcome to The Nook! \n" +
	                            " Date: " + formattedDate + "\n" +
	                            " ~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n";

	    for (Products item : cart.getCartItems()) {
	        int quantity = getItemCount(item.getName());
	        double itemTotal = item.getPrice() * quantity;

	        receiptContent += " " + item.getName() + " x" + quantity + "\n" +
	                          " Price: $" + String.format("%.2f", itemTotal) + "\n\n";

	        totalCost += itemTotal;
	    }

	    receiptContent += " ~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n" +
	                      " Grand Total: $" + String.format("%.2f", totalCost) + "\n" +
	                      " ~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n" +
	                      " Thank you for shopping at The Nook! \n" +
	                      " ~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n";
	try {
        FileWriter writer = new FileWriter("Receipt.txt");
        writer.write(receiptContent.toString());
        writer.close();
        System.out.println("Receipt printed to Receipt.txt ");
    } catch (IOException e) {
        e.printStackTrace();
    }}

	// 🛒 Helper function to count items in cart
	private int getItemCount(String itemName) {
	    int count = 0;
	    for (Products item : cart.getCartItems()) {
	        if (item.getName().equals(itemName)) {
	            count++;
	        }
	    }
	    return count;
	}

	
	
	public void aboutMe(Stage stage) {
	    VBox root = new VBox(10); 
	    root.setStyle("-fx-alignment: center; -fx-padding: 20px;");

	    // Load the image
	    Image me = new Image(new File("C:\\Users\\Mc\\eclipse-workspace\\TheNook\\Images\\me.jpeg").toURI().toString());
	    ImageView imageView = new ImageView(me);
	    imageView.setFitWidth(300); 
	    imageView.setFitHeight(400);
	    

	    // Create text labels
	    Label nameText = new Label("My Name is Juman");
	    nameText.setFont(Font.font("Calibri", FontWeight.BOLD, 50));
	    nameText.setTextFill(Color.SADDLEBROWN);
	    Label studentNumberText = new Label("My Student Number: 202404205");
	    studentNumberText.setFont(Font.font("Calibri", FontWeight.BOLD, 50));
	    studentNumberText.setTextFill(Color.SADDLEBROWN);

	    // Add elements to the layout
	    root.getChildren().addAll(imageView, nameText, studentNumberText);

	    // Set up the scene and stage
	    Scene scene = new Scene(root, 400, 300);
	    stage.setScene(scene);
	    stage.setTitle("About Me");
	    stage.setFullScreen(true);
	    stage.show();
	}
}
	
	
	
		


