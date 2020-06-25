<?php


$dbUser = "root";
$dbPass = "Campus@13";
$dbHost = "127.0.0.1:3306";
$dbName = "schedule_management";

$weeks = array('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday');



$scheduleDetails = $_GET['scheduleDetails'];
//$emp_id = $_GET['emp_id'];

$db = new PDO("mysql:host=$dbHost;dbname=$dbName", $dbUser, $dbPass);

$response = array();
$dbData = array();
//array_push($response,"successfully inserted");

$schedule = json_decode($scheduleDetails,true);


if($scheduleDetails == ""){
    $dbData['message'] = "Fields are missing";
    // echo json_encode($dbData);
}else{
    //$dbData['message'] = "count" . count($schedule);
    foreach($schedule as $sched){
       //$dbData['message'] = $dbData['message'] .  "count" . $sched['emp_id'];

        $sql = "SELECT * from `schedule` where `emp_id` = ? and `shift_date` = ?";
        $stmt = $db->prepare($sql);
        $stmt->execute(array($sched['emp_id'],$sched['shift_date']));
        $rows = $stmt->fetch();

        if($rows){
        $dbData['message'] = $dbData['message']  . "Already present";
        $sql = "UPDATE `schedule` SET `start_time` = ?, `end_time` = ?  WHERE `emp_id` = ? and `shift_date` = ?";
        $stmt = $db->prepare($sql);
        $rows = $stmt->execute(array( $sched['start_time'],$sched['end_time'],$sched['emp_id'],$sched['shift_date']));
    
    }else{
        

        $sql = "INSERT INTO `schedule` (`emp_id`, `shift_date`, `start_time`, `end_time` ) values(?,?,?,?)";
        $stmt = $db->prepare($sql);
        $rows = $stmt->execute(array($sched['emp_id'], $sched['shift_date'], $sched['start_time'],$sched['end_time']));
        if($rows){
            $dbData['message'] = $dbData['message'] . "Successfully inserted " . $sched['emp_id'];
    
        }else{
            $dbData['message'] = $dbData['message'] . "Not inserted" . $sched['emp_id'];
   
        }
    }

    }


}
echo json_encode($dbData);


?>