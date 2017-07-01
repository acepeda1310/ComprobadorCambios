package com.adrisoft.comprobador;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Main {

	public static boolean seguimos = true;
	public final static String URL = "https://ieszurbarannav.educarex.es/index.php";
	public final static int[] LIMITES={19000, 23000};
	public static boolean primeraVez = true;
	public static String primerContenido = "";

	public static void sonar()
			throws LineUnavailableException, InterruptedException, IOException, UnsupportedAudioFileException {
		File archivo = new File("C:\\alarm.wav");
		if (archivo.exists()) {
			Clip sonido = AudioSystem.getClip();
			sonido.open(AudioSystem.getAudioInputStream(new File("C:\\alarm.wav")));
			sonido.start();
			JOptionPane.showMessageDialog(null, "It's a match!!!");
			Thread.sleep(2500);
			sonido.close();
		} else {
			JOptionPane.showMessageDialog(null, "It's a match!!!");
		}
	}

	public static void cambia(String contenido) {
		seguimos = primerContenido.substring(LIMITES[0], LIMITES[1]).equals(contenido.substring(LIMITES[0], LIMITES[1]));
	}

	public static void comprobacion()
			throws IOException, InterruptedException, LineUnavailableException, UnsupportedAudioFileException {
		URL urlFixed = new URL(URL);
		URLConnection uc = urlFixed.openConnection();
		uc.connect();

		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		String inputLine;
		String contenido = "";
		while ((inputLine = in.readLine()) != null) {
			contenido += inputLine + "/n";
		}
		//contenido=contenido.substring(19000, 23000);
		if (primeraVez) {
			primerContenido = contenido;
			primeraVez = false;
		} else
			cambia(contenido);
		Thread.sleep(5000);
		System.out.println(new Date() +" - "+!seguimos);
		//System.out.println(contenido);
		if (seguimos)
			comprobacion();
		else
			sonar();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Sem Mes DD HH:MM:SS CEST Year - Cambio");
		System.out.println("-------------------------------------");
		try {
			comprobacion();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			main(args);
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
		} catch (UnsupportedAudioFileException uafe) {
			uafe.printStackTrace();
		}
	}

}
