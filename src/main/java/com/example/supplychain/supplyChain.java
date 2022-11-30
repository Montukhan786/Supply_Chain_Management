package com.example.supplychain;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class supplyChain extends Application {

    public static final int height = 600, width = 700, upperLine = 50;
    Pane bodyPen = new Pane();

    public login logIn = new login();

    ProductDetails productDetails = new ProductDetails();

    boolean loggedIn = false;

    Label loginLabel;

    Button orederButton;

    private GridPane hearBar(){
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(width,upperLine-5);  //width,upperLine-5
        gridPane.setAlignment(Pos.CENTER);

        TextField searchText = new TextField();
        searchText.setMinWidth(240);//250
        searchText.setPromptText("Please search here");
        loginLabel = new Label("Please LogIn !");
        gridPane.setHgap(5);
        Button loginButton = new Button("LogIn");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedIn == false){
                    bodyPen.getChildren().clear();
                    bodyPen.getChildren().add(loginPage());
                    //loginButton.setText("Logout");
                }

            }
        });

        Button searchButton = new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPen.getChildren().clear();
                //bodyPen.getChildren().add(productDetails.getAllProducts());
                String search = searchText.getText();
                bodyPen.getChildren().add(productDetails.getAllProductsByName(search));
            }
        });
        gridPane.add(searchText,0,0);
        gridPane.add(searchButton,1,0);
        gridPane.add(loginLabel,2,0);
        gridPane.add(loginButton,3,0);

        return gridPane;


    }

    private GridPane footerBar(){
        GridPane gridPane = new GridPane();
        orederButton = new Button("Buy Now");

        orederButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedIn == false){
                    bodyPen.getChildren().clear();
                    bodyPen.getChildren().add(loginPage());
                }
                else{
                    Product product = productDetails.getSelectedProduct();
                    if(product != null){
                        String email = loginLabel.getText();
                        email = email.substring(10);
                        System.out.println(email);

                        if(Order.placeSingleOrder(product,email)){
                            System.out.println("Order Placed");
                        }
                        else{
                            System.out.println("Order Failed !");
                        }
                    }
                    else{
                        System.out.println("Please select a Product");
                    }
                }

            }
        });

        gridPane.add(orederButton,0,0);
        gridPane.setMinWidth(width);
        //gridPane.setHgap(10);
        gridPane.setTranslateY(height+80);//height+80

        return gridPane;

    }
    private GridPane loginPage(){
        Label emailLabel = new Label("E-mail");

        Label passwordLabel = new Label("Password");

        Label messageLabel = new Label("I am a message");

        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Enter a Email");

        PasswordField passwordTextField = new PasswordField();
        passwordTextField.setPromptText("Enter a Password");

        Button loginButton = new Button("Login");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        String email = emailTextField.getText();
                                        String password = passwordTextField.getText();
                                        if(logIn.customerLogin(email,password)){
                                            loginLabel.setText("Welcome : "+ email);
                                            bodyPen.getChildren().clear();
                                            bodyPen.getChildren().add(productDetails.getAllProducts());
                                            loggedIn = true;
                                            messageLabel.setText("Login Succesfull");
                                        }
                                        else{
                                            messageLabel.setText("Invalid User !");
                                        }

                                        //messageLabel.setText("email - " + email + " && passwpord - "+ password);
                                    }
                                });

                GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPen.getMinWidth(),bodyPen.getMinHeight());
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
//        gridPane.setStyle("-fx-background-color: #C0C9C0;");

          gridPane.add(emailLabel,0,0);
          gridPane.add(emailTextField,1,0);
          gridPane.add(passwordLabel,0,1);
          gridPane.add(passwordTextField,1,1);
          gridPane.add(loginButton,0,3);
          gridPane.add(messageLabel,1,3);

        return gridPane;
    }
    Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width,height+upperLine+80);
        bodyPen.setTranslateY(upperLine);
        bodyPen.setMinSize(width,height);
        //bodyPen.setStyle("-fx-background-color: #C0C9C0;");
        bodyPen.getChildren().add(productDetails.getAllProducts());

        root.getChildren().addAll(
                hearBar(),
                bodyPen,
                footerBar()
        );

        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Supply Chain System!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}