# System Zarządzania Biblioteką (Library Management System)

## Opis Projektu
Projekt to prosty system informatyczny służący do obsługi procesów bibliotecznych, zaprojektowany w paradygmacie obiektowym. Aplikacja umożliwia zarządzanie księgozbiorem, obsługę wypożyczeń oraz administrację użytkownikami. Projekt kładzie nacisk na poprawną strukturę klas, separację uprawnień oraz wykorzystanie podstawowych wzorców projektowych.

## Aktorzy Systemu

1.  **Czytelnik (Reader):**
    * Przeglądanie katalogu książek.
    * Wypożyczanie dostępnych pozycji.
    * Zwrot książek.
    * Podgląd historii wypożyczeń.
2.  **Bibliotekarz (Librarian):**
    * Zarządzanie księgozbiorem (dodawanie/usuwanie książek).
    * Weryfikacja stanu książek.
3.  **Administrator (Admin):**
    * Zarządzanie użytkownikami (dodawanie pracowników, czytelników).
    * Blokowanie kont użytkowników.

---

## 1. Diagram Przypadków Użycia (Use Case Diagram)

Diagram obrazuje interakcje aktorów z funkcjami systemu. Wszyscy użytkownicy muszą się zalogować, aby uzyskać dostęp do swoich funkcji.

```plantuml
@startuml
left to right direction
actor "Czytelnik" as User
actor "Bibliotekarz" as Emp
actor "Administrator" as Admin

package "System Biblioteczny" {
  
  usecase "Logowanie" as UC_Login
  
  usecase "Wyszukiwanie książki" as UC_Search
  usecase "Wypożyczenie książki" as UC_Borrow
  usecase "Zwrot książki" as UC_Return
  
  usecase "Dodanie nowej książki" as UC_AddBook
  usecase "Usunięcie książki" as UC_RemoveBook
  
  usecase "Rejestracja użytkownika" as UC_AddUser
  usecase "Zablokowanie konta" as UC_BlockUser
}

User --|> UC_Login
Emp --|> UC_Login
Admin --|> UC_Login

User --> UC_Search
User --> UC_Borrow
User --> UC_Return

Emp --> UC_Search
Emp --> UC_AddBook
Emp --> UC_RemoveBook

Admin --> UC_AddUser
Admin --> UC_BlockUser

UC_Borrow ..> UC_Login : <<include>>
UC_AddBook ..> UC_Login : <<include>>
UC_AddUser ..> UC_Login : <<include>>

@enduml
```

## 2. Diagram Klas
Model domenowy systemu. Zastosowano klasę abstrakcyjną User dla wspólnych cech użytkowników oraz LibraryManager (Singleton) jako główny punkt dostępu do logiki biznesowej.

```plantuml
@startuml

enum BookStatus {
  AVAILABLE
  LOANED
  RESERVED
  MAINTENANCE
}

enum UserRole {
  READER
  LIBRARIAN
  ADMIN
}

class Book {
  - String isbn
  - String title
  - String author
  - BookStatus status
  + getDetails() : String
  + setStatus(BookStatus)
}

abstract class User {
  - int id
  - String name
  - String password
  - UserRole role
  + login() : boolean
}

class Reader extends User {
  - List<Loan> currentLoans
  + borrow(Book)
  + return(Book)
}

class Librarian extends User {
  + addBook(Book)
  + removeBook(Book)
}

class Admin extends User {
  + blockUser(User)
  + registerUser(User)
}

class Loan {
  - Date loanDate
  - Date dueDate
  - Book book
  - Reader reader
  + isOverdue() : boolean
  + calculateFine() : double
}

class LibraryManager {
  - static LibraryManager instance
  - List<Book> inventory
  - List<User> users
  - LibraryManager()
  + getInstance() : LibraryManager
  + findBook(String title) : Book
  + processLoan(Reader, Book)
}

LibraryManager "1" *-- "*" Book
LibraryManager "1" o-- "*" User
Reader "1" o-- "*" Loan
Loan "1" --> "1" Book

@enduml
```

## 3. Diagramy Sekwencji (Sequence Diagrams)

### Scenariusz 1: Wypożyczenie książki (Ścieżka pozytywna)

Sekwencja przedstawia proces wypożyczenia książki przez zalogowanego czytelnika.
```plantuml
@startuml
actor Czytelnik
participant "LibraryInterface" as UI
participant "LibraryManager" as Mgr
participant "Book" as Bk
participant "Loan" as Ln

Czytelnik -> UI: clickBorrow("Wiedźmin")
activate UI

UI -> Mgr: borrowRequest(currentUser, "Wiedźmin")
activate Mgr

Mgr -> Mgr: findBook("Wiedźmin")
Mgr -> Bk: getStatus()
activate Bk
Bk --> Mgr: AVAILABLE
deactivate Bk

Mgr -> Bk: setStatus(LOANED)
activate Bk
deactivate Bk

Mgr -> Ln: new Loan(currentUser, book)
activate Ln
Ln --> Mgr: loanInstance
deactivate Ln

Mgr --> UI: success
deactivate Mgr

UI --> Czytelnik: "Książka wypożyczona pomyślnie"
deactivate UI
@enduml
```
