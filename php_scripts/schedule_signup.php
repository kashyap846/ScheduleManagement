<?php


$dbUser = "root";
$dbPass = "Campus@13";
$dbHost = "127.0.0.1:3306";
$dbName = "schedule_management";

//echo "my hi";
$emailID = $_GET['email'];
//$_GET['emailID'];
//'kashya@gmail.com';
$password = $_GET['password'];
//$_GET['password'];
//'Campus13';

$firstname = $_GET['firstname'];
//$_GET['firstname'];
//'kashyap';
$lastname = $_GET['lastname'];
//$_GET['lastname'];
//'kokkili';

$number = $_GET['number'];

$manager = $_GET['manager'];
echo "manager " . $manager. "  " . $manager == '-1';
//$_GET['number'];
//'7655456543';
//echo "before" . $_SERVER['REQUEST_METHOD'];
//echo "emailb  " . $emailID . "  passowrd   " . $password . "   firstname   " . $firstname ."   lastname   ". $lastname . "  number   " . $number;

$db = new PDO("mysql:host=$dbHost;dbname=$dbName", $dbUser, $dbPass);

if($number == '' || $password == '' || $emailID == '' || $firstname == '' || $lastname == '' || $manager == '-1'){
    echo 'Please fill all fields';
}else{
    $sql = "SELECT * from `signup` where `email` = ?";
    $stmt = $db->prepare($sql);
   $stmt->execute(array($emailID));
   $rows = $stmt->fetch();
   //echo $rows['email'] . "\n";
    
    if($rows){
        echo 'email already present';
    }else{

        $sql = "INSERT INTO `SIGNUP` (`email`, `password`, `firstname`, `lastname`, `contactNumber`, `manager` ) values(?,?,?,?,?,?)";
        $stmt = $db->prepare($sql);
        $rows = $stmt->execute(array($emailID, $password, $firstname, $lastname, $number, $manager));
        //echo "rows::$rows";
        if($rows){
            echo 'successfully registered';
        }else{
            echo 'please try again';
        }
    }
}


?>