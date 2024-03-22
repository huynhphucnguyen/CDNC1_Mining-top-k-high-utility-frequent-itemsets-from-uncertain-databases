Hướng dẫn chạy thực ngiệm trên terminal/command prompt:
Sau khi tải file nén về, tiến hành giải nén và thực hiện các bước sau: 

1. Vào thư mục TUHUFP.
2. Giải nén data.zip (extract here) trong TUHUFP/data.
3. Tại thư mục TUHUFP nhập cmd trên thanh đường dẫn để mở command prompt.
4. Vào thư mục chứa thuật toán bằng câu lệnh:
> cd algorithm/src

Chạy các bộ dữ liệu từ bài báo trong thư mục test:

1. Chess
> javac -d bin test/MainTestTUHUFPChess.java
> java -cp bin test.MainTestTUHUFPChess
Nhập số lượng k và ngưỡng tiện ích mong muốn.
Các mức testcase gồm: k = {100, 500, 900} và percentage = {12%, 14%, 16%, 18%, 20%, 22%}.

2. Foodmart
> javac -d bin test/MainTestTUHUFPFoodmart.java
> java -cp bin test.MainTestTUHUFPFoodmart
Nhập số lượng k và ngưỡng tiện ích mong muốn.
Các mức testcase gồm: k = {100, 500, 900} và percentage = {0.009%, 0.0095%, 0.01%, 0.0105%, 0.011%, 0.0115%}.

3. Retail
> javac -d bin test/MainTestTUHUFPRetail.java
> java -cp bin test.MainTestTUHUFPRetail
Nhập số lượng k và ngưỡng tiện ích mong muốn.
Các mức testcase gồm: k = {100, 500, 900} và percentage = {0.02%, 0.025%, 0.03%, 0.035%, 0.04%, 0.045%}.

4. Pumsb
> javac -d bin test/MainTestTUHUFPPumsb.java
> java -cp bin test.MainTestTUHUFPPumsb
Nhập số lượng k và ngưỡng tiện ích mong muốn.
- Các mức testcase gồm: k = {100, 500, 900} và percentage = {0.2%, 0.4%, 0.6%, 0.8%, 1%, 1.2%}.
