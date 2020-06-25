<?php


$dbUser = "root";
$dbPass = "Campus@13";
$dbHost = "127.0.0.1:3306";
$dbName = "schedule_management";

$weeks = array('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday');



$shift_Details = $_GET['shiftDetails'];
$emp_id = $_GET['emp_id'];

$db = new PDO("mysql:host=$dbHost;dbname=$dbName", $dbUser, $dbPass);

$response = array();
$dbData = array();
//array_push($response,"successfully inserted");

$shift = json_decode($shift_Details,true);


if($emp_id == "" || $shift_Details == ""){
    $dbData['message'] = "Fields are missing";
    // echo json_encode($dbData);
}else{
    foreach($weeks as $week){
    $sql = "SELECT * from `availability` where `emp_id` = ? and `day` = ?";
    $stmt = $db->prepare($sql);
   $stmt->execute(array($emp_id,$week));
   $rows = $stmt->fetch();
   //echo $rows['email'] . "\n";
    
    if($rows){
        $dbData['message'] = $dbData['message']  . "Already present 111" . $emp_id .$week .$shift[$week]['shift_start'];
        $sql = "UPDATE `availability` SET `shift_start` = ?, `shift_end` = ?  WHERE `emp_id` = ? and `day` = ?";
        $stmt = $db->prepare($sql);
        $rows = $stmt->execute(array( $shift[$week]['shift_start'],$shift[$week]['shift_end'],$emp_id, $shift[$week]['day']));
    // echo json_encode($dbData);
    }else{
        

        $sql = "INSERT INTO `availability` (`emp_id`, `day`, `shift_start`, `shift_end` ) values(?,?,?,?)";
        $stmt = $db->prepare($sql);
        $rows = $stmt->execute(array($emp_id, $shift[$week]['day'], $shift[$week]['shift_start'],$shift[$week]['shift_end']));
        if($rows){
            $dbData['message'] = $dbData['message'] . "Successfully inserted " .  $week;
    // echo json_encode($dbData);
        }else{
            $dbData['message'] = $dbData['message'] . "Not inserted" . $emp_id . $week.$shift[$week]['day'] . $shift['$week']['shift_start'] . $shift[$week]['shift_end'] .$shift[$week];
    // echo json_encode($dbData);
        }
    }
}

}
echo json_encode($dbData);


?>