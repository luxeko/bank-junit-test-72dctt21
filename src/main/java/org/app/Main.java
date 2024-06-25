package org.app;

import org.app.Controllers.AccountController;
import org.app.Controllers.AuthController;
import org.app.Controllers.CustomerController;
import org.app.Controllers.TransactionController;
import org.app.Entities.Account;
import org.app.Entities.Customer;
import org.app.Entities.User;

import java.util.List;
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

    public static void showMenu(User user) {
        Account account = new Account();
        CustomerController customerController = new CustomerController();
        TransactionController transactionController = new TransactionController();
        AccountController accountController = new AccountController();
        Scanner sc = new Scanner(System.in);
        if (user.getRole().trim().equals("customer")) {
            do {
                System.out.println("====== NGÂN HÀNG ======");
                System.out.println("1. Thông tin cá nhân");
                System.out.println("2. Thông tin tài khoản");
                System.out.println("3. Báo cáo");
                System.out.println("4. Thoát");
                System.out.print("Chọn: ");
                int functionId;
                functionId = Integer.parseInt(sc.nextLine());
                switch (functionId) {
                    case 1:
                        int choose;
                        boolean check = true;
                        do {
                            Customer customer = customerController.getInfo(user.getId());
                            System.out.println("1. Chỉnh sửa");
                            System.out.println("2. Thoát");
                            System.out.println("Chọn: ");
                            try {
                                choose = Integer.parseInt(sc.nextLine());
                                switch (choose) {
                                    case 1:
                                        Customer customerUpdate = customer.update(customer.getId());
                                        if (customerController.updateCustomer(customerUpdate)) {
                                            System.out.println("Cập nhật thành công");
                                        } else {
                                            System.out.println("Cập nhật thất bại");
                                        }
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
                        break;
                    case 2:
                        int choose2;
                        boolean check2 = true;
                        do {
                            System.out.println("2.1 Thêm tài khoản");
                            System.out.println("2.2 Nạp tiền vào tk trả trước");
                            System.out.println("2.3 Rút/Thanh toán tiền");
                            System.out.println("2.4 Chuyển tiền");
                            System.out.println("2.5 Hiển thị thông tin tài khoản từ số tài khoản");
                            System.out.println("2.6 Hiển thị thông tin các tài khoản từ mã khách hàng");
                            System.out.println("2.7 Thoát");
                            System.out.println("Chọn: ");
                            try {
                                choose2 = Integer.parseInt(sc.nextLine());
                                switch (choose2) {
                                    case 1:
                                        accountController.insertAccount(account);
                                        break;
                                    case 2:
                                        accountController.payInAccount();
                                        break;
                                    case 3:
                                        accountController.withdrawAccount();
                                        break;
                                    case 4:
                                        break;
                                    case 5:
                                        break;
                                    case 6:
                                        break;
                                    case 7:
                                        check2 = false;
                                        break;
                                    default:
                                        System.out.println("Không hợp lệ!. Vui lòng chọn lại");

                                }
                            } catch (Exception e) {
                                System.out.println("ERROR FOUND  " + e.getMessage());
                                e.printStackTrace();
                            }
                        } while (check2);
                        break;
                    case 3:
                        int choose3;
                        boolean check3 = true;
                        do {
                            System.out.println("3.1 Hiển thị thông tin các giao dịch của 1 tài khoản trong khoảng thời gian");
                            System.out.println("3.2 Hiển thị thông tin các giao dịch của tôi trong khoảng thời gian");
                            System.out.println("3.3 Thoát");
                            System.out.println("Chọn: ");
                            try {
                                choose3 = Integer.parseInt(sc.nextLine());
                                switch (choose3) {
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        check3 = false;
                                        break;
                                    default:
                                        System.out.println("Không hợp lệ!. Vui lòng chọn lại");
                                }
                            } catch (Exception e) {
                                System.out.println("ERROR FOUND  " + e.getMessage());
                                e.printStackTrace();
                            }
                        } while (check3);
                        break;
                    case 4:
                        String[] args = new String[0];
                        Main.main(args);
                        break;
                    default:
                        System.out.println("Nhập sai!. Vui lòng nhập lại");
                }
            } while (true);
        } else {
            do {
                System.out.println("====== QUẢN LÝ THÔNG TIN NGÂN HÀNG ======");
                System.out.println("1. Quản lý khách hàng");
                System.out.println("2. Quản lý tài khoản");
                System.out.println("3. Báo cáo");
                System.out.println("4. Thoát");
                System.out.print("Chọn: ");
                int functionId;
                functionId = Integer.parseInt(sc.nextLine());
                switch (functionId) {
                    case 1:
                        int choose;
                        boolean check = true;
                        do {
                            customerController.getListCustomer();
                            System.out.println("1. Thêm mới");
                            System.out.println("2. Chỉnh sửa");
                            System.out.println("3. Xóa");
                            System.out.println("3. Thoát");
                            System.out.println("Chọn: ");
                            try {
                                choose = Integer.parseInt(sc.nextLine());
                                switch (choose) {
                                    case 1:
                                        Customer newCustomer = new Customer().create();
                                        if (customerController.addCustomer(newCustomer)) {
                                            System.out.println("Thêm mới thành công");
                                        } else {
                                            System.out.println("Thêm mới thất bại");
                                        }
                                        break;
                                    case 2:
                                        System.out.print("Nhập mã khách hàng: ");
                                        String customerCode = sc.nextLine();
                                        int userId = customerController.getUserIdByCustomerCode(customerCode);
                                        if (userId == -1) {
                                            System.out.println("Không tìm thấy mã khách hàng");
                                        } else {
                                            Customer customer = customerController.getInfo(userId);
                                            Customer customerUpdate = customer.update(customer.getId());
                                            if (customerController.updateCustomer(customerUpdate)) {
                                                System.out.println("Cập nhật thành công");
                                            } else {
                                                System.out.println("Cập nhật thất bại");
                                            }
                                        }
                                        break;
                                    case 3:
                                        if (customerController.deleteCustomer()) {
                                            System.out.println("Xóa khách thành công");
                                        } else {
                                            System.out.println("Xóa khách hàng thất bại");
                                        }
                                    case 4:
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
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        String[] args = new String[0];
                        Main.main(args);
                        break;
                    default:
                        System.out.println("Nhập sai!. Vui lòng nhập lại");
                }
            } while (true);
        }

    }
}