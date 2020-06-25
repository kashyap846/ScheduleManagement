<?php


$dbUser = "root";
$dbPass = "Campus@13";
$dbHost = "127.0.0.1:3306";
$dbName = "schedule_management";

$db = new PDO("mysql:host=$dbHost;dbname=$dbName", $dbUser, $dbPass);

$sql ="SELECT `firstname`, `lastname` FROM users LIMIT 0,1;";
$stmt= $db->prepare($sql);
$stmt->execute();
$row = $stmt->fetch();
print_r($row);

$success = false;

if (password_verify($password, $row["pasword"]))
{
    $success = true;
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>
</head>
<body>
    <h1><?php
        if ($success)
        {
                echo($row['email']."&nbsp;&nbsp;&nbsp;&nbsp;<br />\n");
        }
        else {
            echo("The user was not logged in");
        }
    ?></h1>
</body>
</html>