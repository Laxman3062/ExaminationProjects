import java.util.*;

class User {
    String username;
    String password;
    String name;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    // Update profile
    public void updateProfile(String newName, String newPassword) {
        this.name = newName;
        this.password = newPassword;
    }
}

class Question {
    String questionText;
    String[] options;
    int correctAnswer;

    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public boolean checkAnswer(int userAnswer) {
        return userAnswer == correctAnswer;
    }
}

class OnlineExaminationSystem {
    private static User loggedInUser = null;
    private static List<Question> questions = new ArrayList<>();
    private static Map<String, User> users = new HashMap<>();
    private static int totalTime = 30;  // Exam duration in seconds
    private static List<Integer> userAnswers = new ArrayList<>();

    // Adding some sample users and questions
    static {
        users.put("admin", new User("admin", "admin123", "Admin"));
        users.put("user", new User("user", "password", "John Doe"));

        questions.add(new Question("What is 2 + 2?", new String[] {"1", "2", "3", "4"}, 3));
        questions.add(new Question("What is the capital of France?", new String[] {"Berlin", "Madrid", "Paris", "London"}, 3));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            if (choice == 1) {
                login(scanner);
                if (loggedInUser != null) {
                    exam(scanner);
                    break;
                }
            } else if (choice == 2) {
                System.out.println("Exiting system...");
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Login system
    public static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.password.equals(password)) {
            loggedInUser = user;
            System.out.println("Login successful! Welcome " + loggedInUser.name);
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    // Exam process
    public static void exam(Scanner scanner) {
        System.out.println("\nStarting the Exam...");
        long startTime = System.currentTimeMillis();

        // Start timer thread
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= totalTime * 1000) {
                    System.out.println("\nTime is up! Automatically submitting the exam.");
                    submitExam();
                }
            }
        }, totalTime * 1000); // Auto-submit after exam time runs out

        // Display questions
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + q.questionText);
            for (int j = 0; j < q.options.length; j++) {
                System.out.println((j + 1) + ". " + q.options[j]);
            }

            System.out.print("Select your answer (1-4): ");
            int answer = scanner.nextInt();
            userAnswers.add(answer - 1);
        }

        submitExam();
    }

    // Submit the exam and calculate results
    public static void submitExam() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            if (q.checkAnswer(userAnswers.get(i))) {
                score++;
            }
        }

        System.out.println("\nExam submitted!");
        System.out.println("Your score: " + score + "/" + questions.size());
        loggedInUser = null; // Logging out user after exam
        System.out.println("You have been logged out.");
    }

    // Profile management (update name or password)
    public static void updateProfile(Scanner scanner) {
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        loggedInUser.updateProfile(newName, newPassword);
        System.out.println("Profile updated successfully!");
    }

    // Logout the user
    public static void logout() {
        loggedInUser = null;
        System.out.println("You have been logged out.");
    }
}
