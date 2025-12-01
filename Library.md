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

### Scenariusz 2: Dodanie nowej książki (Bibliotekarz)

Sekwencja przedstawia proces dodawania nowej pozycji do inwentarza przez pracownika.
```plantuml
@startuml
actor Bibliotekarz
participant "LibraryInterface" as UI
participant "LibraryManager" as Mgr
participant "Book" as Bk

Bibliotekarz -> UI: inputBookData("ISBN-123", "Tytuł", "Autor")
activate UI

UI -> Mgr: addNewBook("ISBN-123", "Tytuł", "Autor")
activate Mgr

Mgr -> Bk: new Book("ISBN-123", "Tytuł", "Autor")
activate Bk
Bk --> Mgr: bookInstance
deactivate Bk

Mgr -> Mgr: inventory.add(bookInstance)
note right: Dodanie do wewnętrznej listy

Mgr --> UI: success
deactivate Mgr

UI --> Bibliotekarz: "Książka dodana do bazy"
deactivate UI
@enduml
```

Opisuje kolejnye sekwencje dla wszystkich przypadków użycia. Może to prowadzić do zapełnienia pliku readme (staje się mało czytelny). Można zastosować nieco inne podejście:

## 1. Diagram Sekwencji: Cykl życia wypożyczenia (Czytelnik)

Ten diagram łączy: Wyszukiwanie, Wypożyczenie i Zwrot w jeden logiczny ciąg z użyciem bloku alt

```plantuml
@startuml
title Proces Obsługi Czytelnika (Wyszukiwanie / Wypożyczenie / Zwrot)
actor "Czytelnik" as User
participant "Interfejs" as UI
participant "LibraryManager" as Mgr
participant "Book" as Bk
participant "Loan" as Ln

User -> UI: Wybór akcji
activate UI

alt Scenariusz: Wypożyczenie Książki
    User -> UI: szukaj("Wiedźmin")
    UI -> Mgr: findBook("Wiedźmin")
    activate Mgr
    Mgr -> Bk: getStatus()
    activate Bk
    Bk --> Mgr: AVAILABLE
    deactivate Bk
    Mgr --> UI: Zwróć detale książki
    deactivate Mgr
    
    User -> UI: wypożycz(Ksiazka)
    UI -> Mgr: processLoan(User, Book)
    activate Mgr
    Mgr -> Bk: setStatus(LOANED)
    activate Bk
    deactivate Bk
    Mgr -> Ln: create(User, Book)
    activate Ln
    Ln --> Mgr: LoanID
    deactivate Ln
    Mgr --> UI: Sukces
    deactivate Mgr
    UI --> User: Potwierdzenie wypożyczenia

else Scenariusz: Zwrot Książki
    User -> UI: zwróć(Ksiazka)
    UI -> Mgr: returnBook(User, Book)
    activate Mgr
    Mgr -> Ln: markAsReturned()
    activate Ln
    Ln -> Ln: calculateFine()
    Ln --> Mgr: fineAmount (0.0)
    deactivate Ln
    Mgr -> Bk: setStatus(AVAILABLE)
    activate Bk
    deactivate Bk
    Mgr --> UI: Zwrot przyjęty
    deactivate Mgr
    UI --> User: Potwierdzenie zwrotu
end

deactivate UI
@enduml
```

## 2. Diagram Sekwencji: Operacje Administracyjne i Zarządcze

Ten diagram optymalizuje procesy Bibliotekarza (zarządzanie zasobami) i Administratora (zarządzanie ludźmi) na jednym widoku, pokazując, że LibraryManager jest centralnym punktem sterowania.

```plantuml
@startuml
title Operacje Zarządcze (Admin / Bibliotekarz)
actor "Administrator" as Admin
actor "Bibliotekarz" as Lib
participant "LibraryInterface" as UI
participant "LibraryManager" as Mgr
participant "UserRegistry" as UR
participant "Inventory" as Inv

note over Admin, Lib: Użytkownik musi być zalogowany z odpowiednią rolą

alt Przypadek: Zarządzanie Użytkownikami (Admin)
    Admin -> UI: registerUser("Jan Kowalski", Rola)
    activate UI
    UI -> Mgr: createUser(Dane, Rola)
    activate Mgr
    Mgr -> UR: addNew(UserObject)
    activate UR
    UR --> Mgr: UserID
    deactivate UR
    Mgr --> UI: Potwierdzenie
    deactivate Mgr
    UI --> Admin: Użytkownik dodany

    else Blokada Użytkownika
    Admin -> UI: blockUser(UserID)
    activate UI
    UI -> Mgr: setUserStatus(UserID, BLOCKED)
    activate Mgr
    Mgr -> UR: updateStatus(UserID, BLOCKED)
    Mgr --> UI: Sukces
    deactivate Mgr
    UI --> Admin: Konto zablokowane
    deactivate UI

else Przypadek: Zarządzanie Księgozbiorem (Bibliotekarz)
    Lib -> UI: addBook(ISBN, Tytuł)
    activate UI
    UI -> Mgr: addNewBook(ISBN, ...)
    activate Mgr
    Mgr -> Inv: add(BookObject)
    activate Inv
    Inv --> Mgr: Success
    deactivate Inv
    Mgr --> UI: Książka w systemie
    deactivate Mgr
    UI --> Lib: Potwierdzenie dodania

    else Usunięcie Książki
    Lib -> UI: removeBook(BookID)
    activate UI
    UI -> Mgr: deleteBook(BookID)
    activate Mgr
    Mgr -> Inv: remove(BookID)
    Mgr --> UI: Usunięto
    deactivate Mgr
    UI --> Lib: Książka usunięta z bazy
    deactivate UI
end
@enduml
```

