module jfxtest {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.sun.jna;
	requires javafx.base;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml;
}
