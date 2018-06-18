package com.example.a12.temparagecalculation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FileDoubleWorker extends FileWorker{

	
	public void write(List<Double> list, File file) {

		String res = new String();
		for (Double double1 : list) {
			res += String.valueOf(double1) + ";";
		}

		write(file,res);
	}
	
	public ArrayList<Double> readDouble(File file) throws FileNotFoundException {

		String textFromFile = read(file);

		ArrayList<Double> list = new ArrayList<>();
		for (String rezval : textFromFile.split(";")) {
			if(!rezval.equals("")) {
				list.add(Double.valueOf(rezval));
			}
		}
		return list;
	}
}
