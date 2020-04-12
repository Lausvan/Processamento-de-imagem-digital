package view;

import java.util.Arrays;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Pdi {

	public static Image cinzaMediaAritmética(Image imagem, int pcr, int  pcg, int pcb) {
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					Color corA = pr.getColor(i,j);
					double media = (corA.getRed()+corA.getGreen()+corA.getBlue())/3;
					if(pcr != 0 || pcg != 0 || pcb != 0)
						media = (corA.getRed()*pcr + corA.getGreen()*pcg +corA.getBlue()*pcb)/100;
					Color corN = new Color(media, media, media, corA.getOpacity());
					pw.setColor(i, j, corN);
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static Image limiarizacao(Image image, double limiar) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for (int i = 0; i < w; i++) {
				for (int j = 0;j < h; j++) {
					Color corA = pr.getColor(i, j);
					Color corN;
				
					if (corA.getRed() >= limiar) {
						corN = new Color(1, 1, 1, corA.getOpacity());
					} else {
						corN = new Color(0, 0, 0, corA.getOpacity());
					}
					pw.setColor(i, j, corN);
				}
			}
			
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static Image negativacao(Image image) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for (int i = 0; i < w; i++) {
				for (int j = 0;j < h; j++) {
					Color corA = pr.getColor(i, j);
					Color corN= new Color(1 - corA.getRed(), 1 - corA.getGreen(), 1 - corA.getBlue(), corA.getOpacity());
					pw.setColor(i, j, corN);
				}
			}
			
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
public static Image ruidos(Image imagem, int tipoVizinho) {
		
		try {
			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();
			
			PixelReader pr = imagem.getPixelReader();
			WritableImage wi = new WritableImage(w, h);
			PixelWriter pw = wi.getPixelWriter();
			
			for (int i = 1; i < w-1; i++) {
				for (int j = 1; j < h-1; j++) {
					
					Color corA = pr.getColor(i, j);
					Pixel p = new Pixel(corA.getRed(), corA.getGreen(), corA.getBlue(), i, j);
					Pixel vetor[] = null;
					
					if(tipoVizinho == VIZINHOS.VIZINHO_3X3)  
						vetor = buscaVizinhos3(imagem, p);
					if(tipoVizinho == VIZINHOS.VIZINHO_CRUZ) 
						vetor =  buscaVizinhosC(imagem,p);
					if(tipoVizinho == VIZINHOS.VIZINHO_X) 
						vetor = buscaVizinhosX(imagem,p);
					
					double red = mediana(vetor,1);
					double green = mediana(vetor,2);
					double blue = mediana(vetor,3);
					
					Color corN = new Color(red,green,blue,corA.getOpacity());
					pw.setColor(i, j, corN);
					
				}
			}
			
		return wi;
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Pixel[] buscaVizinhosX(Image imagem, Pixel p) {
		Pixel[] vX = new Pixel[5];
		PixelReader pr = imagem.getPixelReader();
		
		Color cor = pr.getColor(p.x-1, p.y+1);
		vX[0] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x-1,p.y+1);
		cor = pr.getColor(p.x+1, p.y-1);
		vX[1] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x+1,p.y-1);
		cor = pr.getColor(p.x-1, p.y-1);
		vX[2] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x-1,p.y-1);
		cor = pr.getColor(p.x+1, p.y+1);
		vX[3] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x+1,p.y+1);
		vX[4] = p;
		
		return vX;
	}
	
	public static Pixel[] buscaVizinhosC(Image imagem, Pixel p) {
		Pixel[] vC = new Pixel[5];
		PixelReader pr = imagem.getPixelReader();
		
		Color cor = pr.getColor(p.x, p.y+1);
		vC[0] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x,p.y+1);
		cor = pr.getColor(p.x, p.y-1);
		vC[1] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x,p.y-1);
		cor = pr.getColor(p.x-1, p.y);
		vC[2] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x-1,p.y);
		cor = pr.getColor(p.x+1, p.y);
		vC[3] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x+1,p.y);
		vC[4] = p;
		
		return vC;
	}
	
	public static Pixel[] buscaVizinhos3(Image imagem, Pixel p) {
		Pixel[] v3 = new Pixel[9];
		PixelReader pr = imagem.getPixelReader();
		
		Color cor = pr.getColor(p.x, p.y+1);
		v3[0] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x,p.y+1);
		cor = pr.getColor(p.x, p.y-1);
		v3[1] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x,p.y-1);
		cor = pr.getColor(p.x-1, p.y);
		v3[2] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x-1,p.y);
		cor = pr.getColor(p.x+1, p.y);
		v3[3] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x+1,p.y);
		cor = pr.getColor(p.x-1, p.y+1);
		v3[4] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x-1,p.y+1);
		cor = pr.getColor(p.x+1, p.y-1);
		v3[5] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x+1,p.y-1);
		cor = pr.getColor(p.x-1, p.y-1);
		v3[6] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x-1,p.y-1);
		cor = pr.getColor(p.x+1, p.y+1);
		v3[7] = new Pixel(cor.getRed(),cor.getGreen(),cor.getBlue(),p.x+1,p.y+1);
		v3[8] = p;
		
		return v3;
	}
	
	public static double mediana(Pixel[] pixels, int canal) {
		
		double v[] = new double[pixels.length];
		
		for (int i = 0; i < pixels.length; i++) {
			
			if(canal == 1) 
				v[i] = pixels[i].r;
			if(canal == 2) 
				v[i] = pixels[i].g;
			if(canal == 3) 
				v[i] = pixels[i].b;
			
		}
		
		Arrays.sort(v);
		return v[v.length/2];
	}
	
	public static Image adicao(Image image1, Image image2, double value1, double value2) {
		int w1 = (int) image1.getWidth();
		int h1 = (int) image1.getHeight();

		int w2 = (int) image2.getWidth();
		int h2 = (int) image2.getHeight();

		int w = Math.min(w1, w2);
		int h = Math.min(h1, h2);

		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();

		Color cor1;
		Color cor2;

		double r;
		double g;
		double b;

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				cor1 = image1.getPixelReader().getColor(i, j);
				cor2 = image2.getPixelReader().getColor(i, j);

				r = ((cor1.getRed() * value1) + (cor2.getRed() * value2)) / (value1 + value2);
				g = ((cor1.getGreen() * value1) + (cor2.getGreen() * value2)) / (value1 + value2);
				b = ((cor1.getBlue() * value1) + (cor2.getBlue() * value2)) / (value1 + value2);

				r = r > 1 ? 1 : r;
				g = g > 1 ? 1 : g;
				b = b > 1 ? 1 : b;

				pw.setColor(i, j, new Color(r, g, b, (cor1.getOpacity() + cor2.getOpacity()) / 2));
			}
		}
		return wi;
	}
	
	
	public static Image subtracao(Image image1, Image image2) {
		int w1 = (int) image1.getWidth();
		int h1 = (int) image1.getHeight();

		int w2 = (int) image2.getWidth();
		int h2 = (int) image2.getHeight();

		int w = Math.min(w1, w2);
		int h = Math.min(h1, h2);
		
		PixelReader pr1 = image1.getPixelReader();
		PixelReader pr2 = image2.getPixelReader();

		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();

		double r;
		double g;
		double b;

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				Color oldCor1 = pr1.getColor(i, j);
				Color oldCor2 = pr2.getColor(i, j);

				r = (oldCor1.getRed()) - (oldCor2.getRed());
				g = (oldCor1.getGreen()) - (oldCor2.getGreen());
				b = (oldCor1.getBlue()) - (oldCor2.getBlue());

				r = r < 0 ? 0 : r;
				g = g < 0 ? 0 : g;
				b = b < 0 ? 0 : b;

				pw.setColor(i, j, new Color(r, g, b, oldCor1.getOpacity()));
			}
		}
		return wi;
	}
	
	public static Image marcacao(Image img, int xi, int yi, int xf, int yf) {
		
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		
		PixelReader pr = img.getPixelReader();
		WritableImage wi = new WritableImage(w,h);
		PixelWriter pw = wi.getPixelWriter();
		
		Color corNova = new Color(1,0,0,1);
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				pw.setColor(i, j, pr.getColor(i, j));	
			}
		}
		    
		for(int x=xi; x<=xf; x++) {
			pw.setColor(x,yi , corNova);
			pw.setColor(x, yi+1, corNova);
			pw.setColor(x, yi-1, corNova);
		}
			
		for(int x=xi; x<=xf; x++) {
			pw.setColor(x,yf , corNova);	
			pw.setColor(x, yf+1, corNova);
			pw.setColor(x, yf-1, corNova);
		}
		
		for(int y=yi; y<=yf; y++) {
			pw.setColor(xi,y , corNova);
			pw.setColor(xi+1, y, corNova);
			pw.setColor(xi-1, y, corNova);
		}
		
		for(int y=yi; y<=yf; y++) {
			pw.setColor(xf,y , corNova);	
			pw.setColor(xf+1, y, corNova); 
			pw.setColor(xf-1, y, corNova);
		}
		
		return wi;
	
		}
	
	@SuppressWarnings({"rawtypes","unchecked"})
	public static void montaGrafico(Image img,BarChart<String, Number> grafico) {
		int[] hist = histogramaUnico(img);
		XYChart.Series vlr = new XYChart.Series();
		
		for (int i = 0; i < hist.length; i++) {
			vlr.getData().add(new XYChart.Data(i+"", hist[i]));
		}
		
		grafico.getData().addAll(vlr);
	}
	
	public static int[] histogramaUnico(Image img) {
		int[] qt = new int[256];
		PixelReader pr = img.getPixelReader();
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				qt[(int)(pr.getColor(i, j).getRed()*255)]++;
				qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
				qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
			}
		}
		return qt;
	}
	
	

	
}
