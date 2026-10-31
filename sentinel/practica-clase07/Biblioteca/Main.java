package Biblioteca;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            printMenu();
            System.out.print("Select an option: ");
            
            try {
                option = Integer.parseInt(scanner.nextLine());

                // Estructura Switch clásica de Java (muy similar a C++)
                switch (option) {
                    case 1:
                        System.out.print("Enter ISBN: ");
                        String isbn = scanner.nextLine();
                        System.out.print("Enter Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Author: ");
                        String author = scanner.nextLine();

                        // Si el libro ya existe o los datos están vacíos, 
                        // las clases lanzan una excepción que salta al catch
                        library.add(new Book(isbn, title, author));
                        System.out.println("Book added successfully!");
                        break;

                    case 2:
                        System.out.print("Enter ISBN to remove: ");
                        String isbnToRemove = scanner.nextLine();
                        library.remove(isbnToRemove);
                        System.out.println("Book removed successfully!");
                        break;

                    case 3:
                        List<Book> books = library.listAll();
                        if (books.isEmpty()) {
                            System.out.println("The library is empty.");
                        } else {
                            for (Book b : books) {
                                System.out.println(b);
                            }
                        }
                        break;

                    case 4:
                        System.out.print("Enter search text: ");
                        String text = scanner.nextLine();
                        List<Book> searchResults = library.search(text);
                        if (searchResults.isEmpty()) {
                            System.out.println("No books found matching: " + text);
                        } else {
                            for (Book b : searchResults) {
                                System.out.println(b);
                            }
                        }
                        break;

                    case 5:
                        System.out.print("Enter ISBN to lend: ");
                        String isbnToLend = scanner.nextLine();
                        Book bookToLend = library.findByIsbn(isbnToLend);
                        bookToLend.lend();
                        System.out.println("Book lent successfully!");
                        break;

                    case 6:
                        System.out.print("Enter ISBN to return: ");
                        String isbnToReturn = scanner.nextLine();
                        Book bookToReturn = library.findByIsbn(isbnToReturn);
                        bookToReturn.giveBack();
                        System.out.println("Book returned successfully!");
                        break;

                    case 0:
                        System.out.println("Exiting program...");
                        break;

                    default:
                        System.out.println("Invalid option. Please choose between 0 and 6.");
                }

            } catch (Exception e) {
                // Captura las excepciones de Book o Library (datos vacíos, libro no encontrado, etc.)
                // Muestra el mensaje exacto que configuraste y permite que el bucle continúe normalmente.
                System.out.println("Error: " + e.getMessage());
            }
            
            System.out.println(); // Línea en blanco para ordenar la consola
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("--- LIBRARY MENU ---");
        System.out.println("1) Add");
        System.out.println("2) Remove");
        System.out.println("3) List");
        System.out.println("4) Search");
        System.out.println("5) Lend");
        System.out.println("6) Return");
        System.out.println("0) Exit");
    }
}