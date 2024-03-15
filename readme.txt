# CDNC1_Mining-top-k-high-utility-frequent-patterns-from-uncertain-databases

THỰC HIÊN CHẠY THUẬT TOÁN TRONG TERMINAL CỦA INTELLIJ

I - Chuẩn bị chạy chương trình:

1. Mở project - thư mục TUHUFP (hoặc cmd tại ../TUHUFP nếu chạy trên command prompt)
2. Giải nén data.zip (extract here) trong TUHUFP/data
3. Vào thư mục chứa thuật toán bằng câu lệnh:
> cd algorithm/src

II - Tiến hành chạy chương trình
Chạy các bộ dữ liệu từ bài báo trong thư mục test:

1. Chess
- Các mức testcase gồm: k = {100, 500, 900} và percentage = {12%, 14%, 16%, 18%, 20%, 22%}
- Mặc định k = 900 và percentage = 22% (tùy chỉnh nếu có):
> javac -d bin test/MainTestTUHUFPChess.java
> java -cp bin test.MainTestTUHUFPChess
- Kết quả được lưu trong thư mục out

2. Foodmart
- Các mức testcase gồm: k = {100, 500, 900} và percentage = {0.009%, 0.0095%, 0.01%, 0.0105%, 0.011%, 0.0115%}
- Mặc định k = 900 và percnetage = 0.0115% (tùy chỉnh nếu có)
- Tiến hành chạy lệnh:
> javac -d bin test/MainTestTUHUFPFoodmart.java
> java -cp bin test.MainTestTUHUFPFoodmart
- Kết quả được lưu trong thư mục out

3. Retail
- Các mức testcase gồm: k = {100, 500, 900} và percentage = {0.02%, 0.025%, 0.03%, 0.035%, 0.04%, 0.045%}
- Mặc định k = 900 và percnetage = 0.045% (tùy chỉnh nếu có)
- Tiến hành chạy lệnh:
> javac -d bin test/MainTestTUHUFPRetail.java
> java -cp bin test.MainTestTUHUFPRetail
- Kết quả được lưu trong thư mục out

4. Pumsb
- Các mức testcase gồm: k = {100, 500, 900} và percentage = {0.2%, 0.4%, 0.6%, 0.8%, 1%, 1.2%}
- Mặc định k = 900 và percnetage = 1.2% (tùy chỉnh nếu có)
- Tiến hành chạy lệnh:
> javac -d bin test/MainTestTUHUFPPumsb.java
> java -cp bin test.MainTestTUHUFPPumsb
- Kết quả được lưu trong thư mục out





