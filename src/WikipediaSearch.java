import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.awt.Desktop;
import java.net.URI;

public class WikipediaSearch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введите поисковый запрос: ");
            String query = scanner.nextLine();

        } finally {
            scanner.close();
        }
    }
}
