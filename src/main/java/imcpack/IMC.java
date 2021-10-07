package imcpack;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {
	TextField pesoText;
	TextField alturaText;
	Label pesoLabel;
	Label kgLabel;
	Label alturaLabel;
	Label cmLabel;
	Label imcLabel;
	Label clasificacionLabel;
	StringProperty totalIMC = new SimpleStringProperty();
	StringProperty textoPeso = new SimpleStringProperty(this, "textoPeso");
	StringProperty textoAltura = new SimpleStringProperty(this, "textoAltura");
	DoubleProperty peso = new SimpleDoubleProperty(this, "peso");
	DoubleProperty altura = new SimpleDoubleProperty(this, "altura");
	DoubleProperty total = new SimpleDoubleProperty();
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
			// TextFields
		
				pesoText = new TextField();
				alturaText = new TextField();
				
				// Labels
				
				pesoLabel = new Label("Peso:");
				kgLabel = new Label("kg");
				alturaLabel = new Label("Altura:");
				cmLabel = new Label("cm");
				imcLabel = new Label("IMC: (peso * altura^ 2)");
				clasificacionLabel = new Label("Bajo peso | Normal | Sobrepeso | Obeso");
				
				//alineamos al centro los hbox y vbox
				HBox pesoBox = new HBox(5, pesoLabel, pesoText, kgLabel);
				pesoBox.setAlignment(Pos.CENTER);
				HBox alturaBox = new HBox(5, alturaLabel, alturaText, cmLabel);
				alturaBox.setAlignment(Pos.CENTER);
				//
				HBox imcBox = new HBox(imcLabel); 
				imcBox.setAlignment(Pos.CENTER);
				VBox root = new VBox(4, pesoBox, alturaBox, imcBox, clasificacionLabel);
				root.setAlignment(Pos.CENTER);
				root.setFillWidth(false);

				// Scene
				
				Scene scene = new Scene(root, 320, 200);
				primaryStage.setTitle("PRACTICA IMC");
				primaryStage.setScene(scene);
				primaryStage.show();
				//Realizamos los bindings y los calculos 
				textoPeso.bindBidirectional(pesoText.textProperty());
				textoAltura.bindBidirectional(alturaText.textProperty());
				Bindings.bindBidirectional(textoPeso, peso, new NumberStringConverter());
				Bindings.bindBidirectional(textoAltura, altura, new NumberStringConverter());
				DoubleBinding resultado = ((peso.divide(altura.multiply(altura))).multiply(10000d));
				total.bind(resultado);
				
				totalIMC.set("IMC: ");
				
				imcLabel.textProperty().bind(totalIMC.concat(resultado.asString("%.2f\n")));
				
				total.addListener((o, ov, nv) -> clasificaPeso(nv));
	}
		public void clasificaPeso(Number nv) {
		
		if (nv.doubleValue() < 18.5) {

			clasificacionLabel.setText("Peso Bajo");
			
		} else if (nv.doubleValue() >= 18.5 && nv.doubleValue() < 25) {
			
			clasificacionLabel.setText("Normal");
			
		} else if (nv.doubleValue() >= 25 && nv.doubleValue() < 30) {
			
			clasificacionLabel.setText("Sobrepeso");

		} else {
			
			clasificacionLabel.setText("Obeso");

		}
			
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
