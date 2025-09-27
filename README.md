# Hệ thống Quản lý Dữ liệu & Phân quyền

## Mô tả dự án
Đây là một dự án xây dựng **hệ thống quản lý dữ liệu và phân quyền** sử dụng kiến trúc hiện đại với Front-end và Back-end tách biệt.  
Dự án được triển khai thành công trên nền tảng **Cloud (AWS)**, đảm bảo **hiệu suất cao, bảo mật và trải nghiệm người dùng tối ưu**.

### Các tính năng chính
- Quản lý dữ liệu với quyền truy cập khác nhau cho từng vai trò (Admin, Giáo viên, Sinh viên)
- Phân quyền đa cấp (Multi-Role Authorization) bảo vệ tài nguyên và API
- Giao diện React kết nối trực tiếp với RESTful API qua Fetch API
- Triển khai và vận hành trên Cloud với Docker & AWS

---

## Công nghệ sử dụng
- **Backend:** Java, Spring Boot, Spring Security, RESTful API  
- **Frontend:** React, BootstrapReact, Fetch API  
- **DevOps & Cloud:** Docker, Docker Hub, AWS  
- **Kiểm thử:** Postman  

---

## Thành tựu nổi bật

### 1. Triển khai Cloud & DevOps
- Đóng gói Backend bằng Docker và đẩy lên Docker Hub
- Triển khai ứng dụng trên AWS , tối ưu hóa quy trình vận hành (Operation)

### 2. Bảo mật & Phân quyền Nâng cao
- Triển khai mô hình Multi-Role Authorization với Spring Security
- Kiểm soát quyền truy cập chặt chẽ cho Admin, Giáo viên và Sinh viên

### 3. Kiến trúc hiện đại
- Frontend React sử dụng Fetch API để kết nối với RESTful API từ Spring Boot
- Đảm bảo hiệu suất và trải nghiệm người dùng tối ưu

### 4. Kiểm thử
- Sử dụng Postman để kiểm thử và xác thực tính ổn định của các endpoints
- Tập trung vào các chức năng liên quan đến bảo mật và phân quyền

---

## Hướng dẫn chạy dự án
1. Clone repository:
```bash
git clone https://github.com/username/ProjectRestfulApi.git
