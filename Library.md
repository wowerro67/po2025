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
