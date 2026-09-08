# Library Management API

Spring Boot 3 kullanılarak geliştirilmiş Kitap ve Kullanıcı Yönetimi ile Ödünç Alma süreçlerini yöneten RESTful API projesidir.

---

## Genel Özellikler

- Katmanlı Mimari (Controller, Service, Repository, DTO, Mapper)
- Spring Security ve JWT ile kimlik doğrulama ve yetkilendirme (ROLE_USER, ROLE_ADMIN)
- Bean Validation (`jakarta.validation`) ve `@RestControllerAdvice` ile merkezi hata yönetimi
- Flyway ile versiyonlu veritabanı şema migrasyonu
- Kitap yönetimi (CRUD), kullanıcı kaydı ve kitap ödünç alma/teslim etme iş akışları
- H2 in-memory veritabanı ve MockMvc kullanılarak yazılmış birim ve entegrasyon testleri
- OpenAPI 3.0 / Swagger UI entegrasyonu

---

## Kullanılan Teknolojiler

- Java 17
- Spring Boot 3.5.3
- Spring Data JPA & Hibernate
- Spring Security & JJWT (0.12.6)
- PostgreSQL (Production) / H2 (Test)
- Flyway Migration
- SpringDoc OpenAPI (Swagger UI)
- JUnit 5 & Mockito
- Lombok

---

## Proje Yapısı

```text
src/
├── main/
│   ├── java/com/example/library/
│   │   ├── config/             # Security ve uygulama konfigürasyonları
│   │   ├── controller/         # REST uç noktaları (Book, User, Loan, Auth)
│   │   ├── dto/                # Data Transfer Objects
│   │   ├── entity/             # JPA Varlıkları (Book, User, BookLoan)
│   │   ├── exception/          # Özel istisnalar ve GlobalExceptionHandler
│   │   ├── mapper/             # Entity - DTO dönüşüm sınıfları
│   │   ├── repository/         # JPA Repository arayüzleri
│   │   ├── security/           # JWT bileşenleri ve CustomUserDetailsService
│   │   └── service/            # Servis katmanı arayüz ve uygulamaları
│   └── resources/
│       ├── db/migration/       # Flyway SQL migrasyon scriptleri
│       └── application.yml     # Konfigürasyon dosyası
└── test/                       # Birim ve entegrasyon testleri
```

---

## Kurulum ve Çalıştırma

### Gereksinimler
- Java 17+
- Maven 3.8+ (veya proje içindeki `./mvnw`)
- PostgreSQL (`localhost:5432/library_db`)

### Çalıştırma Adımları

1. Repoyu klonlayın:
   ```bash
   git clone https://github.com/aysemu/library-management-api.git
   cd library-management-api
   ```

2. Testleri çalıştırın:
   ```bash
   ./mvnw clean test
   ```

3. Uygulamayı başlatın:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## API Uç Noktaları

### Auth (`/api/v1/auth`)
- `POST /api/v1/auth/register` - Yeni kullanıcı kaydı
- `POST /api/v1/auth/login` - Kullanıcı girişi ve JWT üretimi

### Books (`/api/v1/books`)
- `GET /api/v1/books` - Tüm kitapları listeleme
- `GET /api/v1/books/{id}` - ID'ye göre kitap sorgulama
- `POST /api/v1/books` - Yeni kitap ekleme
- `PUT /api/v1/books/{id}` - Kitap güncelleme
- `DELETE /api/v1/books/{id}` - Kitap silme
- `GET /api/v1/books/search/keyword?keyword=...` - Başlıkta arama

### Users (`/api/v1/users`)
- `GET /api/v1/users` - Kullanıcıları listeleme
- `GET /api/v1/users/{id}` - Kullanıcı detayı
- `POST /api/v1/users` - Kullanıcı ekleme

### Loans (`/api/v1/loans`)
- `POST /api/v1/loans/borrow` - Kitap ödünç alma
- `POST /api/v1/loans/{loanId}/return` - Kitap teslim etme
- `GET /api/v1/loans/user/{userId}` - Kullanıcının ödünç aldığı kitapları listeleme

---

## Dokümantasyon (Swagger UI)

Uygulama çalıştıktan sonra Swagger arayüzüne aşağıdaki adresten erişebilirsiniz:

`http://localhost:8080/swagger-ui.html`
