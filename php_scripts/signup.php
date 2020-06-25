<?php

$firstname = 'kash';
$lastname = 'kokkili';
$email = 'kashyap@gmail.com';


//$password = password_hash($password, PASSWORD_DEFAULT);

// $dbUser = "baritchie@maria-test-prof";
// $dbPass = "SuperPassword123";
// $dbHost = "maria-test-prof.mariadb.database.azure.com";
// $dbName = "test_db";

$dbUser = "root";
$dbPass = "Campus@13";
$dbHost = "127.0.0.1:3306";
$dbName = "schedule_management";

$db = new PDO("mysql:host=$dbHost;dbname=$dbName", $dbUser, $dbPass);

$sql = 'INSERT INTO USERS (`email`, `firstname`, `lastname`) VALUES (?, ?, ?)';
$stmt = $db->prepare($sql);
$rows = $stmt->execute(array($email, $firstname, $lastname));

$success = false;

if ($rows)
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
    <title>Document</title>
</head>
<body>
    <h1><?php
        if ($success)
        {
            echo("The user was created!");
        }
        else {
            echo("The user was not created");
        }
    ?></h1>
</body>
</html>