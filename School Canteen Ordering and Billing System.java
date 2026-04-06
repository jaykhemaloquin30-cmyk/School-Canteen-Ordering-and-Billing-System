import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        Scanner jay = new Scanner(System.in);

        String[] menu = {"Burger", "Fries", "Hotdog", "Koolers", "Water", "Coca cola", };
        double[] prices = {25.0, 20.0, 15.0, 15.0, 10.0, 15.0};
        int[] quantities = new int[menu.length];

        int choice;
        double total = 0;
        boolean firstOrderMade = false; 

        System.out.println("=== SCHOOL CANTEEN ORDERING SYSTEM ===");
        
        while (true) {
            System.out.println("\nMenu:");
            for (int i = 0; i < menu.length; i++) {
                String qtyDisplay = (quantities[i] > 0) ? " (Qty: " + quantities[i] + ")" : "";
                System.out.printf("%d. %-10s - %.2f%s\n", (i + 1), menu[i], prices[i], qtyDisplay);
            }

            if (firstOrderMade) {
                System.out.println("7. Checkout");
                System.out.println("8. Customize Order"); 
            }
            System.out.println("0. Cancel Order");

            System.out.print("\nEnter your choice: ");
            choice = jay.nextInt();

            if (choice > 0 && choice <= menu.length) {
                System.out.print("Enter quantity to add: ");
                int addQty = jay.nextInt();
                if (addQty > 0) {
                    quantities[choice - 1] += addQty;
                    total += prices[choice - 1] * addQty;
                    firstOrderMade = true;
                    System.out.println("Added successfully!");
                }

            } else if (choice == 7 && firstOrderMade) {
                if (total > 0) break;
                else System.out.println("Cart is empty!");

            } else if (choice == 8 && firstOrderMade) {
                System.out.print("Enter item number from cart to customize (1-6): ");
                int oldIdx = jay.nextInt() - 1;

                if (oldIdx >= 0 && oldIdx < menu.length && quantities[oldIdx] > 0) {
                    System.out.println("\n[ CUSTOMIZING: " + menu[oldIdx] + " ]");
                    System.out.println("1. Swap with a different Item");
                    System.out.println("2. Adjust Quantity");
                    System.out.println("3. Remove Item");
                    System.out.print("Choice: ");
                    int custChoice = jay.nextInt();

                    if (custChoice == 1) {
                        System.out.print("Enter NEW item number from menu (1-6): ");
                        int newIdx = jay.nextInt() - 1;
                        if (newIdx >= 0 && newIdx < menu.length) {
                            total -= (quantities[oldIdx] * prices[oldIdx]);
                            int oldQty = quantities[oldIdx];
                            quantities[oldIdx] = 0;
                            
                            quantities[newIdx] += oldQty;
                            total += (oldQty * prices[newIdx]);
                            System.out.println("Successfully swapped " + menu[oldIdx] + " for " + menu[newIdx] + "!");
                        }
                    } else if (custChoice == 2) { 
                        System.out.print("Enter NEW total quantity: ");
                        int newQty = jay.nextInt();
                        if (newQty >= 0) {
                            total -= (quantities[oldIdx] * prices[oldIdx]);
                            quantities[oldIdx] = newQty;
                            total += (quantities[oldIdx] * prices[oldIdx]);
                            System.out.println("Quantity adjusted!");
                        }
                    } else if (custChoice == 3) {
                        total -= (quantities[oldIdx] * prices[oldIdx]);
                        quantities[oldIdx] = 0;
                        System.out.println("Removed from cart.");
                    }

                    if (total <= 0) {
                        total = 0;
                        firstOrderMade = false;
                    }
                } else {
                    System.out.println("Item not found in cart.");
                }

            } else if (choice == 0) {
                System.out.println("\nOrder Cancelled. Thank you for visiting!");
                jay.close();
                return;

            } else {
                System.out.println("Invalid choice!");
            }
        }

        System.out.println("\n==============================");
        System.out.println("          RECEIPT");
        System.out.println("==============================");
        System.out.printf("%-12s %-5s %-10s\n", "Item", "Qty", "Total");

        for (int i = 0; i < menu.length; i++) {
            if (quantities[i] > 0) {
                double itemTotal = quantities[i] * prices[i];
                System.out.printf("%-12s %-5d %-10.2f\n", menu[i], quantities[i], itemTotal);
            }
        }

        System.out.println("------------------------------");
        System.out.printf("TOTAL AMOUNT: %.2f\n", total);

        double cash;
        
            System.out.print("Enter payment: ");
            cash = jay.nextDouble();
            

        double change = cash - total;
        System.out.println("------------------------------");
        System.out.printf("CASH: %.2f\n", cash);
        System.out.printf("CHANGE: %.2f\n", change);
        System.out.println("==============================");
        System.out.println("Salamat sa pag order!");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Time: " + now.format(formatter));
        System.out.println("==============================");

        jay.close();
    }
