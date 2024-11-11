# 📚 Sistema de Gestão de Cursos

Bem-vindo ao Sistema de Gestão de Cursos! Este projeto é uma aplicação Java que permite gerenciar cursos, aulas, alunos e professores de forma eficiente e organizada.

**O projeto ainda está em construção**: faltam alguns ajustes para fazer o deploy.

*Meu objetivo é otimizar ao máximo minha aplicação, então se tiver alguma sugestão ou encontrar algum erro, sua ajuda é muito bem-vinda!* 😊

## 🚀 Funcionalidades

- **Gerenciamento de Cursos**: Crie, exclua e visualize cursos.
- **Gerenciamento de Aulas**: Adicione, exclua e liste aulas por módulo.
- **Inscrições**: Inscreva alunos em cursos e visualize as inscrições ativas.
- **Módulos**: Crie e gerencie módulos dentro dos cursos.
- **Gestão de Alunos e Professores**: Adicione, exclua e atualize informações de alunos e professores.

## 🛠️ Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **Hibernate**: Para manipulação do banco de dados.
- **JUnit** e **Mockito**: Para testes unitários.
- **H2**: Banco de dados SQL para testes.
- **Postgres**: Banco de dados SQL para produção.
- - **Swagger**: Para documentação da API e clientes HTTP.

## 📑 Documentação da API

A documentação da API é gerada automaticamente usando o **Swagger**. Você pode acessá-la no seguinte endpoint:

[![Swagger](https://img.shields.io/badge/Swagger-25B7B7?style=for-the-badge&logo=swagger&logoColor=white)](http://localhost:8080/swagger-ui.html)

Este endpoint fornece uma interface interativa para testar os endpoints da API, facilitando o uso e a integração.

## 🌐 Deploy

O projeto está sendo implantado utilizando o **Railway**, que oferece uma maneira simples e prática para fazer o deploy de aplicações em ambientes de produção.

[![Railway](https://img.shields.io/badge/Railway-000000?style=for-the-badge&logo=railway&logoColor=white)](sua-url-aqui)


## 📄 Licença

Este projeto é licenciado sob a [**MIT License**](https://github.com/pinhojuliana/gerenciamento-cursos?tab=MIT-1-ov-file#readme)

## 🧑‍💻 Contribuições

Se você deseja contribuir para o projeto, fique à vontade para dar um **fork** ou **enviar pull requests**. Caso tenha sugestões ou correções, abra uma issue.

## 📊 Diagrama UML
```mermaid
classDiagram
 class Client {
    <<abstract>>
    #id : UUID
    #name : String
    #username : String
    #password : String
    #email : String
    #dateOfBirth : LocalDate
    #createdAt : LocalDateTime
 }
 class Student {
    -description : String
    -educationalLevel : EducationalLevel
    +createNewStudent(requestPayload : StudentRequestPayload) : ClientResponse
    +updateStudentDescription(id : UUID, description : String ) : void
    +updateStudentEducationalLevel(id : UUID, educationalLevel : EducationalLevel) : void
    +updateStudentUsername(id : UUID, username : String) : void
    +updateStudentEmail(id : UUID, email : String) : void
    +updateStudentPassword(id : UUID, oldPassword : String, newPassword : String) : void
    +deleteStudent(id : UUID) : void
    +getAllStudents() : List<StudentDTO>
    +searchStudentName(name : String) : StudentDTO
    +searchStudentId(id : UUID) : StudentDTO
    -validateUniqueUsername(username : String) : void
    -validateUniqueEmail(email : String) : void
    -validateId(id : UUID) : void
    -checkForNoUpdate(oldValue : T, newValue : T) : <T>
    -convertToDTO(student : Student) : StudentDTO
    -validateSkill(skill : String) : boolean
 }
 class Teacher {
    -skills : List<String>
    +createNewTeacher(requestPayload : TeacherRequestPayload) : ClientResponse
    +addSkill(id : UUID, skill : String) : void
    +removeSkill(id : UUID, skill : String) : void
    +updateTeacherUsername(id : UUID, username : String) : void
    +updateTeacherEmail(id : UUID, email : String) : void
    +updateTeacherPassword(id : UUID, oldPassword : String, newPassword : String) : void
    +deleteTeacher(id : UUID) : void
    +findTeacher(name : String) : List<TeacherDTO>
    +getAllTeachers() : List<TeacherDTO>
    +showAllCoursesTaught(teacherId : UUID) : List<String>
    -validateUniqueUsername(username : String) : void
    -validateUniqueEmail(email : String) : void
    -validateId(id : UUID) : void
    -checkForNoUpdate(oldValue : T, newValue : T) : <T>
    -convertToDTO(teacher : Teacher) : TeacherDTO
 }

 class Enrollment {
    -id : UUID
    -student : Student
    -course : Course
    -enrollmentDateTime : LocalDate
    -deadlineForCompletion : LocalDate
    -duration : int
    -active : boolean
    +enrollStudentInCourse(requestPayload : EnrrolmentRequestPayload) : EnrollmentResponse
    +unsubscribeStudentOfCourse(requestPayload : EnrollmentRequestPayload) : void
    +showAllEnrollments() : List<Enrollment>
    +showCourseEnrollments(courseId : UUID) : List<Enrollment>
    +showStudentEnrollments(tudentId : UUID) : List<Enrollment>
    +showStudentsEnrollmentsActive(courseId : UUID) : List<Enrollment>
 }
 class Course {
    -id : UUID
    -title : String
    -description : String
    -createdAt : LocalDateTime
    -updatedAt : LocalDateTime
    +createNewCourse(requestPayload : CourseRequestPayload) : CourseResponse
    +deleteCourse(id : UUID) : void
    +alterDescription(id : UUID, description : String) : void
    +showCourses() : List<Course>
    +findCourseByTitle(title : String) : Course
    +addTeacher(teacherId : UUID, courseId : UUID) : void
    +removeTeacher(teacherId : UUID, courseId : UUID) : void
    +showTeachersOfCourse(courseId : UUID) : List<String>
    -validateUniqueTitle(title : String) : void
    -validateId(id : UUID) : void
 }
 direction BT
 class TeacherCourse{
    -id : UUID
    -teacher : Teacher
    -course: Course

 }
 class Unit{
    -id : UUID
    -title : String
    -description : String
    -difficulty : Difficulty
    -course : Course
    +createNewUnit(requestPayload : UnitRequestPayload) : UnitResponse
    +deleteUnit(id : UUID) : void
    +findUnitByCourse(courseId : UUID) : List<Unit>
    +findMUnitCourse(courseId : UUID, title : String) : List<Unit>
    -validateId(id : UUID) : Unit

 }
 class Lesson{
    -id : UUID
    -title : String
    -description : String
    -unit : Unit
    +createNewLesson(requestPayload : LessonRequestPayload) : LessonResponse
    +deleteLesson(id : UUID) : void
    +showLessonsOfModule (unitId : UUID) : List<Lesson>
    +findLessonsByTitle(title : String) : List<Lesson>
    -validateId(id : UUID) : ClassSection
 }

 class EducationalLevel{
    <<enumeration>>
 FUNDAMENTAL
 AVERAGE
 HIGHER
 MASTERS_DEGREE
 DOCTORATE
 }
 class Difficulty{
    <<enumeration>>
BEGINNER
INTERMEDIATE
ADVANCED
 }

 Client <|-- Student
 Client <|-- Teacher
 Course "1" --> "0..*" Enrollment 
 Student "1" --> "0..*" Enrollment 
 Course "0..*" --> "0..*" TeacherCourse
 Teacher "0..*" --> "0..*" TeacherCourse
 Course "1" --> "0..*" Unit
 Unit "1" --> "0..*" Lesson
 Student -- EducationalLevel
 Unit -- Difficulty

