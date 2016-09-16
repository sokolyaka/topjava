### Test UserMealRestController (application deployed with context `topjava`).
> For windows use `Git Bash`

## Meals

#### get all
`curl -s http://localhost:8080/topjava/rest/profile/meals -H 'Authorization:Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### get 100003
`curl -s http://localhost:8080/topjava/rest/profile/meals/100003 -H 'Authorization:Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### get between
`curl -s "http://localhost:8080/topjava/rest/profile/meals/between?startDateTime=2015-05-30T08:00:00&endDateTime=2015-05-30T16:00:00" -H 'Authorization:Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### get not found
`curl -s -v http://localhost:8080/rest/meals/100008 -H 'Authorization:Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### delete
`curl -s -X DELETE http://localhost:8080/rest/meals/100002 -H 'Authorization:Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### create
`curl -s -v -X POST -d '{"dateTime":"2015-06-01T12:00","description":"Created lunch","calories":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/profile/meals -H 'Authorization:Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### update
`curl -s -v -X PUT -d '{"dateTime":"2015-05-30T07:00", "description":"Updated breakfast"}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/profile/meals/100003 -H 'Authorization:Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`