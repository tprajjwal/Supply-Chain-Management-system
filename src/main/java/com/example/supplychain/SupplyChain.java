package com.example.supplychain;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



//Application refer to java fx,java fx is gui toolkit for java program like swing;
// Application is parent class ,SupplyChain is Derived class,(Inheritance);
public class SupplyChain extends Application {
    // following are properties of supply chain class;
    Button loginButton;

    Pane bodyPane; // it is for searched product;

    boolean customerLoggedIn = false;
    String customerEmail = "";

    Label welcomeUser;

    // static keyword means the value is the same for every instance(variables of object) of the class
    // final keyword means once the variable is assigned a value it can never be changed.
    // So these combination make value constant;
    public static final int width = 700, height = 400, upperLine = 50;

    ProductDetails productDetails = new ProductDetails();

    private Pane headerBar(){
        Pane topPane = new Pane();
        topPane.setPrefSize(width, upperLine-10);

        TextField searchText = new TextField();
        searchText.setPromptText("Please search here");
        searchText.setTranslateX(100); // for setting position;

        int searchEnd = 400;

        Button searchButton = new Button("Search");
        searchButton.setTranslateX(searchEnd);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {

            // following code is genrated by .setOnAction;
            public void handle(ActionEvent actionEvent) {
                //Product which we search is done by following code
                String search = searchText.getText(); //The thing which we write on UI is store in this search variable
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productDetails.getProductsByName(search));
            }
        });

        loginButton = new Button("Login");
        loginButton.setTranslateX(searchEnd+80);
        //Now we want,when we click on login button , product detail box will hide , and login grid will show ,this thing is done by following code
        loginButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
            }
        });

        welcomeUser = new Label("SCMs JAVA PROJECT");
        welcomeUser.setTranslateX(searchEnd + 160);

        topPane.getChildren().addAll(searchText, searchButton, loginButton,  welcomeUser);
        topPane.setTranslateY(10);

        return topPane;
    }
    // footerBar is for order ;All code is almost similar to header bar
    private Pane footerBar(){
        Pane bottomPane = new Pane();
        bottomPane.setPrefSize(width, upperLine-10);
        bottomPane.setTranslateY(upperLine+height+10);

        int searchEnd = 400;
        Button buyNowButton = new Button("Buy Now");
        buyNowButton.setTranslateX(searchEnd);
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                String userEmail;
                int productId;
                userEmail = customerEmail;
                productId = productDetails.getProductId();
                if(!userEmail.isBlank() && productId!=-1){
                    boolean status =  Order.orderProduct(productId,userEmail);
                    if(status){
                        System.out.println("Order Placed");
                    }
                    else {
                        System.out.println("Order Failed");
                    }
                }
                else {
                    System.out.println("Invalid Order Values");
                }

            }
        });

        bottomPane.getChildren().addAll(buyNowButton);
        return bottomPane;
    }

    private GridPane loginPage(){
        Label emilLabel = new Label("E-mail");
        Label passwordLabel = new Label("Password");

        TextField emailText = new TextField();
        emailText.setPromptText("Please enter email");

        PasswordField passwordText = new PasswordField();
        passwordText.setPromptText("Please enter password");

        Button localLoginButton = new Button("Login");

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Login Message");
        dialog.setContentText("Login failed !! Please enter correct email and password!");

        ButtonType buttonType = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().add(buttonType);

        // code for login process;
        localLoginButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                String email = emailText.getText();
                String password = passwordText.getText();
                if(email.isBlank() || password.isBlank()){

                }
                else{
                    if(Login.customerLogin(email,password)){
                        customerLoggedIn = true;
                        customerEmail = email;
                        welcomeUser.setText("Welcome " + email); // after login;
                        bodyPane.getChildren().clear();
                        bodyPane.getChildren().add(productDetails.getAllProducts());
                    }
                    else{
                        welcomeUser.setText("Login Failed");
//                        emailText.clear();
                        passwordText.clear();
                        dialog.showAndWait();

                    }
                }

            }
        });

        Button clearButton = new Button("Clear");
        // on clicking clear button ,all things should delete ,this is done by following code;
        clearButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                emailText.setText("");
                passwordText.setText("");
            }
        });

        GridPane gridPane = new GridPane();

        gridPane.setMinSize(bodyPane.getWidth(), bodyPane.getHeight());

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10); //all vertical things have gaps of 10 on login page;
        gridPane.setHgap(10); //all horizontal things have gaps of 10 on login page;

        gridPane.add(emilLabel, 0,0);
        gridPane.add(emailText,1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordText, 1, 1);
        gridPane.add(localLoginButton, 0, 2);
        gridPane.add(clearButton, 1, 2);


        return gridPane;
    }

    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width, height+2*upperLine+20);
         //bodyPane is for searched product
        bodyPane = new Pane();
        bodyPane.setPrefSize(width,height);
        bodyPane.setTranslateY(upperLine);

        bodyPane.getChildren().add(productDetails.getAllProducts());

        root.getChildren().addAll(headerBar(), bodyPane, footerBar());
        return root;
    }


    public void start(Stage stage) {
        // these are functionality of application class;
        Scene scene = new Scene(createContent());
        stage.setTitle("Supply Chain Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(); // javafx keyword ,used to call main method;
    }
}