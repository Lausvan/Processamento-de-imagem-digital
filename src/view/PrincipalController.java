package view;

import java.io.File;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

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

	private Image img1;
	private Image img2;
	private Image img3;

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
}
