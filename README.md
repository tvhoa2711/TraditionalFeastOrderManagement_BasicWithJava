# 🍽️ Traditional Feast Order Management

> **Mã đề bài:** J1.L.P0028 — LAB211 | FPT University

Ứng dụng console Java quản lý đặt tiệc truyền thống Việt Nam. Hệ thống cho phép đăng ký khách hàng, quản lý thực đơn tiệc (cưới, sinh nhật, họp mặt, …) và đặt/cập nhật đơn hàng tiệc theo bàn.

---

## 📋 Mục lục

- [Tính năng](#-tính-năng)
- [Kiến trúc dự án](#-kiến-trúc-dự-án)
- [Yêu cầu hệ thống](#-yêu-cầu-hệ-thống)
- [Cài đặt & Chạy](#-cài-đặt--chạy)
- [Hướng dẫn sử dụng](#-hướng-dẫn-sử-dụng)
- [Cấu trúc dữ liệu](#-cấu-trúc-dữ-liệu)
- [Mô tả các class](#-mô-tả-các-class)
- [Định dạng & Validation](#-định-dạng--validation)
- [Thực đơn mẫu](#-thực-đơn-mẫu)
- [Tác giả](#-tác-giả)

---

## ✨ Tính năng

| # | Chức năng | Mô tả |
|:-:|-----------|-------|
| 1 | **Đăng ký khách hàng** | Thêm khách hàng mới với mã, tên, SĐT, email — kiểm tra trùng mã |
| 2 | **Cập nhật khách hàng** | Tìm theo mã và cập nhật tên, SĐT, email |
| 3 | **Tìm khách hàng** | Tìm kiếm khách hàng theo tên (hỗ trợ tìm gần đúng) |
| 4 | **Hiển thị thực đơn** | Xem toàn bộ danh sách set menu tiệc (đọc từ CSV) |
| 5 | **Đặt tiệc** | Chọn khách hàng → chọn set menu → số bàn → ngày tổ chức → xác nhận |
| 6 | **Cập nhật đơn hàng** | Sửa thông tin đơn đặt tiệc (set menu, số bàn, ngày) |
| 7 | **Lưu dữ liệu** | Ghi dữ liệu khách hàng & đơn hàng ra file `.dat` (Java Serialization) |
| 8 | **Xem danh sách khách hàng** | In bảng thông tin toàn bộ khách hàng |
| 9 | **Xem danh sách đơn hàng** | In bảng thông tin toàn bộ đơn đặt tiệc kèm tổng tiền |
| 10 | **Thoát** | Hỏi lưu thay đổi trước khi thoát chương trình |

---

## 🏗️ Kiến trúc dự án

```
FearstPrj/
├── src/
│   ├── core/                      # Business logic & Data models
│   │   ├── Customer.java          # Model khách hàng
│   │   ├── CustList.java          # Quản lý danh sách khách hàng
│   │   ├── SetMenu.java           # Model set menu tiệc
│   │   ├── SetMenuList.java       # Quản lý danh sách set menu
│   │   ├── Order.java             # Model đơn đặt tiệc
│   │   └── OrderList.java         # Quản lý danh sách đơn hàng
│   ├── run/
│   │   └── FeastMng.java          # ★ Entry point — Main class
│   └── tool/
│       └── ConsoleInputter.java   # Tiện ích nhập liệu console
├── FeastMenu.csv                  # Dữ liệu thực đơn mẫu (6 set menu)
├── customers.dat                  # [Auto-generated] Dữ liệu khách hàng
└── feast_order_service.dat        # [Auto-generated] Dữ liệu đơn hàng
```

---

## 💻 Yêu cầu hệ thống

- **Java JDK** 8 trở lên
- **IDE** (khuyên dùng): Apache NetBeans / IntelliJ IDEA / Eclipse
- Hệ điều hành: Windows / macOS / Linux

---

## 🚀 Cài đặt & Chạy

### Cách 1: Dùng IDE (NetBeans)

1. Clone repository:
   ```bash
   git clone https://github.com/<username>/TraditionalFeastOrderManagement.git
   ```
2. Mở project `FearstPrj` bằng NetBeans.
3. Chạy file `FeastMng.java` (Run → Run File hoặc **Shift + F6**).

### Cách 2: Dùng Command Line

```bash
# Di chuyển vào thư mục source
cd FearstPrj/src

# Biên dịch toàn bộ source
javac -d ../build run/FeastMng.java core/*.java tool/ConsoleInputter.java

# Copy file CSV vào thư mục build
cp ../FeastMenu.csv ../build/

# Chạy chương trình
cd ../build
java run.FeastMng
```

> **⚠️ Lưu ý:** File `FeastMenu.csv` phải nằm cùng thư mục khi chạy chương trình, nếu không sẽ không thể load được thực đơn.

---

## 📖 Hướng dẫn sử dụng

Khi chạy chương trình, menu chính sẽ hiển thị:

```
FEAST ORDER MANAGEMENT
----------------------
1-Register customers
2-Update customer information
3-Search for customer information by name
4-Display feast menus
5-Place a feast order
6-Update order information
7-Save data to file
8-Display Customer list
9-Display Order list
10-Quit
Choose [1, 10]:
```

### Luồng sử dụng cơ bản

```
Bước 1 ──▶ [4] Xem thực đơn tiệc
Bước 2 ──▶ [1] Đăng ký khách hàng
Bước 3 ──▶ [5] Đặt tiệc (chọn khách → chọn menu → số bàn → ngày)
Bước 4 ──▶ [7] Lưu dữ liệu ra file
Bước 5 ──▶ [9] Xem danh sách đơn hàng để kiểm tra
```

---

## 🗄️ Cấu trúc dữ liệu

### Customer

| Trường | Kiểu | Mô tả | Ràng buộc |
|--------|------|-------|-----------|
| `id` | `String` | Mã khách hàng | `[C/G/K] + 4 chữ số` (VD: `C1001`, `G2345`) |
| `name` | `String` | Tên khách hàng | 2–25 ký tự |
| `phone` | `String` | Số điện thoại | 10 chữ số, đầu `0[2/3/5/7/8/9]` |
| `email` | `String` | Địa chỉ email | Đúng định dạng email |

### SetMenu

| Trường | Kiểu | Mô tả |
|--------|------|-------|
| `menuId` | `String` | Mã set menu (VD: `PW001`) |
| `menuName` | `String` | Tên set menu |
| `price` | `int` | Giá mỗi bàn (VND) |
| `ingredients` | `String` | Danh sách món, ngăn cách bởi `#` |

### Order

| Trường | Kiểu | Mô tả |
|--------|------|-------|
| `orderCode` | `int` | Mã đơn hàng (tự động tăng) |
| `customerId` | `String` | Mã khách hàng |
| `setMenuCode` | `String` | Mã set menu đã chọn |
| `numOfTables` | `int` | Số bàn (1–100) |
| `preferedDate` | `Date` | Ngày tổ chức (phải sau ngày hiện tại) |

> **Tổng tiền** = `price × numOfTables`

---

## 📦 Mô tả các class

### `run.FeastMng` — Entry Point
- Khởi tạo 3 danh sách: `SetMenuList`, `CustList`, `OrderList`
- Đọc dữ liệu từ file khi chương trình khởi động
- Hiển thị menu chính và xử lý lựa chọn
- Theo dõi trạng thái thay đổi (`custChanged`, `orderChanged`) để hỏi lưu khi thoát

### `core.Customer` — Model khách hàng
- Implements `Serializable` để hỗ trợ ghi/đọc file `.dat`
- Validation ngay trong setter (tên, SĐT, email)
- Override `equals()`, `hashCode()`, `toString()`

### `core.CustList` — Quản lý khách hàng
- Extends `ArrayList<Customer>`
- CRUD: Thêm, cập nhật, tìm kiếm theo tên (tìm gần đúng, không phân biệt hoa/thường)
- I/O: Đọc/ghi file `.dat` bằng `ObjectInputStream` / `ObjectOutputStream`

### `core.SetMenu` — Model set menu
- Chứa thông tin thực đơn tiệc (mã, tên, giá, danh sách món)
- Hỗ trợ hiển thị chi tiết với `toStringScreen()`

### `core.SetMenuList` — Quản lý set menu
- Extends `ArrayList<SetMenu>`
- Đọc dữ liệu từ file CSV (`FeastMenu.csv`)
- Tìm kiếm set menu theo mã

### `core.Order` — Model đơn hàng
- Implements `Serializable`
- Tự động tính tổng tiền qua `getTotalCost()`

### `core.OrderList` — Quản lý đơn hàng
- Extends `ArrayList<Order>`
- Tham chiếu tới `SetMenuList` và `CustList` để validate khi đặt hàng
- Tự động sinh `orderCode` tăng dần
- In chi tiết đơn hàng với thông tin khách + menu + tổng tiền

### `tool.ConsoleInputter` — Tiện ích nhập liệu
- Tập hợp các static method hỗ trợ nhập dữ liệu từ console
- Hỗ trợ: `boolean`, `int`, `double`, `String` (có/không pattern), `Date`
- Menu builder: `intMenu()`, `objMenu()` — tự động hiển thị và validate lựa chọn

---

## ✅ Định dạng & Validation

| Dữ liệu | Pattern / Ràng buộc |
|----------|---------------------|
| Mã khách hàng | `^[cCgGkK]\d{4}$` — Bắt đầu bằng C, G hoặc K + 4 chữ số |
| Tên khách hàng | `^[\w][\w ]{1,24}$` — 2 đến 25 ký tự |
| Số điện thoại | `^0[235789]\d{8}$` — SĐT Việt Nam 10 số |
| Email | `^[\w-\.]+[\w-\.]@([\w]+\.)+[\w]+[\w]$` |
| Ngày tổ chức | `dd/MM/yyyy` — Phải sau ngày hiện tại |
| Số bàn | `1 – 100` |

---

## 🍜 Thực đơn mẫu

Chương trình đi kèm **6 set menu** được load từ `FeastMenu.csv`:

| Mã | Tên | Giá/bàn (VND) |
|----|-----|---------------|
| PW001 | Wedding Party 01 | 3,750,000 |
| PW002 | Company Year End Party | 2,085,000 |
| PW003 | Birthday Party 01 | 1,850,000 |
| PW004 | Wedding Party 02 | 3,550,000 |
| PW005 | Birthday Party 02 | 2,250,000 |
| PW006 | Meeting Party | 1,950,000 |

Mỗi set menu bao gồm **3 phần**: Khai vị, Món chính, và Tráng miệng.

---

## 📂 Lưu trữ dữ liệu

| File | Định dạng | Mô tả |
|------|-----------|-------|
| `FeastMenu.csv` | CSV (text) | Dữ liệu thực đơn — **chỉ đọc**, có sẵn |
| `customers.dat` | Java Object Serialization (binary) | Dữ liệu khách hàng — tự tạo khi lưu lần đầu |
| `feast_order_service.dat` | Java Object Serialization (binary) | Dữ liệu đơn hàng — tự tạo khi lưu lần đầu |

> **Lưu ý:** Các file `.dat` được tạo tự động khi người dùng chọn **[7] Save data to file** lần đầu tiên. Nếu file chưa tồn tại khi khởi động, chương trình sẽ bỏ qua và tiếp tục chạy bình thường.

---

## 🛠️ Công nghệ sử dụng

- **Ngôn ngữ:** Java SE
- **IDE gốc:** Apache NetBeans
- **Lưu trữ:** Java Object Serialization (`.dat`) + CSV
- **Patterns:** MVC đơn giản (Model → `Customer`, `Order`, `SetMenu` | Controller → `CustList`, `OrderList`, `SetMenuList`)

---

## 👤 Tác giả

- **Sinh viên FPT University**
- **Môn học:** LAB211 — OOP with Java Lab
- **Học kỳ:** Fall 2026

---

## 📄 License

Dự án này được thực hiện cho mục đích học tập tại FPT University.
