package Lab4.utils;

import java.io.*;

public class FileManager
{
    /**
     * Saves the board to a specified path
     * @param board Board to save
     * @param filePath File path to save the board to
     */
    public static void saveBoard(int[][] board, String filePath) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            objectOutputStream.writeObject(board);
        }
    }

    /**
     * Loads the board from a specified file name
     * @param filePath File name to the board
     * @return The board
     */
    public static int[][] loadBoard(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            return (int[][])objectInputStream.readObject();
        }
    }
}
