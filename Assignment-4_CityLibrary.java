import java.io.*;
import java.util.*;

public class CityLibrary {
    static class Book implements Comparable<Book> {
        int id;
        String title, author, category;
        boolean isIssued;

        Book(int id, String t, String a, String c, boolean issued) {
            this.id = id;
            this.title = t;
            this.author = a;
            this.category = c;
            this.isIssued = issued;
        }

        void markAsIssued() { isIssued = true; }
        void markAsReturned() { isIssued = false; }

        void display() {
            System.out.println("ID: " + id + " | " + title + " | " + author +
                    " | " + category + " | Issued: " + isIssued);
        }

        String toFileString() {
            return id + "|" + title + "|" + author + "|" + category + "|" + isIssued;
        }

        static Book fromFileString(String line) {
            if (line == null || line.trim().isEmpty()) return null;
            String[] p = line.split("\\|", -1);
            if (p.length < 5) return null;
            try {
                return new Book(
                        Integer.parseInt(p[0]),
                        p[1], p[2], p[3],
                        Boolean.parseBoolean(p[4])
                );
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public int compareTo(Book b) {
            return this.title.compareToIgnoreCase(b.title);
        }
    }

    static class Member {
        int id;
        String name, email;
        List<Integer> issuedBooks = new ArrayList<>();

        Member(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        void display() {
            System.out.println("ID: " + id + " | " + name + " | " + email +
                    " | Issued Books: " + issuedBooks);
        }

        String toFileString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < issuedBooks.size(); i++) {
                if (i > 0) sb.append(",");
                sb.append(issuedBooks.get(i));
            }
            return id + "|" + name + "|" + email + "|" + sb.toString();
        }

        static Member fromFileString(String line) {
            if (line == null || line.trim().isEmpty()) return null;
            String[] p = line.split("\\|", -1);
            if (p.length < 4) return null;

            try {
                Member m = new Member(Integer.parseInt(p[0]), p[1], p[2]);

                if (!p[3].trim().isEmpty()) {
                    String[] ids = p[3].split(",");
                    for (String s : ids)
                        if (!s.trim().isEmpty())
                            m.issuedBooks.add(Integer.parseInt(s.trim()));
                }
                return m;
            } catch (Exception e) {
                return null;
            }
        }
    }

    private final Map<Integer, Book> books = new TreeMap<>();
    private final Map<Integer, Member> members = new TreeMap<>();
    private final Set<String> categories = new TreeSet<>();

    private final String BOOKS_FILE = "books.txt";
    private final String MEMBERS_FILE = "members.txt";

    private int nextBookId = 101;
    private int nextMemberId = 201;

    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        CityLibrary app = new CityLibrary();
        app.loadFromFile();
        app.menuLoop();
    }

    void menuLoop() {
        while (true) {
            System.out.println("\nWelcome to City Library Digital Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. List All Books");
            System.out.println("8. List All Members");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1": addBook(); break;
                case "2": addMember(); break;
                case "3": issueBook(); break;
                case "4": returnBook(); break;
                case "5": searchBooks(); break;
                case "6": sortBooks(); break;
                case "7": listAllBooks(); break;
                case "8": listAllMembers(); break;
                case "9":
                    saveToFile();
                    System.out.println("Data saved. Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    void addBook() {
        System.out.print("Enter Title: ");
        String t = sc.nextLine();
        System.out.print("Enter Author: ");
        String a = sc.nextLine();
        System.out.print("Enter Category: ");
        String c = sc.nextLine();

        int id = nextBookId++;
        Book b = new Book(id, t, a, c, false);

        books.put(id, b);
        categories.add(c);

        saveToFile();
        System.out.println("Book added with ID: " + id);
    }

    void addMember() {
        System.out.print("Enter Name: ");
        String n = sc.nextLine();
        System.out.print("Enter Email: ");
        String e = sc.nextLine();

        int id = nextMemberId++;
        Member m = new Member(id, n, e);

        members.put(id, m);

        saveToFile();
        System.out.println("Member added with ID: " + id);
    }

    void issueBook() {
        try {
            System.out.print("Enter Book ID: ");
            int bid = Integer.parseInt(sc.nextLine());

            Book b = books.get(bid);
            if (b == null) { System.out.println("Book not found."); return; }
            if (b.isIssued) { System.out.println("Book already issued."); return; }

            System.out.print("Enter Member ID: ");
            int mid = Integer.parseInt(sc.nextLine());

            Member m = members.get(mid);
            if (m == null) { System.out.println("Member not found."); return; }

            b.markAsIssued();
            if (!m.issuedBooks.contains(bid)) m.issuedBooks.add(bid);

            saveToFile();
            System.out.println("Book issued successfully.");
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    void returnBook() {
        try {
            System.out.print("Enter Book ID: ");
            int bid = Integer.parseInt(sc.nextLine());

            Book b = books.get(bid);
            if (b == null) { System.out.println("Book not found."); return; }
            if (!b.isIssued) { System.out.println("Book is not issued."); return; }

            System.out.print("Enter Member ID: ");
            int mid = Integer.parseInt(sc.nextLine());

            Member m = members.get(mid);
            if (m == null) { System.out.println("Member not found."); return; }

            if (!m.issuedBooks.contains(bid)) {
                System.out.println("This member does not have the book.");
                return;
            }

            b.markAsReturned();
            m.issuedBooks.remove(Integer.valueOf(bid));

            saveToFile();
            System.out.println("Book returned successfully.");
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    void searchBooks() {
        System.out.println("Search by: 1.Title  2.Author  3.Category");
        System.out.print("Choice: ");
        String c = sc.nextLine().trim();

        System.out.print("Enter search term: ");
        String term = sc.nextLine().trim().toLowerCase();

        boolean found = false;

        for (Book b : books.values()) {
            boolean match = false;

            if (c.equals("1") && b.title.toLowerCase().contains(term)) match = true;
            else if (c.equals("2") && b.author.toLowerCase().contains(term)) match = true;
            else if (c.equals("3") && b.category.toLowerCase().contains(term)) match = true;
            else if (!Arrays.asList("1", "2", "3").contains(c)) {
                if (b.title.toLowerCase().contains(term) ||
                        b.author.toLowerCase().contains(term) ||
                        b.category.toLowerCase().contains(term))
                    match = true;
            }

            if (match) {
                b.display();
                found = true;
            }
        }

        if (!found) System.out.println("No books found.");
    }

    void sortBooks() {
        System.out.println("Sort by: 1.Title  2.Author  3.Category");
        System.out.print("Choice: ");
        String c = sc.nextLine().trim();

        List<Book> list = new ArrayList<>(books.values());

        if (c.equals("2")) {
            Collections.sort(list, new Comparator<Book>() {
                public int compare(Book a, Book b) {
                    return a.author.compareToIgnoreCase(b.author);
                }
            });
        } else if (c.equals("3")) {
            Collections.sort(list, new Comparator<Book>() {
                public int compare(Book a, Book b) {
                    int r = a.category.compareToIgnoreCase(b.category);
                    return (r != 0) ? r : a.title.compareToIgnoreCase(b.title);
                }
            });
        } else {
            Collections.sort(list); 
        }

        for (Book b : list) b.display();
    }

    void listAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        for (Book b : books.values()) b.display();
        System.out.println("Categories: " + categories);
    }

    void listAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }
        for (Member m : members.values()) m.display();
    }

    void loadFromFile() {
        loadBooks();
        loadMembers();

        if (!books.isEmpty())
            nextBookId = Collections.max(books.keySet()) + 1;

        if (!members.isEmpty())
            nextMemberId = Collections.max(members.keySet()) + 1;
    }

    void loadBooks() {
        File f = new File(BOOKS_FILE);
        try {
            if (!f.exists()) f.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                Book b = Book.fromFileString(line);
                if (b != null) {
                    books.put(b.id, b);
                    categories.add(b.category);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading books.");
        }
    }

    void loadMembers() {
        File f = new File(MEMBERS_FILE);
        try {
            if (!f.exists()) f.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                Member m = Member.fromFileString(line);
                if (m != null) members.put(m.id, m);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading members.");
        }
    }

    void saveToFile() {
        try {
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(BOOKS_FILE));
            for (Book b : books.values()) {
                bw1.write(b.toFileString());
                bw1.newLine();
            }
            bw1.close();

            BufferedWriter bw2 = new BufferedWriter(new FileWriter(MEMBERS_FILE));
            for (Member m : members.values()) {
                bw2.write(m.toFileString());
                bw2.newLine();
            }
            bw2.close();
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }
}

