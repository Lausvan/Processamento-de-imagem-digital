package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrincipalController {

	@FXML
	ImageView imageView1;
	@FXML
	ImageView imageView2;
	@FXML
	ImageView imageView3;

	@FXML
	Label lblR;
	@FXML
	Label lblG;
	@FXML
	Label lblB;
	
	@FXML 
	TextField txtPctR;
	@FXML 
	TextField txtPctG;
	@FXML 
	TextField txtPctB;
	
	@FXML
	Slider slider;
	
	@FXML
	Slider adiSubSlider1;
	
	@FXML
	Slider adiSubSlider2;

	
	@FXML RadioButton vizX;
	@FXML RadioButton viz3;
	@FXML RadioButton vizC;


	private Image img1;
	private Image img2;
	private Image img3;
	
	int xi;
	int yi;
	int xf;
	int yf;

	@FXML
	public void cinzaAritmetica() {
		img3 = Pdi.cinzaMediaAritmética(img1, 0, 0, 0);
		atualizaImagem3();
	}

	@FXML
	private void abreImagem1() {
		img1 = abreImagem(imageView1, img1);
	}

	@FXML
	private void abreImagem2() {
		img2 = abreImagem(imageView2, img2);
	}

	private void atualizaImagem3() {
		imageView3.setImage(img3);
		imageView3.setFitWidth(img3.getWidth());
		imageView3.setFitHeight(img3.getHeight());
	}

	private Image abreImagem(ImageView imageView, Image image) {
		File f = selecionaImagem();
		if (Objects.nonNull(f)) {
			image = new Image(f.toURI().toString());
			imageView.setImage(image);
			imageView.setFitWidth(image.getWidth());
			imageView.setFitHeight(image.getHeight());
		}
		return image;
	}

	private File selecionaImagem() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.JPG", "*.png",
				"*.PNG", "*.gif", "*.GIF", "*.bmp", "*.BMP"));
		// fileChooser.setInitialDirectory(new
		// File("C:\\Users\\laus1\\Documents\\PDI\\natureza"));
		File imgSelec = fileChooser.showOpenDialog(null);
		try {
			if (imgSelec != null) {
				return imgSelec;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@FXML
	public void rasterImg(MouseEvent evt) {
		ImageView iw = (ImageView) evt.getTarget();
		if (iw.getImage() != null)
			verificaCor(iw.getImage(), (int) evt.getX(), (int) evt.getY());
	}

	private void verificaCor(Image img, int x, int y) {
		try {
			Color cor = img.getPixelReader().getColor(x - 1, y - 1);
			lblR.setText("R: " + (int) (cor.getRed() * 255));
			lblG.setText("G: " + (int) (cor.getGreen() * 255));
			lblB.setText("B: " + (int) (cor.getBlue() * 255));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML 
	public void cinzaPonderada() {
			String pctR = txtPctR.getText();
			String pctG = txtPctG.getText();
			String pctB = txtPctB.getText();
			
			if(pctR.equals("")) {
				pctR = "0";
			}
			
			if(pctG.equals("")) {
				pctG = "0";
			}
			
			if(pctB.equals("")) {
				pctB = "0";
			}
			
			int ipctR = Integer.parseInt(pctR);
			int ipctG =  Integer.parseInt(pctG);
			int ipctB =  Integer.parseInt(pctB);
			
			if((ipctR + ipctG + ipctB) > 100) {
				return;
			}

			img3 = Pdi.cinzaMediaAritmética(img1, ipctR, ipctG, ipctB);
			atualizaImagem3();
	}
	
	public void initialize() {
		setPropertiesSlider();
	}

	private void setPropertiesSlider() {
		slider.setMin(0);
		slider.setMax(250);
		slider.setValue(125);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(50);
		slider.setMinorTickCount(3);
		slider.setBlockIncrement(10);
		
		adiSubSlider1.setMin(0);
		adiSubSlider1.setMax(250);
		adiSubSlider1.setValue(125);
		adiSubSlider1.setShowTickLabels(true);
		adiSubSlider1.setShowTickMarks(true);
		adiSubSlider1.setMajorTickUnit(50);
		adiSubSlider1.setMinorTickCount(3);
		adiSubSlider1.setBlockIncrement(10);
		
		adiSubSlider2.setMin(0);
		adiSubSlider2.setMax(250);
		adiSubSlider2.setValue(125);
		adiSubSlider2.setShowTickLabels(true);
		adiSubSlider2.setShowTickMarks(true);
		adiSubSlider2.setMajorTickUnit(50);
		adiSubSlider2.setMinorTickCount(3);
		adiSubSlider2.setBlockIncrement(10);
		
	}
	
	@FXML
	public void limirizar() {
		img3 = Pdi.limiarizacao(img1, slider.getValue()/100);
		atualizaImagem3();
	}
	
	@FXML
	public void negativar() {
		img3 = Pdi.negativacao(img1);
		atualizaImagem3();
	}
	
	@FXML
	public void adicao() {
		img3 = Pdi.adicao(img1, img2, adiSubSlider1.getValue(), adiSubSlider2.getValue());
		atualizaImagem3();
	}
	
	@FXML
	public void subtracao() {
		img3 = Pdi.subtracao(img1, img2);
		atualizaImagem3();
	}
	
	@FXML 
	public void ruido() {
		
		int vizinho = 0;
		
		if (viz3.isSelected()) 
			vizinho = 1;
		else 
			if (vizC.isSelected())
				vizinho = 2;
			else
				if (vizX.isSelected())
					vizinho = 3;
		
		img3 = Pdi.ruidos(img1, vizinho);
		atualizaImagem3();
	}
	
	@FXML
	public void salvarImagem() throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				FileChooser.ExtensionFilter("PNG",".png"));
		fileChooser.setTitle("Salvar Imagem");
		File file = fileChooser.showSaveDialog(null); 
		if (file != null) {
			BufferedImage bfImf = SwingFXUtils.fromFXImage(img3, null);
			ImageIO.write(bfImf, "png", file);
		}
	
	}
	
	@FXML
	public void MarcacaoMousePressed(MouseEvent evento) {
		ImageView img = (ImageView)evento.getTarget();
		if(img.getImage()!=null) {
			 xi = (int)evento.getX();
			 yi = (int)evento.getY();
		}
	}
	
	@FXML 	
	public void MarcacaoMouseRelease(MouseEvent evento) {
		ImageView img = (ImageView)evento.getTarget();
		if(img.getImage()!=null) {
			 xf = (int)evento.getX();
			 yf = (int)evento.getY(); 
			 img.setImage(Pdi.marcacao(img3, Math.min(xi, xf), Math.min(yi, yf), Math.max(xi, xf), Math.max(yi, yf)));				
		}
		
	}
	
	@FXML
	 public void abreHistograma(ActionEvent event) {
		 try {
			 Stage stage = new Stage();
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("Histograma.fxml"));
			 Parent root = loader.load();
			 stage.setScene(new Scene(root));
			 stage.setTitle("Histograma");			 
			 stage.initOwner(((Node) event.getSource()).getScene().getWindow());
			 stage.show();
			 
			 HistogramaController controller = (HistogramaController)loader.getController();
			 
			 if(img1!=null) {
				 Pdi.montaGrafico(img1, controller.grafico1);
			 }
			 if(img2!=null) {
				 Pdi.montaGrafico(img2, controller.grafico2);
			 }
			 if(img3!=null) {
				 Pdi.montaGrafico(img3, controller.grafico3);
			 }
			 
			 
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
	 }

}
