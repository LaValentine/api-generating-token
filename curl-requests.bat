curl -X POST -H "Content-Type:application/json" -d "{ \"name\": \"Diana\", \"password\": \"FRqjSU\" }" http://127.0.0.1:1509/api/login

curl -X POST -H "Content-Type:application/json" -d "{ \"name\": \"Diana\", \"password\": \"wrong-password\" }" http://127.0.0.1:1509/api/login

curl -X POST -H "Content-Type:application/json" -d "{ \"name\": \"User\", \"password\": \"password\" }" http://127.0.0.1:1509/api/login

curl -X POST -H "Content-Type:application/json" -H "Authorization:Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEaWFuYSJ9.dM0RCyiJF_SVpMFia7hWACdHmdoNvGNjoNZj-rPe8u8" -d "{ \"name\": \"Diana\", \"message\": \"message\" }" http://127.0.0.1:1509/api/message

curl -X POST -H "Content-Type:application/json" -H "Authorization:Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEaWFuYSJ9.dM0RCyiJF_SVpMFia7hWACdHmdoNvGNjoNZj-rPe8u8" -d "{ \"name\": \"Diana\", \"message\": \"Diana sent any message\" }" http://127.0.0.1:1509/api/message

curl -X POST -H "Content-Type:application/json" -H "Authorization:Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEaWFuYSJ9.dM0RCyiJF_SVpMFia7hWACdHmdoNvGNjoNZj-rPe8u8" -d "{ \"name\": \"Diana\", \"message\": \"history 3\" }" http://127.0.0.1:1509/api/message

curl -X POST -H "Content-Type:application/json" -H "Authorization:token" -d "{ \"name\": \"Diana\", \"message\": \"message\" }" http://127.0.0.1:1509/api/message

pause
