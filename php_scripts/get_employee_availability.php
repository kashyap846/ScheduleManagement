<?php


$dbUser = "root";
$dbPass = "Campus@13";
$dbHost = "127.0.0.1:3306";
$dbName = "schedule_management";

$weeks = array('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday');
//<item>Monday</item>
// <item>Tuesday</item>
// <item>Wednesday</item>
// <item>Thursday</item>
// <item>Friday</item>
// <item>Saturday</item>
// <item>Sunday</item>

//echo "my hi";
//$emailID = $_GET['email'];
//$_GET['emailID'];
//'kashya@gmail.com';
//$password = $_GET['password'];
//$_GET['password'];
//'Campus13';

//$firstname = $_GET['firstname'];
//$_GET['firstname'];
//'kashyap';
//$lastname = $_GET['lastname'];
//$_GET['lastname'];
//'kokkili';



$emp_id = $_GET['emp_id'];
//$number = $_GET['number'];

//$manager = $_GET['manager'];
//echo "manager " . $manager. "  " . $manager == '-1';
//$_GET['number'];
//'7655456543';
//echo "before" . $_SERVER['REQUEST_METHOD'];
//echo "emailb  " . $emailID . "  passowrd   " . $password . "   firstname   " . $firstname ."   lastname   ". $lastname . "  number   " . $number;

$db = new PDO("mysql:host=$dbHost;dbname=$dbName", $dbUser, $dbPass);

// $response = array();
// $dbData = array();
// array_push($response,"successfully inserted");

// $shift = json_decode($shift_Details,true);

//echo $yummy->type;
//$dbData['message'] = "successfully inserted " . $shift['Monday']['shiftStart'];
// echo json_encode($dbData);

// if($emp_id == ""){
//     $dbData['message'] = "Fields are missing";
//     // echo json_encode($dbData);
// }else{
    
    $sql = "SELECT * from `availability` where `emp_id` = ?";
    $stmt = $db->prepare($sql);
   $stmt->execute(array($emp_id));
   //$rows = $stmt->fetch();
   //echo $rows['email'] . "\n";
   //echo json_encode($rows);
   $arrayResult = array();
   while ($result = $stmt->fetch()) {
    //$arrayResult.push($result);
    array_push($arrayResult,$result);

}
    echo json_encode($arrayResult);

//}


//{"Monday":{"shiftEnd":"END","shiftStart":"START","week":"Monday"},"Wednesday":{"shiftEnd":"END","shiftStart":"START","week":"Wednesday"}}

// CREATE TABLE availability (
//     id INT(6) AUTO_INCREMENT PRIMARY KEY,
//     emp_id INT(6) NOT NULL,
//     day VARCHAR(30) NOT NULL,
//     shift_start VARCHAR(30) NOT NULL,
//     shift_end VARCHAR(30) NOT NULL
//     );

//<item>Monday</item>
// <item>Tuesday</item>
// <item>Wednesday</item>
// <item>Thursday</item>
// <item>Friday</item>
// <item>Saturday</item>
// <item>Sunday</item>
?>