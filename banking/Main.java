package com.banking;


import com.banking.model.*;
import com.banking.service.AccountService;
import com.banking.exception.*;
import java.math.BigDecimal;
import java.util.Scanner;

import static com.banking.db.DatabaseConnection.ExecuteSQL;
import static com.banking.db.DatabaseConnection.startH2Server;


public class Main {
    public static void main(String[] args) {

        // Start H2 Web Console
        startH2Server();

        // Execute SQL
        ExecuteSQL();

        Scanner scanner = new Scanner(System.in);
        BankingSystem bankingSystem = new BankingSystem();
        AccountService accountService = new AccountService(bankingSystem);

        while (true) {
            System.out.println("=============================================");
            System.out.println("===          Banking System Menu          ===");
            System.out.println("=============================================");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. View Transactions");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1: // Create Account
                        System.out.print("Enter Account ID: ");
                        String accountId = scanner.nextLine();
                        System.out.print("Enter Account Type (SAVINGS/CHECKING): ");
                        String accountTypeStr = scanner.nextLine().toUpperCase();
                        System.out.print("Enter Initial Balance: ");
                        BigDecimal initialBalance = scanner.nextBigDecimal();
                        scanner.nextLine();

                        AccountType accountType = AccountType.valueOf(accountTypeStr);
                        accountService.createAccount(accountType, accountId, initialBalance);
                        System.out.println("✅ Account created successfully!\uD83D\uDE01");
                        break;

                    case 2: // View Account
                        System.out.print("Enter Account ID: ");
                        String viewAccountId = scanner.nextLine();
                        String accountDetails = accountService.viewAccount(viewAccountId);
                        System.out.println("\n=== Account Details ===");
                        System.out.println(accountDetails);
                        break;

                    case 3: // Deposit
                        System.out.print("Enter Account ID: ");
                        String depositAccountId = scanner.nextLine();
                        System.out.print("Enter Deposit Amount: ");
                        BigDecimal depositAmount = scanner.nextBigDecimal();
                        scanner.nextLine();

                        accountService.deposit(depositAccountId, depositAmount);
                        System.out.println("✅ Deposit successful!\uD83D\uDE01");
                        break;

                    case 4: // Withdraw
                        System.out.print("Enter Account ID: ");
                        String withdrawAccountId = scanner.nextLine();
                        System.out.print("Enter Withdrawal Amount: ");
                        BigDecimal withdrawAmount = scanner.nextBigDecimal();
                        scanner.nextLine(); // Consume newline

                        accountService.withdraw(withdrawAccountId, withdrawAmount);
                        System.out.println("✅ Withdrawal successful!\uD83D\uDE01");
                        break;

                    case 5: // Transfer
                        System.out.print("Enter Sender Account ID: ");
                        String fromAccountId = scanner.nextLine();
                        System.out.print("Enter Receiver Account ID: ");
                        String toAccountId = scanner.nextLine();
                        System.out.print("Enter Transfer Amount: ");
                        BigDecimal transferAmount = scanner.nextBigDecimal();
                        scanner.nextLine(); // Consume newline

                        accountService.transfer(fromAccountId, toAccountId, transferAmount);
                        System.out.println("✅ Transfer successful!\uD83D\uDE01");
                        break;

                    case 6: // View Transactions
                        System.out.println("\n=== Transactions ===");
                        accountService.getAllTransactions().forEach(System.out::println);
                        break;

                    case 7: // Exit
                        System.out.println("🚪 Exiting... Thank you!");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("\uD83D\uDEA8" + " Invalid choice. Please try again." + " \uD83D\uDEA8");
                }
            } catch (BankingException e) {
                System.out.println("\uFE0F Error: " + e.getMessage() + " \uFE0F");
            } catch (IllegalArgumentException e) {
                System.out.println("\uFE0F Invalid account type. Use SAVINGS or CHECKING." + " \uFE0F");
            }
        }
    }


}