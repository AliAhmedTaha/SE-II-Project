<?php 

	$color=$_GET['search'];
	$url = 'http://www.colourlovers.com/api/color/'.$color.'?format=json';
    $json = file_get_contents($url);
    $array = json_decode($json, true);
    $title = $array[0]['title'];
    $code = $array[0]["hex"];
    $r = $array[0]["rgb"]['red'];
    $g = $array[0]["rgb"]['green'];
    $b = $array[0]["rgb"]['blue'];
    echo "name: ".$title."<br>";
    echo "code: ".$code."<br>";
    echo "red: ".$r."<br>";
    echo "green: ".$g."<br>";
    echo "blue: ".$b."<br>";

?>
<br>
<table width="100" height="100" style="background-color: #<?php echo $code ?>">
	<tr ></tr>
</table>
