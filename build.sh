javac --module-path lib/ --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base,javafx.web,javafx.media -cp lib/mysql-connector-j-9.2.0.jar *.java
java --module-path lib/ --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base,javafx.web,javafx.media -cp lib/mysql-connector-j-9.2.0.jar:. CRUD
