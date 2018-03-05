<?php 

    $color=$_GET['search'];
    $url = 'http://www.colourlovers.com/api/colors/new?format=json';
    $json = file_get_contents($url);
    $array = json_decode($json, true);
for ($i=0; $i<20; $i++)
{
	$title = $array[$i]['title'];
    $code = $array[$i]["hex"];
    $r = $array[$i]["rgb"]['red'];
    $g = $array[$i]["rgb"]['green'];
    $b = $array[$i]["rgb"]['blue'];
    echo "<br>name: ".$title."<br>";
    echo "code: ".$code."<br>";
    echo "red: ".$r."<br>";
    echo "green: ".$g."<br>";
    echo "blue: ".$b."<br>";
    echo "<table border='1' width='100' height='100' style='background-color: #".$code."'>";
    echo "<tr></tr></table>";
}
?>
