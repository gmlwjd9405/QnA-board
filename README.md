# QnA Board
### Spring Boot, JPA를 이용한 질문/답변 게시판
   
---

## REST API
### Domain 
Server | Domain
---- | ----
WAS | localhost:8080

### API
- **사용자 관리**

Method | URI | Description
---- | ---- | ----
GET | /users/loginForm | return user/login.html view
POST | /users/login | login user 
GET | /users/logout | logout user
GET | /users/form | return user/form.html view
POST | /users | create user
GET | /users | return user/list.html view 
GET | /users/{id}/form |  return user/updateForm.html view
PUT | /users/{id} | update user

- **질문, 답변하기**

Method | URI | Description
---- | ---- | ----  
GET | / | return index.html view
GET | /questions/form | return qna/form.html view
POST | /questions | create question
GET | /questions/{id} | return qna/show.html view
GET | /questions/{id}/form | return qna/updateForm.html view
PUT | /questions/{id} | update question
DELETE | /questions/{id} | delete question

---

## Development Environment
- Language
    - Java8
    - HTML, CSS
- Back-end
    - Spring Boot 2.1.0
    - Spring Data JPA
    - H2 1.4.196
- Front-end
    - Bootstrap 3.3.7
    - jQuery 3.2.1
    - Mustache Template Engine
- Etc
    - GitHub
    - AWS EC2
    - Maven
    - Devtools
    
## References
* [https://www.slipp.net](https://www.slipp.net/wiki/pages/viewpage.action?pageId=25529113)
