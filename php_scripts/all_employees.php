<?php


$dbUser = "root";
$dbPass = "Campus@13";
$dbHost = "127.0.0.1:3306";
$dbName = "schedule_management";

//echo "my hi";
//$emailID = $_POST['email'];
//$_GET['emailID'];
//'kashya@gmail.com';
//$password = $_POST['password'];
//$_GET['password'];
//'Campus13';


//$_GET['number'];
//'7655456543';
//echo "before" . $_SERVER['REQUEST_METHOD'];


$db = new PDO("mysql:host=$dbHost;dbname=$dbName", $dbUser, $dbPass);


    $sql = "SELECT * from `signup` where manager = 0";
    $stmt = $db->prepare($sql);
   $stmt->execute();
   //$rows = $stmt->fetch();

    
    // if($rows){
    //     echo json_encode($rows);
    // }else{

    //     echo 'User not present please signup';
    // }

   $arrayResult = array();
while ($result = $stmt->fetch()) {
    //$arrayResult.push($result);
    array_push($arrayResult,$result);

}

echo json_encode($arrayResult);



?>