import java.util.Scanner;

public class BufMgrTester {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("please specify buffer size");
            return;
        }
        BufMgr bufMgr = new BufMgr(Integer.parseInt(args[0]));
        Scanner s = new Scanner(System.in);
        int inChoice = 0;

        displayMenu();
        while ((inChoice = s.nextInt()) != -1) {
            switch (inChoice) {
                case 1:
                    System.out.print("Enter number of pages to create: ");
                    int arg = s.nextInt();
                    if (arg < 1) {
                        System.out.println("Can only create a positive number of pages");
                        break;
                    }
                    for (int i = 0; i < arg; i++) {
                        bufMgr.createPage(i);
                    }
                    break;
                case 2:
                    System.out.print("Enter page number to be requested: ");
                    int arg2 = s.nextInt();
                    bufMgr.pin(arg2);
                    bufMgr.displayPage(arg2);
                    break;
                case 3:
                    System.out.print("Enter page number to update: ");
                    int arg3 = s.nextInt();
                    s.nextLine(); //Eat leftover newline
                    bufMgr.displayPage(arg3);
                    System.out.print("Input text to append: ");
                    bufMgr.updatePage(arg3, s.nextLine());
                    break;
                case 4:
                    System.out.print("Enter page number to be relinquished: ");
                    int arg4 = s.nextInt();
                    bufMgr.unpin(arg4);
                    break;
                default:
                    System.out.println("Invalid option selected");
            }
            displayMenu();
        }
    }

    private static void displayMenu() {
        System.out.println("1. Create pages\n2. Request a page\n3. Update a page\n4. Relinquish a page\n-1. Quit");
    }
}
