import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Store student names and grades
        ArrayList<String> studentNames = new ArrayList<>();
        ArrayList<Integer> studentGrades = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        // Input student data
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name of student " + (i + 1) + ": ");
            String name = sc.nextLine();
            System.out.print("Enter grade of " + name + ": ");
            int grade = sc.nextInt();
            sc.nextLine(); // consume newline

            studentNames.add(name);
            studentGrades.add(grade);
        }

        // Calculate average, highest, and lowest
        int sum = 0, highest = Integer.MIN_VALUE, lowest = Integer.MAX_VALUE;
        String topStudent = "", lowStudent = "";

        for (int i = 0; i < studentGrades.size(); i++) {
            int grade = studentGrades.get(i);
            sum += grade;

            if (grade > highest) {
                highest = grade;
                topStudent = studentNames.get(i);
            }
            if (grade < lowest) {
                lowest = grade;
                lowStudent = studentNames.get(i);
            }
        }

        double average = (double) sum / studentGrades.size();

        // Display summary report
        System.out.println("\n--- Student Grades Summary ---");
        for (int i = 0; i < studentNames.size(); i++) {
            System.out.println("Name: " + studentNames.get(i) + " | Grade: " + studentGrades.get(i));
        }

        System.out.println("\nAverage Grade: " + average);
        System.out.println("Highest Grade: " + highest + " (by " + topStudent + ")");
        System.out.println("Lowest Grade: " + lowest + " (by " + lowStudent + ")");

        sc.close();
    }
}
