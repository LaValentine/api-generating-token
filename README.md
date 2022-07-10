# Описание
Сервис генерирует jwt токен для заранее зарегестрированных пользователей и сохраняеет отправляемые ими сообщения
## Технологии
* Java 11
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Flyway

## Запуск приложения
### Шаг #1
Установите переменные окружения или же оставьте значения поумолчанию
  ```
    SERVER_PORT=1509
    DATASOURCE_HOST=localhost
    DATASOURCE_PORT=5432
    DATASOURCE_DATABASE=api-generating-token
    DATASOURCE_USERNAME=postgres
    DATASOURCE_USERNAME=root
  ```
### Шаг #2 
Проверьте существует ли база данных с именем, храмимым переменной окружения `DATASOURCE_DATABASE`
Необходимо создать, если такой базы данных не существует
  ```
    CREATE DATABASE DATASOURCE_DATABASE
  ```

### Шаг #3
  ```
    mvn package -DskipTests
  ```
### Шаг #4
  ```
    java -jar ./target/api-generating-token-0.0.1-SNAPSHOT.jar
  ```
## Запуск приложения с использованием Docker
### Шаг #1
  ```
    docker run --name db -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=api-generating-token postgres
  ```
### Шаг #2
  ```
    docker build -t api-generating-token .
  ```
### Шаг #3
  ```
    docker run --name api-generating-token -p 1509:1509 -e DATASOURCE_HOST=host.docker.internal api-generating-token
  ```
## Или
### Шаг #1
  ```
    docker compose up
  ```

## Документация API
### POST  `/api/login`
#### Верификация пользователя и получение токена
* Запрос
  * Тип данных: `Application/json`
  * Формат данных:
```
{ 
    name: "имя отправителя" 
    password: "пароль"  
} 
```
* Ответ
  * Тип данных: `Application/json`
  * Формат данных:
```
{ 
    token: "тут сгенерированный токен" 
} 
```  
### POST `/api/message`
#### Сохранение сообщения
* Запрос
  * Заголовок
  ```
  Authorization = Bearer_"тут сгенерированный токен"
  ```
  * Тип данных: `Application/json`
  * Формат данных:
  ```
  { 
      name: "имя отправителя", 
      message: "текст сообщение" 
  }  
  ```
* Ответ
  * Тип данных: `Application/json`
  * Формат данных:
  ```
  { 
      name: "имя отправителя", 
      message: "текст сообщение"  
  } 
  ``` 
#### Получение истории 10 сообщений
* Запрос
  * Заголовок
  ```
    Authorization = Bearer_"тут сгенерированный токен"
  ```
  * Тип данных: `Application/json`
  * Формат данных:
  ```
  { 
      name: "имя отправителя", 
      message: "history 10" 
  }  
  ```
* Ответ
  * Тип данных: `Application/json`
  * Формат данных:
  ```
  [
      { 
          name: "имя отправителя", 
          message: "текст сообщение" 
      },
      { 
          name: "имя отправителя", 
          message: "текст сообщение" 
      },
      { 
          name: "имя отправителя", 
          message: "текст сообщение" 
      },
      { 
          name: "имя отправителя", 
          message: "текст сообщение" 
      },
      { 
          name: "имя отправителя", 
          message: "текст сообщение" 
      },
      { 
          name: "имя отправителя", 
          message: "текст сообщение" 
      },
      { 
          name: "имя отправителя", 
          message: "текст сообщение" 
      },
      { 
          name: "имя отправителя", 
          message: "текст сообщение" 
      },
      { 
          name: "имя отправителя", 
          message: "текст сообщение" 
      },
      { 
          name: "имя отправителя", 
          message: "текст сообщение" 
      }
  ] 
  ```

## Примеры запросов через терминал 
*** Для демостриции откройте `curl-requests.bat`

*** Примеры состалены с учетом, что сервис запущен с порта 1509
>  ### Запрос
>  ```
>    curl -X POST -H "Content-Type:application/json" -d "{ \"name\": \"Diana\", \"password\": \"FRqjSU\" }" http://127.0.0.1:1509/api/login
>  ```
>  ### Ответ
> ```
>  {
>    "token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEaWFuYSJ9.dM0RCyiJF_SVpMFia7hWACdHmdoNvGNjoNZj-rPe8u8"
>  }
> ```

> ### Запрос
> ```
>  curl -X POST -H "Content-Type:application/json" -d "{ \"name\": \"Diana\", \"password\": \"wrong-password\" }" http://127.0.0.1:1509/api/login
> ```
> ### Ответ
> ```
>  Wrong password
> ```

> ### Запрос
> ```
>   curl -X POST -H "Content-Type:application/json" -d "{ \"name\": \"User\", \"password\": \"password\" }" http://127.0.0.1:1509/api/login
> ```
> ### Ответ
> ```
>   User not exist
> ```

> ### Запрос
> ```
>   curl -X POST -H "Content-Type:application/json" -H "Authorization:Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEaWFuYSJ9.dM0RCyiJF_SVpMFia7hWACdHmdoNvGNjoNZj-rPe8u8" -d "{ \"name\": \"Diana\", \"message\": \"message\" }" http://127.0.0.1:1509/api/message
> ```
> ### Ответ
> ```
>   {
>     "name":"Diana",
>     "message":"message"
>   }
> ```

> ### Запрос
> ```
>  curl -X POST -H "Content-Type:application/json" -H "Authorization:Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEaWFuYSJ9.dM0RCyiJF_SVpMFia7hWACdHmdoNvGNjoNZj-rPe8u8" -d "{ \"name\": \"Diana\", \"message\": \"Diana sent any message\" }" http://127.0.0.1:1509/api/message
> ```
> ### Ответ
> ```
>   {
>     "name":"Diana",
>     "message":"Diana sent any message"
>   }
> ```

> ### Запрос
> ```
>   curl -X POST -H "Content-Type:application/json" -H "Authorization:Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEaWFuYSJ9.dM0RCyiJF_SVpMFia7hWACdHmdoNvGNjoNZj-rPe8u8" -d "{ \"name\": \"Diana\", \"message\": \"history 10\" }" http://127.0.0.1:1509/api/message
> ```
> ### Ответ
> ```
>   [
>     {
>       "name":"Diana",
>       "message":"message"
>     },
>     ...
>     {
>       "name":"Diana",
>       "message":"message"
>     },
>     {
>       "name":"Diana",
>       "message":"Diana sent any message"
>     }
>   ]
> ```

> ### Запрос
> ```
>   curl -X POST -H "Content-Type:application/json" -H "Authorization:token" -d "{ \"name\": \"Diana\", \"message\": \"message\" }" http://127.0.0.1:1509/api/message
> ```
> ### Ответ
> ```
>   Token is wrong
> ```