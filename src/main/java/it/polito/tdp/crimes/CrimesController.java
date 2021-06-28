/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Arco;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Arco> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo...\n");
    	
    	String categoria = boxCategoria.getValue();
    	Integer mese = boxMese.getValue();
    	if(categoria == null || mese == null) {
    		txtResult.appendText("Devi scegliere una categoria e un mese!");
    		return;
    	}
    	
    	this.model.creaGrafo(categoria, mese);
    	txtResult.appendText("# VERTICI: " + model.nVertici()+"\n");
    	txtResult.appendText("# ARCHI: " + model.nArchi()+ "\n");
    	
    	txtResult.appendText("Archi con peso superiore alla media: \n");
    	for(Arco a: this.model.getArchiSupMedia()) {
    		txtResult.appendText(a.toString()+"\n");
    	}
    	boxArco.getItems().addAll(this.model.getArchiSupMedia());
    	
    }
    
    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso...\n");
    	
    	Arco a = boxArco.getValue();
    	if(a == null) {
    		txtResult.appendText("Devi scegliere un arco!");
    		return;
    	}
    	String partenza = a.getId1();
    	String arrivo = a.getId2();
    	
    	this.model.trovaPercorso(partenza, arrivo);
    	for(String s: this.model.trovaPercorso(partenza, arrivo)) {
    		txtResult.appendText(s+"\n");
    	}
    	
    	
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxCategoria.getItems().addAll(this.model.getCategorie());
    	boxMese.getItems().addAll(this.model.getMesi());
    }
}
