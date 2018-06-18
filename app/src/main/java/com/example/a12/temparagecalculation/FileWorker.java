package com.example.a12.temparagecalculation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileWorker {


    public static void writeDoubleList(List<Double> list, File file) {

        StringBuilder res = new StringBuilder();
        for (Double double1 : list) {
            res.append(String.valueOf(double1)).append(";");
        }

        write(file,res.toString());
    }

    public static ArrayList<Double> readDoubleList(File file) throws FileNotFoundException {

        String textFromFile = read(file);

        ArrayList<Double> list = new ArrayList<>();
        for (String rezval : textFromFile.split(";")) {
            if(!rezval.equals("")) {
                list.add(Double.valueOf(rezval));
            }
        }
        return list;
    }

    public static void write(File file, String text) {

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(File file) throws FileNotFoundException {

        StringBuilder sb = new StringBuilder();

        exists(file);

        try {

            BufferedReader in = new BufferedReader(new FileReader((file).getAbsoluteFile()));
            try {

                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    // sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    protected static void exists(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
    }

    public static void delete(File file) throws FileNotFoundException {
        exists(file);
        file.delete();
    }
}
