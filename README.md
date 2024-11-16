# 📚 Sistema de Gestão de Cursos

Bem-vindo ao Sistema de Gestão de Cursos! Este projeto é uma aplicação Java que permite gerenciar cursos, aulas, alunos e professores de forma eficiente e organizada.

**O projeto ainda está em construção**: nos pŕoximos meses quero aprimorar o código e adicionar segurança, além de fazer um frontend.

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
- **Swagger**: Para documentação da API e clientes HTTP.
## 🌐 Deploy

O projeto está sendo implantado utilizando o **Railway**, que oferece uma maneira simples e prática para fazer o deploy de aplicações em ambientes de produção.

Obs: Ficará no Railway por tempo limitado.

[![Railway](https://img.shields.io/badge/Railway-000000?style=for-the-badge&logo=railway&logoColor=white)](https://gerenciamento-cursos-production.up.railway.app/swagger-ui/index.html)

## 💻 Como rodar o programa no meu computador?
Dê um fork no repositório e faça um pull para um repositório local. Você só precisa de uma IDE e do ambiente Java instalados (esse projeto foi escrito em java 17).
Você pode utilizar um cliente http da sua preferência ou o próprio swagger (endopoint : swagger-ui/index.html#/).

Indico utilizar o perfil de dev (utiliza banco de dados h2).

## 📌 Endpoints
### Course Controller
- /courses : retorna todos os cursos existentes
- /courses/{title} : pesquisa os cursos por título
- /courses/teachers/{id} : retorna os professores de um curso a partir do id do curso
- /courses/register : cria um novo curso (deve conter título e descrição)
- /courses/description/{id} : altera a descrição de um curso a partir do seu id
- /courses/teachers/add/{id} : adiciona um professor ao curso a partir do id do curso 
- /courses/teachers/remove/{id} : remove um professor de um curso a partir do id do curso
- /courses/{id} : deleta um curso
### Teacher Controller
- /teachers : retorna todos os professores existentes
- /teachers/{name} : pesquisa os professores por nome
- /teachers/courses-taught/{id} : retorna cursos que um professor deu aula a partir do id do professor
- /teachers/register : cadastra novo professor
- /teachers/{id}/add-skill : adiciona uma skil ao perfil do professor
- /teachers/{id}/remove-skill : remove uma skill do perfil do professor
- /teachers/{id}/username : atualiza o username
- /teachers/{id}/email : atualiza o email
- /teachres/{id}/password : atualiza a senha
- /teachres/{id} : deleta um professor 
### Student Controller
- /students : retorna todos os professores existentes
- /students/search/{name} : pesquisa estudantes por nome
- /students/{id} : pesquisa estudantes por id
- /students/register : cria novo estudante
- /students/{id}/description : atualiza a descrição
- /students/{id}/username : atualiza o username
- /students/{id}/email : atualiza o email
- /students/{id}/password : atualiza a senha
- /students/{id} : deleta um estudante
### Enrollment Controller
- /enrollments : retorna todas as inscrições existentes
- /enrollments/course/{courseId} : retorna todas as inscrições de um curso
- /enrollments/course/active/{courseId} : retorna todas as inscrições ativas de um curso
- /enrollments/student/{studentId} : retorna todas as inscrições de um aluno
- /enrollments/subscribe : inscreve um estudante em um curso
- /enrollments/unsubscribe : desativa a inscrição
### Unit Controler
- /units/course/{courseId} : retorna todos os módulos de um curso
- /units/search/course/{courseId} : encontra unidade atravez do titulo (Request Param)
- /units/register : cria novo módulo
- /units/{id} : deleta um módulo
### Lesson Controller
- /lessons/units/{unitId} : retorna as aulas de um módulo
- /lessons/search/units/{unitId} : encontra aula de um modulo pelo titul (Request Param)
- /lessons/register : cria nova aula
- /lessons/{id} : deleta aula

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
ELEMENTARY
HIGH_SCHOOL
UNDERGRADUATE
MASTERS
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
 Course "1" --> "0..*" Unit
 Unit "1" --> "0..*" Lesson
 Student -- EducationalLevel
 Unit -- Difficulty

