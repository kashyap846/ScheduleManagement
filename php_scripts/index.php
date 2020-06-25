<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
    
    form>* {
        display: block;
    }

    form {
        padding: 2rem;
        margin: 0 50%;
        transform: translateX(-50%);
        width: 10%;
    }
    
    </style>
</head>
<body>
    <form action="/signup.php" method="post">
        <h3>Sign Up</h3>
        <label for="email">email</label>
        <input type="email" name="email" id="email">

        <label for="password">Password</label>
        <input type="password" name="password" id="password">
        
        <input type="submit" value="Sign Up!">
    </form>

    <form action="/login.php" method="post">
        <h3>Login</h3>
        <label for="email">email</label>
        <input type="email" name="email" id="email">

        <label for="password">Password</label>
        <input type="password" name="password" id="password">
        
        <input type="submit" value="Login!">
    </form>
    
</body>
</html>