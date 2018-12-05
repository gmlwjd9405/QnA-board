# QnA Board
### Spring Boot, JPA를 이용한 질문/답변 게시판
   
---

## REST API
### Domain 
Server | Domain
---- | ----
Embedded Tomcat(WAS) | localhost:8080

### API
- **메인 페이지**
    
Method | URI | Description
---- | ---- | ----
GET | / | return index.html view (show questions list)

- **사용자 관리**
    - Base URI: /users

Method | URI | Description
---- | ---- | ----
GET| loginForm | return user/login.html view
POST | /login | login user 
GET | /logout | logout user
GET | /form | return user/form.html view
POST | | create user
GET | | return user/list.html view 
GET | /{id}/form |  return user/updateForm.html view
PUT | /{id} | update user

- **질문하기**
    - Base URI: /questions

Method | URI | Description
---- | ---- | ----  
GET | /form | return qna/form.html view
POST | | create question
GET | /{id} | return qna/show.html view
GET | /{id}/form | return qna/updateForm.html view
PUT | /{id} | update question
DELETE | /{id} | delete question

- **답변하기**
    - Base URI: /api/questions/{{questionID}}/answers

Method | URI | Description
---- | ---- | ----  
POST | | create answer
 
---

## Development Environment
- Language
    - Java8
    - HTML, CSS
- Back-end
    - Spring Boot 2.1.0
    - Spring Data JPA
        - hibernate-core 5.3.7
        - hibernate-validator 6.0.13
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
