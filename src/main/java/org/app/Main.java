package org.app;

import org.app.Controllers.AccountController;
import org.app.Controllers.AuthController;
import org.app.Controllers.CustomerController;
import org.app.Controllers.TransactionController;
import org.app.Entities.Account;
import org.app.Entities.Customer;
import org.app.Entities.User;
import org.app.Services.interfaces.IAuth;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthController authController = new AuthController();
        User user;
        int functionId;
        String username = "";
        String password = "";
        do {
            Main.loginMenu();
            try {
                System.out.print("Chọn: ");
                functionId = Integer.parseInt(sc.nextLine());
                if (functionId == 0) {
                    System.out.println("Thoát!");
                    System.exit(1);
                    break;
                }
                System.out.print("Tên đăng nhập: ");
                username = sc.nextLine();
                System.out.print("Mật khẩu: ");
                password = sc.nextLine();
                user = authController.login(username, password);
                if (user != null) {
                    Main.showMenu(user);
                } else {
                    System.out.println("Tên đăng nhập hoặc tài khoản không đúng!");
                }
            } catch (Exception e) {
                System.out.println("Error Found " + e.getMessage());
            }


        } while (true);
    }

    public static void loginMenu() {
        System.out.println("====== ĐĂNG NHẬP ======");
        System.out.println("1. Đăng nhập");
        System.out.println("0. Thoát");
    }

    public static void password() {
        System.out.println("====== ĐĂNG NHẬP ======");
        System.out.println("Mật khẩu");
        System.out.println("0. Quay lại");
    }

    public static void showMenu(User user) {
        Customer customer = new Customer();
        Account account = new Account();
        CustomerController customerController = new CustomerController();
        TransactionController transactionController = new TransactionController();
        Scanner sc = new Scanner(System.in);
        if (user.getRole().trim().equals("customer")) {
            System.out.println("====== NGÂN HÀNG ======");
            System.out.println("1. Thông tin cá nhân");
            System.out.println("2. Thông tin tài khoản");
            System.out.println("3. Report");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");
            int functionId;
            functionId = Integer.parseInt(sc.nextLine());
            switch (functionId) {
                case 1:
                    int choose;
                    boolean check = true;
                    do {
                        customerController.getInfo(user.getId());
                        System.out.println("1. Chỉnh sửa");
                        System.out.println("2. Thoát");
                        System.out.println("Chọn: ");
                        try {
                            choose = Integer.parseInt(sc.nextLine());
                            switch (choose) {
                                case 1:
                                    customerController.updateCustomer(customer);
                                    break;
                                case 2:
                                    check = false;
                                    break;
                                default:
                                    System.out.println("Không hợp lệ!. Vui lòng chọn lại");

                            }
                        } catch (Exception e) {
                            System.out.println("ERROR FOUND  " + e.getMessage());
                            e.printStackTrace();
                        }
                    } while (check);
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    String[] args = new String[0];
                    Main.main(args);
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("====== QUẢN LÝ THÔNG TIN NGÂN HÀNG ======");
            System.out.println("1. Quản lý khách hàng");
            System.out.println("2. Quản lý tài khoản");
            System.out.println("3. Report");
            System.out.println("4. Thoát");
        }

    }
}