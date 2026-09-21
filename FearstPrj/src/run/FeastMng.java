/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package run;

/**
 *
 * @author Administrator
 */
/* Chương trình chính – Quản lý đặt tiệc truyền thống
   Decomposition – Phân rã bài toán
   Data:
     - Danh sách Set menu   → SetMenuList (đọc từ FeastMenu.csv)
     - Danh sách Customer   → CustList    (đọc từ customers.dat)
     - Danh sách Order      → OrderList   (đọc từ feast_order_service.dat)

   Các chức năng:
    1. Register customers.
    2. Update customer information.
    3. Search for customer information by name.
    4. Display feast menus.
    5. Place a feast order.
    6. Update order information.
    7. Save data to file.
    8. Display Customer list.
    9. Display Order list.
    10. Quit.
*/

import core.SetMenuList;
import core.CustList;
import core.OrderList;
import tool.ConsoleInputter;

public class FeastMng {
    public static void main(String[] args) {
        // Tên các file của chương trình
        String setMnuFile = "FeastMenu.csv";
        String custFile  = "customers.dat";
        String orderFile = "feast_order_service.dat";

        // Chuẩn bị 3 list cơ bản, đọc dữ liệu từ file
        SetMenuList setMenuList = new SetMenuList();
        setMenuList.readFile(setMnuFile); // Đọc FeastMenu.csv (pathFile cứng trong SetMenuList)

        // Nếu không đọc được thực đơn → không thể chạy chương trình
        if (setMenuList.isEmpty()) {
            System.out.println("Set menus are not ready. The program can not run.");
            ConsoleInputter.getStr("Press Enter to quit.");
            System.exit(0);
        }

        CustList custList = new CustList();
        custList.readFile(custFile);

        // OrderList nhận setMenuList + custList để validate khi đặt hàng
        OrderList orderList = new OrderList(setMenuList, custList);
        orderList.readFile(orderFile);

        // Biến menu
        Object[] mnuOptions = {
            /*1*/ "Register customers",
            /*2*/ "Update customer information",
            /*3*/ "Search for customer information by name",
            /*4*/ "Display feast menus",
            /*5*/ "Place a feast order",
            /*6*/ "Update order information",
            /*7*/ "Save data to file",
            /*8*/ "Display Customer list",
            /*9*/ "Display Order list",
           /*10*/ "Quit"
        };

        int choice;
        // Biến quản lý sự thay đổi trong hai danh sách custList và orderList
        boolean custChanged  = false;
        boolean orderChanged = false;
        String title = "\nFEAST ORDER MANAGEMENT\n----------------------";

        // Chạy chương trình
        do {
            System.out.println(title);
            choice = ConsoleInputter.intMenu(mnuOptions);
            switch (choice) {
                case 1:  custList.addNewCustomer();    custChanged  = true; break;
                case 2:  custList.updateCust();     custChanged  = true; break;
                case 3:  custList.searchName();                          break;
                case 4:  setMenuList.print();                     break;
                case 5:  orderList.addOrder();      orderChanged = true; break;
                case 6:  orderList.updateOrder();   orderChanged = true; break;
                case 7:
                    if (custChanged) {
                        custList.writeFile(custFile);
                        custChanged = false;
                    }
                    if (orderChanged) {
                        orderList.writeFile(orderFile);
                        orderChanged = false;
                    }
                    break;
                case 8:  custList.print(); break;
                case 9:  orderList.printOrderList(); break;
                default: // case 10: Quit
                    if (custChanged || orderChanged) {
                        boolean resp = ConsoleInputter.getBoolean("Save changes before quitting?");
                        if (resp) {
                            if (custChanged){
                                custList.writeFile(custFile);
                            }
                            if (orderChanged){
                                orderList.writeFile(orderFile);
                            }
                            System.out.println("Saved. Good bye!");
                        } else {
                            System.out.println("Changes discarded. Good bye!");
                        }
                    } else {
                        System.out.println("Good bye!");
                    }
            }
        } while (choice < mnuOptions.length); // Thoát khi chọn option cuối (Quit)
    }// main
}
